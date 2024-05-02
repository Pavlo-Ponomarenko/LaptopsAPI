package org.laptops.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaptopDetailsDto {

    private Long id;
    private String title;
    private ProducerInfoDto producer;
    private String processor;
    private String memory;
    @JsonProperty("optional_ports")
    private String optionalPorts;
}
