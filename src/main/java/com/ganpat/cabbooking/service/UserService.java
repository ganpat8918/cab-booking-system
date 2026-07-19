package com.ganpat.cabbooking.service;

import java.util.List;

import com.ganpat.cabbooking.dto.UserDto;

public interface UserService {

    UserDto registerUser(UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}