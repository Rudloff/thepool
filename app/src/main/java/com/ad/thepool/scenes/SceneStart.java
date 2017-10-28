package com.ad.thepool.scenes;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.CameraComponent;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.WaypointComponent;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.ImageBox;
import com.ad.thepool.objects.Player;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.objects.Waypoint;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;
import com.ad.thepool.wrapper.StreamWrapper;

public class SceneStart extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5639856081921567513L;
	public static final int SCRIPT_HELP = 1;
	public static final int SCRIPT_PLAY = 2;

	TextBox help;
	ImageBox logo;
	Exit exit;

	public SceneStart(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/start.lvl", "maps/start.idx");
		Map map2 = new Map("maps/start2.lvl", null);
		addMap(map);
		addMap(map2);
		addMenu();
		showPause = false;
		logo = addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_paper.png", RenderImageComponent.POS_PARALLAX_SLOW, false);
		logo.getRenderImageComponent().setColorReplacement(GraphicsWrapper.getRGB(240, 240, 240, 255));
		setParFastRatioX(0.8);
		addImageBox(0, 0, GObject.Z_FOREGROUND, 25, 15, "logo.png", RenderImageComponent.POS_PARALLAX_FAST, false);
		addMouseCursor();

		// addImageBox(0,0,GObject.Z_FOREGROUND,25,15,"front_pixel.png",
		// RenderImageComponent.POS_SCALE,false);
		script = SCRIPT_HELP;
		setState(Scene.STATE_PAUSE_TAGS);
		setShuffleState(SHUFFLE_STATE_IN);
		getPauseTags().add("player");
		Waypoint waypoint1 = (Waypoint) searchGObjectBySceneScriptIndex('1').get(0);
		Waypoint waypoint2 = (Waypoint) searchGObjectBySceneScriptIndex('2').get(0);

		Player player = (Player) searchGObjectByTag("player").get(0);
		TransformTileComponent trans = player.getTransformTileComponent();
		int highx = trans.getX();
		int highy = trans.getY();

		help = addTextBox(highx, highy, 15, 13, 10, 3, "Press to start\nMenu for options", GraphicsWrapper.COLOR_BLACK, true, RenderTextComponent.POS_RIGHT_UP, false, null, null);

		WaypointComponent waycomp;
		waycomp = waypoint1.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("a game\nby adaumann");
		waycomp = waypoint2.getWaypointComponent();
		waycomp.setTag("player");
		waycomp.setHelpEnterText("music\nby bluemillenium");

		Exit exit1 = (Exit) searchGObjectBySceneScriptIndex('a').get(0);
		Exit exit2 = (Exit) searchGObjectBySceneScriptIndex('b').get(0);
		// Exit exit3 = (Exit)searchGObjectBySceneScriptIndex('c').get(0);
		Exit exit4 = (Exit) searchGObjectBySceneScriptIndex('d').get(0);

		ExitComponent exitcomp;
		exitcomp = exit1.getExitComponent();
		if (StreamWrapper.fileExists(Game.INFO_SAVE_FILE_NAME) == true) {
			exitcomp.setHelpEnterText("New Game - Overwrite Savegame");
		} else {
			exitcomp.setHelpEnterText("New Game");
		}

		exitcomp = exit2.getExitComponent();
		exitcomp.setKey("load");
		if (StreamWrapper.fileExists(Game.INFO_SAVE_FILE_NAME) == true) {
			exitcomp.setHelpEnterText("Resume Last Checkpoint");
		} else {
			exitcomp.setHelpEnterText("New game");
		}

		// exitcomp = (ExitComponent)exit3.getExitComponent();
		// exitcomp.setKey("quit");
		// exitcomp.setHelpEnterText("Quit");

		exitcomp = exit4.getExitComponent();
		exitcomp.setKey("tutorial");
		exitcomp.setHelpEnterText("Tutorial");
		CameraComponent cam = player.getCameraComponent();
		cam.setType(CameraComponent.TYPE_FOLLOW_X);
		setBrightnessDarkest(0.9F);
		setBrightnessScale(1.0F);

	}

	@Override
	public void keyDown(int key) {
		super.keyDown(key);
		if (script == SCRIPT_HELP && key == KeyEventWrapper.KEY_ENTER) {
			script = SCRIPT_PLAY;
			setState(Scene.STATE_PLAY);
			getPauseTags().clear();
			RenderTextComponent rendertxt = help.getRenderTextComponent();
			rendertxt.setActive(false);

		}
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		super.receiveMessage(source, dest, tag, sendkey);
		if (dest != null && dest.hasTag("player")) {
			if (sendkey.equals("std.enter")) {
				setNewScene(source);
			}
			if (sendkey.equals("load.enter")) {
				sendMessage(null, null, null, "control.checkpoint");
			}
			if (sendkey.equals("quit.enter")) {
				System.exit(0);
			}
			if (sendkey.equals("tutorial.enter")) {
				setNewScene(1000);
			}
		}
		if (sendkey.equals("mouse.click.enter")) {
			if (script == SCRIPT_HELP) {
				script = SCRIPT_PLAY;
				setState(Scene.STATE_PLAY);
				getPauseTags().clear();
				RenderTextComponent rendertxt = help.getRenderTextComponent();
				rendertxt.setActive(false);

			}
		}
	}

	// public void onTriggerEnter(GObject sourceobj, GObject triggerobj, int x,
	// int y)
	// {
	// if(sourceobj.hasTag("player") == true)
	// {
	// ExitComponent exitComp = (ExitComponent)exit.getExitComponent();
	//
	// setNewScene(triggerobj);
	// }
	// }

}
