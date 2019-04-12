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
        for(Domain domainDataBase : domainsInDataBase) {
            if(!domainsMap.containsKey(domainDataBase.getTitle())) {
                List<SubDomain> subDomains = domainDataBase.getSubdomains();
                for(SubDomain subDomain : subDomains) {
                    subDomainService.deleteSubDomain(subDomain);
                    log.info(subDomain.getTitle() + " IS DELETED");
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
                            for (Skill skillDataBase : skillsInDataBase) {
                                if (!domainsMap.get(domainDataBase.getTitle()).get(subDomainDataBase.getTitle()).containsKey(skillDataBase.getTitle())) {
                                    skillService.delete(subDomainDataBase.getTitle(), skillDataBase.getTitle());
                                }
                            }
                        }
                    }
                }
                domainsMap.remove(domainDataBase.getTitle());
            }
        }

        

        for(String titleDomain : domainsMap.keySet()) {
            Domain domain = Domain.builder().title(titleDomain).icon("icon").build();
            domainService.createDomain(domain);
            List<SubDomain> subDomains = new ArrayList<>();
            for(String titleSubdomain : domainsMap.get(titleDomain).keySet()) {
                SubDomain subDomain = SubDomain.builder().title(titleDomain).build();
                List<Skill> skills = new ArrayList<>();
                for(String titleSkill : domainsMap.get(titleDomain).get(titleSubdomain).keySet()) {
                    skills.add(Skill.builder().title(titleSkill).description("...").build());
                }
                subDomain.setSkills(skills);
                subDomains.add(subDomain);
                domain.setSubdomains(subDomains);
                domainService.updateDomain(domain);
            }
        }
    }
}
