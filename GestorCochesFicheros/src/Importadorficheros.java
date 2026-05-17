
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class responsible for reading the CSV file and creating Coche objects.
 */
public class Importadorficheros {

    /**
     * Reads the files and returns a list of cars.
     *
     * @param nombreArchivo The name of the file to read
     * @return An ArrayList containing all the cars from the file
     */
    public ArrayList<Coche> importarCSV(String nombreArchivo) {
        ArrayList<Coche> listaCoches = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        CalculadorPrecio calculador = new CalculadorPrecio();

        try {
            fr = new FileReader(nombreArchivo);
            br = new BufferedReader(fr);

            String linea = br.readLine(); // Read header and ignore it
            linea = br.readLine(); // Read first line of actual data

            while (linea != null) {
                String[] datos = linea.split(",", -1);

                String marca = datos[0];
                String modelo = datos[1];
                int anio = Integer.parseInt(datos[2]);
                int kilometros = Integer.parseInt(datos[3]);
                double precioBase = Double.parseDouble(datos[4]);

                double descuento = 0.0;
                if (!datos[5].isEmpty()) {
                    descuento = Double.parseDouble(datos[5]);
                }

                String color = datos[6];
                String combustible = datos[7];
                String transmision = datos[8];
                String estado = datos[9];

                Coche coche = new Coche(marca, modelo, anio, kilometros, precioBase, descuento, color, combustible, transmision, estado);

                // Calculate price using our single-responsibility class
                calculador.calcularPrecioFinal(coche);

                listaCoches.add(coche);
                linea = br.readLine(); // Read next line
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo: " + e.getMessage());
            }
        }

        return listaCoches;
    }
}
