/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
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
/*  12:    */ public abstract class TypeTrnRqCmHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeTrnRqCm clone(TypeTrnRqCm paramTypeTrnRqCm)
/*  17:    */   {
/*  18: 16 */     if (paramTypeTrnRqCm == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeTrnRqCm localTypeTrnRqCm = new TypeTrnRqCm();
/*  22: 21 */     localTypeTrnRqCm.TrnUID = paramTypeTrnRqCm.TrnUID;
/*  23: 22 */     localTypeTrnRqCm.CltCookie = paramTypeTrnRqCm.CltCookie;
/*  24: 23 */     localTypeTrnRqCm.CltCookieExists = paramTypeTrnRqCm.CltCookieExists;
/*  25: 24 */     localTypeTrnRqCm.TAN = paramTypeTrnRqCm.TAN;
/*  26: 25 */     localTypeTrnRqCm.TANExists = paramTypeTrnRqCm.TANExists;
/*  27: 26 */     return localTypeTrnRqCm;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeTrnRqCm read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeTrnRqCm localTypeTrnRqCm = new TypeTrnRqCm();
/*  33: 33 */     localTypeTrnRqCm.TrnUID = paramInputStream.read_string();
/*  34: 34 */     localTypeTrnRqCm.CltCookie = paramInputStream.read_string();
/*  35: 35 */     localTypeTrnRqCm.CltCookieExists = paramInputStream.read_boolean();
/*  36: 36 */     localTypeTrnRqCm.TAN = paramInputStream.read_string();
/*  37: 37 */     localTypeTrnRqCm.TANExists = paramInputStream.read_boolean();
/*  38: 38 */     return localTypeTrnRqCm;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeTrnRqCm paramTypeTrnRqCm)
/*  42:    */   {
/*  43: 45 */     if (paramTypeTrnRqCm == null) {
/*  44: 47 */       paramTypeTrnRqCm = new TypeTrnRqCm();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypeTrnRqCm.TrnUID);
/*  47: 50 */     paramOutputStream.write_string(paramTypeTrnRqCm.CltCookie);
/*  48: 51 */     paramOutputStream.write_boolean(paramTypeTrnRqCm.CltCookieExists);
/*  49: 52 */     paramOutputStream.write_string(paramTypeTrnRqCm.TAN);
/*  50: 53 */     paramOutputStream.write_boolean(paramTypeTrnRqCm.TANExists);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeTrnRqCm";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static TypeCode type()
/*  59:    */   {
/*  60: 65 */     if (_type == null)
/*  61:    */     {
/*  62: 67 */       ORB localORB = ORB.init();
/*  63: 68 */       StructMember[] arrayOfStructMember = 
/*  64: 69 */         {
/*  65: 70 */         new StructMember("TrnUID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  66: 71 */         new StructMember("CltCookie", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  67: 72 */         new StructMember("CltCookieExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  68: 73 */         new StructMember("TAN", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  69: 74 */         new StructMember("TANExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeTrnRqCm", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeTrnRqCm paramTypeTrnRqCm)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeTrnRqCm);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeTrnRqCm extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeTrnRqCm:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCmHelper
 * JD-Core Version:    0.7.0.1
 */