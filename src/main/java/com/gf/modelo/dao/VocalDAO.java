/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.modelo.dao;

import com.gf.conexion.Conexion;
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
public class VocalDAO {
    public int insert(Vocal vocal) {
        String sql = "Insert into vocales values (?,?)";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, vocal.getId());
            ps.setBlob(2, vocal.getImagen());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(VocalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public int delete(Vocal vocal) {
        String sql = "delete from vocales where id=?";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, vocal.getId());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(VocalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public int update(Vocal vocal) {
        String sql = "update vocales set imagen=? where id=?";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {

            ps.setBlob(1, vocal.getImagen());
            ps.setInt(2, vocal.getId());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(VocalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public Vocal get(Integer id) {
        String sql = "select * from vocales where id=?";
        Vocal vocal = null;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                vocal = new Vocal(rs.getInt("1"), rs.getBlob("2"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(VocalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vocal;
    }

    public List<Vocal> getAll() {
        String sql = "select * from vocales";
        List<Vocal> lista = new ArrayList<>();
        try (Statement st = Conexion.abrirConexion().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Vocal(rs.getInt("1"), rs.getBlob("2")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(VocalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
