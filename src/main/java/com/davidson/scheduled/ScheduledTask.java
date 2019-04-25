package com.davidson.scheduled;

import com.davidson.skill.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Scheduled task that updates the database according to the files present in /resources/main
 */
@Component
public class ScheduledTask {

    @Autowired
    ScanService scanService;

    @Autowired
    ProcessService processService;

    /**
     * process called every x time
     */
    @Scheduled(cron = "0 0 6 * * *")
    public void update() throws Exception {
        Map<String, Map<String, Map<String, Skill>>> domainsMap = scanService.mapDomain("src/main/resources/domains");
        processService.processMain(domainsMap);
    }
}
