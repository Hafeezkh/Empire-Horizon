package com.EmpireHotel.Hotel.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EmpireHotel.Hotel.Exception.UserAlreadyExistsException;
import com.EmpireHotel.Hotel.Repository.RoleRepository;
import com.EmpireHotel.Hotel.Repository.UserRepository;
import com.EmpireHotel.Hotel.model.Role;
import com.EmpireHotel.Hotel.model.User;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceDiffblueTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#registerUser(User)}
     */
    @Test
    void testRegisterUser() {
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(true);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(user));
        verify(userRepository).existsByEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#registerUser(User)}
     */
    @Test
    void testRegisterUser2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);

        Role role = new Role();
        role.setId(1L);
        role.setName("Name");
        role.setUsers(new ArrayList<>());
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRoles(new ArrayList<>());
        User actualRegisterUserResult = userService.registerUser(user2);
        verify(roleRepository).findByName(Mockito.<String>any());
        verify(userRepository).existsByEmail(Mockito.<String>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals("secret", user2.getPassword());
        assertEquals(1, user2.getRoles().size());
        assertSame(user, actualRegisterUserResult);
    }

    /**
     * Method under test: {@link UserService#registerUser(User)}
     */
    @Test
    void testRegisterUser3() {
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);

        Role role = new Role();
        role.setId(1L);
        role.setName("Name");
        role.setUsers(new ArrayList<>());
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
        when(passwordEncoder.encode(Mockito.<CharSequence>any()))
                .thenThrow(new UserAlreadyExistsException("An error occurred"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(user));
        verify(roleRepository).findByName(Mockito.<String>any());
        verify(userRepository).existsByEmail(Mockito.<String>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link UserService#registerUser(User)}
     */
    @Test
    void testRegisterUser4() {
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);
        Optional<Role> emptyResult = Optional.empty();
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        assertThrows(RuntimeException.class, () -> userService.registerUser(user));
        verify(roleRepository).findByName(Mockito.<String>any());
        verify(userRepository).existsByEmail(Mockito.<String>any());
    }
}
