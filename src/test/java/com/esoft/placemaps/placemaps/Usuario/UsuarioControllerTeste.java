package com.esoft.placemaps.placemaps.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.esoft.placemaps.placemaps.evento.Evento;
import com.esoft.placemaps.placemaps.evento.EventoRepository;
import com.esoft.placemaps.placemaps.localizacao.Localizacao;
import com.esoft.placemaps.placemaps.usuario.TipoUsuario;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.esoft.placemaps.placemaps.usuario.UsuarioRepository;
import com.esoft.placemaps.placemaps.usuario.dto.AtualizarUsuarioDTO;
import com.esoft.placemaps.placemaps.usuario.exception.UsuarioBadRequestException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@AutoConfigureMockMvc
@SpringBootTest
public class UsuarioControllerTeste {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private EventoRepository eventoRepository;

    @Test
    @WithMockUser(roles = "USUARIO")
    void lembrarEvento() throws Exception {
        Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.USUARIO, null, new ArrayList<Evento>(), null);
        Localizacao localizacao = new Localizacao("brasil", "parana", "maringa", "centro", "789", "rua", 7f, 6f, null, null);
        Evento evento = new Evento(new Date(1609470000000l), new Date(1609556400000l), "evento", "descricao", true, null, localizacao, null, null, null);

        Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
                .thenReturn(Optional.of(usuario));
        Mockito.when(this.eventoRepository.findById(evento.getId()))
                .thenReturn(Optional.of(evento));
        Mockito.when(this.usuarioRepository.save(usuario))
                .thenReturn(usuario);

        String jsonResponse = mockMvc.perform(put("/api/usuario/lembrar-evento/{eventoId}", evento.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()  
                .getResponse()
                .getContentAsString(); 

        Assertions.assertEquals("Ok", jsonResponse);
    }

    @Test
    @WithMockUser(roles = "USUARIO")
    void esquecerEvento() throws Exception {
        Localizacao localizacao = new Localizacao("brasil", "parana", "maringa", "centro", "789", "rua", 7f, 6f, null, null);
        Evento evento = new Evento(new Date(1609470000000l), new Date(1609556400000l), "evento", "descricao", true, null, localizacao, null, null, null);
        List<Evento> eventos = new ArrayList<>();
        eventos.add(evento);
        Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.USUARIO, null, eventos, null);

        Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
                .thenReturn(Optional.of(usuario));
        Mockito.when(this.eventoRepository.findById(evento.getId()))
                .thenReturn(Optional.of(evento));
        Mockito.when(this.usuarioRepository.save(usuario))
                .thenReturn(usuario);

        String jsonResponse = mockMvc.perform(put("/api/usuario/lembrar-evento/{eventoId}", evento.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()  
                .getResponse()
                .getContentAsString(); 

        Assertions.assertEquals("Ok", jsonResponse);
    }

    @Test
    @WithMockUser(roles = "PROPRIETARIO")
    void atualizarDocumento() throws Exception {
        Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "44126735020", null, null);
        
        Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
            .thenReturn(Optional.of(usuario));
        
        int status = mockMvc.perform(put("/api/usuario/atualizar-documento?documento=" + "44126735020")
            .contentType(MediaType.APPLICATION_JSON))
            .andReturn()  
            .getResponse()
            .getStatus(); 

        Assertions.assertEquals(HttpStatus.ACCEPTED.value(), status);
    }

    @Test
    @WithMockUser(roles = "PROPRIETARIO")
    void throwAtualizarComDocumentoInvalido() throws Exception {
        Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "44126735020", null, null);
        
        Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
            .thenReturn(Optional.of(usuario));
        
        mockMvc.perform(put("/api/usuario/atualizar-documento?documento=" + "44126735021")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof UsuarioBadRequestException))
                .andExpect(result -> Assertions.assertEquals("Documento inválido.", result.getResolvedException().getMessage()));
    }

    @Test
    @WithMockUser(roles = "PROPRIETARIO")
    void atualizarUsuario() throws Exception {
        Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "44126735020", null, null);
        
        Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
            .thenReturn(Optional.of(usuario));
        
        AtualizarUsuarioDTO atualizarUsuarioDTO = new AtualizarUsuarioDTO("usuario", null);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(atualizarUsuarioDTO); 

        int status = mockMvc.perform(put("/api/usuario")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andReturn()  
            .getResponse()
            .getStatus(); 

        Assertions.assertEquals(HttpStatus.ACCEPTED.value(), status);
    }

}
