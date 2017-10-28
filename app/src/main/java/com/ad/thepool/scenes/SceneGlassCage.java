package com.ad.thepool.scenes;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneGlassCage extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6980053216999952091L;
	TextBox help;

	public SceneGlassCage(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();
		clearScene();
		Map map = new Map("maps/glascages.lvl", null);
		addMap(map);
		addMouseCursor();
		addMenu(); 
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);


		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 23, 5, "Build your stairway...", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
		// addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_grey.png",
		// RenderImageComponent.POS_SCALE, false);
		// addImageBox(0, 0, GObject.Z_FOREGROUND, 25, 15, "front_col.png",
		// RenderImageComponent.POS_SCALE, false);
		saveCheckPoint(true);

	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		super.receiveMessage(source, dest, tag, sendkey);

		if (sendkey.equals("std.enter") && dest.hasTag("player")) {
			setNewScene(source);
		}
		if (sendkey.equals("mouse.click.enter") && script == SCRIPT_INIT) {
			RenderTextComponent render = help.getRenderTextComponent();
			if (render != null) {
				render.setActive(false);
			}
			setState(Scene.STATE_PLAY);
		}
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
}
