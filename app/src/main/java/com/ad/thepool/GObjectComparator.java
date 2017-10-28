package com.ad.thepool;

import java.util.Comparator;

import com.ad.thepool.components.TransformTileComponent;

public class GObjectComparator implements Comparator<GObject> {
	@Override
	public int compare(GObject go1, GObject go2) {
		TransformTileComponent tileComp1 = go1.getTransformTileComponent();
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
