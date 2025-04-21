/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Products;

import java.util.List;

/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */
public class Menu implements Product {
    // Atributos de la clase Menu

    private int discount; // Porcentaje de descuento aplicado al precio total del menú
    private List<IndividualProduct> products; // Lista de productos individuales incluidos en el menú
    private int final_price; //precio final

    public Menu(List<IndividualProduct> products, int final_price) {
        this.products = products; // Asigna la lista de productos al atributo products
        this.final_price = final_price; // Asigna el precio final al menu
    }

    public void setDiscount(int discount) {
        this.discount = discount; // Actualiza el valor del atributo discount
    }

    public void setPrice(int final_price){ this.final_price = final_price;}

    public int getPrice() {
        // Calcula el precio total de los productos sumando sus precios individuales
        int total = products.stream().mapToInt(IndividualProduct::getPrice).sum();
        // Aplica el descuento y devuelve el precio final
        return total - (total * discount / 100);
    }

    public String getName() {
        return "Menu"; // Devuelve un nombre genérico para el menú
    }

    public IndividualProduct getProduct(int index) {
        return products.get(index); // Devuelve el producto en el índice especificado
    }
}

