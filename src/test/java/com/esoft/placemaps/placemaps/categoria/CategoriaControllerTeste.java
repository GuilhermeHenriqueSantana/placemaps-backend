package com.esoft.placemaps.placemaps.categoria;

import com.esoft.placemaps.placemaps.categoria.exception.CategoriaBadRequestException;
import com.esoft.placemaps.placemaps.ponto.PontoRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoriaControllerTeste {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @MockBean
    private CategoriaRepository categoriaRepository;
    @MockBean
    private PontoRepository pontoRepository;

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

  @Test
  @WithMockUser(roles = "ADMIN")
  void deletarCategoria() throws Exception {
        String id = "id";
        Boolean existePonto = Boolean.FALSE;
        Mockito.when(this.pontoRepository.existePontoComCategoriaId(id))
                .thenReturn(existePonto);

        mockMvc.perform(delete("/api/categoria/id")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()  
                .getResponse()
                .getContentAsString();
        
        Mockito.verify(this.categoriaRepository).deleteById(id);

        Assertions.assertTrue(Boolean.TRUE);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void throwDeletarCategoriaComPontoExistente() throws Exception {
    String id = "id";
    Boolean existePonto = Boolean.TRUE;
    Mockito.when(this.pontoRepository.existePontoComCategoriaId(id))
            .thenReturn(existePonto);
    
    mockMvc.perform(delete("/api/categoria/id")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof CategoriaBadRequestException))
            .andExpect(result -> assertEquals("Categoria já utilizada. Não é possível realizar a exclusão.", result.getResolvedException().getMessage()));
  }
}