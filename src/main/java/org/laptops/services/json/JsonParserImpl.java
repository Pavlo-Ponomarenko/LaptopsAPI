package org.laptops.services.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.laptops.dtos.LaptopSaveDto;
import org.laptops.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JsonParserImpl implements JsonParser {

    @Override
    public List<LaptopSaveDto> parseBytesToList(byte[] bytes) {
        List<LaptopSaveDto> dtoList;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            dtoList = objectMapper.readValue(bytes, new TypeReference<List<LaptopSaveDto>>(){});
        } catch (IOException e) {
            throw new BadRequestException("invalid file");
        }
        return dtoList;
    }
}
