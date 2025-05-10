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
    private List<Product> menu_products; // Lista de productos incluidos en la orden

    // Constructor que recibe la sección, la lista de productos y el índice de sección
    public MenuScreen(String section, List<IndividualProduct> products, int sectionIndex) {
        this.section = section;
        this.products = products;
        this.currentSectionIndex = sectionIndex;
        this.menu_products = new ArrayList<Product>();
    }

    public KioskScreen show(Context context) {
        SimpleKiosk kiosk = context.getKiosk();
        CarrouselScreen carrousel = new CarrouselScreen(products);
        TranslatorManager translator = context.getTranslator();
        Order order = context.getOrder();
        int discount = 0;
        Menu menu = new Menu(products, discount);

        configureScreenButtons(context); // Configura los botones en la pantalla
        displayProduct(kiosk, carrousel.getCurrentProduct(), translator); // Muestra el primer producto

        //Navegar entre los productos
        //boolean navigating = true; // Variable para controlar la navegación
        while (true) {
            char event = kiosk.waitPressButton(); // Espera la pulsación de un botón
            switch (event) {
                case 'C': //aun no funciona
                    // Añadir producto actual al pedido
                    //order.addProduct(carrousel.getCurrentProduct());
                    menu_products.add(carrousel.getCurrentProduct()); //nos guardará los tres elementos del menu

                    kiosk.setDescription(translator.translate("Producto Guardado") + ": " +
                            translator.translate(carrousel.getCurrentProduct().getName()));

                    kiosk.wain1second(); //1 segundo para mostrar el mensaje de "Producto Guardado"

                    // Determinar la siguiente sección
                    int nextSectionIndex = currentSectionIndex + 1;
                    if (nextSectionIndex < 3) { // 0:Hamburguesas, 1:Bebidas, 2:Complementos
                        String nextSectionName = getSectionName(nextSectionIndex);
                        List<IndividualProduct> nextProducts = context.getMenuCard()
                                .getSection(nextSectionIndex).getProducts();
                        return new MenuScreen(nextSectionName, nextProducts, nextSectionIndex);
                    } else {
                        // Si ya pasó por todas las secciones, ir a la pantalla de pedido
                        int menu_price_with_no_discount = 0;
                        for (Product product : menu_products) {
                            menu_price_with_no_discount = menu_price_with_no_discount + product.getPrice();
                        }

                        discount = (menu_price_with_no_discount * 20)/100; //20% descuento al menú

                        int  final_price = menu_price_with_no_discount-discount;
                        //menu.setDiscount(discount);

                        System.out.println("Descuento: "+ discount/ 100.0f);
                        System.out.println("Descuento SIN aplicar: "+ menu_price_with_no_discount/ 100.0f);
                        System.out.println("Descuento aplicado: "+ final_price / 100.0f);

                        menu.setPrice(final_price);
                        order.addMenu(menu);

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

        kiosk.setOption('B', translator.translate("Escoge tus")+ ": "+section);
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
