package com.se1by.ETM.GameStates;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.ETM;

public class Credits implements BaseState {
	
	Font font;
	Font titleFont;


	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		titleFont.drawString(240, 50, "Everything", Color.black);
		titleFont.drawString(380, 110, "-", Color.black);
		titleFont.drawString(310, 160, "se1by", Color.black);
		font.drawString(350, 300, "Back");
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		font = new AngelCodeFont("res/font/font.fnt", "res/font/font_0.tga");
		titleFont = new AngelCodeFont("res/font/font1.fnt", "res/font/font1_0.tga");
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		if(con.getInput().isKeyPressed(Input.KEY_ENTER)){
			ETM.state = GameState.MENU;
		}
	}

}
