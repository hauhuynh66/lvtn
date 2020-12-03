package com.lvtn.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Utils {
    static private Logger log = LogManager.getLogger();
    public BCryptPasswordEncoder encoder(int n){
        return new BCryptPasswordEncoder(n);
    }

    public String objectMapper(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException jpe){
            log.info(jpe.getMessage());
        }
        return json;
    }
}
