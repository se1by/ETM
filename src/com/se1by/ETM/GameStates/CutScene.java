package com.se1by.ETM.GameStates;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.Levels.BaseLevel;

public class CutScene implements BaseState {
	
	Font font;
	Font titleFont;
	public int score;
	BaseLevel level;
	
	public CutScene(BaseLevel level){
		this.level = level;
	}

	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		titleFont.drawString(230, 50, "Level done!", Color.black);
		font.drawString(300, 200, "You scored", Color.black);
		titleFont.drawString(350, 300, score + "", Color.red);
		font.drawString(350, 400, "Points", Color.black);
		font.drawString(320, 500, "Next level", Color.white);
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		font = new AngelCodeFont("res/font/font.fnt", "res/font/font_0.tga");
		titleFont = new AngelCodeFont("res/font/font1.fnt", "res/font/font1_0.tga");
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		if(con.getInput().isKeyPressed(Input.KEY_ENTER)){
			level.setNextLevel(true);
		}
	}

}
