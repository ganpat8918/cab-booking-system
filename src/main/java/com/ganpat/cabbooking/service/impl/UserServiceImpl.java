package com.ganpat.cabbooking.service.impl;

import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ganpat.cabbooking.dto.UserDto;
import com.ganpat.cabbooking.entity.User;
import com.ganpat.cabbooking.exception.DuplicateResourceException;
import com.ganpat.cabbooking.exception.ResourceNotFoundException;
import com.ganpat.cabbooking.repository.UserRepository;
import com.ganpat.cabbooking.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

this.userRepository = userRepository;
this.passwordEncoder = passwordEncoder;
}

    @Override
    public UserDto registerUser(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (userRepository.existsByPhone(userDto.getPhone())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setPassword(userDto.getPassword());
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return mapToDto(savedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return mapToDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        // Check duplicate email
        if (!user.getEmail().equals(userDto.getEmail())
                && userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // Check duplicate phone
        if (!user.getPhone().equals(userDto.getPhone())
                && userRepository.existsByPhone(userDto.getPhone())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User updatedUser = userRepository.save(user);

        return mapToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    private UserDto mapToDto(User user) {

        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .build();
    }
}