package org.laptops.services.csv;

import java.util.List;

/**
 * Interface for generating CSV (Comma-Separated Values) files from a list of items.
 * @param <T> The type of items to be included in the CSV file.
 * @author Pavlo Ponomarenko
 */
public interface CSVGenerator<T> {

    /**
     * Generates a CSV file as a byte array from the provided list of items.
     * @param items The list of items to be included in the CSV file.
     * @return A byte array representing the generated CSV file.
     */
    byte[] generateCSV(List<T> items);
}
