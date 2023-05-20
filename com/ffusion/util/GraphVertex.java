package com.ffusion.util;

import java.util.HashSet;
import java.util.Iterator;

public class GraphVertex
{
  HashSet a = new HashSet();
  HashSet jdField_if = new HashSet();
  Object jdField_do = null;
  
  public GraphVertex() {}
  
  public GraphVertex(Object paramObject)
  {
    this();
    this.jdField_do = paramObject;
  }
  
  public GraphVertex(Object paramObject, GraphVertex paramGraphVertex)
  {
    this(paramObject);
    this.a.add(paramGraphVertex);
  }
  
  public void setKey(Object paramObject)
  {
    this.jdField_do = paramObject;
  }
  
  public void addOutgoingVertex(GraphVertex paramGraphVertex)
  {
    this.jdField_if.add(paramGraphVertex);
  }
  
  public void addIncomingVertex(GraphVertex paramGraphVertex)
  {
    this.a.add(paramGraphVertex);
  }
  
  public boolean removeOutgoingVertex(GraphVertex paramGraphVertex)
  {
    return this.jdField_if.remove(paramGraphVertex);
  }
  
  public boolean removeIncomingVertex(GraphVertex paramGraphVertex)
  {
    return this.a.remove(paramGraphVertex);
  }
  
  public Object getKey()
  {
    return this.jdField_do;
  }
  
  public boolean isOutgoingVertex(GraphVertex paramGraphVertex)
  {
    return this.jdField_if.contains(paramGraphVertex);
  }
  
  public boolean isIncomingVertex(GraphVertex paramGraphVertex)
  {
    return this.a.contains(paramGraphVertex);
  }
  
  public HashSet getOutgoingVertices()
  {
    return this.jdField_if;
  }
  
  public HashSet getIncomingVertices()
  {
    return this.a;
  }
  
  private GraphVertex a(HashSet paramHashSet)
  {
    Iterator localIterator = paramHashSet.iterator();
    GraphVertex localGraphVertex = null;
    if (localIterator.hasNext()) {
      localGraphVertex = (GraphVertex)localIterator.next();
    }
    return localGraphVertex;
  }
  
  public GraphVertex getNextOutgoingVertex()
  {
    return a(this.jdField_if);
  }
  
  public GraphVertex getNextIncomingVertex()
  {
    return a(this.a);
  }
  
  public boolean isLeaf()
  {
    boolean bool = this.jdField_if.size() == 0;
    return bool;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((!(paramObject instanceof GraphVertex)) || (paramObject == null)) {
      return false;
    }
    return this.jdField_do.equals(((GraphVertex)paramObject).getKey());
  }
  
  public int hashCode()
  {
    return this.jdField_do.hashCode();
  }
  
  public int numOutgoingVertices()
  {
    return this.jdField_if.size();
  }
  
  public int numIncomingVertices()
  {
    return this.a.size();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.GraphVertex
 * JD-Core Version:    0.7.0.1
 */