import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { BienesService } from '../../services/bienesService';
import { Comprobante } from '../../models/comprobante';
import { TableColumn } from '../../models/table-column';
import { GenericTable } from '../generic-table/generic-table';

@Component({
  selector: 'app-entrega-bienes',
  imports: [FormsModule, GenericTable],
  templateUrl: './entrega-bienes.html',
  styleUrl: './entrega-bienes.scss',
})
export class EntregaBienes {
  bienesService = inject(BienesService);

  idComprobante = signal(2568);
  comprobantes = signal<Comprobante[]>([]);
  totalElements = signal(0);
  page = signal(0);
  size = signal(10);
  cargando = signal(false);
  error = signal('');

  columnas: TableColumn<Comprobante>[] = [
    { key: 'numeroComprobante', header: 'N° Comprobante' },
    { key: 'dspComprobante', header: 'Tipo Comprobante' },
    { key: 'fecha', header: 'Fecha' },
    { key: 'item', header: 'Ítem' },
    { key: 'dspItem', header: 'Bien' },
    { key: 'descripcion', header: 'Descripción' },
    { key: 'cantidad', header: 'Cantidad' },
    { key: 'inventario', header: 'Inventario' },
    { key: 'dspEstadoBien', header: 'Estado' },
    { key: 'precioUnitario', header: 'Precio Unit.' },
    { key: 'precioTotal', header: 'Precio Total' },
  ];

  consultar(): void {
    this.page.set(0);
    this.cargarComprobantes();
  }

  cambiarPagina(event: PageEvent): void {
    this.page.set(event.pageIndex);
    this.size.set(event.pageSize);
    this.cargarComprobantes();
  }

  cargarComprobantes(): void {
    this.cargando.set(true);
    this.error.set('');

    this.bienesService
      .obtenerComprobante(this.idComprobante(), this.page(), this.size())
      .subscribe({
        next: (response) => {
          this.comprobantes.set(response.content);
          this.totalElements.set(response.totalElements);
          this.cargando.set(false);
        },
        error: () => {
          this.error.set('Error al realizar la consulta.');
          this.cargando.set(false);
        },
      });
  }
}
