package com.main.fastserver.Scheduled;

import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Entity.SubDomain;
import com.main.fastserver.Service.DomainService;
import com.main.fastserver.Service.SubDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    DomainService domainService;
    @Autowired
    SubDomainService subDomainService;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(fixedRate = 5000)
    public void update() {
        File repo = new File("src/main/resources/domains");
        List<Domain> domainsFile = getRepositoryDomains(repo);
        List<Domain> domainsPresentDatabase = domainService.findAll();
        for(Domain dFile : domainsFile) {
            boolean isPresent = false;
            for(Domain dDatabase : domainsPresentDatabase) {
                if(dFile.getTitle().equals(dDatabase.getTitle())) {
                    isPresent = true;
                }
             }
            if(!isPresent) {
                log.info("DOMAIN CREATE");
                domainService.createDomain(dFile);
            }else{
                log.info("NOT UPDATE");
            }
        }

        for(Domain dDatabase : domainsPresentDatabase) {
            boolean isPresent = false;
            List<SubDomain> subDomainsInFile = new ArrayList<>();
            for(Domain dFile : domainsFile) {
                subDomainsInFile = dFile.getSubdomains();
                if(dFile.getTitle().equals(dDatabase.getTitle())) {
                    List<SubDomain> subDomainsInDomainDb = dDatabase.getSubdomains();
                    updateSubDomain(dDatabase, subDomainsInDomainDb, subDomainsInFile);
                    isPresent = true;
                }
            }
            if(!isPresent) {
                log.info(dDatabase.getTitle() + " IS DELETE");
                List<SubDomain> listSubDomain = dDatabase.getSubdomains();
                if (listSubDomain != null) {
                    for (SubDomain s : listSubDomain) {
                        subDomainService.deleteSubDomain(s);
                        log.info(s.getTitle() + "is deleted");
                    }
                }
                domainService.deleteDomain(dDatabase);
            }else{

                log.info("NOT DELETE");
            }
        }
    }

    public void updateSubDomain(Domain domain, List<SubDomain> subDomainsInDataBase, List<SubDomain> subDomainsInFile) {
        log.info("salut");
        for (SubDomain subDomain : subDomainsInFile){
            boolean isPresent = false;
            for( SubDomain subDomainDataBase : subDomainsInDataBase){
                if (subDomain.getTitle().equals(subDomainDataBase.getTitle())){
                    isPresent = true;
                }
            }
            if (!isPresent){
                List<SubDomain> subDomainList = domain.getSubdomains();
                subDomainList.add(new SubDomain(subDomain.getTitle(), null));
                domain.setSubdomains(subDomainList);
                domainService.updateDomain(domain);
                log.info(subDomain.getTitle() + " IS CREATED");
            }
        }

        for (SubDomain subDomainDatabase : subDomainsInDataBase){
            boolean isPresent = false;
            for (SubDomain subDomainFile : subDomainsInFile){
                if (subDomainDatabase.getTitle().equals(subDomainFile.getTitle())){
                    isPresent = true;
                }
            }
            if (!isPresent){
                subDomainService.deleteSubDomain(subDomainDatabase);
                log.info(subDomainDatabase.getTitle() + " IS DELETED");
            }
        }
    }

    public List<Domain> getRepositoryDomains(File repository) {
        List<Domain> domains = new ArrayList<>();
        if(repository.isDirectory()) {
            File[] files = repository.listFiles();
            for(File file: files) {
                List<SubDomain> subDomainList = new ArrayList<>();
                if (file.isDirectory()){
                    File [] subDomainTab = file.listFiles();
                    for (File subDomain : subDomainTab){
                        SubDomain s = new SubDomain(subDomain.getName(),null);
                        subDomainList.add(s);
                    }
                }
                Domain d = new Domain(file.getName(), "icon", subDomainList);
                domains.add(d);
            }
        }else {
            log.info(repository.getAbsolutePath());
        }
        return domains;
    }
}
