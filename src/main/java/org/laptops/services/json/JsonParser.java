package org.laptops.services.json;

import org.laptops.dtos.LaptopSaveDto;

import java.util.List;

public interface JsonParser {

    List<LaptopSaveDto> parseBytesToList(byte[] bytes);
}
