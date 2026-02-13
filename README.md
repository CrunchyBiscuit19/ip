# Nemo

Nemo is a desktop program used for managing personal tasks. It functions as an interactive chatbot with elements of the Command Line Interface (CLI) presented with a Graphical User Interface (GUI).

To get started, read the [user guide](https://crunchybiscuit19.github.io/ip/).

## Setting up in Intellij

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
2. Configure the project to use **JDK 17** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk). 
In the same dialog, set the **Project language level** field to the `SDK default` option.
&nbsp;
**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Credits

Reused code / assets have been credited wherever they have been used within this repository, in both the codebase and the user guide.
