# Typing Test Application

## Purpose of this app
This application is intendend to help people get better at typing efficiently by allowing them to practice typing random common words, sentence, phrases etc. The application will do this by noting down the number of words typed per second, accuracy, and general typing etiquette etc. 

## Intended users for this typing test application
- People who want to improve their typing speed, accuracy, ettiquette.
- Programmers who want to learn how to type efficiently
- Writers who want to improve their productivity
- Language learners who want to familarise themselves with new keyboard layouts  
- People who generally enjoy taking typing tests and want more stastics to improve their speeds than provided currently by other typing test softwares

## My intention for building a typing test application
I was an extremely slow typer in highschool which lead to problems with typing up schoolwork.  To solve this problem, I decided to get better at typing.  To do this, I decided to use a variety of typing tests I found online. However, I was not able to increase my typing speed above 60 words per minute for a long time. I eventually found out that this was because I was finding it difficult to type certain words due to my posture. However, at the time I was finding it difficult to increase my typing efficiency as none of other typing tests were providing me with the information needed to pinpoint the words I have having trouble typing. Therefore, to help others like myself, I have decided to make my own typing test software with helpful features that I believe will best help others get faster with greater ease, rather than struggling like I did. Furthermore, this typing test application will allow people to make their own typing tests with a great degree of freedom regarding the 
qualities defining each test.

## User Stories
- As a user, I want to add my own personalised typing tests with custom phrases, words, characters, or languages to the list of typing tests

- As a user, I want to make add my own difficulties of typing tests to categorise different tests according to certain qualifiers such as  language and quantifiers such as time limits, to the exisitng list of difficulty

- As a user, I want to be able to view statistics relating to my previous test 

- As a user, I want to be able to save autogenerated tests produced by the application for future practice

- As a user, I want to be able to save statistics from certain test\tests

- As a user, I want to be able to create individual tests to practice with

- As a user, I want to be able to create custom individual tests to practice with

- As a user, I want to be able to create my own custom difficulties describing tests

- As a user, I want to be able to view the difficulties that I have created

- As a user, I want to be able to view general information related to the tests I have already taken 

- As a user, I want to be able to save all the difficulties and statisitcs related to each test, if I choose to.

- As a user, I want to be able to load all the difficulties and statistics related to each previous test, if I choose to

- As a user, I want to be able to delete specific tests from the list of typing tests

- As a user, I want to be able to filter typing tests according to wpm while viewing them

- As a user, I want to bea able to filter difficulties according to their name while viewing them


## Instructions for End User
# How to add a difficulty to the list of difficulties
- First load the application
- Then click on the difficulties button
- On the top right corner there is an add button
- Press the add button
- Enter the necessary details for the difficulty
- press the submit button
- done 

# How to filter difficulties by name
- From the main menu press Difficulties
- There is a search bar on the top left hand corner
- Click on it and enter the name you want to search
- The list of Difficulties will automatically update as you type in the name

# How to add a typing test to the list of typing tests
- First load the application
- Then click on either the Regular Typing test or the Custom Typing test Button
- If its the regular typing test, the test will automatically start
- After the test is done, the test will be added to the list of tests
- For custom typing tests, choose a difficulty from the drop down menu
- The test will then automatically start
- After the test is complete, it will automatically be added to the list of typing tests

# How to delete a particular test
- From the main menu, press on View typing tests
- You will be greeted by all the typing tests you have done
- Click on anyone of them, and then press the delete button 
- on the right hand side corner to delete it

# How to search for a particular typing test by filtering by WPM
- From the main menu, press View typing tests
- You will see a search bar on the top left hand corner
- Click on it and enter the WPM you want to filter for
- As you enter, the typing tests on the screen will automatically be filtered


# Where to find visual component
- There is a visual component in the form of a splash screen of a penguin
- Upon opening the application you will be greeted by the penguin for 3 seconds
- Before the application loads

# How to save the state of the applicatio to file
- From the main menu, you will see a menu bar with one option
- That option is file, click on file and it will show you a drop down menu
- From the drop down menu, there are two options - save and load
- Press on the save button and your work will be saved

# How to load the state of the application from file
-  From the main menu, you will see a menu bar with one option
- That option if file, click on file and it will show you a drop down menu
- From the drop down menu, there are two options- save and load
- Press on the load button and your previously saved state will be loaded

# Representative sample of events that occur when the program runs
- Mon Nov 25 13:26:31 PST 2024
  Added difficulty Hard to the list of difficulties
- Mon Nov 25 13:26:48 PST 2024
  Added difficulty Easy to the list of difficulties
- Mon Nov 25 13:26:49 PST 2024
  Filtering Difficulties that contain the string N from the list
- Mon Nov 25 13:26:50 PST 2024
  Filtering Difficulties that contain the string  from the list
- Mon Nov 25 13:27:17 PST 2024
  Added typing test 1 to the list of typing tests
- Mon Nov 25 13:27:46 PST 2024
  Added typing test 2 to the list of typing tests
- Mon Nov 25 13:27:55 PST 2024
  Filtering Tests that have wpm 6 from the list
- Mon Nov 25 13:27:56 PST 2024
  Filtering Tests that have wpm 66 from the list
- Mon Nov 25 13:28:02 PST 2024 
  removed 1 from the list of typing tests

  # Phase 4: Task 3
  - Improvements I would have made to the project if I had more time:

  - 1) I would refactor the DifficultiesPanel and the ViewTypingTestPanel by creating an abstract class called ViewPanel that would have all the methods common between these two Panels in it. This is because the DifficultiesPanel and the ViewTypingTestPanel are extremely similar. In fact, they have the exact same constructor and very similar functionality. My main reason for doing this is to reduce duplicate code. Thereby, making the structure of my application more cohesive and easier to debug if any problems arise.

  - 2) I would also create specific classes for JButtons, JPanels, and JLists etc. This is because, I defined a lot of the components with the same properties in every class (EX: the back button). I would create new classes for these specific components so as to reduce code duplication.

  - 3) Instead of using an ArrayList to represent ListOfDifficulties, I would use a LinkedHashSet and rework my application to utilise sets instead of lists. This is because, I needed to ensure that there are no duplicate difficulties. By using an overriden LinkedHashSet instead of an ArrayList, it would make my application more efficient.


  