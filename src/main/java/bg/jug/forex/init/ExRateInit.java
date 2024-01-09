package bg.jug.forex.init;

import bg.jug.forex.service.ExRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
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
