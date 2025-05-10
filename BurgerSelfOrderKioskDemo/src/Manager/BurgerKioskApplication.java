package Manager;

import java.io.IOException;
/**
 * @authors  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */
public class BurgerKioskApplication {
    public static void main(String[] args) throws IOException {
        KioskManager kioskManager = new KioskManager();
        kioskManager.start();
    }
}
