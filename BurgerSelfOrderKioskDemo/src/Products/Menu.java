/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Products;
import java.io.*;
import java.util.List;


public class Menu implements Product {

    private String DISCOUNT_FILE_PATH = "BurgerSelfOrderKioskDemo/src/Files/Discounts.txt";
    private List<IndividualProduct> products; // Lista de productos individuales incluidos en el menú
    private int final_price; //precio final
    private String menu_name; // Nombre del menú

    public Menu(List<IndividualProduct> products, int final_price, String menuName) {
        this.products = products; // El menu es una lista de productos
        this.menu_name = menuName;
        this.final_price = final_price; // Asigna el precio final al menu
    }

    public void setPrice(int final_price){ this.final_price = final_price;}

    public int getPrice() {return this.final_price;}

    public void setMenuName(String menuName) { this.menu_name = menuName; }

    public String getName() {
        return this.menu_name; // Devuelve un nombre genérico para el menú
    }

    public IndividualProduct getProduct(int index) {
        return products.get(index); // Devuelve el producto en el índice especificado
    }

    public int getMenuDiscount() {
        int discount_readed;

        try (BufferedReader reader = new BufferedReader(new FileReader(DISCOUNT_FILE_PATH))) {
            String line = reader.readLine();

            discount_readed = Integer.parseInt(line.trim());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return discount_readed;
    }
}

