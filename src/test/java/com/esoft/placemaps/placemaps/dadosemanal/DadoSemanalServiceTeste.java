package com.esoft.placemaps.placemaps.dadosemanal;

import com.esoft.placemaps.placemaps.categoria.Categoria;
import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalAtualizarDTO;
import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalDiaDaSemanaDTO;
import com.esoft.placemaps.placemaps.dadosemanal.dto.DadoSemanalFormDTO;
import com.esoft.placemaps.placemaps.dadosemanal.exception.DadoSemanalBadRequestException;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemanaRepository;
import com.esoft.placemaps.placemaps.diadasemana.NomeDiaSemana;
import com.esoft.placemaps.placemaps.item.ItemRepository;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.ponto.PontoRepository;
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

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@SpringBootTest
public class DadoSemanalServiceTeste {

  @MockBean
  private PontoRepository pontoRepository;
  @MockBean
  private DiaDaSemanaRepository diaDaSemanaRepository;
  @MockBean
  private DadoSemanalRepository dadoSemanalRepository;
  @MockBean
  private ItemRepository itemRepository;
  @Autowired
  private DadoSemanalService dadoSemanalService;

  @Test
  void salvar() {
    Categoria categoria = new Categoria("Nome");
    Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);

    DiaDaSemana domingo = new DiaDaSemana(NomeDiaSemana.DOMINGO);
    DiaDaSemana segunda = new DiaDaSemana(NomeDiaSemana.SEGUNDA);
    DiaDaSemana terca = new DiaDaSemana(NomeDiaSemana.TERCA);
    DiaDaSemana quarta = new DiaDaSemana(NomeDiaSemana.QUARTA);
    DiaDaSemana quinta = new DiaDaSemana(NomeDiaSemana.QUINTA);
    DiaDaSemana sexta = new DiaDaSemana(NomeDiaSemana.SEXTA);
    DiaDaSemana sabado = new DiaDaSemana(NomeDiaSemana.SABADO);

    List<DiaDaSemana> diaDaSemanaList = new ArrayList<>();
    diaDaSemanaList.add(domingo);
    diaDaSemanaList.add(segunda);
    diaDaSemanaList.add(terca);
    diaDaSemanaList.add(quarta);
    diaDaSemanaList.add(quinta);
    diaDaSemanaList.add(sexta);
    diaDaSemanaList.add(sabado);

    DadoSemanal dadoSemanal = new DadoSemanal("Nome",
            new Time(200),
            new Time(300),
            "Descricao",
            Boolean.TRUE,
            diaDaSemanaList,
            ponto);

    DadoSemanalFormDTO dadoSemanalFormDTO = new DadoSemanalFormDTO(dadoSemanal.getNome(),
            dadoSemanal.getHoraInicio(),
            dadoSemanal.getHoraFim(),
            dadoSemanal.getDescricao(),
            dadoSemanal.getPossuiValor(),
            ponto.getId(),
            diaDaSemanaList.stream().map(s -> s.getId()).collect(Collectors.toList()),
            dadoSemanal.getId()
            );

    Mockito.when(this.pontoRepository.findById(ponto.getId()))
            .thenReturn(Optional.of(ponto));
    Mockito.when(this.diaDaSemanaRepository.obterDiasDaSemanaPorIds(dadoSemanalFormDTO.getDiasDaSemanaIds()))
            .thenReturn(diaDaSemanaList);
    Mockito.when(this.dadoSemanalRepository.save(dadoSemanal))
            .thenReturn(dadoSemanal);

    DadoSemanal dadoSemanalSalvo = this.dadoSemanalService.salvar(dadoSemanalFormDTO);

    Assertions.assertEquals(dadoSemanal, dadoSemanalSalvo);
  }

  @Test
  void throwDiasNaoEncontradosSalvar() {
    Categoria categoria = new Categoria("Nome");
    Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);

    DiaDaSemana domingo = new DiaDaSemana(NomeDiaSemana.DOMINGO);
    DiaDaSemana segunda = new DiaDaSemana(NomeDiaSemana.SEGUNDA);
    DiaDaSemana terca = new DiaDaSemana(NomeDiaSemana.TERCA);
    DiaDaSemana quarta = new DiaDaSemana(NomeDiaSemana.QUARTA);
    DiaDaSemana quinta = new DiaDaSemana(NomeDiaSemana.QUINTA);
    DiaDaSemana sexta = new DiaDaSemana(NomeDiaSemana.SEXTA);
    DiaDaSemana sabado = new DiaDaSemana(NomeDiaSemana.SABADO);

    List<DiaDaSemana> diaDaSemanaList = new ArrayList<>();
    diaDaSemanaList.add(domingo);
    diaDaSemanaList.add(segunda);
    diaDaSemanaList.add(terca);
    diaDaSemanaList.add(quarta);
    diaDaSemanaList.add(quinta);
    diaDaSemanaList.add(sexta);
    diaDaSemanaList.add(sabado);

    DadoSemanal dadoSemanal = new DadoSemanal("Nome",
            new Time(200),
            new Time(300),
            "Descricao",
            Boolean.TRUE,
            diaDaSemanaList,
            ponto);

    DadoSemanalFormDTO dadoSemanalFormDTO = new DadoSemanalFormDTO(dadoSemanal.getNome(),
            dadoSemanal.getHoraInicio(),
            dadoSemanal.getHoraFim(),
            dadoSemanal.getDescricao(),
            dadoSemanal.getPossuiValor(),
            ponto.getId(),
            diaDaSemanaList.stream().map(s -> s.getId()).collect(Collectors.toList()),
            dadoSemanal.getId()
    );

    dadoSemanalFormDTO.getDiasDaSemanaIds().add("invalido");

    Mockito.when(this.pontoRepository.findById(ponto.getId()))
            .thenReturn(Optional.of(ponto));
    Mockito.when(this.diaDaSemanaRepository.obterDiasDaSemanaPorIds(dadoSemanalFormDTO.getDiasDaSemanaIds()))
            .thenReturn(diaDaSemanaList);

    Exception exception = assertThrows((DadoSemanalBadRequestException.class), () -> {
      this.dadoSemanalService.salvar(dadoSemanalFormDTO);
    });

    String expectedMessage = "Algum(ns) DiaDaSemana não foi encontrado.";
    String actualMessage = exception.getMessage();

    Assertions.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void atualizar() {
    Categoria categoria = new Categoria("Nome");
    Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);

    DiaDaSemana domingo = new DiaDaSemana(NomeDiaSemana.DOMINGO);
    DiaDaSemana segunda = new DiaDaSemana(NomeDiaSemana.SEGUNDA);
    DiaDaSemana terca = new DiaDaSemana(NomeDiaSemana.TERCA);
    DiaDaSemana quarta = new DiaDaSemana(NomeDiaSemana.QUARTA);
    DiaDaSemana quinta = new DiaDaSemana(NomeDiaSemana.QUINTA);
    DiaDaSemana sexta = new DiaDaSemana(NomeDiaSemana.SEXTA);
    DiaDaSemana sabado = new DiaDaSemana(NomeDiaSemana.SABADO);

    List<DiaDaSemana> diaDaSemanaList = new ArrayList<>();
    diaDaSemanaList.add(domingo);
    diaDaSemanaList.add(segunda);
    diaDaSemanaList.add(terca);
    diaDaSemanaList.add(quarta);
    diaDaSemanaList.add(quinta);
    diaDaSemanaList.add(sexta);
    diaDaSemanaList.add(sabado);

    DadoSemanal dadoSemanal = new DadoSemanal("Nome",
            new Time(200),
            new Time(300),
            "Descricao",
            Boolean.TRUE,
            diaDaSemanaList,
            ponto);

    DadoSemanalAtualizarDTO dadoSemanalAtualizarDTO = new DadoSemanalAtualizarDTO(dadoSemanal.getNome(),
            dadoSemanal.getHoraInicio(),
            dadoSemanal.getHoraFim(),
            dadoSemanal.getDescricao(),
            dadoSemanal.getPossuiValor(),
            diaDaSemanaList.stream().map(s -> s.getId()).collect(Collectors.toList())
    );

    Mockito.when(this.diaDaSemanaRepository.obterDiasDaSemanaPorIds(dadoSemanalAtualizarDTO.getDiasDaSemanaIds()))
            .thenReturn(diaDaSemanaList);
    Mockito.when(this.dadoSemanalRepository.save(dadoSemanal))
            .thenReturn(dadoSemanal);
    Mockito.when(this.dadoSemanalRepository.findById(dadoSemanal.getId()))
            .thenReturn(Optional.of(dadoSemanal));

    DadoSemanal dadoSemanalSalvo = this.dadoSemanalService.atualizar(dadoSemanal.getId(), dadoSemanalAtualizarDTO);

    Assertions.assertEquals(dadoSemanal, dadoSemanalSalvo);
  }

  @Test
  void throwDadoNaoEncontradoAtualizar() {
    Categoria categoria = new Categoria("Nome");
    Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);

    DiaDaSemana domingo = new DiaDaSemana(NomeDiaSemana.DOMINGO);
    DiaDaSemana segunda = new DiaDaSemana(NomeDiaSemana.SEGUNDA);
    DiaDaSemana terca = new DiaDaSemana(NomeDiaSemana.TERCA);
    DiaDaSemana quarta = new DiaDaSemana(NomeDiaSemana.QUARTA);
    DiaDaSemana quinta = new DiaDaSemana(NomeDiaSemana.QUINTA);
    DiaDaSemana sexta = new DiaDaSemana(NomeDiaSemana.SEXTA);
    DiaDaSemana sabado = new DiaDaSemana(NomeDiaSemana.SABADO);

    List<DiaDaSemana> diaDaSemanaList = new ArrayList<>();
    diaDaSemanaList.add(domingo);
    diaDaSemanaList.add(segunda);
    diaDaSemanaList.add(terca);
    diaDaSemanaList.add(quarta);
    diaDaSemanaList.add(quinta);
    diaDaSemanaList.add(sexta);
    diaDaSemanaList.add(sabado);

    DadoSemanal dadoSemanal = new DadoSemanal("Nome",
            new Time(200),
            new Time(300),
            "Descricao",
            Boolean.TRUE,
            diaDaSemanaList,
            ponto);

    DadoSemanalAtualizarDTO dadoSemanalAtualizarDTO = new DadoSemanalAtualizarDTO(dadoSemanal.getNome(),
            dadoSemanal.getHoraInicio(),
            dadoSemanal.getHoraFim(),
            dadoSemanal.getDescricao(),
            dadoSemanal.getPossuiValor(),
            diaDaSemanaList.stream().map(s -> s.getId()).collect(Collectors.toList())
    );

    Mockito.when(this.diaDaSemanaRepository.obterDiasDaSemanaPorIds(dadoSemanalAtualizarDTO.getDiasDaSemanaIds()))
            .thenReturn(diaDaSemanaList);
    Mockito.when(this.dadoSemanalRepository.findById(dadoSemanal.getId()))
            .thenReturn(Optional.empty());

    Exception exception = assertThrows((DadoSemanalBadRequestException.class), () -> {
      this.dadoSemanalService.atualizar(dadoSemanal.getId(), dadoSemanalAtualizarDTO);
    });

    String expectedMessage = "Dado semanal não encontrado.";
    String actualMessage = exception.getMessage();

    Assertions.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void throwDiasNaoEncontradoAtualizar() {
    Categoria categoria = new Categoria("Nome");
    Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);

    DiaDaSemana domingo = new DiaDaSemana(NomeDiaSemana.DOMINGO);
    DiaDaSemana segunda = new DiaDaSemana(NomeDiaSemana.SEGUNDA);
    DiaDaSemana terca = new DiaDaSemana(NomeDiaSemana.TERCA);
    DiaDaSemana quarta = new DiaDaSemana(NomeDiaSemana.QUARTA);
    DiaDaSemana quinta = new DiaDaSemana(NomeDiaSemana.QUINTA);
    DiaDaSemana sexta = new DiaDaSemana(NomeDiaSemana.SEXTA);
    DiaDaSemana sabado = new DiaDaSemana(NomeDiaSemana.SABADO);

    List<DiaDaSemana> diaDaSemanaList = new ArrayList<>();
    diaDaSemanaList.add(domingo);
    diaDaSemanaList.add(segunda);
    diaDaSemanaList.add(terca);
    diaDaSemanaList.add(quarta);
    diaDaSemanaList.add(quinta);
    diaDaSemanaList.add(sexta);
    diaDaSemanaList.add(sabado);

    DadoSemanal dadoSemanal = new DadoSemanal("Nome",
            new Time(200),
            new Time(300),
            "Descricao",
            Boolean.TRUE,
            diaDaSemanaList,
            ponto);

    DadoSemanalAtualizarDTO dadoSemanalAtualizarDTO = new DadoSemanalAtualizarDTO(dadoSemanal.getNome(),
            dadoSemanal.getHoraInicio(),
            dadoSemanal.getHoraFim(),
            dadoSemanal.getDescricao(),
            dadoSemanal.getPossuiValor(),
            diaDaSemanaList.stream().map(s -> s.getId()).collect(Collectors.toList())
    );

    dadoSemanalAtualizarDTO.getDiasDaSemanaIds().add("invalido");

    Mockito.when(this.diaDaSemanaRepository.obterDiasDaSemanaPorIds(dadoSemanalAtualizarDTO.getDiasDaSemanaIds()))
            .thenReturn(diaDaSemanaList);
    Mockito.when(this.dadoSemanalRepository.findById(dadoSemanal.getId()))
            .thenReturn(Optional.of(dadoSemanal));

    Exception exception = assertThrows((DadoSemanalBadRequestException.class), () -> {
      this.dadoSemanalService.atualizar(dadoSemanal.getId(), dadoSemanalAtualizarDTO);
    });

    String expectedMessage = "Algum(ns) DiaDaSemana não foi encontrado.";
    String actualMessage = exception.getMessage();

    Assertions.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void obterDadosPorPontoId() {
    String pontoId = "id";

    Map map = new HashMap();
    map.put("id", "id");
    map.put("nome", "nome");
    map.put("hora_inicio", new Time(200));
    map.put("hora_fim", new Time(300));
    map.put("descricao", "descricao");
    map.put("possui_valor", Boolean.TRUE);

    Pageable pageable = PageRequest.of(0, 10);

    Page<Map<String, Object>> dadosSemanaisPage = new PageImpl(Collections.singletonList(map), pageable, 1);

    Mockito.when(this.dadoSemanalRepository.findDadoSemanalsByPontoId(pageable, pontoId))
            .thenReturn(dadosSemanaisPage);

    Page<Map<String, Object>> dadosSemanaisPageObtidos = this.dadoSemanalService.obterDadosPorPontoId(pageable, pontoId);

    Assertions.assertEquals(dadosSemanaisPage, dadosSemanaisPageObtidos);
  }

  @Test
  void obterDadosPorPontoIdPorDias() {
    String pontoId = "id";

    Map map = new HashMap();
    map.put("id", "id");
    map.put("nome", "nome");
    map.put("descricao", "descricao");

    List<Map<String, Object>> mapList = Collections.singletonList(map);

    DiaDaSemana domingo = new DiaDaSemana(NomeDiaSemana.DOMINGO);
    DiaDaSemana segunda = new DiaDaSemana(NomeDiaSemana.SEGUNDA);
    DiaDaSemana terca = new DiaDaSemana(NomeDiaSemana.TERCA);
    DiaDaSemana quarta = new DiaDaSemana(NomeDiaSemana.QUARTA);
    DiaDaSemana quinta = new DiaDaSemana(NomeDiaSemana.QUINTA);
    DiaDaSemana sexta = new DiaDaSemana(NomeDiaSemana.SEXTA);
    DiaDaSemana sabado = new DiaDaSemana(NomeDiaSemana.SABADO);

    List<DiaDaSemana> diaDaSemanaList = new ArrayList<>();
    diaDaSemanaList.add(domingo);
    diaDaSemanaList.add(segunda);
    diaDaSemanaList.add(terca);
    diaDaSemanaList.add(quarta);
    diaDaSemanaList.add(quinta);
    diaDaSemanaList.add(sexta);
    diaDaSemanaList.add(sabado);

    List<DadoSemanalDiaDaSemanaDTO> dadoSemanalDiaDaSemanaDTOList = new ArrayList<>();
    for (DiaDaSemana diaDaSemana: diaDaSemanaList
         ) {
      DadoSemanalDiaDaSemanaDTO dadoSemanalDiaDaSemanaDTO = new DadoSemanalDiaDaSemanaDTO();
      dadoSemanalDiaDaSemanaDTO.setDiaDaSemana(diaDaSemana.getNomeDiaSemana().name());
      dadoSemanalDiaDaSemanaDTO.setDadoSemanalList(mapList);
      Mockito.when(this.dadoSemanalRepository.findDadoSemanalsByPontoIdAndDiaDaSemanaId(pontoId, diaDaSemana.getId()))
                      .thenReturn(mapList);
      dadoSemanalDiaDaSemanaDTOList.add(dadoSemanalDiaDaSemanaDTO);
    }

    Mockito.when(this.diaDaSemanaRepository.findAll())
                    .thenReturn(diaDaSemanaList);

    List<DadoSemanalDiaDaSemanaDTO> dadoSemanalDiaDaSemanaDTOObtidos = this.dadoSemanalService.obterDadosPorPontoIdPorDias(pontoId);

    Assertions.assertEquals(dadoSemanalDiaDaSemanaDTOList.size(), dadoSemanalDiaDaSemanaDTOObtidos.size());
  }

  @Test
  void obterDadoSemanalPeloId() {
    Categoria categoria = new Categoria("Nome");
    Ponto ponto = new Ponto("Nome", "Descricao", "SubTitulo", true, true, null, null, categoria);


    DiaDaSemana domingo = new DiaDaSemana(NomeDiaSemana.DOMINGO);
    DiaDaSemana segunda = new DiaDaSemana(NomeDiaSemana.SEGUNDA);
    DiaDaSemana terca = new DiaDaSemana(NomeDiaSemana.TERCA);
    DiaDaSemana quarta = new DiaDaSemana(NomeDiaSemana.QUARTA);
    DiaDaSemana quinta = new DiaDaSemana(NomeDiaSemana.QUINTA);
    DiaDaSemana sexta = new DiaDaSemana(NomeDiaSemana.SEXTA);
    DiaDaSemana sabado = new DiaDaSemana(NomeDiaSemana.SABADO);

    List<DiaDaSemana> diaDaSemanaList = new ArrayList<>();
    diaDaSemanaList.add(domingo);
    diaDaSemanaList.add(segunda);
    diaDaSemanaList.add(terca);
    diaDaSemanaList.add(quarta);
    diaDaSemanaList.add(quinta);
    diaDaSemanaList.add(sexta);
    diaDaSemanaList.add(sabado);

    DadoSemanal dadoSemanal = new DadoSemanal("Nome",
            new Time(200),
            new Time(300),
            "Descricao",
            Boolean.TRUE,
            diaDaSemanaList,
            ponto);

    Mockito.when(this.dadoSemanalRepository.findById(dadoSemanal.getId()))
            .thenReturn(Optional.of(dadoSemanal));

    DadoSemanal dadoSemanalObtido = this.dadoSemanalService.obterDadoSemanalPeloId(dadoSemanal.getId());

    Assertions.assertEquals(dadoSemanal, dadoSemanalObtido);
  }

  @Test
  void obterDadoSemanalNuloPeloId() {
    String id = "id";
    Mockito.when(this.dadoSemanalRepository.findById(id))
            .thenReturn(Optional.empty());

    DadoSemanal dadoSemanalObtido = this.dadoSemanalService.obterDadoSemanalPeloId(id);

    Assertions.assertEquals(null, dadoSemanalObtido);
  }

  @Test
  void deletarDadoSemanal() {
    String id = "id";

    this.dadoSemanalService.deletarDadoSemanal(id);

    Mockito.verify(this.dadoSemanalRepository).deleteById(id);
    Mockito.verify(this.itemRepository).removeByDadoSemanalId(id);

    Assertions.assertTrue(Boolean.TRUE);
  }
}
