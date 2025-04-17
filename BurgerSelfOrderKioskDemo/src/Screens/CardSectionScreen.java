package Screens;

import Manager.Context;
import Manager.SimpleKiosk;
import Manager.KioskScreen;

public class CardSectionScreen implements KioskScreen{
    public KioskScreen show(Context context) {
        //configureScreenButtons(context);
        SimpleKiosk kiosk = context.getKiosk();
        char event = kiosk.waitPressButton();
        KioskScreen nextScreen = this;
     return nextScreen;
    }
}


