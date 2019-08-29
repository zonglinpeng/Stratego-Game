package strategy.delta;

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
import strategy.StrategyException;

import java.util.LinkedList;
import java.util.List;

/**
 * *******************************************Initial Layup********************************************************
LIEUTENANT          FLAG                SERGEANT            CAPTAIN             COLONEL             BOMB                SCOUT               MINER               BOMB                SCOUT               
CAPTAIN             COLONEL             SERGEANT            SERGEANT            LIEUTENANT          LIEUTENANT          SCOUT               MINER               MINER               BOMB                
SCOUT               SCOUT               MINER               CAPTAIN             CAPTAIN             GENERAL             BOMB                MAJOR               MAJOR               MAJOR               
SCOUT               SPY                 SERGEANT            LIEUTENANT          MINER               MARSHAL             SCOUT               SCOUT               BOMB                BOMB                
[NORMAL]            [NORMAL]            [CHOKE]             [CHOKE]             [NORMAL]            [NORMAL]            [CHOKE]             [CHOKE]             [NORMAL]            [NORMAL]            
[NORMAL]            [NORMAL]            [CHOKE]             [CHOKE]             [NORMAL]            [NORMAL]            [CHOKE]             [CHOKE]             [NORMAL]            [NORMAL]            
BOMB                MARSHAL             COLONEL             CAPTAIN             BOMB                SPY                 SERGEANT            LIEUTENANT          MINER               SCOUT               
MAJOR               GENERAL             COLONEL             CAPTAIN             LIEUTENANT          LIEUTENANT          BOMB                BOMB                SCOUT               SCOUT               
BOMB                MINER               FLAG                CAPTAIN             CAPTAIN             MAJOR               MINER               MINER               MINER               SCOUT               
SCOUT               BOMB                SERGEANT            SERGEANT            MAJOR               LIEUTENANT          SERGEANT            SCOUT               SCOUT               SCOUT  
 ******************************************************************************************************************
 */
class DeltaStrategyTest {

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
	{	//								CHOKE		CHOKE								CHOKE		CHOKE		
		redLineUpMy = redLineUp.makeLineUp(RED,
				SCOUT,		BOMB, 		SERGEANT,	SERGEANT,	MAJOR,		LIEUTENANT, SERGEANT,	SCOUT, 		SCOUT,		SCOUT,
				BOMB,		MINER,		FLAG, 		CAPTAIN,	CAPTAIN,	MAJOR,		MINER,		MINER,		MINER,		SCOUT,
				MAJOR,		GENERAL, 	COLONEL,	CAPTAIN, 	LIEUTENANT, LIEUTENANT, BOMB, 		BOMB, 		SCOUT, 		SCOUT,
				BOMB, 		MARSHAL, 	COLONEL, 	CAPTAIN, 	BOMB, 		SPY, 		SERGEANT, 	LIEUTENANT, MINER, 		SCOUT); //FRONT
		blueLineUpMy = blueLineUp.makeLineUp(BLUE,
				SCOUT, 		SPY,  		SERGEANT, 	LIEUTENANT, MINER, 		MARSHAL, 	SCOUT,		SCOUT,		BOMB,		BOMB,	//FRONT
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				CAPTAIN, 	COLONEL,	SERGEANT, 	SERGEANT, 	LIEUTENANT, LIEUTENANT, SCOUT, 		MINER,		MINER, 		BOMB,
				LIEUTENANT, FLAG,		SERGEANT, 	CAPTAIN, 	COLONEL, 	BOMB, 		SCOUT, 		MINER,		BOMB, 		SCOUT);
		board = new BoardImpl(10, 10, redLineUpMy, blueLineUpMy, DELTA);
		theGame = makeGame(DELTA, board); //Game Factory
	}
	
	private void setupMaster()
	{
		theBoard = new OldTestBoard(10, 10);
		redLineup = theBoard.makeLineup(RED,
				SCOUT,		BOMB, 		SERGEANT,	SERGEANT,	MAJOR,		LIEUTENANT, SERGEANT,	SCOUT, 		SCOUT,		SCOUT,
				BOMB,		MINER,		FLAG, 		CAPTAIN,	CAPTAIN,	MAJOR,		MINER,		MINER,		MINER,		SCOUT,
				MAJOR,		GENERAL, 	COLONEL,	CAPTAIN, 	LIEUTENANT, LIEUTENANT, BOMB, 		BOMB, 		SCOUT, 		SCOUT,
				BOMB, 		MARSHAL, 	COLONEL, 	CAPTAIN, 	BOMB, 		SPY, 		SERGEANT, 	LIEUTENANT, MINER, 		SCOUT); //FRONT
		blueLineup = theBoard.makeLineup(BLUE,
				SCOUT, 		SPY,  		SERGEANT, 	LIEUTENANT, MINER, 		MARSHAL, 	SCOUT,		SCOUT,		BOMB,		BOMB,	//FRONT
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				CAPTAIN, 	COLONEL,	SERGEANT, 	SERGEANT, 	LIEUTENANT, LIEUTENANT, SCOUT, 		MINER,		MINER, 		BOMB,
				LIEUTENANT, FLAG,		SERGEANT, 	CAPTAIN, 	COLONEL, 	BOMB, 		SCOUT, 		MINER,		BOMB, 		SCOUT);
		theBoard.initialize(10, 10, redLineup, blueLineup);
		theGame = makeGame(DELTA, theBoard);
	}
	
	private void setupMasterLessPiece()
	{
		theBoard = new OldTestBoard(10, 10);
		redLineup = theBoard.makeLineup(RED,
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				CAPTAIN, 	COLONEL,	SERGEANT, 	SERGEANT, 	LIEUTENANT, LIEUTENANT, SCOUT, 		MINER,		MINER, 		BOMB,
				LIEUTENANT, FLAG,		SERGEANT, 	CAPTAIN, 	COLONEL, 	BOMB, 		SCOUT, 		MINER,		BOMB, 		SCOUT);
		blueLineup = theBoard.makeLineup(BLUE,
				SCOUT, 		SPY,  		SERGEANT, 	LIEUTENANT, MINER, 		MARSHAL, 	SCOUT,		SCOUT,		BOMB,		BOMB,	//FRONT
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				CAPTAIN, 	COLONEL,	SERGEANT, 	SERGEANT, 	LIEUTENANT, LIEUTENANT, SCOUT, 		MINER,		MINER, 		BOMB,
				LIEUTENANT, FLAG,		SERGEANT, 	CAPTAIN, 	COLONEL, 	BOMB, 		SCOUT, 		MINER,		BOMB, 		SCOUT);
		theBoard.initialize(10, 10, redLineup, blueLineup);
	}
	
	private void setupMasterMorePiece()
	{
		theBoard = new OldTestBoard(10, 10);
		redLineup = theBoard.makeLineup(RED,
				SCOUT,		BOMB, 		SERGEANT,	SERGEANT,	MAJOR,		LIEUTENANT, SERGEANT,	SCOUT, 		SCOUT,		SCOUT,
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				CAPTAIN, 	COLONEL,	SERGEANT, 	SERGEANT, 	LIEUTENANT, LIEUTENANT, SCOUT, 		MINER,		MINER, 		BOMB,
				LIEUTENANT, FLAG,		SERGEANT, 	CAPTAIN, 	COLONEL, 	BOMB, 		SCOUT, 		MINER,		BOMB, 		SCOUT);
		blueLineup = theBoard.makeLineup(BLUE,
				SCOUT,		BOMB, 		SERGEANT,	SERGEANT,	MAJOR,		LIEUTENANT, SERGEANT,	SCOUT, 		SCOUT,		SCOUT,
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				CAPTAIN, 	COLONEL,	SERGEANT, 	SERGEANT, 	LIEUTENANT, LIEUTENANT, SCOUT, 		MINER,		MINER, 		BOMB,
				LIEUTENANT, FLAG,		SERGEANT, 	CAPTAIN, 	COLONEL, 	BOMB, 		SCOUT, 		MINER,		BOMB, 		SCOUT);
		theBoard.initialize(10, 10, redLineup, blueLineup);
	}
	
	private void setupMasterWrongPiece()
	{
		theBoard = new OldTestBoard(10, 10);
		redLineup = theBoard.makeLineup(RED,
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				CAPTAIN, 	COLONEL,	SERGEANT, 	SERGEANT, 	LIEUTENANT, LIEUTENANT, SCOUT, 		MINER,		MINER, 		BOMB,
				LIEUTENANT, FLAG,		SERGEANT, 	CAPTAIN, 	COLONEL, 	BOMB, 		SCOUT, 		MINER,		BOMB, 		SCOUT);
		blueLineup = theBoard.makeLineup(BLUE,
				SCOUT, 		SPY,  		SERGEANT, 	LIEUTENANT, MINER, 		MARSHAL, 	SCOUT,		SCOUT,		BOMB,		BOMB,	//FRONT
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				CAPTAIN, 	COLONEL,	SERGEANT, 	SERGEANT, 	LIEUTENANT, LIEUTENANT, SCOUT, 		MINER,		MINER, 		BOMB,
				LIEUTENANT, FLAG,		SERGEANT, 	CAPTAIN, 	COLONEL, 	BOMB, 		SCOUT, 		MINER,		BOMB, 		SCOUT);
		theBoard.initialize(10, 10, redLineup, blueLineup);
	}
	
	
	private void setupUnmoveEvenRank()
	{	//								CHOKE		CHOKE								CHOKE		CHOKE		
		redLineUpMy = redLineUp.makeLineUp(RED,
				SCOUT,		BOMB,		FLAG, 		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,
				BOMB,		SCOUT); //FRONT
		blueLineUpMy = blueLineUp.makeLineUp(BLUE,
				BOMB,		SCOUT,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,//FRONT
				BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,
				SCOUT,		BOMB,		FLAG, 		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,
				BOMB);
		board = new BoardImpl(10, 10, redLineUpMy, blueLineUpMy, DELTA);
		theGame = makeGame(DELTA, board); //Game Factory
	}
	
	private void setupUnmoveDifferentRank()
	{	//								CHOKE		CHOKE								CHOKE		CHOKE		
		redLineUpMy = redLineUp.makeLineUp(RED,
				SCOUT,		BOMB,		FLAG, 		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,
				BOMB,		GENERAL,	BOMB, 		BOMB); //FRONT
		blueLineUpMy = blueLineUp.makeLineUp(BLUE,
				BOMB,		SCOUT,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,//FRONT
				BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,
				SCOUT,		BOMB,		FLAG, 		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,		BOMB,
				BOMB);
		board = new BoardImpl(10, 10, redLineUpMy, blueLineUpMy, DELTA);
		theGame = makeGame(DELTA, board); //Game Factory
	}

	@Test 
	void MasterBoardSetupTest() // DONE
	{
		setupMaster();
		assertEquals(OK, theGame.move(3, 1, 4, 1));
		setupMasterLessPiece();
		assertThrows(StrategyException.class, () -> makeGame(DELTA, theBoard));
		setupMasterMorePiece();
		assertThrows(StrategyException.class, () -> makeGame(DELTA, theBoard));
		setupMasterWrongPiece();
		assertThrows(StrategyException.class, () -> makeGame(DELTA, theBoard));
	}
	
	@Test 
	void chokeRed() // DONE
	{
		assertEquals(BLUE_WINS, theGame.move(3, 2, 4, 2));	
		setup();
		assertEquals(BLUE_WINS, theGame.move(3, 3, 4, 3));
		setup();
		assertEquals(BLUE_WINS, theGame.move(3, 6, 4, 6));	
		setup();
		assertEquals(BLUE_WINS, theGame.move(3, 7, 4, 7));	
	}

	@Test 
	void chokeBlue() // DONE
	{
		assertEquals(OK, theGame.move(3, 1, 4, 1));
		assertEquals(RED_WINS, theGame.move(6, 2, 5, 2));
		setup();
		assertEquals(OK, theGame.move(3, 1, 4, 1));
		assertEquals(RED_WINS, theGame.move(6, 3, 5, 3));
		setup();
		assertEquals(OK, theGame.move(3, 1, 4, 1));
		assertEquals(RED_WINS, theGame.move(6, 6, 5, 6));
		setup();
		assertEquals(OK, theGame.move(3, 1, 4, 1));
		assertEquals(RED_WINS, theGame.move(6, 7, 5, 7));
	}

	@Test
	void moveNewNormalPieces() 
	{
		//BOMB
		assertEquals(BLUE_WINS, theGame.move(3, 0, 4, 0));
		setup();
		theGame.move(3, 1, 4, 1);
		assertEquals(RED_WINS, theGame.move(6, 9, 5, 9));
		//MINER
		setup();
		assertEquals(OK, theGame.move(3, 8, 4, 8));
		assertEquals(OK, theGame.move(6, 4, 5, 4));
		assertEquals(BLUE_WINS, theGame.move(4, 8, 6, 8));
		//SPY
		setup();
		assertEquals(OK, theGame.move(3, 5, 4, 5));
		assertEquals(OK, theGame.move(6, 1, 5, 1));
		assertEquals(OK, theGame.move(4, 5, 3, 5));
		assertEquals(RED_WINS, theGame.move(3, 5, 3, 5));
	}
	
	@Test // DONE
	void moveScouts() 
	{
		// no blocking
		assertEquals(OK, theGame.move(3, 9, 4, 9));
		assertEquals(OK, theGame.move(6, 0, 5, 0));
		assertEquals(OK, theGame.move(4, 9, 3, 9));
		assertEquals(OK, theGame.move(5, 0, 6, 0));
		assertEquals(OK, theGame.move(3, 9, 5, 9));
		assertEquals(OK, theGame.move(6, 0, 4, 0));
		assertEquals(OK, theGame.move(5, 9, 5, 8));
		assertEquals(OK, theGame.move(4, 0, 4, 1));
		assertEquals(BLUE_WINS, theGame.move(5, 8, 2, 8));
		// over choke points
		setup();
		assertEquals(OK, theGame.move(3, 9, 4, 9));
		assertEquals(OK, theGame.move(6, 0, 5, 0));
		assertEquals(OK, theGame.move(4, 9, 4, 8));
		assertEquals(OK, theGame.move(5, 0, 5, 1));
		assertEquals(BLUE_WINS, theGame.move(4, 8, 4, 0));
		setup();
		assertEquals(OK, theGame.move(3, 9, 4, 9));
		assertEquals(OK, theGame.move(6, 0, 5, 0));
		assertEquals(OK, theGame.move(4, 9, 4, 8));
		assertEquals(RED_WINS, theGame.move(5, 0, 5, 5));
		// over pieces
		setup();
		assertEquals(OK, theGame.move(3, 9, 4, 9));
		assertEquals(OK, theGame.move(6, 0, 5, 0));
		assertEquals(BLUE_WINS, theGame.move(2, 9, 5, 9));
		setup();
		assertEquals(OK, theGame.move(3, 9, 4, 9));
		assertEquals(OK, theGame.move(6, 0, 5, 0));
		assertEquals(OK, theGame.move(4, 9, 5, 9));
		assertEquals(RED_WINS, theGame.move(7, 0, 4, 0));
		setup();
		assertEquals(OK, theGame.move(3, 5, 4, 5));
		assertEquals(OK, theGame.move(6, 0, 5, 0));
		assertEquals(BLUE_WINS, theGame.move(3, 9, 3, 5));
		setup();
		assertEquals(OK, theGame.move(3, 9, 4, 9));
		assertEquals(OK, theGame.move(6, 4, 5, 4));
		assertEquals(OK, theGame.move(4, 9, 4, 8));
		assertEquals(RED_WINS, theGame.move(6, 6, 6, 4));
	}
	
	@Test 
	void defeatAboutBomb() 
	{
		// bang
		theGame.move(3, 9, 5, 9);
		theGame.move(6, 0, 4, 0);
		assertEquals(STRIKE_BLUE, theGame.move(5, 9, 6, 9));
		setup();
		theGame.move(3, 9, 5, 9);
		theGame.move(6, 0, 4, 0);
		theGame.move(5, 9, 5, 8);
		assertEquals(STRIKE_RED, theGame.move(4, 0, 3, 0));
		// dig
		setup();
		theGame.move(3, 8, 4, 8);
		theGame.move(6, 4, 5, 4);
		theGame.move(4, 8, 5, 8);
		theGame.move(5, 4, 4, 4);
		assertEquals(STRIKE_RED, theGame.move(5, 8, 6, 8));
		setup();
		theGame.move(3, 8, 4, 8);
		theGame.move(6, 4, 5, 4);
		theGame.move(4, 8, 5, 8);
		theGame.move(5, 4, 4, 4);
		theGame.move(5, 8, 4, 8);
		assertEquals(STRIKE_BLUE, theGame.move(4, 4, 3, 4));
	}
	
	@Test 
	void defeatsAboutSpy() 
	{
		// spy defeats marshal
		theGame.move(3, 9, 4, 9);
		theGame.move(6, 1, 5, 1);
		theGame.move(4, 9, 5, 9);
		theGame.move(5, 1, 4, 1);
		theGame.move(5, 9, 6, 9);
		assertEquals(STRIKE_BLUE, theGame.move(4, 1, 3, 1));
		
		// marshal defeats spy
		setup();
		theGame.move(3, 9, 4, 9);
		theGame.move(6, 1, 5, 1);
		theGame.move(4, 9, 5, 9);
		theGame.move(5, 1, 4, 1);
		assertEquals(STRIKE_RED, theGame.move(3, 1, 4, 1));
	}
	
	@Test 
	void defeatAboutScout() 
	{
		// long attack
		assertEquals(BLUE_WINS, theGame.move(3, 9, 6, 5));
		setup();
		theGame.move(3, 9, 5, 9);
		assertEquals(RED_WINS, theGame.move(6, 0, 3, 0));
		// adjacent attack
		setup();
		theGame.move(3, 9, 5, 9);
		theGame.move(6, 0, 4, 0);
		theGame.move(5, 9, 5, 8);
		theGame.move(4, 0, 4, 1);
		assertEquals(STRIKE_BLUE, theGame.move(5, 8, 6, 8));
		setup();
		theGame.move(3, 9, 5, 9);
		theGame.move(6, 0, 4, 0);
		theGame.move(5, 9, 5, 8);
		theGame.move(4, 0, 4, 1);
		theGame.move(5, 8, 5, 9);
		assertEquals(STRIKE_RED, theGame.move(4, 1, 3, 1));
	}
	
	@Test 
	void noMovable() 
	{
		// strike with a draw
		setupUnmoveEvenRank();
		assertEquals(OK, theGame.move(1, 1, 5, 1));
		assertEquals(BLUE_WINS, theGame.move(6, 1, 5, 1));
		setupUnmoveEvenRank();
		assertEquals(OK, theGame.move(1, 1, 4, 1));
		assertEquals(OK, theGame.move(6, 1, 5, 1));
		assertEquals(RED_WINS, theGame.move(4, 1, 5, 1));
		// strike with a win
		setupUnmoveDifferentRank();
		assertEquals(OK, theGame.move(1, 1, 2, 1));
		assertEquals(OK, theGame.move(6, 1, 3, 1));
		assertEquals(RED_WINS, theGame.move(2, 1, 3, 1));
		// strike with a lost
		setupUnmoveDifferentRank();
		assertEquals(OK, theGame.move(1, 1, 2, 1));
		assertEquals(OK, theGame.move(6, 1, 4, 1));
		assertEquals(OK, theGame.move(2, 1, 3, 1));
		assertEquals(RED_WINS, theGame.move(4, 1, 3, 1));
	}
}
