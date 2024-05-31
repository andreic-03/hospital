package org.hospital.repositories;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.hospital.config.RepositoryConfig;
import org.hospital.persistence.entity.UserEntity;
import org.hospital.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Import(RepositoryConfig.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    @Test
    @DatabaseSetup("/dataset/users.xml")
    void findById_success() {
        Optional<UserEntity> user = userRepository.findById(1L);

        assertAll(() -> {
            assertTrue(user.isPresent());
            assertEquals(1L, user.get().getId());
            assertEquals("login", user.get().getUsername());
            assertEquals("123123123", user.get().getPhoneNumber());
            assertEquals("asdfa@asd.com", user.get().getEmail());
            assertEquals("ACTIVE", user.get().getStatus().toString());
        });
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    void findByUsername_success() {
        Optional<UserEntity> user = userRepository.findByUsername("login");

        assertAll(() -> {
            assertTrue(user.isPresent());
            assertEquals(1L, user.get().getId());
            assertEquals("login", user.get().getUsername());
            assertEquals("123123123", user.get().getPhoneNumber());
            assertEquals("asdfa@asd.com", user.get().getEmail());
            assertEquals("ACTIVE", user.get().getStatus().toString());
        });
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    void findByEmail_success() {
        UserEntity user = userRepository.findByEmail("asdfa@asd.com");

        assertAll(() -> {
            assertEquals(1L, user.getId());
            assertEquals("login", user.getUsername());
            assertEquals("123123123", user.getPhoneNumber());
            assertEquals("asdfa@asd.com", user.getEmail());
            assertEquals("ACTIVE", user.getStatus().toString());
        });
    }
}
