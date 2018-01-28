import java.util.ArrayList;
import java.util.Iterator;

/**
 * JCFunctionCallBlock is a fundamental class whose purpose is to contain a set of
 * basic blocks representing a function invocation. These basic blocks form a 
 * section of the control flow graph basic block objects for the specified function
 * call.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCFunctionCallBlock extends JCBlock 
{
	private static boolean _debugAddMinimumCostSolution = false; // JCC
	private static boolean _debugDisplayBlockSolutions = false;
	private static boolean _debugDisplayPreemptionBlockSolutions = false;
	private static boolean _debugBlockCost = false;
	private static boolean _debugBlockPreemptionCost = false;
	private static boolean _debugCostMapSolution = false; // JCC
	private static boolean _debugCostMapSizes = false; // JCC2
	private static boolean _debugNextBasicBlockPair = false;
	private static boolean _debugMaximumPreemptionCost = false;
	private static boolean _debugOldSolutionCheck = false;
	private static boolean _debugSearchOldSolutionCheck = false;
	private static boolean _debugSolutionCombinedCostKeys = false; // JCC
	private static boolean _debugSolutionPreemptionPoints = false;
	private static boolean _debugNPRRegionCheck = false;
	private static boolean _debugSupportMultiplePreemptionPoints = true; // JCC3
	private static boolean _debugDisplayPreemptionFunctionCallBlockSolutions = false;
	private static boolean _debugDisplayPreemptionFunctionCallBlockNumberOfSolutions = false; // JCC2
	private static boolean _debugVisiblePreemptionPoints = false;
	private static boolean _debugInputPreemptionSolutionSizes = false; // JCC2

	private static int _nextID = 0;

	private static int _functionCallBlockCount = 0;                     // Counter to count which function call block object it is
	private static ArrayList<JCFunctionCallBlock> _functionCallBlocks = 
			          new ArrayList<JCFunctionCallBlock>();             // Function calls contained in the graph

	private int _functionCallBlockID;

    private String _functionCallName;
	private int _functionBlockID;

	/**
	 * Default constructor.
	 */
	JCFunctionCallBlock()
	{
		super();
		_functionCallBlockID = _nextID;
		_nextID++;
		_functionCallBlockCount = _nextID;
		_functionCallName = "";
		_functionBlockID = -1;
		setFunctionCallBlock(_functionCallBlockID, this);
	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCFunctionCallBlock
	 */
	public static void reset()
	{
		_nextID = 0;
		_functionCallBlockCount = 0;
		_functionCallBlocks = new ArrayList<JCFunctionCallBlock>();
	}
	
	/**
	 * Returns the numeric identifier of the function call block object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _functionCallBlockID    the identifier of this function call block object
	 * 
	 * @see                            JCFunctionCallBlock
	 */
	public int getFunctionCallBlockID()
	{
		return _functionCallBlockID;
	}

	/**
	 * Returns the string name of the function call block object. 
	 *
	 * @return _ID     the string name of this function call block object
	 * 
	 * @see            JCFunctionCallBlock
	 */
	public String getFunctionCallBlockName()
	{
		String id = "JCFunctionCallBlock" + _functionCallBlockID;
		
		return id;
	}
	
	/**
	 * Stores the function call block object at the specified location in
	 * the function call block array. 
	 *
	 * @param  functionCallBlockID    the identifier of the stored function call block object
	 * 
	 * @param  functionCallBlockObj   the function call block object to be stored
	 * 
	 * @see                           JCFunctionCallBlock
	 */
	public static void setFunctionCallBlock(int functionCallBlockID, JCFunctionCallBlock functionCallBlockObj)
	{
		if (functionCallBlockID < _functionCallBlockCount)
		{
			_functionCallBlocks.add(functionCallBlockObj);
		}
	}
	
	/**
	 * Returns the function block object associated with the specified identifier. 
	 *
	 * @param  functionCallBlockID  the identifier of the stored function call block object
	 * 
	 * @return functionBlockObj     the function call block object stored
	 * 
	 * @see                         JCFunctionCallBlock
	 */
	public static JCFunctionCallBlock getFunctionCallBlock(int functionCallBlockID)
	{
		if (functionCallBlockID < _functionCallBlockCount)
		{
	        return _functionCallBlocks.get(functionCallBlockID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Returns the string name of the function call block object. 
	 *
	 * @return _functionCallName   the string name of this function call block object
	 * 
	 * @see                        JCFunctionBlock
	 */
	public String getFunctionCallName()
	{
		return _functionCallName;
	}
	
	/**
	 * Sets the string name of the function call block object. 
	 *
	 * @param functionCallName     the string name of this function call block object
	 * 
	 * @see                        JCFunctionBlock
	 */
	public void setFunctionCallName(String functionCallName)
	{
		_functionCallName = functionCallName;
	}
	
	/**
	 * Gets the function block for this function call block 
	 *
	 * @param functionBlockObj    the function block for this function call object
	 * 
	 * @see                       JCFunctionBlock
	 * @see                       JCFunctionCallBlock
	 */
	JCFunctionBlock getFunctionBlock()
	{
		JCFunctionBlock functionBlockObj = null;
		
		if (_functionBlockID >= 0)
		{
			functionBlockObj = JCFunctionBlock.getFunctionBlock(_functionBlockID);
		}
		
		return functionBlockObj;
	}
	
	/**
	 * Sets the function block for this function call block 
	 *
	 * @param functionBlockObj    the function block for this function call block object
	 * 
	 * @see                       JCFunctionBlock
	 * @see                       JCFunctionCallBlock
	 */
	void setFunctionBlock(JCFunctionBlock functionBlockObj)
	{
		if (functionBlockObj != null)
		{
			_functionBlockID = functionBlockObj.getFunctionBlockID();
		}
		else
		{
			_functionBlockID = -1;
		}
	}
	
	/**
	 * Finds the function block object with the specified function name. 
	 *
	 * @param functionCallName   The function call name to search for
	 * 
	 * @see                      JCFunctionCallBlock
	 */
    public static JCFunctionCallBlock findFunctionCallBlock(String functionCallName)
    {
	    int                  functionCallBlockID;
	    JCFunctionCallBlock  functionCallBlock = null;
	    JCFunctionCallBlock  currentFunctionCallBlock;
	    String               currentFunctionCallName;
		
		for (functionCallBlockID = 0; ((functionCallBlockID < _functionCallBlocks.size()) && (functionCallBlock == null)); functionCallBlockID++)
		{
			currentFunctionCallBlock = JCFunctionCallBlock.getFunctionCallBlock(functionCallBlockID);
			currentFunctionCallName = currentFunctionCallBlock.getFunctionCallName();
			if (currentFunctionCallName.compareTo(functionCallName) == 0)
			{
				functionCallBlock = currentFunctionCallBlock;
			}
		}

        return functionCallBlock;
    }

	
	/**
	 * Displays the input preemption point solutions for debugging purposes.
	 *
	 * @param  predecessorBlock       the predecessor block object to the conditional block
	 *                                object
	 *               
	 * @param  conditionalBlock       the conditional block object to compute the preemption cost
	 *                                solution with respect to the predecessor basic block and
	 *                                successor basic block
	 *               
	 * @param  successorBlock         the successor block object to the conditional block
	 *                                object
	 *               
	 * @see                           JCBlock
	 * @see                           JCCostKey
	 * @see                           JCCostSolution
	 * @see                           JCConditionalBlock
	 * @see                           JCConditionalSection
	 * @see                           JCSubBlock
	 */
	public void displayPreemptionSolutionSizes(JCSubBlock predecessorBlock, JCSubBlock successorBlock)
	{
		JCCostKey existingLeftCostKey;
		JCCostSolution existingLeftCostSolution;
		JCCostKey existingRightCostKey;
		JCCostSolution existingRightCostSolution;
		Iterator<JCCostSolution> leftPredecessorMapIterator;
		Iterator<JCCostSolution> rightSuccessorMapIterator;
		int solutionNumber;

		if (predecessorBlock != null)
		{
			System.out.println("The number of predecessor solutions is " + predecessorBlock.getSolutionMap().size());
			solutionNumber = 1;
			leftPredecessorMapIterator = predecessorBlock.getSolutionMap().iterator();
			while (leftPredecessorMapIterator.hasNext() == true)
			{
				existingLeftCostSolution = leftPredecessorMapIterator.next();
				existingLeftCostKey = existingLeftCostSolution.getSolutionKey();
				System.out.println("    Solution " + solutionNumber + " (" + existingLeftCostKey.getLeftIndex() + ","  + (existingLeftCostKey.getRightIndex())  + ","  + (existingLeftCostKey.getLeftBasicBlock()) + ","  + (existingLeftCostKey.getRightBasicBlock()) + ") number of preemption solutions is " + existingLeftCostSolution.numberOfPreemptionPointSolutions());
				solutionNumber++;
			}
		}
		
		if (successorBlock != null)
		{
			System.out.println("The number of successor solutions is " + successorBlock.getSolutionMap().size());
			solutionNumber = 1;
	        rightSuccessorMapIterator = successorBlock.getSolutionMap().iterator();
			while (rightSuccessorMapIterator.hasNext() == true)
			{
				existingRightCostSolution = rightSuccessorMapIterator.next();
				existingRightCostKey = existingRightCostSolution.getSolutionKey();
				System.out.println("    Solution " + solutionNumber + " (" + existingRightCostKey.getLeftIndex() + ","  + (existingRightCostKey.getRightIndex())  + ","  + (existingRightCostKey.getLeftBasicBlock()) + ","  + (existingRightCostKey.getRightBasicBlock()) + ") number of preemption solutions is " + existingRightCostSolution.numberOfPreemptionPointSolutions());
				solutionNumber++;
			}
		}
	}
	
    /**
	 * Computes the potential preemption point solutions for the specified function
	 * call block object. 
	 *
	 * @param  functionCallBlock      the function call block object to compute the preemption cost
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
	 * @see                           JCFunctionCallBlock
	 * @see                           JCPreemptionCostMatrix
	 * @see                           JCSectionBlock
	 */
	public void computeFunctionCallBlockSolution(JCBlock functionCallBlock, JCPreemptionCostMatrix pcmMatrix)
	{	
		JCCostSolution existingCostSolution;
		int existingCostValue;
		JCCostKey existingLeftCostKey;
		JCCostSolution existingLeftCostSolution;
		JCCostKey existingRightCostKey;
		JCCostSolution existingRightCostSolution;
		JCFunctionBlock functionBlock;
		String functionName;
        int maximumPreemptionCost;
		Iterator<JCCostSolution> leftPredecessorMapIterator;
		int leftPredecessorNPRValue;
	    JCBasicBlock leftPredecessorBasicBlock;
	    JCBasicBlock leftPredecessorQBasicBlock;
		JCSectionBlock leftPredecessorSection;
		int leftPredecessorSectionID;
		JCPreemptionPoints leftPreemptionPoints;
		Iterator<JCPreemptionPoints> leftPreemptionPointSolutionIterator;
	    JCBasicBlock leftSuccessorBasicBlock;
		int leftSuccessorNPRValue;
	    JCBasicBlock leftSuccessorQBasicBlock;
		JCSectionBlock leftSuccessorSection;
		int leftSuccessorSectionID;
		ArrayList<Integer> leftVisiblePreemptionPoints;
		JCCostKey minimumBlocksCostKey;
		JCCostSolution minimumBlocksCostSolution;
		JCPreemptionPoints minimumBlocksPreemptionPoints;
		int minimumBlocksCostValue;
        int preemptionCost;
        JCBasicBlock rightPredecessorBasicBlock;
		int rightPredecessorNPRValue;
	    JCBasicBlock rightPredecessorQBasicBlock;
		JCSectionBlock rightPredecessorSection;
		int rightPredecessorSectionID;
		JCPreemptionPoints rightPreemptionPoints;
		Iterator<JCPreemptionPoints> rightPreemptionPointSolutionIterator;
	    JCBasicBlock rightSuccessorBasicBlock;
		Iterator<JCCostSolution> rightSuccessorMapIterator;
		int rightSuccessorNPRValue;
	    JCBasicBlock rightSuccessorQBasicBlock;
		JCSectionBlock rightSuccessorSection;
		int rightSuccessorSectionID;
		ArrayList<Integer> rightVisiblePreemptionPoints;
		int combinedCostValue;
		JCCostSolution solution;
		int solutionCount;
		Iterator<JCCostSolution> solutionIterator;
		int solutionPreemptionCost;
		boolean validSolution;
		JCBasicBlock visiblePPBasicBlock;
		
		functionName = this.getFunctionCallName();
		functionBlock = JCFunctionBlock.findFunctionBlock(functionName);
		if ((functionBlock != null) && (functionCallBlock != null))
		{
			if (_debugInputPreemptionSolutionSizes == true)
			{
	            System.out.println("computeFunctionCallBlockSolution(): input preemption solution sizes:");
				displayPreemptionSolutionSizes(functionBlock, functionCallBlock);
			}
			
	        // Create new hash map objects representing the new aggregate solution containing the next basic block.
	        createCostSolution();
	        
	        leftPredecessorSectionID = functionCallBlock.getStartingSectionID();
	        leftPredecessorSection = JCSectionBlock.getSectionBlock(leftPredecessorSectionID);
	        leftPredecessorBasicBlock = leftPredecessorSection.getLeftMostBasicBlock();
	        rightPredecessorSectionID = functionBlock.getStartingSectionID();
	        rightPredecessorSection = JCSectionBlock.getSectionBlock(rightPredecessorSectionID);
	        rightPredecessorBasicBlock = rightPredecessorSection.getLeftMostBasicBlock();
	        leftSuccessorSectionID = functionCallBlock.getEndingSectionID();
	        leftSuccessorSection = JCSectionBlock.getSectionBlock(leftSuccessorSectionID);
	        leftSuccessorBasicBlock = leftSuccessorSection.getRightMostBasicBlock();
	        rightSuccessorSectionID = functionBlock.getEndingSectionID();
	        rightSuccessorSection = JCSectionBlock.getSectionBlock(rightSuccessorSectionID);
	        rightSuccessorBasicBlock = rightSuccessorSection.getRightMostBasicBlock();
			
	        leftPredecessorMapIterator = functionCallBlock.getSolutionMap().iterator();

	        if (_debugCostMapSizes == true)
	        {
	        	if (functionCallBlock.getSolutionMap().size() == 0)
	        	{
	                System.out.println("computeFunctionCallBlockSolution(): function call block cost map size " + functionCallBlock.getSolutionMap().size() + " sub-block id " + functionCallBlock.getSubBlockID() + " sub-block name " + functionCallBlock.getSubBlockName() + " function call name " + this.getFunctionCallName());
	        	}
	        }

	        if (_debugCostMapSizes == true)
	        {
	        	if (functionBlock.getSolutionMap().size() == 0)
	        	{
	                System.out.println("computeFunctionCallBlockSolution(): function block cost map size " + functionBlock.getSolutionMap().size() + " sub-block id " + functionBlock.getSubBlockID() + " sub-block name " + functionBlock.getSubBlockName() + " function name " + functionBlock.getFunctionName());
	        	}
	        }

	        // Process the left or predecessor solutions.
			while (leftPredecessorMapIterator.hasNext() == true)
			{
				existingLeftCostSolution = leftPredecessorMapIterator.next();
				existingLeftCostKey = existingLeftCostSolution.getSolutionKey();
				leftPredecessorQBasicBlock = JCBasicBlock.getBasicBlock(existingLeftCostKey.getLeftBasicBlock());
		        leftPredecessorNPRValue = existingLeftCostKey.getLeftIndex();
	            leftSuccessorQBasicBlock = JCBasicBlock.getBasicBlock(existingLeftCostKey.getRightBasicBlock());
	            leftSuccessorNPRValue = existingLeftCostKey.getRightIndex();
				
	        	if (_debugNextBasicBlockPair == true)
	            {
	            	System.out.println("    JCFunctionCallBlock: Function Call Block " + this.getBlockName() + " processing next left predecessor basic block " + leftPredecessorQBasicBlock.getBBlockName() + " next left successor basic block " + leftSuccessorQBasicBlock.getBBlockName());
	            }

		        rightSuccessorMapIterator = functionBlock.getSolutionMap().iterator();

		        // Process the right or successor solutions.
				while (rightSuccessorMapIterator.hasNext() == true)
				{
					existingRightCostSolution = rightSuccessorMapIterator.next();
					existingRightCostKey = existingRightCostSolution.getSolutionKey();
					rightSuccessorQBasicBlock = JCBasicBlock.getBasicBlock(existingRightCostKey.getRightBasicBlock());
					rightSuccessorNPRValue = existingRightCostKey.getRightIndex();
	      	        rightPredecessorQBasicBlock = JCBasicBlock.getBasicBlock(existingRightCostKey.getLeftBasicBlock());
	      	        rightPredecessorNPRValue = existingRightCostKey.getLeftIndex();
					
					// Compute the minimum cost value for the current conditional left predecessor and right successor cost key values.
					minimumBlocksCostValue = Integer.MAX_VALUE;
					minimumBlocksCostKey = null;
					minimumBlocksCostSolution = null;
					minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
					
	            	if (_debugNextBasicBlockPair == true)
	                {
	                	System.out.println("    JCFunctionCallBlock: Function Call Block " + this.getBlockName() + " processing next right predecessor basic block " + rightPredecessorQBasicBlock.getBBlockName() + " next right successor basic block " + rightSuccessorQBasicBlock.getBBlockName());
	                }
	                
	            	if (_debugSearchOldSolutionCheck == true)
	                {
	                	System.out.println("    JCFunctionCallBlock: **Searching old left solution for (" + existingLeftCostKey.getLeftIndex() + ","  + (existingLeftCostKey.getRightIndex()) + ","  + (existingLeftCostKey.getLeftBasicBlock()) + ","  + (existingLeftCostKey.getRightBasicBlock()) + ")**");
	                }

	                if (existingLeftCostSolution != null)
	                {
	            		// Process the previous preemption based solutions generated.
	                    if (_debugOldSolutionCheck == true)
	                    {
	                    	System.out.println("    JCFunctionCallBlock: **Processing old left solution for (" + existingLeftCostKey.getLeftIndex() + ","  + (existingLeftCostKey.getRightIndex())  + ","  + (existingLeftCostKey.getLeftBasicBlock()) + ","  + (existingLeftCostKey.getRightBasicBlock()) + ")**");
	                    }
	                    
		                if (_debugCostMapSolution == true)
		                {
		                	System.out.println("    JCFunctionCallBlock: left costmap (" + leftPredecessorNPRValue + "," + leftSuccessorNPRValue + ") = " + existingLeftCostSolution.getSolutionCost() + " number of preemption point solutions = " + existingLeftCostSolution.numberOfPreemptionPointSolutions());
		                }

		                leftPreemptionPointSolutionIterator = existingLeftCostSolution.getPreemptionPointSolutionsIterator();
		                while (leftPreemptionPointSolutionIterator.hasNext() == true)
		                {
		                	leftPreemptionPoints = leftPreemptionPointSolutionIterator.next();
	    	                if (_debugSolutionPreemptionPoints == true)
	    	                {
	        	                System.out.print("    JCFunctionCallBlock: Existing left costmap solution preemption points: ");
	        	                leftPreemptionPoints.displayObjectInformation();
	    		    	    	System.out.println();
	    	                }
			    	    	
	    	                leftVisiblePreemptionPoints = existingLeftCostSolution.getVisibleEndingPPs(functionCallBlock, leftPreemptionPoints);
	    	                
	    	                if (_debugVisiblePreemptionPoints == true)
	    	                {
	        	                System.out.print("    JCFunctionCallBlock: Left visible ending preemption points (size=" + leftVisiblePreemptionPoints.size() + "): ");
	        	                if (leftVisiblePreemptionPoints.size() > 0)
	        	                {
	        		    			for (Integer item : leftVisiblePreemptionPoints) 
	        		    			{
	          	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
	          	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
	        		    			}
	        	                }
	        	                System.out.println();
		    	            }
		    	                
	                    	if (_debugSearchOldSolutionCheck == true)
	                        {
	                        	System.out.println("    JCFunctionCallBlock: **Searching old right solution for (" + existingRightCostKey.getLeftIndex() + ","  + (existingRightCostKey.getRightIndex()) + ","  + (existingRightCostKey.getLeftBasicBlock()) + ","  + (existingRightCostKey.getRightBasicBlock()) + ")**");
	                        }

	                        if (existingRightCostSolution != null)
	                        {
	                    		// Process the previous preemption based solutions generated.
	                            if (_debugOldSolutionCheck == true)
	                            {
	                            	System.out.println("    JCFunctionCallBlock: **Processing old right solution for (" + existingRightCostKey.getLeftIndex() + ","  + (existingRightCostKey.getRightIndex())  + ","  + (existingRightCostKey.getLeftBasicBlock()) + ","  + (existingRightCostKey.getRightBasicBlock()) + ")**");
	                            }
	                            
	        	                if (_debugCostMapSolution == true)
	        	                {
	        	                	System.out.println("    JCFunctionCallBlock: right costmap (" + rightPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + existingRightCostSolution.getSolutionCost() + " number of preemption point solutions = " + existingRightCostSolution.numberOfPreemptionPointSolutions());
	        	                }
	                		    
	        	                rightPreemptionPointSolutionIterator = existingRightCostSolution.getPreemptionPointSolutionsIterator();
	        	                while (rightPreemptionPointSolutionIterator.hasNext() == true)
	        	                {
	        	                	rightPreemptionPoints = rightPreemptionPointSolutionIterator.next();
	            	                if (_debugSolutionPreemptionPoints == true)
	            	                {
	                	                System.out.print("    JCFunctionCallBlock: Existing right costmap solution preemption points: ");
	                	                rightPreemptionPoints.displayObjectInformation();
	            		    	    	System.out.println();
	            	                }
	        		    	    	
	            	                rightVisiblePreemptionPoints = existingRightCostSolution.getVisibleStartingPPs(functionBlock, rightPreemptionPoints);
	            	                
	      	    	                if (_debugVisiblePreemptionPoints == true)
	      	    	                {
	                	                System.out.print("    JCFunctionCallBlock: Right visible starting preemption points (size=" + rightVisiblePreemptionPoints.size() + "): ");
	                	                if (rightVisiblePreemptionPoints.size() > 0)
	                	                {
	                		    			for (Integer item : rightVisiblePreemptionPoints) 
	                		    			{
	                  	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
	                  	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
	                		    			}
	                	                }
	                	                System.out.println();
	      	    	                }
	               	                
	            	                maximumPreemptionCost = 0;
	            	                if ((leftVisiblePreemptionPoints.size() > 0) && (rightVisiblePreemptionPoints.size() > 0))
	            	                {
	            		    			for (Integer leftItem : leftVisiblePreemptionPoints) 
	            		    			{
	                		    			for (Integer rightItem : rightVisiblePreemptionPoints) 
	                		    			{
	                		            		preemptionCost = pcmMatrix.getMatrixEntry(leftItem.intValue(), 
	                		            				                                  rightItem.intValue());
	                  	                        preemptionCost = (preemptionCost < 0) ? 0 : preemptionCost;
	                  	                        
	                  	                        if (maximumPreemptionCost < preemptionCost)
	                  	                        {
	                  	                        	maximumPreemptionCost = preemptionCost;
	                  	                        }
	                		    			}
	            		    			}
	            	    			}
	                               	
	            	                if (_debugMaximumPreemptionCost == true)
	            	                {
	                	                System.out.println("    JCFunctionCallBlock: Maximum preemption cost = " + maximumPreemptionCost);
	            	                }
	            	                
	              	                if (_debugNPRRegionCheck == true)
	            	                {
	                	                System.out.println("    JCFunctionCallBlock: Q = " + JCBlock.getQValue() + " Left NPR cost (" + existingLeftCostKey.getRightIndex() + "," + maximumPreemptionCost + "," + existingRightCostKey.getLeftIndex() + ") = " + (existingLeftCostKey.getRightIndex() + maximumPreemptionCost + existingRightCostKey.getLeftIndex()));
	            	                }
	              	                
	                                // Check to ensure that maximum non-preemptive region constraint is satisfied.
	              	                validSolution = ((existingLeftCostKey.getRightIndex() + maximumPreemptionCost + existingRightCostKey.getLeftIndex()) <= JCBlock.getQValue());
	                                
	              	                solutionPreemptionCost = maximumPreemptionCost;
	                              	                                                                
	                              	if (validSolution == true)
	                                {
	                         	    	combinedCostValue = existingLeftCostSolution.getSolutionCost() + 
	                         	    	                    solutionPreemptionCost +
	                         	    	 		            existingRightCostSolution.getSolutionCost();
	                         	            			
	                  	                if (_debugBlockCost == true)
	                	                {
	                                    	System.out.println("    JCFunctionCallBlock: combinedCost = " + combinedCostValue);
	                	                }
	                  	                
	                  	                if (_debugBlockPreemptionCost == true)
	                	                {
	                                    	System.out.println("    JCFunctionCallBlock: preemptionCost = " + solutionPreemptionCost);
	                	                }
	                                	                
	                	                if (_debugSolutionCombinedCostKeys == true)
	                	                {
	                    	                System.out.println("    JCFunctionCallBlock: Q = " + JCBlock.getQValue() + " Left cost key (" + existingLeftCostKey.getLeftIndex() + "," + existingLeftCostKey.getRightIndex() + "," + existingLeftCostKey.getLeftBasicBlock() + "," + existingLeftCostKey.getRightBasicBlock() + ")" +
	                                                           " Right cost key (" + existingRightCostKey.getLeftIndex() + "," + existingRightCostKey.getRightIndex() + "," + existingRightCostKey.getLeftBasicBlock() + "," + existingRightCostKey.getRightBasicBlock()+ ")");
	                	                }
	                	                
	                	                minimumBlocksCostValue = combinedCostValue;
	                            		minimumBlocksCostKey = new JCCostKey(existingLeftCostKey.getLeftIndex(), existingRightCostKey.getRightIndex());
	                            		minimumBlocksCostKey.setLeftBasicBlock(leftPredecessorQBasicBlock.getBBlockID());
	                            		minimumBlocksCostKey.setRightBasicBlock(rightSuccessorQBasicBlock.getBBlockID());

	            	                    existingCostSolution = getCostSolution(minimumBlocksCostKey);
	            	                    if (existingCostSolution != null)
	            	                    {
	            	                    	existingCostValue = existingCostSolution.getSolutionCost();
	            	                    	if (existingCostValue > minimumBlocksCostValue)
	            	                    	{
	            	                    		existingCostSolution.setSolutionCost(minimumBlocksCostValue);
	            	                    		existingCostSolution.clearPreemptionPointSolutions();

	            	                    		minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
	                            			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(leftPreemptionPoints);
	                            			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(rightPreemptionPoints);

	                            			  	if (existingCostSolution.hasVisiblePreemptionPointSolution(functionCallBlock, functionBlock, minimumBlocksPreemptionPoints) == false)
	                            			  	{
		                            			  	existingCostSolution.addPreemptionPointSolution(minimumBlocksPreemptionPoints);

		                            			  	if (_debugAddMinimumCostSolution == true)
		            	        			        {
		            	        			        	System.out.print("    JCFunctionCallBlock: adding minimum blocks cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumBlocksCostValue + " preemption points size " + existingCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
		            	        			        	minimumBlocksPreemptionPoints.displayObjectInformation();
		            	        			        	System.out.println();
		            	        			        }
	                            			  	}
	            	                    	}
	            	                    	else if (existingCostValue == minimumBlocksCostValue)
	            	                    	{
	            	        					if (_debugSupportMultiplePreemptionPoints == true)
	            	        					{
	                                			  	minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
	                                			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(leftPreemptionPoints);
	                                			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(rightPreemptionPoints);

		                            			  	if (existingCostSolution.hasVisiblePreemptionPointSolution(functionCallBlock, functionBlock, minimumBlocksPreemptionPoints) == false)
		                            			  	{
		                                			  	existingCostSolution.addPreemptionPointSolution(minimumBlocksPreemptionPoints);

		                	        					if (_debugAddMinimumCostSolution == true)
		                	        			        {
		                	        			        	System.out.print("    JCFunctionCallBlock: updating minimum blocks cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumBlocksCostValue + " preemption points ");
		                	        			        	minimumBlocksPreemptionPoints.displayObjectInformation();
		                	        			        	System.out.println();
		                	        			        }
		                            			  	}
	            	                    	    }
	            	                    	}
	            	                    }
	            	                    else
	            	                    {
	                                		minimumBlocksCostSolution = new JCCostSolution();
	                                		minimumBlocksCostSolution.setSolutionCost(combinedCostValue);
	                                		minimumBlocksCostSolution.setSolutionKey(minimumBlocksCostKey);
	                        			  	minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
	                        			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(leftPreemptionPoints);
	                        			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(rightPreemptionPoints);

	                        			  	minimumBlocksCostSolution.addPreemptionPointSolution(minimumBlocksPreemptionPoints);

	                        			  	putCostSolution(minimumBlocksCostSolution, true);

	                    					if (_debugAddMinimumCostSolution == true)
	                    			        {
	                    			        	System.out.print("    JCFunctionCallBlock: adding minimum blocks cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumBlocksCostValue + " preemption points ");
	                    			        	minimumBlocksPreemptionPoints.displayObjectInformation();
	                    			        	System.out.println();
	                    			        }
	            	                    }
	                    			}
	                            }
	                        }
	                	}
	                }
	            }
	        }
	                
			
			// Display the computed block solutions if necessary.
			if (_debugDisplayBlockSolutions == true)
			{
				displayBlockSolution();
			}
			else
			{
		    	if (_debugDisplayPreemptionFunctionCallBlockNumberOfSolutions == true)
		    	{
	                System.out.println("    JCFunctionCallBlock: Number of minimum cost solutions for function call block " + getBlockName() + " is " + getSolutionMap().size());
		    	}
		    	if (_debugDisplayPreemptionFunctionCallBlockSolutions == true)
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
			functionCallBlock.deleteCostSolution();
		}
	}
	
    /**
	 * Computes the potential preemption point solutions for the specified function
	 * call block object. 
	 *
	 * @param  functionCallBlock      the function call block object to compute the preemption cost
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
	 * @see                           JCFunctionCallBlock
	 * @see                           JCPreemptionCostMatrix
	 * @see                           JCSectionBlock
	 */
	public void computeFunctionCallBlockSolutionOld(JCBlock functionCallBlock, JCPreemptionCostMatrix pcmMatrix)
	{	
		JCCostSolution existingCostSolution;
		int existingCostValue;
		JCCostKey existingFunctionCallCostKey;
		JCCostSolution existingFunctionCallCostSolution;
		Iterator<JCCostSolution> functionCallBlockMapIterator;
	    int functionCallSolutionCostValue;
		int functionCallPredecessorNPRValue;
	    JCBasicBlock functionCallPredecessorQBasicBlock;
		JCPreemptionPoints functionCallPreemptionPoints;
		Iterator<JCPreemptionPoints> functionCallPreemptionPointSolutionIterator;
		int functionCallSuccessorNPRValue;
	    JCBasicBlock functionCallSuccessorQBasicBlock;
		JCCostKey minimumFunctionCallBlocksCostKey;
		JCCostSolution minimumFunctionCallBlocksCostSolution;
		JCPreemptionPoints minimumFunctionCallBlocksPreemptionPoints;
		int minimumFunctionCallBlocksCostValue;
		JCCostSolution solution;
		int solutionCount;
		Iterator<JCCostSolution> solutionIterator;
		boolean validSolution;
		
        // Create new hash map objects representing the new aggregate solution containing the next basic block.
        createCostSolution();
        
        functionCallBlockMapIterator = functionCallBlock.getSolutionMap().iterator();

        // Process the left or predecessor solutions.
		while (functionCallBlockMapIterator.hasNext() == true)
		{
			existingFunctionCallCostSolution = functionCallBlockMapIterator.next();
			existingFunctionCallCostKey = existingFunctionCallCostSolution.getSolutionKey();
			functionCallPredecessorQBasicBlock = JCBasicBlock.getBasicBlock(existingFunctionCallCostKey.getLeftBasicBlock());
	        functionCallPredecessorNPRValue = existingFunctionCallCostKey.getLeftIndex();
            functionCallSuccessorQBasicBlock = JCBasicBlock.getBasicBlock(existingFunctionCallCostKey.getRightBasicBlock());
            functionCallSuccessorNPRValue = existingFunctionCallCostKey.getRightIndex();
			
        	if (_debugNextBasicBlockPair == true)
            {
            	System.out.println("    JCFunctionCallBlock: Block " + this.getBlockName() + " processing next left predecessor basic block " + functionCallPredecessorQBasicBlock.getBBlockName() + " next left successor basic block " + functionCallSuccessorQBasicBlock.getBBlockName());
            }

            if (existingFunctionCallCostSolution != null)
            {
        		// Process the previous preemption based solutions generated.
                if (_debugOldSolutionCheck == true)
                {
                	System.out.println("    JCFunctionCallBlock: **Processing old function call solution for (" + existingFunctionCallCostKey.getLeftIndex() + ","  + (existingFunctionCallCostKey.getRightIndex())  + ","  + (existingFunctionCallCostKey.getLeftBasicBlock()) + ","  + (existingFunctionCallCostKey.getRightBasicBlock()) + ")**");
                }
                
                if (_debugCostMapSolution == true)
                {
                	System.out.println("    JCFunctionCallBlock: function call costmap (" + functionCallPredecessorNPRValue + "," + functionCallSuccessorNPRValue + ") = " + existingFunctionCallCostSolution.getSolutionCost() + " number of preemption point solutions = " + existingFunctionCallCostSolution.numberOfPreemptionPointSolutions());
                }

                functionCallPreemptionPointSolutionIterator = existingFunctionCallCostSolution.getPreemptionPointSolutionsIterator();
                while (functionCallPreemptionPointSolutionIterator.hasNext() == true)
                {
                	functionCallPreemptionPoints = functionCallPreemptionPointSolutionIterator.next();
    	            if (_debugSolutionPreemptionPoints == true)
    	            {
    	                System.out.print("    JCFunctionCallBlock: Existing function call costmap solution preemption points: ");
    	                functionCallPreemptionPoints.displayObjectInformation();
		    	    	System.out.println();
	                }
	    	    	
                	if (_debugSearchOldSolutionCheck == true)
                    {
                    	System.out.println("    JCFunctionCallBlock: **Searching old solution for (" + existingFunctionCallCostKey.getLeftIndex() + ","  + (existingFunctionCallCostKey.getRightIndex()) + ","  + (existingFunctionCallCostKey.getLeftBasicBlock()) + ","  + (existingFunctionCallCostKey.getRightBasicBlock()) + ")**");
                    }

    	            if (_debugNPRRegionCheck == true)
                    {
    	                System.out.println("    JCFunctionCallBlock: Q = " + JCBlock.getQValue() + " Function NPR cost (" + existingFunctionCallCostKey.getLeftIndex() + "," + existingFunctionCallCostKey.getRightIndex() + ") = " + (existingFunctionCallCostKey.getRightIndex() + existingFunctionCallCostKey.getLeftIndex()));
                    }
    	                
                    // Check to ensure that maximum non-preemptive region constraint is satisfied.
    	            validSolution = true;
                    
                  	if (validSolution == true)
                    {
    	                if (_debugSolutionCombinedCostKeys == true)
    	                {
        	                System.out.println("    JCFunctionCallBlock: Q = " + JCBlock.getQValue() + " Function cost key (" + existingFunctionCallCostKey.getLeftIndex() + "," + existingFunctionCallCostKey.getRightIndex() + "," + existingFunctionCallCostKey.getLeftBasicBlock() + "," + existingFunctionCallCostKey.getRightBasicBlock() + ")");
    	                }
    	                
    	                minimumFunctionCallBlocksCostValue = existingFunctionCallCostSolution.getSolutionCost();
    	                functionCallSolutionCostValue = minimumFunctionCallBlocksCostValue;
                		minimumFunctionCallBlocksCostKey = new JCCostKey(existingFunctionCallCostKey.getLeftIndex(), existingFunctionCallCostKey.getRightIndex());
                		minimumFunctionCallBlocksCostKey.setLeftBasicBlock(functionCallPredecessorQBasicBlock.getBBlockID());
                		minimumFunctionCallBlocksCostKey.setRightBasicBlock(functionCallSuccessorQBasicBlock.getBBlockID());

                        existingCostSolution = getCostSolution(minimumFunctionCallBlocksCostKey);
                        if (existingCostSolution != null)
                        {
                        	existingCostValue = existingCostSolution.getSolutionCost();
                        	if (existingCostValue > functionCallSolutionCostValue)
                        	{
                        		existingCostSolution.setSolutionCost(functionCallSolutionCostValue);
                        		existingCostSolution.clearPreemptionPointSolutions();

                        		minimumFunctionCallBlocksPreemptionPoints = new JCPreemptionPoints(false);
                			  	minimumFunctionCallBlocksPreemptionPoints.combinePreemptionPoints(functionCallPreemptionPoints);

                			  	existingCostSolution.addPreemptionPointSolution(minimumFunctionCallBlocksPreemptionPoints);

                			  	if (_debugAddMinimumCostSolution == true)
            			        {
            			        	System.out.print("    JCFunctionCallBlock: adding minimum blocks cost (" + functionCallPredecessorNPRValue + "," + functionCallSuccessorNPRValue + ") = " + functionCallSolutionCostValue + " preemption points size " + existingCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
            			        	minimumFunctionCallBlocksPreemptionPoints.displayObjectInformation();
            			        	System.out.println();
            			        }
                        	}
                        	else if (existingCostValue == functionCallSolutionCostValue)
                        	{
            					if (_debugSupportMultiplePreemptionPoints == true)
            					{
                    			  	minimumFunctionCallBlocksPreemptionPoints = new JCPreemptionPoints(false);
                    			  	minimumFunctionCallBlocksPreemptionPoints.combinePreemptionPoints(functionCallPreemptionPoints);

                    			  	if (existingCostSolution.hasVisiblePreemptionPointSolution(functionCallBlock, functionCallBlock, minimumFunctionCallBlocksPreemptionPoints) == false)
                    			  	{
                        			  	existingCostSolution.addPreemptionPointSolution(minimumFunctionCallBlocksPreemptionPoints);

        	        					if (_debugAddMinimumCostSolution == true)
        	        			        {
        	        			        	System.out.print("    JCFunctionCallBlock: updating minimum blocks cost (" + functionCallPredecessorNPRValue + "," + functionCallSuccessorNPRValue + ") = " + functionCallSolutionCostValue + " preemption points ");
        	        			        	minimumFunctionCallBlocksPreemptionPoints.displayObjectInformation();
        	        			        	System.out.println();
        	        			        }
                    			  	}
                        	    }
                        	}
                        }
                        else
                        {
                    		minimumFunctionCallBlocksCostSolution = new JCCostSolution();
                    		functionCallSolutionCostValue = existingFunctionCallCostSolution.getSolutionCost();
                    		minimumFunctionCallBlocksCostSolution.setSolutionCost(functionCallSolutionCostValue);
                    		minimumFunctionCallBlocksCostSolution.setSolutionKey(minimumFunctionCallBlocksCostKey);
            			  	minimumFunctionCallBlocksPreemptionPoints = new JCPreemptionPoints(false);
            			  	minimumFunctionCallBlocksPreemptionPoints.combinePreemptionPoints(functionCallPreemptionPoints);

            			  	minimumFunctionCallBlocksCostSolution.addPreemptionPointSolution(minimumFunctionCallBlocksPreemptionPoints);

            			  	putCostSolution(minimumFunctionCallBlocksCostSolution, true);

        					if (_debugAddMinimumCostSolution == true)
        			        {
        			        	System.out.print("    JCFunctionCallBlock: adding minimum blocks cost (" + functionCallPredecessorNPRValue + "," + functionCallSuccessorNPRValue + ") = " + functionCallSolutionCostValue + " preemption points ");
        			        	minimumFunctionCallBlocksPreemptionPoints.displayObjectInformation();
        			        	System.out.println();
        			        }
                        }
        			}
                }
            }
    	}
    		
		// Display the computed block solutions if necessary.
		if (_debugDisplayBlockSolutions == true)
		{
			displayBlockSolution();
		}
		else
		{
	    	if (_debugDisplayPreemptionFunctionCallBlockNumberOfSolutions == true)
	    	{
		    	System.out.println("    JCFunctionCallBlock: Number of minimum cost solutions for function call block " + getFunctionCallBlockName() + " is " + getSolutionMap().size());
	    	}
	    	if (_debugDisplayPreemptionFunctionCallBlockSolutions == true)
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
        functionCallBlock.deleteCostSolution();
	}
	
	/**
	 * Returns the string function call block object type name. 
	 *
	 * @return ID     the string type name of the function call block object
	 * 
	 * @see           JCFunctionCallBlock
	 */
	@Override
	public String getObjectTypeName()
	{
		return "JCFunctionCallBlock";
	}
    
	/**
	 * Displays information about this function call block object. 
	 *
	 * @see           JCFunctionCallBlock
	 */
    @Override
    public void displayObjectInformation()
    {
       	System.out.println("Function Call Block ID " + getFunctionCallBlockID() + " Name " + getFunctionCallBlockName() + " block ID " + getBlockID() + " block name " + getBlockName() + " sub-block ID " + getSubBlockID() + " sub-block name " + getSubBlockName());

    	super.displayObjectInformation();   	
    }

	/**
	 * Displays information about all function call block objects. 
	 *
	 * @see           JCFunctionCallBlock
	 */
    public static void displayAllObjects()
    {
	    int                  functionCallBlockID;
	    JCFunctionCallBlock  currentFunctionCallBlock;
		
    	System.out.println("The number of function call blocks is " + _functionCallBlocks.size());
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("....................................................................................................................................");
		for (functionCallBlockID = 0; functionCallBlockID < _functionCallBlocks.size(); functionCallBlockID++)
		{
			currentFunctionCallBlock = JCFunctionCallBlock.getFunctionCallBlock(functionCallBlockID);
			currentFunctionCallBlock.displayObjectInformation();
	    	System.out.println("....................................................................................................................................");
		}
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}
