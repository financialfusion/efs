package com.ffusion.services;

import com.ffusion.dataconsolidator.adapter.DCException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface DataConsolidator3
  extends DataConsolidator2
{
  public abstract ArrayList getBAITypeCodesForTransaction(int paramInt, HashMap paramHashMap)
    throws DCException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.DataConsolidator3
 * JD-Core Version:    0.7.0.1
 */