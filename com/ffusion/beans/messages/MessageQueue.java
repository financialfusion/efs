package com.ffusion.beans.messages;

import com.ffusion.beans.CollectionElement;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.applications.ProductI18N;
import com.ffusion.beans.applications.StatusI18N;
import com.ffusion.util.Filterable;
import com.ffusion.util.Sortable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.Collator;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MessageQueue
  extends MessageQueueI18N
  implements Sortable, Filterable, Comparable, Serializable, CollectionElement
{
  public static final String PRODUCT_ID = "PRODUCT_ID";
  public static final String STATUS_ID = "STATUS_ID";
  public static final String QUEUE_MEMBER_ACTIVE = "0";
  public static final String QUEUE_MEMBER_INACTIVE = "1";
  public static final String QUEUE_MSG = "0";
  public static final String QUEUE_APP = "1";
  public static final String QUEUE_MSG_NAME = "message";
  public static final String QUEUE_APP_NAME = "application";
  public static final String QueueID = "QueueID";
  public static final String QueueName = "QueueName";
  public static final String QueueType = "QueueType";
  public static final String QueueStatus = "QueueStatus";
  public static final String QueueStatusId = "QueueStatusId";
  public static final String QueueStatusName = "QueueStatusName";
  public static final String QueueProductID = "QueueProductID";
  public static final String QueueProductDesc = "QueueProductDesc";
  public static final String QueueAutoReplyText = "QueueAutoReplyText";
  public static final String QueueAutoReplySubject = "QueueAutoReplySubject";
  public static final String QueueAutoReplyEmployeeId = "QueueAutoReplyEmployeeId";
  public static final String QueueAutoReplyEmailAddress = "QueueAutoReplyEmailAddress";
  public static final String QueueModifiedDate = "ModifiedDate";
  private String qk = "0";
  private String ql = "";
  private String qs = "";
  private String qm = "N/A";
  private String qA = "0";
  private String qB = "N/A";
  private String qo = "0";
  private String qn = "N/A";
  private MessageQueueMembers qw = new MessageQueueMembers();
  private MessageQueueMembers qh = new MessageQueueMembers();
  private MessageQueueResponses qy = new MessageQueueResponses();
  private boolean qp = true;
  private boolean qx = true;
  protected String trackingID;
  private String qz = "";
  private DateTime qt;
  private HashMap qq = null;
  public static final String defaultLanguage = "en_US";
  private String qv = "en_US";
  private String qu = "en_US";
  private static String qi = "MessageQueue";
  private HashMap qj = new HashMap();
  private HashMap qr = new HashMap();
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public String getId()
  {
    return getQueueID();
  }
  
  public String getUniqueIDName()
  {
    return qi + getQueueID();
  }
  
  public String getQueueName()
  {
    if (this.qu == null) {
      return super.getQueueName();
    }
    return getQueueName(this.qu);
  }
  
  public String getQueueName(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equalsIgnoreCase("en_US")) {
        return super.getQueueName();
      }
      if (this.qq == null) {
        return null;
      }
      MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.qq.get(paramString);
      if (localMessageQueueI18N == null) {
        return null;
      }
      return localMessageQueueI18N.getQueueName();
    }
    return null;
  }
  
  public String getFirstAvailableQueueName()
  {
    String str1 = getQueueName();
    if ((str1 != null) && (str1.length() >= 0)) {
      return str1;
    }
    str1 = getQueueName("en_US");
    if ((str1 != null) && (str1.length() >= 0)) {
      return str1;
    }
    Iterator localIterator = getLanguages();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        str1 = getQueueName(str2);
        if ((str1 != null) && (str1.length() > 0)) {
          return str1;
        }
      }
    }
    return "";
  }
  
  public void setQueueName(String paramString)
  {
    if (this.qu != null) {
      setQueueName(this.qu, paramString);
    } else {
      super.setQueueName(paramString);
    }
  }
  
  public void setQueueName(String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      if (paramString1.equalsIgnoreCase("en_US"))
      {
        super.setQueueName(paramString2);
      }
      else
      {
        if (this.qq == null) {
          this.qq = new HashMap();
        }
        MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.qq.get(paramString1);
        if (localMessageQueueI18N != null)
        {
          localMessageQueueI18N.setQueueName(paramString2);
        }
        else
        {
          localMessageQueueI18N = new MessageQueueI18N();
          localMessageQueueI18N.setQueueID(getQueueID());
          localMessageQueueI18N.setLanguage(paramString1);
          localMessageQueueI18N.setQueueName(paramString2);
          this.qq.put(paramString1, localMessageQueueI18N);
        }
      }
    }
  }
  
  public String getQueueAutoReplySubject()
  {
    if (this.qu == null) {
      return super.getQueueAutoReplySubject();
    }
    return getQueueAutoReplySubject(this.qu);
  }
  
  public String getQueueAutoReplySubject(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equalsIgnoreCase("en_US")) {
        return super.getQueueAutoReplySubject();
      }
      if (this.qq == null) {
        return null;
      }
      MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.qq.get(paramString);
      if (localMessageQueueI18N == null) {
        return null;
      }
      return localMessageQueueI18N.getQueueAutoReplySubject();
    }
    return null;
  }
  
  public void setQueueAutoReplySubject(String paramString)
  {
    if (this.qu != null) {
      setQueueAutoReplySubject(this.qu, paramString);
    } else {
      super.setQueueAutoReplySubject(paramString);
    }
  }
  
  public void setQueueAutoReplySubject(String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      if (paramString1.equalsIgnoreCase("en_US"))
      {
        super.setQueueAutoReplySubject(paramString2);
      }
      else
      {
        if (this.qq == null) {
          this.qq = new HashMap();
        }
        MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.qq.get(paramString1);
        if (localMessageQueueI18N != null)
        {
          localMessageQueueI18N.setQueueAutoReplySubject(paramString2);
        }
        else
        {
          localMessageQueueI18N = new MessageQueueI18N();
          localMessageQueueI18N.setQueueID(getQueueID());
          localMessageQueueI18N.setLanguage(paramString1);
          localMessageQueueI18N.setQueueAutoReplySubject(paramString2);
          this.qq.put(paramString1, localMessageQueueI18N);
        }
      }
    }
  }
  
  public String getQueueAutoReplyText()
  {
    if (this.qu == null) {
      return super.getQueueAutoReplyText();
    }
    return getQueueAutoReplyText(this.qu);
  }
  
  public String getQueueAutoReplyText(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equalsIgnoreCase("en_US")) {
        return super.getQueueAutoReplyText();
      }
      if (this.qq == null) {
        return null;
      }
      MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.qq.get(paramString);
      if (localMessageQueueI18N == null) {
        return null;
      }
      return localMessageQueueI18N.getQueueAutoReplyText();
    }
    return null;
  }
  
  public void setQueueAutoReplyText(String paramString)
  {
    if (this.qu != null) {
      setQueueAutoReplyText(this.qu, paramString);
    } else {
      super.setQueueAutoReplyText(paramString);
    }
  }
  
  public void setQueueAutoReplyText(String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      if (paramString1.equalsIgnoreCase("en_US"))
      {
        super.setQueueAutoReplyText(paramString2);
      }
      else
      {
        if (this.qq == null) {
          this.qq = new HashMap();
        }
        MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.qq.get(paramString1);
        if (localMessageQueueI18N != null)
        {
          localMessageQueueI18N.setQueueAutoReplyText(paramString2);
        }
        else
        {
          localMessageQueueI18N = new MessageQueueI18N();
          localMessageQueueI18N.setQueueID(getQueueID());
          localMessageQueueI18N.setLanguage(paramString1);
          localMessageQueueI18N.setQueueAutoReplyText(paramString2);
          this.qq.put(paramString1, localMessageQueueI18N);
        }
      }
    }
  }
  
  public String getDescription()
  {
    if (this.qu == null) {
      return super.getDescription();
    }
    return getDescription(this.qu);
  }
  
  public String getDescription(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equalsIgnoreCase("en_US")) {
        return super.getDescription();
      }
      if (this.qq == null) {
        return null;
      }
      MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.qq.get(paramString);
      if (localMessageQueueI18N == null) {
        return null;
      }
      return localMessageQueueI18N.getDescription();
    }
    return null;
  }
  
  public void setDescription(String paramString)
  {
    if (this.qu != null) {
      setDescription(this.qu, paramString);
    } else {
      super.setDescription(paramString);
    }
  }
  
  public void setDescription(String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      if (paramString1.equalsIgnoreCase("en_US"))
      {
        super.setDescription(paramString2);
      }
      else
      {
        if (this.qq == null) {
          this.qq = new HashMap();
        }
        MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.qq.get(paramString1);
        if (localMessageQueueI18N != null)
        {
          localMessageQueueI18N.setDescription(paramString2);
        }
        else
        {
          localMessageQueueI18N = new MessageQueueI18N();
          localMessageQueueI18N.setQueueID(getQueueID());
          localMessageQueueI18N.setLanguage(paramString1);
          localMessageQueueI18N.setDescription(paramString2);
          this.qq.put(paramString1, localMessageQueueI18N);
        }
      }
    }
  }
  
  public Iterator getLanguages()
  {
    if (this.qq == null) {
      return null;
    }
    return this.qq.keySet().iterator();
  }
  
  public String getQueueType()
  {
    return this.qk;
  }
  
  public String getQueueTypeName()
  {
    if (this.qk.equals("1")) {
      return "application";
    }
    return "message";
  }
  
  public String getQueueStatus()
  {
    return this.ql;
  }
  
  public String getQueueStatusID()
  {
    return this.qA;
  }
  
  public String getQueueStatusName(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("en_US"))) {
      return this.qB;
    }
    StatusI18N localStatusI18N = (StatusI18N)this.qr.get(paramString);
    if (localStatusI18N == null) {
      return null;
    }
    return localStatusI18N.getName();
  }
  
  public String getQueueStatusName()
  {
    return getQueueStatusName(this.qu);
  }
  
  public String getQueueProductID()
  {
    return this.qs;
  }
  
  public String getQueueProductDesc(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("en_US"))) {
      return this.qm;
    }
    ProductI18N localProductI18N = (ProductI18N)this.qj.get(paramString);
    if (localProductI18N == null) {
      return null;
    }
    return localProductI18N.getTitle();
  }
  
  public String getQueueProductDesc()
  {
    return getQueueProductDesc(this.qu);
  }
  
  public String getQueueAutoReplyEmailAddress()
  {
    return this.qn;
  }
  
  public String getQueueAutoReplyEmployeeID()
  {
    return this.qo;
  }
  
  public MessageQueueMembers getActiveQueueMembers()
  {
    return this.qw;
  }
  
  public MessageQueueMembers getInactiveQueueMembers()
  {
    return this.qh;
  }
  
  public MessageQueueResponses getQueueResponses()
  {
    return this.qy;
  }
  
  public String getBankId()
  {
    return this.qz;
  }
  
  public void setQueueType(String paramString)
  {
    this.qk = paramString;
  }
  
  public void setQueueStatus(String paramString)
  {
    this.ql = paramString;
  }
  
  public void setQueueStatusID(String paramString)
  {
    this.qA = paramString;
  }
  
  public void setQueueStatusName(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.equalsIgnoreCase("en_US"))) {
      this.qB = paramString2;
    }
    StatusI18N localStatusI18N = (StatusI18N)this.qr.get(paramString1);
    if (localStatusI18N == null)
    {
      localStatusI18N = new StatusI18N();
      localStatusI18N.setLanguage(paramString1);
      this.qr.put(paramString1, localStatusI18N);
    }
    localStatusI18N.setName(paramString2);
  }
  
  public void setQueueStatusName(String paramString)
  {
    this.qB = paramString;
  }
  
  public void setQueueProductID(String paramString)
  {
    this.qs = paramString;
  }
  
  public void setQueueProductDesc(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.equalsIgnoreCase("en_US"))) {
      this.qm = paramString2;
    }
    ProductI18N localProductI18N = (ProductI18N)this.qj.get(paramString1);
    if (localProductI18N == null)
    {
      localProductI18N = new ProductI18N();
      localProductI18N.setLanguage(paramString1);
      this.qj.put(paramString1, localProductI18N);
    }
    localProductI18N.setTitle(paramString2);
  }
  
  public void setQueueProductDesc(String paramString)
  {
    this.qm = paramString;
  }
  
  public void setQueueAutoReplyEmailAddress(String paramString)
  {
    this.qn = paramString;
  }
  
  public void setQueueAutoReplyEmployeeID(String paramString)
  {
    this.qo = paramString;
  }
  
  public void setActiveQueueMembers(MessageQueueMembers paramMessageQueueMembers)
  {
    this.qw = paramMessageQueueMembers;
  }
  
  public void setInactiveQueueMembers(MessageQueueMembers paramMessageQueueMembers)
  {
    this.qh = paramMessageQueueMembers;
  }
  
  public void setQueueResponses(MessageQueueResponses paramMessageQueueResponses)
  {
    this.qy = paramMessageQueueResponses;
  }
  
  public void setBankId(String paramString)
  {
    this.qz = paramString;
  }
  
  public void setModifiedDate(DateTime paramDateTime)
  {
    this.qt = paramDateTime;
  }
  
  public void setModifiedDate(String paramString)
  {
    try
    {
      if (this.qt == null) {
        this.qt = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.qt.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setModifiedDate(Timestamp paramTimestamp)
  {
    if (this.qt == null) {
      this.qt = new DateTime(this.locale);
    }
    this.qt.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public DateTime getModifiedDateValue()
  {
    return this.qt;
  }
  
  public String getModifiedDate()
  {
    if (this.qt != null)
    {
      this.qt.setFormat(this.datetype);
      return this.qt.toString();
    }
    return null;
  }
  
  public Timestamp getModifiedDateAsTimestamp()
  {
    return new Timestamp(this.qt.getTime().getTime());
  }
  
  public void set(MessageQueue paramMessageQueue)
  {
    setQueueAutoReplyEmailAddress(paramMessageQueue.getQueueAutoReplyEmailAddress());
    setQueueAutoReplyEmployeeID(paramMessageQueue.getQueueAutoReplyEmployeeID());
    setQueueID(paramMessageQueue.getQueueID());
    setQueueProductID(paramMessageQueue.getQueueProductID());
    setQueueProductDesc(paramMessageQueue.getQueueProductDesc());
    setQueueStatus(paramMessageQueue.getQueueStatus());
    setQueueStatusID(paramMessageQueue.getQueueStatusID());
    setQueueStatusName(paramMessageQueue.getQueueStatusName());
    setQueueType(paramMessageQueue.getQueueType());
    setIsConsumer(paramMessageQueue.getIsConsumer());
    setIsCorporate(paramMessageQueue.getIsCorporate());
    setQueueResponses((MessageQueueResponses)paramMessageQueue.getQueueResponses().clone());
    setActiveQueueMembers((MessageQueueMembers)paramMessageQueue.getActiveQueueMembers().clone());
    setInactiveQueueMembers((MessageQueueMembers)paramMessageQueue.getInactiveQueueMembers().clone());
    setBankId(paramMessageQueue.getBankId());
    setQueueName("en_US", paramMessageQueue.getQueueName("en_US"));
    setDescription("en_US", paramMessageQueue.getDescription("en_US"));
    setQueueAutoReplySubject("en_US", paramMessageQueue.getQueueAutoReplySubject("en_US"));
    setQueueAutoReplyText("en_US", paramMessageQueue.getQueueAutoReplyText("en_US"));
    if (paramMessageQueue.getModifiedDateValue() != null) {
      setModifiedDate((DateTime)paramMessageQueue.getModifiedDateValue().clone());
    } else {
      setModifiedDate((DateTime)null);
    }
    setSearchLanguage(paramMessageQueue.getSearchLanguage());
    setCurrentLanguage(paramMessageQueue.getCurrentLanguage());
    Iterator localIterator = paramMessageQueue.getLanguages();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        setQueueName(str, paramMessageQueue.getQueueName(str));
        setDescription(str, paramMessageQueue.getDescription(str));
        setQueueAutoReplySubject(str, paramMessageQueue.getQueueAutoReplySubject(str));
        setQueueAutoReplyText(str, paramMessageQueue.getQueueAutoReplyText(str));
      }
    }
    super.set(paramMessageQueue);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "QueueID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    MessageQueue localMessageQueue = (MessageQueue)paramObject;
    int i = 0;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("QueueID")) && (getQueueID() != null) && (localMessageQueue.getQueueID() != null)) {
      i = localCollator.compare(getQueueID(), localMessageQueue.getQueueID());
    } else if ((paramString.equals("QueueName")) && (getQueueName() != null) && (localMessageQueue.getQueueName() != null)) {
      i = localCollator.compare(getQueueName(), localMessageQueue.getQueueName());
    } else if ((paramString.equals("QueueType")) && (getQueueType() != null) && (localMessageQueue.getQueueType() != null)) {
      i = localCollator.compare(getQueueType(), localMessageQueue.getQueueType());
    } else if ((paramString.equals("QueueStatus")) && (getQueueStatus() != null) && (localMessageQueue.getQueueStatus() != null)) {
      i = localCollator.compare(getQueueStatus(), localMessageQueue.getQueueStatus());
    } else if ((paramString.equals("QueueStatusName")) && (getQueueStatusName() != null) && (localMessageQueue.getQueueStatusName() != null)) {
      i = localCollator.compare(getQueueStatusName(), localMessageQueue.getQueueStatusName());
    } else if ((paramString.equals("QueueProductDesc")) && (getQueueProductDesc() != null) && (localMessageQueue.getQueueProductDesc() != null)) {
      i = localCollator.compare(getQueueProductDesc().toLowerCase(), localMessageQueue.getQueueProductDesc().toLowerCase());
    } else if (paramString.equals("ModifiedDate"))
    {
      if (getModifiedDateValue() == null)
      {
        if (localMessageQueue.getModifiedDateValue() == null) {
          i = 0;
        } else {
          i = -1;
        }
      }
      else
      {
        if (localMessageQueue.getModifiedDateValue() == null) {
          return 1;
        }
        i = getModifiedDateValue().compare(localMessageQueue.getModifiedDateValue());
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equalsIgnoreCase("PRODUCT_ID")) && (getQueueProductID() != null)) {
      return isFilterable(getQueueProductID(), paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("STATUS_ID")) && (getQueueStatusID() != null)) {
      return isFilterable(getQueueStatusID(), paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("QueueType")) && (getQueueType() != null)) {
      return isFilterable(getQueueType(), paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("QueueID")) && (getQueueID() != null)) {
      return isFilterable(getQueueID(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getSearchLanguage()
  {
    return this.qv;
  }
  
  public void setSearchLanguage(String paramString)
  {
    if (paramString == null) {
      this.qv = null;
    } else if (paramString.length() == 0) {
      this.qv = null;
    } else {
      this.qv = paramString;
    }
  }
  
  public String getAutoReplyExists()
  {
    if (this.qv != null)
    {
      if (b(getQueueAutoReplyText(this.qv))) {
        return new String("true");
      }
    }
    else
    {
      if (b(getQueueAutoReplyText())) {
        return new String("true");
      }
      if (this.qq != null)
      {
        Iterator localIterator = this.qq.values().iterator();
        while (localIterator.hasNext())
        {
          MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)localIterator.next();
          if (b(localMessageQueueI18N.getQueueAutoReplyText())) {
            return new String("true");
          }
        }
      }
    }
    return new String("false");
  }
  
  private boolean b(String paramString)
  {
    return (paramString != null) && (paramString.length() > 0);
  }
  
  public String getCurrentLanguage()
  {
    return this.qu;
  }
  
  public void setCurrentLanguage(String paramString)
  {
    if (paramString == null) {
      this.qu = null;
    } else if (paramString.length() == 0) {
      this.qu = null;
    } else {
      this.qu = paramString;
    }
  }
  
  public boolean getIsConsumer()
  {
    return this.qx;
  }
  
  public boolean getIsCorporate()
  {
    return this.qp;
  }
  
  public void setIsConsumer(boolean paramBoolean)
  {
    this.qx = paramBoolean;
  }
  
  public void setIsCorporate(boolean paramBoolean)
  {
    this.qp = paramBoolean;
  }
  
  public void setIsConsumer(String paramString)
  {
    setIsConsumer(new Boolean(paramString).booleanValue());
  }
  
  public void setIsCorporate(String paramString)
  {
    setIsCorporate(new Boolean(paramString).booleanValue());
  }
  
  public String getIsCorporateString()
  {
    return "" + this.qp;
  }
  
  public String getIsConsumerString()
  {
    return "" + this.qx;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageQueue
 * JD-Core Version:    0.7.0.1
 */