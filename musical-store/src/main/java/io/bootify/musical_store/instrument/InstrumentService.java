package io.bootify.musical_store.instrument;

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
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;
    private final UserRepository userRepository;

    public InstrumentService(final InstrumentRepository instrumentRepository,
            final UserRepository userRepository) {
        this.instrumentRepository = instrumentRepository;
        this.userRepository = userRepository;
    }

    public List<InstrumentDTO> findAll() {
        final List<Instrument> instruments = instrumentRepository.findAll(Sort.by("id"));
        return instruments.stream()
                .map(instrument -> mapToDTO(instrument, new InstrumentDTO()))
                .toList();
    }

    public InstrumentDTO get(final Long id) {
        return instrumentRepository.findById(id)
                .map(instrument -> mapToDTO(instrument, new InstrumentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final InstrumentDTO instrumentDTO) {
        validateInstrumentData(instrumentDTO);

        final Instrument instrument = new Instrument();
        mapToEntity(instrumentDTO, instrument);
        return instrumentRepository.save(instrument).getId();
    }

    private static void validateInstrumentData(InstrumentDTO instrumentDTO) {
        if (Objects.isNull(instrumentDTO.getFamily())) {
            throw new InstrumentValidationException("Instrument family not provided or inexistent!");
        } else if (StringUtils.isEmpty(instrumentDTO.getType()) || instrumentDTO.getType().length() > 255) {
            throw new InstrumentValidationException("Invalid instrument type or type not provided!");
        } else if (StringUtils.isEmpty(instrumentDTO.getModel()) || instrumentDTO.getModel().length() > 255) {
            throw new InstrumentValidationException("Model not provided or inexistent!");
        } else if (StringUtils.isEmpty(instrumentDTO.getPrice().toString())) {
            throw new InstrumentValidationException("Price not provided! You must enter a price!");
        } else if (Objects.isNull(instrumentDTO.getPrice()) || instrumentDTO.getPrice().compareTo(BigDecimal.ZERO) == -1) {
            throw new InstrumentValidationException("Price not provided! You must enter a price!");
        }
    }

    public void update(final Long id, final InstrumentDTO instrumentDTO) {
        final Instrument instrument = instrumentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        validateInstrumentData(instrumentDTO);
        mapToEntity(instrumentDTO, instrument);
        instrumentRepository.save(instrument);
    }

    public void delete(final Long id) {
        instrumentRepository.deleteById(id);
    }

    private InstrumentDTO mapToDTO(final Instrument instrument, final InstrumentDTO instrumentDTO) {
        instrumentDTO.setId(instrument.getId());
        instrumentDTO.setFamily(instrument.getFamily());
        instrumentDTO.setType(instrument.getType());
        instrumentDTO.setModel(instrument.getModel());
        instrumentDTO.setPrice(instrument.getPrice());
        instrumentDTO.setUser(instrument.getUser() == null ? null : instrument.getUser().getId());
        return instrumentDTO;
    }

    private Instrument mapToEntity(final InstrumentDTO instrumentDTO, final Instrument instrument) {
        instrument.setFamily(instrumentDTO.getFamily());
        instrument.setType(instrumentDTO.getType());
        instrument.setModel(instrumentDTO.getModel());
        instrument.setPrice(instrumentDTO.getPrice());
        final User user = instrumentDTO.getUser() == null ? null : userRepository.findById(instrumentDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        instrument.setUser(user);
        return instrument;
    }

}
