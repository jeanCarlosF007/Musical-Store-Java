package io.bootify.musical_store.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserResourceTest {

    @InjectMocks
    private UserResource userResource;
    @Mock
    private UserService userService;
    private MockMvc mockMvc;

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDTO> users = new ArrayList<>();
        users.add(userService.mapToDTO(UserServiceTest
                .getUser(1L, "Harry Potter", "37209490019", "Rua Vicente de Cúrsio, 375, Serraria, São José/SC", 150.0), new UserDTO()));
        Mockito.when(userService.findAll()).thenReturn(users);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String resp = result.getResponse().getContentAsString();
        Assertions.assertEquals("[{\"id\":1,\"name\":\"Harry Potter\"," +
                "\"documentNumber\":\"37209490019\", \"adress\":\"Rua Vicente de Cúrsio, 375, Serraria, São José/SC\"," +
                "\"credit\":150.0}]", resp);

    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }
}
