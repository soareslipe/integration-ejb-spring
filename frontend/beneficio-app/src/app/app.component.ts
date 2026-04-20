import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { finalize } from 'rxjs/operators';
import { Beneficio } from './models/beneficio';
import { BeneficioCreate } from './models/beneficioCreate';
import { ApiService } from './services/api.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatSlideToggleModule,
    MatDividerModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  beneficios: Beneficio[] = [];
  form!: FormGroup;

  displayedColumns: string[] = [
    'id',
    'nome',
    'descricao',
    'valor',
    'ativo',
    'acoes',
  ];

  loading = false;

  transferFromId = 1;
  transferToId = 2;
  transferAmount = 100;

  constructor(
    private api: ApiService,
    private fb: FormBuilder,
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null as number | null],
      nome: ['', Validators.required],
      descricao: [''],
      valor: [0, [Validators.required, Validators.min(0)]],
      ativo: [true],
      version: [null as number | null],
    });

    this.listar();
  }

  listar(): void {
    this.loading = true;
    this.api
      .listar()
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (data) => (this.beneficios = data ?? []),
        error: () => alert('Erro ao carregar a lista.'),
      });
  }

  selecionar(b: Beneficio): void {
    this.form.patchValue(b);
  }

 

  criar(): void {
  if (this.form.invalid) {
    this.form.markAllAsTouched();
    return;
  }

  const v = this.form.getRawValue();

  const payload: BeneficioCreate = {
    nome: v.nome,
    descricao: v.descricao ?? '',
    valor: v.valor,
    ativo: v.ativo,
  };

  this.api.criar(payload).subscribe({
    next: () => {
      this.listar();
      this.novo();
    },
    error: () => alert('Erro ao criar.')
  });
}

atualizar(): void {
  if (this.form.invalid) {
    this.form.markAllAsTouched();
    return;
  }

  const v = this.form.getRawValue();

  const id = v.id;
  if (!id) return;

  const payload: Beneficio = {
    id,
    nome: v.nome,
    descricao: v.descricao ?? '',
    valor: v.valor,
    ativo: v.ativo,
    version: v.version ?? 0,
  };

  this.api.atualizar(id, payload).subscribe({
    next: () => {
      this.listar();
      this.novo();
    },
    error: (err) => {
      if (err.status === 409) {
        alert('Conflito de versão.');
      } else {
        alert('Erro ao atualizar.');
      }
    }
  });
}

  deletar(id: number): void {
    if (!confirm('Excluir este benefício?')) {
      return;
    }
    this.api.deletar(id).subscribe({
      next: () => {
        this.listar();
        if (this.form.get('id')?.value === id) {
          this.novo();
        }
      },
      error: () => alert('Erro ao excluir.'),
    });
  }

  transferir(): void {
    this.api
      .transferir(this.transferFromId, this.transferToId, this.transferAmount)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.listar();
        },
        error: (err) => console.error(err),
      });
  }

  novo(): void {
    this.form.reset({
      id: null,
      nome: '',
      descricao: '',
      valor: 0,
      ativo: true,
      version: null,
    });
  }
}
