package org.PersonalFinanceTracker.IOTest;


import org.PersonalFinanceTracker.io.SystemIO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

class SystemIOTest {

    @Test
    void testReadLineReadsFromSystemIn() throws Exception {
        String simulatedInput = "hello world\n";
        InputStream originalIn = System.in;

        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            SystemIO io = new SystemIO();

            assertEquals("hello world", io.readLine());
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    void testPrintWritesToSystemOut() {
        PrintStream originalOut = System.out;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            SystemIO io = new SystemIO();
            io.print("Hello!");

            assertTrue(outContent.toString().contains("Hello!"));
        } finally {
            System.setOut(originalOut);
        }
    }
}

