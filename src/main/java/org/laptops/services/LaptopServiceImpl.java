package org.laptops.services;

import org.laptops.converters.LaptopDataConverter;
import org.laptops.dtos.LaptopDetailsDto;
import org.laptops.dtos.LaptopInfoDto;
import org.laptops.dtos.LaptopSaveDto;
import org.laptops.dtos.LaptopsSearchResultDto;
import org.laptops.entities.Laptop;
import org.laptops.entities.Producer;
import org.laptops.exceptions.NotFoundException;
import org.laptops.repositories.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    @Override
    public void create(LaptopSaveDto laptopSaveDto) {
        Laptop entity = laptopDataConverter.saveDtoToEntity(laptopSaveDto);
        producerService.validateId(entity.getProducer().getName());
        laptopRepository.save(entity);
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

    private void validateLaptopId(Long id) {
        if (!laptopRepository.existsById(id)) {
            throw new NotFoundException("The item with id " + id + " was not found");
        }
    }
}
