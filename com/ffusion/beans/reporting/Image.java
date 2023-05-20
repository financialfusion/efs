package com.ffusion.beans.reporting;

import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.ExtendABean;

public class Image
  extends ExtendABean
{
  private byte[] jdField_do = null;
  private String jdField_if = null;
  private ILocalizable a = null;
  public static final String VAL_IMAGE_JPG = "image/jpeg";
  public static final String VAL_IMAGE_GIF = "image/gif";
  
  public Image() {}
  
  public Image(byte[] paramArrayOfByte, String paramString, ILocalizable paramILocalizable)
  {
    this.jdField_do = paramArrayOfByte;
    this.jdField_if = paramString;
    this.a = paramILocalizable;
  }
  
  public byte[] getImageData()
  {
    return this.jdField_do;
  }
  
  public String getImageType()
  {
    return this.jdField_if;
  }
  
  public ILocalizable getAlternateText()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.Image
 * JD-Core Version:    0.7.0.1
 */