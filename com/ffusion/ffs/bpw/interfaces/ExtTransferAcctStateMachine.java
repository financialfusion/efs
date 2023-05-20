package com.ffusion.ffs.bpw.interfaces;

public class ExtTransferAcctStateMachine
{
  public static boolean canAddNew(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return (paramExtTransferAcctInfo.getVerifyStatus() == 0) || (paramExtTransferAcctInfo.getVerifyStatus() == 1);
  }
  
  public static boolean canActivate(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return true;
  }
  
  public static boolean canDeactivate(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return true;
  }
  
  public static boolean canDelete(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo.getVerifyStatus() != 2;
  }
  
  public static boolean canDeposit(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return (paramExtTransferAcctInfo.getVerifyStatus() != 4) && (paramExtTransferAcctInfo.getVerifyStatus() != 0);
  }
  
  public static boolean canReDeposit(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo.getVerifyStatus() == 2;
  }
  
  public static boolean canVerify(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo.getVerifyStatus() == 2;
  }
  
  public static boolean canCreatePrenoteEntry(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return true;
  }
  
  public static boolean canPrenoteMature(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return true;
  }
  
  public static ExtTransferAcctInfo actionAddNew(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo;
  }
  
  public static ExtTransferAcctInfo actionActivate(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo;
  }
  
  public static ExtTransferAcctInfo actionDeactivate(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo;
  }
  
  public static ExtTransferAcctInfo actionDelete(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo;
  }
  
  public static ExtTransferAcctInfo actionDeposit(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo;
  }
  
  public static ExtTransferAcctInfo actionVerify(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo;
  }
  
  public static ExtTransferAcctInfo actionCreatePrenoteEntry(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo;
  }
  
  public static ExtTransferAcctInfo actionRePrenote(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    return paramExtTransferAcctInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ExtTransferAcctStateMachine
 * JD-Core Version:    0.7.0.1
 */