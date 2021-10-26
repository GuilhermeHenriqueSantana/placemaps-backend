package com.esoft.placemaps.placemaps.item;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanal;
import com.esoft.placemaps.placemaps.dadosemanal.DadoSemanalRepository;
import com.esoft.placemaps.placemaps.item.dto.ItemAtualizarDTO;
import com.esoft.placemaps.placemaps.item.dto.ItemFormDTO;
import com.esoft.placemaps.placemaps.item.exception.ItemBadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Optional<DadoSemanal> dadoSemanalOptional = dadoSemanalRepository.findById(itemFormDTO.getDadoSemanalId());
        if (dadoSemanalOptional.isPresent()) {
            item.setDadoSemanal(dadoSemanalOptional.get());
            return itemRepository.save(item);
        }
        throw new ItemBadRequestException("DadoSemanal não encontrado.");
    }

    @Transactional
    public Item atualizar(String id, ItemAtualizarDTO itemAtualizarDTO) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (!itemOptional.isPresent()) {
            throw new ItemBadRequestException("Item não encontrado.");
        }
        return itemRepository.save(itemAtualizarDTO.atualizarItem(itemOptional.get()));
    }

    @Transactional(readOnly = true)
    public Page<Map<String, Object>> pegarItensPeloDadoSemanal(Pageable pageable, String dadoSemanalId) {
        return this.itemRepository.findByDadoSemanalId(pageable, dadoSemanalId);
    }

    @Transactional(readOnly = true)
    public Item obterItemPeloId(String id) {
        return this.itemRepository.findById(id).orElse(null);
    }

    public void deletarItem(String id) {
        try {
            this.itemRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
    }

    @Transactional
    public void deletarItens(String dadoSemanalId) {
        this.itemRepository.removeByDadoSemanalId(dadoSemanalId);
    }
}
