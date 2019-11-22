package example.springdata.geode.server.wan.event.config;

import example.springdata.geode.server.wan.event.filters.EvenNumberedKeyWanEventFilter;
import example.springdata.geode.server.wan.event.server.siteA.config.SiteAWanEnabledServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WanEnabledServerCommonConfig.class, SiteAWanEnabledServerConfig.class,
        SiteBWanEventFilteringServerConfig.class, EvenNumberedKeyWanEventFilter.class})
public class WanEventFiltersConfig {
}
