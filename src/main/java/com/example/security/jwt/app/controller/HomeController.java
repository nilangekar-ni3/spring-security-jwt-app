package com.example.security.jwt.app.controller;

import com.example.security.jwt.app.entity.TokenRequest;
import com.example.security.jwt.app.helper.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String getHomePage() {
        return "This is Home Page";
    }

    @PostMapping("/validateUser")
    public String generateJwtToken(@RequestBody TokenRequest tokenRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(tokenRequest.getUserName(), tokenRequest.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new Exception("There is some issue while authenticating user");
            return "Invalid Username/Password";
        }
        return jwtTokenUtil.generateToken(tokenRequest.getUserName());
    }
}
