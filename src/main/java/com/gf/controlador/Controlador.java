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
import com.gf.modelo.entidades.Ranking;
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
import java.util.Random;
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
    private ArrayList<JButton> botones = null;
    private ArrayList<ImageIcon> lista = null;
    private int num = 0;
    private Timer tiempo;
    private int cont;
    private boolean faseAprendizaje = true;

    public Controlador(RankingDAO rankingDAO, FormaDAO formaDAO, NumeroDAO numeroDAO, VocalDAO vocalDAO) {
        try {
            this.rankingDAO = rankingDAO;
            this.formaDAO = formaDAO;
            this.numeroDAO = numeroDAO;
            this.vocalDAO = vocalDAO;
            this.urlCancion = ".\\src\\main\\java\\com\\gf\\recursos\\InicioAplicacion.wav"; //Asignamos la cancion
            this.vistaPrincipal = new VistaPrincipal();
            //Asignamos los listener a los botones de la vista principal
            this.vistaPrincipal.getBotonAjustes().addActionListener(this);
            this.vistaPrincipal.getBotonComenzar().addActionListener(this);
            this.vistaPrincipal.getBotonRanking().addActionListener(this);
            //Asignamos los listener a los botones de la vista de la eleccion
            this.vistaPrincipal.getVistaEleccion().getBotonFiguras().addActionListener(this);
            this.vistaPrincipal.getVistaEleccion().getBotonNumeros().addActionListener(this);
            this.vistaPrincipal.getVistaEleccion().getBotonVocales().addActionListener(this);
            this.vistaPrincipal.setVisible(true);
            this.vistaPrincipal.getVistaRanking().addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {//Metodo para saber si se ha cerrado la ventana del ranking
                    try {
                        setUrlCancion(".\\src\\main\\java\\com\\gf\\recursos\\InicioAplicacion.wav"); //Asignamos la cancion
                        playMusic(false);
                        setSonido();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
            setSonido();
            this.dtm = getTabla();//Metodo para asignar el modelo de la tabla
            //Metodos para asignar el modelo a la tabla y para darle formato
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
        //Obtenemos los datos del RankingDAO
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

    private void setImagenes() {//Asignamos los botones y las imagenes del juego
        this.botones = vistaJuego.getAllBotones();

        if (this.mode == 1) {
            this.lista = formaDAO.getAllImagenes();

        } else if (this.mode == 2) {
            this.lista = vocalDAO.getAllImagenes();

        } else if (this.mode == 3) {
            this.lista = numeroDAO.getAllImagenes();

        }
        desordenar();
        for (int i = 0; i < botones.size(); i++) {
            //Asignamos el listener para cada boton del juego
            this.botones.get(i).addMouseListener(this);
        }
    }

    private void desordenar() {
        Random r = new Random();
        ArrayList<JButton> listaAuxBotones = new ArrayList<>();
        ArrayList<ImageIcon> listaAuxImagenes = new ArrayList<>();
        while (listaAuxBotones.size() < this.botones.size()) {
            if (this.mode == 3) {
                int num = r.nextInt(10);
                JButton boton = this.botones.get(num);
                ImageIcon imagen=this.lista.get(num);
                if (listaAuxBotones.contains(boton)) {
                    num = r.nextInt(10);
                } else {
                    listaAuxBotones.add(boton);
                    listaAuxImagenes.add(imagen);
                }
            }else{
                int num = r.nextInt(5);
                JButton boton = this.botones.get(num);
                ImageIcon imagen=this.lista.get(num);
                if (listaAuxBotones.contains(boton)) {
                    num = r.nextInt(5);
                } else {
                    listaAuxBotones.add(boton);
                    listaAuxImagenes.add(imagen);
                }
            }
        }
        this.botones=listaAuxBotones;
        this.lista=listaAuxImagenes;
    }

    private void setJuego() {
        this.num = 0;
        this.vistaJuego = new VistaJuego(this.mode);
        setImagenes();
        this.vistaJuego.getBotonAdivinar().setIcon(lista.get(num));
        this.vistaPrincipal.getVistaEleccion().setVisible(false);//Cerramos la ventana de la eleccion
        this.vistaJuego.setVisible(true);
        this.cont = 15;
        this.tiempo = new Timer(1000, this);//Usamos el timer para saber el tiempo
        this.tiempo.start();
        this.vistaJuego.getLabelReloj().setIcon(new ImageIcon(".\\src\\main\\java\\com\\gf\\recursos\\Reloj.gif"));

    }

    private void segundo() {
        this.vistaJuego.getLabelTemporizador().setText(String.valueOf(cont));
        if (this.faseAprendizaje) {
            //Si esta en la fase de aprendizaje, el contador ira hacia atras
            this.cont--;
            if (this.cont == 0) {//Si llega a cero, la fase de aprendizaje habra acabado
                this.faseAprendizaje = false;
                this.vistaJuego.getLabelReloj().setIcon(null);
                this.vistaJuego.getLabelAprendizaje().setText("FASE DE ADIVINANZA");
            }
        } else if (this.faseAprendizaje == false) {
            this.cont++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == this.vistaPrincipal.getBotonAjustes()) {
                //Este metodo nos servira para controlar de manera grafica el sonido
                int opcion = JOptionPane.showOptionDialog(null, "¿Quieres mantener la musica?", "Memory", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Encender musica", "Apagar musica"}, null);

                if (opcion == 0) {
                    playMusic(true);
                } else if (opcion == 1) {
                    playMusic(false);
                }

            } else if (e.getSource() == this.vistaPrincipal.getBotonComenzar()) {
                //Si damos al boton de comenzar, sacara una eleccion para saber a cual quieres jugar
                this.vistaPrincipal.getVistaEleccion().setVisible(true);

            } else if (e.getSource() == this.vistaPrincipal.getBotonRanking()) {
                //Sirve para ver el ranking 
                this.urlCancion = ".\\src\\main\\java\\com\\gf\\recursos\\Ranking.wav";
                playMusic(false);
                setSonido();

                this.vistaPrincipal.getVistaRanking().setVisible(true);
            } else if (e.getSource() == this.vistaPrincipal.getVistaEleccion().getBotonFiguras()) {
                //Sirve para empezar a jugar con las figuras
                this.mode = 1;
                setJuego();

            } else if (e.getSource() == this.vistaPrincipal.getVistaEleccion().getBotonVocales()) {
                //Sirve para empezar a jugar con las vocales
                this.mode = 2;
                setJuego();
            } else if (e.getSource() == this.vistaPrincipal.getVistaEleccion().getBotonNumeros()) {
                //Sirve para empezar a jugar con los numeros
                this.mode = 3;
                setJuego();

            } else if (e.getSource() == tiempo) {
                //Controlamos que cada vez que pase 1 segundo, se lo añada al contador
                segundo();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Controlador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton boton = (JButton) e.getSource();
        int numBot = botones.indexOf(boton);
        if (numBot == num && num < botones.size() && this.faseAprendizaje == false) {
            //Controlamos que este en fase de aprendizaje y que sea el mismo boton
            num++;
            this.vistaJuego.getBotonAdivinar().setIcon(lista.get(num));
            botones.get(numBot).setIcon(lista.get(numBot));
        } else if (num == botones.size()) {
            //Sirve para saber si hemos ganado ya
            tiempo.stop();
            JOptionPane.showMessageDialog(null, "Ganaste el juego en " + this.cont + " segundos!");
            this.vistaJuego.setVisible(false);
            rankingDAO.insert(new Ranking(rankingDAO.getUltimoId() + 1, cont));
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
        if (this.faseAprendizaje) {
            //Controlamos la imagen al entrar o salir del boton
            JButton boton = (JButton) e.getSource();
            int numBot = this.botones.indexOf(boton);
            this.botones.get(numBot).setIcon(this.lista.get(numBot));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Cuando salimos, asignamos el boton para que no aparezca la imagen
        JButton boton = (JButton) e.getSource();
        boton.setIcon(null);
    }

}
