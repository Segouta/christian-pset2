### Welcome to the Mad Libs app!
The game where you pick a story, drop some words and enjoy the silly result!

By:               Christian

Student number:   11930683

Date:             10-11-2017

Subject:          Native App Studio UvA

##
**This app has 3 activities.**
##
The **first** one is the splash page, where a user is welcomed and a short explaination is given.
##
In the **next** screen, one can choose which story he wants to use, or he can let the app pick a random one for him.
Then, the user is asked to fill in word by word, according to the word types hinted in the text field.
One can not type a word befor a story is picked, the editText is not editable till that has happened.
If a user tries to use a word before a story is picked, a message is showed.
If a user tries to use a word before a word is filled in, a message is showed.
Also a progress bar is showed that indicates the progress a user has made. This is also indicated by the text in the button.
##
In the **last** screen, the story is displayed. The filled in words are red and bolt, and the text layout is up to normal standards.
One can also scroll through the text if it's too long to display at once.
##
Note: *Every single activity works in portrait mode AND in landscape mode.*

Extra note: *The buttons and the scroll bar indicator are in a custom style.*

Even another more important note: *The app works with fullscreen on every activity!*
##
IMPROVEMENTS: Story.java only accepted placeholders in texts that had spaces around it. That is less nice because some words were placed
between quotes. So in the texts, by default there were no characters before or after placeholders. This is fixed now
##

So the app's splash screen looks like this:  

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/layout_splash.jpeg)

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/landscape_splash.jpeg)

The pick screen looks like this:

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/layout_pick_init.jpeg)

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/landscape_pick.jpeg)

When picking the story, the user sees the first placeholder:

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/layout_picking_story.jpeg)

When you've made some progress things look like this:

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/landscape_picking.jpeg)

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/layout_pick.jpeg)

When entering the last word, the button text changes:

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/layout_pick_last.jpeg)

When you try to use an empty word, this toast poppes up, also above the keyboard:

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/layout_toast_pick.jpeg)

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/layout_toast_pick_keyboard.jpeg)

When you try to use a word before a story is picked, this toast poppes up:

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/layout_toast_pick_fillinword.jpeg)

The story screen looks like this (note the scroll bar while scrolling):

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/layout_story.jpeg)

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/landscape_story.jpeg)

Note the scrolling bar:

![plaatje](https://github.com/Segouta/christian-pset2/blob/master/doc/layout_story_scrolling.jpeg)

