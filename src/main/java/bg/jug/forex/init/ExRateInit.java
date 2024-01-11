package bg.jug.forex.init;

import bg.jug.forex.service.ExRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
    value="exrate.init",
    havingValue="true",
    matchIfMissing = true
)
public class ExRateInit implements CommandLineRunner {

  private final ExRateService exRateService;

  public ExRateInit(ExRateService exRateService) {
    this.exRateService = exRateService;
  }

  @Override
  public void run(String... args) throws Exception {

    if (!exRateService.hasInitializedExRates()) {
      var exRates = exRateService.fetchExRates();
      exRateService.updateRates(exRates);
    }


  }
}
