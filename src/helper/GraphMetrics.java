package helper;

import edge.Edge;
import edge.SameMovieHyperEdge;
import graph.Graph;
import graph.MovieGraph;
import graph.NetworkTopology;
import javafx.util.Pair;
import vertex.Vertex;

import java.util.*;

/*
Static Method:
degreeCentrality: for a specified point the ans is the sum of in/out degree, and for the graph:
the point shares the largest in and out degree, and the return value is equal to the sum of  the
 value of the largest point minored by each point and normalized before return.

shortestPath(Graph, vertex): as an private method, return Pair
using the algorithm of dijkstra

closenessCentrality: return one count of centrality, caluated by shortestPath

betweennessCentrality: the shortest path between two point v1 and v2, and if this path passes v,
the count added 1, and the counter is divided by the whole number of path.

In/out degree: the size of source/target points set.

Distance(v1, v2): using Floyd algorithm. Return a double value of the distance between v1 and v2.

Eccentricity(v): First introduced in math to describe ellipse. Graph Theory describe the value by
the shortest path between v1 and v2.

Radius: of Graph g: calculated by eccentricity of every graph and divide this value by 2.

Diameter: radius*2.

 */

public class GraphMetrics
{

    public static <L,E> double degreeCentrality(Graph<L,E> g, L v)
    {
        return g.targets(v).size()+g.targets(v).size();
    }

    public static <L,E> double degreeCentrality(Graph<L,E> g)
    {
        if(g.edges().size()==0) return 0;
        Double maxDeg = -1.0;
        for(L v: g.vertices())
        {
            maxDeg = Math.max(maxDeg, g.sources(v).size()+g.targets(v).size());
        }
        Double sum = 0.0;
        for(L v:g.vertices())
        {
            sum+=(maxDeg-(g.sources(v).size()+g.targets(v).size()));
        }
        Integer V = g.vertices().size();
        return sum/(V*V-3*V+2);
    }

    private static <L extends Vertex,E extends Edge> Pair<Double, int[]> shortestPath(Graph<L ,E> g, L v)
    {
        Object[] vert = g.vertices().toArray();
        Map<Object, Integer> reverse = new HashMap<>();
        int i = 0;
        for (Object vertex : vert) {
            reverse.put(vertex, i++);
        }
        double [][] map = new double[vert.length+10][vert.length+10];
        int num = vert.length;
        for(Object vertex: vert)
        {
            int poss = reverse.get(vertex);
            Map<Vertex, List<Double>> tmp = (Map<Vertex, List<Double>>) g.targets((L)vertex);
            for(Vertex vt : tmp.keySet())
            {
                int end = reverse.get(vt);
                List<Double> tmpp = tmp.get(vt);
                double ans=1e12;
                for(Double d:tmpp) if(ans-d>=1e-5) ans= d;
                map[poss][end] = ans;
            }
        }
        for(int j=0;j<vert.length;j++)
            for(int k=0;k<vert.length;k++)
                if(map[j][k]!=0) map[j][k]=1e12;

        boolean []find = new boolean[vert.length+10];
        double []minlist = new double[vert.length+10];
        int []prenode = new int[vert.length+10];

        for(int j=0;j<vert.length;j++)
        {
            find[j] = false;
            minlist[j] = map[reverse.get(v)][j];
            prenode[j]=  j;
        }
        int near = 0;
        for(int j=0;j<vert.length;j++)
        {
            double min = 1e12;
            for(int k=0;k<vert.length;k++)
            {
                if(!find[j] && minlist[j] < min)
                {
                    min = minlist[j];
                    near = j;
                }
            }
            find[near] = true;

            for(int k=0;k<vert.length;k++)
            {
                if(!find[k] && (min+map[near][k])<minlist[k])
                {
                    prenode[k] = near;
                    minlist[k] = min+map[near][k];
                }
            }
        }
        double sum = 0;
        for(int k=0;k<vert.length;k++)
        {
            if(minlist[k]<1e9)
                sum+=minlist[k];
        }
        return new Pair<>(1/(sum/(vert.length-1)), prenode);
    }

    public static <L extends Vertex,E extends Edge> double closenessCentrality(Graph<L ,E> g, L v)
    {
        if(g.edges().size()==0) return 0;
        return shortestPath(g, v).getKey();
    }

    public static <L extends Vertex,E extends Edge> double betweennessCentrality(Graph<L,E> g, L v)
    {
        if(g.edges().size()==0) return 0;
        Object[] vert = g.vertices().toArray();
        Map<Object, Integer> reverse = new HashMap<>();
        int cnt=0;
        for(Object vt: vert) reverse.put(vt, cnt++);
        Set<Edge> edges = (Set<Edge>) g.edges();
        double [][]wei = new double[vert.length+10][vert.length+10];
        boolean Path[][] = new boolean[vert.length+10][vert.length+10];
        int [][] path =  new int[vert.length][vert.length];
        for(int i=0;i<vert.length;i++)
            for(int j=0;j<vert.length;j++) {
                path[i][j]=-1;wei[i][j]=1e12;
                Path[i][j]=false;
            }
        for(Edge e: edges)
        {
            if(!(e instanceof SameMovieHyperEdge))
            {
              for(Vertex vs: e.sourceVertices())
              {
                  for(Vertex vt: e.targetVertices())
                  {
                      wei[reverse.get(vs)][reverse.get(vt)] = e.getWeight()==-1? 1:e.getWeight();
                  }
              }
            }
        }
        for(int k=0;k<vert.length;k++)
        {
            for(int i=0;i<vert.length;i++)
            {
                for(int j=0;j<vert.length;j++)
                {
                    if(wei[i][j]>wei[i][k]+wei[k][j])
                    {
                        wei[i][j]=wei[i][k]+wei[k][j];
                        path[i][j] = k;
                    }
                }
            }
        }


        for(int i=0;i<vert.length;i++)
        {
            for(int j=0;j<vert.length;j++)
            {
                if(i==reverse.get(v)||j==reverse.get(v))
                    continue;
                int pos=j;
                Stack<Integer> st = new Stack<>();
                st.add(j);
                while(pos!=i)
                {
                    if(pos==reverse.get(v))
                    {
                        Path[i][pos] = true;
                        break;
                    }
                    pos = path[i][pos];
                    if(pos==-1) break;
                    if(pos>=0&&Path[i][pos])
                        break;
                    st.add(pos);
                }
                if(pos!=i)
                {
                    while(!st.empty())
                    {
                        Path[i][st.pop()] = true;
                    }
                }
            }
        }
        //count
        int sum=0;
        for(int i=0;i<vert.length;i++)
        {
            for(int j=0;j<vert.length;j++)
            {
                if(Path[i][j]) sum+=1;
            }
        }
        if(g instanceof NetworkTopology || g instanceof MovieGraph)
            return sum*2/((vert.length-1)*(vert.length-2));
        else
            return sum/((vert.length-1)*(vert.length-2));
    }

    public static <L,E> double inDegreeCentrality(Graph<L,E> g, L v)
    {
        if(g.edges().size()==0) return 0;
        return g.sources(v).size();
    }

    public static <L,E> double outDegreeCentrality(Graph<L,E> g, L v)
    {
        if(g.edges().size()==0) return 0;
        return g.targets(v).size();
    }

    public static <L,E> double distance(Graph<L,E> g, L start, L end)
    {
        if(g.edges().size()==0) return 0;
        Object[] vert = g.vertices().toArray();
        Map<Object, Integer> reverse = new HashMap<>();
        int cnt=0;
        for(Object vt: vert) reverse.put(vt, cnt++);
        Set<Edge> edges = (Set<Edge>) g.edges();
        double [][]wei = new double[vert.length+10][vert.length+10];
        boolean Path[][] = new boolean[vert.length+10][vert.length+10];
        int [][] path =  new int[vert.length][vert.length];
        for(int i=0;i<vert.length;i++)
            for(int j=0;j<vert.length;j++) {
                path[i][j]=-1;wei[i][j]=1e12;
                Path[i][j]=false;
            }
        for(Edge e: edges)
        {
            if(!(e instanceof SameMovieHyperEdge))
            {
                for(Vertex vs: e.sourceVertices())
                {
                    for(Vertex vt: e.targetVertices())
                    {
                        wei[reverse.get(vs)][reverse.get(vt)] = e.getWeight()==-1? 1:e.getWeight();
                    }
                }
            }
        }
        for(int k=0;k<vert.length;k++)
        {
            for(int i=0;i<vert.length;i++)
            {
                for(int j=0;j<vert.length;j++)
                {
                    if(wei[i][j]>wei[i][k]+wei[k][j])
                    {
                        wei[i][j]=wei[i][k]+wei[k][j];
                        path[i][j] = k;
                    }
                }
            }
        }
        return wei[reverse.get((Vertex)start)][reverse.get((Vertex)end)];
    }

    public static <L extends Vertex,E extends Edge> double eccentricity(Graph<L,E> g, L v)
    {
        if(g.edges().size()==0) return 0;
        Object[] vert = g.vertices().toArray();
        Map<Object, Integer> reverse = new HashMap<>();
        int cnt=0;
        for(Object vt: vert) reverse.put(vt, cnt++);
        Set<Edge> edges = (Set<Edge>) g.edges();
        double [][]wei = new double[vert.length+10][vert.length+10];
        boolean Path[][] = new boolean[vert.length+10][vert.length+10];
        int [][] path =  new int[vert.length][vert.length];
        for(int i=0;i<vert.length;i++)
            for(int j=0;j<vert.length;j++) {
                path[i][j]=-1;wei[i][j]=1e12;
                Path[i][j]=false;
            }
        for(Edge e: edges)
        {
            if(!(e instanceof SameMovieHyperEdge))
            {
                for(Vertex vs: e.sourceVertices())
                {
                    for(Vertex vt: e.targetVertices())
                    {
                        wei[reverse.get(vs)][reverse.get(vt)] = e.getWeight()==-1? 1:e.getWeight();
                    }
                }
            }
        }
        for(int k=0;k<vert.length;k++)
        {
            for(int i=0;i<vert.length;i++)
            {
                for(int j=0;j<vert.length;j++)
                {
                    if(wei[i][j]>wei[i][k]+wei[k][j])
                    {
                        wei[i][j]=wei[i][k]+wei[k][j];
                        path[i][j] = k;
                    }
                }
            }
        }
        int choicePoint = reverse.get((Vertex)v);
        boolean judge = true;
        for(int j=0;j<vert.length;j++)
            if(wei[choicePoint][j]<1e10)
                judge=false;
        Queue<Integer> que=  new ArrayDeque<>();
        Queue<Integer> edgepoint = new ArrayDeque<>();
        que.add(choicePoint);
        Map<Object, Boolean> vis = new HashMap<>();
        vis.put(choicePoint, true);
        while(!que.isEmpty())
        {
            choicePoint = que.poll();
            for(int j=0;j<vert.length;j++)
            {
                if(wei[choicePoint][j]<1e10&&(!vis.containsKey(j)||!vis.get(j)))
                {
                    que.add(j);
                    vis.put(j, true);
                }
                else
                    edgepoint.add(j);
            }
        }
        double ans=-1;
        while(!edgepoint.isEmpty())
        {
            int poi = edgepoint.poll();
            if(wei[reverse.get(v)][poi]<1e10)
            {
                ans = Math.max(ans, wei[reverse.get(v)][poi]);
            }
        }
        return ans;
    }

    public static <L extends Vertex,E extends Edge> double radius(Graph<L,E> g)
    {
        if(g.edges().size()==0) return 0;
        double ans = -1;
        for(L v: g.vertices())
        {
            ans = Math.max(ans, eccentricity(g, v));
        }
        return ans/2.0;
    }

    public static <L extends Vertex,E extends Edge> double diameter(Graph<L,E> g)
    {
        return radius(g)*2;
    }
}

