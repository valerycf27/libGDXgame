package com.valery.libgdxgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MyGame extends ApplicationAdapter {
	SpriteBatch batch;
	Prota prota;



	@Override
	public void create () {
		batch = new SpriteBatch();


		prota = new Prota(2, 5, new Vector2(Constantes.ANCHO_PANTALLA/2, 0));
	}

	@Override
	public void render () {
		actualizar();
		pintar();
	}

	@Override
	public void dispose () {
		batch.dispose();

	}

	private void actualizar() {

		float dt = Gdx.graphics.getDeltaTime();

		prota.actualizar(dt);

		//generarEnemigos();
		accionesTeclado();
		//comprobarLimites();
		/*moverNPCs();
		comprobarColisiones();*/
	}

	private void accionesTeclado() {
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			prota.estado = Personaje.Estados.DERECHA;
			prota.moverDerecha();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			prota.estado = Personaje.Estados.IZQUIERDA;
			prota.moverIzquierda();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			prota.moverArriba();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			prota.moverAbajo();
		}

	}
	/*private void comprobarLimites() {
		if(prota.posicion.x < 0){
			prota.posicion.x = 0;
		}
		if(prota.posicion.x > Constantes.ANCHO_PANTALLA - prota.getTextura().getWidth()){
			prota.posicion.x = Constantes.ANCHO_PANTALLA - prota.getTextura().getWidth();
		}
		if(prota.posicion.y < 0){
			prota.posicion.y = 0;
		}
		if(prota.posicion.y > Constantes.ALTO_PANTALL - prota.getTextura().getHeight()){
			prota.posicion.y = Constantes.ALTO_PANTALL - prota.getTextura().getHeight();
		}
	}

	 */

	private void pintar() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(prota.getTextura(), prota.posicion.x, prota.posicion.y);
		prota.dibujar(batch);

		batch.end();
	}
}
