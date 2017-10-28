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
import com.ad.thepool.components.WaypointComponent;
import com.ad.thepool.objects.Door;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.ImageBox;
import com.ad.thepool.objects.SwitchManual;
import com.ad.thepool.objects.Waypoint;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneTutorial extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3730911391299682219L;

	Exit exit;
	ImageBox back;

	public static final int SCRIPT_PAUSE = 101;

	public SceneTutorial(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();
		clearScene();
		Map map = new Map("maps/tutorial.lvl", "maps/tutorial.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		back = addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_paper.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		back.getRenderImageComponent().setColorReplacement(GraphicsWrapper.getRGB(240, 240, 240, 255));

		setShuffleState(SHUFFLE_STATE_IN);
		tempSavefile = "temp_tutorial.bin";

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);
		exitcomp.setNextSceneId(0);
		script = SCRIPT_PAUSE;
		// addImageBox(0, 0, GObject.Z_BACK, 25, 15, "graphics/back_wood.bmp",
		// RenderImageComponent.POS_SCALE, false, false);
		Waypoint waypoint1 = (Waypoint) searchGObjectBySceneScriptIndex('a').get(0);
		Waypoint waypoint2 = (Waypoint) searchGObjectBySceneScriptIndex('b').get(0);
		Waypoint waypoint3 = (Waypoint) searchGObjectBySceneScriptIndex('c').get(0);
		Waypoint waypoint4 = (Waypoint) searchGObjectBySceneScriptIndex('d').get(0);
		Waypoint waypoint5 = (Waypoint) searchGObjectBySceneScriptIndex('e').get(0);
		Waypoint waypoint6 = (Waypoint) searchGObjectBySceneScriptIndex('f').get(0);
		Waypoint waypoint7 = (Waypoint) searchGObjectBySceneScriptIndex('g').get(0);
		Waypoint waypoint8 = (Waypoint) searchGObjectBySceneScriptIndex('h').get(0);
		Waypoint waypoint9 = (Waypoint) searchGObjectBySceneScriptIndex('i').get(0);
		Waypoint waypoint10 = (Waypoint) searchGObjectBySceneScriptIndex('j').get(0);
		Waypoint waypoint11 = (Waypoint) searchGObjectBySceneScriptIndex('k').get(0);
		Waypoint waypoint12 = (Waypoint) searchGObjectBySceneScriptIndex('l').get(0);
		Waypoint waypoint13 = (Waypoint) searchGObjectBySceneScriptIndex('m').get(0);

		SwitchManual switchDoor = (SwitchManual) searchGObjectBySceneScriptIndex('n').get(0);
		Door door = (Door) searchGObjectBySceneScriptIndex('o').get(0);
		// LaserCanonUp cannon =
		// (LaserCanonUp)searchGObjectBySceneScriptIndex('p').get(0);

		WaypointComponent waycomp;
		waycomp = waypoint1.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("You can go in an endless direction,\nbut you'll meet the same things over and over.");
		waycomp = waypoint2.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("This is plattform game with ladders...");
		waycomp = waypoint3.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("... and something to climb\nStep over and climb (left,up)\nJumping is not your favourite");
		waycomp = waypoint4.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("... and robes");
		waycomp = waypoint5.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("This is important, my hand holds a raygun,\nUse WASD or touch edges to fire");
		waycomp = waypoint6.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("Falling down (or sometimes other directions)");
		waycomp = waypoint7.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("Shot left and right,\nportals are popular these days...");
		waycomp = waypoint8.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("Other lasers are your enemy, please avoid.");
		waycomp = waypoint9.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("You flipped gravity, nice feature");
		waycomp = waypoint10.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("Don't forget the laser");
		waycomp = waypoint11.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("These are your silly enemys, please avoid anyway");
		waycomp = waypoint12.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("Go back, find a switch.");
		waycomp = waypoint13.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("Switches might help, use something heavy!");

		SwitchComponent switchComp = switchDoor.getSwitchComponent();
		DoorComponent doorComp = door.getDoorComponent();
		// LaserComponent laserComp =
		// (LaserComponent)cannon.getLaserComponent();

		switchComp.setKey("door");
		doorComp.setKey("door");
		// laserComp.setSwitchKey("door");

		doorComp.setOpen(false);
		setBrightnessDarkest(1.0F);
		setBrightnessScale(1.5F);

		saveCheckPoint(true);
	}

	@Override
	public void onTriggerEnter(GObject sourceobj, GObject triggerobj, int x, int y) {
		if (sourceobj.hasTag("player") && triggerobj.hasTag("waypoint")) {
			WaypointComponent waycomp = triggerobj.getWaypointComponent();
			if (waycomp.getGobject().getSceneScriptIndex() == 'f') {
				setState(Scene.STATE_PAUSE_TAGS);
				getPauseTags().add("player");
				script = SCRIPT_PAUSE;
			}
		}
	}

	// @Override
	@Override
	public void keyDown(int key) {
		super.keyDown(key);
		if (script == SCRIPT_PAUSE && key == KeyEventWrapper.KEY_ENTER) {
			setState(Scene.STATE_PLAY);
			script = SCRIPT_PLAY;
		}
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
		if (sendkey.equals("mouse.click.enter")) {
			if (script == SCRIPT_PAUSE) {
				setState(Scene.STATE_PLAY);
				script = SCRIPT_PLAY;
			}
		}

	}

}
