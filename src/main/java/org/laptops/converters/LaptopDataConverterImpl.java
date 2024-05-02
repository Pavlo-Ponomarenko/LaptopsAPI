package org.laptops.converters;

import org.laptops.dtos.LaptopDetailsDto;
import org.laptops.dtos.LaptopInfoDto;
import org.laptops.dtos.LaptopSaveDto;
import org.laptops.dtos.ProducerInfoDto;
import org.laptops.entities.Laptop;
import org.laptops.entities.Producer;
import org.springframework.stereotype.Service;

@Service
public class LaptopDataConverterImpl implements LaptopDataConverter {

    @Override
    public Laptop saveDtoToEntity(LaptopSaveDto laptopSaveDto) {
        Laptop entity = new Laptop();
        entity.setTitle(laptopSaveDto.getTitle());
        entity.setMemory(laptopSaveDto.getMemory());
        entity.setProcessor(laptopSaveDto.getProcessor());
        entity.setOptionalPorts(laptopSaveDto.getOptionalPorts());
        Producer producer = new Producer(laptopSaveDto.getProducer());
        entity.setProducer(producer);
        return entity;
    }

    @Override
    public LaptopDetailsDto entityToDetailsDto(Laptop entity) {
        LaptopDetailsDto detailsDto = new LaptopDetailsDto();
        detailsDto.setId(entity.getId());
        detailsDto.setTitle(entity.getTitle());
        detailsDto.setProducer(new ProducerInfoDto(entity.getProducer().getName()));
        detailsDto.setProcessor(entity.getProcessor());
        detailsDto.setMemory(entity.getMemory());
        detailsDto.setOptionalPorts(entity.getOptionalPorts());
        return detailsDto;
    }

    @Override
    public LaptopInfoDto entityToInfoDto(Laptop entity) {
        LaptopInfoDto infoDto = new LaptopInfoDto();
        infoDto.setTitle(entity.getTitle());
        infoDto.setProducer(entity.getProducer().getName());
        infoDto.setProcessor(entity.getProcessor());
        infoDto.setMemory(entity.getMemory());
        return infoDto;
    }
}
