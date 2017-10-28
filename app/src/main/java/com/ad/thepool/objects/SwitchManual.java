package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerAbleComponent;

public class SwitchManual extends GObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1089928652075107374L;

	public SwitchManual(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('r', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 88, true); // 89
		SwitchComponent switchC = new SwitchComponent(true, SwitchComponent.SWITCH_OFF, "std", SwitchComponent.TYPE_MANUAL, "Activate Switch", "Deactivate Switch", "Switch activated", "Switch deactivated", "");
		TriggerAbleComponent trigger = new TriggerAbleComponent(true);
		LightComponent light = new LightComponent(true, 2, -1, 0, LightComponent.MODE_NO, SwitchComponent.RED1, SwitchComponent.RED2, 30, LightComponent.MODE_STEP);

		addComponent(tiletransform);
		addComponent(light);
		addComponent(rendertile);
		addComponent(switchC); // after render
		addComponent(trigger);

		Animation animOn = new Animation(89, 89, 0, Animation.TYPE_STILL, true);
		Animation animOff = new Animation(88, 88, 0, Animation.TYPE_STILL, true);

		rendertile.getAnimationList().put("on", animOn);
		rendertile.getAnimationList().put("off", animOff);

		rendertile.setActiveAnimation("off", true);

	}

}
