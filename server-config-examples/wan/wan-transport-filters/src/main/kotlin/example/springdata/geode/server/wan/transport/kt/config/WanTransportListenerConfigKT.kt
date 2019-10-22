package example.springdata.geode.server.wan.transport.kt.config

import example.springdata.geode.server.wan.kt.config.WanEnabledServerCommonConfigKT
import example.springdata.geode.server.wan.kt.server.siteA.config.SiteAWanEnabledServerConfigKT
import example.springdata.geode.server.wan.transport.kt.transport.WanTransportEncryptionListenerKT
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(WanEnabledServerCommonConfigKT::class, SiteAWanEnabledServerConfigKT::class,
        SiteBWanTransportListenerServerConfigKT::class, WanTransportEncryptionListenerKT::class)
class WanTransportListenerConfigKT
