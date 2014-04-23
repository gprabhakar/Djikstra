/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package djikstra;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author prabh_000
 */
public class Vertex {
    String name;
    HashMap<Vertex, Integer> AdjVertices = new HashMap();
    Integer MinCost = Integer.MAX_VALUE;
    Vertex prev = null;
    
    Vertex()
    {}
    
    Vertex(String vertex_name) 
    {
     name= vertex_name;
                  
     }
    
    void loadAdjVertices(Vertex v,int cost)
    {
        AdjVertices.put(v, cost);
    }
    
    Vertex minAdjVertex()
    {
    Vertex minVertex = null;
    int minValue = Integer.MAX_VALUE;
    for(Vertex key : AdjVertices.keySet())
    {
        int value = AdjVertices.get(key);
        if(value < minValue) {
            minValue = value;
            minVertex = key;
        }
    }
    return minVertex;
    }
    void UpdateMinCost(HashMap<Vertex, Integer> AdjVertices)
    {
     Iterator it=AdjVertices.entrySet().iterator();
     while(it.hasNext())
     {
        Map.Entry entry = (Map.Entry) it.next();
        Vertex AdjVertex = (Vertex) entry.getKey();
        Integer Cost= this.MinCost + (Integer)entry.getValue();
        if(Cost<AdjVertex.MinCost)
        {
            AdjVertex.MinCost = Cost;
        }
     }   
    }

}
