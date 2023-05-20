package com.ffusion.ffs.ofx.interfaces;

import java.io.Serializable;
import java.util.Hashtable;

public class TypeUserData
  implements Serializable
{
  public String _ofxHeader;
  public String _version;
  public String _security;
  public String _oldFileUID;
  public String _newFileUID;
  public String _data;
  public String _encoding;
  public String _charSet;
  public String _compression;
  public String _org;
  public String _fid;
  public String _uid;
  public String _submittedBy;
  public String _tran_id;
  public Object _userDefined;
  public ServerObject _srvrObj;
  public String _sessionID;
  public int _stamp;
  public String _srvrUid;
  public Hashtable _privateTagContainer = null;
  public static final String TAGCONTAINER_HASHKEY_INVOICE = "INVOICE";
  public String _agentID;
  public String _agentType;
  
  public TypeUserData() {}
  
  public TypeUserData(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, Object paramObject, ServerObject paramServerObject, String paramString13, int paramInt, String paramString14)
  {
    this._ofxHeader = paramString1;
    this._version = paramString2;
    this._security = paramString3;
    this._oldFileUID = paramString4;
    this._newFileUID = paramString5;
    this._data = paramString6;
    this._encoding = paramString7;
    this._charSet = paramString8;
    this._compression = paramString9;
    this._org = paramString10;
    this._fid = paramString11;
    this._uid = paramString12;
    this._userDefined = paramObject;
    this._srvrObj = paramServerObject;
    this._sessionID = paramString13;
    this._stamp = paramInt;
    this._srvrUid = paramString14;
  }
  
  public TypeUserData(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, Object paramObject, ServerObject paramServerObject, String paramString15, int paramInt, String paramString16)
  {
    this._ofxHeader = paramString1;
    this._version = paramString2;
    this._security = paramString3;
    this._oldFileUID = paramString4;
    this._newFileUID = paramString5;
    this._data = paramString6;
    this._encoding = paramString7;
    this._charSet = paramString8;
    this._compression = paramString9;
    this._org = paramString10;
    this._fid = paramString11;
    this._uid = paramString12;
    this._submittedBy = paramString13;
    this._tran_id = paramString14;
    this._userDefined = paramObject;
    this._srvrObj = paramServerObject;
    this._sessionID = paramString15;
    this._stamp = paramInt;
    this._srvrUid = paramString16;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.ofx.interfaces.TypeUserData
 * JD-Core Version:    0.7.0.1
 */