package strategy.zpeng.component;

import strategy.Piece;

public class PieceImpl implements Piece{

	private PieceColor color;
	private PieceType type;
	
	/**
	 * Constructor of PieceImpl
	 * @param color is the color of this object
	 * @param type is the type of this object
	 */
	public PieceImpl(PieceColor color, PieceType type) {
		this.color = color;
		this.type = type;
	}
	
	/*
	 * (non-Javadoc) Get the piece color
	 * @see strategy.Piece#getPieceColor()
	 */
	@Override
	public PieceColor getPieceColor() {
		return this.color;
	}
	
	/*
	 * (non-Javadoc) Get the piece type
	 * @see strategy.Piece#getPieceType()
	 */
	@Override
	public PieceType getPieceType() {
		return this.type;
	}
}
