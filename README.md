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

🚀 Comandos Disponibles
Puedes interactuar con la aplicación usando los siguientes comandos una vez que la ejecutes:

Comando	Descripción	Entradas (Opciones)	Salida de Ejemplo
add --description "Nombre" --amount <monto>	📝 Añade un nuevo gasto.	--description: Descripción (String), --amount: Monto (Número)	✅ Gasto agregado correctamente: ID: 1
list	📄 Lista todos los gastos registrados.	Ninguna	Tabla formateada con ID, Fecha, Descripción y Monto de todos los gastos.
summary [--month <numero_mes>]	📊 Muestra el total de gastos (general o por mes).	--month (Opcional): Número del mes (1-12)	📊 Total de gastos para Abril 2025: $55.20 o 📊 Total de todos los gastos: $120.50
delete --id <id_gasto>	🗑️ Elimina un gasto específico usando su ID.	--id: ID numérico del gasto a eliminar	🗑️ Gasto eliminado correctamente. o ⚠️ Gasto no encontrado con ID: 5
help	❓ Muestra la ayuda con todos los comandos disponibles.	Ninguna	Lista de comandos y sus descripciones.
exit / quit	👋 Cierra la aplicación.	Ninguna	(Cierra la aplicación)
Nota: Los iconos y colores pueden variar ligeramente dependiendo de tu terminal.

▶️ Cómo Ejecutar la Aplicación
Construir el JAR: Asegúrate de tener Maven instalado y ejecuta el siguiente comando en la raíz del proyecto (donde está el pom.xml):

Bash

mvn clean package -DskipTests
Esto generará un archivo .jar en el directorio target/. El nombre será algo como expense_tracker-0.0.1-SNAPSHOT.jar.

Ejecutar el JAR: Abre una terminal o línea de comandos, navega hasta el directorio target/ y ejecuta:

Bash

java -jar <nombre-del-archivo>.jar
(Reemplaza <nombre-del-archivo>.jar con el nombre real del archivo JAR generado).

¡Interactúa! Una vez ejecutado, verás el prompt de Spring Shell (shell:>) y podrás empezar a usar los comandos listados arriba.