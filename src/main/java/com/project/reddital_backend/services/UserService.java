package com.project.reddital_backend.services;

import com.project.reddital_backend.DTOs.mappers.UserMapper;
import com.project.reddital_backend.DTOs.models.UserDto;
import com.project.reddital_backend.exceptions.DuplicateEntityException;
import com.project.reddital_backend.exceptions.EntityNotFoundException;
import com.project.reddital_backend.models.User;
import com.project.reddital_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Service
public class UserService {

    //@Autowired
    private UserRepository userRepository;

    //@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //@Autowired
    private ModelMapper modelMapper;


    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserDto signup(UserDto userDto) {
        Optional<User> user = getOptional(userRepository.findByUsername(userDto.getUsername()));

        if (user.isEmpty()) {
            User newUser = new User()
                    .setUsername(userDto.getUsername())
                    .setEmail(userDto.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

            return mapDto(userRepository.save(newUser));
        }

        throw new DuplicateEntityException("Failed to signup, user already exists : " + user.get());
    }


    public UserDto findUserByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));

        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }

       throw new EntityNotFoundException(String.format("The user %s was not found!", username));
    }

    public UserDto findUserById(long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id));

        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }

        throw new EntityNotFoundException(String.format("The user with id %d was not found!", id));

    }

    public UserDto saveUser(User user) {

        if (user == null) {
            throw new NullPointerException("Received a null user!");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);

        return modelMapper.map(saved, UserDto.class);
    }


    public UserDto updateProfile(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(userDto.getUsername()));

        if (user.isPresent()) {
            User userModel = user.get();
            userModel
                    .setEmail(userDto.getEmail());

            return UserMapper.toUserDto(userRepository.save(userModel));
        }

        throw new EntityNotFoundException(String.format("The user %s was not found!", userDto.getUsername()));
    }

    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(userDto.getUsername()));

        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));

            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw new EntityNotFoundException(String.format("The user %s was not found!", userDto.getUsername()));
    }


    // ------------------------------------------------------- private methods

    private Optional<User> getOptional(User user) {
        return Optional.ofNullable(user);
    }

    private UserDto mapDto(User user) {
        return UserMapper.toUserDto(user);
    }
}
