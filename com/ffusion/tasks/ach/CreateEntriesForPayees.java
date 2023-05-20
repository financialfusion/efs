package com.ffusion.tasks.ach;

import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateEntriesForPayees
  extends BaseTask
  implements Task
{
  private String Aa;
  private String Ab = "ACHBatch";
  private String Ac = "ACHPayees";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
    String str = this.successURL;
    this.error = 0;
    if (this.Ab == null)
    {
      this.error = 16002;
      str = this.taskErrorURL;
    }
    else
    {
      ACHBatch localACHBatch = null;
      Object localObject1 = localHttpSession.getAttribute(this.Ab);
      if ((localObject1 instanceof ACHBatch))
      {
        localACHBatch = (ACHBatch)localObject1;
      }
      else if ((localObject1 instanceof FundsTransactionTemplate))
      {
        FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)localObject1;
        localACHBatch = (ACHBatch)localFundsTransactionTemplate.getFundsTransaction();
      }
      if (localACHBatch != null)
      {
        int i = 0;
        int j = 0;
        int k;
        try
        {
          i = Integer.parseInt((String)localACHBatch.get("TEMP_ENTRY_ID"));
        }
        catch (Exception localException)
        {
          localObject2 = localACHBatch.getACHEntries();
          k = 0;
        }
        Object localObject3;
        while (k < ((ACHEntries)localObject2).size())
        {
          localObject3 = ((ACHEntry)((ACHEntries)localObject2).get(k)).getID();
          try
          {
            j = Integer.parseInt((String)localObject3);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            j = 0;
          }
          if (j > i) {
            i = j;
          }
          k++;
        }
        StringTokenizer localStringTokenizer = new StringTokenizer(this.Aa, ",");
        Object localObject2 = null;
        while (localStringTokenizer.hasMoreTokens())
        {
          localObject2 = localStringTokenizer.nextToken();
          if (localObject2 != null)
          {
            ACHEntry localACHEntry = (ACHEntry)localACHBatch.create();
            localObject3 = (ACHPayees)localHttpSession.getAttribute(this.Ac);
            if ((localObject3 != null) && (((ACHPayees)localObject3).getByID((String)localObject2) != null)) {
              localACHEntry.setAchPayee(((ACHPayees)localObject3).getByID((String)localObject2));
            }
            localACHEntry.setAmount("0");
            localACHEntry.setActive("true");
            localACHEntry.setID(String.valueOf(++i));
          }
        }
        localACHBatch.put("TEMP_ENTRY_ID", String.valueOf(i));
      }
      else
      {
        this.error = 16002;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public final void setPayeeIDs(String paramString)
  {
    this.Aa = paramString;
  }
  
  public final void setBatchName(String paramString)
  {
    this.Ab = paramString;
  }
  
  public void setPayeesCollection(String paramString)
  {
    this.Ac = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.CreateEntriesForPayees
 * JD-Core Version:    0.7.0.1
 */