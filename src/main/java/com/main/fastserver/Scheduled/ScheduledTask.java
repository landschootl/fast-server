package com.main.fastserver.Scheduled;

import com.main.fastserver.Entity.Skill;
import com.main.fastserver.Scheduled.dto.DomainDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Scheduled task that updates the database according to the files present in /resources/main
 */
@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    ScanService scanService;

    @Autowired
    ProcessService processService;

    /**
     * process called every x time
     */
    @Scheduled(fixedRate = 10000)
    public void update() throws Exception {
        List<DomainDTO> domainsList = scanService.getDomainsFile("src/main/resources/domains");
        processService.process(domainsList);
    }
}
