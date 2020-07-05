import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @author Vinayak Chaturvedi
 */

public class Producer {
    private static final Logger logger = LogManager.getLogger(Producer.class);

    public static void main(String[] args) {
        String topicName;
        int numEvents;

        if (args.length != 2) {
            System.out.println("Please provide command line arguments: topicName numEvents");
            System.exit(-1);
        }
        topicName = args[0];
        numEvents = Integer.parseInt(args[1]);
        logger.info("Starting HelloProducer...");
        logger.debug("topicName=" + topicName + ", numEvents=" + numEvents);
        logger.trace("Creating Kafka Producer...");
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "HelloProducer");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);
        logger.trace("Start sending messages...");
        try {
            for (int i = 1; i <= numEvents; i++) {
                String value = "Simple Message- " + LocalDateTime.now().toString() + " ";
                producer.send(new ProducerRecord<>(topicName, i, value + i));
            }
        } catch (KafkaException e) {
            logger.error("Exception occurred – Check log for more details.\n" + e.getMessage());
            System.exit(-1);
        } finally {
            logger.info("Finished HelloProducer – Closing Kafka Producer.");
            producer.close();
        }
    }
}