# Pre-work - *ToDone*

**ToDone** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Ray Megal**

Time spent: **12** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] Using Toolbar-s instead of buttons
* [X] Added a splash screen. Not sure I would use this in an app that didn't have a significant startup cost.

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://cloud.githubusercontent.com/assets/213904/22850554/63d283f0-efaf-11e6-82f2-d460f9729c20.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

* Storing multi-line items in a TXT file was troublesome since loading a multi-line task would result in multiple tasks being created in the ListView. I changed to storing a serialized version of the underlying ArrayList as JSON to resolve this.
* I'm working in a Windows 10 environment with Hyper-V enabled so I followed this to dual-boot into an environment that allowed me to use the Genymotion emulator: http://www.hanselman.com/blog/SwitchEasilyBetweenVirtualBoxAndHyperVWithABCDEditBootEntryInWindows81.aspx
* The [ideavim](https://github.com/JetBrains/ideavim) plugin allowed me to use vim keybindings within Android Studio. Very handy.

## Updates
* 20170201: Now storing data in database using DBFlow/SQLite. The look/feel/functionality is identical.

## License

    Copyright [2017] [Ray Megal]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
