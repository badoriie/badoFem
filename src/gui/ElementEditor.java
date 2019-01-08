package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


import fem.*;

public class ElementEditor extends JFrame {
	
	private static final long serialVersionUID = 8565119204890831294L;
	
	private JLabel eModulus,crossSection;
	private JTextField eModulusT,crossSectionT;
	private JButton ok,cancel,delete;


	public ElementEditor(final int elementID, final Structure struct){
		
		
		super( "ElementEditor" );
		
		
		GridLayout gridLayout = new GridLayout( 4, 2, 5, 5 );
		setLayout(gridLayout);
		
		eModulus = new JLabel(" eModulus: ");
		add(eModulus);
		
		crossSection = new JLabel(" Cross-Section: ");
		add(crossSection);
		
		eModulusT = new JTextField(String.valueOf(struct.getElement(elementID).getEModulus()));
		add(eModulusT);
		
		crossSectionT = new JTextField(String.valueOf(struct.getElement(elementID).getArea()));
		add(crossSectionT);
		
		ok = new JButton("OK");
		add(ok);
		
		ok.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			    struct.getElement(elementID).setEModulus(Double.parseDouble(eModulusT.getText()));
				struct.getElement(elementID).setArea(Double.parseDouble(crossSectionT.getText()));
				dispose();
			}
		});
		
		cancel = new JButton("Cancel");
		add(cancel);
		
		cancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		delete = new JButton("Delete");
		add(delete);
		
		delete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				struct.deleteElement(elementID);
				dispose();
			}
		});
	}
	
}
