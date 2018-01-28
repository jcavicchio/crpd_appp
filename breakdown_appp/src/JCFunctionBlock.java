import java.util.ArrayList;
import java.util.Iterator;

/**
 * JCFunctionBlock is a fundamental class whose purpose is to contain a set of
 * sub-blocks representing a function definition. These sub-blocks form the 
 * structure of the control flow graph basic block objects for the 
 * specified function.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCFunctionBlock extends JCBlock 
{
	private static boolean _debugAddMinimumCostSolution = false; // JCC
	private static boolean _displayBlockSolutions = false;
	private static boolean _debugCostMapSolution = false; // JCC
	private static boolean _debugNextBasicBlockPair = false;
	private static boolean _debugOldSolutionCheck = false;
	private static boolean _debugSearchOldSolutionCheck = false;
	private static boolean _debugSolutionCombinedCostKeys = false; // JCC
	private static boolean _debugSolutionPreemptionPoints = false;
	private static boolean _debugNPRRegionCheck = false;
	private static boolean _debugSupportMultiplePreemptionPoints = true; // JCC3
	private static boolean _debugDisplayPreemptionFunctionBlockSolutions = false;
	private static boolean _debugDisplayPreemptionFunctionBlockNumberOfSolutions = false; // JCC2
	private static boolean _debugInputPreemptionSolutionSizes = false; // JCC2
	
	private static int _nextID = 0;

	private static int _functionBlockCount = 0;                 // Counter to count which function block object it is
	private static ArrayList<JCFunctionBlock> _functionBlocks = 
			          new ArrayList<JCFunctionBlock>();         // Functions contained in the graph

	private int _functionBlockID;
	
    private String _functionName;
    private ArrayList<Integer> _functionCallBlocks;
    
	/**
	 * Default constructor.
	 */
	JCFunctionBlock()
	{
		super();
		_functionBlockID = _nextID;
		_nextID++;
		_functionBlockCount = _nextID;
		_functionName = "";
		_functionCallBlocks = new ArrayList<Integer>();
		setFunctionBlock(_functionBlockID, this);
	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCFunctionBlock
	 */
	public static void reset()
	{
		_nextID = 0;
		_functionBlockCount = 0;
		_functionBlocks = new ArrayList<JCFunctionBlock>();
	}
	
	/**
	 * Returns the numeric identifier of the function block object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _functionBlockID    the identifier of this function block object
	 * 
	 * @see                        JCFunctionBlock
	 */
	public int getFunctionBlockID()
	{
		return _functionBlockID;
	}

	/**
	 * Returns the string name of the function block object. 
	 *
	 * @return _ID     the string name of this function block object
	 * 
	 * @see            JCFunctionBlock
	 */
	public String getFunctionBlockName()
	{
		String id = "JCFunctionBlock" + _functionBlockID;
		
		return id;
	}
	
	/**
	 * Stores the function block object at the specified location in
	 * the function block array. 
	 *
	 * @param  functionBlockID     the identifier of the stored function block object
	 * 
	 * @param  functionBlockObj    the function block object to be stored
	 * 
	 * @see                        JCFunctionBlock
	 */
	public static void setFunctionBlock(int functionBlockID, JCFunctionBlock functionBlockObj)
	{
		if (functionBlockID < _functionBlockCount)
		{
			_functionBlocks.add(functionBlockObj);
		}
	}
	
	/**
	 * Returns the function block object associated with the specified identifier. 
	 *
	 * @param  functionBlockID      the identifier of the stored function block object
	 * 
	 * @return functionBlockObj     the function block object stored
	 * 
	 * @see                         JCFunctionBlock
	 */
	public static JCFunctionBlock getFunctionBlock(int functionBlockID)
	{
		if (functionBlockID < _functionBlockCount)
		{
	        return _functionBlocks.get(functionBlockID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Returns the string name of the function block object. 
	 *
	 * @return _functionName   the string name of this function block object
	 * 
	 * @see                    JCFunctionBlock
	 */
	public String getFunctionName()
	{
		return _functionName;
	}
	
	/**
	 * Sets the string name of the function block object. 
	 *
	 * @param functionName     the string name of this function block object
	 * 
	 * @see                    JCFunctionBlock
	 */
	public void setFunctionName(String functionName)
	{
		_functionName = functionName;
	}
	
	/**
	 * Adds the function call block object to this function block object. 
	 *
	 * @param  functionCallBlock  the function call block object to add to this function
	 *                            block object
	 *               
	 * @see                       JCFunctionBlock
	 * @see                       JCFunctionCallBlock
	 */
	public void addFunctionCallBlock(JCFunctionCallBlock functionCallBlock)
	{
		if (_functionCallBlocks.add(functionCallBlock.getFunctionCallBlockID()) != true)
		{
			System.out.println("JCFunctionBlock: Error adding function call block " + functionCallBlock.getFunctionCallBlockName());
		}
		functionCallBlock.setFunctionBlock(this);
	}
	
	/**
	 * Removes the function call block object from this function block object. 
	 *
	 * @param  functionCallBlock  the function call block object to remove from this
	 *                            function block object
	 *               
	 * @see                       JCFunctionBlock
	 * @see                       JCFunctionCallBlock
	 */
	public void removeFunctionCallBlock(JCFunctionCallBlock functionCallBlock)
	{
		if (_functionCallBlocks.remove((Integer)functionCallBlock.getFunctionCallBlockID()) != true)
		{
			System.out.println("JCFunctionBlock: Error removing basic block " + functionCallBlock.getFunctionCallBlockName());
		}
		functionCallBlock.setFunctionBlock(null);
	}
	
	/**
	 * Determines if the specified function call block has a containing relationship to
	 * this function block object. 
	 *
	 * @param  functionCallBlock     the function call block object to check for a containing
	 *                               relationship to this function block object
	 *               
	 * @return hasFunctionCallBlock  true/false indicating is the specified function call block
	 *                               object is contained in this function block
	 *                        
	 * @see                          JCFunctionBlock
	 * @see                          JCFunctionCallBlock
	 */
	public boolean hasFunctionCallBlock(JCFunctionCallBlock functionCallBlock)
	{
		return _functionCallBlocks.contains(functionCallBlock.getFunctionCallBlockID());
	}
	
	/**
	 * Determines if the specified function call block has a containing relationship to
	 * this function block object. 
	 *
	 * @param  functionCallBlockID   the function call block object to check for a containing
	 *                               relationship to this function block object
	 *               
	 * @return hasFunctionCallBlock  true/false indicating is the specified function call block
	 *                               object is contained in this function block
	 *                        
	 * @see                          JCFunctionBlock
	 * @see                          JCFunctionCallBlock
	 */
	public boolean hasFunctionCallBlock(Integer functionCallBlockID)
	{
		return _functionCallBlocks.contains(functionCallBlockID);
	}

	/**
	 * Finds the basic block object from within this section block object
	 * with the specified block name. 
	 *
	 * @param  functionCallBlockName  the string name of the function call block 
	 *                                object to search for
	 *               
	 * @return functionCallBlock      the found function call block object in this 
	 *                                function block object
	 *               
	 * @see                           JCFunctionBlock
	 * @see                           JCFunctionCallBlock
	 */
	public JCFunctionCallBlock findFunctionCallBlock(String functionCallBlockName)
	{
		JCFunctionCallBlock currentFunctionCallBlock;
		Integer currentFCBID;
	    String currentFunctionCallBlockName;
		JCFunctionCallBlock functionCallBlock = null;
	    Iterator<Integer> iterator;
	    
	    if (functionCallBlockName != null)
	    {
		    iterator = getFunctionCallBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (functionCallBlockName == null))
		        {
		        	currentFCBID = iterator.next();
		        	currentFunctionCallBlock = JCFunctionCallBlock.getFunctionCallBlock(currentFCBID);
		        	currentFunctionCallBlockName = currentFunctionCallBlock.getFunctionCallBlockName();
		        	if (currentFunctionCallBlock != null)
		        	{
		        		if (currentFunctionCallBlockName.compareTo(functionCallBlockName) == 0)
		        		{
		        			functionCallBlock = currentFunctionCallBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return functionCallBlock;
	}
	
	/**
	 * Returns the function call block object at the specified index in this 
	 * function block object.
	 *
	 * @param  fcbIndex        the index of the function call block object to return
	 *               
	 * @return basicBlock      the found function call block object in this function block
	 *                         object
	 *               
	 * @see                    JCFunctionBlock
	 * @see                    JCFunctionCallBlock
	 */
	public JCFunctionCallBlock getFunctionCallBlockAtIndex(int fcbIndex)
	{
		int fcbBlockID;
		JCFunctionCallBlock functionCallBlock;
		
		fcbBlockID = _functionCallBlocks.get(fcbIndex);
		functionCallBlock = JCFunctionCallBlock.getFunctionCallBlock(fcbBlockID);
		
		return functionCallBlock;
	}
	
	/**
	 * Returns the number of function call block objects in this function block 
	 * object. 
	 *
	 * @return numberOfFunctionCallBlocks  the number of function call blocks contained 
	 *                                     in this function block object
	 *                        
	 * @see                                JCFunctionBlock
	 * @see                                JCFunctionCallBlock
	 */
	public long numberOfFunctionCallBlocks()
	{
		return _functionCallBlocks.size();
	}
	
	/**
	 * Returns the function call block iterator for accessing the function call
	 * block objects in this function block object. 
	 *
	 * @return iterator  the function call block iterator for accessing the 
	 *                   function call block objects
	 *                        
	 * @see              JCFunctionBlock
	 * @see              JCFunctionCallBlock
	 */
	public Iterator<Integer> getFunctionCallBlockIterator()
	{
		return _functionCallBlocks.iterator();
	}
	
	/**
	 * Finds the function block object with the specified function name. 
	 *
	 * @param functionName   The function name to search for
	 * 
	 * @see                  JCFunctionBlock
	 */
    public static JCFunctionBlock findFunctionBlock(String functionName)
    {
	    int                  functionBlockID;
	    JCFunctionBlock      functionBlock = null;
	    JCFunctionBlock      currentFunctionBlock;
	    String               currentFunctionName;
		
		for (functionBlockID = 0; ((functionBlockID < _functionBlocks.size()) && (functionBlock == null)); functionBlockID++)
		{
			currentFunctionBlock = JCFunctionBlock.getFunctionBlock(functionBlockID);
			currentFunctionName = currentFunctionBlock.getFunctionName();
			if (currentFunctionName.compareTo(functionName) == 0)
			{
				functionBlock = currentFunctionBlock;
			}
		}

        return functionBlock;
    }

	/**
	 * Displays the input preemption point solutions for debugging purposes.
	 *
	 * @param  functionBlock          the function block object
	 *               
	 * @see                           JCBlock
	 * @see                           JCCostKey
	 * @see                           JCCostSolution
	 */
	public void displayPreemptionSolutionSizes(JCBlock functionBlock)
	{
		Iterator<JCCostSolution> blockPredecessorMapIterator;
		JCCostKey existingBlockCostKey;
		JCCostSolution existingBlockCostSolution;
		int solutionNumber;

		if (functionBlock != null)
		{
			System.out.println("The number of predecessor solutions is " + functionBlock.getSolutionMap().size());
			solutionNumber = 1;
			blockPredecessorMapIterator = functionBlock.getSolutionMap().iterator();
			while (blockPredecessorMapIterator.hasNext() == true)
			{
				existingBlockCostSolution = blockPredecessorMapIterator.next();
				existingBlockCostKey = existingBlockCostSolution.getSolutionKey();
				System.out.println("    Solution " + solutionNumber + " (" + existingBlockCostKey.getLeftIndex() + ","  + (existingBlockCostKey.getRightIndex())  + ","  + (existingBlockCostKey.getLeftBasicBlock()) + ","  + (existingBlockCostKey.getRightBasicBlock()) + ") number of preemption solutions is " + existingBlockCostSolution.numberOfPreemptionPointSolutions());
				solutionNumber++;
			}
		}	
	}
	
    /**
	 * Computes the potential preemption point solutions for the specified function
	 * block object. 
	 *
	 * @param  functionBlock          the function block object to compute the preemption cost
	 *                                solutions from
	 *               
	 * @param  pcmMatrix              the preemption cost matrix used to compute the preemption cost
	 *                                solution
	 *               
	 * @see                           ArrayList
	 * @see                           JCBasicBlock
	 * @see                           JCBlock
	 * @see                           JCCostKey
	 * @see                           JCCostSolution
	 * @see                           JCConditionalBlock
	 * @see                           JCConditionalSection
	 * @see                           JCFunctionBlock
	 * @see                           JCPreemptionCostMatrix
	 * @see                           JCSectionBlock
	 */
	public void computeFunctionBlockSolution(JCBlock functionBlock, JCPreemptionCostMatrix pcmMatrix)
	{	
		JCCostSolution existingCostSolution;
		int existingCostValue;
		JCCostKey existingFunctionCostKey;
		JCCostSolution existingFunctionCostSolution;
		Iterator<JCCostSolution> functionBlockMapIterator;
	    int functionSolutionCostValue;
		int functionPredecessorNPRValue;
	    JCBasicBlock functionPredecessorQBasicBlock;
		JCPreemptionPoints functionPreemptionPoints;
		Iterator<JCPreemptionPoints> functionPreemptionPointSolutionIterator;
		int functionSuccessorNPRValue;
	    JCBasicBlock functionSuccessorQBasicBlock;
		JCCostKey minimumFunctionBlocksCostKey;
		JCCostSolution minimumFunctionBlocksCostSolution;
		JCPreemptionPoints minimumFunctionBlocksPreemptionPoints;
		int minimumFunctionBlocksCostValue;
		JCCostSolution solution;
		int solutionCount;
		Iterator<JCCostSolution> solutionIterator;
		boolean validSolution;
		
		if (_debugInputPreemptionSolutionSizes == true)
		{
            System.out.println("computeFunctionBlockSolution(): input preemption solution sizes:");
			displayPreemptionSolutionSizes(functionBlock);
		}
		
        // Create new hash map objects representing the new aggregate solution containing the next basic block.
        createCostSolution();
        
        functionBlockMapIterator = functionBlock.getSolutionMap().iterator();

        // Process the left or predecessor solutions.
		while (functionBlockMapIterator.hasNext() == true)
		{
			existingFunctionCostSolution = functionBlockMapIterator.next();
			existingFunctionCostKey = existingFunctionCostSolution.getSolutionKey();
			functionPredecessorQBasicBlock = JCBasicBlock.getBasicBlock(existingFunctionCostKey.getLeftBasicBlock());
	        functionPredecessorNPRValue = existingFunctionCostKey.getLeftIndex();
            functionSuccessorQBasicBlock = JCBasicBlock.getBasicBlock(existingFunctionCostKey.getRightBasicBlock());
            functionSuccessorNPRValue = existingFunctionCostKey.getRightIndex();
			
        	if (_debugNextBasicBlockPair == true)
            {
            	System.out.println("    JCFunctionBlock: Block " + this.getBlockName() + " processing next left predecessor basic block " + functionPredecessorQBasicBlock.getBBlockName() + " next left successor basic block " + functionSuccessorQBasicBlock.getBBlockName());
            }

            if (existingFunctionCostSolution != null)
            {
        		// Process the previous preemption based solutions generated.
                if (_debugOldSolutionCheck == true)
                {
                	System.out.println("    JCFunctionBlock: **Processing old function solution for (" + existingFunctionCostKey.getLeftIndex() + ","  + (existingFunctionCostKey.getRightIndex())  + ","  + (existingFunctionCostKey.getLeftBasicBlock()) + ","  + (existingFunctionCostKey.getRightBasicBlock()) + ")**");
                }
                
                if (_debugCostMapSolution == true)
                {
                	System.out.println("    JCFunctionBlock: function costmap (" + functionPredecessorNPRValue + "," + functionSuccessorNPRValue + ") = " + existingFunctionCostSolution.getSolutionCost() + " number of preemption point solutions = " + existingFunctionCostSolution.numberOfPreemptionPointSolutions());
                }

                functionPreemptionPointSolutionIterator = existingFunctionCostSolution.getPreemptionPointSolutionsIterator();
                while (functionPreemptionPointSolutionIterator.hasNext() == true)
                {
                	functionPreemptionPoints = functionPreemptionPointSolutionIterator.next();
    	            if (_debugSolutionPreemptionPoints == true)
    	            {
    	                System.out.print("    JCFunctionBlock: Existing function costmap solution preemption points: ");
    	                functionPreemptionPoints.displayObjectInformation();
		    	    	System.out.println();
	                }
	    	    	
                	if (_debugSearchOldSolutionCheck == true)
                    {
                    	System.out.println("    JCFunctionBlock: **Searching old solution for (" + existingFunctionCostKey.getLeftIndex() + ","  + (existingFunctionCostKey.getRightIndex()) + ","  + (existingFunctionCostKey.getLeftBasicBlock()) + ","  + (existingFunctionCostKey.getRightBasicBlock()) + ")**");
                    }

    	            if (_debugNPRRegionCheck == true)
                    {
    	                System.out.println("    JCFunctionBlock: Q = " + JCBlock.getQValue() + " Function NPR cost (" + existingFunctionCostKey.getLeftIndex() + "," + existingFunctionCostKey.getRightIndex() + ") = " + (existingFunctionCostKey.getRightIndex() + existingFunctionCostKey.getLeftIndex()));
                    }
    	                
                    // Check to ensure that maximum non-preemptive region constraint is satisfied.
    	            validSolution = true;
                    
                  	if (validSolution == true)
                    {
    	                if (_debugSolutionCombinedCostKeys == true)
    	                {
        	                System.out.println("    JCFunctionBlock: Q = " + JCBlock.getQValue() + " Function cost key (" + existingFunctionCostKey.getLeftIndex() + "," + existingFunctionCostKey.getRightIndex() + "," + existingFunctionCostKey.getLeftBasicBlock() + "," + existingFunctionCostKey.getRightBasicBlock() + ")");
    	                }
    	                
    	                minimumFunctionBlocksCostValue = existingFunctionCostSolution.getSolutionCost();
    	                functionSolutionCostValue = minimumFunctionBlocksCostValue;
                		minimumFunctionBlocksCostKey = new JCCostKey(existingFunctionCostKey.getLeftIndex(), existingFunctionCostKey.getRightIndex());
                		minimumFunctionBlocksCostKey.setLeftBasicBlock(functionPredecessorQBasicBlock.getBBlockID());
                		minimumFunctionBlocksCostKey.setRightBasicBlock(functionSuccessorQBasicBlock.getBBlockID());

                        existingCostSolution = getCostSolution(minimumFunctionBlocksCostKey);
                        if (existingCostSolution != null)
                        {
                        	existingCostValue = existingCostSolution.getSolutionCost();
                        	if (existingCostValue > functionSolutionCostValue)
                        	{
                        		existingCostSolution.setSolutionCost(functionSolutionCostValue);
                        		existingCostSolution.clearPreemptionPointSolutions();

                        		minimumFunctionBlocksPreemptionPoints = new JCPreemptionPoints(false);
                			  	minimumFunctionBlocksPreemptionPoints.combinePreemptionPoints(functionPreemptionPoints);

                			  	existingCostSolution.addPreemptionPointSolution(minimumFunctionBlocksPreemptionPoints);

                			  	if (_debugAddMinimumCostSolution == true)
            			        {
            			        	System.out.print("    JCFunctionBlock: adding minimum blocks cost (" + functionPredecessorNPRValue + "," + functionSuccessorNPRValue + ") = " + functionSolutionCostValue + " preemption points size " + existingCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
            			        	minimumFunctionBlocksPreemptionPoints.displayObjectInformation();
            			        	System.out.println();
            			        }
                        	}
                        	else if (existingCostValue == functionSolutionCostValue)
                        	{
            					if (_debugSupportMultiplePreemptionPoints == true)
            					{
                    			  	minimumFunctionBlocksPreemptionPoints = new JCPreemptionPoints(false);
                    			  	minimumFunctionBlocksPreemptionPoints.combinePreemptionPoints(functionPreemptionPoints);
                    			  	// Check to see if an existing visible preemption points solution exists
                    			  	if (existingCostSolution.hasVisiblePreemptionPointSolution(functionBlock, functionBlock, minimumFunctionBlocksPreemptionPoints) == false)
                    			  	{
                        			  	existingCostSolution.addPreemptionPointSolution(minimumFunctionBlocksPreemptionPoints);

        	        					if (_debugAddMinimumCostSolution == true)
        	        			        {
        	        			        	System.out.print("    JCFunctionBlock: updating minimum blocks cost (" + functionPredecessorNPRValue + "," + functionSuccessorNPRValue + ") = " + functionSolutionCostValue + " preemption points ");
        	        			        	minimumFunctionBlocksPreemptionPoints.displayObjectInformation();
        	        			        	System.out.println();
        	        			        }
                    			  	}
                        	    }
                        	}
                        }
                        else
                        {
                    		minimumFunctionBlocksCostSolution = new JCCostSolution();
                    		functionSolutionCostValue = existingFunctionCostSolution.getSolutionCost();
                    		minimumFunctionBlocksCostSolution.setSolutionCost(functionSolutionCostValue);
                    		minimumFunctionBlocksCostSolution.setSolutionKey(minimumFunctionBlocksCostKey);
            			  	minimumFunctionBlocksPreemptionPoints = new JCPreemptionPoints(false);
            			  	minimumFunctionBlocksPreemptionPoints.combinePreemptionPoints(functionPreemptionPoints);

            			  	minimumFunctionBlocksCostSolution.addPreemptionPointSolution(minimumFunctionBlocksPreemptionPoints);

            			  	putCostSolution(minimumFunctionBlocksCostSolution, true);

        					if (_debugAddMinimumCostSolution == true)
        			        {
        			        	System.out.print("    JCFunctionBlock: adding minimum blocks cost (" + functionPredecessorNPRValue + "," + functionSuccessorNPRValue + ") = " + functionSolutionCostValue + " preemption points ");
        			        	minimumFunctionBlocksPreemptionPoints.displayObjectInformation();
        			        	System.out.println();
        			        }
                        }
        			}
                }
            }
    	}
    		
		// Display the computed block solutions if necessary.
		if (_displayBlockSolutions == true)
		{
			displayBlockSolution();
		}
		else
		{
	    	if (_debugDisplayPreemptionFunctionBlockNumberOfSolutions == true)
	    	{
	            System.out.println("    JCFunctionBlock: Number of minimum cost solutions for function block " + getFunctionBlockName() + " is " + getSolutionMap().size());
	    	}
	    	if (_debugDisplayPreemptionFunctionBlockSolutions == true)
	    	{
		    	solutionIterator = getSolutionMap().iterator();
		    	solutionCount = 1;
		    	while (solutionIterator.hasNext() == true)
		    	{
		    		solution = solutionIterator.next();
		    		System.out.println("        Solution " + solutionCount + " number of preemption point solutions is " + solution.numberOfPreemptionPointSolutions());
		    		solutionCount++;
		    	}
	    	}
		}
		
        // Clean up the previous cost map and preemption points map information 
        // as we no longer need these data structures.  
        functionBlock.deleteCostSolution();
	}
	
	/**
	 * Returns the string function block object type name. 
	 *
	 * @return ID     the string type name of the function block object
	 * 
	 * @see           JCFunctionBlock
	 */
	@Override
	public String getObjectTypeName()
	{
		return "JCFunctionBlock";
	}
    
	/**
	 * Displays information about this function block object. 
	 *
	 * @see           JCFunctionBlock
	 */
    @Override
    public void displayObjectInformation()
    {
       	System.out.println("Function Block ID " + getFunctionBlockID() + " Name " + getFunctionBlockName() + " block ID " + getBlockID() + " block name " + getBlockName() + " sub-block ID " + getSubBlockID() + " sub-block name " + getSubBlockName());

    	super.displayObjectInformation();   	
    }

	/**
	 * Displays information about all function block objects. 
	 *
	 * @see           JCFunctionBlock
	 */
    public static void displayAllObjects()
    {
	    int                  functionID;
	    JCFunctionBlock      currentFunctionBlock;
		
    	System.out.println("The number of function blocks is " + _functionBlocks.size());
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("....................................................................................................................................");
		for (functionID = 0; functionID < _functionBlocks.size(); functionID++)
		{
			currentFunctionBlock = JCFunctionBlock.getFunctionBlock(functionID);
			currentFunctionBlock.displayObjectInformation();
	    	System.out.println("....................................................................................................................................");
		}
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}
