package com.ad.thepool.scenes;

import com.ad.thepool.GObject;
import com.ad.thepool.Game;
import com.ad.thepool.Map;
import com.ad.thepool.Scene;
import com.ad.thepool.components.PathMoveComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.WaypointComponent;
import com.ad.thepool.objects.Character;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.objects.Waypoint;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;

public class SceneOLDEdge extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1096646524163019830L;
	public static final int SCRIPT_WAY1 = 100;
	public static final int SCRIPT_DIALOG1 = 101;
	public static final int SCRIPT_WAY2 = 102;
	public static final int SCRIPT_DIALOG2 = 103;

	TextBox help;
	Character character;

	public SceneOLDEdge(int order, Game game) {
		super(order, game);
	}

	@Override
	public void init() {
		super.init();

		clearScene();
		Map map = new Map("maps/edge.lvl", "maps/edge.idx");
		addMap(map);
		addMouseCursor();
		addMenu();
		setState(STATE_PAUSE_ALL);
		setShuffleState(SHUFFLE_STATE_IN);

		Waypoint waypoint1 = (Waypoint) searchGObjectBySceneScriptIndex('a').get(0);
		Waypoint waypoint2 = (Waypoint) searchGObjectBySceneScriptIndex('b').get(0);
		Waypoint waypoint3 = (Waypoint) searchGObjectBySceneScriptIndex('c').get(0);

		WaypointComponent waycomp;
		waycomp = waypoint1.getWaypointComponent();
		waycomp.setOrder(0);
		waycomp.setTag("player");
		waycomp = waypoint2.getWaypointComponent();
		waycomp.setOrder(0);
		waycomp.setTag("character");
		waycomp = waypoint3.getWaypointComponent();
		waycomp.setOrder(1);
		waycomp.setTag("character");

		character = (Character) (searchGObjectByTag("character").get(0));
		PathMoveComponent pathMove = character.getPathMoveComponent();
		pathMove.getPath().clear();
		pathMove.addPath("character", 0, "left.down", false);
		pathMove.addPath("character", 1, "right.down", false);
		pathMove.reset();
		pathMove.setDoMove(false);

		help = addTextBox(1, 1, 23, 5, "The Edge", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
		saveCheckPoint(true);

	}

	@Override
	public void onTriggerEnter(GObject sourceobj, GObject triggerobj, int x, int y) {
		if (sourceobj.hasTag("player") && triggerobj.hasTag("waypoint")) {
			WaypointComponent waycomp = triggerobj.getWaypointComponent();
			if (waycomp.getOrder() == 0 && waycomp.getVisited() == 1) {
				TransformTileComponent trans = sourceobj.getTransformTileComponent();
				int highx = trans.getX();
				int highy = trans.getY();

				help = addTextBox(highx, highy, 1, 1, 10, 5, "Standing on the edge,\n\tthe world became so little,\n\tso little.", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
				script = SCRIPT_DIALOG1;
				setState(Scene.STATE_PAUSE_TAGS);
				getPauseTags().add("player");
			}
		}
		if (sourceobj.hasTag("character") && triggerobj.hasTag("waypoint")) {
			WaypointComponent waycomp = triggerobj.getWaypointComponent();
			if (waycomp.getOrder() == 0 && waycomp.getVisited() == 1) {
				TransformTileComponent trans = sourceobj.getTransformTileComponent();
				int highx = trans.getX();
				int highy = trans.getY();

				help = addTextBox(highx, highy, 1, 1, 10, 5, "May I follow you?", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_LEFT_UP, false, null, null);
				script = SCRIPT_DIALOG2;
				setState(Scene.STATE_PAUSE_TAGS);
				getPauseTags().add("character");
			}

		}
	}

	@Override
	public void keyDown(int key) {
		super.keyDown(key);
		if (script == SCRIPT_INIT && key == KeyEventWrapper.KEY_ENTER) {
			RenderTextComponent render = help.getRenderTextComponent();
			render.setActive(false);
			setState(Scene.STATE_PLAY);
		}
		if (script == SCRIPT_DIALOG1 && key == KeyEventWrapper.KEY_ENTER) {
			script = SCRIPT_PLAY;
			setState(Scene.STATE_PLAY);
			getPauseTags().clear();
			RenderTextComponent render = help.getRenderTextComponent();
			render.setActive(false);
			PathMoveComponent pathMove = character.getPathMoveComponent();
			pathMove.setDoMove(true);
		} else if (script == SCRIPT_DIALOG2 && key == KeyEventWrapper.KEY_ENTER) {
			script = SCRIPT_PLAY;
			setState(Scene.STATE_PLAY);
			RenderTextComponent render = help.getRenderTextComponent();
			render.setActive(false);
			getPauseTags().clear();
		}
	}

	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		super.receiveMessage(source, dest, tag, sendkey);

		if (sendkey.equals("std.enter") && dest.hasTag("player")) {
			setNewScene(source);
		}
		if (sendkey.equals("mouse.click.enter")) {
			if (script == SCRIPT_INIT) {
				RenderTextComponent render = help.getRenderTextComponent();
				render.setActive(false);
				setState(Scene.STATE_PLAY);
			}
			if (script == SCRIPT_DIALOG1) {
				script = SCRIPT_PLAY;
				setState(Scene.STATE_PLAY);
				getPauseTags().clear();
				RenderTextComponent render = help.getRenderTextComponent();
				render.setActive(false);
				PathMoveComponent pathMove = character.getPathMoveComponent();
				pathMove.setDoMove(true);
			} else if (script == SCRIPT_DIALOG2) {
				script = SCRIPT_PLAY;
				setState(Scene.STATE_PLAY);
				RenderTextComponent render = help.getRenderTextComponent();
				render.setActive(false);
				getPauseTags().clear();
			}
		}
	}

}
