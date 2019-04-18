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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service that creates or deletes nodes from the database based on what is in the map returned by ScanService
 */
@Service
public class ProcessService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private DomainService domainService;

    @Autowired
    private SubDomainService subDomainService;

    @Autowired
    private SkillService skillService;

    /**
     * main process that will be called every x time by ScheduledTask
     * @param domainsMap
     */
    public void processMain(Map<String, Map<String, Map<String, Skill>>> domainsMap) {
        List<Domain> domainsInDataBase = domainService.findAll();
        this.processDelete(domainsMap, domainsInDataBase);
        this.processCreate(domainsMap, domainsInDataBase);
    }

    /**
     * process that manages the deletion of nodes in the database based on what is in /resources/domains
     * @param domainsMap
     * @param domainsInDataBase
     */
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

    /**
     * process that manages the creation of nodes in the database based on what is in /resources/domains
     * @param domainsMap
     * @param domainsInDataBase
     */
    public void processCreate(Map<String, Map<String, Map<String, Skill>>> domainsMap, List<Domain> domainsInDataBase) {
        for(String titleDomain : domainsMap.keySet()) {
            boolean isPresentDomain = false;
            for(Domain domainInDatabase : domainsInDataBase) {
                if(titleDomain.equals(domainInDatabase.getTitle())) {
                    isPresentDomain = true;
                    this.updateSubDomains(domainsMap.get(titleDomain), domainInDatabase);
                }
            }
            if(!isPresentDomain) {
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

    /**
     * updates subdomains if the domain is already present in the database based on what is in /resources/domains
     * @param subDomainsMap
     * @param domainInDatabase
     */
    public void updateSubDomains(Map<String, Map<String, Skill>> subDomainsMap, Domain domainInDatabase) {
        List<SubDomain> subDomainsInDatabase = domainInDatabase.getSubdomains();
        for(String titleSubDomain : subDomainsMap.keySet()) {
            boolean isPresentSubDomain = false;
            if(subDomainsInDatabase != null) {
                for (SubDomain subDomainInDatabase : subDomainsInDatabase) {
                    if (titleSubDomain.equals(subDomainInDatabase.getTitle())) {
                        isPresentSubDomain = true;
                        this.updateSkills(subDomainsMap.get(titleSubDomain), subDomainInDatabase, domainInDatabase);
                    }
                }
            }
            if(!isPresentSubDomain) {
                if (subDomainsInDatabase == null) {
                    subDomainsInDatabase = new ArrayList<>();
                    subDomainsInDatabase.add(SubDomain.builder().title(titleSubDomain).skills(null).build());
                } else {
                    subDomainsInDatabase.add(SubDomain.builder().title(titleSubDomain).skills(null).build());
                }
                domainInDatabase.setSubdomains(subDomainsInDatabase);
                domainService.updateDomain(domainInDatabase);
            }
        }
    }

    /**
     * updates skills if the subdomain is already in the database based on what is in /resources/domains
     * @param skillsMap
     * @param subDomainInDatabase
     * @param domainInDatabase
     */
    public void updateSkills(Map<String, Skill> skillsMap, SubDomain subDomainInDatabase, Domain domainInDatabase) {
        List<Skill> skillsInDatabase = subDomainInDatabase.getSkills();
        for(String titleSkill : skillsMap.keySet()) {
            boolean isPresentSkill = false;
            if(skillsInDatabase != null) {
                for(Skill skillInDatabase : skillsInDatabase) {
                    if(titleSkill.equals(skillInDatabase.getTitle())) {
                        isPresentSkill = true;
                    }
                }
            }
            if(!isPresentSkill) {
                if(skillsInDatabase == null) {
                    skillsInDatabase = new ArrayList<>();
                }
                skillsInDatabase.add(Skill.builder().title(titleSkill).description("...").build());
                subDomainInDatabase.setSkills(skillsInDatabase);
                domainService.updateDomain(domainInDatabase);
            }
        }
    }

    /**
     * deletes the domain and its subdomains if the domain is present in the database but not present in /resources/domains
     * @param domainInDatabase
     */
    public void deleteDomainWithSubDomains(Domain domainInDatabase) {
        List<SubDomain> subDomainsInDatabase = domainInDatabase.getSubdomains();
        if(subDomainsInDatabase != null) {
            for (SubDomain subDomainInDatabase : subDomainsInDatabase) {
                subDomainService.deleteSubDomain(subDomainInDatabase);
                log.info(subDomainInDatabase.getTitle() + " IS DELETED");
            }
        }
        domainService.deleteDomain(domainInDatabase);
        log.info(domainInDatabase.getTitle() + " IS DELETED");
    }

    /**
     * deletes the subdomain and its skills if the domain is ok and its subdomains are
     * present in the database but not present in /resources/domains
     * @param subDomainMap
     * @param subDomainInDatabase
     */
    public void deleteSubDomainsWithSkills(Map<String, Map<String, Skill>> subDomainMap, SubDomain subDomainInDatabase) {
        if (!subDomainMap.containsKey(subDomainInDatabase.getTitle())) {
            subDomainService.deleteSubDomain(subDomainInDatabase);
            log.info(subDomainInDatabase.getTitle() + " IS DELETED");
        } else {
            List<Skill> skillsInDataBase = subDomainInDatabase.getSkills();
            if(skillsInDataBase != null) {
                for (Skill skillInDatabase : skillsInDataBase) {
                    if (!subDomainMap.get(subDomainInDatabase.getTitle()).containsKey(skillInDatabase.getTitle())) {
                        skillService.deleteSkill(skillInDatabase.getId());
                        log.info(skillInDatabase.getTitle() + " IS DELETED");
                    }
                }
            }
        }
    }
}
