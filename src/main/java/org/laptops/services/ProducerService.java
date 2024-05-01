package org.laptops.services;

import org.laptops.dtos.ProducerInfoDto;
import org.laptops.dtos.ProducerSaveDto;

public interface ProducerService {

    void create(ProducerSaveDto producerSaveDto);
    void update(String id, ProducerSaveDto producerSaveDto);
    void delete(String id);
    ProducerInfoDto findById(String id);
    void validateId(String id);
}
