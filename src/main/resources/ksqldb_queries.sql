CREATE STREAM users_stream (
    user_id INT,
    name STRING,
    email STRING
) WITH (
    KAFKA_TOPIC = 'mysql-users',
    VALUE_FORMAT = 'JSON',
    PARTITIONS = 1,
    REPLICAS = 1
);

CREATE STREAM orders_stream (
    order_id INT,
    user_id INT,
    order_date DATE,
    order_amount INT
) WITH (
    KAFKA_TOPIC = 'mysql-orders',
    VALUE_FORMAT = 'JSON',
    PARTITIONS = 1,
    REPLICAS = 1
);

CREATE TABLE user_orders_table AS
  SELECT
    u.user_id,
    u.name,
    u.email,
    o.order_id,
    o.order_date,
    o.order_amount
  FROM users_stream u
  LEFT JOIN orders_stream o
  ON u.user_id = o.user_id
  EMIT CHANGES;


CREATE TABLE users_table AS
  SELECT user_id, name, email
  FROM users_stream
  PARTITION BY user_id;


CREATE TABLE orders_table AS
    SELECT order_id, user_id, order_amount, order_date
    FROM orders_stream
    GROUP BY order_id;

CREATE TABLE users_table (
    user_id INT PRIMARY KEY,
    name VARCHAR,
    email VARCHAR
) WITH (
    KAFKA_TOPIC = 'mysql-users',
    VALUE_FORMAT = 'JSON',
    PARTITIONS = 1,
    REPLICAS = 1
);

CREATE TABLE orders_table (
    order_id INT PRIMARY KEY,
    user_id INT,
    order_date DATE,
    order_amount INT
) WITH (
    KAFKA_TOPIC = 'mysql-orders',
    VALUE_FORMAT = 'JSON',
    PARTITIONS = 1,
    REPLICAS = 1
);

CREATE TABLE orders_with_user_info_table AS
    SELECT
        O.order_id,
        O.user_id,
        O.order_amount,
        O.order_date,
        U.name AS userName,
        U.email AS userEmail
    FROM
        orders_table O
    LEFT JOIN
        users_table U
    ON
        O.user_id = U.user_id
    EMIT CHANGES;