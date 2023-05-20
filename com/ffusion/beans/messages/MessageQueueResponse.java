package com.ffusion.beans.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.Sortable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.Collator;
import java.util.Date;

public class MessageQueueResponse
  extends ExtendABean
  implements Sortable, Comparable, Serializable
{
  public static final String QueueID = "QueueID";
  public static final String ResponseID = "ResponseID";
  public static final String ResponseName = "ResponseName";
  public static final String ResponseText = "ResponseText";
  public static final String ModifiedDate = "ModifiedDate";
  public static final String ModifierId = "ModifierId";
  public static final String ModifierName = "ModifierName";
  private String sJ;
  private String sM;
  private String sN;
  private String sL;
  private DateTime sI;
  private String sK;
  private String sH;
  protected String trackingID;
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public String getQueueID()
  {
    return this.sJ;
  }
  
  public String getResponseID()
  {
    return this.sM;
  }
  
  public String getResponseName()
  {
    return this.sN;
  }
  
  public String getResponseText()
  {
    return this.sL;
  }
  
  public String getModifierId()
  {
    return this.sK;
  }
  
  public String getModifierName()
  {
    return this.sH;
  }
  
  public void setQueueID(String paramString)
  {
    this.sJ = paramString;
  }
  
  public void setResponseID(String paramString)
  {
    this.sM = paramString;
  }
  
  public void setResponseName(String paramString)
  {
    this.sN = paramString;
  }
  
  public void setResponseText(String paramString)
  {
    this.sL = paramString;
  }
  
  public void setModifierId(String paramString)
  {
    this.sK = paramString;
  }
  
  public void setModifierName(String paramString)
  {
    this.sH = paramString;
  }
  
  public void setModifiedDate(DateTime paramDateTime)
  {
    this.sI = paramDateTime;
  }
  
  public void setModifiedDate(String paramString)
  {
    try
    {
      if (this.sI == null) {
        this.sI = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.sI.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setModifiedDate(Timestamp paramTimestamp)
  {
    if (this.sI == null) {
      this.sI = new DateTime(this.locale);
    }
    this.sI.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public DateTime getModifiedDateValue()
  {
    return this.sI;
  }
  
  public String getModifiedDate()
  {
    if (this.sI != null)
    {
      this.sI.setFormat(this.datetype);
      return this.sI.toString();
    }
    return null;
  }
  
  public Timestamp getModifiedDateAsTimestamp()
  {
    return new Timestamp(this.sI.getTime().getTime());
  }
  
  public void set(MessageQueueResponse paramMessageQueueResponse)
  {
    setQueueID(paramMessageQueueResponse.getQueueID());
    setResponseID(paramMessageQueueResponse.getResponseID());
    setResponseName(paramMessageQueueResponse.getResponseName());
    setResponseText(paramMessageQueueResponse.getResponseText());
    if (paramMessageQueueResponse.getModifiedDateValue() != null) {
      setModifiedDate((DateTime)paramMessageQueueResponse.getModifiedDateValue().clone());
    } else {
      setModifiedDate((DateTime)null);
    }
    setModifierId(paramMessageQueueResponse.getModifierId());
    setModifierName(paramMessageQueueResponse.getModifierName());
    super.set(paramMessageQueueResponse);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ResponseID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    MessageQueueResponse localMessageQueueResponse = (MessageQueueResponse)paramObject;
    int i = 0;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("QueueID")) && (getQueueID() != null) && (localMessageQueueResponse.getQueueID() != null)) {
      i = localCollator.compare(getQueueID(), localMessageQueueResponse.getQueueID());
    } else if ((paramString.equals("ResponseID")) && (getResponseID() != null) && (localMessageQueueResponse.getResponseID() != null)) {
      i = localCollator.compare(getResponseID(), localMessageQueueResponse.getResponseID());
    } else if ((paramString.equals("ResponseName")) && (getResponseName() != null) && (localMessageQueueResponse.getResponseName() != null)) {
      i = localCollator.compare(getResponseName(), localMessageQueueResponse.getResponseName());
    } else if ((paramString.equals("ResponseText")) && (getResponseText() != null) && (localMessageQueueResponse.getResponseText() != null)) {
      i = localCollator.compare(getResponseText(), localMessageQueueResponse.getResponseText());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageQueueResponse
 * JD-Core Version:    0.7.0.1
 */