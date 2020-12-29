import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SlotMachine extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new SlotMachine();
	}
	
	private JLabel spinner1;	// Left digit display
	private JLabel spinner2;	// Middle digit display
	private JLabel spinner3;	// Right digit display
	private JLabel result1;
	private JLabel result2;
	
	private Spinner aa;         // First spinner
	private Spinner bb;         // Second spinner
	private Spinner cc;         // Third spinner
	
	private int[] values;		// Digit values
	
	private int count;

	private JButton startStop;	// Button to start or stop spinner(s).
		
	// Constructor
	public SlotMachine() {
		super("Slots O' FUN!!!");
		values = new int[3];
		makeFrame();
		count = 0;
	}
    
    // Spinner class 
	private class Spinner extends Thread{
		public boolean running = true;
        
        // Increments value on each spinner at varying rates.
		public synchronized void run() {
			while(true) {
				if(currentThread().equals(aa)) {
					if(aa.running) {
						values[0] = (values[0] + 1) % 10;
						spinner1.setText(values[0] + "");
						try {
							Thread.sleep(250);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(currentThread().equals(bb)) { 
					if(bb.running) {
						values[1] = (values[1] + 1) % 10;
						spinner2.setText(values[1] + "");
						try {
							Thread.sleep(265);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					}
				}
				
				if(currentThread().equals(cc)) {
					if(cc.running) {
						values[2] = (values[2] + 1) % 10;
						spinner3.setText(values[2] + "");
						try {
							Thread.sleep(280);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	// Makes all three digits start spinning.
	public synchronized void startSpinning() {
		if(aa == null) {
			aa = new Spinner();
			aa.start();
		}
			
 		if(bb == null) {
			bb = new Spinner();
			bb.start();
 		}
 		
 		if(cc == null) {
			cc = new Spinner();
			cc.start();		
		}
 		
	}
	
	// Makes one digit stop spinning.
	// If all digits stop, displays a message if all three digits are the same.
	public void stopSpinning() {
		count ++;
		if(aa.running || bb.running || cc.running) {
			if(count == 1) {
				aa.running = false;
			} else if(count == 2) {
				bb.running = false;
			}else if(count == 3) {
				cc.running = false;
                
                // End game message
				if(spinner1.getText().equals(spinner2.getText()) && spinner2.getText().equals(spinner3.getText())) {
					JOptionPane.showMessageDialog(null,"CONGRATULATIONS!!!! $$$$$$$$");
				} else {
					JOptionPane.showMessageDialog(null, "Dude, you suck :/");
				}
							
				count = 0;
				startStop.setText("START");
				aa = null;
				bb = null;
				cc = null;
			}
		}
	}
	

	// Called when Start/Stop button is clicked.
	public void actionPerformed(ActionEvent e) {
		if(startStop.getText().equals("START")) {
			startStop.setText("STOP");
			startSpinning();
		} else {
			stopSpinning();
		}
	}
			
	
	// Builds and launches window
	private void makeFrame() {
		setLayout(new BorderLayout(5, 5));
		
		JPanel spinnerPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		spinner1 = new JLabel("0", JLabel.CENTER);
		spinner1.setFont(new Font(null, Font.BOLD, 40));
		spinnerPanel.add(spinner1);
		
		spinner2 = new JLabel("0", JLabel.CENTER);
		spinner2.setFont(new Font(null, Font.BOLD, 40));
		spinnerPanel.add(spinner2);
		
		spinner3 = new JLabel("0", JLabel.CENTER);
		spinner3.setFont(new Font(null, Font.BOLD, 40));
		spinnerPanel.add(spinner3);
		
		add(spinnerPanel, BorderLayout.CENTER);
		
		startStop = new JButton("START");
		startStop.setFont(new Font(null, Font.ITALIC, 20));
		startStop.addActionListener(this);
		add(startStop, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(725, 350);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}