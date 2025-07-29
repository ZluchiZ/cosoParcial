 import java.util.ArrayList;          // 1) Importa la clase ArrayList, que permite crear listas dinámicas de elementos.
import java.util.Scanner;            // 2) Importa Scanner, que sirve para leer texto desde la consola.
public class programa_MUY_comentado { // 3) Declaración de la clase principal
   
    static Scanner scanner =        // 4) Crea un objeto global Scanner para leer todo el tiempo
        new Scanner(System.in);     //    lo que escriba el usuario por la consola.

    static ArrayList<String>       // 5) Declara tres listas globales de texto (String):
        pendientes = new ArrayList<>(),    //    - “pendientes” para tareas nuevas.
        enProceso  = new ArrayList<>(),    //    - “enProceso” para las que ya empezaron.
        finalizadas= new ArrayList<>();    //    - “finalizadas” para las que ya terminaron.

    static String usuario    = "a";         // 6) Usuario fijo (login) “a”.
    static String contrasena = "1";         // 7) Contraseña fija “1”.

    public static void main(String[] args) {// 8) Método principal: punto de entrada del programa.
        try {                               // 9) Inicia un bloque para atrapar errores inesperados.
            if (!iniciarSesion())          // 10) Llama a iniciarSesion(); si devuelve false, sale del programa.
                return;

            System.out.println(            // 11) Muestra mensaje de bienvenida.
                "Bienvenido al sistema de control de tareas estilo Kanban!");

            int opcion;                    // 12) Declara la variable que guardará la elección del usuario.

            do {                           // 13) Inicia un bucle que se repite hasta que opcion sea 7.
                mostrarMenu();             // 14) Muestra en pantalla las opciones disponibles.
                // 15) Pide al usuario un número entre 1 y 7; no avanza hasta que sea válido:
                opcion = leerEnteroConRango("", 1, 7);

                switch (opcion) {          // 16) Decide qué función ejecutar según la opción:
                    case 1 -> agregarTarea();     // 16a) Agregar nueva tarea.
                    case 2 -> moverTarea(
                                   pendientes,
                                   enProceso,
                                   "Pendiente",
                                   "En Proceso"
                               );             // 16b) Mover de Pendientes a En Proceso.
                    case 3 -> moverTarea(
                                   enProceso,
                                   finalizadas,
                                   "En Proceso",
                                   "Finalizada"
                               );             // 16c) Mover de En Proceso a Finalizada.
                    case 4 -> modificarTarea();    // 16d) Modificar el texto de una tarea.
                    case 5 -> eliminarTarea();     // 16e) Eliminar tarea(s).
                    case 6 -> listarTareas();      // 16f) Mostrar todas las tareas.
                    case 7 ->                     // 16g) Si elige 7, imprime mensaje y sale del bucle.
                        System.out.println(
                           "Saliendo del programa...");
                }

            } while (opcion != 7);       // 17) Repite hasta que opcion sea 7.

        } catch (Exception e) {         // 18) Captura cualquier excepción no prevista.
            System.out.println(         // 19) Muestra mensaje de error si algo falla.
                "Ocurrio un error inesperado: " + e.getMessage());
        } finally {                     // 20) Bloque que siempre se ejecuta al final:
            scanner.close();            // 21) Cierra el Scanner para liberar recursos.
        }
    }

    public static boolean iniciarSesion() {  // 22) Función para pedir y verificar usuario/clave.
        int intentos = 3;                    // 23) Permite un máximo de 3 intentos.

        while (intentos-- > 0) {             // 24) Bucle que resta un intento cada vez.
            System.out.print("User: ");      // 25) Pregunta el usuario.
            String u = scanner.nextLine();   // 26) Lee lo que escribe el usuario.

            System.out.print("Password: ");  // 27) Pregunta la contraseña.
            String c = scanner.nextLine();   // 28) Lee la contraseña.

            if (u.equals(usuario) &&        // 29) Compara con los datos fijos.
                c.equals(contrasena))
                return true;                // 30) Si coinciden, devuelve true (login exitoso).

            // 31) Si no coinciden, informa y sigue al siguiente intento:
            System.out.println(
                "Credenciales incorrectas. " +
                "Intentos restantes: " + intentos);
        }

        // 32) Si se acaban los intentos:
        System.out.println(
            "Demasiados intentos fallidos. Cerrando programa.");
        return false;                       // 33) Devuelve false (no pudo iniciar sesión).
    }

    public static void mostrarMenu() {      // 34) Función para imprimir el menú.
        System.out.println("\nMenu de Opciones:");
        System.out.println("1. Agregar tarea nueva");
        System.out.println("2. Mover tarea a En Proceso");
        System.out.println("3. Finalizar tarea");
        System.out.println("4. Modificar tarea");
        System.out.println("5. Eliminar tarea");
        System.out.println("6. Listar tareas");
        System.out.println("7. Salir");
    }

    public static void agregarTarea() {      // 35) Función para añadir una nueva tarea.
        System.out.print(
            "Ingrese el nombre de la nueva tarea: ");
        String tarea = scanner.nextLine();   // 36) Lee el texto de la tarea.

        System.out.println(
            "Tarea ingresada: \"" + tarea + "\"");  // 37) Muestra lo que escribió.
        System.out.print(
            "¿Esta seguro que desea agregar esta tarea? (s/n): ");
        String confirmacion =                 // 38) Lee “s” o “n”.
            scanner.nextLine().trim().toLowerCase();

        if (confirmacion.equals("s")) {      // 39) Si confirma “s”:
            pendientes.add(tarea);           // 40) Añade a la lista “pendientes”.
            System.out.println(
                "Tarea agregada a la lista de Pendientes.");
        } else {
            System.out.println(
                "Operacion cancelada. La tarea no fue agregada.");
        }
    }

    public static void moverTarea(         // 41) Función para mover tareas.
        ArrayList<String> origen,
        ArrayList<String> destino,
        String nombreOrigen,
        String nombreDestino
    ) {
        if (origen.isEmpty()) {            // 42) Si la lista origen está vacía:
            System.out.println(
                "No hay tareas en " + nombreOrigen + ".");
            return;                        // 43) Sale sin hacer nada.
        }

        int index = buscarTarea(            // 44) Llama a buscarTarea() para elegir tarea.
            origen, nombreOrigen);

        if (index != -1) {                  // 45) Si encontró un índice válido:
            String tarea = origen.get(index);// 46) Toma el nombre de la tarea.

            // 47) Pregunta confirmación antes de mover:
            System.out.println(
                "Tarea seleccionada: \"" + tarea + "\"");
            System.out.print(
                "¿Esta seguro que desea mover esta tarea de " +
                 nombreOrigen + " a " +
                 nombreDestino + "? (s/n): ");
            String confirmacion =
                scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s")) { // 48) Si confirma “s”:
                origen.remove(index);      // 49) Quita de la lista origen.
                destino.add(tarea);        // 50) Agrega a la lista destino.
                System.out.println(
                    "Tarea movida de " + nombreOrigen +
                    " a " + nombreDestino + ".");
            } else {
                System.out.println(
                    "Operacion cancelada. La tarea no fue movida.");
            }
        }
    }

    public static void modificarTarea() {   // 51) Función para renombrar una tarea.
        System.out.println(
            "¿En que lista esta la tarea a modificar?");
        System.out.println("1. Pendientes\n2. En Proceso");

        int opcion = leerEnteroConRango(    // 52) Pide 1 o 2 vali­dado.
            "Seleccione una opcion: ", 1, 2);

        ArrayList<String> lista =          // 53) Elige la lista según la opción.
            (opcion == 1) ? pendientes : enProceso;

        int index = buscarTarea(lista,     // 54) Busca la tarea en esa lista.
            "modificar");
        if (index != -1) {                 // 55) Si la encuentra:
            System.out.print(
                "Nuevo nombre de la tarea: ");
            String nuevoNombre =
                scanner.nextLine();        // 56) Lee el nuevo texto.
            lista.set(index, nuevoNombre); // 57) Reemplaza el elemento en la lista.
            System.out.println(
                "Tarea modificada correctamente.");
        }
    }

    public static void eliminarTarea() {    // 58) Función para eliminar tarea(s).
        // 59) Ofrece cuatro opciones, incluida “todas las listas”:
        int opcion = leerEnteroConRango(
            "¿De que lista desea eliminar la tarea?\n" +
            "1. Pendientes\n" +
            "2. En Proceso\n" +
            "3. Finalizadas\n" +
            "4. Todas las listas\n" +
            "Seleccione una opcion: ", 1, 4
        );

        if (opcion >= 1 && opcion <= 3) {   // 60) Si elige una lista específica:
            ArrayList<String> lista = switch (opcion) {
                case 1 -> pendientes;
                case 2 -> enProceso;
                default-> finalizadas;
            };

            if (lista.isEmpty()) {         // 61) Si la lista está vacía:
                System.out.println(
                    "No hay tareas en esta lista.");
                return;
            }

            int tipo = leerEnteroConRango( // 62) Pregunta eliminar una o todas:
                "¿Desea eliminar una tarea especifica (1) o todas (2)?: ",
                1, 2
            );

            if (tipo == 1) {               // 63) Si es específica:
                int index = buscarTarea(   // 64) Llama a buscarTarea para elegir índice.
                    lista, "eliminar");
                if (index != -1) {
                    String tareaAEliminar = lista.get(index); // 65) Nombre tarea.
                    System.out.println(
                        "Tarea seleccionada: \"" +
                        tareaAEliminar + "\"");
                    System.out.print(
                        "¿Esta seguro que desea eliminar esta tarea? (s/n): ");
                    String confirmacion =
                        scanner.nextLine().trim().toLowerCase();

                    if (confirmacion.equals("s")) {
                        lista.remove(index);      // 66) Elimina esa tarea.
                        System.out.println(
                            "Tarea eliminada correctamente.");
                    } else {
                        System.out.println(
                            "Operacion cancelada. La tarea no fue eliminada.");
                    }
                }
            } else {                       // 67) Si es “todas” de esa lista:
                System.out.print(
                    "¿Esta seguro que desea eliminar TODAS las tareas de esta lista? (s/n): ");
                String confirmacion =
                    scanner.nextLine().trim().toLowerCase();

                if (confirmacion.equals("s")) {
                    lista.clear();         // 68) Vacía la lista.
                    System.out.println(
                        "Todas las tareas han sido eliminadas.");
                } else {
                    System.out.println(
                        "Operacion cancelada. No se elimino ninguna tarea.");
                }
            }
        }

        if (opcion == 4) {               // 69) Si elige “Todas las listas”:
            System.out.print(
                "¿Esta seguro que desea eliminar TODAS las tareas de TODAS las listas? (s/n): ");
            String confirmacion =
                scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s")) {
                pendientes.clear();    // 70) Borra todas de “pendientes”.
                enProceso.clear();     // 71) Borra todas de “enProceso”.
                finalizadas.clear();   // 72) Borra todas de “finalizadas”.
                System.out.println(
                    "Todas las tareas de todas las listas han sido eliminadas.");
            } else {
                System.out.println(
                    "Operacion cancelada. No se elimino ninguna tarea.");
            }
        }
    }

    public static void listarTareas() {     // 73) Función para mostrar tareas.
        System.out.println(
            "¿Que lista desea visualizar?");
        System.out.println(
            "1. Pendientes\n2. En Proceso\n3. Finalizadas\n4. Todas");

        int opcion = leerEnteroConRango(
            "Seleccione una opcion: ", 1, 4);

        if (opcion == 1 || opcion == 4)
            mostrarLista("Pendientes", pendientes);    // 74) Muestra pendientes.
        if (opcion == 2 || opcion == 4)
            mostrarLista("En Proceso", enProceso);     // 75) Muestra enProceso.
        if (opcion == 3 || opcion == 4)
            mostrarLista("Finalizadas", finalizadas); // 76) Muestra finalizadas.
    }

    public static void mostrarLista(        // 77) Función que imprime una lista.
        String nombre, ArrayList<String> lista
    ) {
        System.out.println("\nTareas " + nombre + ":"); // 78) Título.
        for (int i = 0; i < lista.size(); i++) {      // 79) Recorre cada elemento.
            System.out.println(
                (i + 1) + ". " + lista.get(i));        // 80) Imprime número y texto.
        }
        System.out.println(
            "Total: " + lista.size() + " tareas");       // 81) Muestra el conteo.
    }

    public static int buscarTarea(            // 82) Función para elegir tarea.
        ArrayList<String> lista, String accion
    ) {
        System.out.println(
            "Buscar por nombre (1) o por posicion (2)?"); // 83) Pregunta método.
        int tipo = leerEnteroConRango(
            "Seleccione una opcion: ", 1, 2);

        if (tipo == 1) {              // 84) Si busca por nombre:
            System.out.print(
                "Ingrese el nombre de la tarea: ");
            String nombre = scanner.nextLine();

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i)
                         .equalsIgnoreCase(nombre))
                    return i;         // 85) Devuelve índice si coincide.
            }

        } else {                     // 86) Si busca por posición:
            System.out.print(
                "Ingrese la posicion de la tarea (1 a " +
                lista.size() + "): ");
            int pos = leerEnteroConRango(
                "", 1, lista.size());
            return pos - 1;          // 87) Ajusta a índice base 0.
        }

        System.out.println(
            "Tarea no encontrada.");  // 88) Si no halló nada.
        return -1;                   // 89) Retorna -1 indicando “no la encontró”.
    }

    public static int leerEnteroConRango(     // 90) Función para leer un número.
        String mensaje, int min, int max
    ) {
        int valor;
        while (true) {                   // 91) Bucle hasta recibir valor válido.
            try {
                if (!mensaje.isEmpty())
                    System.out.print(mensaje); // 92) Imprime el mensaje si lo hay.
                valor = Integer.parseInt(
                    scanner.nextLine()      // 93) Lee línea y la convierte a entero.
                );
                if (valor >= min && valor <= max)
                    return valor;          // 94) Si está en el rango, lo devuelve.

                System.out.println(
                    "Por favor, ingrese un numero entre " +
                    min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println(
                    "Entrada invalida. Ingrese un numero.");
            }
        }
    }
}


// 95) Fin de la clase principal.
