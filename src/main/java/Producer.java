import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.Scanner;

/**
 * @author Vinayak Chaturvedi
 */

public class Producer {
    private static final Logger logger = LogManager.getLogger(Producer.class);

    public static void main(String[] args) {
        String topicName = "test";


        logger.info("Starting HelloProducer...");
        logger.debug("topicName=" + topicName);
        logger.trace("Creating Kafka Producer...");
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "HelloProducer");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);
        logger.trace("Start sending messages...");
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Print 1 for message and 2 for exit: ");
            int input = Integer.parseInt(sc.nextLine());
            while (input != 2) {
                System.out.print("Message: ");
                String message = sc.nextLine();
                producer.send(new ProducerRecord<>(topicName, message));
                System.out.print("Print 1 for message and 2 for exit: ");
                input = Integer.parseInt(sc.nextLine());
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