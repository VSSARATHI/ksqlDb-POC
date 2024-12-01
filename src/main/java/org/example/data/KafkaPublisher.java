package org.example.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.example.models.Order;
import org.example.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaPublisher {
    // Kafka producer configuration
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";  // Update with your Kafka broker address
    private static final String USER_TOPIC = "users-topic";
    private static final String ORDER_TOPIC = "orders-topic";

    public static void main(String[] args) throws Exception {

        // Initialize Kafka Producer
        Properties props = new Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());  // Key serializer
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());  // Value serializer

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Create sample data
        User user1 = new User("user1", "Alice", "alice@example.com");
        User user2 = new User("user2", "Bob", "bob@example.com");

        Order order1 = new Order("order1", "user1", 100.50, "2024-11-30");
        Order order2 = new Order("order2", "user1", 75.00, "2024-11-29");
        Order order3 = new Order("order3", "user2", 150.00, "2024-11-28");

        // Convert objects to JSON and publish to Kafka topics
        ObjectMapper objectMapper = new ObjectMapper();

        // Publish users data
        producer.send(new ProducerRecord<>(USER_TOPIC, user1.getUserId(), objectMapper.writeValueAsString(user1)));
        producer.send(new ProducerRecord<>(USER_TOPIC, user2.getUserId(), objectMapper.writeValueAsString(user2)));


        List<Future<RecordMetadata>> records = new ArrayList<>();
        // Publish orders data
        records.add(producer.send(new ProducerRecord<>(ORDER_TOPIC, order1.getOrderId(), objectMapper.writeValueAsString(order1))));
        records.add(producer.send(new ProducerRecord<>(ORDER_TOPIC, order2.getOrderId(), objectMapper.writeValueAsString(order2))));
        records.add(producer.send(new ProducerRecord<>(ORDER_TOPIC, order3.getOrderId(), objectMapper.writeValueAsString(order3))));

        for (Future<RecordMetadata> record : records) {
            System.out.println("Successfully published record on topic : " + record.get().topic() + " partition-offset: "
                    + record.get().partition() + "-" + record.get().offset());
        }

        // Close the producer
        producer.close();

        System.out.println("Sample data has been published to Kafka topics.");
    }
}
