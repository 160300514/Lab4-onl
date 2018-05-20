package graph;

import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import exception.RepFieldException;
import vertex.Person;

import java.io.StringWriter;
import java.util.*;

public class SocialNetwork extends ConcreteGraph<Person,Edge> // weight of each edges: int:frequency, sum value is hold onto the graph
{

    private String label;
    private Set<Person> vertices;
    private Map<Person, Set<Edge>> map;
    private int state;//state = 0:deactive     1:active     2: locked

    public String getLabel()
    {
        return this.label;
    }

    public void checkRep() throws RepFieldException {
        Double cnt = 0.0;
        for(Person p:map.keySet())
        {
            for(Edge e:map.get(p))
            {
                cnt+=e.getWeight();
            }
        }
        if(cnt-1.0>1e-5 || cnt-1.0<-1e-5)
        {
            throw new RepFieldException("SocialNetwork: Sum of Weight not equals to 1.0");
        }
    }

    public void lock()
    {
        if(state == 2)
        {
            System.out.println("System has already locked.");
        }
        else
        {
            state = 2;
            System.out.println("System become locked.");
        }
    }

    public void active()
    {
        if(state == 1) System.out.println("System is active now.");
        else
        {
            state = 1;
            System.out.println("System become active.");
        }
    }

    public void deactive()
    {
        if(state == 2) System.out.println("System is locked now, cannot be deactive.(use unlock() to unlock)");
        else if(state == 1)
        {
            state = 0;
            System.out.println("System is now be deactive.");
        }
        else
            System.out.println("System has already be deactive");
    }

    public void unlock()
    {
        if(state==2)
        {
            state = 1;
            System.out.println("System is unlocked.");
        }
        else System.out.println("Error state.");
    }

    SocialNetwork(String label)
    {
        this.label = label;
        vertices = new HashSet<>();
        map = new HashMap<>();
        state = 1;
    }

    SocialNetwork(String label, Set<Person> s, Map<Person, Set<Edge>> maps, int state)
    {
        this.label = label;
        this.vertices = new HashSet<>(s);
        this.map = new HashMap<>(maps);
        this.state = state;
        super.setGraphName(label);
        super.setVertices(vertices);
        super.setMap(map);
    }

    public SocialNetwork clone()
    {
        return new SocialNetwork(label, vertices, map, state);
    }

    @Override
    public boolean addVertex(Person v)
    {
        if(state!=1)
        {
            System.out.println("System not active.");
            return false;
        }
        return this.vertices.add(v);
    }

    @Override
    public synchronized boolean removeVertex(Person v) {
        if(state!=1)
        {
            System.out.println("System not active.");
            return false;
        }
        if(v==null) return false;
        this.vertices.remove(v);
        //this.map.remove(v);
        Set<Edge> ed = new HashSet<>();
        for(Person p: this.map.keySet())
        {
            Iterator it = this.map.get(p).iterator();
            while(it.hasNext())
            {
                Edge e = (Edge) it.next();
                if(e.containVertex(v))
                    ed.add(e);
            }
            /*
            for(Edge e: this.map.get(p))
            {
                if(e.containVertex(v))
                {
                    //System.out.println(e.toString());
                    this.map.get(p).remove(e);e.weightConfig(e.getWeight()/(1-edge.getWeight()));
                }
            }*/
        }
        ed.forEach(edge -> removeedgeuseString(edge.getLabel()));
        return true;
    }

    @Override
    public Set<Person> vertices() {
        return this.vertices;
    }

    @Override
    public Map<Person, List<Double>> sources(Person target) {
        if(state == 2)
        {
            System.out.println("System locked, please unlock it.");
            return null;
        }
        Map<Person, List<Double>> ans = new HashMap<>();
        for(Person p: this.map.keySet())
        {
            for(Edge e: this.map.get(p))
            {
                if(e.targetVertices().contains(target))
                {
                    if(ans.containsKey(p))
                        ans.get(target).add(e.getWeight());
                    else
                    {
                        List<Double> tmp = new ArrayList<>();
                        tmp.add(e.getWeight());
                        ans.put(p, tmp);
                    }
                }
            }
        }
        return ans;
    }

    @Override
    public Map<Person, List<Double>> targets(Person source) {
        if(state == 2)
        {
            System.out.println("System locked, please unlock it.");
            return null;
        }
        Map<Person, List<Double>> ans = new HashMap<>();
        if(!this.map.containsKey(source)) return null;
        //this.map.get(source).forEach(edge -> (ans.containsKey((Person)edge.targetVertices().toArray()[0]) ? ans.replace((Person)edge.targetVertices().toArray()[0], ans.get((Person)edge.targetVertices().toArray()[0])+edge.getWeight()): ans.put((Person)edge.targetVertices().toArray()[0], edge.getWeight())));
        for(Edge e: this.map.get(source))
        {
            Person target = (Person)e.targetVertices().toArray()[0];
            if(ans.containsKey(target))
            {
                ans.get(target).add(e.getWeight());
            }
            else
            {
                List<Double> tmp = new ArrayList<>();
                tmp.add(e.getWeight());
                ans.put(target, tmp);
            }
        }
        return ans;
    }

    @Override
    public boolean addEdge(Edge edge, Boolean filein) {
        if(state!=1)
        {
            System.out.println("System not active.");
            return false;
        }
        if(edge==null) return false;
        if(edge instanceof CommentTie || edge instanceof ForwardTie || edge instanceof FriendTie)
        {
            //System.out.println("Addedge: "+edge.toString());
            Person src = (Person)edge.sourceVertices().toArray()[0];
            Person tar = (Person)edge.targetVertices().toArray()[0];
            if(filein)
            {
                this.vertices.add(src);
                this.vertices.add(tar);
                if(this.map.containsKey(src))
                {
                    this.map.get(src).add(edge);
                }
                else
                {
                    Set<Edge> tmp =new HashSet<>();
                    tmp.add(edge);
                    this.map.put(src, tmp);
                }
                return true;
            }
            Double consis = 1-edge.getWeight();
            boolean added = false;
            for(Person p:  this.map.keySet())
            {
                if(this.map.containsKey(src))
                {
                    for(Edge e: this.map.get(p))
                    {
                        if(e.equals(edge))
                        {
                            added = true;
                            e.weightConfig(e.getWeight()*consis+edge.getWeight());
                        }
                        else
                            e.weightConfig(e.getWeight()*consis);
                    }
                }
                else
                {
                    for(Edge e: this.map.get(p))
                    {
                        e.weightConfig(e.getWeight()*consis);
                    }
                }
            }
            if(this.map.containsKey(src))
                this.map.get(src).add(edge);
            else {
                Set<Edge> tmp = new HashSet<>();
                tmp.add(edge);
                this.map.put(src, tmp);
            }
        }
        return true;
    }

    private synchronized boolean removeedgeuseString(String label)
    {
        Edge ee = null;
        for(Edge e: edges())
            if(e.getLabel().equals(label))
                ee = e;
        removeEdge(ee);
        return ee!=null;
    }

    @Override
    public synchronized boolean removeEdge(Edge edge) {
        if(state!=1)
        {
            System.out.println("System not active.");
            return false;
        }
        if(edge==null) return false;
        boolean removed = false;
        Double tmp = edge.getWeight();
        for(Person p:this.map.keySet())
        {
            Iterator it = this.map.get(p).iterator();
            while(it.hasNext())
            {
                Edge e = (Edge) it.next();
                if(e.equals(edge))
                {
                    it.remove();
                    removed = true;
                    break;
                }
            }
            /*
            for(Edge e: this.map.get(p))
            {
                if(e.equals(edge))
                {
                    this.map.get(p).remove(edge);
                    return true;
                }
                else
                {
                    e.weightConfig(e.getWeight()/(1-edge.getWeight()));
                }
            }*/
        }
        if(removed)
        {
            for(Person p:this.map.keySet())
            {
                for(Edge e:this.map.get(p))
                {
                    e.weightConfig(e.getWeight()/(1-tmp));
                }
            }
        }
        //System.out.println(toString());
        return removed;
    }

    @Override
    public Set<Edge> edges() {
        Set<Edge> ans = new HashSet<>();
        this.map.keySet().forEach(person -> ans.addAll(this.map.get(person)));
        return ans;
    }

    @Override
    public String toString()
    {
        StringWriter swt = new StringWriter();
        swt.write("Graph: Social Graph:"+(state == 0? "deactive":(state==1? "active": "locked"))+"   \n\twoth vertices:\n");
        vertices.forEach(person -> swt.write("\t"+person.toString()+"\n"));
        swt.write("Edges:\n");
        edges().forEach(edge -> swt.write("\t"+edge.toString()+"\n"));
        return swt.toString();
    }
}
