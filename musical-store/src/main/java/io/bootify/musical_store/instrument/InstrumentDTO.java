package io.bootify.musical_store.instrument;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class InstrumentDTO {

    private Long id;

    @NotNull
    private InstrumentFamily family;

    @NotNull
    @Size(max = 255)
    private String type;

    @NotNull
    @Size(max = 255)
    private String model;

    @NotNull
    private BigDecimal price;

    private Long user;

}
