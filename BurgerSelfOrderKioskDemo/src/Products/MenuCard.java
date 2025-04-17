/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Products;

import java.io.Serializable;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */
public class MenuCard implements Serializable {

    private static final String CATALOG_FILE_PATH = "BurgerSelfOrderKioskDemo/src/Files/Catalog.xml";
    private List<MenuCardSection> sectionList; // Lista de secciones del menú


    public MenuCard(List<MenuCardSection> sectionList) {this.sectionList = sectionList;}


    public MenuCardSection getSection(int index) {return this.sectionList.get(index);}

    public List<MenuCardSection> getSectionList() {return this.sectionList;}

    public int getNumberOfSections() {return sectionList.size();}

    /**
     * Método estático para cargar un objeto MenuCard desde un archivo XML.
     *
     * @return El objeto MenuCard cargado desde el disco, o null si ocurre un error.
     */
    public static MenuCard loadFromDisk() {
        try {
            FileInputStream fileInputStream = new FileInputStream(CATALOG_FILE_PATH);
            XMLDecoder decoder = new XMLDecoder(fileInputStream);

            // Lee el objeto MenuCard desde el archivo
            MenuCard menuCard = (MenuCard) decoder.readObject();
            decoder.close();
            return menuCard; // Devuelve el objeto deserializado

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Devuelve null si ocurre un error
    }
}



