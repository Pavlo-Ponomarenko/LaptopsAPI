package org.laptops.services;

import org.laptops.dtos.ProducerInfoDto;
import org.laptops.dtos.ProducerSaveDto;

import java.util.List;

public interface ProducerService {

    void create(ProducerSaveDto producerSaveDto);
    void update(String id, ProducerSaveDto producerSaveDto);
    void delete(String id);
    List<ProducerInfoDto> getALL();
    void validateId(String id);
}
