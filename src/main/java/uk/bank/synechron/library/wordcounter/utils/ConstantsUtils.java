package uk.bank.synechron.library.wordcounter.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstantsUtils {

  public static final String COUNTER_URL  = "/wordcounter";
  public static final String VALID_QUERY_PARAM = "?givenWord=London";
  public static final String INVALID_QUERY_PARAM = "?givenWord=LEEDS";
  public static final String X_CORRELATION_ID = "X-Correlation-ID";
  public static final String X_CORRELATION_ID_VALUE = "5bf47572-bafb-4c81-9094-a8ba0ba74821";

}
