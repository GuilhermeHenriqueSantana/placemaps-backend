package com.esoft.placemaps.placemaps.item;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    
}
