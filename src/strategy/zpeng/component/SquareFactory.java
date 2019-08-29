package strategy.zpeng.component;

import strategy.Board.SquareType;
import strategy.zpeng.component.Square;

public final class SquareFactory {

	/**
	 * Make a square
	 * @param row of the square
	 * @param col of the square
	 * @return the object of square
	 */
	public static Square makeSquare(int row, int col)
	{
		return new Square(row, col);
	}
	
	/**
	 * Make a square
	 * @param row of the square
	 * @param col of the square
	 * @return the object of square
	 */
	public static Square makeSquare(int row, int col, SquareType type)
	{
		return new Square(row, col, type);
	}
}
