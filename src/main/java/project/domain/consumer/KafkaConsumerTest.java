package project.domain.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaConsumerTest {
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "test",
            groupId = "test",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumer(final String message) throws JsonProcessingException {

        //final var publishEvent = objectMapper.readValue(message, TYPE_REFERENCE);

        System.out.println(message);

    }
}
