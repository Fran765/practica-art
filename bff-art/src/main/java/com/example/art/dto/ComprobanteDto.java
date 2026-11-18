package com.example.art.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record ComprobanteDto(
        Long idComprobanteCabecera,
        String comprobante,
        String dspComprobante,
        String fecha,
        Long numeroComprobante,
        Long depositoOrigen,
        String dspDepositoOrigen,
        Long responsableOrigen,
        String dspResponsableOrigen,
        Long depositoDestino,
        String dspDepositoDestino,
        Long responsableDestino,
        String dspResponsableDestino,
        String observaciones,
        String anulado,
        String causaAnulacion,
        String usuarioAnulacion,
        Long idComprobanteDetalle,
        Long item,
        String dspItem,
        BigDecimal cantidad,
        String inventario,
        String serie,
        String estadoBien,
        String dspEstadoBien,
        String descripcion,
        BigDecimal precioUnitario,
        BigDecimal precioTotal
) {
}
