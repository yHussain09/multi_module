//package com.example.equeue.config;
//
//import com.example.equeue.domain.dao.OrganizerRepository;
//import com.example.equeue.domain.entities.Organizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OrganizerConfig {
//
//    private final OrganizerRepository repository;
//
//    @Autowired
//    public OrganizerConfig(OrganizerRepository repository) {
//        this.repository = repository;
//    }
//
//    @Bean
//    CommandLineRunner commandLineRunner (repository) {
//        return args -> {
//            new Organizer();
//        };
//    }
//}
