package com.esoft.placemaps.placemaps.categoria;

import com.esoft.placemaps.placemaps.categoria.exception.CategoriaBadRequestException;
import com.esoft.placemaps.placemaps.ponto.PontoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoriaServiceTeste {
  @MockBean
  private CategoriaRepository categoriaRepository;
  @MockBean
  private PontoRepository pontoRepository;
  @Autowired
  private CategoriaService categoriaService;

  @Test
  void obterCategoriaExistente() {
    Optional<Categoria> categoria = Optional.of(new Categoria("Categoria 1"));
    String categoriaId = categoria.get().getId();

    Mockito.when(this.categoriaRepository.findById(categoriaId))
            .thenReturn(categoria);

    Categoria categoriaExistente = this.categoriaService.obterCategoriaExistente(categoriaId);

    Assertions.assertEquals(categoria.get(), categoriaExistente);
  }

  @Test
  void salvar() {
    Categoria categoria = new Categoria("Categoria 1");

    Mockito.when(this.categoriaRepository.save(categoria))
            .thenReturn(categoria);

    Categoria categoriaSalva = this.categoriaService.salvar(categoria);

    Assertions.assertEquals(categoria, categoriaSalva);
  }

  @Test
  void obterTodas() {
    List<Categoria> categorias = Arrays.asList(
            new Categoria("Categoria 1"),
            new Categoria("Categoria 2"),
            new Categoria("Categoria 3")
    );
    Mockito.when(this.categoriaRepository.findAll())
            .thenReturn(categorias);

    List<Categoria> categoriasExistentes = this.categoriaService.obterTodas();

    Assertions.assertEquals(categorias, categoriasExistentes);
  }

  @Test
  void deletarCategoria() {
    Boolean existePonto = Boolean.FALSE;
    Mockito.when(this.pontoRepository.existePontoComCategoriaId("id"))
            .thenReturn(existePonto);

    this.categoriaService.deletarCategoria("id");

    Mockito.verify(this.categoriaRepository).deleteById("id");

    Assertions.assertTrue(Boolean.TRUE);
  }

  @Test
  void throwDeletarCategoriaComPontoExistente() {
    Boolean existePonto = Boolean.TRUE;
    Mockito.when(this.pontoRepository.existePontoComCategoriaId("id"))
            .thenReturn(existePonto);

    Exception exception = assertThrows((CategoriaBadRequestException.class), () -> {
      this.categoriaService.deletarCategoria("id");
    });

    String expectedMessage = "Categoria já utilizada. Não é possível realizar a exclusão.";
    String actualMessage = exception.getMessage();

    Assertions.assertEquals(actualMessage, expectedMessage);
  }

}
