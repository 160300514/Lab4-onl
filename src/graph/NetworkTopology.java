package graph;

import edge.Edge;
import edge.NetworkConnection;
import exception.RepFieldException;
import vertex.NetworkPather;

import java.io.StringWriter;
import java.util.*;

public class NetworkTopology extends ConcreteGraph<NetworkPather,NetworkConnection> {
    private String label;
    private Set<NetworkPather> vertices;
    private Map<NetworkPather, Set<NetworkConnection>> map;
    private int state ;//0: open, 1:close


    NetworkTopology(String label)
    {
        this.label = label;
        vertices = new HashSet<>();
        map = new HashMap<>();
        state = 0;
        super.setGraphName(label);
        super.setVertices(vertices);
        super.setMap(map);
    }

    NetworkTopology(String label, Set<NetworkPather> s, Map<NetworkPather, Set<NetworkConnection>> maps, int state)
    {
        this.label = label;
        this.vertices = new HashSet<>(s);
        this.map = new HashMap<>(maps);
        this.state = state;
    }

    public NetworkTopology clone()
    {
        return new NetworkTopology(label, vertices, map, state);
    }

    public void close() throws RepFieldException {
        if(state!=0){
            //System.out.println("[W] System is already closed.");
            throw new RepFieldException("[W] System is already closed.");
        }
        else{
            state=1;
            System.out.println("[I] Closed Successfully");
        }
    }

    public void open()
    {
        if(state==0) System.out.println("[W] System is already opened.");
        else{
            state=0;
            System.out.println("[I] Open Successfully");
        }
    }


    public String getLabel() {
        return label;
    }

    @Override
    public boolean addVertex(NetworkPather v) {
        if(state == 1)
        {
            System.out.println("[E] Now the System is closed.");
        }
        return this.vertices.add(v);
    }

    @Override
    public boolean removeVertex(NetworkPather v) {
        if(state == 1)
        {
            System.out.println("[E] Now the System is closed.");
        }
        if(!this.vertices.contains(v)) return false;
        this.map.remove(v);
        for(NetworkPather np : this.map.keySet())
        {
            Iterator it = this.map.get(np).iterator();
            while(it.hasNext())
            {
                Edge e = (Edge) it.next();
                if(e.containVertex(v))
                    it.remove();
            }
        }
        return true;
    }

    @Override
    public Set<NetworkPather> vertices() {
        return this.vertices;
    }

    @Override
    public Map<NetworkPather, List<Double>> sources(NetworkPather target) {
        if(state == 1)
        {
            System.out.println("[E] Now the System is closed.");
        }
        Map<NetworkPather, List<Double>> ans=  new HashMap<>();
        for(NetworkPather np: this.map.keySet())
        {
            for(NetworkConnection nc: this.map.get(np))
            {
                NetworkPather npt = (NetworkPather)nc.targetVertices().toArray()[0];
                NetworkPather nps = (NetworkPather)nc.sourceVertices().toArray()[0];
                if(npt.equals(target))
                {
                    if(ans.containsKey(nps))
                    {
                        ans.get(nps).add(nc.getWeight());
                    }
                    else
                    {
                        List<Double> tmp = new ArrayList<>();
                        tmp.add(nc.getWeight());
                        ans.put(nps, tmp);
                    }
                }
            }
        }
        return ans;
    }

    @Override
    public Map<NetworkPather, List<Double>> targets(NetworkPather source)
    {
        if(state == 1)
        {
            System.out.println("[E] Now the System is closed.");
        }
        Map<NetworkPather, List<Double>> ans = new HashMap<>();
        if(this.map.containsKey(source))
        {
            for(NetworkConnection nc: this.map.get(source))
            {
                NetworkPather npt = (NetworkPather)nc.targetVertices().toArray()[0];
                if(ans.containsKey(npt))
                    ans.get(npt).add(nc.getWeight());
                else
                {
                    List<Double> tmp = new ArrayList<>();
                    tmp.add(nc.getWeight());
                    ans.put(npt, tmp);
                }
                //ans.put(npt, nc.getWeight());
            }return ans;
        }
        else return null;
    }

    @Override
    public boolean addEdge(NetworkConnection edge, Boolean filein) {
        if(state == 1)
        {
            System.out.println("[E] Now the System is closed.");
        }
        if(edge==null) return false;
        NetworkPather nps = (NetworkPather)edge.sourceVertices().toArray()[0];
        NetworkPather npt = (NetworkPather)edge.targetVertices().toArray()[0];
        boolean added = false;
        if(this.map.containsKey(nps))
        {
            if(!this.map.get(nps).contains(edge))
            {
                this.map.get(nps).add(edge);
                added = true;
            }
        }
        else
        {
            Set<NetworkConnection> tmp = new HashSet<>();
            tmp.add(edge);
            this.map.put(nps, tmp);
        }
        if(this.map.containsKey(npt))
        {
            if(!this.map.get(npt).contains(edge))
            {
                this.map.get(npt).add(edge);
                added = true;
            }
        }
        else
        {
            Set<NetworkConnection> tmp = new HashSet<>();
            tmp.add(edge);
            this.map.put(npt, tmp);
        }
        if(!added) return false;
        return true;
    }

    @Override
    public boolean removeEdge(NetworkConnection edge) {
        if(state == 1)
        {
            System.out.println("[E] Now the System is closed.");
        }
        if(edge==null) return false;
        boolean removed = false;
        for(NetworkPather np:this.map.keySet())
        {
            Iterator it = this.map.get(np).iterator();
            while(it.hasNext())
            {
                Edge e = (Edge) it.next();
                if(e.equals(edge))
                {
                    removed = true;
                    it.remove();
                }
            }
            /*
            for(NetworkConnection nc: this.map.get(np))
            {
                if(nc.equals(edge))
                {
                    this.map.get(np).remove(edge);
                    return true;
                }
            }*/
        }
        return removed;
    }

    @Override
    public Set<NetworkConnection> edges() {
        Set<NetworkConnection> ans = new HashSet<>();
        this.map.keySet().forEach(networkPather -> ans.addAll(this.map.get(networkPather)));
        return ans;
    }

    @Override
    public String toString()
    {
        StringWriter swt = new StringWriter();
        swt.write("Graph: NetworkTopology:"+(state==0? "Open": "Close")+" \n\twith vertices:\n");
        this.vertices.forEach(networkPather -> swt.write("\t \tVertex: "+networkPather.toString()+"\n"));
        swt.write("\tEdges:\n");
        this.edges().forEach(networkConnection -> swt.write("\t\tEdge: "+networkConnection.toString()+"\n"));
        return swt.toString();
    }
}
