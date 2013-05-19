package com.se1by.ETM.GameStates;

import java.util.ArrayList;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.ETM;
import com.se1by.ETM.util.Networker;

public class Submit implements BaseState {
	Font font;
	Font titleFont;
	ArrayList<Character> name;
	Networker net;

	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		g.setBackground(new org.newdawn.slick.Color(45, 61, 97));
		titleFont.drawString(50, 50, "Submit your score!", Color.black);
		font.drawString(50, 100, "You reached a total of "  + Ingame.score + " points!", Color.black);
		font.drawString(50, 200, "Enter your name:", Color.black);
		font.drawString(300, 400, name.toString().replace(",", "").replace("[", "").replace("]", "").replace(" ", ""));
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		font = new AngelCodeFont("res/font/font.fnt", "res/font/font_0.tga");
		titleFont = new AngelCodeFont("res/font/font1.fnt", "res/font/font1_0.tga");
		name = new ArrayList<Character>();
		net = new Networker();
		con.getInput().addKeyListener(new KeyListener() {
			
			boolean isPressed;
			
			@Override
			public void setInput(Input input) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isAcceptingInput() {
				if(ETM.state.equals(GameState.SUBMIT)){					
					return true;
				}
				return false;
			}
			
			@Override
			public void inputStarted() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void inputEnded() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(int key, char c) {
				isPressed = false;
				System.out.println("released");
			}
			
			@Override
			public void keyPressed(int key, char c) {
				if(isPressed){
					return;
				}
				System.out.println("char c = " + c);
				if(key == 14 && name.size() > 0){
					name.remove(name.size()-1);
				}
				else{
					
					name.add(c);
				}
				if(key != 42){
					isPressed = true;					
				}
			}
		});
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		if(con.getInput().isKeyPressed(Input.KEY_ENTER)){
			net.addScore(name.toString().replace(",", "").replace("[", "").replace("]", "").replace(" ", "").trim(), Ingame.score);
			net.end();
			ETM.state = GameState.END;
		}
	}

}
