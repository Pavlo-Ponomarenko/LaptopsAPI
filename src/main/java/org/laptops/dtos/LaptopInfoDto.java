package org.laptops.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaptopInfoDto {

    private String title;
    private String producer;
    private String processor;
    private String memory;
    private String optionalPorts;
}
