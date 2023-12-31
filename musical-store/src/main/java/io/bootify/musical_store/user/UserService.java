package io.bootify.musical_store.user;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import io.bootify.musical_store.util.NotFoundException;
import java.util.List;

import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UserDTO create(final UserDTO userDTO) throws UserValidationException{
        validateUserData(userDTO);
        final User user = new User();
        mapToEntity(userDTO, user);
        return mapToDTO(userRepository.save(user), userDTO);
    }

    private static void validateUserData(UserDTO userDTO) {
        if(StringUtils.isEmpty(userDTO.getName()) || userDTO.getName().length() > 255) {
            throw new UserValidationException("Name not provided or invalid name!");
        } else if (StringUtils.isEmpty(userDTO.getAdress()) || userDTO.getAdress().length() > 255) {
            throw new UserValidationException("Adress not provided or invalid adress!");
        }
        List<ValidationMessage> messages =
                new CPFValidator().invalidMessagesFor(userDTO.getDocumentNumber());
        if (!messages.isEmpty()) {
            throw new UserValidationException("Document number related errors: " + messages);
        }
    }

    public UserDTO update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        validateUserData(userDTO);
        mapToEntity(userDTO, user);
        return mapToDTO(userRepository.save(user), userDTO);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setDocumentNumber(user.getDocumentNumber());
        userDTO.setAdress(user.getAdress());
        userDTO.setCredit(user.getCredit());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setDocumentNumber(userDTO.getDocumentNumber());
        user.setAdress(userDTO.getAdress());
        user.setCredit(userDTO.getCredit());
        return user;
    }

    public boolean documentNumberExists(final String documentNumber) {
        return userRepository.existsByDocumentNumberIgnoreCase(documentNumber);
    }

}
