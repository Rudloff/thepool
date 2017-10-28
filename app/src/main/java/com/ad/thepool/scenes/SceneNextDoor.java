package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneNextDoor extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7195134747329003497L;

	Exit exit;

	TextBox help;

	public SceneNextDoor(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/next_door.lvl", null);
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);

		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		// exitcomp.setKey("door");
		exitcomp.setOpen(false);

		// ArrayList<GObject> switches = searchGObjectByTileType('r');
		//
		// SwitchManual sw=(SwitchManual)switches.get(0);
		// sw.getSwitchComponent().setKey("door");

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 24, 5, "Exit: One level up", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
		saveCheckPoint(true);
	}

	@Override
	public void keyDown(int key) {
		super.keyDown(key);
		if (script == SCRIPT_INIT && key == KeyEventWrapper.KEY_ENTER) {
			RenderTextComponent render = help.getRenderTextComponent();
			if (render != null) {
				render.setActive(false);
			}
			setState(Scene.STATE_PLAY);
			script = SCRIPT_PLAY;
		}
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		super.receiveMessage(source, dest, tag, sendkey);
		ExitComponent exitcomp = exit.getExitComponent();

		if (sendkey.equals("std.enter") && dest.hasTag("player")) {
			if (exitcomp.isOpen() == true) {
				setNewScene(source);
			}
		}
		if (sendkey.equals("mouse.click.enter") && script == SCRIPT_INIT) {
			RenderTextComponent render = help.getRenderTextComponent();
			if (render != null) {
				render.setActive(false);
			}
			setState(Scene.STATE_PLAY);
			script = SCRIPT_PLAY;
		}

	}

}
