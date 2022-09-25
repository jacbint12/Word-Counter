package uk.bank.synechron.library.wordcounter.controller;

import java.util.Arrays;
import java.util.Stack;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.bank.synechron.library.wordcounter.service.WordCounter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.bank.synechron.library.wordcounter.utils.ConstantsUtils.COUNTER_URL;
import static uk.bank.synechron.library.wordcounter.utils.ConstantsUtils.INVALID_QUERY_PARAM;
import static uk.bank.synechron.library.wordcounter.utils.ConstantsUtils.VALID_QUERY_PARAM;
import static uk.bank.synechron.library.wordcounter.utils.ConstantsUtils.X_CORRELATION_ID;
import static uk.bank.synechron.library.wordcounter.utils.ConstantsUtils.X_CORRELATION_ID_VALUE;


import lombok.SneakyThrows;
import uk.bank.synechron.library.wordcounter.utils.Translator;
import static org.mockito.Mockito.when;

@WebMvcTest(WordCounterController.class)
public class WordCounterControllerTest {

  private static final String SENTENCE  = "London Londres";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  WordCounter wordCounter;
  @Mock
  Translator translator;

  @Autowired
  WordCounterController wordCounterController;

  @BeforeEach
  public void setup(){
    wordCounter.freeStack();
  }

  @Test
  @SneakyThrows
  void shouldAddTheGivenWords() {

    Stack<String> expected = new Stack<String>();

    expected.add("London");
    expected.add("Londres");
    when(wordCounter.addWords(SENTENCE)).thenReturn(expected);

    final ResultActions resultActions = mockMvc.perform(
        post(COUNTER_URL)
            .contentType(MediaType.TEXT_PLAIN)
            .header(X_CORRELATION_ID, X_CORRELATION_ID_VALUE)
            .content(SENTENCE))
            .andDo(print()).andExpect(status().isOk());


    String actual = resultActions.andReturn().getResponse().getContentAsString()
        .replaceAll( "[^A-Za-z]", "" );

    assertEquals(SENTENCE.replaceAll("\\s+",""), actual, "Shoul be HereisLondonLondres");
  }

  @Test
  @SneakyThrows
  void shouldReturnCorrectNumber(){

    wordCounter.addWords(SENTENCE);
    given(wordCounter.count("London")).willReturn(2);

    Integer actual = wordCounterController.count(X_CORRELATION_ID, "London").getBody();

    assertEquals(2, actual,"Should be 2");
  }

  @Test
  @SneakyThrows
  void shouldReturnCorrectZero(){

    wordCounter.addWords(SENTENCE);
    given(wordCounter.count("Leeds")).willReturn(0);

    Integer actual = wordCounterController.count(X_CORRELATION_ID, "Leeds").getBody();

    assertEquals(0, actual,"Should be 0");
  }
  }
