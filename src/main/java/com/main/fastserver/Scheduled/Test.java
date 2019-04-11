package com.main.fastserver.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Test {

    private static final Logger log = LoggerFactory.getLogger(Test.class);

    @Scheduled()
    public void hello() {
        log.info("Hello World!");
    }

}
