package com.esoft.placemaps.placemaps.avaliacao;

import com.esoft.placemaps.placemaps.avaliacao.dto.AvaliacaoFormDTO;
import com.esoft.placemaps.placemaps.categoria.Categoria;
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
  @WithMockUser(roles = "PROPRIETARIO")
  void salvar() {
    Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.ADMIN, "Documento", null, null);
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

}
