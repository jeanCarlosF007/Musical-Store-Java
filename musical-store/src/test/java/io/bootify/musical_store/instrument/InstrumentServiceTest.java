package io.bootify.musical_store.instrument;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InstrumentServiceTest {

    @InjectMocks
    private InstrumentService instrumentService;

    @Mock
    private InstrumentRepository instrumentRepository;

    @Test
    public void testCreateInstrument() {

    }

}
