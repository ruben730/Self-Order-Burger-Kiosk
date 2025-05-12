package Products;

import Manager.TranslatorManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Order {

    private int orderNumber; // Número único que identifica la orden
    private List<Product> products; // Lista de productos incluidos en la orden
    private int totalAmount;
    private List<Menu> menus = new ArrayList<>();

    public Order(int orderNumber) {
        this.orderNumber = orderNumber; // Asigna el número de la orden
        this.products = new ArrayList<>(); // Inicializa la lista de productos como una lista vacía
        this.totalAmount = 0; //irá guardando el precio
    }

    //Método importante. Se guardará una lista de los productos de todo tipo (bebidas, hamburguesas, etc.) del pedido.
    public List<Product> getProducts() { // Guarda los productos del pedido, es para facilitar operaciones
        return this.products;
    }

    public String getOrderTextForConsumerTicket(TranslatorManager translator) {
        StringBuilder sb = new StringBuilder(); // Crea un StringBuilder para construir el texto
        // Itera sobre los productos y añade sus nombres traducidos al StringBuilder
        products.forEach(product -> sb.append(translator.translate(product.getName())).append(", "));
        // Elimina la última coma y espacio, si la lista de productos no está vacía
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
    }

   public String getOrderText() { //este no traducia el ticket para el usuario. getOrderTextForConsumerTicket si lo hace.
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

    public void addMenu(Menu menu) {
        this.products.add(menu); // Añade el menu a la lista
    }

    public List<Menu> getMenus() {return menus;}

    public int getTotalAmount() {
        int totalAmount = 0;

        // Iteramos sobre los productos del pedido
        for (Product product : this.products) {
            // Si el producto es un menú, sumamos su precio
            if (product instanceof Menu) {
                totalAmount += product.getPrice(); // Obtenemos el precio del menú
            } else {
                // Si es un producto individual, sumamos su precio
                totalAmount += product.getPrice(); // Obtenemos el precio del producto
            }
        }
        return totalAmount; // El total es en céntimos
    }

    public void removeProduct(Product product) {
        // Recorremos la lista por índices
        for (int i = 0; i < this.products.size(); i++) {
            // Comparamos por nombre (o por cualquier otra propiedad única)
            if (this.products.get(i).getName().equals(product.getName())) {
                this.products.remove(i);  // Eliminamos el producto por índice
                break;                    // Salimos tras la primera coincidencia
            }
        }
    }

}


