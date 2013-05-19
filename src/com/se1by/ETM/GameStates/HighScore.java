package com.se1by.ETM.GameStates;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.ETM;
import com.se1by.ETM.util.Networker;

public class HighScore implements BaseState {

	Font font;
	Font titleFont;
	Networker net;
	LinkedHashMap<String, Integer> results;
	Iterator<Entry<String, Integer>> set;
	
	@Override
	public void render(GameContainer con, Graphics g) throws SlickException {
		titleFont.drawString(200, 50, "Top Scores:", Color.black);
		for(int i = 1; i < 12; i++){
			if(!set.hasNext()){
				set = results.entrySet().iterator();
				break;
			}
			Entry<String, Integer> entry = set.next();
			font.drawString(100, 100 + i * 25, i + ". " + entry.getKey(), Color.black);
			font.drawString(600, 100 + i*25, entry.getValue() + "", Color.black);
		}
		
		//titleFont.drawString(300, 50, "Ups!", Color.black);
		//titleFont.drawString(50, 100, "Not implemented yet :(", Color.black);
		font.drawString(300, 500, "Back");
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		font = new AngelCodeFont("res/font/font.fnt", "res/font/font_0.tga");
		titleFont = new AngelCodeFont("res/font/font1.fnt", "res/font/font1_0.tga");
		net = new Networker();
		results = net.getTopScore();
		net.end();
		set = results.entrySet().iterator();
	}

	@Override
	public void update(GameContainer con, int delta) throws SlickException {
		if(con.getInput().isKeyPressed(Input.KEY_ENTER)){
			ETM.state = GameState.MENU;
		}
	}

}
