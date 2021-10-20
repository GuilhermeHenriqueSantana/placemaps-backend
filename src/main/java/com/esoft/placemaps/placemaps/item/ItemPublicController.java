package com.esoft.placemaps.placemaps.item;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/item")
public class ItemPublicController {

    private final ItemService itemService;

    @Autowired
    public ItemPublicController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<Page<Map<String, Object>>> pegarItensPeloDadoSemanal(Pageable pageable, @RequestParam String dadoSemanalId) {  
        return ResponseEntity.ok(itemService.pegarItensPeloDadoSemanal(pageable, dadoSemanalId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> obterItemPeloId(@PathVariable String id) {
        return ResponseEntity.ok(this.itemService.obterItemPeloId(id));
    }
}
