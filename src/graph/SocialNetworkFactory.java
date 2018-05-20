package graph;

import helper.ParserInputHelper;

public class SocialNetworkFactory
{
    public SocialNetwork createGraph(String filePath) throws Exception {
        ParserInputHelper pih = new ParserInputHelper();
        return (SocialNetwork) pih.establishGraph(filePath);
    }
}
