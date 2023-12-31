package io.bootify.musical_store.accessories;

import io.bootify.musical_store.user.User;
import io.bootify.musical_store.user.UserRepository;
import io.bootify.musical_store.util.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AccessoriesService {

    private final AccessoriesRepository accessoriesRepository;
    private final UserRepository userRepository;

    public AccessoriesService(final AccessoriesRepository accessoriesRepository,
            final UserRepository userRepository) {
        this.accessoriesRepository = accessoriesRepository;
        this.userRepository = userRepository;
    }

    public List<AccessoriesDTO> findAll() {
        final List<Accessories> accessorieses = accessoriesRepository.findAll(Sort.by("id"));
        return accessorieses.stream()
                .map(accessories -> mapToDTO(accessories, new AccessoriesDTO()))
                .toList();
    }

    public AccessoriesDTO get(final Long id) {
        return accessoriesRepository.findById(id)
                .map(accessories -> mapToDTO(accessories, new AccessoriesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AccessoriesDTO accessoriesDTO) {
        validateAccessoryData(accessoriesDTO);
        final Accessories accessories = new Accessories();
        mapToEntity(accessoriesDTO, accessories);
        return accessoriesRepository.save(accessories).getId();
    }

    private static void validateAccessoryData(AccessoriesDTO accessoriesDTO) {
        if (StringUtils.isEmpty(accessoriesDTO.getDepartment()) || accessoriesDTO.getDepartment().length() > 255) {
            throw new AccessoriesValidationException("Department not valid or inexistent!");
        } else if (StringUtils.isEmpty(accessoriesDTO.getName()) || accessoriesDTO.getName().length() > 255) {
            throw new AccessoriesValidationException("Accessory not valid or inexistent!");
        } else if (Objects.isNull(accessoriesDTO.getPrice()) || accessoriesDTO.getPrice().compareTo(BigDecimal.ZERO) == -1 ) {
            throw new AccessoriesValidationException("Price invalid or not provided!");
        }
    }

    public void update(final Long id, final AccessoriesDTO accessoriesDTO) {
        final Accessories accessories = accessoriesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        validateAccessoryData(accessoriesDTO);
        mapToEntity(accessoriesDTO, accessories);
        accessoriesRepository.save(accessories);
    }

    public void delete(final Long id) {
        accessoriesRepository.deleteById(id);
    }

    private AccessoriesDTO mapToDTO(final Accessories accessories,
            final AccessoriesDTO accessoriesDTO) {
        accessoriesDTO.setId(accessories.getId());
        accessoriesDTO.setDepartment(accessories.getDepartment());
        accessoriesDTO.setName(accessories.getName());
        accessoriesDTO.setPrice(accessories.getPrice());
        accessoriesDTO.setUser(accessories.getUser() == null ? null : accessories.getUser().getId());
        return accessoriesDTO;
    }

    private Accessories mapToEntity(final AccessoriesDTO accessoriesDTO,
            final Accessories accessories) {
        accessories.setDepartment(accessoriesDTO.getDepartment());
        accessories.setName(accessoriesDTO.getName());
        accessories.setPrice(accessoriesDTO.getPrice());
        final User user = accessoriesDTO.getUser() == null ? null : userRepository.findById(accessoriesDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        accessories.setUser(user);
        return accessories;
    }

}
