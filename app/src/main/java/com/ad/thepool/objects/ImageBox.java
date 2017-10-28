package com.ad.thepool.objects;

import com.ad.thepool.GObject;
import com.ad.thepool.components.RenderImageComponent;
import com.ad.thepool.components.TransformTileComponent;

public class ImageBox extends GObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1524245286764190129L;

	public ImageBox(int id, String filename) {
		super(id, true);
		TransformTileComponent trans = new TransformTileComponent('I', true, GObject.Z_BACK);
		RenderImageComponent renderimg = new RenderImageComponent(filename, true);
		addComponent(trans);
		addComponent(renderimg);

		trans.setXY(1, 0, false);

	}

}
