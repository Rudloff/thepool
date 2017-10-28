package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.TileMap;
import com.ad.thepool.components.TransformTileComponent;
import com.ad.thepool.components.TriggerAbleComponent;
import com.ad.thepool.components.WaypointComponent;

public class Waypoint extends GObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -843300706910010007L;

	public Waypoint(int id, TileMap tilemap) {
		super(id, true);
		TransformTileComponent tiletransform = new TransformTileComponent('.', true, Z_BACK2);
		TriggerAbleComponent trigger = new TriggerAbleComponent(true);
		WaypointComponent waypoint = new WaypointComponent(true, 0, "player");
		addComponent(tiletransform);
		addComponent(trigger);
		addComponent(waypoint);
		getTagList().add("waypoint");
	}
}
