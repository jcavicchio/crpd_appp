import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * JCPreemptionPoints is a fundamental class whose purpose is to contain a 
 * set of preemption points representing a potential sub-solution or solution
 * to the preemption points placement problem for the control flow graph 
 * basic block objects for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCPreemptionPoints extends JCObject 
{
	private final static int  _numberOfPPSPerEntry = 8;
	
	private static int _bbCount = 0;                         // Basic block count

	private static int _numberOfEntries = 0;
	
    private static int _nextID = 0;

	private static int _ppCount = 0;                         // Counter to count which preemption points it is
    private static ArrayList<JCPreemptionPoints> _preemptionPointEntries = 
	          new ArrayList<JCPreemptionPoints>();           // Preemption point objects

	private int  _preemptionPointsID;
	private byte _preemptionPoints[];

	/**
	 * Default constructor.
	 */
	JCPreemptionPoints(boolean storedObject)
	{
		int idx;
		
		if (storedObject == true)
		{
			_preemptionPointsID = _nextID;
			_nextID++;
			_ppCount = _nextID;
		}
		else
		{
			_preemptionPointsID = 0;
		}
		
		_preemptionPoints = new byte[_numberOfEntries];
		for (idx = 0; idx < _numberOfEntries; idx++)
		{
			_preemptionPoints[idx] = 0;
		}
	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCPreemptionPoints
	 */
	public static void reset()
	{
		_nextID = 0;
		_bbCount = 0;
		_numberOfEntries = 0;
		_ppCount = 0;
		_preemptionPointEntries = new ArrayList<JCPreemptionPoints>();
	}
	
	/**
	 * Returns the numeric identifier of the preemption points object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _preemptPointsID   the identifier of this preemption points object
	 * 
	 * @see                       JCPreemptionPoints
	 */
	public int getPreemptionPointsID()
	{
		return _preemptionPointsID;
	}

	/**
	 * Sets the size of number of the basic block objects that comprise 
	 * the current control flow graph. 
	 *
	 * @param  n    the number of basic block objects in the CFG
	 * 
	 * @see         JCBasicBlock
	 * @see         JCPreemptionPoints
	 */
	public static void setNValue(int n)
	{
		_bbCount = n;
		
		if ((_bbCount % _numberOfPPSPerEntry) == 0)
		{
		    _numberOfEntries = (_bbCount > _numberOfPPSPerEntry) ? (_bbCount/_numberOfPPSPerEntry) : 1;
		}
		else
		{
		    _numberOfEntries = (_bbCount > _numberOfPPSPerEntry) ? ((_bbCount/_numberOfPPSPerEntry)+1) : 1;
		}
	}
	
	   /**
		 * Stores the preemption points object at the specified location in
		 * the preemption points array. 
		 *
		 * @param  preemptionPointsID           the identifier of the stored preemption points object
		 * 
		 * @param  preemptionPoints             the preemption points object to be stored
		 * 
		 * @see                                 JCPreemptionPoints
		 */
		public static void setConditionalSection(int preemptionPointsID, JCPreemptionPoints preemptionPoints)
		{
			if (preemptionPointsID < _ppCount)
			{
				_preemptionPointEntries.add(preemptionPoints);
			}
		}
		
		/**
		 * Returns the conditional section object associated with the specified identifier. 
		 *
		 * @param  preemptionPointsID      the identifier of the stored conditional section object
		 * 
		 * @return preemptionPoints        the preemption points object stored
		 * 
		 * @see                            JCPreemptionPoints
		 */
		public static JCPreemptionPoints getPreemptionPoints(int preemptionPointsID)
		{
			if (preemptionPointsID < _ppCount)
			{
		        return _preemptionPointEntries.get(preemptionPointsID);
			}
			else
			{
				return null;
			}
		}
		
	/**
	 * Adds the specified basic block object as a preemption point. 
	 *
	 * @param  bBlockID    the identifier of the basic block preemption point location
	 * 
	 * @see                JCBasicBlock
	 * @see                JCPreemptionPoints
	 */
	public void addPreemptionPoint(int bBlockID)
	{
		int arrayEntry;
		int arrayOffset;
		byte pPointMask;
		
		if (bBlockID < _bbCount)
		{
			arrayEntry = _numberOfEntries - (bBlockID/_numberOfPPSPerEntry) - 1;
			arrayOffset = bBlockID % _numberOfPPSPerEntry;
			pPointMask = (byte)(1 << arrayOffset);
			_preemptionPoints[arrayEntry] |= pPointMask;
			//System.out.println("Adding preemption point " + bBlockID + " _preemptionPoints[" + arrayEntry + "] = " + _preemptionPoints[arrayEntry]);
		}
	}
		
	/**
	 * Removes the specified basic block object as a preemption point. 
	 *
	 * @param  bBlockID    the identifier of the basic block preemption point location
	 * 
	 * @see                JCBasicBlock
	 * @see                JCPreemptionPoints
	 */
	public void removePreemptionPoint(int bBlockID)
	{
		int arrayEntry;
		int arrayOffset;
		byte pPointMask;
		
		if (bBlockID < _bbCount)
		{
			arrayEntry = _numberOfEntries - (bBlockID/_numberOfPPSPerEntry) - 1;
			arrayOffset = bBlockID % _numberOfPPSPerEntry;
			pPointMask = (byte)(1 << arrayOffset);
			pPointMask = (byte)~pPointMask;
			_preemptionPoints[arrayEntry] &= pPointMask;
		}
	}
	
	/**
	 * Determines if the specified preemption point is selected. 
	 *
	 * @param  bBlockID    the identifier of the basic block preemption point location
	 * 
	 * @return isPreemptionPointSelected   true/false indicating preemption point selected
	 * 
	 * @see                                JCPreemptionPoints
	 */
	public boolean isPreemptionPointSelected(int bBlockID)
	{
		int arrayEntry;
		int arrayOffset;
		byte pPointMask;
		boolean preemptionPointSelected = false;
		
		if (bBlockID < _bbCount)
		{
			arrayEntry = _numberOfEntries - (bBlockID/_numberOfPPSPerEntry) - 1;
			arrayOffset = bBlockID % _numberOfPPSPerEntry;
			pPointMask = (byte)(1 << arrayOffset);
			if ((byte)(_preemptionPoints[arrayEntry] & pPointMask) == pPointMask)
			{
				preemptionPointSelected = true;
			}
		}

		return preemptionPointSelected;	
	}

	/**
	 * Combines the stored preemption points from the specified two locations. 
	 *
	 * @param  firstPoint    the preemption points entry of the first location
	 * 
	 * @param  secondPoint   the preemption points entry of the second location
	 * 
	 * @see                  JCPreemptionPoints
	 */
    public void combineSectionPreemptionPoints(JCPreemptionPoints firstPoint, JCPreemptionPoints secondPoint)
    {
    	int arrayEntry;

    	for (arrayEntry = 0; arrayEntry < _numberOfEntries; arrayEntry++)
    	{
		    _preemptionPoints[arrayEntry] = (byte) (firstPoint._preemptionPoints[arrayEntry] | secondPoint._preemptionPoints[arrayEntry]);
    	}
    }
    
	/**
	 * Combines the stored preemption points from the specified two locations. 
	 *
	 * @param  firstPoint    the preemption points entry of the first location
	 * 
	 * @param  secondPoint   the preemption points entry of the second location
	 * 
	 * @param  thirdPoint    the preemption points entry of the third location
	 * 
	 * @see                  JCPreemptionPoints
	 */
    public void combineCondPreemptionPoints(JCPreemptionPoints firstPoint, JCPreemptionPoints secondPoint, JCPreemptionPoints thirdPoint)
    {
    	int arrayEntry;

    	for (arrayEntry = 0; arrayEntry < _numberOfEntries; arrayEntry++)
    	{
		    _preemptionPoints[arrayEntry] = (byte) (firstPoint._preemptionPoints[arrayEntry] | secondPoint._preemptionPoints[arrayEntry] | thirdPoint._preemptionPoints[arrayEntry]);
    	}
    }
    
	/**
	 * Combines the stored preemption points from the specified two locations. 
	 *
	 * @param  firstPoint    the preemption points entry of the first location
	 * 
	 * @see                  JCPreemptionPoints
	 */
    public void combinePreemptionPoints(JCPreemptionPoints firstPoint)
    {
    	int arrayEntry;

    	for (arrayEntry = 0; arrayEntry < _numberOfEntries; arrayEntry++)
    	{
		    _preemptionPoints[arrayEntry] = (byte) (_preemptionPoints[arrayEntry] | firstPoint._preemptionPoints[arrayEntry]);
    	}
    }
    
	/**
	 * Sets the stored preemption points from the specified preemption points object. 
	 *
	 * @param  preemptionPoints  the preemption points entry of the first location
	 * 
	 * @see                      JCPreemptionPoints
	 */
    public void setPreemptionPoints(JCPreemptionPoints preemptionPoints)
    {
    	int arrayEntry;

    	for (arrayEntry = 0; arrayEntry < _numberOfEntries; arrayEntry++)
    	{
		    _preemptionPoints[arrayEntry] = (byte) (preemptionPoints._preemptionPoints[arrayEntry]);
    	}
    }
    
	/**
	 * Clears the stored preemption points from the specified preemption points object. 
	 *
	 * @see                      JCPreemptionPoints
	 */
    public void clearPreemptionPoints()
    {
    	int arrayEntry;

    	for (arrayEntry = 0; arrayEntry < _numberOfEntries; arrayEntry++)
    	{
		    _preemptionPoints[arrayEntry] = (byte) 0;
    	}
    }
    
	/**
	 * Displays the hexadecimal representation of the stored preemption points. 
	 *
	 * @see                                JCPreemptionPoints
	 */
    public void displayHexValue()
    {
		int  arrayEntry;

		for (arrayEntry = 0; arrayEntry < _numberOfEntries; arrayEntry++)
    	{
    		System.out.print(String.format("%02X", _preemptionPoints[arrayEntry]));
    	}
    }
    
	/**
	 * Determines is the specified object is a JCPreemptionPoints object with 
	 * matching preemption points.
	 *
	 * @return object     the preemption points object to compare with
	 * 
	 * @see               JCPreemptionPoints
	 */
	public boolean equals (Object o)
	{
		int  arrayEntry;
		boolean sameObjects = false;
		
		if (o instanceof JCPreemptionPoints)
		{
			sameObjects = true;
	    	for (arrayEntry = 0; arrayEntry < _numberOfEntries; arrayEntry++)
	    	{
				if (_preemptionPoints[arrayEntry] != ((JCPreemptionPoints) o)._preemptionPoints[arrayEntry])
				{
					sameObjects = false;
				}
	    	}
		}
		return sameObjects;
	}

	/**
	 * Returns the string preemption points object type name. 
	 *
	 * @return ID     the string type name of the preemption points object
	 * 
	 * @see           JCPreemptionPoints
	 */
	@Override
	public String getObjectTypeName() 
	{
    	return "JCPreemptionPoints";
	}

	/**
	 * Displays information about this basic block object. 
	 *
	 * @see           JCPreemptionPoints
	 */
	@Override
	public void displayObjectInformation() 
	{
		final boolean displayBBIDs = false;
		final boolean displayBBNames = true;
		final boolean displayPPHexValues = false;

		int  arrayEntry;
		int  arrayOffset;
		JCBasicBlock basicBlock;
		int  bBlockID;
		int  displayCount = 0;
		byte pPointMask;
		
		System.out.print("{");
		if ((displayBBIDs == true) || (displayBBNames == true))
		{
			for (bBlockID = 0; bBlockID < _bbCount; bBlockID++)
			{
				arrayEntry = _numberOfEntries - (bBlockID/_numberOfPPSPerEntry) - 1;
				arrayOffset = bBlockID % _numberOfPPSPerEntry;
				pPointMask = (byte)(1 << arrayOffset);
				if ((byte)(_preemptionPoints[arrayEntry] & pPointMask) == pPointMask)
				{
					if (displayCount > 0)
					{
						System.out.print(",");
					}
					if (displayBBIDs == true)
					{
						System.out.print(bBlockID);
					}
					if (displayBBNames == true)
					{
						basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
						System.out.print(basicBlock.getBBlockName());
					}
					displayCount++;
				}
			}
		}
		else if (displayPPHexValues == true)
		{
	    	for (arrayEntry = 0; arrayEntry < _numberOfEntries; arrayEntry++)
	    	{
	    		System.out.print(String.format("%02X", _preemptionPoints[arrayEntry]));
	    	}
		}
		System.out.print("}");
	}
	   

	/**
	 * Displays information about this basic block object. 
	 *
	 * @see           JCPreemptionPoints
	 */
	public void displayObjectInformation(PrintWriter ppWriter) 
	{
		final boolean displayBBIDs = false;
		final boolean displayBBNames = true;
		final boolean displayPPHexValues = false;

		int  arrayEntry;
		int  arrayOffset;
		JCBasicBlock basicBlock;
		int  bBlockID;
		int  displayCount = 0;
		byte pPointMask;
		
		ppWriter.print("{");
		if ((displayBBIDs == true) || (displayBBNames == true))
		{
			for (bBlockID = 0; bBlockID < _bbCount; bBlockID++)
			{
				arrayEntry = _numberOfEntries - (bBlockID/_numberOfPPSPerEntry) - 1;
				arrayOffset = bBlockID % _numberOfPPSPerEntry;
				pPointMask = (byte)(1 << arrayOffset);
				if ((byte)(_preemptionPoints[arrayEntry] & pPointMask) == pPointMask)
				{
					if (displayCount > 0)
					{
						ppWriter.print(",");
					}
					if (displayBBIDs == true)
					{
						ppWriter.print(bBlockID);
					}
					if (displayBBNames == true)
					{
						basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
						ppWriter.print(basicBlock.getBBlockName());
					}
					displayCount++;
				}
			}
		}
		else if (displayPPHexValues == true)
		{
	    	for (arrayEntry = 0; arrayEntry < _numberOfEntries; arrayEntry++)
	    	{
	    		ppWriter.print(String.format("%02X", _preemptionPoints[arrayEntry]));
	    	}
		}
		ppWriter.print("}");
	}
	   
	/**
	 * Displays information about all preemption points objects. 
	 *
	 * @see           JCPreemptionPoints
	 */
    public static void displayAllObjects()
    {
	    JCPreemptionPoints   currentPreemptionPoints;
	    int                  preemptionPointsID;
		
		for (preemptionPointsID = 0; preemptionPointsID < _preemptionPointEntries.size(); preemptionPointsID++)
		{
			currentPreemptionPoints = JCPreemptionPoints.getPreemptionPoints(preemptionPointsID);
			currentPreemptionPoints.displayObjectInformation();
		}
    }
}
