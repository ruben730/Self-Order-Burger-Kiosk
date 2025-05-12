package Screens;

import java.io.*;
import java.time.*;//utilizo * para que quede mas simplificado el codigo
import java.time.format.DateTimeFormatter;
import Manager.*;
import Products.Product;
import urjc.UrjcBankServer; //para pagos
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.naming.CommunicationException;
import Manager.TranslatorManager;
import Products.Order;


public class PurshaseScreen implements KioskScreen {

    @Override
    public KioskScreen show(Context context) {
        SimpleKiosk kiosk = context.getKiosk();
        Order order = context.getOrder(); // Obtener la orden actual
        TranslatorManager translator = context.getTranslator(); // Obtener el traductor
        UrjcBankServer bank = new UrjcBankServer(); // Servidor del banco para procesar pagos

        // Obtenemos los datos del pedido
        String orderTextForConsumerTicket = order.getOrderTextForConsumerTicket(translator); //en el idioma del consumidor

        int totalToPay = order.getTotalAmount(); // Obtener el total a pagar en céntimos, ejem. 2000ct = 20€
        float totalToPayFloat = totalToPay / 100.0f; // Convertir el total a euros

        for (Product product : order.getProducts()) {
            System.out.println("Producto: " + product.getName() + " - Precio: " + product.getPrice());
        }
        System.out.println("Total to pay: " + totalToPayFloat); //<-- Para que veas qué pinta por terminal

        configureScreenButtons(kiosk, context); // Configura los botones de la pantalla

        // Mostramos la descripción de la pantalla de pago. Total: 87.0€
        kiosk.setDescription(orderTextForConsumerTicket + "\n" + translator.translate("Total") +": " + totalToPayFloat + " € \n" + translator.translate("Introduce la tarjeta de crédito"));

        char event = kiosk.waitPressButton();

        KioskScreen nextScreen = this; // Inicializamos la pantalla siguiente como la actual

        switch (event) {
            case 'A', 'B' -> {
                return new OrderScreen(); //en caso de cancelar pago o modificar pago se vuelve a order.
            }
            case '1' -> { //1 igual a puerto donde se mete la tarjeta.

                kiosk.retainCreditCard(false); // Retenemos la tarjeta
                long creditCardNumber = kiosk.getCardNumber(); // Obtener el número de tarjeta

                if (bank.comunicationAvaiable()) {
                    int newOrderNum = incrementOrderNum();

                    try {
                        // Primero realizamos la operación bancaria
                        bank.doOperation(creditCardNumber, totalToPay);

                        // Si no lanza excepción, se ha pagado -> ahora sí escribimos archivos
                        writeOrderToFile(order, newOrderNum);
                        writeCommandToFile(order, newOrderNum);

                        // Generamos e imprimimos el ticket del cliente
                        ArrayList<String> ticket = new ArrayList<>();
                        ticket.add("URJC BURGUER");
                        ticket.add("Nº"+ translator.translate("Pedido")+" "+ newOrderNum);
                        ticket.add("=====================");
                        ticket.add(orderTextForConsumerTicket); //el ticket debe ir en el idioma del consumidor
                        ticket.add("=====================");
                        ticket.add(totalToPayFloat + " €");
                        kiosk.print(ticket);

                        kiosk.clearScreen();
                        kiosk.setMessageMode();

                        //Mensaje de éxito
                        kiosk.setDescription(translator.translate("Pago realizado con éxito")+"!"
                                +"\n"+translator.translate("Recoja su ticket abajo")
                                +"\n\n"+translator.translate("No olvide su tarjeta.")+
                                "\n"+translator.translate("Número de pedido") +": " + newOrderNum);

                    } catch (Exception e) {
                        kiosk.clearScreen();
                        kiosk.setMessageMode();
                        kiosk.setDescription(translator.translate("Error en el pago")+": " + e.getMessage());
                        kiosk.waitToInCard();
                        return new WellcomeScreen();
                    }
                } else {
                    kiosk.clearScreen();
                    kiosk.setMessageMode();
                    kiosk.setDescription(translator.translate("Error al intentar comunicarse con su banco.")
                            + "\n"+translator.translate("Contacte con el personal."));
                    kiosk.waitToInCard();
                    return new WellcomeScreen();
                } //fin if y else

                kiosk.expelCreditCard(5); //usuario retira su tarjeta en +5 seg
                kiosk.timeToRefreshKiosk(); //en 7 seg reiniciamos a pantalla wellcome para sigueinte consumidor
                //Antes debemos limpiar la lista de los productos del usuario anterior
                //no es necesario ver si no esta vacia ya que si ha llegado hasta aqui es porque ha pagado
                //cierta cantidad (>0) de productos
                order.getProducts().clear(); // vaciamos la lista de productos para el siguiente cliente
                //aqui definimos el español como idioma antes de mostrar wellcome
                TranslatorManager translatorManager = context.getTranslator();
                translatorManager.selectLanguage("spanish");
                context.setTranslator(translatorManager);

                nextScreen = new WellcomeScreen();
                return nextScreen; //reiniciamos para siguiente persona.
            }
            default -> {
                // Mantenemos la misma pantalla por defecto
                return this;
            }
        }
    }

    private void configureScreenButtons(SimpleKiosk kiosk, Context context) {
        TranslatorManager translator = context.getTranslator(); // Obtener el traductor
        // Configuramos los botones de la pantalla
        kiosk.clearScreen(); // Limpiar la pantalla
        kiosk.setMessageMode(); // Establecer el modo de mensaje
        kiosk.setTitle("Pantalla de pago"); // Establecer el título de la pantalla
        kiosk.setOption('A', translator.translate("Modificar Pedido")); // Opción para modificar el pedido
        kiosk.setOption('B', translator.translate("Cancelar Pago")); // Opción para cancelar el pago
    }

    private void writeCommandToFile(Order order, int orderNumber) {
        // Escribimos la orden en un archivo para cocina
        String COOKERS_COMMAND_PATH = "BurgerSelfOrderKioskDemo/src/Files/CoockersCommand.txt";
        try {
            File file = new File(COOKERS_COMMAND_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("========== COMANDA =========");
            bw.newLine();
            bw.write("Número de pedido: " + orderNumber);
            bw.newLine();
            bw.write("-----------------------------");
            bw.newLine();
            bw.write("            PEDIDO");
            bw.newLine();
            bw.write("-----------------------------");
            bw.newLine();

            // Usamos un Map para agrupar productos y sus cantidades
            Map<Product, Integer> productQuantityMap = new HashMap<>();
            // Contamos la cantidad de cada producto
            for (Product product : order.getProducts()) {
                productQuantityMap.merge(product, 1, Integer::sum);
            }
            // Pintamos cada producto solo una vez con su cantidad total
            for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
                Product product = entry.getKey();
                int totalQuantity = entry.getValue();
                bw.write("-  " + product.getName() + "  x" + totalQuantity); //-Alitas x9
                bw.newLine();
            }
            bw.write("======== FIN COMANDA ========");
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            WellcomeScreen wellcomeScreen = new WellcomeScreen();
            //wellcomeScreen.show();
            e.printStackTrace();
        }
    }
    public void writeOrderToFile(Order order, int newOrderNum) {
        // Escribimos la orden en un archivo para cocina
        String ORDERS_FILE_PATH = "BurgerSelfOrderKioskDemo/src/Files/Orders";
        try {
            File file = new File(ORDERS_FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("========== PEDIDO =========");
            bw.newLine();
            bw.write("Número de pedido: " + newOrderNum);
            bw.newLine();
            bw.write("-----------------------------");
            bw.newLine();
            bw.write("            PEDIDO");
            bw.newLine();
            bw.write("-----------------------------");
            bw.newLine();

            // Usamos un Map para agrupar productos y sus cantidades
            Map<Product, Integer> productQuantityMap = new HashMap<>();
            // Contamos la cantidad de cada producto
            for (Product product : order.getProducts()) {
                productQuantityMap.merge(product, 1, Integer::sum);
            }
            // Pintamos cada producto solo una vez con su cantidad total
            for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
                Product product = entry.getKey();
                int totalQuantity = entry.getValue();
                bw.write("-  " + product.getName() + "  x" + totalQuantity); //-Alitas x4
                bw.newLine();
            }
            int totalToPay = order.getTotalAmount();
            float totalToPayFloat = totalToPay / 100.0f;
            bw.write("         TOTAL "+totalToPayFloat+ "€");
            bw.newLine();
            bw.write("======== FIN PEDIDO ========");
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            WellcomeScreen wellcomeScreen = new WellcomeScreen();
            //wellcomeScreen.show();
            e.printStackTrace();
        }
    }
    private int incrementOrderNum(){

        String ACTUAL_ORDER_PATH = "BurgerSelfOrderKioskDemo/src/Files/ActualOrder.txt";

        int orderNum;
        LocalDate lastResetDate;

        // Leer el número de orden y la fecha del último reinicio
        try (BufferedReader reader = new BufferedReader(new FileReader(ACTUAL_ORDER_PATH))) {
            String line = reader.readLine();
            System.out.println("Contenido leído del archivo: " + line); //<- para que veas qué pinta por terminal

            String[] parts = line.split(",");
            orderNum = Integer.parseInt(parts[0]);
            lastResetDate = LocalDate.parse(parts[1]);

            System.out.println("Número de pedido leído: " + orderNum);  //<- para que veas qué pinta por terminal
            System.out.println("Última fecha de reinicio leída: " + lastResetDate);  //<- para que veas qué pinta por terminal

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Hora y fecha actual
        LocalTime nowTime = LocalTime.now();
        LocalDate today = LocalDate.now();

        System.out.println("Hora actual: " + nowTime);
        System.out.println("Fecha actual: " + today);

        // Verificamos si ya pasaron las 6 AM y aún no se reinició hoy los números de pedido
        //A las 06:00 de todos los días ActualOrder se reinicia a 0.
        if (nowTime.isAfter(LocalTime.of(6, 0)) && !lastResetDate.equals(today)) {
            System.out.println(">> Reinicio diario detectado. Reiniciando número de orden a 0.");
            orderNum = 0;
            lastResetDate = today;
        } else {
            orderNum++;
            System.out.println(">> Incrementando número de pedido. Nuevo valor: " + orderNum);
        }

        // Guardamos el nuevo número de orden y la fecha del último reinicio
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACTUAL_ORDER_PATH))) {
            writer.write(orderNum + "," + lastResetDate.toString());
            System.out.println("Guardado en archivo: " + orderNum + "," + lastResetDate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Número de pedido devuelto: " + orderNum);
        System.out.println("--------------------------------------------------");
        return orderNum;
    }
}
