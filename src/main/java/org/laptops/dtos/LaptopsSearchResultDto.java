package org.laptops.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LaptopsSearchResultDto {

    List<LaptopInfoDto> list;
    Integer totalPages;
}
