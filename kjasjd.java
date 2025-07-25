package parcial;

// Importación de clases necesarias
import java.util.ArrayList;                   // Listas dinámicas para almacenar tareas
import java.util.NoSuchElementException;     // Excepción si la entrada se cierra inesperadamente
import java.util.Scanner;                    // Para capturar entrada del usuario

// Clase principal del sistema Kanban
public class kjasjd {

    // Scanner global para leer desde consola
    static Scanner scanner = new Scanner(System.in);

    // Listas que representan las columnas del tablero Kanban
    static ArrayList<String> pendientes = new ArrayList<>();
    static ArrayList<String> enProceso = new ArrayList<>();
    static ArrayList<String> finalizadas = new ArrayList<>();

    // Credenciales fijas para iniciar sesión
    static String usuario = "admin";
    static String contrasena = "1234";

    // Método principal del programa
    public static void main(String[] args) {
        try {
            // Se solicita al usuario iniciar sesión
            if (!iniciarSesion()) return;

            // Bienvenida al sistema
            System.out.println("Bienvenido al sistema de control de tareas estilo Kanban!");

            int opcion = 0;

            // Bucle del menú principal
            do {
                mostrarMenu(); // Muestra opciones disponibles

                try {
                    opcion = Integer.parseInt(scanner.nextLine()); // Lee opción del usuario
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no válida, por favor ingresa un número.");
                    continue; // Vuelve al menú sin ejecutar nada
                }

                // Ejecuta la opción elegida
                switch (opcion) {
                    case 1 -> agregarTarea(); // Agregar nueva tarea
                    case 2 -> moverTarea(pendientes, enProceso, "Pendiente", "En Proceso"); // Mover a En Proceso
                    case 3 -> moverTarea(enProceso, finalizadas, "En Proceso", "Finalizada"); // Mover a Finalizada
                    case 4 -> modificarTarea(); // Modificar una tarea
                    case 5 -> eliminarTarea(); // Eliminar tarea
                    case 6 -> listarTareas(); // Ver tareas
                    case 7 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println("Opción inválida."); // Opción no reconocida
                }

            } while (opcion != 7); // Sale si se elige la opción 7

        } catch (NoSuchElementException e) {
            // Si el usuario cierra la entrada (ej. Ctrl+D)
            System.out.println("\nEntrada finalizada. Saliendo...");
        } catch (Exception e) {
            // Cualquier otro error inesperado
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        } finally {
            // Siempre se cierra el scanner al finalizar
            scanner.close();
        }
    }

    // Método que maneja el inicio de sesión del usuario
    public static boolean iniciarSesion() {
        int intentos = 3; // Se permiten 3 intentos

        while (intentos-- > 0) {
            // Solicita usuario y contraseña
            System.out.print("Usuario: ");
            String u = scanner.nextLine();

            System.out.print("Contraseña: ");
            String c = scanner.nextLine();

            // Verifica credenciales
            if (u.equals(usuario) && c.equals(contrasena)) {
                return true; // Login exitoso
            } else {
                System.out.println("Credenciales incorrectas. Intentos restantes: " + intentos);
            }
        }

        // Si se fallan los 3 intentos
        System.out.println("Demasiados intentos fallidos. Cerrando programa.");
        return false;
    }

    // Muestra las opciones disponibles al usuario
    public static void mostrarMenu() {
        System.out.println("\nMenú de Opciones:");
        System.out.println("1. Agregar tarea nueva");
        System.out.println("2. Mover tarea a En Proceso");
        System.out.println("3. Finalizar tarea");
        System.out.println("4. Modificar tarea");
        System.out.println("5. Eliminar tarea");
        System.out.println("6. Listar tareas");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Agrega una tarea a la lista de pendientes
    public static void agregarTarea() {
        System.out.print("Ingrese el nombre de la nueva tarea: ");
        String tarea = scanner.nextLine(); // Lee el nombre
        pendientes.add(tarea); // Agrega a la lista
        System.out.println("Tarea agregada a la lista de Pendientes.");
    }

    // Mueve una tarea entre listas
    public static void moverTarea(ArrayList<String> origen, ArrayList<String> destino, String nombreOrigen, String nombreDestino) {
        if (origen.isEmpty()) {
            System.out.println("No hay tareas en " + nombreOrigen + ".");
            return;
        }

        // Busca la tarea a mover
        int index = buscarTarea(origen, nombreOrigen);

        if (index != -1) {
            String tarea = origen.remove(index); // Quita de la lista origen
            destino.add(tarea); // Agrega a la lista destino
            System.out.println("Tarea movida de " + nombreOrigen + " a " + nombreDestino + ".");
        }
    }

    // Permite modificar el nombre de una tarea existente
    public static void modificarTarea() {
        System.out.println("¿En qué lista está la tarea a modificar?\n1. Pendientes\n2. En Proceso");

        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return;
        }

        // Determina la lista según la opción
        ArrayList<String> lista = (opcion == 1) ? pendientes : (opcion == 2) ? enProceso : null;

        if (lista != null) {
            int index = buscarTarea(lista, "modificar"); // Busca la tarea
            if (index != -1) {
                System.out.print("Nuevo nombre de la tarea: ");
                String nuevoNombre = scanner.nextLine(); // Nuevo nombre
                lista.set(index, nuevoNombre); // Se actualiza
                System.out.println("Tarea modificada correctamente.");
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    // Elimina una tarea específica o todas de una lista
    public static void eliminarTarea() {
        System.out.println("¿De qué lista deseas eliminar la tarea?\n1. Pendientes\n2. En Proceso\n3. Finalizadas");

        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return;
        }

        // Selecciona la lista correspondiente
        ArrayList<String> lista = switch (opcion) {
            case 1 -> pendientes;
            case 2 -> enProceso;
            case 3 -> finalizadas;
            default -> null;
        };

        if (lista == null) {
            System.out.println("Opción inválida.");
            return;
        }

        // Verifica si la lista tiene tareas
        if (lista.isEmpty()) {
            System.out.println("No hay tareas en esta lista.");
            return;
        }

        // Pregunta si quiere eliminar una sola o todas
        System.out.print("¿Deseas eliminar una tarea específica (1) o todas (2)?: ");
        int tipo;
        try {
            tipo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return;
        }

        // Ejecuta según el tipo de eliminación
        switch (tipo) {
            case 1 -> {
                int index = buscarTarea(lista, "eliminar");
                if (index != -1) {
                    lista.remove(index); // Elimina una sola
                    System.out.println("Tarea eliminada correctamente.");
                }
            }
            case 2 -> {
                lista.clear(); // Borra toda la lista
                System.out.println("Todas las tareas han sido eliminadas.");
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    // Muestra tareas de una lista específica o todas
    public static void listarTareas() {
        System.out.println("¿Qué lista deseas visualizar?\n1. Pendientes\n2. En Proceso\n3. Finalizadas\n4. Todas");

        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return;
        }

        // Muestra según lo elegido
        if (opcion == 1 || opcion == 4) mostrarLista("Pendientes", pendientes);
        if (opcion == 2 || opcion == 4) mostrarLista("En Proceso", enProceso);
        if (opcion == 3 || opcion == 4) mostrarLista("Finalizadas", finalizadas);
    }

    // Imprime todas las tareas de una lista
    public static void mostrarLista(String nombre, ArrayList<String> lista) {
        System.out.println("\nTareas " + nombre + ":");

        // Recorre la lista e imprime tareas numeradas
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }

        // Imprime el total
        System.out.println("Total: " + lista.size() + " tareas");
    }

    // Busca una tarea por nombre o posición
    public static int buscarTarea(ArrayList<String> lista, String accion) {
        System.out.print("Buscar por nombre (1) o por posición (2)?: ");
        int tipo;

        try {
            tipo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return -1;
        }

        if (tipo == 1) {
            // Buscar por nombre (ignora mayúsculas/minúsculas)
            System.out.print("Ingresa el nombre de la tarea: ");
            String nombre = scanner.nextLine();

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).equalsIgnoreCase(nombre)) {
                    return i; // Retorna índice
                }
            }

        } else if (tipo == 2) {
            // Buscar por número de posición
            System.out.print("Ingresa la posición de la tarea (1 a " + lista.size() + "): ");
            int pos;
            try {
                pos = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida.");
                return -1;
            }

            // Verifica que esté dentro del rango
            if (pos >= 1 && pos <= lista.size()) {
                return pos - 1; // Ajuste al índice 0 basado
            }
        }

        // Si no encuentra o hay error
        System.out.println("Tarea no encontrada o entrada inválida.");
        return -1;
    }
}
