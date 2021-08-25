package net.darkhax.effecttooltips.addons.crafttweaker.expanded;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.events.CTEventManager;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;

import net.darkhax.effecttooltips.event.EffectTooltipEvent.SpecificEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.loading.FMLEnvironment;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.potion.MCPotionEffect")
public class ExpandEffect {
    
    @ZenCodeType.Method
    public static Effect addTooltipHead (Effect self, MCTextComponent tooltipEntry) {
        
        return addTooltip(self, EventPriority.HIGH, tooltipEntry);
    }
    
    @ZenCodeType.Method
    public static Effect addTooltipBody (Effect self, MCTextComponent tooltipEntry) {
        
        return addTooltip(self, EventPriority.NORMAL, tooltipEntry);
    }
    
    @ZenCodeType.Method
    public static Effect addTooltipTail (Effect self, MCTextComponent tooltipEntry) {
        
        return addTooltip(self, EventPriority.LOW, tooltipEntry);
    }
    
    private static Effect addTooltip (Effect effect, EventPriority priority, MCTextComponent tooltipEntry) {
        
        if (FMLEnvironment.dist.isClient()) {
            
            CTEventManager.register(SpecificEffect.class, priority, e -> {
                
                if (e.getEffect().getEffect() == effect) {
                    
                    e.getTooltips().add(tooltipEntry.getInternal());
                }
            });
        }
        
        return effect;
    }
}