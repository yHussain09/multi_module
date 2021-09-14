package com.example.security.domain.dao;

//import com.example.api.DemoApplication;
import com.example.security.domain.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


//@ContextConfiguration(classes= DemoApplication.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository underTest;

    @Test
    void findOneByUsername() {
        // given
        String username = "yasir";

        // when
        Optional<User> optionalUser = underTest.findOneByUsername(username);

        // then
        String expectedUsername = "yasir";
        assertThat(optionalUser.get().getUsername()).isEqualTo(expectedUsername);

    }
}