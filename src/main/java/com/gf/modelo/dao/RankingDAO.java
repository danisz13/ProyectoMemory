/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.modelo.dao;

import com.gf.conexion.Conexion;
import com.gf.modelo.entidades.Ranking;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sanarrda
 */
public class RankingDAO {

    public int insert(Ranking ranking) {
        String sql = "Insert into ranking values (?,?)";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, ranking.getId());
            ps.setInt(2, ranking.getSegundos());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(RankingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public int delete(Ranking ranking) {
        String sql = "delete from ranking where id=?";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, ranking.getId());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(RankingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }

    public int update(Ranking ranking) {
        String sql = "update ranking set segundos=? where id=?";
        int filasAfectadas = 0;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {

            ps.setInt(1, ranking.getSegundos());
            ps.setInt(2, ranking.getId());
            filasAfectadas = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(RankingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas;
    }
    
    public Integer getUltimoId() {
        String sql = "select max(id) from ranking";
        Integer ranking = null;
        try (Statement st = Conexion.abrirConexion().prepareStatement(sql)) {
           
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                ranking=rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RankingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ranking;
    }
    
    public Ranking get(Integer id) {
        String sql = "select * from ranking where id=?";
        Ranking ranking = null;
        try (PreparedStatement ps = Conexion.abrirConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ranking = new Ranking(rs.getInt("1"), rs.getInt("2"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(RankingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ranking;
    }

    public List<Ranking> getAll() {
        String sql = "select * from ranking";
        List<Ranking> lista = new ArrayList<>();
        try (Statement st = Conexion.abrirConexion().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Ranking(rs.getInt("1"), rs.getInt("2")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(RankingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public Object[][] getDatos() {
        Object[][] datos = null;
        try (Statement st = Conexion.abrirConexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            String sql = "SELECT * FROM ranking";
            ResultSet rs = st.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();
            rs.last();

            int numFilas = rs.getRow();
            int numColumnas = rsmd.getColumnCount();
            datos = new Object[numFilas][numColumnas];

            //procesamos el resultSet de datos
            rs.beforeFirst();
            int i = 0; //Indicador de fila de la matriz
            int j = 0; //Indicador de columna de la matriz
            while (rs.next()) {
                for (j = 0; j < numColumnas; j++) {
                    datos[i][j] = rs.getObject(j + 1);
                }
                i++;
            }

            return datos;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return datos;
    }

    public Object[] getColumnas() {
        Object[] titulosColumnas = null;
        try (Statement st = Conexion.abrirConexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            String sql = "SELECT * FROM ranking";

            ResultSet rs = st.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumnas = rsmd.getColumnCount();

            titulosColumnas = new Object[numColumnas];

            //
            for (int i = 0; i < numColumnas; i++) {
                titulosColumnas[i] = rsmd.getColumnName(i + 1);
            }

            return titulosColumnas;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return titulosColumnas;
    }

}
