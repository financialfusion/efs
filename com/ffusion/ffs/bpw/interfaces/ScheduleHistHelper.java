/*   1:    */ package com.ffusion.ffs.bpw.interfaces;
/*   2:    */ 
/*   3:    */ import org.omg.CORBA.Any;
/*   4:    */ import org.omg.CORBA.BAD_OPERATION;
/*   5:    */ import org.omg.CORBA.ORB;
/*   6:    */ import org.omg.CORBA.StructMember;
/*   7:    */ import org.omg.CORBA.TCKind;
/*   8:    */ import org.omg.CORBA.TypeCode;
/*   9:    */ import org.omg.CORBA.portable.InputStream;
/*  10:    */ import org.omg.CORBA.portable.OutputStream;
/*  11:    */ 
/*  12:    */ public abstract class ScheduleHistHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static ScheduleHist clone(ScheduleHist paramScheduleHist)
/*  17:    */   {
/*  18: 16 */     if (paramScheduleHist == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     ScheduleHist localScheduleHist = new ScheduleHist();
/*  22: 21 */     localScheduleHist.SchHistID = paramScheduleHist.SchHistID;
/*  23: 22 */     localScheduleHist.LogDate = paramScheduleHist.LogDate;
/*  24: 23 */     localScheduleHist.ScheduleName = paramScheduleHist.ScheduleName;
/*  25: 24 */     localScheduleHist.FIID = paramScheduleHist.FIID;
/*  26: 25 */     localScheduleHist.InstructionType = paramScheduleHist.InstructionType;
/*  27: 26 */     localScheduleHist.ServerName = paramScheduleHist.ServerName;
/*  28: 27 */     localScheduleHist.EventType = paramScheduleHist.EventType;
/*  29: 28 */     localScheduleHist.EventTrigger = paramScheduleHist.EventTrigger;
/*  30: 29 */     localScheduleHist.EventDescription = paramScheduleHist.EventDescription;
/*  31: 30 */     localScheduleHist.CutOffId = paramScheduleHist.CutOffId;
/*  32: 31 */     localScheduleHist.CutOffDay = paramScheduleHist.CutOffDay;
/*  33: 32 */     localScheduleHist.CutOffProcessTime = paramScheduleHist.CutOffProcessTime;
/*  34: 33 */     localScheduleHist.CutOffExtension = paramScheduleHist.CutOffExtension;
/*  35: 34 */     localScheduleHist.ProcessId = paramScheduleHist.ProcessId;
/*  36: 35 */     localScheduleHist.FileName = paramScheduleHist.FileName;
/*  37: 36 */     return localScheduleHist;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static ScheduleHist read(InputStream paramInputStream)
/*  41:    */   {
/*  42: 42 */     ScheduleHist localScheduleHist = new ScheduleHist();
/*  43: 43 */     localScheduleHist.SchHistID = paramInputStream.read_string();
/*  44: 44 */     localScheduleHist.LogDate = paramInputStream.read_string();
/*  45: 45 */     localScheduleHist.ScheduleName = paramInputStream.read_string();
/*  46: 46 */     localScheduleHist.FIID = paramInputStream.read_string();
/*  47: 47 */     localScheduleHist.InstructionType = paramInputStream.read_string();
/*  48: 48 */     localScheduleHist.ServerName = paramInputStream.read_string();
/*  49: 49 */     localScheduleHist.EventType = paramInputStream.read_string();
/*  50: 50 */     localScheduleHist.EventTrigger = paramInputStream.read_string();
/*  51: 51 */     localScheduleHist.EventDescription = paramInputStream.read_string();
/*  52: 52 */     localScheduleHist.CutOffId = paramInputStream.read_string();
/*  53: 53 */     localScheduleHist.CutOffDay = paramInputStream.read_long();
/*  54: 54 */     localScheduleHist.CutOffProcessTime = paramInputStream.read_string();
/*  55: 55 */     localScheduleHist.CutOffExtension = paramInputStream.read_string();
/*  56: 56 */     localScheduleHist.ProcessId = paramInputStream.read_string();
/*  57: 57 */     localScheduleHist.FileName = paramInputStream.read_string();
/*  58: 58 */     return localScheduleHist;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static void write(OutputStream paramOutputStream, ScheduleHist paramScheduleHist)
/*  62:    */   {
/*  63: 65 */     if (paramScheduleHist == null) {
/*  64: 67 */       paramScheduleHist = new ScheduleHist();
/*  65:    */     }
/*  66: 69 */     paramOutputStream.write_string(paramScheduleHist.SchHistID);
/*  67: 70 */     paramOutputStream.write_string(paramScheduleHist.LogDate);
/*  68: 71 */     paramOutputStream.write_string(paramScheduleHist.ScheduleName);
/*  69: 72 */     paramOutputStream.write_string(paramScheduleHist.FIID);
/*  70: 73 */     paramOutputStream.write_string(paramScheduleHist.InstructionType);
/*  71: 74 */     paramOutputStream.write_string(paramScheduleHist.ServerName);
/*  72: 75 */     paramOutputStream.write_string(paramScheduleHist.EventType);
/*  73: 76 */     paramOutputStream.write_string(paramScheduleHist.EventTrigger);
/*  74: 77 */     paramOutputStream.write_string(paramScheduleHist.EventDescription);
/*  75: 78 */     paramOutputStream.write_string(paramScheduleHist.CutOffId);
/*  76: 79 */     paramOutputStream.write_long(paramScheduleHist.CutOffDay);
/*  77: 80 */     paramOutputStream.write_string(paramScheduleHist.CutOffProcessTime);
/*  78: 81 */     paramOutputStream.write_string(paramScheduleHist.CutOffExtension);
/*  79: 82 */     paramOutputStream.write_string(paramScheduleHist.ProcessId);
/*  80: 83 */     paramOutputStream.write_string(paramScheduleHist.FileName);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static String _idl()
/*  84:    */   {
/*  85: 88 */     return "com::ffusion::ffs::bpw::interfaces::ScheduleHist";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static TypeCode type()
/*  89:    */   {
/*  90: 95 */     if (_type == null)
/*  91:    */     {
/*  92: 97 */       ORB localORB = ORB.init();
/*  93: 98 */       StructMember[] arrayOfStructMember = 
/*  94: 99 */         {
/*  95:100 */         new StructMember("SchHistID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  96:101 */         new StructMember("LogDate", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  97:102 */         new StructMember("ScheduleName", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  98:103 */         new StructMember("FIID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  99:104 */         new StructMember("InstructionType", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 100:105 */         new StructMember("ServerName", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 101:106 */         new StructMember("EventType", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 102:107 */         new StructMember("EventTrigger", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 103:108 */         new StructMember("EventDescription", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 104:109 */         new StructMember("CutOffId", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 105:110 */         new StructMember("CutOffDay", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 106:111 */         new StructMember("CutOffProcessTime", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 107:112 */         new StructMember("CutOffExtension", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 108:113 */         new StructMember("ProcessId", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 109:114 */         new StructMember("FileName", localORB.get_primitive_tc(TCKind.tk_string), null) };
/* 110:    */       
/* 111:116 */       _type = localORB.create_struct_tc(id(), "ScheduleHist", arrayOfStructMember);
/* 112:    */     }
/* 113:118 */     return _type;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static void insert(Any paramAny, ScheduleHist paramScheduleHist)
/* 117:    */   {
/* 118:125 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 119:126 */     write(localOutputStream, paramScheduleHist);
/* 120:127 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static ScheduleHist extract(Any paramAny)
/* 124:    */   {
/* 125:133 */     if (!paramAny.type().equal(type())) {
/* 126:135 */       throw new BAD_OPERATION();
/* 127:    */     }
/* 128:137 */     return read(paramAny.create_input_stream());
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static String id()
/* 132:    */   {
/* 133:142 */     return "IDL:com/ffusion/ffs/bpw/interfaces/ScheduleHist:1.0";
/* 134:    */   }
/* 135:    */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ScheduleHistHelper
 * JD-Core Version:    0.7.0.1
 */