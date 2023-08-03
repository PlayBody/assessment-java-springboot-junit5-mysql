package com.assessment.testcontainers;

import com.assessment.purchase.model.customer.*;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {ModelRepositoryTests.Initializer.class })
public class ModelRepositoryTests {

    @Container
    public static MySQLContainer mySQLContainer = new MySQLContainer("mysql:5.7")
            .withDatabaseName("testspring")
            .withUsername("root")
            .withPassword("");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" +mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private CustomerRepository userRepository;

    @Test
    public void testContextLoads() {
        assertNotNull(userRepository);
    }

    @Test
    public void testCrud() {
        Customer user = new Customer();
        String initial = "test";
        user.setName(initial);
        Customer created = userRepository.save(user);
        assertNotNull(created.getId());
        assertEquals(initial, created.getName());
        String next = "updated";
        created.setName(next);
        userRepository.save(created);
        Optional<Customer> updatedOpt = userRepository.findById(created.getId());
        assertTrue(updatedOpt.isPresent());
        Customer updated = updatedOpt.get();
        assertEquals(created.getId(), updated.getId());
        assertEquals(next, updated.getName());
        List<Customer> allUsers = Lists.newArrayList(userRepository.findAll());
        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
        userRepository.deleteById(updated.getId());
        allUsers = Lists.newArrayList(userRepository.findAll());
        assertNotNull(allUsers);
        assertEquals(0, allUsers.size());
    }

}
