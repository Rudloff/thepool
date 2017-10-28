package com.ad.thepool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.ad.thepool.components.Component;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.MenuComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.objects.ExplodeRound;
import com.ad.thepool.objects.ImageBox;
import com.ad.thepool.objects.Menu;
import com.ad.thepool.objects.MouseCursor;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.ImageWrapper;
import com.ad.thepool.wrapper.KeyEventWrapper;
import com.ad.thepool.wrapper.Log;

public class Scene implements Serializable {

	private static final long serialVersionUID = -6580248967783102444L;
	protected int id;
	private ArrayList<GObject> objectList;
	protected ArrayList<GObject> bufferList;
	private ArrayList<GObject> shuffleList;
	private ArrayList<Gizmo> gizmoList;
	private transient Queue<ControlEvent> controlQueue;
	
	public static final int DIMX = 25;
	public static final int DIMY = 15;
	public static final int DIMZ = 8;
	public static final int LIGHT_DIMX = DIMX;
	public static final int LIGHT_DIMY = DIMY;
	public static final int LIGHT_RASTER = Game.raster;

	public static final int ID_TEXTBOX = 1000;
	public static final int ID_IMAGEBOX = 1001;
	public static final int ID_MOUSECURSOR = 1002;
	public static final int ID_MENU = 1003;
	public static final int STATE_PLAY = 0;
	public static final int STATE_DO_NOTHING = 1;
	public static final int STATE_PAUSE_TAGS = 2;
	public static final int STATE_PAUSE_ALL = 3;
	public static final int SHUFFLE_STATE_IN = 1;
	public static final int SHUFFLE_STATE_OUT = 2;
	public static final int SHUFFLE_STATE_OFF = 0;

	public static final int DURATION_KILL = 50;
	public static final int DURATION_SCENE_SETUP = 50;

	public static final int SCRIPT_INIT = 0;
	public static final int SCRIPT_PLAY = 1;

	protected int script;

	public ArrayList<String> pauseTags;

	private int frame;
	private int shufflePos;
	protected Game game;
	private int newSceneId;
	protected int state, oldState, shuffleState;

	public double parSlowRatioX, parSlowRatioY, parFastRatioX, parFastRatioY;
	
	int centerx, centery;
	int lastCenterx, lastCentery;
	double curx, cury;
	double parSlowCurx, parSlowCury;
	double parFastCurx, parFastCury;
	public int modx, mody;
	public int parSlowModx, parSlowMody;
	public int parFastModx, parFastMody;
	Vector2D dir;

	private long counter;
	private transient GraphicsWrapper graphicsWrapper;
	private boolean moveTextbox;

	private int killDuration;
	private int killTimer;

	private int shuffleTimer;

	private TextBox pauseText;

	protected boolean restore, save;

	private boolean newSceneAfterKill;

	public transient int lightmap[][] = new int[LIGHT_DIMY][LIGHT_DIMX];
	private transient ArrayList<Light> lightList = new ArrayList<Light>();

	private int lightBackColor;
	private float brightnessDarkest;
	private float brightnessScale;

	private int posCache[][][] = new int[DIMZ][DIMY][DIMX];

	private static final String[] menu_val = new String[7];
	private static final String[] menu_id = new String[7];

	boolean buttonPress = false;

	protected boolean showPause = true;

	protected String tempSavefile;

	private transient GObjectComparator objComp = new GObjectComparator();

	private static int[] pos = new int[2];

	private int[] dist = new int[9];

	private transient Vector2D[] camvecs = new Vector2D[9];

	private boolean switchQuality = false;

	static {
		menu_val[0] = "Main\nMenu";
		menu_val[1] = "Rerun\nLevel";
		menu_val[2] = "Check\nPoint";
		menu_val[3] = "Qual";
		menu_val[4] = "Pause";
		menu_val[5] = "Sound";
		menu_val[6] = "Music";
		
		menu_id[0] = "control.main";
		menu_id[1] = "control.restart";
		menu_id[2] = "control.checkpoint";
		menu_id[3] = "control.quality";
		menu_id[4] = "control.pause";
		menu_id[5] = "control.sound";
		menu_id[6] = "control.music";
	}

	public Scene(int id, Game game) {
		this.id = id;
		objectList = new ArrayList<GObject>();
		bufferList = new ArrayList<GObject>();
		shuffleList = new ArrayList<GObject>();
		gizmoList = new ArrayList<Gizmo>();
		pauseTags = new ArrayList<String>();
		controlQueue = new ArrayBlockingQueue<ControlEvent>(16);
		
		counter = 0;
		frame = 0;
		newSceneId = -1;
		state = STATE_PLAY;
		oldState = -1;
		script = SCRIPT_INIT;
		centerx = DIMX * Game.raster / 2;
		centery = DIMY * Game.raster / 2;
		lastCenterx = centerx;
		lastCentery = centery;
		curx = centerx;
		cury = centery;
		dir = new Vector2D(0, 0);
		moveTextbox = true;
		killDuration = 0;
		killTimer = 0;
		save = false;
		restore = false;
		newSceneAfterKill = true;
		lightBackColor = GraphicsWrapper.getRGB(0, 0, 0, 64);
		brightnessDarkest = 0.8F;
		brightnessScale = 1.5F;
		parSlowRatioX = 0.5;
		parSlowRatioY =0.5;
		parFastRatioX = 1.5;
		parFastRatioY = 1.5;
		lightmap = new int[LIGHT_DIMY][LIGHT_DIMX];
		lightList = new ArrayList<Light>();
		clearLightMap();
		this.game = game;
	}

	public void init() {
		setShuffleState(SHUFFLE_STATE_IN);
		game.getSound("shuffle").play();
		counter = 0;
		frame = 0;
		script = SCRIPT_INIT;
		killDuration = 0;
		killTimer = 0;
		save = false;
		restore = false;
		lightmap = new int[LIGHT_DIMY][LIGHT_DIMX];
		lightList = new ArrayList<Light>();
		camvecs = new Vector2D[9];
		controlQueue = new ArrayBlockingQueue<ControlEvent>(16);
		for (int i = 0; i < camvecs.length; i++) {
			camvecs[i] = new Vector2D();
		}
		tempSavefile = null;
		clearLightMap();
	}

	public MouseCursor addMouseCursor() {
		MouseCursor prefabObj = (MouseCursor) searchComponentPrefab(ID_MOUSECURSOR);
		MouseCursor newObj = null;
		try {
			newObj = (MouseCursor) prefabObj.clone();
			newObj.setScene(this);
			addBufferList(newObj);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newObj;
	}

	public Menu addMenu() {
		Menu prefabObj = (Menu) searchComponentPrefab(ID_MENU);
		Menu newObj = null;
		try {
			newObj = (Menu) prefabObj.clone();
			newObj.setScene(this);
			MenuComponent comp = newObj.getMenuComponent();
			comp.initMenu(menu_id, menu_val, game.getQualityText(), "control.quality",game.getBooleanText(game.isSound()),"control.sound", game.getBooleanText(game.isMusic()),"control.music");
			addBufferList(newObj);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newObj;
	}

	public void addMap(Map map) {
		int i, j;
		GObject prefabObj;
		GObject newObj;
		TransformTileComponent tileComp;

		if (map != null) {
			char matrix[][] = map.getMatrix();
			char index[][] = map.getIndex();
			for (i = 0; i < matrix.length; i++) {
				for (j = 0; j < (matrix[0]).length; j++) {
					char tileChar = matrix[i][j];
					char tagChar = index[i][j];
					prefabObj = searchTileComponentPrefab(tileChar);
					if (prefabObj != null) {
						try {
							newObj = (GObject) prefabObj.clone();
							newObj.setScene(this);
							newObj.setSceneScriptIndex(tagChar);
							tileComp = newObj.getTransformTileComponent();
							tileComp.setXY(j, i, false);
							addBufferList(newObj);
						} catch (CloneNotSupportedException e) {
						}
					}
				}
			}
		}
	}

	public TextBox addTextBox(int xpos, int ypos, int width, int height, String text, int fontColor, boolean animate, int position, boolean background, String setTag, String sendkey) {
		TextBox prefabObj;
		TextBox newObj;

		prefabObj = (TextBox) searchComponentPrefab(ID_TEXTBOX);
		if (prefabObj != null) {
			try {
				newObj = (TextBox) prefabObj.clone();
				newObj.setScene(this);
				if (setTag != null) {
					newObj.getTagList().add(setTag);
				}
				RenderTextComponent rendertext = newObj.getRenderTextComponent();
				TransformTileComponent trans = newObj.getTransformTileComponent();
				trans.setXY(xpos, ypos, false);
				rendertext.setWidth(width);
				rendertext.setHeight(height);
				rendertext.setText(text);
				rendertext.setFontColor(fontColor);
				rendertext.setAnimate(animate);
				rendertext.setPosition(position);
				rendertext.setBackground(background);
				rendertext.setSendkey(sendkey);
				trans.setZ(GObject.Z_FOREGROUND, false);
				addBufferList(newObj);

				return newObj;
			} catch (CloneNotSupportedException e) {
			}

		}
		return null;
	}

	public TextBox addTextBox(int highx, int highy, int xpos, int ypos, int width, int height, String text, int fontColor, boolean animate, int position, boolean background, String setTag, String sendkey) {
		if (moveTextbox == true) {
			if (highx >= xpos && highx <= xpos + width && highy >= ypos && highy <= ypos + height) {
				ypos = DIMY - (height + ypos);
			}
		}

		return addTextBox(xpos, ypos, width, height, text, fontColor, animate, position, background, setTag, sendkey);
	}

	public ImageBox addImageBox(int xpos, int ypos, int zpos, int width, int height, String filename, int position, boolean background) {
		ImageBox prefabObj;
		ImageBox newObj;

		prefabObj = (ImageBox) searchComponentPrefab(ID_IMAGEBOX);
		if (prefabObj != null) {
			try {
				newObj = (ImageBox) prefabObj.clone();
				newObj.setScene(this);
				RenderImageComponent renderimage = newObj.getRenderImageComponent();
				TransformTileComponent trans = newObj.getTransformTileComponent();
				trans.setXY(xpos, ypos, false);
				trans.setZ(zpos, false);
				renderimage.setWidth(width);
				renderimage.setHeight(height);
				renderimage.setFilename(filename);
				renderimage.setPosition(position);
				renderimage.setBackground(background);
				addBufferList(newObj);

				return newObj;
			} catch (CloneNotSupportedException e) {
			}

		}
		return null;
	}

	private void drawWrapImage(GraphicsWrapper g, ImageWrapper img, int x, int y) {
		int screenx = Scene.DIMX * Game.raster;
		int screeny = Scene.DIMY * Game.raster;

		g.drawImage(img, x, y, 0);
		g.drawImage(img, x - screenx, y, 0);
		g.drawImage(img, x, y - screeny, 0);
		g.drawImage(img, x - screenx, y - screeny, 0);

	}

	public void clearScene() {
		objectList.clear();
		if (bufferList == null) {
			bufferList = new ArrayList<GObject>();
		}
		clearBufferList();
		gizmoList.clear();
		counter = 0;
		frame = 0;
	}

	public GObject cloneGObject(char tileChar) {
		GObject prefabObj;
		GObject newObj = null;
		prefabObj = searchTileComponentPrefab(tileChar);
		if (prefabObj != null) {
			try {
				newObj = (GObject) prefabObj.clone();
				newObj.setScene(this);
			} catch (CloneNotSupportedException e) {
			}
		}
		return newObj;
	}

	public void addGObject(GObject obj) {
		addBufferList(obj);
	}

	private void sortObjectListByZAxis() {
		Collections.sort(objectList, objComp);
	}

	public GObject searchTileComponentPrefab(char tileChar) {
		ArrayList<GObject> repos = game.getPrefab().getRepository();
		GObject obj = null;
		boolean found = false;

		for (Iterator<GObject> iterator = repos.iterator(); iterator.hasNext();) {
			obj = iterator.next();
			TransformTileComponent comp = obj.getTransformTileComponent();
			if (comp != null && comp.getLevTile() == tileChar) {
				found = true;
				break;
			}

		}
		if (found == true) {
			return obj;
		} else {
			return null;
		}
	}

	public GObject searchComponentPrefab(int id) {
		ArrayList<GObject> repos = game.getPrefab().getRepository();
		GObject obj = null;
		boolean found = false;

		for (Iterator<GObject> iterator = repos.iterator(); iterator.hasNext();) {
			obj = iterator.next();
			if (id == obj.getId()) {
				found = true;
				break;
			}

		}
		if (found == true) {
			return obj;
		} else {
			return null;
		}
	}


	public GObject searchGObjectByPosition(int x, int y, int z) {
		int val = posCache[z][y][x];
		if (val != -1) {
			return bufferList.get(val);
		} else {
			return null;
		}

	}

	public ArrayList<GObject> searchGObjectByPosition(int x, int y) {
		ArrayList<GObject> foundObj = new ArrayList<GObject>();

		for (Iterator<GObject> iterator = bufferList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			TransformTileComponent comp = obj.getTransformTileComponent();
			if (x == comp.getX() && y == comp.getY()) {
				foundObj.add(obj);
			}
		}
		return foundObj;
	}

	public ArrayList<GObject> searchGObjectByTileType(char levTile) {
		ArrayList<GObject> foundObj = new ArrayList<GObject>();

		for (Iterator<GObject> iterator = bufferList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			TransformTileComponent comp = obj.getTransformTileComponent();
			if (comp != null && levTile == comp.getLevTile()) {
				foundObj.add(obj);
			}
		}
		return foundObj;
	}

	public ArrayList<GObject> searchGObjectByTag(String tag) {
		ArrayList<GObject> foundObj = new ArrayList<GObject>();

		for (Iterator<GObject> iterator = bufferList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			if (obj.hasTag(tag) == true) {
				foundObj.add(obj);
			}
		}
		return foundObj;
	}

	public ArrayList<GObject> searchGObjectBySceneScriptIndex(char index) {
		ArrayList<GObject> foundObj = new ArrayList<GObject>();

		for (Iterator<GObject> iterator = bufferList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			if (obj.getSceneScriptIndex() == index) {
				foundObj.add(obj);
			}
		}
		return foundObj;
	}

	public static int[] calcPosition(int startx, int starty, int offsetx, int offsety) {
		int destx;
		int desty;

		destx = startx + offsetx;
		desty = starty + offsety;

		if (destx < 0) {
			pos[0] = DIMX + (destx % DIMX);
		} else {
			pos[0] = (startx + offsetx) % DIMX;
		}

		if (desty < 0) {
			pos[1] = DIMY + (desty % DIMY);
		} else {
			pos[1] = (starty + offsety) % DIMY;
		}

		return pos;
	}

	public static int calcPositionX(int startx, int offsetx) {
		int destx;
		int retx;

		destx = startx + offsetx;

		if (destx < 0) {
			retx = DIMX + (destx % DIMX);
		} else {
			retx = (startx + offsetx) % DIMX;
		}

		return retx;
	}

	public static int calcPositionY(int starty, int offsety) {
		int desty;
		int rety;

		desty = starty + offsety;

		if (desty < 0) {
			rety = DIMY + (desty % DIMY);
		} else {
			rety = (starty + offsety) % DIMY;
		}

		return rety;
	}

	public int calcDistance(GObject source, GObject dest) {
		TransformTileComponent transSource = source.getTransformTileComponent();
		int sourcex = transSource.getX();
		int sourcey = transSource.getY();
		TransformTileComponent transDest = dest.getTransformTileComponent();
		int destx = transDest.getX();
		int desty = transDest.getY();
		return calcDistance(sourcex * Game.raster + Game.raster / 2, sourcey * Game.raster + Game.raster / 2, destx * Game.raster + Game.raster / 2, desty * Game.raster + Game.raster / 2);
	}

	// as squared distance
	private int calcDistance(int sourcex, int sourcey, int destx, int desty) {
		int c = 0;

		int len;
		int offsetx, offsety;

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				offsetx = DIMX * Game.raster * i;
				offsety = DIMY * Game.raster * j;

				len = ((sourcex + offsetx - destx) * (sourcex + offsetx - destx) + (sourcey + offsety - desty) * (sourcey + offsety - desty));

				dist[c] = len;
				c++;
			}
		}

		int minLen = Integer.MAX_VALUE;

		for (int i = 0; i < dist.length; i++) {
			if (dist[i] < minLen) {
				minLen = dist[i];
			}
		}

		return minLen;

	}

	public static double sqrt(final double a) {
		final long x = Double.doubleToLongBits(a) >> 32;
		double y = Double.longBitsToDouble((x + 1072632448) << 31);
		return y;
	}

	public void calcCurrentCamera() {

		int offsetx, offsety;
		int k = 0;

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				offsetx = DIMX * Game.raster * i;
				offsety = DIMY * Game.raster * j;
				// Vector2D vec = new Vector2D(centerx + offsetx - curx, centery
				// + offsety - cury);
				camvecs[k].setVector(centerx + offsetx - curx, centery + offsety - cury);
				k++;
			}
		}

		double minLen = Integer.MAX_VALUE;

		for (int i = 0; i < camvecs.length; i++) {
			if (camvecs[i].getLen() < minLen) {
				dir = camvecs[i];
				minLen = camvecs[i].getLen();

			}
		}

		double speed = getCameraSpeed() * Game.raster / 16 * dir.getLen();

		if (Math.abs(curx - centerx) > speed || Math.abs(cury - centery) > speed) {
			curx = curx + (dir.getNormX() * speed);
			cury = cury + (dir.getNormY() * speed);
			parSlowCurx = parSlowCurx + (dir.getNormX() * speed * parSlowRatioX);
			parSlowCury = parSlowCury + (dir.getNormY() * speed * parSlowRatioY);
			parFastCurx = parFastCurx + (dir.getNormX() * speed * parFastRatioX);
			parFastCury = parFastCury + (dir.getNormY() * speed * parFastRatioY);
			if (curx < 0) {
				curx = DIMX * Game.raster + curx;
			}
			if (cury < 0) {
				cury = DIMY * Game.raster + cury;
			}
			if (curx > DIMX * Game.raster) {
				curx = -DIMX * Game.raster + curx;
			}
			if (cury > DIMY * Game.raster) {
				cury = -DIMY * Game.raster + cury;
			}
			// par
			if (parSlowCurx < 0) {
				parSlowCurx = DIMX * Game.raster + parSlowCurx;
			}
			if (parSlowCury < 0) {
				parSlowCury = DIMY * Game.raster + parSlowCury;
			}
			if (parSlowCurx > DIMX * Game.raster) {
				parSlowCurx = -DIMX * Game.raster + parSlowCurx;
			}
			if (parSlowCury > DIMY * Game.raster) {
				parSlowCury = -DIMY * Game.raster + parSlowCury;
			}
			// par fast
			if (parFastCurx < 0) {
				parFastCurx = DIMX * Game.raster + parFastCurx;
			}
			if (parFastCury < 0) {
				parFastCury = DIMY * Game.raster + parFastCury;
			}
			if (parFastCurx > DIMX * Game.raster) {
				parFastCurx = -DIMX * Game.raster + parFastCurx;
			}
			if (parFastCury > DIMY * Game.raster) {
				parFastCury = -DIMY * Game.raster + parFastCury;
			}
			
		}
	}

	private double getCameraSpeed() 
	{
		return 0.3;
	}
//		switch (game.getQuality()) {
//		case Game.QUAL_LOW:
//			return 0.3;
//		case Game.QUAL_MED:
//			return 0.3;
//		case Game.QUAL_HIGH:
//			return 0.1;
//		default:
//			return 0.1;
//		}
//	}

	public static boolean isFramePosition(int destx, int desty) {

		if (destx <= 0 || destx >= DIMX - 1 || desty <= 0 || desty >= DIMY - 1) {
			return true;
		} else {
			return false;
		}
	}

	public ImageWrapper createImage() {
		ImageWrapper image = new ImageWrapper(DIMX * Game.raster, DIMY * Game.raster, false);
		GraphicsWrapper g = image.createGraphics();
		if (shuffleState == SHUFFLE_STATE_IN) 
		{
			if(shufflePos == 0)
			{
				createShuffleList();
			}
			List<GObject> shortShuffleList = shuffleList.subList(0, shufflePos);

			paintFull(g, shortShuffleList);
		} else if (shuffleState == SHUFFLE_STATE_OUT) 
		{
			if(shufflePos == 0)
			{
				createShuffleList();
			}
			List<GObject> shortShuffleList = shuffleList.subList(0, shuffleList.size() - shufflePos);

			paintFull(g, shortShuffleList);
		} else {
			paintFull(g, objectList);
		}
		return image;
	}

	public boolean paint(GraphicsWrapper g) {
		if (shuffleState != SHUFFLE_STATE_OFF) {

			shuffleTimer++;
			float shuffleStep = (float) shuffleList.size() / (float) DURATION_SCENE_SETUP;
			shufflePos = (int) (shuffleStep * shuffleTimer);
			if (shuffleTimer > DURATION_SCENE_SETUP) {
				shuffleTimer = 0;
				setShuffleState(SHUFFLE_STATE_OFF);
			}
		}

		if (killDuration > 0 && newSceneAfterKill == true) {
			killTimer++;
			if (killTimer > killDuration) {
				killTimer = 0;
				killDuration = 0;

				restore = true;

			}
		}
		frame = (int) counter % getAnimSeq();
		counter++;

		if (frame == 0) {
			objectList.clear();
			objectList.addAll(bufferList);
			sortObjectListByZAxis();
			replaceBufferList(objectList);
		}

		// paint Background

		paintBackForeground(g, GObject.Z_BACKGROUND);

		// Paint Moving planes -2 to 2
		ImageWrapper image = createImage();
		calcCurrentCamera();
		g.setClip(0, 0, DIMX * Game.raster, DIMY * Game.raster);

		// g.drawImage(image, 0, 0, null, null);
		modx = ((DIMX * Game.raster) / 2 - (int) curx) % (DIMX * Game.raster);
		mody = ((DIMY * Game.raster) / 2 - (int) cury) % (DIMY * Game.raster);
		parSlowModx = ((DIMX * Game.raster ) - (int) parSlowCurx) % (DIMX * Game.raster);
		parSlowMody = ((DIMY * Game.raster ) - (int) parSlowCury) % (DIMY * Game.raster);
		parFastModx = ((DIMX * Game.raster ) - (int) parFastCurx) % (DIMX * Game.raster);
		parFastMody = ((DIMY * Game.raster ) - (int) parFastCury) % (DIMY * Game.raster);

		
		
		if (modx < 0) {
			modx = modx + (DIMX * Game.raster);
		}
		if (mody < 0) {
			mody = mody + (DIMY * Game.raster);
		}
		if (parSlowModx < 0) {
			parSlowModx = parSlowModx + (DIMX * Game.raster);
		}
		if (parSlowMody < 0) {
			parSlowMody = parSlowMody + (DIMY * Game.raster);
		}
		if (parFastModx < 0) {
			parFastModx = parFastModx + (DIMX * Game.raster);
		}
		if (parFastMody < 0) {
			parFastMody = parFastMody + (DIMY * Game.raster);
		}

//		Log.d("a:" + modx + " " + mody);
//		Log.d("b:" + parModx + " " + parMody);

		drawWrapImage(g, image, modx, mody);

		// paint Foreground

		paintBackForeground(g, GObject.Z_FOREGROUND);

		switchScene();
		
		executeControlQueue();
		

		if (save == true) {
			saveCheckPoint(false);
			save = false;
		}
		if (restore == true) {
			// restoreCheckPoint();
			restore = false;
			return true;
		}
		if (switchQuality == true) {
			switchQuality();
			switchQuality = false;
		}
		return false;
	}

	protected void saveCheckPoint(boolean start) {
		for (Iterator<GObject> iterator = objectList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
				Component comp = iterator2.next();
				if (comp.isActive() == true) {
					comp.initRestore();
				}
			}
		}
		if (tempSavefile == null) 
		{
			if(start == false)
			{
				game.getFrame().saveGame(game, Game.SAVE_FILE_NAME, Game.INFO_SAVE_FILE_NAME);
			}
			else
			{
				game.getFrame().saveGame(game, null, Game.INFO_SAVE_FILE_NAME);
			}
		} 
		else 
		{
			game.getFrame().saveGame(game, tempSavefile, null);
		}
	}

	private void createShuffleList() {
		ArrayList<GObject> tempList = new ArrayList<GObject>();
		for (Iterator<GObject> iterator = objectList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			TransformTileComponent trans = obj.getTransformTileComponent();
			if (trans.getZ() == GObject.Z_MAIN || trans.getZ() == GObject.Z_BACK || trans.getZ() == GObject.Z_FRONT) {
				tempList.add(obj);
			}
		}
		Collections.shuffle(tempList);
		shuffleList.clear();
		shuffleList.addAll(tempList);
	}

	public void paintBackForeground(GraphicsWrapper g, int level) {

		if (state == STATE_DO_NOTHING) {
			return;
		}

		for (Iterator<GObject> iterator = objectList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			TransformTileComponent tileComp = obj.getTransformTileComponent();
			if (tileComp != null && tileComp.getZ() == level) {
				for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
					Component comp = iterator2.next();
					{
						if (comp.isActive() == true) 
						{
							comp.paintOverlay(g);
						}
//							if (game.getQuality() > Game.QUAL_MED) {
//								comp.paintOverlay(g);
//							} else if (!(comp instanceof RenderImageComponent)) {
//								comp.paintOverlay(g);
//							}
//						}
					}
				}
			}
		}
	}

	public void paintFull(GraphicsWrapper g, List<GObject> objList) {
		graphicsWrapper = g;
		int lastZ = GObject.Z_BACKGROUND;
		if (objList.size() > 0) {
			lastZ = objList.get(0).getTransformTileComponent().getZ();
		}
		LightComponent light;
		TransformTileComponent trans;
		RenderTileComponent render;

		lightList.clear();

		for (Iterator<GObject> iterator = objList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();

			for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
				Component comp = iterator2.next();
				if (comp.isActive() == true) {
					if (frame == 0) {
						comp.initFrame();
						if (state != STATE_PAUSE_ALL && (state == STATE_PLAY || !(state == STATE_PAUSE_TAGS && isPauseTag(obj.getTagList())))) {
							comp.update();
						}
					}
					if (state != STATE_DO_NOTHING) {
						comp.paint(g);
					}
				}
			}
			TransformTileComponent tileComp = obj.getTransformTileComponent();

			if (state != STATE_DO_NOTHING) {
				if (tileComp.getZ() != lastZ) {
					for (Iterator<GObject> iteratorI = objList.iterator(); iteratorI.hasNext();) {
						GObject obj2 = iteratorI.next();
						TransformTileComponent tileComp2 = obj2.getTransformTileComponent();
						if (tileComp2.getZ() == lastZ && lastZ != GObject.Z_BACKGROUND && lastZ != GObject.Z_FOREGROUND) {
							for (Iterator<Component> iteratorI2 = obj2.getComponentList().iterator(); iteratorI2.hasNext();) {
								Component comp = iteratorI2.next();
								if (comp.isActive() == true) {
									comp.paintOverlay(g);
								}
							}
						}
					}
					lastZ = tileComp.getZ();
				}
			}

			if (game.getQuality() > Game.QUAL_LOW) {
				light = obj.getLightComponent();
				if (light != null) {
					trans = obj.getTransformTileComponent();
					render = obj.getRenderTileComponent();
					if (render != null) {
						lightList.add(new Light(light.getIntensity() * Game.raster, light.getColor(), render.getCenterx(), render.getCentery()));
					} else if (trans != null) {
						lightList.add(new Light(light.getIntensity() * Game.raster, light.getColor(), trans.getX() * Game.raster + (Game.raster / 2), trans.getY() * Game.raster + (Game.raster / 2)));
					}
				}
			}
		}
		// drawGizmos();
		// clearLightMap();
		if (game.getQuality() > Game.QUAL_LOW) {
			clearLightMap();
			createLightMap();
		}
		// paintLightMap(g);
	}

	public void sendMessage(GObject source, GObject dest, String tag, String key) {
		for (Iterator<GObject> iterator = objectList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
				Component comp = iterator2.next();
				// if (comp.isActive() == true) {
				comp.receiveMessage(source, dest, tag, key);
				// }
			}
		}
		receiveMessage(source, dest, tag, key);
	}

	public void receiveMessage(GObject source, GObject dest, String tag, String sendkey) 
	{
		Log.d("Message: Source: " + source + " Dest: " + dest + " Tag: " + tag + " Key: " + sendkey);
		if (sendkey.equals("std.kill") && dest.hasTag("player")) {
			killDuration = DURATION_KILL;
			ExplodeRound newExplode = (ExplodeRound) cloneGObject('G');
			TransformTileComponent transComp = newExplode.getTransformTileComponent();
			TransformTileComponent transCompPlay = dest.getTransformTileComponent();
			transComp.setXY(transCompPlay.getX(), transCompPlay.getY(), false);
			removeBufferList(dest);
			addBufferList(newExplode);
		}
		if (sendkey.equals("checkpoint.on") && getShuffleState() == SHUFFLE_STATE_OFF) {
			save = true;
		}

		else if (sendkey.equals("control.main")) {
			setNewScene(0);
		} else if (sendkey.equals("control.restart")) {
			setNewScene(id);
		} else if (sendkey.equals("control.checkpoint")) {
			lastCheckpoint();
		} else if (sendkey.equals("control.pause")) {
			pause();
		} else if (sendkey.equals("control.unpause")) {
			restoreState();
		} else if (sendkey.equals("control.quality")) {
			switchQuality = true;
		}
		else if (sendkey.equals("control.sound")) 
		{
			if(game.isSound())
				game.setSound(false);
			else
				game.setSound(true);
		}
		else if (sendkey.equals("control.music")) {
			if(game.isMusic())
				game.setMusic(false);
			else
				game.setMusic(true);
		}
	}

	private void switchQuality() {
		switch (game.getQuality()) {
		case Game.QUAL_LOW:
			game.setQuality(Game.QUAL_MED);
			break;
		case Game.QUAL_MED:
			game.setQuality(Game.QUAL_HIGH);
			break;
		case Game.QUAL_HIGH:
			game.setQuality(Game.QUAL_LOW);
			clearLightMap();
			break;
		default:
			break;
		}
	}

	public void setCamera(int x, int y, boolean followX, boolean followY) {
		moveTextbox = false;
		lastCentery = centery;
		lastCenterx = centery;
		// Testing
		// followX = false; followY = false;
		if (followX == false) {
			centerx = DIMX * Game.raster / 2;
		} else {
			centerx = x;
		}
		if (followY == false) {
			centery = DIMY * Game.raster / 2;
		} else {
			centery = y;
		}
	}
	
	private void executeControlQueue()
	{
		ControlEvent event;
		event = controlQueue.poll();
		
		while(event != null)
		{
			switch (event.getType()) {
			case ControlEvent.TYPE_KEY_DOWN:
				executeKeyDown(event.getKey());
				break;
			case ControlEvent.TYPE_KEY_UP:
				executeKeyUp(event.getKey());
				break;
			case ControlEvent.TYPE_MOUSE_DOWN:
				executeMouseDown(event.getMouseX(), event.getMouseY());
				break;
			case ControlEvent.TYPE_MOUSE_UP:
				executeMouseUp(event.getMouseX(), event.getMouseY());
				break;
			case ControlEvent.TYPE_MOUSE_MOVE:
				executeMouseMove(event.getMouseX(), event.getMouseY());
				break;
			default:
				break;
			}
			event = controlQueue.poll();
		}
	}

	private void executeKeyDown(int key) {
		for (Iterator<GObject> iterator = objectList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
				Component comp = iterator2.next();
				if (comp.isActive() == true) {
					comp.keyDown(key);
				}
			}
		}
		if (key == KeyEventWrapper.KEY_RESTART) {
			setNewScene(id);
		} else if (key == KeyEventWrapper.KEY_MAIN) {
			setNewScene(0);
		} else if (key == KeyEventWrapper.KEY_CHECKPOINT) {
			restore = true;
		} else if (key == KeyEventWrapper.KEY_PAUSE) {
			pause();
		} else if (key == KeyEventWrapper.KEY_ENTER) {
			if (state == STATE_PAUSE_ALL) {
				restoreState();
			}
		}

	}

	private void executeKeyUp(int key) {
		for (Iterator<GObject> iterator = objectList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
				Component comp = iterator2.next();
				if (comp.isActive() == true) {
					comp.keyUp(key);
				}
			}
		}
	}

	public void keyDown(int key) {
		controlQueue.offer(new ControlEvent(ControlEvent.TYPE_KEY_DOWN, key));
	}

	public void lastCheckpoint() {
		restore = true;
	}

	public void keyUp(int key) {
		controlQueue.offer(new ControlEvent(ControlEvent.TYPE_KEY_UP, key));
	}
	
	public void mouseDown(int x, int y)
	{
		controlQueue.offer(new ControlEvent(ControlEvent.TYPE_MOUSE_DOWN, x,y));
	}

	public void mouseUp(int x, int y)
	{
		controlQueue.offer(new ControlEvent(ControlEvent.TYPE_MOUSE_UP, x,y));
	}
	public void mouseMove(int x, int y)
	{
		controlQueue.offer(new ControlEvent(ControlEvent.TYPE_MOUSE_MOVE, x,y));
	}


	private void executeMouseDown(int x, int y) {
		boolean curComp = false;
		buttonPress = false;
		ArrayList<GObject> buttons = searchGObjectByTag("button");
		for (Iterator<GObject> iterator = buttons.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
				Component comp = iterator2.next();
				if (comp.isActive() == true) {
					curComp = comp.mouseDown(x, y);
					if (curComp != false) {
						buttonPress = true;
					}
				}
			}
		}
		if (buttonPress == false) {
			for (Iterator<GObject> iterator = objectList.iterator(); iterator.hasNext();) {
				GObject obj = iterator.next();
				if (!obj.hasTag("button")) {
					for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
						Component comp = iterator2.next();
						if (comp.isActive() == true) {
							comp.mouseDown(x, y);
						}
					}
				}
			}
			boolean mousewait = false;
			if (state == STATE_PAUSE_TAGS) {
				for (Iterator<String> iterator = pauseTags.iterator(); iterator.hasNext();) {
					String tag = iterator.next();
					if (tag.equals("player")) {
						mousewait = true;
						break;
					}
				}
			}
			if (state == STATE_PAUSE_ALL || state == STATE_DO_NOTHING || mousewait == true) {
				sendMessage(null, null, null, "mouse.click.enter");
			}
		}
	}

	private void executeMouseUp(int x, int y) {

		buttonPress = false;
		for (Iterator<GObject> iterator = objectList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
				Component comp = iterator2.next();
				if (comp.isActive() == true) {
					comp.mouseUp(x, y);
				}
			}
		}
	}

	private void executeMouseMove(int x, int y) {
		if (buttonPress == true) {
			return;
		}
		for (Iterator<GObject> iterator = objectList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
				Component comp = iterator2.next();
				if (comp.isActive() == true) {
					comp.mouseMove(x, y);
				}
			}
		}
	}

	public void onTriggerEnter(GObject sourceobj, GObject triggerobj, int x, int y) {

	}

	public void onTriggerStay(GObject sourceobj, GObject triggerobj, int x, int y) {

	}

	public void onTriggerLeave(GObject sourceobj, GObject triggerobj, int x, int y) {

	}

	protected boolean setNewScene(GObject triggerobj) {
		boolean rc = false;

		ExitComponent exit = triggerobj.getExitComponent();
		if (exit != null && exit.isOpen() == true) {
			newSceneId = exit.getNextSceneId();
			if (newSceneId == -1) {
				newSceneId = id + 1;
			}
			Scene newScene = getGame().findSceneById(newSceneId);
			if (newScene == null) {
				newSceneId = 0;
			}
			setShuffleState(SHUFFLE_STATE_OUT);
			game.getSound("shuffle").play();
			rc = true;
		}
		return rc;
	}

	protected void setNewScene(int nextSceneId) {
		if (nextSceneId == -1) {
			newSceneId = id + 1;
			Scene newScene = getGame().findSceneById(newSceneId);
			if (newScene == null) {
				newSceneId = 0;
				setShuffleState(SHUFFLE_STATE_OUT);
				game.getSound("shuffle").play();

			}
		} else {
			Scene newScene = getGame().findSceneById(nextSceneId);
			if (newScene == null) {
				newSceneId = 0;
			} else {
				newSceneId = nextSceneId;
				setShuffleState(SHUFFLE_STATE_OUT);
				game.getSound("shuffle").play();
			}
		}
	}

	private void switchScene() {
		if (newSceneId != -1 && shuffleState == SHUFFLE_STATE_OFF) {
			if (tempSavefile != null) {
				getGame().deleteFile(tempSavefile);
			}
			Scene newScene = getGame().findSceneById(newSceneId);
			getGame().setActiveScene(newScene);
			// newScene.init();
			newSceneId = -1;
			oldState = -1;
			setShuffleState(SHUFFLE_STATE_IN);
			game.getSound("shuffle").play();
		}
	}

	public void debugDrawTile(int x, int y, char id) {

		Gizmo gizmo = new Gizmo(x, y, id);

		gizmoList.add(gizmo);
	}

	private void drawGizmos() {
		for (Iterator<Gizmo> iterator = gizmoList.iterator(); iterator.hasNext();) {
			Gizmo gizmo = iterator.next();

			char[] txt = new char[1];
			txt[0] = gizmo.getC();
			// Graphics2D.setColor(Color.cyan);
			graphicsWrapper.drawChars(txt.toString(), 0, 1, gizmo.getX() * Game.raster, (gizmo.getY() + 1) * Game.raster, GraphicsWrapper.COLOR_GREEN, 0);
		}
		gizmoList.clear();
	}

	public boolean isPauseTag(ArrayList<String> objTags) {
		if (pauseTags.size() == 0 || objTags.size() == 0) {
			return false;
		}

		for (Iterator<String> iterator = pauseTags.iterator(); iterator.hasNext();) {
			String ptag = iterator.next();
			for (Iterator<String> iterator2 = objTags.iterator(); iterator2.hasNext();) {
				String otag = iterator2.next();
				if (otag.equals(ptag)) {
					return true;
				}
			}
		}
		return false;
	}

	private void paintLightMap(GraphicsWrapper g) {

		for (int i = 0; i < lightmap.length; i++) {
			for (int j = 0; j < (lightmap[0]).length; j++) {
				g.fillRectRGB(j * LIGHT_RASTER, i * LIGHT_RASTER, LIGHT_RASTER, LIGHT_RASTER, lightmap[i][j]);
			}
		}

	}

	private void clearLightMap() {
		int i, j;

		for (i = 0; i < lightmap.length; i++) {
			for (j = 0; j < (lightmap[0]).length; j++) {
				lightmap[i][j] = lightBackColor;
			}
		}
	}

	private void createLightMap() {
		double dist;
		double ratio;

		for (int i = 0; i < lightmap.length; i++) {
			for (int j = 0; j < (lightmap[0]).length; j++) {
				for (Iterator<Light> iterator = lightList.iterator(); iterator.hasNext();) {
					Light lightSource = iterator.next();
					dist = calcDistance(j * LIGHT_RASTER + (LIGHT_RASTER / 2), i * LIGHT_RASTER + (LIGHT_RASTER / 2), lightSource.getX(), lightSource.getY());
					if (dist <= lightSource.getIntensity() * lightSource.getIntensity()) {
						ratio = 1 - (dist / (lightSource.getIntensity() * lightSource.getIntensity()));

						lightmap[i][j] = GraphicsWrapper.calcColor(lightmap[i][j], lightSource.getColor(), ratio);
					}
				}
			}
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<GObject> getObjectList() {
		return objectList;
	}

	public void setObjectList(ArrayList<GObject> objectList) {
		this.objectList = objectList;
	}

	public void addBufferList(GObject obj) {
		bufferList.add(obj);

		TransformTileComponent trans = obj.getTransformTileComponent();
		// if (trans == null) {
		// Log.w("no transform tile");
		// re
		// }
		if (obj != null) {
			// if (posCache[trans.getZ()][trans.getY()][trans.getX()] != -1) {
			// GObject objOld =
			// bufferList.get(posCache[trans.getZ()][trans.getY()][trans.getX()]);
			// System.out.println("add conflict: " + objOld);
			// System.out.println("add conflict: " + obj);
			// System.out.println("add conflict: " + trans.getZ() + " " +
			// trans.getY() + " " + trans.getX());
			//
			// // System.out.println("a:" +
			// //
			// bufferList.get(posCache[trans.getZ()][trans.getY()][trans.getX()]));
			// }
			posCache[trans.getZ()][trans.getY()][trans.getX()] = bufferList.size() - 1;
		}
	}

	public void removeBufferList(GObject obj) {
		int i = bufferList.indexOf(obj);
		bufferList.remove(obj);
		if (i == -1) {
			return;
		}

		TransformTileComponent trans = obj.getTransformTileComponent();
		if (trans != null) {
			posCache[trans.getZ()][trans.getY()][trans.getX()] = -1;
			for (int l = 0; l < posCache.length; l++) {
				for (int j = 0; j < (posCache[0]).length; j++) {
					for (int k = 0; k < (posCache[0][0]).length; k++) {
						int v = posCache[l][j][k];
						if (v == i) {
							posCache[l][j][k] = -1;
						}
						if (v > i) {
							posCache[l][j][k]--;
						}
					}
				}
			}

		}

	}

	public void changeBufferObject(int oldx, int oldy, int oldz, int newx, int newy, int newz) {
		if (oldx == newx && oldy == newy && oldz == newz) {
			return;
		}
		int val = posCache[oldz][oldy][oldx];
		if (val != -1) {
			// if (posCache[newz][newy][newx] != -1) {
			// System.out.println("move conflict: " +
			// bufferList.get(posCache[oldz][oldy][oldx]));
			// System.out.println("move conflict: " +
			// bufferList.get(posCache[newz][newy][newx]));
			// System.out.println("move conflict: " + newz + " " + newy + " " +
			// newx);
			// }

			posCache[newz][newy][newx] = val;
			posCache[oldz][oldy][oldx] = -1;
		}
	}

	public void swapBufferObject(int oldx, int oldy, int oldz, int newx, int newy, int newz) {
		if (oldx == newx && oldy == newy && oldz == newz) {
			return;
		}
		int valOld = posCache[oldz][oldy][oldx];
		int valNew = posCache[newz][newy][newx];

		posCache[oldz][oldy][oldx] = valNew;
		posCache[newz][newy][newx] = valOld;
	}

	public void clearBufferList() {
		bufferList.clear();
		for (int l = 0; l < posCache.length; l++) {
			for (int j = 0; j < (posCache[0]).length; j++) {
				for (int k = 0; k < (posCache[0][0]).length; k++) {
					posCache[l][j][k] = -1;
				}
			}
		}
	}

	public void replaceBufferList(ArrayList<GObject> newList) {
		clearBufferList();
		for (GObject obj : newList) {
			addBufferList(obj);
		}
	}

	public Iterator<GObject> getBufferListIterator() {
		return bufferList.iterator();
	}

	public long getCounter() {
		return counter;
	}

	public int getFrame() {
		return frame;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getState() {
		return state;
	}

	protected void setState(int newState) {
		oldState = state;
		state = newState;

		if (pauseText != null) {
			bufferList.remove(pauseText);
			pauseText = null;
		}
		if (state == STATE_PAUSE_ALL) {
			int y = 4;
			if (showPause == false) {
				y = 1;
			}

			pauseText = addTextBox(8, y, 10, 2, "Game Paused", GraphicsWrapper.COLOR_WHITE, true, RenderTextComponent.POS_CENTER, true, "pause", "control.unpause");
			pauseText.getRenderTextComponent().setTopBox(false);
		}
	}

	public int getShuffleState() {
		return shuffleState;
	}

	public void setShuffleState(int shuffleState) {
		this.shuffleState = shuffleState;
		if (shuffleState != SHUFFLE_STATE_OUT) {
			shuffleTimer = 0;
			shufflePos = 0;
		}
	}

	public void pause() {
		if (state != STATE_PAUSE_ALL) {
			setState(STATE_PAUSE_ALL);
		}

	}

	public void restoreState() {
		if (oldState != -1 && oldState != STATE_PAUSE_ALL) {
			setState(oldState);
		} else {
			setState(STATE_PLAY);
		}
	}

	public void restoreSerializedState() {
		for (Iterator<GObject> iterator = bufferList.iterator(); iterator.hasNext();) {
			GObject obj = iterator.next();
			for (Iterator<Component> iterator2 = obj.getComponentList().iterator(); iterator2.hasNext();) {
				Component comp = iterator2.next();
				comp.restoreSerializedState();
			}
		}
		lightmap = new int[LIGHT_DIMY][LIGHT_DIMX];
		lightList = new ArrayList<Light>();
		objComp = new GObjectComparator();
		camvecs = new Vector2D[9];
		controlQueue = new ArrayBlockingQueue<ControlEvent>(16);
		for (int i = 0; i < camvecs.length; i++) {
			camvecs[i] = new Vector2D();
		}

	}

	public ArrayList<String> getPauseTags() {
		return pauseTags;
	}

	public void setPauseTags(ArrayList<String> pauseTags) {
		this.pauseTags = pauseTags;
	}

	public boolean isNewSceneAfterKill() {
		return newSceneAfterKill;
	}

	public void setNewSceneAfterKill(boolean newSceneAfterKill) {
		this.newSceneAfterKill = newSceneAfterKill;
	}

	public int[][] getLightmap() {
		return lightmap;
	}

	public int getLightBackColor() {
		return lightBackColor;
	}

	public void setLightBackColor(int lightBackColor) {
		this.lightBackColor = lightBackColor;
	}

	public float getBrightnessDarkest() {
		return brightnessDarkest;
	}

	public void setBrightnessDarkest(float brightnessDarkest) {
		this.brightnessDarkest = brightnessDarkest;
	}

	public float getBrightnessScale() {
		return brightnessScale;
	}

	public void setBrightnessScale(float brightnessScale) {
		this.brightnessScale = brightnessScale;
	}

	public int getAnimSeq() {
		switch (game.getQuality()) {
		case Game.QUAL_HIGH:
			return 4;
		case Game.QUAL_MED:
			return 2;
		case Game.QUAL_LOW:
			return 2;
		default:
			return 4;
		}
	}

	public int getModx() {
		return modx;
	}

	public int getMody() {
		return mody;
	}

	public int getParSlowModx() {
		return parSlowModx;
	}

	public int getParSlowMody() {
		return parSlowMody;
	}

	public int getParFastModx() {
		return parFastModx;
	}

	public int getParFastMody() {
		return parFastMody;
	}

	public double getParSlowRatioX() {
		return parSlowRatioX;
	}

	public void setParSlowRatioX(double parSlowRatioX) {
		this.parSlowRatioX = parSlowRatioX;
	}

	public double getParSlowRatioY() {
		return parSlowRatioY;
	}

	public void setParSlowRatioY(double parSlowRatioY) {
		this.parSlowRatioY = parSlowRatioY;
	}

	public double getParFastRatioX() {
		return parFastRatioX;
	}

	public void setParFastRatioX(double parFastRatioX) {
		this.parFastRatioX = parFastRatioX;
	}

	public double getParFastRatioY() {
		return parFastRatioY;
	}

	public void setParFastRatioY(double parFastRatioY) {
		this.parFastRatioY = parFastRatioY;
	}

	
}
