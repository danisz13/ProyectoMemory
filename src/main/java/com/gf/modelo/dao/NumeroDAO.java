/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.modelo.dao;

import com.gf.conexion.Conexion;
import com.gf.modelo.entidades.Numero;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dani
 */
public class NumeroDAO {

    public int insert(Numero numero) {
        String sql = "Insert into numeros values (?,?)";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, numero.getId());
            ps.setBlob(2, numero.getImagen());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(NumeroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public int delete(Numero numero) {
        String sql = "delete from numeros where id=?";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, numero.getId());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(NumeroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public int update(Numero numero) {
        String sql = "update numeros set imagen=? where id=?";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {

            ps.setBlob(1, numero.getImagen());
            ps.setInt(2, numero.getId());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(NumeroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public Numero get(Integer id) {
        String sql = "select * from numeros where id=?";
        Numero numero = null;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                numero = new Numero(rs.getInt("1"), rs.getBlob("2"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(NumeroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numero;
    }

    public List<Numero> getAll() {
        String sql = "select * from numeros";
        List<Numero> lista = new ArrayList<>();
        try (Statement st = Conexion.abrirConexion().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Numero(rs.getInt("1"), rs.getBlob("2")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(NumeroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
