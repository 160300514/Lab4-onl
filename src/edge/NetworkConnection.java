package edge;

import exception.RepFieldException;
import helper.HashEncoderHelper;
import vertex.NetworkPather;
import vertex.Vertex;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NetworkConnection extends Edge//undirected
{
    private NetworkPather []list;

    public NetworkConnection(String label, double weight, NetworkPather v1, NetworkPather v2) throws RepFieldException {
        super(label, weight);
        if(weight < -0.0)
            throw new RepFieldException("[E] In Class: NetworkConnection weight cannot be less than 0.");

        list = new NetworkPather[]{v1, v2};
        System.out.println(v1.getClass());
        System.out.println(list[0].getClass());
    }

    public NetworkPather[] getList() {
        return list;
    }

    @Override
    public void checkRep()
    {
        assert list != null && list[0] != null && list[1] != null;
    }

    @Override
    public boolean addVertices(List<Vertex> vertices) {
        throw new NoSuchMethodError();
    }

    @Override
    public boolean containVertex(Vertex v) {
        if(v instanceof NetworkPather)
        {
            NetworkPather p = (NetworkPather)v;
            return this.list[0].equals(p) || this.list[1].equals(p);
        }
        else return false;
    }

    @Override
    public Set<Vertex> vertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.list[0]);
        ans.add(this.list[1]);
        return ans;
    }

    @Override
    public Set<Vertex> sourceVertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.list[0]);
        ans.add(this.list[1]);
        return ans;
    }

    @Override
    public Set<Vertex> targetVertices() {
        Set<Vertex> ans = new HashSet<>();
        ans.add(this.list[0]);
        ans.add(this.list[1]);
        return ans;
    }

    @Override
    public String toString() {
        StringWriter swt = new StringWriter();
        swt.write("Edge: NetworkConnection with vertices:   "+this.list[0]+"\tand:   "+this.list[1]+"\tweight is: "+super.getWeight()+"\tis labeled: "+this.getLabel());
        return swt.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NetworkConnection)
        {
            NetworkConnection nc = (NetworkConnection)obj;
            NetworkPather[] np = nc.getList();
            return np[0].equals(list[0]) && np[1].equals(list[1]) && np[2].equals(list[2]) && np[3].equals(list[3]) && super.getLabel().equals(nc.getLabel());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (new HashEncoderHelper()).hash(this.toString());
    }
}
