package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.LightComponent;
import com.ad.thepool.components.RenderTileComponent;
import com.ad.thepool.components.SwitchComponent;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerAbleComponent;
import com.ad.thepool.wrapper.GraphicsWrapper;

public class Checkpoint extends GObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7042736722429109346L;

	public Checkpoint(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('P', true, Z_BACK);
		RenderTileComponent rendertile = new RenderTileComponent(tilemap, 53, true); // 89
		SwitchComponent switchC = new SwitchComponent(true, SwitchComponent.SWITCH_OFF, "checkpoint", SwitchComponent.TYPE_ONCE, "Save Checkpoint", "", "Checkpoint saved", "", "Checkpoint restored");
		TriggerAbleComponent trigger = new TriggerAbleComponent(true);
		LightComponent light = new LightComponent(true, 2, GraphicsWrapper.getRGB(0, 200, 100, 16));

		addComponent(tiletransform);
		addComponent(rendertile);
		addComponent(switchC); // after render
		addComponent(trigger);
		addComponent(light);
	}

}
