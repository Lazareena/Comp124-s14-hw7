Comp 124 - HW7: Multilingual entity extraction
===
In this homework assignment you will develop a program that analyzes text in another language. Your program will:

- Detect the language of a passage of text.
- Identify concepts in the text associated with Wikipedia articles.
- Translate the concepts (if possible) to English.

Here it is in action for the Scots language:

```
Kofi Annan (born 8 Aprile 1938) is a Ghanaian diplomat who served as the seivent Secretary-General o the Unitit Naitions frae 1 Januar 1997 tae 31 December 2006. 
Annan an the Unitit Naitions wur the co-recipients o the 2001 Nobel Peace Prize for his foondin the Global AIDS and Health Fund for tae support developin kintras in their fecht tae care for their fowk.

Translating text from Scots to Simple English found entites:
	'Kofi Annan' => 'Kofi Annan'
	'8 Aprile' => 'April 8'
	'Unitit Naitions' => 'United Nations'
	'1 Januar' => 'January 1'
	'1997' => '1997'
	'31 December' => 'December 31'
	'Unitit Naitions' => 'United Nations'
	'2001' => '2001'
	'Nobel Peace Prize' => 'Nobel Peace Prize'
	'Kintra' => 'State'
```

And for Hindi:
```
कोफ़ी अन्नान(जन्म: 8 अप्रैल 1938) एक घानाई कूटनीतिज्ञ हैं। वे 1962 से 1974 तक, और 1974 से 2006 तक संयुक्त राष्ट्र में कार्यरत रहे। वे 1 जनवरी 1997 से 31 दिसम्बर 2006 तक दो कार्यकालों के लिये संयुक्त राष्ट्र के महासचिव रहे। उन्हें संयुक्त राष्ट्र के साथ 2001 में नोबेल शांति पुरस्कार से सह-पुरस्कृत किया गया।

translating text from Hindi to Simple English found entites:
	'८ अप्रैल' => 'April 8'
	'राजनय' => 'Diplomacy'
	'१९६२' => '1962'
	'१९७४' => '1974'
	'१९७४' => '1974'
	'२००६' => '2006'
	'संयुक्त राष्ट्र' => 'United Nations'
	'१ जनवरी' => 'January 1'
	'१९९७' => '1997'
	'दिसम्बर' => 'December'
	'२००६' => '2006'
	'संयुक्त राष्ट्र' => 'United Nations'
	'संयुक्त राष्ट्र' => 'United Nations'
	'२००१' => '2001'
	'नोबेल शांति पुरस्कार' => uknown
```

Along the way, you'll get a chance to practice with Java's Maps. Otherwise known as HashMaps, hashtables, or dictionaries.

### Prepartion

 - Fork this repo, and clone your fork.
 - Import the module into IntelliJ.
 - Add all the jars in the `hw7/lib` as libraries (right click -> add as library).
 - Download the [database](http://poliwiki.macalester.edu/shilad/wikAPIdia.translation.zip) and extract it. 
It contains a single `wikAPIdia` directory that contains a `db` directory. 
If you are using a lab computer, **do not put the database in your H: drive** or your program will run ridiculously slowly.

### Test your setup
 A few notes about running your programs:
 
 - You will need to adjust your programs' run configurations. To do this, after running a program once, click on "edit configurations" from the run menu.
 - You should run your programs from the hw7 directory, not your project workspace. 
   To change this, go to the program's run configuration, select the rightmost button after Working directory and select MODULE_DIR.
 - You may need to adjust the memory settings. 
 Select the program's run configuration and add the vm option `-Xmx1024M` to give your program 1GB of memory (this should be plenty).
 
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

`train()`: The train method needs to do the following *for each language*:

* Extract the page text from the first 1000 pages.
* Split each page text into words.
* Count the number of times each word occurs across all 1000 page texts.

You'll need to create instance variables to capture the data.
The Utils class has some helpful constants and a method to split words.

`detect(text)`: Given a particular text, the detect method does the following for each language: 
* Split the text into words.
* Retrieve the number of times each word occurs in the first 1000 page for that language.
* Sum up those counts.

Choose the language with the highest count. 

Test your program by Googling texts in each language.
Your program should do a pretty good job, but it may get a few languages confused - for example Scots and Simple English.
For extra credit, figure out a way to improve the detection algorithm.

### Part 2: Entity extractor

I have provided you with an empty entity extractor class. 
For the second portion of the homework, you'll need to complete the entity extractor class and the main method that uses it.

**A. Implement a simple extract:** Your `extract()` method should first detect the language of the text using its language detector.
Next, split your text into words and check each word to see if it is the title of a Wikipedia article in the source langauge.
If a word does correspond to an article, try to find the simple english equivalent of the article (this isn't always possible).
Format your results similarly to the output you see above.

**B. Implement main:** Complete the main method of your program.
You can model your work on the LanguageDetector's main method.
Create the components necessary for an entity extractor and then create the entity extractor itself.
Repeatedly ask the user for a text and then extract entities in the text.
You should now be able to test your program, but it will only extract single word concepts.

**C. Implement a fancy extract:**
Finally, you'll improve the performance of your algorithm by looking for more *specific* concepts that span more than one word (e.g. `Barack Obama`).
To do this, you'll need to understand the concept of [n-grams](http://en.wikipedia.org/wiki/N-gram) to complete this task. 
An n-gram is simply a series of n words that occur consecutively in a text. For example, given the text:
```
Macalester is committed to being a preeminent liberal arts college
```
* The 1-grams (i.e. unigrams) would be the individual words: "Macalester", "is", "committed", ....
* The 2-grams (i.e. bigrams) would be consecutive pairs of words: "Macalester is", "is committed", "commited to" ...
* The 3-grams (i.e. trigrams) would be consecutive triples of words: "Macalester is committed", "is committed to", ...

Adjust your program so that it looks for trigrams, bigrams, and unigrams.
Your program should prefer trigrams to bigrams and bigrams to unigrams. 
More specifically, if a word is part of a trigram, it should not be used as a bigram or unigram.
