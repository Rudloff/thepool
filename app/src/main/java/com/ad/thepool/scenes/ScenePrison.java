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
import com.ad.thepool.components.PathMoveComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.components.WaypointComponent;
import com.ad.thepool.objects.Character;
import com.ad.thepool.objects.Door;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.LaserCanonLeft;
import com.ad.thepool.objects.LaserCanonRight;
import com.ad.thepool.objects.Player;
import com.ad.thepool.objects.PullAbleBox;
import com.ad.thepool.objects.SwitchWeight;
import com.ad.thepool.objects.SwitchWeightTimed;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.objects.Waypoint;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class ScenePrison extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7632721553032462223L;
	Exit exit;
	TextBox help;
	Character char1;
	Character char2;

	public ScenePrison(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/prison.lvl", "maps/prison.idx");
		addMap(map);
		// Map map2 = new Map("maps/prison2.lvl","maps/prison2.idx");
		// addMap(map2);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);

		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);

		Waypoint waypoint1 = (Waypoint) searchGObjectBySceneScriptIndex('a').get(0);
		Waypoint waypoint2 = (Waypoint) searchGObjectBySceneScriptIndex('b').get(0);
		Waypoint waypoint3 = (Waypoint) searchGObjectBySceneScriptIndex('c').get(0);
		Waypoint waypoint4 = (Waypoint) searchGObjectBySceneScriptIndex('d').get(0);
		SwitchWeight sweight1 = (SwitchWeight) searchGObjectBySceneScriptIndex('e').get(0);
		Door door1 = (Door) searchGObjectBySceneScriptIndex('f').get(0);
		SwitchWeight sweight2 = (SwitchWeight) searchGObjectBySceneScriptIndex('g').get(0);
		Door door2 = (Door) searchGObjectBySceneScriptIndex('h').get(0);
		SwitchWeightTimed sweight3 = (SwitchWeightTimed) searchGObjectBySceneScriptIndex('i').get(0);
		LaserCanonLeft laser3 = (LaserCanonLeft) searchGObjectBySceneScriptIndex('k').get(0);
		SwitchWeightTimed sweight4 = (SwitchWeightTimed) searchGObjectBySceneScriptIndex('j').get(0);
		LaserCanonRight laser4 = (LaserCanonRight) searchGObjectBySceneScriptIndex('l').get(0);
		//Waypoint waypointBox = (Waypoint) searchGObjectBySceneScriptIndex('m').get(0);
		PullAbleBox pullb = (PullAbleBox) searchGObjectBySceneScriptIndex('n').get(0);
		pullb.getTagList().add("box");
		SwitchWeightTimed sweight5 = (SwitchWeightTimed) searchGObjectBySceneScriptIndex('o').get(0);
		Door door5 = (Door) searchGObjectBySceneScriptIndex('p').get(0);

		char1 = (Character) searchGObjectBySceneScriptIndex('1').get(0);
		char2 = (Character) searchGObjectBySceneScriptIndex('2').get(0);

		char2.getTagList().add("character2");

		WaypointComponent waycomp;
		waycomp = waypoint1.getWaypointComponent();
		waycomp.setOrder(0);
		waycomp.setTag("player");
		waycomp.setHelpEnterText("Someone whispers in the next cell");
		waycomp = waypoint2.getWaypointComponent();
		waycomp.setOrder(0);
		waycomp.setTag("character");
		waycomp = waypoint3.getWaypointComponent();
		waycomp.setOrder(1);
		waycomp.setTag("character");
		waycomp = waypoint4.getWaypointComponent();
		waycomp.setOrder(2);
		waycomp.setTag("character");

		SwitchComponent scomp = sweight1.getSwitchComponent();
		scomp.setKey("door1");
		DoorComponent dcomp = door1.getDoorComponent();
		dcomp.setKey("door1");

		scomp = sweight2.getSwitchComponent();
		scomp.setKey("door2");
		dcomp = door2.getDoorComponent();
		dcomp.setKey("door2");

		scomp = sweight3.getSwitchComponent();
		scomp.setKey("laser3");
		LaserComponent lcomp = laser3.getLaserComponent();
		lcomp.setActive(false);
		lcomp.setSwitchKey("laser3");

		scomp = sweight4.getSwitchComponent();
		scomp.setKey("laser4");
		lcomp = laser4.getLaserComponent();
		lcomp.setActive(false);
		lcomp.setSwitchKey("laser4");

		scomp = sweight5.getSwitchComponent();
		scomp.setKey("door3");
		scomp.setDuration(100);
		dcomp = door5.getDoorComponent();
		dcomp.setKey("door3");
		dcomp.setReverseSwitch(true);
		dcomp.setOpen(true);

		PathMoveComponent pathMove = char1.getPathMoveComponent();
		pathMove.setRemoveAfterMove(false);
		pathMove.getPath().clear();
		pathMove.addPath("character", 0, "left.down", false);
		pathMove.addPath("character", 1, "down.down", false);
		pathMove.addPath("character", 2, "left.down", false);
		pathMove.addPath("character", 3, "laser_up.down", 5, false);
		pathMove.addPath("character", 4, "right.down", 5, true);
		pathMove.addPath("character", 5, "laser_right.down", 10, false);
		pathMove.addPath("character", 6, "laser_right.up", 1, false);
		pathMove.reset();
		pathMove.setDoMove(false);

		PathMoveComponent pathMove2 = char2.getPathMoveComponent();
		pathMove2.setRemoveAfterMove(false);
		pathMove2.getPath().clear();
		pathMove2.addPath("character2", 0, "left.down", 50, false);
		pathMove2.reset();
		pathMove2.setDoMove(false);

		ArrayList<GObject> players = searchGObjectByTag("player");
		Player player = (Player) players.get(0);
		CameraComponent cam = player.getCameraComponent();
		cam.setType(CameraComponent.TYPE_FOLLOW_Y);

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 24, 5, "Last day in prison...", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
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
		}
	}

	@Override
	public void onTriggerEnter(GObject sourceobj, GObject triggerobj, int x, int y) {
		if (sourceobj.hasTag("player") && triggerobj.hasTag("waypoint")) {
			WaypointComponent waycomp = triggerobj.getWaypointComponent();
			if (waycomp.getOrder() == 0 && waycomp.getVisited() == 1) {
				PathMoveComponent pathMove = char1.getPathMoveComponent();
				pathMove.setDoMove(true);
			}
		}
		if (sourceobj.hasTag("box") && triggerobj.hasTag("waypoint")) {
			PathMoveComponent pathMove = char1.getPathMoveComponent();
			pathMove.setDoMove(true);
			PathMoveComponent pathMove2 = char2.getPathMoveComponent();
			pathMove2.setDoMove(true);

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
		if (sendkey.equals("mouse.click.enter") && script == SCRIPT_INIT) {
			RenderTextComponent render = help.getRenderTextComponent();
			if (render != null) {
				render.setActive(false);
			}
			setState(Scene.STATE_PLAY);
		}

	}

}
