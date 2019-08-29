package strategy.beta;

import static org.junit.jupiter.api.Assertions.*;
import static strategy.StrategyGame.MoveResult.*;
import static strategy.StrategyGame.Version.BETA;
import static strategy.required.StrategyGameFactory.makeGame;
import strategy.Board;
import strategy.Piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import strategy.StrategyGame;
import strategy.testutil.OldTestBoard;

import static strategy.Piece.PieceColor.*;
import static strategy.Piece.PieceType.*;

import strategy.zpeng.component.BoardImpl;
import strategy.zpeng.component.PieceImpl;
import strategy.zpeng.lineup.BlueLineUp;
import strategy.zpeng.lineup.RedLineUp;

import java.util.LinkedList;
import java.util.List;

/**
 * *******************************************Initial Layup********************************************************
LIEUTENANT          FLAG                SERGEANT            CAPTAIN             COLONEL             MARSHAL             
CAPTAIN             COLONEL             SERGEANT            SERGEANT            LIEUTENANT          LIEUTENANT          
[NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            
[NORMAL]            MARSHAL             [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            
FLAG                [NORMAL]            COLONEL             CAPTAIN             LIEUTENANT          SERGEANT            
SERGEANT            SERGEANT            COLONEL             CAPTAIN             LIEUTENANT          LIEUTENANT    
 ******************************************************************************************************************
 */
class BetaStrategyTest {

	private StrategyGame theGame = null;
	private RedLineUp redLineUp = new RedLineUp();
	private BlueLineUp blueLineUp = new BlueLineUp();
	private List<PieceImpl> redLineUpMy = new LinkedList<PieceImpl>();
	private List<PieceImpl> blueLineUpMy = new LinkedList<PieceImpl>();
	private List<Piece> redLineup = null;
	private List<Piece> blueLineup = null;
	private OldTestBoard theBoard = null;
	private Board board = null;

	
	@BeforeEach
	private void setup()
	{
		redLineUpMy = redLineUp.makeLineUp(RED,
				SERGEANT, SERGEANT, COLONEL, CAPTAIN, LIEUTENANT, LIEUTENANT,
				FLAG, MARSHAL, COLONEL, CAPTAIN, LIEUTENANT, SERGEANT);
		blueLineUpMy = blueLineUp.makeLineUp(BLUE,
				CAPTAIN, COLONEL, SERGEANT, SERGEANT, LIEUTENANT, LIEUTENANT,
				LIEUTENANT, FLAG, SERGEANT, CAPTAIN, COLONEL, MARSHAL);
		board = new BoardImpl(6, 6, redLineUpMy, blueLineUpMy, BETA);
		theGame = makeGame(BETA, board); //Game Factory
		
	}
	
	private void setupMaster() 
	{
		theBoard = new OldTestBoard(6, 6);
		redLineup = theBoard.makeLineup(RED,
				SERGEANT, SERGEANT, COLONEL, CAPTAIN, LIEUTENANT, LIEUTENANT,
				FLAG, MARSHAL, COLONEL, CAPTAIN, LIEUTENANT, SERGEANT);
		blueLineup = theBoard.makeLineup(BLUE,
				MARSHAL, COLONEL, CAPTAIN, SERGEANT, FLAG, LIEUTENANT,
				LIEUTENANT, LIEUTENANT, SERGEANT, SERGEANT, COLONEL, CAPTAIN);
		theBoard.initialize(6, 6, redLineup, blueLineup);
		theGame = makeGame(BETA, theBoard);
	}

	@Test 
	void masterTest() // DONE
	{
		setupMaster();
		theGame.move(1, 1, 2, 1);	// Move 1
		theGame.move(4, 2, 3, 2);
		theGame.move(2, 1, 1, 1);	// Move 2
		theGame.move(3, 2, 4, 2);
		theGame.move(1, 1, 2, 1);	// Move 3
		theGame.move(4, 2, 3, 2);
		theGame.move(2, 1, 1, 1);	// Move 4
		theGame.move(3, 2, 4, 2);
		theGame.move(1, 1, 2, 1);	// Move 5
		theGame.move(4, 2, 3, 2);
		theGame.move(2, 1, 1, 1);	// Move 6
		theGame.move(3, 2, 4, 2);
		theGame.move(1, 1, 2, 1);	// Move 7
		theGame.move(4, 2, 3, 2);
		assertEquals(OK, theGame.move(2, 1, 1, 1));	// Move 8
		assertEquals(RED_WINS, theGame.move(3, 2, 4, 2));
		assertEquals(GAME_OVER, theGame.move(2, 1, 3, 1));
	}
	
//	@Test 
//	void lackingRedPiecesException() // DONE
//	{		
//		redLineUpMy = redLineUp.makeLineUp(RED,
//				SERGEANT, SERGEANT, COLONEL, CAPTAIN, LIEUTENANT, LIEUTENANT);
//		blueLineUpMy = blueLineUp.makeLineUp(BLUE,				
//				CAPTAIN, COLONEL, SERGEANT, SERGEANT, LIEUTENANT, LIEUTENANT,
//				LIEUTENANT, FLAG, SERGEANT, CAPTAIN, COLONEL, MARSHAL);
//
//		assertThrows(StrategyException.class, () ->new BoardImpl(6, 6, redLineUpMy, blueLineUpMy, BETA));
//	}
//	
//	@Test 
//	void lackingBluePiecesException() // DONE
//	{		
//		redLineUpMy = redLineUp.makeLineUp(RED,
//				SERGEANT, SERGEANT, COLONEL, CAPTAIN, LIEUTENANT, LIEUTENANT,
//				FLAG, MARSHAL, COLONEL, CAPTAIN, LIEUTENANT, SERGEANT);
//		blueLineUpMy = blueLineUp.makeLineUp(BLUE,				
//				CAPTAIN, COLONEL, SERGEANT, SERGEANT, LIEUTENANT, LIEUTENANT);
//
//		assertThrows(StrategyException.class, () ->new BoardImpl(6, 6, redLineUpMy, blueLineUpMy, BETA));
//	}
	
	@Test 
	void moveAllPieces() // DONE
	{
		// Vertical
		assertEquals(OK, theGame.move(1, 1, 2, 1));
		assertEquals(OK, theGame.move(4, 2, 3, 2));
		assertEquals(OK, theGame.move(1, 3, 2, 3));
		assertEquals(OK, theGame.move(4, 4, 3, 4));
		assertEquals(OK, theGame.move(1, 5, 2, 5));
		// Horizontal
		assertEquals(OK, theGame.move(3, 2, 3, 1));
		assertEquals(OK, theGame.move(2, 3, 2, 2));
		assertEquals(OK, theGame.move(3, 4, 3, 3));
		assertEquals(OK, theGame.move(2, 5, 2, 4));	
		//Stay
		assertEquals(RED_WINS, theGame.move(5, 4, 5, 4));	
		setup();
		assertEquals(BLUE_WINS, theGame.move(1, 4, 1, 4));	
		//Diagonal
		setup();
		assertEquals(OK, theGame.move(1, 1, 2, 1));
		assertEquals(RED_WINS, theGame.move(4, 0, 3, 1));
		setup();
		assertEquals(OK, theGame.move(1, 1, 2, 1));
		assertEquals(RED_WINS, theGame.move(4, 2, 3, 1));
		setup();
		assertEquals(BLUE_WINS, theGame.move(1, 4, 2, 3));	
		setup();
		assertEquals(BLUE_WINS, theGame.move(1, 2, 2, 3));	
	}
	
	
	@Test 
	void redWinsAfterEightTurns() // DONE
	{
		theGame.move(1, 1, 2, 1);	// Move 1
		theGame.move(4, 2, 3, 2);
		theGame.move(2, 1, 1, 1);	// Move 2
		theGame.move(3, 2, 4, 2);
		theGame.move(1, 1, 2, 1);	// Move 3
		theGame.move(4, 2, 3, 2);
		theGame.move(2, 1, 1, 1);	// Move 4
		theGame.move(3, 2, 4, 2);
		theGame.move(1, 1, 2, 1);	// Move 5
		theGame.move(4, 2, 3, 2);
		theGame.move(2, 1, 1, 1);	// Move 6
		theGame.move(3, 2, 4, 2);
		theGame.move(1, 1, 2, 1);	// Move 7
		theGame.move(4, 2, 3, 2);
		assertEquals(OK, theGame.move(2, 1, 1, 1));	// Move 8
		assertEquals(RED_WINS, theGame.move(3, 2, 4, 2));
		assertEquals(GAME_OVER, theGame.move(2, 1, 3, 1));
	}
	
	@Test 
	void moveUnmovablePieces() // DONE
	{
		theGame.move(1, 1, 2, 1);
		theGame.move(4, 1, 3, 1);	// BLUE piece below FLAG
		theGame.move(2, 1, 1, 1);
		assertEquals(RED_WINS, theGame.move(5, 1, 4, 1));	// BLUE FLAG
		setup();
		assertEquals(BLUE_WINS, theGame.move(1, 0, 2, 0));	// RED FLAG
	}
	
	@Test 
	void moveOutOfBoundary() // DONE
	{
		assertEquals(BLUE_WINS, theGame.move(6, 1, 5, 1));
		setup();
		assertEquals(BLUE_WINS, theGame.move(-1, 1, 0, 1));
		setup();
		assertEquals(BLUE_WINS, theGame.move(5, 6, 5, 5));
		setup();
		assertEquals(BLUE_WINS, theGame.move(5, -1, 5, 0));
		setup();
		theGame.move(1, 1, 2, 1);
		assertEquals(RED_WINS, theGame.move(5, 1, 6, 1));
		setup();
		theGame.move(1, 1, 2, 1);
		assertEquals(RED_WINS, theGame.move(5, 1, -1, 1));	
		setup();
		theGame.move(1, 1, 2, 1);
		assertEquals(RED_WINS, theGame.move(5, 5, 5, 6));	
		setup();
		theGame.move(1, 1, 2, 1);
		assertEquals(RED_WINS, theGame.move(5, 0, 5, -1));
		setup();
		assertEquals(BLUE_WINS, theGame.move(-1, 6, -1, 7));
	}
	
	@Test 
	void gameRoundsRegulation() // DONE
	{
		// Red First
		assertEquals(BLUE_WINS, theGame.move(3, 2, 4, 2));
		setup();
		// Alternative
		theGame.move(1, 2, 2, 2);
		assertEquals(RED_WINS, theGame.move(1, 3, 2, 1));		
	}
	
	@Test 
	void successfulStrike()
	{
		theGame.move(1, 1, 2, 1);
		theGame.move(4, 1, 3, 1);
		theGame.move(1, 4, 2, 4);
		theGame.move(4, 4, 3, 4);
		theGame.move(1, 5, 2, 5);
		theGame.move(4, 5, 3, 5);
		assertEquals(STRIKE_RED, theGame.move(2, 1, 3, 1));	// Self defeats opponent
		assertEquals(OK, theGame.move(3, 4, 2, 4));		// Draw
		assertEquals(STRIKE_BLUE, theGame.move(2, 5, 3, 5));	//Opponent defeats self
		assertEquals(RED_WINS, theGame.move(4, 2, 5, 2));	 // Same Color
	}
	
	@Test 
	void flagTakenOff()
	{
		theGame.move(1, 1, 2, 1);
		theGame.move(4, 0, 3, 0);
		theGame.move(1, 4, 2, 4);
		theGame.move(3, 0, 2, 0);
		theGame.move(1, 5, 2, 5);
		assertEquals(BLUE_WINS, theGame.move(2, 0, 1, 0));
		setup();
		theGame.move(1, 1, 2, 1);
		theGame.move(4, 0, 3, 0);
		theGame.move(2, 1, 3, 1);
		theGame.move(4, 3, 3, 3);
		theGame.move(3, 1, 4, 1);
		theGame.move(4, 5, 3, 5);
		assertEquals(RED_WINS, theGame.move(4, 1, 5, 1));
	}
	
	@Test 
	void gameoverResult()
	{
		theGame.move(1, 1, 2, 1);
		assertEquals(RED_WINS, theGame.move(2, 1, 3, 1));
		assertEquals(GAME_OVER, theGame.move(1, 0, 2, 0)); //try move flag
		assertEquals(GAME_OVER, theGame.move(4, 0, 3, 0));
		setup();
		assertEquals(BLUE_WINS, theGame.move(4, 1, 3, 1));
		assertEquals(GAME_OVER, theGame.move(4, 0, 3, 0));
		assertEquals(GAME_OVER, theGame.move(1, 0, 2, 0)); //try move flag
	}

	/********************************ALPHA TESTS**************************************/
//		@Test
//		void validGame()
//		{
//			game = makeGame(ALPHA, null);
//			assertEquals(RED_WINS, game.move(0, 0, 1, 0));
//		}
//
//		@Test
//		void wrongMove()
//		{
//			game = makeGame(ALPHA, null);
//			assertEquals(BLUE_WINS, game.move(0, 0, 0, 1));
//		}
//	
//		
//		@Test
//		void versionNotImplemented()
//		{
//			assertThrows(NotImplementedException.class, () -> makeGame(ZETA, null));
//		}

}
