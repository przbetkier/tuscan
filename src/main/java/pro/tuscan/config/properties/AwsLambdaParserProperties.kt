package pro.tuscan.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("lambda-parser")
@ConstructorBinding
data class AwsLambdaParserProperties(val endpoint: String)
