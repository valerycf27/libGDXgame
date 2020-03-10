package com.valery.libgdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class SegundoNivel implements Screen {
    Juego juego;
    Prota prota;
    Enemigo boss;
    private Array<TextureRegion> textureRegionArrayBoss = new Array<>();
    private Array<TextureRegion> textureRegionArrayBala = new Array<>();

    Array<Bala> balasArrayprota;

    long ultimoDisparoBoss;

    boolean lucha;

    Vector2 tmpVector;

    Sound sonidoDisparo;
    Music musica;

    public SegundoNivel(Juego juego, Prota prota) {
        this.juego = juego;
        this.prota = prota;

        inicializarArraysTexturas();
        float posX = Constantes.ANCHO_PANTALLA / 2f - textureRegionArrayBoss.get(0).getRegionWidth() / 2f;
        boss = new Enemigo(3, 2, new Vector2(posX, Constantes.ALTO_PANTALLA), textureRegionArrayBoss);

        prota.setPosicion(new Vector2(Constantes.ANCHO_PANTALLA / 2f, 0));

        balasArrayprota = new Array<>();

        ultimoDisparoBoss = TimeUtils.millis();
        lucha = false;
        tmpVector = new Vector2();

        sonidoDisparo = Gdx.audio.newSound(Gdx.files.internal("Sonido/disparo.mp3"));
        musica = Gdx.audio.newMusic(Gdx.files.internal("Sonido/item.wav"));

        if (ConfigurationManager.isSoundEnabled()) {
            musica.setLooping(true);
            musica.play();
        }


    }

    private void inicializarArraysTexturas() {

        textureRegionArrayBoss.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Enemigo/Boss/Boss1.png"))));
        textureRegionArrayBoss.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Enemigo/Boss/Boss2.png"))));
        textureRegionArrayBoss.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Enemigo/Boss/Boss3.png"))));

        textureRegionArrayBala.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Fuego/llama.png"))));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        actualizarr(delta);
        dibujar();
    }

    private void actualizarr(float dt) {

        if (lucha) {
            acionesTeclado(dt);
            comprobarLimitesPantalla();
        }
        moverNPCs(dt);
        comprobarColisiones();
    }

    private void acionesTeclado(float dt) {
        prota.actualizar(dt);
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                prota.estado = Personaje.Estados.DERECHA;
                prota.moverDerecha();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                prota.estado = Personaje.Estados.IZQUIERDA;
                prota.moverIzquierda();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                prota.estado = Personaje.Estados.ARRIBA;
                prota.moverArriba();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                prota.estado = Personaje.Estados.ABAJO;
                prota.moverAbajo();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                prota.setPuedeDisparar(false);
                prota.setMomentoUltimaRecarga(TimeUtils.millis());
                prota.recargar();
            }
            if (!prota.isPuedeDisparar())
                prota.recargar();
            else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                disparar();
            }
        } else {
            prota.estado = Personaje.Estados.QUIETO;
        }

    }

    private void disparar() {
        if (TimeUtils.millis() - prota.getMomentoUltimoDisparo() > Prota.CADENCIA) {
            float posX = prota.getPosicion().x + prota.getTexturaActual().getRegionWidth() / 2f - textureRegionArrayBala.get(0).getRegionWidth();
            float posY = prota.getPosicion().y + prota.getTexturaActual().getRegionHeight();
            Bala bala = new Bala(10, new Vector2(posX, posY), textureRegionArrayBala);
            bala.estado = Personaje.Estados.ABAJO;
            balasArrayprota.add(bala);
            if (ConfigurationManager.isSoundEnabled())
                sonidoDisparo.play();
            prota.setCargador(prota.getCargador() - 1);
            prota.setMomentoUltimoDisparo(TimeUtils.millis());
            if (prota.getCargador() <= 0) {
                prota.setPuedeDisparar(false);
                prota.setMomentoUltimaRecarga(TimeUtils.millis());
            }
        }
    }

    private void comprobarLimitesPantalla() {
        if (prota.getPosicion().x < 0)
            prota.setPosicion(new Vector2(0, prota.getPosicion().y));

        if (prota.getPosicion().x > Constantes.ANCHO_PANTALLA - prota.getTexturaActual().getRegionWidth())
            prota.setPosicion(new Vector2(Constantes.ANCHO_PANTALLA - prota.getTexturaActual().getRegionWidth(), prota.getPosicion().y));

        if (prota.getPosicion().y > Constantes.ALTO_PANTALLA - prota.getTexturaActual().getRegionHeight())
            prota.setPosicion(new Vector2(prota.getPosicion().x, Constantes.ALTO_PANTALLA - prota.getTexturaActual().getRegionHeight()));

        if (prota.getPosicion().y < 0)
            prota.setPosicion(new Vector2(prota.getPosicion().x, 0));

        if (boss.getPosicion().x < 0)
            boss.setPosicion(new Vector2(0, boss.getPosicion().y));

        if (boss.getPosicion().x > Constantes.ANCHO_PANTALLA - boss.getTexturaActual().getRegionWidth())
            boss.setPosicion(new Vector2(Constantes.ANCHO_PANTALLA - boss.getTexturaActual().getRegionWidth(), boss.getPosicion().y));

        if (boss.getPosicion().y > Constantes.ALTO_PANTALLA - boss.getTexturaActual().getRegionHeight())
            boss.setPosicion(new Vector2(boss.getPosicion().x, Constantes.ALTO_PANTALLA - boss.getTexturaActual().getRegionHeight()));

        if (boss.getPosicion().y < 0)
            boss.setPosicion(new Vector2(boss.getPosicion().x, 0));

        for (Bala bala : balasArrayprota)
            if (bala.getPosicion().y > Constantes.ALTO_PANTALLA)
                balasArrayprota.removeValue(bala, true);
    }

    private void moverNPCs(float dt) {
        boss.actualizar(dt);
        if (!lucha && boss.getPosicion().y > Constantes.ALTO_PANTALLA * 3 / 4f) {
            boss.moverAbajo();
        } else {
            lucha = true;
            if (prota.estado != Personaje.Estados.QUIETO) {
                if (prota.getPosicion().y >= boss.getPosicion().y) {
                    if (prota.getPosicion().x >= boss.getPosicion().x) {
                        boss.mover(new Vector2(1, 1));
                    } else {
                        boss.mover(new Vector2(-1, 1));
                    }
                } else {
                    if (prota.getPosicion().x >= boss.getPosicion().x) {
                        boss.mover(new Vector2(1, -1));
                    } else {
                        boss.mover(new Vector2(-1, -1));
                    }
                }
            }
        }

        for (Bala bala : balasArrayprota) {
            bala.moverArriba();
            bala.actualizar(dt);
        }

    }

    private void comprobarColisiones() {

        for (Bala bala : balasArrayprota) {
            if (bala.rectangulo.overlaps(boss.rectangulo)) {
                balasArrayprota.removeValue(bala, true);
                boss.setVida(boss.getVida() - 1);
                if(boss.getVida() == 0){
                    juego.setScreen(new MenuPrincipal(juego));
                }
            }
        }
        if (boss.rectangulo.overlaps(prota.rectangulo)) {
            juego.setScreen(new MenuPrincipal(juego));
        }
    }


    private void dibujar() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        juego.batch.begin();

        juego.font.draw(juego.batch, "Vidas: " + prota.getVida(), 0, Constantes.ALTO_PANTALLA - juego.font.getXHeight());
        juego.font.draw(juego.batch, "Vida Boss: " + boss.getVida(), 0, Constantes.ALTO_PANTALLA - juego.font.getXHeight() * 3);
        juego.font.draw(juego.batch, "Nivel: " + prota.getNivel(), 0, Constantes.ALTO_PANTALLA - juego.font.getXHeight() * 7);
        if (prota.isPuedeDisparar())
            juego.font.draw(juego.batch, "Cargador: " + prota.getCargador(), 0, Constantes.ALTO_PANTALLA - juego.font.getXHeight() * 9);
        else
            juego.font.draw(juego.batch, "Recargando...", 0, Constantes.ALTO_PANTALLA - juego.font.getXHeight() * 9);

        prota.dibujar(juego.batch);
        boss.dibujar(juego.batch);
        for (Bala bala : balasArrayprota) {
            bala.dibujar(juego.batch);
        }
        juego.batch.end();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        if(musica.isPlaying())
            musica.stop();
    }

    @Override
    public void dispose() {

    }
}
