package io.bootify.musical_store.instrument;

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
@RequestMapping(value = "/api/instruments", produces = MediaType.APPLICATION_JSON_VALUE)
public class InstrumentResource {

    private final InstrumentService instrumentService;

    public InstrumentResource(final InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping
    public ResponseEntity<List<InstrumentDTO>> getAllInstruments() {
        return ResponseEntity.ok(instrumentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstrumentDTO> getInstrument(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(instrumentService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createInstrument(
            @RequestBody @Valid final InstrumentDTO instrumentDTO) {
        final Long createdId = instrumentService.create(instrumentDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateInstrument(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final InstrumentDTO instrumentDTO) {
        instrumentService.update(id, instrumentDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstrument(@PathVariable(name = "id") final Long id) {
        instrumentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
