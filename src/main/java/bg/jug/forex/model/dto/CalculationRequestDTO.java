package bg.jug.forex.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class CalculationRequestDTO {

  private @NotEmpty String from;
  private @NotEmpty String to;
  private @NotNull @Positive BigDecimal amount;

  public String getFrom() {
    return from;
  }

  public CalculationRequestDTO setFrom(String from) {
    this.from = from;
    return this;
  }

  public String getTo() {
    return to;
  }

  public CalculationRequestDTO setTo(String to) {
    this.to = to;
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public CalculationRequestDTO setAmount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }
}
