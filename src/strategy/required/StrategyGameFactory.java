/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package strategy.required;

import strategy.*;
import strategy.StrategyGame.Version;
import strategy.zpeng.version.alpha.AlphaStrategyGame;
import strategy.zpeng.component.BoardImpl;
import strategy.zpeng.game.BetaStrategyGame;
import strategy.zpeng.game.DeltaStrategyGame;
import strategy.zpeng.game.EpsilonStrategyGame;
import strategy.zpeng.game.GammaStrategyGame;

import static strategy.StrategyGame.Version.*;

/**
 * Factory for creating Strategy games.
 * @version Mar 18, 2019
 */
public class StrategyGameFactory
{
	/**
	 * Create a new game based on the version
	 * @param version of the desired game
	 * @param board of the game
	 * @return the object of game
	 * @throws NotImplementedException the version not implemented
	 */
	public static StrategyGame makeGame(Version version, Board board)
	{
		StrategyGame game;
		BoardImpl myBoard;
		switch (version)
		{
			case ALPHA:					// No need for the board
				game = new AlphaStrategyGame();
				break;
			case BETA:
				if(board instanceof BoardImpl) {
					myBoard = (BoardImpl) board;
				}
				else {
					myBoard= new BoardImpl(board, 6, 6, BETA);
				}
				game = new BetaStrategyGame(myBoard);
				break;
			case GAMMA:
				if(board instanceof BoardImpl) {
					myBoard = (BoardImpl) board;
				}
				else {
					myBoard= new BoardImpl(board, 6, 6, GAMMA);
				}
				game = new GammaStrategyGame(myBoard);
				break;
			case DELTA:
				if(board instanceof BoardImpl) {
					myBoard = (BoardImpl) board;
				}
				else {
					myBoard= new BoardImpl(board, 10, 10, DELTA);
				}
				game = new DeltaStrategyGame(myBoard);
				break;	
			case EPSILON:
				if(board instanceof BoardImpl) {
					myBoard = (BoardImpl) board;
				}
				else {
					myBoard= new BoardImpl(board, 10, 10, EPSILON);
				}
				game = new EpsilonStrategyGame(myBoard);
				break;	
			default:
				throw new NotImplementedException(
						"StrategyGameFactory.makeGame for version " + version);
		}
		return game;
	}
}
