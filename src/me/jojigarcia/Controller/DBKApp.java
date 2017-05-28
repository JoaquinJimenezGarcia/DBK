package me.jojigarcia.Controller;

import me.jojigarcia.Model.Conexion;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by garci on 26/05/2017.
 */
public class DBKApp {
    Conexion conexion;

    public DBKApp(){
        conexion = new Conexion();
    }

    public void run() throws SQLException{
        int opcion;

        while ((opcion = mostrarMenu())!= 0){
            switch (opcion) {
                case 1:
                    if (!conexion.getConectado()) {
                        conexion.conectar(leerUsuario(), leerPasswd(), leerBaseDatos());
                    }
                    break;
                case 2:
                    if (conexion.getConectado()) {
                        conexion.cerrar();
                    }
                    break;
                case 3:
                    if (conexion.getConectado()) {
                        conexion.crearBaseDatos(leerBaseDatos());
                    }
                    break;
                case 4:
                    if (conexion.getConectado()) {
                        conexion.cambiarBaseDatos(leerBaseDatos());
                    }
                    break;
                default:
                    break;
            }
        }

        conexion.cerrar();
    }

    public int mostrarMenu(){
        Scanner input = new Scanner(System.in);
        int opcion;

        if (conexion.getConectado()){
            System.out.println("Conectado a la Base de Datos: " + conexion.getNombre());
            System.out.println("Usuario: " + conexion.getUser());
        }
        System.out.println("**********************************");
        if (!conexion.getConectado()){
            System.out.println("* 1. Establecer conexión         *");
        }
        if (conexion.getConectado()) {
            System.out.println("* 2. Cerrar conexión con la db   *");
            System.out.println("* 3. Crear Base de Datos         *");
            System.out.println("* 4. Cambiar de Base de Datos    *");
        }
        System.out.println("* 0. Salir                       *");
        System.out.println("**********************************");

        System.out.println("Opción: ");
        try {
            opcion = input.nextInt();
            return opcion;
        }catch (InputMismatchException e){
            System.out.println("Opción inválida.");
        }
        return mostrarMenu();
    }

    public String leerBaseDatos(){
        Scanner input = new Scanner(System.in);

        System.out.println("Introduzca la base de datos: ");
        return input.nextLine();
    }

    public String leerUsuario(){
        Scanner input = new Scanner(System.in);

        System.out.println("Introduzca nombre de usuario: ");
        return input.nextLine();
    }

    public String leerPasswd(){
        Scanner input = new Scanner(System.in);

        System.out.println("Introduzca contraseña: ");
        return input.nextLine();
    }
}
