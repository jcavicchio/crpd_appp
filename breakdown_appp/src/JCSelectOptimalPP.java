import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * JCSelectOptimalPP is the algorithm class whose purpose is to implement the 
 * optimal preemption point placement algorithms for the control flow graph 
 * for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCSelectOptimalPP extends JCObject
{
	private static boolean _computeSectionBlockMatrices = true;
	private static boolean _debugBlockCostMap = false;
	private static boolean _debugDisplayBlocks = false; // JCC2
	private static boolean _debugDisplayConditionalBlocks = false; // JCC2
	private static boolean _debugProcessBlock = false; // JCC2
	private static boolean _debugDisplayConditionalSectionBlocks = false; // JCC2
	private static boolean _debugDisplayBasicBlock = false; // JCC2
	private static boolean _debugDisplaySubBlocks = false;
	private static boolean _debugDisplaySolutionPreemptionCost = false; // JCC2
	private static boolean _debugDisplaySolutionPreemptionPoints = false; // JCC2
	private static boolean _debugLoopBlockCostMap = false; // JCC2
	private static boolean _debugFunctionBlockCostMap = false; // JCC2
	private static boolean _debugFunctionCallBlockCostMap = false; // JCC2
	private static boolean _debugProcessConditionalBlock = false; // JCC2
	private static boolean _debugProcessConditionalSection = false; // JCC2
	private static boolean _debugProcessLoopBlock = false; // JCC2
	private static boolean _debugProcessFunctionBlock = false; // JCC2
	private static boolean _debugProcessFunctionCallBlock = false; // JCC2
	private static boolean _debugDisplaySectionBlocks = false; // JCC2
	private static boolean _debugDisplayLoopBlocks = false; // JCC2
	private static boolean _debugDisplayFunctionBlocks = false; // JCC2
	private static boolean _debugDisplayFunctionCallBlocks = false; // JCC2
	private static boolean _debugDisplayPreemptionPointObjects = false;
	private static boolean _debugDumpPreemptionPointObjects = false; // JCC2
	private static boolean _debugOutputSolutionCostToFile = true;
	private static boolean _debugOutputSolutionPreemptionPointsToFile = true;
	private static boolean _debugDisplayExecutionTime = false;
	
	private static int _nextID = 0;

	private static int _soppCount = 0;                      // Counter to count which SOPP object it is
	private static ArrayList<JCSelectOptimalPP> _selectOptimalPPs = 
			          new ArrayList<JCSelectOptimalPP>();   // SOPP object structure

	private int _selectOptimalPPID;
	private int _brt;                    // Block reload cycles 
	private int _q;                      // Maximum non preemptive region 
	private int _n;                      // Number of basic blocks
	private boolean _programStarted;
	private int _startingBBID;
	private int _endingBBID;
	private int _programBlockID;
	private int _basicBlockCount;
	private int _blockCount;
	private int _sectionCount;
	private int _conditionalSectionCount;
	private int _conditionalBlockCount;
	private int _loopBlockCount;
	private int _functionBlockCount;
	private int _functionCallBlockCount;
	private int _WCETCRPD;
	private long _startTime;
	private long _endTime;
	private long _elapsedTime;
	private String _grammarCostFileName;
	private String _grammarPPFileName;
	private ArrayList<ArrayList<Integer>> _path_storage;
	private ArrayList<ArrayList<Integer>> _loop_storage;
	private ArrayList<Integer> _loop_path_ids;
	private ArrayList<JCBasicBlock> _sectionBasicBlocks;
	private Stack<JCBlock> _blockStack;
	private Stack<JCConditionalBlock> _conditionalBlocks;
	private int _pcmRowIndex;
	private int _pcmColumnIndex;
	private JCPreemptionCostMatrix _pcmMatrix;
	private JCCNPCostMatrix _cnpMatrix;
	private JCQCostMatrix _qMatrix;
	private JCBCostMatrix _bMatrix;
	private JCPreemptionPointsMatrix _pbMatrix;
	
	/**
	 * Default constructor.
	 */
	JCSelectOptimalPP()
	{
		super();
		_selectOptimalPPID = _nextID;
		_nextID++;
		_soppCount = _nextID;
        _n = 0;
        _q = Integer.MAX_VALUE;
        _brt = 1;
        _startingBBID = -1;
        _endingBBID = -1;
        _programStarted = false;
        _programBlockID = -1;
        _basicBlockCount = 0;
        _sectionCount = 0; 
        _conditionalSectionCount = 0;
        _conditionalBlockCount = 0;
        _loopBlockCount = 0;
        _functionBlockCount = 0;
        _functionCallBlockCount = 0;
        _blockCount = 0; 
    	_WCETCRPD = Integer.MAX_VALUE;
        _grammarCostFileName = "";
        _grammarPPFileName = "";
    	_sectionBasicBlocks = new ArrayList<JCBasicBlock>();
    	_blockStack = new Stack<JCBlock>();
    	_conditionalBlocks = new Stack<JCConditionalBlock>();
    	_pcmRowIndex = 0;
    	_pcmColumnIndex = 0;
    	_pcmMatrix = null;
    	_cnpMatrix = null;
    	_qMatrix = null;
    	_bMatrix = null;
    	_pbMatrix = null;
    	_path_storage = null;
    	_loop_storage = null;
    	_loop_path_ids = null;
    	_startTime = System.nanoTime();
   	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCSelectOptimalPP
	 */
	public static void reset()
	{
		_nextID = 0;
		_soppCount = 0;
		_selectOptimalPPs = new ArrayList<JCSelectOptimalPP>();
	}
	
	/**
	 * Resets the static variables for all PPP classes. 
	 *
	 * @see                JCBasicBlock
	 * @see                JCBCostMatrix
	 * @see                JCBlock
	 * @see                JCCNPCostMatrix
	 * @see                JCConditionalBlock
	 * @see                JCConditionalSection
	 * @see                JCFunctionBlock
	 * @see                JCFunctionCallBlock
	 * @see                JCLoopBlock
	 * @see                JCPreemptionCostMatrix
	 * @see                JCPreemptionPoints
	 * @see                JCPreemptionPointsMatrix
	 * @see                JCQCostMatrix
	 * @see                JCSectionBlock
	 * @see                JCSelectOptimalPP
	 * @see                JCSubBlock
	 */
	public static void resetAll()
	{
		JCBasicBlock.reset();
		JCBCostMatrix.reset();
		JCBlock.reset();
		JCCNPCostMatrix.reset();
		JCConditionalBlock.reset();
		JCConditionalSection.reset();
		JCFunctionBlock.reset();
		JCFunctionCallBlock.reset();
		JCLoopBlock.reset();
		JCPreemptionCostMatrix.reset();
		JCPreemptionPoints.reset();
		JCPreemptionPointsMatrix.reset();
		JCQCostMatrix.reset();
		JCSectionBlock.reset();
		JCSelectOptimalPP.reset();
		JCSubBlock.reset();
	}
	
	/**
	 * Returns the latest select optimal preemption points object stored. 
	 *
	 * @return selectOptimalPP   the latest select optimal preemption points object
	 * 
	 * @see                      JCSelectOptimalPP
	 */
	public static JCSelectOptimalPP getLatestSelectOptimalPP()
	{
		int selectOptimalPPID = _nextID - 1;
		return JCSelectOptimalPP.getSelectOptimalPP(selectOptimalPPID);
	}
	
	/**
	 * Returns the numeric identifier of the select optimal preemption points object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _selectOptimalPPID   the identifier of this select optimal preemption points object
	 * 
	 * @see                         JCSelectOptimalPP
	 */
	public int getSelectOptimalPPID()
	{
		return _selectOptimalPPID;
	}

	/**
	 * Returns the string name of the select optimal preemption points object. 
	 *
	 * @return ID     the string name of this select optimal preemption points object
	 * 
	 * @see           JCSelectOptimalPP
	 */
	public String getSelectOptimalPPName()
	{
		String id = "JCSelectOptimalPP" + _selectOptimalPPID;
		
		return id;
	}
	
	/**
	 * Stores the select optimal preemption points object at the specified location in
	 * the select optimal preemption points array. 
	 *
	 * @param  selectOptimalPPID    the identifier of the stored select optimal preemption points object
	 * 
	 * @param  selectOptimalPP      the select optimal preemption points object to be stored
	 * 
	 * @see                         JCSelectOptimalPP
	 */
	public static void setSelectOptimalPP(int selectOptimalPPID, JCSelectOptimalPP selectOptimalPP)
	{
		if (selectOptimalPPID < _soppCount)
		{
			_selectOptimalPPs.add(selectOptimalPP);
		}
	}
	
	/**
	 * Returns the select optimal preemption points object associated with the specified identifier. 
	 *
	 * @return selectOptimalPP  the select optimal preemption points object stored
	 * 
	 * @see                     JCSelectOptimalPP
	 */
	public static JCSelectOptimalPP getSelectOptimalPP(int selectOptimalPPID)
	{
		if (selectOptimalPPID < _soppCount)
		{
	        return _selectOptimalPPs.get(selectOptimalPPID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Gets the size of the maximum non-preemptive region for 
	 * the current control flow graph. 
	 *
	 * @return  Q    the maximum non-preemptive region for this CFG
	 * 
	 * @see         JCSelectOptimalPP
	 */
	public int getQValue()
	{
		return this._q;
	}
	
	/**
	 * Sets the size of the maximum non-preemptive region for 
	 * the current control flow graph. 
	 *
	 * @param  Q    the maximum non-preemptive region for this CFG
	 * 
	 * @see         JCSelectOptimalPP
	 */
	public void setQValue(int Q)
	{
		JCSectionBlock.setQValue(Q);
		JCBasicBlock.setQValue(Q);
		JCBlock.setQValue(Q);
		this._q = Q;
	    //System.out.println("    Q = " + Q);
	}
	
	/**
	 * Sets the size of number of the basic block objects that comprise 
	 * the current control flow graph. 
	 *
	 * @param  n    the number of basic block objects in the CFG
	 * 
	 * @see         JCSelectOptimalPP
	 */
	public void setNBValue(int n)
	{
		this._n = n;
		if (n > 0)
		{
			// Allocate basic block array containing the structure of the graph.
			JCBasicBlock.setNValue(n);
			// Allocate preemption cost matrix.
			//JCPreemptionCostMatrix.setNValue(n);
			// Allocate CNP cost matrix.
			//JCCNPCostMatrix.setNValue(n);
			// Allocate Q cost matrix.
			//JCQCostMatrix.setNValue(n);
			// Allocate B cost matrix.
			//JCBCostMatrix.setNValue(n);
			// Allocate preemption points entry.
			JCPreemptionPoints.setNValue(n);
			// Allocate PB cost matrix.
			//JCPreemptionPointsMatrix.setNValue(n);
		}
	    //System.out.println("    n = " + n);
	}
	
	
	/**
	 * Sets the size of number of the basic block objects that comprise 
	 * the current control flow graph. 
	 *
	 * @param  n    the number of basic block objects in the preemption cost matrix
	 * 
	 * @see         JCSelectOptimalPP
	 */
	public void setNMValue(int n)
	{
		if (n > 0)
		{
			// Allocate basic block array containing the structure of the graph.
			//JCBasicBlock.setNValue(n);
			// Allocate preemption cost matrix.
			JCPreemptionCostMatrix.setNValue(n);
			// Allocate CNP cost matrix.
			//JCCNPCostMatrix.setNValue(n);
			// Allocate Q cost matrix.
			//JCQCostMatrix.setNValue(n);
			// Allocate B cost matrix.
			//JCBCostMatrix.setNValue(n);
			// Allocate preemption points entry.
			//JCPreemptionPoints.setNValue(n);
			// Allocate PB cost matrix.
			//JCPreemptionPointsMatrix.setNValue(n);
		}
	    //System.out.println("    n = " + n);
	}
	
	/**
	 * Gets the block reload cycles for the current control flow graph. 
	 *
	 * @return  brt   the block reload cycles for this CFG
	 * 
	 * @see           JCSelectOptimalPP
	 */
	public int getBRTValue()
	{
		return this._brt;
	}
	
	/**
	 * Sets the block reload cycles for the current control flow graph. 
	 *
	 * @param  brt   the block reload cycles for this CFG
	 * 
	 * @see           JCSelectOptimalPP
	 */
	public void setBRTValue(int brt)
	{
		this._brt = brt;
	    //System.out.println("    BRT = " + brt);
	}

	/**
     * Returns the file name of the parse object. 
     *
     * @return _grammarCostFileName   the cost file name of this PPP run
     * 
     * @see                           JCSelectOptimalPP
     */
    public String getCostFileName()
    {
        return _grammarCostFileName;
    }
    
    /**
     * Sets the file name of the parse object. 
     *
     * @param grammarCostFileName     the cost file name of this PPP run
     * 
     * @see                           JCSelectOptimalPP
     */
    public void setCostFileName(String grammarCostFileName)
    {
        _grammarCostFileName = grammarCostFileName;
    }


	/**
     * Returns the file name of the parse object. 
     *
     * @return _grammarPPFileName     the PP file name of this PPP run
     * 
     * @see                           JCSelectOptimalPP
     */
    public String getPPFileName()
    {
        return _grammarPPFileName;
    }
    
    /**
     * Sets the file name of the parse object. 
     *
     * @param grammarPPFileName     the PP file name of this PPP run
     * 
     * @see                         JCSelectOptimalPP
     */
    public void setPPFileName(String grammarPPFileName)
    {
        _grammarPPFileName = grammarPPFileName;
    }

    /**
     * Sets the grammar file name of this PPP run. 
     *
     * @param grammarFileName     the grammar file name of this PPP run
     * 
     * @see                       JCSelectOptimalPP
     */
    public void setGrammarFileName(String grammarFileName)
    {
        String grammarCostFileName = "";
        String grammarPPFileName = "";
        int    underscore_index;
        
        underscore_index = grammarFileName.indexOf("_");
        grammarCostFileName = grammarFileName.substring(0, underscore_index) + "_cost.txt";
        grammarPPFileName = grammarFileName.substring(0, underscore_index) + "_pps.txt";
        _grammarCostFileName = grammarCostFileName;
        _grammarPPFileName = grammarPPFileName;
    }

	/**
	 * Gets the program block for this preemption point placement problem 
	 *
	 * @return blockObj           the program block
	 * 
	 * @see                       JCBlock
	 * @see                       JCSelectOptimalPP
	 */
	public JCBlock getProgramBlock()
	{
		JCBlock blockObj = null;
		
		if (_programBlockID >= 0)
		{
			blockObj = JCBlock.getBlock(_programBlockID);
		}
		
		return blockObj;
	}
	
	/**
	 * Sets the program block for this preemption point placement problem 
	 *
	 * @param blockObj            the program block
	 * 
	 * @see                       JCBlock
	 * @see                       JCSelectOptimalPP
	 */
	public void setProgramBlock(JCBlock blockObj)
	{
		if (blockObj != null)
		{
			_programBlockID = blockObj.getBlockID();
		}
		else
		{
			_programBlockID = -1;
		}
	}
	
	/**
	 * Gets the starting program basic block for this preemption 
	 * point placement problem 
	 *
	 * @return bBlockObj          the program starting basic block
	 * 
	 * @see                       JCBasicBlock
	 * @see                       JCSelectOptimalPP
	 */
	public JCBasicBlock getStartingBasicBlock()
	{
		JCBasicBlock bBlockObj = null;
		
		if (_startingBBID >= 0)
		{
			bBlockObj = JCBasicBlock.getBasicBlock(_startingBBID);
		}
		
		return bBlockObj;
	}
	
	/**
	 * Sets the starting program basic block for this 
	 * preemption point placement problem 
	 *
	 * @param bBlockObj           the program starting basic block
	 * 
	 * @see                       JCBasicBlock
	 * @see                       JCSelectOptimalPP
	 */
	public void setStartingBasicBlock(JCBasicBlock bBlockObj)
	{
		if (bBlockObj != null)
		{
			_startingBBID = bBlockObj.getBBlockID();
			JCBasicBlock.setStartingBasicBlock(bBlockObj);
		}
		else
		{
			_startingBBID = -1;
		}
	}
	
	/**
	 * Gets the ending program basic block for this preemption 
	 * point placement problem 
	 *
	 * @return bBlockObj          the program ending basic block
	 * 
	 * @see                       JCBasicBlock
	 * @see                       JCSelectOptimalPP
	 */
	public JCBasicBlock getEndingBasicBlock()
	{
		JCBasicBlock bBlockObj = null;
		
		if (_endingBBID >= 0)
		{
			bBlockObj = JCBasicBlock.getBasicBlock(_endingBBID);
		}
		
		return bBlockObj;
	}
	
	/**
	 * Sets the ending program basic block for this 
	 * preemption point placement problem 
	 *
	 * @param bBlockObj           the program ending basic block
	 * 
	 * @see                       JCBasicBlock
	 * @see                       JCSelectOptimalPP
	 */
	public void setEndingBasicBlock(JCBasicBlock bBlockObj)
	{
		if (bBlockObj != null)
		{
			_endingBBID = bBlockObj.getBBlockID();
			JCBasicBlock.setEndingBasicBlock(bBlockObj);
		}
		else
		{
			_endingBBID = -1;
		}
	}
	
	/**
	 * Sets the program started flag for this 
	 * preemption point placement problem 
	 *
	 * @see                       JCSelectOptimalPP
	 */
	public void setProgramStarted()
	{
		_programStarted = true;
	}
	
	/**
	 * Clears the program started flag for this 
	 * preemption point placement problem 
	 *
	 * @see                       JCSelectOptimalPP
	 */
	public void clearProgramStarted()
	{
		_programStarted = false;
	}
	
	/**
	 * Checks the program started flag for this 
	 * preemption point placement problem 
	 *
	 * @see                       JCSelectOptimalPP
	 */
	public boolean isProgramStarted()
	{
		return _programStarted;
	}
	
	/**
	 * References the basic block object located at the current
	 * parsing location for the current control flow graph. 
	 *
	 * @param  bbID        the string identifier of the parsed basic block object
	 * 
	 * @param  bbWCET      the parsed worst case execution time of the basic block object
	 * 
	 * @return basicBlock  the basic block object at this parse location
	 * 
	 * @see                JCBasicBlock
	 * @see                JCSelectOptimalPP
	 */
	public JCBasicBlock accessBasicBlock(String bbID, int bbWCET)
	{
		JCBasicBlock basicBlock;

		basicBlock = JCBasicBlock.getBasicBlock(this._basicBlockCount);
		if (_debugDisplayBasicBlock == true)
		{
	        System.out.println("    Basic block " + this._basicBlockCount + "(" + basicBlock.getBBlockID() + ") ID: " + bbID + " WCET: " + bbWCET + " referenced");
            basicBlock.displayObjectInformation();
		}
	    this._basicBlockCount++;	
	    
	    return basicBlock;
	}

	/**
	 * Creates the basic block object located at the current
	 * parsing location for the current control flow graph. 
	 *
	 * @param  bbID        the string identifier of the parsed basic block object
	 * 
	 * @param  bbWCET      the parsed worst case execution time of the basic block object
	 * 
	 * @return basicBlock  the basic block object at this parse location
	 * 
	 * @see                JCBasicBlock
	 * @see                JCSelectOptimalPP
	 */
	public JCBasicBlock createBasicBlock(String bbID, int bbWCET)
	{
		JCBasicBlock basicBlock;
		JCBasicBlock startingBasicBlock;
	    
		basicBlock = new JCBasicBlock(bbID, bbWCET);
	    JCBasicBlock.setBasicBlock(this._basicBlockCount, basicBlock);
		if (_debugDisplayBasicBlock == true)
		{
	        System.out.println("    Basic block " + this._basicBlockCount + "(" + basicBlock.getBBlockID() + ") ID: " + bbID + " WCET: " + bbWCET + " created");
		}
		
		if (isProgramStarted() == true)
		{
			setStartingBasicBlock(basicBlock);
			clearProgramStarted();
			if (_debugDisplayBasicBlock == true)
			{
	            System.out.println("    Starting Basic block set to BB ID " + this._basicBlockCount + "(" + basicBlock.getBBlockID() + ") ID: " + bbID + " WCET: " + bbWCET);
			}
		}
		
		startingBasicBlock = getStartingBasicBlock();
		if (startingBasicBlock != null)
		{
			setEndingBasicBlock(basicBlock);
		}
		
	    this._basicBlockCount++;	
	    
	    return basicBlock;
	}
	
	/**
	 * Creates the section block object comprising a linear sequence
	 * of basic blocks located at the current parsing location for 
	 * the current control flow graph. 
	 *
	 * @return sectionBlock  the section block object at this parse location
	 * 
	 * @see                  JCSectionBlock
	 * @see                  JCSelectOptimalPP
	 */
	public JCSectionBlock createLinearSection()
	{
		JCSectionBlock sectionBlock = new JCSectionBlock();
		if (_debugDisplaySectionBlocks == true)
		{
		    System.out.println("    Section block " + "(" + sectionBlock.getSectionBlockID()+ ") (" + sectionBlock.getSubBlockID() + ") created");
		}
		_sectionCount++;
		
		return sectionBlock;
	}
	
	/**
	 * Creates a section block object and stores the saved basic block 
	 * objects located at the current parsing location in the section 
	 * block object for the current control flow graph. 
	 *
	 * @param basicBlock  the basic block object at this parse location
	 * 
	 * @see              JCBasicBlock
	 * @see              JCSelectOptimalPP
	 */
	public void addBasicBlockToLinearSection(JCBasicBlock basicBlock)
	{
		_sectionBasicBlocks.add(basicBlock);
	}
	
	/**
	 * Stores the basic block object located at the current parsing 
	 * location to be contained in the section block object for the
	 * current control flow graph. 
	 *
	 * @return sectionBlock  the section block object at this parse location
	 * 
	 * @see                  JCBasicBlock
	 * @see                  JCSectionBlock
	 * @see                  JCSelectOptimalPP
	 */
	public JCSectionBlock storeLinearSection()
	{
		JCBasicBlock basicBlock;
		int bBlockID;
		JCBasicBlock prevBasicBlock = null;
		JCSectionBlock sectionBlock = createLinearSection();
		
		for (bBlockID=0; bBlockID<_sectionBasicBlocks.size();bBlockID++)
		{
			basicBlock = _sectionBasicBlocks.get(bBlockID);
            sectionBlock.addBasicBlock(basicBlock);
    		if (_debugDisplayBasicBlock == true)
    		{
    	        System.out.println("    Basic block " + "(" + basicBlock.getBBlockID() + ") ID: " + basicBlock.getBBlockName() + " added to section block (" + sectionBlock.getSectionBlockID() + ")");
    		}
    	    if (prevBasicBlock != null)
    	    {
            	prevBasicBlock.addSuccessorBasicBlock(basicBlock);
            	basicBlock.addPredecessorBasicBlock(prevBasicBlock);
            	
            	basicBlock.updatePredecessorQListBasicBlocks(prevBasicBlock);
    	    }
    	    prevBasicBlock = basicBlock;
		}
		_sectionBasicBlocks.clear();
		
		return sectionBlock;
	}
	
	/**
	 * References the section block object comprising a linear sequence
	 * of basic blocks located at the current parsing location for 
	 * the current control flow graph. 
	 *
	 * @return sectionBlock  the section block object at this parse location
	 * 
	 * @see                  JCSectionBlock
	 * @see                  JCSelectOptimalPP
	 */
	public JCSectionBlock accessLinearSection()
	{
		JCSectionBlock sectionBlock;
		
		sectionBlock = JCSectionBlock.getSectionBlock(_sectionCount);
		if (_debugDisplaySectionBlocks == true)
		{
	        System.out.println("    Section block " + "(" + sectionBlock.getSectionBlockID() + ") (" + sectionBlock.getSubBlockID() + ") accessed");
		}
		_sectionCount++;
		
		if (_computeSectionBlockMatrices == true)
		{
			sectionBlock.computeSectionSolutionMatrix(_pcmMatrix);
			sectionBlock.computeSectionSolutionsViaMatrix(_pcmMatrix);
		}
		else
		{
			sectionBlock.computeSectionSolution(_pcmMatrix);
		}
		
		return sectionBlock;
	}

	/**
	 * Creates the conditional section object comprising a linear 
	 * sequence of blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @return conditionalSection  the conditional section object at this parse location
	 * 
	 * @see                        JCConditionalSection
	 * @see                        JCSelectOptimalPP
	 */
	public JCConditionalSection createConditionalSection()
	{
		JCConditionalSection conditionalSection = new JCConditionalSection();
		if (_debugDisplayConditionalSectionBlocks == true)
		{
	        System.out.println("    Conditional section " + "(" + conditionalSection.getConditionalSectionID() + ") (" + conditionalSection.getBlockID() + ") (" + conditionalSection.getSubBlockID() + ") created");
		}
		_blockStack.push(conditionalSection);
		_blockCount++;
		_conditionalSectionCount++;
		
		return conditionalSection;
	}
	
	/**
	 * Adds a section block object to the current block object comprising 
	 * a linear sequence of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @return sectionBlock  the section block object at this parse location
	 * 
	 * @see                  JCBlock
	 * @see                  JCConditionalSection
	 * @see                  JCSelectOptimalPP
	 * @see                  JCSubBlock
	 */
	public JCBlock addSectionToBlocks(JCSectionBlock sectionBlock)
	{
		int cbSectionSBID;
		JCConditionalBlock conditionalBlock;
		JCBlock blockObj;
		int newSectionSBID;
		int numberOfSubBlocks;
		int prevSectionSBID;
		JCSectionBlock prevSectionBlock;
		JCSubBlock subBlock;
		
		blockObj = _blockStack.pop();
		blockObj.addSubBlock(sectionBlock);
		if (_debugDisplaySectionBlocks == true)
		{
	        System.out.println("    Section block " + "(" + sectionBlock.getSectionBlockID() + ") (" + sectionBlock.getSubBlockID() + ") ID: " + sectionBlock.getSectionBlockName() + " Object type: " + sectionBlock.getObjectTypeName() + " added to block " + blockObj.getBlockName() + " (" + blockObj.getBlockID() + ") (" + blockObj.getSubBlockID() + ")");
		}
		
	    numberOfSubBlocks = (int)blockObj.numberOfSubBlocks();
        if (numberOfSubBlocks == 1)
        {
        	blockObj.setStartingSectionID(sectionBlock.getSectionBlockID());
        }
	    blockObj.setEndingSectionID(sectionBlock.getSectionBlockID());
	    
	    if (numberOfSubBlocks > 2)
	    {
	    	newSectionSBID = (numberOfSubBlocks-1);
	    	cbSectionSBID = (newSectionSBID-1);
	    	subBlock = blockObj.getSubBlockAtIndex(cbSectionSBID);
			if (subBlock.getObjectTypeName().equals("JCConditionalBlock") == true)
			{
				conditionalBlock = (JCConditionalBlock)subBlock;
		    	prevSectionSBID = (cbSectionSBID-1);
		    	subBlock = blockObj.getSubBlockAtIndex(prevSectionSBID);
				if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
				{
					prevSectionBlock = (JCSectionBlock)subBlock;
					//computeConditionalCosts(prevSectionBlock, conditionalBlock, sectionBlock);
				}
			}	    	
	    }
	    
	    if (numberOfSubBlocks == 1)
	    {
	    	blockObj.setStartingSectionID(sectionBlock.getSectionBlockID());
	    }
    	blockObj.setEndingSectionID(sectionBlock.getSectionBlockID());
	    
		_blockStack.push(blockObj);
		
		return blockObj;
	}

	/**
	 * Adds a conditional block object to the current block object comprising 
	 * a linear sequence of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @param  conditionalBlock  the conditional block object being parsed.
	 *
	 * @return blockObj          the containing block object at this parse location
	 * 
	 * @see                      JCBlock
	 * @see                      JCConditionalBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCBlock addConditionalToBlocks(JCConditionalBlock conditionalBlock)
	{
		JCBlock blockObj;
		int numberOfSubBlocks;
		
		blockObj = _blockStack.pop();
		blockObj.addSubBlock(conditionalBlock);
		if (_debugDisplayConditionalBlocks == true)
		{
	        System.out.println("    Conditional block " + "(" + conditionalBlock.getConditionalBlockID() + ") (" + conditionalBlock.getSubBlockID() + ") ID: " + conditionalBlock.getConditionalBlockName() + " Object type: " + conditionalBlock.getObjectTypeName() + " added to block " + blockObj.getBlockName() + " (" + blockObj.getBlockID() + ") (" + blockObj.getSubBlockID() + ")");
		}
		
	    numberOfSubBlocks = (int)blockObj.numberOfSubBlocks();
        if (numberOfSubBlocks == 1)
        {
        	blockObj.setStartingSectionID(conditionalBlock.getStartingSectionID());
        }
	    blockObj.setEndingSectionID(conditionalBlock.getStartingSectionID());
	    
		_blockStack.push(blockObj);
		
		return blockObj;
	}
	
	/**
	 * Adds a block object to the current block object located at the 
	 * current parsing location for the current control flow graph. 
	 *
	 * @param  newBlock          the block object being parsed.
	 *
	 * @return blockObj          the containing block object at this parse location
	 * 
	 * @see                      JCBlock
	 * @see                      JCConditionalBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCBlock addBlockToBlocks(JCBlock newBlock)
	{
		JCBlock blockObj;
		int numberOfSubBlocks;
		
		blockObj = _blockStack.pop();
		blockObj.addSubBlock(newBlock);
		if (_debugDisplayBlocks == true)
		{
		    System.out.println("    Block " + "(" + newBlock.getBlockID() + ") (" + newBlock.getSubBlockID() + ") ID: " + newBlock.getBlockName() + " Object type: " + newBlock.getObjectTypeName() + " added to block " + blockObj.getBlockName() + " (" + blockObj.getBlockID() + ") (" + blockObj.getSubBlockID() + ")");
		}
		
	    numberOfSubBlocks = (int)blockObj.numberOfSubBlocks();
        if (numberOfSubBlocks == 1)
        {
        	blockObj.setStartingSectionID(newBlock.getStartingSectionID());
        }
	    blockObj.setEndingSectionID(newBlock.getStartingSectionID());
	    
		_blockStack.push(blockObj);
		
		return blockObj;
	}
	
	/**
	 * Stores the conditional section object located at the current parsing 
	 * location to be contained in the conditional block object for the
	 * current control flow graph. 
	 *
	 * @return conditionalSection  the conditional section object at this parse location
	 * 
	 * @see                        JCBlock
	 * @see                        JCConditionalSection
	 * @see                        JCSelectOptimalPP
	 */
	public JCConditionalSection storeConditionalSection()
	{
		JCBlock bBlock = _blockStack.pop();
		JCConditionalSection conditionalSection = null;
		
		if (bBlock.getObjectTypeName().equals("JCConditionalSection") == true)
		{
			conditionalSection = (JCConditionalSection)bBlock;
			if (_debugDisplayConditionalSectionBlocks == true)
			{
		        System.out.println("    Conditional section " + "(" + conditionalSection.getConditionalSectionID() + ") (" + conditionalSection.getBlockID() + ") (" + conditionalSection.getSubBlockID() + ") completed");
			}
		}
		
		return conditionalSection;
	}
	
	/**
	 * References the conditional section object comprising a linear 
	 * sequence of blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @return conditionalSection  the conditional section object at this parse location
	 * 
	 * @see                        JCConditionalSection
	 * @see                        JCSelectOptimalPP
	 */
	public JCConditionalSection accessConditionalSection()
	{
		JCConditionalSection conditionalSection;
		
		conditionalSection = JCConditionalSection.getConditionalSection(_conditionalSectionCount);
		if (_debugDisplayConditionalSectionBlocks == true)
		{
            System.out.println("    Conditional section " + "(" + conditionalSection.getConditionalSectionID() + ") (" + conditionalSection.getBlockID() + ") (" + conditionalSection.getSubBlockID() + ") accessed");
		}
	    _conditionalSectionCount++;
		_blockCount++;
		
		return conditionalSection;
	}
	
	/**
	 * Creates the conditional block object comprising a linear 
	 * sequence of conditional sections located at the current 
	 * parsing location for the current control flow graph. 
	 *
	 * @return conditionalBlock  the conditional block object at this parse location
	 * 
	 * @see                      JCConditionalBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCConditionalBlock createConditionalBlock()
	{
		JCBlock bBlock = _blockStack.peek();
		JCConditionalBlock conditionalBlock = new JCConditionalBlock();
		conditionalBlock.setParentBlock(bBlock);
		if (_debugDisplayConditionalBlocks == true)
		{
	        System.out.println("    Conditional block " + "(" + conditionalBlock.getConditionalBlockID() + ") (" + conditionalBlock.getSubBlockID() + ") created");
		}
		_conditionalBlocks.push(conditionalBlock);
		_conditionalBlockCount++;
		
		return conditionalBlock;
	}
	
	/**
	 * Adds a conditional section object to the current conditional block object 
	 * comprising a linear sequence of conditional sections located at the current 
	 * parsing location for the current control flow graph. 
	 *
	 * @param conditionalSection  the conditional section object at this parse location
	 * 
	 * @see                      JCConditionalBlock
	 * @see                      JCConditionalSection
	 * @see                      JCSelectOptimalPP
	 */
	public void addConditionalSectionToConditional(JCConditionalSection conditionalSection)
	{
		JCConditionalBlock conditionalBlock;
		
		conditionalBlock = _conditionalBlocks.pop();
		conditionalBlock.addConditionalSection(conditionalSection);
		conditionalSection.setParentBlock(conditionalBlock.getParentBlock());
		if (_debugDisplayConditionalSectionBlocks == true)
		{
	        System.out.println("    Conditional section " + "(" + conditionalSection.getConditionalSectionID() + ") (" + conditionalSection.getBlockID() + ") (" + conditionalSection.getSubBlockID() + ") ID: " + conditionalSection.getConditionalSectionName() + " Object type: " + conditionalSection.getObjectTypeName() + " added to conditional block " + conditionalBlock.getConditionalBlockName() + " (" + conditionalBlock.getConditionalBlockID() + ") (" + conditionalBlock.getSubBlockID() + ")");
		}
		_conditionalBlocks.push(conditionalBlock);
	}

	/**
	 * Stores the conditional block object located at the current 
	 * parsing location to be contained in the current block object 
	 * for the current control flow graph. 
	 *
	 * @return conditionalBlock  the conditional block object at this parse location
	 * 
	 * @see                      JCConditionalBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCConditionalBlock storeConditionalBlock()
	{
		JCConditionalBlock conditionalBlock;
		
		conditionalBlock = _conditionalBlocks.pop();
		if (_debugDisplayConditionalBlocks == true)
		{
        	System.out.println("    Conditional block " + "(" + conditionalBlock.getConditionalBlockID() + ") (" + conditionalBlock.getSubBlockID() + ") completed");
		}
		
		return conditionalBlock;
	}
	
	/**
	 * References the conditional block object comprising a linear 
	 * sequence of conditional sections located at the current 
	 * parsing location for the current control flow graph. 
	 *
	 * @return conditionalBlock  the conditional block object at this parse location
	 * 
	 * @see                      JCConditionalBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCConditionalBlock accessConditionalBlock()
	{
		JCConditionalBlock conditionalBlock;
		
		conditionalBlock = JCConditionalBlock.getConditionalBlock(_conditionalBlockCount);
		if (_debugDisplayConditionalBlocks == true)
		{
	        System.out.println("    Conditional block " + "(" + conditionalBlock.getConditionalBlockID() + ") (" + conditionalBlock.getSubBlockID() + ") accessed");
		}
	    _conditionalBlockCount++;
		
		return conditionalBlock;
	}
	
	/**
	 * Creates the block object comprising a linear sequence 
	 * of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @return bBlock  the block object at this parse location
	 * 
	 * @see            JCBlock
	 * @see            JCSelectOptimalPP
	 */
	public JCBlock createBlock()
	{
		JCBlock bBlock;
		
		bBlock = new JCBlock();
		if (_debugDisplayBlocks == true)
		{
	        System.out.println("    Block " + "(" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") created");
		}
		_blockCount++;
		_blockStack.push(bBlock);
		
		return bBlock;
	}
	
	/**
	 * Stores the block object located at the current parsing
	 * location to be contained in the current block object 
	 * for the current control flow graph. 
	 *
	 * @return blockObj  the block object at this parse location
	 * 
	 * @see              JCBlock
	 * @see              JCSelectOptimalPP
	 */
	public JCBlock storeBlock()
	{
		JCBlock blockObj = null;
		JCBlock bBlock = _blockStack.pop();
		
		if (bBlock.getObjectTypeName().equals("JCBlock") == true)
		{
			blockObj = (JCBlock)bBlock;
			if (_debugDisplayBlocks == true)
			{
		        System.out.println("    Block " + "(" + blockObj.getBlockID() + ") (" + blockObj.getSubBlockID() + ") completed");
			}
		}
		
		return blockObj;
	}

	/**
	 * Creates a new containing block object so that a set of 
	 * sub-blocks can be cost processed resulting in created
	 * containing block object. Block objects comprise a linear 
	 * sequence of sub-blocks located at the current parsing 
	 * location for the current control flow graph. 
	 *
	 * @param bBlock     the parent block object
	 * 
	 * @param leftBlock  the left sub-block object
	 * 
	 * @param rightBlock the right sub-block object
	 * 
	 * @return newBlock  the newly created containing block object 
	 *                   at this parse location
	 * 
	 * @see              JCBlock
	 * @see              JCSelectOptimalPP
	 */
	JCBlock createNewContainingBlock(JCBlock bBlock, JCSubBlock leftBlock, JCSubBlock rightBlock)
	{
		JCSubBlock existingSubBlock;
		JCBlock newBlock;
		JCSectionBlock sectionBlock;
		Integer subBlockID;
		Iterator<Integer> subBlockIterator;
		ArrayList<Integer> subBlockList;

		// Create a containing block for the combination of section conditional section objects.
		bBlock.removeSubBlock(rightBlock);
		bBlock.removeSubBlock(leftBlock);
		
		newBlock = new JCBlock();
		newBlock.reAddSubBlock(leftBlock);
		leftBlock.setParentBlock(newBlock);
		newBlock.reAddSubBlock(rightBlock);
		rightBlock.setParentBlock(newBlock);
		
		if (leftBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		{
			sectionBlock = (JCSectionBlock)leftBlock;
			newBlock.setStartingSectionID(sectionBlock.getSectionBlockID());
		}
		else
		{
			newBlock.setStartingSectionID(leftBlock.getStartingSectionID());
		}
		
		if (rightBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		{
			sectionBlock = (JCSectionBlock)rightBlock;
			newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());
		}
		else
		{
			newBlock.setEndingSectionID(rightBlock.getEndingSectionID());
		}
		
		subBlockList = new ArrayList<Integer>();
		subBlockIterator = bBlock.getSubBlockIterator();
		while (subBlockIterator.hasNext() == true)
		{
			subBlockID = subBlockIterator.next();
			subBlockList.add(subBlockID);
		}
		
		// Temporarily remove the sub-blocks in the current block.
		subBlockIterator = subBlockList.iterator();
		while (subBlockIterator.hasNext() == true)
		{
			subBlockID = subBlockIterator.next();
			existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
			bBlock.removeSubBlock(existingSubBlock);
		}
		
		// Re-add the new block plus the remaining sub-blocks 
		// to the current block.
		bBlock.reAddSubBlock(newBlock);
		newBlock.setParentBlock(bBlock);
		subBlockIterator = subBlockList.iterator();
		while (subBlockIterator.hasNext() == true)
		{
			subBlockID = subBlockIterator.next();
			existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
			bBlock.reAddSubBlock(existingSubBlock);
		}
		
		return newBlock;
	}
	
	/**
	 * Creates a new containing block object so that a set of 
	 * sub-blocks can be cost processed resulting in created
	 * containing block object. Block objects comprise a linear 
	 * sequence of sub-blocks located at the current parsing 
	 * location for the current control flow graph. 
	 *
	 * @param bBlock      the parent block object
	 * 
	 * @param leftBlock   the left sub-block object
	 * 
	 * @param middleBlock the middle sub-block object
	 * 
	 * @param rightBlock  the right sub-block object
	 * 
	 * @return newBlock   the newly created containing block object 
	 *                    at this parse location
	 * 
	 * @see               JCBlock
	 * @see               JCSelectOptimalPP
	 */
	JCBlock createNewContainingBlock(JCBlock bBlock, JCSubBlock leftBlock, JCSubBlock middleBlock, JCSubBlock rightBlock)
	{
		JCSubBlock existingSubBlock;
		JCBlock newBlock;
		JCSectionBlock sectionBlock;
		Integer subBlockID;
		Iterator<Integer> subBlockIterator;
		ArrayList<Integer> subBlockList;

		// Create a containing block for the combination of left block middle block right block objects.
		bBlock.removeSubBlock(rightBlock);
		bBlock.removeSubBlock(middleBlock);
		bBlock.removeSubBlock(leftBlock);
		
		newBlock = new JCBlock();
		newBlock.reAddSubBlock(leftBlock);
		leftBlock.setParentBlock(newBlock);
		newBlock.reAddSubBlock(middleBlock);
		middleBlock.setParentBlock(newBlock);
		newBlock.reAddSubBlock(rightBlock);
		rightBlock.setParentBlock(newBlock);

		if (leftBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		{
			sectionBlock = (JCSectionBlock)leftBlock;
			newBlock.setStartingSectionID(sectionBlock.getSectionBlockID());
		}
		else
		{
			newBlock.setStartingSectionID(leftBlock.getStartingSectionID());
		}
		
		if (rightBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		{
			sectionBlock = (JCSectionBlock)rightBlock;
			newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());
		}
		else
		{
			newBlock.setEndingSectionID(rightBlock.getEndingSectionID());
		}
				
		subBlockList = new ArrayList<Integer>();
		subBlockIterator = bBlock.getSubBlockIterator();
		while (subBlockIterator.hasNext() == true)
		{
			subBlockID = subBlockIterator.next();
			subBlockList.add(subBlockID);
		}
		
		// Temporarily remove the sub-blocks in the current block.
		subBlockIterator = subBlockList.iterator();
		while (subBlockIterator.hasNext() == true)
		{
			subBlockID = subBlockIterator.next();
			existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
			bBlock.removeSubBlock(existingSubBlock);
		}
		
		// Re-add the new block plus the remaining sub-blocks 
		// to the current block.
		bBlock.reAddSubBlock(newBlock);
		newBlock.setParentBlock(bBlock);
		subBlockIterator = subBlockList.iterator();
		while (subBlockIterator.hasNext() == true)
		{
			subBlockID = subBlockIterator.next();
			existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
			bBlock.reAddSubBlock(existingSubBlock);
		}
		
		return newBlock;
	}
	
	/**
	 * Creates a new containing block object so that a set of 
	 * sub-blocks can be cost processed resulting in created
	 * containing block object. Block objects comprise a linear 
	 * sequence of sub-blocks located at the current parsing 
	 * location for the current control flow graph. 
	 *
	 * @param bBlock     the parent block object
	 * 
	 * @param newBlock   the newly created containing block object 
	 *                   at this parse location
	 * 
	 * @see              JCBlock
	 * @see              JCSelectOptimalPP
	 */
	void checkSingleBlockSolutionRemaining(JCBlock bBlock, JCBlock newBlock)
	{
		JCCostKey existingCostKey;
		JCCostSolution existingCostSolution;
		Iterator<JCCostSolution> solutionIterator;
		ArrayList<JCCostSolution> solutionMap;

		// If there is only one block left, copy the solution to the current block.
		solutionMap = bBlock.getSolutionMap();
		if ((bBlock.numberOfSubBlocks() == 1) && ((solutionMap == null) || (solutionMap.size() == 0)))
		{
			solutionMap = newBlock.getSolutionMap();
			if (solutionMap.size() > 0)
			{
				bBlock.setSolutionMap(solutionMap);
                if (_debugBlockCostMap == true)
                {
                	System.out.println("    JCBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
                	solutionIterator = solutionMap.iterator();
	    			while (solutionIterator.hasNext() == true) 
	    			{
	    				existingCostSolution = solutionIterator.next();
	    				existingCostKey = existingCostSolution.getSolutionKey();
                    	System.out.println("    JCBlock: costmap key " + existingCostKey);
                    	System.out.println("    JCBlock: costmap solution " + existingCostSolution.getSolutionCost());
	    			}
                }
			}
		}
	}
	
	/**
	 * Checks the current block object to see if a set of 
	 * sub-blocks can be cost processed resulting in a containing
	 * block object. Block objects comprise a linear sequence 
	 * of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @return bBlock  the block object at this parse location
	 * 
	 * @see            JCBlock
	 * @see            JCSelectOptimalPP
	 */
	public JCBlock processBlock(JCBlock blockObj)
	{
		JCBlock bBlock;
		int blkBlockID;
		JCSubBlock existingSubBlock;
		int indexOfSubBlock;
		JCBlock leftBlock;
		JCBlock newBlock;
		int newBlockID;
		int numberOfSubBlocks;
		JCSectionBlock prevSectionBlock;
		JCBlock rightBlock;
		JCSectionBlock sectionBlock;
		JCSubBlock subBlock;
		Integer subBlockID;
		Iterator<Integer> subBlockIterator;

		bBlock = blockObj.getParentBlock();
	    
		indexOfSubBlock = bBlock.indexOfSubBlock(blockObj);
		numberOfSubBlocks = (int)bBlock.numberOfSubBlocks();
		
		if (_debugProcessBlock == true)
		{
			System.out.println("    JCBlock processBlock() Block " + "(" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") ID: " + bBlock.getBlockName() + " Object type: " + bBlock.getObjectTypeName() + " number of subblocks is " + numberOfSubBlocks);
			System.out.println("    JCBlock processBlock() checking block " + "(" + blockObj.getBlockID()+ ") sub-block (" + blockObj.getSubBlockID() + ") index of subblock is " + indexOfSubBlock);
		}
		
		if (_debugDisplaySubBlocks == true)
		{
			subBlockIterator = bBlock.getSubBlockIterator();
			while (subBlockIterator.hasNext() == true)
			{
				subBlockID = subBlockIterator.next();
				existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
				System.out.println("    JCBlock processBlock() subBlock " + existingSubBlock.getSubBlockName() + " object type " + "(" + existingSubBlock.getObjectTypeName() + ") (" + existingSubBlock.getSubBlockID() + ") cost map size " + existingSubBlock.getSolutionMap().size());
			}
		}
		
	    if (indexOfSubBlock >= 1)
	    {
    		newBlockID = indexOfSubBlock;
	    	subBlock = bBlock.getSubBlockAtIndex(newBlockID);
	    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
	    	{
				rightBlock = (JCBlock)subBlock;
		    	blkBlockID = (newBlockID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(blkBlockID);
		    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
		    	{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of block objects.
					newBlock = createNewContainingBlock(bBlock, leftBlock, rightBlock);
					
					if (_debugProcessFunctionCallBlock == true)
					{
						System.out.println("    JCBlock processBlock() computing BB blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
		    	}
		    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		    	{
					sectionBlock = (JCSectionBlock)subBlock;
					
					// Create a containing block for the combination of section and block objects.
					newBlock = createNewContainingBlock(bBlock, sectionBlock, rightBlock);
					
					if (_debugProcessBlock == true)
					{
						System.out.println("    JCBlock processBlock() computing SB blocks solution");
					}
					newBlock.computeBlocksSolution(sectionBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
		    	}
	    	}
	    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
	    	{
	    		sectionBlock = (JCSectionBlock)subBlock;
		    	blkBlockID = (newBlockID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(blkBlockID);
		    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
		    	{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of section and block objects.
					newBlock = createNewContainingBlock(bBlock, leftBlock, sectionBlock);
					
					if (_debugProcessBlock == true)
					{
						System.out.println("    JCBlock processBlock() computing BS blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
		    	}
		    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		    	{
		    		prevSectionBlock = (JCSectionBlock)subBlock;
					
					// Create a containing block for the combination of section block objects.
					newBlock = createNewContainingBlock(bBlock, prevSectionBlock, sectionBlock);
					
					if (_debugProcessBlock == true)
					{
						System.out.println("    JCBlock processBlock() computing SS blocks solution");
					}
					newBlock.computeBlocksSolution(prevSectionBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
		    	}
	    	}
	    }
	    else if ((indexOfSubBlock == 0) && (numberOfSubBlocks == 1))
	    {
			// There is only one block left, copy the solution to the current block.
			checkSingleBlockSolutionRemaining(bBlock, blockObj);
	    }
	    
	    return bBlock;
	}
	
	/**
	 * Creates the loop block object comprising a sequence of blocks 
	 * with a fixed maximum number of iterations located at the current 
	 * parsing location for the current control flow graph. 
	 *
	 * @return loopBlock           the loop block object at this parse location
	 * 
	 * @see                        JCLoopBlock
	 * @see                        JCSelectOptimalPP
	 */
	public JCLoopBlock createLoopBlock()
	{
		JCLoopBlock loopBlock = new JCLoopBlock();
		
		if (_debugDisplayLoopBlocks == true)
		{
	        System.out.println("    Loop block " + "(" + loopBlock.getLoopBlockID() + ") (" + loopBlock.getBlockID() + ") (" + loopBlock.getSubBlockID() + ") created");
		}
		
		_blockStack.push(loopBlock);
		_blockCount++;
		_loopBlockCount++;
		
		return loopBlock;
	}

	/**
	 * Stores the loop block object located at the current parsing 
	 * location to be contained in the current block object for the
	 * current control flow graph. 
	 *
	 * @return loopBlock         the loop block object at this parse location
	 * 
	 * @see                      JCLoopBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCLoopBlock storeLoopBlock()
	{
		JCLoopBlock loopBlock = null;
		String objectTypeName;
		JCBlock bBlock;
		
		bBlock = _blockStack.pop();
		
		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCLoopBlock") == 0)
		{
			loopBlock = (JCLoopBlock)bBlock;
			if (_debugDisplayLoopBlocks == true)
			{
		        System.out.println("    Loop block " + "(" + loopBlock.getLoopBlockID() + ") (" + loopBlock.getBlockID() + ") (" + loopBlock.getSubBlockID() + ") completed");
			}
		}
		
		return loopBlock;
	}
	
	/**
	 * References the loop block object comprising a sequence of blocks 
	 * with a fixed maximum number of iterations located at the current 
	 * parsing location for the current control flow graph. 
	 *
	 * @return loopBlock           the loop block object at this parse location
	 * 
	 * @see                        JCLoopBlock
	 * @see                        JCSelectOptimalPP
	 */
	public JCLoopBlock accessLoopBlock()
	{
		JCBlock bBlock;
		JCLoopBlock loopBlock = null;
		String objectTypeName;
		
		bBlock = JCBlock.getBlock(_blockCount);

		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCLoopBlock") == 0)
		{
			loopBlock = (JCLoopBlock)bBlock;
			if (_debugDisplayLoopBlocks == true)
			{
		        System.out.println("    Loop block " + "(" + loopBlock.getLoopBlockID() + ") (" + loopBlock.getBlockID() + ") (" + loopBlock.getSubBlockID() + ") accessed");
			}
		}
		else
		{
	        System.out.println("accessLoopBlock(): Error: Loop block not found block type is " + objectTypeName + " (" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") accessed instead");
		}
		
	    _blockCount++;
		_loopBlockCount++;
		
	    return loopBlock;
	}
	
	/**
	 * Adds a loop block object to the current block object comprising 
	 * a sequence of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @param  loopBlock         the loop block object being parsed.
	 *
	 * @return blockObj          the containing block object at this parse location
	 * 
	 * @see                      JCBlock
	 * @see                      JCLoopBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCBlock addLoopToBlocks(JCLoopBlock loopBlock)
	{
		JCBlock blockObj;
		int numberOfSubBlocks;
		
		blockObj = _blockStack.pop();
		blockObj.addSubBlock(loopBlock);
		loopBlock.setParentBlock(blockObj);
		if (_debugDisplayLoopBlocks == true)
		{
	        System.out.println("    Loop block " + "(" + loopBlock.getLoopBlockID() + ") (" + loopBlock.getSubBlockID() + ") ID: " + loopBlock.getLoopBlockName() + " Object type: " + loopBlock.getObjectTypeName() + " added to block " + blockObj.getBlockName() + " (" + blockObj.getBlockID() + ") (" + blockObj.getSubBlockID() + ")");
		}
		
	    numberOfSubBlocks = (int)blockObj.numberOfSubBlocks();
        if (numberOfSubBlocks == 1)
        {
        	blockObj.setStartingSectionID(loopBlock.getStartingSectionID());
        }
	    blockObj.setEndingSectionID(loopBlock.getStartingSectionID());
	    
		_blockStack.push(blockObj);
		
		return blockObj;
	}
	
	/**
	 * Adds a block object to the current loop block object comprising 
	 * sequence of blocks with maximum number of iterations located at 
	 * the current parsing location for the current control flow graph. 
	 *
	 * @param block              the block object at this parse location
	 * 
	 * @see                      JCBlock
	 * @see                      JCLoopBlock
	 * @see                      JCSelectOptimalPP
	 */
	public void addBlocksToLoop(JCBlock block)
	{
		int blockID;
		int bBlockID;
		JCBlock bBlock;
		JCLoopBlock loopBlock;
		int numberOfSubBlocks;
		String objectTypeName;
		
		bBlock = _blockStack.pop();
		
		blockID = block.getBlockID();
		bBlockID = bBlock.getBlockID();
		
		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCLoopBlock") == 0)
		{
			loopBlock = (JCLoopBlock)bBlock;
			if (blockID != bBlockID)
			{
			    loopBlock.addSubBlock(block);
				block.setParentBlock(loopBlock);
				
				if (_debugDisplayLoopBlocks == true)
				{
				    System.out.println("    Block " + "(" + block.getBlockID() + ") (" + block.getSubBlockID() + ") ID: " + block.getBlockName() + " Object type: " + block.getObjectTypeName() + " added to loop block " + loopBlock.getLoopBlockName() + " (" + loopBlock.getLoopBlockID() + ") (" + loopBlock.getSubBlockID() + ")");
				}
			    
				numberOfSubBlocks = (int)loopBlock.numberOfSubBlocks();
		        if (numberOfSubBlocks == 1)
		        {
		        	loopBlock.setStartingSectionID(block.getStartingSectionID());
		        }
		        loopBlock.setEndingSectionID(block.getStartingSectionID());
			}
		}
		
		_blockStack.push(bBlock);
	}

	/**
	 * Sets the maximum number of iterations for the loop located at 
	 * the current parsing location for the current control flow graph. 
	 *
	 * @param maxIterations      the maximum number of iterations for
	 *                           the current loop object
	 * 
	 * @see                      JCLoopBlock
	 * @see                      JCSelectOptimalPP
	 */
	void setLoopMaxIterations(int maxIterations)
	{
		JCBlock bBlock;
		JCLoopBlock loopBlock;
		String objectTypeName;
		
		bBlock = _blockStack.pop();
		
		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCLoopBlock") == 0)
		{
			loopBlock = (JCLoopBlock)bBlock;
			loopBlock.setMaxLoopIterations(maxIterations);
			if (_debugDisplayLoopBlocks == true)
			{
		        System.out.println("    Loop block " + "(" + loopBlock.getLoopBlockID() + ") (" + loopBlock.getBlockID() + ") (" + loopBlock.getSubBlockID() + ") ID: " + loopBlock.getLoopBlockName() + " Object type: " + loopBlock.getObjectTypeName() + " maximum iterations set to " + maxIterations);
			}
		}
		
		_blockStack.push(bBlock);
	}
	
	/**
	 * Computes the potential preemption point solutions for the specified loop
	 * block objects. 
	 *               
	 * @param maxIterations      the maximum number of iterations for
	 *                           the current loop object
	 *                           
	 * @see                      ArrayList
	 * @see                      JCBlock
	 * @see                      JCPreemptionCostMatrix
	 * @see                      JCLoopBlock
	 * @see                      JCSectionBlock
	 */
	void computeLoopBlockSolution(JCLoopBlock loopBlock)
	{
		JCBlock block;
		JCCostKey existingCostKey;
		JCCostSolution existingCostSolution;
		JCBlock newBlock;
    	String objectType;
		Iterator<JCCostSolution> solutionIterator;
		ArrayList<JCCostSolution> solutionMap;
    	JCSubBlock subBlock;
		
		if (loopBlock.numberOfSubBlocks() == 1)
		{
			subBlock = loopBlock.getSubBlockAtIndex(0);
           	objectType = subBlock.getObjectTypeName();
           	if (objectType.compareTo("JCBlock") == 0)
           	{
           		block = (JCBlock) subBlock;

               	// If there is only one block left, copy the solution to the current block.
				solutionMap = block.getSolutionMap();
				if ((block.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
				{
					subBlock = block.getSubBlockAtIndex(0);
		           	objectType = subBlock.getObjectTypeName();
		           	if (objectType.compareTo("JCBlock") == 0)
		           	{
		           		newBlock = (JCBlock) subBlock;
						solutionMap = newBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							block.setSolutionMap(solutionMap);
	                        if (_debugLoopBlockCostMap == true)
	                        {
	                        	System.out.println("    JCLoopBlock: costmap from " + newBlock.getBlockName() + " stored into block " + block.getBlockName() + " size = " + solutionMap.size());
	                        	solutionIterator = solutionMap.iterator();
	    		    			while (solutionIterator.hasNext() == true) 
	    		    			{
	    		    				existingCostSolution = solutionIterator.next();
	    		    				existingCostKey = existingCostSolution.getSolutionKey();
	                            	System.out.println("    JCLoopBlock: costmap key " + existingCostKey);
	                            	System.out.println("    JCLoopBlock: costmap solution " + existingCostSolution.getSolutionCost());
	    		    			}
	                        }
						}
		           	}
				}
				
				if (_debugProcessLoopBlock == true)
				{
	                System.out.println("Computing loop preemption point solutions for (" + loopBlock.getLoopBlockID() + "," + loopBlock.getLoopBlockName() + "," + loopBlock.getSubBlockID() + "," + loopBlock.getSubBlockName() + ") ");
	                System.out.println("    Processing loop block (" + block.getBlockID() + "," + block.getBlockName() + "," + block.getSubBlockID() + "," + block.getSubBlockName() + ") ");
				}
           		loopBlock.computeLoopBlockSolution(block, _pcmMatrix);
           	}
		}
	}
	
	/**
	 * Checks the current block object to see if a set of 
	 * sub-blocks can be cost processed resulting in a containing
	 * block object. Block objects comprise a linear sequence 
	 * of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @return bBlock  the block object at this parse location
	 * 
	 * @see            JCBlock
	 * @see            JCLoopBlock
	 * @see            JCSelectOptimalPP
	 */
	public JCBlock processLoopBlock(JCLoopBlock loopBlock)
	{
		JCBlock bBlock;
		int blkBlockID;
		int cbSectionSBID;
		JCConditionalBlock conditionalBlock;
		JCSubBlock existingSubBlock;
		int indexOfSubBlock;
		JCBlock leftBlock;
		JCBlock newBlock;
		int newLoopBlockID;
		int newSectionSBID;
		int numberOfSubBlocks;
		JCSectionBlock prevSectionBlock;
		JCBlock rightBlock;
		JCSectionBlock sectionBlock;
		JCSubBlock subBlock;
		Integer subBlockID;
		Iterator<Integer> subBlockIterator;

		bBlock = loopBlock.getParentBlock();
	    
		indexOfSubBlock = bBlock.indexOfSubBlock(loopBlock);
		numberOfSubBlocks = (int)bBlock.numberOfSubBlocks();
		
		if (_debugProcessLoopBlock == true)
		{
			System.out.println("    JCLoopBlock processLoopBlock() Block " + "(" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") ID: " + bBlock.getBlockName() + " Object type: " + bBlock.getObjectTypeName() + " number of subblocks is " + numberOfSubBlocks);
			System.out.println("    JCLoopBlock processLoopBlock() checking loop block " + "(" + loopBlock.getLoopBlockID()+ ") sub-block (" + loopBlock.getSubBlockID() + ") index of subblock is " + indexOfSubBlock);
		}
		
		if (_debugDisplaySubBlocks == true)
		{
			subBlockIterator = bBlock.getSubBlockIterator();
			while (subBlockIterator.hasNext() == true)
			{
				subBlockID = subBlockIterator.next();
				existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
				System.out.println("    JCLoopBlock processLoopBlock() subBlock " + existingSubBlock.getSubBlockName() + " object type " + "(" + existingSubBlock.getObjectTypeName() + ") (" + existingSubBlock.getSubBlockID() + ") cost map size " + existingSubBlock.getSolutionMap().size());
			}
		}
		
	    if (indexOfSubBlock >= 2)
	    {
	    	newLoopBlockID = indexOfSubBlock;
	    	subBlock = bBlock.getSubBlockAtIndex(newLoopBlockID);
			if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
			    (subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
			{
				rightBlock = (JCBlock)subBlock;
		    	cbSectionSBID = (newLoopBlockID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(cbSectionSBID);
				if (subBlock.getObjectTypeName().equals("JCConditionalBlock") == true)
				{
					conditionalBlock = (JCConditionalBlock)subBlock;
					newSectionSBID = (cbSectionSBID-1);
			    	subBlock = bBlock.getSubBlockAtIndex(newSectionSBID);
					if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
					{
						prevSectionBlock = (JCSectionBlock)subBlock;

						// Create a containing block for the combination of section conditional block objects.
						newBlock = createNewContainingBlock(bBlock, prevSectionBlock, conditionalBlock, rightBlock);
						
						if (_debugProcessConditionalBlock == true)
						{
							System.out.println("    JCBlock processConditionalBlock() computing SCB conditional solution");
						}
						newBlock.computeConditionalSolution(prevSectionBlock, conditionalBlock, rightBlock, _pcmMatrix);
						
						// If there is only one block left, copy the solution to the current block.
						checkSingleBlockSolutionRemaining(bBlock, newBlock);					
					}
					else if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
						 	 (subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
							 (subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))

					{
						leftBlock = (JCBlock)subBlock;
						
						// Create a containing block for the combination of section conditional section objects.
						newBlock = createNewContainingBlock(bBlock, leftBlock, conditionalBlock, rightBlock);
												
						if (_debugProcessConditionalBlock == true)
						{
							System.out.println("    JCBlock processConditionalBlock() computing BCB conditional solution");
						}
						newBlock.computeConditionalSolution(leftBlock, conditionalBlock, rightBlock, _pcmMatrix);
						
						// If there is only one block left, copy the solution to the current block.
						checkSingleBlockSolutionRemaining(bBlock, newBlock);					
					}
				}
				else if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
						 (subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
						 (subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))

				{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of section conditional section objects.
					newBlock = createNewContainingBlock(bBlock, leftBlock, rightBlock);
					
					if (_debugProcessBlock == true)
					{
						System.out.println("    JCBlock processConditionalBlock() computing BB blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);					
				}
			}
	    }
	    else if (indexOfSubBlock == 1)
	    {
    		newLoopBlockID = indexOfSubBlock;
	    	subBlock = bBlock.getSubBlockAtIndex(newLoopBlockID);
	    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
	    	{
				rightBlock = (JCBlock)subBlock;
		    	blkBlockID = (newLoopBlockID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(blkBlockID);
		    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
		    	{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of block objects.
					newBlock = createNewContainingBlock(bBlock, leftBlock, rightBlock);
					
					if (_debugProcessLoopBlock == true)
					{
						System.out.println("    JCLoopBlock processLoopBlock() computing BB blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);					
					
					// Check to see if the newly created block can be further processed via a conditional block.
					// bBlock = this.processConditionalBlock(newBlock);
		    	}
		    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		    	{
					sectionBlock = (JCSectionBlock)subBlock;
					
					// Create a containing block for the combination of section and block objects.
					newBlock = createNewContainingBlock(bBlock, sectionBlock, rightBlock);
					
					if (_debugProcessLoopBlock == true)
					{
						System.out.println("    JCLoopBlock processLoopBlock() computing SB blocks solution");
					}
					newBlock.computeBlocksSolution(sectionBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
					
					// Check to see if the newly created block can be further processed via a conditional block.
					// bBlock = this.processConditionalBlock(newBlock);
		    	}
	    	}
	    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
	    	{
	    		sectionBlock = (JCSectionBlock)subBlock;
		    	blkBlockID = (newLoopBlockID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(blkBlockID);
		    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
		    	{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of section and block objects.
					newBlock = createNewContainingBlock(bBlock, leftBlock, sectionBlock);
					
					if (_debugProcessLoopBlock == true)
					{
						System.out.println("    JCLoopBlock processLoopBlock() computing BS blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
					
					// Check to see if the newly created block can be further processed via a conditional block.
					// bBlock = this.processConditionalBlock(newBlock);
		    	}
		    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		    	{
		    		prevSectionBlock = (JCSectionBlock)subBlock;
					
					// Create a containing block for the combination of section block objects.
					newBlock = createNewContainingBlock(bBlock, prevSectionBlock, sectionBlock);
					
					if (_debugProcessLoopBlock == true)
					{
						System.out.println("    JCLoopBlock processLoopBlock() computing SS blocks solution");
					}
					newBlock.computeBlocksSolution(prevSectionBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
					
					// Check to see if the newly created block can be further processed via a conditional block.
					// bBlock = this.processConditionalBlock(newBlock);
		    	}
	    	}
	    }
	    else if ((indexOfSubBlock == 0) && (numberOfSubBlocks == 1))
	    {
			// There is only one block left, copy the solution to the current block.
			checkSingleBlockSolutionRemaining(bBlock, loopBlock);
	    }
	    
	    return bBlock;
	}
	
	/**
	 * Creates the function block object comprising a sequence of blocks 
	 * located at the current parsing location for the current control 
	 * flow graph. 
	 *
	 * @return functionBlock       the function block object at this parse location
	 * 
	 * @see                        JCFunctionBlock
	 * @see                        JCSelectOptimalPP
	 */
	public JCFunctionBlock createFunctionBlock()
	{
		JCFunctionBlock functionBlock = new JCFunctionBlock();
		
		if (_debugDisplayFunctionBlocks == true)
		{
	        System.out.println("    Function block " + "(" + functionBlock.getFunctionBlockID() + ") (" + functionBlock.getBlockID() + ") (" + functionBlock.getSubBlockID() + ") created");
		}
		
		_blockStack.push(functionBlock);
		_blockCount++;
		_functionBlockCount++;
		
		return functionBlock;
	}

	/**
	 * Stores the function block object located at the current 
	 * parsing location to be contained in the current block object 
	 * for the current control flow graph. 
	 *
	 * @return functionBlock     the function block object at this parse location
	 * 
	 * @see                      JCFunctionBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCFunctionBlock storeFunctionBlock()
	{
		JCFunctionBlock functionBlock = null;
		String objectTypeName;
		JCBlock bBlock;
		
		bBlock = _blockStack.pop();
		
		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCFunctionBlock") == 0)
		{
			functionBlock = (JCFunctionBlock)bBlock;
			if (_debugDisplayFunctionBlocks == true)
			{
		        System.out.println("    Function block " + "(" + functionBlock.getFunctionBlockID() + ") (" + functionBlock.getBlockID() + ") (" + functionBlock.getSubBlockID() + ") completed");
			}
		}
		
		return functionBlock;
	}
	
	/**
	 * References the function block object comprising a sequence of blocks 
	 * with a fixed maximum number of iterations located at the current 
	 * parsing location for the current control flow graph. 
	 *
	 * @return functionBlock       the function block object at this parse location
	 * 
	 * @see                        JCFunctionBlock
	 * @see                        JCSelectOptimalPP
	 */
	public JCFunctionBlock accessFunctionBlock()
	{
		JCBlock bBlock;
		JCFunctionBlock functionBlock = null;
		String objectTypeName;
		
		bBlock = JCBlock.getBlock(_blockCount);

		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCFunctionBlock") == 0)
		{
			functionBlock = (JCFunctionBlock)bBlock;
			if (_debugDisplayFunctionBlocks == true)
			{
		        System.out.println("    Function block " + "(" + functionBlock.getFunctionBlockID() + ") (" + functionBlock.getBlockID() + ") (" + functionBlock.getSubBlockID() + ") accessed");
			}
		}
		else
		{
	        System.out.println("accessFunctionBlock(): Error: Function block not found block type is " + objectTypeName + " (" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") accessed instead");
		}
		
	    _blockCount++;
		_functionBlockCount++;
		
	    return functionBlock;
	}
	
	/**
	 * Adds a function block object to the current block object comprising 
	 * a sequence of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @param  functionBlock     the function block object being parsed.
	 *
	 * @return blockObj          the containing block object at this parse location
	 * 
	 * @see                      JCBlock
	 * @see                      JCFunctionBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCBlock addFunctionBlockToBlocks(JCFunctionBlock functionBlock)
	{
		JCBlock blockObj;
		int numberOfSubBlocks;
		
		blockObj = _blockStack.pop();
		blockObj.addSubBlock(functionBlock);
		functionBlock.setParentBlock(blockObj);
		
		if (_debugDisplayFunctionBlocks == true)
		{
	        System.out.println("    Function block " + "(" + functionBlock.getFunctionBlockID() + ") (" + functionBlock.getSubBlockID() + ") ID: " + functionBlock.getFunctionBlockName() + " Object type: " + functionBlock.getObjectTypeName() + " added to block " + blockObj.getBlockName() + " (" + blockObj.getBlockID() + ") (" + blockObj.getSubBlockID() + ")");
		}
		
	    numberOfSubBlocks = (int)blockObj.numberOfSubBlocks();
        if (numberOfSubBlocks == 1)
        {
        	blockObj.setStartingSectionID(functionBlock.getStartingSectionID());
        }
	    blockObj.setEndingSectionID(functionBlock.getStartingSectionID());
	    
		_blockStack.push(blockObj);
		
		return blockObj;
	}
	
	/**
	 * Adds a block object to the current function block object comprising 
	 * sequence of blocks with maximum number of iterations located at 
	 * the current parsing location for the current control flow graph. 
	 *
	 * @param block              the block object at this parse location
	 * 
	 * @see                      JCBlock
	 * @see                      JCFunctionBlock
	 * @see                      JCSelectOptimalPP
	 */
	public void addBlocksToFunctionBlock(JCBlock block)
	{
		int blockID;
		int bBlockID;
		JCBlock bBlock;
		JCFunctionBlock functionBlock;
		int numberOfSubBlocks;
		String objectTypeName;
		
		//System.out.println("addBlockstoFunctionBlock called");
		
		bBlock = _blockStack.pop();
		
		blockID = block.getBlockID();
		bBlockID = bBlock.getBlockID();
		
		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCFunctionBlock") == 0)
		{
			functionBlock = (JCFunctionBlock)bBlock;
			if (blockID != bBlockID)
			{
			    functionBlock.addSubBlock(block);
				block.setParentBlock(functionBlock);
				
				if (_debugDisplayFunctionBlocks == true)
				{
				    System.out.println("    Block " + "(" + block.getBlockID() + ") (" + block.getSubBlockID() + ") ID: " + block.getBlockName() + " Object type: " + block.getObjectTypeName() + " added to function block " + functionBlock.getFunctionBlockName() + " (" + functionBlock.getFunctionBlockID() + ") (" + functionBlock.getSubBlockID() + ")");
				}
				
			    numberOfSubBlocks = (int)functionBlock.numberOfSubBlocks();
		        if (numberOfSubBlocks == 1)
		        {
		        	functionBlock.setStartingSectionID(block.getStartingSectionID());
		        }
		        functionBlock.setEndingSectionID(block.getStartingSectionID());
			}
			else
			{
				System.out.println("addBlockstoFunctionBlock(): error both function block and block objects are the same!");
			}
		}
		else
		{
	        System.out.println("addBlocksToFunctionBlock(): Error: Function block not found block type is " + objectTypeName + " (" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") popped instead");
		}
		
		_blockStack.push(bBlock);
	}

	/**
	 * Sets the function name for the function block located at 
	 * the current parsing location for the current control flow graph. 
	 *
	 * @param functionName       the function name identifying the 
	 *                           the current function block object
	 * 
	 * @see                      JCFunctionBlock
	 * @see                      JCSelectOptimalPP
	 */
	void setFunctionName(String functionName)
	{
		JCBlock bBlock;
		JCFunctionBlock functionBlock;
		String objectTypeName;
		
		bBlock = _blockStack.pop();
		
		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCFunctionBlock") == 0)
		{
			functionBlock = (JCFunctionBlock)bBlock;
			functionBlock.setFunctionName(functionName);
			if (_debugDisplayFunctionBlocks == true)
			{
		        System.out.println("    Function block " + "(" + functionBlock.getFunctionBlockID() + ") (" + functionBlock.getBlockID() + ") (" + functionBlock.getSubBlockID() + ") ID: " + functionBlock.getFunctionBlockName() + " Object type: " + functionBlock.getObjectTypeName() + " function name set to " + functionName);
			}
		}
		
		_blockStack.push(bBlock);
	}
	
	/**
	 * Computes the potential preemption point solutions for the specified function
	 * block objects. 
	 *               
	 * @param functionBlock      the current function block object to 
	 *                           compute the preemption point solutions for
	 *                           
	 * @see                      ArrayList
	 * @see                      JCBlock
	 * @see                      JCFunctionBlock
	 * @see                      JCPreemptionCostMatrix
	 * @see                      JCSectionBlock
	 */
	void computeFunctionBlockSolution(JCFunctionBlock functionBlock)
	{
		JCBlock block;
		JCCostKey existingCostKey;
		JCCostSolution existingCostSolution;
		JCBlock newBlock;
    	String objectType;
		Iterator<JCCostSolution> solutionIterator;
		ArrayList<JCCostSolution> solutionMap;
    	JCSubBlock subBlock;
		
		if (functionBlock.numberOfSubBlocks() == 1)
		{
			subBlock = functionBlock.getSubBlockAtIndex(0);
           	objectType = subBlock.getObjectTypeName();
           	if (objectType.compareTo("JCBlock") == 0)
           	{
           		block = (JCBlock) subBlock;

               	// If there is only one block left, copy the solution to the current block.
				solutionMap = block.getSolutionMap();
				if ((block.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
				{
					subBlock = block.getSubBlockAtIndex(0);
		           	objectType = subBlock.getObjectTypeName();
		           	if (objectType.compareTo("JCBlock") == 0)
		           	{
		           		newBlock = (JCBlock) subBlock;
						solutionMap = newBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							block.setSolutionMap(solutionMap);
	                        if (_debugFunctionBlockCostMap == true)
	                        {
	                        	System.out.println("    JCFunctionBlock: costmap from " + newBlock.getBlockName() + " stored into block " + block.getBlockName() + " size = " + solutionMap.size());
	                        	solutionIterator = solutionMap.iterator();
	    		    			while (solutionIterator.hasNext() == true) 
	    		    			{
	    		    				existingCostSolution = solutionIterator.next();
	    		    				existingCostKey = existingCostSolution.getSolutionKey();
	                            	System.out.println("    JCFunctionBlock: costmap key " + existingCostKey);
	                            	System.out.println("    JCFunctionBlock: costmap solution " + existingCostSolution.getSolutionCost());
	    		    			}
	                        }
						}
		           	}
				}
				
				if (_debugProcessFunctionBlock == true)
				{
	                System.out.println("Computing function preemption point solutions for (" + functionBlock.getFunctionBlockID() + "," + functionBlock.getFunctionBlockName() + "," + functionBlock.getSubBlockID() + "," + functionBlock.getSubBlockName() + ") ");
	                System.out.println("    Processing function block (" + block.getBlockID() + "," + block.getBlockName() + "," + block.getSubBlockID() + "," + block.getSubBlockName() + ") ");
				}
           		functionBlock.computeFunctionBlockSolution(block, _pcmMatrix);
           	}
		}
	}
	
	/**
	 * Checks the current function block object to see if a set of 
	 * sub-blocks can be cost processed resulting in a containing
	 * block object. Block objects comprise a linear sequence 
	 * of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @return bBlock  the block object at this parse location
	 * 
	 * @see            JCBlock
	 * @see            JCFunctionBlock
	 * @see            JCSelectOptimalPP
	 */
	public JCBlock processFunctionBlock(JCFunctionBlock functionBlock)
	{
		JCBlock bBlock;
		int blkBlockID;
		JCSubBlock existingSubBlock;
		int indexOfSubBlock;
		JCBlock leftBlock;
		JCBlock newBlock;
		int newLoopBlockID;
		int numberOfSubBlocks;
		JCSectionBlock prevSectionBlock;
		JCBlock rightBlock;
		JCSectionBlock sectionBlock;
		JCSubBlock subBlock;
		Integer subBlockID;
		Iterator<Integer> subBlockIterator;

		bBlock = functionBlock.getParentBlock();
	    
		indexOfSubBlock = bBlock.indexOfSubBlock(functionBlock);
		numberOfSubBlocks = (int)bBlock.numberOfSubBlocks();
		
		if (_debugProcessFunctionBlock == true)
		{
			System.out.println("    JCFunctionBlock processFunctionBlock() Block " + "(" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") ID: " + bBlock.getBlockName() + " Object type: " + bBlock.getObjectTypeName() + " number of subblocks is " + numberOfSubBlocks);
			System.out.println("    JCFunctionBlock processFunctionBlock() checking function block " + "(" + functionBlock.getFunctionBlockID()+ ") sub-block (" + functionBlock.getSubBlockID() + ") index of subblock is " + indexOfSubBlock);
		}
		
		if (_debugDisplaySubBlocks == true)
		{
			subBlockIterator = bBlock.getSubBlockIterator();
			while (subBlockIterator.hasNext() == true)
			{
				subBlockID = subBlockIterator.next();
				existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
				System.out.println("    JCFunctionBlock processLoopBlock() subBlock " + existingSubBlock.getSubBlockName() + " object type " + "(" + existingSubBlock.getObjectTypeName() + ") (" + existingSubBlock.getSubBlockID() + ") cost map size " + existingSubBlock.getSolutionMap().size());
			}
		}
		
	    if (indexOfSubBlock >= 1)
	    {
    		newLoopBlockID = indexOfSubBlock;
	    	subBlock = bBlock.getSubBlockAtIndex(newLoopBlockID);
	    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
	    	{
				rightBlock = (JCBlock)subBlock;
		    	blkBlockID = (newLoopBlockID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(blkBlockID);
		    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
		    	{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of block objects.
					newBlock = createNewContainingBlock(bBlock, leftBlock, rightBlock);
					
					if (_debugProcessFunctionBlock == true)
					{
						System.out.println("    JCFunctionBlock processFunctionBlock() computing BB blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);					
		    	}
		    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		    	{
					sectionBlock = (JCSectionBlock)subBlock;
					
					// Create a containing block for the combination of section and block objects.
					newBlock = createNewContainingBlock(bBlock, sectionBlock, rightBlock);
					
					if (_debugProcessFunctionBlock == true)
					{
						System.out.println("    JCFunctionBlock processFunctionBlock() computing SB blocks solution");
					}
					newBlock.computeBlocksSolution(sectionBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
		    	}
	    	}
	    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
	    	{
	    		sectionBlock = (JCSectionBlock)subBlock;
		    	blkBlockID = (newLoopBlockID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(blkBlockID);
		    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
		    	{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of section and block objects.
					newBlock = createNewContainingBlock(bBlock, leftBlock, sectionBlock);
					
					if (_debugProcessFunctionBlock == true)
					{
						System.out.println("    JCFunctionBlock processFunctionBlock() computing BS blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
		    	}
		    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		    	{
		    		prevSectionBlock = (JCSectionBlock)subBlock;
					
					// Create a containing block for the combination of section block objects.
					newBlock = createNewContainingBlock(bBlock, prevSectionBlock, sectionBlock);
					
					if (_debugProcessFunctionBlock == true)
					{
						System.out.println("    JCFunctionBlock processFunctionBlock() computing SS blocks solution");
					}
					newBlock.computeBlocksSolution(prevSectionBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
		    	}
	    	}
	    }
	    else if ((indexOfSubBlock == 0) && (numberOfSubBlocks == 1))
	    {
			// There is only one block left, copy the solution to the current block.
			checkSingleBlockSolutionRemaining(bBlock, functionBlock);
	    }
	    
	    return bBlock;
	}
	
	/**
	 * Creates the function call block object comprising a sequence of blocks 
	 * located at the current parsing location for the current control flow
	 * graph. 
	 *
	 * @return functionBlock       the function call block object at this 
	 *                             parse location
	 * 
	 * @see                        JCFunctionCallBlock
	 * @see                        JCSelectOptimalPP
	 */
	public JCFunctionCallBlock createFunctionCallBlock()
	{
		JCFunctionCallBlock functionCallBlock = new JCFunctionCallBlock();
		
		if (_debugDisplayFunctionCallBlocks == true)
		{
	        System.out.println("    Function call block " + "(" + functionCallBlock.getFunctionCallBlockID() + ") (" + functionCallBlock.getBlockID() + ") (" + functionCallBlock.getSubBlockID() + ") created");
		}
		
		_blockStack.push(functionCallBlock);
		_blockCount++;
		_functionCallBlockCount++;
		
		return functionCallBlock;
	}

	/**
	 * Stores the function call block object located at the current 
	 * parsing location to be contained in the current block object 
	 * for the current control flow graph. 
	 *
	 * @return functionCallBlock the function call block object at 
	 *                           this parse location
	 * 
	 * @see                      JCFunctionCallBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCFunctionCallBlock storeFunctionCallBlock()
	{
		JCFunctionCallBlock functionCallBlock = null;
		String objectTypeName;
		JCBlock bBlock;
		
		bBlock = _blockStack.pop();
		
		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCFunctionCallBlock") == 0)
		{
			functionCallBlock = (JCFunctionCallBlock)bBlock;
			if (_debugDisplayFunctionCallBlocks == true)
			{
		        System.out.println("    Function call block " + "(" + functionCallBlock.getFunctionCallBlockID() + ") (" + functionCallBlock.getBlockID() + ") (" + functionCallBlock.getSubBlockID() + ") completed");
			}
		}
		
		return functionCallBlock;
	}
	
	/**
	 * References the function call block object comprising a sequence of blocks 
	 * with a fixed maximum number of iterations located at the current 
	 * parsing location for the current control flow graph. 
	 *
	 * @return functionBlock       the function call block object at this 
	 *                             parse location
	 * 
	 * @see                        JCFunctionCallBlock
	 * @see                        JCSelectOptimalPP
	 */
	public JCFunctionCallBlock accessFunctionCallBlock()
	{
		JCBlock bBlock;
		JCFunctionCallBlock functionCallBlock = null;
		String objectTypeName;
		
		bBlock = JCBlock.getBlock(_blockCount);

		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCFunctionCallBlock") == 0)
		{
			functionCallBlock = (JCFunctionCallBlock)bBlock;
			if (_debugDisplayFunctionCallBlocks == true)
			{
		        System.out.println("    Function call block " + "(" + functionCallBlock.getFunctionCallBlockID() + ") (" + functionCallBlock.getBlockID() + ") (" + functionCallBlock.getSubBlockID() + ") accessed");
			}
		}
		else
		{
	        System.out.println("accessFunctionCallBlock(): Error: Function call block not found block type is " + objectTypeName + " (" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") accessed instead");
		}
		
	    _blockCount++;
	    _functionCallBlockCount++;
		
	    return functionCallBlock;
	}
	
	/**
	 * Adds a function call block object to the current block object comprising 
	 * a sequence of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @param  functionCallBlock the function call block object being parsed.
	 *
	 * @return blockObj          the containing block object at this parse location
	 * 
	 * @see                      JCFunctionCallBlock
	 * @see                      JCSelectOptimalPP
	 */
	public JCBlock addFunctionCallBlockToBlocks(JCFunctionCallBlock functionCallBlock)
	{
		JCBlock blockObj;
		int numberOfSubBlocks;
		
		blockObj = _blockStack.pop();
		blockObj.addSubBlock(functionCallBlock);
		functionCallBlock.setParentBlock(blockObj);
		
		if (_debugDisplayFunctionCallBlocks == true)
		{
	        System.out.println("    Function call block " + "(" + functionCallBlock.getFunctionCallBlockID() + ") (" + functionCallBlock.getSubBlockID() + ") ID: " + functionCallBlock.getFunctionCallBlockName() + " Object type: " + functionCallBlock.getObjectTypeName() + " added to block " + blockObj.getBlockName() + " (" + blockObj.getBlockID() + ") (" + blockObj.getSubBlockID() + ")");
		}
		
	    numberOfSubBlocks = (int)blockObj.numberOfSubBlocks();
        if (numberOfSubBlocks == 1)
        {
        	blockObj.setStartingSectionID(functionCallBlock.getStartingSectionID());
        }
	    blockObj.setEndingSectionID(functionCallBlock.getStartingSectionID());
	    
		_blockStack.push(blockObj);
		
		return blockObj;
	}
	
	/**
	 * Adds a block object to the current function call block object comprising 
	 * sequence of blocks located at the current parsing location for the 
	 * current control flow graph. 
	 *
	 * @param block              the block object at this parse location
	 * 
	 * @see                      JCBlock
	 * @see                      JCFunctionCallBlock
	 * @see                      JCSelectOptimalPP
	 */
	public void addBlocksToFunctionCallBlock(JCBlock block)
	{
		int blockID;
		int bBlockID;
		JCBlock bBlock;
		JCFunctionCallBlock functionCallBlock;
		int numberOfSubBlocks;
		String objectTypeName;
		
		//System.out.println("addBlockstoFunctionCallBlock called");
		
		bBlock = _blockStack.pop();
		
		blockID = block.getBlockID();
		bBlockID = bBlock.getBlockID();
		
		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCFunctionCallBlock") == 0)
		{
			functionCallBlock = (JCFunctionCallBlock)bBlock;
			if (blockID != bBlockID)
			{
			    functionCallBlock.addSubBlock(block);
				block.setParentBlock(functionCallBlock);
				
				if (_debugDisplayFunctionCallBlocks == true)
				{
				    System.out.println("    Block " + "(" + block.getBlockID() + ") (" + block.getSubBlockID() + ") ID: " + block.getBlockName() + " Object type: " + block.getObjectTypeName() + " added to function call block " + functionCallBlock.getFunctionCallBlockName() + " (" + functionCallBlock.getFunctionCallBlockID() + ") (" + functionCallBlock.getSubBlockID() + ")");
				}
				
			    numberOfSubBlocks = (int)functionCallBlock.numberOfSubBlocks();
		        if (numberOfSubBlocks == 1)
		        {
		        	functionCallBlock.setStartingSectionID(block.getStartingSectionID());
		        }
		        functionCallBlock.setEndingSectionID(block.getStartingSectionID());			    
			}
			else
			{
				System.out.println("addBlockstoFunctionCallBlock(): error both function block and block objects are the same!");
			}
		}
		else
		{
	        System.out.println("addBlocksToFunctionCallBlock(): Error: Function call block not found block type is " + objectTypeName + " (" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") popped instead");
		}
		
		_blockStack.push(bBlock);
	}

	/**
	 * Sets the function call name for the function call block located at 
	 * the current parsing location for the current control flow graph. 
	 *
	 * @param functionCallName   the function call name identifying the 
	 *                           the current function call block object
	 * 
	 * @see                      JCFunctionCallBlock
	 * @see                      JCSelectOptimalPP
	 */
	void setFunctionCallName(String functionCallName)
	{
		JCBlock bBlock;
		JCFunctionBlock functionBlock;
		JCFunctionCallBlock functionCallBlock;
		String objectTypeName;
		
		bBlock = _blockStack.pop();
		
		objectTypeName = bBlock.getObjectTypeName();
		if (objectTypeName.compareTo("JCFunctionCallBlock") == 0)
		{
			functionCallBlock = (JCFunctionCallBlock)bBlock;
			functionCallBlock.setFunctionCallName(functionCallName);
			if (_debugDisplayFunctionCallBlocks == true)
			{
		        System.out.println("    Function call block " + "(" + functionCallBlock.getFunctionCallBlockID() + ") (" + functionCallBlock.getSubBlockID() + ") ID: " + functionCallBlock.getFunctionCallBlockName() + " Object type: " + functionCallBlock.getObjectTypeName() + " function name set to " + functionCallName);
			}
			
			functionBlock = JCFunctionBlock.findFunctionBlock(functionCallName);
			if (functionBlock != null)
			{
				functionBlock.addFunctionCallBlock(functionCallBlock);
				functionCallBlock.setFunctionBlock(functionBlock);
				
				if (_debugDisplayFunctionCallBlocks == true)
				{
			        System.out.print("    Function call block " + "(" + functionCallBlock.getFunctionCallBlockID() + ") (" + functionCallBlock.getSubBlockID() + ") ID: " + functionCallBlock.getFunctionCallBlockName() + " Object type: " + functionCallBlock.getObjectTypeName() + " associated with Function Block name " + functionCallName);
			        System.out.println(" " + "(" + functionBlock.getFunctionBlockID() + ") (" + functionBlock.getSubBlockID() + ") ID: " + functionBlock.getFunctionBlockName() + " Object type: " + functionBlock.getObjectTypeName() + " added to block " + functionBlock.getBlockName() + " (" + functionBlock.getBlockID() + ") (" + functionBlock.getSubBlockID() + ")");
				}
			}
		}
		
		_blockStack.push(bBlock);
	}
	
	/**
	 * Computes the potential preemption point solutions for the specified function
	 * call block objects. 
	 *               
	 * @param functionCallBlock  the current function call block object to 
	 *                           compute the preemption point solutions for
	 *                           
	 * @see                      ArrayList
	 * @see                      JCBlock
	 * @see                      JCFunctionCallBlock
	 * @see                      JCPreemptionCostMatrix
	 * @see                      JCSectionBlock
	 */
	void computeFunctionCallBlockSolution(JCFunctionCallBlock functionCallBlock)
	{
		JCBlock block;
		JCCostKey existingCostKey;
		JCCostSolution existingCostSolution;
		JCBlock newBlock;
    	String objectType;
    	JCSectionBlock sectionBlock;
		Iterator<JCCostSolution> solutionIterator;
		ArrayList<JCCostSolution> solutionMap;
    	JCSubBlock subBlock;
		
		if (functionCallBlock.numberOfSubBlocks() == 1)
		{
			subBlock = functionCallBlock.getSubBlockAtIndex(0);
           	objectType = subBlock.getObjectTypeName();
           	if (objectType.compareTo("JCBlock") == 0)
           	{
           		block = (JCBlock) subBlock;

               	// If there is only one block left, copy the solution to the current block.
				solutionMap = block.getSolutionMap();
				if ((block.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
				{
					subBlock = block.getSubBlockAtIndex(0);
		           	objectType = subBlock.getObjectTypeName();
		           	if (objectType.compareTo("JCBlock") == 0)
		           	{
		           		newBlock = (JCBlock) subBlock;
						solutionMap = newBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							block.setSolutionMap(solutionMap);
	                        if (_debugFunctionCallBlockCostMap == true)
	                        {
	                        	System.out.println("    JCFunctionCallBlock: costmap from " + newBlock.getBlockName() + " stored into block " + block.getBlockName() + " size = " + solutionMap.size());
	                        	solutionIterator = solutionMap.iterator();
	    		    			while (solutionIterator.hasNext() == true) 
	    		    			{
	    		    				existingCostSolution = solutionIterator.next();
	    		    				existingCostKey = existingCostSolution.getSolutionKey();
	                            	System.out.println("    JCFunctionCallBlock: costmap key " + existingCostKey);
	                            	System.out.println("    JCFunctionCallBlock: costmap solution " + existingCostSolution.getSolutionCost());
	    		    			}
	                        }
						}
		           	}
		           	else if (objectType.compareTo("JCSectionBlock") == 0)
		           	{
		           		sectionBlock = (JCSectionBlock) subBlock;
						solutionMap = sectionBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							block.setSolutionMap(solutionMap);
	                        if (_debugFunctionCallBlockCostMap == true)
	                        {
	                        	System.out.println("    JCFunctionCallBlock: costmap from " + sectionBlock.getSectionBlockName() + " stored into block " + block.getBlockName() + " size = " + solutionMap.size());
	                        	solutionIterator = solutionMap.iterator();
	    		    			while (solutionIterator.hasNext() == true) 
	    		    			{
	    		    				existingCostSolution = solutionIterator.next();
	    		    				existingCostKey = existingCostSolution.getSolutionKey();
	                            	System.out.println("    JCFunctionCallBlock: costmap key " + existingCostKey);
	                            	System.out.println("    JCFunctionCallBlock: costmap solution " + existingCostSolution.getSolutionCost());
	    		    			}
	                        }
						}
		           	}
				}
				
				if (_debugProcessFunctionCallBlock == true)
				{
	                System.out.println("Computing function call preemption point solutions for (" + functionCallBlock.getFunctionCallBlockID() + "," + functionCallBlock.getFunctionCallBlockName() + "," + functionCallBlock.getSubBlockID() + "," + functionCallBlock.getSubBlockName() + ") ");
	                System.out.println("    Processing function call block (" + block.getBlockID() + "," + block.getBlockName() + "," + block.getSubBlockID() + "," + block.getSubBlockName() + ") ");
				}
           		functionCallBlock.computeFunctionCallBlockSolution(block, _pcmMatrix);
           	}
		}
	}
	
	/**
	 * Checks the current function call block object to see if a set of 
	 * sub-blocks can be cost processed resulting in a containing
	 * block object. Block objects comprise a linear sequence 
	 * of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @return bBlock  the block object at this parse location
	 * 
	 * @see            JCBlock
	 * @see            JCFunctionCallBlock
	 * @see            JCSelectOptimalPP
	 */
	public JCBlock processFunctionCallBlock(JCFunctionCallBlock functionCallBlock)
	{
		JCBlock bBlock;
		int blkBlockID;
		JCSubBlock existingSubBlock;
		int indexOfSubBlock;
		JCBlock leftBlock;
		JCBlock newBlock;
		int newLoopBlockID;
		int numberOfSubBlocks;
		JCSectionBlock prevSectionBlock;
		JCBlock rightBlock;
		JCSectionBlock sectionBlock;
		JCSubBlock subBlock;
		Integer subBlockID;
		Iterator<Integer> subBlockIterator;

		bBlock = functionCallBlock.getParentBlock();
	    
		indexOfSubBlock = bBlock.indexOfSubBlock(functionCallBlock);
		numberOfSubBlocks = (int)bBlock.numberOfSubBlocks();
		
		if (_debugProcessFunctionCallBlock == true)
		{
			System.out.println("    JCFunctionCallBlock processFunctionCallBlock() Block " + "(" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") ID: " + bBlock.getBlockName() + " Object type: " + bBlock.getObjectTypeName() + " number of subblocks is " + numberOfSubBlocks);
			System.out.println("    JCFunctionCallBlock processFunctionCallBlock() checking function call block " + "(" + functionCallBlock.getFunctionCallBlockID()+ ") sub-block (" + functionCallBlock.getSubBlockID() + ") index of subblock is " + indexOfSubBlock);
		}
		
		if (_debugDisplaySubBlocks == true)
		{
			subBlockIterator = bBlock.getSubBlockIterator();
			while (subBlockIterator.hasNext() == true)
			{
				subBlockID = subBlockIterator.next();
				existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
				System.out.println("    JCFunctionCallBlock processFunctionCallBlock() subBlock " + existingSubBlock.getSubBlockName() + " object type " + "(" + existingSubBlock.getObjectTypeName() + ") (" + existingSubBlock.getSubBlockID() + ") cost map size " + existingSubBlock.getSolutionMap().size());
			}
		}
		
	    if (indexOfSubBlock >= 1)
	    {
    		newLoopBlockID = indexOfSubBlock;
	    	subBlock = bBlock.getSubBlockAtIndex(newLoopBlockID);
	    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
	    	{
				rightBlock = (JCBlock)subBlock;
		    	blkBlockID = (newLoopBlockID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(blkBlockID);
		    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
		    	{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of block objects.
					newBlock = createNewContainingBlock(bBlock, leftBlock, rightBlock);
					
					if (_debugProcessFunctionCallBlock == true)
					{
						System.out.println("    JCFunctionCallBlock processFunctionCallBlock() computing BB blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);					
					
					// Check to see if the newly created block can be further processed via a conditional block.
					// bBlock = this.processConditionalBlock(newBlock);
		    	}
		    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		    	{
					sectionBlock = (JCSectionBlock)subBlock;
					
					// Create a containing block for the combination of section and block objects.
					newBlock = createNewContainingBlock(bBlock, sectionBlock, rightBlock);
					
					if (_debugProcessFunctionCallBlock == true)
					{
						System.out.println("    JCFunctionCallBlock processFunctionCallBlock() computing SB blocks solution");
					}
					newBlock.computeBlocksSolution(sectionBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
					
					// Check to see if the newly created block can be further processed via a conditional block.
					// bBlock = this.processConditionalBlock(newBlock);
		    	}
	    	}
	    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
	    	{
	    		sectionBlock = (JCSectionBlock)subBlock;
		    	blkBlockID = (newLoopBlockID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(blkBlockID);
		    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
		    	{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of section and block objects.
					newBlock = createNewContainingBlock(bBlock, leftBlock, sectionBlock);
					
					if (_debugProcessFunctionCallBlock == true)
					{
						System.out.println("    JCFunctionCallBlock processFunctionCallBlock() computing BS blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
					
					// Check to see if the newly created block can be further processed via a conditional block.
					// bBlock = this.processConditionalBlock(newBlock);
		    	}
		    	else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		    	{
		    		prevSectionBlock = (JCSectionBlock)subBlock;
					
					// Create a containing block for the combination of section block objects.
					newBlock = createNewContainingBlock(bBlock, prevSectionBlock, sectionBlock);
					
					if (_debugProcessFunctionCallBlock == true)
					{
						System.out.println("    JCFunctionCallBlock processFunctionCallBlock() computing SS blocks solution");
					}
					newBlock.computeBlocksSolution(prevSectionBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					checkSingleBlockSolutionRemaining(bBlock, newBlock);
					
					// Check to see if the newly created block can be further processed via a conditional block.
					// bBlock = this.processConditionalBlock(newBlock);
		    	}
	    	}
	    }
	    else if ((indexOfSubBlock == 0) && (numberOfSubBlocks == 1))
	    {
			// There is only one block left, copy the solution to the current block.
			checkSingleBlockSolutionRemaining(bBlock, functionCallBlock);
	    }
	    
	    return bBlock;
	}
	
	/**
	 * Checks the current block object to see if a set of 
	 * sub-blocks can be cost processed resulting in a containing
	 * block object. Block objects comprise a linear sequence 
	 * of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @param  parseSectionBlock  the section block object at this
	 *                            parse location
	 *                            
	 * @return bBlock             the parent block object at this 
	 *                            parse location
	 * 
	 * @see                       JCBlock
	 * @see                       JCSelectOptimalPP
	 */
	public JCBlock processConditionalSectionBlock(JCSectionBlock parseSectionBlock)
	{
		JCBlock bBlock;
		int blockID;
		int cbSectionSBID;
		JCConditionalBlock conditionalBlock;
		JCCostKey existingCostKey;
		JCCostSolution existingCostSolution;
		JCSubBlock existingSubBlock;
		int indexOfSubBlock;
		JCBlock leftBlock;
		JCBlock newBlock;
		int newSectionSBID;
		int numberOfSubBlocks;
		int prevSectionSBID;
		JCSectionBlock prevSectionBlock;
		JCSectionBlock sectionBlock;
		Iterator<JCCostSolution> solutionIterator;
		ArrayList<JCCostSolution> solutionMap;
		JCSubBlock subBlock;
		Integer subBlockID;
		Iterator<Integer> subBlockIterator;
		ArrayList<Integer> subBlockList;

		bBlock = parseSectionBlock.getParentBlock();
		
		indexOfSubBlock = bBlock.indexOfSubBlock(parseSectionBlock);
		numberOfSubBlocks = (int)bBlock.numberOfSubBlocks();
		
		if (_debugProcessConditionalBlock == true)
		{
			System.out.println("    JCBlock processConditionalSectionBlock() Block " + "(" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") ID: " + bBlock.getBlockName() + " Object type: " + bBlock.getObjectTypeName() + " number of subblocks is " + numberOfSubBlocks);
			System.out.println("    JCBlock processConditionalSectionBlock() checking section " + "(" + parseSectionBlock.getSectionBlockID()+ ") sub-block (" + parseSectionBlock.getSubBlockID() + ") index of subblock is " + indexOfSubBlock);
		}
		
		if (_debugDisplaySubBlocks == true)
		{
			subBlockIterator = bBlock.getSubBlockIterator();
			while (subBlockIterator.hasNext() == true)
			{
				subBlockID = subBlockIterator.next();
				existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
				System.out.println("    JCBlock processConditionalSectionBlock() subBlock " + existingSubBlock.getSubBlockName() + " object type " + "(" + existingSubBlock.getObjectTypeName() + ") (" + existingSubBlock.getSubBlockID() + ") cost map size " + existingSubBlock.getSolutionMap().size());
			}
		}
		
	    if (indexOfSubBlock >= 2)
	    {
	    	newSectionSBID = indexOfSubBlock;
	    	subBlock = bBlock.getSubBlockAtIndex(newSectionSBID);
			if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
			{
				sectionBlock = (JCSectionBlock)subBlock;
		    	cbSectionSBID = (newSectionSBID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(cbSectionSBID);
				if (subBlock.getObjectTypeName().equals("JCConditionalBlock") == true)
				{
					conditionalBlock = (JCConditionalBlock)subBlock;
			    	prevSectionSBID = (cbSectionSBID-1);
			    	subBlock = bBlock.getSubBlockAtIndex(prevSectionSBID);
					if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
					{
						prevSectionBlock = (JCSectionBlock)subBlock;

						// Create a containing block for the combination of section conditional section objects.
						bBlock.removeSubBlock(sectionBlock);
						bBlock.removeSubBlock(conditionalBlock);
						bBlock.removeSubBlock(prevSectionBlock);
						
						newBlock = new JCBlock();
						newBlock.reAddSubBlock(prevSectionBlock);
						prevSectionBlock.setParentBlock(newBlock);
						newBlock.reAddSubBlock(conditionalBlock);
						conditionalBlock.setParentBlock(newBlock);
						newBlock.reAddSubBlock(sectionBlock);
						sectionBlock.setParentBlock(newBlock);
						newBlock.setStartingSectionID(prevSectionBlock.getSectionBlockID());
						newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());
						//newBlock.setStartingSectionID(bBlock.getStartingSectionID());
						//newBlock.setEndingSectionID(bBlock.getEndingSectionID());
						
						subBlockList = new ArrayList<Integer>();
						subBlockIterator = bBlock.getSubBlockIterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							subBlockList.add(subBlockID);
						}
						
						// Temporarily remove the sub-blocks in the current block.
						subBlockIterator = subBlockList.iterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
							bBlock.removeSubBlock(existingSubBlock);
						}
						
						// Re-add the new block plus the remaining sub-blocks 
						// to the current block.
						bBlock.reAddSubBlock(newBlock);
						newBlock.setParentBlock(bBlock);
						subBlockIterator = subBlockList.iterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
							bBlock.reAddSubBlock(existingSubBlock);
						}
						
						if (_debugProcessConditionalBlock == true)
						{
							System.out.println("    JCBlock processConditionalSectionBlock() computing SCS conditional solution");
						}
						newBlock.computeConditionalSolution(prevSectionBlock, conditionalBlock, sectionBlock, _pcmMatrix);
						
						// If there is only one block left, copy the solution to the current block.
						solutionMap = bBlock.getSolutionMap();
						if ((bBlock.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
						{
							solutionMap = newBlock.getSolutionMap();
							if (solutionMap.size() > 0)
							{
								bBlock.setSolutionMap(solutionMap);
	                            if (_debugBlockCostMap == true)
	                            {
	                            	System.out.println("    JCBlock: processConditionalSectionBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
	                            	solutionIterator = solutionMap.iterator();
	        		    			while (solutionIterator.hasNext() == true) 
	        		    			{
	        		    				existingCostSolution = solutionIterator.next();
	        		    				existingCostKey = existingCostSolution.getSolutionKey();
	                                	System.out.println("    JCBlock: processConditionalSectionBlock: costmap key " + existingCostKey);
	                                	System.out.println("    JCBlock: processConditionalSectionBlock: costmap solution " + existingCostSolution.getSolutionCost());
	        		    			}
	                            }
							}
						}
					}
					else if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
						 	 (subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
							 (subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))

					{
						leftBlock = (JCBlock)subBlock;
						
						// Create a containing block for the combination of section conditional section objects.
						bBlock.removeSubBlock(sectionBlock);
						bBlock.removeSubBlock(conditionalBlock);
						bBlock.removeSubBlock(leftBlock);
						
						newBlock = new JCBlock();
						newBlock.reAddSubBlock(leftBlock);
						leftBlock.setParentBlock(newBlock);
						newBlock.reAddSubBlock(conditionalBlock);
						conditionalBlock.setParentBlock(newBlock);
						newBlock.reAddSubBlock(sectionBlock);
						sectionBlock.setParentBlock(newBlock);
						newBlock.setStartingSectionID(leftBlock.getStartingSectionID());
						newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());					
						//newBlock.setStartingSectionID(bBlock.getStartingSectionID());
						//newBlock.setEndingSectionID(bBlock.getEndingSectionID());					
						
						subBlockList = new ArrayList<Integer>();
						subBlockIterator = bBlock.getSubBlockIterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							subBlockList.add(subBlockID);
						}
						
						// Temporarily remove the sub-blocks in the current block.
						subBlockIterator = subBlockList.iterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
							bBlock.removeSubBlock(existingSubBlock);
						}
						
						// Re-add the new block plus the remaining sub-blocks 
						// to the current block.
						bBlock.reAddSubBlock(newBlock);
						newBlock.setParentBlock(bBlock);
						subBlockIterator = subBlockList.iterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
							bBlock.reAddSubBlock(existingSubBlock);
						}
						
						if (_debugProcessConditionalBlock == true)
						{
							System.out.println("    JCBlock processConditionalSectionBlock() computing BCS conditional solution");
						}
						newBlock.computeConditionalSolution(leftBlock, conditionalBlock, sectionBlock, _pcmMatrix);
						
						// If there is only one block left, copy the solution to the current block.
						solutionMap = bBlock.getSolutionMap();
						if ((bBlock.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
						{
							solutionMap = newBlock.getSolutionMap();
							if (solutionMap.size() > 0)
							{
								bBlock.setSolutionMap(solutionMap);
	                            if (_debugBlockCostMap == true)
	                            {
	                            	System.out.println("    JCBlock: processConditionalSectionBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
	                            	solutionIterator = solutionMap.iterator();
	        		    			while (solutionIterator.hasNext() == true) 
	        		    			{
	        		    				existingCostSolution = solutionIterator.next();
	        		    				existingCostKey = existingCostSolution.getSolutionKey();
	                                	System.out.println("    JCBlock: processConditionalSectionBlock: costmap key " + existingCostKey);
	                                	System.out.println("    JCBlock: processConditionalSectionBlock: costmap solution " + existingCostSolution.getSolutionCost());
	        		    			}
	                            }
							}
						}
					}
				}
				else if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
						 (subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
						 (subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))

				{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of section conditional section objects.
					bBlock.removeSubBlock(sectionBlock);
					bBlock.removeSubBlock(leftBlock);
					
					newBlock = new JCBlock();
					newBlock.reAddSubBlock(leftBlock);
					leftBlock.setParentBlock(newBlock);
					newBlock.reAddSubBlock(sectionBlock);
					sectionBlock.setParentBlock(newBlock);
					newBlock.setStartingSectionID(leftBlock.getStartingSectionID());
					newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());					
					//newBlock.setStartingSectionID(bBlock.getStartingSectionID());
					//newBlock.setEndingSectionID(bBlock.getEndingSectionID());					
					
					subBlockList = new ArrayList<Integer>();
					subBlockIterator = bBlock.getSubBlockIterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						subBlockList.add(subBlockID);
					}
					
					// Temporarily remove the sub-blocks in the current block.
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.removeSubBlock(existingSubBlock);
					}
					
					// Re-add the new block plus the remaining sub-blocks 
					// to the current block.
					bBlock.reAddSubBlock(newBlock);
					newBlock.setParentBlock(bBlock);
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.reAddSubBlock(existingSubBlock);
					}
					
					if (_debugProcessBlock == true)
					{
						System.out.println("    JCBlock processConditionalSectionBlock() computing BS blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					solutionMap = bBlock.getSolutionMap();
					if ((bBlock.numberOfSubBlocks() == 1) && ((solutionMap == null) || (solutionMap.size() == 0)))
					{
						solutionMap = newBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							bBlock.setSolutionMap(solutionMap);
                            if (_debugBlockCostMap == true)
                            {
                            	System.out.println("    JCBlock: processConditionalSectionBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
                            	solutionIterator = solutionMap.iterator();
        		    			while (solutionIterator.hasNext() == true) 
        		    			{
        		    				existingCostSolution = solutionIterator.next();
        		    				existingCostKey = existingCostSolution.getSolutionKey();
                                	System.out.println("    JCBlock: processConditionalSectionBlock: costmap key " + existingCostKey);
                                	System.out.println("    JCBlock: processConditionalSectionBlock: costmap solution " + existingCostSolution.getSolutionCost());
        		    			}
                            }
						}
					}
				}
			}
	    }
	    else if (indexOfSubBlock == 1)
	    {
	    	newSectionSBID = indexOfSubBlock;
	    	subBlock = bBlock.getSubBlockAtIndex(newSectionSBID);
			if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
			{
				sectionBlock = (JCSectionBlock)subBlock;
		    	blockID = (newSectionSBID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(blockID);
		    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
				{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of section conditional section objects.
					bBlock.removeSubBlock(sectionBlock);
					bBlock.removeSubBlock(leftBlock);
					
					newBlock = new JCBlock();
					newBlock.reAddSubBlock(leftBlock);
					leftBlock.setParentBlock(newBlock);
					newBlock.reAddSubBlock(sectionBlock);
					sectionBlock.setParentBlock(newBlock);
					newBlock.setStartingSectionID(leftBlock.getStartingSectionID());
					newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());					
					//newBlock.setStartingSectionID(bBlock.getStartingSectionID());
					//newBlock.setEndingSectionID(bBlock.getEndingSectionID());					
					
					subBlockList = new ArrayList<Integer>();
					subBlockIterator = bBlock.getSubBlockIterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						subBlockList.add(subBlockID);
					}
					
					// Temporarily remove the sub-blocks in the current block.
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.removeSubBlock(existingSubBlock);
					}
					
					// Re-add the new block plus the remaining sub-blocks 
					// to the current block.
					bBlock.reAddSubBlock(newBlock);
					newBlock.setParentBlock(bBlock);
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.reAddSubBlock(existingSubBlock);
					}
					
					if (_debugProcessBlock == true)
					{
						System.out.println("    JCBlock processConditionalSectionBlock() computing BS blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					solutionMap = bBlock.getSolutionMap();
					if ((bBlock.numberOfSubBlocks() == 1) && ((solutionMap == null) || (solutionMap.size() == 0)))
					{
						solutionMap = newBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							bBlock.setSolutionMap(solutionMap);
                            if (_debugBlockCostMap == true)
                            {
                            	System.out.println("    JCBlock: processConditionalSectionBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
                            	solutionIterator = solutionMap.iterator();
        		    			while (solutionIterator.hasNext() == true) 
        		    			{
        		    				existingCostSolution = solutionIterator.next();
        		    				existingCostKey = existingCostSolution.getSolutionKey();
                                	System.out.println("    JCBlock: processConditionalSectionBlock: costmap key " + existingCostKey);
                                	System.out.println("    JCBlock: processConditionalSectionBlock: costmap solution " + existingCostSolution.getSolutionCost());
        		    			}
                            }
						}
					}
				}
			    else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
				{
					prevSectionBlock = (JCSectionBlock)subBlock;

					// Create a containing block for the combination of section conditional section objects.
					bBlock.removeSubBlock(sectionBlock);
					bBlock.removeSubBlock(prevSectionBlock);
					
					newBlock = new JCBlock();
					newBlock.reAddSubBlock(prevSectionBlock);
					prevSectionBlock.setParentBlock(newBlock);
					newBlock.reAddSubBlock(sectionBlock);
					sectionBlock.setParentBlock(newBlock);
					newBlock.setStartingSectionID(prevSectionBlock.getSectionBlockID());
					newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());
					//newBlock.setStartingSectionID(bBlock.getStartingSectionID());
					//newBlock.setEndingSectionID(bBlock.getEndingSectionID());
					
					subBlockList = new ArrayList<Integer>();
					subBlockIterator = bBlock.getSubBlockIterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						subBlockList.add(subBlockID);
					}
					
					// Temporarily remove the sub-blocks in the current block.
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.removeSubBlock(existingSubBlock);
					}
					
					// Re-add the new block plus the remaining sub-blocks 
					// to the current block.
					bBlock.reAddSubBlock(newBlock);
					newBlock.setParentBlock(bBlock);
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.reAddSubBlock(existingSubBlock);
					}
					
					if (_debugProcessBlock == true)
					{
						System.out.println("    JCBlock processConditionalSectionBlock() computing SS blocks solution");
					}
					newBlock.computeBlocksSolution(prevSectionBlock, sectionBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					solutionMap = bBlock.getSolutionMap();
					if ((bBlock.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
					{
						solutionMap = newBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							bBlock.setSolutionMap(solutionMap);
                            if (_debugBlockCostMap == true)
                            {
                            	System.out.println("    JCBlock: processConditionalSectionBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
                            	solutionIterator = solutionMap.iterator();
        		    			while (solutionIterator.hasNext() == true) 
        		    			{
        		    				existingCostSolution = solutionIterator.next();
        		    				existingCostKey = existingCostSolution.getSolutionKey();
                                	System.out.println("    JCBlock: processConditionalSectionBlock: costmap key " + existingCostKey);
                                	System.out.println("    JCBlock: processConditionalSectionBlock: costmap solution " + existingCostSolution.getSolutionCost());
        		    			}
                            }
						}
					}
				}
			}
	    }
	    else if ((indexOfSubBlock == 0) && (numberOfSubBlocks == 1))
	    {
			// If there is only one block left, copy the solution to the current block.
			solutionMap = bBlock.getSolutionMap();
			if ((bBlock.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
			{
				solutionMap = parseSectionBlock.getSolutionMap();
				if (solutionMap.size() > 0)
				{
					bBlock.setSolutionMap(solutionMap);
                    if (_debugBlockCostMap == true)
                    {
                    	System.out.println("    JCBlock: processConditionalSectionBlock: costmap from " + parseSectionBlock.getSectionBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
                    	solutionIterator = solutionMap.iterator();
		    			while (solutionIterator.hasNext() == true) 
		    			{
		    				existingCostSolution = solutionIterator.next();
		    				existingCostKey = existingCostSolution.getSolutionKey();
                        	System.out.println("    JCBlock: processConditionalSectionBlock: costmap key " + existingCostKey);
                        	System.out.println("    JCBlock: processConditionalSectionBlock: costmap solution " + existingCostSolution.getSolutionCost());
		    			}
                    }
				}
			}
	    }
	    
	    return bBlock;
	}
	
	/**
	 * Checks the current block object to see if a set of 
	 * sub-blocks can be cost processed resulting in a containing
	 * block object. Block objects comprise a linear sequence 
	 * of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @param  parseBlock  the block object at this parse location
	 *                            
	 * @return bBlock      the parent block object at this parse location
	 * 
	 * @see                JCBlock
	 * @see                JCSelectOptimalPP
	 */
	public JCBlock processConditionalBlock(JCBlock parseBlock)
	{
		JCBlock bBlock;
		int blockID;
		int cbSectionSBID;
		JCConditionalBlock conditionalBlock;
		JCCostKey existingCostKey;
		JCCostSolution existingCostSolution;
		JCSubBlock existingSubBlock;
		int indexOfSubBlock;
		JCBlock leftBlock;
		JCBlock newBlock;
		int newSectionSBID;
		int numberOfSubBlocks;
		int prevSectionSBID;
		JCSectionBlock prevSectionBlock;
		JCBlock rightBlock;
		//JCSectionBlock sectionBlock;
		Iterator<JCCostSolution> solutionIterator;
		ArrayList<JCCostSolution> solutionMap;
		JCSubBlock subBlock;
		Integer subBlockID;
		Iterator<Integer> subBlockIterator;
		ArrayList<Integer> subBlockList;

		bBlock = parseBlock.getParentBlock();
		
		indexOfSubBlock = bBlock.indexOfSubBlock(parseBlock);
		numberOfSubBlocks = (int)bBlock.numberOfSubBlocks();
		
		if (_debugProcessConditionalBlock == true)
		{
			System.out.println("    JCBlock processConditionalBlock() Block " + "(" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") ID: " + bBlock.getBlockName() + " Object type: " + bBlock.getObjectTypeName() + " number of subblocks is " + numberOfSubBlocks);
			System.out.println("    JCBlock processConditionalBlock() checking block " + "(" + parseBlock.getBlockID()+ ") sub-block (" + parseBlock.getSubBlockID() + ") index of subblock is " + indexOfSubBlock);
		}
		
		if (_debugDisplaySubBlocks == true)
		{
			subBlockIterator = bBlock.getSubBlockIterator();
			while (subBlockIterator.hasNext() == true)
			{
				subBlockID = subBlockIterator.next();
				existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
				System.out.println("    JCBlock processConditionalBlock() subBlock " + existingSubBlock.getSubBlockName() + " object type " + "(" + existingSubBlock.getObjectTypeName() + ") (" + existingSubBlock.getSubBlockID() + ") cost map size " + existingSubBlock.getSolutionMap().size());
			}
		}
		
	    if (indexOfSubBlock >= 2)
	    {
	    	newSectionSBID = indexOfSubBlock;
	    	subBlock = bBlock.getSubBlockAtIndex(newSectionSBID);
			if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
			    (subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
			{
				rightBlock = (JCBlock)subBlock;
		    	cbSectionSBID = (newSectionSBID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(cbSectionSBID);
				if (subBlock.getObjectTypeName().equals("JCConditionalBlock") == true)
				{
					conditionalBlock = (JCConditionalBlock)subBlock;
			    	prevSectionSBID = (cbSectionSBID-1);
			    	subBlock = bBlock.getSubBlockAtIndex(prevSectionSBID);
					if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
					{
						prevSectionBlock = (JCSectionBlock)subBlock;

						// Create a containing block for the combination of section conditional section objects.
						bBlock.removeSubBlock(rightBlock);
						bBlock.removeSubBlock(conditionalBlock);
						bBlock.removeSubBlock(prevSectionBlock);
						
						newBlock = new JCBlock();
						newBlock.reAddSubBlock(prevSectionBlock);
						prevSectionBlock.setParentBlock(newBlock);
						newBlock.reAddSubBlock(conditionalBlock);
						conditionalBlock.setParentBlock(newBlock);
						newBlock.reAddSubBlock(rightBlock);
						rightBlock.setParentBlock(newBlock);
						newBlock.setStartingSectionID(prevSectionBlock.getSectionBlockID());
						newBlock.setEndingSectionID(rightBlock.getEndingSectionID());
						//newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());
						//newBlock.setStartingSectionID(bBlock.getStartingSectionID());
						
						subBlockList = new ArrayList<Integer>();
						subBlockIterator = bBlock.getSubBlockIterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							subBlockList.add(subBlockID);
						}
						
						// Temporarily remove the sub-blocks in the current block.
						subBlockIterator = subBlockList.iterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
							bBlock.removeSubBlock(existingSubBlock);
						}
						
						// Re-add the new block plus the remaining sub-blocks 
						// to the current block.
						bBlock.reAddSubBlock(newBlock);
						newBlock.setParentBlock(bBlock);
						subBlockIterator = subBlockList.iterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
							bBlock.reAddSubBlock(existingSubBlock);
						}
						
						if (_debugProcessConditionalBlock == true)
						{
							System.out.println("    JCBlock processConditionalBlock() computing SCB conditional solution");
						}
						newBlock.computeConditionalSolution(prevSectionBlock, conditionalBlock, rightBlock, _pcmMatrix);
						
						// If there is only one block left, copy the solution to the current block.
						solutionMap = bBlock.getSolutionMap();
						if ((bBlock.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
						{
							solutionMap = newBlock.getSolutionMap();
							if (solutionMap.size() > 0)
							{
								bBlock.setSolutionMap(solutionMap);
	                            if (_debugBlockCostMap == true)
	                            {
	                            	System.out.println("    JCBlock: processConditionalBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
	                            	solutionIterator = solutionMap.iterator();
	        		    			while (solutionIterator.hasNext() == true) 
	        		    			{
	        		    				existingCostSolution = solutionIterator.next();
	        		    				existingCostKey = existingCostSolution.getSolutionKey();
	                                	System.out.println("    JCBlock: processConditionalBlock: costmap key " + existingCostKey);
	                                	System.out.println("    JCBlock: processConditionalBlock: costmap solution " + existingCostSolution.getSolutionCost());
	        		    			}
	                            }
							}
						}
					}
					else if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
						 	 (subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
							 (subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))

					{
						leftBlock = (JCBlock)subBlock;
						
						// Create a containing block for the combination of section conditional section objects.
						bBlock.removeSubBlock(rightBlock);
						bBlock.removeSubBlock(conditionalBlock);
						bBlock.removeSubBlock(leftBlock);
						
						newBlock = new JCBlock();
						newBlock.reAddSubBlock(leftBlock);
						leftBlock.setParentBlock(newBlock);
						newBlock.reAddSubBlock(conditionalBlock);
						conditionalBlock.setParentBlock(newBlock);
						newBlock.reAddSubBlock(rightBlock);
						rightBlock.setParentBlock(newBlock);
						newBlock.setStartingSectionID(leftBlock.getStartingSectionID());
						newBlock.setEndingSectionID(rightBlock.getEndingSectionID());					
						//newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());					
						//newBlock.setStartingSectionID(bBlock.getStartingSectionID());
						
						subBlockList = new ArrayList<Integer>();
						subBlockIterator = bBlock.getSubBlockIterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							subBlockList.add(subBlockID);
						}
						
						// Temporarily remove the sub-blocks in the current block.
						subBlockIterator = subBlockList.iterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
							bBlock.removeSubBlock(existingSubBlock);
						}
						
						// Re-add the new block plus the remaining sub-blocks 
						// to the current block.
						bBlock.reAddSubBlock(newBlock);
						newBlock.setParentBlock(bBlock);
						subBlockIterator = subBlockList.iterator();
						while (subBlockIterator.hasNext() == true)
						{
							subBlockID = subBlockIterator.next();
							existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
							bBlock.reAddSubBlock(existingSubBlock);
						}
						
						if (_debugProcessConditionalBlock == true)
						{
							System.out.println("    JCBlock processConditionalBlock() computing BCB conditional solution");
						}
						newBlock.computeConditionalSolution(leftBlock, conditionalBlock, rightBlock, _pcmMatrix);
						
						// If there is only one block left, copy the solution to the current block.
						solutionMap = bBlock.getSolutionMap();
						if ((bBlock.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
						{
							solutionMap = newBlock.getSolutionMap();
							if (solutionMap.size() > 0)
							{
								bBlock.setSolutionMap(solutionMap);
	                            if (_debugBlockCostMap == true)
	                            {
	                            	System.out.println("    JCBlock: processConditionalBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
	                            	solutionIterator = solutionMap.iterator();
	        		    			while (solutionIterator.hasNext() == true) 
	        		    			{
	        		    				existingCostSolution = solutionIterator.next();
	        		    				existingCostKey = existingCostSolution.getSolutionKey();
	                                	System.out.println("    JCBlock: processConditionalBlock: costmap key " + existingCostKey);
	                                	System.out.println("    JCBlock: processConditionalBlock: costmap solution " + existingCostSolution.getSolutionCost());
	        		    			}
	                            }
							}
						}
					}
				}
				else if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
						 (subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
						 (subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))

				{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of section conditional section objects.
					bBlock.removeSubBlock(rightBlock);
					bBlock.removeSubBlock(leftBlock);
					
					newBlock = new JCBlock();
					newBlock.reAddSubBlock(leftBlock);
					leftBlock.setParentBlock(newBlock);
					newBlock.reAddSubBlock(rightBlock);
					rightBlock.setParentBlock(newBlock);
					newBlock.setStartingSectionID(leftBlock.getStartingSectionID());
					newBlock.setEndingSectionID(rightBlock.getEndingSectionID());					
					//newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());					
					//newBlock.setStartingSectionID(bBlock.getStartingSectionID());
					
					subBlockList = new ArrayList<Integer>();
					subBlockIterator = bBlock.getSubBlockIterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						subBlockList.add(subBlockID);
					}
					
					// Temporarily remove the sub-blocks in the current block.
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.removeSubBlock(existingSubBlock);
					}
					
					// Re-add the new block plus the remaining sub-blocks 
					// to the current block.
					bBlock.reAddSubBlock(newBlock);
					newBlock.setParentBlock(bBlock);
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.reAddSubBlock(existingSubBlock);
					}
					
					if (_debugProcessBlock == true)
					{
						System.out.println("    JCBlock processConditionalBlock() computing BB blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					solutionMap = bBlock.getSolutionMap();
					if ((bBlock.numberOfSubBlocks() == 1) && ((solutionMap == null) || (solutionMap.size() == 0)))
					{
						solutionMap = newBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							bBlock.setSolutionMap(solutionMap);
                            if (_debugBlockCostMap == true)
                            {
                            	System.out.println("    JCBlock: processConditionalBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
                            	solutionIterator = solutionMap.iterator();
        		    			while (solutionIterator.hasNext() == true) 
        		    			{
        		    				existingCostSolution = solutionIterator.next();
        		    				existingCostKey = existingCostSolution.getSolutionKey();
                                	System.out.println("    JCBlock: processConditionalBlock: costmap key " + existingCostKey);
                                	System.out.println("    JCBlock: processConditionalBlock: costmap solution " + existingCostSolution.getSolutionCost());
        		    			}
                            }
						}
					}
				}
			}
	    }
	    else if (indexOfSubBlock == 1)
	    {
	    	newSectionSBID = indexOfSubBlock;
	    	subBlock = bBlock.getSubBlockAtIndex(newSectionSBID);
	    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
			{
				rightBlock = (JCBlock)subBlock;
		    	blockID = (newSectionSBID-1);
		    	subBlock = bBlock.getSubBlockAtIndex(blockID);
		    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
					(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
				{
					leftBlock = (JCBlock)subBlock;
					
					// Create a containing block for the combination of section conditional section objects.
					bBlock.removeSubBlock(rightBlock);
					bBlock.removeSubBlock(leftBlock);
					
					newBlock = new JCBlock();
					newBlock.reAddSubBlock(leftBlock);
					leftBlock.setParentBlock(newBlock);
					newBlock.reAddSubBlock(rightBlock);
					rightBlock.setParentBlock(newBlock);
					newBlock.setStartingSectionID(leftBlock.getStartingSectionID());
					newBlock.setEndingSectionID(rightBlock.getEndingSectionID());					
					//newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());					
					//newBlock.setStartingSectionID(bBlock.getStartingSectionID());
					
					subBlockList = new ArrayList<Integer>();
					subBlockIterator = bBlock.getSubBlockIterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						subBlockList.add(subBlockID);
					}
					
					// Temporarily remove the sub-blocks in the current block.
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.removeSubBlock(existingSubBlock);
					}
					
					// Re-add the new block plus the remaining sub-blocks 
					// to the current block.
					bBlock.reAddSubBlock(newBlock);
					newBlock.setParentBlock(bBlock);
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.reAddSubBlock(existingSubBlock);
					}
					
					if (_debugProcessBlock == true)
					{
						System.out.println("    JCBlock processConditionalBlock() computing BB blocks solution");
					}
					newBlock.computeBlocksSolution(leftBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					solutionMap = bBlock.getSolutionMap();
					if ((bBlock.numberOfSubBlocks() == 1) && ((solutionMap == null) || (solutionMap.size() == 0)))
					{
						solutionMap = newBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							bBlock.setSolutionMap(solutionMap);
                            if (_debugBlockCostMap == true)
                            {
                            	System.out.println("    JCBlock: processConditionalBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
                            	solutionIterator = solutionMap.iterator();
        		    			while (solutionIterator.hasNext() == true) 
        		    			{
        		    				existingCostSolution = solutionIterator.next();
        		    				existingCostKey = existingCostSolution.getSolutionKey();
                                	System.out.println("    JCBlock: processConditionalBlock: costmap key " + existingCostKey);
                                	System.out.println("    JCBlock: processConditionalBlock: costmap solution " + existingCostSolution.getSolutionCost());
        		    			}
                            }
						}
					}
				}
			    else if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
				{
					prevSectionBlock = (JCSectionBlock)subBlock;

					// Create a containing block for the combination of section conditional section objects.
					bBlock.removeSubBlock(rightBlock);
					bBlock.removeSubBlock(prevSectionBlock);
					
					newBlock = new JCBlock();
					newBlock.reAddSubBlock(prevSectionBlock);
					prevSectionBlock.setParentBlock(newBlock);
					newBlock.reAddSubBlock(rightBlock);
					rightBlock.setParentBlock(newBlock);
					newBlock.setStartingSectionID(prevSectionBlock.getSectionBlockID());
					newBlock.setEndingSectionID(rightBlock.getEndingSectionID());
					//newBlock.setEndingSectionID(sectionBlock.getSectionBlockID());
					//newBlock.setStartingSectionID(bBlock.getStartingSectionID());
					
					subBlockList = new ArrayList<Integer>();
					subBlockIterator = bBlock.getSubBlockIterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						subBlockList.add(subBlockID);
					}
					
					// Temporarily remove the sub-blocks in the current block.
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.removeSubBlock(existingSubBlock);
					}
					
					// Re-add the new block plus the remaining sub-blocks 
					// to the current block.
					bBlock.reAddSubBlock(newBlock);
					newBlock.setParentBlock(bBlock);
					subBlockIterator = subBlockList.iterator();
					while (subBlockIterator.hasNext() == true)
					{
						subBlockID = subBlockIterator.next();
						existingSubBlock = JCSubBlock.getSubBlock(subBlockID);
						bBlock.reAddSubBlock(existingSubBlock);
					}
					
					if (_debugProcessBlock == true)
					{
						System.out.println("    JCBlock processConditionalBlock() computing SB blocks solution");
					}
					newBlock.computeBlocksSolution(prevSectionBlock, rightBlock, _pcmMatrix);
					
					// If there is only one block left, copy the solution to the current block.
					solutionMap = bBlock.getSolutionMap();
					if ((bBlock.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
					{
						solutionMap = newBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							bBlock.setSolutionMap(solutionMap);
                            if (_debugBlockCostMap == true)
                            {
                            	System.out.println("    JCBlock: processConditionalBlock: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
                            	solutionIterator = solutionMap.iterator();
        		    			while (solutionIterator.hasNext() == true) 
        		    			{
        		    				existingCostSolution = solutionIterator.next();
        		    				existingCostKey = existingCostSolution.getSolutionKey();
                                	System.out.println("    JCBlock: processConditionalBlock: costmap key " + existingCostKey);
                                	System.out.println("    JCBlock: processConditionalBlock: costmap solution " + existingCostSolution.getSolutionCost());
        		    			}
                            }
						}
					}
				}
			}
	    }
	    else if ((indexOfSubBlock == 0) && (numberOfSubBlocks == 1))
	    {
			// If there is only one block left, copy the solution to the current block.
			solutionMap = bBlock.getSolutionMap();
			if ((bBlock.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
			{
				solutionMap = parseBlock.getSolutionMap();
				if (solutionMap.size() > 0)
				{
					bBlock.setSolutionMap(solutionMap);
                	System.out.println("    JCBlock: processConditionalBlock: costmap from " + parseBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
                    if (_debugBlockCostMap == true)
                    {
                    	System.out.println("    JCBlock: processConditionalBlock: costmap from " + parseBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
                    	solutionIterator = solutionMap.iterator();
		    			while (solutionIterator.hasNext() == true) 
		    			{
		    				existingCostSolution = solutionIterator.next();
		    				existingCostKey = existingCostSolution.getSolutionKey();
                        	System.out.println("    JCBlock: processConditionalBlock: costmap key " + existingCostKey);
                        	System.out.println("    JCBlock: processConditionalBlock: costmap solution " + existingCostSolution.getSolutionCost());
		    			}
                    }
				}
			}
	    }
	    
	    return bBlock;
	}
	
	/**
	 * References the block object comprising a linear sequence 
	 * of sub-blocks located at the current parsing location 
	 * for the current control flow graph. 
	 *
	 * @return bBlock  the block object at this parse location
	 * 
	 * @see            JCBlock
	 * @see            JCSelectOptimalPP
	 */
	public JCBlock accessBlock()
	{
		JCBlock bBlock;
		
		bBlock = JCBlock.getBlock(_blockCount);
		if (_debugDisplayBlocks == true)
		{
	        System.out.println("    Block " + "(" + bBlock.getBlockID() + ") (" + bBlock.getSubBlockID() + ") accessed");
		}
	    _blockCount++;
		
	    return bBlock;
	}
	
	/**
	 * Marks the current block as the program block object representing the 
	 * overall preemption point placement solution. 
	 *
	 * @see            JCBlock
	 * @see            JCSelectOptimalPP
	 */
	public void markProgramBlock()
	{
		JCBlock blockObj = null;
		JCBlock bBlock = _blockStack.peek();
		
		if (bBlock.getObjectTypeName().equals("JCBlock") == true)
		{
			blockObj = (JCBlock)bBlock;
			if (_debugDisplayBlocks == true)
			{
		        System.out.println("    Block " + "(" + blockObj.getBlockID() + ") (" + blockObj.getSubBlockID() + ") set as the program block");
			}
			setProgramBlock(blockObj);
		}
	}
	
	/**
	 * Represents the completion of parsing for the entire program
	 * blocks for the current control flow graph.  Used to reset
	 * object access counters supporting the second parsing pass.
	 *
	 * @see                      JCSelectOptimalPP
	 */
	public void programProduction()
	{
	    // Reset the global counts to 0, to let the objects to start from the very beginning.
        _basicBlockCount = 0;
	    _blockCount = 0;
	    _sectionCount = 0;
        _conditionalSectionCount = 0;
        _conditionalBlockCount = 0;
        _loopBlockCount = 0;
        _functionBlockCount = 0;
        _functionCallBlockCount = 0;
	}
	
	/**
	 * Displays the computed preemption points solution.
	 *
	 * @see       JCSelectOptimalPP
	 */
	public void displayProgramSolution()
	{
		JCBlock bBlock;
		JCCostKey existingCostKey;
		JCCostSolution existingCostSolution;
		JCCostSolution minimumCostSolution;
		JCCostKey minimumCostKey;
		JCPreemptionPoints minimumCostPreemptionPoints;
		Iterator<JCPreemptionPoints> minimumPreemptionPointsIterator;
		JCBlock newBlock;
		Iterator<JCCostSolution> solutionIterator;
		ArrayList<JCCostSolution> solutionMap;
		JCSubBlock subBlock;
		
		bBlock = this.getProgramBlock();
		
		// If there is only one block left, copy the solution to the current block.
		solutionMap = bBlock.getSolutionMap();
		if ((bBlock.numberOfSubBlocks() == 1) && ((solutionMap == null) || (solutionMap.size() == 0)))
		{
			subBlock = bBlock.getSubBlockAtIndex(0);
	    	if ((subBlock.getObjectTypeName().equals("JCBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCLoopBlock") == true) ||
				(subBlock.getObjectTypeName().equals("JCFunctionCallBlock") == true))
			{
				newBlock = (JCBlock) subBlock;
				solutionMap = newBlock.getSolutionMap();
				if (solutionMap.size() > 0)
				{
					bBlock.setSolutionMap(solutionMap);
	                if (_debugBlockCostMap == true)
	                {
	                	System.out.println("    JCSelectOptimalPP: costmap from " + newBlock.getBlockName() + " stored into block " + bBlock.getBlockName() + " size = " + solutionMap.size());
	                	solutionIterator = solutionMap.iterator();
		    			while (solutionIterator.hasNext() == true) 
		    			{
		    				existingCostSolution = solutionIterator.next();
		    				existingCostKey = existingCostSolution.getSolutionKey();
		    				if (existingCostKey.getLeftIndex() > 0)
		    				{
		    					existingCostKey.setLeftIndex(0);
		    				}
		    				
		    				if (existingCostKey.getRightIndex() > 0)
		    				{
		    					existingCostKey.setRightIndex(0);
		    				}
		    				
	                    	System.out.println("    JCSelectOptimalPP: costmap key " + existingCostKey);
	                    	System.out.println("    JCSelectOptimalPP: costmap solution " + existingCostSolution.getSolutionCost());
		    			}
	                }
	                newBlock.deleteCostSolution();
				}
			}
		}

		minimumCostKey = new JCCostKey(0, 0);
		minimumCostKey.setLeftBasicBlock((JCBasicBlock.getStartingBasicBlock().getBBlockID()));
		minimumCostKey.setRightBasicBlock((JCBasicBlock.getEndingBasicBlock().getBBlockID()));
		minimumCostSolution = bBlock.getCostSolution(minimumCostKey);
		if (minimumCostSolution != null)
		{
		    if (_debugDisplaySolutionPreemptionCost == true)
		    {
	            System.out.println("Minimum cost solution is " + minimumCostSolution.getSolutionCost());
		    }
		    
            if (_debugDisplaySolutionPreemptionPoints == true)
            {
                System.out.print("Preemption points ");
                minimumPreemptionPointsIterator = minimumCostSolution.getPreemptionPointSolutionsIterator();
                while (minimumPreemptionPointsIterator.hasNext() == true)
                {
                    minimumCostPreemptionPoints = minimumPreemptionPointsIterator.next();
                    minimumCostPreemptionPoints.displayObjectInformation();
                    System.out.print(" ");
                }
                System.out.println();
            }
             
            if (_debugOutputSolutionCostToFile == true)
            {
                try
                {
                    PrintWriter costWriter = new PrintWriter(getCostFileName(), "UTF-8");
                    costWriter.println(minimumCostSolution.getSolutionCost());
                    costWriter.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
            
            if (_debugOutputSolutionPreemptionPointsToFile == true)
            {
                try
                {
                    PrintWriter ppWriter = new PrintWriter(getPPFileName(), "UTF-8");
                    minimumPreemptionPointsIterator = minimumCostSolution.getPreemptionPointSolutionsIterator();
                    while (minimumPreemptionPointsIterator.hasNext() == true)
                    {
                        minimumCostPreemptionPoints = minimumPreemptionPointsIterator.next();
                        minimumCostPreemptionPoints.displayObjectInformation(ppWriter);
                        ppWriter.print(" ");
                    }
                    ppWriter.println();
                    ppWriter.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }         
		}
		else
		{
            if (_debugDisplaySolutionPreemptionCost == true)
            {
                System.out.println("Unable to find minimum cost solution");
            }
            
			if (_debugDumpPreemptionPointObjects == true)
			{
		       	System.out.println();
		       	JCBasicBlock.displayAllObjects();
		       	JCSectionBlock.displayAllObjects();
		       	JCConditionalSection.displayAllObjects();
		       	JCConditionalBlock.displayAllObjects();
		       	JCLoopBlock.displayAllObjects();
		       	JCFunctionBlock.displayAllObjects();
		       	JCFunctionCallBlock.displayAllObjects();
		       	JCSubBlock.displayAllObjects();
		       	JCBlock.displayAllObjects();
			}
			
            if (_debugOutputSolutionCostToFile == true)
            {
                try
                {
                    PrintWriter costWriter = new PrintWriter(getCostFileName(), "UTF-8");
                    costWriter.println(Long.MAX_VALUE);
                    costWriter.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
            
            if (_debugOutputSolutionPreemptionPointsToFile == true)
            {
                try
                {
                    PrintWriter ppWriter = new PrintWriter(getPPFileName(), "UTF-8");
                    ppWriter.println("No PP solution found");
                    ppWriter.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }         
		}
       	
		if (_debugDisplayPreemptionPointObjects == true)
		{
	       	System.out.println();
	       	JCBasicBlock.displayAllObjects();
	       	JCSectionBlock.displayAllObjects();
	       	JCConditionalSection.displayAllObjects();
	       	JCConditionalBlock.displayAllObjects();
	       	JCLoopBlock.displayAllObjects();
	       	JCBlock.displayAllObjects();
		}
		
    	_endTime = System.nanoTime();
    	_elapsedTime = _endTime - _startTime;
		if (_debugDisplayExecutionTime == true)
		{
	    	System.out.println("Computation time is " + (_elapsedTime/1000000000l) + " seconds " + (_elapsedTime/1000000l) + " milliseconds " + (_elapsedTime/1000l) + " microseconds");
		}
    }
	
	/**
	 * Returns the computed minimum preemption points solution cost.
	 *
	 * @return    minimumCost - computed minimum cost solution
	 *
	 * @see       JCSelectOptimalPP
	 */
	public Integer getMinimumCostSolution()
	{
		JCBlock bBlock;
		JCCostSolution minimumCostSolution;
		Integer minimumCost = -1;
		JCCostKey minimumCostKey;
		
		bBlock = JCBlock.getBlock(0);
		minimumCostKey = new JCCostKey(0, 0);
		minimumCostKey.setLeftBasicBlock((JCBasicBlock.getStartingBasicBlock().getBBlockID()));
		minimumCostKey.setRightBasicBlock((JCBasicBlock.getEndingBasicBlock().getBBlockID()));
		minimumCostSolution = bBlock.getCostSolution(minimumCostKey);
		if (minimumCostSolution != null)
		{
			minimumCost = minimumCostSolution.getSolutionCost();
		}
		return minimumCost;
	}
	
	/**
	 * Returns the computed minimum preemption points solution cost.
	 *
	 * @return    minimumCost - computed minimum cost solution
	 *
	 * @see       JCSelectOptimalPP
	 */
	public boolean hasMinimumCostSolution()
	{
		JCBlock bBlock;
		boolean hasMinimumCost = false;
		JCCostSolution minimumCostSolution;
		JCCostKey minimumCostKey;
		
		bBlock = JCBlock.getBlock(0);
		minimumCostKey = new JCCostKey(0, 0);
		minimumCostKey.setLeftBasicBlock((JCBasicBlock.getStartingBasicBlock().getBBlockID()));
		minimumCostKey.setRightBasicBlock((JCBasicBlock.getEndingBasicBlock().getBBlockID()));
		minimumCostSolution = bBlock.getCostSolution(minimumCostKey);
		if (minimumCostSolution != null)
		{
			hasMinimumCost = true;
		}
		return hasMinimumCost;
	}
	
	/**
	 * Initializes the preemption cost matrix object and the 
	 * associated row and column indices.
	 *
	 * @see                      JCSelectOptimalPP
	 */
	public void startPreemptionCostMatrix()
	{
		int pcMatrixID;
		
    	_pcmRowIndex = 0;
    	_pcmColumnIndex = 0;
    	_pcmMatrix = new JCPreemptionCostMatrix();
    	pcMatrixID = _pcmMatrix.getPCMatrixID();
    	JCPreemptionCostMatrix.setPreemptionCostMatrix(pcMatrixID, _pcmMatrix);
	}
	
	/**
	 * Increments the preemption cost matrix row index and resets the 
	 * column indices.
	 *
	 * @see                      JCSelectOptimalPP
	 */
	public void nextPreemptionCostMatrixRow()
	{
    	_pcmRowIndex++;
    	_pcmColumnIndex = 0;
	}
	
	/**
	 * Stores the current preemption cost matrix cost entry and
	 * increments the column index.
	 *
	 * @see                      JCSelectOptimalPP
	 */
	public void storePreemptionCostValue(int costValue)
	{
		int brt;
		int matrixValue;
		
		brt = this.getBRTValue();
		matrixValue = costValue * brt;
		_pcmMatrix.setMatrixEntry(_pcmRowIndex, _pcmColumnIndex, matrixValue);
		_pcmColumnIndex++;
	}
	
	/**
	 * Stores the current preemption cost matrix cost ID entry.
	 *
	 * @see                      JCSelectOptimalPP
	 */
	public void storePreemptionCostID(int bBlockID)
	{
		Integer storedBBlockID = new Integer(bBlockID);
		
		_pcmMatrix.addBasicBlockID(storedBBlockID);
	}
	
	/**
	 * Displays the current preemption cost matrix cost entries.
	 *
	 * @see                      JCSelectOptimalPP
	 */
	public void displayPreemptionCostMatrix()
	{
		_pcmMatrix.displayObjectInformation();
	}

	/**
	 * Creates the various cost matrices for the preemption point placement 
	 * algorithm.
	 *
	 * @see                      JCBCostMatrix
	 * @see                      JCCNPCostMatrix
	 * @see                      JCPreemptionPointsMatrix
	 * @see                      JCQCostMatrix
	 * @see                      JCSelectOptimalPP
	 */
	public void createCostMatrices()
	{
		//int bMatrixID;
		//int cnpMatrixID;
		//int pbMatrixID;
		//int qMatrixID;
		
		//_cnpMatrix = new JCCNPCostMatrix(this._n);
    	//cnpMatrixID = _cnpMatrix.getCNPMatrixID();
    	//JCCNPCostMatrix.setCNPCostMatrix(cnpMatrixID, _cnpMatrix);
    	
    	//_qMatrix = new JCQCostMatrix(this._n);
    	//qMatrixID = _qMatrix.getQMatrixID();
    	//JCQCostMatrix.setQCostMatrix(qMatrixID, _qMatrix);
    	
    	//_bMatrix = new JCBCostMatrix(this._n);
    	//bMatrixID = _bMatrix.getBMatrixID();
    	//JCBCostMatrix.setBCostMatrix(bMatrixID, _bMatrix);
    	
    	//_pbMatrix = new JCPreemptionPointsMatrix(this._n);
    	//pbMatrixID = _pbMatrix.getPPMatrixID();
    	//JCPreemptionPointsMatrix.setPreemptionPointsMatrix(pbMatrixID, _pbMatrix);
	}
	
	/**
	 * Computes the preemption point costs for the specified section. 
	 *
	 * @param sectionBlock       the section block object to compute preemption costs for
	 * 
	 * @see                      JCBCostMatrix
	 * @see                      JCCNPCostMatrix
	 * @see                      JCPreemptionPointsMatrix
	 * @see                      JCQCostMatrix
	 * @see                      JCSectionBlock
	 * @see                      JCSelectOptimalPP
	 */
	public void computeSectionCosts(JCSectionBlock sectionBlock)
	{
		JCBasicBlock bbj;
		int bbjID;
		JCBasicBlock bbk;
		int bbkID;
		JCBasicBlock bbm;
		int bbmID;
		JCBasicBlock bbpk;
		int bbpkID;
		int j;
		int k;
		int m;
		int ni;
		int pcost;
		int s;
		
		System.out.println("Computing preemption costs for section " + sectionBlock.getSectionBlockName());
		ni = (int)sectionBlock.numberOfBasicBlocks();
		
		for (s = 1; s < ni; s++)
		{
		    for (j = 0; j < ni-s; j++)
			{
			    bbj = sectionBlock.getBasicBlockAtIndex(j);
			    bbjID = bbj.getBBlockID();
			    k = j + s;
			    bbk = sectionBlock.getBasicBlockAtIndex(k);
			    bbkID = bbk.getBBlockID();
			    bbpk = sectionBlock.getBasicBlockAtIndex(k-1);
			    bbpkID = bbpk.getBBlockID();
			    // npCostMatrix[j][k] = npCostMatrix[j][k-1] + blockCycles[k]
			    _cnpMatrix.setMatrixEntry(bbjID, bbkID, (_cnpMatrix.getMatrixEntry(bbjID, bbpkID) + bbk.getBBlockWCET()));
			    // qCostMatrix[j][k] = npCostMatrix[j][k] + pCostMatrix[j][k];
			    _qMatrix.setMatrixEntry(bbjID, bbkID, (_cnpMatrix.getMatrixEntry(bbjID, bbkID) + _pcmMatrix.getMatrixEntry(bbjID, bbkID)));

                // if (qCostMatrix[j][k] < maxTaskQI)
			    if (_qMatrix.getMatrixEntry(bbjID, bbkID) <= this._q)
			    {
  			        // bCostMatrix[j][k] = qCostMatrix[j][k];
			    	_bMatrix.setMatrixEntry(bbjID, bbkID, _qMatrix.getMatrixEntry(bbjID, bbkID));
			    	_pbMatrix.addPreemptionPoint(bbjID, bbkID, bbjID);
			    	_pbMatrix.addPreemptionPoint(bbjID, bbkID, bbkID);
			    }
			    else
				{
		            // bCostMatrix[j][k] = LC;
			    	_bMatrix.setMatrixEntry(bbjID, bbkID, JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE);
			    }
			    
				for (m = j + 1; m < k; m++)
				{
				    bbm = sectionBlock.getBasicBlockAtIndex(m);
				    bbmID = bbm.getBBlockID();
		            //if ((q[j][m] <= QI) && (q[m][k] <= QI))
				    if ((_qMatrix.getMatrixEntry(bbjID, bbmID) <= this._q) &&
				    	(_qMatrix.getMatrixEntry(bbmID, bbkID) <= this._q))
				    {
					    pcost = _qMatrix.getMatrixEntry(bbjID, bbmID) + _qMatrix.getMatrixEntry(bbmID, bbkID);
						//if (pcost <=  b[j][k])
					    if (pcost <= _bMatrix.getMatrixEntry(bbjID, bbkID))
						{
				            // b[j][k] = pcost;
				            _bMatrix.setMatrixEntry(bbjID, bbkID, pcost);
					        // pb[j][k] = pb[j][m]+pb[m][k];
				            _pbMatrix.combineSectionPreemptionPoints(bbjID, bbkID, bbmID);
						}
					}
				}
	 		}
		}
	    this.displayCostMatrices();
	}
	
	/**
	 * Computes the preemption point costs for the specified adjacent sections. 
	 *
	 * @param predSectionBlock   the predecessor (left) block object to compute preemption costs for
	 * 
	 * @param succSectionBlock   the successor (right) block object to compute preemption costs for
	 * 
	 * @see                      JCBasicBlock
	 * @see                      JCBCostMatrix
	 * @see                      JCCNPCostMatrix
	 * @see                      JCPreemptionPointsMatrix
	 * @see                      JCQCostMatrix
	 * @see                      JCSectionBlock
	 * @see                      JCSelectOptimalPP
	 */
	public void computeAdjacentSectionCosts(JCSectionBlock predSectionBlock, JCSectionBlock succSectionBlock)
	{
		int bbLeftID;
		int bbLeftLastID;
		int bbMiddleID;
		int bbRightID;
		int bbRightFirstID;
		int lCount;
		JCBasicBlock leftBB;
		JCBasicBlock leftLastBB;
		int lidx;
		int mBBIndex;
		int mCount;
		JCBasicBlock middleBB;
		int midx;
		int pcost;
		int rCount;
		int ridx;
		JCBasicBlock rightBB;
		JCBasicBlock rightFirstBB;
		
		System.out.println("Computing preemption costs for adjacent sections " + predSectionBlock.getSectionBlockName() + " and " + succSectionBlock.getSectionBlockName());
		rightFirstBB = succSectionBlock.getLeftMostBasicBlock();
		bbRightFirstID = rightFirstBB.getBBlockID();
		leftLastBB = predSectionBlock.getRightMostBasicBlock();
		bbLeftLastID = leftLastBB.getBBlockID();
		
		for (lidx = (int)(predSectionBlock.numberOfBasicBlocks()-1); lidx >= 0; lidx--)
		{
			leftBB = predSectionBlock.getBasicBlockAtIndex(lidx);
			bbLeftID = leftBB.getBBlockID();
			for (ridx = 0; ridx < (int)(succSectionBlock.numberOfBasicBlocks()); ridx++)
			{
				rightBB = succSectionBlock.getBasicBlockAtIndex(ridx);
				bbRightID = rightBB.getBBlockID();
				
				if ((bbLeftLastID != bbLeftID) || (bbRightFirstID != bbRightID))
				{
					_cnpMatrix.setMatrixEntry(bbLeftID, bbRightID, (_cnpMatrix.getMatrixEntry(bbLeftID,bbLeftLastID) +
                                                                    _cnpMatrix.getMatrixEntry(bbLeftLastID, bbRightFirstID) +
                                                                    _cnpMatrix.getMatrixEntry(bbRightFirstID,bbRightID)));
				}
				else
				{
					_cnpMatrix.setMatrixEntry(bbLeftID, bbRightID, rightBB.getBBlockWCET());
				}
			    _qMatrix.setMatrixEntry(bbLeftID, bbRightID, (_cnpMatrix.getMatrixEntry(bbLeftID, bbRightID) + _pcmMatrix.getMatrixEntry(bbLeftID, bbRightID)));

			    if (_qMatrix.getMatrixEntry(bbLeftID, bbRightID) <= this._q)
			    {
			    	_bMatrix.setMatrixEntry(bbLeftID, bbRightID, _qMatrix.getMatrixEntry(bbLeftID, bbRightID));
			    	_pbMatrix.addPreemptionPoint(bbLeftID, bbRightID, bbLeftID);
			    	_pbMatrix.addPreemptionPoint(bbLeftID, bbRightID, bbRightID);
			    }
			    else
				{
			    	_bMatrix.setMatrixEntry(bbLeftID, bbRightID, JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE);
			    }
			    
			    lCount = (int) (predSectionBlock.numberOfBasicBlocks()-1) - lidx;
			    rCount = ridx;
			    mCount = (lCount + rCount);
			    if (mCount > 0)
			    {
					for (midx = 0; midx < mCount; midx++)
					{
						if (midx < lCount)
						{
							mBBIndex = lidx + 1 + midx;
							middleBB = predSectionBlock.getBasicBlockAtIndex(mBBIndex);
						}
						else
						{
							mBBIndex = midx - lCount;
							middleBB = succSectionBlock.getBasicBlockAtIndex(mBBIndex);
						}
						bbMiddleID = middleBB.getBBlockID();
					    
						if ((_bMatrix.getMatrixEntry(bbLeftID, bbMiddleID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE) &&
							(_bMatrix.getMatrixEntry(bbMiddleID, bbRightID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE))
						{
							pcost = _bMatrix.getMatrixEntry(bbLeftID, bbMiddleID) + _bMatrix.getMatrixEntry(bbMiddleID, bbRightID);
						    if (pcost < _bMatrix.getMatrixEntry(bbLeftID, bbRightID))
						    {
						    	_bMatrix.setMatrixEntry(bbLeftID, bbRightID, pcost);
					            _pbMatrix.combineSectionPreemptionPoints(bbLeftID, bbRightID, bbMiddleID);
						    }
					    }
					}
				}
			}
		}
	    this.displayCostMatrices();
	}
	
	/**
	 * Computes the preemption point costs for the specified adjacent sections. 
	 *
	 * @param predSectionBlock   the predecessor (left) block object to compute preemption costs for
	 * 
	 * @param conditionalBlock   the conditional (center) block object to compute preemption costs for
	 * 
	 * @param succSectionBlock   the successor (right) block object to compute preemption costs for
	 * 
	 * @param leftIndex          the index of the predecessor (left) matrix entry to compute preemption costs for
	 * 
	 * @param rightIndex         the index of the successor (right) matrix entry to compute preemption costs for
	 * 
	 * @see                      JCBasicBlock
	 * @see                      JCConditionalBlock
	 * @see                      JCBCostMatrix
	 * @see                      JCCNPCostMatrix
	 * @see                      JCPreemptionPointsMatrix
	 * @see                      JCQCostMatrix
	 * @see                      JCSectionBlock
	 * @see                      JCSelectOptimalPP
	 */
	public void computeConditionalCostEntry(JCSectionBlock predSectionBlock, JCConditionalBlock conditionalBlock, JCSectionBlock succSectionBlock,
			                                int leftIndex, int rightIndex)
	{
		final boolean _debugSetBValues = false;
		final boolean _debugStoreBValues = false;
		
		int bbCondLeftID;
		int bbCondRightID;
		int bbLeftID;
		int bbLeftLastID;
		int bbMiddleLeftID;
		int bbMiddleRightID;
		int bbRightFirstID;
		int bbRightID;
		int bValue;
		int bSValue;
		int clidx;
		int cnpValue;
		JCConditionalSection conditionalSection;
		JCBasicBlock condLeftBB;
		JCBasicBlock condRightBB;
		JCSectionBlock condSectionBlock;
		int cridx;
		int csidx;
		JCPreemptionPoints ePbValue;
		JCBasicBlock leftBB;
		JCBasicBlock leftLastBB;
		int lCount;
		int lidx;
		int mBBIndex;
		JCBasicBlock middleLeftBB;
		JCBasicBlock middleRightBB;
		int mlidx;
		int mridx;
		int offset;
		JCPreemptionPoints pbValue = null;
		JCPreemptionPoints pbSValue = null;
		int pcost;
		int qValue;
		int rCount;
		int ridx;
		JCBasicBlock rightBB;
		JCBasicBlock rightFirstBB;
		int sbidx;
		JCSubBlock subBlock;

		rightFirstBB = succSectionBlock.getLeftMostBasicBlock();
		bbRightFirstID = rightFirstBB.getBBlockID();
		leftLastBB = predSectionBlock.getRightMostBasicBlock();
		bbLeftLastID = leftLastBB.getBBlockID();

		leftBB = predSectionBlock.getBasicBlockAtIndex(leftIndex);
		bbLeftID = leftBB.getBBlockID();
		rightBB = succSectionBlock.getBasicBlockAtIndex(rightIndex);
		bbRightID = rightBB.getBBlockID();

		bValue = JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE;
        pbValue = new JCPreemptionPoints(false);
		
		for (csidx = 0; csidx < (int)(conditionalBlock.numberOfConditionalSections()); csidx++)
		{
			conditionalSection = conditionalBlock.getConditionalSectionAtIndex(csidx);
			for (sbidx = 0; sbidx < (int)(conditionalSection.numberOfSubBlocks()); sbidx++)
			{
				subBlock = conditionalSection.getSubBlockAtIndex(sbidx);
				if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
				{
					condSectionBlock = (JCSectionBlock)subBlock;
		        	if (_debugSetBValues == true)
		        	{
						System.out.println("Processing conditional section block " + condSectionBlock.getSectionBlockName() + " " + (int)condSectionBlock.numberOfBasicBlocks() + " basic blocks");
		        	}
		        	
					clidx = 0;
					condLeftBB = condSectionBlock.getBasicBlockAtIndex(clidx);
					bbCondLeftID = condLeftBB.getBBlockID();
					cridx = (int)(condSectionBlock.numberOfBasicBlocks()-1);
					condRightBB = condSectionBlock.getBasicBlockAtIndex(cridx);
					bbCondRightID = condRightBB.getBBlockID();
					
					if ((bbLeftLastID != bbLeftID) || (bbRightFirstID != bbRightID))
					{
						cnpValue = (_cnpMatrix.getMatrixEntry(bbLeftID, bbLeftLastID) +
                                    _cnpMatrix.getMatrixEntry(bbLeftLastID, bbCondLeftID) +
	                                _cnpMatrix.getMatrixEntry(bbCondLeftID, bbCondRightID) +
	                                _cnpMatrix.getMatrixEntry(bbCondRightID, bbRightID));
					}
					else
					{
						cnpValue = (_cnpMatrix.getMatrixEntry(bbLeftID, bbCondLeftID) +
                                    _cnpMatrix.getMatrixEntry(bbCondLeftID, bbCondRightID) +
								    rightBB.getBBlockWCET());
					}
					
			        if (_cnpMatrix.getMatrixEntry(bbLeftID, bbRightID) < cnpValue)
			        {
						_cnpMatrix.setMatrixEntry(bbLeftID, bbRightID, cnpValue);
			        }
			        
					qValue = (cnpValue + _pcmMatrix.getMatrixEntry(bbLeftID, bbRightID));
			        if (_qMatrix.getMatrixEntry(bbLeftID, bbRightID) < qValue)
			        {
						_qMatrix.setMatrixEntry(bbLeftID, bbRightID, qValue);
			        }

			        pbSValue = new JCPreemptionPoints(false);
			        if (qValue <= this._q)
				    {
			        	bSValue = qValue;
			        	pbSValue.addPreemptionPoint(bbLeftID);
			        	pbSValue.addPreemptionPoint(bbRightID);
			        	if (_debugSetBValues == true)
			        	{
				        	System.out.println("Q: BSV["+bbLeftID+"]["+bbRightID+"]="+bSValue);
				        	System.out.print("Q: PBSV["+bbLeftID+"]["+bbRightID+"]=");
				        	pbSValue.displayObjectInformation();
				        	System.out.println("");
			        	}
				    }
				    else
					{
				    	bSValue = JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE;
			        	if (_debugSetBValues == true)
			        	{
				        	System.out.println("INF: BSV["+bbLeftID+"]["+bbRightID+"]="+bSValue);
				        	System.out.print("INF: PBSV["+bbLeftID+"]["+bbRightID+"]=");
				        	pbSValue.displayObjectInformation();
				        	System.out.println("");
			        	}
				    }
				    
					// Loop through all the basic blocks in the current conditional section.
					for (clidx = 0; clidx < (int)(condSectionBlock.numberOfBasicBlocks()); clidx++)
					{
						condLeftBB = condSectionBlock.getBasicBlockAtIndex(clidx);
						bbCondLeftID = condLeftBB.getBBlockID();
						for (cridx = (int)(condSectionBlock.numberOfBasicBlocks()-1); cridx > clidx; cridx--)
						{
							condRightBB = condSectionBlock.getBasicBlockAtIndex(cridx);
							bbCondRightID = condRightBB.getBBlockID();

							// Check preemption cost at BB bbLeftID, BB bbCondLeftID, BB bbCondRightID, and BB bbRightID.
					        if ((_qMatrix.getMatrixEntry(bbLeftID, bbCondLeftID) <= this._q) &&
								(_bMatrix.getMatrixEntry(bbCondLeftID, bbCondRightID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE) &&
						        (_qMatrix.getMatrixEntry(bbCondRightID, bbRightID) <= this._q))
						    {
								pcost = _qMatrix.getMatrixEntry(bbLeftID, bbCondLeftID) +
										_bMatrix.getMatrixEntry(bbCondLeftID, bbCondRightID) + 
										_qMatrix.getMatrixEntry(bbCondRightID, bbRightID);
							    if (pcost < bSValue)
							    {
							    	bSValue = pcost;
							    	pbSValue.clearPreemptionPoints();
							    	pbSValue.setPreemptionPoints(_pbMatrix.getMatrixEntry(bbCondLeftID, bbCondRightID));
							    	pbSValue.addPreemptionPoint(bbLeftID);
							    	pbSValue.addPreemptionPoint(bbRightID);
						        	if (_debugSetBValues == true)
						        	{
							        	System.out.println("QBQ: BSV["+bbLeftID+"]["+bbRightID+"]="+bSValue);
							        	System.out.print("QBQ: PBSV["+bbLeftID+"]["+bbRightID+"]=");
							        	System.out.println("bbLeftID = " + bbLeftID + " bbCondLeftID = " + bbCondLeftID + " bbCondRightID = " + bbCondRightID + " bbRightID = " + bbRightID);
							        	pbSValue.displayObjectInformation();
							        	System.out.println("");
						        	}
							    }
						    }
							    
						    // Check preemption cost at BB bbLeftID, BB bbCondRightID, and BB bbRightID.
						    if ((_qMatrix.getMatrixEntry(bbLeftID, bbCondRightID) <= this._q) &&
						        (_qMatrix.getMatrixEntry(bbCondRightID, bbRightID) <= this._q))
						    {
							    pcost = _qMatrix.getMatrixEntry(bbLeftID, bbCondRightID) +
										_qMatrix.getMatrixEntry(bbCondRightID, bbRightID);
								if (pcost < bSValue)
								{
									bSValue = pcost;
								   	pbSValue.clearPreemptionPoints();
								   	pbSValue.addPreemptionPoint(bbLeftID);
								   	pbSValue.addPreemptionPoint(bbCondRightID);
								   	pbSValue.addPreemptionPoint(bbRightID);
						        	if (_debugSetBValues == true)
						        	{
							        	System.out.println("QQ LRR: BSV["+bbLeftID+"]["+bbRightID+"]="+bSValue);
							        	System.out.print("QQ LRR: PBSV["+bbLeftID+"]["+bbRightID+"]=");
							        	System.out.println("bbLeftID = " + bbLeftID + " bbCondRightID = " + bbCondRightID + " bbRightID = " + bbRightID);
							        	pbSValue.displayObjectInformation();
							        	System.out.println("");
						        	}
								}
						    }
							    
						    // Check preemption cost at BB bbLeftID, BB bbCondLeftID, and BB bbRightID.
						    if ((_qMatrix.getMatrixEntry(bbLeftID, bbCondLeftID) <= this._q) &&
						        (_qMatrix.getMatrixEntry(bbCondLeftID, bbRightID) <= this._q))
						    {
								pcost = _qMatrix.getMatrixEntry(bbLeftID, bbCondLeftID) +
										_qMatrix.getMatrixEntry(bbCondLeftID, bbRightID);
							    if (pcost < bSValue)
							    {
							    	bSValue = pcost;
							    	pbSValue.clearPreemptionPoints();
							    	pbSValue.addPreemptionPoint(bbLeftID);
							    	pbSValue.addPreemptionPoint(bbCondLeftID);
							    	pbSValue.addPreemptionPoint(bbRightID);
						        	if (_debugSetBValues == true)
						        	{
							        	System.out.println("QQ LLR: BSV["+bbLeftID+"]["+bbRightID+"]="+bSValue);
							        	System.out.print("QQ LLR: PBSV["+bbLeftID+"]["+bbRightID+"]=");
							        	System.out.println("bbLeftID = " + bbLeftID + " bbCondLeftID = " + bbCondLeftID + " bbRightID = " + bbRightID);
							        	pbSValue.displayObjectInformation();
							        	System.out.println("");
						        	}
							    }
						    }    
						}
					}

					if (bSValue == JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE)
					{
					    lCount = (int) (predSectionBlock.numberOfBasicBlocks()-1) - leftIndex;
					    rCount = rightIndex;
					    if (lCount > 0)
					    {
							for (mlidx = 0; mlidx < lCount; mlidx++)
							{
								mBBIndex = leftIndex + 1 + mlidx;
								middleLeftBB = predSectionBlock.getBasicBlockAtIndex(mBBIndex);
								bbMiddleLeftID = middleLeftBB.getBBlockID();
								if (rCount > 0)
								{
									for (mridx = 0; mridx < rCount; mridx++)
									{
										mBBIndex = mridx;
										middleRightBB = succSectionBlock.getBasicBlockAtIndex(mBBIndex);
										bbMiddleRightID = middleRightBB.getBBlockID();
										
										if ((_bMatrix.getMatrixEntry(bbLeftID, bbMiddleLeftID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE) &&
											(_bMatrix.getMatrixEntry(bbMiddleLeftID, bbMiddleRightID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE) &&
											(_bMatrix.getMatrixEntry(bbMiddleRightID, bbRightID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE))
										{
											pcost = _bMatrix.getMatrixEntry(bbLeftID, bbMiddleLeftID) + 
													_bMatrix.getMatrixEntry(bbMiddleLeftID, bbMiddleRightID) +
											        _bMatrix.getMatrixEntry(bbMiddleRightID, bbRightID);
											if (pcost < bSValue)
											{
												bSValue = pcost;
												pbSValue.clearPreemptionPoints();
												pbSValue.combineCondPreemptionPoints(_pbMatrix.getMatrixEntry(bbLeftID, bbMiddleLeftID),
			    	                                                                 _pbMatrix.getMatrixEntry(bbMiddleLeftID, bbMiddleRightID), 
			    	                             	                                 _pbMatrix.getMatrixEntry(bbMiddleRightID, bbRightID));
									        	if (_debugSetBValues == true)
									        	{
										        	System.out.println("BBB: BSV["+bbLeftID+"]["+bbRightID+"]="+bSValue);
										        	System.out.print("BBB: PBSV["+bbLeftID+"]["+bbRightID+"]=");
										        	System.out.println("bbLeftID = " + bbLeftID + " bbMiddleLeftID = " + bbMiddleLeftID + " bbMiddleRightID = " + bbMiddleRightID + " bbRightID = " + bbRightID);
										        	pbSValue.displayObjectInformation();
										        	System.out.println("");
									        	}
											}
										}
										
										if ((_bMatrix.getMatrixEntry(bbLeftID, bbMiddleLeftID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE) &&
											(_bMatrix.getMatrixEntry(bbMiddleLeftID, bbRightID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE))
										{
											pcost = _bMatrix.getMatrixEntry(bbLeftID, bbMiddleLeftID) + 
											        _bMatrix.getMatrixEntry(bbMiddleLeftID, bbRightID);
											if (pcost < bSValue)
											{
												bSValue = pcost;
												pbSValue.clearPreemptionPoints();
												pbSValue.combineSectionPreemptionPoints(_pbMatrix.getMatrixEntry(bbLeftID, bbMiddleLeftID),
					    	                           		                            _pbMatrix.getMatrixEntry(bbMiddleLeftID, bbRightID));
										       	if (_debugSetBValues == true)
										       	{
										        	System.out.println("BB LLR: BSV["+bbLeftID+"]["+bbRightID+"]="+bSValue);
										        	System.out.print("BB LLR: PBSV["+bbLeftID+"]["+bbRightID+"]=");
										        	System.out.println("bbLeftID = " + bbLeftID + " bbMiddleLeftID = " + bbMiddleLeftID + " bbRightID = " + bbRightID);
										        	pbSValue.displayObjectInformation();
										        	System.out.println("");
										       	}
											}
										}

										if ((_bMatrix.getMatrixEntry(bbLeftID, bbMiddleRightID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE) &&
											(_bMatrix.getMatrixEntry(bbMiddleRightID, bbRightID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE))
										{
											pcost = _bMatrix.getMatrixEntry(bbLeftID, bbMiddleRightID) + 
											        _bMatrix.getMatrixEntry(bbMiddleRightID, bbRightID);
											if (pcost < bSValue)
											{
												bSValue = pcost;
												pbSValue.clearPreemptionPoints();
												pbSValue.combineSectionPreemptionPoints(_pbMatrix.getMatrixEntry(bbLeftID, bbMiddleRightID),
						    	                   		                                _pbMatrix.getMatrixEntry(bbMiddleRightID, bbRightID));
										       	if (_debugSetBValues == true)
										       	{
										        	System.out.println("BB LRR: BSV["+bbLeftID+"]["+bbRightID+"]="+bSValue);
										        	System.out.print("BB LRR: PBSV["+bbLeftID+"]["+bbRightID+"]=");
										        	System.out.println("bbLeftID = " + bbLeftID + " bbMiddleRightID = " + bbMiddleRightID + " bbRightID = " + bbRightID);
										        	pbSValue.displayObjectInformation();
										        	System.out.println("");
										       	}
											}
										}
									}
								}
								else
								{
									if ((_bMatrix.getMatrixEntry(bbLeftID, bbMiddleLeftID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE) &&
										(_bMatrix.getMatrixEntry(bbMiddleLeftID, bbRightID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE))
									{
										pcost = _bMatrix.getMatrixEntry(bbLeftID, bbMiddleLeftID) + 
										        _bMatrix.getMatrixEntry(bbMiddleLeftID, bbRightID);
										if (pcost < bSValue)
										{
											bSValue = pcost;
											pbSValue.clearPreemptionPoints();
											pbSValue.combineSectionPreemptionPoints(_pbMatrix.getMatrixEntry(bbLeftID, bbMiddleLeftID),
			    	                           		                                _pbMatrix.getMatrixEntry(bbMiddleLeftID, bbRightID));
								        	if (_debugSetBValues == true)
								        	{
									        	System.out.println("BB LLR: BSV["+bbLeftID+"]["+bbRightID+"]="+bSValue);
									        	System.out.print("BB LLR: PBSV["+bbLeftID+"]["+bbRightID+"]=");
									        	pbSValue.displayObjectInformation();
									        	System.out.println("");
								        	}
										}
									}
								}
							}
						}
					    else
					    {
							for (mridx = 0; mridx < rCount; mridx++)
							{
								mBBIndex = mridx;
								middleRightBB = succSectionBlock.getBasicBlockAtIndex(mBBIndex);
								bbMiddleRightID = middleRightBB.getBBlockID();
								
								if ((_bMatrix.getMatrixEntry(bbLeftID, bbMiddleRightID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE) &&
									(_bMatrix.getMatrixEntry(bbMiddleRightID, bbRightID) != JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE))
								{
									pcost = _bMatrix.getMatrixEntry(bbLeftID, bbMiddleRightID) + 
									        _bMatrix.getMatrixEntry(bbMiddleRightID, bbRightID);
									if (pcost < bSValue)
									{
										bSValue = pcost;
										pbSValue.clearPreemptionPoints();
										pbSValue.combineSectionPreemptionPoints(_pbMatrix.getMatrixEntry(bbLeftID, bbMiddleRightID),
			    	                       		                                _pbMatrix.getMatrixEntry(bbMiddleRightID, bbRightID));
							        	if (_debugSetBValues == true)
							        	{
								        	System.out.println("BB LRR: BSV["+bbLeftID+"]["+bbRightID+"]="+bSValue);
								        	System.out.print("BB LRR: PBSV["+bbLeftID+"]["+bbRightID+"]=");
								        	pbSValue.displayObjectInformation();
								        	System.out.println("");
							        	}
									}
								}
							}
					    }
				    }

				    // Update the preemption cost and preemption points matrix values for the current
					// conditional section.
			        if ((bValue < bSValue) || (bValue == JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE))
				    {
				        bValue = bSValue;
			        }
			        pbValue.combinePreemptionPoints(pbSValue);
		        	if (_debugStoreBValues == true)
		        	{
			        	System.out.println("BV["+bbLeftID+"]["+bbRightID+"]="+bValue);
			        	System.out.print("PBV["+bbLeftID+"]["+bbRightID+"]=");
			        	pbValue.displayObjectInformation();
			        	System.out.println("");
		        	}
				}
			}
		}
		
	    // Update the preemption cost and preemption points matrix values for the current
		// conditional section.
        if ((_bMatrix.getMatrixEntry(bbLeftID, bbRightID) < bValue) ||
	        (_bMatrix.getMatrixEntry(bbLeftID, bbRightID) == JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE))
	    {
	       	_bMatrix.setMatrixEntry(bbLeftID, bbRightID, bValue);
	       	ePbValue = _pbMatrix.getMatrixEntry(bbLeftID, bbRightID);
	       	pbValue.combinePreemptionPoints(ePbValue);
	       	_pbMatrix.setMatrixEntry(bbLeftID, bbRightID, pbValue);
	       	if (_debugStoreBValues == true)
	       	{
	        	System.out.println("B["+bbLeftID+"]["+bbRightID+"]="+bValue);
	        	System.out.print("PB["+bbLeftID+"]["+bbRightID+"]=");
	        	pbValue.displayObjectInformation();
	        	System.out.println("");
	       	}
	    }
	}
	
	/**
	 * Computes the preemption point costs for the specified adjacent sections. 
	 *
	 * @param predSectionBlock   the predecessor (left) block object to compute preemption costs for
	 * 
	 * @param succSectionBlock   the successor (right) block object to compute preemption costs for
	 * 
	 * @see                      JCBasicBlock
	 * @see                      JCConditionalBlock
	 * @see                      JCBCostMatrix
	 * @see                      JCCNPCostMatrix
	 * @see                      JCPreemptionPointsMatrix
	 * @see                      JCQCostMatrix
	 * @see                      JCSectionBlock
	 * @see                      JCSelectOptimalPP
	 */
	public void computeConditionalCosts(JCSectionBlock predSectionBlock, JCConditionalBlock conditionalBlock, JCSectionBlock succSectionBlock)
	{
		final boolean _debugComputeIndices = false;
		
		int lidx;
		int offset;
		int ridx;

		System.out.println("Computing preemption costs for conditional block " + conditionalBlock.getConditionalBlockName() + " left block " + predSectionBlock.getSectionBlockName() + " right block " + succSectionBlock.getSectionBlockName());
		
		for (lidx = (int)(predSectionBlock.numberOfBasicBlocks()-1); lidx >= 0; lidx--)
		{
			ridx = 0;
			offset = 0;
			while (((lidx+offset) < (int)(predSectionBlock.numberOfBasicBlocks())) && 
				   (ridx < (int)(succSectionBlock.numberOfBasicBlocks())))
			{
				if (_debugComputeIndices == true)
				{
				    System.out.println("lidx = " + (lidx + offset) + " ridx = " + ridx);
				}
				computeConditionalCostEntry(predSectionBlock, conditionalBlock, succSectionBlock,
						                    (lidx+offset), ridx);
				offset++;
				ridx++;
			}
		}
		
		for (ridx = 1; ridx < (int)(succSectionBlock.numberOfBasicBlocks()); ridx++)
		{
			lidx = 0;
			offset = 0;
			while (((ridx+offset) < (int)(succSectionBlock.numberOfBasicBlocks())) && 
				   (lidx < (int)(predSectionBlock.numberOfBasicBlocks())))
			{
				if (_debugComputeIndices == true)
				{
					System.out.println("lidx = " + lidx + " ridx = " + (ridx + offset));
				}
				computeConditionalCostEntry(predSectionBlock, conditionalBlock, succSectionBlock,
						                    lidx, (ridx+offset));
				offset++;
				lidx++;
			}
		}

	    this.displayCostMatrices();
	}
	
	/**
	 * Checks to see if two basic blocks are connected in the current control 
	 * flow graph. 
	 * 
	 * @return connected    true/false indicating if two basic blocks are
	 *                      connected.
	 *
	 * @see                 JCBasicBlock
	 * @see                 JCSelectOptimalPP
	 */
	public boolean areBasicBlocksConnected(int curr_index, int next_index)
	{
		boolean connected = false;
		JCBasicBlock curr_bb = JCBasicBlock.getBasicBlock(curr_index);
		JCBasicBlock next_bb = JCBasicBlock.getBasicBlock(next_index);
		
		if ((curr_bb != null) && (next_bb != null))
		{
			if (curr_bb.hasSuccessorBasicBlock(next_bb) == true)
			{
				//System.out.println("Basic block " + curr_index + " connected to basic block " + next_index);
				connected = true;
			}
		}
		
		return connected;
	}

	/**
	 * Traverses the control flow graph enumerating the unique paths. 
	 *
	 * @see                      JCPreemptionCostMatrix
	 * @see                      JCBasicBlock
	 * @see                      JCSelectOptimalPP
	 */
	public void traversePaths()
	{
		final int NODE_NOT_VISITED = 0;
		final int NODE_VISITED = 1;

	    int                            curr_index;
	    int                            curr_loop_index;
	    ArrayList<Integer>             curr_loop;
	    ArrayList<Integer>             curr_path;
	    boolean                        done;
	    int                            end_index = this._n - 1;
	    int                            j;
	    int                            loop_end_stack;
	    int                            loop_identifier = 0;
	    int                            loop_index;
	    int                            loop_path_stack_top;
	    int                            loop_start_stack;
	    int                            next_index;
	    int                            number_of_nodes = this._n;
	    byte []                        node_visited = new byte[this._n];
	    int []                         path_indices = new int[this._n];
	    Stack<Integer>                 path_stack = new Stack<Integer>();
	    int                            path_identifier = 0;
	    int                            start_index = 0;

	    _loop_path_ids = new ArrayList<Integer>();
	    _loop_storage = new ArrayList<ArrayList<Integer>>();
	    _path_storage = new ArrayList<ArrayList<Integer>>();
	    
	    for (j = 0; j < number_of_nodes; j++)
	    {
	        node_visited[j] = NODE_NOT_VISITED;
	        path_indices[j] = 0;
	    }
	    path_identifier = 0;
	    
	    curr_index = start_index;
	    node_visited[curr_index] = NODE_VISITED;
	    path_stack.push(curr_index);

	    while (path_stack.size() > 0)
	    {
            //System.out.println("while: stack size = " + path_stack.size());
	        while (curr_index != end_index)
	        {
                //System.out.println("for: curr_index = " + curr_index);
	            for (next_index = path_indices[curr_index]; next_index < number_of_nodes; next_index++,path_indices[curr_index]=next_index)
	            {
	                //System.out.println("wind: curr_index = " + curr_index + " next_index = " + next_index);
	                if ((areBasicBlocksConnected(curr_index, next_index) == true) &&
	                    (node_visited[next_index] != NODE_VISITED))
	                {
	                    if (path_indices[curr_index] < number_of_nodes)
	                    {
	                        path_indices[curr_index]++;
	                    }
	                    curr_index = next_index; 
	                    node_visited[curr_index] = NODE_VISITED;
	            	    path_stack.push(curr_index);
	                    break;
	                }
	                else if ((areBasicBlocksConnected(curr_index, next_index) == true) &&
	                         (node_visited[next_index] == NODE_VISITED))
	                {
	                    loop_index = next_index;
	                    loop_path_stack_top = path_stack.size();
	                    loop_end_stack = loop_path_stack_top;
	                    done = false;
	                    /* Perform a virtual unwind to the previous point where this node is. */
	                    while (done == false)
	                    {
	                        /* Perform a virtual pop of the current node from the stack. */
	                        if (loop_path_stack_top > 0)
	                        {
	                            loop_path_stack_top--;
	                            curr_loop_index = path_stack.get(loop_path_stack_top);
	                            //System.out.println("loop unwind: curr_loop_index = " + curr_loop_index + ".");
	                            if (curr_loop_index == loop_index)
	                            {
	                                loop_start_stack = loop_path_stack_top;
	                                done = true;
	                                System.out.println("loop indices: loop_start_stack = " + loop_start_stack + " loop_end_stack = " + loop_end_stack);
	                                System.out.print("Start to end loop path: ");
	                                curr_loop = new ArrayList<Integer>();
	                                for (j = loop_start_stack; j < loop_end_stack; j++)
	                                {
	                                    curr_loop.add(path_stack.get(j));
	                                    System.out.print(path_stack.get(j) + " ");
	                                }
	                                _loop_storage.add(curr_loop);
	                                _loop_path_ids.add(path_identifier);
	                                loop_identifier++;
	                                System.out.println("");
	                            }
	                        }
	                        else
	                        {
	                            done = true;
	                        }
	                    }
	                }
	            }
	        }
	        
	        System.out.print("Start to end path: ");
	 
            curr_path = new ArrayList<Integer>();
	        for (j = 0; j < path_stack.size(); j++)
	        {
	            curr_path.add(path_stack.get(j));
                System.out.print(path_stack.get(j) + " ");
	        }
            _path_storage.add(curr_path);
	        path_identifier++;
            System.out.println("");

	        /* Unwind to the previous junction. */
	        done = false;
	        while (done == false)
	        {
	            /* Pop the current node from the stack. */
	            if (path_stack.size() > 0)
	            {
	                path_indices[curr_index] = 0;
	                node_visited[curr_index] = NODE_NOT_VISITED;
	                curr_index = path_stack.pop();
	                for (next_index = path_indices[curr_index]; next_index < number_of_nodes; next_index++,path_indices[curr_index]=next_index)
	                {
	                    if ((areBasicBlocksConnected(curr_index, next_index) == true) &&
	                        (node_visited[next_index] != NODE_VISITED))
	                    {
	                        if (path_indices[curr_index] < number_of_nodes)
	                        {
	                            path_indices[curr_index]++;
	                        }
	                        /* System.out.println("unwind: curr_index = " + curr_index + " next_index = " + next_index); */
	                        path_stack.push(curr_index);
	                        curr_index = next_index; 
	                        node_visited[curr_index] = NODE_VISITED;
	                        path_stack.push(curr_index);
	                        done = true;
	                        break;
	                    }
	                }
	            }
	            else
	            {
	                done = true;
	            }
	        }
	        /* System.out.println("Stack top = " + path_stack_top); */
	    }
	    System.out.println("Number of loops = " + loop_identifier);
	    System.out.println("Number of paths = " + path_identifier);
	}
	
	/**
	 * Performs a brute force computation of the optimal preemption points
	 * for the current control flow graph. 
	 *
	 * @see                      JCPreemptionCostMatrix
	 * @see                      JCBasicBlock
	 * @see                      JCSelectOptimalPP
	 */
	void bruteForceSolution()
	{
		final int NODE_NOT_VISITED = 0;
		final int NODE_VISITED = 1;
		final boolean _debugBestSolutions = false;
        final boolean _debugValidPaths = false;
        
	    long                           best_solution = Long.MAX_VALUE;
	    ArrayList<Integer>             curr_path;
	    int                            maximum_cost;
	    int                            minimum_cost;
	    Iterator<Long>                 minimum_cost_iterator;
	    JCCostSolution                 minimum_cost_solution = new JCCostSolution();
	    ArrayList<Long>                minimum_cost_solution_list = new ArrayList<Long>();
	    long                           minimum_no_of_pps = 0;
	    int                            node_idx;
	    int                            node_index;
	    int                            node_number;
	    long                           node_value;
	    byte []                        nodes_selected = new byte[this._n];
	    long                           no_of_pps;
	    int                            number_of_nodes = this._n;
	    long                           number_of_solutions;
	    JCBasicBlock                   path_bb;
	    int                            path_idx;
	    int                            path_incr_cost;
	    int                            path_cost;
	    int                            preemption_cost;
	    Iterator<JCPreemptionPoints>   preemptionPointIterator;
	    JCPreemptionPoints             preemptionPoints;
	    int                            prev_node_number;
	    long                           solution;
	    long                           solution_value;
	    boolean                        valid_path;

	    minimum_cost = Integer.MAX_VALUE;
	    number_of_solutions = (long) Math.pow(2,number_of_nodes);
	    System.out.println("Number of possible solutions is " + number_of_solutions);
        /* Iterate through all possible solutions. */
	    for (solution = 0; solution < number_of_solutions; solution++)
	    {
	        solution_value = solution;
	        /* Initialize the selected nodes array. */
	        for (node_index = 0; node_index < number_of_nodes; node_index++)
	        {
	            nodes_selected[node_index] = NODE_NOT_VISITED;
	        }
	        
	        /* For the current solution, determine which nodes are selected as preemption points. */
	        no_of_pps = 0;
	        for (node_index = 0; node_index < number_of_nodes; node_index++)
	        {
	            node_number = number_of_nodes - node_index - 1;
	            node_value = (long) Math.pow(2, node_number);
	            if (node_value <= solution_value)
	            {
	                solution_value -= node_value;
	                nodes_selected[node_number] = NODE_VISITED;
	                no_of_pps++;
	            }
	        }
	        
	        if (nodes_selected[0] != NODE_VISITED)
	        {
	            nodes_selected[0] = NODE_VISITED;
	            no_of_pps++;
	        }
	        
	        if (nodes_selected[number_of_nodes-1] != NODE_VISITED)
	        {
	            nodes_selected[number_of_nodes-1] = NODE_VISITED;
	            no_of_pps++;
	        }
	        
	        maximum_cost = 0;
	        path_cost = 0;
	        path_incr_cost = 0;
	        valid_path = true;
	        for (path_idx = 0; path_idx < _path_storage.size(); path_idx++)
	        {
	        	curr_path = _path_storage.get(path_idx);
	        	if (curr_path.size() > 0)
	        	{
		            prev_node_number = curr_path.get(0);
		            path_bb = JCBasicBlock.getBasicBlock(prev_node_number);
		            path_cost = path_bb.getBBlockWCET();
		            path_incr_cost = path_bb.getBBlockWCET();
		            for (node_idx = 1; node_idx < curr_path.size(); node_idx++)
		            {
		                node_number = curr_path.get(node_idx);
			            path_bb = JCBasicBlock.getBasicBlock(node_number);
			            if (path_bb != null)
			            {
			                path_cost += path_bb.getBBlockWCET();
			                path_incr_cost += path_bb.getBBlockWCET();

			                if (nodes_selected[node_number] == NODE_VISITED)
			                {
			                	preemption_cost = _pcmMatrix.getMatrixEntry(prev_node_number, node_number);
			                	if (preemption_cost < 0)
			                	{
			                		valid_path = false;
			                	}
			                	preemption_cost = (preemption_cost < 0) ? 0 : preemption_cost;
			                    path_incr_cost += preemption_cost;
			                    //System.out.println("Solution " + solution + " path " + path_idx + " prev_node = " + prev_node_number + " curr_node = " + node_number + " cost " + path_incr_cost);

			                    if (path_incr_cost > this._q)
			                    {
			                        valid_path = false;
			                    }
			                    path_cost += preemption_cost;
			                    prev_node_number = node_number;
			                    path_incr_cost = 0;
			                }
			            }
			            else
			            {
		                    System.out.println("Solution " + solution + " path " + path_idx + " prev_node = " + prev_node_number + " curr_node = " + node_number + " basic block null ");
			            }
		            }

		            if (valid_path == true)
		            {
		                if (path_cost > maximum_cost)
		                {
		                    maximum_cost = path_cost;
		                }

		                if (_debugValidPaths == true)
		                {
			                System.out.println("Path " + path_idx + " cost " + path_cost);
			                for (node_idx = 0; node_idx < curr_path.size(); node_idx++)
			                {
			                    System.out.print(curr_path.get(node_idx) + " ");
			                }
			                System.out.println("");
			                System.out.print("Preemption points: ");
			                for (node_index = 0; node_index < number_of_nodes; node_index++)
			                {
			                    if (nodes_selected[node_index] == NODE_VISITED)
			                    {
			        	            path_bb = JCBasicBlock.getBasicBlock(node_index);
			        	        	System.out.print(path_bb.getBBlockName() + "(" + node_index + ") ");
			                    }
			                }
			                System.out.println("");
		                }
		            }
	        	}
	        }

            if (_debugValidPaths == true)
            {
    	        if (valid_path == true)
    	        {
    	            System.out.println("Solution " + solution + " has valid paths with maximum cost " + maximum_cost + ".");
    	        }
    	        else
    	        {
    	            System.out.println("Solution " + solution + " has invalid paths.");
    	        }
	        }

	        /* Check to see if this solution is the best so far. */
	        if (((minimum_cost > maximum_cost) || ((minimum_cost == maximum_cost) && (no_of_pps <= minimum_no_of_pps))) && (valid_path == true))
	        {
	            if (minimum_cost > maximum_cost)
	            {
	            	minimum_cost_solution_list.clear();
	            	minimum_cost_solution.clearPreemptionPointSolutions();
	            }
	            minimum_cost = maximum_cost;
	            minimum_cost_solution.setSolutionCost(minimum_cost);
	            minimum_no_of_pps = no_of_pps;
	            best_solution = solution;
	            minimum_cost_solution_list.add(solution);
	            preemptionPoints = new JCPreemptionPoints(false);
	            for (node_index = 0; node_index < number_of_nodes; node_index++)
	            {
	                if (nodes_selected[node_index] == NODE_VISITED)
	                {
	                	preemptionPoints.addPreemptionPoint(node_index);
	                }
	            }
	            
	            minimum_cost_solution.addPreemptionPointSolution(preemptionPoints);
	            
                if (_debugBestSolutions == true)
                {
    	            System.out.println("Best solution thus far " + best_solution + " minimum cost " + minimum_cost);
    	            System.out.print("Preemption points: ");
    	            for (node_index = 0; node_index < number_of_nodes; node_index++)
    	            {
    	                if (nodes_selected[node_index] == NODE_VISITED)
    	                {
    	    	            path_bb = JCBasicBlock.getBasicBlock(node_index);
    	    	        	System.out.print(path_bb.getBBlockName() + "(" + node_index + ") ");
    	                }
    	            }
    	            System.out.println("");
    	            
    		        maximum_cost = 0;
    		        path_cost = 0;
    		        path_incr_cost = 0;
    		        valid_path = true;
    		        for (path_idx = 0; path_idx < _path_storage.size(); path_idx++)
    		        {
    		        	curr_path = _path_storage.get(path_idx);
    		        	if (curr_path.size() > 0)
    		        	{
    		        		System.out.print("   Path " + path_idx + " : ");
    			            prev_node_number = curr_path.get(0);
    			            path_bb = JCBasicBlock.getBasicBlock(prev_node_number);
    			            System.out.print("<" + prev_node_number + "," + path_bb.getBBlockWCET() + "> ");
    			            path_cost = path_bb.getBBlockWCET();
    			            path_incr_cost = path_bb.getBBlockWCET();
    			            for (node_idx = 1; node_idx < curr_path.size(); node_idx++)
    			            {
    			                node_number = curr_path.get(node_idx);
    				            path_bb = JCBasicBlock.getBasicBlock(node_number);
    				            if (path_bb != null)
    				            {
    				                path_cost += path_bb.getBBlockWCET();
    	    			            System.out.print("+ <" + node_number + "," + path_bb.getBBlockWCET() + "> ");
    				                path_incr_cost += path_bb.getBBlockWCET();

    				                if (nodes_selected[node_number] == NODE_VISITED)
    				                {
    				                	preemption_cost = _pcmMatrix.getMatrixEntry(prev_node_number, node_number);
    				                	preemption_cost = (preemption_cost < 0) ? 0 : preemption_cost;
    				                    path_incr_cost += preemption_cost;
        	    			            System.out.print("+ [" + prev_node_number + "," + node_number + "," + preemption_cost + "] ");

    				                    //System.out.println("Solution " + solution + " path " + path_idx + " prev_node = " + prev_node_number + " curr_node = " + node_number + " cost " + path_incr_cost);

    				                    path_cost += preemption_cost;
    				                    prev_node_number = node_number;
    				                    path_incr_cost = 0;
    				                }
    				            }
    				            else
    				            {
    			                    System.out.println("Solution " + solution + " path " + path_idx + " prev_node = " + prev_node_number + " curr_node = " + node_number + " basic block null ");
    				            }
    			            }
    			            System.out.println(" = " + path_cost);
    		        	}
    		        }
                }
	        }
	    }

	    /* Display the best solution using the brute force method. */
	    System.out.println("Best solution minimum cost " + minimum_cost);
	    preemptionPointIterator = minimum_cost_solution.getPreemptionPointSolutionsIterator();
	    while(preemptionPointIterator.hasNext() == true)
	    {
	    	preemptionPoints = preemptionPointIterator.next();
		    //System.out.println("Best solution " + best_solution);
		    //solution_value = best_solution;
		    //for (node_index = 0; node_index < number_of_nodes; node_index++)
		    //{
		    //[node_index] = NODE_NOT_VISITED;
		    //}

		    //for (node_index = 0; node_index < number_of_nodes; node_index++)
		    //{
		    //    node_number = number_of_nodes - node_index - 1;
		    //    node_value = (long) Math.pow(2, node_number);
		    //    if (node_value <= solution_value)
		    //    {
		    //        solution_value -= node_value;
		    //        nodes_selected[node_number] = NODE_VISITED;
		    //    }
		    //}
		    //nodes_selected[0] = NODE_VISITED;
		    //nodes_selected[number_of_nodes-1] = NODE_VISITED;

		    System.out.print("Preemption points: ");
		    preemptionPoints.displayObjectInformation();
		    //for (node_index = 0; node_index < number_of_nodes; node_index++)
		    //{
		    //    if (nodes_selected[node_index] == NODE_VISITED)
		    //    {
		    //        path_bb = JCBasicBlock.getBasicBlock(node_index);
		    //    	System.out.print(path_bb.getBBlockName() + "(" + node_index + ") ");
		    //    }
		    //}
		    System.out.println("");
	    }
	    
	    _path_storage.clear();
	    _loop_path_ids.clear();
	    _loop_storage.clear();
	}
	
	/**
	 * Displays the preemption point cost matrices. 
	 *
	 * @see                      JCBCostMatrix
	 * @see                      JCCNPCostMatrix
	 * @see                      JCPreemptionPointsMatrix
	 * @see                      JCQCostMatrix
	 * @see                      JCSelectOptimalPP
	 */
	public void displayCostMatrices()
	{
		_cnpMatrix.displayObjectInformation();
		_qMatrix.displayObjectInformation();
		_bMatrix.displayObjectInformation();
		_pbMatrix.displayObjectInformation();
	}
	
	/**
	 * Returns the string select optimal preemption points object type name. 
	 *
	 * @return ID     the string type name of the select optimal preemption points object
	 * 
	 * @see           JCSelectOptimalPP
	 */
	@Override
	public String getObjectTypeName()
	{
		return "JCSelectOptimalPP";
	}
    
	/**
	 * Displays information about this select optimal preemption points object. 
	 *
	 * @see                      JCBCostMatrix
	 * @see                      JCCNPCostMatrix
	 * @see                      JCPreemptionPointsMatrix
	 * @see                      JCQCostMatrix
	 * @see                      JCSelectOptimalPP
	 */
    @Override
    public void displayObjectInformation()
    {
		_cnpMatrix.displayObjectInformation();
		_qMatrix.displayObjectInformation();
		_bMatrix.displayObjectInformation();
		_pbMatrix.displayObjectInformation();
    }

	/**
	 * Displays information about all preemption points matrix objects. 
	 *
	 * @see           JCSelectOptimalPP
	 */
    public static void displayAllObjects()
    {
	    JCSelectOptimalPP         currentSelectOptimalPP;
	    int                       selectOptimalPPID;
		
		for (selectOptimalPPID = 0; selectOptimalPPID < _selectOptimalPPs.size(); selectOptimalPPID++)
		{
			currentSelectOptimalPP = JCSelectOptimalPP.getSelectOptimalPP(selectOptimalPPID);
			currentSelectOptimalPP.displayObjectInformation();
		}
    }
}
