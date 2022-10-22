package il.co.codeguru.corewars8086.gui;

import javax.swing.*;
import java.awt.*;

public class FlagFields extends JPanel {
	
	private final JCheckBox checkBox;
	
	public FlagFields(String name) {
		
		super.setLayout(new GridLayout(1,2));
		super.setSize(20,20);
		super.add(new JLabel(name + ":"),BorderLayout.LINE_START);
		
		checkBox = new JCheckBox(); 
		
		super.add(checkBox,BorderLayout.LINE_START);
	}
	
	public void setValue(boolean value){
		checkBox.setSelected(value);
	}
	
	public boolean getValue(){
		return checkBox.isSelected();
	}
	
}
