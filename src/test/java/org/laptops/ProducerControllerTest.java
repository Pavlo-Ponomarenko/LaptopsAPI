package org.laptops;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.laptops.dtos.ProducerSaveDto;
import org.laptops.entities.Producer;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @AfterAll
    public void afterAll() {
        producerRepository.save(new Producer("Apple"));
        producerRepository.save(new Producer("Lenovo"));
        producerRepository.save(new Producer("Dell"));
        producerRepository.save(new Producer("Asus"));
        producerRepository.save(new Producer("Microsoft"));
    }

    @Test
    public void createProducer() throws Exception {
        // given
        ProducerSaveDto dto = new ProducerSaveDto();
        dto.setName("New_producer");
        String requestBody = objectMapper.writeValueAsString(dto);
        // when
        // then
        mvc.perform(post("/api/producer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAllProducers() throws Exception {
        mvc.perform(get("/api/producer/"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProducer() throws Exception {
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
    public void deleteProducer() throws Exception {
        // given
        ProducerSaveDto dto = new ProducerSaveDto();
        String name = "UNKNOWN";
        dto.setName(name);
        String requestBody = objectMapper.writeValueAsString(dto);
        // when
        mvc.perform(post("/api/producer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        //then
        mvc.perform(delete("/api/producer/" + name))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteWrongProducer() throws Exception {
        // given
        String name = "UNKNOWN";
        // when
        // then
        mvc.perform(delete("/api/producer/" + name))
                .andExpect(status().isNotFound());
    }
}
