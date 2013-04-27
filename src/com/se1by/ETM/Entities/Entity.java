package com.se1by.ETM.Entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.util.Vector2i;

public interface Entity {
	
	public void init(GameContainer con) throws SlickException;
	public void setPosition(Vector2i pos);
	public Vector2i getPosition();
	public void addPosition(Vector2i vec);
	public void setImage(Image image);
	public Image getImage();

}
