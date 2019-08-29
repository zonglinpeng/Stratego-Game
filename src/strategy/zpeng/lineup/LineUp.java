package strategy.zpeng.lineup;

import java.util.List;

import strategy.Piece.PieceColor;
import strategy.Piece.PieceType;
import strategy.zpeng.component.PieceImpl;

public interface LineUp {

	/*
	 * Get a list of pieces
	 */
	public List<PieceImpl> makeLineUp (PieceColor color, PieceType ...type);
}
