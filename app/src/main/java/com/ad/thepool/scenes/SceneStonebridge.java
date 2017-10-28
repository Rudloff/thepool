package com.ad.thepool.scenes;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneStonebridge extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2017110463567550800L;

	Exit exit;

	TextBox help;
	int hintCounter;
	public static final int MAX_HINT_COUNTER = 100;

	public SceneStonebridge(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();
		clearScene();
		Map map = new Map("maps/stonebridge.lvl", "maps/stonebridge.idx");
		Map map2 = new Map("maps/stonebridge2.lvl", null);
		addMap(map);
		addMap(map2);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);

		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 24, 15, "Stones will hide treasures...", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);

		ArrayList<GObject> switchHint = searchGObjectBySceneScriptIndex('a');
		for (Iterator<GObject> iterator = switchHint.iterator(); iterator.hasNext();) {
			GObject gObject = (GObject) iterator.next();
			SwitchComponent switchComp = gObject.getSwitchComponent();
			switchComp.setKey("hint");
		}
		saveCheckPoint(true);

	}

	@Override
	public boolean paint(GraphicsWrapper g) {
		if (hintCounter > 0) {
			hintCounter--;
			int modHintC = hintCounter % 20;
			if (modHintC == 0) {
				showBoulders(true);
			}
			if (modHintC == 10) {
				showBoulders(false);
			}
			if (hintCounter == 0) {
				showBoulders(true);
			}
		}
		return super.paint(g);
	}

	private void showBoulders(boolean active) {
		ArrayList<GObject> boulders = searchGObjectByTileType('b');
		for (Iterator<GObject> iterator = boulders.iterator(); iterator.hasNext();) {
			GObject gObject = (GObject) iterator.next();
			RenderTileComponent render = gObject.getRenderTileComponent();
			if (render != null) {
				render.setActive(active);
			}
		}
		boulders = searchGObjectByTileType('e');
		for (Iterator<GObject> iterator = boulders.iterator(); iterator.hasNext();) {
			GObject gObject = (GObject) iterator.next();
			RenderTileComponent render = gObject.getRenderTileComponent();
			if (render != null) {
				render.setActive(active);
			}
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

		if (sendkey.startsWith("coin.empty") && source.hasTag("player")) {
			exitcomp.setOpen(true);
		}
		if (sendkey.equals("mouse.click.enter") && script == SCRIPT_INIT) {
			RenderTextComponent render = help.getRenderTextComponent();
			if (render != null) {
				render.setActive(false);
			}
			setState(Scene.STATE_PLAY);
			script = SCRIPT_PLAY;
		}
		if (sendkey.equals("hint.on")) {
			hintCounter = MAX_HINT_COUNTER;
		}
	}

}
