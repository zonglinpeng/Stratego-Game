package strategy.zpeng.lineup;

import java.util.LinkedList;
import java.util.List;

import strategy.Piece.PieceColor;
import strategy.Piece.PieceType;
import strategy.zpeng.component.PieceImpl;

public class RedLineUp implements LineUp{

	/*
	 * Line up constructor
	 */
	public RedLineUp() {}
	
	/**
	 * Convert the input to a list of pieces
	 * @param the piece color
	 * @param ... all the piece types
	 * @return a list of created pieces
	 */
	@Override
	public List<PieceImpl> makeLineUp (PieceColor color, PieceType ...type){
		List<PieceImpl> list= new LinkedList<PieceImpl>();
		int i = 0;
		int max = type.length;
		while (i < max) {
			PieceType theType = type[i++];
			list.add(new PieceImpl(color, theType));
		}
		return list;
	}
}
