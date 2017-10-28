package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.CameraComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.objects.Player;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneSimplePortal extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3859942259926056196L;
	TextBox help;

	public SceneSimplePortal(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/simpleportal.lvl", null);
		addMap(map);
		addMouseCursor();
		addMenu();
		setShuffleState(SHUFFLE_STATE_IN);

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 23, 5, "Crossing the river", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		
		ArrayList<GObject> players = searchGObjectByTag("player");
		Player player = (Player) players.get(0);
		CameraComponent cam = player.getCameraComponent();
		cam.setType(CameraComponent.TYPE_FOLLOW_Y);

		saveCheckPoint(true);
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		super.receiveMessage(source, dest, tag, sendkey);

		if (sendkey.equals("std.enter") && dest.hasTag("player")) {
			setNewScene(source);
		}
		if (sendkey.equals("mouse.click.enter") && script == SCRIPT_INIT) {
			RenderTextComponent render = help.getRenderTextComponent();
			if (render != null) {
				render.setActive(false);
			}
			setState(Scene.STATE_PLAY);
		}
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
		}

	}
}
