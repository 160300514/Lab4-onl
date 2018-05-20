package graph;

import helper.ParserInputHelper;

public class GraphPoetFactory
{
    public GraphPoet createGraph(String filePath) throws Exception {
        ParserInputHelper pih = new ParserInputHelper();
        return (GraphPoet)pih.establishGraph(filePath);
    }
}
