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

import com.se1by.ETM.Levels.BaseLevel;
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
	private int wasBlocked;
	
	public Player(BaseLevel level, Vector2i pos){
		this.map = level.getMap();
		this.level = level;
		setPosition(pos);
	}

	@Override
	public void setPosition(Vector2i pos) {
		this.pos = pos;
	}

	@Override
	public Vector2i getPosition() {
		return pos;
	}
	
	@Override
	public void addPosition(Vector2i vec) {
		pos.add(vec);
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
		
		//WALL/TERRAIN COLLISION
		for(int i = 0; i < level.getLayers(); i++){
			for(int x : level.getBlockedID()){
				if(inBlocked(i, x)){
					setPosition(oldTilePos.multiply(map.getTileWidth(), map.getTileHeight()));
					toRender.add(i);
					hitSound.play();
				}
			}
		}
		
		//FINISHED?
		if(inBlocked(level.getLayers()-1, level.getFinishID())){
			level.setFinished(true);
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
	
	private boolean inBlocked(int layer, int blockID){
		//upper left
		if(map.getTileId(getPosition().getX()/map.getTileWidth(), getPosition().getY()/map.getTileHeight(), layer) == blockID){
			return true;
		}
		
		//upper right
		int x = (getPosition().getX() + getImage().getWidth() - 1)/map.getTileWidth();

		if(map.getTileId(x, getPosition().getY()/map.getTileHeight() , layer) == blockID){
			return true;
		}
		
		//lower left
		if(map.getTileId(getPosition().getX()/map.getTileWidth(), (getPosition().getY() + image.getHeight() -1)/map.getTileHeight(), layer) == blockID){
			return true;
		}
		
		//lower right
		if(map.getTileId((getPosition().getX() + getImage().getWidth() -1)/map.getTileWidth(), (getPosition().getY() + getImage().getHeight() - 1)/map.getTileHeight(), layer) == blockID){
			return true;
		}
		
		return false;
	}

}
