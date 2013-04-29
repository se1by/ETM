package com.se1by.ETM.Entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;


public interface LivingEntity extends Entity{
		public void update(GameContainer con, int delta) throws SlickException;
}
