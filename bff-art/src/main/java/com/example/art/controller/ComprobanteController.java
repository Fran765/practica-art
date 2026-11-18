package com.example.art.controller;

import com.example.art.dto.ComprobanteDto;
import com.example.art.dto.PaginatedResponse;
import com.example.art.service.ComprobanteService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/entregas-bienes")
public class ComprobanteController {

    private final ComprobanteService comprobanteService;

    public ComprobanteController(ComprobanteService comprobanteService) {
        this.comprobanteService = comprobanteService;
    }

    @GetMapping("/comprobante/{idComprobante}")
    public ResponseEntity<PaginatedResponse<ComprobanteDto>> getByIdComprobante(
            @PathVariable Long idComprobante,
            @RequestParam(name = "page", defaultValue = "0") @Min(value = 0) int page,
            @RequestParam(name = "size", defaultValue = "10") @Min(value = 1) @Max(value = 100) int size
    ) {
        return ResponseEntity.ok(comprobanteService.getPaginatedByIdComprobante(idComprobante, page, size));
    }
}
