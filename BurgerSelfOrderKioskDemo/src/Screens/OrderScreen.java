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


public class OrderScreen implements KioskScreen {

    private static final String ORDER_IMG_PATH = "BurgerSelfOrderKioskDemo/src/Images/Pedido.png";

    @Override
    public KioskScreen show(Context context) {
        configureScreenButtons(context); // Configura los botones en la pantalla
        Order order = context.getOrder(); //Obtenemos la informacion del pedido del usuario actual
        SimpleKiosk kiosk = context.getKiosk(); // Obtenemos el quiosco

        List<IndividualProduct> menu_products = new ArrayList<>();

        // Espera que el usario pulse un botón
        char event = kiosk.waitPressButton();

        KioskScreen nextScreen = this; // Inicializa nextScreen a la pantalla actual

        switch (event) {
            case 'A': // Añadir producto individual. Enviamos a la pantalla de secciones (bebidas, hamburguesas, etc.)
                nextScreen = (KioskScreen) new SectionScreen(); //Cambia a la pantalla de secciones
                break;

            case 'B': // Añadir menú. Mostramos hamburguesas primero.
                nextScreen = (KioskScreen) new MenuScreen( "Hamburguesas" ,context.getMenuCard().getSection(0).getProducts(), 0, menu_products);
                break;

            case 'C':// Eliminar producto. Mostrar un carrousel de context.getOrder().
                if (!order.getProducts().isEmpty()) { // si no está vacío
                    nextScreen = new DeleteProductScreen(order.getProducts());
                }
                break;
            case 'D':
                if (order.getProducts().isEmpty()){ //si no hay nada en pedido, al clickar simula no funcionar el botón
                    //en realidad se regenera esta misma pantalla
                    nextScreen = (KioskScreen) new OrderScreen();
                }else { // Vamos a pantalla de pago
                    nextScreen = new PurshaseScreen(); // Cambia a la pantalla de pago
                }
                break;

            case 'E': // Cancelar pedido implica borrar productos elegidos.
                if (!order.getProducts().isEmpty()) { // si NO está vacía
                    order.getProducts().clear(); // vaciamos directamente la lista del pedido
                }
                nextScreen = new WellcomeScreen(); // Cambia a la pantalla de bienvenida
                break;
            default:
                break;
        }
        return nextScreen; // Devuelve la siguiente pantalla según la acción del usuario
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
        kiosk.setOption('E', manager.translate("Cancelar pedido"));
    }

}




