import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea areaTitle;
	private JTextArea areaContent;
	private String buttonString;
	private static final Color purple = new Color(72, 61, 139);
	private static final double weight = 15;
	private static final double weight2 = 3;
	public Keyword keyword;
	public ArrayList<Keyword> keywords;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Gui() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		final JButton btnNba = new JButton("NBA");
		btnNba.setForeground(purple);
		btnNba.setBounds(330, 220, 55, 30);

		final JButton btnMlb = new JButton("MLB");
		btnMlb.setForeground(purple);
		btnMlb.setBounds(385, 220, 55, 30);

		final JButton btnNfl = new JButton("NFL");
		btnNfl.setForeground(purple);
		btnNfl.setBounds(440, 220, 55, 30);

		final JButton btnNhl = new JButton("NHL");
		btnNhl.setForeground(purple);
		btnNhl.setBounds(495, 220, 55, 30);

		buttonString = "";
		btnNba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonString = "NBA";
				btnNba.setForeground(Color.RED);
				btnMlb.setForeground(purple);
				btnNfl.setForeground(purple);
				btnNhl.setForeground(purple);
			}
		});
		contentPane.add(btnNba);

		btnMlb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonString = "MLB";
				btnMlb.setForeground(Color.RED);
				btnNba.setForeground(purple);
				btnNfl.setForeground(purple);
				btnNhl.setForeground(purple);
			}
		});
		contentPane.add(btnMlb);

		btnNfl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonString = "NFL";
				btnNfl.setForeground(Color.RED);
				btnNba.setForeground(purple);
				btnMlb.setForeground(purple);
				btnNhl.setForeground(purple);
			}
		});
		contentPane.add(btnNfl);

		btnNhl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonString = "NHL";
				btnNhl.setForeground(Color.RED);
				btnNba.setForeground(purple);
				btnMlb.setForeground(purple);
				btnNfl.setForeground(purple);
			}
		});
		contentPane.add(btnNhl);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(330, 190, 220, 30);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblBallballgo = new JLabel("BallBallGO!");
		lblBallballgo.setFont(new Font("Corsiva Hebrew", Font.PLAIN, 20));
		lblBallballgo.setForeground(Color.WHITE);
		lblBallballgo.setBounds(395, 150, 104, 30);
		contentPane.add(lblBallballgo);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(550, 190, 75, 30);
		contentPane.add(btnSearch);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.removeAll();

				areaTitle = new JTextArea();
				areaTitle.setBounds(10, 10, 220, 20);
				contentPane.add(areaTitle);
				areaTitle.setText(textField.getText());

				areaContent = new JTextArea();
				areaContent.setBounds(10, 60, 990, 700);
				areaContent.setBackground(Color.PINK);
				contentPane.add(areaContent);
				areaContent.setColumns(10);

				textField.setColumns(10);

				contentPane.updateUI();

				keyword = new Keyword();
				keywords = new ArrayList<>();
				String input = textField.getText();
				int m = input.indexOf(" ");
				String input1 = input;
				String input2 = "";
				String text = "";
				if (!input.isEmpty()) {
					while (input1.contains(" ")) {
						m = input1.indexOf(" ");
						input2 = input1.substring(m + 1, input1.length());
						input1 = input1.substring(0, m);

						keyword.addKeyword(new Keyword(input1, weight, buttonString));
						text = text + "+" + input1;

						input1 = input2;
					}
					keyword.addKeyword(new Keyword(input1, weight, buttonString));
					text = text + "+" + input1;
				}
				if (buttonString != "") {
					keyword.addKeyword(new Keyword(buttonString, weight2, buttonString));
					text = text + "+" + buttonString;
				} else {
					keyword.addKeyword(new Keyword("sport", weight2, buttonString));
					text = text + "+sport";
				}

				keywords = keyword.keywords;
				try {
					new HtmlMatcher(buttonString, text + "+" + buttonString + "+" + "news");
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				WebList news = null;
				try {
					news = new WebList(keywords);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				areaContent.setText(news.sort());
			}
		});
	}
}
