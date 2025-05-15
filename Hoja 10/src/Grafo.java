import java.util.*;

public class Grafo {
    private int[][] matriz;
    private String[] ciudades;
    private int numVertices;

    public Grafo(String[] ciudades) {
        this.ciudades = ciudades;
        this.numVertices = ciudades.length;
        this.matriz = new int[numVertices][numVertices];
        for (int[] fila : matriz) Arrays.fill(fila, Integer.MAX_VALUE / 2);
        for (int i = 0; i < numVertices; i++) matriz[i][i] = 0;
    }

    public void agregarArco(String origen, String destino, int tiempo) {
        int i = indiceCiudad(origen);
        int j = indiceCiudad(destino);
        if (i != -1 && j != -1) matriz[i][j] = tiempo;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public String[] getCiudades() {
        return ciudades;
    }

    private int indiceCiudad(String ciudad) {
        for (int i = 0; i < ciudades.length; i++) {
            if (ciudades[i].equals(ciudad)) return i;
        }
        return -1;
    }
}

