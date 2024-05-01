package org.laptops.converters;

import org.laptops.dtos.ProducerInfoDto;
import org.laptops.dtos.ProducerSaveDto;
import org.laptops.entities.Producer;

public interface ProducerDataConverter {

    Producer saveDtoToEntity(ProducerSaveDto producerSaveDto);
    ProducerInfoDto entityToInfoDto(Producer entity);
}
