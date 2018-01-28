import java.util.ArrayList;

/**
 * JCBCostMatrix is a fundamental class whose purpose is to represent a 
 * B cost matrix for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCBCostMatrix extends JCObject 
{	
	private final int B_COST_MATRIX_UNUSED_ENTRY = -1;
    public static int B_COST_MATRIX_LARGE_VALUE = Integer.MAX_VALUE;
    
    private static int _nextID = 0;

	private static int _bCount = 0;                        // Counter to count which matrix it is
    private static ArrayList<JCBCostMatrix> _bCostMatrices = 
	          new ArrayList<JCBCostMatrix>();              // Non-preemptive + preemptive cost matrix for a task

	private int _bMatrixID;
	private int _bbCount;                                  // Basic block count
	private Integer[][] _b_cost_matrix;
    
	/**
	 * Default constructor.
	 */
    JCBCostMatrix()
    {
		int columnIndex;
		int rowIndex;

    	_bMatrixID = _nextID;
    	_bbCount = 0;
		_nextID++;
		_bCount = _nextID;
		
		if (_bbCount > 0)
		{
	    	_b_cost_matrix = new Integer[_bbCount][_bbCount];
			for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
			{
				for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
				{
					_b_cost_matrix[rowIndex][columnIndex] = B_COST_MATRIX_UNUSED_ENTRY;
				}
			}
		}
		else
		{
			_b_cost_matrix = null;
		}
    }
    
	/**
	 * Constructor.
	 */
    JCBCostMatrix(int bbCount)
    {
		int columnIndex;
		int rowIndex;

    	_bMatrixID = _nextID;
    	_bbCount = bbCount;
		_nextID++;
		_bCount = _nextID;
		
		if (_bbCount > 0)
		{
	    	_b_cost_matrix = new Integer[_bbCount][_bbCount];
			for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
			{
				for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
				{
					_b_cost_matrix[rowIndex][columnIndex] = B_COST_MATRIX_UNUSED_ENTRY;
				}
			}
		}
		else
		{
			_b_cost_matrix = null;
		}
    }
    
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCBCostMatrix
	 */
	public static void reset()
	{
		_nextID = 0;
		_bCount = 0;
		_bCostMatrices = new ArrayList<JCBCostMatrix>();
	}
	
	/**
	 * Returns the numeric identifier of the b cost matrix object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _bMatrixID  the identifier of this b cost matrix object
	 * 
	 * @see                JCBCostMatrix
	 */
	public int getBMatrixID()
	{
		return _bMatrixID;
	}

	/**
	 * Sets the size or number of the basic block objects that comprise 
	 * the current control flow graph. 
	 *
	 * @param  n    the number of basic block objects in the CFG
	 * 
	 * @see         JCBCostMatrix
	 * @see         JCSectionBlock
	 */
	public void setNValue(int n)
	{
		_bbCount = n;
	}
	    
	/**
	 * Stores the b cost matrix object at the specified location in
	 * the b cost matrix array. 
	 *
	 * @param  bMatrixID    the identifier of the stored b cost matrix object
	 * 
	 * @param  bMatrix      the b cost matrix object to be stored in the graph
	 * 
	 * @see                 JCBCostMatrix
	 */
	public static void setBCostMatrix(int bMatrixID, JCBCostMatrix bMatrix)
	{
		if (bMatrixID < _bCount)
		{
	        _bCostMatrices.add(bMatrix);
		}
	}
	
	/**
	 * Returns the b cost matrix object associated with the specified identifier. 
	 *
	 * @return bCostMatrix  the b cost matrix object stored in the array
	 * 
	 * @see                 JCBCostMatrix
	 */
	public static JCBCostMatrix getBCostMatrix(int bMatrixID)
	{
		if (bMatrixID < _bCount)
		{
		    return _bCostMatrices.get(bMatrixID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Sets the value of the specified cell in the b cost matrix. 
	 *
	 * @param  rowIndex      the row index of the stored value in the b cost matrix
	 * 
	 * @param  columnIndex   the column index of the stored value in the b cost matrix
	 * 
	 * @param  costValue     the cost value to store in the b cost matrix
	 * 
	 * @see                  JCBCostMatrix
	 */
     public void setMatrixEntry(int rowIndex, int columnIndex, int costValue) 
     {
         _b_cost_matrix[rowIndex][columnIndex] = costValue;
     }

 	/**
 	 * Returns the value of the specified cell in the b cost matrix. 
 	 *
 	 * @param  rowIndex      the row index of the stored value in the b cost matrix
 	 * 
 	 * @param  columnIndex   the column index of the stored value in the b cost matrix
 	 * 
 	 * @return costValue     the cost value stored in the b cost matrix
 	 * 
 	 * @see                  JCBCostMatrix
 	 */
      public int getMatrixEntry(int rowIndex, int columnIndex) 
      {
          return _b_cost_matrix[rowIndex][columnIndex];
      }

	/**
	 * Returns the string b cost matrix object type name. 
	 *
	 * @return ID     the string type name of the b cost matrix object
	 * 
	 * @see           JCBCostMatrix
	 */
	@Override
	public String getObjectTypeName() 
	{
    	return "JCQCostMatrix";
	}

	/**
	 * Displays information about this b cost matrix object. 
	 *
	 * @see           JCBCostMatrix
	 */
	@Override
	public void displayObjectInformation() 
	{
		int columnIndex;
		int rowIndex;
		
		System.out.println("B Cost Matrix:");
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
				System.out.print(_b_cost_matrix[rowIndex][columnIndex]);
			}
			System.out.println("]");
		}
		System.out.println("}");
	}

	/**
	 * Displays information about all b cost matrix objects. 
	 *
	 * @see           JCBCostMatrix
	 */
    public static void displayAllObjects()
    {
	    int                  bMatrixID;
	    JCBCostMatrix        currentBCostMatrix;
		
		for (bMatrixID = 0; bMatrixID < _bCostMatrices.size(); bMatrixID++)
		{
			currentBCostMatrix = JCBCostMatrix.getBCostMatrix(bMatrixID);
			currentBCostMatrix.displayObjectInformation();
		}
    }
}
