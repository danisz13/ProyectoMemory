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
import com.gf.modelo.entidades.Forma;
import com.gf.vista.VistaJuego;
import com.gf.vista.VistaPrincipal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sanarrda
 */
public class Controlador implements ActionListener, MouseListener {

    private VistaPrincipal vistaPrincipal;
    private VistaJuego vistaJuego;
    private RankingDAO rankingDAO;
    private NumeroDAO numeroDAO;
    private VocalDAO vocalDAO;
    private FormaDAO formaDAO;
    private AudioInputStream audioInputStream;
    private Clip clip;
    private String urlCancion;
    private DefaultTableModel dtm;
    private int mode;
    private ImageIcon imagen;
    private ArrayList<JButton> botones = null;
    private ArrayList<ImageIcon> lista = null;
    private int num = 0;
    private boolean empezado = false;
    private Timer tiempo;
    private int cont;

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
            this.vistaPrincipal.getVistaEleccion().getBotonFiguras().addActionListener(this);
            this.vistaPrincipal.getVistaEleccion().getBotonNumeros().addActionListener(this);
            this.vistaPrincipal.getVistaEleccion().getBotonVocales().addActionListener(this);
            this.vistaPrincipal.setVisible(true);
            this.vistaPrincipal.getVistaRanking().addWindowListener(new java.awt.event.WindowAdapter() {
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
            this.dtm = getTabla();

            this.vistaPrincipal.getVistaRanking().getTablaRanking().setModel(this.dtm);
            this.vistaPrincipal.getVistaRanking().getTablaRanking().setBackground(Color.blue);
            this.vistaPrincipal.getVistaRanking().getTablaRanking().setForeground(Color.white);
            this.vistaPrincipal.getVistaRanking().getTablaRanking().setEnabled(false);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setUrlCancion(String urlCancion) {
        this.urlCancion = urlCancion;
    }

    private DefaultTableModel getTabla() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(this.rankingDAO.getDatos(), this.rankingDAO.getColumnas());
        return dtm;
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

    private void setImagenes() {
        this.botones = vistaJuego.getAllBotones();

        if (this.mode == 1) {
            this.lista = formaDAO.getAllImagenes();

        } else if (this.mode == 2) {
            this.lista = vocalDAO.getAllImagenes();

        } else if (this.mode == 3) {
            this.lista = numeroDAO.getAllImagenes();

        }
        for (int i = 0; i < botones.size(); i++) {
            this.botones.get(i).setIcon(lista.get(i));
            this.botones.get(i).addMouseListener(this);
        }
    }

    private void setJuego() {
        this.num = 0;
        this.vistaJuego = new VistaJuego(this.mode);
        setImagenes();
        this.vistaJuego.getBotonAdivinar().setIcon(lista.get(num));
        this.vistaPrincipal.getVistaEleccion().setVisible(false);
        this.vistaJuego.setVisible(true);
        this.tiempo=new Timer(0, this);
        this.tiempo.setRepeats(false);
        this.tiempo.start();
        
        
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

                this.vistaPrincipal.getVistaEleccion().setVisible(true);

            } else if (e.getSource() == this.vistaPrincipal.getBotonRanking()) {
                this.urlCancion = ".\\src\\main\\java\\com\\gf\\recursos\\Ranking.wav";
                playMusic(false);
                setSonido();

                this.vistaPrincipal.getVistaRanking().setVisible(true);
            } else if (e.getSource() == this.vistaPrincipal.getVistaEleccion().getBotonFiguras()) {
                this.mode = 1;
                setJuego();

            } else if (e.getSource() == this.vistaPrincipal.getVistaEleccion().getBotonVocales()) {
                this.mode = 2;
                setJuego();
            } else if (e.getSource() == this.vistaPrincipal.getVistaEleccion().getBotonNumeros()) {
                this.mode = 3;
                setJuego();
                
            } else if (e.getSource() == tiempo) {
                cont++;
                this.vistaJuego.getLabelTemporizador().setText(String.valueOf(cont));
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Controlador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton boton = (JButton) e.getSource();
        if (boton.getIcon() == this.vistaJuego.getBotonAdivinar().getIcon() && num < botones.size()) {
            num++;
            this.vistaJuego.getBotonAdivinar().setIcon(lista.get(num));
        } 
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

        JButton boton = (JButton) e.getSource();

    }

    @Override
    public void mouseExited(MouseEvent e) {

        JButton boton = (JButton) e.getSource();

    }

}
