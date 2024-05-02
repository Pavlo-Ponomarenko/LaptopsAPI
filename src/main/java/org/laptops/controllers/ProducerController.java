package org.laptops.controllers;

import jakarta.validation.Valid;
import org.laptops.dtos.ProducerInfoDto;
import org.laptops.dtos.ProducerSaveDto;
import org.laptops.dtos.RestResponseDto;
import org.laptops.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/producer")
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public RestResponseDto createProducer(@Valid @RequestBody ProducerSaveDto dto) {
        producerService.create(dto);
        return new RestResponseDto("OK");
    }

    @GetMapping("/")
    @ResponseBody
    public List<ProducerInfoDto> getAllProducers() {
        return producerService.getALL();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProducer(@PathVariable String id, @RequestBody ProducerSaveDto dto) {
        producerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProducer(@PathVariable String id) {
        producerService.delete(id);
    }
}
