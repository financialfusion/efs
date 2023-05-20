package com.ffusion.tasks.disbursement;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String DISBURSEMENT_SUMMARIES = "DisbursementSummaries";
  public static final String DISBURSEMENT_PRESENTMENT_SUMMARIES = "DisbursementPresentmentSummaries";
  public static final String DISBURSEMENT_SUMMARIES_FOR_PRESENTMENT = "DisbursementSummariesForPresentment";
  public static final String DISBURSEMENT_ACCOUNT = "DisbursementAccount";
  public static final String DISBURSEMENT_ITEMS = "DisbursementItems";
  public static final String DISBURSEMENT_TRANSACTIONINDEX = "DisbursementTransactionIndex";
  public static final int ERROR_ACCOUNT_NOT_SPECIFIED = 21000;
  public static final int ERROR_SUMMARIES_NOT_FOUND = 21001;
  public static final int ERROR_PRESENTMENT_NOT_SPECIFIED = 21002;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.disbursement.Task
 * JD-Core Version:    0.7.0.1
 */