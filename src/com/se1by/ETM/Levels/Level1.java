package com.se1by.ETM.Levels;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.se1by.ETM.Entities.Player;
import com.se1by.ETM.GameStates.CutScene;
import com.se1by.ETM.GameStates.Ingame;
import com.se1by.ETM.util.Vector2i;

public class Level1 implements BaseLevel{
	
	Player player;
	TiledMap map;
	boolean finished;
	ArrayList<Integer> blockedID;
	private int score;
	private int punishment;
	private boolean nextLevel;
	private CutScene scene;
	private int counter;

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void setMap(TiledMap map) {
		this.map = map;
	}

	@Override
	public TiledMap getMap() {
		return map;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}
	
	@Override
	public void setFinished(boolean finished) {
		this.finished = finished;
		Ingame.score += score;
	}
	
	@Override
	public void init(GameContainer con) throws SlickException {
		map = new TiledMap("res/level1.tmx");
		player = new Player(this, new Vector2i(50,50));
		player.init(con);
		finished = false;
		blockedID = new ArrayList<Integer>();
		blockedID.add(1);
		score = 1000;
		punishment = 50;
		
		scene = new CutScene(this);
		scene.init(con);
	}

	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		if(getFinished()){
			scene.render(con, g);
		}else{
			for(int i : player.toRender){
				map.render(0, 0, i);
			}
			player.render(con, g);
		}
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		if(getFinished()){
			scene.score = score;
			scene.update(con, delta);
		}else{
			player.update(con, delta);
			counter += delta;
			if(counter > 1000){
				counter = 0;
				score -= 10;
			}
		}
	}

	@Override
	public int getLayers() {
		return map.getLayerCount();
	}

	@Override
	public ArrayList<Integer> getBlockedID() {
		return blockedID;
	}

	@Override
	public int getFinishID() {
		return 3;
	}
	
	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getPunishment() {
		return punishment;
	}

	@Override
	public void setScore(int i) {
		score = i;
	}

	@Override
	public void setNextLevel(boolean b) {
		nextLevel = b;
	}

	@Override
	public boolean getNextLevel() {
		return nextLevel;
	}
	

	@Override
	public boolean getFinished() {
		return finished;
	}

	@Override
	public void setCutScene(CutScene scene) {
		this.scene = scene;
	}

	@Override
	public CutScene getCutScene() {
		return scene;
	}
}
