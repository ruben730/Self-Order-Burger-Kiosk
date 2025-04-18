package Manager;

import java.io.*;
import java.util.Scanner;

import Products.Order;
import Products.MenuCard;

/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 * Clase Context: gestiona el contexto del sistema incluyendo la configuración del kiosco,
 * la traducción de textos, el menú, y los pedidos.
 */
public class Context {

    // Atributos privados para gestionar el estado del contexto
    private SimpleKiosk kiosk;
    private TranslatorManager translator = new TranslatorManager();
    private Order order = new Order(5);
    private MenuCard menuCard;
    public int orderNumber;
    private int kioskNumber;
    private int numberOfKiosks;
    private final String ORDER_FILE_PATH = "BurgerSelfOrderKioskDemo/src/Files/ActualOrder.txt";
    private final String KIOSK_CONFIG_FILE_PATH = "BurgerSelfOrderKioskDemo/src/Files/KioskConfiguration.txt";

    // Constructor
    public Context() throws IOException { //Context = Initialize

        this.translator = new TranslatorManager();
        this.kiosk = new SimpleKiosk(this.translator);
        this.menuCard = MenuCard.loadFromDisk();

        try (InputStream stream = new FileInputStream(KIOSK_CONFIG_FILE_PATH);
             Scanner scanner = new Scanner(stream)) {
            if (scanner.hasNextLine()) {
                this.kioskNumber = Integer.parseInt(scanner.nextLine().trim());
            }
            if (scanner.hasNextLine()) {
                this.numberOfKiosks = Integer.parseInt(scanner.nextLine().trim());
            }
            System.out.println("Configuración cargada: Kiosco #" + kioskNumber + ", Total kioscos: " + numberOfKiosks);
        } catch (FileNotFoundException e) {
            // Manejo de error si el archivo de configuración no se encuentra
            System.err.println("Archivo de configuración no encontrado: " + KIOSK_CONFIG_FILE_PATH);
        } catch (NumberFormatException e) {
            // Manejo de error si hay un formato incorrecto en el archivo de configuración
            System.err.println("Formato incorrecto en el archivo de configuracion.");
        }

        File orderFile = new File(ORDER_FILE_PATH);
        if (!orderFile.exists() || orderFile.length() == 0) {
            // Si el archivo no existe o está vacío, inicializar el número de pedido a 1
            try (Writer writer = new FileWriter(orderFile)) {
                writer.write("1");
                this.orderNumber = 1; // Establecer número de pedido inicial
                System.out.println("Número de pedido inicializado a 1.");
            } catch (IOException e) {
                // Manejo de error al crear o escribir en el archivo
                System.err.println("Error al crear o escribir en ActualOrder.txt: " + e.getMessage());
            }

        } else {// si sí existe, actualizamos Número de pedido actual y el resto de cosas.
            // Leer el número de pedido actual desde el archivo
            try (InputStream orderStream = new FileInputStream(orderFile);
                 Scanner orderScanner = new Scanner(orderStream)) {
                if (orderScanner.hasNextLine()) {
                    this.orderNumber = Integer.parseInt(orderScanner.nextLine().trim());
                    System.out.println("Número de pedido actual: " + this.orderNumber);
                } else {
                    // Si el archivo está vacío, inicializar el número de pedido a 1
                    System.err.println("ActualOrder.txt está vacío. Se inicializará el número de pedido a 1.");
                    this.orderNumber = 1;
                }
            } catch (IOException | NumberFormatException e) {
                // Manejo de error al leer o procesar el archivo
                System.err.println("Error al leer ActualOrder.txt: " + e.getMessage());
                this.orderNumber = 1;
            }
        }
    }

    public TranslatorManager getTranslator() {
        return translator; // Devuelve el gestor de traducciones actual
    }
    public void setTranslator(TranslatorManager translator) {this.translator = translator;}

    public SimpleKiosk getKiosk() {
        return kiosk; // Devuelve la instancia de SimpleKiosk
    }

    public Order getOrder() {
        return order; // Devuelve el pedido actual
    }
    public void setOrder(Order order) {this.order = order;}

    public MenuCard getMenuCard() {
        return menuCard; // Devuelve la carta de menú cargada
    }

    public void resetOrder() {this.order = null; }
}

