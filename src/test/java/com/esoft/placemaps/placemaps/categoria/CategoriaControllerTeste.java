package com.esoft.placemaps.placemaps.categoria;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoriaControllerTeste {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void salvar() throws Exception {
        Categoria categoria = new Categoria("Categoria 1");
        
        Mockito.when(this.categoriaRepository.save(categoria))
                .thenReturn(categoria);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(categoria);
    
        String categoriaJson = mockMvc.perform(post("/api/categoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn()  
                .getResponse()
                .getContentAsString();  
                
        Categoria categoriaSalva = jacksonObjectMapper.readValue(categoriaJson, Categoria.class);

        assertEquals(categoria, categoriaSalva);
  }
}