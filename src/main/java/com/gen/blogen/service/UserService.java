package com.gen.blogen.service;

import com.gen.blogen.model.User;
import com.gen.blogen.model.UserLogin;
import com.gen.blogen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> registerUser(User user) {
        if (userRepository.findByUser(user.getUser()).isPresent())
            return Optional.empty();

        user.setPassword(encryptPassword(user.getPassword()));

        return Optional.of(userRepository.save(user));
    }

    public Optional<User> updateUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            Optional<User> findUser = userRepository.findByUser(user.getUser());
            if ((findUser.isPresent()) && (findUser.get().getId() != user.getId()))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"usuário já existe!", null);

            user.setPassword(encryptPassword(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));
        }

        return Optional.empty();
    }

    public Optional<UserLogin> authenticateUser(Optional<UserLogin> userLogin) {
        Optional<User> user = userRepository.findByUser(userLogin.get().getUser());

        if (user.isPresent()) {
            if (comparePasswords(userLogin.get().getPassword(), user.get().getPassword())) {

                userLogin.get().setId(user.get().getId());
                userLogin.get().setName(user.get().getName());
                userLogin.get().setIcon(user.get().getIcon());
                userLogin.get().setToken(generateBasicToken(userLogin.get().getUser(), userLogin.get().getPassword());
                userLogin.get().setPassword(user.get().getPassword());

                return userLogin;
            }
        }

        return Optional.empty();
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);
    }

    private boolean comparePasswords(String typePassword, String bankPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(typePassword, bankPassword);
    }

    private String generateBasicToken(String user, String password) {

        String token = user + ":" + password;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));

        return "Basic " + new String(tokenBase64);
    }

}
