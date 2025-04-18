/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;
        
import java.util.List;
import Products.IndividualProduct;

/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */                             
public class CarrouselScreen { 
    //CLASE PERFECTA
    private List<IndividualProduct> products;  //cambiar por ArrayList
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

    // metodo que intenta ajusta los botones segun el producto actual y el numero total de productos
    protected void adjustCarruselButtons(int currentElement, int numberOfElements) {
        //si estamos en el primer producto, deshabilitamos el boton 'anterior'
        boolean isPreviousButtonEnabled = currentElement > 0;
        
        // si estamos en el ultimo producto, deshabilitamos el boton 'siguiente'
        boolean isNextButtonEnabled = currentElement < numberOfElements - 1;
        
        // actualizamos los botones, habilitandolos o deshabilitandolos
        updateButtonState("H", isPreviousButtonEnabled); // boton H (anterior)
        updateButtonState("G", isNextButtonEnabled); // boton G (siguiente)
    }

    // metodo protegido para configurar los botones de la pantalla
    protected void configureScreenButtons() {
        // configuramos los botones segun el estado actual
        adjustCarruselButtons(currentIndex, products.size());
    }

    // metodo para actualizar los botones segun su estado
    private void updateButtonState(String buttonName, boolean isEnabled) {
        // aqui actualizas el estado de los botones en la interfaz grafica del kiosco
        // en lugar de imprimir, habilitaras o deshabilitaras los botones reales
        if (isEnabled) {
            // habilitar el boton en la interfaz
            System.out.println("Boton " + buttonName + " habilitado");
        } else {
            // deshabilitar el boton en la interfaz
            System.out.println("Boton " + buttonName + " deshabilitado");
        }
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


