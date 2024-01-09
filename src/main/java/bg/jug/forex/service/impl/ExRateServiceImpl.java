package bg.jug.forex.service.impl;

import bg.jug.forex.config.ForexApiConfiguration;
import bg.jug.forex.model.dto.ExRatesResponseDTO;
import bg.jug.forex.model.entity.ExRateEntity;
import bg.jug.forex.repository.ExRateRepository;
import bg.jug.forex.service.ExRateService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ExRateServiceImpl implements ExRateService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExRateServiceImpl.class);

  private static final String BASE_CURRENCY = "USD";

  private final ForexApiConfiguration forexApiConfiguration;
  private final ExRateRepository exRateRepository;

  public ExRateServiceImpl(ForexApiConfiguration forexApiConfiguration,
      ExRateRepository exRateRepository) {
    this.forexApiConfiguration = forexApiConfiguration;
    this.exRateRepository = exRateRepository;
  }

  @Override
  public boolean hasInitializedExRates() {
    return exRateRepository.count() > 0;
  }

  @Override
  public ExRatesResponseDTO fetchExRates() {

    RestClient restClient = RestClient.create();

    return restClient.
        get().
        uri(forexApiConfiguration.getUrl(), forexApiConfiguration.getKey()).
        accept(MediaType.APPLICATION_JSON).
        retrieve().
        body(ExRatesResponseDTO.class);
  }

  @Override
  public void updateRates(ExRatesResponseDTO exRates) {
    LOGGER.info("Processing {} exchange rates.", exRates.rates().size());

    if (!Objects.equals(BASE_CURRENCY, exRates.base())) {
      throw new IllegalArgumentException("The base currency should be: " +
          BASE_CURRENCY);
    }

    exRates.rates().forEach((currency, rate) -> {
      var exRateEntity = exRateRepository.
          findByCurrency(currency).
          orElse(new ExRateEntity().setCurrency(currency));
      exRateEntity.setRate(rate);

      exRateRepository.save(exRateEntity);
    });
  }

  @Override
  public Optional<BigDecimal> findExRate(String from, String to) {

    if (Objects.equals(from, to)){
      return  Optional.of(BigDecimal.ONE);
    }

    // USD/BGN = x
    // USD/EUR = y
    // => EUR/BGN

    Optional<BigDecimal> fromOpt = BASE_CURRENCY.equals(from) ?
        Optional.of(BigDecimal.ONE) :
        exRateRepository.findByCurrency(from).map(ExRateEntity::getRate);

    Optional<BigDecimal> toOpt = BASE_CURRENCY.equals(to) ?
        Optional.of(BigDecimal.ONE) :
        exRateRepository.findByCurrency(to).map(ExRateEntity::getRate);

    if (fromOpt.isEmpty() || toOpt.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(toOpt.get().divide(fromOpt.get(), 2, RoundingMode.HALF_DOWN));
    }
  }

  @Override
  public BigDecimal convert(String from, String to, String amount) {
    return null;
  }
}
