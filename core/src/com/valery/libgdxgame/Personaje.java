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

    private int vida;
    private int velocidad;
    private Vector2 posicion;
    public Rectangle rectangulo;

    public static final int MIN_GENERAR_ENEMIGOS = 1000;

    private Animation animacionDerecha, animacionIzquierda, animacionArriba, animacionAbajo;
    private Array<TextureRegion> textureRegionArrayDerecha = new Array<>();
    private Array<TextureRegion> textureRegionArrayIzquierda = new Array<>();
    private Array<TextureRegion> textureRegionArrayArriba = new Array<>();
    private Array<TextureRegion> textureRegionArrayAbajo = new Array<>();
    public Estados estado;
    private float stateTime;
    private TextureRegion texturaActual;


    public enum Estados{
        QUIETO, DERECHA, IZQUIERDA, ARRIBA, ABAJO
    }

    public Personaje(int vida, int velocidad, Vector2 posicion, Array<TextureRegion> ... textureRegionArray ) {
        this.vida = vida;
        this.velocidad = velocidad;
        this.posicion=posicion;

        inicializarAnimaciones(textureRegionArray);
        estado = Estados.QUIETO;
        this.rectangulo = new Rectangle(posicion.x, posicion.y ,textureRegionArrayDerecha.get(0).getRegionWidth(), textureRegionArrayDerecha.get(0).getRegionHeight());


        /*textureRegionArrayDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde1.png"))));
        textureRegionArrayDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde2.png"))));
        textureRegionArrayDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde3.png"))));

        this.animacionDerecha = new Animation(0.25f, textureRegionArrayDerecha, Animation.PlayMode.LOOP);

        textureRegionArrayIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde1R.png"))));
        textureRegionArrayIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde2R.png"))));
        textureRegionArrayIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/protaVerde3R.png"))));

        this.animacionIzquierda = new Animation(0.25f, textureRegionArrayIzquierda, Animation.PlayMode.LOOP);*/
    }

    public Personaje(int velocidad, Vector2 posicion, Array<TextureRegion> ... textureRegionArray ) {
        this.vida = 0;
        this.velocidad = velocidad;
        this.posicion = posicion;

        inicializarAnimaciones(textureRegionArray);
        estado = Estados.QUIETO;

        rectangulo = new Rectangle(posicion.x, posicion.y, textureRegionArrayDerecha.get(0).getRegionWidth() , textureRegionArrayDerecha.get(0).getRegionHeight());
    }

    private void inicializarAnimaciones(Array<TextureRegion>[] textureRegionArray) {

        if (textureRegionArray.length == 1){
            textureRegionArrayDerecha = textureRegionArray[0];
            animacionDerecha = new Animation(0.25f, textureRegionArrayDerecha);
            textureRegionArrayIzquierda = textureRegionArray[0];
            animacionIzquierda = new Animation(0.25f, textureRegionArrayIzquierda);

        }else{
            textureRegionArrayDerecha = textureRegionArray[0];
            textureRegionArrayIzquierda = textureRegionArray[1];

            animacionDerecha = new Animation(0.25f, textureRegionArrayDerecha);
            animacionIzquierda = new Animation(0.25f, textureRegionArrayIzquierda);
        }

    }

//Getters y setters
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

    public TextureRegion getTexturaActual() {
        return texturaActual;
    }

    public void setTexturaActual(TextureRegion texturaActual) {
        this.texturaActual = texturaActual;
    }



    public void mover(Vector2 vector){
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
            case IZQUIERDA:
                texturaActual = (TextureRegion) animacionIzquierda.getKeyFrame(stateTime, true);
                break;
            case ABAJO:
            case ARRIBA:
            case DERECHA:
                texturaActual = (TextureRegion) animacionDerecha.getKeyFrame(stateTime, true);
                break;
            case QUIETO:
            default:
                texturaActual = (TextureRegion) animacionDerecha.getKeyFrame(0, true);
                break;

        }
    }

    public void dibujar(SpriteBatch batch){
        batch.draw(texturaActual, getPosicion().x, getPosicion().y);
    }


}
