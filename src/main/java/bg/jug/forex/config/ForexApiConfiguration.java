package bg.jug.forex.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "forex.api")
public class ForexApiConfiguration {

  private String key;
  private String url;

  public String getKey() {
    return key;
  }

  public ForexApiConfiguration setKey(String key) {
    this.key = key;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public ForexApiConfiguration setUrl(String url) {
    this.url = url;
    return this;
  }
}
