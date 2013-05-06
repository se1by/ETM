package com.se1by.ETM.GameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface BaseState {
	
	public void render(GameContainer con, Graphics g)
			throws SlickException;

	public void init(GameContainer con) throws SlickException;

	public void update(GameContainer con, int delta)
			throws SlickException;

}
