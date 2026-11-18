package com.example.art.client;

import com.example.art.config.CacheConfig;
import com.example.art.config.MaterialesApiProperties;
import com.example.art.dto.ComprobanteDto;
import com.example.art.dto.ComprobanteRequest;
import com.example.art.exception.UpstreamServiceException;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
public class MaterialesApiClient {

    private static final ParameterizedTypeReference<List<ComprobanteDto>> RESPONSE_TYPE =
            new ParameterizedTypeReference<>() {};

    private final RestClient materialesRestClient;
    private final MaterialesApiProperties properties;

    public MaterialesApiClient(RestClient materialesRestClient, MaterialesApiProperties properties) {
        this.materialesRestClient = materialesRestClient;
        this.properties = properties;
    }

    @Cacheable(cacheNames = CacheConfig.COMPROBANTES_CACHE, key = "#idComprobante")
    public List<ComprobanteDto> fetchByIdComprobante(Long idComprobante) {
        try {
            List<ComprobanteDto> response = materialesRestClient.post()
                    .uri(properties.url())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(new ComprobanteRequest(idComprobante))
                    .retrieve()
                    .body(RESPONSE_TYPE);

            if (response == null || response.isEmpty()) {
                return Collections.emptyList();
            }

            return List.copyOf(response);
        } catch (RestClientException ex) {
            throw new UpstreamServiceException("No se pudieron recuperar los elementos del comprobante desde el webservice.", ex);
        }
    }
}
