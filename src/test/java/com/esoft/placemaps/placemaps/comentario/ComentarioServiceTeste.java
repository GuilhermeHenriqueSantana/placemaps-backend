package com.esoft.placemaps.placemaps.comentario;

import com.esoft.placemaps.placemaps.categoria.Categoria;
import com.esoft.placemaps.placemaps.comentario.dto.ComentarioFormDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ComentarioServiceTeste {

  @MockBean
  private ComentarioRepository comentarioRepository;
  @MockBean
  private PontoRepository pontoRepository;
  @MockBean
  private UsuarioRepository usuarioRepository;
  @Autowired
  private ComentarioService comentarioService;

  @Test
  @WithMockUser(roles = "USUARIO")
  void salvar() {
    Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.USUARIO, "Documento", null, null);
    Categoria categoria = new Categoria("Nome");
    Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);
    Date date = new Date();
    Comentario comentario = new Comentario("Descricao", date, usuario, ponto);

    Mockito.when(this.pontoRepository.findById(ponto.getId()))
            .thenReturn(Optional.of(ponto));
    Mockito.when(this.usuarioRepository.findByEmail(usuario.getEmail()))
            .thenReturn(Optional.of(usuario));
    Mockito.when(this.comentarioRepository.save(comentario))
            .thenReturn(comentario);

    ComentarioFormDTO comentarioFormDTO = new ComentarioFormDTO(comentario.getDescricao(), ponto.getId(), comentario.getId());

    Comentario comentarioSalvo = this.comentarioService.salvar(comentarioFormDTO);

    Assertions.assertEquals(comentario, comentarioSalvo);
  }

  @Test
  void obterComentarioPorPontoId() {
    String pontoId = "id";

    Map map = new HashMap();
    map.put("id", "id");
    map.put("descricao", "descricao");
    map.put("data", new Date());
    map.put("nome", "nome");

    Pageable pageable = PageRequest.of(0, 10);

    Page<Map<String, Object>> comentariosPage = new PageImpl(Collections.singletonList(map), pageable, 1);

    Mockito.when(this.comentarioRepository.obterComentariosPeloPonto(pageable, pontoId))
            .thenReturn(comentariosPage);

    Page<Map<String, Object>> comentariosObtidasPage = this.comentarioService.obterComentariosPorPontoId(pageable, pontoId);

    Assertions.assertEquals(comentariosPage, comentariosObtidasPage);
  }

  @Test
  void obterComentarioPeloId() {
    Usuario usuario = new Usuario("Nome", "user", "password", TipoUsuario.USUARIO, "Documento", null, null);
    Categoria categoria = new Categoria("Nome");
    Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);
    Date date = new Date();
    Comentario comentario = new Comentario("Descricao", date, usuario, ponto);

    Mockito.when(this.comentarioRepository.findById(comentario.getId()))
            .thenReturn(Optional.of(comentario));

    Comentario comentarioObtido = this.comentarioService.obterComentarioPeloId(comentario.getId());

    Assertions.assertEquals(comentario, comentarioObtido);
  }
}
