package org.laptops.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LaptopSaveDto {

    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "producer is required")
    private String producer;
    @NotBlank(message = "processor is required")
    private String processor;
    @NotBlank(message = "memory is required")
    private String memory;
    @JsonProperty("optional_ports")
    @NotBlank(message = "ports are required")
    private String optionalPorts;
}
