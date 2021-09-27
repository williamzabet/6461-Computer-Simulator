package csci6461Project;

import java.awt.EventQueue; // 
import javax.swing.JFrame;   //
import javax.swing.JLabel;		//
import java.awt.Font;				//
import java.awt.Color;			     // These packages were imported for java swing
import javax.swing.JTextField;		 // They also enable one to go in and design in 
import javax.swing.JButton;			//	design in real time
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;		//
import java.awt.event.ActionListener;//
import java.awt.event.ActionEvent; // 
import javax.swing.JToggleButton; // 
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea; // io exception in case file is not found 



public class MainGUI extends JFrame {
	/* MainGUI is the front end GUI of the simulator which builds the layout of the simulator.
	 * The buttons and textfields are updated from calling upon the BackEnd class.
	 * Main Developer: Zabet, William B. 
	 * Reviewer: Ajayi, Oluwasegun E.
	*/

	private static BackEnd backEnd; // calls on the BackEnd class as some methods from it are used
	
	private JFrame frame; // The main frame of the GUI
	
	// These are JTextfields - the text fields where the binary instrucitons go. 
	protected JTextField textField_gpr0;
	protected JTextField textField_gpr1;
	protected JTextField textField_gpr2;
	protected JTextField textField_gpr3;
	protected JTextField textField_ixr1;
	protected JTextField textField_ixr2;
	protected JTextField textField_ixr3;
	protected JTextField textField_pc;
	protected JTextField textField_mar;
	protected JTextField textField_mbr;
	protected JTextField textField_ir;
	protected JTextField textField_mfr;
	protected JTextField textField_cc;
	// these are the JCheckBoxes, where the user can select their own instruction, and then load it
	private JCheckBox op0box;
	private JCheckBox op1box;
	private JCheckBox op2box;
	private JCheckBox op3box;
	private JCheckBox op4box;
	private JCheckBox op5box;
	private JCheckBox gpr0box;
	private JCheckBox gpr1box;
	private JCheckBox ixr0box;
	private JCheckBox ixr1box;
	private JCheckBox i0box;
	private JCheckBox address0box;
	private JCheckBox address1box;
	private JCheckBox address2box;
	private JCheckBox address3box;
	private JCheckBox address4box;
	
	// jtogglebutton is a toggle button that needs to be pressed to turn on and off.
	// the tgl_run runs all the entire instructions unless tgl_ss (single instruction) is turned on. 
	protected JToggleButton tgl_run;
	protected JToggleButton tgl_ss;
	// this loads in instructions from the program.txt file
	protected JButton btn_ipl;
	
	// this just helps with  a simple ldr (opcode 01) by checking off the necessary boxes. 
	private JToggleButton tgl_load;
	// this just helps with  a simple str (opcode 01) by checking off the necessary boxes. 
	private JToggleButton tgl_store;
	// console printer device
	protected JTextArea consolePrinter;
	// console keyboard device
	protected JTextField textField_keyboard;
	// button to initialize program 1
	private JButton btn_p1;
	// button to initialize program 2
	private JButton btn_p2;
	// reads in the list of 20 numbers for program 1
	private JButton btn_read;
	// reads in a word to find for program 1
	private JButton btn_find;
	// a string array of all the numbers entered from btn_read
	private String input20;
	// a string of the word to find from btn_find
	private String find_program1; 
	// an integer list for all numbers on the keyboard
	public List<Integer> keyboard = new ArrayList<Integer>();
	// finds the word inputed within the paragraph for program 2
	private JButton btn_search;
	
	

// launches the application. 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI gui = new MainGUI();
					gui.frame.setVisible(true);
					backEnd = new BackEnd(gui);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// this converts the checked boxes in to a binary string. checked = 1, unchecked = 0. 
	public String checkedBoxes() {
		String boxResults = "";
		
		JCheckBox[] boxArray = {op0box, op1box, op2box, op3box, op4box, op5box, gpr0box, gpr1box, ixr0box, ixr1box, i0box, 
				address0box, address1box, address2box, address3box, address4box};
		for (int i = 0; i < boxArray.length; i++) {
			if (boxArray[i].isSelected() == true) {
				boxResults += "1";
			}
			
			else {
				boxResults += "0";
			}
		}
		return boxResults;
	}
	
	
	// converts the string into a short. Shorts are numbers of 1 byte size per digit.
	private short convert_to_16bits(String instruction) {
		short convert = (short) Integer.parseInt(instruction, 2);
		return convert;  
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	// most of this is generated code thanks to the built in design application using eclipse. 
	public void initialize() { // 
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.getContentPane().setBackground(new Color(244, 164, 96));
		frame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 9));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// every jswing item is given a variable name, and has allignments w/ bounds. 
		JLabel lbl_gpr1 = new JLabel("GPR 1");
		lbl_gpr1.setBounds(6, 26, 36, 11);
		lbl_gpr1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_gpr1.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_gpr2 = new JLabel("GPR 2");
		lbl_gpr2.setBounds(6, 44, 36, 13);
		lbl_gpr2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_gpr2.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_gpr3 = new JLabel("GPR 3");
		lbl_gpr3.setBounds(6, 63, 36, 11);
		lbl_gpr3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_gpr3.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_ixr1 = new JLabel("IXR 1");
		lbl_ixr1.setBounds(6, 88, 36, 11);
		lbl_ixr1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ixr1.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_ixr2 = new JLabel("IXR 2");
		lbl_ixr2.setBounds(6, 107, 36, 13);
		lbl_ixr2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ixr2.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_ixr3 = new JLabel("IXR 3");
		lbl_ixr3.setBounds(6, 126, 36, 13);
		lbl_ixr3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ixr3.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		textField_gpr0 = new JTextField();
		textField_gpr0.setBounds(48, 6, 122, 13);
		textField_gpr0.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_gpr0.setColumns(20);
		
		textField_gpr1 = new JTextField();
		textField_gpr1.setBounds(48, 25, 122, 13);
		textField_gpr1.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_gpr1.setColumns(16);
		
		textField_gpr2 = new JTextField();
		textField_gpr2.setBounds(48, 44, 122, 13);
		textField_gpr2.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_gpr2.setColumns(16);
		
		textField_gpr3 = new JTextField();
		textField_gpr3.setBounds(48, 63, 122, 13);
		textField_gpr3.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_gpr3.setColumns(16);
		
		textField_ixr1 = new JTextField();
		textField_ixr1.setBounds(48, 88, 122, 13);
		textField_ixr1.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_ixr1.setColumns(16);
		
		textField_ixr2 = new JTextField();
		textField_ixr2.setBounds(48, 107, 122, 13);
		textField_ixr2.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_ixr2.setColumns(16);
		
		textField_ixr3 = new JTextField();
		textField_ixr3.setBounds(48, 126, 122, 13);
		textField_ixr3.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_ixr3.setColumns(20);
		
		// I added an action listener here. where all load buttons have pretty much the same code
		// if the load button is clicked, the user's input from the checked boxes will be 
		// stored within the contents of the load button's respective textfield.
		JButton load_gpr0 = new JButton("Load");
		load_gpr0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = checkedBoxes();
				backEnd.gpr0 = convert_to_16bits(input);
				backEnd.updateGUI();
			}
		});
		load_gpr0.setBounds(176, 6, 37, 13);
		load_gpr0.setForeground(new Color(0, 0, 0));
		load_gpr0.setBackground(Color.WHITE);
		load_gpr0.setFont(new Font("Andale Mono", Font.PLAIN, 9));
		
		JButton load_gpr1 = new JButton("Load");
		load_gpr1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = checkedBoxes();
				backEnd.gpr1 = convert_to_16bits(input);
				backEnd.updateGUI();
			}
		});
		load_gpr1.setBounds(176, 25, 37, 13);
		load_gpr1.setForeground(Color.BLACK);
		load_gpr1.setFont(new Font("Andale Mono", Font.BOLD, 9));
		load_gpr1.setBackground(Color.WHITE);
		
		JButton load_gpr2 = new JButton("Load");
		load_gpr2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = checkedBoxes();
				backEnd.gpr2 = convert_to_16bits(input);
				backEnd.updateGUI();
			}
		});
		load_gpr2.setBounds(176, 44, 37, 13);
		load_gpr2.setForeground(Color.BLACK);
		load_gpr2.setFont(new Font("Andale Mono", Font.PLAIN, 9));
		load_gpr2.setBackground(Color.WHITE);
		
		JButton load_ixr1 = new JButton("Load");
		load_ixr1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = checkedBoxes();
				backEnd.ixr1 = convert_to_16bits(input);
				backEnd.updateGUI();
			}
		});
		load_ixr1.setBounds(176, 88, 37, 13);
		load_ixr1.setForeground(Color.BLACK);
		load_ixr1.setFont(new Font("Andale Mono", Font.PLAIN, 9));
		load_ixr1.setBackground(Color.WHITE);
		
		JButton load_ixr2 = new JButton("Load");
		load_ixr2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = checkedBoxes();
				backEnd.ixr2 = convert_to_16bits(input);
				backEnd.updateGUI();
			}
		});
		load_ixr2.setBounds(176, 107, 37, 13);
		load_ixr2.setForeground(Color.BLACK);
		load_ixr2.setFont(new Font("Andale Mono", Font.PLAIN, 9));
		load_ixr2.setBackground(Color.WHITE);
		
		JButton load_ixr3 = new JButton("Load");
		load_ixr3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = checkedBoxes();
				backEnd.ixr3 = convert_to_16bits(input);
				backEnd.updateGUI();
			}
		});
		load_ixr3.setBounds(176, 126, 37, 13);
		load_ixr3.setForeground(Color.BLACK);
		load_ixr3.setFont(new Font("Andale Mono", Font.PLAIN, 9));
		load_ixr3.setBackground(Color.WHITE);
		
		JLabel lbl_pc = new JLabel("PC");
		lbl_pc.setBounds(225, 6, 21, 13);
		lbl_pc.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_pc.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_gpr0 = new JLabel("GPR 0");
		lbl_gpr0.setBounds(6, 7, 36, 11);
		lbl_gpr0.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_gpr0.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_mar = new JLabel("MAR");
		lbl_mar.setBounds(225, 25, 21, 13);
		lbl_mar.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_mar.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_mbr = new JLabel("MBR");
		lbl_mbr.setBounds(225, 44, 21, 13);
		lbl_mbr.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_mbr.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_ir = new JLabel("IR");
		lbl_ir.setBounds(225, 63, 21, 13);
		lbl_ir.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ir.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_mfr = new JLabel("MFR");
		lbl_mfr.setBounds(225, 88, 21, 13);
		lbl_mfr.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_mfr.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		JLabel lbl_cc = new JLabel("CC");
		lbl_cc.setBounds(225, 107, 21, 13);
		lbl_cc.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_cc.setFont(new Font("Times New Roman", Font.BOLD, 9));
		
		textField_pc = new JTextField();
		textField_pc.setBounds(252, 6, 122, 13);
		textField_pc.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_pc.setColumns(16);
		
		textField_mar = new JTextField();
		textField_mar.setBounds(252, 25, 122, 13);
		textField_mar.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_mar.setColumns(16);
		
		textField_mbr = new JTextField();
		textField_mbr.setBounds(252, 44, 122, 13);
		textField_mbr.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_mbr.setColumns(16);
		
		textField_ir = new JTextField();
		textField_ir.setBounds(252, 63, 122, 13);
		textField_ir.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_ir.setColumns(16);
		
		textField_mfr = new JTextField();
		textField_mfr.setBounds(252, 88, 122, 13);
		textField_mfr.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_mfr.setColumns(16);
		
		textField_cc = new JTextField();
		textField_cc.setBounds(252, 107, 122, 13);
		textField_cc.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_cc.setColumns(16);
		
		JButton load_pc = new JButton("Load");
		load_pc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = checkedBoxes();
				backEnd.pc = convert_to_16bits(input);
				backEnd.updateGUI();
			}
		});
		load_pc.setBounds(380, 6, 37, 13);
		load_pc.setForeground(Color.BLACK);
		load_pc.setFont(new Font("Andale Mono", Font.PLAIN, 9));
		load_pc.setBackground(Color.WHITE);
		
		JButton load_mar = new JButton("Load");
		load_mar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = checkedBoxes();
				backEnd.mar = convert_to_16bits(input);
				backEnd.updateGUI();
			}
		});
		load_mar.setBounds(380, 25, 37, 13);
		load_mar.setForeground(Color.BLACK);
		load_mar.setFont(new Font("Andale Mono", Font.PLAIN, 9));
		load_mar.setBackground(Color.WHITE);
		
		JButton load_mbr = new JButton("Load");
		load_mbr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = checkedBoxes();
				backEnd.mbr = convert_to_16bits(input);
				backEnd.updateGUI();
			}
		});
		load_mbr.setBounds(380, 44, 37, 13);
		load_mbr.setForeground(Color.BLACK);
		load_mbr.setFont(new Font("Andale Mono", Font.PLAIN, 9));
		load_mbr.setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		op0box = new JCheckBox("");
		op0box.setBounds(14, 152, 28, 23);
		op0box.setBackground(Color.WHITE);
		op0box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		frame.getContentPane().add(op0box);
		
		frame.getContentPane().add(lbl_gpr1);
		frame.getContentPane().add(lbl_gpr0);
		frame.getContentPane().add(textField_gpr1);
		frame.getContentPane().add(textField_gpr0);
		frame.getContentPane().add(lbl_gpr2);
		frame.getContentPane().add(textField_gpr2);
		frame.getContentPane().add(lbl_gpr3);
		frame.getContentPane().add(textField_gpr3);
		frame.getContentPane().add(lbl_ixr1);
		frame.getContentPane().add(textField_ixr1);
		frame.getContentPane().add(lbl_ixr2);
		frame.getContentPane().add(textField_ixr2);
		frame.getContentPane().add(lbl_ixr3);
		frame.getContentPane().add(textField_ixr3);
		frame.getContentPane().add(load_gpr0);
		frame.getContentPane().add(lbl_pc);
		frame.getContentPane().add(textField_pc);
		frame.getContentPane().add(load_pc);
		frame.getContentPane().add(load_gpr1);
		frame.getContentPane().add(lbl_mar);
		frame.getContentPane().add(textField_mar);
		frame.getContentPane().add(load_mar);
		frame.getContentPane().add(load_gpr2);
		frame.getContentPane().add(lbl_mbr);
		frame.getContentPane().add(textField_mbr);
		frame.getContentPane().add(load_mbr);
		frame.getContentPane().add(lbl_ir);
		frame.getContentPane().add(textField_ir);
		frame.getContentPane().add(load_ixr1);
		frame.getContentPane().add(lbl_mfr);
		frame.getContentPane().add(textField_mfr);
		frame.getContentPane().add(load_ixr2);
		frame.getContentPane().add(lbl_cc);
		frame.getContentPane().add(textField_cc);
		frame.getContentPane().add(load_ixr3);
		
		JButton load_gpr3 = new JButton("Load");
		load_gpr3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = checkedBoxes();
				backEnd.gpr3 = convert_to_16bits(input);
				backEnd.updateGUI();
			}
		});
		load_gpr3.setBounds(176, 63, 37, 13);
		load_gpr3.setForeground(Color.BLACK);
		load_gpr3.setFont(new Font("Andale Mono", Font.PLAIN, 9));
		load_gpr3.setBackground(Color.WHITE);
		frame.getContentPane().add(load_gpr3);
		
		op2box = new JCheckBox("");
		op2box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		op2box.setBackground(Color.WHITE);
		op2box.setBounds(53, 152, 28, 23);
		frame.getContentPane().add(op2box);
		
		op1box = new JCheckBox("");
		op1box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		op1box.setBackground(Color.WHITE);
		op1box.setBounds(33, 152, 28, 23);
		frame.getContentPane().add(op1box);
		
		op3box = new JCheckBox("");
		op3box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		op3box.setBackground(Color.WHITE);
		op3box.setBounds(73, 152, 28, 23);
		frame.getContentPane().add(op3box);
		
		op4box = new JCheckBox("");
		op4box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		op4box.setBackground(Color.WHITE);
		op4box.setBounds(93, 152, 28, 23);
		frame.getContentPane().add(op4box);
		
		op5box = new JCheckBox("");
		op5box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		op5box.setBackground(Color.WHITE);
		op5box.setBounds(113, 152, 28, 23);
		frame.getContentPane().add(op5box);
		
		gpr0box = new JCheckBox("");
		gpr0box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		gpr0box.setBackground(Color.WHITE);
		gpr0box.setBounds(147, 152, 28, 23);
		frame.getContentPane().add(gpr0box);
		
		ixr1box = new JCheckBox("");
		ixr1box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		ixr1box.setBackground(Color.WHITE);
		ixr1box.setBounds(230, 152, 28, 23);
		frame.getContentPane().add(ixr1box);
		
		gpr1box = new JCheckBox("");
		gpr1box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		gpr1box.setBackground(Color.WHITE);
		gpr1box.setBounds(168, 152, 28, 23);
		frame.getContentPane().add(gpr1box);
		
		ixr0box = new JCheckBox("");
		ixr0box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		ixr0box.setBackground(Color.WHITE);
		ixr0box.setBounds(208, 152, 28, 23);
		frame.getContentPane().add(ixr0box);
		
		i0box = new JCheckBox("");
		i0box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		i0box.setBackground(Color.WHITE);
		i0box.setBounds(270, 152, 28, 23);
		frame.getContentPane().add(i0box);
		
		address0box = new JCheckBox("");
		address0box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		address0box.setBackground(Color.WHITE);
		address0box.setBounds(320, 152, 28, 23);
		frame.getContentPane().add(address0box);
		
		address1box = new JCheckBox("");
		address1box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		address1box.setBackground(Color.WHITE);
		address1box.setBounds(339, 152, 28, 23);
		frame.getContentPane().add(address1box);
		
		address2box = new JCheckBox("");
		address2box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		address2box.setBackground(Color.WHITE);
		address2box.setBounds(360, 152, 28, 23);
		frame.getContentPane().add(address2box);
		
		address3box = new JCheckBox("");
		address3box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		address3box.setBackground(Color.WHITE);
		address3box.setBounds(380, 152, 28, 23);
		frame.getContentPane().add(address3box);
		
		JLabel lbl_op = new JLabel("Operation");
		lbl_op.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_op.setFont(new Font("Times New Roman", Font.BOLD, 9));
		lbl_op.setBounds(52, 174, 41, 16);
		frame.getContentPane().add(lbl_op);
		
		JLabel lbl_gpr = new JLabel("GPR");
		lbl_gpr.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_gpr.setFont(new Font("Times New Roman", Font.BOLD, 9));
		lbl_gpr.setBounds(153, 172, 41, 16);
		frame.getContentPane().add(lbl_gpr);
		
		JLabel lbl_ixr = new JLabel("IXR");
		lbl_ixr.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ixr.setFont(new Font("Times New Roman", Font.BOLD, 9));
		lbl_ixr.setBounds(217, 172, 28, 16);
		frame.getContentPane().add(lbl_ixr);
		
		JLabel lblI = new JLabel("I");
		lblI.setHorizontalAlignment(SwingConstants.CENTER);
		lblI.setFont(new Font("Times New Roman", Font.BOLD, 9));
		lblI.setBounds(280, 174, 11, 16);
		frame.getContentPane().add(lblI);
		
		JLabel lbl_address = new JLabel("Address");
		lbl_address.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_address.setFont(new Font("Times New Roman", Font.BOLD, 9));
		lbl_address.setBounds(360, 172, 41, 16);
		frame.getContentPane().add(lbl_address);
		
		// store toggle button. 
		tgl_store = new JToggleButton("Store");
		tgl_store.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tgl_store.isSelected()) {
					// when pressed, the load toggle gets disabled to prevent complications.
					tgl_load.setEnabled(false);
					// the store  toggle  then reads "STOP" to make it easier for the user to see that it is on.
					tgl_store.setText("STOP");
					
					// since the opcode for a an STR = 000010, that means op4box has to be checked.
					// if its not unchecked, then the program automatically checks it. 
					
					if (op4box.isSelected() == false) {
						op4box.doClick();
					}
					// if any others are checked, they get clicked so they become unchecked. 
					if (op0box.isSelected() == true) {
						op0box.doClick();
					}
					if (op1box.isSelected() == true) {
						op1box.doClick();
					}
					if (op2box.isSelected() == true) {
						op2box.doClick();
					}
					if (op3box.isSelected() == true) {
						op3box.doClick();
					}
					if (op5box.isSelected() == true) {
						op5box.doClick();
					}
					// to prevent the user messing with the checkboxes in the operation, the other opboxes are
					// disabled to they cant be checked, thus ensuring that the opcode 02 = 000010 = STR
					op0box.setEnabled(false);
					op1box.setEnabled(false);
					op2box.setEnabled(false);
					op3box.setEnabled(false);
					op5box.setEnabled(false);
				}
				
				// when its deselected, the op4box is unchecked, and the rest of the opboxes are
				// enabled along with the load button, thus resetting the checkboxes and buttons. 
				// it then reads "Store" to tell the user that it isn't on. 
				if (!tgl_store.isSelected()) {
					op4box.doClick();
					tgl_store.setText("Store");
					tgl_load.setEnabled(true);
					op0box.setEnabled(true);
					op1box.setEnabled(true);
					op2box.setEnabled(true);
					op3box.setEnabled(true);
					op5box.setEnabled(true);
				}
			}
		});

		tgl_store.setFont(new Font("Times New Roman", Font.BOLD, 9));
		tgl_store.setBounds(350, 187, 89, 23);
		frame.getContentPane().add(tgl_store);
		
		// Same thing going on here except for the fact that it is now ensuring that the checkboxes
		// represent opcode 01 = 0000001 = LDR
		tgl_load = new JToggleButton("Load");
		tgl_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tgl_load.isSelected()) {
					tgl_store.setEnabled(false);
					tgl_load.setText("STOP");
					if (op5box.isSelected() == false) {
						op5box.doClick();
					}
					if (op0box.isSelected() == true) {
						op0box.doClick();
					}
					if (op1box.isSelected() == true) {
						op1box.doClick();
					}
					if (op2box.isSelected() == true) {
						op2box.doClick();
					}
					if (op3box.isSelected() == true) {
						op3box.doClick();
					}
					if (op4box.isSelected() == true) {
						op4box.doClick();
					}
					op0box.setEnabled(false);
					op1box.setEnabled(false);
					op2box.setEnabled(false);
					op3box.setEnabled(false);
					op4box.setEnabled(false);
				}
				if (!tgl_load.isSelected()) {
					op5box.doClick();
					tgl_load.setText("Load");
					tgl_store.setEnabled(true);
					op0box.setEnabled(true);
					op1box.setEnabled(true);
					op2box.setEnabled(true);
					op3box.setEnabled(true);
					op4box.setEnabled(true);
				}
			}
		});
		tgl_load.setFont(new Font("Times New Roman", Font.BOLD, 9));
		tgl_load.setBounds(350, 210, 89, 23);
		frame.getContentPane().add(tgl_load);
		
		// Initial program load. 
		btn_ipl = new JButton("IPL"); 
		btn_ipl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// reaches out to the BackEnd class to use the ipl function which 
				// reads in the data from the program.txt, and executes various commands. 
				backEnd.iPL();
			}
		});
		btn_ipl.setFont(new Font("Times New Roman", Font.BOLD, 9));
		btn_ipl.setBounds(350, 233, 89, 23);
		frame.getContentPane().add(btn_ipl);
		
		// run toggle, if ss button is also on, then the run toggle just executes one cycle.
		// if not, then it runs the whole cycle until something tells it to stop such as a halt. 
		// all other buttons are disabled so the program wont crash during execution. 
		tgl_run = new JToggleButton("RUN");
		tgl_run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tgl_ss.isSelected()) {
					backEnd.ssInstruction();
				}
				else {
					if (tgl_run.isSelected()) {
						backEnd.runGUI(true);
						load_gpr0.setEnabled(false);
						load_gpr1.setEnabled(false);
						load_gpr2.setEnabled(false);
						load_gpr3.setEnabled(false);
						load_ixr1.setEnabled(false);
						load_ixr2.setEnabled(false);
						load_ixr3.setEnabled(false);
						load_pc.setEnabled(false);
						load_mar.setEnabled(false);
						load_mbr.setEnabled(false);
						tgl_store.setEnabled(false);
						tgl_load.setEnabled(false);
						btn_ipl.setEnabled(false);
						tgl_ss.setEnabled(false);
						tgl_run.setText("STOP");
						backEnd.runInstructions(); // uses the run instructions from backend
					}
					// when deselected, every other button/toggle becomes enabled. 
					if (!tgl_run.isSelected()) {
						backEnd.runGUI(false);
						load_gpr0.setEnabled(true);
						load_gpr1.setEnabled(true);
						load_gpr2.setEnabled(true);
						load_gpr3.setEnabled(true);
						load_ixr1.setEnabled(true);
						load_ixr2.setEnabled(true);
						load_ixr3.setEnabled(true);
						load_pc.setEnabled(true);
						load_mar.setEnabled(true);
						load_mbr.setEnabled(true);
						tgl_store.setEnabled(true);
						tgl_load.setEnabled(true);
						btn_ipl.setEnabled(true);
						tgl_ss.setEnabled(true);
						tgl_run.setText("RUN");
					}
				}
			}
		});
		tgl_run.setFont(new Font("Times New Roman", Font.BOLD, 9));
		tgl_run.setBounds(303, 184, 60, 29);
		frame.getContentPane().add(tgl_run);
		
		tgl_ss = new JToggleButton("SS");
		tgl_ss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tgl_ss.isSelected()) {
					tgl_ss.setText("STOP SS");
				}
				if (!tgl_ss.isSelected()) {
					tgl_ss.setText("SS");
				}
			}
		});
		tgl_ss.setFont(new Font("Times New Roman", Font.BOLD, 9));
		tgl_ss.setBounds(303, 207, 60, 29);
		frame.getContentPane().add(tgl_ss);
		
		address4box = new JCheckBox("");
		address4box.setFont(new Font("Times New Roman", Font.BOLD, 9));
		address4box.setBackground(Color.WHITE);
		address4box.setBounds(400, 152, 28, 23);
		frame.getContentPane().add(address4box);
		
		textField_keyboard = new JTextField();
		textField_keyboard.setHorizontalAlignment(SwingConstants.CENTER);
		textField_keyboard.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		textField_keyboard.setColumns(20);
		textField_keyboard.setBounds(73, 259, 163, 13);
		frame.getContentPane().add(textField_keyboard);
		
		consolePrinter = new JTextArea();
		consolePrinter.setText("");
		consolePrinter.setEditable(false);
		consolePrinter.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		consolePrinter.setWrapStyleWord(true);
		consolePrinter.setLineWrap(true);
		consolePrinter.setTabSize(4);
		consolePrinter.setBounds(63, 192, 180, 64);
		frame.getContentPane().add(consolePrinter);
		
		// read button. once entered 20, press read. then you can enter a find number and press find
		btn_read = new JButton("Read");
		btn_read.setEnabled(false);
		btn_read.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				input20 = textField_keyboard.getText();
				// if 20 numbers arent entered, it will ask the user to try again
				if (input20 == null || input20.split(",").length != 20) {
					consolePrinter.setText("Please try again: In the keyboard below, please enter 20 numbers seperated by commas.  Press Read when done. ");
				}
				// 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20
				// if 20 numbers are entered, it will then ask you to enter a number to search for
				if (input20.split(",").length == 20) {
					textField_keyboard.setText("");
					btn_find.setEnabled(true);
					consolePrinter.setText("You entered: ["+input20+"] Now enter a number you want to find");
					String[] stringArr = input20.split(",");
					int[] intArr = new int[stringArr.length];
					for (int i = 0; i < stringArr.length; i++) {
					    try {
					    	intArr[i] = Integer.parseInt(stringArr[i]);
					    } catch (NumberFormatException nfe) {
					        //NOTE: write something here if you need to recover from formatting errors
					    };
					}
					// adds the 20 numbers into the keyboard
					for (int i = 0; i <intArr.length; i++) {
						keyboard.add(intArr[i]);
					}
					btn_read.setEnabled(false);
				}
			}
		});
		btn_read.setFont(new Font("Times New Roman", Font.BOLD, 9));
		btn_read.setBounds(14, 216, 36, 11);
		frame.getContentPane().add(btn_read);
		
		
		// the find button gets enabled once 20 numbers are successfully read and inputted into the keyboard.
		// after you enter another number on the keyboard, you can press find and runProgram1() will run and return the textarea of program1
		// textarea of program one will be converted into a general purpose console register so that it can return manny messages such as errors,
		// it could potentially return code inputted into the IR as binary, hex, and decimal formats, it can also potentially give instructions on how 
		// to use buttons such as by printing out that "SS" button is on and that the user should press it to turn it off.
		// lastly, it will also have to work with program 2 in part III. 
		
		// at this moment in time, program 1 only runs once as I need to figure out how to successfully reset the keyboard. 
		// to run it again, exit out of the application and run it again on your IDE, or JAR file. 
		btn_find = new JButton("Find");
		btn_find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				find_program1 = textField_keyboard.getText();
				consolePrinter.setText("Finding: "+find_program1+" in ["+input20+"] Please wait...");
				List<Integer> keyboard1 = new ArrayList<Integer>();
				keyboard1.add(Integer.parseInt(find_program1));
				// keyboard 1 added the search value first, and then the 20 following numbers after
				// this makes the first value the first to be returned upon executing opcode IN so that it can
				// be compared between all 20 values 
				keyboard1.addAll(keyboard);
				keyboard = keyboard1;
				backEnd.runProgram1();
				tgl_run.doClick();
				consolePrinter.setText("Finding: "+find_program1+" in ["+input20+"] Closest Number: " + backEnd.ixr1 + " Difference of: " + backEnd.gpr3);
				btn_find.setEnabled(false);
				btn_p1.setEnabled(true);
				btn_p2.setEnabled(true);
				textField_keyboard.setText("");
			}
		});
		btn_find.setEnabled(false);
		btn_find.setFont(new Font("Times New Roman", Font.BOLD, 9));
		btn_find.setBounds(14, 233, 36, 11);
		frame.getContentPane().add(btn_find);
		
		btn_p1 = new JButton("Program 1"); // starts program 1 
		btn_p1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_keyboard.setText("");
				keyboard.clear();
				
				// initializes the instructions
				consolePrinter.setText("In the keyboard below, please enter 20 numbers seperated by commas.  Press Read when done. ");
				btn_read.setEnabled(true);
				btn_p1.setEnabled(false);
				btn_p2.setEnabled(false);
			}
		});
		btn_p1.setFont(new Font("Times New Roman", Font.BOLD, 9));
		btn_p1.setBounds(6, 199, 48, 11);
		frame.getContentPane().add(btn_p1);
		
		// initializes program 2
		btn_p2 = new JButton("Program 2");
		btn_p2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_keyboard.setText("");
				keyboard.clear();
				
				btn_p1.setEnabled(false);
				btn_p2.setEnabled(false);
				btn_search.setEnabled(true);
				// displays the paragraph on the console printer and asks for the user to search for a word. 
				consolePrinter.setText("This is a first sentence. Second line for program two. How does three sound. Or what about four. Just kidding heres five. Final time I promise."
						+ "                                                                                                           Enter a word & press search");
			}
		});
		btn_p2.setFont(new Font("Times New Roman", Font.BOLD, 9));
		btn_p2.setBounds(252, 216, 48, 11);
		frame.getContentPane().add(btn_p2);
		
		btn_search = new JButton("Search");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gets the word entered
				String searchWord = textField_keyboard.getText();
				// converts it to lowercase
				searchWord = searchWord.toLowerCase();
				int inter = 0;
				// converts it to binary for each letter, and then adds them up to get a distinct value for the word
				for(int i = 0; i < searchWord.length(); i++) {
					inter = inter + (int)searchWord.charAt(i);
				}
				
				// string of the paragraph. 
				String para = "This is a first sentence. Second line for program two. How does three sound. Or what about four. Just kidding heres five. Final time I promise.";
				
				// empty string list
				List<String> listInter = new ArrayList<String>();
				// converts it to lowercase
				String paraInter = para.toLowerCase();
				// splits the words apart, removes whitespaces, and appends them into an array
				String[] wordsInter = paraInter.split("\\s+");
				for(int i = 0; i < wordsInter.length; i++) {
					wordsInter[i] = wordsInter[i].replaceAll("[^\\w]", "");
					listInter.add(wordsInter[i]);
				}	
				// same thing happens here. the string is lowercased and then split apart and put into an array
				String para1 = para.toLowerCase();
				String[] words = para1.split("\\s+");
				for (int i = 0; i < words.length; i++) {
				    words[i] = words[i].replaceAll("[^\\w]", "");
				}
				
				// empty integer list
				List<Integer> wordsToints = new ArrayList<Integer>();
				
				// for each word, the letters are converted into binary value and they are then added together
				// giving each word their distinct value (I made sure that all words within the paragraph
				// were different and that their values were different as well so this method doesn't work for 
				// all paragraphs. 
				for(int i = 0; i < words.length; i++) {
					String buffer = words[i];
					int intermediate = 0;
					for(int j = 0; j < buffer.length(); j++) {
						intermediate = intermediate + (int)buffer.charAt(j);}
					wordsToints.add(intermediate);}
				
				// if the word entered isnt within the paragraph, the console printer asks the user to try again
				if (listInter.contains(searchWord) == false) {
					consolePrinter.setText("This is a first sentence. Second line for program two. How does three sound. Or what about four. Just kidding heres five. Final time I promise."
							+ "                                                                                                          Word not found, try again.");
				}
				
				else {
					// if the word is in the paragraph then the values for each word are added together where the search word is the first in the index
					List<Integer> keyboardInter = new ArrayList<Integer>();
					keyboardInter.add(inter);
					keyboardInter.addAll(wordsToints);
					// they get added into the keyboard variable so they can be used for IN instructions
					keyboard = keyboardInter;
					// console printer tells user that it is searching for the word. 
					consolePrinter.setText("This is a first sentence. Second line for program two. How does three sound. Or what about four. Just kidding heres five. Final time I promise."
							+ "                                                                                                          Searching for " + searchWord);
					btn_search.setEnabled(false);
					btn_p1.setEnabled(true);
					btn_p2.setEnabled(true);
					// runs the program2 method to read in the instructions
					backEnd.runProgram2();
					// presses the run so the instruction cycle can execute. the sentence number and word number are displayed in gpr3 and gpr2 respectively. 
					tgl_run.doClick();
					textField_keyboard.setText("");
					consolePrinter.setText("This is a first sentence. Second line for program two. How does three sound. Or what about four. Just kidding heres five. Final time I promise."
							+ "                                                                                                          Word: (" + searchWord + ") is word #" + backEnd.gpr2 + " in sentence #" + backEnd.gpr3);
				}
			}
		});
		btn_search.setFont(new Font("Times New Roman", Font.BOLD, 9));
		btn_search.setEnabled(false);
		btn_search.setBounds(252, 228, 36, 11);
		frame.getContentPane().add(btn_search);
	}
}