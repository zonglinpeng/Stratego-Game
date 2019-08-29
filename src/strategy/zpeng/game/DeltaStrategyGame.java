/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 *******************************************************************************/
package strategy.zpeng.game;

import static strategy.StrategyGame.MoveResult.*;
import static strategy.StrategyGame.Version.DELTA;

import strategy.zpeng.component.BoardImpl;

/**
 * Description
 * @version Mar 18, 2019
 */
public class DeltaStrategyGame extends BaseGame
{

	/**
	 * Constructor
	 * @param board of game
	 */
	public DeltaStrategyGame(BoardImpl board) {
		super(board);
	}
	
	/**
	 * Determines the consequence of a move
	 * @param from row
	 * @param from column
	 * @param to row
	 * @param to column
	 * @return the move result of such a move
	 */
	public MoveResult move(int fr, int fc, int tr, int tc)	{
		// PREGAME
		super.setBoardSize(DELTA);
		
		MoveResult moveResult;
		MoveResult postgameResult;
		MoveResult pregameResult = super.checkPregameState(fr, fc, tr, tc);
		
		if(pregameResult != OK) {
			return super.gameState = pregameResult;
		}
		
		// INGAME
		if(this.myBoard.getPieceAt(tr, tc) == null) {
			moveResult = super.checkMoveValidity(fr, fc, tr, tc);
		}else {
			moveResult = super.checkStrikeValidity(this.myBoard, fr, fc, tr, tc, DELTA);
		}
		
		postgameResult = super.checkPostGameState(fr, fc, tr, tc, DELTA);
		if(postgameResult != OK) {
			return super.gameState = postgameResult;
		}
		
		// POSTGAME
		super.printBoard();
		
		super.postActionProcess();
		
		return super.gameState = moveResult;
	}
}