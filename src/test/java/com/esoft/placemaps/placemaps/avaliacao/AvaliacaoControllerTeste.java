package com.esoft.placemaps.placemaps.avaliacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import com.esoft.placemaps.placemaps.avaliacao.dto.AvaliacaoFormDTO;
import com.esoft.placemaps.placemaps.avaliacao.dto.RespostaDeAvaliacaoDTO;
import com.esoft.placemaps.placemaps.avaliacao.exception.AvaliacaoBadRequestException;
import com.esoft.placemaps.placemaps.categoria.Categoria;
import com.esoft.placemaps.placemaps.controleponto.ControlePonto;
import com.esoft.placemaps.placemaps.controleponto.ControlePontoRepository;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.ponto.PontoRepository;
import com.esoft.placemaps.placemaps.usuario.TipoUsuario;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@AutoConfigureMockMvc
@SpringBootTest
public class AvaliacaoControllerTeste {
    
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @MockBean
    private AvaliacaoRepository avaliacaoRepository;
    @MockBean
    private PontoRepository pontoRepository;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private ControlePontoRepository controlePontoRepository;

    @Test
    @WithMockUser(roles = "USUARIO")
    void salvar() throws Exception {
        Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.USUARIO, "Documento", null, null);
        Categoria categoria = new Categoria("Nome");
        Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);
        Date date = new Date();
        Avaliacao avaliacao = new Avaliacao(5, "Resposta", "Descricao", date, usuario, ponto);

        Mockito.when(this.pontoRepository.findById(ponto.getId()))
                .thenReturn(Optional.of(ponto));
        Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
                .thenReturn(Optional.of(usuario));
        Mockito.when(this.controlePontoRepository.findFirstByUsuario(usuario))
                .thenReturn(null);
        Mockito.when(this.avaliacaoRepository.save(avaliacao))
                .thenReturn(avaliacao);

        AvaliacaoFormDTO avaliacaoFormDTO = new AvaliacaoFormDTO(5, "Descricao", ponto.getId(), avaliacao.getId());


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(avaliacaoFormDTO);
    
        String avaliacaoJson = mockMvc.perform(post("/api/avaliacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn()  
                .getResponse()
                .getContentAsString();  

        Avaliacao avaliacaoSalva = jacksonObjectMapper.readValue(avaliacaoJson, Avaliacao.class);

        Assertions.assertEquals(avaliacao, avaliacaoSalva);
    }

    @Test
    @WithMockUser(roles = "PROPRIETARIO")
    void throwSalvar() throws Exception {
        Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "Documento", null, null);
        Categoria categoria = new Categoria("Nome");
        Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);
        Date date = new Date();
        Avaliacao avaliacao = new Avaliacao(5, "Resposta", "Descricao", date, usuario, ponto);
        ControlePonto controlePonto = new ControlePonto(5, 5, usuario, Collections.singletonList(ponto));

        Mockito.when(this.pontoRepository.findById(ponto.getId()))
                .thenReturn(Optional.of(ponto));
        Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
                .thenReturn(Optional.of(usuario));
        Mockito.when(this.controlePontoRepository.findFirstByUsuario(usuario))
                .thenReturn(controlePonto);

        AvaliacaoFormDTO avaliacaoFormDTO = new AvaliacaoFormDTO(5, "Descricao", ponto.getId(), avaliacao.getId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(avaliacaoFormDTO);

        mockMvc.perform(post("/api/avaliacao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AvaliacaoBadRequestException))
            .andExpect(result -> Assertions.assertEquals("Não é possível avaliar o próprio ponto.", result.getResolvedException().getMessage()));
    }

    @Test
    @WithMockUser(roles = "PROPRIETARIO")
    void responderAvaliacao() throws Exception {
        Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "Documento", null, null);
        Categoria categoria = new Categoria("Nome");
        Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);
        Date date = new Date();
        Avaliacao avaliacao = new Avaliacao(5, "Resposta", "Descricao", date, usuario, ponto);
        ControlePonto controlePonto = new ControlePonto(5, 5, usuario, Collections.singletonList(ponto));
    
        Mockito.when(this.avaliacaoRepository.findById(avaliacao.getId()))
                .thenReturn(Optional.of(avaliacao));
        Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
                .thenReturn(Optional.of(usuario));
        Mockito.when(this.controlePontoRepository.findFirstByUsuario(usuario))
                .thenReturn(controlePonto);
        Mockito.when(this.avaliacaoRepository.save(avaliacao))
                .thenReturn(avaliacao);

        RespostaDeAvaliacaoDTO respostaDeAvaliacaoDTO = new RespostaDeAvaliacaoDTO(avaliacao.getResposta(), avaliacao.getId());
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(respostaDeAvaliacaoDTO);

        String avaliacaoJson = mockMvc.perform(put("/api/avaliacao/responder-avaliacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn()  
                .getResponse()
                .getContentAsString();  
      
        Avaliacao avaliacaoSalva = jacksonObjectMapper.readValue(avaliacaoJson, Avaliacao.class);
    
        Assertions.assertEquals(avaliacao, avaliacaoSalva);
    }
  
    @Test
    @WithMockUser(roles = "PROPRIETARIO")
    void throwProprietarioInvalidoResponderAvaliacao() throws Exception {
        Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "Documento", null, null);
        Categoria categoria = new Categoria("Nome");
        Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);
        Date date = new Date();
        Avaliacao avaliacao = new Avaliacao(5, "Resposta", "Descricao", date, usuario, ponto);
        ControlePonto controlePonto = new ControlePonto(5, 5, usuario, new ArrayList<>());
    
        Mockito.when(this.avaliacaoRepository.findById(avaliacao.getId()))
                .thenReturn(Optional.of(avaliacao));
        Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
                .thenReturn(Optional.of(usuario));
        Mockito.when(this.controlePontoRepository.findFirstByUsuario(usuario))
                .thenReturn(controlePonto);
    
        RespostaDeAvaliacaoDTO respostaDeAvaliacaoDTO = new RespostaDeAvaliacaoDTO(avaliacao.getResposta(), avaliacao.getId());
    
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(respostaDeAvaliacaoDTO);

        mockMvc.perform(put("/api/avaliacao/responder-avaliacao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AvaliacaoBadRequestException))
            .andExpect(result -> Assertions.assertEquals("Apenas o proprietário pode responder uma avaliação.", result.getResolvedException().getMessage()));
    }
  
    @Test
    @WithMockUser(roles = "PROPRIETARIO")
    void throwSemAvaliacaoResponderAvaliacao() throws Exception {
        String id = "id";
        String resposta = "resposta";
    
        Mockito.when(this.avaliacaoRepository.findById(id))
                .thenReturn(Optional.empty());
    
        RespostaDeAvaliacaoDTO respostaDeAvaliacaoDTO = new RespostaDeAvaliacaoDTO(resposta, id);
    
        ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String requestJson = ow.writeValueAsString(respostaDeAvaliacaoDTO);

        mockMvc.perform(put("/api/avaliacao/responder-avaliacao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AvaliacaoBadRequestException))
            .andExpect(result -> Assertions.assertEquals("Avaliação não encontrada.", result.getResolvedException().getMessage()));
    }

}
