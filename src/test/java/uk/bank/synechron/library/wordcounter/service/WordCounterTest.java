package uk.bank.synechron.library.wordcounter.service;

import java.util.Stack;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.bank.synechron.library.wordcounter.utils.Translator;

@ExtendWith(SpringExtension.class)
public class WordCounterTest {

  @InjectMocks
  private WordCounter wordCounter;

  private Stack<String> stack = new Stack<String>();
  @Mock private Translator translator;

  @BeforeEach
  public void setup(){
    wordCounter.freeStack();
  }

  @Test
  public void shouldAddOneWord(){
    String oneWord = "Hello";

   stack = wordCounter.addWords(oneWord);

    assertNotNull(stack);
    assertEquals(1, stack.size(),"The stack size should be 1");
    assertEquals("Hello", stack.get(0), "The contents of the stack should be Hello ");

  }

  @Test
  public void shouldAddTwoWord(){
    String twoWord = "Hello Synechron";

    stack = wordCounter.addWords(twoWord);

    assertNotNull(stack);
    assertEquals(2, stack.size(),"The stack size should be 2");
    assertEquals("Hello", stack.get(0), "The first contents of the stack should be Hello");
    assertEquals("Synechron", stack.get(1), "The second contents of the stack should be Synechron");

  }

  @Test
  public void shouldNotAddWordWithSpecialCharacter(){
    String threeWord = "Hello d+ear Synechron";

    stack = wordCounter.addWords(threeWord);

    assertNotNull(stack);
    assertEquals(2, stack.size(),"The stack size should be 2");
    assertEquals("Hello", stack.get(0), "The first contents of the stack should be Hello");
    assertEquals("Synechron", stack.get(1), "The second contents of the stack should be Synechron");

  }

  @Test
  public void shouldNotAddWordWithNumerical(){
    String threeWord = "Hello d3ear Synechron";

    stack = wordCounter.addWords(threeWord);

    assertNotNull(stack);
    assertEquals(2, stack.size(),"The stack size should be 2");
    assertEquals("Hello", stack.get(0), "The first contents of the stack should be Hello");
    assertEquals("Synechron", stack.get(1), "The second contents of the stack should be Synechron");

  }

  @Test
  public void shouldReturCorrectnumberIfAllInsertedWordsAreInEnglish(){
    String allInEnglish = "Hello my my friend";
    given(translator.translate("Hello")).willReturn("Hello");
    given(translator.translate("my")).willReturn("my");
    given(translator.translate("friend")).willReturn("friend");
    stack = wordCounter.addWords(allInEnglish);

    assertNotNull(stack);


    assertEquals(2, wordCounter.count("my"), "Should be 2 time my");

  }

  @Test
  public void shouldReturCorrectnumberIfSomeInsertedWordsAreNotInEnglish(){
    String notAllInEnglish = "Here is London Londen Londres";
    given(translator.translate("Here")).willReturn("Here");
    given(translator.translate("is")).willReturn("is");
    given(translator.translate("London")).willReturn("London");
    given(translator.translate("Londen")).willReturn("London"); // London in Dutch
    given(translator.translate("Londres")).willReturn("London"); // London in french
    stack = wordCounter.addWords(notAllInEnglish);

    assertNotNull(stack);


    assertEquals(3, wordCounter.count("London"), "Should be 3 time for London");

  }

  @Test
  public void shouldReturZero(){
    String allInEnglish = "Here is London";
    given(translator.translate("Here")).willReturn("Here");
    given(translator.translate("is")).willReturn("is");
    given(translator.translate("London")).willReturn("London");
    given(translator.translate("Leeds")).willReturn("Leeds");
    stack = wordCounter.addWords(allInEnglish);

    assertNotNull(stack);


    assertEquals(0, wordCounter.count("Leeds"), "Should be 0 time for Leeds");

  }
}
