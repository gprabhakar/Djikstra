
package djikstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author prabh_000
 */
public class Djikstra {

    /**
     * @param args the command line arguments
     */
    // Djiktra's algorithm
    HashMap<Vertex, Integer> djikstra(Vertex S,ArrayList<Vertex> Unvisited)
    {
       
     HashMap<Vertex, Integer> VertexCost;
     VertexCost = Initialize(Unvisited);              // Unvisited list does not contain Source vertex
       
     while(Unvisited.isEmpty()!=true)                 // While Unvisited Vertex list is not empty
     {
      VertexCost = updateminDistance(S,VertexCost);   // Update minimum cost from source to its adjacent vertices if it is less than the current cost
      
      S = findMinVertex(VertexCost,Unvisited);        // Find the vertex with least cost in the VertexCost map and assign to S as it is now the source
      
      Unvisited.remove(S);                            // Remove S from Unvisited List as it is visited now
     }
      
      // Printing Cost Table
      System.out.println("Cost table");   
      System.out.println("Vertex  Cost   Previous Vertex");
      
      Iterator it = VertexCost.entrySet().iterator();
      while(it.hasNext())
      {
          Map.Entry entry = (Map.Entry) it.next();
          Vertex V = (Vertex) entry.getKey();
          String cost;
          if(entry.getValue().equals(2147483647))
          {
              cost="infinity";
          }
          else
          {
              cost = String.valueOf(entry.getValue());
          }
          if(V.prev == null)
           System.out.println(V.name+"\t"+cost+" "+"Not Visited");
          else
           System.out.println(V.name+"\t"+cost+"\t "+V.prev.name);
        }
      
     return VertexCost;
     }
    
    HashMap<Vertex, Integer> Initialize(ArrayList<Vertex> A)
    {
        HashMap<Vertex, Integer> VC = new HashMap();
        for(Vertex V:A)
        {
          VC.put(V, Integer.MAX_VALUE);
        }
        return VC;
    }
    
    // This function calculates distance from Source to its adjacent vertices and updates the distance if the calculated distance is smaller than existing distance 
    HashMap<Vertex, Integer> updateminDistance(Vertex Source,HashMap<Vertex, Integer> VC)
     {
      
      Iterator it=VC.entrySet().iterator();
      //Iterator it2=V.AdjVertices.entrySet().iterator();
      while(it.hasNext())                                                     //iterating through VC map
      {
        Map.Entry VCVertex = (Map.Entry) it.next();
        
        for(Map.Entry AdjVertex : Source.AdjVertices.entrySet())              //For each Adjacent vertex of current source
        {
            if(AdjVertex.getKey() == VCVertex.getKey())                       // when adjacent vertex is found in VC map
            {
        
              Integer Cost= Source.MinCost + (Integer)AdjVertex.getValue();   //Cost = Min cost to reach S + cost to reach adj vertex from S
              if(Cost<((Integer)VCVertex.getValue()))                         //if new cost < existing cost update new cost for that vertex in the VC map
             {   
                Vertex V =  (Vertex) VCVertex.getKey();
               
                V.MinCost = Cost;
                V.prev = Source;
                VC.put(V, Cost);
               }
            }
        }
      }
      return VC;
   }
    
   // This function returns the vertex from the Unvisited List that has the least cost value
    Vertex findMinVertex(HashMap<Vertex, Integer> VC,ArrayList<Vertex> UV)
    { 
        int cost;
        int j=0;
        //Vertex minVertex = null;
        int minCost = Integer.MAX_VALUE;
        for(int i=0;i<UV.size();i++)
        {
        Vertex key = UV.get(i);
        cost = VC.get(key);
        if(cost < minCost) 
         {
          j = i;
          minCost=cost;
         }
        }
        return UV.get(j); 
    }
    
    // This function displays the Vertex-Cost table and the shortest path to each vertex from the source
    void displayAll(HashMap<Vertex, Integer> VC)
    {   
        int Cost=0;
        for(Vertex V: VC.keySet())
        {
           System.out.println("Vertex: "+V.name);
           Cost = VC.get(V);
           StringBuffer path = new StringBuffer(); 
           path.append(V.name);
           while(V.prev!=null)
           {
             V = V.prev;
             path.append(">-").append(V.name);
           }
           path.reverse();
           System.out.println(path+"\nCost:"+Cost);        
        }
        
    }
    
    // Returns the shortest path for a destination vertex
    StringBuffer Display(Vertex V)
    {
        StringBuffer path = new StringBuffer(); 
           path.append(V.name);
           while(V.prev!=null)
           {
             V = V.prev;
             path.append(">-").append(V.name);
           }
           path.reverse();
        return path;
    }
    
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        //ArrayList<String> Vertices = new ArrayList();
        
        String[] adjEntry;
        boolean x = false;
        HashMap<Vertex, Integer> CostMap;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Enter the number of vertices");
        int size=Integer.valueOf(br.readLine());
        String[] vertices = new String[size];
        Vertex[] Vertices=new Vertex[size];
        

        Vertex Source = new Vertex();
        
        System.out.println("Enter the Vertices");                                           // get all vertices    
        
        
        for(int i=0;i<size;i++)
        {
        vertices[i]=br.readLine();
        
        Vertices[i]=new Vertex(vertices[i]); 
        }
        
        
        for(int i=0;i<Vertices.length;i++)                                                  // get adjacent vertices for each vertex and cost
        {
        System.out.println("Enter the number of adjacent vertices for "+vertices[i]);
        size=Integer.valueOf(br.readLine());
        System.out.println("Enter adjacent vertices for "+vertices[i]+" and cost");                 
        for(int k=0;k<size;k++)
        {
          adjEntry=br.readLine().split(" ");
          for(int j=0;j<Vertices.length;j++)                                                // getting the object of the adjacent vertex
          {
           if(adjEntry[0].equals(Vertices[j].name))
           {
              Vertices[i].loadAdjVertices(Vertices[j],Integer.valueOf(adjEntry[1]));        // adding adjacent vertex to adjVertices Map of the current vertex object
              x = true;
              break;
            }
          }
          if(x==false)
          {
             System.out.println("Vertex not found or entered vertex is the current vertex"); 
          }
         
        }
       }
      
        System.out.println("Enter Source");
        String name=br.readLine();
        int j;
        for(j=0;j<Vertices.length;j++)                                                // getting the object of the Source vertex
          {
           
           if(name.equals(Vertices[j].name))
           {
              Vertices[j].MinCost=0;                                                      // setting Cost of Source to 0
              Source=Vertices[j];
              Source.prev=null;
             }
           
           }
        for(int i=0;i<Vertices.length;i++)
        {
          if(Vertices[i].AdjVertices.containsKey(Source))
          {
            Vertices[i].AdjVertices.remove(Source);                                 //removing source from adjacent vertices map of other vertices
            //System.out.println(" removed ");
          }
        }
        
        Djikstra D= new Djikstra();
        ArrayList<Vertex> Unvisited = new ArrayList();
        for(int i=0;i<Vertices.length;i++) 
        {
          Unvisited.add(Vertices[i]);
        }
        Unvisited.remove(Source);

        
        CostMap=D.djikstra(Source,Unvisited);
        
        System.out.println("Do you want to \n 1.Display shortest path to all vertices \n 2.Display shortest path to a particular vertex\n Type q to quit");
        
        String option = br.readLine();
        while(!option.equals("q"))
        {
        if(option.equals("1"))                                                            // display path for all vertices
        {
        D.displayAll(CostMap);
        }
        else if(option.equals("2"))                                                       // display path for single vertex
        {
        int Cost;
        System.out.println("Enter the destination vertex");    
        name = br.readLine();
        for(int k=0;k<Vertices.length;k++)                                                // getting the object of the destination vertex
          {
            if(name.equals(Source.name))                                                  // if destination is Source
            {
                System.out.println("The Entered vertex is source: "+Source.name+"\nCost: 0");
                break;
            }
            else if(name.equals(Vertices[k].name))
           {
             StringBuffer path = D.Display(Vertices[k]); 
             Cost = CostMap.get(Vertices[k]);
             System.out.println(path+"\n Cost:"+Cost);
             break;
            }
           }
          }
        else
        {
            System.out.println("Wrong Input");
        }
        System.out.println("Do you want to \n 1.Display shortest path to all vertices \n 2.Display shortest path to a particular vertex\n Type q to quit");
        option = br.readLine();
       }
    }      
}
