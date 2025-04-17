/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;

import Manager.SimpleKiosk;
import Manager.KioskScreen;
import Manager.Context;
import Manager.TranslatorManager;
import Products.IndividualProduct;
import Products.Order;
import Products.Product;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */
public class OrderScreen implements KioskScreen {
    private static final String ORDER_IMG_PATH = "BurgerSelfOrderKioskDemo/src/Images/Pedido.png";

    @Override
    public KioskScreen show(Context context) {
        configureScreenButtons(context); // Configura los botones en la pantalla
        Order order = context.getOrder();
        SimpleKiosk kiosk = context.getKiosk(); // Obtenemos el quiosco

        // Espera la pulsación de un botón
        char event = kiosk.waitPressButton();
        
        KioskScreen nextScreen = this; // Inicializa nextScreen a la pantalla actual

        switch (event) {
            case 'A':
                // Añadir producto individual
                nextScreen = (KioskScreen) new SectionScreen(); // Cambia a la pantalla de sección
                break;
            case 'B':
                // Añadir menú
                nextScreen = (KioskScreen) new MenuScreen(context.getMenuCard().getSection(2).getProducts());
                break;
            case 'C':// Eliminar producto NO VA AÚN
                // 1. Obtener los productos como ArrayList<Product>
                ArrayList<Product> orderProducts = new ArrayList<>(order.getProducts());
                // 2. Filtrar y convertir a IndividualProduct (si es necesario)
                List<IndividualProduct> individualProducts = new ArrayList<>();
                for (Product product : orderProducts) {
                    if (product instanceof IndividualProduct) {
                        individualProducts.add((IndividualProduct) product);
                    }
                }
                // 3. Crear el carrusel con los productos individuales
                CarrouselScreen carrousel = new CarrouselScreen(individualProducts);
                kiosk.clearScreen();
                carrousel.configureScreenButtons();
                char event2 = kiosk.waitPressButton();
                switch (event2) {
                     case 'H': // Siguiente producto. Funciona Perfecto
                             break;
                        case 'G': // Producto anterior. Funciona Perfecto
                            break;
                }

                break;
            case 'D':
                // Vamos a pantalla de pago
                nextScreen = new PurshaseScreen(); // Cambia a la pantalla de compra
                break;
            case 'E':
                // Cancelar
                nextScreen = new WellcomeScreen(); // Cambia a la pantalla de bienvenida
                break;
            default:
                break;
        }
        return nextScreen; // Retorna la siguiente pantalla según la acción del usuario
    }

    private void configureScreenButtons(Context context) {
        TranslatorManager manager = context.getTranslator(); // Obtener el manager de traducción
        SimpleKiosk kiosk = context.getKiosk(); // Obtener el quiosco

        kiosk.clearScreen(); // Limpiar la pantalla
        kiosk.setMenuMode(); // Establecer el modo de menú
        kiosk.setImage(ORDER_IMG_PATH); // Establecer la imagen
        kiosk.setTitle(manager.translate("Pedido")); // Establecer el título traducido

        // Configurar las opciones de la pantalla
        kiosk.setOption('A', manager.translate("Añadir producto individual a pedido"));
        kiosk.setOption('B', manager.translate("Añadir menú al pedido"));
        kiosk.setOption('C', manager.translate("Eliminar producto"));
        kiosk.setOption('D', manager.translate("Finalizar pedido"));
        kiosk.setOption('E', manager.translate("Cancelar"));
    }

    private void deleteProduct(Order order) {
        for (Product product: order.getProducts()) {

        }
    }
}




