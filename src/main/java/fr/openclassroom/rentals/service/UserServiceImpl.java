package fr.openclassroom.rentals.service;

import fr.openclassroom.rentals.dto.*;
import fr.openclassroom.rentals.exception.UserNotFoundException;
import fr.openclassroom.rentals.model.User;
import fr.openclassroom.rentals.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User userData) {
        User existing = userRepository.findById(id).orElseThrow();

        existing.setUsername(userData.getUsername());
        existing.setEmail(userData.getEmail());
        existing.setPassword(userData.getPassword());
        existing.setEnabled(userData.isEnabled());
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

    @Override
    public User findByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public List<UserListDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserListDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return UserDetailDto.from(user);
    }

    @Override
    public UserDetailDto createUser(CreateUserDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setEnabled(true);

        return UserDetailDto.from(userRepository.save(user));
    }

    @Override
    public UserDetailDto updateUser(Long id, UpdateUserDto dto) {
        User existing = userRepository.findById(id).orElseThrow();

        existing.setUsername(dto.username());
        existing.setEmail(dto.email());

        return UserDetailDto.from(userRepository.save(existing));
    }
}
