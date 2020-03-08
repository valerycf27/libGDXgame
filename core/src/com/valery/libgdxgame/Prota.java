package com.valery.libgdxgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Prota extends Personaje{
    private int puntos;
    private boolean inmune;

    public Prota(int vida, int velocidad, Vector2 posicion) {
        super(vida,velocidad, posicion);
        inmune = false;
        puntos = 0;
    }

    public int getPuntos() {
        return puntos;
    }
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    public boolean isInmune() {
        return inmune;
    }

    public void setInmune(boolean inmune) {
        this.inmune = inmune;
    }

    /*public void cambiarEstado(){
        this.inmune = !this.inmune;
    }*/


}
