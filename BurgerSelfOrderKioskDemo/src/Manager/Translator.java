package Manager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
/**
 *
 * @author  Victor Oliveira, Rubén Ruiz y Ariel Rodríguez
 */
public class Translator {
    
    private final Map <String, String> translations; // Diccionario que guarda las traducciones: clave (texto en español) y valor (texto traducido)
    private final String language; // Idioma de destino para la traducción de los textos


    public Translator(String language) throws FileNotFoundException, IOException {
        this.language = language;
        translations = new HashMap<>();
        loadTranslationsFromDisk(); // Llama al método para cargar las traducciones desde un archivo en disco
    }

    private void loadTranslationsFromDisk() throws FileNotFoundException, IOException {
        String fileName = language + ".txt"; // Construye el nombre del archivo usando el idioma y agregando ".txt"
        FileReader reader = new FileReader(fileName); // Abre el archivo de traducción para su lectura
        BufferedReader buffer = new BufferedReader(reader); // Crea un buffer para leer el archivo línea por línea
        
        String originalString = buffer.readLine(); // Lee la primera línea del archivo (texto en español)
        String translatedString = buffer.readLine(); // Lee la segunda línea del archivo (traducción)
        
        while (originalString != null) {
            translations.put(originalString, translatedString); // Añade el par texto original y traducción al mapa
            originalString = buffer.readLine(); // Lee la siguiente línea (nuevo texto en español)
            translatedString = buffer.readLine(); // Lee la siguiente línea (nueva traducción)
        } 
    }

    public String translate(String text) {
        String translation = translations.get(text);
        if (translation != null)
            return translation;
        else
            return text;
    }
}

