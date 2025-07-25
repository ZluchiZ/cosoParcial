package parcial;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class kjasjd {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<String> pendientes = new ArrayList<>();
    static ArrayList<String> enProceso = new ArrayList<>();
    static ArrayList<String> finalizadas = new ArrayList<>();

    static String usuario = "admin";
    static String contrasena = "1234";

    public static void main(String[] args) {
        try {
            if (!iniciarSesion()) return;
            System.out.println("Bienvenido al sistema de control de tareas estilo Kanban!");

            int opcion=0;
            do {
                mostrarMenu();
                try {
                    opcion = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no valida, por favor ingresa un numero.");
                    continue;
                }
                switch (opcion) {
                    case 1 -> agregarTarea();
                    case 2 -> moverTarea(pendientes, enProceso, "Pendiente", "En Proceso");
                    case 3 -> moverTarea(enProceso, finalizadas, "En Proceso", "Finalizada");
                    case 4 -> modificarTarea();
                    case 5 -> eliminarTarea();
                    case 6 -> listarTareas();
                    case 7 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println("Opcion invalida.");
                }
            } while (opcion != 7);

        } catch (NoSuchElementException e) {
            System.out.println("\nEntrada finalizada. Saliendo...");
        } catch (Exception e) {
            System.out.println("Ocurrio un error inesperado: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static boolean iniciarSesion() {
        int intentos = 3;
        while (intentos-- > 0) {
            System.out.print("Usuario: ");
            String u = scanner.nextLine();
            System.out.print("Contrasena: ");
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

    public static void mostrarMenu() {
        System.out.println("\nMenu de Opciones:");
        System.out.println("1. Agregar tarea nueva");
        System.out.println("2. Tarea en Proceso");
        System.out.println("3. Finalizar tarea");
        System.out.println("4. Modificar tarea");
        System.out.println("5. Eliminar tarea");
        System.out.println("6. Listar tareas");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    public static void agregarTarea() {
        System.out.print("Ingrese el nombre de la nueva tarea: ");
        String tarea = scanner.nextLine();
        pendientes.add(tarea);
        System.out.println("Tarea agregada a la lista de Pendientes.");
    }

    public static void moverTarea(ArrayList<String> origen, ArrayList<String> destino, String nombreOrigen, String nombreDestino) {
        if (origen.isEmpty()){
            System.out.println("No hay tareas en " + nombreOrigen + ".");
            return;
        }
        int index = buscarTarea(origen, nombreOrigen);
        if (index != -1) {
            String tarea = origen.remove(index);
            destino.add(tarea);
            System.out.println("Tarea movida de " + nombreOrigen + " a " + nombreDestino + ".");
        }
    }

    public static void modificarTarea() {
        System.out.println("En que lista esta la tarea a modificar?\n1. Pendientes\n2. En Proceso");
        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida.");
            return;
        }
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
            System.out.println("Opcion invalida.");
        }
    }

    public static void eliminarTarea() {
        System.out.println("De que lista deseas eliminar la tarea?\n1. Pendientes\n2. En Proceso\n3. Finalizadas");
        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida.");
            return;
        }
        ArrayList<String> lista = (opcion == 1) ? pendientes : (opcion == 2) ? enProceso : (opcion == 3) ? finalizadas : null;
        if (lista != null) {
            System.out.print("Deseas eliminar una tarea especifica (1) o todas (2)?: ");
            int tipo;
            try {
                tipo = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida.");
                return;
            }
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
                System.out.println("Opcion invalida.");
            }
            if (lista.isEmpty()){
            System.out.println("No hay tareas en " + lista + ".");
            return;
        }
        }
        
    }

    public static void listarTareas() {
        System.out.println("Que lista deseas visualizar?\n1. Pendientes\n2. En Proceso\n3. Finalizadas\n4. Todas");
        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida.");
            return;
        }
        if (opcion == 1 || opcion == 4) mostrarLista("Pendientes", pendientes);
        if (opcion == 2 || opcion == 4) mostrarLista("En Proceso", enProceso);
        if (opcion == 3 || opcion == 4) mostrarLista("Finalizadas", finalizadas);
    }

    public static void mostrarLista(String nombre, ArrayList<String> lista) {
        System.out.println("\nTareas " + nombre + ":");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }
        System.out.println("Total: " + lista.size() + " tareas");
    }

    public static int buscarTarea(ArrayList<String> lista, String accion) {
        System.out.print("Buscar por nombre (1) o por posicion (2)?: ");
        int tipo;
        try {
            tipo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida.");
            return -1;
        }
        if (tipo == 1) {
            System.out.print("Ingresa el nombre de la tarea: ");
            String nombre = scanner.nextLine();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).equalsIgnoreCase(nombre)) {
                    return i;
                }
            }
        } else if (tipo == 2) {
            System.out.print("Ingresa la posicion de la tarea (1 a " + lista.size() + "): ");
            int pos;
            try {
                pos = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida.");
                return -1;
            }
            if (pos >= 1 && pos <= lista.size()) {
                return pos - 1;
            }
        }
        System.out.println("Tarea no encontrada o entrada invalida.");
        return -1;
    }
}

