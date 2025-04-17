/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;

import java.util.List;
import Manager.Context;
import Manager.KioskScreen;
import Manager.SimpleKiosk;
import Manager.TranslatorManager;
import Products.IndividualProduct;


/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */
public class ProductScreen implements KioskScreen{
    
    private String section; // Para almacenar la sección seleccionada
    private final List<IndividualProduct> products; // Lista de productos de la sección

    // Constructor que recibe la sección y la lista de productos
    public ProductScreen(String section, List<IndividualProduct> products) { 
        this.section = section;
        this.products = products;
    }

    @Override
    public KioskScreen show(Context context) {
        TranslatorManager manager = context.getTranslator(); // Obtener el manager de traducción
        SimpleKiosk kiosk = context.getKiosk(); // Obtener el quiosco
        CarrouselScreen carrousel = new CarrouselScreen(products); // Crear un carrusel con los productos

        // Configurar la pantalla
        kiosk.clearScreen(); // Limpiar la pantalla
        kiosk.setMenuMode(); // Establecer el modo de menú
        kiosk.setTitle(manager.translate("Productos")+" - " + section); // Establecer el título de la pantalla con la sección seleccionada
        
        // Establecer las opciones del menú
        kiosk.setOption('D', manager.translate("Añadir Producto"));
        kiosk.setOption('E', manager.translate("Cancelar"));
        kiosk.setOption('H', ">"); // Siguiente producto
        kiosk.setOption('G', "<"); // Producto anterior

         // Mostrar el primer producto
        displayProduct(kiosk, carrousel.getCurrentProduct()); // Mostrar el primer producto en el carrusel
        KioskScreen nextScreen = this; // Inicializa la siguiente pantalla a la actual

        // Navegar entre los productos
        boolean navigating = true; // Variable para controlar la navegación
        while (navigating) {
            char event = kiosk.waitPressButton(); // Espera la pulsación de un botón
            switch (event) {
                case 'H': // Siguiente producto
                    carrousel.next(); // Mover al siguiente producto
                    displayProduct(kiosk, carrousel.getCurrentProduct()); // Actualizar la pantalla con el siguiente producto
                    break;
                case 'G': // Producto anterior
                    carrousel.previous(); // Mover al producto anterior
                    displayProduct(kiosk, carrousel.getCurrentProduct()); // Actualizar la pantalla con el producto anterior
                    break;
                case 'D': // Seleccionar el producto
                    kiosk.setDescription(manager.translate("Producto seleccionado")+": "+ carrousel.getCurrentProduct().getName()); // Mostrar descripción del producto seleccionado
                   // navigating = false; // Terminar la navegación (comentado para que la navegación siga activa)
                    break;
                case 'E': // Cancelar
                    nextScreen = (KioskScreen) new SectionScreen(); // Volver a la pantalla de sección
                    navigating = false; // Terminar la navegación
                    break;
                default:
                    // No hacer nada si no se presionan las teclas esperadas
                    break;
            }
        }

        return nextScreen; // Regresa la misma pantalla o la pantalla seleccionada
    }

    // Método para mostrar un producto en la pantalla
    private void displayProduct(SimpleKiosk kiosk, IndividualProduct indProduct) {
        kiosk.setImage(indProduct.getImageFileName()); // Establecer la imagen del producto
        kiosk.setDescription(indProduct.getDescription()); // Establecer la descripción del producto
    }
}
