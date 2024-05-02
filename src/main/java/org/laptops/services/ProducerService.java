package org.laptops.services;

import org.laptops.dtos.ProducerInfoDto;
import org.laptops.dtos.ProducerSaveDto;

import java.util.List;

/**
 * Service interface for managing producer-related operations.
 * @author Pavlo Ponomarenko
 */
public interface ProducerService {

    /**
     * Creates a new producer entity based on the provided data.
     * @param producerSaveDto The DTO containing the information for creating the producer.
     */
    void create(ProducerSaveDto producerSaveDto);

    /**
     * Updates an existing producer entity with the provided data.
     * @param id               The ID of the producer to be updated.
     * @param producerSaveDto The DTO containing the updated information for the producer.
     */
    void update(String id, ProducerSaveDto producerSaveDto);

    /**
     * Deletes a producer entity by its ID.
     * @param id The ID of the producer to be deleted.
     */
    void delete(String id);

    /**
     * Retrieves basic information about all producers.
     * @return A list of DTOs containing basic information about all producers.
     */
    List<ProducerInfoDto> getALL();

    /**
     * Validates the format of the provided producer ID.
     * @param id The ID of the producer to be validated.
     */
    void validateId(String id);
}
