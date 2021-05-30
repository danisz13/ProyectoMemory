/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.app;

import com.gf.controlador.Controlador;
import com.gf.modelo.dao.RankingDAO;

/**
 *
 * @author sanarrda
 */
public class Principal {
    public static void main(String[] args) {
        RankingDAO modeloRanking =new RankingDAO();
        Controlador controlador=new Controlador(modeloRanking);
        
    }
    
}
