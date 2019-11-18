package example.springdata.geode.server.wan.substitution.config;


import example.springdata.geode.server.wan.config.WanEnabledServerCommonConfig;
import example.springdata.geode.server.wan.server.siteA.config.SiteAWanEnabledServerConfig;
import example.springdata.geode.server.wan.substitution.filter.WanEventSubstitutionFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WanEnabledServerCommonConfig.class, SiteAWanEnabledServerConfig.class,
        SiteBWanEventSubstitutionFilterServerConfig.class, WanEventSubstitutionFilter.class})
public class WanEventSubstitutionFilterConfig {
}
