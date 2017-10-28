package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.DoorComponent;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.objects.Door;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.SwitchManual;
import com.ad.thepool.objects.SwitchWeight;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneCollect extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7762530529769371065L;

	Exit exit;

	TextBox help;

	public SceneCollect(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/collect.lvl", "maps/collect.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);

		SwitchManual switchDoorA = (SwitchManual) searchGObjectBySceneScriptIndex('a').get(0);
		Door doorA = (Door) searchGObjectBySceneScriptIndex('A').get(0);

		SwitchWeight switchDoorB = (SwitchWeight) searchGObjectBySceneScriptIndex('b').get(0);
		Door doorB = (Door) searchGObjectBySceneScriptIndex('B').get(0);

		SwitchManual switchDoorC = (SwitchManual) searchGObjectBySceneScriptIndex('c').get(0);
		Door doorC = (Door) searchGObjectBySceneScriptIndex('C').get(0);

		// A
		SwitchComponent switchComp = switchDoorA.getSwitchComponent();
		DoorComponent doorComp = doorA.getDoorComponent();
		doorComp.setOpen(false);
		switchComp.setKey("doorA");
		doorComp.setKey("doorA");

		// B
		switchComp = switchDoorB.getSwitchComponent();
		doorComp = doorB.getDoorComponent();
		doorComp.setOpen(true);
		switchComp.setSwitchState(SwitchComponent.SWITCH_ON);
		switchComp.setKey("doorB");
		doorComp.setKey("doorB");
		doorComp.setReverseSwitch(true);

		// C
		switchComp = switchDoorC.getSwitchComponent();
		doorComp = doorC.getDoorComponent();
		doorComp.setOpen(false);
		switchComp.setKey("doorC");
		doorComp.setKey("doorC");

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 23, 5, "Coins in the pool...", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
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

	}

}
