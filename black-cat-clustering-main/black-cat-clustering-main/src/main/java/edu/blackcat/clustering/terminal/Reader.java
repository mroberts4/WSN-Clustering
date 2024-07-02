package edu.blackcat.clustering.terminal;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Reader {

    private final BufferedReader reader;

    public Reader() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Get a line from the buffered reader. If no line is available exit
     * @return Return the whole line with 0 additional formatting
     */
    public String readLine() {
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return line;
    }

}
