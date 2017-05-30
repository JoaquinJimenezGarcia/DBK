package me.jojigarcia.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by garci on 26/05/2017.
 */
public class Conexion {
    private Connection conexion = null;
    private String nombre;
    private String user;
    private Boolean conectado = false;

    public String getNombre() {
        return nombre;
    }

    public String getUser() {
        return user;
    }

    public Boolean getConectado() {
        return conectado;
    }

    public void conectar(String user, String passwd, String db) throws SQLException{
        String servidor = "jdbc:mysql://localhost:3306/" + db;
        conexion = DriverManager.getConnection(servidor,user,passwd);
        conexion.setAutoCommit(false);

        this.nombre = db;
        this.user = user;

        this.conectado = true;
    }

    public void cerrar() throws SQLException{
        if (conexion != null){
            conexion.close();
            this.conectado = false;
            System.out.println("Desconectado de la base de datos con éxito.");
        }
    }

    public void crearBaseDatos(String nombreDB) throws SQLException{
        final String QUERY = "CREATE DATABASE " + nombreDB;
        PreparedStatement db = null;

        try {
            db = conexion.prepareStatement(QUERY);
            db.executeUpdate();

            conexion.commit();
            System.out.println("Base de datos: " + nombreDB + " creada con éxito.");
        } catch (SQLException e){
            System.out.println("Ha ocurrido un error al crear la base de datos.");
            conexion.rollback();
        } finally {
            if (db!=null){
                db.close();
            }
        }
    }

    public void crearTabla() throws SQLException {
        Scanner input = new Scanner(System.in);
        final String QUERY;
        PreparedStatement db = null;
        int atributos;
        String nombre;
        String [] listaAtributos;

        System.out.println("Nombre de la tabla: ");
        nombre = input.next();

        System.out.println("Cuántos atributos va a tener?");
        atributos = input.nextInt();
        listaAtributos = new String[atributos];

        QUERY = "Create TABLE " + nombre + " ";

        for (int i = 0; i <= atributos; i++) {
            System.out.println("Atributo " + (i+1) + " :");
            listaAtributos[i] = input.next();
        }


        for (int i = 0; i < listaAtributos.length ; i++) {
            String nombreAtr;

            System.out.println("Introduzca nombre del parametro: ");
            nombreAtr = input.next();
            QUERY = QUERY + " " + nombre;
        }

        try {
            db = conexion.prepareStatement(QUERY);
            db.executeUpdate();

            conexion.commit();
            System.out.println("Tabla creada con éxito.");
        } catch (SQLException e) {
            System.out.println("Ha habido un error intentando añadir la tabla");
            conexion.rollback();
        } finally {
            if (db!=null){
                db.close();
            }
        }
    }

    public void cambiarBaseDatos(String nombreDB) throws SQLException{
        final String query = "USE " + nombreDB;
        PreparedStatement cambio = null;

        try{
            cambio = conexion.prepareStatement(query);
            cambio.executeUpdate();

            System.out.println("Cambio de base de datos realizado con éxito.");
            this.nombre = nombreDB;
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error durante el cambio de base de datos.");
        } finally {
            if (cambio!=null){
                cambio.close();
            }
        }
    }
}
