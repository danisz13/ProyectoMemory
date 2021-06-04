/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.controlador;

import com.gf.modelo.dao.FormaDAO;
import com.gf.modelo.dao.NumeroDAO;
import com.gf.modelo.dao.RankingDAO;
import com.gf.modelo.dao.VocalDAO;
import com.gf.vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 *
 * @author sanarrda
 */
public class Controlador implements ActionListener {

    private VistaPrincipal vistaPrincipal;
    private RankingDAO rankingDAO;
    private NumeroDAO numeroDAO;
    private VocalDAO vocalDAO;
    private FormaDAO formaDAO;
    private AudioInputStream audioInputStream;
    private Clip clip;
    private String urlCancion;
    

    public Controlador(RankingDAO rankingDAO, FormaDAO formaDAO, NumeroDAO numeroDAO, VocalDAO vocalDAO) {
        try {
            this.rankingDAO = rankingDAO;
            this.formaDAO = formaDAO;
            this.numeroDAO = numeroDAO;
            this.vocalDAO = vocalDAO;
            this.urlCancion = ".\\src\\main\\java\\com\\gf\\recursos\\InicioAplicacion.wav";
            this.vistaPrincipal = new VistaPrincipal();
            this.vistaPrincipal.getBotonAjustes().addActionListener(this);
            this.vistaPrincipal.getBotonComenzar().addActionListener(this);
            this.vistaPrincipal.getBotonRanking().addActionListener(this);
            this.vistaPrincipal.setVisible(true);
            this.vistaPrincipal.getVistaJuegoRanking().addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    try {
                        setUrlCancion(".\\src\\main\\java\\com\\gf\\recursos\\InicioAplicacion.wav");
                        playMusic(false);
                        setSonido();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
            setSonido();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setUrlCancion(String urlCancion) {
        this.urlCancion = urlCancion;
    }

    public void setSonido() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //Este metodo sirve para indicar que la cancion sea la indicada y la reproduzca en bucle
        this.audioInputStream = AudioSystem.getAudioInputStream(new File(this.urlCancion).getAbsoluteFile());
        this.clip = AudioSystem.getClip();
        this.clip.open(this.audioInputStream);
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void playMusic(boolean on) {
        if (this.clip.isRunning()) { //Controlo si la cancion esta sonando o no

            if (on == false) {
                this.clip.stop();
                this.clip.setMicrosecondPosition(0); //Reinicio la cancion
            }
        } else {
            if (on == true) {
                this.clip.start();

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == this.vistaPrincipal.getBotonAjustes()) {
                int opcion = JOptionPane.showOptionDialog(null, "¿Quieres mantener la musica?", "Memory", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Encender musica", "Apagar musica"}, null);

                if (opcion == 0) {
                    playMusic(true);
                } else if (opcion == 1) {
                    playMusic(false);
                }

            } else if (e.getSource() == this.vistaPrincipal.getBotonComenzar()) {
                playMusic(false);
                this.vistaPrincipal.getVistaEleccion().setVisible(true);

            } else if (e.getSource() == this.vistaPrincipal.getBotonRanking()) {
                this.urlCancion = ".\\src\\main\\java\\com\\gf\\recursos\\Ranking.wav";
                playMusic(false);
                setSonido();
                this.vistaPrincipal.getVistaJuegoRanking().setMode(1);
                this.vistaPrincipal.getVistaJuegoRanking().setVisible(true);
            } 

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Controlador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
