/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.zpeng.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import strategy.Board;
import strategy.Piece;
import strategy.Piece.PieceColor;
import strategy.Piece.PieceType;
import strategy.StrategyGame.Version;
import strategy.zpeng.component.Square;

import static strategy.Board.SquareType.*;
import static strategy.Piece.PieceColor.*;
import static strategy.Piece.PieceType.*;
import static strategy.zpeng.component.PieceImplFactory.makePieceImpl;
import static strategy.zpeng.component.SquareFactory.makeSquare;

import strategy.StrategyException;

/**
 * Description
 * @version Mar 18, 2019
 */
public class BoardImpl implements Board
{

	private Map<Square, PieceImpl> theBoard;
	private Map<PieceType, Integer> redPieceNumbers;
	private Map<PieceType, Integer> bluePieceNumbers;
	
	/**
	 * Copy constructor for GAMMA & DELTA: convert a board to a boardImpl
	 * @param board is the general board
	 * @param row the height of the board
	 * @param col the width of the board
	 */
	public BoardImpl(Board board, int row, int col, Version version) {
		theBoard = new HashMap<Square, PieceImpl>();
		redPieceNumbers = new HashMap<PieceType, Integer>();
		bluePieceNumbers = new HashMap<PieceType, Integer>();

		for(int i = 0; i < row; ++i) {
			for(int j = 0; j < col; ++j) {
				Piece thePiece = board.getPieceAt(i, j);
				if(thePiece != null) {	
					this.putPieceAt(makeSquare(i, j), makePieceImpl(thePiece));
				}
			}
		}
		switch(version) {
		case EPSILON:
		case DELTA:
			redPieceNumbers.put(MARSHAL, 1);
			redPieceNumbers.put(GENERAL, 1);
			redPieceNumbers.put(COLONEL, 2);
			redPieceNumbers.put(MAJOR, 3);
			redPieceNumbers.put(CAPTAIN, 4);
			redPieceNumbers.put(LIEUTENANT, 4);
			redPieceNumbers.put(SERGEANT, 4);
			redPieceNumbers.put(MINER, 5);
			redPieceNumbers.put(SCOUT, 8);
			redPieceNumbers.put(SPY, 1);
			redPieceNumbers.put(BOMB, 6);
			redPieceNumbers.put(FLAG, 1);
			bluePieceNumbers.put(MARSHAL, 1);
			bluePieceNumbers.put(GENERAL, 1);
			bluePieceNumbers.put(COLONEL, 2);
			bluePieceNumbers.put(MAJOR, 3);
			bluePieceNumbers.put(CAPTAIN, 4);
			bluePieceNumbers.put(LIEUTENANT, 4);
			bluePieceNumbers.put(SERGEANT, 4);
			bluePieceNumbers.put(MINER, 5);
			bluePieceNumbers.put(SCOUT, 8);
			bluePieceNumbers.put(SPY, 1);
			bluePieceNumbers.put(BOMB, 6);
			bluePieceNumbers.put(FLAG, 1);
			break;
		default: //BETA & GAMMA
			redPieceNumbers.put(MARSHAL, 1);
			redPieceNumbers.put(COLONEL, 2);
			redPieceNumbers.put(CAPTAIN, 2);
			redPieceNumbers.put(LIEUTENANT, 3);
			redPieceNumbers.put(SERGEANT, 3);
			redPieceNumbers.put(FLAG, 1);
			bluePieceNumbers.put(MARSHAL, 1);
			bluePieceNumbers.put(COLONEL, 2);
			bluePieceNumbers.put(CAPTAIN, 2);
			bluePieceNumbers.put(LIEUTENANT, 3);
			bluePieceNumbers.put(SERGEANT, 3);
			bluePieceNumbers.put(FLAG, 1);
			break;
		}
		placeChokePoint(version);
		checkBoardSetup(version, row, col);
	}
	
	/**
	 * Copy constructor 1.5: convert a board to a boardImpl
	 * @param board is the general board
	 * @param row the height of the board
	 * @param col the width of the board
	 * @return the converted board as type of BoardImpl
	 */
//	public static BoardImpl convertBoard(Board board, int row, int col) {
//		theBoard = new HashMap<Square, PieceImpl>();
//
//		if(board instanceof BoardImpl) {
//			return (BoardImpl) board;
//		}
//		for(int i = 0; i < row; ++i) {
//			for(int j = 0; j < col; ++j) {
//				Piece thePiece = board.getPieceAt(i, j);
//				if(thePiece != null) {	
//					myBoard.putPieceAt(makeSquare(i, j), makePieceImpl(thePiece));
//				}
//			}
//		}
//		return myBoard;
//	}
	
	/**
	 * Copy constructor 2: convert the list of red and blue pieces list to board
	 * @param row the height of board
	 * @param col the width of the board
	 * @param red the list of red pieces
	 * @param blue the list of blue pieces
	 * @throws StrategyException when the board is created illegally
	 */
	public BoardImpl(int row, int col, List<PieceImpl> red, List<PieceImpl> blue, Version version){
		theBoard = new HashMap<Square, PieceImpl>();
		//RED
		for(int i = 0; i < row/2 - 1; ++i) {
			for(int j = 0; j < col; ++j) {
				try {
					PieceImpl curPiece = red.get(0);
					red.remove(0);
					if(curPiece.getPieceType() == null) continue;
					this.putPieceAt(makeSquare(i, j), curPiece);
				}catch(IndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		//BLUE
		for(int i = row/2 + 1; i < row; ++i) {
			for(int j = 0; j < col; ++j) {
				try {
					PieceImpl curPiece = blue.get(0);
					blue.remove(0);
					if(curPiece.getPieceType() == null) continue;
					this.putPieceAt(makeSquare(i, j), curPiece);
				}catch(IndexOutOfBoundsException e) {
					continue;
				}
			}
		}		
		placeChokePoint(version);
	}

	/**
	 * Put a given piece to a given location
	 * @param square is the given location square; duplicate squares overwrite old pieces
	 * @param piece is the piece
	 */
	public void putPieceAt (Square square, PieceImpl piece) {
		this.theBoard.put(square, piece);
	}
	
	/**
	 * Remove a piece from the board
	 * @param square is the target square which must exit
	 */
	public void removePieceAt (Square square) {
		this.theBoard.remove(square);
	}
	
	/**
	 * Get a piece at the given coordinate
	 * @param row is the row
	 * @param col is the column
	 * @return the piece in this location
	 */
	@Override
	public PieceImpl getPieceAt(int row, int col) {
		PieceImpl thePiece = theBoard.get(makeSquare(row, col));
		return thePiece;
	}
	
	/**
	 * Get a square type at the given coordinate: Normal / Choked; do not check if out of bound
	 * @param row is the row
	 * @param col is the column
	 * @return the square type in this location
	 */
	@Override
	public SquareType getSquareTypeAt(int row, int col) {
		SquareType theType = NORMAL;
		for(Square sq: theBoard.keySet()) {
			if(sq.getRow() == row && sq.getColumn() == col) {
				return sq.getSquareType();
			}
		}
		return theType;
	}
	
	/**
	 * HELPER: Check a board setup if is correct
	 * @param version of the game
	 */
	private void placeChokePoint (Version version) {
		
		switch (version)
		{
			case GAMMA:
				for(int i = 2; i <= 3; ++i) {
					for(int j = 2; j <= 3; ++j) {
						this.putPieceAt(makeSquare(i, j, CHOKE), null);
					}
				}
				break;
			case EPSILON:
			case DELTA:
				for(int i = 4; i <= 5; ++i) {
					for(int j = 2; j <= 3; ++j) {
						this.putPieceAt(makeSquare(i, j, CHOKE), null);
					}
				}
				for(int i = 4; i <= 5; ++i) {
					for(int j = 6; j <= 7; ++j) {
						this.putPieceAt(makeSquare(i, j, CHOKE), null);
					}
				}
				break;
			default: 
				break;
		}
	}
	
	/**
	 * HELPER: Check if the pieces passed in by a board is legal
	 * @param version of the game
	 * @param row the row size of the board
	 * @param col the column size of the board
	 */
	private void checkBoardSetup(Version version, int row, int col) {
		// COLOR
		//RED
		for(int i = 0; i < row/2 - 1; ++i) {
			for(int j = 0; j < col; ++j) {
				try {
					if(this.getPieceAt(i, j).getPieceColor() != RED) {
						throw new StrategyException("Blue is not place in position");
					}
				}catch(NullPointerException e) {
					throw new StrategyException("Null Pointer Exists");
				}
			}
		}
		
		//BLUE
		for(int i = row/2 + 1; i < row; ++i) {
			for(int j = 0; j < col; ++j) {
				try {
					if(this.getPieceAt(i, j).getPieceColor() != BLUE) {
						throw new StrategyException("Red is not place in position");
					}
				}catch(NullPointerException e) {
					throw new StrategyException("Null Pointer Exists");
				}
			}
		}
		
		//EMPTY ROWS
		for(int i = row/2 - 1; i < row/2 + 1; ++i) {
			for(int j = 0; j < col; ++j) {
				if(this.getPieceAt(i, j) != null) {
					throw new StrategyException("Some piece is placed in the middle rows");
				}
			}
		}
		
		// PIECE DISTRIBUTION
		for(int i = 0; i < row; ++i) {
			for(int j = 0; j < col; ++j) {
				Piece curPiece = this.getPieceAt(i, j);
				PieceType curPieceType = curPiece!= null ? curPiece.getPieceType(): null;
				PieceColor curPieceColor = curPiece!= null ? curPiece.getPieceColor(): null;
				if(curPieceColor == RED) {
					Object curNumber = this.redPieceNumbers.get(curPieceType);
					if(curNumber == null) {
						throw new StrategyException("Number of red pieces is not correct");
					}
					int pieceRemain = (int)curNumber-1;
					if(pieceRemain <= 0) {
						this.redPieceNumbers.remove(curPieceType);
					}else {
						this.redPieceNumbers.put(curPieceType, (int)curNumber-1);
					}
				}else if(curPieceColor == BLUE){
					Object curNumber = this.bluePieceNumbers.get(curPieceType);
					if(curNumber == null) {
						throw new StrategyException("Number of red pieces is not correct");
					}
					int pieceRemain = (int)curNumber-1;
					if(pieceRemain <= 0) {
						this.bluePieceNumbers.remove(curPieceType);
					}else {
						this.bluePieceNumbers.put(curPieceType, (int)curNumber-1);
					}
				}
			}
		}
		if(this.redPieceNumbers.size() > 0) {
			throw new StrategyException("Number of red pieces is not correct");
		}
		if(this.bluePieceNumbers.size() > 0) {
			throw new StrategyException("Number of blue pieces is not correct");
		}
	}
}
