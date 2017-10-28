package com.ad.thepool.scenes;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.objects.SolidRock;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneWashing extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3859942259926056196L;
	TextBox help;
	TextBox left;
	TextBox right;
	TextBox up;
	TextBox down;

	public SceneWashing(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/washing.lvl", null);
		addMap(map);
		addMouseCursor();
		addMenu();
		addImageBox(0, 0, GObject.Z_BACKGROUND, 25, 15, "back_cave.png", RenderImageComponent.POS_PARALLAX_SLOW, false);

		setShuffleState(SHUFFLE_STATE_IN);

		setState(STATE_PAUSE_ALL);
		help = addTextBox(1, 1, 23, 5, "Stone Washed Controller...", GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_LEFT_UP, false, null, null);
		left = addTextBox(0,6,3,3,"Y", GraphicsWrapper.COLOR_RED,false,RenderTextComponent.POS_CENTER,true,null,"grav.left");
		left.getRenderTextComponent().setTopBox(false);
		right = addTextBox(22,6,3,3,"A", GraphicsWrapper.COLOR_RED,false,RenderTextComponent.POS_CENTER,true,null,"grav.right");
		right.getRenderTextComponent().setTopBox(false);
		up = addTextBox(11,0,3,3,"X", GraphicsWrapper.COLOR_RED,false,RenderTextComponent.POS_CENTER,true,null,"grav.up");
		up.getRenderTextComponent().setTopBox(false);
		down = addTextBox(11,12,3,3,"B", GraphicsWrapper.COLOR_RED,false,RenderTextComponent.POS_CENTER,true,null,"grav.down");
		down.getRenderTextComponent().setTopBox(false);
		//gravityAll(Component.LEFT);
		saveCheckPoint(true);
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		super.receiveMessage(source, dest, tag, sendkey);

		if (sendkey.equals("std.enter") && dest.hasTag("player")) {
			setNewScene(source);
		}
		if(sendkey.equals("grav.left"))
		{
			gravityAll(Component.LEFT);
		}
		if(sendkey.equals("grav.right"))
		{
			gravityAll(Component.RIGHT);
		}
		if(sendkey.equals("grav.up"))
		{
			gravityAll(Component.UP);
		}
		if(sendkey.equals("grav.down"))
		{
			gravityAll(Component.DOWN);
		}
		if (sendkey.equals("mouse.click.enter") && script == SCRIPT_INIT) {
			RenderTextComponent render = help.getRenderTextComponent();
			if (render != null) {
				render.setActive(false);
			}
			setState(Scene.STATE_PLAY);
		}
	}
	
	private void gravityAll(int direction)
	{
		ArrayList<GObject> rocks = searchGObjectByTileType('b');
		for (Iterator iterator = rocks.iterator(); iterator.hasNext();) {
			SolidRock rock = (SolidRock) iterator.next();
			rock.getPhysicsComponent().setGravity(direction);
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
