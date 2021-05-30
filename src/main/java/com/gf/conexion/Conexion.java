/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sanarrda
 * Clase para realizar la conexion con la base de datos 'dibujos'
 */
public class Conexion {
    private static final String url = "jdbc:mysql://localhost:3306/memory";
    private static final String user = "root";
    private static final String password = "";
    
    public static Connection abrirConexion() throws SQLException {    
        return DriverManager.getConnection(url, user, password);
    }
}
