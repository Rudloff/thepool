package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.objects.Exit;

public class SceneRevert extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -190247105294969484L;
	Exit exit;

	public SceneRevert(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		clearScene();
		super.init();

		Map map = new Map("maps/revert.lvl", null);
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);

		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);
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
