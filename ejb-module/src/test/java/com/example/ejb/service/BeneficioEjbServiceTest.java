package com.example.ejb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ejb.model.Beneficio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
class BeneficioEjbServiceTest {

    @InjectMocks
    private BeneficioEjbService service;

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Beneficio> query;

    @Test
    void deveCriarBeneficio() {
        Beneficio b = new Beneficio();
        b.setNome("Teste");

        service.create(b);

        verify(em).persist(b);
    }

    @Test
    void deveBuscarPorId() {
        Beneficio mock = new Beneficio();
        mock.setId(1L);

        when(em.find(Beneficio.class, 1L)).thenReturn(mock);

        Beneficio result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(em).find(Beneficio.class, 1L);
    }

    @Test
    void deveListarTodos() {

        when(em.createQuery("SELECT b FROM Beneficio b", Beneficio.class))
            .thenReturn(query);

        when(query.getResultList())
            .thenReturn(List.of(new Beneficio(), new Beneficio()));

        List<Beneficio> result = service.findAll();

        assertEquals(2, result.size());

        verify(em).createQuery("SELECT b FROM Beneficio b", Beneficio.class);
        verify(query).getResultList();
    }

    @Test
    void deveAtualizarBeneficio() {
        Beneficio b = new Beneficio();
        b.setId(1L);

        when(em.merge(b)).thenReturn(b);

        Beneficio result = service.update(b);

        assertNotNull(result);
        verify(em).merge(b);
    }

    @Test
    void deveDeletarBeneficio() {
        Beneficio b = new Beneficio();
        b.setId(1L);

        when(em.find(Beneficio.class, 1L)).thenReturn(b);

        service.delete(1L);

        verify(em).remove(b);
    }

    @Test
    void deveLancarErroQuandoNaoEncontrarDelete() {
        when(em.find(Beneficio.class, 99L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.delete(99L));

        assertEquals("Registro não encontrado para exclusão.", ex.getMessage());
    }

    @Test
    void deveTransferirValores() {
        Beneficio from = new Beneficio();
        from.setId(1L);
        from.setValor(new BigDecimal("1000"));
        from.setAtivo(true);

        Beneficio to = new Beneficio();
        to.setId(2L);
        to.setValor(new BigDecimal("500"));
        to.setAtivo(true);

        when(em.find(Beneficio.class, 1L, LockModeType.PESSIMISTIC_WRITE)).thenReturn(from);
        when(em.find(Beneficio.class, 2L, LockModeType.PESSIMISTIC_WRITE)).thenReturn(to);

        service.transfer(1L, 2L, new BigDecimal("200"));

        assertEquals(new BigDecimal("800"), from.getValor());
        assertEquals(new BigDecimal("700"), to.getValor());

        verify(em).flush();
    }

    @Test
    void deveFalharPorSaldoInsuficiente() {
        Beneficio from = new Beneficio();
        from.setId(1L);
        from.setValor(new BigDecimal("100"));
        from.setAtivo(true);

        Beneficio to = new Beneficio();
        to.setId(2L);
        to.setValor(new BigDecimal("500"));
        to.setAtivo(true);

        when(em.find(Beneficio.class, 1L, LockModeType.PESSIMISTIC_WRITE)).thenReturn(from);
        when(em.find(Beneficio.class, 2L, LockModeType.PESSIMISTIC_WRITE)).thenReturn(to);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.transfer(1L, 2L, new BigDecimal("200")));

        assertTrue(ex.getMessage().contains("Saldo insuficiente"));
    }
}