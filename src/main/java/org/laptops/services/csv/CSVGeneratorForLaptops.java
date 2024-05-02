package org.laptops.services.csv;

import com.opencsv.CSVWriter;
import org.laptops.dtos.LaptopInfoDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Implementation of the CSVGenerator interface for generating CSV files specifically for laptops.
 * @author Pavlo Ponomarenko
 */
@Service
public class CSVGeneratorForLaptops implements CSVGenerator<LaptopInfoDto> {

    @Override
    public byte[] generateCSV(List<LaptopInfoDto> items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))) {
            String[] header = {"Title", "Producer", "Processor", "Memory"};
            writer.writeNext(header);
            for (LaptopInfoDto item : items) {
                String[] row = {item.getTitle(), item.getProducer(), item.getProcessor(), item.getMemory()};
                writer.writeNext(row);
            }
            writer.flush();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
