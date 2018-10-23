#ie.blastfurnace

Tweaks involving the Blast Furnace from Immersive Engineering.

## add
*tweak.ie.blastfurnace.add([stack](/arguments/stack) output, [stack](/arguments/stack) input, [integer](/arguments/integer) time);*  
*tweak.ie.blastfurnace.add([stack](/arguments/stack) output, [stack](/arguments/stack) input, [integer](/arguments/integer) time, [stack](/arguments/stack) slag);*  
*tweak.ie.blastfurnace.add([stack](/arguments/stack) output, [dict](/arguments/dict) input, [integer](/arguments/integer) time);*  
*tweak.ie.blastfurnace.add([stack](/arguments/stack) output, [dict](/arguments/dict) input, [integer](/arguments/integer) time, [stack](/arguments/stack) slag);*

Adds a new recipe to the blast furnace.
```python
#adds recipe where stone turns into diamonds and produces coal as slag, taking 1000 ticks
tweak.ie.blastfurnace.add(<minecraft:diamond>, <minecraft:stone>, 1000, <minecraft:coal>);
```
<br>

---
## remove
*tweak.ie.blastfurnace.remove([stack](/arguments/stack) output);*  
*tweak.ie.blastfurnace.remove([stackList](/arguments/stacklist) outputs);*  
*tweak.ie.blastfurnace.remove([all](/arguments/all) all);*

Removes all recipes from the blast furnace that have the provided output.
```python
#removes a recipe to make coal from the blast furnace
tweak.ie.blastfurnace.remove(<minecraft:coal>);

#removes all recipes from the blast furnace
tweak.ie.blastfurnace.remove(*);
```
<br>

---
## addFuel
*tweak.ie.blastfurnace.addFuel([stack](/arguments/stack) fuel, [integer](/arguments/integer) burnTime);*  
*tweak.ie.blastfurnace.addFuel([dict](/arguments/dict) fuel, [integer](/arguments/integer) burnTime);*

Adds a new fuel to the blast furnace.
```python
#adds diamonds as a fuel type that burn for 1000 ticks
tweak.ie.blastfurnace.addFuel(<minecraft:diamond>, 1000);
```
<br>

---
## removeFuel
*tweak.ie.blastfurnace.removeFuel([stack](/arguments/stack) fuel);*  
*tweak.ie.blastfurnace.removeFuel([stackList](/arguments/stacklist) fuels);*  
*tweak.ie.blastfurnace.removeFuel([all](/arguments/all) all);*

Removes any matching fuels from the blast furnace.
```python
#removes a charcoal as a fuel from the blast furnace
tweak.ie.blastfurnace.removeFuel(<minecraft:charcoal>);

#removes all fuels from the blast furnace
tweak.ie.blastfurnace.removeFuel(*);
```