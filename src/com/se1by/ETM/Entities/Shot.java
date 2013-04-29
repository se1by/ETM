package com.se1by.ETM.Entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import com.se1by.ETM.Levels.BaseLevel;
import com.se1by.ETM.util.Vector2i;

public class Shot implements LivingEntity {
	Vector2i position;
	Vector2i movingVec;
	Image image;
	boolean hit;
	boolean playerHit;
	boolean wallHit;
	int speed;
	BaseLevel level;
	Turret mom;

	public Shot(int direction, Vector2i pos, BaseLevel level, Turret mom){
		speed = 10;
		position = pos.copy();
		switch(direction){
		case 1:
			movingVec = new Vector2i(-speed, 0);
			position.addY(mom.getImage().getHeight()/2 -1);
			break;
		case 2:
			movingVec = new Vector2i(0, speed);
			position.addX(mom.getImage().getWidth()/2 -1);
			position.addY(mom.getImage().getHeight());
			break;
		case 3:
			movingVec = new Vector2i(speed, 0);
			position.addY(mom.getImage().getHeight()/2 -1);
			position.addX(mom.getImage().getWidth());
			break;
		case 4:
			movingVec = new Vector2i(0,-speed);
			position.addX(mom.getImage().getWidth()/2 -1);
			break;
		default:
			System.out.println("UNKNOWN DIRECTION!");
		}
		this.level = level;
		this.mom = mom;
	}
	
	@Override
	public void init(GameContainer con) throws SlickException {
		image = new Image("res/shot.png");
		hit = false;
		playerHit = false;
		wallHit = false;
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
		g.drawImage(getImage(), getPosition().getX(), getPosition().getY());
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		//MOVEMENT
		if(!hit){
			addPosition(movingVec);
		}
		
		//SCREN COLLISION
		if(getPosition().getX() + image.getWidth() > con.getWidth()){
			hit = true;
			wallHit = true;
			return;
		}
		else if(getPosition().getX() < 0){
			hit = true;
			wallHit = true;
			return;
		}
		if(getPosition().getY() + image.getHeight() > con.getHeight()){
			hit = true;
			wallHit = true;
			return;
		}
		else if(getPosition().getY() < 0){
			hit = true;
			wallHit = true;
			return;
		}
		
		//COLLISION WITH WALLS
		TiledMap map = mom.level.getMap();
		for(int i = 0; i < level.getLayers(); i++){
			int id = level.getMap().getTileId(getPosition().getX()/map.getTileWidth(), getPosition().getY()/map.getTileHeight(), i);
			if(level.getBlockedID().contains(id)){
				hit = true;
				wallHit = true;
			}
		}
		
		//COLLISION WITH PLAYER
		Player player = level.getPlayer();
		Rectangle playerRec = new Rectangle(player.getPosition().getX(), player.getPosition().getY(), player.getImage().getWidth() -1, player.getImage().getHeight() -1);
		Rectangle shotRec = new Rectangle(getPosition().getX(), getPosition().getY(), getImage().getWidth() -1, getImage().getHeight() -1);
		if(playerRec.intersects(shotRec)){
			hit = true;
			playerHit = true;
			mom.visible = true;
			mom.level.getPlayer().hit = true;
			mom.level.getPlayer().hitCD = 0;
		}
	}

}
