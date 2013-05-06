package com.se1by.ETM.GameStates;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.se1by.ETM.ETM;
import com.se1by.ETM.Entities.Player;
import com.se1by.ETM.Levels.BaseLevel;
import com.se1by.ETM.Levels.Level1;
import com.se1by.ETM.Levels.Level2;
import com.se1by.ETM.util.Vector2i;

public class Ingame implements BaseState {
	Player player;
	ArrayList<BaseLevel> levels;
	int counter;

	@Override
	public void render(GameContainer con, Graphics g)
			throws SlickException {
		levels.get(counter).render(con, g);
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		levels = new ArrayList<BaseLevel>();
		levels.add(new Level1());
		levels.add(new Level2());
		counter = 0;
		levels.get(counter).init(con);
	}

	@Override
	public void update(GameContainer con, int delta)
			throws SlickException {
		//DEBUG
		if(con.getInput().isKeyDown(Input.KEY_P)){
			System.out.println(levels.get(counter).getPlayer().getPosition().toString());
		}
		else if(con.getInput().isKeyDown(Input.KEY_I)){
			Vector2i p = levels.get(counter).getPlayer().getPosition();
			TiledMap map = levels.get(counter).getMap();
			for(int i = 0; i < levels.get(counter).getLayers(); i++)
				System.out.println(map.getTileId(p.getX()/map.getTileWidth(), p.getY()/map.getTileHeight(), i));
		}
		
		if(con.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			ETM.state = GameState.PAUSED;
		}
		
		if(levels.get(counter).isFinished()){
			//System.out.println("Counter:" + counter + "\nlevels.size:" + levels.size() + "\ncurrent level: level" + (counter+1));
			counter++;
			if(counter == levels.size()){
				ETM.state = GameState.END;
				return;
			}
			levels.get(counter).init(con);
		}
		levels.get(counter).update(con, delta);
	}
}
