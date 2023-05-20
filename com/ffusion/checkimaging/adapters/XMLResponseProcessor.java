package com.ffusion.checkimaging.adapters;

import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.util.XMLTag;
import java.util.ArrayList;
import java.util.HashMap;

class XMLResponseProcessor
  implements CheckImageDefines, CheckImageTags
{
  protected XMLTag tagResponsePackage;
  protected boolean imagesRequested = true;
  
  public XMLResponseProcessor() {}
  
  public XMLResponseProcessor(String paramString1, String paramString2)
  {
    this.imagesRequested = paramString1.equalsIgnoreCase("both");
    A(paramString2);
  }
  
  public XMLResponseProcessor(String paramString)
  {
    A(paramString);
  }
  
  private void A(String paramString)
  {
    this.tagResponsePackage = new XMLTag(true);
    try
    {
      this.tagResponsePackage.build(paramString);
    }
    catch (Throwable localThrowable)
    {
      this.tagResponsePackage = null;
    }
  }
  
  public boolean isValidXML()
  {
    return this.tagResponsePackage != null;
  }
  
  public int processResponsePackage(ImageResults paramImageResults)
    throws Exception
  {
    int i = 0;
    int j = 0;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    if (this.tagResponsePackage == null) {
      throw new Exception("processResponsePackage: No responsePackage XML to process");
    }
    localHashMap1 = this.tagResponsePackage.getTagHashMap();
    paramImageResults.setPackageId((String)localHashMap1.get("requestPackageId"));
    paramImageResults.setStatus((String)localHashMap1.get("status"));
    XMLTag localXMLTag1 = this.tagResponsePackage.getContainedTag("statusResponse");
    if (localXMLTag1 == null) {
      localXMLTag1 = this.tagResponsePackage.getContainedTag("response");
    }
    if (localXMLTag1 == null) {
      throw new Exception("processResponsePackage: Missing response tags");
    }
    localHashMap2 = localXMLTag1.getTagHashMap();
    paramImageResults.setReturnCode((String)localHashMap2.get("returnCode"));
    paramImageResults.setReasonCode((String)localHashMap2.get("reasonCode"));
    try
    {
      i = Integer.parseInt(paramImageResults.getReturnCode());
      j = Integer.parseInt(paramImageResults.getReasonCode());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      i = 1;
      j = 1;
    }
    paramImageResults.setUserRequestId((String)localHashMap2.get("userRequestId"));
    paramImageResults.setDescription((String)localHashMap2.get("description"));
    paramImageResults.setMessages((String)localHashMap2.get("messages"));
    if ((paramImageResults.getStatus().equals("Complete")) || (paramImageResults.getStatus().equals("Error")))
    {
      if (paramImageResults.getStatus().equals("Complete"))
      {
        paramImageResults.setSuccessfulItems((String)localHashMap2.get("successfulItems"));
        paramImageResults.setTotalItems((String)localHashMap2.get("totalItems"));
      }
      ArrayList localArrayList = localXMLTag1.getContainedTagList();
      for (int k = 0; k < localArrayList.size(); k++)
      {
        XMLTag localXMLTag2 = (XMLTag)localArrayList.get(k);
        if (localXMLTag2.getTagName().equalsIgnoreCase("itemDetail")) {
          processItemDetail(localXMLTag2, paramImageResults.create());
        }
      }
    }
    return i;
  }
  
  protected int processItemDetail(XMLTag paramXMLTag, ImageResult paramImageResult)
  {
    int i = 0;
    int j = 0;
    HashMap localHashMap = new HashMap();
    localHashMap = paramXMLTag.getTagHashMap();
    paramImageResult.setImageHandle((String)localHashMap.get("handle"));
    paramImageResult.setStorageTier((String)localHashMap.get("storageTier"));
    paramImageResult.setReturnCode((String)localHashMap.get("returnCode"));
    paramImageResult.setReasonCode((String)localHashMap.get("reasonCode"));
    try
    {
      i = Integer.parseInt(paramImageResult.getReturnCode());
      j = Integer.parseInt(paramImageResult.getReasonCode());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      i = 1;
      j = 1;
    }
    if (this.imagesRequested) {
      switch (i)
      {
      case 0: 
        paramImageResult.setStatus("AVAILABLE");
        break;
      case 4: 
        if (j == 2) {
          paramImageResult.setStatus("PENDING");
        } else {
          paramImageResult.setStatus("NOT REQUESTED");
        }
        break;
      default: 
        paramImageResult.setStatus("NOT REQUESTED");
      }
    }
    Object localObject1 = null;
    Object localObject2 = null;
    ArrayList localArrayList = paramXMLTag.getContainedTagList();
    for (int k = 0; k < localArrayList.size(); k++)
    {
      XMLTag localXMLTag = (XMLTag)localArrayList.get(k);
      try
      {
        if (localXMLTag.getTagName().equalsIgnoreCase("index")) {
          processIndex(localXMLTag, paramImageResult);
        }
        if (localXMLTag.getTagName().equalsIgnoreCase("view")) {
          processView(localXMLTag, paramImageResult);
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace(System.out);
      }
    }
    return i;
  }
  
  protected void processIndex(XMLTag paramXMLTag, ImageResult paramImageResult)
    throws Exception
  {
    String str1 = null;
    String str2 = null;
    Object localObject1 = paramXMLTag.getAttribute("name");
    Object localObject2 = paramXMLTag.getAttribute("value");
    if (((localObject1 instanceof String)) && ((localObject2 instanceof String)))
    {
      str1 = (String)localObject1;
      str2 = (String)localObject2;
    }
    else
    {
      throw new Exception("processIndex: Name or Value attributes not strings");
    }
    if (str1.equals("Cycle Date")) {
      paramImageResult.setPostingDate(str2);
    } else if (str1.equals("Amount")) {
      paramImageResult.setAmount(str2);
    } else if (str1.equals("Credit/Debit")) {
      paramImageResult.setDrCr(str2);
    } else if (str1.equals("MICR Account")) {
      paramImageResult.setAccountID(str2);
    } else if (str1.equals("Serial")) {
      paramImageResult.setCheckNumber(str2);
    } else if (str1.equals("Routing")) {
      paramImageResult.setRoutingTransitNumber(str2);
    } else if (str1.equals("Process Control")) {
      paramImageResult.setTransCode(str2);
    } else if (str1.equals("Sequence Number")) {
      paramImageResult.setSequenceNumber(str2);
    } else if (str1.equals("Capture Sequence")) {
      paramImageResult.setCaptureSequence(str2);
    } else if (str1.equals("CPCS Entry")) {
      paramImageResult.setEntryNumber(str2);
    } else if (str1.equals("Tracer")) {
      paramImageResult.setTracerNumber(str2);
    } else if (str1.equals("Deposit Sequence")) {
      paramImageResult.setDepositTraceID(str2);
    } else if (str1.equals("Bank ID")) {
      paramImageResult.setBankId(str2);
    }
  }
  
  protected void processView(XMLTag paramXMLTag, ImageResult paramImageResult)
  {
    String str1 = paramXMLTag.getContainedTag("viewType").getTagContent();
    String str2 = paramXMLTag.getContainedTag("imageKey").getTagContent();
    if ((str1.equals("FBW")) || (str1.equals("FGS"))) {
      paramImageResult.setFrontViewHandle(str2);
    } else if ((str1.equals("BGS")) || (str1.equals("BBW"))) {
      paramImageResult.setBackViewHandle(str2);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.checkimaging.adapters.XMLResponseProcessor
 * JD-Core Version:    0.7.0.1
 */