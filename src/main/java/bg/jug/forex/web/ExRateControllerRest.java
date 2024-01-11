package bg.jug.forex.web;

import bg.jug.forex.model.dto.ExRateDTO;
import bg.jug.forex.service.ExRateService;
import bg.jug.forex.service.ObjectNotFoundException;
import javax.print.attribute.standard.Media;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExRateControllerRest {

  private final ExRateService exRateService;

  public ExRateControllerRest(ExRateService exRateService) {
    this.exRateService = exRateService;
  }

  @GetMapping(value = "/api/exrate",
      produces = {
          MediaType.APPLICATION_JSON_VALUE,
          MediaType.APPLICATION_XML_VALUE
      })
  public ResponseEntity<ExRateDTO> getExRates(
      @RequestParam("from") String from,
      @RequestParam("to") String to) {

    ExRateDTO result = exRateService.
        findExRate(from, to).
        map(r -> new ExRateDTO(from, to, r)).
        orElseThrow(ObjectNotFoundException::new);

    return  ResponseEntity.ok(result);
  }
}
