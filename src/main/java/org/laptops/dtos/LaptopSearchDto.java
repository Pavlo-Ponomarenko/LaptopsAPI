package org.laptops.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaptopSearchDto extends LaptopFiltersDto {
    @NotNull(message = "page number is required")
    private Integer page;
    @NotNull(message = "page size is required")
    private Integer size;
}
