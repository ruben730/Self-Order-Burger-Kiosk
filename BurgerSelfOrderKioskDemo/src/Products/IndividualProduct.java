package Products;


public class IndividualProduct implements Product {

    private String name; // Nombre del producto
    private String description; // Descripción del producto
    private String imageFileName; // Nombre del archivo de imagen asociado al producto
    private int price; // Precio del producto en centavos o la unidad monetaria correspondiente

    public int getPrice() {return this.price;}
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public String getImageFileName() {
        return this.imageFileName;
    }

    /**
     * Constructor de la clase IndividualProduct.
     * Inicializa los atributos de un producto con los valores proporcionados.
     * @param name Nombre del producto
     * @param description Descripción del producto
     * @param imageFileName Nombre del archivo de imagen del producto
     * @param price Precio del producto
     */
    public IndividualProduct(String name, String description, String imageFileName, int price) {
        this.name = name; // Asigna el nombre al atributo name
        this.description = description; // Asigna la descripción al atributo description
        this.imageFileName = imageFileName; // Asigna el nombre del archivo de imagen al atributo imageFileName
        this.price = price; // Asigna el precio al atributo price
    }
}

