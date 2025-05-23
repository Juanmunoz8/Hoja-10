import java.util.*;

public class Floyd {
    public static int[][] calcular(int[][] grafo, int[][] caminos) {
        int n = grafo.length;
        int[][] dist = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                dist[i][j] = grafo[i][j];
                if (i != j && grafo[i][j] < Integer.MAX_VALUE / 2)
                    caminos[i][j] = i;
                else
                    caminos[i][j] = -1;
            }

        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        caminos[i][j] = caminos[k][j];
                    }

        return dist;
    }

    public static String obtenerRuta(int i, int j, int[][] caminos, String[] ciudades) {
        if (i == j) return ciudades[i];
        if (caminos[i][j] == -1) return "No hay ruta";

        Stack<String> ruta = new Stack<>();
        while (j != i) {
            ruta.push(ciudades[j]);
            j = caminos[i][j];
        }
        ruta.push(ciudades[i]);

        StringBuilder sb = new StringBuilder();
        while (!ruta.isEmpty()) sb.append(ruta.pop()).append(" -> ");
        return sb.substring(0, sb.length() - 4);
    }

    public static String centroDelGrafo(int[][] dist, String[] ciudades) {
        int n = dist.length;
        int[] excentricidades = new int[n];
        Arrays.fill(excentricidades, 0);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                excentricidades[i] = Math.max(excentricidades[i], dist[i][j]);

        int min = Integer.MAX_VALUE;
        int centro = -1;
        for (int i = 0; i < n; i++) {
            if (excentricidades[i] < min) {
                min = excentricidades[i];
                centro = i;
            }
        }
        return ciudades[centro];
    }
}

