package org.laptops.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.laptops.converters.ProducerDataConverter;
import org.laptops.dtos.*;
import org.laptops.exceptions.BadRequestException;
import org.laptops.services.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/laptop")
public class LaptopController {

    @Autowired
    private LaptopService laptopService;
    @Autowired
    private ProducerDataConverter producerDataConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public RestResponseDto createLaptop(@Valid @RequestBody LaptopSaveDto dto) {
        Long result = laptopService.create(dto);
        return new RestResponseDto(result.toString());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public LaptopDetailsDto getLaptopById(@PathVariable Long id) {
        return laptopService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLaptop(@PathVariable Long id, @RequestBody LaptopSaveDto dto) {
        laptopService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLaptop(@PathVariable Long id) {
        laptopService.delete(id);
    }

    @PostMapping("/_list")
    @ResponseBody
    public LaptopsSearchResultDto getLaptops(@Valid @RequestBody LaptopSearchDto dto) {
        return laptopService.findAll(retrieveFilters(dto), dto.getPage(), dto.getSize());
    }

    @PostMapping(value = "/_report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void generateReport(HttpServletResponse response, @RequestBody LaptopFiltersDto dto) {
        laptopService.generateReport(response, retrieveFilters(dto));
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public JsonUploadResultDto uploadFromFile(@RequestParam("file") MultipartFile file) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new BadRequestException("invalid file");
        }
        return laptopService.uploadFromFile(bytes);
    }

    private Map<String,Object> retrieveFilters(LaptopFiltersDto dto) {
        Map<String,Object> filters = new HashMap<>();
        if (dto.getTitle() != null) {
            filters.put("title", dto.getTitle());
        }
        if (dto.getProducer() != null) {
            filters.put("producer", producerDataConverter.nameToEntity(dto.getProducer()));
        }
        if (dto.getProcessor() != null) {
            filters.put("processor", dto.getProcessor());
        }
        if (dto.getMemory() != null) {
            filters.put("memory", dto.getMemory());
        }
        return filters;
    }
}
