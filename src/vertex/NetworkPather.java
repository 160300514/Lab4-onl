package vertex;



public abstract class NetworkPather extends Vertex
{
    private int[] ip;
    public NetworkPather(String label, int[] ipAddress)
    {
        super(label);
        assert ipAddress.length == 4;
        this.ip = ipAddress;
    }

    public int[] getIPAddress()
    {
        return this.ip;
    }

    @Override
    void fillVertexInfo(String[] args) throws NoSuchMethodException {
        assert args.length == 4;
        this.ip[0] = Integer.parseInt(args[0]);
        this.ip[1] = Integer.parseInt(args[1]);
        this.ip[2] = Integer.parseInt(args[2]);
        this.ip[3] = Integer.parseInt(args[3]);
        //throw new NoSuchMethodException();
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
