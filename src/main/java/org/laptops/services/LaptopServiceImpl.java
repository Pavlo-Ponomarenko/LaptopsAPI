package org.laptops.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.laptops.converters.LaptopDataConverter;
import org.laptops.dtos.*;
import org.laptops.entities.Laptop;
import org.laptops.entities.Producer;
import org.laptops.exceptions.BadRequestException;
import org.laptops.exceptions.CSVFileGenerationFailedException;
import org.laptops.exceptions.NotFoundException;
import org.laptops.repositories.LaptopRepository;
import org.laptops.services.csv.CSVGenerator;
import org.laptops.services.json.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LaptopServiceImpl implements LaptopService {

    @Autowired
    private LaptopRepository laptopRepository;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private LaptopDataConverter laptopDataConverter;
    @Autowired
    private CSVGenerator<LaptopInfoDto> csvGenerator;
    @Autowired
    private JsonParser jsonParser;

    @Override
    public Long create(LaptopSaveDto laptopSaveDto) {
        Laptop entity = laptopDataConverter.saveDtoToEntity(laptopSaveDto);
        producerService.validateId(entity.getProducer().getName());
        Laptop savedEntity = laptopRepository.save(entity);
        return savedEntity.getId();
    }

    @Override
    public void update(Long id, LaptopSaveDto laptopSaveDto) {
        validateLaptopId(id);
        Laptop oldLaptop = laptopRepository.findById(id).get();
        String producerName = laptopSaveDto.getProducer();
        producerService.validateId(producerName);
        oldLaptop.setTitle(laptopSaveDto.getTitle());
        oldLaptop.setProducer(new Producer(producerName));
        oldLaptop.setMemory(laptopSaveDto.getMemory());
        oldLaptop.setProcessor(laptopSaveDto.getProcessor());
        oldLaptop.setOptionalPorts(laptopSaveDto.getOptionalPorts());
        laptopRepository.save(oldLaptop);
    }

    @Override
    public void delete(Long id) {
        validateLaptopId(id);
        laptopRepository.deleteById(id);
    }

    @Override
    public LaptopDetailsDto findById(Long id) {
        validateLaptopId(id);
        Optional<Laptop> result = laptopRepository.findById(id);
        return laptopDataConverter.entityToDetailsDto(result.get());
    }

    @Override
    public LaptopsSearchResultDto findAll(Map<String,Object> filters, int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        Page<Laptop> result = laptopRepository.findAll(new LaptopSpecification(filters), request);
        List<LaptopInfoDto> dtoList = result.toList().stream().map(laptopDataConverter::entityToInfoDto).toList();
        return new LaptopsSearchResultDto(dtoList, result.getTotalPages());
    }

    @Override
    public void generateReport(HttpServletResponse response, Map<String,Object> filters) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=laptops.csv");
        List<Laptop> result = laptopRepository.findAll(new LaptopSpecification(filters));
        List<LaptopInfoDto> dtoList = result.stream().map(laptopDataConverter::entityToInfoDto).toList();
        byte[] generatedFile = csvGenerator.generateCSV(dtoList);
        try {
            response.getOutputStream().write(generatedFile);
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new CSVFileGenerationFailedException();
        }
    }

    @Override
    public JsonUploadResultDto uploadFromFile(byte[] file) {
        List<LaptopSaveDto> dtoList = jsonParser.parseBytesToList(file);
        int successful = 0;
        int unsuccessful = 0;
        for (LaptopSaveDto dto : dtoList) {
            try {
                create(dto);
            } catch(RuntimeException e) {
                unsuccessful += 1;
                continue;
            }
            successful += 1;
        }
        return new JsonUploadResultDto(successful, unsuccessful);
    }

    private void validateLaptopId(Long id) {
        if (!laptopRepository.existsById(id)) {
            throw new NotFoundException("The item with id " + id + " was not found");
        }
    }
}
