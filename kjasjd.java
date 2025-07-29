package parcial;

// Importación de clases necesarias
import java.util.ArrayList;
import java.util.Scanner;

// Clase principal del sistema Kanban
public class kjasjd {


	
	    // Scanner global para leer entradas desde consola
	    static Scanner scanner = new Scanner(System.in);

	    // Listas que representan las columnas del tablero Kanban
	    static ArrayList<String> pendientes = new ArrayList<>();
	    static ArrayList<String> enProceso = new ArrayList<>();
	    static ArrayList<String> finalizadas = new ArrayList<>();

	    // Usuario y contraseña fijos
	    static String usuario = "a";
	    static String contrasena = "1";

	    public static void main(String[] args) {
	        try {
	            // Si no se inicia sesion correctamente, se sale
	            if (!iniciarSesion()) return;

	            System.out.println("Bienvenido al sistema de control de tareas estilo Kanban!");

	            int opcion;

	            // Bucle principal del menu
	            do {
	                mostrarMenu(); // Mostrar opciones
	                opcion = leerEnteroRango("", 1, 7); // Leer opcion valida

	                // Ejecutar la opcion seleccionada
	                switch (opcion) {
	                    case 1 -> agregarTarea(); // Agregar nueva tarea
	                    case 2 -> moverTarea(pendientes, enProceso, "Pendiente", "En Proceso"); // Mover de Pendientes a En Proceso
	                    case 3 -> moverTarea(enProceso, finalizadas, "En Proceso", "Finalizada"); // Mover a Finalizada
	                    case 4 -> modificarTarea(); // Modificar una tarea
	                    case 5 -> eliminarTarea(); // Eliminar tarea(s)
	                    case 6 -> listarTareas(); // Ver tareas
	                    case 7 -> System.out.println("Saliendo del programa..."); // Salir
	                    default -> System.out.println("Opcion invalida.");
	                }

	            } while (opcion != 7); // Repetir hasta que se elija salir

	        } catch (Exception e) {
	            // Manejo de error general
	            System.out.println("Ocurrio un error inesperado: " + e.getMessage());
	        } finally {
	            // Siempre cerrar el scanner
	            scanner.close();
	        }
	    }

	    // Funcion para iniciar sesion
	    public static boolean iniciarSesion() {
	        int intentos = 3; // Intentos disponibles

	        while (intentos-- > 0) {
	            System.out.print("User: ");
	            String u = scanner.nextLine(); // Leer usuario

	            System.out.print("Password: ");
	            String c = scanner.nextLine(); // Leer contraseña

	            if (u.equals(usuario) && c.equals(contrasena)) {
	                return true; // Credenciales correctas
	            } else {
	                // Mensaje de error y cantidad restante de intentos
	                System.out.println("Credenciales incorrectas. Intentos restantes: " + intentos);
	            }
	        }

	        // Si se acaban los intentos
	        System.out.println("Demasiados intentos fallidos. Cerrando programa.");
	        return false;
	    }

	    // Muestra el menu principal
	    public static void mostrarMenu() {
	        System.out.println("\nMenu de Opciones:");
	        System.out.println("1. Agregar tarea nueva");
	        System.out.println("2. Mover tarea a En Proceso");
	        System.out.println("3. Finalizar tarea");
	        System.out.println("4. Modificar tarea");
	        System.out.println("5. Eliminar tarea");
	        System.out.println("6. Listar tareas");
	        System.out.println("7. Salir");
	        System.out.print("Seleccione una opcion: ");
	    }

	    // Agrega una tarea a la lista de pendientes
	    public static void agregarTarea() {
	        System.out.print("Ingrese el nombre de la nueva tarea: ");
	        String tarea = scanner.nextLine(); // Leer nombre
	        pendientes.add(tarea); // Agregar a la lista de pendientes
	        System.out.println("Tarea agregada a la lista de Pendientes.");
	    }

	    // Mueve una tarea de una lista origen a una destino
	    public static void moverTarea(ArrayList<String> origen, ArrayList<String> destino, String nombreOrigen, String nombreDestino) {
	        if (origen.isEmpty()) {
	            // Si no hay tareas, informar
	            System.out.println("No hay tareas en " + nombreOrigen + ".");
	            return;
	        }

	        int index = buscarTarea(origen, nombreOrigen); // Buscar tarea a mover

	        if (index != -1) {
	            String tarea = origen.remove(index); // Quitar de la lista origen
	            destino.add(tarea); // Agregar a la lista destino
	            System.out.println("Tarea movida de " + nombreOrigen + " a " + nombreDestino + ".");
	        }
	    }

	    // Modifica el nombre de una tarea existente
	    public static void modificarTarea() {
	        // Elegir lista donde esta la tarea
	        int opcion = leerEnteroRango("En que lista esta la tarea a modificar?\n1. Pendientes\n2. En Proceso\nSeleccione una opcion: ", 1, 2);

	        ArrayList<String> lista = (opcion == 1) ? pendientes : enProceso;

	        int index = buscarTarea(lista, "modificar"); // Buscar tarea

	        if (index != -1) {
	            System.out.print("Nuevo nombre de la tarea: ");
	            String nuevoNombre = scanner.nextLine(); // Leer nuevo nombre
	            lista.set(index, nuevoNombre); // Reemplazar tarea
	            System.out.println("Tarea modificada correctamente.");
	        }
	    }

	    // Elimina una tarea especifica o todas de una lista
	    public static void eliminarTarea() {
	        // Elegir lista
	        int opcion = leerEnteroRango("De que lista deseas eliminar la tarea?\n1. Pendientes\n2. En Proceso\n3. Finalizadas\nSeleccione una opcion: ", 1, 3);

	        ArrayList<String> lista = switch (opcion) {
	            case 1 -> pendientes;
	            case 2 -> enProceso;
	            case 3 -> finalizadas;
	            default -> null;
	        };

	        if (lista.isEmpty()) {
	            // Si la lista esta vacia
	            System.out.println("No hay tareas en esta lista.");
	            return;
	        }

	        // Eliminar una o todas
	        int tipo = leerEnteroRango("Deseas eliminar una tarea especifica (1) o todas (2)?: ", 1, 2);

	        if (tipo == 1) {
	            int index = buscarTarea(lista, "eliminar");
	            if (index != -1) {
	                lista.remove(index); // Eliminar tarea
	                System.out.println("Tarea eliminada correctamente.");
	            }
	        } else {
	            lista.clear(); // Vaciar lista
	            System.out.println("Todas las tareas han sido eliminadas.");
	        }
	    }

	    // Muestra las tareas de una o varias listas
	    public static void listarTareas() {
	        int opcion = leerEnteroRango("Que lista deseas visualizar?\n1. Pendientes\n2. En Proceso\n3. Finalizadas\n4. Todas\nSeleccione una opcion: ", 1, 4);

	        if (opcion == 1 || opcion == 4) mostrarLista("Pendientes", pendientes);
	        if (opcion == 2 || opcion == 4) mostrarLista("En Proceso", enProceso);
	        if (opcion == 3 || opcion == 4) mostrarLista("Finalizadas", finalizadas);
	    }

	    // Muestra el contenido de una lista
	    public static void mostrarLista(String nombre, ArrayList<String> lista) {
	        System.out.println("\nTareas " + nombre + ":");

	        // Mostrar cada tarea con numero
	        for (int i = 0; i < lista.size(); i++) {
	            System.out.println((i + 1) + ". " + lista.get(i));
	        }

	        // Mostrar total de tareas
	        System.out.println("Total: " + lista.size() + " tareas");
	    }

	    // Buscar una tarea por nombre o posicion
	    public static int buscarTarea(ArrayList<String> lista, String accion) {
	        while (true) {
	            // Elegir metodo de busqueda
	            int tipo = leerEnteroRango("Buscar por nombre (1) o por posicion (2)?: ", 1, 2);

	            if (tipo == 1) {
	                System.out.print("Ingresa el nombre de la tarea: ");
	                String nombre = scanner.nextLine();

	                for (int i = 0; i < lista.size(); i++) {
	                    if (lista.get(i).equalsIgnoreCase(nombre)) {
	                        return i; // Encontrada
	                    }
	                }
	            } else {
	                // Buscar por posicion
	                int pos = leerEnteroRango("Ingresa la posicion de la tarea (1 a " + lista.size() + "): ", 1, lista.size());
	                return pos - 1;
	            }

	            // Si no se encuentra
	            System.out.println("Tarea no encontrada. Intente de nuevo.");
	        }
	    }

	    // Leer un numero entero dentro de un rango valido
	    public static int leerEnteroRango(String mensaje, int min, int max) {
	        int numero;
	        while (true) {
	            if (!mensaje.isEmpty()) System.out.print(mensaje);
	            try {
	                numero = Integer.parseInt(scanner.nextLine()); // Leer entrada
	                if (numero >= min && numero <= max) {
	                    return numero; // Entrada valida
	                } else {
	                    System.out.println("Por favor, ingrese un numero entre " + min + " y " + max + ".");
	                }
	            } catch (Exception e) {
	                // Si no es un numero valido
	                System.out.println("Entrada no valida. Intente de nuevo.");
	            }
	        }
	    }
	}
