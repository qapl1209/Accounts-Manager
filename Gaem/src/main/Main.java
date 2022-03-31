package main;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import static main.MainPanel.filepath;
import static state.AccountListState.saveData;

public class Main {

	public static void main(String[] args) {
		GraphicsEnvironment graphics =
		GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = graphics.getDefaultScreenDevice();
		
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(new MainPanel(), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);

		ShutDownTask shutDownTask = new ShutDownTask();
		Runtime.getRuntime().addShutdownHook(shutDownTask);
	}
	//save data upon closing of program
	/***************************************************************************************
 *    From Uli Köhler, available at https://stackoverflow.com/questions/5747803/running-code-on-program-exit-in-java
 ***************************************************************************************/
	private static class ShutDownTask extends Thread{
		@Override
		public void run(){
			saveData(filepath);
		}
	}
	
}
