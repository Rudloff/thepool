package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.DoorComponent;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.objects.Door;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.SwitchManual;
import com.ad.thepool.objects.SwitchWeight;

public class SceneFloat extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5361470779786428211L;
	Exit exit;

	public SceneFloat(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/float.lvl", "maps/float.idx");
		Map map2 = new Map("maps/float_water.lvl", null);
		addMap(map);
		addMap(map2);
		addMouseCursor();
		addMenu();
		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(true);

		SwitchManual switchDoor1 = (SwitchManual) searchGObjectBySceneScriptIndex('a').get(0);
		Door door1 = (Door) searchGObjectBySceneScriptIndex('c').get(0);
		SwitchManual switchDoor2 = (SwitchManual) searchGObjectBySceneScriptIndex('b').get(0);
		Door door2 = (Door) searchGObjectBySceneScriptIndex('d').get(0);
		SwitchWeight switchDoor3 = (SwitchWeight) searchGObjectBySceneScriptIndex('e').get(0);
		Door door3 = (Door) searchGObjectBySceneScriptIndex('f').get(0);

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
	}

}
