package com.sorrer.utils.entity;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sorrer.utils.CamUtils;

public class EntityDrawer {
	private static LinkedList<Entity> temp = new LinkedList<Entity>();
	private static LinkedList<Entity> temp2 = new LinkedList<Entity>();
	public static void draw(LinkedList<EntityManager> em, SpriteBatch b, ShapeRenderer r){
		LinkedList<Entity> draw = getArranged(em);
		for(int i = 0; i < draw.size(); i++){
			draw.get(i).draw(b, r);
		}
		
		temp.clear();
		temp2.clear();
	}
	
	public static LinkedList<Entity> getArranged(LinkedList<EntityManager> em){
		for(EntityManager en : em){
			temp.addAll(en.getEntities());
//			temp2.addAll(en.getEntitiesRenderable());
		}
		
		if(temp.isEmpty()){
			return temp;
		}
		
		Entity greatest = temp.getFirst();
		
		while(!temp.isEmpty()){
			for(Entity e : temp){
//			LinkedList<Entity> moveInfront = new LinkedList<Entity>();
//			float closestY = 0;
//			for(Entity t : temp){
//				if(e == t){
//					continue;
//				}
//				if(e.getPos().y < t.getPos().y & e.getRectangle().overlaps(t.getRectangle())){
//					moveInfront.add(t);
//					closestY = t.getPos().y;
//				}
//			}
//			
//			int index = temp2.indexOf(e);
//			for(Entity s : moveInfront){
//				if(s.getPos().y < closestY){
//					closestY = s.getPos().y;
//					index = temp2.indexOf(s);
//				}
//			}
//			System.out.println(closestY + " " + e.getPos().y);
//			
//			if(temp2.indexOf(e) <= temp2.indexOf(moveInfront)){
//				index = (index - 1 <= -1 ? 0 : index - 1);
//				temp2.remove(e);
//				temp2.add(index, e);
//			}else{
//				temp2.remove(e);
//				temp2.add(index, e);
//			}
			
				if(greatest.getPos().y < e.getPos().y){
					greatest = e;
				}
			}
			
			temp.remove(greatest);
			temp2.add(greatest);
			if(!temp.isEmpty()){
				greatest = temp.getFirst();
			}
		}
		return temp2;
	}
	
	public static LinkedList<Entity> getArrangedRenderable(LinkedList<EntityManager> em){
		for(EntityManager en : em){
			temp.addAll(en.getEntitiesRenderable());
//			temp2.addAll(en.getEntitiesRenderable());
		}
		
		if(temp.isEmpty()){
			return temp;
		}
		
		Entity greatest = temp.getFirst();
		
		while(!temp.isEmpty()){
			for(Entity e : temp){
//			LinkedList<Entity> moveInfront = new LinkedList<Entity>();
//			float closestY = 0;
//			for(Entity t : temp){
//				if(e == t){
//					continue;
//				}
//				if(e.getPos().y < t.getPos().y & e.getRectangle().overlaps(t.getRectangle())){
//					moveInfront.add(t);
//					closestY = t.getPos().y;
//				}
//			}
//			
//			int index = temp2.indexOf(e);
//			for(Entity s : moveInfront){
//				if(s.getPos().y < closestY){
//					closestY = s.getPos().y;
//					index = temp2.indexOf(s);
//				}
//			}
//			System.out.println(closestY + " " + e.getPos().y);
//			
//			if(temp2.indexOf(e) <= temp2.indexOf(moveInfront)){
//				index = (index - 1 <= -1 ? 0 : index - 1);
//				temp2.remove(e);
//				temp2.add(index, e);
//			}else{
//				temp2.remove(e);
//				temp2.add(index, e);
//			}
			
				if(greatest.getPos().y < e.getPos().y){
					greatest = e;
				}
			}
			
			temp.remove(greatest);
			temp2.add(greatest);
			if(!temp.isEmpty()){
				greatest = temp.getFirst();
			}
		}
		return temp2;
	}
	public static Entity getSelectedEntity(LinkedList<EntityManager> em, OrthographicCamera cam){
		LinkedList<Entity> es = getArranged(em);
		
		Entity selected = new PlaceholderEntity();
		Vector3 pos = CamUtils.mouseWorldCoords(cam);
		for(Entity e: es){
			if(e.getRectangle().contains(pos.x, pos.y)){
				selected = e;
			}
		}
		return selected;
		
	}

	public static void draw(EntityManager enemies, SpriteBatch b, ShapeRenderer sr) {
		LinkedList<EntityManager> em = new LinkedList<EntityManager>();
		em.add(enemies);
		EntityDrawer.draw(em, b, sr);
	}
}
