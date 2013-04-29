package com.se1by.ETM.Levels;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.se1by.ETM.Entities.Player;

public interface BaseLevel {
	
	public Player getPlayer();
	public void setPlayer(Player player);
	public void setMap(TiledMap map);
	public TiledMap getMap();
	public void init(GameContainer con) throws SlickException;
	public void render(GameContainer con, Graphics g) throws SlickException;
	public void update(GameContainer con, int delta) throws SlickException;
	public boolean isFinished();
	public void setFinished(boolean finished);
	public int getLayers();
	public ArrayList<Integer> getBlockedID();
	public int getFinishID();
}
