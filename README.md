# GoParser

(done by Bulat Khabirov and Anastasiia Repryntseva)

## About the project 
This is a parser for **Go programming language**. Implemented using Java 8 and [ANTLR](http://www.antlr.org) 
parser generator.

All the source files are located in [antlr](./src/main/java/antlr) package. Here you can find a
[Go.g4](./src/main/java/antlr/Go.g4) file, a grammar that describes the language and other files that are used to parse 
the input file. The grammar for the language was done referencing official 
[Go documentation](https://golang.org/ref/spec#Introduction). The other files were autogenerated by the ANTLR tool. 

There are separate files that list the tokens, classes for lexer and parser. 

We also decided to describe the grammar for the whole language and not only for the specified language constructs. Also
all unicode values are supported, since Go allows usage of non-latin alphabets for identifier declaration. 

To represent the result of the parsing we used [GSON](https://github.com/google/gson) library that converts Java objects
 to JSON. In our case parser's tree is converted to JSON and written to [out.txt](.out.txt) file. 

 
## Testing
A great part of the work was dedicated to testing the grammar we described. The grammar itself consists of grammar rules
and tokens description. The former is written using EBNF form and the latter - using regular expressions and unicode 
values. 
Lexer and Parser were tested separately. For the lexer - all groups of of tokens were tested. For the parser - there
 are tests for each of the structure specified in Moodle as obligatory.  
There are separate packages for Lexer and Parser. Can be found in [test](./src/test) folder. 


## How to run 

If you use UNIX system substitute `gradle` for `./gradlew` and if you use Windows — substitute for `gradlew.bat`:

- Step 1: `cd project folder`
- Step 2: `gradle build`
- Step 3: `gradle run`
- Step 4: (for tests) `gradle test`

## Last words
   
We decided on Oracle's Java code conventions. Intended line length - 120 symbols. Project also includes
 javadoc-compatible documentation.
