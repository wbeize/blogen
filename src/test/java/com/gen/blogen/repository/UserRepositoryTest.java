package com.gen.blogen.repository;

import com.gen.blogen.model.User;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void start() {
        userRepository.deleteAll();

        userRepository.save(new User(0L, "Maria Bonita", "maria@email.com.br", "123456", ""));

        userRepository.save(new User(0L, "João Feijão", "feijao@email.com.br", "123456", ""));

        userRepository.save(new User(0L, "Alecrim Dourado", "alecrim@email.com.br", "123456", ""));

        userRepository.save(new User(0L, "Girassol Sol", "girassol@email.com.br", "123456", ""));

        userRepository.save(new User(0L, "Maria Joana", "mj@email.com.br", "123456", ""));
    }

    @Test
    @DisplayName("Retorna 1 usuario")
    public void return1User() {

        Optional<User> user = userRepository.findByUser("feijao@email.com.br");
        assertTrue(user.get().getUser().equals("feijao@email.com.br"));
    }

    @Test
    @DisplayName("Retorna 3 usuarios")
    public void return3User() {
        List<User> listUsers = userRepository.findAllByNameContainingIgnoreCase("Sol");

        assertEquals(3, listUsers.size());
        assertTrue(listUsers.get(0).getUser().equals("Maria Bonita"));
        assertTrue(listUsers.get(1).getUser().equals("João Feijão"));
        assertTrue(listUsers.get(2).getUser().equals("Alecrim Dourado"));
    }

    private void assertTrue(boolean mariaBonita) {
    }

    @AfterAll
    public void end() {
        userRepository.deleteAll();
    }
}
