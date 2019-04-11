package com.main.fastserver.Scheduled;

import com.main.fastserver.Entity.Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(Test.class);

    public List<Domain> GetRepositoryDomains;
}
