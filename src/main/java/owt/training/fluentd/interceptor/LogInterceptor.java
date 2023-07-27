package owt.training.fluentd.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import owt.training.fluentd.fluentd.FluentdClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private FluentdClient fluentdClient;

    public LogInterceptor(FluentdClient fluentdClient) {
        this.fluentdClient = fluentdClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("requestId", UUID.randomUUID().toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Map<String, String> data = MDC.getCopyOfContextMap();
        if (data != null) {
            Map<String, Object> logData = new HashMap<>();
            logData.putAll(data);
            fluentdClient.send("myapp.request", logData);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
