package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.FontaineComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.FontaineCanonRight;
import com.ad.thepool.objects.SwitchManual;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class ScenePortal extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -230298564094643169L;
	Exit exit;
	TextBox help;

	public ScenePortal(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/portal.lvl", "maps/portal.idx");
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
		FontaineCanonRight font1 = (FontaineCanonRight) searchGObjectBySceneScriptIndex('b').get(0);

		SwitchComponent switchComp = switchDoor1.getSwitchComponent();
		FontaineComponent fontComp = font1.getFontaineComponent();

		switchComp.setKey("font1");
		switchComp.setSwitchState(SwitchComponent.SWITCH_OFF);
		fontComp.setReverseSwitch(true);
		fontComp.setSwitchKey("font1");
		fontComp.setActive(false);

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 23, 5, "The right portal?", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
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
