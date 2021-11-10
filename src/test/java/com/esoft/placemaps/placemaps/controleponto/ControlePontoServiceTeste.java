package com.esoft.placemaps.placemaps.controleponto;

import com.esoft.placemaps.placemaps.avaliacao.AvaliacaoRepository;
import com.esoft.placemaps.placemaps.comentario.ComentarioRepository;
import com.esoft.placemaps.placemaps.controleponto.dto.DashboardDTO;
import com.esoft.placemaps.placemaps.controleponto.exception.ControlePontoBadRequestException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@SpringBootTest
public class ControlePontoServiceTeste {
  @MockBean
  private UsuarioRepository usuarioRepository;
  @MockBean
  private ControlePontoRepository controlePontoRepository;
  @MockBean
  private PontoRepository pontoRepository;
  @MockBean
  private AvaliacaoRepository avaliacaoRepository;
  @MockBean
  private ComentarioRepository comentarioRepository;
  @Autowired
  private ControlePontoService controlePontoService;

  @Test
  @WithMockUser(roles = "PROPRIETARIO")
  void alterarQuantidadePontosSolicitados() {
    Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "Documento", null, null);
    ControlePonto controlePonto = new ControlePonto(5, 5, usuario, null);
    ControlePonto controlePontoSolicitado = new ControlePonto(5, 10, usuario, null);

    Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
            .thenReturn(Optional.of(usuario));
    Mockito.when(this.controlePontoRepository.findFirstByUsuario(usuario))
            .thenReturn(controlePonto);

    Mockito.when(this.controlePontoRepository.save(controlePontoSolicitado))
            .thenReturn(controlePontoSolicitado);

    String esperado = "OK";
    String retorno = this.controlePontoService.alterarQuantidadePontosSolicitados(controlePontoSolicitado.getPontosSolicitados());

    Assertions.assertEquals(esperado, retorno);
  }

  @Test
  @WithMockUser(roles = "PROPRIETARIO")
  void throwEmAndamentoAlterarQuantidadePontosSolicitados() {
    Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "Documento", null, null);
    ControlePonto controlePonto = new ControlePonto(5, 10, usuario, null);

    Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
            .thenReturn(Optional.of(usuario));
    Mockito.when(this.controlePontoRepository.findFirstByUsuario(usuario))
            .thenReturn(controlePonto);

    Exception exception = assertThrows((ControlePontoBadRequestException.class), () -> {
      this.controlePontoService.alterarQuantidadePontosSolicitados(controlePonto.getPontosSolicitados());
    });

    String expectedMessage = "Pedido já em andamento.";
    String actualMessage = exception.getMessage();

    Assertions.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void aceitarSolicitacaoDeAlteracao() {
    Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "Documento", null, null);
    ControlePonto controlePonto = new ControlePonto(5, 5, usuario, null);

    Mockito.when(this.controlePontoRepository.findById(controlePonto.getId()))
            .thenReturn(Optional.of(controlePonto));

    Mockito.when(this.controlePontoRepository.save(controlePonto))
            .thenReturn(controlePonto);

    String esperado = "OK";
    String retorno = this.controlePontoService.aceitarNegarSolicitacaoDeAlteracao(controlePonto.getId(), Boolean.TRUE);

    Assertions.assertEquals(esperado, retorno);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void negarSolicitacaoDeAlteracao() {
    Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "Documento", null, null);
    ControlePonto controlePonto = new ControlePonto(5, 5, usuario, null);

    Mockito.when(this.controlePontoRepository.findById(controlePonto.getId()))
            .thenReturn(Optional.of(controlePonto));

    Mockito.when(this.controlePontoRepository.save(controlePonto))
            .thenReturn(controlePonto);

    String esperado = "OK";
    String retorno = this.controlePontoService.aceitarNegarSolicitacaoDeAlteracao(controlePonto.getId(), Boolean.FALSE);

    Assertions.assertEquals(esperado, retorno);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void throwAceitarNegarSolicitacaoDeAlteracao() {
    String id = "id";

    Mockito.when(this.controlePontoRepository.findById(id))
            .thenReturn(Optional.empty());

    Exception exception = assertThrows((ControlePontoBadRequestException.class), () -> {
      this.controlePontoService.aceitarNegarSolicitacaoDeAlteracao(id, Boolean.TRUE);
    });

    String expectedMessage = "Controle de ponto não encontrado.";
    String actualMessage = exception.getMessage();

    Assertions.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  @WithMockUser(roles = "PROPRIETARIO")
  void obterPeloProprietario() {
    Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "Documento", null, null);

    Map map = new HashMap();
    map.put("id", "id");
    map.put("ativo", 5);
    map.put("solicitados", 5);

    Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
            .thenReturn(Optional.of(usuario));
    Mockito.when(this.controlePontoRepository.obterPeloProprietario(usuario.getId()))
            .thenReturn(map);

    Map mapObtido = this.controlePontoService.obterPeloProprietario();

    Assertions.assertEquals(map, mapObtido);
  }

  @Test
  @WithMockUser(roles = "PROPRIETARIO")
  void obterControlesPontoSolicitados() {
    Map map = new HashMap();
    map.put("id", "id");
    map.put("ativo", 5);
    map.put("solicitados", 5);

    Pageable pageable = PageRequest.of(0, 10);

    Page<Map<String, Object>> controlePontosPage = new PageImpl(Collections.singletonList(map), pageable, 1);

    Mockito.when(this.controlePontoRepository.obterControlesPontoSolicitados(pageable))
            .thenReturn(controlePontosPage);

    Page<Map<String, Object>> controlePontosPageObtido = this.controlePontoService.obterControlesPontoSolicitados(pageable);

    Assertions.assertEquals(controlePontosPage, controlePontosPageObtido);
  }

  @Test
  @WithMockUser(roles = "PROPRIETARIO")
  void dashboard() {
    Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.PROPRIETARIO, "Documento", null, null);
    ControlePonto controlePonto = new ControlePonto(5, 5, usuario, null);

    Integer qtdePontosPermitidos = 5;
    Integer qtdePontosAtivos = 3;
    Integer qtdeAvaliacaoes = 50;
    Integer qtdeComentarios = 150;

    DashboardDTO dashboardDTO =  new DashboardDTO();
    dashboardDTO.setQtdePontosPermitidos(qtdePontosPermitidos);
    dashboardDTO.setQtdePontosAtivos(qtdePontosAtivos);
    dashboardDTO.setQtdeAvaliacoes(qtdeAvaliacaoes);
    dashboardDTO.setQtdeComentarios(qtdeComentarios);

    Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
            .thenReturn(Optional.of(usuario));
    Mockito.when(this.controlePontoRepository.findFirstByUsuarioId(usuario.getId()))
            .thenReturn(controlePonto);
    Mockito.when(this.pontoRepository.obterQuantidadeDePontosAtivosPeloControle(controlePonto.getId()))
            .thenReturn(qtdePontosAtivos);
    Mockito.when(this.avaliacaoRepository.obterQtdeAvaliacoesPeloControleDePonto(controlePonto.getId()))
            .thenReturn(qtdeAvaliacaoes);
    Mockito.when(this.comentarioRepository.obterQtdeComentariosPeloControleDePonto(controlePonto.getId()))
            .thenReturn(qtdeComentarios);

    DashboardDTO dashboardDTOObtido = this.controlePontoService.dashboard();

    Assertions.assertTrue(
            (dashboardDTO.getQtdePontosPermitidos() == dashboardDTOObtido.getQtdePontosPermitidos())
            && (dashboardDTO.getQtdePontosAtivos() == dashboardDTOObtido.getQtdePontosAtivos())
            && (dashboardDTO.getQtdeComentarios() == dashboardDTOObtido.getQtdeComentarios())
            && (dashboardDTO.getQtdeAvaliacoes() == dashboardDTOObtido.getQtdeAvaliacoes())
    );
  }

}
