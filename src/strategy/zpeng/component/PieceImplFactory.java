package strategy.zpeng.component;

import strategy.Piece;

public class PieceImplFactory {

	/**
	 * Make a piece
	 * @param piece is a general piece
	 * @return the piece of PieceImpl
	 */
	public static PieceImpl makePieceImpl(Piece piece) {
		return new PieceImpl(piece.getPieceColor(), piece.getPieceType());
	}
}
