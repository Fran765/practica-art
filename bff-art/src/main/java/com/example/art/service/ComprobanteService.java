package com.example.art.service;

import com.example.art.client.MaterialesApiClient;
import com.example.art.dto.ComprobanteDto;
import com.example.art.dto.PaginatedResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ComprobanteService {

    private final MaterialesApiClient materialesApiClient;

    public ComprobanteService(MaterialesApiClient materialesApiClient) {
        this.materialesApiClient = materialesApiClient;
    }

    public PaginatedResponse<ComprobanteDto> getPaginatedByIdComprobante(Long idComprobante, int page, int size) {
        if (idComprobante == null) {
            throw new IllegalArgumentException("IdComprobante no debe ser nulo");
        }
        if (page < 0) {
            throw new IllegalArgumentException("El índice de página no debe ser menor que cero");
        }
        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("El tamaño de la página debe ser entre 1 y 100.");
        }

        List<ComprobanteDto> allItems = materialesApiClient.fetchByIdComprobante(idComprobante);
        List<ComprobanteDto> safeItems = allItems != null ? allItems : Collections.emptyList();

        int totalElements = safeItems.size();
        int totalPages = totalElements == 0 ? 0 : (int) Math.ceil((double) totalElements / size);

        long fromIndexLong = (long) page * size;
        if (fromIndexLong >= totalElements) {
            return new PaginatedResponse<>(Collections.emptyList(), page, size, totalElements, totalPages);
        }

        int fromIndex = (int) fromIndexLong;
        int toIndex = (int) Math.min(fromIndexLong + size, totalElements);
        List<ComprobanteDto> pageContent = List.copyOf(safeItems.subList(fromIndex, toIndex));

        return new PaginatedResponse<>(pageContent, page, size, totalElements, totalPages);
    }
}
