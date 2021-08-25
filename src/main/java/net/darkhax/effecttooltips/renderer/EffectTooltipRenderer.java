package net.darkhax.effecttooltips.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import net.darkhax.effecttooltips.EffectTooltips;
import net.darkhax.effecttooltips.event.EffectTooltipEvent;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = EffectTooltips.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class EffectTooltipRenderer {
    
    private static final int HUD_WIDTH = 120;
    private static final int HUD_HEIGHT = 33;
    
    private static List<EffectInstance> getHoveredEffects (int guiTop, int guiLeft, int mouseX, int mouseY, Collection<EffectInstance> activeEffects) {
        
        final List<EffectInstance> hoveredEffects = new ArrayList<>();
        
        if (!activeEffects.isEmpty()) {
            
            final int hudX = guiLeft - 124;
            final int effectHudHeight = activeEffects.size() > 5 ? 132 / (activeEffects.size() - 1) : HUD_HEIGHT;
            
            int y = guiTop;
            
            for (final EffectInstance effect : activeEffects.stream().filter(EffectInstance::shouldRender).sorted().collect(Collectors.toList())) {
                
                final boolean hovered = mouseX >= hudX && mouseY >= y && mouseX < hudX + HUD_WIDTH && mouseY < y + HUD_HEIGHT;
                
                if (effect.shouldRenderInvText() && hovered) {
                    
                    hoveredEffects.add(effect);
                }
                
                y += effectHudHeight;
            }
        }
        
        return hoveredEffects;
    }
    
    @SubscribeEvent
    public static void onScreenRender (DrawScreenEvent.Post event) {
        
        if (event.getGui() instanceof DisplayEffectsScreen) {
            
            final DisplayEffectsScreen<?> screen = (DisplayEffectsScreen<?>) event.getGui();
            final PlayerEntity player = screen.getMinecraft().player;
            final ITooltipFlag flags = screen.getMinecraft().options.advancedItemTooltips ? TooltipFlags.ADVANCED : TooltipFlags.NORMAL;
            final int mouseX = event.getMouseX();
            final int mouseY = event.getMouseY();
            final List<EffectInstance> hoveredEffects = getHoveredEffects(screen.getGuiTop(), screen.getGuiLeft(), mouseX, mouseY, player.getActiveEffects());
            final List<ITextComponent> tooltip = new LinkedList<>();
            
            for (final EffectInstance effect : hoveredEffects) {
                
                final List<ITextComponent> effectTooltip = new LinkedList<>();
                final EffectTooltipEvent.SpecificEffect specificEvent = new EffectTooltipEvent.SpecificEffect(effect, screen, effectTooltip, player, flags);
                
                if (!MinecraftForge.EVENT_BUS.post(specificEvent)) {
                    
                    if (!tooltip.isEmpty()) {
                        
                        tooltip.add(new StringTextComponent(" "));
                    }
                    
                    tooltip.addAll(effectTooltip);
                }
            }
            
            final EffectTooltipEvent.AllEffects tooltipEvent = new EffectTooltipEvent.AllEffects(hoveredEffects, screen, tooltip, player, flags);
            
            if (!MinecraftForge.EVENT_BUS.post(tooltipEvent) && !tooltipEvent.getTooltips().isEmpty()) {
                
                screen.renderComponentTooltip(event.getMatrixStack(), tooltipEvent.getTooltips(), mouseX, mouseY);
            }
        }
    }
}