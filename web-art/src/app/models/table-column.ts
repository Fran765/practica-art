export interface TableColumn<T> {
  key: Extract<keyof T, string>;
  header: string;
}
