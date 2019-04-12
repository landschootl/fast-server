package com.main.fastserver.Scheduled;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private final GsonBuilder builder = new GsonBuilder();
    private final Gson gson = builder.create();

    @Scheduled(fixedRate = 5000)
    public void update() {
        File repo = new File("src/main/resources/domains");
        File[] domainsFile = repo.listFiles();
        List<Domain> domains = new ArrayList<>();
        for(File file : domainsFile ) {

        }
        log.info(gson.toJson(domains));
    }
}
