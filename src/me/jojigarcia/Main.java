package me.jojigarcia;

import me.jojigarcia.Controller.DBKApp;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        DBKApp dbkApp = new DBKApp();
        try{
            dbkApp.run();
        }catch (SQLException e){
            System.out.println("Ha habido algún error. Revise usuario y contraseña, por favor.");
        }

    }
}
