package strategy.gamma;

import static org.junit.jupiter.api.Assertions.*;
import static strategy.StrategyGame.MoveResult.*;
import static strategy.StrategyGame.Version.*;
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
NULL                NULL                [CHOKE]             [CHOKE]             NULL                NULL                
NULL                NULL                [CHOKE]             [CHOKE]             NULL                NULL                
FLAG                MARSHAL             COLONEL             CAPTAIN             LIEUTENANT          SERGEANT            
SERGEANT            SERGEANT            COLONEL             CAPTAIN             LIEUTENANT          LIEUTENANT
 ******************************************************************************************************************
 */
class GammaStrategyTest {

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
		board = new BoardImpl(6, 6, redLineUpMy, blueLineUpMy, GAMMA);
		theGame = makeGame(GAMMA, board); //Game Factory
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
		theGame = makeGame(GAMMA, theBoard);
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
		assertEquals(GAME_OVER, theGame.move(2, 1, 1, 1));	
	}
	
	@Test 
	void chokeRed() // DONE
	{
		assertEquals(BLUE_WINS, theGame.move(1, 2, 2, 2));	
		setup();
		assertEquals(BLUE_WINS, theGame.move(1, 3, 2, 3));	
	}

	@Test 
	void chokeBlue() // DONE
	{
		assertEquals(OK, theGame.move(1, 1, 2, 1));
		assertEquals(RED_WINS, theGame.move(4, 3, 3, 3));
		setup();
		assertEquals(OK, theGame.move(1, 1, 2, 1));
		assertEquals(RED_WINS, theGame.move(4, 2, 3, 2));
	}
	
	@Test 
	void moveUntilRedWins() // DONE
	{
		theGame.move(1, 1, 2, 1);
		theGame.move(4, 0, 3, 0);
		theGame.move(2, 1, 3, 1);
		theGame.move(4, 4, 3, 4);
		theGame.move(3, 1, 4, 1);
		theGame.move(4, 5, 3, 5);
		theGame.move(0, 1, 1, 1);
		theGame.move(3, 5, 2, 5);
		theGame.move(1, 4, 2, 4);
		theGame.move(2, 5, 1, 5);
		theGame.move(2, 4, 3, 4);
		theGame.move(1, 5, 0, 5);
		theGame.move(0, 4, 1, 4);
		theGame.move(5, 5, 4, 5);
		theGame.move(1, 4, 2, 4);
		theGame.move(4, 5, 3, 5);
		theGame.move(2, 4, 3, 4);
		theGame.move(3, 5, 2, 5);
		theGame.move(3, 4, 4, 4);
		theGame.move(2, 5, 1, 5);
		theGame.move(4, 4, 5, 4);
		theGame.move(1, 5, 0, 5);
		theGame.move(0, 2, 0, 1);
		theGame.move(3, 0, 2, 0);
		assertEquals(RED_WINS, theGame.move(4, 1, 5, 1));
	}

	@Test 
	void moveUntilBlueWins() // DONE
	{
		theGame.move(1, 1, 2, 1);
		theGame.move(4, 0, 3, 0);
		theGame.move(2, 1, 3, 1);
		theGame.move(4, 4, 3, 4);
		theGame.move(3, 1, 4, 1);
		theGame.move(4, 5, 3, 5);
		theGame.move(0, 1, 1, 1);
		theGame.move(3, 5, 2, 5);
		theGame.move(1, 4, 2, 4);
		theGame.move(2, 5, 1, 5);
		theGame.move(2, 4, 3, 4);
		theGame.move(1, 5, 0, 5);
		theGame.move(0, 4, 1, 4);
		theGame.move(5, 5, 4, 5);
		theGame.move(1, 4, 2, 4);
		theGame.move(4, 5, 3, 5);
		theGame.move(2, 4, 3, 4);
		theGame.move(3, 5, 2, 5);
		theGame.move(3, 4, 4, 4);
		theGame.move(2, 5, 1, 5);
		theGame.move(4, 4, 5, 4);
		theGame.move(1, 5, 0, 5);
		theGame.move(0, 2, 0, 1);
		theGame.move(3, 0, 2, 0);
		theGame.move(0, 3, 0, 2);
		assertEquals(BLUE_WINS, theGame.move(2, 0, 1, 0));
	}
	
	@Test 
	void moveRepetitionRed() 
	{
		theGame.move(1, 1, 2, 1);
		theGame.move(4, 0, 3, 0);
		theGame.move(2, 1, 1, 1);
		theGame.move(4, 4, 3, 4);
		assertEquals(BLUE_WINS, theGame.move(1, 1, 2, 1));
		setup();
		theGame.move(1, 1, 2, 1);
		theGame.move(4, 0, 3, 0);
		theGame.move(2, 1, 2, 0);
		theGame.move(4, 4, 3, 4);
		theGame.move(2, 0, 2, 1);
		theGame.move(4, 5, 3, 5);
		assertEquals(BLUE_WINS, theGame.move(2, 1, 2, 0));
	}
	
	@Test 
	void moveRepetitionBlue() 
	{
		theGame.move(1, 1, 2, 1);
		theGame.move(4, 0, 3, 0);
		theGame.move(2, 1, 3, 1);
		theGame.move(3, 0, 4, 0);
		theGame.move(3, 1, 4, 1);
		assertEquals(RED_WINS, theGame.move(4, 0, 3, 0));
		setup();
		theGame.move(1, 1, 2, 1);
		theGame.move(4, 0, 3, 0);
		theGame.move(1, 5, 2, 5);
		theGame.move(3, 0, 3, 1);
		theGame.move(1, 4, 2, 4);
		theGame.move(3, 1, 3, 0);
		theGame.move(2, 5, 3, 5);
		assertEquals(RED_WINS, theGame.move(3, 0, 3, 1));
	}
}
