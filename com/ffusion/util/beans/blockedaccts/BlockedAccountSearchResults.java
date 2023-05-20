package com.ffusion.util.beans.blockedaccts;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class BlockedAccountSearchResults
  extends FilteredList
{
  public BlockedAccountSearchResult getByAccountNumberAndRoutingNumberAndUserName(String paramString1, String paramString2, String paramString3)
  {
    BlockedAccountSearchResult localBlockedAccountSearchResult1 = null;
    BlockedAccountSearchResult localBlockedAccountSearchResult2 = null;
    int i = -1;
    Iterator localIterator = null;
    if ((paramString1 != null) && (paramString2 != null))
    {
      if (paramString3 == null) {
        i = 0;
      }
      if (!super.isEmpty())
      {
        localIterator = super.iterator();
        while ((localIterator.hasNext()) && (localBlockedAccountSearchResult2 == null))
        {
          localBlockedAccountSearchResult1 = (BlockedAccountSearchResult)localIterator.next();
          if ((localBlockedAccountSearchResult1 != null) && (paramString2.equals(localBlockedAccountSearchResult1.getRoutingNumber())) && (paramString1.equals(localBlockedAccountSearchResult1.getAccountNumber()))) {
            if (i == -1)
            {
              if (paramString3.equals(localBlockedAccountSearchResult1.getUserName())) {
                localBlockedAccountSearchResult2 = localBlockedAccountSearchResult1;
              }
            }
            else if (i == localBlockedAccountSearchResult1.getUserDirectoryID()) {
              localBlockedAccountSearchResult2 = localBlockedAccountSearchResult1;
            }
          }
        }
      }
    }
    return localBlockedAccountSearchResult2;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResults
 * JD-Core Version:    0.7.0.1
 */