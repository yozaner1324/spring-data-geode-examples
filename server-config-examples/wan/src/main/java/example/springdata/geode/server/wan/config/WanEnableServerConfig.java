package example.springdata.geode.server.wan.config;

import example.springdata.geode.server.wan.server.siteA.config.SiteAWanEnabledServerConfig;
import example.springdata.geode.server.wan.server.siteB.config.SiteBWanEnabledServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WanEnabledServerCommonConfig.class, SiteAWanEnabledServerConfig.class, SiteBWanEnabledServerConfig.class})
public class WanEnableServerConfig {
}
