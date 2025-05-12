package Manager;

import java.io.*;
import Screens.WellcomeScreen;

class KioskManager {
    //Este ÚNICO objeto Context será el que vaya guardando toda la información del pedido
    private Context context;

    public void start() throws IOException {
        this.context = new Context();
        WellcomeScreen ws = new WellcomeScreen();
        KioskScreen nextScreen = ws.show(context);
        while (true) {
            nextScreen = nextScreen.show(context);
        }
    }
}

