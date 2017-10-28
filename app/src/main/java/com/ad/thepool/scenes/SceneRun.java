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
import com.ad.thepool.objects.PushAbleBox;
import com.ad.thepool.objects.SwitchManual;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneRun extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4847906974522086410L;
	Exit exit;
	TextBox help;

	public SceneRun(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		super.init();
		Map map = new Map("maps/run.lvl", "maps/run.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		
		setShuffleState(SHUFFLE_STATE_IN);
		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 24, 5, "Run! Run! Run! (or find a shortcut)", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
		SwitchManual switchDoor = (SwitchManual) searchGObjectBySceneScriptIndex('a').get(0);
		Door door = (Door) searchGObjectBySceneScriptIndex('b').get(0);
		PushAbleBox push1 = (PushAbleBox) searchGObjectBySceneScriptIndex('1').get(0);
		PushAbleBox push2 = (PushAbleBox) searchGObjectBySceneScriptIndex('2').get(0);

		push1.getPushPullAbleComponent().setMaxEnergy(25);
		push2.getPushPullAbleComponent().setMaxEnergy(25);

		SwitchComponent switchComp = switchDoor.getSwitchComponent();
		DoorComponent doorComp = door.getDoorComponent();
		switchComp.setKey("door");
		doorComp.setKey("door");
		doorComp.setOpen(false);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);
		saveCheckPoint(true);

	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		super.receiveMessage(source, dest, tag, sendkey);

		if (sendkey.equals("std.enter") && dest.hasTag("player")) {
			ExitComponent exitcomp = exit.getExitComponent();
			if (exitcomp.isOpen() == true) {
				setNewScene(source);
			}
		} else if (sendkey.equals("std.on") && dest.hasTag("player")) {
			ExitComponent exitcomp = exit.getExitComponent();
			exitcomp.setOpen(true);
		} else if (sendkey.equals("std.off") && dest.hasTag("player")) {
			ExitComponent exitcomp = exit.getExitComponent();
			exitcomp.setOpen(false);
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
