package com.main.fastserver.Scheduled;

import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Entity.Skill;
import com.main.fastserver.Entity.SubDomain;
import com.main.fastserver.Scheduled.dto.DomainDTO;
import com.main.fastserver.Scheduled.dto.SubDomainDTO;
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

    public Domain convertToEntity(DomainDTO domainDTO) {
        Domain domain = modelMapper().map(domainDTO, Domain.class);
        return domain;
    }

    public List<DomainDTO> domainRetainAll(List<DomainDTO> domains1, List<DomainDTO> domains2) {
        List<DomainDTO> tmp = new ArrayList<>(domains1);
        tmp.retainAll(domains2);
        return tmp;
    }

    public List<DomainDTO> domainRemoveAll(List<DomainDTO> domains1, List<DomainDTO> domains2) {
        List<DomainDTO> tmp = new ArrayList<>(domains1);
        tmp.removeAll(domains2);
        return tmp;
    }

    public List<SubDomainDTO> subDomainRetainAll(List<SubDomainDTO> subDomain1, List<SubDomainDTO> subDomain2) {
        List<SubDomainDTO> tmp = new ArrayList<>(subDomain1);
        tmp.retainAll(subDomain2);
        return tmp;
    }

    public List<SubDomainDTO> subDomainRemoveAll(List<SubDomainDTO> subDomain1, List<SubDomainDTO> subDomain2) {
        List<SubDomainDTO> tmp = new ArrayList<>(subDomain1);
        tmp.removeAll(subDomain2);
        return tmp;
    }

    public void process(List<DomainDTO> domainsInFile) {
        List<Domain> domainsInDatabase = domainService.findAll();
        List<DomainDTO> domainsDTOInDatabase = new ArrayList<>();
        for(Domain domain : domainsInDatabase) {
            domainsDTOInDatabase.add(convertToDto(domain));
        }
        List<DomainDTO> domainsInFileRetain = domainRetainAll(domainsInFile, domainsDTOInDatabase);
        List<DomainDTO> domainsInFileRemove = domainRemoveAll(domainsInFile, domainsDTOInDatabase);
        if(!domainsInFileRemove.isEmpty()) {
            for(DomainDTO domain : domainsInFileRemove) {
                domainService.createDomain(convertToEntity(domain));
            }
        }
        if(!domainsInFileRetain.isEmpty()) {
            for(DomainDTO domain : domainsInFileRetain) {
                // ici je doit je vérifier les sous domaines pour lesquels le domaine existe déja.
                // je peux donc récuperer les sous domaines du domaine dans les fichiers mais je ne peux
                // pas avoir les sous domaines du domaine de la base de données.
            }
        }
    }
}
