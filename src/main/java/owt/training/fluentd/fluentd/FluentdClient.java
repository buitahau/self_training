package owt.training.fluentd.fluentd;

import org.fluentd.logger.FluentLogger;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FluentdClient {

    private static final FluentLogger logger = FluentLogger.getLogger("debug", "localhost", 24224);

    public void send(String tag, Map<String, Object> data) {
        logger.log(tag, data);
    }
}
