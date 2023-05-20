package com.ffusion.jtf.taglib;

import com.ffusion.beans.user.UserLocale;
import com.ffusion.util.HTMLUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class GetL10NStringTag
  extends TagSupport
{
  public static final String RESOURCE_BUNDLE_ROOT = "com.ffusion.templates.";
  public static final String RESOURCE_BUNDLE_FILE = ".resources";
  protected String _rsrcFile;
  protected String _msgKey;
  protected String[] _parms = new String[5];
  protected String _assignTo;
  protected boolean _encode = true;
  protected boolean _encodeLeadingSpaces = false;
  protected boolean[] _encodeParams = { true, true, true, true, true };
  
  public int doEndTag()
    throws JspException
  {
    try
    {
      a("");
      String str1 = null;
      String str2 = propertyValue.Evaluate(this._rsrcFile, this.pageContext);
      String str3 = propertyValue.Evaluate(this._msgKey, this.pageContext);
      String str4 = propertyValue.Evaluate(this._assignTo, this.pageContext);
      String[] arrayOfString = new String[this._parms.length];
      for (int i = 0; i < this._parms.length; i++) {
        if (this._parms[i] != null)
        {
          arrayOfString[i] = propertyValue.Evaluate(this._parms[i], this.pageContext);
          if ((!this._encode) && (this._encodeParams[i] != 0)) {
            arrayOfString[i] = HTMLUtil.encode(arrayOfString[i]);
          }
        }
      }
      if (this._rsrcFile == null) {
        throw new NullPointerException("Missing rsrcFile attribute");
      }
      String str5 = "com.ffusion.templates." + str2 + ".resources";
      Locale localLocale = null;
      HttpSession localHttpSession = this.pageContext.getSession();
      UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
      if (localUserLocale != null) {
        localLocale = localUserLocale.getLocale();
      }
      if (localLocale == null)
      {
        localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
        if (localLocale == null) {
          localLocale = Locale.getDefault();
        }
      }
      if (this._msgKey == null) {
        throw new NullPointerException("Missing msgKey attribute");
      }
      String str6 = ResourceUtil.getString(str3, str5, localLocale);
      str1 = MessageFormat.format(str6, arrayOfString);
      try
      {
        if (str1 != null) {
          if (str4 == null)
          {
            if (this._encode) {
              str1 = HTMLUtil.encode(str1);
            }
            if (this._encodeLeadingSpaces) {
              str1 = HTMLUtil.encodeLeadingSpaces(str1);
            }
            this.pageContext.getOut().write(str1);
          }
          else
          {
            this.pageContext.setAttribute(str4, str1);
          }
        }
      }
      catch (IOException localIOException) {}
    }
    catch (Throwable localThrowable)
    {
      a(localThrowable);
    }
    return 6;
  }
  
  public void release()
  {
    this._rsrcFile = null;
    this._msgKey = null;
    for (int i = 0; i < this._parms.length; i++) {
      this._parms[i] = null;
    }
    super.release();
  }
  
  public String getMsgKey()
  {
    return this._msgKey;
  }
  
  public void setMsgKey(String paramString)
  {
    this._msgKey = paramString;
  }
  
  public String getParm0()
  {
    return this._parms[0];
  }
  
  public void setParm0(String paramString)
  {
    this._parms[0] = paramString;
  }
  
  public String getParm1()
  {
    return this._parms[1];
  }
  
  public void setParm1(String paramString)
  {
    this._parms[1] = paramString;
  }
  
  public String getParm2()
  {
    return this._parms[2];
  }
  
  public void setParm2(String paramString)
  {
    this._parms[2] = paramString;
  }
  
  public String getParm3()
  {
    return this._parms[3];
  }
  
  public void setParm3(String paramString)
  {
    this._parms[3] = paramString;
  }
  
  public void setParm4(String paramString)
  {
    this._parms[4] = paramString;
  }
  
  public String getRsrcFile()
  {
    return this._rsrcFile;
  }
  
  public void setRsrcFile(String paramString)
  {
    this._rsrcFile = paramString;
  }
  
  public void setAssignTo(String paramString)
  {
    this._assignTo = paramString;
  }
  
  private void a(String paramString)
  {
    if (DebugLog.getLogger().isLoggable(Level.FINEST)) {
      DebugLog.log(Level.FINEST, jdMethod_if(paramString));
    }
  }
  
  private void a(Throwable paramThrowable)
  {
    DebugLog.throwing(jdMethod_if("\n\tException: "), paramThrowable);
  }
  
  private String jdMethod_if(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("\tgetL10NString rsrcFile=").append(this._rsrcFile);
    if (this._msgKey != null) {
      localStringBuffer.append(" msgKey=").append(this._msgKey);
    }
    for (int i = 0; i < this._parms.length; i++) {
      if (this._parms[i] != null) {
        localStringBuffer.append(" parm").append(i).append("=").append(this._parms[i]);
      }
    }
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
  
  public void setEncode(String paramString)
  {
    this._encode = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setEncodeLeadingSpaces(String paramString)
  {
    this._encodeLeadingSpaces = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setEncodeParm0(String paramString)
  {
    this._encodeParams[0] = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setEncodeParm1(String paramString)
  {
    this._encodeParams[1] = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setEncodeParm2(String paramString)
  {
    this._encodeParams[2] = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setEncodeParm3(String paramString)
  {
    this._encodeParams[3] = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setEncodeParm4(String paramString)
  {
    this._encodeParams[4] = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.GetL10NStringTag
 * JD-Core Version:    0.7.0.1
 */