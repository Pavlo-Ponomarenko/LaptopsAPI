package org.laptops.services;

import org.laptops.dtos.LaptopDetailsDto;
import org.laptops.dtos.LaptopSaveDto;
import org.laptops.dtos.LaptopsSearchResultDto;

import java.util.Map;

public interface LaptopService {

    void create(LaptopSaveDto laptopSaveDto);
    void update(Long id, LaptopSaveDto laptopSaveDto);
    void delete(Long id);
    LaptopDetailsDto findById(Long id);
    LaptopsSearchResultDto findAll(Map<String,Object> filters, int page, int size);
}
