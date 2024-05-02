package org.laptops.converters;

import org.laptops.dtos.ProducerInfoDto;
import org.laptops.dtos.ProducerSaveDto;
import org.laptops.entities.Producer;
import org.springframework.stereotype.Service;

@Service
public class ProducerDataConverterImpl implements ProducerDataConverter {

    @Override
    public Producer saveDtoToEntity(ProducerSaveDto producerSaveDto) {
        return new Producer(producerSaveDto.getName());
    }

    @Override
    public Producer nameToEntity(String name) {
        return new Producer(name);
    }

    @Override
    public ProducerInfoDto entityToInfoDto(Producer entity) {
        return new ProducerInfoDto(entity.getName());
    }
}
