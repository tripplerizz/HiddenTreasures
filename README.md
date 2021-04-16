# Hidden Treasures

Kevin Nguyen and
Hector Rizo

An app to find cool local places wherever you travel!

## To run
  - Inside the root directory of the project, create a `local.properties` file
  - Add your Google Maps API key to the file: `MAPS_API_KEY="YOUR_API_KEY_HERE"`
  - On android emulator or device, set current location to FSU in order to view posts that have already been made

## Feature summary
  - Pirate Addies
    - click the right "send" icon to "swipe right" in order to indicate to the application you have been to the Treausure Spot
    - click the left "send" icon to "swipe left" in order to indicate to the application you HAVE NOT been to Treasure Spot
    - Once all the images are done -> a blank screen should pop up
  - Maps
    - move POV arround to view current images posted by other users
    - click on an image to open up a page with more information
  - Home
    - this fragment displays images which have been revently posted by users
    - click on an image to view more information
  - Treasure Item Info -> (information page)
    - this page will load when you click an image on the MAPS or HOME fragment
    - by clicking on the maps segment in the information page, you will navigate to Google Maps application to start route to location
      - ### information displayed by fragment
        - Image, Location, Name, Descriptiong, and Pirate Count ( number of people who have been there )
  

## Contributions

- Kevin
  - Implemented navigation components
  - Set up MapsAPI
  - Set up Firebase Storage
  - Set up Firebase Database
  - Implemented Recycler View on HomeFragment
  - Put pictures on MapFragment
  - Designed the architecture of the app

- Hector
  - Implemented Pirate Swipe Fragmet 
  - Added calls to Firebase Storage and Database on Swipe Fragment
  - Edited Treasure Item object for Database
  - Implemented TreasureItemInfo Fragment
  - Created Layout for Survey, Pirate Swipe, and ItemInfo
  - Implemented Permissions for Camera and Location
  - UI fixes


