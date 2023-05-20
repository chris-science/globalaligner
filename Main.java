/***************************************************************/
/* Main.java - this is the entry point for the global aligner */
/***************************************************************/


/* declare name of new package for this class */
package globalaligner ;


/* make this class visible to itself */
import globalaligner.* ;


/* make the basic functions of Java visisble to this class */
import java.lang.* ;


/* this is the main entry point for the program, extends top Object class */
public class Main extends Object
{

	/* program starts in this method*/
	static public void main (String[] args)
	{
		/* initialize two Strings, to hold the sequences to be globally aligned */
		String sequence_1 = "aatgcc" ;
		String sequence_2 = "cgaatg" ;
		
		/* declare variables to hold the scores for nucleotide match, mismatch, and indel */
		int score_match    =  1 ;
		int score_mismatch =  0 ;
		int score_indel    = -1 ;
		
		
		
		/* create a GlobalAligner object */
		GlobalAligner global_alignment = new GlobalAligner () ;
		
		/* set sequences */
		global_alignment.setSequences(sequence_1, sequence_2) ;
		
		/* set scores */
		global_alignment.setScores(score_match, score_mismatch, score_indel) ;
		
		/* set inital array */
		global_alignment.setArray() ;
		
		
		
		/* view sequences to be aligned */
		System.out.format("%n") ;
		global_alignment.viewSequences() ;
		
		
		/* view alignment matrix */
		System.out.format("%nAlignment Matrix - Initialized%n") ;
		global_alignment.viewArray() ;
		
		System.out.format( "%n" ) ;
		System.out.format( "%n" ) ;
				
		
		/* set left-most column scores */
		global_alignment.setArrayLeftCol() ;
		
		/* view alignment matrix */
		System.out.format("%nAlignment Matrix - Left Column Initialized%n") ;
		global_alignment.viewArray() ;

		System.out.println("") ;
		System.out.println("") ;
		
		
		
		/* set top-most row scores */
		global_alignment.setArrayTopRow() ;
		
		/* view alignment matrix */
		System.out.format("%nAlignment Matrix - Top Row Initialized%n") ;
		global_alignment.viewArray() ;
		
		System.out.format( "%n" ) ;
		System.out.format( "%n" ) ;
		
		
		/*
		/* calculate score for cell [1][1]
		global_alignment.getScoreVerticalForCell(1,1) ;

		/* calculate score for cell [1][1]
		global_alignment.getScoreHorizontalForCell(1,1) ;

		/* calculate score for cell [1][1] 
		global_alignment.getScoreDiagonalForCell(1,1) ;
		
		/* view alignment matrix 
		global_alignment.viewArray() ;
		
		System.out.println("") ;
		System.out.println("") ;
		*/
		
		
		
		/*
		/* get max score for row with index 1 (i.e. the second row
		global_alignment.calculateScoresForRow(1) ;
		
		/* view alignment matrix
		System.out.format("%nAlignment Matrix - Second Row Initialized%n") ;
		global_alignment.viewArray() ;
		*/



		/* get max scores for entire array */
		global_alignment.calculateScoresForArray() ;
		
		/* view alignment matrix */
		System.out.format("%nAlignment Matrix - Third Row Initialized%n") ;
		global_alignment.viewArray() ;

		
		System.out.format( "%n" ) ;
		System.out.format( "%n" ) ;

	}

}