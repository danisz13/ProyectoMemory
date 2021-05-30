/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gf.modelo.entidades;

import java.sql.Blob;

/**
 *
 * @author sanarrda
 */
public class Forma {
    
    private int id;
    private Blob imagen;

    public Forma() {
    }

    public Forma(int id, Blob imagen) {
        this.id = id;
        this.imagen = imagen;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }
    
}
