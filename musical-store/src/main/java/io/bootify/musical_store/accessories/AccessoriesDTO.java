package io.bootify.musical_store.accessories;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class AccessoriesDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String department;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private BigDecimal price;

    private Long user;

}
