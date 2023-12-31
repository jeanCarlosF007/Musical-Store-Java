package io.bootify.musical_store.user;

import io.bootify.musical_store.util.DTOConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindAllUsers() {
        List<User> users = new ArrayList<>();

        users.add(getUser(1L, "Harry Potter", "37209490019", "Rua Vicente de Cúrsio, 375, Serraria, São José/SC", 150.0));
        users.add(getUser(2L, "Hermione Granger", "87716486071", "Av. Madre Benvenutta, Trindade, Florianópolis/SC", 0.0));

        Mockito.when(userRepository.findAll(Sort.by("id"))).thenReturn(users);
        List<UserDTO> usersReturn = userService.findAll();

        Assertions.assertEquals(2, usersReturn.size());
    }

    @Test
    public void testCreateUser() {
        User userDB = getUser(3L, "Ronald Weasley", "61760913065", "Rua Lauro Linhares, Trindade, Florianópolis/SC", 0.0);
        UserDTO userDTO = userService.mapToDTO(userDB, new UserDTO());


        Mockito.when(userRepository.save(Mockito.any()))
                .thenReturn(userDB);

        UserDTO user = userService.create(userDTO);
        Assertions.assertEquals("Ronald Weasley", user.getName());
        Assertions.assertEquals("61760913065", user.getDocumentNumber());
        Assertions.assertEquals("Rua Lauro Linhares, Trindade, Florianópolis/SC", user.getAdress());
        Assertions.assertEquals(0.0, user.getCredit());
    }

    @Test
    public void testUpdateUser() {
        User userDB = getUser(1L, "Albus Dumbleodore", "83664689097", "Sala Principal, Hogwarts", 1000.00);

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(userDB));
        Mockito.when(userRepository.save(Mockito.any()))
                .thenReturn(userDB);

        UserDTO userDTO = userService.mapToDTO(userDB, new UserDTO());
        userDTO.setAdress("Main Tower, Hogwarts");
        userDTO.setCredit(200.0);

        UserDTO user = userService.update(1L, userDTO);
        Assertions.assertEquals("Main Tower, Hogwarts", user.getAdress());
        Assertions.assertEquals(200.0, user.getCredit());

    }

    public static User getUser (Long id, String name, String documentNumber, String adress, Double credit) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setDocumentNumber(documentNumber);
        user.setAdress(adress);
        user.setCredit(credit);
        return user;
    }
}
