package org.laptops.dtos;

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
    private String optionalPorts;
}
