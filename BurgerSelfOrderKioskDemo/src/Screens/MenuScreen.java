/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;

import java.util.List;
import Manager.Context;
import Manager.SimpleKiosk;
import Manager.KioskScreen;
import Manager.TranslatorManager;
import Products.IndividualProduct;

/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */ 
public class MenuScreen extends CarrouselScreen {

    public MenuScreen(List<IndividualProduct> products) {
        super(products); // Llama al constructor de la clase CarrouselScreen con los productos
    }

    public KioskScreen show(Context context) {
        configureScreenButtons(context); //mete los botones en la pantalla
        SimpleKiosk kiosk = context.getKiosk(); //creamos kiosk
        
        // ahora el waitbutton espera a que el usuario pulse uno de los botones 
        char event = kiosk.waitPressButton(); // en SimpleKiosk hay un metodo public q tiene waitButton() {return waitevent(60);}
        
        KioskScreen nextScreen = (KioskScreen) this; // inicializamos nextScreen con la pantalla actual (MenuScreen)
        
        if (event == 'B') {
            // add menu to cart, lógicamente debe agregar el menú al carrito de compras
        } else if (event == 'C') {
            nextScreen = new OrderScreen(); // si el usuario elige la opción 'C', se va a la pantalla de pedidos
        } else if (event == 'G') {
            // product.nextProduct(context); // Avanzar al siguiente producto en el carrusel
        } else if (event == 'H') {
            // product.previousProduct(context); // Retroceder al producto anterior en el carrusel
        }
        
        return nextScreen; // Retorna la pantalla siguiente, dependiendo de la acción del usuario
    }

    private void configureScreenButtons(Context context) { // configuración de los botones
        TranslatorManager translator = context.getTranslator(); // Usar el TranslatorManager del contexto

        SimpleKiosk kiosk = context.getKiosk();
        
        kiosk.clearScreen(); // Limpiar la pantalla
        kiosk.setMenuMode(); // Establecer el modo menú
        kiosk.setTitle(translator.translate("Menu")); // Establecer el título traducido
        
        // Configurar las opciones del menú
        kiosk.setOption('B', translator.translate("Añadir menu"));
        kiosk.setOption('C', translator.translate("Cancelar"));
        
        // Esta clase implementa un carrusel que va mostrando los productos del menú
        kiosk.setOption('G', "<"); // Opción para el producto anterior
        kiosk.setOption('H', ">"); // Opción para el producto siguiente
    }

}// END.
