package com.solution.service;

import com.solution.bean.AuthenticationRequest;
import com.solution.bean.AuthenticationResponse;
import com.solution.bean.RegisterRequest;
import com.solution.config.JwtService;
import com.solution.config.constant.Messages;
import com.solution.config.exception.DuplicateRecordException;
import com.solution.config.exception.RecordNotFoundException;
import com.solution.config.exception.ServiceException;
import com.solution.model.Member;
import com.solution.model.enums.Role;
import com.solution.model.User;
import com.solution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final Messages messages;
    private final AuthenticationManager authenticationManager;

    /**
     * Save The User In Table From Provided Data in RegisterRequest and generate token
     * @param request
     * @return Token
     */
    public AuthenticationResponse register(RegisterRequest request) {
        try {
            Boolean isUserExist = userRepository.findDuplicates(request.getEmail(), Long.MAX_VALUE);

            if(!isUserExist) {
                var user = User.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .passwordSalt(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                        .isActive(true)
                        .build();
                userRepository.save(user);

                var jwt = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .token(jwt)
                        .build();
            } else {
                throw new DuplicateRecordException(messages.get("duplicate.record"));
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Authenticate The User From Provided AuthenticationRequest and generate token
     * @param request
     * @return Token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        try {
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RecordNotFoundException(messages.get("no.records.found")));

            var jwt = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwt)
                    .build();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Logout user
     * @return message
     */
    public String logout() {
        SecurityContextHolder.clearContext();
        return "Logout successful";
    }
}
