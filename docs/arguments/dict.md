# dict

A dict is used to represent all items/blocks referenced by the ore dictionary.

A dict is a type of [ingredient](/arguments/ingredient/) and can therefore be used in any method as one.

## Argument
`<orename>`

A dict is created by simply specifying the ore name with no tokens.
```python
#specifys all items registered under gemDiamond, in this case a standard diamond
<gemDiamond>
```
<br>

---
## Variable

A dict can also be stored as a variable, the argument is the same as specified above.	
```python
#creates a dict assigned to Diamonds
$varDict = dict(<gemDiamond>);
```
