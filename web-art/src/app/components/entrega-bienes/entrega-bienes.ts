import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BienesService } from '../../services/bienesService';
import { Comprobante } from '../../models/comprobante';
import { TableColumn } from '../../models/table-column';
import { GenericTable } from '../generic-table/generic-table';
import { createPaginator } from '../../utils/create-paginator';

@Component({
  selector: 'app-entrega-bienes',
  imports: [FormsModule, GenericTable],
  templateUrl: './entrega-bienes.html',
  styleUrl: './entrega-bienes.scss',
})
export class EntregaBienes {
  private readonly bienesService = inject(BienesService);

  idComprobante = signal(2568);

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

  paginador = createPaginator((page, size) =>
    this.bienesService.obtenerComprobante(this.idComprobante(), page, size)
  );
}
