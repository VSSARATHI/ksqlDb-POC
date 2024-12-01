CREATE TABLE users_table (
    userId STRING PRIMARY KEY,
    name STRING,
    email STRING
) WITH (
    KAFKA_TOPIC = 'users-topic',
    VALUE_FORMAT = 'JSON',
    PARTITIONS = 1,
    REPLICAS = 1
);

CREATE TABLE orders_table (
    orderId STRING PRIMARY KEY,
    userId STRING,
    orderAmount DOUBLE,
    orderDate STRING
) WITH (
    KAFKA_TOPIC = 'orders-topic',
    VALUE_FORMAT = 'JSON',
    PARTITIONS = 1,
    REPLICAS = 1
);



CREATE TABLE orders_with_user_info_table AS
SELECT
    O.orderId,
    O.userId,
    O.orderAmount,
    O.orderDate,
    U.name AS userName,
    U.email AS userEmail
FROM
    orders_table O
LEFT JOIN
    users_table U
ON
    O.userId = U.userId
EMIT CHANGES;