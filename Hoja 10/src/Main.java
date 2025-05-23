import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\davcf\\OneDrive\\Documentos\\hdt10\\Hoja-10\\Hoja 10\\src\\Logistica.txt"));
        List<String> lineas = new ArrayList<>();
        Set<String> nombres = new LinkedHashSet<>();

        while (br.ready()) {
            String linea = br.readLine();
            lineas.add(linea);
            String[] partes = linea.split(" ");
            nombres.add(partes[0]);
            nombres.add(partes[1]);
        }

        String[] ciudades = nombres.toArray(new String[0]);
        Map<String, int[][]> mapasPorClima = new HashMap<>();
        String[] climas = {"normal", "lluvia", "nieve", "tormenta"};

        for (String clima : climas)
            mapasPorClima.put(clima, new int[ciudades.length][ciudades.length]);

        for (String clima : climas) {
            int[][] matriz = mapasPorClima.get(clima);
            for (int[] fila : matriz) Arrays.fill(fila, Integer.MAX_VALUE / 2);
            for (int i = 0; i < ciudades.length; i++) matriz[i][i] = 0;
        }

        for (String linea : lineas) {
            String[] partes = linea.split(" ");
            String origen = partes[0];
            String destino = partes[1];
            for (int i = 0; i < 4; i++) {
                int tiempo = Integer.parseInt(partes[2 + i]);
                mapasPorClima.get(climas[i])[Arrays.asList(ciudades).indexOf(origen)][Arrays.asList(ciudades).indexOf(destino)] = tiempo;
            }
        }

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Ruta más corta");
            System.out.println("2. Mostrar centro del grafo");
            System.out.println("3. Salir");
            System.out.println("4. Ver ciudades y conexiones");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {
                System.out.print("Clima (normal, lluvia, nieve, tormenta): ");
                String clima = sc.nextLine().toLowerCase();
                if (!mapasPorClima.containsKey(clima)) {
                    System.out.println("Clima inválido.");
                    continue;
                }

                System.out.print("Ciudad origen: ");
                String origen = sc.nextLine();
                System.out.print("Ciudad destino: ");
                String destino = sc.nextLine();

                int i = Arrays.asList(ciudades).indexOf(origen);
                int j = Arrays.asList(ciudades).indexOf(destino);
                if (i == -1 || j == -1) {
                    System.out.println("Ciudad no encontrada.");
                    continue;
                }

                int[][] caminos = new int[ciudades.length][ciudades.length];
                int[][] resultado = Floyd.calcular(mapasPorClima.get(clima), caminos);

                System.out.println("Distancia más corta: " + resultado[i][j]);
                System.out.println("Ruta: " + Floyd.obtenerRuta(i, j, caminos, ciudades));
            } else if (opcion == 2) {
                System.out.print("Clima para calcular el centro: ");
                String clima = sc.nextLine().toLowerCase();
                if (!mapasPorClima.containsKey(clima)) {
                    System.out.println("Clima inválido.");
                    continue;
                }

                int[][] caminos = new int[ciudades.length][ciudades.length];
                int[][] resultado = Floyd.calcular(mapasPorClima.get(clima), caminos);
                String centro = Floyd.centroDelGrafo(resultado, ciudades);
                System.out.println("Centro del grafo: " + centro);
            } else if (opcion == 3) {
                break;
            } else if (opcion == 4) {
                System.out.println("\n--- Ciudades y conexiones disponibles ---");
                for (int i = 0; i < ciudades.length; i++) {
                    String origen = ciudades[i];
                    System.out.println("\nDesde " + origen + ":");
                    for (int j = 0; j < ciudades.length; j++) {
                        if (i == j) continue;
                        boolean tieneConexion = false;
                        StringBuilder info = new StringBuilder();
                        for (String clima : climas) {
                            int tiempo = mapasPorClima.get(clima)[i][j];
                            if (tiempo < Integer.MAX_VALUE / 2) {
                                if (!tieneConexion) {
                                    info.append("  Hacia ").append(ciudades[j]).append(":\n");
                                    tieneConexion = true;
                                }
                                info.append("    ").append(clima).append(": ").append(tiempo).append("h\n");
                            }
                        }
                        if (tieneConexion) System.out.print(info);
                    }
                }
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }
}

