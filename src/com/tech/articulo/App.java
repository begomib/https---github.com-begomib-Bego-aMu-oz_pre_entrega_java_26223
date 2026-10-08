package com.tech.articulo;

import com.tech.articulo.model.Articulo;
import com.tech.articulo.model.ArticuloAlimenticio;
import com.tech.articulo.model.ArticuloElectronico;
import com.tech.articulo.model.Categoria;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static final ArrayList<Categoria> categorias = new ArrayList<>();
    private static final ArrayList<Articulo> articulos = new ArrayList<>();

    public static void main(String[] args) {
        precargarCategorias();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("======================================================");
            System.out.println("SISTEMA DE ARTÍCULOS - HERENCIA Y TO_STRING");
            System.out.println("======================================================");
            System.out.println("1 - Ingresar artículo");
            System.out.println("2 - Listar artículos");
            System.out.println("3 - Consultar un artículo");
            System.out.println("4 - Modificar un artículo");
            System.out.println("5 - Eliminar un artículo");
            System.out.println("6 - Listar categorías");
            System.out.println("0 - Salir");
            System.out.println("======================================================");
            System.out.print("Ingrese una opción: ");

            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    ingresarArticulo(scanner);
                    break;
                case 2:
                    listarArticulos();
                    break;
                case 3:
                    consultarArticulo(scanner);
                    break;
                case 4:
                    modificarArticulo(scanner);
                    break;
                case 5:
                    eliminarArticulo(scanner);
                    break;
                case 6:
                    listarCategorias();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void precargarCategorias() {
        categorias.add(new Categoria(1, "Electrónica", "Productos tecnológicos"));
        categorias.add(new Categoria(2, "Periféricos", "Accesorios de computadora"));
        categorias.add(new Categoria(3, "Alimentos", "Productos alimenticios"));
        categorias.add(new Categoria(4, "Limpieza", "Artículos de limpieza"));
    }

    private static void ingresarArticulo(Scanner scanner) {
        System.out.println("\n--- INGRESAR ARTÍCULO ---");

        int tipo;
        do {
            System.out.println("1 - Artículo electrónico");
            System.out.println("2 - Artículo alimenticio");
            System.out.print("Seleccione el tipo: ");
            tipo = leerEntero(scanner);
            if (tipo != 1 && tipo != 2) {
                System.out.println("Tipo inválido.");
            }
        } while (tipo != 1 && tipo != 2);

        int codigo = leerEntero(scanner, "Ingrese código: ");
        if (buscarArticuloPorCodigo(codigo) != null) {
            System.out.println("Ya existe un artículo con ese código.");
            return;
        }

        String nombre = leerTexto(scanner, "Ingrese nombre: ");
        double precio = leerDouble(scanner, "Ingrese precio: ");
        Categoria categoria = pedirCategoria(scanner);

        Articulo articulo;
        if (tipo == 1) {
            int garantia = leerEntero(scanner, "Ingrese garantía en meses: ");
            articulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantia);
        } else {
            int dias = leerEntero(scanner, "Ingrese días para vencimiento: ");
            articulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, dias);
        }

        articulos.add(articulo);
        System.out.println("Artículo agregado correctamente.");
        System.out.println(articulo);
    }

    private static void listarArticulos() {
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        System.out.println("\n--- LISTADO DE ARTÍCULOS ---");
        for (Articulo articulo : articulos) {
            System.out.println(articulo);
        }
    }

    private static void consultarArticulo(Scanner scanner) {
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Ingrese código a consultar: ");
        Articulo articulo = buscarArticuloPorCodigo(codigo);
        if (articulo == null) {
            System.out.println("Artículo no encontrado.");
            return;
        }

        System.out.println(articulo);
    }

    private static void modificarArticulo(Scanner scanner) {
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Ingrese código a modificar: ");
        Articulo articulo = buscarArticuloPorCodigo(codigo);
        if (articulo == null) {
            System.out.println("Artículo no encontrado.");
            return;
        }

        String nombre = leerTexto(scanner, "Nuevo nombre: ");
        double precio = leerDouble(scanner, "Nuevo precio: ");
        Categoria categoria = pedirCategoria(scanner);

        articulo.setNombre(nombre);
        articulo.setPrecio(precio);
        articulo.setCategoria(categoria);

        if (articulo instanceof ArticuloElectronico) {
            ArticuloElectronico electronico = (ArticuloElectronico) articulo;
            int garantia = leerEntero(scanner, "Nueva garantía en meses: ");
            electronico.setGarantiaMeses(garantia);
        } else if (articulo instanceof ArticuloAlimenticio) {
            ArticuloAlimenticio alimenticio = (ArticuloAlimenticio) articulo;
            int dias = leerEntero(scanner, "Nuevos días para vencimiento: ");
            alimenticio.setDiasParaVencimiento(dias);
        }

        System.out.println("Artículo modificado correctamente.");
    }

    private static void eliminarArticulo(Scanner scanner) {
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Ingrese código a eliminar: ");
        Articulo articulo = buscarArticuloPorCodigo(codigo);
        if (articulo == null) {
            System.out.println("Artículo no encontrado.");
            return;
        }

        articulos.remove(articulo);
        System.out.println("Artículo eliminado correctamente.");
    }

    private static void listarCategorias() {
        System.out.println("\n--- CATEGORÍAS ---");
        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    private static Articulo buscarArticuloPorCodigo(int codigo) {
        for (Articulo articulo : articulos) {
            if (articulo.getCodigo() == codigo) {
                return articulo;
            }
        }
        return null;
    }

    private static Categoria pedirCategoria(Scanner scanner) {
        listarCategorias();
        int codigoCategoria;
        Categoria categoria = null;

        do {
            codigoCategoria = leerEntero(scanner, "Ingrese código de categoría: ");
            for (Categoria c : categorias) {
                if (c.getCodigo() == codigoCategoria) {
                    categoria = c;
                    break;
                }
            }
            if (categoria == null) {
                System.out.println("Categoría inexistente. Intente nuevamente.");
            }
        } while (categoria == null);

        return categoria;
    }

    private static int leerEntero(Scanner scanner) {
        return leerEntero(scanner, "");
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            if (!mensaje.isEmpty()) {
                System.out.print(mensaje);
            }
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero válido.");
            }
        }
    }

    private static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            try {
                double valor = Double.parseDouble(entrada.trim());
                if (valor < 0) {
                    System.out.println("El valor no puede ser negativo.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número decimal válido.");
            }
        }
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            if (!texto.trim().isEmpty()) {
                return texto.trim();
            }
            System.out.println("El texto no puede estar vacío.");
        }
    }
}
