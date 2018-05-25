package com.daniel.security.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * on 5/25/2018.
 */
@RestController
public class AsyncController {
    private Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @GetMapping("/order")
    public Callable<String> order() {
        logger.info("main thrad start ... ");

        Callable<String> call = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("secondary thrad start ... ");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                logger.info("secondary thrad finish ... ");
                return "success";
            }
        };

        logger.info("main thrad finish ... ");
        return call;
    }
}
