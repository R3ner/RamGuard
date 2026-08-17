package com.rener.ramguard;

import com.rener.ramguard.gui.GuiMemoryWarning;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = RamGuard.MODID)
public class ClientEventHandler {

    // Flag to ensure the check is only performed once per session
    private static boolean hasCheckedRam = false;

    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event) {
        // Only check when the main menu is first attempted to be opened
        if (event.getGui() instanceof GuiMainMenu && !hasCheckedRam) {
            hasCheckedRam = true;

            // Get maximum allocated memory in MB
            long maxMemoryMB = Runtime.getRuntime().maxMemory() / (1024L * 1024L);

            // If memory is less than required, we intercept and show our GUI
            if (maxMemoryMB < ModConfig.requiredRamMB) {
                event.setGui(new GuiMemoryWarning(maxMemoryMB));
            }
        }
    }
}
