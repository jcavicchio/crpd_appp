import java.util.ArrayList;
import java.util.Iterator;
/**
 * JCPreemptionCostMatrix is a fundamental class whose purpose is to represent a 
 * P cost matrix for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCPreemptionCostMatrix extends JCObject 
{
	private final int P_COST_MATRIX_UNUSED_ENTRY = -1;
    private final int P_COST_MATRIX_LARGE_VALUE = Integer.MAX_VALUE;

	private static boolean _debugBasicBlocks = false;

    private static int _bbCount = 0;                         // Basic block count
	
    private static int _nextID = 0;

    private static ArrayList<JCPreemptionCostMatrix> _preemptionCostMatrices = 
	          new ArrayList<JCPreemptionCostMatrix>();       // Preemption cost matrix for a task

	private int _pcMatrixID;
	private Integer[][] _preemption_cost_matrix;
	private ArrayList<Integer> _extended_matrix_basic_blocks;
    
	/**
	 * Default constructor.
	 */
    JCPreemptionCostMatrix()
    {
		int columnIndex;
		int rowIndex;

		_pcMatrixID = _nextID;
		_nextID++;
		if (_bbCount > 0)
		{
	    	_preemption_cost_matrix = new Integer[_bbCount][_bbCount];
			for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
			{
				for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
				{
					_preemption_cost_matrix[rowIndex][columnIndex] = P_COST_MATRIX_UNUSED_ENTRY;
				}
			}
		}
		else
		{
			_preemption_cost_matrix = null;
		}
		
		_extended_matrix_basic_blocks = null;
    }
    
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCPreemptionCostMatrix
	 */
	public static void reset()
	{
		_nextID = 0;
		_bbCount = 0;
		_preemptionCostMatrices = new ArrayList<JCPreemptionCostMatrix>();
	}
	
	/**
	 * Returns the numeric identifier of the preemption cost matrix object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _pcMatrixID   the identifier of this preemption cost matrix object
	 * 
	 * @see                  JCPreemptionCostMatrix
	 */
	public int getPCMatrixID()
	{
		return _pcMatrixID;
	}

	/**
	 * Gets the size or number of the basic block objects that comprise 
	 * the current control flow graph. 
	 *
	 * @return n    the number of basic block objects in the CFG
	 * 
	 * @see         JCPreemptionCostMatrix
	 */
	public static int getNValue()
	{
		return _bbCount;
	}
	    
	/**
	 * Sets the size or number of the basic block objects that comprise 
	 * the current control flow graph. 
	 *
	 * @param  n    the number of basic block objects in the CFG
	 * 
	 * @see         JCPreemptionCostMatrix
	 */
	public static void setNValue(int n)
	{
		_bbCount = n;
	}
	    
	/**
	 * Stores the preemption cost matrix object at the specified location in
	 * the preemption cost matrix array. 
	 *
	 * @param  pcMatrixID    the identifier of the stored preemption cost matrix object
	 * 
	 * @param  pcMatrix      the preemption cost matrix object to be stored in the graph
	 * 
	 * @see                  JCPreemptionCostMatrix
	 */
	public static void setPreemptionCostMatrix(int pcMatrixID, JCPreemptionCostMatrix pcMatrix)
	{
		if (pcMatrixID < _bbCount)
		{
	        _preemptionCostMatrices.add(pcMatrix);
		}
	}
	
	/**
	 * Returns the preemption cost matrix object associated with the specified identifier. 
	 *
	 * @return preemptionCostMatrix  the preemption cost matrix object stored in the array
	 * 
	 * @see                          JCPreemptionCostMatrix
	 */
	public static JCPreemptionCostMatrix getPreemptionCostMatrix(int pcMatrixID)
	{
		if (pcMatrixID < _bbCount)
		{
	        return _preemptionCostMatrices.get(pcMatrixID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Sets the value of the specified cell in the preemption cost matrix. 
	 *
	 * @param  rowIndex      the row index of the stored value in the preemption cost matrix
	 * 
	 * @param  columnIndex   the column index of the stored value in the preemption cost matrix
	 * 
	 * @param  costValue     the cost value to store in the preemption cost matrix
	 * 
	 * @see                  JCPreemptionCostMatrix
	 */
     public void setMatrixEntry(int rowIndex, int columnIndex, int costValue) 
     {
    	 Integer matrixEntry;
    	 int mColumnIndex;
    	 int mRowIndex;
    	 
    	 if (_extended_matrix_basic_blocks == null)
    	 {
    		 if ((rowIndex >= 0) && (rowIndex < _bbCount) &&
        		 (columnIndex >= 0) && (columnIndex < _bbCount))
        	 {
                 _preemption_cost_matrix[rowIndex][columnIndex] = costValue;
        	 }
    	 }
    	 else
    	 {
    		 if ((rowIndex >= 0) && (rowIndex < _extended_matrix_basic_blocks.size()) &&
    			 (columnIndex >= 0) && (columnIndex < _extended_matrix_basic_blocks.size()))
    		 {
    			 matrixEntry = _extended_matrix_basic_blocks.get(rowIndex);
    			 mRowIndex = matrixEntry.intValue();
    			 matrixEntry = _extended_matrix_basic_blocks.get(columnIndex);
    			 mColumnIndex = matrixEntry.intValue();
                 _preemption_cost_matrix[mRowIndex][mColumnIndex] = costValue;
    		 }
    	 }
     }

 	/**
 	 * Returns the value of the specified cell in the preemption cost matrix. 
 	 *
 	 * @param  rowIndex      the row index of the stored value in the preemption cost matrix
 	 * 
 	 * @param  columnIndex   the column index of the stored value in the preemption cost matrix
 	 * 
 	 * @return costValue     the cost value stored in the preemption cost matrix
 	 * 
 	 * @see                  JCPreemptionCostMatrix
 	 */
      public int getMatrixEntry(int rowIndex, int columnIndex) 
      {
     	 Integer matrixEntry;
     	 int mColumnIndex;
     	 int mRowIndex;
     	 int matrixReturnValue = P_COST_MATRIX_UNUSED_ENTRY;
     	 
     	 if (_extended_matrix_basic_blocks == null)
     	 {
     		 if ((rowIndex >= 0) && (rowIndex < _bbCount) &&
         		 (columnIndex >= 0) && (columnIndex < _bbCount))
         	 {
     			matrixReturnValue = _preemption_cost_matrix[rowIndex][columnIndex];
         	 }
     	 }
    	 else
    	 {
    		 if ((rowIndex >= 0) && (rowIndex < _extended_matrix_basic_blocks.size()) &&
    			 (columnIndex >= 0) && (columnIndex < _extended_matrix_basic_blocks.size()))
    		 {
    			 matrixEntry = _extended_matrix_basic_blocks.get(rowIndex);
    			 mRowIndex = matrixEntry.intValue();
    			 matrixEntry = _extended_matrix_basic_blocks.get(columnIndex);
    			 mColumnIndex = matrixEntry.intValue();
    			 matrixReturnValue = _preemption_cost_matrix[mRowIndex][mColumnIndex];
    		 }
    	 }
     	 
         return matrixReturnValue;
      }
      	/**
      	 * Adds the basic block object to this preemption cost matrix object. 
      	 *
      	 * @param  basicBlock  the basic block object to add to this preemption 
      	 *                     cost matrix object
      	 *               
      	 * @see                JCBasicBlock
      	 * @see                JCPreemptionCostMatrix
      	 */
      	public void addBasicBlock(JCBasicBlock basicBlock)
      	{
            if (_extended_matrix_basic_blocks == null)
            {
        		_extended_matrix_basic_blocks = new ArrayList<Integer>();
            }
            
      		if (_extended_matrix_basic_blocks.add(basicBlock.getBBlockID()) != true)
      		{
      			System.out.println("JCNode: Error adding instruction " + basicBlock.getBBlockName());
      		}
      		else
      		{
      			if (_debugBasicBlocks == true)
      			{
      				System.out.println("Basic block (" + basicBlock.getBBlockID() + ") ID: " + basicBlock.getBBlockName() + " added to preemption cost matrix (" + this.getPCMatrixID() + ")");
      			}
      		}
      	}
  	

    	/**
    	 * Adds the basic block object to this preemption cost matrix object. 
    	 *
    	 * @param  basicBlock  the basic block object to add to this preemption 
    	 *                     cost matrix object
    	 *               
    	 * @see                JCBasicBlock
    	 * @see                JCPreemptionCostMatrix
    	 */
    	public void addBasicBlockID(Integer basicBlockID)
    	{
          if (_extended_matrix_basic_blocks == null)
          {
      		_extended_matrix_basic_blocks = new ArrayList<Integer>();
          }
          
    		if (_extended_matrix_basic_blocks.add(basicBlockID) != true)
    		{
    			System.out.println("JCNode: Error adding basic block " + basicBlockID);
    		}
    		else
    		{
    			if (_debugBasicBlocks == true)
    			{
    				System.out.println("Basic block (" + basicBlockID + ") added to preemption cost matrix (" + this.getPCMatrixID() + ")");
    			}
    		}
    	}

  	/**
  	 * Removes the basic block object from this preemption cost matrix object. 
  	 *
  	 * @param  basicBlock    the basic block object to remove from this preemption 
  	 *                       cost matrix object
  	 *               
  	 * @see                  JCBasicBlock
  	 * @see                  JCPreemptionCostMatrix
  	 */
  	public void removeBasicBlock(JCBasicBlock basicBlock)
  	{
  		if (_extended_matrix_basic_blocks != null)
  		{
  	  		if (_extended_matrix_basic_blocks.remove((Integer)basicBlock.getBBlockID()) != true)
  	  		{
  	  			System.out.println("JCPreemptionCostMatrix: Error removing instruction " + basicBlock.getBBlockName());
  	  		}
  		}
  	}
  	
  	/**
  	 * Determines if the specified basic block is contained in this preemption 
  	 * cost matrix object. 
  	 *
  	 * @param  basicBlock      the basic block object to remove from this preemption 
  	 *                         cost matrix object
  	 *                       
  	 * @return hasBasicBlock   true/false indicating is the specified basic block
  	 *                         object is in this preemption cost matrix object
  	 *                        
  	 * @see                    JCBasicBlock
  	 * @see                    JCPreemptionCostMatrix
  	 */
  	public boolean hasBasicBlock(JCBasicBlock basicBlock)
  	{
  		boolean hasBasicBlock = false;
  		
  		if (_extended_matrix_basic_blocks != null)
  		{
  			hasBasicBlock = _extended_matrix_basic_blocks.contains(basicBlock.getBBlockID());
  		}
  		
  		return hasBasicBlock;
  	}
  	
    /**
     * Returns the basic block object at the specified index in this 
     * preemption cost matrix object.
     *
     * @param  bbIndex       the index of the basic block object to return
     *               
     * @return basicBlock    the found basic block object
     *               
  	 * @see                    JCBasicBlock
  	 * @see                    JCPreemptionCostMatrix
     */
    public JCBasicBlock getInstructionAtIndex(int bbIndex)
    {
        JCBasicBlock basicBlock = null;
  	    int basicBlockID;
  	
  		if (_extended_matrix_basic_blocks != null)
  		{
  	        basicBlockID = _extended_matrix_basic_blocks.get(bbIndex);
  	        basicBlock = JCBasicBlock.getBasicBlock(basicBlockID);
  		}
  		
  	    return basicBlock;
    }

  	/**
  	 * Finds the instruction object from within this program object
  	 * with the specified node name. 
  	 *
  	 * @param  basicBlockName    the string name of the basic block object to search for
  	 *                           
  	 *               
     * @return basicBlock        the found basic block object
  	 *               
  	 * @see                      JCBasicBlock
  	 * @see                      JCPreemptionCostMatrix
  	 */
  	public JCBasicBlock findBasicBlock(String basicBlockName)
  	{
  		JCBasicBlock basicBlock = null;
  		JCBasicBlock currentBasicBlock;
  		Integer currentBasicBlockID;
  	    String currentBasicBlockName;
  	    Iterator<Integer> iterator;
  	    
  	    if (basicBlockName != null)
  	    {
  		    iterator = getInstructionIterator();
  		    if (iterator != null)
  		    {
  		        while ((true == iterator.hasNext()) && (basicBlockName == null))
  		        {
  		        	currentBasicBlockID = iterator.next();
  		        	currentBasicBlock = JCBasicBlock.getBasicBlock(currentBasicBlockID);
  		        	currentBasicBlockName = currentBasicBlock.getBBlockName();
  		        	if (currentBasicBlock != null)
  		        	{
  		        		if (currentBasicBlockName.compareTo(basicBlockName) == 0)
  		        		{
  		        			basicBlock = currentBasicBlock;
  		        		}
  		        	}
  		        }
  		    }
  	    }
  	    
  	    return basicBlock;
  	}
  	
  	/**
  	 * Returns the number of basic block objects in this preemption
  	 * cost matrix object. 
  	 *
  	 * @return numberOfBasicBlocks  the number of instructions contained in 
  	 *                              this preemption cost matrix object
  	 *                        
  	 * @see                         JCBasicBlock
  	 * @see                         JCPreemptionCostMatrix
  	 */
  	public long numberOfBasicBlocks()
  	{
  		long matrixSize = 0;
  		
  		if (_extended_matrix_basic_blocks != null)
  		{
  			matrixSize = _extended_matrix_basic_blocks.size();
  		}
  		
  		return matrixSize;
  	}
  	
  	/**
  	 * Returns the basic block iterator for accessing the basic block
  	 * objects in this preemption cost matrix object. 
  	 *
  	 * @return iterator  the basic block iterator for accessing the 
  	 *                   basic block objects
  	 *                        
  	 * @see              JCBasicBlock
  	 * @see              JCPreemptionCostMatrix
  	 */
  	public Iterator<Integer> getInstructionIterator()
  	{
  		Iterator<Integer> iterator = null;
  				
  		if (_extended_matrix_basic_blocks != null)
  		{
  			iterator = _extended_matrix_basic_blocks.iterator();
  		}
  		
  		return iterator;
  	}
  	
	/**
	 * Returns the string preemption cost matrix object type name. 
	 *
	 * @return ID     the string type name of the preemption cost matrix object
	 * 
	 * @see           JCPreemptionCostMatrix
	 */
	@Override
	public String getObjectTypeName() 
	{
    	return "JCPreemptionCostMatrix";
	}

	/**
	 * Displays information about this preemption cost matrix object. 
	 *
	 * @see           JCPreemptionCostMatrix
	 */
	@Override
	public void displayObjectInformation() 
	{
		int columnIndex;
		Iterator<Integer> iterator;
		int rowIndex;
		Integer matrixEntry;
		
		System.out.println("Preemption Cost Matrix:");
		System.out.println("{");
		for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
		{
			System.out.print("[");
			for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
			{
				if (columnIndex > 0)
				{
					System.out.print(",");
				}
				System.out.print(_preemption_cost_matrix[rowIndex][columnIndex]);
			}
			System.out.println("]");
		}
		System.out.println("}");
		
		if (_extended_matrix_basic_blocks != null)
		{
			System.out.println();
			System.out.print("Extended Preemption Cost Matrix Basic Blocks: ");
			iterator = _extended_matrix_basic_blocks.iterator();
			while (iterator.hasNext() == true)
			{
				matrixEntry = iterator.next();
				System.out.print(matrixEntry.toString());
			}
			System.out.println();
		}
	}
}
