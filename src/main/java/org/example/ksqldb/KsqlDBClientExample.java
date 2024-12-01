package org.example.ksqldb;

import io.confluent.ksql.api.client.*;
import org.example.models.OrderWithUser;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class KsqlDBClientExample {

    public static String KSQLDB_SERVER_HOST = "0.0.0.0";
    public static int KSQLDB_SERVER_HOST_PORT = 8088;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ClientOptions options = ClientOptions.create()
                .setHost(KSQLDB_SERVER_HOST)
                .setPort(KSQLDB_SERVER_HOST_PORT);
        Client client = Client.create(options);

        // Send requests with the client by following the other examples

        String sql = "SELECT * FROM ORDERS_WITH_USER_INFO_TABLE LIMIT 5;";

        BatchedQueryResult batchedQueryResult = client.executeQuery(sql);

        // Wait for query result
        List<Row> resultRows = batchedQueryResult.get();

        System.out.println("Received results. Num rows: " + resultRows.size());
        for (Row row : resultRows) {
            OrderWithUser data = new OrderWithUser(row.getString("ORDERID"),
                    row.getString("O_USERID"), row.getDouble("ORDERAMOUNT"),
                    row.getString("ORDERAMOUNT"), row.getString("USERNAME"), row.getString("USEREMAIL"));
            System.out.println("Row: " + data);
        }

        // Terminate any open connections and close the client
        client.close();
    }
}
