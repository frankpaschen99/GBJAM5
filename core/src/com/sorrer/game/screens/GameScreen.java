package com.sorrer.game.screens;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sorrer.game.CoreGame;
import com.sorrer.game.entities.Enemy;
import com.sorrer.game.entities.Player;
import com.sorrer.utils.Calc;
import com.sorrer.utils.PrintLog;
import com.sorrer.utils.entity.Entity;
import com.sorrer.utils.entity.EntityDrawer;
import com.sorrer.utils.entity.EntityManager;

public class GameScreen implements Screen{
	private CoreGame game;
	Player player = new Player(10, 10);
	EntityManager enemies, players;
	OrthographicCamera cam;
	FitViewport fv;
	SpriteBatch b;
	ShapeRenderer sr;
	
	public GameScreen(CoreGame game){
		this.game = game;

		cam = new OrthographicCamera();
		
		b = new SpriteBatch();
		sr = new ShapeRenderer();
		
		fv = new FitViewport(160, 144, cam);

		fv.setScreenSize(640, 576);
		fv.apply();
		
		cam.position.set(cam.viewportWidth/2, cam.viewportHeight/2, 0);

		enemies = new EntityManager(cam, null,b,sr,null);
		players = new EntityManager(cam, null,b,sr,null);

		oldCam = new Rectangle(cam.position.x - cam.viewportWidth/2, cam.position.y - cam.viewportHeight/2, cam.viewportWidth, cam.viewportHeight);
		
		players.add(player);
		players.update();
		Random random = new Random();
		for(int i = 0; i < 20; i++){
			enemies.add(new Enemy(random.nextInt(160), random.nextInt(90)));
		}
		enemies.update();
		
		PrintLog.printGame(enemies.getEntitiesRenderable().toString());
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		b.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		b.begin();
		LinkedList<EntityManager> em = new LinkedList<EntityManager>();
		em.add(enemies);
		em.add(players);
		EntityDrawer.draw(em, b, sr);
		b.end();
	}
	
	Rectangle oldCam;
	boolean tt1 = false;
	
	public void update(){
		if(Gdx.input.isKeyPressed(Keys.A)){
			player.moveX(-1.5f);
			player.flip(true);
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			player.moveX(1.5f);
			player.flip(false);
		}

		if(Gdx.input.isKeyPressed(Keys.W)){
			player.moveY(.5f);
		}else if(Gdx.input.isKeyPressed(Keys.S)){
			player.moveY(-.5f);
		}
		
		
		for(Entity e: enemies.getEntities()){
			if(player.getAttackBox().overlaps(e.getRectangle()) && Calc.within(player.getPos().y, e.getPos().y, 5)){
				Enemy en = (Enemy)e;
				if(en.getPos().x < player.getPos().x){
					en.damage(1, false);
				}else{
					en.damage(1, true);
				}
			}
		}

		players.update();
		enemies.update();
		if(!tt1){
			if(player.getPos().x < (cam.position.x - cam.viewportWidth/2)){
				oldCam = new Rectangle(cam.position.x - cam.viewportWidth/2, cam.position.y - cam.viewportHeight/2, cam.viewportWidth, cam.viewportHeight);
				tt1 = true;
				cam.position.x -= cam.viewportWidth;
			}else if(player.getPos().x + player.getSize().x > (cam.position.x - cam.viewportWidth/2) + cam.viewportWidth){
				oldCam = new Rectangle(cam.position.x - cam.viewportWidth/2, cam.position.y - cam.viewportHeight/2, cam.viewportWidth, cam.viewportHeight);
				tt1 = true;
				cam.position.x += cam.viewportWidth;
			}
		}else if(!player.getRectangle().overlaps(oldCam) && tt1){
			tt1 = false;
		}
		
		cam.update();
	}

	@Override
	public void resize(int width, int height) {
		fv.update(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

}
