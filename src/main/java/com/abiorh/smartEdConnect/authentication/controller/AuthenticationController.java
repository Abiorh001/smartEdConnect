package com.abiorh.smartEdConnect.authentication.controller;

import com.abiorh.smartEdConnect.authentication.dto.UserDto;
import com.abiorh.smartEdConnect.authentication.dto.UserResponseDto;
import com.abiorh.smartEdConnect.authentication.request.AuthenticationRequest;
import com.abiorh.smartEdConnect.authentication.response.AuthenticationResponse;
import com.abiorh.smartEdConnect.authentication.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/smartedconnect.io/api/v1")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

    }


    @PostMapping("auth/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){

        UserResponseDto userResponseDto = authenticationService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PostMapping("auth/access_token")
    public ResponseEntity<AuthenticationResponse> generateJwtToken(
            @RequestBody AuthenticationRequest authenticationRequest) {

           AuthenticationResponse authenticationResponse = authenticationService.generateJwtToken(authenticationRequest);
           return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }

    @GetMapping("/demo")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> demoGet() {
        return ResponseEntity.status(HttpStatus.OK).body("testing this");
    }




}
