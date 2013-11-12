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

### Test your setup
 
 Change PATH_DB in Utils.java to the full path to your extracted `wikAPIdia` directory (make sure you have the capitalization correct).
 Run the LanguageDetector.java program. You should see some test output like:
 
```
Apple in other languages:
	Bosnian: Jabuka
	Scots: Aiple
	Welsh: Afal
	Icelandic: Epli
	Hindi: सेब
	Simple English: Apple
Enter text to detect language, or 'stop'.
```
Type stop to stop the detector. It won't work until you finish Part 1.
 
### Part 1: Language detector

For your first task, you'll write a class that detects the language of a text. 
Take a look at LanguageDector.java. 
You'll complete your work for part 1 in this class.
I have declared the main methods for you: `train()`, `detect()`, and the `main()` method, but they don't do anything useful.

A LanguageDetector must be trained once to identify words in each language. 
To do this, you must call the `train()` method once each time your program is run.
After training the detector, you can call `detect()` as many times as you would like.

The train method needs to do the following *for each language*:

* Extract the page text from the first 1000 pages.
* Split each page text into words.
* Count the number of times each word occurs across all 1000 page texts.

You'll need to create instance variables to capture the data.
The Utils class has some helpful constants and a method to split words.

Next, complete the `detect` method for a given text.
Do the following for each language: 
* Split the text into words.
* Retrieve the number of times each word occurs in the first 1000 page for that language.
* Sum up those counts.

Choose the language with the highest count. 

Test your program by Googling texts in each language.
Your program should do a pretty good job, but it may get a few languages confused - for example Scots and Simple English.
For extra credit, figure out a way to improve the detection algorithm.

### Part 2: Entity extractor


