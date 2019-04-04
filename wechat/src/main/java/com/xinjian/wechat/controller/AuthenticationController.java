package com.freshman.controller;

import com.freshman.domain.User;
import com.freshman.util.JwtTokenUtil;
import com.freshman.vo.AuthenticationRequest;
import com.freshman.exception.ErrorMessage;
import com.freshman.service.UserDetailsServiceImpl;
import com.freshman.vo.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${api.base-path}/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping(value = "login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generate(userDetails);

        // Return the token
        return new AuthenticationResponse(token,userDetails.getUsername());
    }

    @PostMapping("checkUserByUsername")
    User checkUserByUsername(@RequestBody AuthenticationRequest request) {
        User user = userDetailsService.checkUserByUsername(request.getUsername());
        return user;
    }

    //优先级更高
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException exception) {
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(new ErrorMessage("403", "authentication exception"));
    }


}
