# Expense Tracker CLI 💸

¡Una sencilla aplicación de línea de comandos (CLI) para registrar y gestionar tus gastos diarios! Construida con Java y Spring Boot.

## ✨ Tecnologías y Dependencias

Este proyecto utiliza las siguientes tecnologías principales:

* ☕ **Java 21**
* 🚀 **Spring Boot 3.x** (Framework principal)
* 🐚 **Spring Shell** (Para la interfaz de línea de comandos interactiva)
* 🐘 **Apache Maven** (Gestor de dependencias y build)
* 📜 **Jackson Databind** (Para la serialización/deserialización de JSON)
* 🛠️ **Lombok** (Para reducir código boilerplate en modelos Java)


## 📂 Estructura del Proyecto

```bash
📂 expense-tracker/
├── 📂 src/
│   └── 📂 main/
│       ├── 📂 java/
│       │   └── 📂 com/
│       │       └── 📂 expense_tracker/
│       │           ├── ✨ ExpenseTrackerApplication.java  # Punto de entrada Spring Boot
│       │           ├── 📂 cli/
│       │           │   └── 💻 ExpenseCommands.java      # Define los comandos de la shell
│       │           ├── 📂 model/
│       │           │   └── 🧱 Expense.java              # Clase de datos para un Gasto
│       │           ├── 📂 repository/
│       │           │   ├── 💾 ExpenseRepository.java    # Interfaz del repositorio
│       │           │   └── 💾 FileExpenseRepository.java # Implementación con archivo JSON
│       │           ├── 📂 service/
│       │           │   └── 🛠️ ExpenseService.java       # Lógica de negocio
│       │           └── 📂 exceptions/                 # Excepciones personalizadas
│       │               ├── ⚠️ ExpenseNotFoundException.java
│       │               └── ⚠️ ExpenseStorageException.java
│       └── 📂 resources/
│           └── 🔧 application.properties          # Configuración de Spring (si aplica)
├── 📜 expenses.json                       # Archivo donde se guardan los gastos (se crea al ejecutar)
└── ⚙️ pom.xml                             # Archivo de configuración de Maven
```
## 🚀 Cómo Empezar

Sigue estos pasos para ejecutar la aplicación en tu máquina local:

1.  **Prerrequisitos:**
    * Tener instalado Java Development Kit (JDK) 21 o superior.
    * Tener instalado Apache Maven.
2.  **Clonar el Repositorio:**
    ```bash
    git clone <URL-DE-TU-REPOSITORIO>
    cd expense-tracker
    ```
3.  **Compilar el Proyecto:**
    Usa Maven para compilar y empaquetar la aplicación:
    ```bash
    mvn clean package
    ```
    Esto generará un archivo `.jar` en el directorio `target/`.
4.  **Ejecutar la Aplicación:**
    Ejecuta el archivo JAR generado:
    ```bash
    java -jar target/expense-tracker-*.jar
    ```
    (Reemplaza `*` con la versión específica generada por Maven).

    Una vez ejecutado, verás el prompt de Spring Shell (`shell:>`), listo para recibir comandos.

## 💻 Comandos Disponibles

Aquí están los comandos que puedes usar en la CLI:

---

### 📝 `add` - Añadir un Gasto

Añade un nuevo registro de gasto al sistema.

* **Uso:**
    ```bash
    add --description "Descripción del gasto" --amount <monto>
    ```
* **Opciones:**
    * `--description` (Obligatorio): La descripción de qué fue el gasto (entre comillas si tiene espacios).
    * `--amount` (Obligatorio): El monto numérico del gasto.
* **Ejemplo:**
    ```bash
    shell:> add --description "Café con amigos" --amount 15.50
    ```
* **Salida Exitosa:**
    ```
    ✅ Gasto agregado correctamente: ID: 1
    ```
* **Salida de Error:**
    ```
    ❌ Error al crear el gasto: <Mensaje específico del error>
    ```

---

### 📄 `list` - Listar Gastos

Muestra una tabla con todos los gastos registrados hasta el momento.

* **Uso:**
    ```bash
    shell:> list
    ```
* **Salida (con gastos):**
    ```
    ╔══════════════════════════════════════════════════╗
    ║              📄 LISTA DE GASTOS                  ║
    ╚══════════════════════════════════════════════════╝

    ID    📅 Fecha      📝 Descripción             💰 Monto
    -------------------------------------------------------------
    1     2025-04-08   Café con amigos             $15.50
    2     2025-04-08   Supermercado                $75.20
    3     2025-04-07   Libro de Java               $35.00
    ```
  *(Nota: La fecha mostrada será la fecha en que se agregó el gasto)*
* **Salida (sin gastos):**
    ```
    ⚠️ No hay gastos registrados.
    ```

---

### 📊 `summary` - Resumen de Gastos

Calcula y muestra el total de gastos. Opcionalmente, puede filtrar por mes.

* **Uso:**
    ```bash
    summary [--month <numero_mes>]
    ```
* **Opciones:**
    * `--month` (Opcional): Número del mes (1-12) para calcular el total solo de ese mes.
* **Ejemplo (Total General):**
    ```bash
    shell:> summary
    ```
* **Salida (Total General):**
    ```
    📊 Total de todos los gastos: $125.70
    ```
* **Ejemplo (Total Mensual):**
  *(Suponiendo que hoy estamos en Abril 2025)*
    ```bash
    shell:> summary --month 4
    ```
* **Salida (Total Mensual):**
    ```
    📊 Total de gastos para Abril 2025: $90.70
    ```
* **Salida de Error:**
    ```
    ❌ Error al calcular el resumen: <Mensaje específico del error>
    ```

---

### 🗑️ `delete` - Eliminar Gasto

Elimina un gasto específico usando su ID.

* **Uso:**
    ```bash
    delete --id <ID_del_gasto>
    ```
* **Opciones:**
    * `--id` (Obligatorio): El ID numérico del gasto que deseas eliminar (puedes verlo con el comando `list`).
* **Ejemplo:**
    ```bash
    shell:> delete --id 2
    ```
* **Salida Exitosa:**
    ```
    🗑️ Gasto eliminado correctamente.
    ```
* **Salida (ID no encontrado):**
    ```
    ⚠️ No se encontró ningún gasto con ID: 2
    ```

---

### `exit` / `quit`

Para salir de la aplicación CLI.

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Si tienes ideas para mejorar la aplicación o encuentras un error, por favor abre un *Issue* o envía un *Pull Request*.

## 📄 Licencia

Este proyecto está bajo la Licencia MIT (o la licencia que prefieras). Puedes añadir un archivo `LICENSE` al repositorio.
