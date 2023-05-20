package com.ffusion.ffs.ofx.interfaces;

import com.ffusion.ffs.interfaces.FFSStatusConst;

public abstract interface OFXStatusConst
  extends FFSStatusConst
{
  public static final int OFX_SUCCESS = 3000;
  public static final int OFX_BAD_REQUEST = 3001;
  public static final int OFX_SERVER_ERROR = 3002;
  public static final int OFX_SERVER_INITED = 3003;
  public static final int OFX_SERVER_NOT_INITED = 3004;
  public static final int OFX_SERVER_READY = 3005;
  public static final int OFX_SHUTDOWN_FAILURE = 3006;
  public static final int OFX_INVALID_SRVR_NAME = 3007;
  public static final int OFX_SUSPENTION_FAILURE = 3008;
  public static final int OFX_FIINFO_ERROR = 3009;
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.ofx.interfaces.OFXStatusConst
 * JD-Core Version:    0.7.0.1
 */