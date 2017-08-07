package game;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;// swing is a framework
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TypingTutor extends JFrame implements ActionListener, KeyListener {
	private JLabel lbltimer;
	private JLabel lblscore;
	private JLabel lblWord;
	private JTextField txtWord;
	private JButton btnStart;
	private JButton btnStop;

	private Timer clocktimer = null;
	private Timer wordtimer = null;
	private boolean running = false;
	private int timeRemaining = 0;
	private int score = 0;
	private String[] words = null;

	public TypingTutor(String[] args) {
		words = args;
		GridLayout layout = new GridLayout(3, 2);
		super.setLayout(layout);
		Font font = new Font("Agency FB", 1, 150);
		lbltimer = new JLabel("Time");
		lbltimer.setFont(font);
		super.add(lbltimer);

		lblscore = new JLabel("Score");
		lblscore.setFont(font);
		super.add(lblscore);

		lblWord = new JLabel("");
		lblWord.setFont(font);
		super.add(lblWord);

		txtWord = new JTextField("");
		txtWord.setFont(font);
		txtWord.addKeyListener(this);
		super.add(txtWord);

		btnStart = new JButton("Start");
		btnStart.setFont(font);
		btnStart.addActionListener(this);
		super.add(btnStart);

		btnStop = new JButton("Stop");
		btnStop.setFont(font);
		btnStop.addActionListener(this);
		super.add(btnStop);

		super.setTitle("Typing Tutor");
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setExtendedState(MAXIMIZED_BOTH);
		super.setVisible(true);
		setupthegame();
	}

	private void setupthegame() {
		clocktimer = new Timer(1000, this);
		clocktimer.setInitialDelay(0);
		wordtimer = new Timer(3000, this);
		wordtimer.setInitialDelay(0);
		running = false;
		timeRemaining = 20;
		score = 0;
		lbltimer.setText("Time: " + timeRemaining);
		lblscore.setText("Score: " + score);
		lblWord.setText("");
		txtWord.setText("");

		btnStop.setEnabled(false);
		txtWord.setEnabled(false);

	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {
			handelStart();
		} else if (e.getSource() == btnStop) {
			handelStop();
		} else if (e.getSource() == clocktimer) {
			handelclockTimer();
		} else if (e.getSource() == wordtimer) {
			handelwordTimer();
		}
	}

	private void handelclockTimer() {
		timeRemaining--;
		lbltimer.setText("Time: " + timeRemaining);
		if (timeRemaining == 0) {
			handelStop();
		}
	}

	private void handelwordTimer() {
		String actual = txtWord.getText();
		String expected = lblWord.getText();
		if (expected.length() > 0 && actual.equals(expected)) {
			score++;
		}
		lblscore.setText("Score: " + score);
		int ridx = (int) (Math.random() * words.length);
		lblWord.setText(words[ridx]);
		txtWord.setText("");
	}

	private void handelStart() {
		if (running == false) {
			clocktimer.start();
			wordtimer.start();
			running = true;
			btnStart.setText("Pause");
			txtWord.setEnabled(true);
			btnStop.setEnabled(true);
		} else {
			clocktimer.stop();
			wordtimer.stop();
			running = false;
			btnStart.setText("Start");
			txtWord.setEnabled(false);
			btnStop.setEnabled(false);
		}
	}

	private void handelStop() {
		clocktimer.stop();
		wordtimer.stop();
		int choice = JOptionPane.showConfirmDialog(this, "Do you want to continue? ");
		if (choice == JOptionPane.YES_OPTION) {
			setupthegame();
		} else if (choice == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(this, "Score is: " + score);
			super.dispose();
		} else if (choice == JOptionPane.CANCEL_OPTION) {
			if (timeRemaining == 0)
				setupthegame();
			else {
				clocktimer.start();
				wordtimer.start();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String actual = txtWord.getText();
		String expected = lblWord.getText();
		if (expected.length() > 0 && actual.equals(expected)) {
			score++;

			lblscore.setText("Score: " + score);
			int ridx = (int) (Math.random() * words.length);
			lblWord.setText(words[ridx]);
			txtWord.setText("");

			wordtimer.restart();
		}

	}
}
