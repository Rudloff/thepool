package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.CameraComponent;
import com.ad.thepool.components.DoorComponent;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.LaserComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.objects.Door;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.LaserCanonLeft;
import com.ad.thepool.objects.LaserCanonRight;
import com.ad.thepool.objects.Player;
import com.ad.thepool.objects.SwitchManual;

public class SceneTower extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1034017345677064883L;
	Exit exit;

	public SceneTower(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();
		clearScene();
		Map map = new Map("maps/tower.lvl", "maps/tower.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);

		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);

		SwitchManual switchDoor1 = (SwitchManual) searchGObjectBySceneScriptIndex('a').get(0);
		Door door1 = (Door) searchGObjectBySceneScriptIndex('b').get(0);
		SwitchManual switchDoor2 = (SwitchManual) searchGObjectBySceneScriptIndex('c').get(0);
		SwitchManual switchLaser1 = (SwitchManual) searchGObjectBySceneScriptIndex('d').get(0);
		SwitchManual switchLaser2 = (SwitchManual) searchGObjectBySceneScriptIndex('e').get(0);
		LaserCanonLeft laser1 = (LaserCanonLeft) searchGObjectBySceneScriptIndex('f').get(0);
		LaserCanonRight laser2 = (LaserCanonRight) searchGObjectBySceneScriptIndex('g').get(0);
		Door door2 = (Door) searchGObjectBySceneScriptIndex('h').get(0);

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

		switchComp = switchLaser1.getSwitchComponent();
		LaserComponent laserComp = laser1.getLaserComponent();
		switchComp.setKey("laser1");
		laserComp.setSwitchKey("laser1");
		laserComp.setSwitchReverse(true);
		laserComp.setActive(true);
		switchComp = switchLaser2.getSwitchComponent();
		laserComp = laser2.getLaserComponent();
		switchComp.setKey("laser2");
		laserComp.setActive(true);
		laserComp.setSwitchKey("laser2");
		laserComp.setSwitchReverse(true);

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
