package org.example.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.models.Order;
import org.example.models.User;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerExample {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";  // Kafka broker address
    private static final String USER_TOPIC = "mysql-users";  // Kafka topic for user data
    private static final String ORDER_TOPIC = "orders-topic";  // Kafka topic for order data
    private static final String GROUP_ID = "consumer-group-3";  // Consumer group ID

    public static void main(String[] args) {
        // Set up Kafka Consumer properties
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");  // Read from the earliest offset if no offset is committed

        // Create KafkaConsumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to both topics

        // Set up Jackson ObjectMapper for JSON processing

        try (consumer) {
            consumer.subscribe(Arrays.asList(USER_TOPIC));
            System.out.println(consumer.assignment());
            System.out.println(consumer.endOffsets(consumer.assignment()));
            ObjectMapper objectMapper = new ObjectMapper();
            while (true) {
                // Poll the consumer for messages
                consumer.poll(1000).forEach(record -> {
                    String topic = record.topic();
                    String value = record.value();

                    try {
                        // Process messages based on the topic
                        if (USER_TOPIC.equals(topic)) {
                            // Deserialize the user JSON to User object
                            User user = objectMapper.readValue(value, User.class);
                            System.out.println("Received User: " + user.toString());
                        } else if (ORDER_TOPIC.equals(topic)) {
                            // Deserialize the order JSON to Order object
                            Order order = objectMapper.readValue(value, Order.class);
                            System.out.println("Received Order: " + order.toString());
                        }
                    } catch (Exception e) {
                        System.err.println("Error deserializing message: " + e.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            System.err.println("Error while consuming messages: " + e.getMessage());
        }
        // Close the consumer gracefully when exiting
        System.exit(0);
    }
}
