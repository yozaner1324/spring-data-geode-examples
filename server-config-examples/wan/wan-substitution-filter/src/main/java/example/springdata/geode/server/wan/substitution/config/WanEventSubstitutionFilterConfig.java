package example.springdata.geode.server.wan.substitution.config;

import example.springdata.geode.server.wan.substitution.filter.WanEventSubstitutionFilter;
import example.springdata.geode.server.wan.substitution.server.siteA.config.SiteAWanEnabledServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WanEnabledServerCommonConfig.class, SiteAWanEnabledServerConfig.class,
        SiteBWanEventSubstitutionFilterServerConfig.class, WanEventSubstitutionFilter.class})
public class WanEventSubstitutionFilterConfig {
}
