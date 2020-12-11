import Clavardage.Database.Queries.MessageInsertQuery;
import Clavardage.Database.Queries.QueryParameters;

public class Test {
    public static void main(String[] args) {
        MessageInsertQuery msgInQ = new MessageInsertQuery();
        QueryParameters queryParameters = new QueryParameters();

        queryParameters.append(1);
        queryParameters.append("Toto");
        queryParameters.append("Hello World !");

        msgInQ.prepare();
        msgInQ.setParameters(queryParameters);
        msgInQ.execute();
        msgInQ.close();
    }
}
