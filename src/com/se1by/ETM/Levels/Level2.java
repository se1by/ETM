package com.se1by.ETM.Levels;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.tiled.TiledMapPlus;

import com.se1by.ETM.Entities.Player;
import com.se1by.ETM.Entities.Turret;
import com.se1by.ETM.util.Vector2i;

public class Level2 implements BaseLevel {
	Player player;
	TiledMap map;
	boolean finished;
	ArrayList<Integer> blockedID;
	ArrayList<Turret> turrets;
	int finishID;

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
	public void init(GameContainer con) throws SlickException {
		map = new TiledMapPlus("res/level2.tmx");
		player = new Player(this, new Vector2i(0,526));
		player.init(con);
		finished = false;
		blockedID = new ArrayList<Integer>();
		blockedID.add(5);
		finishID = 7;
		
		//SETUP TURRETS
		turrets = new ArrayList<Turret>();
		TiledMapPlus tmp = (TiledMapPlus)map;
		Layer layer = tmp.getLayer("Kachelebene 2");
		for(int x = 0; x < layer.width; x++){
			for(int y = 0; y < layer.height; y++){
				int id = layer.data[x][y][2];
				if(id < 5 && id != 0){
					Turret t = new Turret(id, new Vector2i(x*tmp.getTileWidth(), y*tmp.getTileHeight()), this);
					t.init(con);
					turrets.add(t);
				}
			}
		}
	}

	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		for(int i : player.toRender){
			map.render(0, 0, i);
		}
		for(Turret t : turrets){
			t.render(con, g);
		}
		player.render(con, g);
		
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		player.update(con, delta);
		for(Turret t : turrets){
			t.update(con, delta);
		}
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
	public int getLayers() {
		return map.getLayerCount();
	}

	@Override
	public ArrayList<Integer> getBlockedID() {
		return blockedID;
	}

	@Override
	public int getFinishID() {
		return finishID;
	}

}
