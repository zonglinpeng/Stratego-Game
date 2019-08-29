package strategy.zpeng.game;

import strategy.Board.SquareType;
import strategy.Piece;
import strategy.Piece.PieceColor;
import strategy.Piece.PieceType;

import strategy.StrategyGame.MoveResult;
import strategy.StrategyGame.Version;
import strategy.zpeng.component.BoardImpl;
import strategy.zpeng.component.PieceImpl;

import static strategy.Piece.PieceColor.*;
import static strategy.Piece.PieceType.*;
import static strategy.StrategyGame.MoveResult.*;
import static strategy.Board.SquareType.*;
import static strategy.StrategyGame.Version.*;

import java.util.HashMap;
import java.util.Map;

public class Rules {
	
	private Map<PieceType, Integer> rank;
	
	/**
	 * Constructor where set up the rank map
	 */
	public Rules () {
		rank = new HashMap<PieceType, Integer>();
		rank.put(MARSHAL, 12);
		rank.put(GENERAL, 11);
		rank.put(COLONEL, 10);
		rank.put(MAJOR, 9);
		rank.put(CAPTAIN, 8);
		rank.put(LIEUTENANT, 7);
		rank.put(SERGEANT, 6);
		rank.put(MINER, 5);
		rank.put(SCOUT, 4);
		rank.put(SPY, 3);
		rank.put(BOMB, 2);
		rank.put(FLAG, 1);
	}
	
	/**
	 * Any movable pieces remains on one and the other side
	 * @param board of the current game
	 * @param whoseTurn of either side of the game
	 * @return the game status
	 */
	public MoveResult isMovableRemaining(BoardImpl board, PieceColor whoseTurn, int size) {
		whoseTurn = whoseTurn == RED? BLUE: RED;
		for(int r = 0; r < size; r++) {
			for(int c = 0; c < size; c++) {
				Piece curPiece = board.getPieceAt(r, c);
				PieceType curPieceType = curPiece!= null ? curPiece.getPieceType(): null;
				PieceColor curPieceColor = curPiece!= null ? curPiece.getPieceColor(): null;
				//movable type 
				if(curPieceType != FLAG && curPieceType != BOMB && curPieceColor == whoseTurn) {
					//up
					boolean boundCheck = r < size - 1;
					Piece surPiece = board.getPieceAt(r+1, c);
					PieceColor surPieceColor = surPiece!=null? surPiece.getPieceColor(): null;
					boolean colorCheck = surPieceColor != whoseTurn;
					boolean isChoked = isChoked(board, r+1, c, r+1, c);
					boolean upCheck = boundCheck && colorCheck && !isChoked;			
					//down
					boundCheck = r > 1;
					surPiece = board.getPieceAt(r-1, c);
					surPieceColor = surPiece!=null? surPiece.getPieceColor(): null;
					colorCheck = surPieceColor != whoseTurn;
					isChoked = isChoked(board, r-1, c, r-1, c);
					boolean downCheck = boundCheck && colorCheck && !isChoked;
					//left
					boundCheck = c > 1;
					surPiece = board.getPieceAt(r, c-1);
					surPieceColor = surPiece!=null? surPiece.getPieceColor(): null;
					colorCheck = surPieceColor != whoseTurn;
					isChoked = isChoked(board, r, c-1, r, c-1);
					boolean leftCheck = boundCheck && colorCheck && !isChoked;
					//right
					boundCheck = c < size - 1;
					surPiece = board.getPieceAt(r, c+1);
					surPieceColor = surPiece!=null? surPiece.getPieceColor(): null;
					colorCheck = surPieceColor != whoseTurn;
					isChoked = isChoked(board, r, c+1, r, c+1);
					boolean rightCheck = boundCheck && colorCheck && !isChoked;

					//if not trapped
					if(upCheck || downCheck || leftCheck || rightCheck) {
						return OK;
					}
				}					
			}
		}
		return whoseTurn == RED ? BLUE_WINS : RED_WINS;
	}
	
	/**
	 * Test if a piece can defeat the target piece
	 * @param master is the current piece
	 * @param slave is the target piece
	 * @return the move result of the strike
	 */
	public MoveResult canDefeat(PieceImpl master, PieceImpl slave) {
		boolean canDefeat = false;
		int masterRank = rank.get(master.getPieceType());
		int slaveRank = rank.get(slave.getPieceType());
		PieceColor masterColor = master.getPieceColor();
		PieceColor slaveColor = slave.getPieceColor();
		MoveResult masterResult = masterColor == RED? STRIKE_RED: STRIKE_BLUE;
		MoveResult slaveResult = slaveColor == RED? STRIKE_RED: STRIKE_BLUE;
		//Equal Rank
		if(masterRank == slaveRank) {
			return OK;
		}

		switch(masterRank) {
		//MINER
		case 5:
			canDefeat = slaveRank >= 2 && slaveRank <= 4;
			break;
		//SPY
		case 3:
			canDefeat = slaveRank == 12;
			break;
		//Other Pieces
		default:
			canDefeat = slaveRank < masterRank && slaveRank != 2;
			break;
		}
		
		return canDefeat ? masterResult : slaveResult;
	}

	/**
	 * Test if a strike is valid
	 * @param from is the piece of the current
	 * @param to the is target piece to strike
	 * @param from row
	 * @param from column
	 * @param to row
	 * @param to column
	 * @return true if the strike can be made
	 */
	public boolean isValidAttack(BoardImpl board, int fr, int fc, int tr, int tc, Version version) {
		boolean isValidAttackMove = false;
		PieceImpl from = board.getPieceAt(fr, fc);
		PieceImpl to = board.getPieceAt(tr, tc);
		if(from.getPieceType() == SCOUT && version == EPSILON) {
			boolean isValidDistance = (fr == tr) && (Math.abs(fc - tc) <= 3) // three-step horizontal
					|| (fc == tc) && (Math.abs(fr - tr) <= 3); // three-step vertical
			isValidAttackMove = isValidDistance && !isBlocked(board, fr, fc, tr, tc);
		}else {
			isValidAttackMove = (fr == tr) && (Math.abs(fc - tc) == 1) // one-step horizontal
					|| (fc == tc) && (Math.abs(fr - tr) == 1); // one-step vertical
		}
		return from.getPieceColor() != to.getPieceColor() && isValidAttackMove;
	}
	
	/**
	 * Test if a move is valid
	 * @param board is the game board
	 * @param type is the type of the current piece
	 * @param from row
	 * @param from column
	 * @param to row
	 * @param to column
	 * @return true if the move can be made
	 */
	public boolean isValidMove(BoardImpl board, PieceType type, int size, int fr, int fc, int tr, int tc) {
		boolean isValidMove = false;
		if (!isMoveable(type) || isOutOfBoundary(size, fr, fc, tr, tc) 
				|| isUnmoved(fr, fc, tr, tc) || isChoked(board, fr, fc, tr, tc)) {
			return isValidMove;
		}
		//SCOUT
		if(type == SCOUT) {
			boolean isStraightPath = fr == tr || fc == tc;
			isValidMove = !isBlocked(board, fr, fc, tr, tc) && isStraightPath;
		}
		//Other Pieces
		else {
			isValidMove = (fr == tr) && (Math.abs(fc - tc) == 1) // one-step horizontal
				|| (fc == tc) && (Math.abs(fr - tr) == 1); // one-step vertical
		}
		return isValidMove;
	}
	
	/**
	 * HELPER: Check is a long path of SCOUT is blocked
	 * @param board of the game
	 * @param fromRow
	 * @param fromCol
	 * @param toRow
	 * @param toCol
	 * @return true if the path is blocked by piece or choke point
	 */
	private boolean isBlocked (BoardImpl board, int fromRow, int fromCol, int toRow, int toCol) 
	{
		boolean isBlocked = false;
		int iterateCol = fromCol;
		int iterateRow = fromRow;

		do{
			if(iterateCol < toCol)
			{
				iterateCol++;
			}
			else if(iterateCol > toCol)
			{
				iterateCol--;
			}
			if(iterateRow < toRow)
			{
				iterateRow++;
			}
			else if(iterateRow > toRow)
			{
				iterateRow--;
			}
			//the square is taken and is not the destination
			if(isSquareOccupied(board, iterateRow, iterateCol) && (iterateCol != toCol || iterateRow != toRow))
			{
				isBlocked = true;
				break;
			}
		}while(Math.abs(iterateCol - toCol) > 1 || (Math.abs(iterateRow - toRow) > 1));
		return isBlocked;
	}
	
	/**
	 * HELPER: Check if a square is occupied by piece or choke point
	 * @param board of the game
	 * @param r the target row
	 * @param c the target column
	 * @return true the square is occupied
	 */
	private boolean isSquareOccupied(BoardImpl board, int r, int c) {
		return board.getPieceAt(r, c) != null || board.getSquareTypeAt(r, c) == CHOKE;
	}
	
	/**
	 * HELPER: Test if a piece is moveble
	 * @param type is the piece type
	 * @return if the piece is moveable piece
	 */
	private boolean isMoveable(PieceType type) {
		return type != FLAG && type != BOMB;
	}
	
	/**
	 * HELPER: Test if the coordinate is of out the Beta board
	 * @param from row
	 * @param from column
	 * @param to row
	 * @param to column
	 * @return true if the coordinate is invalid
	 */
	private boolean isOutOfBoundary(int size, int fr, int fc, int tr, int tc) {
		size--;
		return tr > size || tr < 0 || tc > size || tc < 0 
				||fr > size || fr < 0 || fc > size || fc < 0;
	}
	
	/**
	 * HELPER: Test if the destination and the source is the same
	 * @param from row
	 * @param from column
	 * @param to row
	 * @param to column
	 * @return true if the piece is not moved
	 */
	private boolean isUnmoved(int fr, int fc, int tr, int tc) {
		return fr == tr && fc == tc;
	}
	
	/**
	 * HELPER: Check if the move leads to a choke
	 * @param board of the game
	 * @param from row
	 * @param from column
	 * @param to row
	 * @param to column
	 * @return true if the move is choked
	 */
	private boolean isChoked(BoardImpl board, int fr, int fc, int tr, int tc) {
		SquareType fromSquareType = board.getSquareTypeAt(fr, fc);
		SquareType toSquareType = board.getSquareTypeAt(tr, tc);
		return fromSquareType == CHOKE || toSquareType == CHOKE;
	}
}
