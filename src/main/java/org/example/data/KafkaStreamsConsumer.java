package org.example.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.example.models.Order;
import org.example.models.User;

import java.util.Properties;

public class KafkaStreamsConsumer {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";  // Kafka broker address
    private static final String USER_TOPIC = "users-topic";  // Kafka topic for user data
    private static final String ORDER_TOPIC = "orders-topic";  // Kafka topic for order data
    private static final String APPLICATION_ID = "streams-app";  // Kafka Streams application ID

    public static void main(String[] args) throws InterruptedException {
        // Set up Kafka Streams properties
        Properties props = new Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Create Kafka Streams builder
        StreamsBuilder builder = new StreamsBuilder();

        // Set up Jackson ObjectMapper for JSON processing
        ObjectMapper objectMapper = new ObjectMapper();

        // 1. Define a stream for the `users_topic`
        KStream<String, String> userStream = builder.stream(USER_TOPIC);

        // Deserialize the JSON user data and print
        userStream.peek((key, value) -> {
            System.out.println("Raw User Data: " + value);  // Log raw data
            try {
                User user = objectMapper.readValue(value, User.class);
                System.out.println("User Data: " + user);
            } catch (Exception e) {
                System.err.println("Error processing user data: " + e.getMessage());
            }
        });

        // 2. Define a stream for the `orders_topic`
        KStream<String, String> orderStream = builder.stream(ORDER_TOPIC);

        // Deserialize the JSON order data and print
        orderStream.peek((key, value) -> {
            System.out.println("Raw Order Data: " + value);  // Log raw data
            try {
                Order order = objectMapper.readValue(value, Order.class);
                System.out.println("Order Data: " + order);
            } catch (Exception e) {
                System.err.println("Error processing order data: " + e.getMessage());
            }
        });

        // Build the Kafka Streams application
        try (KafkaStreams streams = new KafkaStreams(builder.build(), props)) {
            // Start the Kafka Streams application
            streams.start();

            // Add shutdown hook to gracefully close the stream
            Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        }

        // Sleep to keep the application running for testing purposes (adjust as needed)
        Thread.sleep(10_000);
    }
}
