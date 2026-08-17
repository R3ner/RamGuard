package com.rener.ramguard;

import com.rener.ramguard.gui.GuiMemoryWarning;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = RamGuard.MODID)
public class ClientEventHandler {

    // Bandera para asegurar que la comprobación solo se realice una vez por sesión
    private static boolean hasCheckedRam = false;

    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event) {
        // Solo comprobar cuando se intenta abrir el menú principal por primera vez
        if (event.getGui() instanceof GuiMainMenu && !hasCheckedRam) {
            hasCheckedRam = true;
            
            // Obtener memoria máxima asignada en MB
            long maxMemoryMB = Runtime.getRuntime().maxMemory() / (1024L * 1024L);
            
            // Si la memoria es menor a la requerida, interceptamos y mostramos nuestra GUI
            if (maxMemoryMB < ModConfig.requiredRamMB) {
                event.setGui(new GuiMemoryWarning(maxMemoryMB));
            }
        }
    }
}
