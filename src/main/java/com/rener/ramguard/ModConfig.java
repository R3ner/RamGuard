package com.rener.ramguard;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = RamGuard.MODID)
public class ModConfig {

    @Config.Comment("Minimum required RAM in MB to play without warnings")
    public static int requiredRamMB = 6144;

    @Config.Comment("Recommended RAM in MB for an optimal experience")
    public static int recommendedRamMB = 8192;

    @Config.Comment("If true, the user won't be able to bypass the warning and will be forced to quit the game")
    public static boolean forceCrashOnLowRam = false;

    @Config.Comment("URL to the tutorial on how to allocate more RAM to Minecraft")
    public static String tutorialUrl = "https://example.com/ram-guide";

    @Config.Comment("Hexadecimal color for the warning title (Example: 0xFF5555)")
    public static String headerColorHex = "0xFF5555";

    @Config.Comment("Hexadecimal color for the warning text (Example: 0xFFFFFF)")
    public static String textColorHex = "0xFFFFFF";

    @Config.Comment({
            "Text configurations for the warning screen.",
            "You can use %MB% for the Megabytes value and %GB% for the Gigabytes value."
    })
    @Config.Name("Text Translation")
    public static TextConfig textConfig = new TextConfig();

    public static class TextConfig {
        @Config.Comment("Warning window title")
        public String windowTitle = "Warning: Insufficient RAM!";

        @Config.Comment("First line of the warning information")
        public String infoLine1 = "The game requires a minimum of %MB% MB (About %GB% GB) to run properly.";

        @Config.Comment("Second line of the warning information")
        public String infoLine2 = "You currently have only %MB% MB (About %GB% GB) of RAM allocated.";

        @Config.Comment("Third line of the warning information")
        public String infoLine3 = "We recommend allocating %MB% MB (About %GB% GB) for an optimal experience.";

        @Config.Comment("Text for the tutorial button")
        public String buttonTutorial = "How to allocate more RAM";

        @Config.Comment("Text for the continue button")
        public String buttonContinue = "Continue Anyway";

        @Config.Comment("Text for the quit game button")
        public String buttonQuit = "Quit Game";
    }

    @Mod.EventBusSubscriber(modid = RamGuard.MODID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(RamGuard.MODID)) {
                ConfigManager.sync(RamGuard.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
