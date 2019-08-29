package strategy.epsilon;

import static org.junit.jupiter.api.Assertions.*;
import static strategy.StrategyGame.MoveResult.*;
import static strategy.StrategyGame.Version.*;
import static strategy.required.StrategyGameFactory.makeGame;
import strategy.Board;
import strategy.Piece;

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

class EpsilonStrategyTest {

	private StrategyGame theGame = null;
	private RedLineUp redLineUp = new RedLineUp();
	private BlueLineUp blueLineUp = new BlueLineUp();
	private List<PieceImpl> redLineUpMy = new LinkedList<PieceImpl>();
	private List<PieceImpl> blueLineUpMy = new LinkedList<PieceImpl>();
	private List<Piece> redLineup = null;
	private List<Piece> blueLineup = null;
	private OldTestBoard theBoard = null;
	private Board board = null;
	
	private void setupChoke()
	{	//								CHOKE		CHOKE								CHOKE		CHOKE		
		redLineUpMy = redLineUp.makeLineUp(RED,
				SCOUT,		BOMB, 		SERGEANT,	SERGEANT,	MAJOR,		LIEUTENANT, SERGEANT,	SCOUT, 		SCOUT,		SCOUT,
				MARSHAL,	MINER,		MINER, 		CAPTAIN,	CAPTAIN,	MAJOR,		MINER,		MINER,		MINER,		SCOUT,
				MAJOR,		GENERAL, 	COLONEL,	CAPTAIN, 	LIEUTENANT, LIEUTENANT, SPY, 		SCOUT, 		SCOUT, 		BOMB,
				BOMB, 		BOMB, 		COLONEL, 	CAPTAIN, 	BOMB, 		BOMB, 		SERGEANT, 	LIEUTENANT, FLAG, 		SCOUT); //FRONT
		blueLineUpMy = blueLineUp.makeLineUp(BLUE,
				BOMB, 		BOMB,  		SERGEANT, 	LIEUTENANT, BOMB, 		BOMB, 		SCOUT,		SCOUT,		FLAG,		BOMB,	//FRONT
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				CAPTAIN, 	COLONEL,	SERGEANT, 	SERGEANT, 	LIEUTENANT, LIEUTENANT, SCOUT, 		MINER,		MINER, 		BOMB,
				LIEUTENANT, FLAG,		SERGEANT, 	CAPTAIN, 	COLONEL, 	BOMB, 		SCOUT, 		MINER,		BOMB, 		SCOUT);
		board = new BoardImpl(10, 10, redLineUpMy, blueLineUpMy, EPSILON);
		theGame = makeGame(EPSILON, board); //Game Factory
	}
	
	private void setupAgressorAdvantage()
	{	//								CHOKE		CHOKE								CHOKE		CHOKE		
		redLineUpMy = redLineUp.makeLineUp(RED,
				SCOUT,		BOMB, 		SERGEANT,	SERGEANT,	MAJOR,		LIEUTENANT, SERGEANT,	SCOUT, 		SCOUT,		SCOUT,
				MARSHAL,	MINER,		MINER, 		CAPTAIN,	CAPTAIN,	MAJOR,		MINER,		MINER,		MINER,		SCOUT,
				MAJOR,		GENERAL, 	COLONEL,	CAPTAIN, 	LIEUTENANT, LIEUTENANT, SPY, 		SCOUT, 		SCOUT, 		BOMB,
				SCOUT, 		SCOUT, 		COLONEL, 	CAPTAIN, 	SERGEANT, 	BOMB, 		SERGEANT, 	LIEUTENANT, FLAG, 		SCOUT); //FRONT
		blueLineUpMy = blueLineUp.makeLineUp(BLUE,
				SCOUT, 		BOMB,  		SERGEANT, 	LIEUTENANT, SERGEANT, 	SERGEANT, 	SCOUT,		SCOUT,		SCOUT,		BOMB,	//FRONT
				SCOUT,		SCOUT,		MINER,		CAPTAIN,	CAPTAIN,	GENERAL,	BOMB,		MAJOR,		MAJOR,		MAJOR,
				CAPTAIN, 	COLONEL,	SERGEANT, 	SERGEANT, 	LIEUTENANT, LIEUTENANT, FLAG, 		MINER,		MINER, 		BOMB,
				LIEUTENANT, FLAG,		SERGEANT, 	CAPTAIN, 	COLONEL, 	BOMB, 		SCOUT, 		MINER,		BOMB, 		SCOUT);
		board = new BoardImpl(10, 10, redLineUpMy, blueLineUpMy, EPSILON);
		theGame = makeGame(EPSILON, board); //Game Factory
	}
	
	private void setupDistanceScoutAttack()
	{	//								CHOKE		CHOKE								CHOKE		CHOKE		
		redLineUpMy = redLineUp.makeLineUp(RED,
				null,		null, 		null,		null,		null,		SCOUT, 		null,		null, 		null,		null,
				null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null,
				null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null,
				SCOUT,		SCOUT, 		SCOUT,		null,		SCOUT,		null, 		null,		SCOUT, 		SCOUT,		MAJOR);	//FRONT
		blueLineUpMy = blueLineUp.makeLineUp(BLUE,
				SCOUT,		null, 		SCOUT,		null,		SCOUT,		SCOUT, 		SCOUT,		null, 		null,		SCOUT, //FRONT
				null,		SCOUT, 		null,		null,		SCOUT,		null, 		null,		null, 		null,		null,
				null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null,
				null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null);
		board = new BoardImpl(10, 10, redLineUpMy, blueLineUpMy, EPSILON);
		theGame = makeGame(EPSILON, board); //Game Factory
	}
	
	private void setupDepleteBombs()
	{	//								CHOKE		CHOKE								CHOKE		CHOKE		
		redLineUpMy = redLineUp.makeLineUp(RED,
				null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null,
				null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null,
				SCOUT,		SCOUT, 		null,		null,		null,		null, 		null,		null, 		null,		null,
				SCOUT,		BOMB, 		null,		null,		null,		SCOUT, 		null,		null, 		SCOUT,		SCOUT);	//FRONT
		blueLineUpMy = blueLineUp.makeLineUp(BLUE,
				BOMB,		SCOUT, 		null,		null,		SCOUT,		null, 		null,		null, 		SCOUT,		SCOUT, //FRONT
				SCOUT,		SCOUT, 		null,		null,		null,		null, 		null,		null, 		null,		null,
				null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null,
				null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null);
		board = new BoardImpl(10, 10, redLineUpMy, blueLineUpMy, EPSILON);
		theGame = makeGame(EPSILON, board); //Game Factory
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
		theGame = makeGame(EPSILON, theBoard);
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

	@Test 
	void MasterBoardSetupTest() // DONE
	{
		setupMaster();
		assertEquals(OK, theGame.move(3, 1, 4, 1));
		setupMasterLessPiece();
		assertThrows(StrategyException.class, () -> makeGame(EPSILON, theBoard));
		setupMasterMorePiece();
		assertThrows(StrategyException.class, () -> makeGame(EPSILON, theBoard));
		setupMasterWrongPiece();
		assertThrows(StrategyException.class, () -> makeGame(EPSILON, theBoard));
	}

	@Test 
	void noMovableStartupBlue() 
	{
		setupChoke();
		assertEquals(RED_WINS, theGame.move(3, 9, 4, 9));
	}
	
	@Test 
	void agressorAdvantage() 
	{
		//red
		setupAgressorAdvantage();
		assertEquals(OK, theGame.move(3, 4, 4, 4));
		assertEquals(OK, theGame.move(6, 4, 5, 4));
		assertEquals(STRIKE_RED, theGame.move(4, 4, 5, 4));
		//blue
		setupAgressorAdvantage();
		assertEquals(OK, theGame.move(3, 4, 4, 4));
		assertEquals(OK, theGame.move(6, 4, 5, 4));
		assertEquals(OK, theGame.move(3, 1, 4, 1));
		assertEquals(STRIKE_BLUE, theGame.move(5, 4, 4, 4));
	}
	
//null,		null, 		null,		null,		null,		SCOUT, 		null,		null, 		null,		null,
//null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null,
//null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null,
//SCOUT,	SCOUT,		SCOUT,		null,		SCOUT,		null, 		null,		SCOUT, 		SCOUT,		MAJOR); //FRONT

//SCOUT,	null, 		SCOUT,		null,		SCOUT,		SCOUT, 		SCOUT,		null, 		null,		SCOUT, //FRONT
//null,		SCOUT, 		null,		null,		SCOUT,		null, 		null,		null, 		null,		null,
//null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null,
//null,		null, 		null,		null,		null,		null, 		null,		null, 		null,		null)
	@Test 
	void distanceScoutAttack() 
	{
		setupDistanceScoutAttack();
		assertEquals(STRIKE_RED, theGame.move(3, 0, 6, 0)); //red up success
		assertEquals(RED_WINS, theGame.move(7, 1, 3, 1)); //blue up exceed distance
		
		setupDistanceScoutAttack();
		assertEquals(OK, theGame.move(3, 2, 2, 2)); 
		assertEquals(STRIKE_BLUE, theGame.move(6, 0, 3, 0)); //blue down success
		assertEquals(BLUE_WINS, theGame.move(3, 1, 7, 1)); //red up exceed distance
		
		setupDistanceScoutAttack();
		assertEquals(OK, theGame.move(3, 2, 2, 2)); 
		assertEquals(STRIKE_RED, theGame.move(6, 9, 3, 9));  //blue long attack defeated
		assertEquals(OK, theGame.move(3, 9, 4, 9)); //red unmoved
		
		setupDistanceScoutAttack();
		assertEquals(OK, theGame.move(3, 2, 2, 2)); 
		assertEquals(OK, theGame.move(6, 9, 4, 9));
		assertEquals(OK, theGame.move(3, 1, 4, 1));
		assertEquals(STRIKE_RED, theGame.move(4, 9, 3, 9));  //blue short attack defeated
		assertEquals(OK, theGame.move(4, 9, 5, 9));  //red moved

		setupDistanceScoutAttack();
		assertEquals(OK, theGame.move(3, 9, 2, 9)); //move Major away
		assertEquals(OK, theGame.move(6, 9, 3, 9));
		assertEquals(OK, theGame.move(3, 8, 6, 8));
		assertEquals(STRIKE_BLUE, theGame.move(3, 9, 3, 7)); // blue left success
		assertEquals(STRIKE_RED, theGame.move(6, 8, 6, 6)); // red left success
		assertEquals(OK, theGame.move(3, 7, 3, 6)); // blue over oppo piece
		assertEquals(STRIKE_RED, theGame.move(6, 6, 6, 5)); // red left success
		assertEquals(RED_WINS, theGame.move(3, 6, 3, 3)); // blue over oppo piece

		
		setupDistanceScoutAttack();
		assertEquals(OK, theGame.move(3, 9, 2, 9)); //move Major away
		assertEquals(OK, theGame.move(6, 9, 3, 9));
		assertEquals(OK, theGame.move(3, 8, 6, 8));
		assertEquals(STRIKE_BLUE, theGame.move(3, 9, 3, 7)); // blue left success
		assertEquals(STRIKE_RED, theGame.move(6, 8, 6, 6)); // red left success
		assertEquals(OK, theGame.move(3, 7, 3, 6));
		assertEquals(BLUE_WINS, theGame.move(6, 6, 6, 4)); // red over oppo piece


		setupDistanceScoutAttack();
		assertEquals(BLUE_WINS, theGame.move(3, 4, 7, 4)); //red over oppo piece

		setupDistanceScoutAttack();
		assertEquals(OK, theGame.move(3, 9, 2, 9)); //move Major away
		assertEquals(RED_WINS, theGame.move(7, 4, 3, 4)); //blue over self piece

		setupDistanceScoutAttack();
		assertEquals(BLUE_WINS, theGame.move(0, 5, 6, 5)); //red exceed distance attack
		
		setupDistanceScoutAttack();
		assertEquals(OK, theGame.move(3, 9, 2, 9)); //move Major away
		assertEquals(RED_WINS, theGame.move(6, 5, 0, 5)); //blue exceed distance attack
		
		setupDistanceScoutAttack();
		assertEquals(BLUE_WINS, theGame.move(3, 2, 6, 2)); //blue over choke
		
		setupDistanceScoutAttack();
		assertEquals(OK, theGame.move(3, 2, 2, 2)); 
		assertEquals(RED_WINS, theGame.move(6, 2, 3, 2)); //red over choke
	}
	

//[NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            
//[NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            
//SCOUT               SCOUT               [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            
//BOMB                SCOUT               [NORMAL]            [NORMAL]            SCOUT               [NORMAL]            [NORMAL]            [NORMAL]            SCOUT               SCOUT               
//[NORMAL]            [NORMAL]            [CHOKE]             [CHOKE]             [NORMAL]            [NORMAL]            [CHOKE]             [CHOKE]             [NORMAL]            [NORMAL]            
//[NORMAL]            [NORMAL]            [CHOKE]             [CHOKE]             [NORMAL]            [NORMAL]            [CHOKE]             [CHOKE]             [NORMAL]            [NORMAL]            
//SCOUT	              BOMB                [NORMAL]            [NORMAL]            [NORMAL]            SCOUT               [NORMAL]            [NORMAL]            SCOUT               SCOUT               
//SCOUT               SCOUT               [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            
//[NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            
//[NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            [NORMAL]            

	@Test 
	void depletingBombs() 
	{
		setupDepleteBombs();
		assertEquals(STRIKE_BLUE, theGame.move(3, 0, 6, 0)); // BOOM!
		assertEquals(OK, theGame.move(6, 9, 5, 9));
		assertEquals(OK, theGame.move(2, 0, 5, 0));
		assertEquals(OK, theGame.move(5, 9, 4, 9));
		assertEquals(OK, theGame.move(5, 0, 6, 0)); // blue bomb depleted
		assertEquals(OK, theGame.move(7, 0, 6, 0)); // bomb has been moved
		
		setupDepleteBombs();
		assertEquals(OK, theGame.move(3, 9, 4, 9));
		assertEquals(STRIKE_RED, theGame.move(6, 1, 3, 1));  // BOOM!
		assertEquals(OK, theGame.move(4, 9, 5, 9));
		assertEquals(OK, theGame.move(7, 1, 4, 1));
		assertEquals(OK, theGame.move(3, 8, 4, 8));
		assertEquals(OK, theGame.move(4, 1, 3, 1)); // red bomb depleted
		assertEquals(OK, theGame.move(2, 1, 3, 1)); // bomb has been moved
		
		setupDepleteBombs();
		assertEquals(BLUE_WINS, theGame.move(3, 1, 4, 1)); // BOOM!
		
		setupDepleteBombs();
		assertEquals(OK, theGame.move(3, 9, 4, 9));
		assertEquals(RED_WINS, theGame.move(6, 0, 5, 0)); // BOOM!
	}
}
