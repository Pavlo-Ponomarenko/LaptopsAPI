package org.laptops.services.json;

import org.laptops.dtos.LaptopSaveDto;

import java.util.List;

/**
 * Interface for parsing JSON data into a list of LaptopSaveDto objects.
 * @author Pavlo Ponomarenko
 */
public interface JsonParser {

    /**
     * Parses the provided byte array representing JSON data into a list of LaptopSaveDto objects.
     * @param bytes The byte array containing JSON data to be parsed.
     * @return A list of LaptopSaveDto objects parsed from the JSON data.
     */
    List<LaptopSaveDto> parseBytesToList(byte[] bytes);
}
