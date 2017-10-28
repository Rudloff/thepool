package com.ad.thepool.scenes;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.CameraComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.objects.BlowyRandomRoundMovingTrap;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.Player;
import com.ad.thepool.objects.RandomRoundMovingTrap;
import com.ad.thepool.objects.SwitchWeightTimed;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class ScenePac extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7495176623771266348L;
	Exit exit;
	TextBox help;

	public ScenePac(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/pac.lvl", null);
//		Map mapw = new Map("maps/water_all.lvl", null);
		addMap(map);
//		addMap(mapw);
		addMouseCursor();
		addMenu();
		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 23, 5, "Back in 1983. Pac it!", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);

		ArrayList<GObject> traps = searchGObjectByTileType('v');
		for (Iterator<GObject> iterator = traps.iterator(); iterator.hasNext();) {
			RandomRoundMovingTrap trap = (RandomRoundMovingTrap) iterator.next();
			trap.getDestroyCollideComponent().setDamageEnergy(10);
		}
		ArrayList<GObject> switches = searchGObjectByTileType('W');
		for (Iterator<GObject> iterator = switches.iterator(); iterator.hasNext();) {
			SwitchWeightTimed swTimed = (SwitchWeightTimed) iterator.next();
			swTimed.getSwitchComponent().setKey("swap");
		}
		ArrayList<GObject> players = searchGObjectByTag("player");
		Player player = (Player) players.get(0);
		CameraComponent cam = player.getCameraComponent();
		cam.setType(CameraComponent.TYPE_FIX);
		player.getActivateComponent().setPermanentSwim(true);
		setShuffleState(SHUFFLE_STATE_IN);

		saveCheckPoint(true);

	}

	private void swapTraps(boolean toBlowy) {

		TransformTileComponent trans;
		int x, y;

		if (toBlowy == false) {
			ArrayList<GObject> traps = searchGObjectByTileType('V');

			for (Iterator<GObject> iterator = traps.iterator(); iterator.hasNext();) {
				BlowyRandomRoundMovingTrap oldTrap = (BlowyRandomRoundMovingTrap) iterator.next();
				trans = oldTrap.getTransformTileComponent();
				RandomRoundMovingTrap newTrap = (RandomRoundMovingTrap) cloneGObject('v');
				x = trans.getX();
				y = trans.getY();
				newTrap.getDestroyCollideComponent().setDamageEnergy(10);
				removeBufferList(oldTrap);
				trans = newTrap.getTransformTileComponent();
				trans.setXY(x, y, true);
				addBufferList(newTrap);
			}
		} else {
			ArrayList<GObject> traps = searchGObjectByTileType('v');

			for (Iterator<GObject> iterator = traps.iterator(); iterator.hasNext();) {
				RandomRoundMovingTrap oldTrap = (RandomRoundMovingTrap) iterator.next();
				trans = oldTrap.getTransformTileComponent();
				BlowyRandomRoundMovingTrap newTrap = (BlowyRandomRoundMovingTrap) cloneGObject('V');
				x = trans.getX();
				y = trans.getY();
				newTrap.getDestroyCollideComponent().setDamageEnergy(10);
				removeBufferList(oldTrap);
				trans = newTrap.getTransformTileComponent();
				trans.setXY(x, y, true);
				addBufferList(newTrap);
			}
		}
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		super.receiveMessage(source, dest, tag, sendkey);

		if (sendkey.startsWith("coin.empty") && source.hasTag("player")) {
			setNewScene(-1);
		}
		if(sendkey.equals("swap.on"))
		{
			swapTraps(true);
		}
		if(sendkey.equals("swap.off"))
		{
			swapTraps(false);
			removeBufferList(source);
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
