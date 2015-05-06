import gui.CurveFeverPanel;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class CurveFeverWindow extends JFrame {

	private static final long serialVersionUID = -5748280295741128123L;
	
	private static final int WINDOW_WIDTH = 1024;
	private static final int WINDOW_HEIGHT = 600;

	private CurveFeverPanel curvePanel;
	
	public CurveFeverWindow() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});

		this.setVisible(true);
		this.setResizable(true);
		this.setTitle("CurveFever - IUT Edition");
		this.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);

		this.curvePanel = new CurveFeverPanel();
		this.getContentPane().add(this.curvePanel);
		this.curvePanel.setLayout(new BorderLayout());

		this.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CurveFeverWindow();
			}
		});
	}

}