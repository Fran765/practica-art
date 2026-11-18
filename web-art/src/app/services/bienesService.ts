import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Comprobante } from '../models/comprobante';
import { PaginatedResponse } from '../models/paginated-response';

@Service()
export class BienesService {
  readonly http = inject(HttpClient);
  readonly baseUrl = environment.apiUrl;
  readonly sufixUrl = 'entregas-bienes/comprobante';

  obtenerComprobante(id: number, page = 0, size = 10): Observable<PaginatedResponse<Comprobante>> {
    return this.http.get<PaginatedResponse<Comprobante>>(`${this.baseUrl}${this.sufixUrl}/${id}`, {
      params: { page, size },
    });
  }
}
