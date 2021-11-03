package com.esoft.placemaps.placemaps.categoria;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoriaPublicControllerTeste {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper jacksonObjectMapper;

  @MockBean
  private CategoriaRepository categoriaRepository;

  @Test
  void obterTodas() throws Exception {
    List<Categoria> categorias = Arrays.asList(
            new Categoria("Categoria 1"),
            new Categoria("Categoria 2"),
            new Categoria("Categoria 3")
    );
    Mockito.when(this.categoriaRepository.findAll())
            .thenReturn(categorias);

    String categoriaListJson = mockMvc.perform(get("/api/public/categoria"))
            .andReturn()
            .getResponse()
            .getContentAsString();

    Categoria[] categoriasExistentes = jacksonObjectMapper.readValue(categoriaListJson, Categoria[].class);

    Assertions.assertEquals(categorias.size(), categoriasExistentes.length);
  }
}
