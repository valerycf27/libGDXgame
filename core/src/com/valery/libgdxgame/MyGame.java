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

import java.util.Random;

public class MyGame implements Screen {
    Juego juego;
    Prota prota;

    Array<TextureRegion> textureRegionArrayProtaDerecha = new Array<>();
    Array<TextureRegion> textureRegionArrayProtaIzquierda = new Array<>();

    Array<TextureRegion> textureRegionArrayBala = new Array<>();

    Array<TextureRegion> textureRegionArrayFatBird = new Array<>();
    Array<TextureRegion> textureRegionArrayRinoDerecha = new Array<>();
    Array<TextureRegion> textureRegionArrayRinoIzquierda = new Array<>();
    Array<Texture> textureArrayPowerUp = new Array<>();

    Array<Bala> balaArray;
    Array<Enemigo> enemigoArray;
    Array<Posion> powerUpArray;

    Random rand = new Random();

    long ultimoEnemigo, siguienteEnemigo;
    long ultimaPocion, siguientePocion;

    Music musica;
    Sound sonidoDisparo;


    public MyGame(Juego juego, Array<TextureRegion> textureRegionArrayProtaDerecha, Array<TextureRegion> textureRegionArrayProtaIzquierda) {
        this.juego = juego;

        this.textureRegionArrayProtaDerecha = textureRegionArrayProtaDerecha;
        this.textureRegionArrayProtaIzquierda = textureRegionArrayProtaIzquierda;

        inicializarTexturas();
        balaArray = new Array<>();
        enemigoArray = new Array<>();
        powerUpArray = new Array<>();

        prota = new Prota(2, 5, new Vector2(Constantes.ANCHO_PANTALLA / 2f, 0), textureRegionArrayProtaDerecha, textureRegionArrayProtaIzquierda);

        siguienteEnemigo = Prota.MIN_GENERAR_ENEMIGOS;
        ultimoEnemigo = TimeUtils.millis();

        siguientePocion = rand.nextInt(Prota.TIEMPO_MAX_POCIONES) + Prota.TIEMPO_MIN_POCIONES;
        ultimaPocion = TimeUtils.millis();

        sonidoDisparo = Gdx.audio.newSound(Gdx.files.internal("Sonido/disparo.mp3"));
        musica = Gdx.audio.newMusic(Gdx.files.internal("Sonido/Musica.ogg"));

        if (ConfigurationManager.isSoundEnabled()) {
            musica.setLooping(true);
            musica.play();
        }
    }

    private void inicializarTexturas() {

        textureRegionArrayBala.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Fuego/llama.png"))));

        textureRegionArrayFatBird.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Enemigo/FatBird/Pajaro1.png"))));
        textureRegionArrayFatBird.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Enemigo/FatBird/Pajaro2.png"))));
        textureRegionArrayFatBird.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Enemigo/FatBird/Pajaro3.png"))));

        textureRegionArrayRinoDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Enemigo/Rino/Rino1D.png"))));
        textureRegionArrayRinoDerecha.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Enemigo/Rino/Rino2D.png"))));
        textureRegionArrayRinoIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Enemigo/Rino/Rino1.png"))));
        textureRegionArrayRinoIzquierda.add(new Sprite(new Texture(Gdx.files.internal("Animaciones/Enemigo/Rino/Rino2.png"))));

        textureArrayPowerUp.add(new Texture(Gdx.files.internal("Posiones/Corazon.png")));
        textureArrayPowerUp.add(new Texture(Gdx.files.internal("Posiones/posionAmarilla.png")));
        textureArrayPowerUp.add(new Texture(Gdx.files.internal("Posiones/posion.png")));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        actualizar(delta);
        pintar();
    }


    private void actualizar(float dt) {

        prota.actualizar(dt);

        generarEnemigos();
        accionesTeclado();
        comprobarLimites();
        moverNPCs(dt);
        aparecenPociones();
        comprobarColisiones();
    }

    private void aparecenPociones() {

        if (TimeUtils.millis() - ultimaPocion > siguientePocion) {
            int pos = rand.nextInt(textureArrayPowerUp.size);
            Texture aspecto = textureArrayPowerUp.get(pos);
            Personaje.Estados direccion;
            int posX, posY;
            int movimiento = rand.nextInt(4);
            if (movimiento == 0) {
                posX = rand.nextInt(Constantes.ANCHO_PANTALLA - aspecto.getWidth());
                posY = Constantes.ALTO_PANTALLA;
                direccion = Personaje.Estados.ABAJO;
            } else if(movimiento == 1){
                posX = -aspecto.getWidth();
                posY = rand.nextInt(Constantes.ALTO_PANTALLA - aspecto.getHeight());
                direccion = Personaje.Estados.DERECHA;
            }else if (movimiento == 2) {
                posX = rand.nextInt(Constantes.ANCHO_PANTALLA - aspecto.getWidth());
                posY = -aspecto.getHeight();
                direccion = Personaje.Estados.ARRIBA;
            } else {
                posX = Constantes.ANCHO_PANTALLA;
                posY = rand.nextInt(Constantes.ALTO_PANTALLA - aspecto.getHeight());
                direccion = Personaje.Estados.IZQUIERDA;
            }

            Posion posion = new Posion(2, new Vector2(posX, posY), aspecto, pos, direccion);

            powerUpArray.add(posion);
            ultimaPocion = TimeUtils.millis();
            siguientePocion = rand.nextInt(Prota.TIEMPO_MAX_POCIONES) + Prota.TIEMPO_MIN_POCIONES;

        }
    }

    private void comprobarColisiones() {

        for (Enemigo enemigo : enemigoArray) {
            for (Bala bala : balaArray) {
                if (bala.rectangulo.overlaps(enemigo.rectangulo)) {
                    balaArray.removeValue(bala, true);
                    enemigo.setVida(enemigo.getVida() - 1);
                }
            }

            if(enemigo.rectangulo.overlaps(prota.rectangulo)){
                enemigoArray.removeValue(enemigo, true);
                prota.setVida(prota.getVida() - 1);
                if(prota.getVida() == 0){
                    juego.setScreen(new MenuPrincipal(juego));
                }
            }

            if (enemigo.getVida() <= 0) {
                enemigoArray.removeValue(enemigo, true);
                prota.setPuntos(prota.getPuntos() + 1);
                if (prota.getPuntos() == 5) {
                    if (musica.isPlaying())
                        musica.stop();
                    juego.setScreen(new SegundoNivel(juego, prota));
                }
            }
        }

		for (Posion posion : powerUpArray) {
		    if(posion.rectangulo.overlaps(prota.rectangulo)){
                switch (posion.tipo) {
                    case 0:
                        prota.setVida(prota.getVida() + 1);
                        break;
                    case 1:
                        Prota.BALAS_CARGADOR += 10;
                        prota.setCargador(prota.getCargador() + 10);
                        break;
                    case 2:
                        enemigoArray.clear();
                        break;
                }
                powerUpArray.removeValue(posion, true);
            }
		}
    }

    private void generarEnemigos() {

        if (TimeUtils.millis() - ultimoEnemigo > siguienteEnemigo) {
            int res = rand.nextInt(2);
            Enemigo enemigo = null;
            if (res == 0) {
                int posX = rand.nextInt(Constantes.ANCHO_PANTALLA - textureRegionArrayFatBird.get(0).getRegionWidth());
                int posY = Constantes.ALTO_PANTALLA;

                enemigo = new Enemigo(2, 1, new Vector2(posX, posY), textureRegionArrayFatBird);
                enemigo.estado = Personaje.Estados.ABAJO;
            } else if (res == 1) {
                if (rand.nextBoolean()) {
                    int posX = -textureRegionArrayFatBird.get(0).getRegionWidth();
                    int posY = rand.nextInt(Constantes.ALTO_PANTALLA - textureRegionArrayFatBird.get(0).getRegionHeight());

                    enemigo = new Enemigo(4, 1, new Vector2(posX, posY), textureRegionArrayRinoDerecha);
                    enemigo.estado = Personaje.Estados.DERECHA;
                } else {
                    int posX = Constantes.ANCHO_PANTALLA;
                    int posY = rand.nextInt(Constantes.ALTO_PANTALLA - textureRegionArrayFatBird.get(0).getRegionHeight());

                    enemigo = new Enemigo(4, 1, new Vector2(posX, posY), textureRegionArrayRinoIzquierda);
                    enemigo.estado = Personaje.Estados.IZQUIERDA;
                }
            }

            enemigoArray.add(enemigo);
            ultimoEnemigo = TimeUtils.millis();
        }

    }

    private void moverNPCs(float dt) {

        for (Bala bala : balaArray) {

            if (bala.estado == Personaje.Estados.ARRIBA) {
                bala.moverArriba();
            } else if (bala.estado == Personaje.Estados.ABAJO) {
                bala.moverAbajo();
            } else if (bala.estado == Personaje.Estados.IZQUIERDA) {
                bala.moverIzquierda();
            } else {
                bala.moverDerecha();
            }
            bala.actualizar(dt);
        }

        for (Enemigo enemigo : enemigoArray) {

            if (enemigo.estado == Personaje.Estados.ARRIBA) {
                enemigo.moverArriba();
            } else if (enemigo.estado == Personaje.Estados.ABAJO) {
                enemigo.moverAbajo();
            } else if (enemigo.estado == Personaje.Estados.IZQUIERDA) {
                enemigo.moverIzquierda();
            } else {
                enemigo.moverDerecha();
            }
            enemigo.actualizar(dt);
        }

		for (Posion posion : powerUpArray) {
			switch (posion.direccion) {
				case ARRIBA:
					posion.moverArriba();
					break;
				case ABAJO:
					posion.moverAbajo();
					break;
				case DERECHA:
					posion.moverDerecha();
					break;
				case IZQUIERDA:
					posion.moverIzquierda();
					break;
			}
		}
    }

    private void accionesTeclado() {

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                juego.setScreen(new MenuPausa(juego, this));
            }
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
        float posX, posY;
        if (prota.estado == Personaje.Estados.ARRIBA) {
            posX = prota.getPosicion().x + prota.getTexturaActual().getRegionWidth() / 2f - textureRegionArrayBala.get(0).getRegionWidth() / 2f;
            posY = prota.getPosicion().y + prota.getTexturaActual().getRegionHeight();
        } else if (prota.estado == Personaje.Estados.ABAJO) {
            posX = prota.getPosicion().x + prota.getTexturaActual().getRegionWidth() / 2f - textureRegionArrayBala.get(0).getRegionWidth() / 2f;
            posY = prota.getPosicion().y;
        } else if (prota.estado == Personaje.Estados.IZQUIERDA) {
            posX = prota.getPosicion().x;
            posY = prota.getPosicion().y + prota.getTexturaActual().getRegionHeight() / 2f - textureRegionArrayBala.get(0).getRegionHeight() / 2f;
        } else {
            posX = prota.getPosicion().x + prota.getTexturaActual().getRegionWidth();
            posY = prota.getPosicion().y + prota.getTexturaActual().getRegionHeight() / 2f - textureRegionArrayBala.get(0).getRegionHeight() / 2f;
        }

        Bala bala = new Bala(8, new Vector2(posX, posY), textureRegionArrayBala);
        bala.estado = prota.estado;

        if (ConfigurationManager.isSoundEnabled())
            sonidoDisparo.play();

        balaArray.add(bala);
        prota.setCargador(prota.getCargador() - 1);
        prota.setMomentoUltimoDisparo(TimeUtils.millis());
        if (prota.getCargador() <= 0) {
            prota.setPuedeDisparar(false);
            prota.setMomentoUltimaRecarga(TimeUtils.millis());
        }

    }

    private void comprobarLimites() {
        if (prota.getPosicion().x < 0) {
            prota.getPosicion().x = 0;
        }
        if (prota.getPosicion().x > Constantes.ANCHO_PANTALLA - prota.getTexturaActual().getRegionWidth()) {
            prota.getPosicion().x = Constantes.ANCHO_PANTALLA - prota.getTexturaActual().getRegionWidth();
        }
        if (prota.getPosicion().y < 0) {
            prota.getPosicion().y = 0;
        }
        if (prota.getPosicion().y > Constantes.ALTO_PANTALLA - prota.getTexturaActual().getRegionHeight()) {
            prota.getPosicion().y = Constantes.ALTO_PANTALLA - prota.getTexturaActual().getRegionHeight();
        }

        for(Enemigo enemigo : enemigoArray){
            if(enemigo.getTexturaActual() != null) {
                if (enemigo.estado == Personaje.Estados.ABAJO && enemigo.getPosicion().y < -enemigo.getTexturaActual().getRegionHeight()) {
                    enemigoArray.removeValue(enemigo, true);
                    prota.setPuntos(prota.getPuntos() - 1);
                }

                if (enemigo.estado == Personaje.Estados.DERECHA && enemigo.getPosicion().x > Constantes.ANCHO_PANTALLA) {
                    enemigoArray.removeValue(enemigo, true);
                    prota.setPuntos(prota.getPuntos() - 1);
                }

                if (enemigo.estado == Personaje.Estados.IZQUIERDA && enemigo.getPosicion().x < -enemigo.getTexturaActual().getRegionWidth()) {
                    enemigoArray.removeValue(enemigo, true);
                    prota.setPuntos(prota.getPuntos() - 1);
                }
            }
        }

    }

    private void pintar() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        juego.batch.begin();

        if (prota.getVida() <= 0) {
            juego.font.draw(juego.batch, "GAME OVER", Constantes.ANCHO_PANTALLA / 2f - 135, Constantes.ALTO_PANTALLA / 2f);
        } else {

            juego.font.draw(juego.batch, "Puntos: " + prota.getPuntos(), 0, Constantes.ALTO_PANTALLA);
            juego.font.draw(juego.batch, "Vida: " + prota.getVida(), 225, Constantes.ALTO_PANTALLA);
            if (prota.isPuedeDisparar())
                juego.font.draw(juego.batch, "Cargador: " + prota.getCargador(), 0, Constantes.ALTO_PANTALLA - juego.font.getXHeight() * 9);
            else
                juego.font.draw(juego.batch, "Recargando...", 0, Constantes.ALTO_PANTALLA - juego.font.getXHeight() * 9);

            prota.dibujar(juego.batch);
            for (Bala bala : balaArray)
                bala.dibujar(juego.batch);
            for (Enemigo enemigo : enemigoArray)
                enemigo.dibujar(juego.batch);
            for (Posion posion : powerUpArray)
                posion.dibujar(juego.batch);
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
