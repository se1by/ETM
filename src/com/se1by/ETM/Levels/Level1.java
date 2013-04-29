package com.se1by.ETM.Levels;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.se1by.ETM.Entities.Player;
import com.se1by.ETM.util.Vector2i;

public class Level1 implements BaseLevel{
	
	Player player;
	TiledMap map;
	boolean finished;
	ArrayList<Integer> blockedID;

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
	}
	
	@Override
	public void init(GameContainer con) throws SlickException {
		map = new TiledMap("res/level1.tmx");
		player = new Player(this, new Vector2i(50,50));
		player.init(con);
		finished = false;
		blockedID = new ArrayList<Integer>();
		blockedID.add(1);
	}

	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		for(int i : player.toRender){
			map.render(0, 0, i);
		}
		player.render(con, g);
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		player.update(con, delta);
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
}
