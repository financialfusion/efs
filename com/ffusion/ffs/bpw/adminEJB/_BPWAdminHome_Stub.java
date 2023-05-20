/*  1:   */ package com.ffusion.ffs.bpw.adminEJB;
/*  2:   */ 
/*  3:   */ import javax.ejb.CreateException;
/*  4:   */ import javax.ejb.EJBHome;
/*  5:   */ import javax.ejb.EJBMetaData;
/*  6:   */ import javax.ejb.HomeHandle;
/*  7:   */ import javax.ejb.RemoveException;
/*  8:   */ import javax.rmi.CORBA.Stub;
/*  9:   */ 
/* 10:   */ public class _BPWAdminHome_Stub
/* 11:   */   extends Stub
/* 12:   */   implements BPWAdminHome
/* 13:   */ {
/* 14:27 */   private static final String[] _type_ids = {
/* 15:28 */     "RMI:com.ffusion.ffs.bpw.adminEJB.BPWAdminHome:0000000000000000", 
/* 16:29 */     "RMI:javax.ejb.EJBHome:0000000000000000" };
/* 17:   */   
/* 18:   */   public String[] _ids()
/* 19:   */   {
/* 20:33 */     return _type_ids;
/* 21:   */   }
/* 22:   */   
/* 23:   */   /* Error */
/* 24:   */   public IBPWAdmin create()
/* 25:   */     throws CreateException, java.rmi.RemoteException
/* 26:   */   {
/* 27:   */     // Byte code:
/* 28:   */     //   0: aload_0
/* 29:   */     //   1: invokestatic 72	javax/rmi/CORBA/Util:isLocal	(Ljavax/rmi/CORBA/Stub;)Z
/* 30:   */     //   4: ifne +154 -> 158
/* 31:   */     //   7: aconst_null
/* 32:   */     //   8: astore_1
/* 33:   */     //   9: aload_0
/* 34:   */     //   10: ldc 12
/* 35:   */     //   12: iconst_1
/* 36:   */     //   13: invokevirtual 46	org/omg/CORBA/portable/ObjectImpl:_request	(Ljava/lang/String;Z)Lorg/omg/CORBA/portable/OutputStream;
/* 37:   */     //   16: astore 5
/* 38:   */     //   18: aload_0
/* 39:   */     //   19: aload 5
/* 40:   */     //   21: invokevirtual 55	org/omg/CORBA/portable/ObjectImpl:_invoke	(Lorg/omg/CORBA/portable/OutputStream;)Lorg/omg/CORBA/portable/InputStream;
/* 41:   */     //   24: checkcast 39	org/omg/CORBA_2_3/portable/InputStream
/* 42:   */     //   27: astore_1
/* 43:   */     //   28: aload_1
/* 44:   */     //   29: getstatic 50	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$com$ffusion$ffs$bpw$adminEJB$IBPWAdmin	Ljava/lang/Class;
/* 45:   */     //   32: ifnull +9 -> 41
/* 46:   */     //   35: getstatic 50	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$com$ffusion$ffs$bpw$adminEJB$IBPWAdmin	Ljava/lang/Class;
/* 47:   */     //   38: goto +12 -> 50
/* 48:   */     //   41: ldc 9
/* 49:   */     //   43: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* 50:   */     //   46: dup
/* 51:   */     //   47: putstatic 50	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$com$ffusion$ffs$bpw$adminEJB$IBPWAdmin	Ljava/lang/Class;
/* 52:   */     //   50: invokevirtual 73	org/omg/CORBA/portable/InputStream:read_Object	(Ljava/lang/Class;)Lorg/omg/CORBA/Object;
/* 53:   */     //   53: checkcast 20	com/ffusion/ffs/bpw/adminEJB/IBPWAdmin
/* 54:   */     //   56: astore_2
/* 55:   */     //   57: jsr +92 -> 149
/* 56:   */     //   60: aload_2
/* 57:   */     //   61: areturn
/* 58:   */     //   62: astore 5
/* 59:   */     //   64: aload 5
/* 60:   */     //   66: invokevirtual 69	org/omg/CORBA/portable/ApplicationException:getInputStream	()Lorg/omg/CORBA/portable/InputStream;
/* 61:   */     //   69: checkcast 39	org/omg/CORBA_2_3/portable/InputStream
/* 62:   */     //   72: astore_1
/* 63:   */     //   73: aload_1
/* 64:   */     //   74: invokevirtual 61	org/omg/CORBA/portable/InputStream:read_string	()Ljava/lang/String;
/* 65:   */     //   77: astore 6
/* 66:   */     //   79: aload 6
/* 67:   */     //   81: ldc 1
/* 68:   */     //   83: invokevirtual 67	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 69:   */     //   86: ifeq +32 -> 118
/* 70:   */     //   89: aload_1
/* 71:   */     //   90: getstatic 52	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$CreateException	Ljava/lang/Class;
/* 72:   */     //   93: ifnull +9 -> 102
/* 73:   */     //   96: getstatic 52	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$CreateException	Ljava/lang/Class;
/* 74:   */     //   99: goto +12 -> 111
/* 75:   */     //   102: ldc 11
/* 76:   */     //   104: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* 77:   */     //   107: dup
/* 78:   */     //   108: putstatic 52	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$CreateException	Ljava/lang/Class;
/* 79:   */     //   111: invokevirtual 77	org/omg/CORBA_2_3/portable/InputStream:read_value	(Ljava/lang/Class;)Ljava/io/Serializable;
/* 80:   */     //   114: checkcast 21	javax/ejb/CreateException
/* 81:   */     //   117: athrow
/* 82:   */     //   118: new 25	java/rmi/UnexpectedException
/* 83:   */     //   121: dup
/* 84:   */     //   122: aload 6
/* 85:   */     //   124: invokespecial 42	java/rmi/UnexpectedException:<init>	(Ljava/lang/String;)V
/* 86:   */     //   127: athrow
/* 87:   */     //   128: pop
/* 88:   */     //   129: jsr +20 -> 149
/* 89:   */     //   132: goto -132 -> 0
/* 90:   */     //   135: astore 5
/* 91:   */     //   137: aload 5
/* 92:   */     //   139: invokestatic 74	javax/rmi/CORBA/Util:mapSystemException	(Lorg/omg/CORBA/SystemException;)Ljava/rmi/RemoteException;
/* 93:   */     //   142: athrow
/* 94:   */     //   143: astore_3
/* 95:   */     //   144: jsr +5 -> 149
/* 96:   */     //   147: aload_3
/* 97:   */     //   148: athrow
/* 98:   */     //   149: astore 4
/* 99:   */     //   151: aload_0
/* :0:   */     //   152: aload_1
/* :1:   */     //   153: invokevirtual 45	org/omg/CORBA/portable/ObjectImpl:_releaseReply	(Lorg/omg/CORBA/portable/InputStream;)V
/* :2:   */     //   156: ret 4
/* :3:   */     //   158: aload_0
/* :4:   */     //   159: ldc 12
/* :5:   */     //   161: getstatic 54	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$com$ffusion$ffs$bpw$adminEJB$BPWAdminHome	Ljava/lang/Class;
/* :6:   */     //   164: ifnull +9 -> 173
/* :7:   */     //   167: getstatic 54	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$com$ffusion$ffs$bpw$adminEJB$BPWAdminHome	Ljava/lang/Class;
/* :8:   */     //   170: goto +12 -> 182
/* :9:   */     //   173: ldc 7
/* ;0:   */     //   175: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* ;1:   */     //   178: dup
/* ;2:   */     //   179: putstatic 54	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$com$ffusion$ffs$bpw$adminEJB$BPWAdminHome	Ljava/lang/Class;
/* ;3:   */     //   182: invokevirtual 48	org/omg/CORBA/portable/ObjectImpl:_servant_preinvoke	(Ljava/lang/String;Ljava/lang/Class;)Lorg/omg/CORBA/portable/ServantObject;
/* ;4:   */     //   185: astore_1
/* ;5:   */     //   186: aload_1
/* ;6:   */     //   187: ifnull +86 -> 273
/* ;7:   */     //   190: aload_1
/* ;8:   */     //   191: getfield 79	org/omg/CORBA/portable/ServantObject:servant	Ljava/lang/Object;
/* ;9:   */     //   194: checkcast 17	com/ffusion/ffs/bpw/adminEJB/BPWAdminHome
/* <0:   */     //   197: invokeinterface 62 1 0
/* <1:   */     //   202: astore 5
/* <2:   */     //   204: aload 5
/* <3:   */     //   206: aload_0
/* <4:   */     //   207: invokevirtual 43	org/omg/CORBA/portable/ObjectImpl:_orb	()Lorg/omg/CORBA/ORB;
/* <5:   */     //   210: invokestatic 63	javax/rmi/CORBA/Util:copyObject	(Ljava/lang/Object;Lorg/omg/CORBA/ORB;)Ljava/lang/Object;
/* <6:   */     //   213: checkcast 20	com/ffusion/ffs/bpw/adminEJB/IBPWAdmin
/* <7:   */     //   216: astore_2
/* <8:   */     //   217: jsr +47 -> 264
/* <9:   */     //   220: aload_2
/* =0:   */     //   221: areturn
/* =1:   */     //   222: astore 5
/* =2:   */     //   224: aload 5
/* =3:   */     //   226: aload_0
/* =4:   */     //   227: invokevirtual 43	org/omg/CORBA/portable/ObjectImpl:_orb	()Lorg/omg/CORBA/ORB;
/* =5:   */     //   230: invokestatic 63	javax/rmi/CORBA/Util:copyObject	(Ljava/lang/Object;Lorg/omg/CORBA/ORB;)Ljava/lang/Object;
/* =6:   */     //   233: checkcast 23	java/lang/Throwable
/* =7:   */     //   236: astore 6
/* =8:   */     //   238: aload 6
/* =9:   */     //   240: instanceof 21
/* >0:   */     //   243: ifeq +9 -> 252
/* >1:   */     //   246: aload 6
/* >2:   */     //   248: checkcast 21	javax/ejb/CreateException
/* >3:   */     //   251: athrow
/* >4:   */     //   252: aload 6
/* >5:   */     //   254: invokestatic 75	javax/rmi/CORBA/Util:wrapException	(Ljava/lang/Throwable;)Ljava/rmi/RemoteException;
/* >6:   */     //   257: athrow
/* >7:   */     //   258: astore_3
/* >8:   */     //   259: jsr +5 -> 264
/* >9:   */     //   262: aload_3
/* ?0:   */     //   263: athrow
/* ?1:   */     //   264: astore 4
/* ?2:   */     //   266: aload_0
/* ?3:   */     //   267: aload_1
/* ?4:   */     //   268: invokevirtual 47	org/omg/CORBA/portable/ObjectImpl:_servant_postinvoke	(Lorg/omg/CORBA/portable/ServantObject;)V
/* ?5:   */     //   271: ret 4
/* ?6:   */     //   273: goto -273 -> 0
/* ?7:   */     // Line number table:
/* ?8:   */     //   Java source line #210	-> byte code offset #0
/* ?9:   */     //   Java source line #211	-> byte code offset #7
/* @0:   */     //   Java source line #212	-> byte code offset #9
/* @1:   */     //   Java source line #213	-> byte code offset #9
/* @2:   */     //   Java source line #214	-> byte code offset #9
/* @3:   */     //   Java source line #215	-> byte code offset #18
/* @4:   */     //   Java source line #216	-> byte code offset #28
/* @5:   */     //   Java source line #217	-> byte code offset #62
/* @6:   */     //   Java source line #218	-> byte code offset #64
/* @7:   */     //   Java source line #219	-> byte code offset #73
/* @8:   */     //   Java source line #220	-> byte code offset #79
/* @9:   */     //   Java source line #221	-> byte code offset #89
/* A0:   */     //   Java source line #223	-> byte code offset #118
/* A1:   */     //   Java source line #224	-> byte code offset #128
/* A2:   */     //   Java source line #225	-> byte code offset #129
/* A3:   */     //   Java source line #227	-> byte code offset #135
/* A4:   */     //   Java source line #228	-> byte code offset #137
/* A5:   */     //   Java source line #212	-> byte code offset #143
/* A6:   */     //   Java source line #230	-> byte code offset #151
/* A7:   */     //   Java source line #212	-> byte code offset #156
/* A8:   */     //   Java source line #233	-> byte code offset #158
/* A9:   */     //   Java source line #234	-> byte code offset #186
/* B0:   */     //   Java source line #237	-> byte code offset #190
/* B1:   */     //   Java source line #238	-> byte code offset #190
/* B2:   */     //   Java source line #239	-> byte code offset #204
/* B3:   */     //   Java source line #240	-> byte code offset #222
/* B4:   */     //   Java source line #241	-> byte code offset #224
/* B5:   */     //   Java source line #242	-> byte code offset #238
/* B6:   */     //   Java source line #243	-> byte code offset #246
/* B7:   */     //   Java source line #245	-> byte code offset #252
/* B8:   */     //   Java source line #237	-> byte code offset #258
/* B9:   */     //   Java source line #247	-> byte code offset #266
/* C0:   */     //   Java source line #237	-> byte code offset #271
/* C1:   */     //   Java source line #209	-> byte code offset #273
/* C2:   */     // Local variable table:
/* C3:   */     //   start	length	slot	name	signature
/* C4:   */     //   0	276	0	this	_BPWAdminHome_Stub
/* C5:   */     //   8	260	1	localObject1	java.lang.Object
/* C6:   */     //   56	165	2	localIBPWAdmin1	IBPWAdmin
/* C7:   */     //   143	5	3	localObject2	java.lang.Object
/* C8:   */     //   258	5	3	localObject3	java.lang.Object
/* C9:   */     //   149	1	4	localObject4	java.lang.Object
/* D0:   */     //   264	1	4	localObject5	java.lang.Object
/* D1:   */     //   16	4	5	localOutputStream	org.omg.CORBA.portable.OutputStream
/* D2:   */     //   62	3	5	localApplicationException	org.omg.CORBA.portable.ApplicationException
/* D3:   */     //   135	3	5	localSystemException	org.omg.CORBA.SystemException
/* D4:   */     //   202	3	5	localIBPWAdmin2	IBPWAdmin
/* D5:   */     //   222	3	5	localThrowable	java.lang.Throwable
/* D6:   */     //   77	176	6	localObject6	java.lang.Object
/* D7:   */     //   128	1	13	localRemarshalException	org.omg.CORBA.portable.RemarshalException
/* D8:   */     // Exception table:
/* D9:   */     //   from	to	target	type
/* E0:   */     //   9	62	62	org/omg/CORBA/portable/ApplicationException
/* E1:   */     //   9	62	128	org/omg/CORBA/portable/RemarshalException
/* E2:   */     //   9	135	135	org/omg/CORBA/SystemException
/* E3:   */     //   9	143	143	finally
/* E4:   */     //   190	222	222	java/lang/Throwable
/* E5:   */     //   190	258	258	finally
/* E6:   */   }
/* E7:   */   
/* E8:   */   /* Error */
/* E9:   */   public EJBMetaData getEJBMetaData()
/* F0:   */     throws java.rmi.RemoteException
/* F1:   */   {
/* F2:   */     // Byte code:
/* F3:   */     //   0: aload_0
/* F4:   */     //   1: invokestatic 72	javax/rmi/CORBA/Util:isLocal	(Ljavax/rmi/CORBA/Stub;)Z
/* F5:   */     //   4: ifne +115 -> 119
/* F6:   */     //   7: aconst_null
/* F7:   */     //   8: astore_1
/* F8:   */     //   9: aload_0
/* F9:   */     //   10: ldc 5
/* G0:   */     //   12: iconst_1
/* G1:   */     //   13: invokevirtual 46	org/omg/CORBA/portable/ObjectImpl:_request	(Ljava/lang/String;Z)Lorg/omg/CORBA/portable/OutputStream;
/* G2:   */     //   16: astore 5
/* G3:   */     //   18: aload_0
/* G4:   */     //   19: aload 5
/* G5:   */     //   21: invokevirtual 55	org/omg/CORBA/portable/ObjectImpl:_invoke	(Lorg/omg/CORBA/portable/OutputStream;)Lorg/omg/CORBA/portable/InputStream;
/* G6:   */     //   24: checkcast 39	org/omg/CORBA_2_3/portable/InputStream
/* G7:   */     //   27: astore_1
/* G8:   */     //   28: aload_1
/* G9:   */     //   29: getstatic 60	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBMetaData	Ljava/lang/Class;
/* H0:   */     //   32: ifnull +9 -> 41
/* H1:   */     //   35: getstatic 60	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBMetaData	Ljava/lang/Class;
/* H2:   */     //   38: goto +12 -> 50
/* H3:   */     //   41: ldc 8
/* H4:   */     //   43: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* H5:   */     //   46: dup
/* H6:   */     //   47: putstatic 60	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBMetaData	Ljava/lang/Class;
/* H7:   */     //   50: invokevirtual 77	org/omg/CORBA_2_3/portable/InputStream:read_value	(Ljava/lang/Class;)Ljava/io/Serializable;
/* H8:   */     //   53: checkcast 26	javax/ejb/EJBMetaData
/* H9:   */     //   56: astore_2
/* I0:   */     //   57: jsr +53 -> 110
/* I1:   */     //   60: aload_2
/* I2:   */     //   61: areturn
/* I3:   */     //   62: astore 5
/* I4:   */     //   64: aload 5
/* I5:   */     //   66: invokevirtual 69	org/omg/CORBA/portable/ApplicationException:getInputStream	()Lorg/omg/CORBA/portable/InputStream;
/* I6:   */     //   69: checkcast 39	org/omg/CORBA_2_3/portable/InputStream
/* I7:   */     //   72: astore_1
/* I8:   */     //   73: aload_1
/* I9:   */     //   74: invokevirtual 61	org/omg/CORBA/portable/InputStream:read_string	()Ljava/lang/String;
/* J0:   */     //   77: astore 6
/* J1:   */     //   79: new 25	java/rmi/UnexpectedException
/* J2:   */     //   82: dup
/* J3:   */     //   83: aload 6
/* J4:   */     //   85: invokespecial 42	java/rmi/UnexpectedException:<init>	(Ljava/lang/String;)V
/* J5:   */     //   88: athrow
/* J6:   */     //   89: pop
/* J7:   */     //   90: jsr +20 -> 110
/* J8:   */     //   93: goto -93 -> 0
/* J9:   */     //   96: astore 5
/* K0:   */     //   98: aload 5
/* K1:   */     //   100: invokestatic 74	javax/rmi/CORBA/Util:mapSystemException	(Lorg/omg/CORBA/SystemException;)Ljava/rmi/RemoteException;
/* K2:   */     //   103: athrow
/* K3:   */     //   104: astore_3
/* K4:   */     //   105: jsr +5 -> 110
/* K5:   */     //   108: aload_3
/* K6:   */     //   109: athrow
/* K7:   */     //   110: astore 4
/* K8:   */     //   112: aload_0
/* K9:   */     //   113: aload_1
/* L0:   */     //   114: invokevirtual 45	org/omg/CORBA/portable/ObjectImpl:_releaseReply	(Lorg/omg/CORBA/portable/InputStream;)V
/* L1:   */     //   117: ret 4
/* L2:   */     //   119: aload_0
/* L3:   */     //   120: ldc 5
/* L4:   */     //   122: getstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* L5:   */     //   125: ifnull +9 -> 134
/* L6:   */     //   128: getstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* L7:   */     //   131: goto +12 -> 143
/* L8:   */     //   134: ldc 10
/* L9:   */     //   136: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* M0:   */     //   139: dup
/* M1:   */     //   140: putstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* M2:   */     //   143: invokevirtual 48	org/omg/CORBA/portable/ObjectImpl:_servant_preinvoke	(Ljava/lang/String;Ljava/lang/Class;)Lorg/omg/CORBA/portable/ServantObject;
/* M3:   */     //   146: astore_1
/* M4:   */     //   147: aload_1
/* M5:   */     //   148: ifnull +72 -> 220
/* M6:   */     //   151: aload_1
/* M7:   */     //   152: getfield 79	org/omg/CORBA/portable/ServantObject:servant	Ljava/lang/Object;
/* M8:   */     //   155: checkcast 30	javax/ejb/EJBHome
/* M9:   */     //   158: invokeinterface 66 1 0
/* N0:   */     //   163: astore 5
/* N1:   */     //   165: aload 5
/* N2:   */     //   167: aload_0
/* N3:   */     //   168: invokevirtual 43	org/omg/CORBA/portable/ObjectImpl:_orb	()Lorg/omg/CORBA/ORB;
/* N4:   */     //   171: invokestatic 63	javax/rmi/CORBA/Util:copyObject	(Ljava/lang/Object;Lorg/omg/CORBA/ORB;)Ljava/lang/Object;
/* N5:   */     //   174: checkcast 26	javax/ejb/EJBMetaData
/* N6:   */     //   177: astore_2
/* N7:   */     //   178: jsr +33 -> 211
/* N8:   */     //   181: aload_2
/* N9:   */     //   182: areturn
/* O0:   */     //   183: astore 5
/* O1:   */     //   185: aload 5
/* O2:   */     //   187: aload_0
/* O3:   */     //   188: invokevirtual 43	org/omg/CORBA/portable/ObjectImpl:_orb	()Lorg/omg/CORBA/ORB;
/* O4:   */     //   191: invokestatic 63	javax/rmi/CORBA/Util:copyObject	(Ljava/lang/Object;Lorg/omg/CORBA/ORB;)Ljava/lang/Object;
/* O5:   */     //   194: checkcast 23	java/lang/Throwable
/* O6:   */     //   197: astore 6
/* O7:   */     //   199: aload 6
/* O8:   */     //   201: invokestatic 75	javax/rmi/CORBA/Util:wrapException	(Ljava/lang/Throwable;)Ljava/rmi/RemoteException;
/* O9:   */     //   204: athrow
/* P0:   */     //   205: astore_3
/* P1:   */     //   206: jsr +5 -> 211
/* P2:   */     //   209: aload_3
/* P3:   */     //   210: athrow
/* P4:   */     //   211: astore 4
/* P5:   */     //   213: aload_0
/* P6:   */     //   214: aload_1
/* P7:   */     //   215: invokevirtual 47	org/omg/CORBA/portable/ObjectImpl:_servant_postinvoke	(Lorg/omg/CORBA/portable/ServantObject;)V
/* P8:   */     //   218: ret 4
/* P9:   */     //   220: goto -220 -> 0
/* Q0:   */     // Line number table:
/* Q1:   */     //   Java source line #132	-> byte code offset #0
/* Q2:   */     //   Java source line #133	-> byte code offset #7
/* Q3:   */     //   Java source line #134	-> byte code offset #9
/* Q4:   */     //   Java source line #135	-> byte code offset #9
/* Q5:   */     //   Java source line #136	-> byte code offset #9
/* Q6:   */     //   Java source line #137	-> byte code offset #18
/* Q7:   */     //   Java source line #138	-> byte code offset #28
/* Q8:   */     //   Java source line #139	-> byte code offset #62
/* Q9:   */     //   Java source line #140	-> byte code offset #64
/* R0:   */     //   Java source line #141	-> byte code offset #73
/* R1:   */     //   Java source line #142	-> byte code offset #79
/* R2:   */     //   Java source line #143	-> byte code offset #89
/* R3:   */     //   Java source line #144	-> byte code offset #90
/* R4:   */     //   Java source line #146	-> byte code offset #96
/* R5:   */     //   Java source line #147	-> byte code offset #98
/* R6:   */     //   Java source line #134	-> byte code offset #104
/* R7:   */     //   Java source line #149	-> byte code offset #112
/* R8:   */     //   Java source line #134	-> byte code offset #117
/* R9:   */     //   Java source line #152	-> byte code offset #119
/* S0:   */     //   Java source line #153	-> byte code offset #147
/* S1:   */     //   Java source line #156	-> byte code offset #151
/* S2:   */     //   Java source line #157	-> byte code offset #151
/* S3:   */     //   Java source line #158	-> byte code offset #165
/* S4:   */     //   Java source line #159	-> byte code offset #183
/* S5:   */     //   Java source line #160	-> byte code offset #185
/* S6:   */     //   Java source line #161	-> byte code offset #199
/* S7:   */     //   Java source line #156	-> byte code offset #205
/* S8:   */     //   Java source line #163	-> byte code offset #213
/* S9:   */     //   Java source line #156	-> byte code offset #218
/* T0:   */     //   Java source line #131	-> byte code offset #220
/* T1:   */     // Local variable table:
/* T2:   */     //   start	length	slot	name	signature
/* T3:   */     //   0	223	0	this	_BPWAdminHome_Stub
/* T4:   */     //   8	207	1	localObject1	java.lang.Object
/* T5:   */     //   56	126	2	localEJBMetaData1	EJBMetaData
/* T6:   */     //   104	5	3	localObject2	java.lang.Object
/* T7:   */     //   205	5	3	localObject3	java.lang.Object
/* T8:   */     //   110	1	4	localObject4	java.lang.Object
/* T9:   */     //   211	1	4	localObject5	java.lang.Object
/* U0:   */     //   16	4	5	localOutputStream	org.omg.CORBA.portable.OutputStream
/* U1:   */     //   62	3	5	localApplicationException	org.omg.CORBA.portable.ApplicationException
/* U2:   */     //   96	3	5	localSystemException	org.omg.CORBA.SystemException
/* U3:   */     //   163	3	5	localEJBMetaData2	EJBMetaData
/* U4:   */     //   183	3	5	localThrowable	java.lang.Throwable
/* U5:   */     //   77	123	6	localObject6	java.lang.Object
/* U6:   */     //   89	1	13	localRemarshalException	org.omg.CORBA.portable.RemarshalException
/* U7:   */     // Exception table:
/* U8:   */     //   from	to	target	type
/* U9:   */     //   9	62	62	org/omg/CORBA/portable/ApplicationException
/* V0:   */     //   9	62	89	org/omg/CORBA/portable/RemarshalException
/* V1:   */     //   9	96	96	org/omg/CORBA/SystemException
/* V2:   */     //   9	104	104	finally
/* V3:   */     //   151	183	183	java/lang/Throwable
/* V4:   */     //   151	205	205	finally
/* V5:   */   }
/* V6:   */   
/* V7:   */   /* Error */
/* V8:   */   public HomeHandle getHomeHandle()
/* V9:   */     throws java.rmi.RemoteException
/* W0:   */   {
/* W1:   */     // Byte code:
/* W2:   */     //   0: aload_0
/* W3:   */     //   1: invokestatic 72	javax/rmi/CORBA/Util:isLocal	(Ljavax/rmi/CORBA/Stub;)Z
/* W4:   */     //   4: ifne +115 -> 119
/* W5:   */     //   7: aconst_null
/* W6:   */     //   8: astore_1
/* W7:   */     //   9: aload_0
/* W8:   */     //   10: ldc 6
/* W9:   */     //   12: iconst_1
/* X0:   */     //   13: invokevirtual 46	org/omg/CORBA/portable/ObjectImpl:_request	(Ljava/lang/String;Z)Lorg/omg/CORBA/portable/OutputStream;
/* X1:   */     //   16: astore 5
/* X2:   */     //   18: aload_0
/* X3:   */     //   19: aload 5
/* X4:   */     //   21: invokevirtual 55	org/omg/CORBA/portable/ObjectImpl:_invoke	(Lorg/omg/CORBA/portable/OutputStream;)Lorg/omg/CORBA/portable/InputStream;
/* X5:   */     //   24: checkcast 39	org/omg/CORBA_2_3/portable/InputStream
/* X6:   */     //   27: astore_1
/* X7:   */     //   28: aload_1
/* X8:   */     //   29: getstatic 58	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$HomeHandle	Ljava/lang/Class;
/* X9:   */     //   32: ifnull +9 -> 41
/* Y0:   */     //   35: getstatic 58	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$HomeHandle	Ljava/lang/Class;
/* Y1:   */     //   38: goto +12 -> 50
/* Y2:   */     //   41: ldc 13
/* Y3:   */     //   43: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* Y4:   */     //   46: dup
/* Y5:   */     //   47: putstatic 58	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$HomeHandle	Ljava/lang/Class;
/* Y6:   */     //   50: invokevirtual 56	org/omg/CORBA_2_3/portable/InputStream:read_abstract_interface	(Ljava/lang/Class;)Ljava/lang/Object;
/* Y7:   */     //   53: checkcast 29	javax/ejb/HomeHandle
/* Y8:   */     //   56: astore_2
/* Y9:   */     //   57: jsr +53 -> 110
/* Z0:   */     //   60: aload_2
/* Z1:   */     //   61: areturn
/* Z2:   */     //   62: astore 5
/* Z3:   */     //   64: aload 5
/* Z4:   */     //   66: invokevirtual 69	org/omg/CORBA/portable/ApplicationException:getInputStream	()Lorg/omg/CORBA/portable/InputStream;
/* Z5:   */     //   69: checkcast 39	org/omg/CORBA_2_3/portable/InputStream
/* Z6:   */     //   72: astore_1
/* Z7:   */     //   73: aload_1
/* Z8:   */     //   74: invokevirtual 61	org/omg/CORBA/portable/InputStream:read_string	()Ljava/lang/String;
/* Z9:   */     //   77: astore 6
/* [0:   */     //   79: new 25	java/rmi/UnexpectedException
/* [1:   */     //   82: dup
/* [2:   */     //   83: aload 6
/* [3:   */     //   85: invokespecial 42	java/rmi/UnexpectedException:<init>	(Ljava/lang/String;)V
/* [4:   */     //   88: athrow
/* [5:   */     //   89: pop
/* [6:   */     //   90: jsr +20 -> 110
/* [7:   */     //   93: goto -93 -> 0
/* [8:   */     //   96: astore 5
/* [9:   */     //   98: aload 5
/* \0:   */     //   100: invokestatic 74	javax/rmi/CORBA/Util:mapSystemException	(Lorg/omg/CORBA/SystemException;)Ljava/rmi/RemoteException;
/* \1:   */     //   103: athrow
/* \2:   */     //   104: astore_3
/* \3:   */     //   105: jsr +5 -> 110
/* \4:   */     //   108: aload_3
/* \5:   */     //   109: athrow
/* \6:   */     //   110: astore 4
/* \7:   */     //   112: aload_0
/* \8:   */     //   113: aload_1
/* \9:   */     //   114: invokevirtual 45	org/omg/CORBA/portable/ObjectImpl:_releaseReply	(Lorg/omg/CORBA/portable/InputStream;)V
/* ]0:   */     //   117: ret 4
/* ]1:   */     //   119: aload_0
/* ]2:   */     //   120: ldc 6
/* ]3:   */     //   122: getstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* ]4:   */     //   125: ifnull +9 -> 134
/* ]5:   */     //   128: getstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* ]6:   */     //   131: goto +12 -> 143
/* ]7:   */     //   134: ldc 10
/* ]8:   */     //   136: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* ]9:   */     //   139: dup
/* ^0:   */     //   140: putstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* ^1:   */     //   143: invokevirtual 48	org/omg/CORBA/portable/ObjectImpl:_servant_preinvoke	(Ljava/lang/String;Ljava/lang/Class;)Lorg/omg/CORBA/portable/ServantObject;
/* ^2:   */     //   146: astore_1
/* ^3:   */     //   147: aload_1
/* ^4:   */     //   148: ifnull +72 -> 220
/* ^5:   */     //   151: aload_1
/* ^6:   */     //   152: getfield 79	org/omg/CORBA/portable/ServantObject:servant	Ljava/lang/Object;
/* ^7:   */     //   155: checkcast 30	javax/ejb/EJBHome
/* ^8:   */     //   158: invokeinterface 65 1 0
/* ^9:   */     //   163: astore 5
/* _0:   */     //   165: aload 5
/* _1:   */     //   167: aload_0
/* _2:   */     //   168: invokevirtual 43	org/omg/CORBA/portable/ObjectImpl:_orb	()Lorg/omg/CORBA/ORB;
/* _3:   */     //   171: invokestatic 63	javax/rmi/CORBA/Util:copyObject	(Ljava/lang/Object;Lorg/omg/CORBA/ORB;)Ljava/lang/Object;
/* _4:   */     //   174: checkcast 29	javax/ejb/HomeHandle
/* _5:   */     //   177: astore_2
/* _6:   */     //   178: jsr +33 -> 211
/* _7:   */     //   181: aload_2
/* _8:   */     //   182: areturn
/* _9:   */     //   183: astore 5
/* `0:   */     //   185: aload 5
/* `1:   */     //   187: aload_0
/* `2:   */     //   188: invokevirtual 43	org/omg/CORBA/portable/ObjectImpl:_orb	()Lorg/omg/CORBA/ORB;
/* `3:   */     //   191: invokestatic 63	javax/rmi/CORBA/Util:copyObject	(Ljava/lang/Object;Lorg/omg/CORBA/ORB;)Ljava/lang/Object;
/* `4:   */     //   194: checkcast 23	java/lang/Throwable
/* `5:   */     //   197: astore 6
/* `6:   */     //   199: aload 6
/* `7:   */     //   201: invokestatic 75	javax/rmi/CORBA/Util:wrapException	(Ljava/lang/Throwable;)Ljava/rmi/RemoteException;
/* `8:   */     //   204: athrow
/* `9:   */     //   205: astore_3
/* a0:   */     //   206: jsr +5 -> 211
/* a1:   */     //   209: aload_3
/* a2:   */     //   210: athrow
/* a3:   */     //   211: astore 4
/* a4:   */     //   213: aload_0
/* a5:   */     //   214: aload_1
/* a6:   */     //   215: invokevirtual 47	org/omg/CORBA/portable/ObjectImpl:_servant_postinvoke	(Lorg/omg/CORBA/portable/ServantObject;)V
/* a7:   */     //   218: ret 4
/* a8:   */     //   220: goto -220 -> 0
/* a9:   */     // Line number table:
/* b0:   */     //   Java source line #171	-> byte code offset #0
/* b1:   */     //   Java source line #172	-> byte code offset #7
/* b2:   */     //   Java source line #173	-> byte code offset #9
/* b3:   */     //   Java source line #174	-> byte code offset #9
/* b4:   */     //   Java source line #175	-> byte code offset #9
/* b5:   */     //   Java source line #176	-> byte code offset #18
/* b6:   */     //   Java source line #177	-> byte code offset #28
/* b7:   */     //   Java source line #178	-> byte code offset #62
/* b8:   */     //   Java source line #179	-> byte code offset #64
/* b9:   */     //   Java source line #180	-> byte code offset #73
/* c0:   */     //   Java source line #181	-> byte code offset #79
/* c1:   */     //   Java source line #182	-> byte code offset #89
/* c2:   */     //   Java source line #183	-> byte code offset #90
/* c3:   */     //   Java source line #185	-> byte code offset #96
/* c4:   */     //   Java source line #186	-> byte code offset #98
/* c5:   */     //   Java source line #173	-> byte code offset #104
/* c6:   */     //   Java source line #188	-> byte code offset #112
/* c7:   */     //   Java source line #173	-> byte code offset #117
/* c8:   */     //   Java source line #191	-> byte code offset #119
/* c9:   */     //   Java source line #192	-> byte code offset #147
/* d0:   */     //   Java source line #195	-> byte code offset #151
/* d1:   */     //   Java source line #196	-> byte code offset #151
/* d2:   */     //   Java source line #197	-> byte code offset #165
/* d3:   */     //   Java source line #198	-> byte code offset #183
/* d4:   */     //   Java source line #199	-> byte code offset #185
/* d5:   */     //   Java source line #200	-> byte code offset #199
/* d6:   */     //   Java source line #195	-> byte code offset #205
/* d7:   */     //   Java source line #202	-> byte code offset #213
/* d8:   */     //   Java source line #195	-> byte code offset #218
/* d9:   */     //   Java source line #170	-> byte code offset #220
/* e0:   */     // Local variable table:
/* e1:   */     //   start	length	slot	name	signature
/* e2:   */     //   0	223	0	this	_BPWAdminHome_Stub
/* e3:   */     //   8	207	1	localObject1	java.lang.Object
/* e4:   */     //   56	126	2	localHomeHandle1	HomeHandle
/* e5:   */     //   104	5	3	localObject2	java.lang.Object
/* e6:   */     //   205	5	3	localObject3	java.lang.Object
/* e7:   */     //   110	1	4	localObject4	java.lang.Object
/* e8:   */     //   211	1	4	localObject5	java.lang.Object
/* e9:   */     //   16	4	5	localOutputStream	org.omg.CORBA.portable.OutputStream
/* f0:   */     //   62	3	5	localApplicationException	org.omg.CORBA.portable.ApplicationException
/* f1:   */     //   96	3	5	localSystemException	org.omg.CORBA.SystemException
/* f2:   */     //   163	3	5	localHomeHandle2	HomeHandle
/* f3:   */     //   183	3	5	localThrowable	java.lang.Throwable
/* f4:   */     //   77	123	6	localObject6	java.lang.Object
/* f5:   */     //   89	1	13	localRemarshalException	org.omg.CORBA.portable.RemarshalException
/* f6:   */     // Exception table:
/* f7:   */     //   from	to	target	type
/* f8:   */     //   9	62	62	org/omg/CORBA/portable/ApplicationException
/* f9:   */     //   9	62	89	org/omg/CORBA/portable/RemarshalException
/* g0:   */     //   9	96	96	org/omg/CORBA/SystemException
/* g1:   */     //   9	104	104	finally
/* g2:   */     //   151	183	183	java/lang/Throwable
/* g3:   */     //   151	205	205	finally
/* g4:   */   }
/* g5:   */   
/* g6:   */   /* Error */
/* g7:   */   public void remove(java.lang.Object paramObject)
/* g8:   */     throws java.rmi.RemoteException, RemoveException
/* g9:   */   {
/* h0:   */     // Byte code:
/* h1:   */     //   0: aload_0
/* h2:   */     //   1: invokestatic 72	javax/rmi/CORBA/Util:isLocal	(Ljavax/rmi/CORBA/Stub;)Z
/* h3:   */     //   4: ifne +127 -> 131
/* h4:   */     //   7: aconst_null
/* h5:   */     //   8: astore_2
/* h6:   */     //   9: aload_0
/* h7:   */     //   10: ldc 15
/* h8:   */     //   12: iconst_1
/* h9:   */     //   13: invokevirtual 46	org/omg/CORBA/portable/ObjectImpl:_request	(Ljava/lang/String;Z)Lorg/omg/CORBA/portable/OutputStream;
/* i0:   */     //   16: astore 5
/* i1:   */     //   18: aload 5
/* i2:   */     //   20: aload_1
/* i3:   */     //   21: invokestatic 78	javax/rmi/CORBA/Util:writeAny	(Lorg/omg/CORBA/portable/OutputStream;Ljava/lang/Object;)V
/* i4:   */     //   24: aload_0
/* i5:   */     //   25: aload 5
/* i6:   */     //   27: invokevirtual 55	org/omg/CORBA/portable/ObjectImpl:_invoke	(Lorg/omg/CORBA/portable/OutputStream;)Lorg/omg/CORBA/portable/InputStream;
/* i7:   */     //   30: pop
/* i8:   */     //   31: jsr +91 -> 122
/* i9:   */     //   34: return
/* j0:   */     //   35: astore 5
/* j1:   */     //   37: aload 5
/* j2:   */     //   39: invokevirtual 69	org/omg/CORBA/portable/ApplicationException:getInputStream	()Lorg/omg/CORBA/portable/InputStream;
/* j3:   */     //   42: checkcast 39	org/omg/CORBA_2_3/portable/InputStream
/* j4:   */     //   45: astore_2
/* j5:   */     //   46: aload_2
/* j6:   */     //   47: invokevirtual 61	org/omg/CORBA/portable/InputStream:read_string	()Ljava/lang/String;
/* j7:   */     //   50: astore 6
/* j8:   */     //   52: aload 6
/* j9:   */     //   54: ldc 2
/* k0:   */     //   56: invokevirtual 67	java/lang/String:equals	(Ljava/lang/Object;)Z
/* k1:   */     //   59: ifeq +32 -> 91
/* k2:   */     //   62: aload_2
/* k3:   */     //   63: getstatic 59	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$RemoveException	Ljava/lang/Class;
/* k4:   */     //   66: ifnull +9 -> 75
/* k5:   */     //   69: getstatic 59	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$RemoveException	Ljava/lang/Class;
/* k6:   */     //   72: goto +12 -> 84
/* k7:   */     //   75: ldc 14
/* k8:   */     //   77: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* k9:   */     //   80: dup
/* l0:   */     //   81: putstatic 59	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$RemoveException	Ljava/lang/Class;
/* l1:   */     //   84: invokevirtual 77	org/omg/CORBA_2_3/portable/InputStream:read_value	(Ljava/lang/Class;)Ljava/io/Serializable;
/* l2:   */     //   87: checkcast 31	javax/ejb/RemoveException
/* l3:   */     //   90: athrow
/* l4:   */     //   91: new 25	java/rmi/UnexpectedException
/* l5:   */     //   94: dup
/* l6:   */     //   95: aload 6
/* l7:   */     //   97: invokespecial 42	java/rmi/UnexpectedException:<init>	(Ljava/lang/String;)V
/* l8:   */     //   100: athrow
/* l9:   */     //   101: pop
/* m0:   */     //   102: jsr +20 -> 122
/* m1:   */     //   105: goto -105 -> 0
/* m2:   */     //   108: astore 5
/* m3:   */     //   110: aload 5
/* m4:   */     //   112: invokestatic 74	javax/rmi/CORBA/Util:mapSystemException	(Lorg/omg/CORBA/SystemException;)Ljava/rmi/RemoteException;
/* m5:   */     //   115: athrow
/* m6:   */     //   116: astore_3
/* m7:   */     //   117: jsr +5 -> 122
/* m8:   */     //   120: aload_3
/* m9:   */     //   121: athrow
/* n0:   */     //   122: astore 4
/* n1:   */     //   124: aload_0
/* n2:   */     //   125: aload_2
/* n3:   */     //   126: invokevirtual 45	org/omg/CORBA/portable/ObjectImpl:_releaseReply	(Lorg/omg/CORBA/portable/InputStream;)V
/* n4:   */     //   129: ret 4
/* n5:   */     //   131: aload_0
/* n6:   */     //   132: ldc 15
/* n7:   */     //   134: getstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* n8:   */     //   137: ifnull +9 -> 146
/* n9:   */     //   140: getstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* o0:   */     //   143: goto +12 -> 155
/* o1:   */     //   146: ldc 10
/* o2:   */     //   148: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* o3:   */     //   151: dup
/* o4:   */     //   152: putstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* o5:   */     //   155: invokevirtual 48	org/omg/CORBA/portable/ObjectImpl:_servant_preinvoke	(Ljava/lang/String;Ljava/lang/Class;)Lorg/omg/CORBA/portable/ServantObject;
/* o6:   */     //   158: astore_2
/* o7:   */     //   159: aload_2
/* o8:   */     //   160: ifnull +82 -> 242
/* o9:   */     //   163: aload_1
/* p0:   */     //   164: aload_0
/* p1:   */     //   165: invokevirtual 43	org/omg/CORBA/portable/ObjectImpl:_orb	()Lorg/omg/CORBA/ORB;
/* p2:   */     //   168: invokestatic 63	javax/rmi/CORBA/Util:copyObject	(Ljava/lang/Object;Lorg/omg/CORBA/ORB;)Ljava/lang/Object;
/* p3:   */     //   171: astore 5
/* p4:   */     //   173: aload_2
/* p5:   */     //   174: getfield 79	org/omg/CORBA/portable/ServantObject:servant	Ljava/lang/Object;
/* p6:   */     //   177: checkcast 30	javax/ejb/EJBHome
/* p7:   */     //   180: aload 5
/* p8:   */     //   182: invokeinterface 76 2 0
/* p9:   */     //   187: jsr +46 -> 233
/* q0:   */     //   190: return
/* q1:   */     //   191: astore 5
/* q2:   */     //   193: aload 5
/* q3:   */     //   195: aload_0
/* q4:   */     //   196: invokevirtual 43	org/omg/CORBA/portable/ObjectImpl:_orb	()Lorg/omg/CORBA/ORB;
/* q5:   */     //   199: invokestatic 63	javax/rmi/CORBA/Util:copyObject	(Ljava/lang/Object;Lorg/omg/CORBA/ORB;)Ljava/lang/Object;
/* q6:   */     //   202: checkcast 23	java/lang/Throwable
/* q7:   */     //   205: astore 6
/* q8:   */     //   207: aload 6
/* q9:   */     //   209: instanceof 31
/* r0:   */     //   212: ifeq +9 -> 221
/* r1:   */     //   215: aload 6
/* r2:   */     //   217: checkcast 31	javax/ejb/RemoveException
/* r3:   */     //   220: athrow
/* r4:   */     //   221: aload 6
/* r5:   */     //   223: invokestatic 75	javax/rmi/CORBA/Util:wrapException	(Ljava/lang/Throwable;)Ljava/rmi/RemoteException;
/* r6:   */     //   226: athrow
/* r7:   */     //   227: astore_3
/* r8:   */     //   228: jsr +5 -> 233
/* r9:   */     //   231: aload_3
/* s0:   */     //   232: athrow
/* s1:   */     //   233: astore 4
/* s2:   */     //   235: aload_0
/* s3:   */     //   236: aload_2
/* s4:   */     //   237: invokevirtual 47	org/omg/CORBA/portable/ObjectImpl:_servant_postinvoke	(Lorg/omg/CORBA/portable/ServantObject;)V
/* s5:   */     //   240: ret 4
/* s6:   */     //   242: goto -242 -> 0
/* s7:   */     // Line number table:
/* s8:   */     //   Java source line #85	-> byte code offset #0
/* s9:   */     //   Java source line #86	-> byte code offset #7
/* t0:   */     //   Java source line #87	-> byte code offset #9
/* t1:   */     //   Java source line #88	-> byte code offset #9
/* t2:   */     //   Java source line #89	-> byte code offset #9
/* t3:   */     //   Java source line #90	-> byte code offset #18
/* t4:   */     //   Java source line #91	-> byte code offset #24
/* t5:   */     //   Java source line #92	-> byte code offset #31
/* t6:   */     //   Java source line #93	-> byte code offset #35
/* t7:   */     //   Java source line #94	-> byte code offset #37
/* t8:   */     //   Java source line #95	-> byte code offset #46
/* t9:   */     //   Java source line #96	-> byte code offset #52
/* u0:   */     //   Java source line #97	-> byte code offset #62
/* u1:   */     //   Java source line #99	-> byte code offset #91
/* u2:   */     //   Java source line #100	-> byte code offset #101
/* u3:   */     //   Java source line #101	-> byte code offset #102
/* u4:   */     //   Java source line #103	-> byte code offset #108
/* u5:   */     //   Java source line #104	-> byte code offset #110
/* u6:   */     //   Java source line #87	-> byte code offset #116
/* u7:   */     //   Java source line #106	-> byte code offset #124
/* u8:   */     //   Java source line #87	-> byte code offset #129
/* u9:   */     //   Java source line #109	-> byte code offset #131
/* v0:   */     //   Java source line #110	-> byte code offset #159
/* v1:   */     //   Java source line #113	-> byte code offset #163
/* v2:   */     //   Java source line #114	-> byte code offset #163
/* v3:   */     //   Java source line #115	-> byte code offset #173
/* v4:   */     //   Java source line #116	-> byte code offset #187
/* v5:   */     //   Java source line #117	-> byte code offset #191
/* v6:   */     //   Java source line #118	-> byte code offset #193
/* v7:   */     //   Java source line #119	-> byte code offset #207
/* v8:   */     //   Java source line #120	-> byte code offset #215
/* v9:   */     //   Java source line #122	-> byte code offset #221
/* w0:   */     //   Java source line #113	-> byte code offset #227
/* w1:   */     //   Java source line #124	-> byte code offset #235
/* w2:   */     //   Java source line #113	-> byte code offset #240
/* w3:   */     //   Java source line #84	-> byte code offset #242
/* w4:   */     // Local variable table:
/* w5:   */     //   start	length	slot	name	signature
/* w6:   */     //   0	245	0	this	_BPWAdminHome_Stub
/* w7:   */     //   0	245	1	paramObject	java.lang.Object
/* w8:   */     //   8	229	2	localObject1	java.lang.Object
/* w9:   */     //   116	5	3	localObject2	java.lang.Object
/* x0:   */     //   227	5	3	localObject3	java.lang.Object
/* x1:   */     //   122	1	4	localObject4	java.lang.Object
/* x2:   */     //   233	1	4	localObject5	java.lang.Object
/* x3:   */     //   16	10	5	localOutputStream	org.omg.CORBA.portable.OutputStream
/* x4:   */     //   35	3	5	localApplicationException	org.omg.CORBA.portable.ApplicationException
/* x5:   */     //   108	3	5	localSystemException	org.omg.CORBA.SystemException
/* x6:   */     //   171	10	5	localObject6	java.lang.Object
/* x7:   */     //   191	3	5	localThrowable	java.lang.Throwable
/* x8:   */     //   50	172	6	localObject7	java.lang.Object
/* x9:   */     //   101	1	13	localRemarshalException	org.omg.CORBA.portable.RemarshalException
/* y0:   */     // Exception table:
/* y1:   */     //   from	to	target	type
/* y2:   */     //   9	35	35	org/omg/CORBA/portable/ApplicationException
/* y3:   */     //   9	35	101	org/omg/CORBA/portable/RemarshalException
/* y4:   */     //   9	108	108	org/omg/CORBA/SystemException
/* y5:   */     //   9	116	116	finally
/* y6:   */     //   163	191	191	java/lang/Throwable
/* y7:   */     //   163	227	227	finally
/* y8:   */   }
/* y9:   */   
/* z0:   */   /* Error */
/* z1:   */   public void remove(javax.ejb.Handle paramHandle)
/* z2:   */     throws java.rmi.RemoteException, RemoveException
/* z3:   */   {
/* z4:   */     // Byte code:
/* z5:   */     //   0: aload_0
/* z6:   */     //   1: invokestatic 72	javax/rmi/CORBA/Util:isLocal	(Ljavax/rmi/CORBA/Stub;)Z
/* z7:   */     //   4: ifne +127 -> 131
/* z8:   */     //   7: aconst_null
/* z9:   */     //   8: astore_2
/* {0:   */     //   9: aload_0
/* {1:   */     //   10: ldc 16
/* {2:   */     //   12: iconst_1
/* {3:   */     //   13: invokevirtual 46	org/omg/CORBA/portable/ObjectImpl:_request	(Ljava/lang/String;Z)Lorg/omg/CORBA/portable/OutputStream;
/* {4:   */     //   16: astore 5
/* {5:   */     //   18: aload 5
/* {6:   */     //   20: aload_1
/* {7:   */     //   21: invokestatic 71	javax/rmi/CORBA/Util:writeAbstractObject	(Lorg/omg/CORBA/portable/OutputStream;Ljava/lang/Object;)V
/* {8:   */     //   24: aload_0
/* {9:   */     //   25: aload 5
/* |0:   */     //   27: invokevirtual 55	org/omg/CORBA/portable/ObjectImpl:_invoke	(Lorg/omg/CORBA/portable/OutputStream;)Lorg/omg/CORBA/portable/InputStream;
/* |1:   */     //   30: pop
/* |2:   */     //   31: jsr +91 -> 122
/* |3:   */     //   34: return
/* |4:   */     //   35: astore 5
/* |5:   */     //   37: aload 5
/* |6:   */     //   39: invokevirtual 69	org/omg/CORBA/portable/ApplicationException:getInputStream	()Lorg/omg/CORBA/portable/InputStream;
/* |7:   */     //   42: checkcast 39	org/omg/CORBA_2_3/portable/InputStream
/* |8:   */     //   45: astore_2
/* |9:   */     //   46: aload_2
/* }0:   */     //   47: invokevirtual 61	org/omg/CORBA/portable/InputStream:read_string	()Ljava/lang/String;
/* }1:   */     //   50: astore 6
/* }2:   */     //   52: aload 6
/* }3:   */     //   54: ldc 2
/* }4:   */     //   56: invokevirtual 67	java/lang/String:equals	(Ljava/lang/Object;)Z
/* }5:   */     //   59: ifeq +32 -> 91
/* }6:   */     //   62: aload_2
/* }7:   */     //   63: getstatic 59	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$RemoveException	Ljava/lang/Class;
/* }8:   */     //   66: ifnull +9 -> 75
/* }9:   */     //   69: getstatic 59	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$RemoveException	Ljava/lang/Class;
/* ~0:   */     //   72: goto +12 -> 84
/* ~1:   */     //   75: ldc 14
/* ~2:   */     //   77: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* ~3:   */     //   80: dup
/* ~4:   */     //   81: putstatic 59	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$RemoveException	Ljava/lang/Class;
/* ~5:   */     //   84: invokevirtual 77	org/omg/CORBA_2_3/portable/InputStream:read_value	(Ljava/lang/Class;)Ljava/io/Serializable;
/* ~6:   */     //   87: checkcast 31	javax/ejb/RemoveException
/* ~7:   */     //   90: athrow
/* ~8:   */     //   91: new 25	java/rmi/UnexpectedException
/* ~9:   */     //   94: dup
/* 0:   */     //   95: aload 6
/* 1:   */     //   97: invokespecial 42	java/rmi/UnexpectedException:<init>	(Ljava/lang/String;)V
/* 2:   */     //   100: athrow
/* 3:   */     //   101: pop
/* 4:   */     //   102: jsr +20 -> 122
/* 5:   */     //   105: goto -105 -> 0
/* 6:   */     //   108: astore 5
/* 7:   */     //   110: aload 5
/* 8:   */     //   112: invokestatic 74	javax/rmi/CORBA/Util:mapSystemException	(Lorg/omg/CORBA/SystemException;)Ljava/rmi/RemoteException;
/* 9:   */     //   115: athrow
/* 0:   */     //   116: astore_3
/* 1:   */     //   117: jsr +5 -> 122
/* 2:   */     //   120: aload_3
/* 3:   */     //   121: athrow
/* 4:   */     //   122: astore 4
/* 5:   */     //   124: aload_0
/* 6:   */     //   125: aload_2
/* 7:   */     //   126: invokevirtual 45	org/omg/CORBA/portable/ObjectImpl:_releaseReply	(Lorg/omg/CORBA/portable/InputStream;)V
/* 8:   */     //   129: ret 4
/* 9:   */     //   131: aload_0
/* 0:   */     //   132: ldc 16
/* 1:   */     //   134: getstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* 2:   */     //   137: ifnull +9 -> 146
/* 3:   */     //   140: getstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* 4:   */     //   143: goto +12 -> 155
/* 5:   */     //   146: ldc 10
/* 6:   */     //   148: invokestatic 53	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$	(Ljava/lang/String;)Ljava/lang/Class;
/* 7:   */     //   151: dup
/* 8:   */     //   152: putstatic 57	com/ffusion/ffs/bpw/adminEJB/_BPWAdminHome_Stub:class$javax$ejb$EJBHome	Ljava/lang/Class;
/* 9:   */     //   155: invokevirtual 48	org/omg/CORBA/portable/ObjectImpl:_servant_preinvoke	(Ljava/lang/String;Ljava/lang/Class;)Lorg/omg/CORBA/portable/ServantObject;
/* 0:   */     //   158: astore_2
/* 1:   */     //   159: aload_2
/* 2:   */     //   160: ifnull +85 -> 245
/* 3:   */     //   163: aload_1
/* 4:   */     //   164: aload_0
/* 5:   */     //   165: invokevirtual 43	org/omg/CORBA/portable/ObjectImpl:_orb	()Lorg/omg/CORBA/ORB;
/* 6:   */     //   168: invokestatic 63	javax/rmi/CORBA/Util:copyObject	(Ljava/lang/Object;Lorg/omg/CORBA/ORB;)Ljava/lang/Object;
/* 7:   */     //   171: checkcast 27	javax/ejb/Handle
/* 8:   */     //   174: astore 5
/* 9:   */     //   176: aload_2
/* 0:   */     //   177: getfield 79	org/omg/CORBA/portable/ServantObject:servant	Ljava/lang/Object;
/* 1:   */     //   180: checkcast 30	javax/ejb/EJBHome
/* 2:   */     //   183: aload 5
/* 3:   */     //   185: invokeinterface 68 2 0
/* 4:   */     //   190: jsr +46 -> 236
/* 5:   */     //   193: return
/* 6:   */     //   194: astore 5
/* 7:   */     //   196: aload 5
/* 8:   */     //   198: aload_0
/* 9:   */     //   199: invokevirtual 43	org/omg/CORBA/portable/ObjectImpl:_orb	()Lorg/omg/CORBA/ORB;
/* 0:   */     //   202: invokestatic 63	javax/rmi/CORBA/Util:copyObject	(Ljava/lang/Object;Lorg/omg/CORBA/ORB;)Ljava/lang/Object;
/* 1:   */     //   205: checkcast 23	java/lang/Throwable
/* 2:   */     //   208: astore 6
/* 3:   */     //   210: aload 6
/* 4:   */     //   212: instanceof 31
/* 5:   */     //   215: ifeq +9 -> 224
/* 6:   */     //   218: aload 6
/* 7:   */     //   220: checkcast 31	javax/ejb/RemoveException
/* 8:   */     //   223: athrow
/* 9:   */     //   224: aload 6
/* 0:   */     //   226: invokestatic 75	javax/rmi/CORBA/Util:wrapException	(Ljava/lang/Throwable;)Ljava/rmi/RemoteException;
/* 1:   */     //   229: athrow
/* 2:   */     //   230: astore_3
/* 3:   */     //   231: jsr +5 -> 236
/* 4:   */     //   234: aload_3
/* 5:   */     //   235: athrow
/* 6:   */     //   236: astore 4
/* 7:   */     //   238: aload_0
/* 8:   */     //   239: aload_2
/* 9:   */     //   240: invokevirtual 47	org/omg/CORBA/portable/ObjectImpl:_servant_postinvoke	(Lorg/omg/CORBA/portable/ServantObject;)V
/* 0:   */     //   243: ret 4
/* 1:   */     //   245: goto -245 -> 0
/* 2:   */     // Line number table:
/* 3:   */     //   Java source line #38	-> byte code offset #0
/* 4:   */     //   Java source line #39	-> byte code offset #7
/* 5:   */     //   Java source line #40	-> byte code offset #9
/* 6:   */     //   Java source line #41	-> byte code offset #9
/* 7:   */     //   Java source line #42	-> byte code offset #9
/* 8:   */     //   Java source line #43	-> byte code offset #18
/* 9:   */     //   Java source line #44	-> byte code offset #24
/* 0:   */     //   Java source line #45	-> byte code offset #31
/* 1:   */     //   Java source line #46	-> byte code offset #35
/* 2:   */     //   Java source line #47	-> byte code offset #37
/* 3:   */     //   Java source line #48	-> byte code offset #46
/* 4:   */     //   Java source line #49	-> byte code offset #52
/* 5:   */     //   Java source line #50	-> byte code offset #62
/* 6:   */     //   Java source line #52	-> byte code offset #91
/* 7:   */     //   Java source line #53	-> byte code offset #101
/* 8:   */     //   Java source line #54	-> byte code offset #102
/* 9:   */     //   Java source line #56	-> byte code offset #108
/* 0:   */     //   Java source line #57	-> byte code offset #110
/* 1:   */     //   Java source line #40	-> byte code offset #116
/* 2:   */     //   Java source line #59	-> byte code offset #124
/* 3:   */     //   Java source line #40	-> byte code offset #129
/* 4:   */     //   Java source line #62	-> byte code offset #131
/* 5:   */     //   Java source line #63	-> byte code offset #159
/* 6:   */     //   Java source line #66	-> byte code offset #163
/* 7:   */     //   Java source line #67	-> byte code offset #163
/* 8:   */     //   Java source line #68	-> byte code offset #176
/* 9:   */     //   Java source line #69	-> byte code offset #190
/* 0:   */     //   Java source line #70	-> byte code offset #194
/* 1:   */     //   Java source line #71	-> byte code offset #196
/* 2:   */     //   Java source line #72	-> byte code offset #210
/* 3:   */     //   Java source line #73	-> byte code offset #218
/* 4:   */     //   Java source line #75	-> byte code offset #224
/* 5:   */     //   Java source line #66	-> byte code offset #230
/* 6:   */     //   Java source line #77	-> byte code offset #238
/* 7:   */     //   Java source line #66	-> byte code offset #243
/* 8:   */     //   Java source line #37	-> byte code offset #245
/* 9:   */     // Local variable table:
/* 0:   */     //   start	length	slot	name	signature
/* 1:   */     //   0	248	0	this	_BPWAdminHome_Stub
/* 2:   */     //   0	248	1	paramHandle	javax.ejb.Handle
/* 3:   */     //   8	232	2	localObject1	java.lang.Object
/* 4:   */     //   116	5	3	localObject2	java.lang.Object
/* 5:   */     //   230	5	3	localObject3	java.lang.Object
/* 6:   */     //   122	1	4	localObject4	java.lang.Object
/* 7:   */     //   236	1	4	localObject5	java.lang.Object
/* 8:   */     //   16	10	5	localOutputStream	org.omg.CORBA.portable.OutputStream
/* 9:   */     //   35	3	5	localApplicationException	org.omg.CORBA.portable.ApplicationException
/* 0:   */     //   108	3	5	localSystemException	org.omg.CORBA.SystemException
/* 1:   */     //   174	10	5	localHandle	javax.ejb.Handle
/* 2:   */     //   194	3	5	localThrowable	java.lang.Throwable
/* 3:   */     //   50	175	6	localObject6	java.lang.Object
/* 4:   */     //   101	1	13	localRemarshalException	org.omg.CORBA.portable.RemarshalException
/* 5:   */     // Exception table:
/* 6:   */     //   from	to	target	type
/* 7:   */     //   9	35	35	org/omg/CORBA/portable/ApplicationException
/* 8:   */     //   9	35	101	org/omg/CORBA/portable/RemarshalException
/* 9:   */     //   9	108	108	org/omg/CORBA/SystemException
/* 0:   */     //   9	116	116	finally
/* 1:   */     //   163	194	194	java/lang/Throwable
/* 2:   */     //   163	230	230	finally
/* 3:   */   }
/* 4:   */ }


/* Location:           D:\drops\jd\jars\Deployed_BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB._BPWAdminHome_Stub
 * JD-Core Version:    0.7.0.1
 */