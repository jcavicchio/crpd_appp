import java.util.ArrayList;

/**
 * JCCNPCostMatrix is a fundamental class whose purpose is to represent a 
 * CNP cost matrix for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCCNPCostMatrix extends JCObject 
{	
	private final int CNP_COST_MATRIX_UNUSED_ENTRY = -1;
	private final int CNP_COST_MATRIX_INITIAL_VALUE = 0;
    private final int CNP_COST_MATRIX_LARGE_VALUE = Integer.MAX_VALUE;
	
    private static int _nextID = 0;

	private static int _cnpCount = 0;                        // Counter to count which matrix it is
    private static ArrayList<JCCNPCostMatrix> _cnpCostMatrices = 
	          new ArrayList<JCCNPCostMatrix>();              // Non-preemptive cost matrix for a task

	private int _cnpMatrixID;
	private int _bbCount;                                    // Basic block count	
	private Integer[][] _cnp_cost_matrix;
    
	/**
	 * Default constructor.
	 */
    JCCNPCostMatrix()
    {
		int columnIndex;
		int rowIndex;

    	_cnpMatrixID = _nextID;
    	_bbCount = 0;
		_nextID++;
		_cnpCount = _nextID;
		
		if (_bbCount > 0)
		{
	    	_cnp_cost_matrix = new Integer[_bbCount][_bbCount];
			for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
			{
				for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
				{
					_cnp_cost_matrix[rowIndex][columnIndex] = CNP_COST_MATRIX_INITIAL_VALUE;
				}
			}
		}
		else
		{
			_cnp_cost_matrix = null;
		}
    }
    
	/**
	 * Constructor.
	 */
    JCCNPCostMatrix(int bbCount)
    {
		int columnIndex;
		int rowIndex;

    	_cnpMatrixID = _nextID;
    	_bbCount = bbCount;
		_nextID++;
		_cnpCount = _nextID;

		if (_bbCount > 0)
		{
	    	_cnp_cost_matrix = new Integer[_bbCount][_bbCount];
			for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
			{
				for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
				{
					_cnp_cost_matrix[rowIndex][columnIndex] = CNP_COST_MATRIX_INITIAL_VALUE;
				}
			}
		}
		else
		{
			_cnp_cost_matrix = null;
		}
    }
    
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCCNPCostMatrix
	 */
	public static void reset()
	{
		_nextID = 0;
		_cnpCount = 0;
		_cnpCostMatrices = new ArrayList<JCCNPCostMatrix>();
	}
	
	/**
	 * Returns the numeric identifier of the cnp cost matrix object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _cnpMatrixID  the identifier of this cnp cost matrix object
	 * 
	 * @see                  JCCNPCostMatrix
	 */
	public int getCNPMatrixID()
	{
		return _cnpMatrixID;
	}

	/**
	 * Sets the size or number of the basic block objects that comprise 
	 * the current control flow graph. 
	 *
	 * @param  n    the number of basic block objects in the CFG
	 * 
	 * @see         JCCNPCostMatrix
	 * @see         JCSectionBlock
	 */
	public void setNValue(int n)
	{
		_bbCount = n;
	}
	    
	/**
	 * Stores the cnp cost matrix object at the specified location in
	 * the cnp cost matrix array. 
	 *
	 * @param  cnpMatrixID    the identifier of the stored cnp cost matrix object
	 * 
	 * @param  cnpMatrix      the cnp cost matrix object to be stored in the graph
	 * 
	 * @see                   JCCNPCostMatrix
	 */
	public static void setCNPCostMatrix(int cnpMatrixID, JCCNPCostMatrix cnpMatrix)
	{
		if (cnpMatrixID < _cnpCount)
		{
	        _cnpCostMatrices.add(cnpMatrix);
		}
	}
	
	/**
	 * Returns the cnp cost matrix object associated with the specified identifier. 
	 *
	 * @return cnpCostMatrix  the cnp cost matrix object stored in the array
	 * 
	 * @see                   JCCNPCostMatrix
	 */
	public static JCCNPCostMatrix getCNPCostMatrix(int cnpMatrixID)
	{
		if (cnpMatrixID < _cnpCount)
		{
		    return _cnpCostMatrices.get(cnpMatrixID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Sets the value of the specified cell in the cnp cost matrix. 
	 *
	 * @param  rowIndex      the row index of the stored value in the cnp cost matrix
	 * 
	 * @param  columnIndex   the column index of the stored value in the cnp cost matrix
	 * 
	 * @param  costValue     the cost value to store in the cnp cost matrix
	 * 
	 * @see                  JCCNPCostMatrix
	 */
     public void setMatrixEntry(int rowIndex, int columnIndex, int costValue) 
     {
         _cnp_cost_matrix[rowIndex][columnIndex] = costValue;
     }

 	/**
 	 * Returns the value of the specified cell in the cnp cost matrix. 
 	 *
 	 * @param  rowIndex      the row index of the stored value in the cnp cost matrix
 	 * 
 	 * @param  columnIndex   the column index of the stored value in the cnp cost matrix
 	 * 
 	 * @return costValue     the cost value stored in the cnp cost matrix
 	 * 
 	 * @see                  JCCNPCostMatrix
 	 */
      public int getMatrixEntry(int rowIndex, int columnIndex) 
      {
          return _cnp_cost_matrix[rowIndex][columnIndex];
      }

	/**
	 * Returns the string cnp cost matrix object type name. 
	 *
	 * @return ID     the string type name of the cnp cost matrix object
	 * 
	 * @see           JCCNPCostMatrix
	 */
	@Override
	public String getObjectTypeName() 
	{
    	return "JCCNPCostMatrix";
	}

	/**
	 * Displays information about this cnp cost matrix object. 
	 *
	 * @see           JCCNPCostMatrix
	 */
	@Override
	public void displayObjectInformation() 
	{
		int columnIndex;
		int rowIndex;
		
		System.out.println("CNP Cost Matrix:");
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
				System.out.print(_cnp_cost_matrix[rowIndex][columnIndex]);
			}
			System.out.println("]");
		}
		System.out.println("}");
	}

	/**
	 * Displays information about all cnp cost matrix objects. 
	 *
	 * @see           JCCNPCostMatrix
	 */
    public static void displayAllObjects()
    {
	    int                  cnpMatrixID;
	    JCCNPCostMatrix      currentCNPCostMatrix;
		
		for (cnpMatrixID = 0; cnpMatrixID < _cnpCostMatrices.size(); cnpMatrixID++)
		{
			currentCNPCostMatrix = JCCNPCostMatrix.getCNPCostMatrix(cnpMatrixID);
			currentCNPCostMatrix.displayObjectInformation();
		}
    }
}
