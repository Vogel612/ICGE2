/*
 * This source file is part of the FIUS ICGE project.
 * For more information see github.com/FIUS/ICGE2
 * 
 * Copyright (c) 2019 the ICGE project authors.
 * 
 * This software is available under the MIT license.
 * SPDX-License-Identifier:    MIT
 */
package de.unistuttgart.informatik.fius.icge.ui.internal;

import java.awt.Dimension;
import java.awt.Font;
import java.io.OutputStream;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;

import de.unistuttgart.informatik.fius.icge.ui.Console;


/**
 * The SwingConsole allows the user to see text output from the system or the simulation itself.
 *
 * @author Tobias Wältken
 * @author David Ruff
 * @version 1.0
 */
public class SwingConsole extends JPanel implements Console {
    private static final long serialVersionUID = 5100186594058483257L;
    
    private JTextPane simulationConsole;
    private JTextPane systemConsole;
    
    private ConsoleBufferedOutputStream simulationOutputStream;
    private ConsoleBufferedOutputStream simulationErrorStream;
    private ConsoleBufferedOutputStream systemOutputStream;
    private ConsoleBufferedOutputStream systemErrorStream;
    
    /**
     * Default constructor
     */
    public SwingConsole() {
        Font standardFont = new Font("monospaced", Font.PLAIN, 12);
        {   // Setup simulation console
            this.simulationConsole = new JTextPane(new DefaultStyledDocument());
            this.simulationConsole.setEditable(false);
            this.simulationConsole.setFont(standardFont);
            final DefaultCaret caret = (DefaultCaret) this.simulationConsole.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            this.simulationOutputStream = new ConsoleBufferedOutputStream(this.simulationConsole, OutputStyle.STANDARD);
            this.simulationErrorStream = new ConsoleBufferedOutputStream(this.simulationConsole, OutputStyle.ERROR);
        }
        {   // Setup system console
            this.systemConsole = new JTextPane(new DefaultStyledDocument());
            this.systemConsole.setEditable(false);
            this.systemConsole.setFont(standardFont);
            final DefaultCaret caret = (DefaultCaret) this.systemConsole.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            this.systemOutputStream = new ConsoleBufferedOutputStream(this.systemConsole, OutputStyle.STANDARD);
            this.systemErrorStream = new ConsoleBufferedOutputStream(this.systemConsole, OutputStyle.ERROR);
            
        }
        // Add consoles to the TabbedPane
        this.add(new JScrollPane(this.simulationConsole));
    }
    
    @Override
    public OutputStream getSimulationOutputStream() {
        return this.simulationOutputStream;
    }
    
    @Override
    public OutputStream getSimulationErrorStream() {
        return this.simulationErrorStream;
    }
    
    @Override
    public OutputStream getSystemOutputStream() {
        return this.systemOutputStream;
    }
    
    @Override
    public OutputStream getSystemErrorStream() {
        return this.systemErrorStream;
    }
    
    @Override
    public void clearSimulationConsole() {
        this.simulationConsole.setText("");
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 200);
    }
}
