package com.leilo.game.engine.display;

import java.io.*;
import java.util.StringTokenizer;

public class WidthLimitedOutputStream {
    // Output stream
    private PrintStream m_out;
    private int width;

    /*
     * WidthLimitedOutputStream constructor
     */
    public WidthLimitedOutputStream(OutputStream out, int width) {
        m_out = new PrintStream (out);
        this.width = width;
    }

    /**
     * string printing method that will automatically insert line breaks.
     */
    public void print (String str) {
        // Start at zero
        int currentWidth =  0;

        // Create a string tokenizer object
        StringTokenizer tokenizer = new StringTokenizer ( str );

        // While words remain
        while (tokenizer.hasMoreTokens()) {
            // Get the next token
            String token = tokenizer.nextToken();
            // If word would exceed width limit
            if (currentWidth + token.length() >= width) {
                // Print a newline
                m_out.println ();
                currentWidth = 0;
            }
            // Print token
            m_out.print (token + " ");
            currentWidth += token.length();
        }
        m_out.flush();
    }

    /**
     * string printing method that will automatically insert line breaks. The println version appends a newline.
     */
    public void println(String str) {
        print (str);
        m_out.println();
    }

    /**
     * Prints a blank line
     */
    public void println() {
        m_out.println();
    }

    public void close() {
        m_out.close();
    }
}
