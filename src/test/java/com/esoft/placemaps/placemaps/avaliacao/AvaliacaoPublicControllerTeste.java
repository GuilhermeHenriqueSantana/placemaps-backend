package com.esoft.placemaps.placemaps.avaliacao;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.usuario.TipoUsuario;
import com.esoft.placemaps.placemaps.usuario.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest
public class AvaliacaoPublicControllerTeste {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @MockBean
    private AvaliacaoRepository avaliacaoRepository;

    @Test
    void obterAvaliacoesPorPontoId() throws Exception {
        String pontoId = "id";
        String jsonEsperado = "{\"content\":[{\"data\":1636669279817,\"nome\":\"nome\",\"id\":\"id\",\"nota\":5,\"descricao\":\"descricao\"}],\"pageable\":{\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true},\"offset\":0,\"pageSize\":10,\"pageNumber\":0,\"paged\":true,\"unpaged\":false},\"last\":true,\"totalElements\":1,\"totalPages\":1,\"size\":10,\"number\":0,\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true},\"first\":true,\"numberOfElements\":1,\"empty\":false}";

        Map map = new HashMap();
        map.put("id", "id");
        map.put("descricao", "descricao");
        map.put("data", new Date(1636669279817l));
        map.put("nota", 5);
        map.put("nome", "nome");
    
        Pageable pageable = PageRequest.of(0, 10);
    
        Page<Map<String, Object>> avaliacoesPage = new PageImpl(Collections.singletonList(map), pageable, 1);
    
        Mockito.when(this.avaliacaoRepository.obterAvaliacoesPeloPonto(pageable, pontoId))
                .thenReturn(avaliacoesPage);

        String avaliacoesObtidasJson = mockMvc.perform(get("/api/public/avaliacao/obter-pelo-ponto/{pontoId}", pontoId)
                .param("page", "0")
                .param("size", "10"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String valoresJsonEsperado[] = jsonEsperado.replaceAll("\"","").replaceAll("content:","").replaceAll("\\{","").replaceAll("\\}","").replaceAll("\\[","").replaceAll("\\]","").split(",");
        String valoresAvaliacoesObtidasJson[] = avaliacoesObtidasJson.replaceAll("\"","").replaceAll("content:","").replaceAll("\\{","").replaceAll("\\}","").replaceAll("\\[","").replaceAll("\\]","").split(",");
        
        Map<String, String> mapValores = new HashMap<String, String>();
        
        for (String valorEsperado : valoresJsonEsperado) {
            String hash[] = valorEsperado.split(":");
            if (!hash[0].equals("unsorted") && !hash[0].equals("empty")) {
                mapValores.put(hash[0], hash[1]);
            }
        }

        for (String valoresObtidos : valoresAvaliacoesObtidasJson) {
            String hash[] = valoresObtidos.split(":");
            if (!hash[0].equals("unsorted") && !hash[0].equals("empty")) {
                Assertions.assertEquals(mapValores.get(hash[0]), hash[1]);
            }
        }
    }

    @Test
    void obterAvaliacaoPorId() throws Exception {
        Ponto ponto = new Ponto("nome", "descricao", "subTitulo", true, true, null, null, null);
        Usuario usuario = new Usuario("user", "email@email.com", "password", TipoUsuario.USUARIO, null, null, null);
        Avaliacao avaliacao = new Avaliacao(5, "resposta", "descricao", new Date(), usuario, ponto);

        Mockito.when(this.avaliacaoRepository.findById(avaliacao.getId()))
                .thenReturn(Optional.of(avaliacao));

        String avaliacaoJson = mockMvc.perform(get("/api/public/avaliacao/{id}", avaliacao.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();
    
        Avaliacao avaliacaoRetornada = jacksonObjectMapper.readValue(avaliacaoJson, Avaliacao.class);
    
        Assertions.assertEquals(avaliacao, avaliacaoRetornada);
    }

    @Test
    void obterAvaliacaoNulaPorId() throws Exception {
        Mockito.when(this.avaliacaoRepository.findById("1"))
                .thenReturn(Optional.empty());

        String avaliacaoJson = mockMvc.perform(get("/api/public/avaliacao/{id}", "1"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    
        Assertions.assertEquals("", avaliacaoJson);
    }
}
