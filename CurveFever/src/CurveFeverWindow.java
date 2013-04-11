import gui.CurveFeverPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class CurveFeverWindow extends JFrame {
	private static final long serialVersionUID = 2853666325233320713L;
	CurveFeverPanel curvePanel;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CurveFeverWindow gui = new CurveFeverWindow();
				gui.setLocationRelativeTo(null);
			}
		});	 	
	}
	
	/*	 * 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					this.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
	
	public CurveFeverWindow() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				setVisible(false);
				dispose();
				System.exit(0);
			}
		});
		
		this.setVisible(true);
		this.setResizable(true);
		this.setBounds(100, 100, 1024, 600);
		this.setTitle("CurveFever - DHBW Edition");
		
		curvePanel = new CurveFeverPanel();
		this.getContentPane().add(curvePanel);
		curvePanel.setLayout(null);
		
		this.repaint();
		
	}
}
