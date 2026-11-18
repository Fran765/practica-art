import { signal, Signal } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Observable, Subscription } from 'rxjs';
import { PaginatedResponse } from '../models/paginated-response';

export interface PaginatorState<T> {
  readonly items: Signal<T[]>;
  readonly totalElements: Signal<number>;
  readonly page: Signal<number>;
  readonly size: Signal<number>;
  readonly cargando: Signal<boolean>;
  readonly error: Signal<string>;
  consultar(): void;
  cambiarPagina(event: PageEvent): void;
}

export function createPaginator<T>(
  fetchFn: (page: number, size: number) => Observable<PaginatedResponse<T>>,
  initialSize = 10
): PaginatorState<T> {
  const items = signal<T[]>([]);
  const totalElements = signal(0);
  const page = signal(0);
  const size = signal(initialSize);
  const cargando = signal(false);
  const error = signal('');
  let currentSub: Subscription | null = null;

  const cargar = (): void => {
    currentSub?.unsubscribe();
    cargando.set(true);
    error.set('');

    currentSub = fetchFn(page(), size()).subscribe({
      next: (response) => {
        items.set(response.content);
        totalElements.set(response.totalElements);
        cargando.set(false);
      },
      error: () => {
        error.set('Error al realizar la consulta.');
        cargando.set(false);
      },
    });
  };

  return {
    items: items.asReadonly(),
    totalElements: totalElements.asReadonly(),
    page: page.asReadonly(),
    size: size.asReadonly(),
    cargando: cargando.asReadonly(),
    error: error.asReadonly(),
    consultar(): void {
      page.set(0);
      cargar();
    },
    cambiarPagina(event: PageEvent): void {
      page.set(event.pageIndex);
      size.set(event.pageSize);
      cargar();
    },
  };
}
