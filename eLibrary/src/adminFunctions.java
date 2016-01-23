import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class adminFunctions extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private bookUpdation bu;
	private bookCreation bc;
	private JTabbedPane tp;

	public adminFunctions(final DBConnections dbc, final homePage hp){
		this.setLayout(new BorderLayout());
		
		tp = new JTabbedPane();
		bu = new bookUpdation(dbc, hp);
		bc = new bookCreation(dbc);
		
		this.tp.add("Book Management", bu);
		this.tp.add("Add a Book", bc);
		this.add(tp);
	}
}

class bookCreation extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton createBookButton;
	private JLabel bookNameLabel, bookDeptLabel;
	private JTextField bookNameField;
	private JComboBox<String> box;
	private String[] depts = {"CSE","ECE","MECH","CIVIL","MME","CHEM", "OTHERS"};

	public bookCreation(final DBConnections dbc){
		this.setLayout(null);
		
		this.bookNameLabel = new JLabel("Enter the Book Name :");
		this.bookNameField = new JTextField();
		this.bookDeptLabel = new JLabel("Choose Book's Department :");
		this.box = new JComboBox<String>(depts);
		this.createBookButton = new JButton("Add Book");
		
		this.createBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!bookNameField.getText().equals("")){
					if (dbc.insertABook(bookNameField.getText(), box.getSelectedItem().toString())){
						JOptionPane.showMessageDialog(null, "Book Insertion Successful.");
						bookNameField.setText("");
					}
					else{
						JOptionPane.showMessageDialog(null, "Book Insertion Failed.\nBook may be already in the library.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please Enter Book Name.");
				}
			}
		});
		
		this.bookNameLabel.setBounds(160, 120, 200, 30);
		this.bookNameField.setBounds(320, 120, 250, 30);
		this.bookDeptLabel.setBounds(180, 180, 200, 30);
		this.box.setBounds(400, 180, 100, 30);
		this.createBookButton.setBounds(280, 250, 120, 30);
		
		this.add(this.bookDeptLabel);
		this.add(this.bookNameField);
		this.add(this.bookNameLabel);
		this.add(this.box);
		this.add(this.createBookButton);		
	}
}

class bookUpdation extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton searchButton, resetButton, logOutButton, deleteBookButton, freeThisBookButton;
	private JTextField searchText;
	private String[] depts = {"All","CSE","ECE","MECH","CIVIL","MME","CHEM", "OTHERS"};
	private JComboBox<String> searchBox;
	private JPanel viewerPanel, treePanel, bookPanel;
	private JLabel bookImageLabel, bookNameLabel, bookIDLabel, getBookNameLabel, getBookIDLabel;
	private JTree tree;
	private Border bdr = BorderFactory.createEtchedBorder();
	private DefaultTreeModel treeModel;
	private DBConnections dbc;
	private DefaultMutableTreeNode root, cseNode, eceNode, mechNode, civilNode, mmeNode,chemNode, othersNode;
	private StringTokenizer st;
	private JScrollPane treeScrollPane;
	private int allRows = 7;
	
	public bookUpdation(final DBConnections dbc, final homePage hp){
		this.setLayout(null);
		this.dbc = dbc;
		
		this.searchText = new JTextField();
		this.searchBox = new JComboBox<String>(depts);
		this.searchButton = new JButton("Search");
		this.resetButton = new JButton("Reset");
		this.logOutButton = new JButton("Log Out");
		
		this.resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createTree("", "ALL");
				searchText.setText("");
				searchBox.setSelectedIndex(0);
			}
		});
		
		this.searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				createTree(searchText.getText(), searchBox.getSelectedItem().toString().toUpperCase());
			}
		});
		
		this.logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hp.showSplashScreen();
			}
		});
		
		this.viewerPanel = new JPanel();
		this.viewerPanel.setLayout(null);
		this.viewerPanel.setBorder(bdr);
		
		this.treePanel = new JPanel();
		this.bookPanel = new JPanel();
		this.bookPanel.setBorder(bdr);
		this.bookPanel.setLayout(null);
		
		this.bookImageLabel = new JLabel();
		this.bookImageLabel.setBorder(bdr);
		this.bookNameLabel = new JLabel("Book Name :");
		this.bookIDLabel = new JLabel("Book ID :");
		this.getBookNameLabel = new JLabel();
		this.getBookIDLabel = new JLabel();
		this.deleteBookButton = new JButton("Delete This Book");
		this.freeThisBookButton = new JButton("Free This Book");
		this.deleteBookButton.setEnabled(false);
		this.freeThisBookButton.setEnabled(false);
		
		this.deleteBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dbc.deleteTheBook(getBookIDLabel.getText())){
					createTree("", "ALL");
					deleteBookButton.setEnabled(false);
					getBookIDLabel.setText("");
					getBookNameLabel.setText("");
				}
				else{
					JOptionPane.showMessageDialog(null, "Error Deleting the Book.\n Please Try Again Later.");
				}
			}
		});
		
		this.freeThisBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dbc.freeThisBook(getBookIDLabel.getText());
				JOptionPane.showMessageDialog(null, "Book is now available to all users.");
				freeThisBookButton.setEnabled(false);
			}
		});
		
		this.tree = new JTree();
		this.tree.setModel(treeModel);
		this.tree.putClientProperty("JTree.lineStyle", "Angled");
		this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);;
		treeScrollPane = new JScrollPane(this.tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				String nodeName = node.getUserObject().toString();
				
				if (Character.isDigit(nodeName.charAt(0))){
					st = new StringTokenizer(nodeName, "---");
					String bookDetails = dbc.searchBook(st.nextToken());
					st = new StringTokenizer(bookDetails, "&&&");
					
					getBookNameLabel.setText(st.nextToken());
					getBookIDLabel.setText(st.nextToken());
					deleteBookButton.setEnabled(true);
					st.nextToken();
					if (!st.nextToken().equals("1234567890")){
						freeThisBookButton.setEnabled(true);
					}
					else{
						freeThisBookButton.setEnabled(false);
					}
				}
			}
		});
		
		root = new DefaultMutableTreeNode("Books Available");
		cseNode = new DefaultMutableTreeNode("Cse Books");
		eceNode = new DefaultMutableTreeNode("Ece Books");
		mechNode = new DefaultMutableTreeNode("Mech Books");
		civilNode = new DefaultMutableTreeNode("Civil Books");
		mmeNode = new DefaultMutableTreeNode("Mme Books");
		chemNode = new DefaultMutableTreeNode("Chemical Books");
		othersNode = new DefaultMutableTreeNode("Other Books");
		
		this.treeModel = new DefaultTreeModel(root);
		this.treeModel.insertNodeInto(cseNode, root, 0);
		this.treeModel.insertNodeInto(eceNode, root, 1);
		this.treeModel.insertNodeInto(mechNode, root, 2);
		this.treeModel.insertNodeInto(civilNode, root, 3);
		this.treeModel.insertNodeInto(mmeNode, root, 4);
		this.treeModel.insertNodeInto(chemNode, root, 5);
		this.treeModel.insertNodeInto(othersNode, root, 6);
		
		createTree("", "ALL");
		
		this.treePanel.setLayout(new BorderLayout());
		this.treePanel.setBorder(bdr);
		
		this.searchText.setBounds(30, 5, 300, 30);
		this.searchBox.setBounds(335, 5, 100, 30);
		this.searchButton.setBounds(440, 5, 90, 30);
		this.resetButton.setBounds(535, 5, 90, 30);
		this.logOutButton.setBounds(630, 5, 90, 30);
		
		this.bookImageLabel.setBounds(60, 20, 100, 120);
		this.bookNameLabel.setBounds(10, 150, 100, 30);
		this.bookIDLabel.setBounds(10, 210, 100, 30);
		this.getBookNameLabel.setBounds(25, 180, 190, 30);
		this.getBookIDLabel.setBounds(25, 235, 190, 30);
		this.deleteBookButton.setBounds(20, 280, 180, 30);
		this.freeThisBookButton.setBounds(20, 325, 180, 30);
		
		this.treePanel.setBounds(5, 5, 450, 370);
		this.bookPanel.setBounds(460, 5, 223, 368);
		
		this.viewerPanel.setBounds(30, 40, 690, 380);
		
		this.bookPanel.add(this.freeThisBookButton);
		this.bookPanel.add(this.bookIDLabel);
		this.bookPanel.add(this.getBookIDLabel);
		this.bookPanel.add(this.bookImageLabel);
		this.bookPanel.add(this.bookNameLabel);
		this.bookPanel.add(this.getBookNameLabel);
		this.bookPanel.add(this.deleteBookButton);
		
		this.treePanel.add(this.treeScrollPane);
		
		this.add(this.searchButton);
		this.add(this.resetButton);
		this.add(this.logOutButton);
		this.add(this.searchBox);
		this.add(this.searchText);
		this.add(this.viewerPanel);
		this.viewerPanel.add(this.treePanel);
		this.viewerPanel.add(this.bookPanel);
	}
	
	public void createTree(String searchString, String searchDept){
		
		removeAllNodeData();
		String books = dbc.getBooks(searchString, searchDept);
		st = new StringTokenizer(books, "&&&");
		int[] countArray = {0, 0 , 0, 0, 0, 0, 0};
		
		if (st.countTokens() == 0){
			JOptionPane.showMessageDialog(null, "No Search Results found!!!\nRedirectering to Default View...");
			createTree("", "ALL");
			this.searchBox.setSelectedIndex(0);
		}
		
		while (st.hasMoreTokens()){
			
			String bookname = st.nextToken();
			String bookid = st.nextToken();
			String bookdept = st.nextToken();
			
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(bookid + "---" + bookname);
			this.allRows++;
			if (bookdept.equals("CSE")){
				this.treeModel.insertNodeInto(node, cseNode, countArray[0]++);
			}
			else if (bookdept.equals("ECE")){
				this.treeModel.insertNodeInto(node, eceNode, countArray[1]++);
			}
			else if (bookdept.equals("MECH")){
				this.treeModel.insertNodeInto(node, mechNode, countArray[2]++);
			}
			else if (bookdept.equals("CIVIL")){
				this.treeModel.insertNodeInto(node, civilNode, countArray[3]++);
			}
			else if (bookdept.equals("MME")){
				this.treeModel.insertNodeInto(node, mmeNode, countArray[4]++);
			}
			else if (bookdept.equals("CHEM")){
				this.treeModel.insertNodeInto(node, chemNode, countArray[5]++);
			}
			else{
				this.treeModel.insertNodeInto(node, othersNode, countArray[6]++);
			}
		}
		expandAllNodes();
	}
	
	public void expandAllNodes(){
		for (int i = 0; i < this.allRows; i++){
			this.tree.expandRow(i);
		}
	}
	
	public void removeAllNodeData(){
		this.cseNode.removeAllChildren();
		this.eceNode.removeAllChildren();
		this.mechNode.removeAllChildren();
		this.civilNode.removeAllChildren();
		this.mmeNode.removeAllChildren();
		this.chemNode.removeAllChildren();
		this.othersNode.removeAllChildren();
		this.allRows = 7;
		this.tree.setModel(null);
		this.tree.setModel(treeModel);
	}
}
