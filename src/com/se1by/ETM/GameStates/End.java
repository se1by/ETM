package com.se1by.ETM.GameStates;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.ETM;

public class End implements BaseState {
	Font font;
	Font titleFont;

	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		titleFont.drawString(250, 50, "You did it!", Color.black);
		font.drawString(320, 100, "(For now!)", Color.black);
		font.drawString(220, 150, "This is v1.0 postcompo.", Color.black);
		font.drawString(200, 200, "More stuff coming soon!", Color.black);
		font.drawString(250, 250, "Just wait for it ;)", Color.black);
		font.drawString(50, 400, "Follow us on twitter to stay informed:", Color.black);
		font.drawString(250, 450, "@BitByterStudios");
		font.drawString(350, 550, "Back");
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
			ETM.reload(con);
		}
	}

}
