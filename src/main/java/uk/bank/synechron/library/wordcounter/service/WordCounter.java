package uk.bank.synechron.library.wordcounter.service;

import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.bank.synechron.library.wordcounter.utils.Translator;

@Service
public class WordCounter implements Runnable {

  private static Stack<String> stack = new Stack<String>();
  @Autowired
  private Translator translator;

  public void freeStack(){
    stack.clear();
  }

  public Stack<String> addWords(String inputString){
    Arrays.asList(inputString.trim()
       .replaceAll("(^| )[^ ]*[^A-Za-z ][^ ]*(?=$| )", "")
       .split("\\s+")).stream().forEach(string -> stack.push(string));
    return stack;
  }

   public synchronized int count(String givenString){

     ConcurrentSkipListMap<String, AtomicInteger> map =  new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);

    stack.stream()
        .map( str -> translator.translate(str)) // if str is in english then translator return the same word, otheerwise it translation is returned.
        .map(String::toLowerCase)
        .collect(Collectors.toConcurrentMap(word -> word, word -> 1, Integer::sum))
        .entrySet().stream().forEach(x -> {
      map.put(x.getKey(), new AtomicInteger(x.getValue().intValue()));
    });

    return stack.contains(givenString)? map.get(givenString).get(): 0;


  }

  @Override
  public void run() {
    System.out.println("This is a word counter");

  }
}
