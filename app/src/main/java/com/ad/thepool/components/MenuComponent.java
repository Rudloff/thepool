package com.ad.thepool.components;

import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.GObject;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;

public class MenuComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5360341386653321940L;
	public static final int COMP_NAME = 41;
	public static final int Y_LINE = 13;
	public static final int X_MENU_START = 4;
	public static final int X_MENU_HIDE = 0;
	public static final int ITEM_WIDTH = 3;
	public static final int ITEM_SPACE = 0;
	public static final int ITEM_HEIGHT = 2;
	public static final int MENU_WIDTH = 3;
	

	public boolean show;
	public boolean doHide;
	public String quality;
	public String qualID;
	public String qualBase;
	public String sound;
	public String soundID;
	public String soundBase;
	public String music;
	public String musicID;
	public String musicBase;

	public MenuComponent(boolean isActive) {
		super(COMP_NAME, isActive);
		show = false;
		doHide = false;
	}

	public void initMenu(String[] id, String[] vals, String quality, String qualID, String sound, String soundID, String music, String musicID) {
		int xpos = X_MENU_START;
		TextBox actBox;

		actBox = gobject.getScene().addTextBox(X_MENU_HIDE, Y_LINE, MENU_WIDTH, ITEM_HEIGHT, "Menu", GraphicsWrapper.COLOR_BLACK, true, RenderTextComponent.POS_CENTER, true, null, "control.menu");
		RenderTextComponent render = actBox.getRenderTextComponent();
		render.setColor(GraphicsWrapper.COLOR_WHITE);
		render.setSpeed(1);
		render.setTopBox(false);
		String val;

		for (int i = 0; i < vals.length; i++) {
			val = vals[i];
			if (qualID.equals(id[i])) {
				qualBase = val;
				val += "\n" + quality;
			}
			if (soundID.equals(id[i])) {
				soundBase = val;
				val += "\n" + sound;
			}
			if (musicID.equals(id[i])) {
				musicBase = val;
				val += "\n" + music;
			}
			actBox = gobject.getScene().addTextBox(xpos, Y_LINE, ITEM_WIDTH, ITEM_HEIGHT, val, GraphicsWrapper.COLOR_WHITE, false, RenderTextComponent.POS_CENTER, true, "menuitem", id[i]);
			render = actBox.getRenderTextComponent();
			render.setActive(show);
			render.setSpeed(1);
			render.setTopBox(false);
			xpos = xpos + ITEM_WIDTH + ITEM_SPACE;
		}
		this.quality = quality;
		this.qualID = qualID;
		this.musicID = musicID;
		this.soundID = soundID;
	}

	@Override
	public boolean mouseUp(int x, int y) {
		if (doHide == true) {
			setShow(false);
		}
		doHide = false;
		return true;

	}

	public void setQuality(String quality) {
		ArrayList<GObject> menuItems = gobject.getScene().searchGObjectByTag("menuitem");
		String val;
		for (Iterator<GObject> iterator = menuItems.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			RenderTextComponent render = obj.getRenderTextComponent();
			if (qualID.equals(render.getSendkey())) {
				val = qualBase + "\n" + quality;
				render.setText(val);
				render.setAnimCharPos(0);
			}
		}

		this.quality = quality;
	}
	
	public void setSound(String sound) {
		ArrayList<GObject> menuItems = gobject.getScene().searchGObjectByTag("menuitem");
		String val;
		for (Iterator<GObject> iterator = menuItems.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			RenderTextComponent render = obj.getRenderTextComponent();
			if (soundID.equals(render.getSendkey())) {
				val = soundBase + "\n" + sound;
				render.setText(val);
				render.setAnimCharPos(0);
			}
		}
		this.sound = sound;
	}

	public void setMusic(String music) {
		ArrayList<GObject> menuItems = gobject.getScene().searchGObjectByTag("menuitem");
		String val;
		for (Iterator<GObject> iterator = menuItems.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			RenderTextComponent render = obj.getRenderTextComponent();
			if (musicID.equals(render.getSendkey())) {
				val = musicBase + "\n" + music;
				render.setText(val);
				render.setAnimCharPos(0);
			}
		}
		this.music = music;
	}


	@Override
	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) {
		// TODO Auto-generated method stub
		if (sendkey.equals("control.menu")) {
			if (show == true) {
				doHide = true;
			} else {
				setShow(true);
			}
		} else if (sendkey.startsWith("quality.")) {
			// setShow(true);
			setQuality(gobject.getScene().getGame().getQualityText());
		}
		else if (sendkey.startsWith("sound.")) {
			// setShow(true);
			setSound(gobject.getScene().getGame().getBooleanText(gobject.getScene().getGame().isSound()));
		}
		else if (sendkey.startsWith("music.")) {
			// setShow(true);
			setMusic(gobject.getScene().getGame().getBooleanText(gobject.getScene().getGame().isMusic()));
		}
		else if (sendkey.startsWith("control.") && !sendkey.equals("control.quality") && !sendkey.equals("control.sound") && !sendkey.equals("control.music") ) {
			doHide = true;
		}
	}

	public void setShow(boolean show) {
		this.show = show;
		ArrayList<GObject> menuItems = gobject.getScene().searchGObjectByTag("menuitem");

		for (Iterator<GObject> iterator = menuItems.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			RenderTextComponent render = obj.getRenderTextComponent();
			render.setActive(show);
			render.setAnimCharPos(0);
		}

	}

}
