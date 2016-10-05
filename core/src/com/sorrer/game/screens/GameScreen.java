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
import com.sorrer.utils.Assets;
import com.sorrer.utils.Calc;
import com.sorrer.utils.CamUtils;
import com.sorrer.utils.PrintLog;
import com.sorrer.utils.entity.Entity;
import com.sorrer.utils.entity.EntityDrawer;
import com.sorrer.utils.entity.EntityManager;

public class GameScreen implements Screen{
	// Reference to CoreGame
	private CoreGame game;
	// Player
	Player player = new Player(10, 10);
	// EntityManager for enemies and players
	EntityManager enemies, players;
	OrthographicCamera cam;
	// FitViewport to make sure aspect ratio is locked
	FitViewport fv;
	SpriteBatch b;
	ShapeRenderer sr;
	
	public GameScreen(CoreGame game){
		this.game = game;
		cam = new OrthographicCamera();
		b = new SpriteBatch();
		sr = new ShapeRenderer();
		
		// Camera stuff
		fv = new FitViewport(160, 144, cam);
		fv.setScreenSize(640, 576);
		fv.apply();
		cam.position.set(cam.viewportWidth/2, cam.viewportHeight/2, 0);

		enemies = new EntityManager(cam, null,b,sr,null);
		players = new EntityManager(cam, null,b,sr,null);

		// no idea
		oldCam = new Rectangle(cam.position.x - cam.viewportWidth/2, cam.position.y - cam.viewportHeight/2, cam.viewportWidth, cam.viewportHeight);
		
		players.add(player);
		players.update();
		Random random = new Random();
		for(int i = 0; i < 20; i++){
			// spawn 20 enemies at random x and y coordinates
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
		Gdx.gl20.glClearColor(10, 10, 10, 1);
		b.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		b.begin();
		b.draw(Assets.manager.get(Assets.background), cam.position.x - cam.viewportWidth/2, cam.position.y - cam.viewportHeight/2);
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
			// a pressed, move left and flip sprite
			player.moveX(-1.5f);
			player.flip(true);
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			// d pressed, move right and flip sprite
			player.moveX(1.5f);
			player.flip(false);
		}

		if(Gdx.input.isKeyPressed(Keys.W)){
			// w pressed, move up on the y axis (2.5d shit)
			player.moveY(.5f);
		}else if(Gdx.input.isKeyPressed(Keys.S)){
			// s pressed, move down on the y axis
			player.moveY(-.5f);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
			for(Entity e: enemies.getEntities()){
				// foreach enemy, if the player's attack box overlaps the enemy's bounding box and is within 5 pixels on the y axis of the enemy, damage the enemy.
				if(player.getAttackBox().overlaps(e.getRectangle()) && Calc.within(player.getPos().y, e.getPos().y, 5)){
					// cast entity to enemy, OK because enemy is subclass of entity
					Enemy en = (Enemy)e;
					en.damage(1, !player.isFlipped());
				}
			}
		}

		players.update();
		enemies.update();
		// Don't think im gonna try to understand this. Probably has to do with switching screens when player hits the border.
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
		}else if((!player.getRectangle().overlaps(oldCam)|| !player.getRectangle().overlaps(CamUtils.getRectangle(cam)) )&& tt1){
			tt1 = false;
		}
		
		cam.update();
	}

	@Override
	public void resize(int width, int height) {
		// fv.update placed here because it has to fix itself when the user adjusts the window
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
