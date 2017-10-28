package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.LaserComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.LaserCanonLeft;
import com.ad.thepool.objects.LaserCanonRight;
import com.ad.thepool.objects.SwitchManual;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneBeSave extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3859942259926056196L;
	TextBox help;

	public SceneBeSave(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/be_save.lvl", "maps/be_save.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		setShuffleState(SHUFFLE_STATE_IN);
		ArrayList<GObject> exits = searchGObjectByTag("exit");
		Exit exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(true);

		SwitchManual switch1 = (SwitchManual) searchGObjectBySceneScriptIndex('a').get(0);
		SwitchManual switch2 = (SwitchManual) searchGObjectBySceneScriptIndex('b').get(0);
		LaserCanonLeft laser1 = (LaserCanonLeft) searchGObjectBySceneScriptIndex('c').get(0);
		LaserCanonRight laser2 = (LaserCanonRight) searchGObjectBySceneScriptIndex('d').get(0);
		SwitchComponent switchComp = switch1.getSwitchComponent();
		switchComp.setKey("laser1");
		
		switchComp = switch2.getSwitchComponent();
		switchComp.setKey("laser2");

		LaserComponent laserComp = laser1.getLaserComponent();
		laserComp.setSwitchKey("laser1");
		laserComp.setSwitchReverse(true);
		laserComp = laser2.getLaserComponent();
		laserComp.setSwitchKey("laser2");
		laserComp.setSwitchReverse(true);

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 23, 5, "Differences in the reflection...", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
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
