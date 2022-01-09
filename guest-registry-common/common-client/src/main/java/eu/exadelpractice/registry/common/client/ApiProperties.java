package eu.exadelpractice.registry.common.client;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Data
@Log4j2
@Component
@ConfigurationProperties(prefix = "guest.registry")
public class ApiProperties {
    private String apiUserName;
    private String apiPassword;
    private String apiHost;

    @PostConstruct
    void init() {
        log.info("Our client will use form communication with api with this configurations: {}", this);
    }
}
