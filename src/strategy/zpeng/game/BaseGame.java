package strategy.zpeng.game;

import static strategy.Piece.PieceColor.BLUE;
import static strategy.Piece.PieceColor.RED;
import static strategy.Piece.PieceType.BOMB;
import static strategy.Piece.PieceType.FLAG;
import static strategy.StrategyGame.MoveResult.BLUE_WINS;
import static strategy.StrategyGame.MoveResult.GAME_OVER;
import static strategy.StrategyGame.MoveResult.OK;
import static strategy.StrategyGame.MoveResult.RED_WINS;
import static strategy.StrategyGame.MoveResult.STRIKE_RED;
import static strategy.StrategyGame.MoveResult.STRIKE_BLUE;
import static strategy.StrategyGame.Version.*;

import java.util.LinkedList;
import java.util.List;

import strategy.StrategyGame;
import strategy.Piece.PieceColor;
import strategy.Piece.PieceType;
import strategy.zpeng.component.BoardImpl;
import strategy.zpeng.component.PieceImpl;
import strategy.zpeng.component.Square;


public abstract class BaseGame implements StrategyGame{
	
	protected int boardSize;
	protected Rules rules;
	protected PieceColor whoseTurn;
	protected MoveResult gameState;
	protected BoardImpl myBoard;
	protected List<List<Integer>> redTrace;
	protected List<List<Integer>> blueTrace;
	private List<Square> bombDepletion;

	
	/**
	 * Constructor of Base Game
	 * @param board the given board of a game
	 */
	public BaseGame(BoardImpl board) {
		this.boardSize = 0;
		this.myBoard = board;
		this.rules = new Rules();
		this.whoseTurn = RED;
		this.gameState = OK;
		this.redTrace = new LinkedList<>();
		this.blueTrace = new LinkedList<>();
		this.bombDepletion = new LinkedList<>();
	}
	
	/**
	 * Determines the consequence of a move
	 * @param from row
	 * @param from column
	 * @param to row
	 * @param to column
	 * @return the move result of such a move
	 */
	public abstract MoveResult move(int fr, int fc, int tr, int tc);

	/**
	 * Set the board size according to the version
	 * @param version of the game
	 */
	protected void setBoardSize(Version version) {
		this.boardSize = (version == DELTA) || (version == EPSILON) ? 10 : 6;
	}
	
	/**
	 * Check the game states for BETA before making a move
	 * @param counter from BETA round counter
	 * @param fr from row 
	 * @param fc from column
	 * @param tr to row
	 * @param tc to column
	 * @return the game state result
	 */
	protected MoveResult checkPregameState(double counter, int fr, int fc, int tr, int tc) {
		// GAME_OVER
		MoveResult isGameOver = this.checkGameOver();
		if(isGameOver != OK) {
			return checkGameOver();
		}
		
		// BETA 8 ROUNDS
		if(counter > 8) {
			return this.gameState = RED_WINS;
		}
		
		// Check From Piece
		MoveResult isFromPieceValid = this.checkFromPiece(fr, fc);
		if(isFromPieceValid != OK) {
			return isFromPieceValid;
		}
		
		return OK;
	}
	
	/**
	 * OVERRIDE: check game state for GAMMA and DELTA
	 * @param counter from BETA round counter
	 * @param fr from row 
	 * @param fc from column
	 * @param tr to row
	 * @param tc to column
	 * @return the game state result
	 */
	protected MoveResult checkPregameState(int fr, int fc, int tr, int tc) {
		// GAME_OVER
		MoveResult isGameOver = this.checkGameOver();
		if(isGameOver != OK) {
			return checkGameOver();
		}
		
		// Check From Piece
		MoveResult isFromPieceValid = this.checkFromPiece(fr, fc);
		if(isFromPieceValid != OK) {
			return isFromPieceValid;
		}
		
		return OK;
	}
	
	/**
	 * Check the validity of a move
	 * @param fr from row 
	 * @param fc from column
	 * @param tr to row
	 * @param tc to column
	 * @return the game state result
	 */
	protected MoveResult checkMoveValidity(int fr, int fc, int tr, int tc) {
		MoveResult theResult = OK;
		PieceImpl fromPiece = this.myBoard.getPieceAt(fr, fc);
		PieceType fromPieceType = fromPiece.getPieceType();
		Square fromSquare = new Square(fr, fc);
		Square toSquare = new Square(tr, tc);
		
		boolean isValidMove = this.rules.isValidMove(this.myBoard, fromPieceType, boardSize, fr, fc, tr, tc);
		
		// VALID MOVE
		if(isValidMove) {
			this.myBoard.putPieceAt(toSquare, fromPiece);
			this.myBoard.removePieceAt(fromSquare);
		}
		theResult = this.finalResult(isValidMove);
		return this.gameState = theResult;
	}
	
	/**
	 * Check the validity of a strike
	 * @param fr from row 
	 * @param fc from column
	 * @param tr to row
	 * @param tc to column
	 * @return the game state result
	 */
	protected MoveResult checkStrikeValidity(BoardImpl board, int fr, int fc, int tr, int tc, Version version) {
		MoveResult theResult = OK;
		PieceType toPieceType = null; //DELTA
		boolean isValidStrike = false;
		Square fromSquare = new Square(fr, fc);
		Square toSquare = new Square(tr, tc);
		PieceImpl fromPiece = this.myBoard.getPieceAt(fr, fc);
		PieceImpl toPiece = this.myBoard.getPieceAt(tr, tc);

		// STRIKE
		toPieceType = toPiece.getPieceType(); // DELTA
		isValidStrike = this.rules.isValidAttack(board, fr, fc, tr, tc, version);

		// VALID
		if (isValidStrike) {
			int attackDistance = Math.abs(fr - tr) + Math.abs(fc - tc); // EPSILON
			
			// Strike cancels repetition
			if(this.whoseTurn == RED) {
				this.redTrace = new LinkedList<>();
			}else {
				this.blueTrace = new LinkedList<>();
			}
			
			// Bomb depletion
			if(version == EPSILON && toPiece.getPieceType() == BOMB) { // EPSILON
				if(this.bombDepletion.contains(toSquare)) {	//exploded once
					this.bombDepletion.remove(toSquare);
					this.myBoard.removePieceAt(fromSquare);
					this.myBoard.removePieceAt(toSquare);
					return this.gameState = OK;
				}else { //not trigger yet
					this.bombDepletion.add(toSquare);
				}
			}
			
			theResult = this.rules.canDefeat(fromPiece, toPiece);

			// DRAW
			if (theResult == OK) {
				if(version == EPSILON) { // EPSILON
					this.myBoard.putPieceAt(toSquare, fromPiece);
					this.myBoard.removePieceAt(fromSquare);
					theResult = this.whoseTurn == RED? STRIKE_RED: STRIKE_BLUE;
				}else{
					this.myBoard.removePieceAt(fromSquare);
					this.myBoard.removePieceAt(toSquare);
				}
			}

			// CONQUERED
			else {
				PieceColor winner = theResult == STRIKE_RED ? RED : BLUE;
				boolean isMasterWinner = winner == this.whoseTurn;
				if (isMasterWinner) {
					this.myBoard.putPieceAt(toSquare, fromPiece);
					this.myBoard.removePieceAt(fromSquare);
					// Flag conquered
					if (toPiece.getPieceType() == FLAG) {
						theResult = theResult == STRIKE_RED ? RED_WINS : BLUE_WINS;
					}
				} else {
					if (toPieceType != BOMB && attackDistance == 1) { // DELTA 
						this.myBoard.putPieceAt(fromSquare, toPiece); //revert attach
						this.myBoard.removePieceAt(toSquare);
					} else {
						this.myBoard.removePieceAt(fromSquare); // DELTA && EPSILON long attack
					}
				}
			}
		} 
		// INVALID
		else {
			theResult = this.finalResult(false);
		}
		
		return this.gameState = theResult;
	}

	/**
	 * Check if the opponents has movable pieces
	 * @return the game state result
	 */
	protected MoveResult checkPostGameState(int fr, int fc, int tr, int tc, Version version) { //DELTA
		MoveResult repetitionCheck = this.checkRepetition(fr, fc, tr, tc);
		if(repetitionCheck != OK) {
			return repetitionCheck; 
		}
		
		if(version == DELTA || version == EPSILON) {
			MoveResult movableOppentCheck = this.rules.isMovableRemaining(this.myBoard, this.whoseTurn, this.boardSize);
			PieceColor opponentColor = this.whoseTurn == RED? BLUE: RED;
			MoveResult movableSelfCheck = this.rules.isMovableRemaining(this.myBoard, opponentColor, this.boardSize);

			if(movableOppentCheck != OK) {
				return movableOppentCheck; 
			}else if(movableSelfCheck != OK) {
				return movableSelfCheck;
			}
		}
		return OK;
	}
	
	/**
	 * The operation after move is done
	 */
	protected void postActionProcess() {
		this.whoseTurn = whoseTurn==RED ? BLUE : RED;
	}
	
	/**
	 * translate the move result
	 * @param is the move a ValidMove
	 * @return the move result
	 */
	protected MoveResult finalResult(boolean isValidMove) {
		MoveResult result = OK;
		if(!isValidMove) {
			result = this.whoseTurn==RED ? BLUE_WINS : RED_WINS;
		}
		return this.gameState = result;
	}
	
	/**
	 * Print the board for debugging
	 */
	protected void printBoard() {		
		System.out.println("\n_______________________GAME_______________________\n");
		for(int i = this.boardSize - 1; i >= 0; --i) {
			for(int j = 0; j <= this.boardSize - 1; ++j) {
				PieceImpl thePiece = this.myBoard.getPieceAt(i, j);
				if(thePiece == null) {
					System.out.printf("%-20s", "[" + this.myBoard.getSquareTypeAt(i, j).toString() + "]");
				}
				else {
					if(thePiece.getPieceType() != null)
						System.out.printf("%-20s",thePiece.getPieceType().toString());
					else
						System.out.printf("%-20s", "null");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * HELPER: Check if the move has been repeated or not
	 * @param fr from row 
	 * @param fc from column
	 * @param tr to row
	 * @param tc to column
	 * @return the a move result of the check
	 */
	private MoveResult checkRepetition(int fr, int fc, int tr, int tc) {
		// Compare trace
		List<List<Integer>> traceList = this.whoseTurn==RED ? redTrace : blueTrace;
		if(traceList.size() >= 2) {
			List<Integer> twoStepsAway = traceList.get(traceList.size() - 2);
			List<Integer> oneStepsAway = traceList.get(traceList.size() - 1);
			boolean forward = twoStepsAway.get(2) == oneStepsAway.get(0) && twoStepsAway.get(3) == oneStepsAway.get(1)
					&& oneStepsAway.get(2) == twoStepsAway.get(0) && oneStepsAway.get(3) == twoStepsAway.get(1);
			boolean back = oneStepsAway.get(2) == fr && oneStepsAway.get(3) == fc
					&& oneStepsAway.get(0) == tr && oneStepsAway.get(1) == tc;
			if(forward && back) {
				return this.finalResult(false); 
			}
		}
		
		// Record trace
		List<Integer> currentTrace = new LinkedList<>();
		currentTrace.add(fr);
		currentTrace.add(fc);
		currentTrace.add(tr);
		currentTrace.add(tc);
		if(this.whoseTurn==RED) {
			redTrace.add(currentTrace);
		}else {
			blueTrace.add(currentTrace);
		}
		
		return OK;		
	}
	
	/**
	 * HELPER: Check if the game is done
	 * @return the state of game
	 */
	private MoveResult checkGameOver() {
		// GAME_OVER
		if(this.gameState == BLUE_WINS || this.gameState == RED_WINS || this.gameState == GAME_OVER) {
			return this.gameState = GAME_OVER;
		}
		return OK;
	}
	
	/**
	 * HELPER: Check if the source piece is valid
	 * @param fr from row
	 * @param fc from column
	 * @return the game state
	 */
	private MoveResult checkFromPiece(int fr, int fc) {
		//Selected piece does not exist
		PieceImpl fromPiece = this.myBoard.getPieceAt(fr, fc);

		if(fromPiece == null) {
			return this.finalResult(false);
		}
		if(fromPiece.getPieceColor() != this.whoseTurn) {
			return this.finalResult(false);
		}
		return OK;
	}
}
