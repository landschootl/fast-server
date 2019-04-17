package com.main.fastserver.scheduled;

import com.main.fastserver.entity.Skill;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * service that will be called every x time by ScheduledTask
 * service that scans the files in /resources/domains and creates a map with the corresponding domains, sub-domains and skills
 */
@Service
public class ScanService {

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
