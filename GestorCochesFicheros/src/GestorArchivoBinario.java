
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class responsible for saving and loading cars into binary files. Uses
 * DataOutputStream and DataInputStream to handle data field by field.
 */
public class GestorArchivoBinario {

    /**
     * Filters cars by brand and max km, sorts them by km, and saves them to a
     * binary file.
     *
     * @param listaCoches The full list of cars
     * @param marca The brand to filter by
     * @param maxKm The maximum kilometers allowed
     * @param nombreArchivo The name of the output binary file
     */
    public void guardarColeccion(ArrayList<Coche> listaCoches, String marca, int maxKm, String nombreArchivo) {
        FileOutputStream fos = null;
        DataOutputStream salida = null;

        ArrayList<Coche> filtrados = new ArrayList<>();
        for (int i = 0; i < listaCoches.size(); i++) {
            Coche c = listaCoches.get(i);
            if (c.getMarca().equalsIgnoreCase(marca) && c.getKilometros() < maxKm) {
                filtrados.add(c);
            }
        }

        // Bubble sort by kilometers
        for (int i = 0; i < filtrados.size() - 1; i++) {
            for (int j = 0; j < filtrados.size() - i - 1; j++) {
                if (filtrados.get(j).getKilometros() > filtrados.get(j + 1).getKilometros()) {
                    Coche temp = filtrados.get(j);
                    filtrados.set(j, filtrados.get(j + 1));
                    filtrados.set(j + 1, temp);
                }
            }
        }

        try {
            fos = new FileOutputStream(nombreArchivo);
            salida = new DataOutputStream(fos);

            for (int i = 0; i < filtrados.size(); i++) {
                Coche c = filtrados.get(i);
                salida.writeUTF(c.getMarca());
                salida.writeUTF(c.getModelo());
                salida.writeInt(c.getAnio());
                salida.writeInt(c.getKilometros());
                salida.writeDouble(c.getPrecioBase());
                salida.writeDouble(c.getDescuento());
                salida.writeUTF(c.getColor());
                salida.writeUTF(c.getCombustible());
                salida.writeUTF(c.getTransmision());
                salida.writeUTF(c.getEstado());
                salida.writeDouble(c.getPrecioFinal());
            }
            System.out.println("Colección guardada exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        } finally {
            try {
                if (salida != null) {
                    salida.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar archivo: " + e.getMessage());
            }
        }
    }

    /**
     * Loads cars from a binary file and prints them to the console. Stops when
     * it reaches EOFException.
     *
     * @param nombreArchivo The binary file to read from
     */
    public void cargarCoches(String nombreArchivo) {
        FileInputStream fis = null;
        DataInputStream entrada = null;

        try {
            fis = new FileInputStream(nombreArchivo);
            entrada = new DataInputStream(fis);

            while (true) {
                String marca = entrada.readUTF();
                String modelo = entrada.readUTF();
                int anio = entrada.readInt();
                int kilometros = entrada.readInt();
                double precioBase = entrada.readDouble();
                double descuento = entrada.readDouble();
                String color = entrada.readUTF();
                String combustible = entrada.readUTF();
                String transmision = entrada.readUTF();
                String estado = entrada.readUTF();
                double precioFinal = entrada.readDouble();

                Coche c = new Coche(marca, modelo, anio, kilometros, precioBase, descuento, color, combustible, transmision, estado);
                c.setPrecioFinal(precioFinal);

                System.out.println(c.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (EOFException e) {
            System.out.println("Fin del archivo alcanzado. Carga completada.");
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        } finally {
            try {
                if (entrada != null) {
                    entrada.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar archivo: " + e.getMessage());
            }
        }
    }

    /**
     * Filters cars by fuel type and max price, sorts them by final price, and
     * saves them.
     *
     * @param listaCoches The full list of cars
     * @param combustible The fuel type to filter by
     * @param maxPrecio The maximum final price allowed
     * @param nombreArchivo The name of the output binary file
     */
    public void guardarIndividual(ArrayList<Coche> listaCoches, String combustible, double maxPrecio, String nombreArchivo) {
        FileOutputStream fos = null;
        DataOutputStream salida = null;

        ArrayList<Coche> filtrados = new ArrayList<>();
        for (int i = 0; i < listaCoches.size(); i++) {
            Coche c = listaCoches.get(i);
            if (c.getCombustible().equalsIgnoreCase(combustible) && c.getPrecioFinal() < maxPrecio) {
                filtrados.add(c);
            }
        }

        // Bubble sort by final price
        for (int i = 0; i < filtrados.size() - 1; i++) {
            for (int j = 0; j < filtrados.size() - i - 1; j++) {
                if (filtrados.get(j).getPrecioFinal() > filtrados.get(j + 1).getPrecioFinal()) {
                    Coche temp = filtrados.get(j);
                    filtrados.set(j, filtrados.get(j + 1));
                    filtrados.set(j + 1, temp);
                }
            }
        }

        try {
            fos = new FileOutputStream(nombreArchivo);
            salida = new DataOutputStream(fos);

            for (int i = 0; i < filtrados.size(); i++) {
                Coche c = filtrados.get(i);
                salida.writeUTF(c.getMarca());
                salida.writeUTF(c.getModelo());
                salida.writeInt(c.getAnio());
                salida.writeInt(c.getKilometros());
                salida.writeDouble(c.getPrecioBase());
                salida.writeDouble(c.getDescuento());
                salida.writeUTF(c.getColor());
                salida.writeUTF(c.getCombustible());
                salida.writeUTF(c.getTransmision());
                salida.writeUTF(c.getEstado());
                salida.writeDouble(c.getPrecioFinal());
            }
            System.out.println("Coches individuales guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        } finally {
            try {
                if (salida != null) {
                    salida.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar archivo: " + e.getMessage());
            }
        }
    }
}
