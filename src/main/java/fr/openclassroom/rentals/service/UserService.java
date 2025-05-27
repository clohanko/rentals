// File: src/main/java/fr/openclassroom/rentals/service/UserService.java
package fr.openclassroom.rentals.service;

import fr.openclassroom.rentals.dto.CreateUserDto;
import fr.openclassroom.rentals.dto.UpdateUserDto;
import fr.openclassroom.rentals.dto.UserDetailDto;
import fr.openclassroom.rentals.dto.UserListDto;
import fr.openclassroom.rentals.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    List<UserListDto> getAllUsers();
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    User findByUsernameOrThrow(String username);

    User create(User user);
    User update(Long id, User user);
    UserDetailDto getUserById(Long id);
    UserDetailDto createUser(CreateUserDto dto);
    UserDetailDto updateUser(Long id, UpdateUserDto dto);
    void deleteUser(Long id);

}
