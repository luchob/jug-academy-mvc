package bg.jug.forex.model.dto;

import java.math.BigDecimal;

public record ExRateDTO(String from, String to, BigDecimal rate) {

}
