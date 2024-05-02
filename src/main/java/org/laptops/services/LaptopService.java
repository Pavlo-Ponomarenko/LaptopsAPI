package org.laptops.services;

import jakarta.servlet.http.HttpServletResponse;
import org.laptops.dtos.JsonUploadResultDto;
import org.laptops.dtos.LaptopDetailsDto;
import org.laptops.dtos.LaptopSaveDto;
import org.laptops.dtos.LaptopsSearchResultDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Service interface for managing laptop-related operations.
 * @author Pavlo Ponomarenko
 */
public interface LaptopService {

    /**
     * Creates a new laptop entity based on the provided data.
     * @param laptopSaveDto The DTO containing the information for creating the laptop.
     * @return The ID of the newly created laptop entity.
     */
    Long create(LaptopSaveDto laptopSaveDto);

    /**
     * Updates an existing laptop entity with the provided data.
     * @param id             The ID of the laptop to be updated.
     * @param laptopSaveDto The DTO containing the updated information for the laptop.
     */
    void update(Long id, LaptopSaveDto laptopSaveDto);

    /**
     * Deletes a laptop entity by its ID.
     * @param id The ID of the laptop to be deleted.
     */
    void delete(Long id);

    /**
     * Retrieves detailed information about a laptop by its ID.
     * @param id The ID of the laptop to retrieve details for.
     * @return A DTO containing detailed information about the laptop.
     */
    LaptopDetailsDto findById(Long id);

    /**
     * Retrieves a paginated list of laptops based on specified filters.
     * @param filters A map containing filters for the laptop search.
     * @param page    The page number for pagination.
     * @param size    The number of items per page.
     * @return A DTO containing the search results with pagination information.
     */
    LaptopsSearchResultDto findAll(Map<String,Object> filters, int page, int size);

    /**
     * Generates a report based on specified filters and writes it to the provided HttpServletResponse.
     * @param httpServletResponse The HttpServletResponse to write the report to.
     * @param filters              A map containing filters for the report generation.
     */
    void generateReport(HttpServletResponse httpServletResponse, Map<String,Object> filters);

    /**
     * Processes an uploaded file containing JSON data for adding new laptops.
     * @param file The byte array representing the uploaded file.
     * @return A DTO containing the result of the upload process.
     */
    JsonUploadResultDto uploadFromFile(byte[] file);
}
