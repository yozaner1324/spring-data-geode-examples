package example.springdata.geode.server.wan.substitution.kt.config


import example.springdata.geode.server.wan.kt.config.WanEnabledServerCommonConfigKT
import example.springdata.geode.server.wan.kt.server.siteA.config.SiteAWanEnabledServerConfigKT
import example.springdata.geode.server.wan.substitution.kt.filter.WanEventSubstitutionFilterKT
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(WanEnabledServerCommonConfigKT::class, SiteAWanEnabledServerConfigKT::class,
    SiteBWanEventSubstitutionFilterServerConfigKT::class, WanEventSubstitutionFilterKT::class)
class WanEventSubstitutionFilterConfigKT
