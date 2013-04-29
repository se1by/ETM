package com.se1by.ETM;

import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.se1by.ETM.GameStates.Credits;
import com.se1by.ETM.GameStates.End;
import com.se1by.ETM.GameStates.GameState;
import com.se1by.ETM.GameStates.HighScore;
import com.se1by.ETM.GameStates.Ingame;
import com.se1by.ETM.GameStates.Menu;
import com.se1by.ETM.GameStates.Paused;

public class ETM extends BasicGame {
	
	public static GameState state;
	Menu menu;
	Ingame ingame;
	Paused paused;
	HighScore hs;
	Credits credits;
	End end;

	public ETM() {
		super("Escape the minimalism");
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		if(state.equals(GameState.MENU)){
			menu.render(container, g);
		}
		else if(state.equals(GameState.PLAY)){
			ingame.render(container, g);
		}
		else if(state.equals(GameState.PAUSED)){
			paused.render(container, g);
		}
		else if(state.equals(GameState.HIGHSCORE)){
			hs.render(container, g);
		}
		else if(state.equals(GameState.CREDITS)){
			credits.render(container, g);
		}
		else if(state.equals(GameState.END)){
			end.render(container, g);
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		menu = new Menu();
		ingame = new Ingame();
		paused = new Paused();
		hs = new HighScore();
		credits = new Credits();
		end = new End();
		menu.init(container);
		ingame.init(container);
		paused.init(container);
		hs.init(container);
		credits.init(container);
		end.init(container);
		state = GameState.MENU;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if(state.equals(GameState.MENU)){
			menu.update(container, delta);
		}
		else if(state.equals(GameState.PLAY)){
			ingame.update(container, delta);
		}
		else if(state.equals(GameState.PAUSED)){
			paused.update(container, delta);
		}
		else if(state.equals(GameState.HIGHSCORE)){
			hs.update(container, delta);
		}
		else if(state.equals(GameState.CREDITS)){
			credits.update(container, delta);
		}
		else if(state.equals(GameState.END)){
			end.update(container, delta);
		}
		else if(state.equals(GameState.EXIT)){
			System.exit(0);
		}
	}

	public static void main(String[] args) throws SlickException {
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		AppGameContainer app = new AppGameContainer(new ETM());

		app.setMinimumLogicUpdateInterval(20);
		app.setMaximumLogicUpdateInterval(20);
		app.setVSync(true);
		app.setDisplayMode(800, 600, false);
		app.setShowFPS(false);
		app.setIcon("res/icon.png");
		app.start();
	}

}
