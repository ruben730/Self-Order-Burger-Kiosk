package Screens;

import java.util.List;
import Manager.Context;
import Manager.KioskScreen;
import Manager.SimpleKiosk;
import Manager.TranslatorManager;
import Products.IndividualProduct;
import Products.Order;


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
        TranslatorManager translator = context.getTranslator(); // Obtener el manager de traducción
        SimpleKiosk kiosk = context.getKiosk(); // Obtener el quiosco
        CarrouselScreen carrousel = new CarrouselScreen(products); // Crear un carrusel con los productos
        Order order = context.getOrder(); //obtenemos el order actual sin perder informacion

        // Configurar la pantalla
        kiosk.clearScreen(); // Limpiar la pantalla
        kiosk.setMenuMode(); // Establecer el modo de menú
        kiosk.setTitle(translator.translate("Productos")+" - " + translator.translate(section)); // Establecer el título de la pantalla con la sección seleccionada
        
        // Establecer las opciones del menú
        kiosk.setOption('D', translator.translate("Añadir Producto"));
        kiosk.setOption('E', translator.translate("Volver"));
        kiosk.setOption('C', "+1"); // Otro producto más
        kiosk.setOption('H', ">"); // Siguiente producto
        kiosk.setOption('G', "<"); // Producto anterior

        // Mostrar el primer producto
        displayProduct(kiosk, carrousel.getCurrentProduct(), context.getTranslator()); // Mostrar el primer producto en el carrusel
        KioskScreen nextScreen = this; // Inicializa la siguiente pantalla a la actual

        // Navegar entre los productos
        boolean navigating = true; // Variable para controlar la navegación
        while (navigating) {
            char event = kiosk.waitPressButton(); // Espera la pulsación de un botón
            switch (event) {
                case 'H': // Siguiente producto. >
                    carrousel.next(); // Mover al siguiente producto
                    displayProduct(kiosk, carrousel.getCurrentProduct(), context.getTranslator()); // Actualizar la pantalla con el siguiente producto
                    break;
                case 'G': // Producto anterior. <
                    carrousel.previous(); // Mover al producto anterior
                    displayProduct(kiosk, carrousel.getCurrentProduct(), context.getTranslator()); // Actualizar la pantalla con el producto anterior
                    break;
                case 'D': // Seleccionar el producto.
                    kiosk.setDescription(translator.translate("Producto Guardado")+": "+ translator.translate(carrousel.getCurrentProduct().getName()));
                    order.addProduct(carrousel.getCurrentProduct()); //Esto añade productos a la lista
                    break;
                case 'C': //Ejercicio del examen. +1
                    displayProduct(kiosk, carrousel.getCurrentProduct(), context.getTranslator());
                    kiosk.setDescription("+1: "+ translator.translate(carrousel.getCurrentProduct().getName()));
                    order.addProduct(carrousel.getCurrentProduct()); //Esto añade productos a la lista
                    break;
                case 'E': // Cancelar = Volver a la pantalla anterior
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
    private void displayProduct(SimpleKiosk kiosk, IndividualProduct indProduct, TranslatorManager translator) {
        kiosk.setImage(indProduct.getImageFileName()); // Establecer la imagen del producto
        kiosk.setDescription(
                translator.translate(indProduct.getDescription()+
                        "\n" + indProduct.getPrice()/ 100.0f +"€") // Establecer la descripción traducida del producto
                            );
    }
}
