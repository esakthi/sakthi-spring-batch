package com.spring.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomerItemReader implements ItemReader<String> {

    private Logger logger = LoggerFactory.getLogger(CustomerItemReader.class);

    private List<String> list = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven");
    int i = 0;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("Inside Item Reader");
        String item;
        if(i < list.size()) {
            item = list.get(i);
            i++;
            return item;
        }
        i = 0;
        return null;
    }
}
