package com.github.silexrr.yandex_market_api.auth.repository;

import com.github.silexrr.yandex_market_api.auth.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;


    @Before
    public void setUp() throws Exception {
        User user1 = new User("a-test", "Alice");
        User user2 = new User("b-test", "Boris");

        assertNotNull(user1.get_id());
        assertNotNull(user2.get_id());

        this.userRepository.save(user1);
        this.userRepository.save(user2);

        assertNotNull(user1.get_id());
        assertNotNull(user2.get_id());
    }

    @Test
    public void testFetchData() {
        User userA = userRepository.findByName("Alice");
        assertNotNull(userA);

        assertEquals(userA.getLogin(), "a-test");

        Iterable<User> users = userRepository.findAll();
        int count = 0;

        for (User u : users) {
            count++;
        }

        assertEquals(count, 2);
    }

    @Test
    public void testUpdateData() {
        User userB = userRepository.findByLogin("b-test");
        assertNotNull(userB);
        assertEquals("Boris", userB.getName());

        userB.setName("Bob");

        userRepository.save(userB);

        User userC = userRepository.findByName("Bob");
        assertNotNull(userC);
        assertEquals("b-test", userB.getLogin());
    }

    @After
    public void tearDown() throws Exception {
        this.userRepository.deleteAll();
    }

}
