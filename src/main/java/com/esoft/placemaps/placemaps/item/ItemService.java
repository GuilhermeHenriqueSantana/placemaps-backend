package com.esoft.placemaps.placemaps.item;

import java.util.Optional;

import javax.transaction.Transactional;

import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanal;
import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanalRepository;
import com.esoft.placemaps.placemaps.item.dto.ItemFormDTO;
import com.esoft.placemaps.placemaps.item.exception.ItemBadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final DadoSemanalRepository dadoSemanalRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, DadoSemanalRepository dadoSemanalRepository) {
        this.itemRepository = itemRepository;
        this.dadoSemanalRepository = dadoSemanalRepository;
    }

    @Transactional
    public Item salvar(ItemFormDTO itemFormDTO) {
        Item item = itemFormDTO.gerarItem();
        System.out.println(itemFormDTO.getDadoSemanalId());
        Optional<DadoSemanal> dadoSemanalOptional = dadoSemanalRepository.findById(itemFormDTO.getDadoSemanalId());
        if (dadoSemanalOptional.isPresent()) {
            item.setDadoSemanal(dadoSemanalOptional.get());
            return itemRepository.save(item);
        }
        throw new ItemBadRequestException("DadoSemanal não encontrado.");
    }
    
}
