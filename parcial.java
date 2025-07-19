package parcial;
import java.util.ArrayList;
import java.util.Scanner;


public class kjasjd {
	// Scanner para leer datos del usuario
    static Scanner scanner = new Scanner(System.in);

    // Listas para guardar tareas según su estado
    static ArrayList<String> pendientes = new ArrayList<>();
    static ArrayList<String> enProceso = new ArrayList<>();
    static ArrayList<String> finalizadas = new ArrayList<>();

    // Usuario y contraseña predefinidos
    static String usuario = "admin";
    static String contrasena = "1234";

    public static void main(String[] args) {
        // Verifica inicio de sesión, si falla se cierra el programa
        if (!iniciarSesion()) return;
        System.out.println("¡Bienvenido al sistema de control de tareas estilo Kanban!");

        int opcion;
        // Bucle principal del menú
        do {
            mostrarMenu();
            opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1 -> agregarTarea(); // Agregar nueva tarea
                case 2 -> moverTarea(pendientes, enProceso, "Pendiente", "En Proceso"); // Mover de Pendiente a En Proceso
                case 3 -> moverTarea(enProceso, finalizadas, "En Proceso", "Finalizada"); // Mover de En Proceso a Finalizada
                case 4 -> modificarTarea(); // Modificar tarea existente
                case 5 -> eliminarTarea(); // Eliminar tarea(s)
                case 6 -> listarTareas(); // Mostrar tareas
                case 7 -> System.out.println("Saliendo del programa..."); // Salir
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 7);
    }

    // Función para manejar inicio de sesión con 3 intentos
    public static boolean iniciarSesion() {
        int intentos = 3;
        while (intentos-- > 0) {
            System.out.print("Usuario: ");
            String u = scanner.nextLine();
            System.out.print("Contraseña: ");
            String c = scanner.nextLine();
            if (u.equals(usuario) && c.equals(contrasena)) {
                return true;
            } else {
                System.out.println("Credenciales incorrectas. Intentos restantes: " + intentos);
            }
        }
        System.out.println("Demasiados intentos fallidos. Cerrando programa.");
        return false;
    }

    // Mostrar menú de opciones
    public static void mostrarMenu() {
        System.out.println("\nMenú de Opciones:");
        System.out.println("1. Agregar tarea nueva");
        System.out.println("2. Tarea en Proceso");
        System.out.println("3. Finalizar tarea");
        System.out.println("4. Modificar tarea");
        System.out.println("5. Eliminar tarea");
        System.out.println("6. Listar tareas");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Agregar una nueva tarea a la lista de Pendientes
    public static void agregarTarea() {
        System.out.print("Ingrese el nombre de la nueva tarea: ");
        String tarea = scanner.nextLine();
        pendientes.add(tarea);
        System.out.println("Tarea agregada a la lista de Pendientes.");
    }

    // Mover una tarea de una lista a otra (ej: de Pendientes a En Proceso)
    public static void moverTarea(ArrayList<String> origen, ArrayList<String> destino, String nombreOrigen, String nombreDestino) {
        int index = buscarTarea(origen, nombreOrigen);
        if (index != -1) {
            String tarea = origen.remove(index);
            destino.add(tarea);
            System.out.println("Tarea movida de " + nombreOrigen + " a " + nombreDestino + ".");
        }
    }

    // Modificar una tarea (solo si está en Pendientes o En Proceso)
    public static void modificarTarea() {
        System.out.println("¿En qué lista está la tarea a modificar?\n1. Pendientes\n2. En Proceso");
        int opcion = Integer.parseInt(scanner.nextLine());
        ArrayList<String> lista = (opcion == 1) ? pendientes : (opcion == 2) ? enProceso : null;
        if (lista != null) {
            int index = buscarTarea(lista, "modificar");
            if (index != -1) {
                System.out.print("Nuevo nombre de la tarea: ");
                String nuevoNombre = scanner.nextLine();
                lista.set(index, nuevoNombre);
                System.out.println("Tarea modificada correctamente.");
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    // Eliminar una tarea específica o todas las de una lista
    public static void eliminarTarea() {
        System.out.println("¿De qué lista desea eliminar la tarea?\n1. Pendientes\n2. En Proceso\n3. Finalizadas");
        int opcion = Integer.parseInt(scanner.nextLine());
        ArrayList<String> lista = (opcion == 1) ? pendientes : (opcion == 2) ? enProceso : (opcion == 3) ? finalizadas : null;
        if (lista != null) {
            System.out.print("¿Desea eliminar una tarea específica (1) o todas (2)?: ");
            int tipo = Integer.parseInt(scanner.nextLine());
            if (tipo == 1) {
                int index = buscarTarea(lista, "eliminar");
                if (index != -1) {
                    lista.remove(index);
                    System.out.println("Tarea eliminada correctamente.");
                }
            } else if (tipo == 2) {
                lista.clear();
                System.out.println("Todas las tareas eliminadas de la lista.");
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    // Mostrar tareas por lista o todas
    public static void listarTareas() {
        System.out.println("¿Qué lista desea visualizar?\n1. Pendientes\n2. En Proceso\n3. Finalizadas\n4. Todas");
        int opcion = Integer.parseInt(scanner.nextLine());
        if (opcion == 1 || opcion == 4) mostrarLista("Pendientes", pendientes);
        if (opcion == 2 || opcion == 4) mostrarLista("En Proceso", enProceso);
        if (opcion == 3 || opcion == 4) mostrarLista("Finalizadas", finalizadas);
    }

    // Mostrar tareas de una lista con su cantidad
    public static void mostrarLista(String nombre, ArrayList<String> lista) {
        System.out.println("\nTareas " + nombre + ":");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }
        System.out.println("Total: " + lista.size() + " tarea(s)");
    }

    // Buscar tarea por nombre o por posición
    public static int buscarTarea(ArrayList<String> lista, String accion) {
        System.out.print("¿Buscar por nombre (1) o por posición (2)?: ");
        int tipo = Integer.parseInt(scanner.nextLine());
        if (tipo == 1) {
            System.out.print("Ingrese el nombre de la tarea: ");
            String nombre = scanner.nextLine();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).equalsIgnoreCase(nombre)) {
                    return i;
                }
            }
        } else if (tipo == 2) {
            System.out.print("Ingrese la posición de la tarea (1 a " + lista.size() + "): ");
            int pos = Integer.parseInt(scanner.nextLine());
            if (pos >= 1 && pos <= lista.size()) {
                return pos - 1;
            }
        }
        System.out.println("Tarea no encontrada o entrada inválida.");
        return -1;
    }
}
