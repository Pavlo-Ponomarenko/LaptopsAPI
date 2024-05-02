package org.laptops.services.csv;

import java.util.List;

public interface CSVGenerator<T> {

    byte[] generateCSV(List<T> items);
}
