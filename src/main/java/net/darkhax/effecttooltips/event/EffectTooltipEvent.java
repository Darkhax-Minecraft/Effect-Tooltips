package net.darkhax.effecttooltips.event;

import java.util.List;

import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class EffectTooltipEvent extends PlayerEvent {
    
    private final List<ITextComponent> tooltips;
    private final DisplayEffectsScreen<?> screen;
    private final ITooltipFlag flags;
    
    public EffectTooltipEvent(final DisplayEffectsScreen<?> screen, List<ITextComponent> tooltips, PlayerEntity player, ITooltipFlag flags) {
        
        super(player);
        this.tooltips = tooltips;
        this.screen = screen;
        this.flags = flags;
    }
    
    public List<ITextComponent> getTooltips () {
        
        return this.tooltips;
    }
    
    public DisplayEffectsScreen<?> getScreen () {
        
        return this.screen;
    }
    
    public ITooltipFlag getTooltipFlags () {
        
        return this.flags;
    }
    
    /**
     * This event is fired on the Forge event bus to compute the tooltips for an individual
     * effect. If more than one effect is being hovered their tooltips will be combined. When
     * cancelled the tooltip computed for the effect will not be used.
     */
    @Cancelable
    public static class SpecificEffect extends EffectTooltipEvent {
        
        private final EffectInstance effect;
        
        public SpecificEffect(EffectInstance effect, DisplayEffectsScreen<?> screen, List<ITextComponent> tooltips, PlayerEntity player, ITooltipFlag flags) {
            
            super(screen, tooltips, player, flags);
            this.effect = effect;
        }
        
        public EffectInstance getEffect () {
            
            return this.effect;
        }
    }
    
    /**
     * This event is fired on the Forge event bus after the tooltips for each individual
     * hovered effect has been computed. This event could be used for adding info that is not
     * tied to one specific enchantment or making broader alterations to the tooltip. Canceling
     * this event will prevent the tooltip from being displayed.
     */
    @Cancelable
    public static class AllEffects extends EffectTooltipEvent {
        
        /**
         * All of the effects that are being hovered over.
         */
        private final List<EffectInstance> effects;
        
        public AllEffects(List<EffectInstance> effects, DisplayEffectsScreen<?> screen, List<ITextComponent> tooltips, PlayerEntity player, ITooltipFlag flags) {
            
            super(screen, tooltips, player, flags);
            this.effects = effects;
        }
        
        /**
         * Gets all of the hovered effects.
         * 
         * @return A list of hovered effects.
         */
        public List<EffectInstance> getEffects () {
            
            return this.effects;
        }
    }
}