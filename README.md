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

### Preparation

 - Fork this repo, and clone your fork.
 - Import the module into IntelliJ.
 - Create a directory called "wp" (you may have already done this for HW6). if you are working on a lab computer, you should place it in /Users/<your name>/wp. If you are on a laptop, you can place it anywhere.
- Download the [wp-hw7 database](http://www.shilad.com/wp-hw7.zip) and extract it. 
It contains a single `wp-hw7` directory that contains a `db` directory. 
If you are using a lab computer, **do not put the database in your H: drive** or your program will run ridiculously slowly.


### Make sure that things are installed correctly:

1. Open the `WikAPIdiaWrapper.java` file in IntelliJ.
2. Update the `DATA_DIRECTORY` variable to point to the [absolute path](http://www.computerhope.com/jargon/a/absopath.htm) to the `wp-hw7` directory you created in the previous step.
1. Open `LanguageDetector.java`.
4. Run the class as an application.
You should see information about the [Apple article](http://simple.wikipedia.org/wiki/Apple):
 
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

In a nutshell, the Language detector generates a score that indicates how common the words in a text are in that langauge.
For example, assume you're scoring the text "Aw human sowels is born free" against the language Scots.
You will sample 1000 Scots Wikipedia articles and find:
 *  "Aw" has count 9
 *  "human" has count 18
 *  "is" has count 2377
 *  "born" has count 67
 *  "free" has count 10
 
Thus, the total score for Scots is (9 + 18 + 2377 + 67 + 10) = 2481. 
Your program will repeat this computation in all languages and choose the language with the highest score.

Take a look at LanguageDector.java. 
You'll complete your work for part 1 in this class.
I have declared the main methods for you: `train()`, `detect()`, and the `main()` method, but they don't do anything useful.

A LanguageDetector must be trained once to identify words in each language. 
The train method essentially "precomputes" the counts for each word in each language to speed up language detection.

`train()`: The train method needs to do the following *for each language*:

* Extract the page text from the first 1000 pages.
* Split each page text into words.
* Count the number of every unique word in the first 1000 articles occurs across all 1000 articles.

You'll need to create instance variables to capture the data.
**The Utils class has some helpful constants and a method to split words.**

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

**C. EXTRA CREDIT: Implement a fancy extract:**
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
