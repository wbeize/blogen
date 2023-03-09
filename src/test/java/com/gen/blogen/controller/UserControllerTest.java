package com.gen.blogen.controller;

import com.gen.blogen.model.User;
import com.gen.blogen.repository.UserRepository;
import com.gen.blogen.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void start() {

        userRepository.deleteAll();

        userService.registerUser(new User(0L,
                "Root", "root@root.com", "rootroot", " "));
    }

    @Test
    @DisplayName("Cadastrar um usuário")
    public void registerUser() {

        HttpEntity<User> corpoRequisicao = new HttpEntity<User>(new User(
                0L, "Girassol Sol", "girassol@email.com.br", "123456", ""));

        ResponseEntity<User> corpoResposta = testRestTemplate.exchange(
                "/users/register", HttpMethod.POST, corpoRequisicao, User.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getName(), corpoResposta.getBody().getName());
        assertEquals(corpoRequisicao.getBody().getUser(), corpoResposta.getBody().getUser());
    }

    private void assertEquals(String name, String name1) {
    }

    private void assertEquals(HttpStatus created, HttpStatus statusCode) {
    }

    @Test
    @DisplayName("Não permite duplicação de usuário")
    public void noDuplicateUser() {

        userService.registerUser(new User(
                0L, "Maria Joana", "mj@email.com.br", "123456", ""));

        HttpEntity<User> corpoRequisicao = new HttpEntity<User>(new User(0L,
                "Maria Joana", "mj@email.com.br", "123456", ""));

        ResponseEntity<User> corpoReposta = testRestTemplate.exchange(
                "/users/register", HttpMethod.POST, corpoRequisicao, User.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoReposta.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar um usuário")
    public void updateUser() {

        Optional<User> registerUser = userService.registerUser(new User(
                0L, "João Feijão", "feijao@email.com.br", "123456", ""));

        User userUpdate = new User(registerUser.get().getId(),
                "João Feijão", "feijao@email.com.br", "123456", "");

        HttpEntity<User> corpoRequisicao = new HttpEntity<User>(userUpdate);

        ResponseEntity<User> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/users/register", HttpMethod.PUT,
                        corpoRequisicao, User.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getName(), corpoResposta.getBody().getName());
        assertEquals(corpoRequisicao.getBody().getUser(), corpoResposta.getBody().getUser());
    }

    @Test
    @DisplayName("Listar todos os usuários")
    public void viewAllUsers() {

        userService.registerUser(new User(0L, "Arruda Veia", "veia@email.com.br", "123456", ""));

        userService.registerUser(new User(0L, "Maracuja Zin", "maracuja@email.com.br", "123456", ""));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/users/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }
}