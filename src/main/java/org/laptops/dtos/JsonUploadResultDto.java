package org.laptops.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JsonUploadResultDto {

    @JsonProperty("successful_records")
    private Integer successfulRecords;
    @JsonProperty("unsuccessful_records")
    private Integer unsuccessfulRecords;
}
