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
        for(String titleDomain : domainsMap.keySet()) {
            boolean isPresent = false;
            for(Domain domainInDatabase : domainsInDataBase) {
                if(titleDomain.equals(domainInDatabase.getTitle())) {
                    isPresent = true;
                    List<SubDomain> subDomains = domainInDatabase.getSubdomains();
                    for(String titleSubDomain : domainsMap.get(titleDomain).keySet()) {
                        boolean isPresentSubDomain = false;
                        if(subDomains != null) {
                            for (SubDomain subDomain : subDomains) {
                                if (titleSubDomain.equals(subDomain.getTitle())) {
                                    isPresentSubDomain = true;
                                    List<Skill> skills = subDomain.getSkills();
                                    for(String titleSkill : domainsMap.get(titleDomain).get(titleSubDomain).keySet()) {
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
                                            subDomain.setSkills(skills);
                                            domainService.updateDomain(domainInDatabase);
                                        }
                                    }
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

    public void processDelete(Map<String, Map<String, Map<String, Skill>>> domainsMap, List<Domain> domainsInDataBase) {
        for(Domain domainDataBase : domainsInDataBase) {
            if(!domainsMap.containsKey(domainDataBase.getTitle())) {
                List<SubDomain> subDomains = domainDataBase.getSubdomains();
                if(subDomains != null) {
                    for (SubDomain subDomain : subDomains) {
                        subDomainService.deleteSubDomain(subDomain);
                        log.info(subDomain.getTitle() + " IS DELETED");
                    }
                }
                domainService.deleteDomain(domainDataBase);
                log.info(domainDataBase.getTitle() + " IS DELETED");
            }else {
                List<SubDomain> subDomainsInDataBase = domainDataBase.getSubdomains();
                if(subDomainsInDataBase != null) {
                    for (SubDomain subDomainDataBase : subDomainsInDataBase) {
                        if (!domainsMap.get(domainDataBase.getTitle()).containsKey(subDomainDataBase.getTitle())) {
                            subDomainService.deleteSubDomain(subDomainDataBase);
                            log.info(subDomainDataBase.getTitle() + " IS DELETED");
                        } else {
                            List<Skill> skillsInDataBase = subDomainDataBase.getSkills();
                            if(skillsInDataBase != null) {
                                for (Skill skillDataBase : skillsInDataBase) {
                                    if (!domainsMap.get(domainDataBase.getTitle()).get(subDomainDataBase.getTitle()).containsKey(skillDataBase.getTitle())) {
                                        skillService.delete(subDomainDataBase.getTitle(), skillDataBase.getTitle());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
