# 📊 Expense Tracker CLI

¡Hola! 👋 Bienvenido/a al Expense Tracker, una sencilla aplicación de línea de comandos para llevar un registro de tus gastos.

## ✨ Tecnologías Utilizadas

Este proyecto está construido con amor y las siguientes tecnologías:

* ☕ **Java 21**: La versión más reciente de Java LTS.
* 🌱 **Spring Boot 3**: Para facilitar la creación de la aplicación y la configuración.
* 🐚 **Spring Shell**: Para crear una interfaz de línea de comandos interactiva.
* 📜 **Maven**: Para la gestión de dependencias y construcción del proyecto.
* 💾 **JSON**: Para almacenar los datos de los gastos de forma persistente en un archivo (`expenses.json`).

##  Dependencias Principales

* `spring-boot-starter-web`: Soporte web básico (aunque principalmente es CLI).
* `spring-boot-starter`: Core de Spring Boot.
* `lombok`: Para reducir código boilerplate en modelos Java.
* `spring-boot-starter-test`: Para pruebas unitarias.
* `jackson-datatype-jsr310`: Soporte para tipos de fecha y hora de Java 8+ en JSON.
* `spring-shell-starter`: El corazón de la interfaz de línea de comandos.    

* `spring-boot-starter-web`: Soporte web básico (aunque principalmente es CLI).
* `spring-boot-starter`: Core de Spring Boot.
* `lombok`: Para reducir código boilerplate en modelos Java.
* `spring-boot-starter-test`: Para pruebas unitarias.
* `jackson-datatype-jsr310`: Soporte para tipos de fecha y hora de Java 8+ en JSON.
* `spring-shell-starter`: El corazón de la interfaz de línea de comandos.

## 📁 Estructura del Proyecto (Simplificada)

Aquí tienes una vista general de cómo está organizado el código fuente:

```bash
expense_tracker/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── expense_tracker/
│                   ├── cli/          # ⌨️ Comandos de la Shell
│                   │   └── ExpenseCommands.java
│                   ├── exceptions/   # ⚠️ Clases de Excepción
│                   │   ├── ExpenseNotFoundException.java
│                   │   └── ExpenseStorageException.java
│                   ├── model/        # 📝 Modelo de Datos
│                   │   └── Expense.java
│                   ├── repository/   # 💾 Acceso a Datos
│                   │   ├── ExpenseRepository.java
│                   │   └── FileExpenseRepository.java
│                   ├── service/      # ⚙️ Lógica de Negocio
│                   │   └── ExpenseService.java
│                   └── ExpenseTrackerApplication.java # ▶️ Punto de Entrada
├── pom.xml           # 📄 Archivo de configuración de Maven (implícito por dependencias)
└── expenses.json     # 🗃️ Archivo donde se guardan los gastos (se crea automáticamente)
```

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

▶️ Cómo Ejecutar la Aplicación
Construir el JAR: Asegúrate de tener Maven instalado y ejecuta el siguiente comando en la raíz del proyecto (donde está el pom.xml):

Bash

mvn clean package -DskipTests
Esto generará un archivo .jar en el directorio target/. El nombre será algo como expense_tracker-0.0.1-SNAPSHOT.jar.

Ejecutar el JAR: Abre una terminal o línea de comandos, navega hasta el directorio target/ y ejecuta:

Bash

java -jar <nombre-del-archivo>.jar
(Reemplaza <nombre-del-archivo>.jar con el nombre real del archivo JAR generado).

¡Interactúa! Una vez ejecutado, verás el prompt de Spring Shell (shell:>) y podrás empezar a usar los comandos.
