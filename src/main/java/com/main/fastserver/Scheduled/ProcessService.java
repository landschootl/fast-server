package com.main.fastserver.Scheduled;

import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Entity.Skill;
import com.main.fastserver.Entity.SubDomain;
import com.main.fastserver.Service.DomainService;
import com.main.fastserver.Service.SkillService;
import com.main.fastserver.Service.SubDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProcessService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private DomainService domainService;

    @Autowired
    private SubDomainService subDomainService;

    @Autowired
    private SkillService skillService;

    public void process(Map<String, Map<String, Map<String, Skill>>> domainsMap) {
        List<Domain> domainsInDataBase = domainService.findAll();
        this.processDelete(domainsMap, domainsInDataBase);
        this.processCreate(domainsMap, domainsInDataBase);
    }

    public void processDelete(Map<String, Map<String, Map<String, Skill>>> domainsMap, List<Domain> domainsInDataBase) {
        for(Domain domainDataBase : domainsInDataBase) {
            if(!domainsMap.containsKey(domainDataBase.getTitle())) {
                this.deleteDomainWithSubDomains(domainDataBase);
            }else {
                List<SubDomain> subDomainsInDataBase = domainDataBase.getSubdomains();
                if(subDomainsInDataBase != null) {
                    for (SubDomain subDomainDataBase : subDomainsInDataBase) {
                        this.deleteSubDomainsWithSkills(domainsMap.get(domainDataBase.getTitle()), subDomainDataBase);
                    }
                }
            }
        }
    }

    public void processCreate(Map<String, Map<String, Map<String, Skill>>> domainsMap, List<Domain> domainsInDataBase) {
        for(String titleDomain : domainsMap.keySet()) {
            boolean isPresent = false;
            for(Domain domainInDatabase : domainsInDataBase) {
                if(titleDomain.equals(domainInDatabase.getTitle())) {
                    isPresent = true;
                    this.updateSubDomains(domainsMap.get(titleDomain), domainInDatabase);
                }
            }
            if(!isPresent) {
                List<SubDomain> subDomains = new ArrayList<>();
                for(String titleSubDomain : domainsMap.get(titleDomain).keySet()) {
                    List<Skill> skills = new ArrayList<>();
                    for(String titleSkill : domainsMap.get(titleDomain).get(titleSubDomain).keySet()) {
                        skills.add(domainsMap.get(titleDomain).get(titleSubDomain).get(titleSkill));
                    }
                    subDomains.add(SubDomain.builder().title(titleSubDomain).skills(skills).build());
                }
                domainService.createDomain(Domain.builder().title(titleDomain).icon("icon").subdomains(subDomains).build());
            }
        }
    }

    public void updateSubDomains(Map<String, Map<String, Skill>> subDomainsMap, Domain domainInDatabase) {
        List<SubDomain> subDomains = domainInDatabase.getSubdomains();
        for(String titleSubDomain : subDomainsMap.keySet()) {
            boolean isPresentSubDomain = false;
            if(subDomains != null) {
                for (SubDomain subDomain : subDomains) {
                    if (titleSubDomain.equals(subDomain.getTitle())) {
                        isPresentSubDomain = true;
                        this.updateSkills(subDomainsMap.get(titleSubDomain), subDomain, domainInDatabase);
                    }
                }
            }
            if(!isPresentSubDomain) {
                if (subDomains == null) {
                    subDomains = new ArrayList<>();
                    subDomains.add(SubDomain.builder().title(titleSubDomain).skills(null).build());
                } else {
                    subDomains.add(SubDomain.builder().title(titleSubDomain).skills(null).build());
                }
                domainInDatabase.setSubdomains(subDomains);
                domainService.updateDomain(domainInDatabase);
            }
        }
    }

    public void updateSkills(Map<String, Skill> skillsMap, SubDomain subDomainInDatabase, Domain domainInDatabase) {
        List<Skill> skills = subDomainInDatabase.getSkills();
        for(String titleSkill : skillsMap.keySet()) {
            boolean isPresentSkill = false;
            if(skills != null) {
                for(Skill skill : skills) {
                    if(titleSkill.equals(skill.getTitle())) {
                        isPresentSkill = true;
                    }
                }
            }
            if(!isPresentSkill) {
                if(skills == null) {
                    skills = new ArrayList<>();
                }
                skills.add(Skill.builder().title(titleSkill).description("...").build());
                subDomainInDatabase.setSkills(skills);
                domainService.updateDomain(domainInDatabase);
            }
        }
    }

    public void deleteDomainWithSubDomains(Domain domainDataBase) {
        List<SubDomain> subDomains = domainDataBase.getSubdomains();
        if(subDomains != null) {
            for (SubDomain subDomain : subDomains) {
                subDomainService.deleteSubDomain(subDomain);
                log.info(subDomain.getTitle() + " IS DELETED");
            }
        }
        domainService.deleteDomain(domainDataBase);
        log.info(domainDataBase.getTitle() + " IS DELETED");
    }

    public void deleteSubDomainsWithSkills(Map<String, Map<String, Skill>> subDomainMap, SubDomain subDomainDataBase) {
        if (!subDomainMap.containsKey(subDomainDataBase.getTitle())) {
            subDomainService.deleteSubDomain(subDomainDataBase);
            log.info(subDomainDataBase.getTitle() + " IS DELETED");
        } else {
            List<Skill> skillsInDataBase = subDomainDataBase.getSkills();
            if(skillsInDataBase != null) {
                for (Skill skillDataBase : skillsInDataBase) {
                    if (!subDomainMap.get(subDomainDataBase.getTitle()).containsKey(skillDataBase.getTitle())) {
                        skillService.delete(subDomainDataBase.getTitle(), skillDataBase.getTitle());
                        log.info(skillDataBase.getTitle() + " IS DELETED");
                    }
                }
            }
        }
    }
}
