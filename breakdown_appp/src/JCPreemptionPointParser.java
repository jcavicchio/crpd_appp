import org.antlr.runtime.RecognitionException;

import org.antlr.runtime.ANTLRInputStream;

import java.io.*;

import org.antlr.runtime.CommonTokenStream;

/**
 * Program is a fundamental class whose purpose is to parse CFGs
 * comprising an entire program for the specified benchmark program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCPreemptionPointParser 
{
	String _grammarFileName;
	
	/**
	 * Default constructor.
	 */
	JCPreemptionPointParser()
    {
		_grammarFileName = "";
    }
	
	/**
	 * Returns the file name of the parse object. 
	 *
	 * @return _grammarFileName   the file name of this parser object
	 * 
	 * @see                       JCPreemptionPointParser
	 */
	public String getParseFileName()
	{
		return _grammarFileName;
	}
	
	/**
	 * Sets the file name of the parse object. 
	 *
	 * @param grammarFileName     the file name of this parser object
	 * 
	 * @see                       JCPreemptionPointParser
	 */
	public void setParseFileName(String grammarFileName)
	{
		_grammarFileName = grammarFileName;
	}

	/**
	 * Parses the file name given in this parser object. 
	 *
	 * @see                       JCPreemptionPointParser
	 */
	void parseFile()
	{
	    String fileName = _grammarFileName;
	    File file = new File(fileName);
	    FileInputStream fis = null;

	    try 
	    {
	    	// Reset the static variables for the PPP classes.
	    	JCSelectOptimalPP.resetAll();
	    	
	        // Open the input file stream
	        fis = new FileInputStream(file);

	        // Create a CharStream that reads from standard input
	        ANTLRInputStream input = new ANTLRInputStream(fis);

	         // Create a lexical analyser that feeds off of input CharStream
	 		JCA_treeLexer lexer = new JCA_treeLexer(input);
	 		
	         // Create a buffer of tokens pulled from the lexical analyser
	        CommonTokenStream tokens = new CommonTokenStream(lexer);
	        
	         // Create a parser that feeds off the tokens buffer
	        JCA_treeParser parser = new JCA_treeParser(tokens);
	        
	        try 
	        {
				parser.prog();
			} 
	        catch (RecognitionException e) 
	        {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // Close the input file
	        fis.close();
	    } 
	    catch (IOException e) 
	    {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
}
