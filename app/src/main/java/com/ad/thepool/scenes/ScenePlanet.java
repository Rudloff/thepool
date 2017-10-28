package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.components.WaypointComponent;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.SwitchWeight;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.objects.Waypoint;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class ScenePlanet extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1087995385381373356L;

	Exit exit;

	TextBox help;
	boolean key1 = false, key2 = false, key3 = false, key4 = false;

	public ScenePlanet(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/planet.lvl", "maps/planet.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_stars.png", RenderImageComponent.POS_PARALLAX_SLOW, false);

		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);
		Waypoint waypoint1 = (Waypoint) searchGObjectBySceneScriptIndex('a').get(0);
		SwitchWeight switchWeight1 = (SwitchWeight) searchGObjectBySceneScriptIndex('b').get(0);
		SwitchWeight switchWeight2 = (SwitchWeight) searchGObjectBySceneScriptIndex('c').get(0);
		SwitchWeight switchWeight3 = (SwitchWeight) searchGObjectBySceneScriptIndex('d').get(0);
		SwitchWeight switchWeight4 = (SwitchWeight) searchGObjectBySceneScriptIndex('e').get(0);

		SwitchComponent switchComp = switchWeight1.getSwitchComponent();
		switchComp.setKey("switch1");
		switchComp = switchWeight2.getSwitchComponent();
		switchComp.setKey("switch2");
		switchComp = switchWeight3.getSwitchComponent();
		switchComp.setKey("switch3");
		switchComp = switchWeight4.getSwitchComponent();
		switchComp.setKey("switch3");

		WaypointComponent waycomp;
		waycomp = waypoint1.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("One at the top, one at the buttom...");

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 24, 5, "Go outside, find a planet", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
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
		if (sendkey.equals("switch1.on")) {
			key1 = true;
		}
		if (sendkey.equals("switch1.off")) {
			key1 = false;
		}
		if (sendkey.equals("switch2.on")) {
			key2 = true;
		}
		if (sendkey.equals("switch2.off")) {
			key2 = false;
		}
		if (sendkey.equals("switch3.on")) {
			key3 = true;
		}
		if (sendkey.equals("switch3.off")) {
			key3 = false;
		}
		if (sendkey.equals("switch4.on")) {
			key4 = true;
		}
		if (sendkey.equals("switch4.off")) {
			key4 = false;
		}

		if (key1 == true && key2 == false && key3 == true && key4 == false) {
			exit.getExitComponent().setOpen(true);
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
