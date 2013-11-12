Comp 124 - HW7: Multilingual entity extraction
===
In this homework assignment you will develop a program that analyzes text in another language. Your program will:

- Detect the language of a passage of text.
- Identify concepts in the text associated with Wikipedia articles.
- Translate the concepts (if possible) to English.

Here it is in action:

```
Foo bar baz
```

Along the way, you'll get a chance to practice with Java's Maps. Otherwise known as HashMaps, hashtables, or dictionaries.

### Prepartion

 - Fork this repo, and clone your fork.
 - Import the module into IntelliJ.
 - Add all the jars in the `hw7/lib` as libraries (right click -> add as library).
 - Download the database and extract it. 
It contains a single `wikAPIdia` directory that contains a `db` directory. 
If you are using a lab computer, **do not put the database in your H: drive** or your program will run ridiculously slowly.
 - Run the LanguageDetector.java program. 
   You'll need to change the path passed to the WikapidiaWrapper constructor to be the full path to your extracted `wikAPIdia` directory 
   (make sure you have the capitalization correct!).
