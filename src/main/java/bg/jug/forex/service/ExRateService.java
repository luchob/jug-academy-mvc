package bg.jug.forex.service;

import bg.jug.forex.model.dto.ExRatesResponseDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExRateService {

  List<String> getAllSupportedCurrencties();

  boolean hasInitializedExRates();

  ExRatesResponseDTO fetchExRates();

  void updateRates(ExRatesResponseDTO exRates);

  Optional<BigDecimal> findExRate(String from, String to);

  BigDecimal convert(String from, String to, BigDecimal amount);
}
