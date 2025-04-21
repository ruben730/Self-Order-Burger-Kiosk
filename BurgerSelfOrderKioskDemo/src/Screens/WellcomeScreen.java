/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import Manager.Context;
import Manager.SimpleKiosk;
import Manager.KioskScreen;
import Manager.TranslatorManager;
import Products.Order;

/**
 *
 * @author Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */
public class WellcomeScreen implements KioskScreen {
    /**Logo Principal**/
    private static final String LOGO_PATH = "BurgerSelfOrderKioskDemo/src/Images/Logo.png";

    @Override
    public KioskScreen show(Context context) {
        SimpleKiosk sk = context.getKiosk(); // Obtenemos el kiosco creado en el contexto
        sk.clearScreen(); // Limpiamos la pantalla del kiosco
        configureScreenButtons(context); // Configuramos los botones en la pantalla

        char event = sk.waitPressButton(); // Método waitPressButton() dentro de SimpleKiosk que espera una entrada del usuario
        
        KioskScreen nextScreen = this; // Inicializamos la pantalla siguiente como la actual

        if (event == 'B') {
            nextScreen = new OrderScreen(); // Si se presiona 'B', pasamos a la pantalla de la orden
        } else if (event == 'C') {
            nextScreen = new LanguajeScreen(); // Si se presiona 'C', pasamos a la pantalla de cambio de idioma
        }
        return nextScreen; // Retornamos la pantalla siguiente, que depende de la opción del usuario
    }

    private void configureScreenButtons(Context context) { // Método para configurar los botones en la pantalla
        TranslatorManager translator = context.getTranslator(); // Usamos el TranslatorManager del contexto
        SimpleKiosk kiosk = context.getKiosk(); // Obtenemos el kiosco del contexto

        kiosk.clearScreen(); // Limpiamos la pantalla
        kiosk.setMenuMode(); // Establecemos el kiosco en modo de menú
        kiosk.setTitle(translator.translate("Bienvenido a URJC Burger")); // Establecemos el título en la pantalla
        kiosk.setImage(LOGO_PATH); // Establecemos la imagen del logo en la pantalla
        kiosk.setDescription("\n\n"+ translator.translate("Dispuestos a hacer la mejor hamburguesa del mundo"));
        
        // Formateamos la hora actual y la mostramos en el botón 'A'
        LocalTime nowTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = nowTime.format(formatter);
        kiosk.setOption('A', formattedTime); // Establecemos la opción 'A' con la hora actual

        // Configuramos las opciones de los botones disponibles
        kiosk.setOption('B', translator.translate("Nuevo pedido"));
        kiosk.setOption('C', translator.translate("Cambiar idioma"));
    }
}


            
            

         
           
             
        

