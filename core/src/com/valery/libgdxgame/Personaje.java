package com.valery.libgdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Personaje {
    private Animation animacionDer, animacionIzq;
    Array<TextureRegion> textureRegionArrayDer = new Array<>();
    Array<TextureRegion> textureRegionArrayIzq = new Array<>();
    float stateTime;
    TextureRegion texturaActual;

    int vida;
    int velocidad;
    Vector2 posicion;
    Rectangle rectangulo;

    public static enum Estados{
        DERECHA, IZQUIERDA
    }

    public Estados estado;

    public Personaje(int vida, int velocidad, Vector2 posicion) {
        textureRegionArrayDer.add(new Sprite(new Texture(Gdx.files.internal("protaVerde1.png"))));
        textureRegionArrayDer.add(new Sprite(new Texture(Gdx.files.internal("protaVerde2.png"))));
        textureRegionArrayDer.add(new Sprite(new Texture(Gdx.files.internal("protaVerde3.png"))));

        this.animacionDer = new Animation(0.25f, textureRegionArrayDer, Animation.PlayMode.LOOP);

        textureRegionArrayIzq.add(new Sprite(new Texture(Gdx.files.internal("protaVerde1 - copia.png"))));
        textureRegionArrayIzq.add(new Sprite(new Texture(Gdx.files.internal("protaVerde2 - copia.png"))));
        textureRegionArrayIzq.add(new Sprite(new Texture(Gdx.files.internal("protaVerde3 - copia.png"))));

        this.animacionIzq = new Animation(0.25f, textureRegionArrayIzq, Animation.PlayMode.LOOP);

        estado = Estados.DERECHA;

        this.vida = vida;
        this.velocidad = velocidad;
        this.posicion=posicion;
        this.rectangulo = new Rectangle(posicion.x, posicion.y ,textureRegionArrayDer.get(0).getRegionWidth(), textureRegionArrayDer.get(0).getRegionHeight());
    }

/*
    public Texture getTextura() {
        return textura;
    }

    public void setTextura(Texture textura) {
        this.textura = textura;
    }

 */

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
    }

    public Rectangle getRectangulo() {
        return rectangulo;
    }

    public void setRectangulo(Rectangle rectangulo) {
        this.rectangulo = rectangulo;
    }



    public void mover( Vector2 vector){
        posicion.add(vector.scl(velocidad));
        rectangulo.setPosition(posicion);
    }

    public void moverDerecha(){
        mover( new Vector2(1,0));
    }
    public void moverIzquierda(){
        mover(new Vector2(-1,0));
    }
    public void moverArriba(){
        mover(new Vector2(0,1));
    }
    public void moverAbajo(){
        mover(new Vector2(0,-1));
    }

    public void actualizar(float dt){
        stateTime += dt;

        switch (estado){

            case DERECHA:
                texturaActual = (TextureRegion) animacionDer.getKeyFrame(stateTime);
                break;

            case IZQUIERDA:
                texturaActual = (TextureRegion) animacionIzq.getKeyFrame(stateTime);
                break;
        }


    }

    public void dibujar(SpriteBatch batch){
        batch.draw(texturaActual, posicion.x, posicion.y);
    }


}
