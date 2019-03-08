# I know what are you looking for....
Probably you will be happy hier: https://robotframework.org/


# About the orquestra project
A dynamically defined human readable BFN-based DSL for testing web app and its Virtual Machine (VM). [As first step]

Currently, only the brazilian portuguese version of the the DSL is defined.
The english version is just cooking.

But you can easily create it. It's dynamics! Just modify the `web-grammar.bnf` file in `samples` directory. See details bellow.

## The future goals

Define a framework (DSL, virtual machine, pluging etc.) for defining general purpose BNF-based grammar for creating web automated tests.
Yes! (I know!!) Probably another BDD-based solution like JBehave, Cucumber etc. but without any code to do it. 
It will be at a abstract level the the business guys can code, compile and run it. ;)
 

Now, there is only a brazilian portuguese web grammar.
The code was made in english, for easy forking. ;)  

# Compiling the solution

Just run the maven clean package command:

	mvn clean package

# Running the solution

The solution have a directory of sample called "samples".
There is some samples for navigating google.com and github.com. 

Command-line usage is as follows:

    java -Dwebdriver.gecko.driver=./lib/geckodriver -jar orquestra.jar --gf file_name.txt --sf script.tst --df object_definition.txt
    
You will need download the corresponding webdriver for your operating system.
Inform your JVM (Java(c) Virtual Machine) where the WebDriver is located:

    java -Dwebdriver.gecko.driver=./lib/geckodriver
    
You will need also inform the Web Grammar, the object definition file (there is not an default one) and the script to be executed:

	-jar orquestra.jar --gf file_name.txt --sf script.tst --df object_definition.txt

For Mozilla Firefox based driver see: https://github.com/mozilla/geckodriver/releases
For Google Chrome webdriver see: https://sites.google.com/a/chromium.org/chromedriver/downloads

## we have same samples

### Surfing GitHub



# The app output

The orquestra outputs two artfacts:
- A json file (`output.json`) with detailed information about the execution for using in automated tests;
- A html file (`applied_template.html`), extracted from the json one, for user easy visualization.

# And Please, Help us! ;)

chicojfp@gmail.com for more details about this project.
