package bg.jug.forex.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "exchange_rates")
public class ExRateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Column(unique = true)
  private String currency;

  @NotNull
  @Positive
  private BigDecimal rate;

  public Long getId() {
    return id;
  }

  public ExRateEntity setId(Long id) {
    this.id = id;
    return this;
  }

  public String getCurrency() {
    return currency;
  }

  public ExRateEntity setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public ExRateEntity setRate(BigDecimal rate) {
    this.rate = rate;
    return this;
  }

  @Override
  public String toString() {
    return "ExRateEntity{" +
        "id=" + id +
        ", currency='" + currency + '\'' +
        ", rate=" + rate +
        '}';
  }
}
