/*   1:    */ package com.ffusion.ffs.bpw.interfaces;
/*   2:    */ 
/*   3:    */ import BCD.BinaryHelper;
/*   4:    */ import com.sybase.CORBA.ObjectVal;
/*   5:    */ import java.util.Properties;
/*   6:    */ import org.omg.CORBA.Any;
/*   7:    */ import org.omg.CORBA.BAD_OPERATION;
/*   8:    */ import org.omg.CORBA.ORB;
/*   9:    */ import org.omg.CORBA.StructMember;
/*  10:    */ import org.omg.CORBA.TCKind;
/*  11:    */ import org.omg.CORBA.TypeCode;
/*  12:    */ import org.omg.CORBA.portable.InputStream;
/*  13:    */ import org.omg.CORBA.portable.OutputStream;
/*  14:    */ 
/*  15:    */ public abstract class ServerGeneralConfigHelper
/*  16:    */ {
/*  17:    */   public static TypeCode _type;
/*  18:    */   
/*  19:    */   public static ServerGeneralConfig clone(ServerGeneralConfig paramServerGeneralConfig)
/*  20:    */   {
/*  21: 16 */     if (paramServerGeneralConfig == null) {
/*  22: 18 */       return null;
/*  23:    */     }
/*  24: 20 */     ServerGeneralConfig localServerGeneralConfig = new ServerGeneralConfig();
/*  25: 21 */     localServerGeneralConfig.minuteNotToRecover = paramServerGeneralConfig.minuteNotToRecover;
/*  26: 22 */     localServerGeneralConfig.fundsAllocRetries = paramServerGeneralConfig.fundsAllocRetries;
/*  27: 23 */     localServerGeneralConfig.batchSize = paramServerGeneralConfig.batchSize;
/*  28: 24 */     localServerGeneralConfig.enforceEnrollment = paramServerGeneralConfig.enforceEnrollment;
/*  29: 25 */     localServerGeneralConfig.enforcePayment = paramServerGeneralConfig.enforcePayment;
/*  30: 26 */     localServerGeneralConfig.startPayeeListID = paramServerGeneralConfig.startPayeeListID;
/*  31: 27 */     localServerGeneralConfig.retries = paramServerGeneralConfig.retries;
/*  32: 28 */     localServerGeneralConfig.noImmediateTransfer = paramServerGeneralConfig.noImmediateTransfer;
/*  33: 29 */     localServerGeneralConfig.supportImmediatePmt = paramServerGeneralConfig.supportImmediatePmt;
/*  34: 30 */     localServerGeneralConfig.timeout = paramServerGeneralConfig.timeout;
/*  35: 31 */     localServerGeneralConfig.useExtdPayeeID = paramServerGeneralConfig.useExtdPayeeID;
/*  36: 32 */     localServerGeneralConfig.purgeWakeupTime = paramServerGeneralConfig.purgeWakeupTime;
/*  37: 33 */     localServerGeneralConfig.closedInstructionAge = paramServerGeneralConfig.closedInstructionAge;
/*  38: 34 */     localServerGeneralConfig.eventHistoryAge = paramServerGeneralConfig.eventHistoryAge;
/*  39: 35 */     localServerGeneralConfig.activityLogAge = paramServerGeneralConfig.activityLogAge;
/*  40: 36 */     localServerGeneralConfig.clusterOn = paramServerGeneralConfig.clusterOn;
/*  41: 37 */     localServerGeneralConfig.clusterDelay = paramServerGeneralConfig.clusterDelay;
/*  42: 38 */     localServerGeneralConfig.otherProperties = ((Properties)ObjectVal.readObject(ObjectVal.writeObject(paramServerGeneralConfig.otherProperties)));
/*  43: 39 */     return localServerGeneralConfig;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static ServerGeneralConfig read(InputStream paramInputStream)
/*  47:    */   {
/*  48: 45 */     ServerGeneralConfig localServerGeneralConfig = new ServerGeneralConfig();
/*  49: 46 */     localServerGeneralConfig.minuteNotToRecover = paramInputStream.read_long();
/*  50: 47 */     localServerGeneralConfig.fundsAllocRetries = paramInputStream.read_long();
/*  51: 48 */     localServerGeneralConfig.batchSize = paramInputStream.read_long();
/*  52: 49 */     localServerGeneralConfig.enforceEnrollment = paramInputStream.read_boolean();
/*  53: 50 */     localServerGeneralConfig.enforcePayment = paramInputStream.read_boolean();
/*  54: 51 */     localServerGeneralConfig.startPayeeListID = paramInputStream.read_long();
/*  55: 52 */     localServerGeneralConfig.retries = paramInputStream.read_long();
/*  56: 53 */     localServerGeneralConfig.noImmediateTransfer = paramInputStream.read_boolean();
/*  57: 54 */     localServerGeneralConfig.supportImmediatePmt = paramInputStream.read_boolean();
/*  58: 55 */     localServerGeneralConfig.timeout = paramInputStream.read_long();
/*  59: 56 */     localServerGeneralConfig.useExtdPayeeID = paramInputStream.read_boolean();
/*  60: 57 */     localServerGeneralConfig.purgeWakeupTime = paramInputStream.read_string();
/*  61: 58 */     localServerGeneralConfig.closedInstructionAge = paramInputStream.read_long();
/*  62: 59 */     localServerGeneralConfig.eventHistoryAge = paramInputStream.read_long();
/*  63: 60 */     localServerGeneralConfig.activityLogAge = paramInputStream.read_long();
/*  64: 61 */     localServerGeneralConfig.clusterOn = paramInputStream.read_boolean();
/*  65: 62 */     localServerGeneralConfig.clusterDelay = paramInputStream.read_longlong();
/*  66: 63 */     localServerGeneralConfig.otherProperties = ((Properties)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/*  67: 64 */     return localServerGeneralConfig;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static void write(OutputStream paramOutputStream, ServerGeneralConfig paramServerGeneralConfig)
/*  71:    */   {
/*  72: 71 */     if (paramServerGeneralConfig == null) {
/*  73: 73 */       paramServerGeneralConfig = new ServerGeneralConfig();
/*  74:    */     }
/*  75: 75 */     paramOutputStream.write_long(paramServerGeneralConfig.minuteNotToRecover);
/*  76: 76 */     paramOutputStream.write_long(paramServerGeneralConfig.fundsAllocRetries);
/*  77: 77 */     paramOutputStream.write_long(paramServerGeneralConfig.batchSize);
/*  78: 78 */     paramOutputStream.write_boolean(paramServerGeneralConfig.enforceEnrollment);
/*  79: 79 */     paramOutputStream.write_boolean(paramServerGeneralConfig.enforcePayment);
/*  80: 80 */     paramOutputStream.write_long(paramServerGeneralConfig.startPayeeListID);
/*  81: 81 */     paramOutputStream.write_long(paramServerGeneralConfig.retries);
/*  82: 82 */     paramOutputStream.write_boolean(paramServerGeneralConfig.noImmediateTransfer);
/*  83: 83 */     paramOutputStream.write_boolean(paramServerGeneralConfig.supportImmediatePmt);
/*  84: 84 */     paramOutputStream.write_long(paramServerGeneralConfig.timeout);
/*  85: 85 */     paramOutputStream.write_boolean(paramServerGeneralConfig.useExtdPayeeID);
/*  86: 86 */     paramOutputStream.write_string(paramServerGeneralConfig.purgeWakeupTime);
/*  87: 87 */     paramOutputStream.write_long(paramServerGeneralConfig.closedInstructionAge);
/*  88: 88 */     paramOutputStream.write_long(paramServerGeneralConfig.eventHistoryAge);
/*  89: 89 */     paramOutputStream.write_long(paramServerGeneralConfig.activityLogAge);
/*  90: 90 */     paramOutputStream.write_boolean(paramServerGeneralConfig.clusterOn);
/*  91: 91 */     paramOutputStream.write_longlong(paramServerGeneralConfig.clusterDelay);
/*  92: 92 */     BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramServerGeneralConfig.otherProperties));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static String _idl()
/*  96:    */   {
/*  97: 97 */     return "com::ffusion::ffs::bpw::interfaces::ServerGeneralConfig";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static TypeCode type()
/* 101:    */   {
/* 102:104 */     if (_type == null)
/* 103:    */     {
/* 104:106 */       ORB localORB = ORB.init();
/* 105:107 */       StructMember[] arrayOfStructMember = 
/* 106:108 */         {
/* 107:109 */         new StructMember("minuteNotToRecover", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 108:110 */         new StructMember("fundsAllocRetries", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 109:111 */         new StructMember("batchSize", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 110:112 */         new StructMember("enforceEnrollment", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 111:113 */         new StructMember("enforcePayment", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 112:114 */         new StructMember("startPayeeListID", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 113:115 */         new StructMember("retries", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 114:116 */         new StructMember("noImmediateTransfer", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 115:117 */         new StructMember("supportImmediatePmt", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 116:118 */         new StructMember("timeout", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 117:119 */         new StructMember("useExtdPayeeID", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 118:120 */         new StructMember("purgeWakeupTime", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 119:121 */         new StructMember("closedInstructionAge", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 120:122 */         new StructMember("eventHistoryAge", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 121:123 */         new StructMember("activityLogAge", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 122:124 */         new StructMember("clusterOn", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 123:125 */         new StructMember("clusterDelay", localORB.get_primitive_tc(TCKind.tk_longlong), null), 
/* 124:126 */         new StructMember("otherProperties", BinaryHelper.type(), null) };
/* 125:    */       
/* 126:128 */       _type = localORB.create_struct_tc(id(), "ServerGeneralConfig", arrayOfStructMember);
/* 127:    */     }
/* 128:130 */     return _type;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static void insert(Any paramAny, ServerGeneralConfig paramServerGeneralConfig)
/* 132:    */   {
/* 133:137 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 134:138 */     write(localOutputStream, paramServerGeneralConfig);
/* 135:139 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static ServerGeneralConfig extract(Any paramAny)
/* 139:    */   {
/* 140:145 */     if (!paramAny.type().equal(type())) {
/* 141:147 */       throw new BAD_OPERATION();
/* 142:    */     }
/* 143:149 */     return read(paramAny.create_input_stream());
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static String id()
/* 147:    */   {
/* 148:154 */     return "IDL:com/ffusion/ffs/bpw/interfaces/ServerGeneralConfig:1.0";
/* 149:    */   }
/* 150:    */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ServerGeneralConfigHelper
 * JD-Core Version:    0.7.0.1
 */