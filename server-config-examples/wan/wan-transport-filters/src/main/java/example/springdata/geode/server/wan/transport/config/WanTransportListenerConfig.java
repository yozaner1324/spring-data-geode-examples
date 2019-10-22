package example.springdata.geode.server.wan.transport.config;

import example.springdata.geode.server.wan.transport.transport.WanTransportEncryptionListener;
import example.springdata.geode.server.wan.config.WanEnabledServerCommonConfig;
import example.springdata.geode.server.wan.server.siteA.config.SiteAWanEnabledServerConfig;
import example.springdata.geode.server.wan.transport.transport.WanTransportEncryptionListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WanEnabledServerCommonConfig.class, SiteAWanEnabledServerConfig.class,
        SiteBWanTransportListenerServerConfig.class, WanTransportEncryptionListener.class})
public class WanTransportListenerConfig {
}
