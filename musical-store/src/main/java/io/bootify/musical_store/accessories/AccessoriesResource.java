package io.bootify.musical_store.accessories;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/accessoriess", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccessoriesResource {

    private final AccessoriesService accessoriesService;

    public AccessoriesResource(final AccessoriesService accessoriesService) {
        this.accessoriesService = accessoriesService;
    }

    @GetMapping
    public ResponseEntity<List<AccessoriesDTO>> getAllAccessoriess() {
        return ResponseEntity.ok(accessoriesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessoriesDTO> getAccessories(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(accessoriesService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createAccessories(
            @RequestBody @Valid final AccessoriesDTO accessoriesDTO) {
        final Long createdId = accessoriesService.create(accessoriesDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAccessories(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AccessoriesDTO accessoriesDTO) {
        accessoriesService.update(id, accessoriesDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessories(@PathVariable(name = "id") final Long id) {
        accessoriesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
