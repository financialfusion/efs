package com.ffusion.ims21.servlet.parse;

import java.util.Vector;

public class PriorityVector
  extends Vector
{
  public void add(ImsMsg paramImsMsg)
  {
    int j = size();
    if (isEmpty())
    {
      super.addElement(paramImsMsg);
    }
    else
    {
      for (int i = 0; i < j; i++)
      {
        ImsMsg localImsMsg = (ImsMsg)elementAt(i);
        if (paramImsMsg.getPriority() > localImsMsg.getPriority())
        {
          super.insertElementAt(paramImsMsg, i);
          return;
        }
        if ((paramImsMsg.getPriority() == localImsMsg.getPriority()) && (paramImsMsg.getDeliveries() < localImsMsg.getDeliveries()))
        {
          super.insertElementAt(paramImsMsg, i);
          return;
        }
      }
      super.addElement(paramImsMsg);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.servlet.parse.PriorityVector
 * JD-Core Version:    0.7.0.1
 */