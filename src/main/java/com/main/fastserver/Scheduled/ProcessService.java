package com.main.fastserver.Scheduled;

import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Entity.Skill;
import com.main.fastserver.Entity.SubDomain;
import com.main.fastserver.Scheduled.dto.DomainDTO;
import com.main.fastserver.Service.DomainService;
import com.main.fastserver.Service.SkillService;
import com.main.fastserver.Service.SubDomainService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
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

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public DomainDTO convertToDto(Domain domain) {
        DomainDTO domainDTO = modelMapper().map(domain, DomainDTO.class);
        return domainDTO;
    }

    public void process(List<DomainDTO> domainsInFile) {
        List<Domain> domainsInDatabase = domainService.findAll();
        List<DomainDTO> domainsDTOInDatabase = new ArrayList<>();
        for(Domain domain : domainsInDatabase) {
            domainsDTOInDatabase.add(convertToDto(domain));
        }
    }
}
