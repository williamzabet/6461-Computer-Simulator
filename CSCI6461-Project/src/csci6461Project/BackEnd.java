package csci6461Project;

import java.io.File; // 
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BackEnd extends Thread {
	/*
	 * This class is the backend that declares each function which is then used to update the GUI
	 * when needed by the MainGUI, the words from the instruction codes are fetched, decoded, and excecuted, the codes
	 * are attained from the Instructions class
	 * Main Developer: Zabet, William B. 
	 * Reviewer: Ajayi, Oluwasegun E.
	 */
	
	public MainGUI gui;
	public Cache cache;

	
	// all CPU registers that are displayed. 
	protected short gpr0 = 0;  
	protected short gpr1 = 0;  
	protected short gpr2 = 0; 
	protected short gpr3 = 0; 
	protected short ixr1 = 0;
	protected short ixr2 = 0;
	protected short ixr3 = 0;
	protected short pc = 0;
	protected short mar = 0;
	protected short mbr = 0;
	protected short ir = 0;
	protected short mfr = 0;
	protected short cc = 0;
	private boolean run = false;
	protected short[] memory = new short[2048]; // machine can have up to 2048 words maximum!
	protected String print = ""; // string variable used to print out strings on the console printer.
	
	// returns true if the value Is overflow.
	public boolean isOverFlow(short number){return (number > 32768);}
	// returns true if the value Is underflow.
	public boolean isUnderFlow(short number){return (number < -32767);}
	//gets the higher order bits of an inputted value. 
	public short getHighOrderBit(short multiple) {return (short) (multiple >> 16);}
	// gets the lower order bits of an inputted value. 
	public short getLowOrderBit(short multiple) {return (short) (multiple & 0xFFFF);}
	
	
	public BackEnd(MainGUI gui) { // updates the GUI when needed. 
		this.gui = gui;
		this.start();
		updateGUI();
	}
		
	
	public void runGUI(boolean state) {
		run = state;
	}
	
	// this updates the GUI and updates the textfields when a load button is pressed.
	// converts shorts to strings and then resizes the binary string based on byte size
	public void updateGUI() {
		gui.textField_gpr0.setText(String.format("%16s", Integer.toBinaryString(0xFFFF & gpr0)).replaceAll(" ", "0"));
		gui.textField_gpr1.setText(String.format("%16s", Integer.toBinaryString(0xFFFF & gpr1)).replaceAll(" ", "0"));
		gui.textField_gpr2.setText(String.format("%16s", Integer.toBinaryString(0xFFFF & gpr2)).replaceAll(" ", "0"));
		gui.textField_gpr3.setText(String.format("%16s", Integer.toBinaryString(0xFFFF & gpr3)).replaceAll(" ", "0"));
		gui.textField_ixr1.setText(String.format("%16s", Integer.toBinaryString(0xFFFF & ixr1)).replaceAll(" ", "0"));
		gui.textField_ixr2.setText(String.format("%16s", Integer.toBinaryString(0xFFFF & ixr2)).replaceAll(" ", "0"));
		gui.textField_ixr3.setText(String.format("%16s", Integer.toBinaryString(0xFFFF & ixr3)).replaceAll(" ", "0"));
		gui.textField_pc.setText(String.format("%12s", Integer.toBinaryString(0xFFFF & pc)).replaceAll(" ", "0"));
		gui.textField_mar.setText(String.format("%12s", Integer.toBinaryString(0xFFFF & mar)).replaceAll(" ", "0"));
		gui.textField_mbr.setText(String.format("%16s", Integer.toBinaryString(0xFFFF & mbr)).replaceAll(" ", "0"));
		gui.textField_ir.setText(String.format("%16s", Integer.toBinaryString(0xFFFF & ir)).replaceAll(" ", "0"));
		gui.textField_mfr.setText(String.format("%4s", Integer.toBinaryString(0xFFFF & mfr)).replaceAll(" ", "0"));
		gui.textField_cc.setText(String.format("%4s", Integer.toBinaryString(0xFFFF & cc)).replaceAll(" ", "0"));
	}
	
//	 load in the IPL, and parse through each line to get the address and instruction. 
//	 they were then added into the memory. 
	public void iPL() {
		// clears the contents of each register, along with the memory
		gpr0 = 0;
		gpr1 = 0; 
		gpr2 = 0;
		gpr3 = 0;
		ixr1 = 0;
		ixr2 = 0;
		ixr3 = 0;
		pc = 0; 
		mar = 0;
		mbr = 0;
		ir = 0;
		mfr = 0;
		cc = 0; 
		memory = new short[2048];
		try {
			File file = new File("program.txt");
			Scanner read = new Scanner(file);
			int address = 0;
			short instruction = 0;
			boolean first = true;
			String line;
			while (read.hasNextLine()) {
				line = read.nextLine();
				// allows for comments to be added on the txt file, as it gets skipped over
				if (line.charAt(0) == '#') {;}
				else {
					// returns the first 4 characters (hexadecimal format, and converts it to a decimal)
					address = Integer.parseInt(line.substring(0,4), 16); 
					// returns the last 4 characters (hexadecimal format, and converts it to a decimal)
					instruction = (short) Integer.parseInt(line.substring((line.length() - 4), (line.length() - 0)), 16);
					memory[address] = instruction;
					// this sets the pc to the address in the first line of the txt file. 
					if (first == true) {pc = (short) address; first  = false; }
				}
			}
			read.close();
			updateGUI();
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
	}

	
	// same as IPL but runs program 1 to find the nearest value inputted on the keyboard from a list of 20 other numbers. 
	// this runs when the find button is clicked on
	public void runProgram1() {
		gpr0 = 0;
		gpr1 = 0; 
		gpr2 = 0;
		gpr3 = 0;
		ixr1 = 0;
		ixr2 = 0;
		ixr3 = 0;
		pc = 0; 
		mar = 0;
		mbr = 0;
		ir = 0;
		mfr = 0;
		cc = 0; 
		memory = new short[2048];
		try {
			File file = new File("prog1.txt");
			Scanner read = new Scanner(file);
			int address = 0;
			short instruction = 0;
			boolean first = true;
			String line;
			while (read.hasNextLine()) {
				line = read.nextLine();
				if (line.charAt(0) == '#') {;}
				else {
					address = Integer.parseInt(line.substring(0,4), 16);
					instruction = (short) Integer.parseInt(line.substring((line.length() - 4), (line.length() - 0)), 16);
					memory[address] = instruction;
					if (first == true) {pc = (short) address; first  = false; }
				}
			}
			read.close();
			updateGUI();
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	// Same as IPL and runProgram1 but it runs the prog2.txt and is called upon when hitting the "search" button. 
	public void runProgram2() {
		gpr0 = 0;
		gpr1 = 0; 
		gpr2 = 0;
		gpr3 = 0;
		ixr1 = 0;
		ixr2 = 0;
		ixr3 = 0;
		pc = 0; 
		mar = 0;
		mbr = 0;
		ir = 0;
		mfr = 0;
		cc = 0; 
		memory = new short[2048];
		try {
			File file = new File("prog2.txt");
			Scanner read = new Scanner(file);
			int address = 0;
			short instruction = 0;
			boolean first = true;
			String line;
			while (read.hasNextLine()) {
				line = read.nextLine();
				if (line.charAt(0) == '#') {;}
				else {
					address = Integer.parseInt(line.substring(0,4), 16);
					instruction = (short) Integer.parseInt(line.substring((line.length() - 4), (line.length() - 0)), 16);
					memory[address] = instruction;
					if (first == true) {pc = (short) address; first  = false; }
				}
			}
			read.close();
			updateGUI();
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
	}

	
	// instructions for the run button to function. It fetches, decodes, and excecutes. I talk more about these stages below. 
	// 
	public void runInstructions() {
		Instructions instruction;
		while (true) {
			fetch();
			instruction = decode(ir);
			execute(instruction);
			updateGUI();
			if (run==false) break;
			if (pc>=2048) {
				// Machine Fault! 
				System.out.println("Machine Fault ID = 3, 3	Illegal Memory Address beyond 2048 (memory installed)");
				mfr = 8;
				gui.textField_mfr.setText("1000");
				run=false;
				break;
			}
		}
	}
	
	// single instruction. where the ir gets loaded into the mbr, the CPU just decodes, and executes without fetching. When the run button is pressed. 
	// a doClick(); happens so that it immediately stops after one cycle. 
	public void ssInstruction() {
		Instructions instruction;
		ir = mbr;
		instruction = decode(ir);
		execute(instruction);
		updateGUI();
		if (gui.tgl_run.isSelected()) {
			gui.tgl_run.doClick();
		}
	}
	
	/*
	 * Each computer's CPU can have different cycles based on different instruction sets, 
	 * but will be similar to the following cycle:
	 * 
	 * Fetch Stage: The next instruction is fetched from the memory address that is currently stored in 
	 * the program counter and stored into the instruction register. At the end of the fetch operation, 
	 * the PC points to the next instruction that will be read at the next cycle.
	 * 
	 * Decode Stage: During this stage, the encoded instruction presented in the instruction register 
	 * is interpreted by the decoder. 
	 * 
	 * Execute Stage: The control unit of the CPU passes the decoded information as a sequence of control 
	 * signals to the relevant function units of the CPU to perform the actions required by the instruction, 
	 * such as reading values from registers, passing them to the ALU to perform mathematical or logic functions on them, and writing the result back to a register. 
	 * If the ALU is involved, it sends a condition signal back to the CU. The result generated by the operation is stored in the main memory or sent to an output device. 
	 * Based on the feedback from the ALU, the PC may be updated to a different address from which the next instruction will be fetched.
	 */
	
	// fetch: address in pc copied to the mar -> pc incremented to point to the next instruction -> instruction found at MAR's address, which is copied by the mbr. 
	// the mbr instrucion is then copied by the ir. 
	public void fetch() {
		mar = pc;
		mbr = memory[mar];
		ir = mbr;
		pc++;
	}
	
	// decode: Computer decodes the contents of the ir (done in the instructions class)
	public Instructions decode(short ir) {
		Instructions instruction = new Instructions(ir);
		return instruction;
	}
	
	// program runs each function twice so this bool variable is used to ensure functions are executed once during execute(). 
	boolean once = false;
	
	// function that gets the gpr that is being used
	public short getGPR(short input) {
		switch (input) {
		case 0:
			return gpr0;
		case 1:
			return gpr1;
		case 2:
			return gpr2;
		default: 
			return gpr3;
		}
	}
	
	// function that then sets the value to the gpr field on the GUI; 
	public void setGPR(short gprN, short value) {
		// TODO Auto-generated method stub
		switch (gprN) {
		// register R0
		case 0:
			this.gpr0 = value;
			break;
		case 1:
			this.gpr1 = value;
			break;
		case 2:
			this.gpr2 = value;
			break;
		default:
			this.gpr3 = value;
			break;
		}
	}
	
	// function that gets the ixr that is being used
	public short getIXR(short input) {
		switch (input) {
		case 1:
			return ixr1;
		case 2:
			return ixr2;
		default: 
			return gpr3;
		}
	}
	
	// function that then sets the value to the ixr field on the GUI; 
	public void setIXR(short ixrN, short value) {
		// TODO Auto-generated method stub
		switch (ixrN) {
		// register R0
		case 1:
			this.ixr1 = value;
			break;
		case 2:
			this.ixr2 = value;
			break;
		default:
			this.ixr3 = value;
			break;
		}
	}
	
	// This calculates the EA based off the instructions from the ProjectClassDescription packet!
	public short eA(Instructions instruction) { 
		short indirectAddy = 0;
		// if I isnt selected
		if (instruction.icode == 0) {
			if (instruction.ixrcode == 0) { return instruction.address;}
			else { switch (instruction.ixrcode) { 
				case 1: indirectAddy = (short) (ixr1 + instruction.address);
					break;
				case 2: indirectAddy = (short) (ixr2 + instruction.address);
					break;
				case 3: indirectAddy = (short) (ixr3 + instruction.address);
					break;} reservations(indirectAddy); return indirectAddy;}}
		else { 
			// if I is selected
			if (instruction.ixrcode == 0) { return memory[instruction.address];}
			else { switch (instruction.ixrcode) { 
				case 1: indirectAddy = (short) (ixr1 + instruction.address);
					break;
				case 2: indirectAddy = (short) (ixr2 + instruction.address);
					break;
				case 3: indirectAddy = (short) (ixr3 + instruction.address);
					break;} reservations(indirectAddy); return memory[indirectAddy]; }}
	}
	
	
	// each of the IDs for machine faults, sets the mfr to its binary value and calls upon trap instructions
	public void machineFault(int fault) {
		memory[4] = pc;
		
		if(fault == 0) {mfr = 1; trap(fault);}
		if(fault == 1) {mfr = 2; trap(fault);}
		if(fault == 2) {mfr = 4; trap(fault);}
		if(fault == 3) {mfr = 8; trap(fault);}
	}
	
	// checks if the address provided is reserved (1, 2, 4, 5) or if the address exceeds 2048
	public void reservations (short address) {
		if (address < 6 && address != 3) {machineFault(1);}
		if (address > 2047) {machineFault(3);}
	}

	// execute: computer sends signals to relevant components. 
	// cases for each opcode which will run its respective instruction. 
	public void execute(Instructions instruction) {
		
		if(instruction.opcode == 26) { if (instruction.opcode == 26 && !once) {gprToixr(instruction); once = true;} else {once = false; }}
		
		if(instruction.opcode == 27) { if (instruction.opcode == 27 && !once) {gprTogpr(instruction); once = true;} else {once = false; }}
		
		if(instruction.opcode == 28) { if (instruction.opcode == 28 && !once) {less(instruction); once = true;} else {once = false; }}
		
		if(instruction.opcode == 29) { if (instruction.opcode == 29 && !once) {abs(instruction); once = true;} else {once = false; }}
		
	    if(instruction.opcode == 0) { if (instruction.opcode == 0 && !once) {hlt(); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 1) { if (instruction.opcode == 1 && !once) {ldr(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 2) { if (instruction.opcode == 2 && !once) {str(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 3) { if (instruction.opcode == 3 && !once) {lda(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 41) { if (instruction.opcode == 41 && !once) {ldx(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 42) { if (instruction.opcode == 42 && !once) {stx(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 10) { if (instruction.opcode == 10 && !once) {jz(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 11) { if (instruction.opcode == 11 && !once) {jne(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 12) { if (instruction.opcode == 12 && !once) {jcc(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 13) { if (instruction.opcode == 13 && !once) {jma(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 14) { if (instruction.opcode == 14 && !once) {jsr(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 15) { if (instruction.opcode == 15 && !once) {rfs(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 16) { if (instruction.opcode == 16 && !once) {sob(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 17) { if (instruction.opcode == 17 && !once) {jge(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 4) { if (instruction.opcode == 4 && !once) {amr(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 5) { if (instruction.opcode == 5 && !once) {smr(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 6) { if (instruction.opcode == 6 && !once) {air(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 7) { if (instruction.opcode == 7 && !once) {sir(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 20) { if (instruction.opcode == 20 && !once) {mlt(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 21) { if (instruction.opcode == 21 && !once) {dvd(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 22) { if (instruction.opcode == 22 && !once) {trr(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 23) { if (instruction.opcode == 23 && !once) {and(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 24) { if (instruction.opcode == 24 && !once) {orr(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 25) { if (instruction.opcode == 25 && !once) {not(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 31) { if (instruction.opcode == 31 && !once) {src(instruction); once = true;} else {once = false; }}
	    
	    if(instruction.opcode == 32) { if (instruction.opcode == 32 && !once) {rrc(instruction); once = true;} else {once = false; }}
	    	    	    
	    if(instruction.opcode == 61) { if (instruction.opcode == 61 && !once) {in(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 62) { if (instruction.opcode == 62 && !once) {out(instruction); once = true;} else {once = false; }}

	    if(instruction.opcode == 63) { if (instruction.opcode == 63 && !once) {chk(instruction); once = true;} else {once = false; }}	
	    
	    if(instruction.opcode == 30) { if (instruction.opcode == 30 && !once) {trap(instruction); once = true;} else {once = false; }}	    
	}
	
	
	// first custom opcode instruction (26), it moves the desired contents at a GPR to an IXR
	public void gprToixr(Instructions instruction) {
		short gpr = instruction.gprcode;
		short ixr = instruction.ixrcode;
		short gprV = getGPR(gpr);
		setIXR(ixr, gprV);
	}
	
	// second custom opcode instruction (27), it moves the desired contents at a GPR to another GPR
	public void gprTogpr(Instructions instruction) {
		short rx = instruction.gprcode;
		short ry = instruction.ixrcode;
		short gprx = getGPR(rx);
		setGPR(ry, gprx);
	}
	
	// third custom opcode instruction (28), conditional code if rx < ry, then contents of GPR(0-3) move to GPR(0-3)
	public void less(Instructions instruction) {
		short rx = instruction.gprcode;
		short ry = instruction.ixrcode;
		short gprx = getGPR(rx);
		short gpry = getGPR(ry);
		
		if (gprx < gpry) {
			setGPR(ry, gprx);
		}
		else {
			pc++;
		}
	}
	
	// fourth custom opcode instruction (29), subtract contents of ry from rx and get the absolute value returned at ry
	public void abs(Instructions instruction) {
		short rx = instruction.gprcode;
		short ry = instruction.ixrcode;
		short gprx = getGPR(rx);
		short gpry = getGPR(ry);
		setGPR(ry, (short) Math.abs(gprx-gpry));
	}
	
	// if the condition is 0. the running state stops. and the run button is deselected to stop everything. 
	public void hlt() {
		run = false;
		pc--;
		if (gui.tgl_run.isSelected()) gui.tgl_run.doClick();			
	}
	
	// ldr: Load Register From Memory. 4 conditions as there are 4 ways the GPR register instructions can appear. 00, 01, 10, 11. 
	public void ldr(Instructions instruction) { // 01 Load Register from Memory
		short gprN = getGPR(instruction.gprcode);
		setGPR(gprN, memory[eA(instruction)]);
	}
	
	// str: Store Register To Memory. same thing but other way around where the memory[] = to the contents of the register. 
	public void str(Instructions instruction) {
		if (instruction.gprcode == 0) {memory[eA(instruction)] = gpr0;}
		if (instruction.gprcode == 1) {memory[eA(instruction)] = gpr1;}
		if (instruction.gprcode == 2) {memory[eA(instruction)] = gpr2;}
		if (instruction.gprcode == 3) {memory[eA(instruction)] = gpr3;}
	}
	
	// LDA: Load Register with Address. the register gets the address straight up
	public void lda(Instructions instruction) {
		if (instruction.gprcode == 0) {gpr0 = eA(instruction);}
		if (instruction.gprcode == 1) {gpr1 = eA(instruction);}
		if (instruction.gprcode == 2) {gpr2 = eA(instruction);}
		if (instruction.gprcode == 3) {gpr3 = eA(instruction);}
	}
	
	// ldx: Load Index Register from Memory: 3 cases, as 00 would not load from a Index register. 
	public void ldx(Instructions instruction) {
		if (instruction.ixrcode == 1) {ixr1 = memory[eA(instruction)];}
		if (instruction.ixrcode == 2) {ixr2 = memory[eA(instruction)];}
		if (instruction.ixrcode == 3) {ixr3 = memory[eA(instruction)];}
		}
	
	// stx: Store Index Register to Memory. same thing but other way around :)
	public void stx(Instructions instruction) {
		if (instruction.ixrcode == 1) {memory[eA(instruction)] = ixr1;}
		if (instruction.ixrcode == 2) {memory[eA(instruction)] = ixr2;}
		if (instruction.ixrcode == 3) {memory[eA(instruction)] = ixr3;}
	}
	
	// jz: Jump If Zero: if contents of r = 0 (gprcode == 0) then pc = EA, else: pc <- PC+1
	public void jz(Instructions instruction) {
		if (instruction.gprcode == 0) {if (gpr0 == 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 1) {if (gpr1 == 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 2) {if (gpr2 == 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 3) {if (gpr3 == 0) {pc = (short) (eA(instruction) - 1);}}
	}
	
	// jne: Jump If not equal: if contents of r != 0 (gprcode == 0) then pc = EA, else: pc <- PC+1
	public void jne(Instructions instruction) {
		if (instruction.gprcode == 0) {if (gpr0 != 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 1) {if (gpr1 != 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 2) {if (gpr2 != 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 3) {if (gpr3 != 0) {pc = (short) (eA(instruction) - 1);}}
	}
	
	//jcc: Jump If Condition Code: cc replaces r, and loads it into the register. if cc bit = 1 PC <- EA, else: pc++
	public void jcc(Instructions instruction) {
		short bit = instruction.gprcode;
		if (cc == bit) {pc = (short) (eA(instruction) - 1);}
	}
	
	//jma: Unconditional Jump To Address. PC <- EA, Note: r is ignored in this instruction
	public void jma(Instructions instruction) {
		pc = (short) (eA(instruction) - 1);
	}
	
	//jsr: Jump and Save Return Address: R3 <- PC+1; PC <- EA R0 should contain pointer to arguments Argument list should end with –1 (all 1s) value
	public void jsr(Instructions instruction) {
		gpr3 = (short) (pc + 1);
		pc = (short) (eA(instruction) - 1);

	}
	
	//rfs: Return From Subroutine w/ return code as Immed portion (optional) stored in the instruction’s address field. 
	// R0 <- Immed; PC <-  c(R3). IX, I fields are ignored.
	public void rfs(Instructions instruction) {
		short inmed = instruction.address;
		gpr0 = inmed;
		pc = (short) (gpr3 - 1);
	}
	
	/* sob: Subtract One and Branch. R = 0..3 , r  c(r) – 1
	 * If c(r) > 0,  PC <- EA; Else PC <- PC + 1 */
	public void sob(Instructions instruction) {
	
		if (instruction.gprcode == 0) {gpr0 = (short) (gpr0-1);
			if (gpr0 > 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 1) { gpr1 = (short) (gpr1-1); 
			if (gpr1 > 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 2) {gpr2 = (short) (gpr2-1);
			if (gpr2 > 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 3) {gpr3 = (short) (gpr3-1);
			if (gpr3 > 0) {pc = (short) (eA(instruction) - 1);}}
	}

	// jge: Jump Greater Than or Equal To:, If c(r) >= 0, then PC <- EA , Else PC <- PC + 1
	public void jge(Instructions instruction) {
		if (instruction.gprcode == 0) {if (gpr0 >= 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 1) {if (gpr1 >= 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 2) {if (gpr2 >= 0) {pc = (short) (eA(instruction) - 1);}}
		
		if (instruction.gprcode == 3) {if (gpr3 >= 0) {pc = (short) (eA(instruction) - 1);}}
	}

	// amr: Add Memory To Register, r = 0..3 r <- c(r) + c(EA)
	public void amr(Instructions instruction) {
		if (instruction.gprcode == 0) {gpr0 = (short) (gpr0 + memory[eA(instruction)]);}
		if (instruction.gprcode == 1) {gpr1 = (short) (gpr1 + memory[eA(instruction)]);}
		if (instruction.gprcode == 2) {gpr2 = (short) (gpr2 + memory[eA(instruction)]);}
		if (instruction.gprcode == 3) {gpr3 = (short) (gpr3 + memory[eA(instruction)]);}
	}
	
	// smr: Subtract Memory To Register, r = 0..3 r <- c(r) - c(EA)
	public void smr(Instructions instruction) {
		if (instruction.ixrcode == 1) {
			if (instruction.gprcode == 0) {gpr1 = (short) (gpr0 - memory[eA(instruction)]);}
			if (instruction.gprcode == 1) {gpr1 = (short) (gpr1 - memory[eA(instruction)]);}
			if (instruction.gprcode == 2) {gpr1 = (short) (gpr2 - memory[eA(instruction)]);}
			if (instruction.gprcode == 3) {gpr1 = (short) (gpr3 - memory[eA(instruction)]);}}
		
		if (instruction.ixrcode == 2) {
			if (instruction.gprcode == 0) {gpr2 = (short) (gpr0 - memory[eA(instruction)]);}
			if (instruction.gprcode == 1) {gpr2 = (short) (gpr1 - memory[eA(instruction)]);}
			if (instruction.gprcode == 2) {gpr2 = (short) (gpr2 - memory[eA(instruction)]);}
			if (instruction.gprcode == 3) {gpr2 = (short) (gpr3 - memory[eA(instruction)]);}}
		
		if (instruction.ixrcode == 3) {
			if (instruction.gprcode == 0) {gpr3 = (short) (gpr0 - memory[eA(instruction)]);}
			if (instruction.gprcode == 1) {gpr3 = (short) (gpr1 - memory[eA(instruction)]);}
			if (instruction.gprcode == 2) {gpr3 = (short) (gpr2 - memory[eA(instruction)]);}
			if (instruction.gprcode == 3) {gpr3 = (short) (gpr3 - memory[eA(instruction)]);}}
		
		if (instruction.ixrcode == 0) {
			if (instruction.gprcode == 0) {gpr0 = (short) (gpr0 - memory[eA(instruction)]);}
			if (instruction.gprcode == 1) {gpr1 = (short) (gpr1 - memory[eA(instruction)]);}
			if (instruction.gprcode == 2) {gpr2 = (short) (gpr2 - memory[eA(instruction)]);}
			if (instruction.gprcode == 3) {gpr3 = (short) (gpr3 - memory[eA(instruction)]);}}
	}
	
	// air: Add  Immediate to Register, r = 0..3 r <- c(r) + Immed
	public void air(Instructions instruction) {
		
		short immediate = instruction.address; 
		if (instruction.address == 0) {gpr0 = (short) (gpr0 + immediate);}
		if (instruction.address == 1) {gpr1 = (short) (gpr1 + immediate);}
		if (instruction.address == 2) {gpr2 = (short) (gpr2 + immediate);}
		if (instruction.address == 3) {gpr3 = (short) (gpr3 + immediate);}
	}
	
	// sir: Subtract  Immediate to Register, r = 0..3 r <- c(r) - Immed
	public void sir(Instructions instruction) {
		
		short immediate = instruction.address; 
		if (instruction.address == 0) {gpr0 = (short) (gpr0 - immediate);}
		if (instruction.address == 1) {gpr1 = (short) (gpr1 - immediate);}
		if (instruction.address == 2) {gpr2 = (short) (gpr2 - immediate);}
		if (instruction.address == 3) {gpr3 = (short) (gpr3 - immediate);}
	}
	
	/* mlt: 
	 * Multiply Register by Register, rx, rx+1 <- c(rx) * c(ry), rx must be 0 or 2, ry must be 0 or 2
	 * rx contains the high order bits, rx+1 contains the low order bits of the result, Set OVERFLOW flag, if overflow */
	public void mlt(Instructions instruction) {
		short rx = instruction.gprcode;
		short ry = instruction.ixrcode;
		
		if ((rx == 0 || rx == 2) && (ry == 0 || ry == 2)) {
			short mlt;
			
			if (rx == 0 && ry == 0) {mlt = (short) (gpr0*gpr0);
				if (!isOverFlow(mlt) && !isUnderFlow(mlt)) {gpr0 = getHighOrderBit(mlt); gpr1 = getLowOrderBit(mlt);}
				else {
					if (isOverFlow(mlt)) {cc = 4;}
					else {cc = 3;}}}
			
			if (rx == 0 && ry == 2) {mlt = (short) (gpr0*gpr2);
				if (!isOverFlow(mlt) && !isUnderFlow(mlt)) {gpr0 = getHighOrderBit(mlt); gpr1 = getLowOrderBit(mlt);}
				else {
					if (isOverFlow(mlt)) {cc = 4;}
					else {cc = 3;}}}
			
			if (rx == 2 && ry == 0) {mlt = (short) (gpr2*gpr0);
				if (!isOverFlow(mlt) && !isUnderFlow(mlt)) {gpr2 = getHighOrderBit(mlt); gpr3 = getLowOrderBit(mlt);}
				else {
					if (isOverFlow(mlt)) {cc = 8;}
					else {cc = 4;}}}
			
			if (rx == 2 && ry == 2) {mlt = (short) (gpr2*gpr2);
				if (!isOverFlow(mlt) && !isUnderFlow(mlt)) {gpr3 = getHighOrderBit(mlt); gpr2 = getLowOrderBit(mlt);}
				else {
					if (isOverFlow(mlt)) {cc = 8;}
					else {cc = 4;}}}
		}
	}
	
	/* dvd: Divide Register by Register, rx, rx+1 <- c(rx)/ c(ry), rx must be 0 or 2, rx contains the quotient; rx+1 contains the remainder
	 * ry must be 0 or 2, If c(ry) = 0, set cc(3) to 1 (set DIVZERO flag) */
	public void dvd(Instructions instruction) {
		short rx = instruction.gprcode;
		short ry = instruction.ixrcode;
		
		if ((rx == 0 || rx == 2) && (ry == 0 || ry == 2)) {
			
			if (rx == 0 && ry == 0) {if (gpr0 == 0) {cc = 2;}
			else {short quotient = (short) (gpr0/gpr0); short remainder = (short) (gpr0%gpr0); gpr0 = quotient; gpr1 = remainder;}}
			
			if (rx == 0 && ry == 2) {if (gpr2 == 0) {cc = 2;}
			else {short quotient = (short) (gpr0/gpr2); short remainder = (short) (gpr0%gpr2); gpr0 = quotient; gpr1 = remainder;}}
			
			if (rx == 2 && ry == 0) {if (gpr0 == 0) {cc = 2;}
			else {short quotient = (short) (gpr2/gpr0); short remainder = (short) (gpr2%gpr0); gpr2 = quotient; gpr3 = remainder;}}
			
			if (rx == 2 && ry == 2) {if (gpr2 == 0) {cc = 2;}
			else {short quotient = (short) (gpr2/gpr2); short remainder = (short) (gpr2%gpr2); gpr2 = quotient; gpr3 = remainder;}}		
		}
	}
		
	// trr: Test the Equality of Register and Register, If c(rx) = c(ry), set cc(4) <- 1; else, cc(4) <-  0
	public void trr(Instructions instruction) {
		short rx = instruction.gprcode;
		short ry = instruction.ixrcode;
		if (getGPR(rx) == getGPR(ry)) {cc = 1;}
		else {cc = 0; }
		}
	
	// and: Logical And of Register and Register, c(rx) <- c(rx) AND c(ry)
	public void and(Instructions instruction) {
		short rx = instruction.gprcode;
		short ry = instruction.ixrcode;
		short gprx = getGPR(rx);
		short gpry = getGPR(ry);
		gprx = (short) (gprx & gpry);
		setGPR(rx, gprx);
	}
	
	// orr: Logical or of Register and Register, c(rx) <- c(rx) OR c(ry)
	public void orr(Instructions instruction) {
		short rx = instruction.gprcode;
		short ry = instruction.ixrcode;
		short gprx = getGPR(rx);
		short gpry = getGPR(ry);
		gprx = (short) (gprx | gpry);
		setGPR(rx, gprx);
	}
	
	// not: Logical not of Register and Register, c(rx) <- c(rx) NOT c(ry)
	public void not(Instructions instruction) {
		short rx = instruction.gprcode;
		short gprx = getGPR(rx);
		gprx = (short) ~gprx;
		setGPR(rx, gprx);
	}
	
	/* src: Shift Register by Count, c(r) is shifted left (L/R =1) or right (L/R = 0) either logically (A/L = 1) or arithmetically (A/L = 0)
	 * XX, XXX are ignored, Count = 0…15, If Count = 0, no shift occurs */
	public void src(Instructions instruction) {
		if (instruction.count == 0) {}
		else {
			short rx = instruction.r; 
			short gprx = getGPR(rx);
			if (instruction.al == 1) {
				if (instruction.lr == 1) {
					gprx = (short) (gprx << instruction.count);
				}
				else if (instruction.lr == 0) {
					if (gprx >= 0) {
						gprx = (short) (gprx >>> instruction.count);}
				}
				else {
					gprx = (short) (gprx >>> instruction.count);
					String temp = String.format("%16s", Integer.toBinaryString(0xFFFF & gprx)).replaceAll(" ", "0"); 
					temp = temp.replace("1111111111111111", "");
					int temp_decimal = Integer.parseInt(temp,2); gprx = (short) temp_decimal;}}
			else {
				if (instruction.lr == 0) {
					gprx = (short) (gprx >> instruction.count);
				}
				else if (instruction.lr == 1) {
					gprx = (short) (gprx << instruction.count);}
			}
			setGPR(rx, gprx);}
	}
	
	/* rrc: Rotate Register by Count c(r) is rotated left (L/R = 1) or right (L/R =0) either logically (A/L =1) XX, 
	 * XXX is ignored Count = 0…15 If Count = 0, no rotate occurs */
	public void rrc(Instructions instruction) {
		String left, right; 
		short rx = instruction.r; 
		short gprx = getGPR(rx);
		String result = String.format("%16s", Integer.toBinaryString(0xFFFF & gprx)).replaceAll(" ", "0");
		if (instruction.count == 0) {}
		else {
			if (gprx >= 0) {result = result.replace("0000000000000000", "");
			}
			if (gprx < 0) {result = result.replace("1111111111111111", "");
			}
			if (instruction.lr == 1) {
				left = result.substring(instruction.count); 
				right = result.substring(0, instruction.count); 
				result = left+right;
			}
			else if (instruction.lr == 0) {
				left = result.substring(0, 16 - instruction.count); 
				right = result.substring(16 - instruction.count); 
				result = left+right;
			}
			int result_decimal = Integer.parseInt(result, 2);
			gprx = (short) result_decimal;
			setGPR(rx, gprx); 
		}
	}
	
	// in: contents of selected GPR get the first value from the keyboard. the value is then removed from the keyboard. 
	public void in(Instructions instruction) {
		if (instruction.devid == 0) {
			if (instruction.gprcode == 0) {
				if (gui.keyboard.isEmpty()) {gpr0 = 0;}
				else {int intermediate = gui.keyboard.get(0); gpr0 = (short) intermediate; gui.keyboard.remove(0);}}
			
			if (instruction.gprcode == 1) {
				if (gui.keyboard.isEmpty()) {gpr1 = 0;}
				else {int intermediate = gui.keyboard.get(0); gpr1 = (short) intermediate; gui.keyboard.remove(0);}}
			
			if (instruction.gprcode == 2) {
				if (gui.keyboard.isEmpty()) {gpr2 = 0;}
				else {int intermediate = gui.keyboard.get(0); gpr2 = (short) intermediate; gui.keyboard.remove(0);}}
			
			if (instruction.gprcode == 3) {
				if (gui.keyboard.isEmpty()) {gpr3 = 0;}
				else {int intermediate = gui.keyboard.get(0); gpr3 = (short) intermediate; gui.keyboard.remove(0);}}
		}
	}
	
	// out: if devid for keyboard is selected, the console printer prints out the char value of the selected GPR
	public void out(Instructions instruction) {
		short output = 0;
		
		if(instruction.devid != 1) {}
		else {
			if(instruction.gprcode == 0) {output = gpr0;}
			if(instruction.gprcode == 1) {output = gpr1;}
			if(instruction.gprcode == 2) {output = gpr2;}
			if(instruction.gprcode == 3) {output = gpr3;}
			
			print += (char) output;
			gui.consolePrinter.setText(print);
		}
	}
		
	// chk: checks if devid 0 (keyboard) or devid 1 (console printer) is enabled. if so the selected r displays 1
	// else: it displays a 0 
	public void chk(Instructions instruction) {
		short gprN = getGPR(instruction.gprcode);
		
		if (instruction.devid == 0) {
			if (gui.textField_keyboard.isEnabled() == true) {setGPR(gprN, (short) 1);}
			else {setGPR(gprN, (short) 0);}}
		
		if (instruction.devid == 1) {
			if (gui.consolePrinter.isEnabled() == true) {setGPR(gprN, (short) 1);}
			else {setGPR(gprN, (short) 0);}}
	}
	
	//trap: stores PC+1 in memory location 2
	public void trap(Instructions instruction) {
		memory[2] = (short)(pc+1);
		// traps to memory 0  
		pc = (short)(memory[(short) (memory[0] + instruction.count)]);
		// runs instructions to go to the routine and execute them. 
		runInstructions();
	}
	
	// trap for no args. takes the value of MFR, prints out error on console printer
	// and then sets the IR, decodes it and then runs the trap method above so the corresponding Trap code can be executed. 
	public void trap(int mfr) {
		Instructions instruction;
		
		if (mfr == 0) {
			gui.consolePrinter.setText("Trap at pc location " + pc + " (Illegal Memory Address to Reserved Locations)");
			ir = 10000;
			instruction = decode(ir);
			trap(instruction);
		}
	
		if (mfr == 1) {
			gui.consolePrinter.setText("Trap at pc location " + pc + " (Illegal TRAP code)");
			ir = 10001;
			instruction = decode(ir);
			trap(instruction);
		}
		
		if (mfr == 2) {
			gui.consolePrinter.setText("Trap at pc location " + pc + " (Illegal Operation Code )");
			ir = 10002;
			instruction = decode(ir);
			trap(instruction);
		}
		if (mfr == 3) {
			gui.consolePrinter.setText("Trap at pc location " + pc + " (Illegal Memory Address beyond 2048 (memory installed)");
			ir = 10003;
			instruction = decode(ir);
			trap(instruction);
		}
	}
}