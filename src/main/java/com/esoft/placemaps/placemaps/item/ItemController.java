package com.esoft.placemaps.placemaps.item;

import com.esoft.placemaps.placemaps.item.dto.ItemAtualizarDTO;
import com.esoft.placemaps.placemaps.item.dto.ItemFormDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @PostMapping
    public ResponseEntity<Item> salvar(@RequestBody ItemFormDTO itemFormDTO) {
        return ResponseEntity.ok(itemService.salvar(itemFormDTO));
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @PutMapping("/{id}")
    public ResponseEntity<Item> atualizar(@RequestBody ItemAtualizarDTO itemAtualizarDTO, @PathVariable String id) {
        return ResponseEntity.ok(itemService.atualizar(id, itemAtualizarDTO));
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    @DeleteMapping("/{id}") 
    public ResponseEntity deletarItem(@PathVariable String id) {
        itemService.deletarItem(id);
        return ResponseEntity.accepted().build();
    }
}
