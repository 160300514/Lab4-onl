package graph;

import helper.ParserInputHelper;

public class MovieGraphFactory
{
    public MovieGraph createGraph(String filePath) throws Exception {
        ParserInputHelper pih = new ParserInputHelper();
        return (MovieGraph) pih.establishGraph(filePath);
    }
}
