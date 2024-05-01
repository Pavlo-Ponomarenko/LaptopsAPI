package org.laptops.services;

import org.laptops.converters.ProducerDataConverter;
import org.laptops.dtos.ProducerInfoDto;
import org.laptops.dtos.ProducerSaveDto;
import org.laptops.entities.Producer;
import org.laptops.exceptions.BadRequestException;
import org.laptops.repositories.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ProducerInfoDto findById(String id) {
        validateId(id);
        Producer entity = producerRepository.findById(id).get();
        return producerDataConverter.entityToInfoDto(entity);
    }

    @Override
    public void validateId(String id) {
        if (!producerRepository.existsById(id)) {
            throw new BadRequestException("There is no producer with such id: " + id);
        }
    }
}
