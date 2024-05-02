package org.laptops.converters;

import org.laptops.dtos.ProducerInfoDto;
import org.laptops.dtos.ProducerSaveDto;
import org.laptops.entities.Producer;

/**
 * An interface to define methods for converting data between different representations of producer information.
 * @author Pavlo Ponomarenko
 */
public interface ProducerDataConverter {

    /**
     * Converts a data transfer object (DTO) representing a producer to an entity object.
     *
     * @param producerSaveDto The DTO containing the information to be converted.
     * @return A Producer entity object populated with the data from the DTO.
     */
    Producer saveDtoToEntity(ProducerSaveDto producerSaveDto);

    /**
     * Converts a producer name to a corresponding entity object.
     *
     * @param name The name of the producer.
     * @return A Producer entity object associated with the given name.
     */
    Producer nameToEntity(String name);

    /**
     * Converts a producer entity object to a DTO containing basic information about the producer.
     *
     * @param entity The Producer entity object to be converted.
     * @return A ProducerInfoDto object containing basic information extracted from the entity.
     */
    ProducerInfoDto entityToInfoDto(Producer entity);
}
