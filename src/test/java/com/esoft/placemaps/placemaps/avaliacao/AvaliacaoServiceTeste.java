package com.esoft.placemaps.placemaps.avaliacao;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@SpringBootTest
public class AvaliacaoServiceTeste {
  @MockBean
  private AvaliacaoRepository avaliacaoRepository;
  @MockBean
  private PontoRepository pontoRepository;
  @MockBean
  private UsuarioRepository usuarioRepository;
  @MockBean
  private ControlePontoRepository controlePontoRepository;
  @Autowired
  private AvaliacaoService avaliacaoService;

  @Test
  @WithMockUser(roles = "USUARIO")
  void salvar() {
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

    Avaliacao avaliacaoSalva = this.avaliacaoService.salvar(avaliacaoFormDTO);

    Assertions.assertEquals(avaliacao, avaliacaoSalva);
  }

  @Test
  @WithMockUser(roles = "PROPRIETARIO")
  void throwSalvar() {
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

    Exception exception = assertThrows((AvaliacaoBadRequestException.class), () -> {
      this.avaliacaoService.salvar(avaliacaoFormDTO);
    });

    String expectedMessage = "Não é possível avaliar o próprio ponto.";
    String actualMessage = exception.getMessage();

    Assertions.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  @WithMockUser(roles = "PROPRIETARIO")
  void responderAvaliacao() {
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

    Avaliacao avaliacaoSalva = this.avaliacaoService.responderAvaliacao(respostaDeAvaliacaoDTO);

    Assertions.assertEquals(avaliacao, avaliacaoSalva);
  }

  @Test
  @WithMockUser(roles = "PROPRIETARIO")
  void throwProprietarioInvalidoResponderAvaliacao() {
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

    Exception exception = assertThrows((AvaliacaoBadRequestException.class), () -> {
      this.avaliacaoService.responderAvaliacao(respostaDeAvaliacaoDTO);
    });

    String expectedMessage = "Apenas o proprietário pode responder uma avaliação.";
    String actualMessage = exception.getMessage();

    Assertions.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  @WithMockUser(roles = "PROPRIETARIO")
  void throwSemAvaliacaoResponderAvaliacao() {
    String id = "id";
    String resposta = "resposta";

    Mockito.when(this.avaliacaoRepository.findById(id))
            .thenReturn(Optional.empty());

    RespostaDeAvaliacaoDTO respostaDeAvaliacaoDTO = new RespostaDeAvaliacaoDTO(resposta, id);

    Exception exception = assertThrows((AvaliacaoBadRequestException.class), () -> {
      this.avaliacaoService.responderAvaliacao(respostaDeAvaliacaoDTO);
    });

    String expectedMessage = "Avaliação não encontrada.";
    String actualMessage = exception.getMessage();

    Assertions.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void obterAvaliacaoPorId() {
    Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.USUARIO, "Documento", null, null);
    Categoria categoria = new Categoria("Nome");
    Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);
    Date date = new Date();
    Avaliacao avaliacao = new Avaliacao(5, "Resposta", "Descricao", date, usuario, ponto);

    Mockito.when(this.avaliacaoRepository.findById(avaliacao.getId()))
            .thenReturn(Optional.of(avaliacao));

    Avaliacao avaliacaoObtida = this.avaliacaoService.obterAvaliacaoPorId(avaliacao.getId());

    Assertions.assertEquals(avaliacao, avaliacaoObtida);
  }

  @Test
  void obterAvaliacaoNulaPorId() {
    String id = "id";
    Mockito.when(this.avaliacaoRepository.findById(id))
            .thenReturn(Optional.empty());

    Avaliacao avaliacaoObtida = this.avaliacaoService.obterAvaliacaoPorId(id);

    Assertions.assertEquals(null, avaliacaoObtida);
  }

}
