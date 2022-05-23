package com.spring.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CustomerItemProcessor implements ItemProcessor<String, String> {

    private Logger logger = LoggerFactory.getLogger(CustomerItemProcessor.class);

    @Override
    public String process(String s) throws Exception {
        logger.info("Inside Item Processor");
        return s + " Enriched";
    }
}
