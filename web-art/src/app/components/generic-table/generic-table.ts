import { Component, computed, input, output } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { TableColumn } from '../../models/table-column';

@Component({
  selector: 'app-generic-table',
  imports: [MatTableModule, MatPaginatorModule],
  templateUrl: './generic-table.html',
  styleUrl: './generic-table.scss',
})
export class GenericTable<T> {
  data = input.required<T[]>();
  columns = input.required<TableColumn<T>[]>();
  totalElements = input<number>(0);
  pageIndex = input<number>(0);
  pageSize = input<number>(10);
  pageSizeOptions = input<number[]>([5, 10, 25, 50]);

  displayedColumns = computed(() => this.columns().map((col) => col.key));

  pageChange = output<PageEvent>();

  onPageChange(event: PageEvent): void {
    this.pageChange.emit(event);
  }
}
