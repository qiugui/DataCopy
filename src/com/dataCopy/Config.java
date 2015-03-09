package com.dataCopy;

import javax.swing.JDialog;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
public class Config {
	public void dialog(){
		JDialog jDialog = new JDialog(Main.frame, "设置", ModalityType.APPLICATION_MODAL);
		jDialog.setSize(400, 250);
		Dimension dialogSize = jDialog.getSize();
		jDialog.setLocation((Main.displaySize.width - dialogSize.width) / 2,  
	(Main.displaySize.height - dialogSize.height) / 2);
		
		jDialog.setVisible(true);
	}
}
