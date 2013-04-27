package com.se1by.ETM.GameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.Entities.Player;
import com.se1by.ETM.Levels.BaseLevel;
import com.se1by.ETM.Levels.Level1;

public class Ingame implements BaseState {
	Player player;
	BaseLevel level;

	@Override
	public void render(GameContainer con, Graphics g)
			throws SlickException {
		level.render(con, g);
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		level = new Level1();
		level.init(con);
	}

	@Override
	public void update(GameContainer con, int delta)
			throws SlickException {
		if(level.isFinished()){
			//load new level
		}
		level.update(con, delta);
	}

}
