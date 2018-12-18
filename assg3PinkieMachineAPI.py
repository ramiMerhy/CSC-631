#Main Arrays Declaration
Executing = []
ReservationStation = []

#Registers Array (integer+float)
R = [0] * 64

#Pinky Machine Instruction Memory
DM = [4, 2]

#Pinky Machine Cores units
Core1 = {'alu_int': 0, 'mult_int': 0, 'alu_double': 0,  'mult_double': 0}
Core2 = {'alu_int': 0, 'mult_int': 0, 'alu_double': 0,  'mult_double': 0}

#Pinky Machine Registers Condition Flags
N_flag = 0
Z_flag = 0

#Pinky Machine Operation Code Definitions
OP_HALT = 0
OP_LDW = 1
OP_STW = 2
OP_ADD = 3
OP_SUB = 4
OP_AND = 5
OP_OR = 6
OP_XOR = 7
OP_ADDS = 8
OP_SUBS = 9
OP_SL = 10
OP_SR = 11
OP_LDW_DOUBLE = 12
OP_STWD_DOUBLE = 13
OP_ADD_DOUBLE = 14
OP_SUB_DOUBLE = 15
OP_MUL = 16
OP_MUL_DOUBLE = 17
OP_DIV = 18
OP_DIV_DOUBLE = 19

#Pinky Machine IR Instr Latencies
latency_allOther = 1
latency_loadStore = 3
latency_float_adderSubtractor = 3
latency_float_multiplier = 6
latency_float_divider = 24

#Pinky Machine Integer Register Definitions
W0 = 0
W1 = 1
W2 = 2
W3 = 3
W4 = 4
W5 = 5
W6 = 6
W7 = 7
W8 = 8
W9 = 9
W10 = 10
W11 = 11
W12 = 12
W13 = 13
W14 = 14
W15 = 15
W16 = 16
W17 = 17
W18 = 18
W19 = 19
W20 = 20
W21 = 21
W22 = 22
W23 = 23
W24 = 24
W25 = 25
W26 = 26
W27 = 27
W28 = 28
W29 = 29
W30 = 30
W31 = 31

#Pinky Machine Float Register Definitions
D0 = 0
D1 = 1
D2 = 2
D3 = 3
D4 = 4
D5 = 5
D6 = 6
D7 = 7
D8 = 8
D9 = 9
D10 = 10
D11 = 11
D12 = 12
D13 = 13
D14 = 14
D15 = 15
D16 = 16
D17 = 17
D18 = 18
D19 = 19
D20 = 20
D21 = 21
D22 = 22
D23 = 23
D24 = 24
D25 = 25
D26 = 26
D27 = 27
D28 = 28
D29 = 29
D30 = 30
D31 = 31

#Pinky Machine Latencies
def setLatencies(allOther, loadStore, float_adderSubtractor, float_multiplier, float_divider):
	latency_allOther = allOther
	latency_loadStore = loadStore
	latency_float_adderSubtractor = float_adderSubtractor
	latency_float_multiplier = float_multiplier
	latency_float_divider = float_divider
	
#Picky Machine Assembly code macros
def HALT():
	return OP_HALT << 20

def LDW(DestReg, BaseReg, Address):
	return (OP_LDW<<20)| (Address<<10) | (BaseReg<<5) | DestReg

def STW(SourceReg, BaseReg, Address):
	return (OP_STW<<20)| (Address<<10) | (BaseReg<<5) | SourceReg

def ADD(DestReg, SourceReg1, SourceReg2):
	return (OP_ADD<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg
	
def SUB(DestReg, SourceReg1, SourceReg2):
	return (OP_SUB<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg

def AND(DestReg, SourceReg1, SourceReg2):
	return (OP_AND<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg
	
def OR(DestReg, SourceReg1, SourceReg2):
	return (OP_OR<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg

def XOR(DestReg, SourceReg1, SourceReg2):
	return (OP_XOR<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg
	
def ADDS(DestReg, SourceReg1, SourceReg2):
	return (OP_ADDS<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg
	
def SUBS(DestReg, SourceReg1, SourceReg2):
	return (OP_SUBS<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg	

def SL(DestReg, SourceReg, ShiftAmt):
	return (OP_SL<<20)| (SourceReg<<15) | (ShiftAmt<<10) | DestReg
	
def SR(DestReg, SourceReg, ShiftAmt):
	return (OP_SR<<20)| (SourceReg<<15) | (ShiftAmt<<5) | DestReg	
	
def LDW_DOUBLE(DestReg, Address, BaseReg):
	return (OP_LDW_DOUBLE<<20)| (Address<<10) | (BaseReg<<5) | DestReg
	
def STW_DOUBLE(SourceReg, Address, BaseReg):
	return (OP_STWD_DOUBLE<<20)| (Address<<10) | (BaseReg<<5) | SourceReg

def ADD_DOUBLE(DestReg, SourceReg1, SourceReg2):
	return (OP_ADD_DOUBLE<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg
	
def SUB_DOUBLE(DestReg, SourceReg1, SourceReg2):
	return (OP_SUB_DOUBLE<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg
	
def MUL(DestReg, SourceReg1, SourceReg2):
	return (OP_MUL<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg
	
def MUL_DOUBLE(DestReg, SourceReg1, SourceReg2):
	return (OP_MUL_DOUBLE<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg
	
def DIV(DestReg, SourceReg1, SourceReg2):
	return (OP_DIV<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg
	
def DIV_DOUBLE(DestReg, SourceReg1, SourceReg2):
	return (OP_DIV_DOUBLE<<20)| (SourceReg1<<15) | (SourceReg2<<5) | DestReg

#Pinky Machine Functions
#Load a value from memory
def LOAD(iDestReg, iAddress, iBaseReg):
	R[iDestReg] = DM[iAddress + iBaseReg]
	
def LOAD_DOUBLE(iDestReg, iAddress, iBaseReg):
	R[iDestReg + 32] = float(DM[iAddress + iBaseReg])

#Store a value in memory
def STORE(iAddress, iBaseReg, iSourceReg):
	DM[iAddress + iBaseReg] = R[iSourceReg]

def STORE_DOUBLE(iAddress, iBaseReg, iSourceReg):
	DM[iAddress + iBaseReg] = R[iSourceReg + 32]

#Shift left the content of a register in the register file and store the results
def SLF(iDestReg, iSourceReg, iShiftAmt):
	R[iDestReg] = R[iSourceReg] << iShiftAmt
	
#Shift right the content of a register in the register file and store the results	
def SRF(iDestReg, iSourceReg, iShiftAmt):
	R[iDestReg] = R[iSourceReg] >> iShiftAmt

# Add The contents of the two registers in the register file and store the results
def ADDf(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg] = R[iSourceReg1] + R[iSourceReg2]
	
def ADD_DOUBLE_FUNCTION(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg + 32] = R[iSourceReg1 + 32] + R[iSourceReg2 + 32]

#Sub The contents of the two registers in the register file and store the result
def SUBf(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg] = R[iSourceReg1] - R[iSourceReg2]

def SUB_DOUBLE_FUNCTION(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg + 32] = R[iSourceReg1 + 32] - R[iSourceReg2 + 32]

#Multiply The contents of the two registers in the register file and store the result
def MULTf(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg] = R[iSourceReg1] * R[iSourceReg2]

def MULTDf(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg + 32] = R[iSourceReg1 + 32]  * R[iSourceReg2+32]

#Divide The contents of the two registers in the register file and store the result	
def DIVf(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg] = R[iSourceReg2] / R[iSourceReg1]

def DIV_DOUBLE_FUNCTION(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg + 32] = float(R[iSourceReg2 + 32]) / R[iSourceReg1 + 32]

#Logical 'and' the contents of the two registers in the register file and store the results
def AND_FUNCTION(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg] = R[iSourceReg1] & R[iSourceReg2]

#Logical 'or' the contents of the two registers in the register file and store the results   
def OR_FUNCTION(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg] = R[iSourceReg1] | R[iSourceReg2]

#Logical 'xor' the contents of the two registers in the register file and store the results   
def XOR_FUNCTION(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg] = R[iSourceReg1] ^ R[iSourceReg2]

#Add The contents of a register and an immediate and stores the results Set the condition flags    	
def ADDS_FUNCTION(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg] = R[iSourceReg1] + R[iSourceReg2]
	Z_flag = 1 if R[iDestReg] == 0 else 0
	N_flag = 1 if R[iDestReg] < 0 else 0

#Add The contents of a register and an immediate and stores the results Set the condition flags    	
def SUBS_FUNCTION(iDestReg, iSourceReg1, iSourceReg2):
	R[iDestReg] = R[iSourceReg1] - R[iSourceReg2]
	Z_flag = 1 if R[iDestReg] == 0 else 0
	N_flag = 1 if R[iDestReg] < 0 else 0

#Picky Machine Decode Unit
def decode_unit(IR):
	iOpcode = IR >> 20
	data = IR & (2**20 - 1)
	iDestReg, iSourceReg1, iSourceReg2, iAddress, iBaseReg, iShiftAmt = (-1,) * 6
		
	if iOpcode == OP_LDW or iOpcode == OP_LDW_DOUBLE:
		iBaseReg = (data >> 5) & 31
		iAddress = (data >> 10) & 31
		iDestReg = data & 31
		
	elif iOpcode == OP_STW or iOpcode == OP_STWD_DOUBLE:
		iBaseReg = (data >> 5) &31
		iAddress = (data >> 10) & 31
		iSourceReg1 = data & 31 
	
	elif iOpcode == OP_ADD or iOpcode == OP_SUB or iOpcode == OP_AND or iOpcode == OP_OR or iOpcode == OP_XOR or iOpcode == OP_ADD_DOUBLE or iOpcode == OP_SUB_DOUBLE or iOpcode == OP_MUL or iOpcode == OP_MUL_DOUBLE or iOpcode == OP_DIV or iOpcode == OP_DIV_DOUBLE or iOpcode == OP_ADDS or iOpcode == OP_SUBS:
		iDestReg = data & 31 
		iSourceReg1 = (data >> 5) & 31
		iSourceReg2 = (data >> 15) &31
	
	elif iOpcode == OP_SL or iOpcode == OP_SR:
		iDestReg = data & 31
		iShiftAmt = (data >> 10) & 31
		iSourceReg1 = (data >>15) & 31

	return {'iOpcode':iOpcode, 'iDestReg':iDestReg, 'iSourceReg1':iSourceReg1, 'iSourceReg2':iSourceReg2, 'iAddress':iAddress, 'iBaseReg':iBaseReg, 'iShiftAmt':iShiftAmt, 'iUnit' : ''}