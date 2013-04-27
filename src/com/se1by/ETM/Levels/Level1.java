package com.se1by.ETM.Levels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.se1by.ETM.Entities.Player;

public class Level1 implements BaseLevel{
	
	Player player;
	TiledMap map;
	boolean finished;

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
	public void init(GameContainer con) throws SlickException {
		map = new TiledMap("res/level1.tmx");
		player = new Player(map);
		player.init(con);
		finished = false;
	}

	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		map.render(0, 0);
		player.render(con, g);
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		player.update(con, delta);
	}

}
