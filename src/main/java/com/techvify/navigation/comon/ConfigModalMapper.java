package com.techvify.navigation.comon;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigModalMapper {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
