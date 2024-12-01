1. resources/docker/docker-compose.yml to start kafka cluster, ksqldb server and ksqldb cli services
2. execute commands in resources/kafka_cli_commands.txt to create necessary topics
3. execute queries in resources/ksqldb_queries.sql using resources/ksqldb_server_cli_commands.txt to create new tables in ksqldb
4. Run KafkaPublisher.java to insert sample data into source topics created in step 2.
5. Run KsqlDBClientExample.java to verify we are able to query joined tables from ksqldb server.
