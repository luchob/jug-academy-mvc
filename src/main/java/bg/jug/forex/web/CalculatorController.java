package bg.jug.forex.web;

import bg.jug.forex.model.dto.CalculationRequestDTO;
import bg.jug.forex.service.ExRateService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CalculatorController {

  private final ExRateService exRateService;

  public CalculatorController(ExRateService exRateService) {
    this.exRateService = exRateService;
  }

  @ModelAttribute("allCurrencies")
  public List<String> getAllSupportedCurrencies() {
    return exRateService.getAllSupportedCurrencties();
  }

  @ModelAttribute("calculationRequestDTO")
  public CalculationRequestDTO calculationRequestDTO() {
    return new CalculationRequestDTO();
  }

  @GetMapping("/calculator")
  public String calculator() {
    return "calculator";
  }

  @PostMapping("/calculator")
  public String calculate(@Valid CalculationRequestDTO calculationRequestDTO,
      Model model) {
    var result = exRateService.convert(calculationRequestDTO.getFrom(),
        calculationRequestDTO.getTo(),
        calculationRequestDTO.getAmount());

    model.addAttribute("result", result);

    return "calculator";
  }

}
