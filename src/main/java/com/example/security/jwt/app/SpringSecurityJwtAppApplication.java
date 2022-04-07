package com.example.security.jwt.app;

import com.example.security.jwt.app.entity.User;
import com.example.security.jwt.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringSecurityJwtAppApplication {

    @Autowired
    public UserRepository userRepository;

    @PostConstruct
    public void initUsersInfo(){
        List<User> userList=Stream.of(new User(101,"user1", "pass1", "Pune"),
                        new User(102,"user2", "pass2", "Mumbai"),
        new User(103,"user3", "pass3", "Delhi"),
        new User(104,"user4", "pass4", "Chennai")).
                collect(Collectors.toList());
        userRepository.saveAll(userList);

    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtAppApplication.class, args);
    }

}
