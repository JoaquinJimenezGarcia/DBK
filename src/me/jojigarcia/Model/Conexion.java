package me.jojigarcia.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        final String query = "CREATE DATABASE " + nombreDB;
        PreparedStatement db = null;

        try {
            db = conexion.prepareStatement(query);
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
