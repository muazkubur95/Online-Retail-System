package miu.edu.onlineRetailSystem.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Logger implements ILogger {

    @Override
    public void info(String info) {
        log.info("{}", info);
    }

    @Override
    public void warning(String warn) {
        log.warn("{}", warn);
    }

    @Override
    public void debug(String debug) {
        log.debug("{}", debug);
    }

    @Override
    public void error(String error) {
        log.error("{}", error);
    }

}
