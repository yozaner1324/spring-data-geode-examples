package example.springdata.geode.server.wan.transport.transport;

import org.apache.geode.cache.wan.GatewayTransportFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;

@Component
public class WanTransportEncryptionListener implements GatewayTransportFilter {

    private final Adler32 CHECKER = new Adler32();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public InputStream getInputStream(InputStream stream) {
        logger.info("CheckedTransportFilter: Getting input stream");
        return new CheckedInputStream(stream, CHECKER);
    }

    @Override
    public OutputStream getOutputStream(OutputStream stream) {
        logger.info("CheckedTransportFilter: Getting output stream");
        return new CheckedOutputStream(stream, CHECKER);
    }
}