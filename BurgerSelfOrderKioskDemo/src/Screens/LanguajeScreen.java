package Screens;

import Manager.Context;
import Manager.SimpleKiosk;
import Manager.KioskScreen;
import Manager.TranslatorManager;

/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */
public class LanguajeScreen implements KioskScreen {
    private static final String LANGUAGE_IMG_PATH = "BurgerSelfOrderKioskDemo/src/Images/idiomas.png";

    @Override
    public KioskScreen show(Context context) {
            configureScreenButtons(context); //mete los botones en la pantalla

            SimpleKiosk kiosk = context.getKiosk(); //creamos kiosk
            TranslatorManager translator = context.getTranslator(); 
          
            //ahora el waitbutton espera a que el usuario pulse uno de los botones 
            char event = kiosk.waitPressButton(); //en SimpleKiosk hay un metodo public q tiene waitButton() {return waitevent(60);}
            KioskScreen nextScreen = this;

        switch (event) {
            case 'B':
                /*Si el usuario apreta el boton de español,
                automaticamente el traslatormanager realiza la traduccion*/
                translator.selectLanguage("spanish");
                configureScreenButtons(context); //mete los botones en la pantalla
                break;
            case 'C':
                translator.selectLanguage("english");
                configureScreenButtons(context); //mete los botones en la pantalla
                break;
            case 'D':
                translator.selectLanguage("catalan");
                configureScreenButtons(context); //mete los botones en la pantalla
                break;
            case 'E':
                translator.selectLanguage("portuguese");
                configureScreenButtons(context); //mete los botones en la pantalla
                 break;
            case 'F':
                nextScreen = new WellcomeScreen();
                break;
        }
        return nextScreen; //no se vuelve a wellcome. Vuelve a esta misma pagina.
     }

    private void configureScreenButtons(Context context) {
                TranslatorManager translator = context.getTranslator();
                SimpleKiosk kiosk = context.getKiosk();
                
                kiosk.clearScreen(); // Limpiamos la pantalla antes de actualizarla
                kiosk.setMenuMode(); // Establecemos el modo menú en la pantalla
                kiosk.setTitle(translator.translate("Selecciona un idioma")); // Título traducido
                kiosk.setImage(LANGUAGE_IMG_PATH); // Establecemos la imagen para el idioma
                kiosk.setDescription("\n\n"+ translator.translate("Dispuestos a hacer la mejor hamburguesa del mundo"));
                
                // Configuramos las opciones del menú con las traducciones correspondientes
                kiosk.setOption('B', translator.translate("Español"));
                kiosk.setOption('C', translator.translate("Ingles"));
                kiosk.setOption('D', translator.translate("Catalan"));
                kiosk.setOption('E', translator.translate("Portugues"));
                kiosk.setOption('F', translator.translate("Volver")); // Opción para volver
            }
}
