/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.modelo.entidades;

/**
 *
 * @author sanarrda
 */
public class Ranking {

    private int id;
    private int segundos;

    public Ranking() {
    }

    public Ranking(int id, int segundos) {
        this.id = id;
        this.segundos = segundos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

}
