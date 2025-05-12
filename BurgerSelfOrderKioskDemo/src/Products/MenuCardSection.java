package Products;

import java.util.List;

public class MenuCardSection {

    private String sectionName; // Nombre de la sección del menú
    private String imageFileName; // Nombre del archivo de imagen asociado con la sección
    private List<IndividualProduct> productList; // Lista de productos dentro de la sección

    public MenuCardSection(String name, String imageFileName, List<IndividualProduct> products) {
        this.sectionName = name; // Asigna el nombre de la sección
        this.imageFileName = imageFileName; // Asigna el nombre del archivo de imagen
        this.productList = products; // Asigna la lista de productos
    }

    public List<IndividualProduct> getProducts() {return this.productList;}

    public String getSectionName() {
        return this.sectionName; // Devuelve el nombre de la sección
    }
    public int getNumberOfProducts() {
        return this.productList.size(); // Devuelve el tamaño de la lista de productos
    }
    public String getImageFileName() {
        return this.imageFileName; // Devuelve el nombre del archivo de imagen
    }

}
