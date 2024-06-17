package com.springboot.sample.application.auth;

import com.springboot.sample.application.auth.token.Token;
import com.springboot.sample.application.auth.token.TokenRepository;
import com.springboot.sample.application.user.User;
import com.springboot.sample.application.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(AuthRequest request) {
        String username;
        if(request.getUsername() != null && request.getEmail() != null){
            return new AuthResponse(null, "Please choose to login using either username or email!");
        }
        else if(request.getUsername() != null){
            username = request.getUsername();
        }
        else if(request.getEmail() != null){
            username = repository.findByEmailAndDataStatusNot(request.getEmail(), 'D').orElseThrow().getUsername();
        }
        else{
            return new AuthResponse(null, "Please provide a username or email and password!");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                request.getPassword()
        ));
        User user = repository.findByUsernameAndDataStatusNot(username, 'D').orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthResponse(jwt, "User login was successful");

    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> t.setLoggedOut(true));

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}