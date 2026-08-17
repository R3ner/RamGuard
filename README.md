# RamGuard

RamGuard is a client-side Minecraft Forge mod for 1.12.2 that ensures your players have enough RAM allocated to run the game smoothly. 

If a player launches the game with insufficient RAM, a customizable warning screen will intercept the Main Menu, prompting them to allocate more memory or quit the game.

## Features
* **Configurable Thresholds**: Set your modpack's minimum required RAM and recommended RAM.
* **Fully Customizable Text**: All warning texts, titles, and buttons can be modified and translated directly in the configuration file.
* **Memory Conversion**: The mod automatically replaces placeholders (`%MB%` and `%GB%`) to display both Megabytes and Gigabytes format.
* **Enforced Limits**: You can configure `forceCrashOnLowRam = true` to force the player to quit if the minimum threshold is not met.
* **Tutorial Link**: Include a helpful URL guiding players on how to allocate more RAM in their launcher.

## Configuration
Upon first launch, a `ramguard.cfg` file will be generated in your `config` folder.
You can use `%MB%` and `%GB%` in the text fields to dynamically inject the RAM values into your custom messages.

## Installation
Drop the compiled `.jar` file into your `mods/` folder. It is safe to include in server modpacks as it is marked as a client-side only mod.

## Credits
Created by Rener.
