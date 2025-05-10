package Manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TranslatorManager {

    private String currentLanguage;  // Lenguaje que está activo en ese momento
    private final Map <String, Translator> languages; // Diccionario que asocia el nombre del idioma con su respectivo Translator
   
    /**
     * Constructor de la clase TranslatorManager.
     * Establece el idioma predeterminado como "español" y configura los idiomas disponibles en el diccionario.
     * @throws FileNotFoundException Si no se encuentra el archivo de traducción correspondiente.
     * @throws IOException Si ocurre algún fallo al leer los archivos de traducción.
     */
    public TranslatorManager() throws FileNotFoundException, IOException {
        currentLanguage = "spanish"; // Idioma predeterminado (español)
        languages = new HashMap<>();  // Crea el diccionario para guardar los objetos Translator de cada idioma
        initializeTranslators();  // Llama a este método para crear y asignar los traductores en el diccionario
    }

    /**
     * Inicializa los objetos Translator para cada idioma disponible
     * y los coloca en el diccionario de idiomas.
     * @throws FileNotFoundException Si algún archivo de traducción no está disponible.
     * @throws IOException Si ocurre un error durante la lectura de los archivos de traducción.
     */
    private void initializeTranslators() throws FileNotFoundException, IOException {
        // Crea un Translator para el idioma español y lo añade al diccionario
        languages.put("spanish", new Translator("BurgerSelfOrderKioskDemo/src/Files/spanish"));
        // Crea un Translator para el idioma inglés y lo añade al diccionario
        languages.put("english", new Translator("BurgerSelfOrderKioskDemo/src/Files/english"));
        // Crea un Translator para el idioma catalán y lo añade al diccionario
        languages.put("catalan", new Translator("BurgerSelfOrderKioskDemo/src/Files/catalan"));
        // Crea un Translator para el idioma portugués y lo añade al diccionario
        languages.put("portuguese", new Translator("BurgerSelfOrderKioskDemo/src/Files/portuguese"));
    }

    /**
     * Actualiza el idioma activo a uno de los disponibles.
     * @param languageSelected El idioma que se va a seleccionar (por ejemplo, "ingles", "catalan", "portugues").
     */
    public void selectLanguage(String languageSelected)  {
        currentLanguage = languageSelected; // Modifica el atributo currentLanguage para reflejar el idioma elegido
    }
    
    /**
     * Realiza la traducción de un texto al idioma activo utilizando el objeto Translator adecuado.
     * @param text El texto que debe ser traducido.
     * @return La traducción del texto en el idioma activo.
     */
    public String translate(String text) {
        // Recupera el Translator asociado con el idioma actualmente activo desde el diccionario
        Translator translator = languages.get(currentLanguage);
        // Retorna la traducción del texto usando el Translator correspondiente
        return translator.translate(text);
    }
}
