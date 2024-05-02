package org.laptops.services;

import org.laptops.converters.ProducerDataConverter;
import org.laptops.dtos.ProducerInfoDto;
import org.laptops.dtos.ProducerSaveDto;
import org.laptops.entities.Producer;
import org.laptops.exceptions.BadRequestException;
import org.laptops.exceptions.NotFoundException;
import org.laptops.repositories.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the ProducerService interface for managing producer-related operations.
 * @author Pavlo Ponomarenko
 */
@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private ProducerDataConverter producerDataConverter;

    @Override
    public void create(ProducerSaveDto producerSaveDto) {
        String name = producerSaveDto.getName();
        if (producerRepository.existsById(name)) {
            throw new BadRequestException("Producer with name " + name + " already exists");
        }
        producerRepository.save(producerDataConverter.saveDtoToEntity(producerSaveDto));
    }

    @Override
    public void update(String id, ProducerSaveDto producerSaveDto) {
        delete(id);
        producerRepository.save(producerDataConverter.saveDtoToEntity(producerSaveDto));
    }

    @Override
    public void delete(String id) {
        validateId(id);
        producerRepository.deleteById(id);
    }

    @Override
    public List<ProducerInfoDto> getALL() {
        List<Producer> entities = producerRepository.findAll();
        return entities.stream().map(producerDataConverter::entityToInfoDto).toList();
    }

    @Override
    public void validateId(String id) {
        if (!producerRepository.existsById(id)) {
            throw new NotFoundException("There is no producer with such id: " + id);
        }
    }
}
