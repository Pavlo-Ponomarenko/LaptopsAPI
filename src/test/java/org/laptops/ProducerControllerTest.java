package org.laptops;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.laptops.dtos.ProducerSaveDto;
import org.laptops.repositories.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = App.class)
@AutoConfigureMockMvc
public class ProducerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void afterEach() {
        producerRepository.deleteAll();
    }

    @Test
    public void testCreateProducer() throws Exception {
        // given
        ProducerSaveDto dto = new ProducerSaveDto();
        dto.setName("NX");
        String requestBody = objectMapper.writeValueAsString(dto);
        // when
        // then
        mvc.perform(post("/api/producer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllProducers() throws Exception {
        mvc.perform(get("/api/producer/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateProducer() throws Exception {
        // given
        ProducerSaveDto oldDto = new ProducerSaveDto();
        oldDto.setName("NX");
        ProducerSaveDto dto = new ProducerSaveDto();
        dto.setName("Updated NX");
        String oldRequestBody = objectMapper.writeValueAsString(oldDto);
        String updatedRequestBody = objectMapper.writeValueAsString(oldDto);
        // when
        mvc.perform(post("/api/producer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(oldRequestBody));
        // then
        mvc.perform(put("/api/producer/NX")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteProducer() throws Exception {
        // given
        ProducerSaveDto dto = new ProducerSaveDto();
        dto.setName("NX");
        String requestBody = objectMapper.writeValueAsString(dto);
        // when
        mvc.perform(post("/api/producer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        mvc.perform(delete("/api/producer/NX"))
                .andExpect(status().isNoContent());
    }
}
