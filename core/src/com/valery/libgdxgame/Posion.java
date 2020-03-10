package com.valery.libgdxgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Posion {

    int velocidad;
    Vector2 posicion;
    Texture aspecto;
    Rectangle rectangulo;
    int tipo;
    Personaje.Estados direccion;

    public Posion(int velocidad, Vector2 posicion, Texture aspecto, int tipo, Personaje.Estados direccion) {
        this.velocidad = velocidad;
        this.posicion = posicion;
        this.aspecto = aspecto;
        this.tipo = tipo;
        rectangulo = new Rectangle(posicion.x, posicion.y, aspecto.getWidth(), aspecto.getHeight());
        this.direccion = direccion;
    }

    private void mover(Vector2 direccion) {
        posicion.add(direccion.scl(velocidad));
        rectangulo.setPosition(posicion);
    }

    public void moverDerecha(){
        mover(new Vector2(1, 0));
    }

    public void moverIzquierda(){
        mover(new Vector2(-1, 0));
    }

    public void moverArriba(){
        mover(new Vector2(0, 1));
    }

    public void moverAbajo(){
        mover(new Vector2(0, -1));
    }
    public void dibujar(SpriteBatch batch){
        batch.draw(aspecto, posicion.x, posicion.y);
    }
}
