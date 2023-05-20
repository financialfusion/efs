package com.ffusion.beans.accounts;

import com.ffusion.util.Filterable;

public abstract interface AccountFilters
  extends Filterable
{
  public static final String CHECKING_FILTER = "Checking";
  public static final String SAVINGS_FILTER = "Savings";
  public static final String CREDIT_FILTER = "Credit";
  public static final String LOAN_FILTER = "Loan";
  public static final String MORTGAGE_FILTER = "Mortgage";
  public static final String HOME_EQUITY_FILTER = "HomeEquity";
  public static final String LINE_OF_CREDIT_FILTER = "CreditLine";
  public static final String CD_FILTER = "CD";
  public static final String IRA_FILTER = "IRA";
  public static final String STOCK_BOND_FILTER = "Stocks";
  public static final String BROKERAGE_FILTER = "Brokerage";
  public static final String MONEY_MARKET_FILTER = "MoneyMarket";
  public static final String BUSINESS_LOAN_FILTER = "BusinessLoan";
  public static final String OTHER_FILTER = "Other";
  public static final String TRANSFER_TO_FILTER = "TransferTo";
  public static final String TRANSFER_FROM_FILTER = "TransferFrom";
  public static final String TRANSACTIONS_FILTER = "Transactions";
  public static final String BILL_PAY_FILTER = "BillPay";
  public static final String EXTERNAL_TRANSFER_FROM_FILTER = "ExternalTransferFrom";
  public static final String EXTERNAL_TRANSFER_TO_FILTER = "ExternalTransferTo";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AccountFilters
 * JD-Core Version:    0.7.0.1
 */