/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016-2017 Gary F. Pollice
 *******************************************************************************/
package strategy.zpeng.game;

import static strategy.StrategyGame.MoveResult.*;
import static strategy.StrategyGame.Version.BETA;

import strategy.zpeng.component.BoardImpl;


/**
 * Description
 * @version Mar 18, 2019
 */
public class BetaStrategyGame extends BaseGame
{	
	private double roundCounter; 

	/**
	 * Constructor
	 * @param board of game
	 */
	public BetaStrategyGame(BoardImpl board) {
		super(board);
		this.roundCounter = 1;
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
		MoveResult moveResult;

		// PREGAME
		super.setBoardSize(BETA);
				
		// PREGAME
		MoveResult pregameResult = super.checkPregameState(this.roundCounter, fr, fc, tr, tc);
		if(pregameResult != OK) {
			return super.gameState = pregameResult;
		}else {
			this.roundCounter += 0.5; // add round number
		}
		
		// INGAME
		if(this.myBoard.getPieceAt(tr, tc) != null) {
			moveResult = super.checkStrikeValidity(this.myBoard, fr, fc, tr, tc, BETA);
		}else {
			moveResult = super.checkMoveValidity(fr, fc, tr, tc);
		}

		// POSTGAME
		super.printBoard();
		
		super.postActionProcess();
		
		return super.gameState = moveResult;
	}
}