package com.ad.thepool.components;

public class MirrorComponent extends Component implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -483699685403493636L;

	public static final int COMP_NAME = 15;

	public static final int TURN_RIGHT = 1;
	public static final int TURN_LEFT = 2;
	public static final int TURN_THROUGH = 3;

	private int turn;

	public MirrorComponent(boolean isActive, int turn) {
		super(COMP_NAME, isActive);
		this.turn = turn;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}
}
