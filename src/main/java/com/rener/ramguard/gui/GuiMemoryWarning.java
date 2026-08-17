package com.rener.ramguard.gui;

import com.rener.ramguard.ModConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;

public class GuiMemoryWarning extends GuiScreen {

    private final long allocatedRamMB;
    private final int headerColor;
    private final int textColor;

    public GuiMemoryWarning(long allocatedRamMB) {
        this.allocatedRamMB = allocatedRamMB;
        
        // Parse hex colors from config
        this.headerColor = parseHexColor(ModConfig.headerColorHex, 0xFF5555);
        this.textColor = parseHexColor(ModConfig.textColorHex, 0xFFFFFF);
    }

    private int parseHexColor(String hex, int defaultColor) {
        try {
            if (hex.startsWith("0x") || hex.startsWith("0X")) {
                hex = hex.substring(2);
            } else if (hex.startsWith("#")) {
                hex = hex.substring(1);
            }
            return (int) Long.parseLong(hex, 16);
        } catch (NumberFormatException e) {
            return defaultColor;
        }
    }

    private String formatText(String template, long mb) {
        double gb = mb / 1024.0;
        String gbString = String.format(Locale.US, "%.1f", gb);
        return template.replace("%MB%", String.valueOf(mb)).replace("%GB%", gbString);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();

        int centerX = this.width / 2;
        int bottomY = this.height / 2 + 40;

        // Button 1: Tutorial
        this.buttonList.add(new GuiButton(0, centerX - 100, bottomY, 200, 20, ModConfig.textConfig.buttonTutorial));

        // Button 2: Continue (Disabled if forceCrashOnLowRam is true)
        GuiButton continueBtn = new GuiButton(1, centerX - 100, bottomY + 25, 200, 20, ModConfig.textConfig.buttonContinue);
        continueBtn.enabled = !ModConfig.forceCrashOnLowRam;
        this.buttonList.add(continueBtn);

        // Button 3: Quit Game
        this.buttonList.add(new GuiButton(2, centerX - 100, bottomY + 50, 200, 20, ModConfig.textConfig.buttonQuit));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                // Open tutorial URL
                try {
                    Desktop.getDesktop().browse(new URI(ModConfig.tutorialUrl));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                // Proceed to main menu if allowed
                if (!ModConfig.forceCrashOnLowRam) {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                }
                break;
            case 2:
                // Quit the game
                this.mc.shutdown();
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Draw dark background
        this.drawDefaultBackground();

        // Warning Title
        String title = ModConfig.textConfig.windowTitle;
        this.drawCenteredString(this.fontRenderer, title, this.width / 2, this.height / 2 - 70, this.headerColor);

        // Information Texts
        String info1 = formatText(ModConfig.textConfig.infoLine1, ModConfig.requiredRamMB);
        String info2 = formatText(ModConfig.textConfig.infoLine2, this.allocatedRamMB);
        String info3 = formatText(ModConfig.textConfig.infoLine3, ModConfig.recommendedRamMB);
        
        this.drawCenteredString(this.fontRenderer, info1, this.width / 2, this.height / 2 - 40, this.textColor);
        this.drawCenteredString(this.fontRenderer, info2, this.width / 2, this.height / 2 - 25, this.textColor);
        this.drawCenteredString(this.fontRenderer, info3, this.width / 2, this.height / 2 - 10, this.textColor);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
