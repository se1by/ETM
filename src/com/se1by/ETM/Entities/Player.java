package com.se1by.ETM.Entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.tiled.TiledMap;

import com.se1by.ETM.ETM;
import com.se1by.ETM.Levels.BaseLevel;
import com.se1by.ETM.Levels.Level1;
import com.se1by.ETM.util.Vector2i;


public class Player implements LivingEntity {
	
	Vector2i pos;
	Image image;
	TiledMap map; //FUCKING DAMN UGLY WAY BUT SHOULD DO THE TRICK
	BaseLevel level;
	int speed;
	public ArrayList<Integer> toRender;
	public boolean hit;
	int hitCD;
	float health;
	Sound hitSound;
	private boolean wasBlocked;
	int hitWall;
	
	public Player(BaseLevel level, Vector2i pos){
		this.map = level.getMap();
		this.level = level;
		this.pos = pos;
	}

	@Override
	public void setPosition(Vector2i pos) {
		if(!outOfScreen(pos) && !inBlocked(pos)){			
			this.pos = pos;
		}
	}

	@Override
	public Vector2i getPosition() {
		return pos;
	}
	
	@Override
	public void addPosition(Vector2i vec) {
		if(!outOfScreen(pos.copy().add(vec)) && !inBlocked(pos.copy().add(vec))){
			pos.add(vec);
		}
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
		if(hit){
			g.drawImage(getImage(), getPosition().getX(), getPosition().getY(), Color.red);
		}
		else{
			g.drawImage(image, pos.getX(), pos.getY());
		}
		
		//HEALTH DRAWING, SOMEHOW NOT WORKING
		/*
		int i = 0;
		float hc = health;
		Image full = new Image("res/live.png");
		while(hc > 1){
			g.drawImage(full, 20 + i*full.getWidth(), 780, Color.red);
			hc--;
			i++;
		}
		if(hc > 0){
			g.drawImage(new Image("res/live2.png"), 20 + i*full.getWidth(), 780);
		}
		*/
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		Vector2i oldTilePos = getCurrentTilePos();
		
		//INPUT
		Input input = con.getInput();
		
		if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)){
			up();
		}
		if(input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN)){
			down();
		}
		if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
			left();
		}
		if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)){
			right();
		}
		
		//SCREEN COLLISION
		if(pos.getX() + image.getWidth() > con.getWidth()){
			setPosition(new Vector2i(con.getWidth() - image.getWidth() - 1, pos.getY()));
		}
		else if(pos.getX() < 0){
			setPosition(new Vector2i(0, pos.getY()));
		}
		if(pos.getY() + image.getHeight() > con.getHeight()){
			setPosition(new Vector2i(pos.getX(), con.getHeight() - image.getHeight() - 1));
		}
		else if(pos.getY() < 0){
			setPosition(new Vector2i(pos.getX(), 0));
		}
		
		//WALL/TERRAIN COLLISION is now done in the "addPosition" and "setPosition" method
		//THIS IS JUST TO LIGHT UP THE WALLS, I KNOW IT'S NOT THE PERFECT SOLUTION
		if(level instanceof Level1){
			for (int i = 0; i < level.getLayers(); i++) {
				if (inBlocked(i, getPosition())) {
					if (!wasBlocked) {
						wasBlocked = true;
						toRender.add(i);
						hitSound.play();
					}
					setPosition(oldTilePos.multiply(map.getTileWidth(),
							map.getTileHeight()));
				}
			}
		}
		
		if(hitWall != -1 && !toRender.contains(hitWall) && level instanceof Level1){
			System.out.println("ADD");
			toRender.add(hitWall);
		}
		
		//FINISHED?
		for(int i = 0; i < level.getLayers(); i++){
			if(inID(i, level.getFinishID(), getPosition())){
				level.setFinished(true);
			}
		}
		
		//HIT COOLDOWN
		if(hit){
			hitCD += delta;
			if(hitCD > 100){
				hit = false;
			}
		}
		
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		setImage(new Image("res/player.png"));
		toRender = new ArrayList<Integer>();
		toRender.add(level.getLayers() - 1);
		speed = 8;
		hit = false;
		health = 3;
		hitSound = new Sound("res/hit.ogg");
		hitWall = -1;
	}
	
	private void right() {
		addPosition(new Vector2i(speed,0));
	}

	private void left() {
		addPosition(new Vector2i(-speed,0));
	}

	private void down() {
		addPosition(new Vector2i(0,speed));
	}

	private void up() {
		addPosition(new Vector2i(0,-speed));
	}
	
	@SuppressWarnings("unused")
	private int getTileIDAtPlayerPos(){
		return map.getTileId(getPosition().getX()/map.getTileWidth(), getPosition().getY()/map.getTileHeight(), 0);
	}
	
	private Vector2i getCurrentTilePos(){
		int x = getPosition().getX()/map.getTileWidth();
		int y = getPosition().getY()/map.getTileHeight();
		
		return new Vector2i(x, y);
	}
	
	private boolean inBlocked(int layer, Vector2i position){
		//upper left
		int topLeftID = map.getTileId(position.getX()/map.getTileWidth(), position.getY()/map.getTileHeight(), layer);
		int topRightID = map.getTileId((position.getX() + getImage().getWidth() - 1)/map.getTileWidth(), position.getY()/map.getTileHeight() , layer);
		int downLeftID = map.getTileId(position.getX()/map.getTileWidth(), (position.getY() + image.getHeight() -1)/map.getTileHeight(), layer);
		int downRightID = map.getTileId((position.getX() + getImage().getWidth() -1)/map.getTileWidth(), (position.getY() + getImage().getHeight() - 1)/map.getTileHeight(), layer);
		
		if(level.getBlockedID().contains(topLeftID)){
			return true;
		}
		else if(level.getBlockedID().contains(topRightID)){
			return true;
		}
		else if(level.getBlockedID().contains(downLeftID)){
			return true;
		}
		else if(level.getBlockedID().contains(downRightID)){
			return true;
		}
		
		return false;
	}
	
	private boolean inID(int layer, int ID, Vector2i position){
		int topLeftID = map.getTileId(position.getX()/map.getTileWidth(), position.getY()/map.getTileHeight(), layer);
		int topRightID = map.getTileId((position.getX() + getImage().getWidth() - 1)/map.getTileWidth(), position.getY()/map.getTileHeight() , layer);
		int downLeftID = map.getTileId(position.getX()/map.getTileWidth(), (position.getY() + image.getHeight() -1)/map.getTileHeight(), layer);
		int downRightID = map.getTileId((position.getX() + getImage().getWidth() -1)/map.getTileWidth(), (position.getY() + getImage().getHeight() - 1)/map.getTileHeight(), layer);
		
		if(topLeftID == ID){
			return true;
		}
		else if(topRightID == ID){
			return true;
		}
		else if(downLeftID == ID){
			return true;
		}
		else if(downRightID == ID){
			return true;
		}
		return false;
	}
	
	private boolean inBlocked(Vector2i position){
		for(int i = 0; i < level.getLayers(); i++){
			if(inBlocked(i, position)){
				hitWall = i;
				if(!wasBlocked){
					level.setScore(level.getScore() - level.getPunishment());
					hitSound.play();
					System.out.println("score-=" + level.getPunishment());
				}
				wasBlocked = true;
				return true;
			}
		}
		wasBlocked = false;
		hitWall = -1;
		return false;
	}
	
	private boolean outOfScreen(Vector2i position){
		if(position.getX() + image.getWidth() > ETM.width){
			return true;
		}
		else if(position.getX() < 0){
			return true;
		}
		if(position.getY() + image.getHeight() > ETM.height){
			return true;
		}
		else if(position.getY() < 0){
			return true;
		}
		return false;
	}

}
