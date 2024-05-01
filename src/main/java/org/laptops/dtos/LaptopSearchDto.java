package org.laptops.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaptopSearchDto {

    private String title;
    private String producer;
    private String processor;
    private String memory;
    private String optionalPorts;
    @NotBlank(message = "page number is required")
    private Integer page;
    @NotBlank(message = "page size is required")
    private Integer size;
}
