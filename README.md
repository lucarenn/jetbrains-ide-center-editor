In most JetBrains IDE's the code is aligned on the very left. Only in distraction free and Zen mode the code is centered. Since there seems to be no setting, with this small live plugin the code can be centered also in the default view mode. 

### How to apply 
1. Install [LivePlugin](https://plugins.jetbrains.com/plugin/7282-liveplugin) by Dmitry Kandalov via the Marketplace (File -> Settings -> Plguins), restart the IDE if it asks you.
2. In the vertical bar on the right side of the screen, a new plugin tool icon should appear
3. Click it and select the + to create a new Live Plugin
4. Select Kotlin, name it something like `center-editor`.
5. Open the `center-editor` folder and replace the code inside the plugin.kts file with the one from this repo
6. Click the run icon above. It will take a second to run the plugin and apply the changes

![setup_structure](setup.png)
