package com.davidson.scheduled;

import com.davidson.skill.Skill;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.io.Charsets;
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

    private Gson gson = new GsonBuilder().create();

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
                            JsonObject jsonSkill = gson.fromJson(Files.toString(skillFile, Charsets.UTF_8), JsonObject.class);
                            Skill skill = Skill.builder()
                                    .title(jsonSkill.get("title").getAsString())
                                    .description(jsonSkill.get("description").getAsString())
                                    .build();
                            mapSkill.put(jsonSkill.get("title").getAsString(), skill);
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
