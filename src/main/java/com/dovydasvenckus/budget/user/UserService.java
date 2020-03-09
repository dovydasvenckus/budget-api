package com.dovydasvenckus.budget.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Optional<UserDTO> getUserById(UUID userId) {
        return userRepository
                .getUserById(userId)
                .map(UserDTO::new);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUser(username);
    }

    Collection<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.save(user);

        return user;
    }
}
