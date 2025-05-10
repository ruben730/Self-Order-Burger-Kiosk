package Manager;

import sienens.BurgerSelfOrderKiosk;
import java.util.*;

public class SimpleKiosk {
    
    private BurgerSelfOrderKiosk kiosk;    // Instancia del kiosco de autopedidos de hamburguesas
    TranslatorManager translator;          // Gestor de traducciones para el kiosco
           
    public SimpleKiosk(TranslatorManager translatorManager){
        this.kiosk = new BurgerSelfOrderKiosk();     // Crea una nueva instancia del kiosco
        this.translator = translatorManager;         // Asigna el gestor de traducciones
    }

    public void clearScreen(){
        this.setTitle((String)null);                // Limpia el título
        this.setImage((String)null);                // Limpia la imagen
        this.setDescription((String)null);          // Limpia la descripción
        // Limpia todas las opciones disponibles en la pantalla
        for (char cont = 'A'; cont <= 'H'; cont++) 
            this.setOption(cont, (String)null);     // Limpia cada opción individualmente
    }

    // Método que espera a que el usuario pulse un botón; si no lo hace en 60 segundos,
    // el programa vuelve a la pantalla de bienvenida
    public char waitPressButton(){  
        return kiosk.waitEvent(60); // Espera 60 segundos por un evento de botón
    }

    public void wain1second(){kiosk.waitEvent(1);}

    //Al llegar a pantalla de pago exitoso (recoja su ticket)
    //volvemos a wellcome para otro consumidor
    public void timeToRefreshKiosk(){kiosk.waitEvent(7);}

    public void waitToInCard(){kiosk.waitEvent(60);}

    public void setOption(char character, String option){kiosk.setOption(character, option);}

    public void setTitle(String title){kiosk.setTitle(title);}

    public void setDescription(String description){kiosk.setDescription(description);}

    public void setMenuMode(){kiosk.setMenuMode();}

    public void setMessageMode(){kiosk.setMessageMode();}

    public void print(List<String> print){
        kiosk.print(print); // Imprime la lista en el kiosco
    }
    public void retainCreditCard(boolean retain){kiosk.retainCreditCard(retain);}

    // Expulsa una tarjeta de crédito del kiosco utilizando un identificador de tarjeta
    public void expelCreditCard(int card){// Expulsa la tarjeta identificada por "card"
        kiosk.expelCreditCard(card);
    }

    public void setImage(String imageFilePath){
        kiosk.setImage(imageFilePath); // Asigna la ruta de la imagen al kiosco
    }

    public long getCardNumber(){
        kiosk.getCardNumber(); // Obtiene el número de la tarjeta desde el kiosco
        return 0;              // Retorna 0 como valor predeterminado
    }
}

