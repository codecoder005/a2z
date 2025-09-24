package com.popcorn.a2z.schedules;

import com.google.gson.Gson;
import com.popcorn.a2z.domain.response.IPInfoIOResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ScheduleJobs {
    private final ModelMapper modelMapper;
    private final Gson jsonHelper;

    /**
     * Sends periodic heartbeat signals to IPInfo.io at a fixed interval.
     *
     * <p>This method is automatically executed by Spring's {@link org.springframework.scheduling.annotation.Scheduled}
     * mechanism. It runs at a fixed rate of <b>every 5 minutes</b>, regardless of how long
     * the method execution takes. If the execution time exceeds the interval, executions may overlap.
     *
     * <p>Use this task to:
     * <ul>
     *   <li>Notify IPInfo.io services that the application is alive.</li>
     *   <li>Maintain connectivity or monitoring signals with IPInfo.io.</li>
     *   <li>Integrate with external health-check mechanisms.</li>
     * </ul>
     *
     * <p><b>Configuration details:</b>
     * <ul>
     *   <li>{@code fixedRate = 5} → runs repeatedly at a fixed rate.</li>
     *   <li>{@code timeUnit = TimeUnit.MINUTES} → the rate is expressed in minutes.</li>
     * </ul>
     *
     * <p>Note: Requires {@code @EnableScheduling} annotation on a configuration class
     * to activate scheduled task processing.
     */
    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void sendHeartBeatsIPInfoIOAtFixedRate5Minutes() {
        log.info("ScheduleJobs::sendHeartBeatsIPInfoIOAtFixedRate5Minutes started");
        RestClient restClient = RestClient.builder().baseUrl("https://ipinfo.io").build(); // Base URL for IPInfo.io
        try{
            var ipInfoIOResponse = restClient.get()
                    .accept(MediaType.APPLICATION_JSON)
                    .acceptCharset(StandardCharsets.UTF_8)
                    .retrieve()
                    .body(IPInfoIOResponse.class);
            log.info("ScheduleJobs::sendHeartBeatsIPInfoIOAtFixedRate5Minutes completed with response: {}", jsonHelper.toJson(ipInfoIOResponse));
        }catch (RestClientException exception) {
            log.error("Error occurred while running schedule sendHeartBeatsIPInfoIOAtFixedRate5Minutes");
            exception.printStackTrace();
        }
    }
}
