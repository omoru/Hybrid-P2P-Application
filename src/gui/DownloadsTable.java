package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import client_src.Client;



public class DownloadsTable extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable table;
	public DownloadsTable(Client ctrlClient) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(	BorderFactory.createLineBorder(Color.black, 2), "Downloads",TitledBorder.LEFT
		,TitledBorder.TOP)); 
		
		DownloadsTableModel download_table = new DownloadsTableModel(ctrlClient);
		this.table = new JTable(download_table);
		add(new JScrollPane(table));
		this.setMaximumSize(new Dimension(800,200));
		this.setPreferredSize(new Dimension(800,200));
		
	}

}
