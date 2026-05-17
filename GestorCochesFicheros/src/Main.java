
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main application class. Provides a console menu to test the system and
 * connects the different classes.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Importadorficheros importador = new Importadorficheros();
        GestorArchivoBinario gestorBinario = new GestorArchivoBinario();

        // This is the master list where all cars will be stored
        ArrayList<Coche> listaPrincipal = new ArrayList<>();
        int opcion;

        do {
            System.out.println("\n----- Menu -----:");
            System.out.println("1. Importar fichero y calcular precios");
            System.out.println("2. Guardar colección de coches ");
            System.out.println("3. Cargar colección de coches");
            System.out.println("4. Guardar coches individuales");
            System.out.println("5. Cargar coches individuales");
            System.out.println("6. Salir");
            System.out.print("Introduce una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    listaPrincipal = importador.importarCSV("coches.csv");
                    if (!listaPrincipal.isEmpty()) {
                        System.out.println("Datos importados y precios calculados.");
                    } else {
                        System.out.println("Error al importar datos o el archivo está vacío.");
                    }
                    break;
                case 2:
                    System.out.print("Introduce la marca: ");
                    String marca = scanner.nextLine().trim();
                    System.out.print("Introduce el máximo de kilómetros: ");
                    int maxKm = Integer.parseInt(scanner.nextLine());
                    gestorBinario.guardarColeccion(listaPrincipal, marca, maxKm, "coleccion.dat");
                    break;
                case 3:
                    // Both methods use the exact same loading logic in binary
                    gestorBinario.cargarCoches("coleccion.dat");
                    break;
                case 4:
                    System.out.print("Introduce el tipo de combustible: ");
                    String combustible = scanner.nextLine().trim();
                    System.out.print("Introduce el precio máximo: ");
                    double maxPrecio = Double.parseDouble(scanner.nextLine());
                    gestorBinario.guardarIndividual(listaPrincipal, combustible, maxPrecio, "individuales.dat");
                    break;
                case 5:
                    gestorBinario.cargarCoches("individuales.dat");
                    break;
                case 6:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);

        scanner.close();
    }
}
