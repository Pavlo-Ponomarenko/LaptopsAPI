package org.laptops.converters;

import org.laptops.dtos.LaptopDetailsDto;
import org.laptops.dtos.LaptopInfoDto;
import org.laptops.dtos.LaptopSaveDto;
import org.laptops.entities.Laptop;

public interface LaptopDataConverter {

    Laptop saveDtoToEntity(LaptopSaveDto laptopSaveDto);
    LaptopDetailsDto entityToDetailsDto(Laptop entity);
    LaptopInfoDto entityToInfoDto(Laptop entity);
}
