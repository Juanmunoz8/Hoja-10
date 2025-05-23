import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FloydTest {

    @Test
    public void testCalculoRutaSimple() {
        int[][] matriz = {
            {0, 3, Integer.MAX_VALUE / 2},
            {Integer.MAX_VALUE / 2, 0, 1},
            {Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2, 0}
        };
        int[][] caminos = new int[3][3];
        int[][] res = Floyd.calcular(matriz, caminos);
        assertEquals(4, res[0][2]);
    }

    @Test
    public void testRutaRecuperada() {
        String[] ciudades = {"A", "B", "C"};
        int[][] caminos = {
            {-1, 0, 1},
            {-1, -1, 1},
            {-1, -1, -1}
        };
        String ruta = Floyd.obtenerRuta(0, 2, caminos, ciudades);
        assertEquals("A -> B -> C", ruta);
    }

    @Test
    public void testCentroDelGrafo() {
        String[] ciudades = {"A", "B", "C"};
        int[][] distancias = {
            {0, 2, 4},
            {2, 0, 2},
            {4, 2, 0}
        };
        String centro = Floyd.centroDelGrafo(distancias, ciudades);
        assertEquals("B", centro);
    }
}

