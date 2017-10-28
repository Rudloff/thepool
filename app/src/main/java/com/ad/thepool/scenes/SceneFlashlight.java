package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.objects.Door;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.Player;
import com.ad.thepool.objects.SwitchManual;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneFlashlight extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1058118405296499053L;
	TextBox help;
	Exit exit;
	Player player;

	public SceneFlashlight(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();
		clearScene();
		Map map = new Map("maps/flashlight.lvl", "maps/flashlight.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
//		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);
		player = (Player) searchGObjectByTileType('@').get(0);
		LightComponent light = player.getLightComponent();

		light.setIntensity2(7);

		SwitchManual switchDoor = (SwitchManual) searchGObjectBySceneScriptIndex('a').get(0);
		Door door = (Door) searchGObjectBySceneScriptIndex('b').get(0);
		switchDoor.getSwitchComponent().setKey("door");
		door.getDoorComponent().setKey("door");
		door.getDoorComponent().setOpen(false);

		setState(STATE_PAUSE_ALL);
		setBrightnessDarkest(0F);
		setBrightnessScale(1.5F);

		help = addTextBox(1, 1, 23, 5, "Dark in here...", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
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
