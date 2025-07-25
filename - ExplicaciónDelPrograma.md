###### **- Explicación del Programa: Sistema de Gestión de Tareas estilo Kanban en Java**

Este programa en Java implementa un sistema de gestión de tareas inspirado en el método Kanban, utilizando la consola como interfaz de usuario. Se trata de una aplicación sencilla pero funcional, en la cual el usuario puede agregar, modificar, mover, eliminar y listar tareas dentro de tres estados posibles: Pendientes, En Proceso y Finalizadas.



El objetivo principal es simular el comportamiento de un tablero Kanban digital de forma textual, aplicando los conceptos de listas dinámicas (ArrayList) y estructuras condicionales y repetitivas, además de validar entradas del usuario y manejar errores comunes.



###### **- Proceso de desarrollo**

Durante el proceso de realización, el código pasó por varios filtros y mejoras progresivas. Lo revisamos en múltiples ocasiones para corregir errores lógicos, prevenir situaciones inesperadas y asegurarnos de que cumpliera tanto con los requisitos técnicos solicitados como con nuestras expectativas de funcionalidad y usabilidad. Este proceso de revisión nos permitió detectar detalles importantes y reforzar el manejo de errores, garantizando una experiencia más robusta y clara para el usuario.



###### **- Inicio de sesión**

Antes de acceder al sistema, el usuario debe iniciar sesión con un nombre de usuario y una contraseña predefinidos (admin / 1234). Se permiten hasta tres intentos. Si se falla, el programa finaliza automáticamente.



Este mecanismo es sencillo pero sirve para mostrar el uso de control de flujo (while) y comparación de cadenas en Java.



###### **- Estructura de datos utilizada**

Se utilizan tres listas principales de tipo ArrayList<String>:



pendientes: contiene las tareas que aún no han comenzado.



enProceso: contiene las tareas que están en desarrollo.



finalizadas: contiene las tareas completadas.



Estas listas permiten agregar, eliminar o mover tareas de manera dinámica durante la ejecución del programa.



###### \- Menú principal

Una vez iniciado el sistema, el usuario interactúa con un menú en bucle que le permite realizar las siguientes acciones:



Agregar tarea nueva

Solicita al usuario el nombre de una tarea y la agrega a la lista de "Pendientes".



Mover tarea a En Proceso

Permite seleccionar una tarea de la lista "Pendientes" y moverla a "En Proceso".



Finalizar tarea

Permite seleccionar una tarea en proceso y moverla a "Finalizadas".



Modificar tarea

Permite editar el nombre de una tarea que esté en "Pendientes" o "En Proceso".



Eliminar tarea

Permite eliminar una tarea específica o todas las tareas de una lista elegida por el usuario.



Listar tareas

Muestra las tareas de una lista específica o de todas, indicando el total de tareas.



Salir

Finaliza el programa.



Cada opción es ejecutada mediante una estructura switch, lo que facilita su lectura y mantenimiento.



###### **- Funciones destacadas**

✅ agregarTarea()

Agrega una tarea ingresada por el usuario a la lista de pendientes.



🔀 moverTarea()

Permite mover una tarea desde una lista de origen a una lista de destino, como de "Pendientes" a "En Proceso", o de "En Proceso" a "Finalizadas".



✏️ modificarTarea()

Permite cambiar el nombre de una tarea existente en "Pendientes" o "En Proceso", utilizando el índice o el nombre para identificarla.



❌ eliminarTarea()

Permite eliminar una o todas las tareas de una lista seleccionada. Se implementaron validaciones para evitar errores al operar sobre listas vacías y para mejorar la experiencia de usuario.



📃 listarTareas()

Muestra las tareas de una lista específica o de todas las listas. También muestra cuántas tareas hay en total.



🔍 buscarTarea()

Se utiliza para encontrar el índice de una tarea, ya sea por su nombre (ignorando mayúsculas/minúsculas) o por su posición numérica (empezando en 1). Es una función de apoyo fundamental para modificar, mover o eliminar tareas.



###### **- Manejo de errores**

El programa implementa un control básico de errores:



Usa try-catch para evitar que entradas no numéricas generen fallos cuando se espera un número.



Utiliza validaciones para impedir acciones con listas vacías.



Captura NoSuchElementException si el usuario cierra inesperadamente la entrada.



Siempre se cierra el Scanner al final para liberar recursos.



###### **- Conclusión**

Este programa representa un proyecto introductorio sólido en Java, ideal para aplicar:



Uso de colecciones (ArrayList)



Entrada/salida de datos con Scanner



Validaciones y manejo de errores



Estructuras condicionales (if, switch)



Modularidad mediante funciones



Además, ilustra cómo estructurar una aplicación orientada a tareas en consola, y cómo organizar los estados de un proceso de trabajo con lógica clara.

Gracias al proceso de revisión y mejora continua, logramos crear una herramienta funcional y adaptable, que puede crecer fácilmente si se desea escalar o trasladar a una interfaz gráfica.

