package com.abiorh.smartEdConnect.authentication.service;

import com.abiorh.smartEdConnect.authentication.config.JwtService;
import com.abiorh.smartEdConnect.authentication.dto.UserResponseDto;
import com.abiorh.smartEdConnect.authentication.entity.Role;
import com.abiorh.smartEdConnect.authentication.entity.User;
import com.abiorh.smartEdConnect.authentication.dto.UserDto;
import com.abiorh.smartEdConnect.authentication.repository.UserRepository;
import com.abiorh.smartEdConnect.authentication.request.AuthenticationRequest;
import com.abiorh.smartEdConnect.authentication.response.AuthenticationResponse;
import com.abiorh.smartEdConnect.globalConfig.customException.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public UserResponseDto registerUser(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        User newUser = userRepository.save(user);
        return modelMapper.map(newUser, UserResponseDto.class);
    }



    public AuthenticationResponse generateJwtToken(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        User user = userRepository.findUserByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(token)
                .build();
    }
}
