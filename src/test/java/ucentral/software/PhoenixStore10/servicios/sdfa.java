package ucentral.software.PhoenixStore10.servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.repositorios.RepoProducto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioProductoTest {

    @Mock
    private RepoProducto repoProducto;

    @InjectMocks
    private ServicioProducto servicioProducto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodos_DeberiaRetornarListaDeProductos() {
        Productos prod1 = new Productos();
        Productos prod2 = new Productos();
        List<Productos> listaMock = Arrays.asList(prod1, prod2);

        when(repoProducto.findAll()).thenReturn(listaMock);

        List<Productos> resultado = servicioProducto.obtenerTodos();
        assertEquals(2, resultado.size());
    }

    @Test
    void obtenerTodosLosProductos_DeberiaRetornarListaDeProductos() {
        Productos prod1 = new Productos();
        Productos prod2 = new Productos();
        List<Productos> listaMock = Arrays.asList(prod1, prod2);

        when(repoProducto.findAll()).thenReturn(listaMock);

        List<Productos> resultado = servicioProducto.obtenerTodosLosProductos();
        assertEquals(2, resultado.size());
    }

    @Test
    void buscarPorNombre_DeberiaRetornarProductosQueCoinciden() {
        String query = "Camisa";
        Productos prod1 = new Productos();
        prod1.setPronombre("Camisa Azul");
        Productos prod2 = new Productos();
        prod2.setPronombre("Camisa Negra");

        List<Productos> resultadosEsperados = Arrays.asList(prod1, prod2);
        when(repoProducto.findByPronombreContainingIgnoreCase(query)).thenReturn(resultadosEsperados);

        List<Productos> resultado = servicioProducto.buscarPorNombre(query);
        assertEquals(2, resultado.size());
    }

    @Test
    void obtenerPorId_DeberiaRetornarProductoCorrecto() {
        Productos producto = new Productos();
        producto.setProid(1L);

        when(repoProducto.findById(1L)).thenReturn(Optional.of(producto));

        Productos resultado = servicioProducto.obtenerPorId(1);
        assertEquals(1L, resultado.getProid());
    }

    @Test
    void obtenerProductoPorId_DeberiaRetornarProductoCorrecto() {
        Productos producto = new Productos();
        producto.setProid(99L);

        when(repoProducto.findById(99L)).thenReturn(Optional.of(producto));

        Productos resultado = servicioProducto.obtenerProductoPorId(99L);
        assertEquals(99L, resultado.getProid());
    }

    @Test
    void obtenerProductoPorId_ProductoNoExiste_LanzaExcepcion() {
        when(repoProducto.findById(999L)).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class, () -> {
            servicioProducto.obtenerProductoPorId(999L);
        });
    }
}
