package com.ad.thepool.scenes;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.CameraComponent;
import com.ad.thepool.components.DoorComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneManyHeros extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6277408518526967689L;
	TextBox help;
	GObject fon;

	public SceneManyHeros(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/many_heros.lvl", "maps/many_heros.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);

		setShuffleState(SHUFFLE_STATE_IN);

		setState(STATE_PAUSE_ALL);
		setNewSceneAfterKill(false);
		help = addTextBox(1, 1, 23, 5, "Too many heros,\ntidy up please!", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
		ArrayList<GObject> players = searchGObjectByTag("player");
		for (Iterator<GObject> iterator = players.iterator(); iterator.hasNext();) {
			GObject player = iterator.next();
			player.getCameraComponent().setActive(false);
			player.getLaserComponent().setActive(false);
		}
		GObject monster = searchGObjectBySceneScriptIndex('a').get(0);
		CameraComponent camera = new CameraComponent(true, CameraComponent.TYPE_FOLLOW_XY);
		monster.addComponent(camera);

		GObject switchDoor = searchGObjectBySceneScriptIndex('b').get(0);
		GObject door = searchGObjectBySceneScriptIndex('c').get(0);

		SwitchComponent switchComp = switchDoor.getSwitchComponent();
		DoorComponent doorComp = door.getDoorComponent();
		switchComp.setKey("door1");
		doorComp.setKey("door1");
		doorComp.setOpen(false);

		GObject switchFon = searchGObjectBySceneScriptIndex('e').get(0);
		fon = searchGObjectBySceneScriptIndex('d').get(0);
		fon.getFontaineComponent().setActive(false);
		switchComp = switchFon.getSwitchComponent();
		switchComp.setKey("fontaine");
		saveCheckPoint(true);

	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		super.receiveMessage(source, dest, tag, sendkey);

		if (sendkey.equals("mouse.click.enter") && script == SCRIPT_INIT) {
			RenderTextComponent render = help.getRenderTextComponent();
			if (render != null) {
				render.setActive(false);
			}
			setState(Scene.STATE_PLAY);
		}
		if (sendkey.equals("std.kill") && dest.hasTag("player")) {
			boolean found = false;
			for (Iterator<GObject> iterator = bufferList.iterator(); iterator.hasNext();) {
				GObject obj = (GObject) iterator.next();
				if (obj.hasTag("player")) {
					found = true;
					break;
				}
			}
			if (found == false) {
				setNewScene(-1);
			}
		}
		if (sendkey.equals("fontaine.on")) {
			fon.getFontaineComponent().setActive(true);
		}
		if (sendkey.equals("fontaine.off")) {
			fon.getFontaineComponent().setActive(false);
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
