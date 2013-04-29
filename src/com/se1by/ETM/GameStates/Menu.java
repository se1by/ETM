package com.se1by.ETM.GameStates;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.ETM;

public class Menu implements BaseState {
	
	GameState[] states;
	int selected;
	Font font;
	Font titleFont;

	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		g.setBackground(new org.newdawn.slick.Color(45, 61, 97));
		titleFont.drawString(50, 50, "Escape the minimalism", Color.black);
		for(int i = 0; i < states.length; i++){
			if(i == selected){
				font.drawString(50, 300 + i * 40, states[i].toString());
			}
			else{
				font.drawString(50, 300 + i * 40, states[i].toString(), Color.black);
			}
		}
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		states = new GameState[4];
		states[0] = GameState.PLAY;
		states[1] = GameState.HIGHSCORE;
		states[2] = GameState.CREDITS;
		states[3] = GameState.EXIT;
		selected = 0;
		
		font = new AngelCodeFont("res/font/font.fnt", "res/font/font_0.tga");
		titleFont = new AngelCodeFont("res/font/font1.fnt", "res/font/font1_0.tga"); 
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		Input input = con.getInput();
		
		if(input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)){
			if(selected > 0){
				selected--;
			}
		}
		if(input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)){
			if(selected < states.length -1){
				selected++;
			}
		}
		if(input.isKeyPressed(Input.KEY_ENTER)){
			ETM.state = states[selected];
		}
	}

}
