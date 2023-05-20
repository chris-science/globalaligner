/*************************************************************/
/* GlobalAligner.java - this contains the algorithm details  */
/*************************************************************/


/* declare name of new package for this class */
package globalaligner ;


/* make this class visible to itself */
import globalaligner.* ;


/* make the basic functions of Java visisble to this class */
import java.lang.* ;


/* this is the main entry point for the program, extends top Object class */
public class GlobalAligner extends Object
{
	
	/** FIELDS **/
	
	
	
	/* declare two Strings, to hold the sequences to be globally aligned */
	String sequence_1 ; // spread over rows
	String sequence_2 ; // spread over columns
	
	
	
	/* declare two int variables to hold the lengths of the Strings */
	int sequence_1_length ;
	int sequence_2_length ;
	
	
	
	/* declare a two dimensional matrix, this is implemented as a java multidimensional array */
	int global_alignment_array[][] ; // = new int [this.sequence_1.length() + 1] [this.sequence_2.length() + 1];
	
	
	
	/* declare variables to hold the scores for nucleotide match, mismatch, and indel */
	int score_match    ;
	int score_mismatch ;
	int score_indel    ;
	
	
	
	
	
	
	/** CONSTRUCTORS **/
	
	
		
	/* default constructor */
	GlobalAligner ()
	{
		super () ;
	}
	
	




	/** METHODS **/

	
	
	/* print sequences to be aligned */
	void viewSequences ()
	{
		System.out.format( "Beginning global alignment of these sequences:%n" ) ;
		System.out.format( "sequence_1: %s%n" , this.sequence_1 ) ;
		System.out.format( "sequence_2: %s%n" , this.sequence_2 ) ;
		System.out.format( "%n" ) ;
	}
	
	
	
	/* add  two sequences to be aligned, store their lengths in separate variables */
	void setSequences (String sequence_1, String sequence_2)
	{
		this.sequence_1 = sequence_1 ;
		this.sequence_2 = sequence_2 ;
		
		this.sequence_1_length = sequence_1.length() ;
		this.sequence_2_length = sequence_2.length() ;
	}
	

	
	/* add scoring system */
	void setScores (int score_match, int score_mismatch, int score_indel)
	{
		this.score_match     = score_match    ;
		this.score_mismatch  = score_mismatch ;
		this.score_indel     = score_indel    ;
	}



	/* create alignment array, add zero in top left cell, add first row and column scores */
	void setArray ()
	{
		/* create array in memory, this gets initalized with 0's b/c integer array */
		this.global_alignment_array = new int [this.sequence_1_length + 1] [this.sequence_2_length + 1] ;
		
		/* redundant, but set top left cell to zero */
		this.global_alignment_array[0][0] = 0 ;
	}



	/* add scores for left-most column */
	void setArrayLeftCol ()
	{
		/* fill in left-most column with indel scores */
		for ( int row = 1 ; row <= this.sequence_1_length ; row++ )
		{
			this.global_alignment_array[row][0] = this.global_alignment_array[row-1][0] + this.score_indel ;
		}
	}



	/* add scores for top-most row */
	void setArrayTopRow ()
	{		
		/* fill in top-most row with indel scores */
		for ( int col = 1 ; col <= this.sequence_2_length ; col++ )
		{
			this.global_alignment_array[0][col] = this.global_alignment_array[0][col-1] + this.score_indel ;
		}
	}
	
	
	
	/* calculate max cell  */
	void calculateScoresForArray ()
	{
		for ( int row = 1 ; row <= this.sequence_1_length ; row++ )
		{
			System.out.format( "Calculating Scores for Row %d" , row ) ;
			calculateScoresForRow(row) ;
		}
	}
	
	
	
	/* calculate max score for each cell in a row, excluding the left-most cell */
	void calculateScoresForRow (int row_index)
	{
		/* visit each cell in the row, except for the left-most cell (the one at index 0) */
		for ( int col_index = 1 ; col_index <= this.sequence_2_length ; col_index++ )
		{
			/* get the maximum score for the cell */
			int score_max = getScoreMaxForCell(row_index, col_index) ;
			
			/* add cell's max score to the alignment array */
			this.global_alignment_array[row_index][col_index] = score_max ;
		}
	}
	
	
	
	/* find the maximum score for a single cell */
	int getScoreMaxForCell(int row_index, int col_index)
	{
		
		/* print which cell is being processed */
		System.out.format( "%n%nFinding Max Score for Cell [%d][%d]%n%n" , row_index, col_index ) ;

		/* get vertical, horizontal, and diagonal scores by method call */
		int score_vertical   = getScoreVerticalForCell   (row_index, col_index) ;
		int score_horizontal = getScoreHorizontalForCell (row_index, col_index) ;
		int score_diagonal   = getScoreDiagonalForCell   (row_index, col_index) ;
		

		/* compare the three scores, choose the largest positive value, put it in array cell */
		//int score_max = Math.max( Math.max( score_vertical , score_horizontal) , score_diagonal ) ;
		//this.global_alignment_array[row_index][col_index] = score_max ;
		int score_max = Math.max( Math.max(score_vertical, score_horizontal) , score_diagonal ) ;

		System.out.format(" score_max= %4d%n", score_max) ;
		System.out.format( "%n" ) ;

		
		return score_max ;
	}
	
	

	/* calculate the vertical score for a cell */
	int getScoreVerticalForCell ( int row_index , int col_index )
	{
		System.out.format( "CellAbove:%n" ) ;
		System.out.format( "  RowIndex-1=%4d%n" , row_index-1 ) ;
		System.out.format( "  ColIndex  =%4d%n" , col_index   ) ;
		
		int score_vertical = this.global_alignment_array[row_index-1][col_index] + this.score_indel ;
		
		System.out.format( " >score_vertical=%3d%n%n" , score_vertical ) ;

		return score_vertical ;
	}



	/* calculate the horizontal score for a cell */
	int getScoreHorizontalForCell ( int row_index , int col_index )
	{
		System.out.format( "CellLeft:%n" ) ;
		System.out.format( "  RowIndex  =%4d%n" , row_index   ) ;
		System.out.format( "  ColIndex-1=%4d%n" , col_index-1 ) ;
		
		int score_horizontal = this.global_alignment_array[row_index][col_index-1] + this.score_indel ;
		
		System.out.format( " >score_horizontal=%3d%n%n" , score_horizontal ) ;

		return score_horizontal ;
	}



	/* calculate the diagonal score for a cell */
	int getScoreDiagonalForCell ( int row_index , int col_index )
	{
		System.out.format( "CellDiag:%n" ) ;
		System.out.format( "  RowIndex-1=%4d%n" , row_index-1 ) ;
		System.out.format( "  ColIndex-1=%4d%n" , col_index-1 ) ;
		
		int score_diagonal ;
		
		if ( this.sequence_1.charAt(row_index-1) == this.sequence_2.charAt(col_index-1) )
		{
			System.out.format( "   :For cell [%d][%d]%n" , row_index, col_index ) ;
			System.out.format( "   :sequence_1 character is: %c%n" , sequence_1.charAt(row_index-1) ) ;
			System.out.format( "   :sequence_2 character is: %c%n" , sequence_2.charAt(col_index-1) ) ;

			System.out.format( "   :Characters match.%n" ) ;
			score_diagonal = this.global_alignment_array[row_index-1][col_index-1] + this.score_match ;
		}
		else
		{
			System.out.format( "   :For cell [%d][%d]%n" , row_index, col_index ) ;
			System.out.format( "   :sequence_1 character is: %c%n" , sequence_1.charAt(row_index-1) ) ;
			System.out.format( "   :sequence_2 character is: %c%n" , sequence_2.charAt(col_index-1) ) ;

			System.out.format( "   :Characters don't match.%n" ) ;
			score_diagonal = this.global_alignment_array[row_index-1][col_index-1] + this.score_mismatch ;
		}

		System.out.format( " >score_diagonal=%3d%n%n" , score_diagonal ) ;
		
		return score_diagonal ;
	}

	
	
	/* view global_alignment_array, printed to terminal */
	void viewArray ()
	{
		for (int row = 0 ; row <= sequence_1.length() ; row++ )
		{
			for (int col = 0 ; col <= sequence_2.length() ; col++ )
			{
				System.out.format("%+4d", global_alignment_array[row][col]) ;
			}
			
			System.out.println("") ;
		}
	}
	
	
	/*  */
	
}
