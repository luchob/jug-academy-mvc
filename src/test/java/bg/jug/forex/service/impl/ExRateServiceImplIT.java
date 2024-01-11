package bg.jug.forex.service.impl;

import bg.jug.forex.model.dto.ExRatesResponseDTO;
import bg.jug.forex.repository.ExRateRepository;
import bg.jug.forex.service.ExRateService;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import net.bytebuddy.asm.MemberSubstitution.Argument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExRateServiceImplIT {

  @Autowired
  private ExRateService toTest;

  @Autowired
  private ExRateRepository exRateRepository;

  @BeforeEach
  void setUp() {
    exRateRepository.deleteAll();
    toTest.updateRates(new ExRatesResponseDTO("USD",
        Map.of(
           "BGN", BigDecimal.valueOf(1.78),
           "EUR", BigDecimal.valueOf(0.93)
        )
    ));
  }

  @AfterEach
  void tearDown() {
    exRateRepository.deleteAll();
  }

  @ParameterizedTest(name = "Rate from {0} to {1}. Expected {2}.")
  @MethodSource("testData")
  void testExRates(String from,
      String to,
      BigDecimal expectedRate) {

    Optional<BigDecimal> exRateOpt = toTest.findExRate(from, to);
    if (expectedRate == null) {
      Assertions.assertTrue(exRateOpt.isEmpty());
    } else {
      Assertions.assertTrue(exRateOpt.isPresent());
      Assertions.assertEquals(expectedRate, exRateOpt.get());
    }
  }

  private static Stream<Arguments> testData() {
    return Stream.of(
      Arguments.of("EUR", "BGN", BigDecimal.valueOf(1.91)),
      Arguments.of("USD", "BGN", BigDecimal.valueOf(1.78)),
      Arguments.of("BGN", "EUR", BigDecimal.valueOf(0.52)),
      Arguments.of("USD", "USD", BigDecimal.ONE),
        Arguments.of("BGJUGD", "USD", null)
    );
  }

}
