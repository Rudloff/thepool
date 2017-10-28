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
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneControlRoom extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1949223010899623378L;
	TextBox help;
	Exit exit;

	public SceneControlRoom(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();
		clearScene();
		Map map = new Map("maps/controlroom.lvl", "maps/controlroom.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(true);

		SwitchManual switchDoor1 = (SwitchManual) searchGObjectBySceneScriptIndex('a').get(0);
		Door door1 = (Door) searchGObjectBySceneScriptIndex('1').get(0);
		SwitchManual switchDoor2 = (SwitchManual) searchGObjectBySceneScriptIndex('b').get(0);
		Door door2 = (Door) searchGObjectBySceneScriptIndex('2').get(0);
		SwitchManual switchDoor3 = (SwitchManual) searchGObjectBySceneScriptIndex('c').get(0);
		Door door3 = (Door) searchGObjectBySceneScriptIndex('3').get(0);

		SwitchComponent switchComp = switchDoor1.getSwitchComponent();
		DoorComponent doorComp = door1.getDoorComponent();
		switchComp.setKey("door1");
		doorComp.setKey("door1");
		doorComp.setOpen(false);

		switchComp = switchDoor2.getSwitchComponent();
		doorComp = door2.getDoorComponent();
		switchComp.setKey("door2");
		doorComp.setKey("door2");
		doorComp.setOpen(false);

		switchComp = switchDoor3.getSwitchComponent();
		doorComp = door3.getDoorComponent();
		switchComp.setKey("door3");
		doorComp.setKey("door3");
		doorComp.setOpen(false);

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 23, 5, "Three doors shut...", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
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
