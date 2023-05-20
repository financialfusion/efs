package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHBatch;

public class AddChildSupportPayment
  extends AddACHTaxPayment
{
  protected void setBatchFundsTransactionType(ACHBatch paramACHBatch)
  {
    paramACHBatch.setType(17);
    paramACHBatch.setACHType("ChildSupportPayment");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.AddChildSupportPayment
 * JD-Core Version:    0.7.0.1
 */