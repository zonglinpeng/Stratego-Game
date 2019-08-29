package strategy.zpeng.component;

import strategy.Board.SquareType;

import static strategy.Board.SquareType.*;

import java.util.Objects;


public class Square {
	
	private final int row;
	private final int col;
	private final SquareType choked;
	
	/**
	 * Constructor of the square
	 * @param row
	 * @param col
	 */
	public Square(int row, int col) {
		this.row = row;
		this.col = col;
		choked = NORMAL; //Beta
	}
	
	/**
	 * Constructor of the square
	 * @param row
	 * @param col
	 */
	public Square(int row, int col, SquareType type) {
		this.row = row;
		this.col = col;
		choked = type; //Gamma
	}
	
	/**
	 * Get the square row
	 * @return the row
	 */
	public final int getRow(){
		return this.row;
	}
	
	/**
	 * Get the square column
	 * @return the column
	 */
	public final int getColumn() {
		return this.col;
	}
	
	/**
	 * Get the square type
	 * @return the type
	 */
	public final SquareType getSquareType() {
		return choked;
	}
	
	/**
	 * Hashcode
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(col, row);
	}
	
	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Square other = (Square) obj;
		return col == other.col && row == other.row;
	}
}
