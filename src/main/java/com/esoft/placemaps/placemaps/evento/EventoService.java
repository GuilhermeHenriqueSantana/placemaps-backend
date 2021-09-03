package com.esoft.placemaps.placemaps.evento;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.diadasemana.NomeDiaSemana;
import com.esoft.placemaps.placemaps.evento.exception.EventoBadRequestException;
import com.esoft.placemaps.placemaps.localizacao.Localizacao;
import com.esoft.placemaps.placemaps.localizacao.LocalizacaoService;
import com.esoft.placemaps.placemaps.ponto.Ponto;
import com.esoft.placemaps.placemaps.ponto.PontoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    private final LocalizacaoService localizacaoService;

    private final PontoService pontoService;

    @Autowired
    public EventoService(EventoRepository eventoRepository, LocalizacaoService localizacaoService, PontoService pontoService) {
        this.eventoRepository = eventoRepository;
        this.localizacaoService = localizacaoService;
        this.pontoService = pontoService;
    }

    @Transactional
    public Evento salvar(String pontoId, Evento evento) {
        if (pontoId.isEmpty()) {
            if (evento.getPonto() != null) {
                throw new EventoBadRequestException("Id do ponto deve ser passado utilizando variavel na url ex: ?pontoId=id");
            }
        } else {
            evento.setPonto(pontoService.obterPontoExistente(pontoId));
            evento.setLocalizacao(copiar(localizacaoService.obterLocalizacaoPeloIdPonto(pontoId), evento.getInicio()));
        }
        return this.eventoRepository.save(evento);
    }

    public Evento obterEventoExistente(String eventoId) {
        Optional<Evento> eventoOptional = eventoRepository.findById(eventoId);
        if (!eventoOptional.isPresent()) {
            throw new EventoBadRequestException("Evento não econtrado.");
        }
        return eventoOptional.get();
    }

    private NomeDiaSemana pegarDiaDaSemana(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
        switch (diaSemana) {
            case 1:
                return NomeDiaSemana.DOMINGO;
            case 2:
                return NomeDiaSemana.SEGUNDA;
            case 3:
                return NomeDiaSemana.TERCA;
            case 4:
                return NomeDiaSemana.QUARTA;
            case 5:
                return NomeDiaSemana.QUINTA;
            case 6:
                return NomeDiaSemana.SEXTA;
            default:
                return NomeDiaSemana.SABADO;
        }
    }

    private Localizacao copiar(List<Localizacao> localizacoes, Date data) {
        Localizacao localizacao = null;
        NomeDiaSemana nomeDiaSemana = pegarDiaDaSemana(data);
        boolean achou = false;
        int contador1 = 0;
        while (contador1 < localizacoes.size() && !achou) {
            int contador2 = 0;
            Localizacao l = localizacoes.get(contador1);
            while (contador2 < l.getDiasDaSemana().size() && !achou) {
                if (l.getDiasDaSemana().get(contador2).getNomeDiaSemana().equals(nomeDiaSemana)) {
                    localizacao = l;
                    achou = true;
                }
                contador2++;
            }
            contador1++;
        }
        if (!achou) {
            throw new EventoBadRequestException("Ponto não possui localização no dia que o evento ira ocorrer.");
        }
        return Localizacao.builder()
            .pais(localizacao.getPais())
            .estado(localizacao.getEstado())
            .cidade(localizacao.getCidade())
            .bairro(localizacao.getBairro())
            .numero(localizacao.getNumero())
            .rua(localizacao.getRua())
            .longitude(localizacao.getLongitude())
            .latitude(localizacao.getLatitude())
            .build();
    }
    
}
