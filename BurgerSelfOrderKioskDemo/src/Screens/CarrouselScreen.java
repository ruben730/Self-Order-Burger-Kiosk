/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;
        
import java.util.List;
import Products.IndividualProduct;


public class CarrouselScreen { 

    private List<IndividualProduct> products;
    private int currentIndex; //Current Product
    
    public CarrouselScreen(List<IndividualProduct> products) {
        this.products = products; 
        this.currentIndex = 0; //Current Product
    }

    public IndividualProduct getCurrentProduct() {
        return this.products.get(this.currentIndex); // devuelve el producto en la posicion actual
    }

    // metodo para ir al producto anterior en el carrusel
    public void previous() {
        // decrementamos el indice y usamos modulo para hacerlo circular
        currentIndex = (currentIndex - 1 + products.size()) % products.size();
        updateUI(); // actualizamos la interfaz despues de cambiar el producto
    }

    // metodo para ir al siguiente producto en el carrusel
    public void next() {
        // incrementamos el indice y usamos modulo para hacerlo circular
        currentIndex = (currentIndex + 1) % products.size();
        updateUI(); // actualizamos la interfaz despues de cambiar el producto
    }

    private void updateUI() {
        IndividualProduct currentProduct = getCurrentProduct();
        updateImage(currentProduct.getImageFileName());
        updateDescription(currentProduct.getDescription());
    }

    private void updateImage(String imagePath) {
        System.out.println("Actualizando imagen con: " + imagePath);
    }

    private void updateDescription(String description) {
        System.out.println("Actualizando descripcion: " + description);
    }
}


