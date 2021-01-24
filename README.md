# Tappie

> Tappie lets you harness the interactivity of NFC tags to make managing your time a dynamic and tactile experience. Make your day tappable!

## Inspiration

It has been almost a year since the outburst of Covid-19, which forced billions of people into lockdowns. The pandemic has fundamentally, and perhaps permanently changed the way people live and work. As more and more people switch from on-site to remote working, the boundary between work and life becomes rather vague. As a result, challenges arose in terms of keeping track of one’s daily timeline.
 
To help users improve work-from-home efficiency and gain better time-management experience, we came up with the idea of a time-tracking app, _Tappie_. We were further inspired by the NFC built-in function of the mobile devices, which can create shortcuts for users' regular behaviours such as opening Apps and starting programs. By integrating NFC tags' available functions, we can help record your time allocation for different activities by attaching NFC tags to every one of your daily events. With the above-mentioned data in hand, we will analyze the data and provide the user with instantaneous user report and feedback.

In the research stage, we learnt about an application _Toggle_, which can also couple NFC tags to track time. Toggle uses NFC tags to create Siri-shortcuts on Apple devices, which further triggers tasks. However, the problem behind _Toggle_  and other NFC relevant apps is that the setup process is tedious, and a third-party app is needed to program and read NFCs, which is not user-friendly. **Our desired approach is to create a time-tracking app that is compatible with NFC itself.**

## What it does

Tappie is a time-tracking app that enables users to keep track of your daily routine with NFC tags' aid. By simply tapping your phone to an NFC tag registered to a custom task, a timer will be triggered to start tracking your activity. 

## How we built it
### Research
In the testing stage, we first test the functionalities of NFC tags to solidify the project feasibility. For instance, whether we have access to certain phone functions like unlock the phone or start an app by reading from NFC.  Upon verification, we then wrote a sample android app simply for NFC tag implementation, bypassing time-tracking app implementation in the first place.

### NFC TEST 
Android devices with NFC activated can constantly search for available NFC devices when the screen is unlocked. As long as an available NFC device is found, it will parse the NDEF record stored on that device and according to its intent, select an appropriate application to handle the intent.

> Our app is developed on Android-studio with Java. 

### High-level design

Our project is divided into three major parts,

- Frontend: Interface UI design, UI implementation and interaction with users and NFC Tags
- Backend: Backend processing, NFC processing
- Data: For prototype, we use JSON files to store our data.

All of the design diagrams' components are independent of each other and can be worked on in parallel, with variables passing between them obtaining a consistent format.

### Testing Methodology

Our testing process is based on the following levels, unit testing, integration testing, system testing. The function is tested during the implementation process. At the same time, the integration test is done after integrating different modules. The application is tested and debugged as a whole to check whether it is delivering the desired response. 

## Challenges we ran into

### Backend Challenges
Our backend has extremely complicated logic as it interacts with front end-user behaviour and takes care of the external interaction with NFC chips through “tapping” motions. In the initial design phase, we needed to develop a Finite State Machine to accommodate all the possible interactions between the three parties and enumerate through all the possible causes. 

### Database Challenges
Android uses a file system that's similar to disk-based file systems on other platforms. The system provides several options for you to save app data. Given that we need constant read and write access to the data storage, we eventually chose to store our app-specific storage data.


## What's next for Tappie

### Extensions
Cloud Firebase:
  - User authentication
  - Cloud database
  - Multi-device synchronization

Machine learning on user data to perform analysis result
- Power - measured by duration of activities
  - Sum all durations over the entire duration
- Consistency - measured by the consistency of each activity
  - Variance in the durations of each activity per day
- Diversity - measured the variation of types of activities
  - Number of different activities
- Tenacity - measured by the number of new activities you attempt and don’t quit
  - Define ‘new’ as being not tried before for more than X hours 
  - Check the number of new activities in the period.
- Balance - the balance between ‘fun’ and ‘self-improvement’ activities
  - Calculate durations for each fun and self-improvement and compare them


