import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("logistica.txt"));
        List<String> lineas = new ArrayList<>();
        Set<String> nombres = new LinkedHashSet<>();

        while ((br.ready())) {
            String linea = br.readLine();
            lineas.add(linea);
            String[] partes = linea.split(" ");
            nombres.add(partes[0]);
            nombres.add(partes[1]);
        }

        String[] ciudades = nombres.toArray(new String[0]);
        Grafo grafo = new Grafo(ciudades);

        for (String linea : lineas) {
            String[] partes = linea.split(" ");
            String origen = partes[0];
            String destino = partes[1];
            int tiempoNormal = Integer.parseInt(partes[2]);
            grafo.agregarArco(origen, destino, tiempoNormal);
        }

        int[][] caminos = new int[ciudades.length][ciudades.length];
        int[][] resultado = Floyd.calcular(grafo.getMatriz(), caminos);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Ruta más corta\n2. Salir");
            int opcion = sc.nextInt(); sc.nextLine();
            if (opcion == 1) {
                System.out.print("Origen: ");
                String origen = sc.nextLine();
                System.out.print("Destino: ");
                String destino = sc.nextLine();
                int i = Arrays.asList(ciudades).indexOf(origen);
                int j = Arrays.asList(ciudades).indexOf(destino);
                if (i == -1 || j == -1) {
                    System.out.println("Ciudad no encontrada.");
                } else {
                    System.out.println("Distancia: " + resultado[i][j]);
                    System.out.println("Ruta: " + Floyd.obtenerRuta(i, j, caminos, ciudades));
                }
            } else break;
        }
    }
}
