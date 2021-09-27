# CSCI6461---Computer-Simulator

DESIGN NOTES AND USER GUIDE FOR CSCI 6461 PROJECT

GROUP 4 MEMBERS:  Zabet, William B., and Ajayi, Oluwasegun
## INTRODUCTION: 
This document is split into two which are the design notes and the user
guide. The design note contains information about the project structure, the code design
pattern, how the functions relate and what the major functions do. The user guide on the
other hand contains information that guides the user on how to make use of the system.

JDK VERSION: JDK 15 or JRE 1.15

EXTERNAL FILES: The IPL function is to read from an external file. For this project, the
external file is named program.txt and it is in the same folder (i.e. src folder) as the source
files.
 
 LOADING THE FILE: The project has a file already created called “program.txt” in the
src/csci6461Project folder. In this file the instructions that are to be read from file are
written in there and we can then run the project. The project also had two other files
prog1.txt and prog2.txt. The prog1.txt is used when saving a list of 20 numbers while the
prog2.txt is used when calling search of the numbers that were saved ahead.

INSTALLATION: A jar file has been built and it is in the CSCI6461-Project folder. The jar file is
titled CSCI6461projectStep3.jar.

## DESIGN NOTES
This project was built with java programming language using the version 15 JDK. There are
four classes that make up the project and they are the MainGUI, the BackEnd, the Cache
and the Instructions. All the classes have their different contributions to the overall
development of the project. In these classes we have different functions that are duly
explained. Apart from the classes we have some other txt files prog1.txt, prog2.txt and
program.txt. All these as well are explained in the section below.

#### THE MAINGUI  
This class has the textfields, buttons and checkboxes used on the panel. It
contains the code that manages the user interaction from the GUI. The class extends the
JFrame class that helps with the GUI. We also declare the BackEnd class object that handles
some of the functionalities. A better explanation is in the backend section. When we run the
application, the user is presented with the GUI defined by this class. We first describe all the
fields in this class below:

- A. The textField_gpr0, textField_gpr1, textField_gpr2, textField_gpr3, textField_ixr1,
textField_ixr2, textField_ixr3, textField_pc, textField_mar, textField_mbr,
textField_ir, textField_mfr, and textField_cc: These textfields are where the binary
instructions go.
- B. The op0box, op1box, op2box, op3box, op4box, op5box, gpr0box, gpr1box, ixr0box,
ixr1box, i0box, address0box, address1box, address2box, address3box, and
address4box: The checkboxes are available for the user to select their own
instruction.
- C. The tgl_run, and tgl_ss: These toggle buttons help to choose between running all the
instructions (tgl_run is on) chosen by the user or single instruction (tgl_ss is on)
- D. The tgl_load: This toggle button helps with a simple ldr (opcode 01) by checking off
the necessary boxes.
- E. The tgl_store: This toggle button helps with a simple str (opcode 01) by checking off
the necessary boxes.
- F. The consolePrinter: This textarea displays printed output.
- G. The textField_keyboard: This textfield displays the keyboard input
- H. The btn_p1: This initializes the program 1.
- I. The btn_p2: This button initializes the program 2.
- J. The btn_read: This reads in the list of numbers in program 1.
- K. The btn_find: This reads in a word to be searched for in program 1.
- L. The input20: This is a variable that has the string array of all numbers in btn_read.
- M. The find_program1: This is a string of word to find from btn_find.
- N. The keyboard: This contains the list of all numbers in the keyboard.
- O. The btn_search: This is the button to find word in program 2.

The methods implemented in this class are described below:
- A. The main method: This method handles the launching of the application
- B. The checkedBoxes method: This is where we convert the checkboxes input from the
user to 0s and 1s and concatenate it to one string. If it is checked, you get a 1 and
not checked sets it as a 0.
- C. The convert_to_16bits method: This takes a string of instructions (the ones gotten
from the result of the checkedBoxes method) and converts to a short (16 bits).
- D. The MainGUI method: This is the constructor that initializes the application.
- E. The initialize method: This is the method that contains all the swing components (i.e.
the buttons, textfields, checkboxes etc.) and the event listeners for many of the
components. Every button has its event listener (i.e. the actionPerformed methods)
that executes some function.

There are other methods in this class that are not methods on their own. Many of them are
event listeners and button actions and they are:
- A. Action performed for Load button: When this button is clicked then the user’s input
from the checkboxes are stored within the contents of the load’s button respective
textfield.
- B. Action performed for Store toggle: When pressed the load toggle gets disabled. Since
the opcode for a an STR = 000010, that means op4box must be checked
automatically if it is not. Furthermore, the others are unchecked if they were
checked before. These opboxes are then disabled to prevent the user from
tampering with it.
- C. Action performed for Load toggle: When pressed the store toggle gets disabled.
Since the opcode for a an STR = 0000001, that means op5box must be checked
automatically if it is not.
- D. IPL Action Listener: This calls the BackEnd function iPL which reads from the
program.txt and performs several instructions.
- E. Action performed for RUN and SS toggle buttons: The application runs continually
until something halts it when SS is toggled off. However, when toggled on it
completes one cycle of the execution.
- F. Action performed for Read: Here we can store 20 numbers.
- G. Action performed for Find: The find button gets enabled once 20 numbers have been
successfully entered and read into the system. The find searches for a new number
that is entered from the list of numbers entered before.
- H. Action performed for program 1: This starts program 1
- I. Action performed for program 2: This starts program 2.
- J. Action performed for Search: This function searches for the input word
#### THE BACKEND
This class starts and updates the MainGUI amongst many other
functionalities. First, we initialize all CPU registers to 0, run is set as false and the memory
value for the machine is a short array of length 2048. We converted the shorts to strings and
had some edge cases with the leading zeroes when a checkbox 1 is clicked. We also can set
run to true or false depending on the state of the click. Note that run helps to handle initial
program loader and cannot run simultaneously with the single instruction. The IPL reads
from a file called “program.txt”. We handled the run instruction which goes through the
cycle to fetch, decode the instruction and then execute. We also handled the single
instruction that simply decodes the instruction and then execute. The fetch, decode and
execute calls are different functions that are called when and where necessary. The backend
contains the following methods:

- A. The BackEnd method: This is the constructor. It updates the GUI when needed.
- B. The short_to_string method: This method takes the short input already gotten from
the MainGUI class and converts back to a string to be displayed.
- C. The runGUI method: This is the method that defines the run status of the GUI. It
could either be false or true. The default is false.
- D. The updateGUI method: This method updates the textfields on the GUI when the
load button is pressed.
- E. The iPL method: This is the method that reads each line of the file to get the address
and instructions that were added into the memory.
- F. The runProgram1 method: It is the same as IPL but runs program 1 to find the
nearest value inputted on the keyboard from a list of 20 other numbers. This runs
when the find button is clicked on.
- G. The runProgram2 method: It is the same as IPL and runProgram1 but it runs the
prog2.txt and is called upon when hitting the "search" button.
- H. The runInstructions method: This handles instructions for the run button to function.
It goes through the instruction cycle – fetch, decode and execute.
I. The ssInstruction method: This is where the IR gets loaded into the MBR and the CPU
just decodes and execute without fetching. This is the Single Instruction. A doClick
happens so that it stops after one cycle.
- J. The fetch method: The next instruction is fetched from the memory address that is
currently stored in the program counter and stores it in the instruction register. At
the end of the fetch operation, the CPU points to the next instruction to be read
from the next cycle.
- K. The decode method: The encoded instruction stored into the instruction register is
decoded.
- L. The execute method: The decoded information is passed as a sequence of signals to
the relevant components for different operations like reading the instruction, logic
processing or writing out the output.

There are some supplementary methods with their own functions, and they are listed
below:
- A. The setGPR and getGPR methods: The getGPR method gets the gpr that is being used
while the setGPR sets the value to the gpr field on the GUI.
- B. The getIXR and setIXR methods: The getIXR method gets the ixr that is being used
while the setIXR sets the value to the ixr field on the GUI.
- C. The eA method: This calculates the EA based off the instructions from the
ProjectClassDescription packet.
- D. The machineFault method: This method takes each of the IDs for machine faults, sets
the mfr to its binary value and calls upon trap instructions.
- E. The reservations method: This method checks if the address provided is reserved (1,
2, 4, 5) or if the address exceeds 2048.
- F. The gprToixr method: It moves the desired contents at a GPR to an IXR
- G. The gprTogpr method: It moves the desired contents at a GPR to another GPR
- H. The less method: If rx < ry, then contents of GPR(0-3) move to GPR(0-3)
- I. The abs method: This method subtracts contents of ry from rx and get the absolute
value returned at ry.
- J. The hit method: This is a helper method for the execute that sets the run to false.
- K. The ldr method: This is a helper method for the execute that loads register from
memory
- L. The str method: This is a helper method for the execute that stores register into
memory
- M. The lda method: Is a helper method for the execute that loads register with address
- N. The ldx method: Is a helper method for the execute that loads index register from
memory
- O. The stx method: Is a helper method for the execute that stores index register into
memory
- P. The jz method is a helper method for the execute that jumps if the content of
gprcode is 0 and therefore program counter is equal to effective address, else the
program counter is incremented
- Q. The jne method is a helper method for the execute that jumps if the content of
gprcode is not 0 and therefore program counter is equal to effective address, else
the program counter is incremented
- R. The jcc method is a helper method for the execute that jumps if the condition code is
1 and therefore program counter is equal to effective address, else the program
counter is incremented
- S. The jma method: This method handles unconditional jump to address
- T. The jsr method: This method handles jump and save return address

Other methods implemented in this class are:
- A. The isOverFlow method: This method returns if the short value is an overflow
- B. The isUnderFlow method: This method returns if the short value is an underflow
- C. The getHighOrderBit method: This method gets the higher order bits of an inputted
value.
- D. The getLowOrderBit method: This method gets the lower order bits of an inputted
value.
- E. The rfs method: This method handles return from subroutine
- F. The sob method: This method handles subtract one and branch
- G. The jge method: This method handles jump greater than or equal to
- H. The amr method: This method handles add memory to register
- I. The smr method: This method handles subtract memory to register
- J. The air method: This method handles add immediate to register
- K. The sir method: This method handles subtract immediate to register
- L. The mlt method: This method handles multiply register by register
- M. The dvd method: This method handles divide register by register
- N. The trr method: This method handles test the equality of register and register
- O. The and method: This method handles logical and of register and register
- P. The orr method: This method handles logical or of register and register
- Q. The not method: This method handles logical not of register and register
- R. The src method: This method handles shift register by count
- S. The rrc method: This method handles rotate register by count
- T. The in method: This method handles contents of selected GPR get the first value
from the keyboard. The value is then removed from the keyboard.
- U. The out method: In this method, if devid for keyboard is selected, the console printer
prints out the char value of the selected GPR
- V. The chk method: This method checks if devid 0 (keyboard) or devid 1 (console
printer) is enabled
- W. The trap method: This method stores PC+1 in memory location 2. The overloaded
method also takes the value of MFR, prints out error on console printer and then
sets the IR, decodes it and then runs the first trap method so the corresponding Trap
code can be executed.

#### THE INSTRUCTIONS
This class has a function that gets the code for each component of the
instruction that is decoded from the IR (Instructions Register). The method here is described
below:
- A. The Instructions method: This is the constructor that sets all the fields in the class.
However, it is a function that gets the code for each component of the instruction
that is decoded for the instruction register.

#### THE CACHE
This class is such that handles all the operations of the cache memory, from
storing data into the cache, retrieving and the management of the cache once full. The
methods are described below:
- A. The store method: This method is such that helps to store new data (i.e. address and
instruction) into the cache. Before a store method is called there must have been a
cache-miss.
- B. The check method: This is the method that handles the search for the instruction if it
is stored in memory by checking for the address. A -1 is returned when there is a
cache miss, and the instruction is returned if there is a cache hit.
- C. The isCacheFull method: This method checks and returns if the cache is full or not.
- D. The isCacheEmpty method: This method checks and returns if the cache is empty or
not.
- E. The getSize method: This method returns the current size of the cache memory.
- F. The remove method: This method removes the least recently used data (i.e. the
instruction and address in the index 0 of the cache array) from the cache.
- G. The changePosition method: This method handles the position change of data (i.e.
the instruction and address in cache) based on the recent cache hit by making them
the most recently used (i.e. the position farthest from the index 0).

## USER GUIDE
There are a lot of checkboxes and textfields on the user interface that could be quite
confusing and so we look at them one at time.

First, we have all the textfields labelled with the registers that they represent and there is a
load button in front of them. We also have the checkboxes where each represents one
binary digit. When the checkbox is selected you have it represented as binary 1 and when
unchecked it is binary 0. So, we have five checkboxes for operation codes (representing 2 to
the power of 5 which equals 32 – we expect that our maximum input is 32 for the opcodes).
When we select for example the first opcode, the third and the fourth then we have 10110
represented. In the case of a single instruction the user clicks on “SSI” button and you can
select the checkboxes you want to represent and click the button in front of any of the
registers and you see the binary representation of the entire instruction. In the case of run
the user clicks on “RUN” button and you can read from the IPL.
Besides all of these is the execution of the load and store instructions. The user can click on
the “LOAD” button and system forces the opcode5 to be checked if it is not while the others
retain their states, and we can see the result. Since the button is a toggle of its sort we can
also click on “STOP” for the same button. For the store instruction the user can click on the
“STORE” button and the system forces the opcode4 now to be checked if it is not while the
others retain their states. You can also click on “STOP” on the same button. Note that the
opcode4 and opcode5 are checked for store and load respectively because the binary input
1 at those positions is integral to the execution of these instructions. Furthermore, we have
disabled the other checkboxes asides opcode1, opcode2, opcode3, opcode4 and opcode5 to
prevent the user from messing with the instruction’s execution.

## CONCLUSION
In summary, the system manages several operations of a computer system with a basic
architecture and some enhancements. There is also an implementation of the cache
memory for quick data retrieval. Another enhancement is the implementation of search of
numbers previously stored on the file prog1.txt. Machine faults and trap code is also added
to give a robustness to the computer system.
