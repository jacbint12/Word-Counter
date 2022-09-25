# Word-Counter
Addd words in  a counter and return the number of it occurence.

Write a library called "WordCounter".  It should have the following two distinct methods:
1. method that allows you to add one or more words
2. method that returns the count of how many times a given word was added to the word counter

It should also have the following requirements:

should NOT allow addition of words with non-alphabetic characters
should treat same words written in different languages as the same word, for example if
adding "flower", "flor" (Spanish word for flower) and "blume" (German word for flower) the counting method should return 3.  You may assume that translation of words will be done via external class provided to you called "Translator" that will have method "translate" accepting word as an argument and it will return English name for it.

Please consider the following:
adopt a TDD manner if possible. 
consider the software design principles you are using.  What are they, if any?
are there any design patterns appropriate for all/part of this task? 
think of the most optimal algorithm for storing and counting words.  Be prepared to describe your approach.
do not use persistence or in-memory DB, but consider the memory utilization of your solution.
don’t make any assumptions about the execution context for the “WordCounter” library.

As a further enhancement, please create a microservice to expose the “Word Counter” functionality to external clients.  Consider how clients will access the service.  Where would you host the service?   How would you ensure resiliency of the service?  

Things to consider . . .

Collections
Hashmaps
Streams
Multi-threading
Updating Map in atomic way
Thread safety
Lambda functions
Java 8 functional style coding
JVM / GC tuning
Ability to code algorithms on-the-fly such as traversing a tree in level order
