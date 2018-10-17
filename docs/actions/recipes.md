#recipes

Tweaks involving crafting table recipes.

## remove

*tweak.recipes#remove([String](/arguments/string) name)*  
*tweak.recipes#remove([StringList](/arguments/stringlist) names)*

Disable the specific recipe that matches the provided name
```java
//remove the default crafting recipe for a torch
tweak.recipes#remove("minecraft:torch");
```
<br>

*tweak.recipes#remove([Stack](/arguments/stack) output)*  
*tweak.recipes#remove([StackList](/arguments/stacklist) outputs)*

Disable all crafting recipes that match the provided output
```java
//remove all crafting recipes for torches
tweak.recipes#remove(<minecraft:torch>);
```
<br>

---
## shaped

*tweak.recipes#shaped([String](/arguments/string) name, [Stack](/arguments/stack) output, [Recipe](/arguments/recipe) recipe)*

Adds a new shaped crafting recipe
```java
//adds a shaped recipe that turns 1 stone into diamond
tweak.recipes#shaped("cheat_diamond", <minecraft:diamond>, ([<minecraft:stone>]));
```