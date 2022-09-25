package uk.bank.synechron.library.wordcounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"uk.bank.synechron.library.wordcounter"})
public class WordCounterApplication {
  public static void main(String[] args) {
    SpringApplication.run(WordCounterApplication.class, args);
  }
}
