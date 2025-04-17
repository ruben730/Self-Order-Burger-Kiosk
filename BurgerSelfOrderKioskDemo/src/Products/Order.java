package Products;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */

public class Order {

    private int orderNumber; // Número único que identifica la orden
    private List<Product> products; // Lista de productos incluidos en la orden

    public Order(int orderNumber) {
        this.orderNumber = orderNumber; // Asigna el número de la orden
        this.products = new ArrayList<>(); // Inicializa la lista de productos como una lista vacía
    }

    public String getOrderText() {
        StringBuilder sb = new StringBuilder(); // Crea un StringBuilder para construir el texto
        // Itera sobre los productos y añade sus nombres al StringBuilder
        products.forEach(product -> sb.append(product.getName()).append(", "));
        // Elimina la última coma y espacio, si la lista de productos no está vacía
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
    }

    public int getOrderNumber() {return this.orderNumber;}

    public void addProduct(Product product) {
        this.products.add(product); // Añade el producto a la lista
    }

    public int getTotalAmount() { // Usa un stream para sumar los precios de todos los productos en la lista
        return this.products.stream().mapToInt(Product::getPrice).sum();
    }
}


