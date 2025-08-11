package tw.fengqing.spring.springbucks.customer.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

import java.util.Arrays;

public class CustomConnectionKeepAliveStrategy implements org.apache.hc.client5.http.ConnectionKeepAliveStrategy {
    private final long DEFAULT_SECONDS = 30;

    @Override
    public TimeValue getKeepAliveDuration(HttpResponse response, HttpContext context) {
        return Arrays.stream(response.getHeaders("Keep-Alive"))
                .filter(h -> StringUtils.equalsIgnoreCase(h.getName(), "timeout")
                        && StringUtils.isNumeric(h.getValue()))
                .findFirst()
                .map(h -> TimeValue.ofSeconds(NumberUtils.toLong(h.getValue(), DEFAULT_SECONDS)))
                .orElse(TimeValue.ofSeconds(DEFAULT_SECONDS));
    }
}
