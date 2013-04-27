package com.se1by.ETM;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.GameStates.GameState;
import com.se1by.ETM.GameStates.Ingame;
import com.se1by.ETM.GameStates.Paused;

public class ETM extends BasicGame {
	
	GameState state;
	Ingame ingame;
	Paused paused;

	public ETM() {
		super("Escape the minimalism");
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		if(state.equals(GameState.INGAME)){
			ingame.render(container, g);
		}
		else if(state.equals(GameState.PAUSED)){
			paused.render(container, g);
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		ingame = new Ingame();
		paused = new Paused();
		ingame.init(container);
		paused.init(container);
		state = GameState.INGAME;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if(state.equals(GameState.INGAME)){
			ingame.update(container, delta);
		}
		else if(state.equals(GameState.PAUSED)){
			paused.update(container, delta);
		}
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new ETM());

		app.setDisplayMode(800, 600, false);
		app.start();
	}

}
