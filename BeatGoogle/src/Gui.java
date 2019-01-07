import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class Gui extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea areaContent;
	private String buttonString;
	private static final Color purple = new Color(72, 61, 139);
	private static final double weight = 20;
	private static final double weight2 = 2;
	public Keyword keyword;
	public ArrayList<Keyword> keywords;
	public JButton btnSearch = new JButton("Search");
	public JTextField textField = new JTextField();
	private JScrollPane scrollPane;
	private JPanel wholePane;

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
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		final JButton btnNba = new JButton("NBA");
		btnNba.setForeground(purple);
		btnNba.setBounds(350, 360, 55, 30);

		final JButton btnMlb = new JButton("MLB");
		btnMlb.setForeground(purple);
		btnMlb.setBounds(405, 360, 55, 30);

		final JButton btnNfl = new JButton("NFL");
		btnNfl.setForeground(purple);
		btnNfl.setBounds(460, 360, 55, 30);

		final JButton btnNhl = new JButton("NHL");
		btnNhl.setForeground(purple);
		btnNhl.setBounds(515, 360, 55, 30);

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

		textField.setBounds(350, 330, 220, 30);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblBallballgo = new JLabel("BallBallGO!");
		lblBallballgo.setFont(new Font("Corsiva Hebrew", Font.PLAIN, 24));
		lblBallballgo.setForeground(Color.WHITE);
		lblBallballgo.setBounds(395, 305, 135, 30);
		contentPane.add(lblBallballgo);

		btnSearch.setBounds(570, 330, 75, 30);
		contentPane.add(btnSearch);
		btnSearch.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		/*
		 * JScrollPane scrollPane = new JScrollPane( areaContent,
		 * ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
		 * ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		 * 
		 * this.getContentPane().removeAll(); this.getContentPane().add(scrollPane);
		 * 
		 * scrollPane.getVerticalScrollBar().setBackground(purple);
		 * scrollPane.getViewport().setBackground(Color.PINK);
		 * setContentPane(scrollPane); setVisible(true);
		 * 
		 * btnSearch.setBounds(240, 10, 75, 30); scrollPane.add(btnSearch);
		 * 
		 * textField.setBounds(10, 13, 220, 20); scrollPane.add(textField);
		 * textField.setColumns(10);
		 * 
		 * areaContent = new JTextArea(); areaContent.setBounds(10, 60, 1190, 740);
		 * areaContent.setBackground(Color.PINK); areaContent.setLineWrap(true);
		 * scrollPane.add(areaContent); areaContent.setColumns(10);
		 * 
		 * scrollPane.updateUI();
		 */

		keyword = new Keyword();
		keywords = new ArrayList<>();
		String input = textField.getText();
		int m = input.indexOf(" ");
		String input1 = input;
		String input2 = "";
		String text = "";

		// 擷取關鍵字
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
		// 有按按鈕
		if (buttonString != "") {
			keyword.addKeyword(new Keyword(buttonString, weight2, buttonString));
		} else {
			// 沒按按鈕直接加關鍵字
			keyword.addKeyword(new Keyword("sport", weight2, buttonString));
		}

		keywords = keyword.keywords;
		// 建list(google結果＋內建結果
		try {
			new HtmlMatcher(buttonString, text);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		// 新聞網站算分數
		WebList news = null;
		try {
			news = new WebList(keywords);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		areaContent = new JTextArea();
		areaContent.setBackground(Color.PINK);
		areaContent.setLineWrap(true);
		areaContent.setColumns(10);

		areaContent.setText(HtmlMatcher.relatedKeyword + "\n" + "Suggest Results: \n" + news.sort());

		wholePane = new JPanel();
		wholePane.setLayout(new BorderLayout());
		wholePane.setBounds(100, 100, 800, 600);
		this.setContentPane(wholePane);

		contentPane = new JPanel();

		textField.setColumns(10);
		contentPane.add(textField, BorderLayout.WEST);
		contentPane.add(btnSearch, BorderLayout.CENTER);
		contentPane.setBackground(Color.PINK);
		// textField.setBounds(50, 13, 220, 20);
		// btnSearch.setBounds(240, 10, 75, 30);

		scrollPane = new JScrollPane(areaContent);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getVerticalScrollBar().setBackground(purple);
		scrollPane.getViewport().setBackground(Color.PINK);

		wholePane.add(contentPane, BorderLayout.NORTH);
		wholePane.add(scrollPane, BorderLayout.CENTER);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wholePane.updateUI();
		this.setVisible(true);

	}

}
