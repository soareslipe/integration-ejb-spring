import { TestBed } from '@angular/core/testing';
import { provideAnimations } from '@angular/platform-browser/animations';
import { of } from 'rxjs';
import { AppComponent } from './app.component';
import { ApiService } from './services/api.service';

const beneficioMock = {
  id: 1,
  nome: 'Teste',
  descricao: '',
  valor: 0,
  ativo: true,
  version: 0
};

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent],
      providers: [
        provideAnimations(),
        {
          provide: ApiService,
          useValue: {
            listar: () => of([]),
            buscarPorId: () => of(beneficioMock),
            criar: (b: typeof beneficioMock) => of(b),
            atualizar: (_id: number, b: typeof beneficioMock) => of(b),
            deletar: () => of(void 0),
            transferir: () => of({})
          }
        }
      ]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render the form', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('form')).toBeTruthy();
  });
});
