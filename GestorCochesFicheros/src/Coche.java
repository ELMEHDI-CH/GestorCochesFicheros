
/**
 * Represents a car (Coche) with all its details.
 * This is a simple data model class with Spanish attributes.
 */
public class Coche {

    private String marca;
    private String modelo;
    private int anio;
    private int kilometros;
    private double precioBase;
    private double descuento;
    private String color;
    private String combustible;
    private String transmision;
    private String estado;
    private double precioFinal;

    /**
     * Constructor to create a new Coche.
     *
     * @param marca The brand of the car
     * @param modelo The model of the car
     * @param anio The manufacturing year
     * @param kilometros The total kilometers driven
     * @param precioBase The starting price without taxes or discounts
     * @param descuento The discount percentage
     * @param color The color of the car
     * @param combustible The type of fuel used
     * @param transmision The transmission type
     * @param estado The condition of the car
     */
    public Coche(String marca, String modelo, int anio, int kilometros, double precioBase,
            double descuento, String color, String combustible, String transmision, String estado) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.kilometros = kilometros;
        this.precioBase = precioBase;
        this.descuento = descuento;
        this.color = color;
        this.combustible = combustible;
        this.transmision = transmision;
        this.estado = estado;
    }

    // Getters
    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnio() {
        return anio;
    }

    public int getKilometros() {
        return kilometros;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public double getDescuento() {
        return descuento;
    }

    public String getColor() {
        return color;
    }

    public String getCombustible() {
        return combustible;
    }

    public String getTransmision() {
        return transmision;
    }

    public String getEstado() {
        return estado;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    // Setters
    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    /**
     * Returns a simple text representation of the car in Spanish.
     *
     * @return String with car details
     */
    @Override
    public String toString() {
        return "Marca: " + marca + ", Modelo: " + modelo + ", Año: " + anio
                + ", Km: " + kilometros + ", Combustible: " + combustible
                + ", Precio Final: " + precioFinal;
    }
}
