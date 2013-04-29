package com.se1by.ETM.GameStates;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.ETM;


public class Paused implements BaseState {

	Font font;
	Font titleFont;
	int selection;
	
	@Override
	public void render(GameContainer con, Graphics g)
			throws SlickException {
		titleFont.drawString(300, 50, "Paused", Color.black);
		if(selection == 0){
			font.drawString(330, 200, "Continue");
			font.drawString(350, 250, "Exit", Color.black);
		}
		else if(selection == 1){
			font.drawString(330, 200, "Continue", Color.black);
			font.drawString(350, 250, "Exit");
		}
		
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		font = new AngelCodeFont("res/font/font.fnt", "res/font/font_0.tga");
		titleFont = new AngelCodeFont("res/font/font1.fnt", "res/font/font1_0.tga");
		selection = 0;
	}

	@Override
	public void update(GameContainer con, int delta)
			throws SlickException {
		Input input = con.getInput();
		
		if(input.isKeyPressed(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)){
			if(selection > 0){
				selection--;
			}
		}
		if(input.isKeyPressed(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN)){
			if(selection < 1){
				selection++;
			}
		}
		if(input.isKeyDown(Input.KEY_ENTER)){
			if(selection == 0){
				ETM.state = GameState.PLAY;
			}
			else if(selection == 1){
				ETM.state = GameState.EXIT;
			}
		}
	}

}
