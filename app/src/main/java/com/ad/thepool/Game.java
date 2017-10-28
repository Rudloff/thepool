package com.ad.thepool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.ad.thepool.components.Animation;
import com.ad.thepool.objects.BackBrickBig;
import com.ad.thepool.objects.BackBrickSmall;
import com.ad.thepool.objects.BackTorch;
import com.ad.thepool.objects.BlowyBlock;
import com.ad.thepool.objects.BlowyLeftRightMovingTrap;
import com.ad.thepool.objects.BlowyRandomRoundMovingTrap;
import com.ad.thepool.objects.BlowyRock;
import com.ad.thepool.objects.BlowyRoundMovingTrap;
import com.ad.thepool.objects.BlowySand;
import com.ad.thepool.objects.BlowyTrap;
import com.ad.thepool.objects.BlowyUpDownMovingTrap;
import com.ad.thepool.objects.Brick;
import com.ad.thepool.objects.BrickLeftTop;
import com.ad.thepool.objects.BrickRightTop;
import com.ad.thepool.objects.BrickTop;
import com.ad.thepool.objects.Character;
import com.ad.thepool.objects.Checkpoint;
import com.ad.thepool.objects.Coin;
import com.ad.thepool.objects.Door;
import com.ad.thepool.objects.Exit;
import com.ad.thepool.objects.ExplodeRound;
import com.ad.thepool.objects.FontaineCanonDown;
import com.ad.thepool.objects.FontaineCanonLeft;
import com.ad.thepool.objects.FontaineCanonRight;
import com.ad.thepool.objects.FontaineCanonUp;
import com.ad.thepool.objects.Glass;
import com.ad.thepool.objects.GravityPadDown;
import com.ad.thepool.objects.GravityPadLeft;
import com.ad.thepool.objects.GravityPadRight;
import com.ad.thepool.objects.GravityPadUp;
import com.ad.thepool.objects.ImageBox;
import com.ad.thepool.objects.Ladder;
import com.ad.thepool.objects.Laser;
import com.ad.thepool.objects.LaserCanonDown;
import com.ad.thepool.objects.LaserCanonLeft;
import com.ad.thepool.objects.LaserCanonRight;
import com.ad.thepool.objects.LaserCanonUp;
import com.ad.thepool.objects.LaserGlow;
import com.ad.thepool.objects.LaserTransporter;
import com.ad.thepool.objects.LaserTransporterOnce;
import com.ad.thepool.objects.LeftRightMovingTrap;
import com.ad.thepool.objects.Menu;
import com.ad.thepool.objects.MirrorLeft;
import com.ad.thepool.objects.MirrorRight;
import com.ad.thepool.objects.Missile;
import com.ad.thepool.objects.MissileLaunchDown;
import com.ad.thepool.objects.MissileLaunchLeft;
import com.ad.thepool.objects.MissileLaunchRight;
import com.ad.thepool.objects.MissileLaunchRound;
import com.ad.thepool.objects.MissileLaunchUp;
import com.ad.thepool.objects.MouseCursor;
import com.ad.thepool.objects.Player;
import com.ad.thepool.objects.Portal;
import com.ad.thepool.objects.PortalBrick;
import com.ad.thepool.objects.PullAbleBox;
import com.ad.thepool.objects.PushAbleBox;
import com.ad.thepool.objects.RandomRoundMovingTrap;
import com.ad.thepool.objects.RobeDown;
import com.ad.thepool.objects.RobeLeft;
import com.ad.thepool.objects.RobeRight;
import com.ad.thepool.objects.RobeUp;
import com.ad.thepool.objects.RoundMovingTrap;
import com.ad.thepool.objects.Sand;
import com.ad.thepool.objects.ShiftPlattformDown;
import com.ad.thepool.objects.ShiftPlattformLeft;
import com.ad.thepool.objects.ShiftPlattformRight;
import com.ad.thepool.objects.ShiftPlattformUp;
import com.ad.thepool.objects.SolidBlock;
import com.ad.thepool.objects.SolidRock;
import com.ad.thepool.objects.SwitchManual;
import com.ad.thepool.objects.SwitchOnce;
import com.ad.thepool.objects.SwitchWeight;
import com.ad.thepool.objects.SwitchWeightTimed;
import com.ad.thepool.objects.TextBox;
import com.ad.thepool.objects.Trap;
import com.ad.thepool.objects.UpDownMovingTrap;
import com.ad.thepool.objects.Water;
import com.ad.thepool.objects.WaterDown;
import com.ad.thepool.objects.WaterLeft;
import com.ad.thepool.objects.WaterRight;
import com.ad.thepool.objects.WaterUp;
import com.ad.thepool.objects.Waypoint;
import com.ad.thepool.scenes.SceneBeSave;
import com.ad.thepool.scenes.SceneBoulder;
import com.ad.thepool.scenes.SceneBreak;
import com.ad.thepool.scenes.SceneBuild;
import com.ad.thepool.scenes.SceneCoinWall;
import com.ad.thepool.scenes.SceneCollect;
import com.ad.thepool.scenes.SceneControlRoom;
import com.ad.thepool.scenes.SceneDarknight;
import com.ad.thepool.scenes.SceneEgg;
import com.ad.thepool.scenes.SceneEndBoss;
import com.ad.thepool.scenes.SceneFirstStep;
import com.ad.thepool.scenes.SceneFlashlight;
import com.ad.thepool.scenes.SceneFlip;
import com.ad.thepool.scenes.SceneFloat;
import com.ad.thepool.scenes.SceneGlassCage;
import com.ad.thepool.scenes.SceneHammer;
import com.ad.thepool.scenes.SceneInnerRoom;
import com.ad.thepool.scenes.SceneLadder;
import com.ad.thepool.scenes.SceneLaserLight;
import com.ad.thepool.scenes.SceneLevelUp;
import com.ad.thepool.scenes.SceneManyHeros;
import com.ad.thepool.scenes.SceneMirror;
import com.ad.thepool.scenes.SceneNextDoor;
import com.ad.thepool.scenes.ScenePac;
import com.ad.thepool.scenes.ScenePlanet;
import com.ad.thepool.scenes.ScenePort;
import com.ad.thepool.scenes.ScenePortal;
import com.ad.thepool.scenes.ScenePortalroom;
import com.ad.thepool.scenes.ScenePrison;
import com.ad.thepool.scenes.ScenePush;
import com.ad.thepool.scenes.SceneRevert;
import com.ad.thepool.scenes.SceneRollercoster;
import com.ad.thepool.scenes.SceneRun;
import com.ad.thepool.scenes.SceneSimplePortal;
import com.ad.thepool.scenes.SceneSimpleSwap;
import com.ad.thepool.scenes.SceneStart;
import com.ad.thepool.scenes.SceneStonebridge;
import com.ad.thepool.scenes.SceneSwapPlayer;
import com.ad.thepool.scenes.SceneTower;
import com.ad.thepool.scenes.SceneTutorial;
import com.ad.thepool.scenes.SceneWalkRight;
import com.ad.thepool.scenes.SceneWashing;
import com.ad.thepool.wrapper.GraphicsWrapper;
import com.ad.thepool.wrapper.Log;
import com.ad.thepool.wrapper.SoundWrapper;
import com.ad.thepool.wrapper.StreamWrapper;

public class Game implements Serializable {

	private static final long serialVersionUID = 9022406390130361503L;

	private transient ApplicationFrame frame;
	private Prefab prefab;
	private ArrayList<Scene> scenes;
	private ArrayList<TileMap> tilemaps;
	private HashMap<String, SoundWrapper> soundSet;
	private Scene activeScene;

	private int quality = QUAL_MED;

	public static final int QUAL_LOW = 1;
	public static final int QUAL_MED = 2;
	public static final int QUAL_HIGH = 3;

	private boolean sound = true;
	private boolean music = true;

	public static int raster;
	public static int offsetX;
	public static int offsetY;
	public static final int AREA_CENTER = 75;
	public static final String SAVE_FILE_NAME = "savegame.bin";
	public static final String INFO_SAVE_FILE_NAME = "savegame.inf";

	public Game(ApplicationFrame frame) {
		this.frame = frame;
		scenes = new ArrayList<Scene>();
		tilemaps = new ArrayList<TileMap>();
		soundSet = new HashMap<String, SoundWrapper>();
	}

	public static int calcRaster(int screenx, int screeny) {
		int r;
		float dwidth = screenx / Scene.DIMX;
		float dheight = screeny / Scene.DIMY;

		float dmin = Math.min(dwidth, dheight);

		r = (int) Math.floor(dmin);

		// if (dmin >= 4 && dmin < 8) {
		// r = 4;
		// } else if (dmin >= 8 && dmin < 12) {
		// r = 8;
		// } else if (dmin >= 12 && dmin < 16) {
		// r = 12;
		// } else if (dmin >= 16 && dmin < 20) {
		// r = 16;
		// } else if (dmin >= 20 && dmin < 24) {
		// r = 20;
		// } else if (dmin >= 24 && dmin < 32) {
		// r = 24;
		// } else if (dmin >= 32 && dmin < 40) {
		// r = 32;
		// } else if (dmin >= 40 && dmin < 48) {
		// r = 40;
		// } else if (dmin >= 48 && dmin < 64) {
		// r = 48;
		// } else if (dmin >= 64) {
		// r = 64;
		// } else {
		// r = 4;
		// }

		return r;
	}

	public static int calcViewX(int screenx, int screeny) {
		int r = calcRaster(screenx, screeny);
		return r * Scene.DIMX;
	}

	public static int calcViewY(int screenx, int screeny) {
		int r = calcRaster(screenx, screeny);
		return r * Scene.DIMY;
	}

	public void init(int screenx, int screeny) {
		scenes = new ArrayList<Scene>();
		tilemaps = new ArrayList<TileMap>();
		soundSet = new HashMap<String, SoundWrapper>();

		raster = calcRaster(screenx, screeny);
		TileMap tilemap = new TileMap("tilemap");
		TileMap tilemapidx;
		tilemaps.add(tilemap);
		if(frame.isAndroid())
		{
			tilemapidx = new TileMap("idx_tilemap");
		}
		else
		{
			tilemapidx = tilemap;
		}
		tilemaps.add(tilemapidx);
		prefab = new Prefab();
		scenes = new ArrayList<Scene>();
		// Tiles Objects

		soundSet.put("jump", new SoundWrapper(this, "jump", false));
		soundSet.put("collide", new SoundWrapper(this, "collide", false));
		soundSet.put("step", new SoundWrapper(this, "step", false));
		soundSet.put("swim", new SoundWrapper(this, "swim", false));
		soundSet.put("robe", new SoundWrapper(this, "robe", false));
		soundSet.put("climb", new SoundWrapper(this, "climb", false));
		soundSet.put("portal", new SoundWrapper(this, "portal", false));
		soundSet.put("gravity", new SoundWrapper(this, "gravity", false));
		soundSet.put("collect", new SoundWrapper(this, "collect", false));
		soundSet.put("open", new SoundWrapper(this, "open", false));
		soundSet.put("close", new SoundWrapper(this, "close", false));
		soundSet.put("laser", new SoundWrapper(this, "laser", false));
		soundSet.put("push", new SoundWrapper(this, "push", false));
		soundSet.put("transport", new SoundWrapper(this, "transport", false));
		soundSet.put("activate_portal", new SoundWrapper(this, "activate_portal", false));
		soundSet.put("missile", new SoundWrapper(this, "missile", false));
		soundSet.put("shift", new SoundWrapper(this, "shift", false));
		soundSet.put("text", new SoundWrapper(this, "text", false));
		soundSet.put("switch_on", new SoundWrapper(this, "switch_on", false));
		soundSet.put("switch_off", new SoundWrapper(this, "switch_off", false));
		soundSet.put("explode", new SoundWrapper(this, "explode", false));
		soundSet.put("shuffle", new SoundWrapper(this, "shuffle", false));
		soundSet.put("crash", new SoundWrapper(this, "crash", false));
		soundSet.put("theme", new SoundWrapper(this, "theme", true));

		// collide
		// step
		// swim
		// climb
		// portal
		// gravity
		// collect
		// open
		// close
		// laser
		// push
		// transport
		// activate_portal
		// missile
		// shift
		// text
		// switch_on
		// switch_off
		// explode
		// shuffle
		// crash

		Brick brick = new Brick(1, tilemapidx);
		SolidRock srock = new SolidRock(2, tilemapidx);
		Player player = new Player(3, tilemapidx);
		Ladder ladder = new Ladder(4, tilemapidx);
		Laser laser = new Laser(5, tilemapidx);
		Sand sand = new Sand(6, tilemap);
		BlowySand bsand = new BlowySand(7, tilemap);
		BlowyRock brock = new BlowyRock(8, tilemapidx);
		RobeDown robeDown = new RobeDown(9, tilemapidx);
		RobeUp robeUp = new RobeUp(10, tilemapidx);
		RobeLeft robeLeft = new RobeLeft(11, tilemapidx);
		RobeRight robeRight = new RobeRight(12, tilemapidx);
		LaserTransporter lasertrans = new LaserTransporter(13, tilemapidx);
		Glass glass = new Glass(14, tilemap);
		MirrorLeft mirrLeft = new MirrorLeft(15, tilemapidx);
		MirrorRight mirrRight = new MirrorRight(16, tilemapidx);
		GravityPadUp gravUp = new GravityPadUp(17, tilemapidx);
		GravityPadDown gravDown = new GravityPadDown(18, tilemapidx);
		GravityPadLeft gravLeft = new GravityPadLeft(19, tilemapidx);
		GravityPadRight gravRight = new GravityPadRight(20, tilemapidx);
		ShiftPlattformUp shiftUp = new ShiftPlattformUp(21, tilemapidx);
		ShiftPlattformDown shiftDown = new ShiftPlattformDown(22, tilemapidx);
		ShiftPlattformLeft shiftLeft = new ShiftPlattformLeft(23, tilemapidx);
		ShiftPlattformRight shiftRight = new ShiftPlattformRight(24, tilemapidx);
		Exit exit = new Exit(25, tilemapidx);
		SwitchManual switchO = new SwitchManual(26, tilemapidx);
		Waypoint way = new Waypoint(27, tilemapidx);
		Character character = new Character(28, tilemapidx);
		PortalBrick portalb = new PortalBrick(29, tilemapidx);
		Portal portal = new Portal(30, tilemapidx);
		PushAbleBox mbox = new PushAbleBox(31, tilemapidx);
		Trap trap = new Trap(32, tilemapidx);
		BlowyTrap btrap = new BlowyTrap(33, tilemapidx);
		LaserCanonDown laserDown = new LaserCanonDown(34, tilemapidx);
		LaserCanonUp laserUp = new LaserCanonUp(35, tilemapidx);
		LaserCanonLeft laserLeft = new LaserCanonLeft(36, tilemapidx);
		LaserCanonRight laserRight = new LaserCanonRight(37, tilemapidx);
		LaserGlow laserGlow = new LaserGlow(38, tilemapidx);
		RoundMovingTrap mtrap = new RoundMovingTrap(39, tilemapidx);
		LaserTransporterOnce laserTransOnce = new LaserTransporterOnce(40, tilemapidx);
		BlowyRoundMovingTrap bmtrap = new BlowyRoundMovingTrap(41, tilemapidx);
		UpDownMovingTrap udtrap = new UpDownMovingTrap(42, tilemapidx);
		LeftRightMovingTrap lrtrap = new LeftRightMovingTrap(43, tilemapidx);
		BlowyUpDownMovingTrap budtrap = new BlowyUpDownMovingTrap(44, tilemapidx);
		BlowyLeftRightMovingTrap blrtrap = new BlowyLeftRightMovingTrap(45, tilemapidx);
		Missile miss = new Missile(46, tilemapidx);
		MissileLaunchDown mld = new MissileLaunchDown(47, tilemapidx);
		MissileLaunchUp mlu = new MissileLaunchUp(48, tilemapidx);
		MissileLaunchLeft mll = new MissileLaunchLeft(49, tilemapidx);
		MissileLaunchRight mlr = new MissileLaunchRight(50, tilemapidx);
		MissileLaunchRound mlround = new MissileLaunchRound(51, tilemapidx);
		Door door = new Door(52, tilemapidx);
		SolidBlock sblock = new SolidBlock(53, tilemapidx);
		BlowyBlock bblock = new BlowyBlock(54, tilemapidx);
		PullAbleBox mpullbox = new PullAbleBox(55, tilemapidx);
		SwitchWeight switchw = new SwitchWeight(56, tilemapidx);
		ExplodeRound explodeR = new ExplodeRound(57, tilemap);
		SwitchOnce switcho = new SwitchOnce(58, tilemapidx);
		Checkpoint check = new Checkpoint(59, tilemapidx);
		Coin coin = new Coin(60, tilemapidx);
		Water water = new Water(61, tilemap);
		RandomRoundMovingTrap rround = new RandomRoundMovingTrap(62, tilemapidx);
		BlowyRandomRoundMovingTrap brround = new BlowyRandomRoundMovingTrap(63, tilemapidx);
		FontaineCanonDown fontaineDown = new FontaineCanonDown(64, tilemapidx);
		FontaineCanonUp fontaineUp = new FontaineCanonUp(65, tilemapidx);
		FontaineCanonLeft fontaineLeft = new FontaineCanonLeft(66, tilemapidx);
		FontaineCanonRight fontaineRight = new FontaineCanonRight(67, tilemapidx);
		WaterUp waterUp = new WaterUp(68, tilemapidx);
		WaterDown waterDown = new WaterDown(69, tilemap);
		WaterLeft waterLeft = new WaterLeft(70, tilemap);
		WaterRight waterRight = new WaterRight(71, tilemap);
		BackBrickBig bbb = new BackBrickBig(72, tilemapidx);
		BackBrickSmall bbs = new BackBrickSmall(73, tilemapidx);
		BrickLeftTop bricklt = new BrickLeftTop(74, tilemapidx);
		BrickRightTop brickrt = new BrickRightTop(75, tilemapidx);
		BrickTop brickt = new BrickTop(76, tilemapidx);
		SwitchWeightTimed switchwt = new SwitchWeightTimed(77, tilemapidx);
		BackTorch torch = new BackTorch(78, tilemap);

		// Other Objects
		TextBox textbox = new TextBox(1000, tilemap, 1);
		ImageBox imgbox = new ImageBox(1001, null);
		MouseCursor mouseCurs = new MouseCursor(1002);
		Menu menu = new Menu(1003);
		prefab.getRepository().add(brick);
		prefab.getRepository().add(srock);
		prefab.getRepository().add(player);
		prefab.getRepository().add(ladder);
		prefab.getRepository().add(laser);
		prefab.getRepository().add(sand);
		prefab.getRepository().add(bsand);
		prefab.getRepository().add(brock);
		prefab.getRepository().add(robeDown);
		prefab.getRepository().add(robeUp);
		prefab.getRepository().add(robeLeft);
		prefab.getRepository().add(robeRight);
		prefab.getRepository().add(lasertrans);
		prefab.getRepository().add(glass);
		prefab.getRepository().add(mirrLeft);
		prefab.getRepository().add(mirrRight);
		prefab.getRepository().add(gravUp);
		prefab.getRepository().add(gravDown);
		prefab.getRepository().add(gravLeft);
		prefab.getRepository().add(gravRight);
		prefab.getRepository().add(shiftUp);
		prefab.getRepository().add(shiftDown);
		prefab.getRepository().add(shiftLeft);
		prefab.getRepository().add(shiftRight);
		prefab.getRepository().add(exit);
		prefab.getRepository().add(switchO);
		prefab.getRepository().add(way);
		prefab.getRepository().add(textbox);
		prefab.getRepository().add(character);
		prefab.getRepository().add(portalb);
		prefab.getRepository().add(portal);
		prefab.getRepository().add(mbox);
		prefab.getRepository().add(trap);
		prefab.getRepository().add(btrap);
		prefab.getRepository().add(laserDown);
		prefab.getRepository().add(laserUp);
		prefab.getRepository().add(laserLeft);
		prefab.getRepository().add(laserRight);
		prefab.getRepository().add(laserGlow);
		prefab.getRepository().add(mtrap);
		prefab.getRepository().add(laserTransOnce);
		prefab.getRepository().add(bmtrap);
		prefab.getRepository().add(udtrap);
		prefab.getRepository().add(lrtrap);
		prefab.getRepository().add(budtrap);
		prefab.getRepository().add(blrtrap);
		prefab.getRepository().add(miss);
		prefab.getRepository().add(mld);
		prefab.getRepository().add(mlu);
		prefab.getRepository().add(mll);
		prefab.getRepository().add(mlr);
		prefab.getRepository().add(mlround);
		prefab.getRepository().add(imgbox);
		prefab.getRepository().add(door);
		prefab.getRepository().add(sblock);
		prefab.getRepository().add(bblock);
		prefab.getRepository().add(mpullbox);
		prefab.getRepository().add(switchw);
		prefab.getRepository().add(explodeR);
		prefab.getRepository().add(switcho);
		prefab.getRepository().add(check);
		prefab.getRepository().add(coin);
		prefab.getRepository().add(water);
		prefab.getRepository().add(rround);
		prefab.getRepository().add(brround);
		prefab.getRepository().add(fontaineDown);
		prefab.getRepository().add(fontaineUp);
		prefab.getRepository().add(fontaineLeft);
		prefab.getRepository().add(fontaineRight);
		prefab.getRepository().add(waterUp);
		prefab.getRepository().add(waterDown);
		prefab.getRepository().add(waterLeft);
		prefab.getRepository().add(waterRight);
		prefab.getRepository().add(mouseCurs);
		prefab.getRepository().add(bbb);
		prefab.getRepository().add(bbs);
		prefab.getRepository().add(bricklt);
		prefab.getRepository().add(brickrt);
		prefab.getRepository().add(brickt);
		prefab.getRepository().add(torch);
		prefab.getRepository().add(switchwt);
		prefab.getRepository().add(menu);

		SceneStart scene0 = new SceneStart(0, this);
		scenes.add(scene0);
		SceneDarknight scene1 = new SceneDarknight(1, this);
		scenes.add(scene1);
		SceneWalkRight scene2 = new SceneWalkRight(2, this);
		scenes.add(scene2);
		SceneNextDoor scene3 = new SceneNextDoor(3, this);
		scenes.add(scene3);
		SceneFlip scene4 = new SceneFlip(4, this);
		scenes.add(scene4);
		SceneEgg scene5 = new SceneEgg(5, this);
		scenes.add(scene5);
		SceneCoinWall scene6 = new SceneCoinWall(6, this);
		scenes.add(scene6);
		SceneSimpleSwap scene7 = new SceneSimpleSwap(7, this);
		scenes.add(scene7);
		SceneSimplePortal scene8 = new SceneSimplePortal(8, this);
		scenes.add(scene8);
		ScenePush scene9 = new ScenePush(9, this);
		scenes.add(scene9);
		SceneBoulder scene10 = new SceneBoulder(10, this);
		scenes.add(scene10);
		SceneBeSave scene11 = new SceneBeSave(11, this);
		scenes.add(scene11);
		SceneWashing scene12 = new SceneWashing(12, this);
		scenes.add(scene12);
		ScenePort scene13 = new ScenePort(13, this);
		scenes.add(scene13);
		SceneRevert scene14 = new SceneRevert(14, this);
		scenes.add(scene14);
		SceneMirror scene15 = new SceneMirror(15, this);
		scenes.add(scene15);
		SceneLevelUp scene16 = new SceneLevelUp(16, this);
		scenes.add(scene16);
		SceneRollercoster scene17 = new SceneRollercoster(17, this);
		scenes.add(scene17);
		SceneTower scene18 = new SceneTower(18, this);
		scenes.add(scene18);
		SceneFlashlight scene19 = new SceneFlashlight(19, this);
		scenes.add(scene19);
		SceneFloat scene20 = new SceneFloat(20, this);
		scenes.add(scene20);
		SceneFirstStep scene21 = new SceneFirstStep(21, this);
		scenes.add(scene21);
		ScenePlanet scene22 = new ScenePlanet(22, this);
		scenes.add(scene22);
		SceneLaserLight scene23 = new SceneLaserLight(23, this);
		scenes.add(scene23);
		ScenePortal scene24 = new ScenePortal(24, this);
		scenes.add(scene24);
		SceneSwapPlayer scene25 = new SceneSwapPlayer(25, this);
		scenes.add(scene25);
		SceneBreak scene26 = new SceneBreak(26, this);
		scenes.add(scene26);
		SceneManyHeros scene27 = new SceneManyHeros(27, this);
		scenes.add(scene27);
		SceneGlassCage scene28 = new SceneGlassCage(28, this);
		scenes.add(scene28);
		SceneInnerRoom scene29 = new SceneInnerRoom(29, this);
		scenes.add(scene29);
		SceneRun scene30 = new SceneRun(30, this);
		scenes.add(scene30);
		SceneStonebridge scene31 = new SceneStonebridge(31, this);
		scenes.add(scene31);
		ScenePortalroom scene32 = new ScenePortalroom(32, this);
		scenes.add(scene32);
		ScenePrison scene33 = new ScenePrison(33, this);
		scenes.add(scene33);
		SceneBuild scene34 = new SceneBuild(34, this);
		scenes.add(scene34);
		SceneControlRoom scene35 = new SceneControlRoom(35, this);
		scenes.add(scene35);
		SceneCollect scene36 = new SceneCollect(36, this);
		scenes.add(scene36);
		SceneHammer scene37 = new SceneHammer(37, this);
		scenes.add(scene37);
		SceneLadder scene38 = new SceneLadder(38, this);
		scenes.add(scene38);
		ScenePac scene39 = new ScenePac(39, this);
		scenes.add(scene39);
		SceneEndBoss scene40 = new SceneEndBoss(40, this);
		scenes.add(scene40);
		SceneTutorial scene1000 = new SceneTutorial(1000, this);
		scenes.add(scene1000);

		setActiveScene(scene0);

		soundSet.get("theme").loop();

		setQuality(QUAL_MED);

	}

	public void pause() {
		activeScene.pause();
	}

	public void unpause() {
		activeScene.restoreState();
	}

	public void mainMenu() {
		activeScene.setNewScene(0);
	}

	public void restartScene() {
		activeScene.setNewScene(activeScene.id);
	}

	public void nextScene() {
		activeScene.setNewScene(-1);
	}

	public void lastCheckpoint() {
		activeScene.lastCheckpoint();
	}

	public void keyDown(int key) {
		activeScene.keyDown(key);
	}

	public void keyUp(int key) {
		activeScene.keyUp(key);
	}

	public void mouseDown(int x, int y) {
		activeScene.mouseDown(x, y);
	}

	public void mouseUp(int x, int y) {
		activeScene.mouseUp(x, y);

	}

	public void mouseMove(int x, int y) {
		activeScene.mouseMove(x, y);

	}

	public boolean paint(GraphicsWrapper g) {
		return activeScene.paint(g);
	}

	public Scene getActiveScene() {
		return activeScene;
	}

	public void setActiveScene(Scene activeScene) {
		this.activeScene = activeScene;
		activeScene.init();

	}

	public Scene findSceneById(int id) {
		Scene scene = null;
		boolean found = false;

		for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();) {
			scene = iterator.next();
			if (scene.getId() == id) {
				found = true;
				break;
			}
		}
		if (found == false) {
			return null;
		}
		return scene;
	}

	public static void saveSerializedGame(Game game, String filename, String rfilename) {

		int activeSceneId = game.getActiveScene().getId();

		if (rfilename != null) {
			FileOutputStream rout = StreamWrapper.getFileOutputStream(rfilename);

			try {
				ObjectOutputStream oout = new ObjectOutputStream(rout);
				oout.writeObject(new Integer(activeSceneId));
				oout.close();
				rout.close();
			} catch (IOException e) {
				Log.e("Cannot write Rescue Savegame");
				e.printStackTrace();
			}
		}

		if (filename != null) {
			FileOutputStream out = StreamWrapper.getFileOutputStream(filename);

			try {
				ObjectOutputStream oout = new ObjectOutputStream(out);
				oout.writeObject(game);
				oout.close();
				out.close();
			} catch (IOException e) {
				Log.e("Cannot write Savegame");
				e.printStackTrace();
			}
		} else {
			Log.d("Delete full save game " + StreamWrapper.deleteFile(SAVE_FILE_NAME));
			// StreamWrapper.deleteFile(SAVE_FILE_NAME);
		}

	}

	public static Game loadSerializedState(String filename) throws Exception {
		// File f = new File(filename);
		Game loadGame;
		//
		// if (f.exists() == false) {
		// return null;
		// }

		FileInputStream in = StreamWrapper.getFileInputStream(filename);
		if (in == null) {
			throw new Exception();
		}

		// ApplicationFrame tframe = frame;
		loadGame = (Game) new ObjectInputStream(in).readObject();
		in.close();
		// game.frame = tframe;
		// game.frame.setGame(game);
		// game.restoreSerializedState();
		// game.pause();
		return loadGame;
	}

	public static int loadLastSceneId(String rfilename) {
		Integer activeSceneId = new Integer(-1);
		FileInputStream in = StreamWrapper.getFileInputStream(rfilename);
		if (in == null) {
			return -1;
		}
		try {
			activeSceneId = (Integer) new ObjectInputStream(in).readObject();
		} catch (Exception e) {
			try {
				in.close();
			} catch (IOException e1) {
			}
			return -1;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("Rescue ID " + activeSceneId);
		return activeSceneId.intValue();
	}

	public boolean deleteFile(String filename) {
		return StreamWrapper.deleteFile(filename);
	}

	public void restoreSerializedState() {
		for (Iterator<TileMap> iterator = tilemaps.iterator(); iterator.hasNext();) {
			TileMap tilemap = iterator.next();
			tilemap.restoreState();
		}

		for (Iterator<Entry<String, SoundWrapper>> it = soundSet.entrySet().iterator(); it.hasNext();) {
			Entry<String, SoundWrapper> entry = it.next();
			entry.getValue().restoreState();
		}
		activeScene.sendMessage(null, null, null, "checkpoint.restore");

		activeScene.restoreSerializedState();
	}

	public Prefab getPrefab() {
		return prefab;
	}

	public void setPrefab(Prefab prefab) {
		this.prefab = prefab;
	}

	public ArrayList<Scene> getScenes() {
		return scenes;
	}

	public void setScenes(ArrayList<Scene> scenes) {
		this.scenes = scenes;
	}

	public HashMap<String, SoundWrapper> getSoundSet() {
		return soundSet;
	}

	public SoundWrapper getSound(String key) {
		return soundSet.get(key);
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
		activeScene.sendMessage(null, null, null, "quality." + quality);
		frame.setFrameGoal(quality);
	}

	public boolean isSound() {
		return sound;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
		activeScene.sendMessage(null, null, null, "sound." + sound);
		if (sound == false) {
			for (Iterator<String> iterator = soundSet.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				SoundWrapper snd = soundSet.get(key);
				if (!snd.isMusic()) {
					snd.stop();
				}
			}
		}
	}

	public boolean isMusic() {
		return music;
	}

	public void setMusic(boolean music) {
		this.music = music;
		activeScene.sendMessage(null, null, null, "music." + music);
		for (Iterator<String> iterator = soundSet.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			SoundWrapper snd = soundSet.get(key);
			if (snd.isMusic()) {
				if (music == false) {
					snd.pause();
				} else {
					snd.loop();
				}
			}
		}
	}

	public String getQualityText() {
		switch (quality) {
		case QUAL_LOW:
			return "Low";
		case QUAL_MED:
			return "Medium";
		case QUAL_HIGH:
			return "High";
		default:
			return "?";
		}
	}

	public String getBooleanText(boolean val) {
		if (val == true)
			return "On";
		else
			return "Off";
	}

	public ApplicationFrame getFrame() {
		return frame;
	}

	public void setFrame(ApplicationFrame frame) {
		this.frame = frame;
	}

}
