package Screens;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Manager.Context;
import Manager.SimpleKiosk;
import Manager.KioskScreen;
import Manager.TranslatorManager;


public class SectionScreen implements KioskScreen {

    private static final String MEAL_IMG_PATH = "BurgerSelfOrderKioskDemo/src/Images/comida.png";

    @Override
    public KioskScreen show(Context context) {
       SimpleKiosk sk = context.getKiosk(); // Obtenemos el kiosco creado por el context
       sk.clearScreen(); // Limpiamos la pantalla del kiosco

        try {
            configureScreenButtons(context); // Llamamos a un método para configurar los botones de la pantalla
        } catch (IOException ex) {
            Logger.getLogger(SectionScreen.class.getName()).log(Level.SEVERE, null, ex); // Capturamos y registramos cualquier error al configurar los botones
        }

       // El método waitPressButton espera a que el usuario pulse uno de los botones disponibles
       char event = sk.waitPressButton();

        KioskScreen nextScreen = this; // Inicializamos la pantalla siguiente como la actual

        switch (event) {           
            case 'B':
                // Si el usuario pulsa 'B', se carga la pantalla de productos de "Bebidas"
                nextScreen = (KioskScreen) new ProductScreen("Bebidas", context.getMenuCard().getSection(1).getProducts());
                break;
            case 'C':
                // Si el usuario pulsa 'C', se carga la pantalla de productos de "Complementos"
                nextScreen = (KioskScreen) new ProductScreen("Complementos", context.getMenuCard().getSection(2).getProducts());
                break;
            case 'D':
                // Si el usuario pulsa 'D', se carga la pantalla de productos de "Hamburguesas". Se accede a seccion 0 en el XML.
                //Antes teníamos puesto...getSection(3).getProducts() y el XML solo tiene las seccions 0 (Ham), 1(Bebidas) y 2(Comple)
                nextScreen = (KioskScreen) new ProductScreen("Hamburguesas", context.getMenuCard().getSection(0).getProducts());
                break;
            case 'E':
                nextScreen = new OrderScreen(); // Si se presiona 'E', volvemos a la pantalla del pedido
                break;
            default:
                // Si no se presiona ninguna opción válida, la pantalla actual se mantiene
                break;
        }
        return nextScreen; // Regresamos la pantalla siguiente, que ha sido determinada por la opción del usuario
    }

    private void configureScreenButtons(Context context) throws IOException {

        SimpleKiosk kiosk = context.getKiosk(); // Obtenemos el kiosco del contexto
        TranslatorManager translator = context.getTranslator(); // Obtenemos el traductor del contexto
        kiosk.clearScreen(); // Limpiamos la pantalla
        kiosk.setMenuMode(); // Establecemos el kiosco en modo de menú

        // Configuramos las opciones de los botones disponibles
        kiosk.setOption('B', translator.translate("Bebidas"));
        kiosk.setOption('C',  translator.translate("Complementos"));
        kiosk.setOption('D',  translator.translate("Hamburguesas"));
        kiosk.setOption('E',  translator.translate("Volver"));


        kiosk.setImage(MEAL_IMG_PATH); // Establecemos una imagen en la pantalla
        kiosk.setTitle(translator.translate("URJC Burguer")); // Establecemos el título de la pantalla
    }
}
