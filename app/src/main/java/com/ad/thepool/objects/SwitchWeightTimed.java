package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.Animation;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerAbleComponent;

public class SwitchWeightTimed extends GObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4176501445128104583L;

	public SwitchWeightTimed(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('W', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 105, true); // 89
		SwitchComponent switchC = new SwitchComponent(true, SwitchComponent.SWITCH_OFF, "std", SwitchComponent.TYPE_HEAVYW_TIMED, "Activate Switch", "Deactivate Switch", "Switch activated", "Switch deactivated", "", 50);
		TriggerAbleComponent trigger = new TriggerAbleComponent(true);
		LightComponent light = new LightComponent(true, 2, -1, 0, LightComponent.MODE_NO, SwitchComponent.RED1, SwitchComponent.RED2, 4, LightComponent.MODE_SINUS);

		addComponent(light);
		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(switchC); // after render
		addComponent(trigger);

		Animation animOn = new Animation(105, 105, 0, Animation.TYPE_STILL, true);
		Animation animOff = new Animation(106, 106, 0, Animation.TYPE_STILL, true);
		Animation animBlink = new Animation(105, 106, 8, Animation.TYPE_LOOP, true);

		rendertile.getAnimationList().put("on", animOn);
		rendertile.getAnimationList().put("off", animOff);
		rendertile.getAnimationList().put("blink", animBlink);

		rendertile.setActiveAnimation("off", true);

	}

}
