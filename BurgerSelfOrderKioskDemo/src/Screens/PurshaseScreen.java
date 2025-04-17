package Screens;

import java.io.*;
import java.time.*;//utilizo * para que quede mas simplificado el codigo
import java.time.format.DateTimeFormatter;
import Manager.*;
import urjc.UrjcBankServer; //para pagos
import java.util.ArrayList;
import javax.naming.CommunicationException;
import Manager.TranslatorManager;
import Products.Order;
/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */ 
public class PurshaseScreen implements KioskScreen {

    @Override 
    public KioskScreen show(Context context) {
        SimpleKiosk kiosk = context.getKiosk();
        Order order = context.getOrder(); // Obtener la orden actual
        TranslatorManager translator = context.getTranslator(); // Obtener el traductor
        UrjcBankServer bank = new UrjcBankServer(); // Servidor del banco para procesar pagos

        // Obtenemos los datos de la orden
        String orderToTxt = order.getOrderText(); // Obtener el texto de la orden
        int totalToPay = order.getTotalAmount(); // Obtener el total a pagar en céntimos
        float totalToPayFloat = totalToPay / 100.0f; // Convertir el total a euros

        configureScreenButtons(kiosk); // Configura los botones de la pantalla

        // Mostramos la descripción inicial
        kiosk.setDescription(orderToTxt + "\n Total: " + totalToPayFloat + " € \n" + translator.translate("Introduce la tarjeta de crédito"));

        char event = kiosk.waitPressButton();

        switch (event) {
            case 'A' -> {
                return new OrderScreen();
            }
            case 'B' -> {
                return new WellcomeScreen();
            }

            case '1' -> {
                // Validación de la tarjeta de crédito
                kiosk.retainCreditCard(false); // Retenemos la tarjeta
                long creditCardNumber = kiosk.getCardNumber(); // Obtener el número de tarjeta

                if (bank.comunicationAvaiable()) { // Si la comunicación con el banco está disponible
                    int newOrderNum;
                    try {
                        newOrderNum = incrementOrderNum(); // Incrementamos el número de pedido
                    } catch (IOException e) {
                        newOrderNum = -1; // En caso de error devolvemos un valor por defecto
                    }
                    writeOrderToFile(order, newOrderNum); // Escribimos la orden en el archivo

                    // Generamos el ticket
                    ArrayList<String> ticket = new ArrayList<>();
                    ticket.add("Nº " + newOrderNum);
                    ticket.add("=====================");
                    ticket.add(orderToTxt); // Agregamos el contenido de la orden
                    ticket.add("=====================");
                    ticket.add(totalToPayFloat + " €"); // Agregamos el total a pagar

                    kiosk.print(ticket); // Imprimimos el ticket

                    try {
                        // Realizamos la operación bancaria
                        bank.doOperation(creditCardNumber, totalToPay);
                        kiosk.clearScreen();
                        kiosk.setMessageMode();
                        kiosk.setDescription("Pago realizado con éxito. \nRecoja su ticket abajo\nNúmero de pedido: " + newOrderNum);
                        kiosk.waitToInCard(); // Pausa de un segundo

                    } catch (CommunicationException e) {
                        // Mostramos un error en caso de fallo de comunicación con el banco
                        kiosk.clearScreen();
                        kiosk.setMessageMode();
                        kiosk.setDescription("Error: " + e);
                        kiosk.waitToInCard();
                    }
                } else {
                    // Manejo de error por falta de conexión bancaria
                    kiosk.clearScreen();
                    kiosk.setMessageMode();
                    kiosk.setDescription("Error al intentar comunicarse con su banco. \nContacte al personal.");
                    kiosk.waitToInCard();
                    return new WellcomeScreen();
                }
                kiosk.expelCreditCard(5); // Expulsamos la tarjeta tras 5 segundos
                return new WellcomeScreen();
            }

            default -> {
                // Mantenemos la misma pantalla por defecto
                return this;
            }
        }
    }

    private int incrementOrderNum() throws IOException {
        // Método para incrementar el número de la orden
        String ACTUAL_ORDER_PATH = "BurgerSelfOrderKioskDemo/src/Files/ActualOrder.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(ACTUAL_ORDER_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(ACTUAL_ORDER_PATH))) {

            int orderNum = Integer.parseInt(reader.readLine()) + 1;

            if (orderNum == 100) {
                orderNum = 0; // Reiniciamos si llegamos a 100
            }
            writer.write(String.valueOf(orderNum)); // Escribimos el nuevo número de pedido
            return orderNum;
        }
    }

    private void configureScreenButtons(SimpleKiosk kiosk) {
        // Configuramos los botones de la pantalla
        kiosk.clearScreen(); // Limpiar la pantalla
        kiosk.setMessageMode(); // Establecer el modo de mensaje
        kiosk.setTitle("Pantalla de pago"); // Establecer el título de la pantalla
        kiosk.setOption('A', "Modificar pedido"); // Opción para modificar el pedido
        kiosk.setOption('B', "Cancelar pago"); // Opción para cancelar el pago
    }

    private void writeOrderToFile(Order order, int orderNumber) {
        // Escribimos la orden en un archivo para cocina
        String COOKERS_COMMAND_PATH = "BurgerSelfOrderKioskDemo/src/Files/CoockersCommand.txt";
        try {
            File file = new File(COOKERS_COMMAND_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("========== COMANDA ==========");
            bw.newLine();
            bw.write("Número de pedido: " + orderNumber);
            bw.write(order.getOrderNumber());
            bw.newLine();
            bw.write("========== FIN COMANDA ==========");
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            WellcomeScreen wellcomeScreen = new WellcomeScreen();
            //wellcomeScreen.show(context);
            e.printStackTrace();
        }
    }
}//End.
