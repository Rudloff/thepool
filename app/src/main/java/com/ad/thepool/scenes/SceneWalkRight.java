package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.CameraComponent;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.Player;
import com.ad.thepool.objects.PushAbleBox;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneWalkRight extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3264264512226212426L;
	Exit exit;
	TextBox help;

	public SceneWalkRight(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();
		clearScene();
		Map map = new Map("maps/walkright.lvl", null);
		addMap(map);
		addMouseCursor();
		addMenu();
		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);
		// addImageBox(0, 0, GObject.Z_BACK2, 25, 15, "back_walkright.png",
		// RenderImageComponent.POS_SCALE, false);
		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 23, 5, "Walk to the right", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		addImageBox(0, 0, GObject.Z_FOREGROUND, 25, 15, "front_mist.png", RenderImageComponent.POS_PARALLAX_FAST, false);		
		Player player = (Player) searchGObjectByTag("player").get(0);
		CameraComponent cam = player.getCameraComponent();
		PushAbleBox push = (PushAbleBox) searchGObjectByTileType('d').get(0);
		push.getPushPullAbleComponent().setMaxEnergy(30);
		cam.setType(CameraComponent.TYPE_FOLLOW_XY);

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
