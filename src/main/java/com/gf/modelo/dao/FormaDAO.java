/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.modelo.dao;

import com.gf.conexion.Conexion;
import com.gf.modelo.entidades.Forma;
import com.gf.modelo.entidades.Vocal;
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
public class FormaDAO {
     public int insert(Forma forma) {
        String sql = "Insert into formas values (?,?)";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, forma.getId());
            ps.setBlob(2, forma.getImagen());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FormaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public int delete(Forma forma) {
        String sql = "delete from formas where id=?";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, forma.getId());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FormaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public int update(Forma forma) {
        String sql = "update formas set imagen=? where id=?";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {

            ps.setBlob(1, forma.getImagen());
            ps.setInt(2, forma.getId());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FormaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public Forma get(Integer id) {
        String sql = "select * from formas where id=?";
        Forma forma = null;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                forma = new Forma(rs.getInt("1"), rs.getBlob("2"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FormaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return forma;
    }

    public List<Forma> getAll() {
        String sql = "select * from vocales";
        List<Forma> lista = new ArrayList<>();
        try (Statement st = Conexion.abrirConexion().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Forma(rs.getInt("1"), rs.getBlob("2")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FormaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
