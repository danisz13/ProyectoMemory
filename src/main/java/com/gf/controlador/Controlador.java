/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.controlador;

import com.gf.modelo.dao.RankingDAO;
import com.gf.vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 *
 * @author sanarrda
 */
public class Controlador implements ActionListener {

    private VistaPrincipal vista;
    private RankingDAO rankingDAO;

    public Controlador(RankingDAO rankingDAO) {

        this.rankingDAO = rankingDAO;
        this.vista = new VistaPrincipal();
        this.vista.getBotonAjustes().addActionListener(this);
        this.vista.getBotonComenzar().addActionListener(this);
        this.vista.getBotonRanking().addActionListener(this);
        this.vista.setVisible(true);
        try {
            this.vista.setSonido(true);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == this.vista.getBotonAjustes()) {
                int opcion = JOptionPane.showOptionDialog(null, "¿Quieres mantener la musica?", "Memory", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, vista);

                if (opcion == 0) {
                    this.vista.setSonido(true);
                } else {
                    this.vista.setSonido(false);
                }

            } else if (e.getSource() == this.vista.getBotonComenzar()) {

                this.vista.getVistaEleccion().setVisible(true);
                this.vista.setSonido(false);

            } else if (e.getSource() == this.vista.getBotonRanking()) {

            }
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
