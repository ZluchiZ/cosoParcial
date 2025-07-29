package parcial;

// Importación de clases necesarias
import java.util.ArrayList;
import java.util.Scanner;

// Clase principal del sistema Kanban
public class kjasjd {

	// Scanner global para leer desde consola
	static Scanner scanner = new Scanner(System.in);

	// Listas que representan las columnas del tablero Kanban
	static ArrayList<String> pendientes = new ArrayList<>();
	static ArrayList<String> enProceso = new ArrayList<>();
	static ArrayList<String> finalizadas = new ArrayList<>();

	// Credenciales fijas para iniciar sesion
	static String usuario = "a";
	static String contrasena = "1";

	// Metodo principal del programa
	public static void main(String[] args) {
	    try {
	        // Se solicita al usuario iniciar sesion
	        if (!iniciarSesion()) return;

	        System.out.println("Bienvenido al sistema de control de tareas estilo Kanban!");

	        int opcion;

	        // Bucle del menu principal
	        do {
	            mostrarMenu(); // Muestra el menu

	            // Lee la opcion del usuario con validacion
	            opcion = leerEnteroConRango("Seleccione una opcion: ", 1, 7);

	            // Ejecuta la opcion elegida
	            switch (opcion) {
	                case 1 -> agregarTarea();
	                case 2 -> moverTarea(pendientes, enProceso, "Pendiente", "En Proceso");
	                case 3 -> moverTarea(enProceso, finalizadas, "En Proceso", "Finalizada");
	                case 4 -> modificarTarea();
	                case 5 -> eliminarTarea();
	                case 6 -> listarTareas();
	                case 7 -> System.out.println("Saliendo del programa...");
	            }

	        } while (opcion != 7); // Sale al elegir 7

	    } catch (Exception e) {
	        // Captura errores inesperados
	        System.out.println("Ocurrio un error inesperado: " + e.getMessage());
	    } finally {
	        // Cierra el scanner
	        scanner.close();
	    }
	}

	// Metodo que solicita usuario y contrasena
	public static boolean iniciarSesion() {
	    int intentos = 3;

	    while (intentos-- > 0) {
	        System.out.print("User: ");
	        String u = scanner.nextLine();

	        System.out.print("Password: ");
	        String c = scanner.nextLine();

	        // Verifica credenciales
	        if (u.equals(usuario) && c.equals(contrasena)) return true;

	        System.out.println("Credenciales incorrectas. Intentos restantes: " + intentos);
	    }

	    // Si falla tres veces
	    System.out.println("Demasiados intentos fallidos. Cerrando programa.");
	    return false;
	}

	// Muestra el menu de opciones
	public static void mostrarMenu() {
	    System.out.println("\nMenu de Opciones:");
	    System.out.println("1. Agregar tarea nueva");
	    System.out.println("2. Mover tarea a En Proceso");
	    System.out.println("3. Finalizar tarea");
	    System.out.println("4. Modificar tarea");
	    System.out.println("5. Eliminar tarea");
	    System.out.println("6. Listar tareas");
	    System.out.println("7. Salir");
	}

	// Agrega una tarea a la lista de pendientes
	public static void agregarTarea() {
	    System.out.print("Ingrese el nombre de la nueva tarea: ");
	    String tarea = scanner.nextLine();
	    pendientes.add(tarea); // Agrega a la lista
	    System.out.println("Tarea agregada a la lista de Pendientes.");
	}

	// Mueve una tarea entre listas con confirmacion del usuario
	public static void moverTarea(ArrayList<String> origen, ArrayList<String> destino, String nombreOrigen, String nombreDestino) {
	    // Si la lista de origen esta vacia, no se puede mover nada
	    if (origen.isEmpty()) {
	        System.out.println("No hay tareas en " + nombreOrigen + ".");
	        return;
	    }

	    // Busca la tarea que se desea mover (por nombre o posicion)
	    int index = buscarTarea(origen, nombreOrigen);

	    if (index != -1) {
	        String tarea = origen.get(index); // Obtiene el nombre de la tarea seleccionada

	        // Muestra la tarea y pide confirmacion
	        System.out.println("Tarea seleccionada: \"" + tarea + "\"");
	        System.out.print("¿Esta seguro que desea mover esta tarea de " + nombreOrigen + " a " + nombreDestino + "? (s/n): ");
	        String confirmacion = scanner.nextLine().trim().toLowerCase();

	        // Si el usuario confirma, se realiza el movimiento
	        if (confirmacion.equals("s")) {
	            origen.remove(index); // Quita de la lista origen
	            destino.add(tarea);   // Agrega a la lista destino
	            System.out.println("Tarea movida de " + nombreOrigen + " a " + nombreDestino + ".");
	        } else {
	            // Si el usuario no confirma, se cancela la operacion
	            System.out.println("Operacion cancelada. La tarea no fue movida.");
	        }
	    }
	}

	// Permite modificar el nombre de una tarea
	public static void modificarTarea() {
	    System.out.println("¿En que lista esta la tarea a modificar?");
	    System.out.println("1. Pendientes\n2. En Proceso");

	    int opcion = leerEnteroConRango("Seleccione una opcion: ", 1, 2);

	    ArrayList<String> lista = (opcion == 1) ? pendientes : enProceso;

	    int index = buscarTarea(lista, "modificar");
	    if (index != -1) {
	        System.out.print("Nuevo nombre de la tarea: ");
	        String nuevoNombre = scanner.nextLine();
	        lista.set(index, nuevoNombre); // Modifica la tarea
	        System.out.println("Tarea modificada correctamente.");
	    }
	}

	// Elimina tareas de una o todas las listas
	public static void eliminarTarea() {
	    // Muestra el menu de seleccion de lista
	    int opcion = leerEnteroConRango(
	        "¿De que lista desea eliminar la tarea?\n" +
	        "1. Pendientes\n2. En Proceso\n3. Finalizadas\n4. Todas las listas\nSeleccione una opcion: ",
	        1, 4
	    );

	    if (opcion >= 1 && opcion <= 3) {
	        // Selecciona la lista correspondiente segun la opcion
	        ArrayList<String> lista = switch (opcion) {
	            case 1 -> pendientes;
	            case 2 -> enProceso;
	            case 3 -> finalizadas;
	            default -> null;
	        };

	        if (lista.isEmpty()) {
	            System.out.println("No hay tareas en esta lista.");
	            return;
	        }

	        // Pregunta si se quiere eliminar una o todas
	        int tipo = leerEnteroConRango("¿Desea eliminar una tarea especifica (1) o todas (2)?: ", 1, 2);

	        if (tipo == 1) {
	            int index = buscarTarea(lista, "eliminar");
	            if (index != -1) {
	                String tareaAEliminar = lista.get(index);
	                System.out.println("Tarea seleccionada: \"" + tareaAEliminar + "\"");
	                System.out.print("¿Esta seguro que desea eliminar esta tarea? (s/n): ");
	                String confirmacion = scanner.nextLine().trim().toLowerCase();

	                if (confirmacion.equals("s")) {
	                    lista.remove(index);
	                    System.out.println("Tarea eliminada correctamente.");
	                } else {
	                    System.out.println("Operacion cancelada. La tarea no fue eliminada.");
	                }
	            }
	        } else {
	            System.out.print("¿Esta seguro que desea eliminar TODAS las tareas de esta lista? (s/n): ");
	            String confirmacion = scanner.nextLine().trim().toLowerCase();

	            if (confirmacion.equals("s")) {
	                lista.clear();
	                System.out.println("Todas las tareas han sido eliminadas.");
	            } else {
	                System.out.println("Operacion cancelada. No se elimino ninguna tarea.");
	            }
	        }
	    }

	    // Opcion para eliminar todas las tareas de todas las listas
	    if (opcion == 4) {
	        System.out.print("¿Esta seguro que desea eliminar TODAS las tareas de TODAS las listas? (s/n): ");
	        String confirmacion = scanner.nextLine().trim().toLowerCase();

	        if (confirmacion.equals("s")) {
	            pendientes.clear();
	            enProceso.clear();
	            finalizadas.clear();
	            System.out.println("Todas las tareas de todas las listas han sido eliminadas.");
	        } else {
	            System.out.println("Operacion cancelada. No se elimino ninguna tarea.");
	        }
	    }
	}

	// Lista tareas por columna o todas
	public static void listarTareas() {
	    System.out.println("¿Que lista desea visualizar?");
	    System.out.println("1. Pendientes\n2. En Proceso\n3. Finalizadas\n4. Todas");

	    int opcion = leerEnteroConRango("Seleccione una opcion: ", 1, 4);

	    if (opcion == 1 || opcion == 4) mostrarLista("Pendientes", pendientes);
	    if (opcion == 2 || opcion == 4) mostrarLista("En Proceso", enProceso);
	    if (opcion == 3 || opcion == 4) mostrarLista("Finalizadas", finalizadas);
	}

	// Muestra tareas de una lista con numeracion
	public static void mostrarLista(String nombre, ArrayList<String> lista) {
	    System.out.println("\nTareas " + nombre + ":");
	    for (int i = 0; i < lista.size(); i++) {
	        System.out.println((i + 1) + ". " + lista.get(i));
	    }
	    System.out.println("Total: " + lista.size() + " tareas");
	}

	// Busca una tarea por nombre o posicion
	public static int buscarTarea(ArrayList<String> lista, String accion) {
	    System.out.println("Buscar por nombre (1) o por posicion (2)?");
	    int tipo = leerEnteroConRango("Seleccione una opcion: ", 1, 2);

	    if (tipo == 1) {
	        System.out.print("Ingrese el nombre de la tarea: ");
	        String nombre = scanner.nextLine();

	        for (int i = 0; i < lista.size(); i++) {
	            if (lista.get(i).equalsIgnoreCase(nombre)) return i;
	        }

	    } else {
	        System.out.print("Ingrese la posicion de la tarea (1 a " + lista.size() + "): ");
	        int pos = leerEnteroConRango("", 1, lista.size());
	        return pos - 1;
	    }

	    System.out.println("Tarea no encontrada.");
	    return -1;
	}

	// Lee un entero valido dentro de un rango
	public static int leerEnteroConRango(String mensaje, int min, int max) {
	    int valor;
	    while (true) {
	        try {
	            if (!mensaje.isEmpty()) System.out.print(mensaje);
	            valor = Integer.parseInt(scanner.nextLine());

	            if (valor >= min && valor <= max) return valor;

	            System.out.println("Por favor, ingrese un numero entre " + min + " y " + max + ".");
	        } catch (NumberFormatException e) {
	            System.out.println("Entrada invalida. Ingrese un numero.");
	        }
	    }
	}
}
