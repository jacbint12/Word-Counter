package uk.bank.synechron.library.wordcounter.controller;


import static uk.bank.synechron.library.wordcounter.utils.ConstantsUtils.COUNTER_URL;
import static uk.bank.synechron.library.wordcounter.utils.ConstantsUtils.VALID_QUERY_PARAM;
import static uk.bank.synechron.library.wordcounter.utils.ConstantsUtils.X_CORRELATION_ID;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Stack;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.bank.synechron.library.wordcounter.service.WordCounter;
import uk.bank.synechron.library.wordcounter.utils.Translator;

@RestController
@Tag(name = "Word Counter Service")
@RequestMapping(COUNTER_URL)
@Slf4j
@Validated
@RequiredArgsConstructor
public class WordCounterController {

  private static final String SENTENCE  = "Here is London Londres";

  @Autowired
  WordCounter wordCounter;
  @Autowired
  Translator translator;

  /**
   * Add given word to the counter.
   *
   * @param correlationId                The correlationId
   * @param text                         list of words to add
   */
  @Operation(summary = "Add a word to the word counter")
      @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully added the given word"),
          @ApiResponse(responseCode = "400", description = "The given word has not been added"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
      @PostMapping()
      public ResponseEntity<Stack> addTheGivenWord(
     @RequestHeader(value = X_CORRELATION_ID) final String correlationId,
      @RequestBody @Valid final String text) {
    log.info("Incoming request to add the given word to the counter");

    return ResponseEntity.ok(wordCounter.addWords(text));
  }

  /**
   * Add given word to the counter.
   *
   * @param correlationId                The correlationId
   * @param givenWord                         list of words to add
   */
  @Operation(summary = "Count the frequence of the given word in the counter")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully  retrieved the given word"),
      @ApiResponse(responseCode = "400", description = "The given word has not been found"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  @PostMapping(path = COUNTER_URL + VALID_QUERY_PARAM)
  public ResponseEntity<Integer> count(
      @RequestHeader(value = X_CORRELATION_ID) final String correlationId,
      @NotNull @RequestParam(name = "givenWord") String givenWord) {
    log.info("Incoming request to add the given word to the counter");

       return ResponseEntity.ok(wordCounter.count(givenWord));
  }
}
