import javax.swing.JFrame;

public class SFrame extends JFrame{
	
	SFrame(){
		SPanel Panel = new SPanel(); 
		this.add(Panel);
		this.setTitle("SnakeGame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		//this.setLocationRelativeTo(null);
	}
}
