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
/*  12:    */ public abstract class ConsumerCrossRefInfoHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static ConsumerCrossRefInfo clone(ConsumerCrossRefInfo paramConsumerCrossRefInfo)
/*  17:    */   {
/*  18: 16 */     if (paramConsumerCrossRefInfo == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     ConsumerCrossRefInfo localConsumerCrossRefInfo = new ConsumerCrossRefInfo();
/*  22: 21 */     localConsumerCrossRefInfo.consumerID = paramConsumerCrossRefInfo.consumerID;
/*  23: 22 */     localConsumerCrossRefInfo.federalTaxID = paramConsumerCrossRefInfo.federalTaxID;
/*  24: 23 */     localConsumerCrossRefInfo.consumerSSN = paramConsumerCrossRefInfo.consumerSSN;
/*  25: 24 */     localConsumerCrossRefInfo.sponsorID = paramConsumerCrossRefInfo.sponsorID;
/*  26: 25 */     localConsumerCrossRefInfo.customerID = paramConsumerCrossRefInfo.customerID;
/*  27: 26 */     localConsumerCrossRefInfo.consumerType = paramConsumerCrossRefInfo.consumerType;
/*  28: 27 */     localConsumerCrossRefInfo.submitDate = paramConsumerCrossRefInfo.submitDate;
/*  29: 28 */     return localConsumerCrossRefInfo;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static ConsumerCrossRefInfo read(InputStream paramInputStream)
/*  33:    */   {
/*  34: 34 */     ConsumerCrossRefInfo localConsumerCrossRefInfo = new ConsumerCrossRefInfo();
/*  35: 35 */     localConsumerCrossRefInfo.consumerID = paramInputStream.read_string();
/*  36: 36 */     localConsumerCrossRefInfo.federalTaxID = paramInputStream.read_string();
/*  37: 37 */     localConsumerCrossRefInfo.consumerSSN = paramInputStream.read_string();
/*  38: 38 */     localConsumerCrossRefInfo.sponsorID = paramInputStream.read_string();
/*  39: 39 */     localConsumerCrossRefInfo.customerID = paramInputStream.read_string();
/*  40: 40 */     localConsumerCrossRefInfo.consumerType = paramInputStream.read_string();
/*  41: 41 */     localConsumerCrossRefInfo.submitDate = paramInputStream.read_string();
/*  42: 42 */     return localConsumerCrossRefInfo;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static void write(OutputStream paramOutputStream, ConsumerCrossRefInfo paramConsumerCrossRefInfo)
/*  46:    */   {
/*  47: 49 */     if (paramConsumerCrossRefInfo == null) {
/*  48: 51 */       paramConsumerCrossRefInfo = new ConsumerCrossRefInfo();
/*  49:    */     }
/*  50: 53 */     paramOutputStream.write_string(paramConsumerCrossRefInfo.consumerID);
/*  51: 54 */     paramOutputStream.write_string(paramConsumerCrossRefInfo.federalTaxID);
/*  52: 55 */     paramOutputStream.write_string(paramConsumerCrossRefInfo.consumerSSN);
/*  53: 56 */     paramOutputStream.write_string(paramConsumerCrossRefInfo.sponsorID);
/*  54: 57 */     paramOutputStream.write_string(paramConsumerCrossRefInfo.customerID);
/*  55: 58 */     paramOutputStream.write_string(paramConsumerCrossRefInfo.consumerType);
/*  56: 59 */     paramOutputStream.write_string(paramConsumerCrossRefInfo.submitDate);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static String _idl()
/*  60:    */   {
/*  61: 64 */     return "com::ffusion::ffs::bpw::interfaces::ConsumerCrossRefInfo";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static TypeCode type()
/*  65:    */   {
/*  66: 71 */     if (_type == null)
/*  67:    */     {
/*  68: 73 */       ORB localORB = ORB.init();
/*  69: 74 */       StructMember[] arrayOfStructMember = 
/*  70: 75 */         {
/*  71: 76 */         new StructMember("consumerID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  72: 77 */         new StructMember("federalTaxID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  73: 78 */         new StructMember("consumerSSN", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  74: 79 */         new StructMember("sponsorID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  75: 80 */         new StructMember("customerID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  76: 81 */         new StructMember("consumerType", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  77: 82 */         new StructMember("submitDate", localORB.get_primitive_tc(TCKind.tk_string), null) };
/*  78:    */       
/*  79: 84 */       _type = localORB.create_struct_tc(id(), "ConsumerCrossRefInfo", arrayOfStructMember);
/*  80:    */     }
/*  81: 86 */     return _type;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static void insert(Any paramAny, ConsumerCrossRefInfo paramConsumerCrossRefInfo)
/*  85:    */   {
/*  86: 93 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  87: 94 */     write(localOutputStream, paramConsumerCrossRefInfo);
/*  88: 95 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static ConsumerCrossRefInfo extract(Any paramAny)
/*  92:    */   {
/*  93:101 */     if (!paramAny.type().equal(type())) {
/*  94:103 */       throw new BAD_OPERATION();
/*  95:    */     }
/*  96:105 */     return read(paramAny.create_input_stream());
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static String id()
/* 100:    */   {
/* 101:110 */     return "IDL:com/ffusion/ffs/bpw/interfaces/ConsumerCrossRefInfo:1.0";
/* 102:    */   }
/* 103:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfoHelper
 * JD-Core Version:    0.7.0.1
 */