package com.main.fastserver.Scheduled;

import com.main.fastserver.Entity.Skill;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Service that will be called every x time by ScheduledTask
 * Service that scans the files in /resources/domains and creates a map with the corresponding domains, sub-domains and skills
 */
@Service
public class ScanService {

    /**
     * scan the folder /resources/domains and return a domain map with a subdomain map and a skill map
     * @param path
     * @return
     */
    public Map<String, Map<String, Map<String, Skill>>> mapDomain(String path) throws Exception {
        File resourcesFile = new File(path);
        if(!resourcesFile.isDirectory()) {throw new Exception("path is not directory");}
        Map<String, Map<String, Map<String, Skill>>> mapDomains = new HashMap<>();
        File[] domainsFile = resourcesFile.listFiles();
        for(File domainFile : domainsFile) {
            Map<String, Map<String, Skill>> mapSubDomains = new HashMap<>();
            File[] subDomainsFile = domainFile.listFiles();
            if(subDomainsFile != null) {
                for (File subDomainFile : subDomainsFile) {
                    Map<String, Skill> mapSkill = new HashMap<>();
                    File[] skillsFile = subDomainFile.listFiles();
                    if(skillsFile != null) {
                        for (File skillFile : skillsFile) {
                            mapSkill.put(skillFile.getName(), Skill.builder().title(skillFile.getName()).description("description").build());
                        }
                    }
                    mapSubDomains.put(subDomainFile.getName(), mapSkill);
                }
            }
            mapDomains.put(domainFile.getName(), mapSubDomains);
        }
        return mapDomains;
    }
}
