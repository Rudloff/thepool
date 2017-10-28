package com.ad.thepool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.ad.thepool.components.ActivateComponent;
import com.ad.thepool.components.CameraComponent;
import com.ad.thepool.components.ClimbAbleComponent;
import com.ad.thepool.components.CollectAbleComponent;
import com.ad.thepool.components.CollectorComponent;
import com.ad.thepool.components.CollideAbleComponent;
import com.ad.thepool.components.ColliderFrameComponent;
import com.ad.thepool.components.Component;
import com.ad.thepool.components.ControlComponent;
import com.ad.thepool.components.DestroyAbleComponent;
import com.ad.thepool.components.DestroyCollideComponent;
import com.ad.thepool.components.DoorComponent;
import com.ad.thepool.components.EraseAbleComponent;
import com.ad.thepool.components.ExitComponent;
import com.ad.thepool.components.FloaterComponent;
import com.ad.thepool.components.FontaineComponent;
import com.ad.thepool.components.GravitySwitchComponent;
import com.ad.thepool.components.LaserComponent;
import com.ad.thepool.components.LaserTransportAbleComponent;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.MenuComponent;
import com.ad.thepool.components.MirrorComponent;
import com.ad.thepool.components.MissileLaunchComponent;
import com.ad.thepool.components.MouseCursorComponent;
import com.ad.thepool.components.MoveComponent;
import com.ad.thepool.components.PathMoveComponent;
import com.ad.thepool.components.PhysicsComponent;
import com.ad.thepool.components.PortalActiveComponent;
import com.ad.thepool.components.PortalComponent;
import com.ad.thepool.components.PushPullAbleComponent;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.RenderTextComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.RobeAbleComponent;
import com.ad.thepool.components.ShiftPlattformComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerAbleComponent;
import com.ad.thepool.components.TriggerFrameComponent;
import com.ad.thepool.components.WaterComponent;
import com.ad.thepool.components.WaypointComponent;
import com.ad.thepool.components.WeightComponent;

public class GObject implements Comparable<GObject>, Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1206390256378091270L;
	private int id;
	private boolean isPrefab;
	private Scene scene;
	private char sceneScriptIndex;
	private boolean isSaveObject;
	private ActivateComponent cactivate;
	private boolean hactivate = true;
	private CameraComponent ccamera;
	private boolean hcamera = true;
	private ClimbAbleComponent cclimbable;
	private boolean hclimbable = true;
	private CollectAbleComponent ccollectable;
	private boolean hcollectable = true;
	private CollectorComponent ccollector;
	private boolean hcollector = true;
	private CollideAbleComponent ccollideable;
	private boolean hcollideable = true;
	private ColliderFrameComponent ccolliderframe;
	private boolean hcolliderframe = true;
	private ControlComponent ccontrol;
	private boolean hcontrol = true;
	private DestroyAbleComponent cdestroyable;
	private boolean hdestroyable = true;
	private DestroyCollideComponent cdestroycollide;
	private boolean hdestroycollide = true;
	private DoorComponent cdoor;
	private boolean hdoor = true;
	private EraseAbleComponent ceraseable;
	private boolean heraseable = true;
	private ExitComponent cexit;
	private boolean hexit = true;
	private GravitySwitchComponent cgravityswitch;
	private boolean hgravityswitch = true;
	private LaserComponent claser;
	private boolean hlaser = true;
	private LaserTransportAbleComponent clasertransportable;
	private boolean hlasertransportable = true;
	private MirrorComponent cmirror;
	private boolean hmirror = true;
	private MissileLaunchComponent cmissilelaunch;
	private boolean hmissilelaunch = true;
	private MoveComponent cmove;
	private boolean hmove = true;
	private PathMoveComponent cpathmove;
	private boolean hpathmove = true;
	private PhysicsComponent cphysics;
	private boolean hphysics = true;
	private PortalActiveComponent cportalactive;
	private boolean hportalactive = true;
	private PortalComponent cportal;
	private boolean hportal = true;
	private PushPullAbleComponent cpushpull;
	private boolean hpushpull = true;
	private RenderImageComponent crenderimage;
	private boolean hrenderimage = true;
	private RenderTextComponent crendertext;
	private boolean hrendertext = true;
	private RenderTileComponent crendertile;
	private boolean hrendertile = true;
	private RobeAbleComponent crobeable;
	private boolean hrobeable = true;
	private ShiftPlattformComponent cshiftplattform;
	private boolean hshiftplattform = true;
	private SwitchComponent cswitchcomponent;
	private boolean hswitchcomponent = true;
	private TransformTileComponent ctransformtile;
	private boolean htransformtile = true;
	private TriggerAbleComponent ctriggerable;
	private boolean htriggerable = true;
	private TriggerFrameComponent ctriggerframe;
	private boolean htriggerframe = true;
	private WaterComponent cwater;
	private boolean hwater = true;
	private WaypointComponent cwaypoint;
	private boolean hwaypoint = true;
	private WeightComponent cweight;
	private boolean hweight = true;
	private FontaineComponent cfontaine;
	private boolean hfontaine = true;
	private FloaterComponent cfloater;
	private boolean hfloater = true;
	private MouseCursorComponent cmousecursor;
	private boolean hmousecursor = true;
	private LightComponent clight;
	private boolean hlight = true;
	private MenuComponent cmenu;
	private boolean hmenu = true;

	public static final int Z_BACKGROUND = 7;
	public static final int Z_BACK3 = 6;
	public static final int Z_BACK2 = 5;
	public static final int Z_BACK = 4;
	public static final int Z_MAIN = 3;
	public static final int Z_FRONT = 2;
	public static final int Z_FRONT2 = 1;
	public static final int Z_FOREGROUND = 0;

	private ArrayList<String> tagList;

	// private HashMap<String,Component> componentList;
	private ArrayList<Component> componentList;

	public GObject() {
		this.id = -1;
		componentList = new ArrayList<Component>();
		this.isSaveObject = false;
	}

	public GObject(int id, boolean isSaveObject) {
		this.id = id;
		componentList = new ArrayList<Component>();
		tagList = new ArrayList<String>();
		this.isSaveObject = isSaveObject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addComponent(Component comp) {
		comp.setGobject(this);
		componentList.add(comp);
		clearCache();
	}

	public boolean isPrefab() {
		return isPrefab;
	}

	public void setPrefab(boolean isPrefab) {
		this.isPrefab = isPrefab;
	}

	public TransformTileComponent getTransformTileComponent() {
		if (ctransformtile != null || htransformtile == false) {
			return ctransformtile;
		} else {
			ctransformtile = (TransformTileComponent) findComponent(TransformTileComponent.COMP_NAME);
			if (ctransformtile == null) {
				htransformtile = false;
			}
			return ctransformtile;
		}

	}

	public ActivateComponent getActivateComponent() {
		if (cactivate != null || hactivate == false) {
			return cactivate;
		} else {
			cactivate = (ActivateComponent) findComponent(ActivateComponent.COMP_NAME);
			if (cactivate == null) {
				hactivate = false;
			}
			return cactivate;
		}

	}

	public CameraComponent getCameraComponent() {
		if (ccamera != null || hcamera == false) {
			return ccamera;
		} else {
			ccamera = (CameraComponent) findComponent(CameraComponent.COMP_NAME);
			if (ccamera == null) {
				hcamera = false;
			}
			return ccamera;
		}

	}

	public ClimbAbleComponent getClimbAbleComponent() {
		if (cclimbable != null || hclimbable == false) {
			return cclimbable;
		} else {
			cclimbable = (ClimbAbleComponent) findComponent(ClimbAbleComponent.COMP_NAME);
			if (cclimbable == null) {
				hclimbable = false;
			}

			return cclimbable;
		}

	}

	public CollectAbleComponent getCollectAbleComponent() {
		if (ccollectable != null || hcollectable == false) {
			return ccollectable;
		} else {
			ccollectable = (CollectAbleComponent) findComponent(CollectAbleComponent.COMP_NAME);
			if (ccollectable == null) {
				hcollectable = false;
			}

			return ccollectable;
		}
	}

	public CollectorComponent getCollectorComponent() {
		if (ccollector != null || hcollector == false) {
			return ccollector;
		} else {
			ccollector = (CollectorComponent) findComponent(CollectorComponent.COMP_NAME);
			if (ccollector == null) {
				hcollector = false;
			}

			return ccollector;
		}
	}

	public CollideAbleComponent getCollideAbleComponent() {
		if (ccollideable != null || hcollideable == false) {
			return ccollideable;
		} else {
			ccollideable = (CollideAbleComponent) findComponent(CollideAbleComponent.COMP_NAME);
			if (ccollideable == null) {
				hcollideable = false;
			}

			return ccollideable;
		}
	}

	public ColliderFrameComponent getColliderFrameComponent() {
		if (ccolliderframe != null || hcolliderframe == false) {
			return ccolliderframe;
		} else {
			ccolliderframe = (ColliderFrameComponent) findComponent(ColliderFrameComponent.COMP_NAME);
			if (ccolliderframe == null) {
				hcolliderframe = false;
			}

			return ccolliderframe;
		}
	}

	public ControlComponent getControlComponent() {
		if (ccontrol != null || hcontrol == false) {
			return ccontrol;
		} else {
			ccontrol = (ControlComponent) findComponent(ControlComponent.COMP_NAME);
			if (ccontrol == null) {
				hcontrol = false;
			}

			return ccontrol;
		}
	}

	public DestroyAbleComponent getDestroyAbleComponent() {
		if (cdestroyable != null || hdestroyable == false) {
			return cdestroyable;
		} else {
			cdestroyable = (DestroyAbleComponent) findComponent(DestroyAbleComponent.COMP_NAME);
			if (cdestroyable == null) {
				hdestroyable = false;
			}

			return cdestroyable;
		}
	}

	public DestroyCollideComponent getDestroyCollideComponent() {
		if (cdestroycollide != null || hdestroycollide == false) {
			return cdestroycollide;
		} else {
			cdestroycollide = (DestroyCollideComponent) findComponent(DestroyCollideComponent.COMP_NAME);
			if (cdestroycollide == null) {
				hdestroycollide = false;
			}

			return cdestroycollide;
		}
	}

	public DoorComponent getDoorComponent() {
		if (cdoor != null || hdoor == false) {
			return cdoor;
		} else {
			cdoor = (DoorComponent) findComponent(DoorComponent.COMP_NAME);
			if (cdoor == null) {
				hdoor = false;
			}

			return cdoor;
		}
	}

	public EraseAbleComponent getEraseAbleComponent() {
		if (ceraseable != null || heraseable == false) {
			return ceraseable;
		} else {
			ceraseable = (EraseAbleComponent) findComponent(EraseAbleComponent.COMP_NAME);
			if (ceraseable == null) {
				heraseable = false;
			}

			return ceraseable;
		}
	}

	public ExitComponent getExitComponent() {
		if (cexit != null || hexit == false) {
			return cexit;
		} else {
			cexit = (ExitComponent) findComponent(ExitComponent.COMP_NAME);
			if (cexit == null) {
				hexit = false;
			}

			return cexit;
		}
	}

	public GravitySwitchComponent getGravitySwitchComponent() {
		if (cgravityswitch != null || hgravityswitch == false) {
			return cgravityswitch;
		} else {
			cgravityswitch = (GravitySwitchComponent) findComponent(GravitySwitchComponent.COMP_NAME);
			if (cgravityswitch == null) {
				hgravityswitch = false;
			}

			return cgravityswitch;
		}
	}

	public LaserComponent getLaserComponent() {
		if (claser != null || hlaser == false) {
			return claser;
		} else {
			claser = (LaserComponent) findComponent(LaserComponent.COMP_NAME);
			if (claser == null) {
				hlaser = false;
			}

			return claser;
		}
	}

	public LaserTransportAbleComponent getLaserTransportAbleComponent() {
		if (clasertransportable != null || hlasertransportable == false) {
			return clasertransportable;
		} else {
			clasertransportable = (LaserTransportAbleComponent) findComponent(LaserTransportAbleComponent.COMP_NAME);
			if (clasertransportable == null) {
				hlasertransportable = false;
			}

			return clasertransportable;
		}
	}

	public MirrorComponent getMirrorComponent() {
		if (cmirror != null || hmirror == false) {
			return cmirror;
		} else {
			cmirror = (MirrorComponent) findComponent(MirrorComponent.COMP_NAME);
			if (cmirror == null) {
				hmirror = false;
			}

			return cmirror;
		}
	}

	public MissileLaunchComponent getMissileLaunchComponent() {
		if (cmissilelaunch != null || hmissilelaunch == false) {
			return cmissilelaunch;
		} else {
			cmissilelaunch = (MissileLaunchComponent) findComponent(MissileLaunchComponent.COMP_NAME);
			if (cmissilelaunch == null) {
				hmissilelaunch = false;
			}

			return cmissilelaunch;
		}
	}

	public MoveComponent getMoveComponent() {
		if (cmove != null || hmove == false) {
			return cmove;
		} else {
			cmove = (MoveComponent) findComponent(MoveComponent.COMP_NAME);
			if (cmove == null) {
				hmove = false;
			}

			return cmove;
		}
	}

	public PathMoveComponent getPathMoveComponent() {
		if (cpathmove != null || hpathmove == false) {
			return cpathmove;
		} else {
			cpathmove = (PathMoveComponent) findComponent(PathMoveComponent.COMP_NAME);
			if (cpathmove == null) {
				hpathmove = false;
			}

			return cpathmove;
		}
	}

	public PhysicsComponent getPhysicsComponent() {
		if (cphysics != null || hphysics == false) {
			return cphysics;
		} else {
			cphysics = (PhysicsComponent) findComponent(PhysicsComponent.COMP_NAME);
			if (cphysics == null) {
				hphysics = false;
			}

			return cphysics;
		}
	}

	public PortalActiveComponent getPortalActiveComponent() {
		if (cportalactive != null || hportalactive == false) {
			return cportalactive;
		} else {
			cportalactive = (PortalActiveComponent) findComponent(PortalActiveComponent.COMP_NAME);
			if (cportalactive == null) {
				hportalactive = false;
			}

			return cportalactive;
		}
	}

	public PortalComponent getPortalComponent() {
		if (cportal != null || hportal == false) {
			return cportal;
		} else {
			cportal = (PortalComponent) findComponent(PortalComponent.COMP_NAME);
			if (cportal == null) {
				hportal = false;
			}

			return cportal;
		}
	}

	public PushPullAbleComponent getPushPullAbleComponent() {
		if (cpushpull != null || hpushpull == false) {
			return cpushpull;
		} else {
			cpushpull = (PushPullAbleComponent) findComponent(PushPullAbleComponent.COMP_NAME);
			if (cpushpull == null) {
				hpushpull = false;
			}

			return cpushpull;
		}
	}

	public RenderImageComponent getRenderImageComponent() {
		if (crenderimage != null || hrenderimage == false) {
			return crenderimage;
		} else {
			crenderimage = (RenderImageComponent) findComponent(RenderImageComponent.COMP_NAME);
			if (crenderimage == null) {
				hrenderimage = false;
			}

			return crenderimage;
		}
	}

	public RenderTextComponent getRenderTextComponent() {
		if (crendertext != null || hrendertext == false) {
			return crendertext;
		} else {
			crendertext = (RenderTextComponent) findComponent(RenderTextComponent.COMP_NAME);
			if (crendertext == null) {
				hrendertext = false;
			}

			return crendertext;
		}
	}

	public RenderTileComponent getRenderTileComponent() {
		if (crendertile != null || hrendertile == false) {
			return crendertile;
		} else {
			crendertile = (RenderTileComponent) findComponent(RenderTileComponent.COMP_NAME);
			if (crendertile == null) {
				hrendertile = false;
			}

			return crendertile;
		}
	}

	public RobeAbleComponent getRobeAbleComponent() {
		if (crobeable != null || hrobeable == false) {
			return crobeable;
		} else {
			crobeable = (RobeAbleComponent) findComponent(RobeAbleComponent.COMP_NAME);
			if (crobeable == null) {
				hrobeable = false;
			}

			return crobeable;
		}
	}

	public ShiftPlattformComponent getShiftPlattformComponent() {
		if (cshiftplattform != null || hshiftplattform == false) {
			return cshiftplattform;
		} else {
			cshiftplattform = (ShiftPlattformComponent) findComponent(ShiftPlattformComponent.COMP_NAME);
			if (cshiftplattform == null) {
				hshiftplattform = false;
			}

			return cshiftplattform;
		}
	}

	public TriggerAbleComponent getTriggerAbleComponent() {
		if (ctriggerable != null || htriggerable == false) {
			return ctriggerable;
		} else {
			ctriggerable = (TriggerAbleComponent) findComponent(TriggerAbleComponent.COMP_NAME);
			if (ctriggerable == null) {
				htriggerable = false;
			}

			return ctriggerable;
		}
	}

	public TriggerFrameComponent getTriggerFrameComponent() {
		if (ctriggerframe != null || htriggerframe == false) {
			return ctriggerframe;
		} else {
			ctriggerframe = (TriggerFrameComponent) findComponent(TriggerFrameComponent.COMP_NAME);
			if (ctriggerframe == null) {
				htriggerframe = false;
			}

			return ctriggerframe;
		}
	}

	public WaterComponent getWaterComponent() {
		if (cwater != null || hwater == false) {
			return cwater;
		} else {
			cwater = (WaterComponent) findComponent(WaterComponent.COMP_NAME);
			if (cwater == null) {
				hwater = false;
			}

			return cwater;
		}
	}

	public WaypointComponent getWaypointComponent() {
		if (cwaypoint != null || hwaypoint == false) {
			return cwaypoint;
		} else {
			cwaypoint = (WaypointComponent) findComponent(WaypointComponent.COMP_NAME);
			if (cwaypoint == null) {
				hwaypoint = false;
			}

			return cwaypoint;
		}
	}

	public WeightComponent getWeightComponent() {
		if (cweight != null || hweight == false) {
			return cweight;
		} else {
			cweight = (WeightComponent) findComponent(WeightComponent.COMP_NAME);
			if (cweight == null) {
				hweight = false;
			}

			return cweight;
		}
	}

	public SwitchComponent getSwitchComponent() {
		if (cswitchcomponent != null || hswitchcomponent == false) {
			return cswitchcomponent;
		} else {
			cswitchcomponent = (SwitchComponent) findComponent(SwitchComponent.COMP_NAME);
			if (cswitchcomponent == null) {
				hswitchcomponent = false;
			}

			return cswitchcomponent;
		}
	}

	public FontaineComponent getFontaineComponent() {
		if (cfontaine != null || hfontaine == false) {
			return cfontaine;
		} else {
			cfontaine = (FontaineComponent) findComponent(FontaineComponent.COMP_NAME);
			if (cfontaine == null) {
				hfontaine = false;
			}

			return cfontaine;
		}
	}

	public FloaterComponent getFloaterComponent() {
		if (cfloater != null || hfloater == false) {
			return cfloater;
		} else {
			cfloater = (FloaterComponent) findComponent(FloaterComponent.COMP_NAME);
			if (cfloater == null) {
				hfloater = false;
			}

			return cfloater;
		}
	}

	public MouseCursorComponent getMouseCursorComponent() {
		if (cmousecursor != null || hmousecursor == false) {
			return cmousecursor;
		} else {
			cmousecursor = (MouseCursorComponent) findComponent(MouseCursorComponent.COMP_NAME);
			if (cmousecursor == null) {
				hmousecursor = false;
			}

			return cmousecursor;
		}
	}

	public LightComponent getLightComponent() {
		if (clight != null || hlight == false) {
			return clight;
		} else {
			clight = (LightComponent) findComponent(LightComponent.COMP_NAME);
			if (clight == null) {
				hlight = false;
			}

			return clight;
		}

	}

	public MenuComponent getMenuComponent() {
		if (cmenu != null || hmenu == false) {
			return cmenu;
		} else {
			cmenu = (MenuComponent) findComponent(MenuComponent.COMP_NAME);
			if (cmenu == null) {
				hmenu = false;
			}

			return cmenu;
		}
	}

	public Component findComponent(int id) {

		for (Iterator<Component> iterator = componentList.iterator(); iterator.hasNext();) {
			Component comp = iterator.next();
			if (comp.getId() == id) {
				return comp;
			}
		}
		return null;
	}

	public ArrayList<Component> getComponentList() {
		return componentList;
	}

	public void setComponentList(ArrayList<Component> componentList) {
		this.componentList = componentList;
	}

	public void clearCache() {
		ctransformtile = null;
		cactivate = null;
		ccamera = null;
		cclimbable = null;
		ccollectable = null;
		ccollector = null;
		ccollideable = null;
		ccolliderframe = null;
		ccontrol = null;
		cdestroyable = null;
		cdestroycollide = null;
		cdoor = null;
		ceraseable = null;
		cexit = null;
		cgravityswitch = null;
		claser = null;
		clasertransportable = null;
		cmirror = null;
		cmissilelaunch = null;
		cmove = null;
		cpathmove = null;
		cphysics = null;
		cportalactive = null;
		cportal = null;
		cpushpull = null;
		crenderimage = null;
		crendertext = null;
		crendertile = null;
		crobeable = null;
		cshiftplattform = null;
		cswitchcomponent = null;
		ctransformtile = null;
		ctriggerable = null;
		ctriggerframe = null;
		cwater = null;
		cwaypoint = null;
		cweight = null;
		cfontaine = null;
		cfloater = null;
		cmousecursor = null;
		clight = null;
		cmenu = null;

		htransformtile = true;
		hactivate = true;
		hcamera = true;
		hclimbable = true;
		hcollectable = true;
		hcollector = true;
		hcollideable = true;
		hcolliderframe = true;
		hcontrol = true;
		hdestroyable = true;
		hdestroycollide = true;
		hdoor = true;
		heraseable = true;
		hexit = true;
		hgravityswitch = true;
		hlaser = true;
		hlasertransportable = true;
		hmirror = true;
		hmissilelaunch = true;
		hmove = true;
		hpathmove = true;
		hphysics = true;
		hportalactive = true;
		hportal = true;
		hpushpull = true;
		hrenderimage = true;
		hrendertext = true;
		hrendertile = true;
		hrobeable = true;
		hshiftplattform = true;
		hswitchcomponent = true;
		htransformtile = true;
		htriggerable = true;
		htriggerframe = true;
		hwater = true;
		hwaypoint = true;
		hweight = true;
		hfontaine = true;
		hfloater = true;
		hmousecursor = true;
		hlight = true;
		hmenu = true;

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		GObject newObj = (GObject) super.clone();

		newObj.componentList = new ArrayList<Component>();
		newObj.clearCache();
		newObj.initClone();

		for (Iterator<Component> iterator = componentList.iterator(); iterator.hasNext();) {
			Component comp = iterator.next();

			Component newComp = (Component) comp.clone();
			newComp.initClone();
			newObj.addComponent(newComp);
		}

		newObj.tagList = new ArrayList<String>();

		for (Iterator<String> iterator = tagList.iterator(); iterator.hasNext();) {
			String tag = iterator.next();
			newObj.getTagList().add(tag);
		}

		return newObj;
	}

	public void initClone() {

	}

	public boolean hasTag(String tag) {
		if (tag == null) {
			return false;
		}

		for (Iterator<String> iterator = tagList.iterator(); iterator.hasNext();) {
			String objtag = iterator.next();
			if (tag.equals(objtag)) {
				return true;
			}
		}
		return false;
	}

	public char getSceneScriptIndex() {
		return sceneScriptIndex;
	}

	public void setSceneScriptIndex(char sceneScriptIndex) {
		this.sceneScriptIndex = sceneScriptIndex;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public ArrayList<String> getTagList() {
		return tagList;
	}

	public void setTagList(ArrayList<String> tagList) {
		this.tagList = tagList;
	}

	public boolean isSaveObject() {
		return isSaveObject;
	}

	public void setSaveObject(boolean isSaveObject) {
		this.isSaveObject = isSaveObject;
	}

	@Override
	public int compareTo(GObject go2) {
		TransformTileComponent tileComp1 = this.getTransformTileComponent();
		TransformTileComponent tileComp2 = go2.getTransformTileComponent();
		if (tileComp1 != null && tileComp2 != null) {
			if (tileComp2.getZ() > tileComp1.getZ()) {
				return 1;
			} else if (tileComp2.getZ() < tileComp1.getZ()) {
				return -1;
			} else {
				if (tileComp2.getPriority() > tileComp1.getPriority()) {
					return -1;
				} else if (tileComp2.getPriority() < tileComp1.getPriority()) {
					return 1;
				} else {
					if (tileComp2.getY() > tileComp1.getY()) {
						return 1;
					} else if (tileComp2.getY() < tileComp1.getY()) {
						return -1;
					} else {
						if (tileComp2.getX() > tileComp1.getX()) {
							return 1;
						} else if (tileComp2.getX() < tileComp1.getX()) {
							return -1;
						} else {
							return 0;
						}
					}
				}
			}
		} else {
			return 0;
		}
	}

}
