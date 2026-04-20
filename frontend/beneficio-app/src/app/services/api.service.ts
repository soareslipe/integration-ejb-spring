import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficio } from '../models/beneficio';
import { BeneficioCreate } from '../models/beneficioCreate';


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private api = 'http://localhost:8081/api/v1/beneficios';

  constructor(private http: HttpClient) {}

  // =========================
  // LISTAR TODOS
  // =========================
  listar(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.api);
  }

  // =========================
  // BUSCAR POR ID
  // =========================
  buscarPorId(id: number): Observable<Beneficio> {
    return this.http.get<Beneficio>(`${this.api}/${id}`);
  }

  // =========================
  // CRIAR
  // =========================
  criar(beneficio: BeneficioCreate): Observable<Beneficio> {
    return this.http.post<Beneficio>(this.api, beneficio);
  }

  // =========================
  // ATUALIZAR
  // =========================
  atualizar(id: number, beneficio: Beneficio): Observable<Beneficio> {
    return this.http.put<Beneficio>(`${this.api}/${id}`, beneficio);
  }

  // =========================
  // DELETAR
  // =========================
  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  // =========================
  // TRANSFERÊNCIA
  // =========================
  transferir(fromId: number, toId: number, amount: number): Observable<any> {

    const params = new HttpParams()
      .set('fromId', fromId)
      .set('toId', toId)
      .set('amount', amount);

    return this.http.post(`${this.api}/transfer`, null, { params });
  }
}