/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.modelo.dao;

import com.gf.conexion.Conexion;
import com.gf.modelo.entidades.Numero;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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

    public ArrayList<Numero> getAll() {
        String sql = "select * from numeros";
        ArrayList<Numero> lista = new ArrayList<>();
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
    public ArrayList<ImageIcon> getAllImagenes(){
        String sql = "select imagen from numeros";
        ArrayList<ImageIcon> lista = new ArrayList<>();
        Blob blob=null;
        try (Statement st = Conexion.abrirConexion().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                blob = rs.getBlob(1);
                byte[] data = blob.getBytes(1, (int)blob.length());
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                lista.add(new ImageIcon(img));
            }

        } catch (SQLException ex) {
            Logger.getLogger(NumeroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NumeroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
