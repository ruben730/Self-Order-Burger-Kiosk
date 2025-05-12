package Screens;

import Manager.Context;
import Manager.KioskScreen;
import Manager.SimpleKiosk;
import Manager.TranslatorManager;
import Products.IndividualProduct;
import Products.Order;
import Products.Product;

import java.util.ArrayList;
import java.util.List;

public class DeleteProductScreen implements KioskScreen {

    private final List<Product> products;

    // Constructor que recibe la sección y la lista de productos
    public DeleteProductScreen(List<Product> products) {
        this.products = products;
    }

    @Override
    public KioskScreen show(Context context) {
        TranslatorManager translator = context.getTranslator(); // Obtener el manager de traducción
        SimpleKiosk kiosk = context.getKiosk(); // Obtener el quiosco
        Order order = context.getOrder();

        // Filtrar solo productos individuales del pedido actual
        List<IndividualProduct> productosActuales = new ArrayList<>();
        for (Product pro : order.getProducts()) {
            if (pro instanceof IndividualProduct) {
                productosActuales.add((IndividualProduct) pro); // Añadir solo productos individuales a la lista
            }
        }

        // Crear el carrusel con los productos individuales actuales
        CarrouselScreen carrousel = new CarrouselScreen(productosActuales);

        // Configurar la pantalla
        kiosk.clearScreen(); // Limpiar la pantalla
        kiosk.setMenuMode(); // Establecer el modo de menú
        kiosk.setTitle(translator.translate("Tus Productos")); // Establecer el título de la pantalla

        // Establecer las opciones del menú
        kiosk.setOption('D', translator.translate("Eliminar Producto"));
        kiosk.setOption('E', translator.translate("Volver"));
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
                case 'D': // Borrar el producto.
                    IndividualProduct current = carrousel.getCurrentProduct();
                    kiosk.setDescription(translator.translate("Producto Eliminado") + ": " +
                            translator.translate(current.getName()));

                    System.out.println("============DELET============"); //<-- Debug
                    System.out.println("Has Borrado el producto: " + current.getName());
                    System.out.println("=============================");

                    order.removeProduct(current);

                    List<IndividualProduct> nuevos = new ArrayList<>();
                    for (Product p : order.getProducts()) {
                        if (p instanceof IndividualProduct) {
                            nuevos.add((IndividualProduct) p);
                        }
                    }
                    carrousel = new CarrouselScreen(nuevos);

                    if (!nuevos.isEmpty()) {
                        displayProduct(kiosk, carrousel.getCurrentProduct(), context.getTranslator());
                    } else {
                        // Si ya no quedan productos, volvemos automáticamente
                        nextScreen = new OrderScreen();
                        navigating = false;
                    }
                    break;

                case 'E': // Cancelar = Volver a la pantalla anterior
                    nextScreen = (KioskScreen) new OrderScreen(); // Volver a la pantalla de Pedidos
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
                translator.translate(indProduct.getDescription())+
                        "\n" + indProduct.getPrice()/ 100.0f +"€" // Establecer la descripción traducida del producto
        );
    }
}
