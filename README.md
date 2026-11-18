In most JetBrains IDE's the code is aligned on the very left. Only in Distraction Free Mode and Zen Mode the code is centered. Since there seems to be no setting, with this small live plugin the code can be centered also in the default view mode. 

### How to apply 
1. Install [LivePlugin](https://plugins.jetbrains.com/plugin/7282-liveplugin) by Dmitry Kandalov via the Marketplace (File -> Settings -> Plugins). Restart the IDE if it asks you
2. In the vertical bar on the right side of the screen, a new plugin tool icon should appear
3. Click it and select the + to create a new Live Plugin
4. Select the `Kotlin Plugin` option and name it something like `center-editor`
5. Open the `center-editor` folder and replace the code inside the `plugin.kts` file with the one from this repo
6. Click the green run button. It will take a second to run the plugin and apply the changes

Code in the editor should now be centered. If the window is too small or two files are open next to each other, it will use the default left alignment.

![setup_structure](setup.png)
