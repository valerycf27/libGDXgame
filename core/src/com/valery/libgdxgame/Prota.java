package com.valery.libgdxgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Prota extends Personaje{

    public static int CADENCIA = 500;
    public static int TIEMPO_RECARGA = 2000;
    public static int TIEMPO_MIN_POCIONES = 5000;
    public static int TIEMPO_MAX_POCIONES = 8000;
    public static int BALAS_CARGADOR = 10;
    private int cargador;
    private boolean puedeDisparar;
    private long momentoUltimaRecarga, momentoUltimoDisparo;
    private int nivel;
    private int puntos;
    private boolean inmune;

    public Prota(int vida, int velocidad, Vector2 posicion, Array<TextureRegion> ... textureRegionArray) {
        super(vida,velocidad, posicion, textureRegionArray);
        inmune = false;
        puntos = 0;
        puedeDisparar=true;
        cargador = BALAS_CARGADOR;
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

    public int getCargador() {
        return cargador;
    }

    public void setCargador(int cargador) {
        this.cargador = cargador;
    }

    public boolean isPuedeDisparar() {
        return puedeDisparar;
    }

    public void setPuedeDisparar(boolean puedeDisparar) {
        this.puedeDisparar = puedeDisparar;
    }

    public long getMomentoUltimaRecarga() {
        return momentoUltimaRecarga;
    }

    public void setMomentoUltimaRecarga(long momentoUltimaRecarga) {
        this.momentoUltimaRecarga = momentoUltimaRecarga;
    }

    public long getMomentoUltimoDisparo() {
        return momentoUltimoDisparo;
    }

    public void setMomentoUltimoDisparo(long momentoUltimoDisparo) {
        this.momentoUltimoDisparo = momentoUltimoDisparo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void recargar() {
        if (TimeUtils.millis() - momentoUltimaRecarga > TIEMPO_RECARGA) {
            this.puedeDisparar = true;
            this.setCargador(BALAS_CARGADOR);
            momentoUltimaRecarga = TimeUtils.millis();
        }
    }

}
