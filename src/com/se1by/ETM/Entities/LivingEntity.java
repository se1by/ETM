package com.se1by.ETM.Entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public interface LivingEntity extends Entity{
	
	public void render(GameContainer con, Graphics g) throws SlickException;
	public void update(GameContainer con, int delta) throws SlickException;
}
