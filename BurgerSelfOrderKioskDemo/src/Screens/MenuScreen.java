/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;

import java.util.ArrayList;
import java.util.List;
import Manager.Context;
import Manager.SimpleKiosk;
import Manager.KioskScreen;
import Manager.TranslatorManager;
import Products.IndividualProduct;
import Products.Order;
import Products.Menu;
import Products.Product;


public class MenuScreen implements KioskScreen {

    private final String section; // Para almacenar la sección seleccionada
    private final List<IndividualProduct> products; // Lista de productos de la sección
    private final int currentSectionIndex; // Índice de la sección actual (0: Hamburguesas, 1: Bebidas, 2: Complementos)

    //menu_products será una lista de paso para almacenar los productos seleccionados
    private List<IndividualProduct> menu_products; // Lista de productos incluidos en la orden ACTUAL. Al final menú se debe borrar

    // Constructor que recibe la sección, la lista de productos y el índice de sección
    public MenuScreen(String section, List<IndividualProduct> products, int sectionIndex, List<IndividualProduct> menu_products) {
        this.section = section;
        this.products = products;
        this.currentSectionIndex = sectionIndex;
        this.menu_products = menu_products; // Inicializa la lista de productos del menú
    }

    public KioskScreen show(Context context) {
        SimpleKiosk kiosk = context.getKiosk();
        CarrouselScreen carrousel = new CarrouselScreen(products);
        TranslatorManager translator = context.getTranslator();
        Order order = context.getOrder();

        int discount; //variable para el descuento
        int total_price = 0;

        String menu_mame = ""; // Nombre del menú

        Menu menu = new Menu(menu_products, total_price, menu_mame);

        configureScreenButtons(context); // Configura los botones en la pantalla
        displayProduct(kiosk, carrousel.getCurrentProduct(), translator); // Muestra el primer producto

        //Navegar entre los productos
        //boolean navigating = true; // Variable para controlar la navegación
        while (true) {
            char event = kiosk.waitPressButton(); // Espera la pulsación de un botón
            switch (event) {
                case 'C': //añadir producto 1 al pedido
                    // Añadir producto actual al pedido

                    menu_products.add(carrousel.getCurrentProduct()); //nos guardará los tres elementos del menu

                    kiosk.setDescription(translator.translate("Producto Guardado") + ": " +
                            translator.translate(carrousel.getCurrentProduct().getName()));

                    kiosk.wain1second(); //1 segundo para mostrar el mensaje de "Producto Guardado"

                    // Determinar la siguiente sección
                    int nextSectionIndex = currentSectionIndex + 1; //pasamos a siguiente sección 0>1>2

                    if (nextSectionIndex < 3) { // 0:Hamburguesas, 1:Bebidas, 2:Complementos
                        String nextSectionName = getSectionName(nextSectionIndex);
                        List<IndividualProduct> nextProducts = context.getMenuCard()
                                .getSection(nextSectionIndex).getProducts(); // Obtenemos los productos de la siguiente sección

                        //Para mantener a los productos anteriormente seleccionados durante multiples creaciones de
                        // pantallas se debe pasar la MISMA lista en menu_products
                        return new MenuScreen(nextSectionName, nextProducts, nextSectionIndex, menu_products);

                    } else {// Si ya pasó por todas las secciones, ir a la pantalla de pedido

                        int menu_price_with_no_discount = 0;

                        for (Product product : menu_products) { //sumamos el precio de todos los productos
                            menu_price_with_no_discount = menu_price_with_no_discount + product.getPrice();
                        }

                        discount = menu.getMenuDiscount(); //20% descuento al menú o el valor que sea en el txt. Devuelve 20.

                        // Calculamos el descuento específico en céntimos: Ej. 3540 * 20 / 100 = 708 céntimos (7,08 €)
                        int descuentoCentimos = menu_price_with_no_discount * discount / 100;

                        // Calculamos el precio final en céntimos: Ej. 3540 - 708 = 2832 céntimos (28,32 €)
                        int final_price = menu_price_with_no_discount - descuentoCentimos;


                        menu.setPrice(final_price);

                        String menu_name = translator.translate("MENU")+"/"
                                + translator.translate(menu_products.get(0).getName()); //el nombre será Menu + nombre de la hamburguesa
                        menu.setMenuName(menu_name);

                        //Para que veas por terminal qué se guarda.
                        System.out.println("===========MENU==============");
                        System.out.println("NOMBRE-MENU: "+menu_name);
                        System.out.println("Descuento: " + discount);
                        System.out.println("Precio Original: " + menu_price_with_no_discount);
                        System.out.println("Precio Final: " +final_price);
                        for (Product product : menu_products) {
                            System.out.println(product.getName());
                        }
                        System.out.println("=============================");

                        //Aqui aseguramos que el menú se guarda en el pedido como forma de paquete de 3 productos
                        Menu thisNewMenu = new Menu(menu_products, final_price, menu_name);

                        //Añadimos el menú al pedido
                        order.addMenu(thisNewMenu);

                        return new OrderScreen();
                    }

                case 'D':
                    return new OrderScreen(); // Volver a la pantalla de pedidos

                case 'G':
                    carrousel.previous(); // Producto anterior en el carrusel
                    displayProduct(kiosk, carrousel.getCurrentProduct(), translator);
                    break;

                case 'H':
                    carrousel.next(); // Siguiente producto en el carrusel
                    displayProduct(kiosk, carrousel.getCurrentProduct(), translator);
                    break;
            }
        }
    }

    private void configureScreenButtons(Context context) {
        TranslatorManager translator = context.getTranslator();
        SimpleKiosk kiosk = context.getKiosk();

        kiosk.clearScreen();
        kiosk.setMenuMode();
        kiosk.setTitle(translator.translate(section)); // Mostrar el nombre de la sección actual

        kiosk.setOption('C', translator.translate("Añadir Producto"));
        kiosk.setOption('D', translator.translate("Volver"));
        kiosk.setOption('G', "<");
        kiosk.setOption('H', ">");
    }

    private String getSectionName(int index) {
        switch (index) {
            case 0: return "Hamburguesas";
            case 1: return "Bebidas";
            case 2: return "Complementos";
            default: return "";
        }
    }

    private void displayProduct(SimpleKiosk kiosk, IndividualProduct indProduct, TranslatorManager translator) {
        kiosk.setImage(indProduct.getImageFileName());
        kiosk.setDescription(
                translator.translate(indProduct.getName()) + "\n" +
                        translator.translate(indProduct.getDescription() +
                                "\n" + indProduct.getPrice()/ 100.0f +"€")
        );
    }
}
