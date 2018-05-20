package graph;

import helper.ParserInputHelper;

public class NetworkTopologyFactory
{
    public NetworkTopology createGraph(String filePath) throws Exception {
        ParserInputHelper pih = new ParserInputHelper();
        return (NetworkTopology) pih.establishGraph(filePath);
    }
}
