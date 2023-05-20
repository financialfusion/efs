package com.ffusion.beans.checkimaging;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class ImageResults
  extends FilteredList
{
  protected String packageId;
  protected String status;
  protected String userRequestId;
  protected String description;
  protected String returnCode;
  protected String reasonCode;
  protected String messages;
  protected String successfulItems;
  protected String totalItems;
  public static final String IMAGE_RESULTS = "IMAGERESULTS";
  
  public ImageResults() {}
  
  public ImageResults(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ImageResult create()
  {
    ImageResult localImageResult = new ImageResult(this.locale);
    super.add(localImageResult);
    return localImageResult;
  }
  
  public boolean add(Object paramObject)
  {
    ImageResult localImageResult = (ImageResult)paramObject;
    localImageResult.setLocale(this.locale);
    return super.add(localImageResult);
  }
  
  public String xmlImagesRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ImageResult localImageResult = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localImageResult = (ImageResult)localIterator.next();
      localStringBuffer.append(localImageResult.xmlImageRequest());
    }
    return localStringBuffer.toString();
  }
  
  public ImageResult getByHandle(String paramString)
  {
    Iterator localIterator = null;
    ImageResult localImageResult1 = null;
    ImageResult localImageResult2 = null;
    localIterator = iterator();
    while (localIterator.hasNext())
    {
      localImageResult1 = (ImageResult)localIterator.next();
      if (localImageResult1.getImageHandle().equals(paramString)) {
        localImageResult2 = localImageResult1;
      }
    }
    return localImageResult2;
  }
  
  public void removeByID(String paramString)
  {
    Iterator localIterator = null;
    ImageResult localImageResult1 = null;
    ImageResult localImageResult2 = null;
    localIterator = iterator();
    while (localIterator.hasNext())
    {
      localImageResult1 = (ImageResult)localIterator.next();
      if (localImageResult1.getImageHandle().equals(paramString)) {
        localImageResult2 = localImageResult1;
      }
    }
    if (localImageResult2 != null) {
      remove(localImageResult2);
    }
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = null;
    ImageResult localImageResult = null;
    localIterator = iterator();
    XMLHandler.appendBeginTag(localStringBuffer, "IMAGERESULTS");
    while (localIterator.hasNext())
    {
      localImageResult = (ImageResult)localIterator.next();
      localStringBuffer.append(localImageResult.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "IMAGERESULTS");
    return localStringBuffer.toString();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(), "IMAGERESULTS");
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setPackageId(String paramString)
  {
    this.packageId = paramString;
  }
  
  public String getPackageId()
  {
    return this.packageId;
  }
  
  public void setUserRequestId(String paramString)
  {
    this.userRequestId = paramString;
  }
  
  public String getUserRequestId()
  {
    return this.userRequestId;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setReturnCode(String paramString)
  {
    this.returnCode = paramString;
  }
  
  public String getReturnCode()
  {
    return this.returnCode;
  }
  
  public void setReasonCode(String paramString)
  {
    this.reasonCode = paramString;
  }
  
  public String getReasonCode()
  {
    return this.reasonCode;
  }
  
  public void setMessages(String paramString)
  {
    this.messages = paramString;
  }
  
  public String getMessages()
  {
    return this.messages;
  }
  
  public void setSuccessfulItems(String paramString)
  {
    this.successfulItems = paramString;
  }
  
  public String getSuccessfulItems()
  {
    return this.successfulItems;
  }
  
  public void setTotalItems(String paramString)
  {
    this.totalItems = paramString;
  }
  
  public String getTotalItems()
  {
    return this.totalItems;
  }
  
  public class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("IMAGERESULT")) {
        ImageResults.this.create().continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.checkimaging.ImageResults
 * JD-Core Version:    0.7.0.1
 */