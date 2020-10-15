package com.hellsten.projekti.harjoitus;

import com.hellsten.projekti.harjoitus.domain.CategoryRepo;
import com.hellsten.projekti.harjoitus.domain.ItemRepo;
import com.hellsten.projekti.harjoitus.domain.User;
import com.hellsten.projekti.harjoitus.domain.UserRepo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class HarjoitusApplicationJpaTests {

    @Autowired
    private UserRepo users;

    @Autowired
    private ItemRepo items;

    @Autowired
    private CategoryRepo categories;

    @Test
    public void createNewUser() {
        User user = users.save(new User("Testi kaveri", "janne", "USER"));
        assertThat(users.findByUsername("Testi kaveri").getUsername()).isEqualTo(user.getUsername());
        
    }

    @Test
    public void findByUsernameShouldReturnUser() {
        User user = users.findByUsername("Janne");
        assertThat(user.getUsername()).isEqualTo("Janne");
        user.setUsername("Kaveri testi");
        users.save(user);
        assertThat(user.getUsername()).isNotEqualTo("Janne");
    }

    @Test
    public void deleteUser() {
        User user = users.findByUsername("Janne");
        users.delete(user);
        assertThat(users.findByUsername("Janne")).isNull();
    }

}
