package com.sorrer.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sorrer.utils.Assets;
import com.sorrer.utils.entity.Entity;

import box2dLight.RayHandler;

public class Enemy extends Entity{
	
	public int health = 2;
	private float x,y;
	private float acc = 0;
	private long lastDamage = 0;
	private Sprite Enemy;
	
	public Enemy(float x, float y){
		this.x = x;
		this.y = y;
		Enemy = new Sprite(Assets.manager.get(Assets.human) );
	}
	
	@Override
	public void update() {
		if(this.health <= 0){
			this.dispose();
		}
		if(acc < 0.01 && acc > -0.01){
			acc = 0;
		}else if(acc != 0){
			this.x += acc;
			acc /= 1.1;
		}
		Enemy.setPosition(x, y);
		
		if(this.y > 144 - 100){
			this.y = 144 - 100;
		}
		
		if(this.y < 0){
			this.y = 0;
		}
	}
	
	public void damage(int damage, boolean left){
		if(System.currentTimeMillis() + 1000 > lastDamage){
			lastDamage = System.currentTimeMillis();
			this.health -= damage;
			// System.out.println(health);
			if(left){
				this.acc = 4;
			}else{
				this.acc = -4;
			}
		}
	}

	@Override
	public void draw(SpriteBatch b, ShapeRenderer sr) {
		Enemy.draw(b);
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
		
	}

	@Override
	public Vector2 getSize() {
		return new Vector2(12, 24);
	}
}
