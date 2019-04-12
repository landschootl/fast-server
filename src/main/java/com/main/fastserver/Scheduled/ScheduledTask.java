package com.main.fastserver.Scheduled;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Entity.Skill;
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
import java.util.Map;

@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    ScanService scanService;

    @Autowired
    ProcessService processService;

    @Scheduled(fixedRate = 5000)
    public void update() {
        Map<String, Map<String, Map<String, Skill>>> domainsMap = scanService.mapDomain("src/main/resources/domains");
        processService.process(domainsMap);
    }
}
