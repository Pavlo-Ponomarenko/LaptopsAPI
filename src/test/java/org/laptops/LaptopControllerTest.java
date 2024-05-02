package org.laptops;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.laptops.dtos.RestResponseDto;
import org.laptops.entities.Laptop;
import org.laptops.entities.Producer;
import org.laptops.repositories.LaptopRepository;
import org.laptops.repositories.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = App.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LaptopControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private LaptopRepository laptopRepository;
    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void beforeAll() {
        producerRepository.deleteAll();
        producerRepository.save(new Producer("Apple"));
        producerRepository.save(new Producer("Lenovo"));
        producerRepository.save(new Producer("Dell"));
        producerRepository.save(new Producer("Asus"));
        producerRepository.save(new Producer("Microsoft"));
    }

    @AfterEach
    public void afterEach() {
        laptopRepository.deleteAll();
    }

    @Test
    public void createLaptop() throws Exception {
        // given
        String title = "MacBook Pro";
        String producer = "Apple";
        String processor = "Apple M1 Pro";
        String memory = "16GB DDR4";
        String optionalPorts = "Thunderbolt 4, USB 4, SDXC, card slot";
        String body = """
                {
                    "title": "%s",
                    "producer": "%s",
                    "processor": "%s",
                    "memory": "%s",
                    "optional_ports": "%s"
                  }
                """.formatted(title, producer, processor, memory, optionalPorts);
        // when
        MvcResult mvcResult = mvc.perform(post("/api/laptop")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isCreated())
                .andReturn();
        RestResponseDto response = parseResponse(mvcResult, RestResponseDto.class);
        int laptopId = Integer.parseInt(response.getResult());
        // then
        assertThat(laptopId).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void createWrongLaptop() throws Exception {
        // given
        String title = "MacBook Pro";
        String producer = "UNKNOWN";
        String processor = "-";
        String memory = "-";
        String optionalPorts = "-";
        String body = """
                {
                    "title": "%s",
                    "producer": "%s",
                    "processor": "%s",
                    "memory": "%s",
                    "optional_ports": "%s"
                  }
                """.formatted(title, producer, processor, memory, optionalPorts);
        // when
        MvcResult mvcResult = mvc.perform(post("/api/laptop")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isNotFound())
                .andReturn();
        // then
        assertThat(laptopRepository.findAll()).isEmpty();
    }

    @Test
    public void updateLaptop() throws Exception {
        // given
        String updatedTitle = "Updated MacBook Pro";
        String updatedProcessor = "Updated Apple M1 Pro";
        String updatedMemory = "32GB DDR4";
        String updatedOptionalPorts = "Thunderbolt 4, USB 4, SDXC, card slot";

        String updatedBody = """
                {
                    "title": "%s",
                    "producer": "Apple",
                    "processor": "%s",
                    "memory": "%s",
                    "optional_ports": "%s"
                }
                """.formatted(updatedTitle, updatedProcessor, updatedMemory, updatedOptionalPorts);
        Laptop laptop = new Laptop();
        laptop.setTitle("p1");
        laptop.setProducer(new Producer("Apple"));
        laptop.setProcessor("p3");
        laptop.setMemory("p4");
        // when
        laptop = laptopRepository.save(laptop);
        mvc.perform(put("/api/laptop/{id}", laptop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBody)
                )
                .andExpect(status().is2xxSuccessful());
        Laptop updatedLaptop = laptopRepository.findById(laptop.getId()).orElse(null);
        // then
        assertThat(updatedLaptop).isNotNull();
        assertThat(updatedLaptop.getTitle()).isEqualTo(updatedTitle);
        assertThat(updatedLaptop.getProcessor()).isEqualTo(updatedProcessor);
        assertThat(updatedLaptop.getMemory()).isEqualTo(updatedMemory);
        assertThat(updatedLaptop.getOptionalPorts()).isEqualTo(updatedOptionalPorts);
    }

    @Test
    public void updateWithWrongProducer() throws Exception {
        // given
        Laptop laptop = saveLaptop();
        String updatedBody = """
                {
                "producer": "UNKNOWN"
                }
                """;
        // when
        // then
        mvc.perform(put("/api/laptop/{id}", laptop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBody)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteLaptop() throws Exception {
        // given
        Laptop laptop = saveLaptop();
        // when
        mvc.perform(delete("/api/laptop/{id}", laptop.getId()))
                .andExpect(status().is2xxSuccessful());
        // then
        assertThat(laptopRepository.findById(laptop.getId())).isEmpty();
    }

    @Test
    public void deleteByWrongId() throws Exception {
        // Given
        int id = -1;
        // When
        // Then
        mvc.perform(delete("/api/laptop/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findLaptopById() throws Exception {
        // Given
        Laptop laptop = saveLaptop();
        // When
        MvcResult mvcResult = mvc.perform(get("/api/laptop/{id}", laptop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        // Then
        String responseBody = mvcResult.getResponse().getContentAsString();
        Laptop returnedLaptop = objectMapper.readValue(responseBody, Laptop.class);
        assertThat(returnedLaptop).isNotNull();
        assertThat(returnedLaptop.getId()).isEqualTo(laptop.getId());
    }

    @Test
    public void findLaptopByWrongId() throws Exception {
        // Given
        int id = -1;
        // When
        // Then
         mvc.perform(get("/api/laptop/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void findAllLaptopsWithPagingInRequestBody() throws Exception {
        // Given
        int page = 0;
        int size = 10;
        loadDb();
        String requestBody = """
                {
                    "page": %d,
                    "size": %d
                }
                """.formatted(page, size);

        // When
        MvcResult mvcResult = mvc.perform(post("/api/laptop/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).isNotNull();
    }

    @Test
    public void uploadLaptops() throws Exception {
        // given
        Path path = Paths.get("src/test/resources/dataset.json");
        String fileName = "laptops.json";
        String contentType = "application/json";
        byte[] content = Files.readAllBytes(path);
        MockMultipartFile file = new MockMultipartFile("file", fileName, contentType, content);
        int page = 0;
        int size = 10;
        loadDb();
        String requestBody = """
                {
                    "page": %d,
                    "size": %d
                }
                """.formatted(page, size);
        // when
        MvcResult result = mvc.perform(MockMvcRequestBuilders.multipart("/api/laptop/upload")
                        .file(file))
                .andExpect(status().isCreated())
                .andReturn();
        MvcResult mvcResult = mvc.perform(post("/api/laptop/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful()).andReturn();
        // then
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).isNotNull();
    }

    @Test
    public void reportGeneration() throws Exception {
        // given
        loadDb();
        // when
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/laptop/_report")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        // then
        assertThat(response.getContentType()).isEqualTo("application/octet-stream");
    }

    private Laptop saveLaptop() {
        Laptop laptop = new Laptop();
        laptop.setTitle("MacBook Pro");
        laptop.setProducer(new Producer("Apple"));
        laptop.setProcessor("Apple M1 Pro");
        laptop.setMemory("16GB DDR4");
        laptop.setOptionalPorts("Thunderbolt 4, USB 4, SDXC, card slot");
        return laptopRepository.save(laptop);
    }

    private void loadDb() {
        saveLaptop();
        saveLaptop();
        saveLaptop();
        saveLaptop();
    }

    private <T>T parseResponse(MvcResult mvcResult, Class<T> c) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), c);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing json" + e.getMessage());
        }
    }
}
