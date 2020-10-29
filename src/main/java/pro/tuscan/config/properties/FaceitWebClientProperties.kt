package pro.tuscan.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Validated
@ConfigurationProperties(prefix = "faceit")
@ConstructorBinding
data class FaceitWebClientProperties(
        @NotEmpty val url: String,
        @NotEmpty val openUrl: String,
        @NotNull val timeout: Timeout,
        @NotEmpty val apiKey: String,
        @NotNull val retry: Retry
)

data class Retry(
        @Min(1) val backoff: Long,
        @Min(1) val maxRetries: Long
)

data class Timeout(
        @Min(1) val read: Long,
        @Min(1) val connect: Long
)

