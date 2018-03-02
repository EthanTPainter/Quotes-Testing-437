# Quotes-Testing-437
Originally provided as a servlet created to test sample quotes on Dr. Offutt's website (shared with cs.gmu.edu)


Modified to include a command line interface rather than sevlet functionality

- Commented out quoteserve java file because running locally requires tomcat or Java EE or other software for servlet.jar files.
- Menu created with switch statements detailing options simialr to servlet functionality
     - Could change to method calls after each case to make it more readable but need to refactor some variables


Modified again to add functionality of Adding Quotations to current quote list

- Refactored code within switch statements (moved most of code within siwtch to methods)
  - Search switch cases (1-3) all relied on same functionality but different search mode parameter input
  - 
- Added another case option for adding quotes after menu options for searching quote texts/authors
- Quotes added are permanent so they must be stored in the xml data file
  - Could store in a separate .txt file, but that would make it a bit confusing
  - Considering the quoteList search and set functions
  
