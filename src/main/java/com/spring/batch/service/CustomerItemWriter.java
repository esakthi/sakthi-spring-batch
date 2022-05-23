package com.spring.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CustomerItemWriter implements ItemWriter<String> {

    private Logger logger = LoggerFactory.getLogger(CustomerItemWriter.class);

    @Override
    public void write(List<? extends String> list) throws Exception {
        logger.info("Inside Item Writer");
        list.stream().forEach( System.out :: println);
    }
}
