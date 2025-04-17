# 🍔 Self-Ordering Kiosk for a Burger Restaurant (KPH)

**Proyecto desarrollado para la asignatura de _Programación Orientada a Objetos_ (curso 2024/2025)**

---

## 📘 Descripción General

Este proyecto simula el funcionamiento de un kiosco de autoservicio en una hamburguesería. El sistema guía al usuario a través de diferentes pantallas para seleccionar el idioma, elegir productos individuales o menús, revisar su pedido y efectuar el pago con tarjeta mediante un sistema bancario simulado.

El diseño del sistema sigue una arquitectura orientada a objetos, con una separación clara de responsabilidades entre clases, facilitando su mantenibilidad, comprensión y escalabilidad.

> **WARNING:** Asegúrate de tener correctamente configurado el entorno de desarrollo y los ficheros de traducción y productos (por ejemplo, `catalog.xml` y los archivos de idioma) antes de ejecutar el programa.

---

## 🧩 Arquitectura del Sistema

### Clases Principales

- `BurgerKioskApplication`: Clase principal que lanza la aplicación.
- `KioskManager`: Controla el flujo principal del programa.
- `Context`: Agrupa los recursos necesarios (traductor, carta, pedido, etc.).
- `SimpleKiosk`: Capa intermedia entre el simulador y la lógica del programa.
- `TranslatorManager` / `Translator`: Manejo de traducciones e idiomas.
- `MenuCard`: Lee y estructura la carta del restaurante.
- `Order`: Representa el pedido del cliente.

### Interfaz de Usuario (`KioskScreen` y derivadas)

- `WellcomeScreen`: Pantalla de inicio.
- `IdiomScreen`: Selección de idioma.
- `OrderScreen`: Gestión general del pedido.
- `SectionScreen`, `ProductScreen`, `MenuScreen`: Navegación por carta y productos.
- `CarouselScreen`: Base para pantallas con muchos elementos.
- `PurcheaseScreen`: Confirmación y pago del pedido.

---

## 🔁 Flujo del Usuario

1. Pantalla de bienvenida.
2. Selección de idioma.
3. Inicio del pedido.
4. Selección de tipo de producto o menú.
5. Navegación tipo carrusel para seleccionar productos.
6. Revisión del pedido.
7. Pantalla de pago (con simulación de tarjeta).
8. Impresión del ticket y almacenamiento del pedido.

---

## 🌐 Internacionalización

El sistema permite cambiar de idioma en cualquier momento. Las cadenas se traducen automáticamente utilizando un sistema de diccionarios gestionado por `TranslatorManager`. Para cada idioma soportado se requiere un fichero de traducción con las frases clave.

---

## 💾 Persistencia

- Los pedidos se almacenan en disco para su posterior procesamiento.
- El número de pedido se guarda y actualiza entre ejecuciones.
- El sistema registra cada pedido en un fichero junto con los productos seleccionados.

---

## 🧪 Simuladores Utilizados

- `BurgerSelfOrderKiosk`: Interfaz gráfica simulada del kiosco real.
- `UrjcBankServer`: Simulación del sistema de pago mediante tarjeta de crédito.

---

## 👨‍💻 Autores

- **Víctor Hugo Oliveira Petroceli**
- **Rubén Ruiz Martín**
- **Ariel Rodríguez Lozano**
