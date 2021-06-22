# Zero's Notes
## About the project
This project, more than anything is an experiment, and a training project to understand and experience the creation of an Android App from start to finish, and to serve as a personal tool.

The main idea behind the project is to keep track of different activities that need to be done - like a "To-Do List". For a good time I have used "Samsung Notes" to create checklists for different things I want to do on a day-by-day basis but also reapeating actions - habits that I wanted to create. This meant creating a new "Note" every day an adding the tasks that I wanted to do that day - be it the same as yesterday's or otherwise. After a month of this creation-recreating business I got tired of it and I decided to start this project.

The App deals with different types of "Notes":
* Notes (Simple) - they just are a "to-do" that is unique.
* Habits - similarly, still a "to-do" but one which resets each day.
* Projects - A type of note that isn't dependent on a date - just a certian project or thing you want to do: for example a project like this one!

## Learning
As I've said before, the main goal of this app is to learn all about the Android App development cycle, from simple things like making a button work, to more complicated concepts like (de)serialization, project planning and structuring, working with Custom UI Components, working with RecyclerViews, and so on...

As of 2021.06.22 I consider this project done, bar perhaps a few bugfixes that would arrive in the future. Here is my retrospective of the whole ordeal:

First thing that I've really learned is how to structure an Android layout, and in general how to use them. It's observable that the later I made a layout file, the better it is. Even so, there are things I would've done differently while making layout files:
* Ditch custom UI Components for what can be done with layouts - they are just not needed for how simple of a concept I used them for. I could've easly used a constraint layout and everything would be okay. But again, this project was a learning exercise so learning how to make and how to use custom UI Components (although confusing since it was the first thing I did), I had fun working with them.
* Clearer ids. At first I used basically random IDs with just a faint correlation to the actual thing (textView1, fragment-button), but then I got super verbose (popup_edit_note_note_contents) and in reality it should've been a mix of both. I als shouldn't have been afraid of using the same ID twice on different files. It would've been completely okay to do so.

Another thing that I've learned is JSON Serialization (with GSON), but that wasn't such a smooth ride. First of all I should have designed the app around what I wanted to actually have as an end product: Notes, Habits, Projects, SubTask, etc. and by going in without much planning I have suffered a lot. The latest commit with the SubTasks is a testament to that. It's not a clean implementation, and there is a lot of code overlap.

One thing I am ashamed of is the adapters. It's the definition of copy-pasting too much. At first I tried to make an universal adapter that would switch logic based on the note type, but I believe I should have had a common master adapter from which all of the sub-adapters could extend (parent-child). Of course that would be a sensible thing to do, so I didn't do that...

Also I should have used tests from the very beginning and used a lot more of them. Would save an insane amount of time on testing if I had implemented something correctly (。_。). Also could've made by comments way better... A lot of them are answering "what" rather than "why".

The things I am most proud of is the singletons and listeners. Using them (the singletons) was a good idea, it saved A WHOLE LOT OF HEADACHE for very few lines of code, and the listeners are very well implemented, the code being readable without comments. It's not messy, it just works and you can tell what the code is supposed to do! 

Also I am proud of the original popup idea, singling it out into a class and having a "showPopupWindow" function was good. The first one - which was date selection - was implemented well enough, but by the time you get to Editing SubTasks it's pretty convoluted and... well... a lot of layouts were made reduntantly and the logic to note creation and editing could have been (not so easly) merged together, or perhaps through inheritance. Either way, not super proud of that one.

## Conclusion
All in all, I am proud of this project, not because I have done things well (I have messed up A LOT, and made A LOT of mistakes that are going to cost me a lot of time if I want to solve them), but because I carried it out to the end and I managed to learn a lot about app creation and java programming in general. Oh, and how to *"avoid"* being a messy programmer. I am ashamed of that one.

But again, it was a learning experience that I have gained a lot through, and I also gained a new app that I can use on a daily basis for myself. My only hope now is that it doesn't crash and burn my phone >.< 