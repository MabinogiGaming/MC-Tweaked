# stack

A stack is a way to represent any item or block, including information such as metadata, item count and nbtcompounds. 

A stack is a type of [ingredient](/arguments/ingredient) and can therefore be used in any method as one.

## Argument
Stack information is split using the `:` modifier, with each split item being referred to as a token.  
A stack can have between 2-5 tokens and the number of tokens determine what information is passed in.

### 2 Tokens - Basic Stack
`<mod:name>`

Creates a stack using the item/block registered by the provided mod and name.
```python
#creates a stack of 1 diamond
<minecraft:diamond>
```

### 3 Tokens - Stack with Meta
`<mod:name:meta>`

*Note : meta must be a parsable integer*

Creates a stack using the item/block registered by the provided mod and name, using the specified meta data.
```python
#creates a stack of specific coloured wool
<minecraft:wool:3>
```

### 4 Tokens - Stack with Meta and Count
`<mod:name:meta:count>`

*Note : meta and count must be parsable integers*

Creates a stack using the item/block registered by the provided mod and name, using the specified meta data and item count.
```python
#creates a stack of 16 diamonds
<minecraft:diamond:0:16>
```

### 5 Tokens - Stack with Meta, Count and NBTCompound
`<mod:name:meta:count:nbt>`

*Note : meta and count must be parsable integers*
*Note : nbt must be a valid nbt string, see the below example*

Creates a stack using the item/block registered by the provided mod and name, using the specified meta data, item count and nbt compound.
```python
#creates a stack of a forge bucket, filled with immersive engineering liquid concrete
<forge:bucketfilled:0:1:{FluidName: "concrete", Amount: 1000}>
```
<br>

---
## Variable

A Stack can also be stored as a variable, the argument can be any of those specified above.	
```python
#creates a variable assigned to a diamond stack
$varStack = stack(<minecraft:diamond>);
```
