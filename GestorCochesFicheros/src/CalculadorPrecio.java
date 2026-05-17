
/**
 * Utility class to calculate the final price of a car.
 */
public class CalculadorPrecio {

    /**
     * Calculates the final price by applying the discount and taxes. Tax is 21%
     * if the car is 20 years old or newer, otherwise 10%.
     *
     * @param coche The car to calculate the price for
     */
    public void calcularPrecioFinal(Coche coche) {
        double precioActual = coche.getPrecioBase();

        // Apply discount if it exists
        if (coche.getDescuento() > 0) {
            double cantidadDescuento = precioActual * (coche.getDescuento() / 100.0);
            precioActual = precioActual - cantidadDescuento;
        }

        // Apply taxes (Assuming current year is 2026)
        int anioActual = 2026;
        int edad = anioActual - coche.getAnio();

        double impuesto = 0.10; // 10% tax for older cars
        if (edad <= 20) {
            impuesto = 0.21; // 21% tax for cars 20 years old or newer
        }

        double precioFinal = precioActual + (precioActual * impuesto);
        coche.setPrecioFinal(precioFinal);
    }
}
