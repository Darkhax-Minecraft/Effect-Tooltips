// The first step to adding a tooltip to an effect is getting a reference to 
// that effect. In CraftTweaker this can be done using the effect bracket 
// handler which will look up an effect ID in the game registry. In this case 
// we are adding a tooltip to the luck effect. The next step is to decide where
// the tooltip should go. You have three options, head(top), body(middle), and 
// footer(bottom).
<effect:minecraft:luck>.addTooltipHead("This line is at the top.");
<effect:minecraft:luck>.addTooltipBody("This line is in the body.");
<effect:minecraft:luck>.addTooltipFooter("This line is at the bottom.");

// In a future update scripts will have significantly more control over these 
// tooltips. The ability to remove entries, dynamic tooltips, access to game 
// context, and more are planned.