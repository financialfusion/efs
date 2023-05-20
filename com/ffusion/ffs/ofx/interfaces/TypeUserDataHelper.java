/*   1:    */ package com.ffusion.ffs.ofx.interfaces;
/*   2:    */ 
/*   3:    */ import BCD.BinaryHelper;
/*   4:    */ import com.sybase.CORBA.ObjectVal;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ import org.omg.CORBA.Any;
/*   7:    */ import org.omg.CORBA.BAD_OPERATION;
/*   8:    */ import org.omg.CORBA.ORB;
/*   9:    */ import org.omg.CORBA.StructMember;
/*  10:    */ import org.omg.CORBA.TCKind;
/*  11:    */ import org.omg.CORBA.TypeCode;
/*  12:    */ import org.omg.CORBA.portable.InputStream;
/*  13:    */ import org.omg.CORBA.portable.OutputStream;
/*  14:    */ 
/*  15:    */ public abstract class TypeUserDataHelper
/*  16:    */ {
/*  17:    */   public static TypeCode _type;
/*  18:    */   
/*  19:    */   public static TypeUserData clone(TypeUserData paramTypeUserData)
/*  20:    */   {
/*  21: 16 */     if (paramTypeUserData == null) {
/*  22: 18 */       return null;
/*  23:    */     }
/*  24: 20 */     TypeUserData localTypeUserData = new TypeUserData();
/*  25: 21 */     localTypeUserData._ofxHeader = paramTypeUserData._ofxHeader;
/*  26: 22 */     localTypeUserData._version = paramTypeUserData._version;
/*  27: 23 */     localTypeUserData._security = paramTypeUserData._security;
/*  28: 24 */     localTypeUserData._oldFileUID = paramTypeUserData._oldFileUID;
/*  29: 25 */     localTypeUserData._newFileUID = paramTypeUserData._newFileUID;
/*  30: 26 */     localTypeUserData._data = paramTypeUserData._data;
/*  31: 27 */     localTypeUserData._encoding = paramTypeUserData._encoding;
/*  32: 28 */     localTypeUserData._charSet = paramTypeUserData._charSet;
/*  33: 29 */     localTypeUserData._compression = paramTypeUserData._compression;
/*  34: 30 */     localTypeUserData._org = paramTypeUserData._org;
/*  35: 31 */     localTypeUserData._fid = paramTypeUserData._fid;
/*  36: 32 */     localTypeUserData._uid = paramTypeUserData._uid;
/*  37: 33 */     localTypeUserData._submittedBy = paramTypeUserData._submittedBy;
/*  38: 34 */     localTypeUserData._tran_id = paramTypeUserData._tran_id;
/*  39: 35 */     localTypeUserData._userDefined = ObjectVal.readObject(ObjectVal.writeObject(paramTypeUserData._userDefined));
/*  40: 36 */     localTypeUserData._srvrObj = ServerObjectHelper.clone(paramTypeUserData._srvrObj);
/*  41: 37 */     localTypeUserData._sessionID = paramTypeUserData._sessionID;
/*  42: 38 */     localTypeUserData._stamp = paramTypeUserData._stamp;
/*  43: 39 */     localTypeUserData._srvrUid = paramTypeUserData._srvrUid;
/*  44: 40 */     localTypeUserData._privateTagContainer = ((Hashtable)ObjectVal.readObject(ObjectVal.writeObject(paramTypeUserData._privateTagContainer)));
/*  45: 41 */     localTypeUserData._agentID = paramTypeUserData._agentID;
/*  46: 42 */     localTypeUserData._agentType = paramTypeUserData._agentType;
/*  47: 43 */     return localTypeUserData;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static TypeUserData read(InputStream paramInputStream)
/*  51:    */   {
/*  52: 49 */     TypeUserData localTypeUserData = new TypeUserData();
/*  53: 50 */     localTypeUserData._ofxHeader = paramInputStream.read_string();
/*  54: 51 */     localTypeUserData._version = paramInputStream.read_string();
/*  55: 52 */     localTypeUserData._security = paramInputStream.read_string();
/*  56: 53 */     localTypeUserData._oldFileUID = paramInputStream.read_string();
/*  57: 54 */     localTypeUserData._newFileUID = paramInputStream.read_string();
/*  58: 55 */     localTypeUserData._data = paramInputStream.read_string();
/*  59: 56 */     localTypeUserData._encoding = paramInputStream.read_string();
/*  60: 57 */     localTypeUserData._charSet = paramInputStream.read_string();
/*  61: 58 */     localTypeUserData._compression = paramInputStream.read_string();
/*  62: 59 */     localTypeUserData._org = paramInputStream.read_string();
/*  63: 60 */     localTypeUserData._fid = paramInputStream.read_string();
/*  64: 61 */     localTypeUserData._uid = paramInputStream.read_string();
/*  65: 62 */     localTypeUserData._submittedBy = paramInputStream.read_string();
/*  66: 63 */     localTypeUserData._tran_id = paramInputStream.read_string();
/*  67: 64 */     localTypeUserData._userDefined = ObjectVal.readObject(BinaryHelper.read(paramInputStream));
/*  68: 65 */     localTypeUserData._srvrObj = ServerObjectHelper.read(paramInputStream);
/*  69: 66 */     localTypeUserData._sessionID = paramInputStream.read_string();
/*  70: 67 */     localTypeUserData._stamp = paramInputStream.read_long();
/*  71: 68 */     localTypeUserData._srvrUid = paramInputStream.read_string();
/*  72: 69 */     localTypeUserData._privateTagContainer = ((Hashtable)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/*  73: 70 */     localTypeUserData._agentID = paramInputStream.read_string();
/*  74: 71 */     localTypeUserData._agentType = paramInputStream.read_string();
/*  75: 72 */     return localTypeUserData;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void write(OutputStream paramOutputStream, TypeUserData paramTypeUserData)
/*  79:    */   {
/*  80: 79 */     if (paramTypeUserData == null) {
/*  81: 81 */       paramTypeUserData = new TypeUserData();
/*  82:    */     }
/*  83: 83 */     paramOutputStream.write_string(paramTypeUserData._ofxHeader);
/*  84: 84 */     paramOutputStream.write_string(paramTypeUserData._version);
/*  85: 85 */     paramOutputStream.write_string(paramTypeUserData._security);
/*  86: 86 */     paramOutputStream.write_string(paramTypeUserData._oldFileUID);
/*  87: 87 */     paramOutputStream.write_string(paramTypeUserData._newFileUID);
/*  88: 88 */     paramOutputStream.write_string(paramTypeUserData._data);
/*  89: 89 */     paramOutputStream.write_string(paramTypeUserData._encoding);
/*  90: 90 */     paramOutputStream.write_string(paramTypeUserData._charSet);
/*  91: 91 */     paramOutputStream.write_string(paramTypeUserData._compression);
/*  92: 92 */     paramOutputStream.write_string(paramTypeUserData._org);
/*  93: 93 */     paramOutputStream.write_string(paramTypeUserData._fid);
/*  94: 94 */     paramOutputStream.write_string(paramTypeUserData._uid);
/*  95: 95 */     paramOutputStream.write_string(paramTypeUserData._submittedBy);
/*  96: 96 */     paramOutputStream.write_string(paramTypeUserData._tran_id);
/*  97: 97 */     BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramTypeUserData._userDefined));
/*  98: 98 */     ServerObjectHelper.write(paramOutputStream, paramTypeUserData._srvrObj);
/*  99: 99 */     paramOutputStream.write_string(paramTypeUserData._sessionID);
/* 100:100 */     paramOutputStream.write_long(paramTypeUserData._stamp);
/* 101:101 */     paramOutputStream.write_string(paramTypeUserData._srvrUid);
/* 102:102 */     BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramTypeUserData._privateTagContainer));
/* 103:103 */     paramOutputStream.write_string(paramTypeUserData._agentID);
/* 104:104 */     paramOutputStream.write_string(paramTypeUserData._agentType);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static String _idl()
/* 108:    */   {
/* 109:109 */     return "com::ffusion::ffs::ofx::interfaces::TypeUserData";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static TypeCode type()
/* 113:    */   {
/* 114:116 */     if (_type == null)
/* 115:    */     {
/* 116:118 */       ORB localORB = ORB.init();
/* 117:119 */       StructMember[] arrayOfStructMember = 
/* 118:120 */         {
/* 119:121 */         new StructMember("_ofxHeader", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 120:122 */         new StructMember("_version", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 121:123 */         new StructMember("_security", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 122:124 */         new StructMember("_oldFileUID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 123:125 */         new StructMember("_newFileUID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 124:126 */         new StructMember("_data", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 125:127 */         new StructMember("_encoding", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 126:128 */         new StructMember("_charSet", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 127:129 */         new StructMember("_compression", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 128:130 */         new StructMember("_org", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 129:131 */         new StructMember("_fid", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 130:132 */         new StructMember("_uid", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 131:133 */         new StructMember("_submittedBy", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 132:134 */         new StructMember("_tran_id", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 133:135 */         new StructMember("_userDefined", BinaryHelper.type(), null), 
/* 134:136 */         new StructMember("_srvrObj", ServerObjectHelper.type(), null), 
/* 135:137 */         new StructMember("_sessionID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 136:138 */         new StructMember("_stamp", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 137:139 */         new StructMember("_srvrUid", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 138:140 */         new StructMember("_privateTagContainer", BinaryHelper.type(), null), 
/* 139:141 */         new StructMember("_agentID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 140:142 */         new StructMember("_agentType", localORB.get_primitive_tc(TCKind.tk_string), null) };
/* 141:    */       
/* 142:144 */       _type = localORB.create_struct_tc(id(), "TypeUserData", arrayOfStructMember);
/* 143:    */     }
/* 144:146 */     return _type;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static void insert(Any paramAny, TypeUserData paramTypeUserData)
/* 148:    */   {
/* 149:153 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 150:154 */     write(localOutputStream, paramTypeUserData);
/* 151:155 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static TypeUserData extract(Any paramAny)
/* 155:    */   {
/* 156:161 */     if (!paramAny.type().equal(type())) {
/* 157:163 */       throw new BAD_OPERATION();
/* 158:    */     }
/* 159:165 */     return read(paramAny.create_input_stream());
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static String id()
/* 163:    */   {
/* 164:170 */     return "IDL:com/ffusion/ffs/ofx/interfaces/TypeUserData:1.0";
/* 165:    */   }
/* 166:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.ofx.interfaces.TypeUserDataHelper
 * JD-Core Version:    0.7.0.1
 */