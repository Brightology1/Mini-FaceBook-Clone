package com.example.week7task.service.serviceImplementation;

import com.example.week7task.model.User;
import com.example.week7task.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

//import static jdk.jfr.internal.jfc.model.Constraint.any;
import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        //user.setId();
        user.setFirstname("");
        user.setLastname("");
        user.setPassword("");
        user.setEmail("ugo@gmail.com");
        user.setDob("");
        user.setGender("");
    }

    @Test
    void shouldCreateUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.createUser(user);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldFindUserByEmailTest() {
        when(userRepository.findPersonByEmail(anyString())).thenReturn(user);
        User foundUser = userService.getUser("ugo@gmail.com", "2345");
        assertNotNull(foundUser);
        assertEquals("ugo@gmail.com", foundUser.getEmail());
        assertEquals("2345", foundUser.getPassword());
       // verify(userRepository, time(1)).findUserByEmail(anyString());
    }
//    @Test
//    void shouldThrowUserServiceException(){
//        when(userRepository.findUserByEmail(anyString())).thenReturn(Optioanal.empty());
//        assertThrows(UserExcetion.class
//        () ->{
//            userService.findUserByEmail("ugo@gmail.com");
//        }
     //   );
 //   }
}