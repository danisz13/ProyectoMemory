/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.app;

import com.gf.controlador.Controlador;
import com.gf.modelo.dao.FormaDAO;
import com.gf.modelo.dao.NumeroDAO;
import com.gf.modelo.dao.RankingDAO;
import com.gf.modelo.dao.VocalDAO;

/**
 *
 * @author sanarrda
 */
public class Principal {
    public static void main(String[] args) {
        RankingDAO modeloRanking =new RankingDAO();
        FormaDAO modeloForma =new FormaDAO();
        NumeroDAO modeloNumero =new NumeroDAO();
        VocalDAO modeloVocal =new VocalDAO();
        Controlador controlador=new Controlador(modeloRanking, modeloForma, modeloNumero, modeloVocal);
        
    }
    
}
