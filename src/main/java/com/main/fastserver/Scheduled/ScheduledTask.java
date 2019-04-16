package com.main.fastserver.Scheduled;

import com.main.fastserver.Entity.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
