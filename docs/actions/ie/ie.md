#ie

Tweaks involving the mod Immersive Engineering.

## disableMultiblock
*tweak.ie.disableMultiblock([string](/arguments/string) name);*  
*tweak.ie.disableMultiblock([stringList](/arguments/stringlist) names);*  
*tweak.ie.disableMultiblock([all](/arguments/all) all);*

Prevents the specified multiblock from forming when hit with an engineers hammer.
```python
#disable the crusher
tweak.ie.disableMultiblock("IE:Crusher");

#disable all multiblocks
tweak.ie.disableMultiblock(*);

#default options
IE:AlloySmelter
IE:ArcFurnace
IE:Assembler
IE:AutoWorkbench
IE:BlastFurnace
IE:BlastFurnaceAdvanced
IE:BottlingMachine
IE:BucketWheel
IE:CokeOven
IE:Crusher
IE:DieselGenerator
IE:Excavator
IE:ExcavatorDemo
IE:Feedthrough
IE:Fermenter
IE:Lightningrod
IE:MetalPress
IE:Mixer
IE:Refinery
IE:SheetmetalTank
IE:Silo
IE:Squeezer
```
