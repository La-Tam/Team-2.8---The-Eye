package vn.vanlanguni.ponggame;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SecondWindow extends JDialog{
	private JTextField txtUsername1;
	private JTextField txtUsername2;
	//Xem khai bao MyDialogResult o cuoi class nay
	public MyDialogResult dialogResult;
	private JLabel lblChooseColorPaddle = new JLabel("Set Color Paddle: ");
	private JLabel lblChooseBall = new JLabel("Set Ball: ");
	private JRadioButton radSoccer = new JRadioButton("Soccer" );
			JRadioButton radVolley = new JRadioButton("Volleyball");
			JRadioButton radBBall = new JRadioButton("BasketBall ");
			JRadioButton radIr = new JRadioButton("Ironman");
			JRadioButton raddog = new JRadioButton("Dog ");
			JRadioButton radAni = new JRadioButton("Anime");
			private	ButtonGroup groupball = new ButtonGroup();
	

	
	//add color
	Color paddleColor;
	public SecondWindow() {
		setPreferredSize(new Dimension(400, 450));
		setTitle("Second Window");
		getContentPane().setLayout(null);
		setModal(true);
		
		dialogResult = MyDialogResult.DEFAULT;
		txtUsername1 = new JTextField("Play 1");
		txtUsername2 = new JTextField("Play 2");
		getContentPane().add(txtUsername1);
		getContentPane().add(txtUsername2);
		txtUsername1.setBounds(90, 26, 100, 20);
		txtUsername2.setBounds(90, 66, 100, 20);
		
		JLabel lblUser_1 = new JLabel("Username 1");
		lblUser_1.setBounds(10, 29, 71, 14);
		getContentPane().add(lblUser_1);
		
		JLabel lblUser_2 = new JLabel("Username 2");
		lblUser_2.setBounds(10, 69, 71, 14);
		getContentPane().add(lblUser_2);
		add(radSoccer);
		add(radVolley);
		add(radBBall);
		add(radAni);
		add(radIr);
		add(raddog);
		radSoccer.setBounds(10,180,90,23);
		radBBall.setBounds(10,200,90,23);
		radVolley.setBounds(10,220,90,23);
		/*radSoccer.setActionCommand(null);
		radBBall.setActionCommand(null);
		radVolley.setActionCommand(null);*/
		radIr.setBounds(120, 180, 90, 23);
		raddog.setBounds(120, 200, 90, 23);
		radAni.setBounds(120, 220, 90, 23);
		add(lblChooseBall);
		lblChooseBall.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		lblChooseBall.setBounds(5, 150, 300, 35);
		
		add(lblChooseColorPaddle);
		lblChooseColorPaddle.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		lblChooseColorPaddle.setBounds(5, 300, 250, 35);

		  groupball.add(radBBall);
		    groupball.add(radSoccer);
		    groupball.add(radVolley);
		    groupball.add(radAni);
		    groupball.add(raddog);
		    groupball.add(radIr);
		radSoccer.setSelected(true);
		   
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialogResult = MyDialogResult.YES;
				setVisible(false);
			}
		});
		btnSave.setBounds(44, 114, 89, 23);
		getContentPane().add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogResult = MyDialogResult.CANCEL;
				setVisible(false);
			}
		});
		btnCancel.setBounds(154, 114, 89, 23);
		getContentPane().add(btnCancel);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				int result = JOptionPane.showConfirmDialog(SecondWindow.this, "Exit?");
				if(result == JOptionPane.YES_OPTION){
					setVisible(false);
				}				
			}
		});
		final JButton btnPad=new JButton("Chose Color");
		add(btnPad);
		btnPad.setBounds(10,350,150,25);
		btnPad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Color Colorchoose= JColorChooser.showDialog(btnPad,"Choose paddle color",Color.RED);
				paddleColor=Colorchoose;
			}
		});

	
	}
	

	public Settings getSetings(){
		Settings st = new Settings();
		st.setUserName1(txtUsername1.getText());
		st.setUserName2(txtUsername2.getText());
		//st.setBallNumber(1);
		
		if (radSoccer.isSelected()) {
			st.setBallNumber(1);
		} else if (radBBall.isSelected()) {
			st.setBallNumber(2);
		}else if(radVolley.isSelected()){
			st.setBallNumber(3);
	
			} else if (radIr.isSelected()) {
			st.setBallNumber(4);
		}else if(raddog.isSelected()){
			st.setBallNumber(5);
		}else if(radAni.isSelected()){
			st.setBallNumber(6);}
	
		st.setPaddleColor(paddleColor);
		return st;
	}


	protected int ImageIcon(String string) {
		// TODO Auto-generated method stub
		return 0;
	}
}

