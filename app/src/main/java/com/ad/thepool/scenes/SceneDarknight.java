package com.ad.thepool.scenes;

import java.util.ArrayList;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.CameraComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.objects.Player;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneDarknight extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5344022726663482828L;
	TextBox help;

	public SceneDarknight(int order, Game game) {
		super(order, game);
		;
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/darknight.lvl", null);
		addMap(map);
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_stars.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		help = addTextBox(1, 1, 20, 13, "In the darkest night,\nafter endless falling,\nmy dream was captured by a breeze\ncaptured from infinity.\n\nThe pain is long forgotten,\nJust remembrance,\nquite remembrance", GraphicsWrapper.COLOR_RED, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
//		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_stars.png", RenderImageComponent.POS_PARALLAX, false);
		ArrayList<GObject> players = searchGObjectByTag("player");
		Player player = (Player) players.get(0);
		CameraComponent cam = player.getCameraComponent();
		cam.setType(CameraComponent.TYPE_FIX);
		saveCheckPoint(true);

		// addImageBox(0, 0, GObject.Z_BACK, 25, 15,
		// "graphics/back_universe.bmp", RenderImageComponent.POS_SCALE, false,
		// false);

	}

	@Override
	public void keyDown(int key) {
		if (key == KeyEventWrapper.KEY_ENTER) {
			setNewScene(-1);
		}
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		super.receiveMessage(source, dest, tag, sendkey);
		if (sendkey.equals("mouse.click.enter")) {
			setNewScene(-1);
		}

	}

}
