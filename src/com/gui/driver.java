package com.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.input.DirectoryFiles;
import com.input.InputTXR;

public class driver extends JFrame {
	private JButton select = new JButton("Select (.TXR) File");
	private JButton selectDir = new JButton("Select Directory/Folder");

	private File useFile;
	private File useFileDir;

	public driver() {
		initUI();
	}

	public final void initUI() {
		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenu about = new JMenu("About");

		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		file.add(eMenuItem);

		menubar.add(file);

		JMenuItem AuthorInfo = new JMenuItem("Author Info");
		JMenuItem version = new JMenuItem("V. 1.3 - Feb 2016");
		AuthorInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String url = "http://www.mgorshkov.com";
					java.awt.Desktop.getDesktop().browse(
							java.net.URI.create(url));
				} catch (java.io.IOException e) {
					System.out.println(e.getMessage());
				}
			}
		});

		about.add(AuthorInfo);
		about.add(version);

		menubar.add(about);

		setJMenuBar(menubar);

		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel a = new JPanel();
				// Create a file chooser
				final JFileChooser fc = new JFileChooser();
				// In response to a button click:
				int returnVal = fc.showOpenDialog(a);
				
				useFile = fc.getSelectedFile();

				if (useFile != null) {

					InputTXR TXR = new InputTXR(useFile, true);
					TXR.makeHeadings();
				}
			}

		});
		
		selectDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel a = new JPanel();
				// Create a file chooser
				final JFileChooser fc2 = new JFileChooser();
				// In response to a button click:
				int returnVal = fc2.showOpenDialog(a);
				
				useFileDir = fc2.getSelectedFile();

				if (useFileDir != null) {
					DirectoryFiles myDirFiles = new DirectoryFiles(useFileDir);
				}
			}

		});

		add(select, BorderLayout.SOUTH);
		add(selectDir, BorderLayout.NORTH);
		
		try {
			add(addImage());
		} catch (IOException e) {
			System.err.println("Could not add the logo.");
			e.printStackTrace();
		}

		setTitle("VHU - TXR Converter");
		setSize(300, 225);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void infoBox()
    {
        JOptionPane.showMessageDialog(null, "All processes were finished.", "Completed", 0);
        System.exit(0);
    }


	private JLabel addImage() throws IOException {
		BufferedImage myPicture = ImageIO.read(getClass().getResource(
				"/resources/logo.jpg"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		return picLabel;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				driver ex = new driver();
				ex.setVisible(true);
			}
		});
	}
}
