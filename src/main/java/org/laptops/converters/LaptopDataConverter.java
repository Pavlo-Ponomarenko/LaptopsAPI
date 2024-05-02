package org.laptops.converters;

import org.laptops.dtos.LaptopDetailsDto;
import org.laptops.dtos.LaptopInfoDto;
import org.laptops.dtos.LaptopSaveDto;
import org.laptops.entities.Laptop;

/**
 * An interface to define methods for converting data between different representations of laptop information.
 * @author Pavlo Ponomarenko
 */
public interface LaptopDataConverter {

    /**
     * Converts a data transfer object (DTO) representing a laptop to an entity object.
     *
     * @param laptopSaveDto The DTO containing the information to be converted.
     * @return A Laptop entity object populated with the data from the DTO.
     */
    Laptop saveDtoToEntity(LaptopSaveDto laptopSaveDto);

    /**
     * Converts a laptop entity object to a DTO containing detailed information about the laptop.
     *
     * @param entity The Laptop entity object to be converted.
     * @return A LaptopDetailsDto object containing detailed information extracted from the entity.
     */
    LaptopDetailsDto entityToDetailsDto(Laptop entity);
    /**
     * Converts a laptop entity object to a DTO containing basic information about the laptop.
     *
     * @param entity The Laptop entity object to be converted.
     * @return A LaptopInfoDto object containing basic information extracted from the entity.
     */
    LaptopInfoDto entityToInfoDto(Laptop entity);
}
