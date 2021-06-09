package com.example.microuser.domains.user;

import com.example.microuser.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

//Unit test
public class UserServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);


    //Mock data
    static User user1 = User.builder()
            .id(UUID.randomUUID())
            .name("name 1")
            .surname("surname 1")
            .build();

    static User user2 = User.builder()
            .id(UUID.randomUUID())
            .name("name 2")
            .surname("surname 2")
            .build();

    static User user3 = User.builder()
            .id(UUID.randomUUID())
            .name("name 3")
            .surname("surname 3")
            .build();

    @Test
    public void getAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));

        Assertions.assertEquals(3, userService.getAllUsers().size());
    }

    @ParameterizedTest
    @MethodSource("getUserByIdArguments")
    public void getUserById(User mockUser, UserDTO expectedUser) {
        Mockito.when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));

        Assertions.assertEquals(expectedUser, userService.getUserById(mockUser.getId()));
    }

    @ParameterizedTest
    @MethodSource("notGetUserByIdArguments")
    public void notGetUserById(User mockUser, UserDTO expectedUser) {
        Mockito.when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));

        Assertions.assertNotEquals(expectedUser, userService.getUserById(mockUser.getId()));
    }

    @Test
    public void getUserByNull() {
        UUID id = UUID.randomUUID();
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
    }

    // Parameterized test input values
    static private Stream<Arguments> getUserByIdArguments() {
        return Stream.of(
                Arguments.of(user1, new UserDTO(user1.getId(), user1.getName(), user1.getSurname())),
                Arguments.of(user2, new UserDTO(user2.getId(), user2.getName(), user2.getSurname())),
                Arguments.of(user3, new UserDTO(user3.getId(), user3.getName(), user3.getSurname()))
        );
    }

    static private Stream<Arguments> notGetUserByIdArguments() {
        return Stream.of(
                Arguments.of(user3, new UserDTO(user1.getId(), user1.getName(), user1.getSurname())),
                Arguments.of(user1, new UserDTO(user2.getId(), user2.getName(), user2.getSurname())),
                Arguments.of(user2, new UserDTO(user3.getId(), user3.getName(), user3.getSurname()))
        );
    }
}
