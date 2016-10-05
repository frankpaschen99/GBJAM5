package com.sorrer.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sorrer.utils.PrintLog;
import com.sorrer.utils.entity.Entity;

import box2dLight.RayHandler;

public class Player extends Entity {
	
	public int health = 10;
	private float x = 0,y = 0;
	private float width = 12,height = 24;
	private Sprite player;
	public Player(float x, float y){
		this.x = x;
		this.y = y;
		
		player = new Sprite(new Texture(Gdx.files.internal("strong.png")));
		player.setPosition(x, y);
	}
	
	@Override
	public void update() {
		if(this.health <= 0){
			this.dispose();
		}
		player.setPosition(x, y);

		if(this.y > 144 - 100){
			this.y = 144 - 100;
		}
		

		if(this.y < 0){
			this.y = 0;
		}
	}

	@Override
	public void draw(SpriteBatch b, ShapeRenderer sr) {

		player.setPosition(x, y);
		player.setFlip(flipped, false);
		player.draw(b);

		if(sr.isDrawing()) sr.end();
		b.end();
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.GREEN);
		if(flipped){
			sr.setColor(Color.ORANGE);
			sr.rect(x - 10, y + height/2, 15, 5);
		}else{
			sr.setColor(Color.ORANGE);
			sr.rect(x + width - 5, y + height/2, 15, 5);
		}
		sr.end();
		b.begin();
	}

	@Override
	public void dispose() {
		this.trash = true;
	}

	@Override
	public void addLights(RayHandler rayH) {
		
	}

	@Override
	public Vector2 getPos() {
		return new Vector2(x,y);
	}

	@Override
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public void moveX(float x){
		this.x += x;
	}
	
	public void moveY(float y){
		this.y += y;
	}
	
	public Rectangle getAttackBox(){
		return flipped ? new Rectangle(x - 10, y + height/2, 15, 5) : new Rectangle(x + width - 5, y + height/2, 15, 5) ;
	}
	
	private boolean flipped = false;
	public void flip(boolean flipped){
		this.flipped = flipped;
	}

	@Override
	public Vector2 getSize() {
		return new Vector2(width,height);
	}

	public boolean isFlipped() {
		return flipped;
	}
}
