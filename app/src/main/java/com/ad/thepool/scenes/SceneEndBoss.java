package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.DoorComponent;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.LaserComponent;
import com.ad.thepool.components.PushPullAbleComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.objects.Door;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.LaserCanonDown;
import com.ad.thepool.objects.PullAbleBox;
import com.ad.thepool.objects.SwitchManual;
import com.ad.thepool.objects.SwitchWeight;
import com.ad.thepool.objects.SwitchWeightTimed;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneEndBoss extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7762530529769371065L;

	Exit exit;

	TextBox help;
	Door door3;
	boolean actDoor1, actDoor2;
	
	public SceneEndBoss(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/endboss.lvl", "maps/endboss.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);

		SwitchWeightTimed switchDoor1 = (SwitchWeightTimed) searchGObjectBySceneScriptIndex('a').get(0);
		SwitchWeightTimed switchDoor2 = (SwitchWeightTimed) searchGObjectBySceneScriptIndex('b').get(0);
		Door door1 = (Door) searchGObjectBySceneScriptIndex('A').get(0);
		Door door2 = (Door) searchGObjectBySceneScriptIndex('B').get(0);
		door3 = (Door) searchGObjectBySceneScriptIndex('C').get(0);
		LaserCanonDown laser1 = (LaserCanonDown) searchGObjectBySceneScriptIndex('1').get(0);
		LaserCanonDown laser2 = (LaserCanonDown) searchGObjectBySceneScriptIndex('2').get(0);
		LaserCanonDown laser3 = (LaserCanonDown) searchGObjectBySceneScriptIndex('3').get(0);
		LaserCanonDown laser4 = (LaserCanonDown) searchGObjectBySceneScriptIndex('4').get(0);
		PullAbleBox box1 = (PullAbleBox) searchGObjectBySceneScriptIndex('x').get(0);
		PullAbleBox box2 = (PullAbleBox) searchGObjectBySceneScriptIndex('y').get(0);		
		SwitchManual switchLaserC = (SwitchManual) searchGObjectBySceneScriptIndex('c').get(0);
		SwitchManual switchLaserD = (SwitchManual) searchGObjectBySceneScriptIndex('d').get(0);

		SwitchComponent switchComp;
		DoorComponent doorComp;
		LaserComponent laserComp;
		PushPullAbleComponent ppComp;

		// door1
		switchComp = switchDoor1.getSwitchComponent();
		doorComp = door1.getDoorComponent();
		doorComp.setOpen(false);
		switchComp.setKey("door1");
		doorComp.setKey("door1");

		// door2
		switchComp = switchDoor2.getSwitchComponent();
		doorComp = door2.getDoorComponent();
		doorComp.setOpen(false);
		switchComp.setKey("door2");
		doorComp.setKey("door2");
		
		// laser1
		switchComp = switchLaserC.getSwitchComponent();
		laserComp = laser2.getLaserComponent();
		ppComp = box2.getPushPullAbleComponent();
		switchComp.setKey("laserc");
		laserComp.setSwitchKey("laserc");
		laserComp.setSwitchReverse(true);
		ppComp.setSwitchKey("laserc");

		// laser2
		switchComp = switchLaserD.getSwitchComponent();
		ppComp = box1.getPushPullAbleComponent();
		laserComp = laser1.getLaserComponent();
		switchComp.setKey("laserd");
		laserComp.setSwitchKey("laserd");
		laserComp.setSwitchReverse(true);
		ppComp.setSwitchKey("laserd");


		//
//		// B
//		switchComp = switchDoorB.getSwitchComponent();
//		doorComp = doorB.getDoorComponent();
//		doorComp.setOpen(true);
//		switchComp.setSwitchState(SwitchComponent.SWITCH_ON);
//		switchComp.setKey("doorB");
//		doorComp.setKey("doorB");
//		doorComp.setReverseSwitch(true);
//
//		// C
//		switchComp = switchDoorC.getSwitchComponent();
//		doorComp = doorC.getDoorComponent();
//		doorComp.setOpen(false);
//		switchComp.setKey("doorC");
//		doorComp.setKey("doorC");

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 23, 5, "The End", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
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
		if(sendkey.equals("door1.on"))
		{
			actDoor1 = true;
		}
		if(sendkey.equals("door1.off"))
		{
			actDoor1 = false;
		}
		if(sendkey.equals("door2.on"))
		{
			actDoor2 = true;
		}
		if(sendkey.equals("door2.off"))
		{
			actDoor2 = false;
		}
		if(actDoor1 == true && actDoor2 == true)
		{
			exit.getExitComponent().setOpen(true);
		}

		if (sendkey.startsWith("coin.empty") && source.hasTag("player")) 
		{
			door3.getDoorComponent().setOpen(true);
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
