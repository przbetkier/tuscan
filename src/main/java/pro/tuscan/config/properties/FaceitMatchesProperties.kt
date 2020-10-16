package pro.tuscan.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("faceit-matches")
@ConstructorBinding
data class FaceitMatchesProperties(val cutoffDateTimestamp: Number)
