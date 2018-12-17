from assg3PinkieMachineAPI import *

#Instruction Memory

IM=[
	LDW(W0,0,0),
	LDW(W1,1,0),
	MUL(W2,W0,W0),
	MUL(W3,W1,W1),
	ADD(W4,W2,W3)
]
