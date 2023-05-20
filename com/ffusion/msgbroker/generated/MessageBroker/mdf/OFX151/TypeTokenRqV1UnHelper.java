/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/*  12:    */ public abstract class TypeTokenRqV1UnHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeTokenRqV1Un clone(TypeTokenRqV1Un paramTypeTokenRqV1Un)
/*  17:    */   {
/*  18: 16 */     if (paramTypeTokenRqV1Un == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeTokenRqV1Un localTypeTokenRqV1Un = new TypeTokenRqV1Un();
/*  22: 21 */     localTypeTokenRqV1Un.__preValueTag = paramTypeTokenRqV1Un.__preValueTag;
/*  23: 22 */     localTypeTokenRqV1Un.__memberName = paramTypeTokenRqV1Un.__memberName;
/*  24: 23 */     localTypeTokenRqV1Un.Token = paramTypeTokenRqV1Un.Token;
/*  25: 24 */     localTypeTokenRqV1Un.TokenOnly = paramTypeTokenRqV1Un.TokenOnly;
/*  26: 25 */     localTypeTokenRqV1Un.Refresh = paramTypeTokenRqV1Un.Refresh;
/*  27: 26 */     return localTypeTokenRqV1Un;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeTokenRqV1Un read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeTokenRqV1Un localTypeTokenRqV1Un = new TypeTokenRqV1Un();
/*  33: 33 */     localTypeTokenRqV1Un.__preValueTag = paramInputStream.read_string();
/*  34: 34 */     localTypeTokenRqV1Un.__memberName = paramInputStream.read_string();
/*  35: 35 */     localTypeTokenRqV1Un.Token = paramInputStream.read_string();
/*  36: 36 */     localTypeTokenRqV1Un.TokenOnly = paramInputStream.read_boolean();
/*  37: 37 */     localTypeTokenRqV1Un.Refresh = paramInputStream.read_boolean();
/*  38: 38 */     return localTypeTokenRqV1Un;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeTokenRqV1Un paramTypeTokenRqV1Un)
/*  42:    */   {
/*  43: 45 */     if (paramTypeTokenRqV1Un == null) {
/*  44: 47 */       paramTypeTokenRqV1Un = new TypeTokenRqV1Un();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramTypeTokenRqV1Un.__preValueTag);
/*  47: 50 */     paramOutputStream.write_string(paramTypeTokenRqV1Un.__memberName);
/*  48: 51 */     paramOutputStream.write_string(paramTypeTokenRqV1Un.Token);
/*  49: 52 */     paramOutputStream.write_boolean(paramTypeTokenRqV1Un.TokenOnly);
/*  50: 53 */     paramOutputStream.write_boolean(paramTypeTokenRqV1Un.Refresh);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeTokenRqV1Un";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static TypeCode type()
/*  59:    */   {
/*  60: 65 */     if (_type == null)
/*  61:    */     {
/*  62: 67 */       ORB localORB = ORB.init();
/*  63: 68 */       StructMember[] arrayOfStructMember = 
/*  64: 69 */         {
/*  65: 70 */         new StructMember("__preValueTag", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  66: 71 */         new StructMember("__memberName", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  67: 72 */         new StructMember("Token", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  68: 73 */         new StructMember("TokenOnly", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  69: 74 */         new StructMember("Refresh", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeTokenRqV1Un", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeTokenRqV1Un paramTypeTokenRqV1Un)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeTokenRqV1Un);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeTokenRqV1Un extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeTokenRqV1Un:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTokenRqV1UnHelper
 * JD-Core Version:    0.7.0.1
 */