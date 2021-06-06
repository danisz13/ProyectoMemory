/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Dani
 */
public class VistaJuego extends javax.swing.JFrame {

    private int mode;
    private final int JUEGO_FORMAS = 1;
    private final int JUEGO_VOCAL = 2;
    private final int JUEGO_NUMERO = 3;
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Creates new form VistaJuego
     */
    public VistaJuego(int mode) {
        initComponents();
        this.mode = mode;
        setFrame();
        if (this.mode == JUEGO_NUMERO) {
            for (int i = 0; getAllBotones().size() < 10; i++) {
                this.getPanelBotones().add(new JButton());

            }

        }

    }

    private void setFrame() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setSize(dim);
        this.panelBotones.setBackground(Color.red);
    }

    

    public JButton getBotonAdivinar() {
        return botonAdivinar;
    }

    public JButton getjButton1() {
        return jButton1;
    }

    public JButton getjButton2() {
        return jButton2;
    }

    public JButton getjButton3() {
        return jButton3;
    }

    public JButton getjButton4() {
        return jButton4;
    }

    public JButton getjButton5() {
        return jButton5;
    }

    public JLabel getLabelTemporizador() {
        return labelTemporizador;
    }

    public JPanel getPanelAdivinar() {
        return panelAdivinar;
    }

    public JPanel getPanelBotones() {
        return panelBotones;
    }

    public void setBotonAdivinar(JButton botonAdivinar) {
        this.botonAdivinar = botonAdivinar;
    }

    public void setjButton1(JButton jButton1) {
        this.jButton1 = jButton1;
    }

    public void setjButton2(JButton jButton2) {
        this.jButton2 = jButton2;
    }

    public void setjButton3(JButton jButton3) {
        this.jButton3 = jButton3;
    }

    public void setjButton4(JButton jButton4) {
        this.jButton4 = jButton4;
    }

    public void setjButton5(JButton jButton5) {
        this.jButton5 = jButton5;
    }

    public void setLabelTemporizador(JLabel labelTemporizador) {
        this.labelTemporizador = labelTemporizador;
    }

    public void setPanelAdivinar(JPanel panelAdivinar) {
        this.panelAdivinar = panelAdivinar;
    }

    public void setPanelBotones(JPanel panelBotones) {
        this.panelBotones = panelBotones;
    }

    public ArrayList<JButton> getAllBotones() {
        ArrayList<JButton> lista = new ArrayList<>();
        Component[] cont = this.getPanelBotones().getComponents();
        for (Component comp : cont) {
            lista.add((JButton) comp);
        }
        return lista;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBotones = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        panelAdivinar = new javax.swing.JPanel();
        botonAdivinar = new javax.swing.JButton();
        labelTemporizador = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelBotones.setLayout(new java.awt.GridLayout(4, 0));
        panelBotones.add(jButton1);
        panelBotones.add(jButton2);
        panelBotones.add(jButton3);
        panelBotones.add(jButton4);
        panelBotones.add(jButton5);

        javax.swing.GroupLayout panelAdivinarLayout = new javax.swing.GroupLayout(panelAdivinar);
        panelAdivinar.setLayout(panelAdivinarLayout);
        panelAdivinarLayout.setHorizontalGroup(
            panelAdivinarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdivinarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelAdivinarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonAdivinar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAdivinarLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(labelTemporizador, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelAdivinarLayout.setVerticalGroup(
            panelAdivinarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdivinarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(botonAdivinar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelTemporizador, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAdivinar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelAdivinar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAdivinar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel labelTemporizador;
    private javax.swing.JPanel panelAdivinar;
    private javax.swing.JPanel panelBotones;
    // End of variables declaration//GEN-END:variables
}
