package com.ffusion.ffs.ofx.interfaces;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class OFXRequestStatus
  implements Serializable
{
  public int _statusCode;
  public String _response;
  
  public OFXRequestStatus()
  {
    this._statusCode = 0;
    this._response = null;
  }
  
  public OFXRequestStatus(int paramInt, String paramString)
  {
    this._statusCode = paramInt;
    this._response = paramString;
  }
  
  public int getStatusCode()
  {
    return this._statusCode;
  }
  
  public String getResponse()
  {
    return this._response;
  }
  
  private void a(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeInt(this._statusCode);
    byte[] arrayOfByte = this._response.getBytes();
    int i = arrayOfByte.length;
    paramObjectOutputStream.writeInt(i);
    if (i > 0) {
      paramObjectOutputStream.write(arrayOfByte);
    }
  }
  
  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this._statusCode = paramObjectInputStream.readInt();
    int i = paramObjectInputStream.readInt();
    if (i == 0)
    {
      this._response = "";
      return;
    }
    byte[] arrayOfByte = new byte[i];
    int j = 0;
    int k = 0;
    do
    {
      if (i - k > 0) {
        j = paramObjectInputStream.read(arrayOfByte, k, i - k);
      }
      if (j > 0) {
        k += j;
      }
    } while ((k < i) && (j != -1));
    this._response = new String(arrayOfByte);
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.ofx.interfaces.OFXRequestStatus
 * JD-Core Version:    0.7.0.1
 */