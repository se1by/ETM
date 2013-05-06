package com.se1by.ETM.Entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.Levels.BaseLevel;
import com.se1by.ETM.util.Vector2i;

public class Turret implements LivingEntity {
	Vector2i position;
	Image image;
	int counter;
	int direction;
	BaseLevel level;
	ArrayList<Shot> shots;
	ArrayList<Shot> toRemove;
	boolean visible;

	public Turret(int direction, Vector2i pos, BaseLevel level){
		setPosition(pos);
		this.direction = direction;
		this.level = level;
	}
	@Override
	public void init(GameContainer con) throws SlickException {
		image = new Image("res/turret" + direction + ".png");
		shots = new ArrayList<Shot>();
		toRemove = new ArrayList<Shot>();
		visible = false;
	}

	@Override
	public void setPosition(Vector2i pos) {
		this.position = pos;
	}

	@Override
	public Vector2i getPosition() {
		return position;
	}

	@Override
	public void addPosition(Vector2i vec) {
		position.add(vec);
	}

	@Override
	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		if(visible){
			g.drawImage(getImage(), getPosition().getX(), getPosition().getY());
			for(Shot s : shots){
				s.render(con, g);
			}
		}
	}
	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		counter += delta;
		if(counter > 1000){
			counter = 0;
			shoot(con);
		}
		for(Shot s : shots){
			s.update(con, delta);
			if(s.hit){
				if(s.playerHit){
					level.setScore(level.getScore() - level.getPunishment());
				}
				toRemove.add(s);
			}
		}
		for(Shot s : toRemove){
			shots.remove(s);
		}
		
	}
	private void shoot(GameContainer con) throws SlickException {
		Shot shot = new Shot(direction, position, level, this);
		shot.init(con);
		shots.add(shot);
	}

}
