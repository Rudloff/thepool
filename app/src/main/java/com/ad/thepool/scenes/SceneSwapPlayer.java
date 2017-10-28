package com.ad.thepool.scenes;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.CameraComponent;
import com.ad.thepool.components.ControlComponent;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.LaserComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.Player;
import com.ad.thepool.objects.SwitchManual;
import com.ad.thepool.objects.SwitchOnce;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneSwapPlayer extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7100657082687073909L;

	Exit exit;

	TextBox help;
	Player activePlayer;
	Player inactivePlayer;
	boolean swapAllowed;
	public static final int MAX_HELP_DURATION = 300;
	private int helpDuration;
	private static final int MAX_SWAP_DURATION = 20;
	private int swapDuration;
	ArrayList<GObject> fonDown;
	ArrayList<GObject> fonUp;

	public SceneSwapPlayer(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();
		clearScene();
		Map map = new Map("maps/swap_player.lvl", "maps/swap_player.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);

		setShuffleState(SHUFFLE_STATE_IN);

		ArrayList<GObject> exits = searchGObjectByTag("exit");
		exit = (Exit) exits.get(0);
		ExitComponent exitcomp = exit.getExitComponent();
		exitcomp.setOpen(false);

		SwitchOnce swap = (SwitchOnce) searchGObjectBySceneScriptIndex('c').get(0);
		SwitchOnce swap2 = (SwitchOnce) searchGObjectBySceneScriptIndex('d').get(0);
		Player playerA = (Player) searchGObjectBySceneScriptIndex('a').get(0);
		Player playerB = (Player) searchGObjectBySceneScriptIndex('b').get(0);
		SwitchManual switchFon = (SwitchManual) searchGObjectBySceneScriptIndex('e').get(0);

		fonUp = searchGObjectBySceneScriptIndex('1');
		fonDown = searchGObjectBySceneScriptIndex('2');

		for (Iterator<GObject> iterator = fonDown.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			obj.getFontaineComponent().setActive(false);
		}

		SwitchComponent switchComp = swap.getSwitchComponent();
		switchComp.setKey("swap");
		switchComp.setInfoOn("Swap Player");
		switchComp.setActOn("Swapped");
		switchComp = swap2.getSwitchComponent();
		switchComp.setKey("swap");
		switchComp.setInfoOn("Swap Player");
		switchComp.setActOn("Swapped");
		switchComp = switchFon.getSwitchComponent();
		switchComp.setKey("fontaine");

		setPlayer(playerA, playerB);
		swapAllowed = true;
		swapDuration = 0;
		help = addTextBox(1, 1, 23, 5, "A clone somewhere...", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
		setState(STATE_PAUSE_ALL);
		saveCheckPoint(true);
	}

	private void setPlayer(Player actPlayer, Player inactPlayer) {
		activePlayer = actPlayer;
		inactivePlayer = inactPlayer;

		ControlComponent control = activePlayer.getControlComponent();
		control.setActive(true);
		CameraComponent cam = activePlayer.getCameraComponent();
		cam.setActive(true);
		LaserComponent laser = activePlayer.getLaserComponent();
		laser.setActive(true);
		if (!activePlayer.hasTag("player")) {
			activePlayer.getTagList().add("player");
		}
		if (activePlayer.hasTag("cloneplayer")) {
			activePlayer.getTagList().remove("cloneplayer");
		}

		control = inactivePlayer.getControlComponent();
		control.setActive(false);
		cam = inactivePlayer.getCameraComponent();
		cam.setActive(false);
		laser = inactivePlayer.getLaserComponent();
		laser.setActive(false);
		if (inactivePlayer.hasTag("player")) {
			inactivePlayer.getTagList().remove("player");
		}
		if (!inactivePlayer.hasTag("cloneplayer")) {
			inactivePlayer.getTagList().add("cloneplayer");
		}

	}

	private void swapPlayer() {
		Player newact, newinact;
		newact = inactivePlayer;
		newinact = activePlayer;
		setPlayer(newact, newinact);
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
		// System.out.println("a" + state);

		if (sendkey.equals("mouse.click.enter") && script == SCRIPT_INIT) {
			RenderTextComponent render = help.getRenderTextComponent();
			if (render != null) {
				render.setActive(false);
			}
			setState(Scene.STATE_PLAY);
			script = SCRIPT_PLAY;
		}
		if (sendkey.equals("std.elsekill") && dest.hasTag("cloneplayer")) {
			help = addTextBox(1, 1, 23, 3, "Now you're alone again, good luck.", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, true, null, null);
			helpDuration = MAX_HELP_DURATION;
			swapAllowed = false;
		}

		if (sendkey.equals("swap.on") && swapAllowed == true && swapDuration == 0) {
			swapPlayer();
			swapDuration = MAX_SWAP_DURATION;
		}
		if (sendkey.equals("fontaine.on")) {
			swapFontaine(false);
		}
		if (sendkey.equals("fontaine.off")) {
			swapFontaine(true);
		}
		if (sendkey.startsWith("coin.empty")) {
			exitcomp.setOpen(true);
		}

	}

	private void swapFontaine(boolean set) {
		boolean reset = !set;

		for (Iterator<GObject> iterator = fonUp.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			obj.getFontaineComponent().setActive(set);
		}
		for (Iterator<GObject> iterator = fonDown.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			obj.getFontaineComponent().setActive(reset);
		}

	}

	@Override
	public boolean paint(GraphicsWrapper g) {
		// TODO Auto-generated method stub

		if (helpDuration > 0) {
			helpDuration--;
			if (helpDuration <= 0) {
				helpDuration = 0;
				if (help != null) {
					removeBufferList(help);
					help = null;
				}
			}
		}

		if (swapDuration > 0) {
			swapDuration--;
			if (swapDuration <= 0) {
				swapDuration = 0;
			}
		}
		return super.paint(g);
	}

}
