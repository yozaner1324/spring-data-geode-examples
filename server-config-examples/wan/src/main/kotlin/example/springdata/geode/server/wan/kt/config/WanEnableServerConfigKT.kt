package example.springdata.geode.server.wan.kt.config

import example.springdata.geode.server.wan.kt.server.siteA.config.SiteAWanEnabledServerConfigKT
import example.springdata.geode.server.wan.kt.server.siteB.config.SiteBWanEnabledServerConfigKT
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(WanEnabledServerCommonConfigKT::class, SiteAWanEnabledServerConfigKT::class, SiteBWanEnabledServerConfigKT::class)
class WanEnableServerConfigKT
