package com.main.fastserver.Scheduled;


import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.main.fastserver.Entity.Skill;
import com.main.fastserver.Scheduled.dto.DomainDTO;
import com.main.fastserver.Scheduled.dto.SkillDTO;
import com.main.fastserver.Scheduled.dto.SubDomainDTO;
import org.apache.commons.io.Charsets;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    public List<DomainDTO> getDomainsFile(String path) throws Exception {
        File resources = new File(path);
        List<DomainDTO> domains = new ArrayList<>();
        File[] domainsFile = resources.listFiles();
        for(File domainFile : domainsFile) {
            DomainDTO domain = DomainDTO.builder().title(domainFile.getName()).build();
            List<SubDomainDTO> subDomains = new ArrayList<>();
            File[] subDomainsFile = domainFile.listFiles();
            for(File subDomainFile : subDomainsFile) {
                if(subDomainFile.isDirectory()) {
                    SubDomainDTO subDomain = SubDomainDTO.builder().title(subDomainFile.getName()).build();
                    List<SkillDTO> skills = new ArrayList<>();
                    File[] skillsFile = subDomainFile.listFiles();
                    for(File skillFile : skillsFile) {
                        JsonObject jsonSkill = gson.fromJson(Files.toString(skillFile, Charsets.UTF_8), JsonObject.class);
                        SkillDTO skill = SkillDTO.builder()
                                .title(skillFile.getName())
                                // utiliser json quand il y aura title dans le fichier
                               // .title(jsonSkill.get("title").getAsString())
                                .description(jsonSkill.get("description").getAsString())
                                .build();
                        skills.add(skill);
                    }
                    subDomain.setSkills(skills);
                    subDomains.add(subDomain);
                }else{
                    JsonObject jsonDomain = gson.fromJson(Files.toString(subDomainFile, Charsets.UTF_8), JsonObject.class);
                    domain.setIcon(jsonDomain.get("icon").getAsString());
                }
            }
            domain.setSubDomains(subDomains);
            domains.add(domain);
        }
        return domains;
    }
}
