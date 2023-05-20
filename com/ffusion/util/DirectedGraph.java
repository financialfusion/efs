package com.ffusion.util;

import java.util.HashSet;
import java.util.Iterator;

public class DirectedGraph
  implements Cloneable
{
  private HashSet a;
  
  public DirectedGraph()
  {
    this.a = new HashSet();
  }
  
  public DirectedGraph(GraphVertex paramGraphVertex)
  {
    this();
    addVertex(paramGraphVertex);
  }
  
  public DirectedGraph(HashSet paramHashSet)
  {
    this.a = paramHashSet;
  }
  
  public void addVertex(GraphVertex paramGraphVertex)
  {
    this.a.add(paramGraphVertex);
  }
  
  public void removeVertex(GraphVertex paramGraphVertex)
  {
    this.a.remove(paramGraphVertex);
  }
  
  public GraphVertex getVertex(GraphVertex paramGraphVertex)
  {
    Object localObject = null;
    if (!containsVertex(paramGraphVertex)) {
      return localObject;
    }
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      GraphVertex localGraphVertex = (GraphVertex)localIterator.next();
      if (paramGraphVertex.equals(localGraphVertex))
      {
        localObject = localGraphVertex;
        break;
      }
    }
    return localObject;
  }
  
  public void removeVertexUpdateTree(GraphVertex paramGraphVertex)
  {
    HashSet localHashSet1 = paramGraphVertex.getIncomingVertices();
    HashSet localHashSet2 = paramGraphVertex.getOutgoingVertices();
    Iterator localIterator1 = localHashSet1.iterator();
    Iterator localIterator2 = localHashSet2.iterator();
    GraphVertex localGraphVertex;
    while (localIterator1.hasNext())
    {
      localGraphVertex = (GraphVertex)localIterator1.next();
      localGraphVertex.removeOutgoingVertex(paramGraphVertex);
    }
    while (localIterator2.hasNext())
    {
      localGraphVertex = (GraphVertex)localIterator2.next();
      localGraphVertex.removeIncomingVertex(paramGraphVertex);
    }
    removeVertex(paramGraphVertex);
  }
  
  public int getNumVertices()
  {
    return this.a.size();
  }
  
  public int getSize()
  {
    return getNumVertices();
  }
  
  public int size()
  {
    return getNumVertices();
  }
  
  public boolean containsVertex(GraphVertex paramGraphVertex)
  {
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      GraphVertex localGraphVertex = (GraphVertex)localIterator.next();
      if (localGraphVertex.equals(paramGraphVertex)) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.DirectedGraph
 * JD-Core Version:    0.7.0.1
 */