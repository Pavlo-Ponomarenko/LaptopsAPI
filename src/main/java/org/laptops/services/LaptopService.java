package org.laptops.services;

import jakarta.servlet.http.HttpServletResponse;
import org.laptops.dtos.JsonUploadResultDto;
import org.laptops.dtos.LaptopDetailsDto;
import org.laptops.dtos.LaptopSaveDto;
import org.laptops.dtos.LaptopsSearchResultDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface LaptopService {

    Long create(LaptopSaveDto laptopSaveDto);
    void update(Long id, LaptopSaveDto laptopSaveDto);
    void delete(Long id);
    LaptopDetailsDto findById(Long id);
    LaptopsSearchResultDto findAll(Map<String,Object> filters, int page, int size);
    void generateReport(HttpServletResponse httpServletResponse, Map<String,Object> filters);
    JsonUploadResultDto uploadFromFile(byte[] file);
}
