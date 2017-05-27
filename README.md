# SpendTracker
Android app that allows users to take pictures of receipts for the data to be entered into a database where queries can be performed to view statistics or purchase histories based on date/time or location
> created by [Charles](https://github.com/mrthefakeperson), [Clement](https://github.com/ccyuen), [Daniel](https://github.com/chungdaniel) and [Jonathan](https://github.com/ngkjon) for EngHack Summer 2017


## About
* The app makes use of the [Google Cloud Vision API](https://cloud.google.com/vision/) to recognize text from the receipt image and to parse it into a readable JSON file
* A small database created using Java to store the user's data in JSON format


## Features:
* read receipts total, location, place, date, and stores info
use collected info to view statistics of user
* places, visited most often
* display locations on map (?)
* track spending over a period of time (?)
* set limits for spending (?)
