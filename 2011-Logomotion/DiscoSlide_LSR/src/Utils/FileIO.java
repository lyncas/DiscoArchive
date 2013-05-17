package Utils;

import java.io.*;
import javax.microedition.io.Connector;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.io.*;
import com.sun.squawk.util.StringTokenizer;

/**
 * @author Nelson
 */
public class FileIO {

    /** -------------------------------------------------------
    @method readFromFile
    @param filename - name of file to be read from
    @purpose returns an array of Strings from the file
    @author Nelson Chen
    @written Feb 7, 2011
    ------------------------------------------------------- */
    public static String[] readFromFile(String filename) {
        String url = "file:///" + filename;
        String rawContents = "";
        String[] contents = null;
        try {
            FileConnection conn = (FileConnection) Connector.open(url);
            BufferedReader buf = new BufferedReader(new InputStreamReader(conn.openInputStream()));
            String line = "";
            while ((line = buf.readLine()) != null) {
                rawContents += line + "·";
            }
            conn.close();
            StringTokenizer lineBreaker = new StringTokenizer(rawContents, "·");
            contents = new String[lineBreaker.countTokens()];
            int k = 0;
            while (lineBreaker.hasMoreTokens()) {
                contents[k] = lineBreaker.nextToken();
                k++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    /** -------------------------------------------------------
    @method writeToFile
    @param filename - name of file to be written
    @purpose writes an array of Strings to a file (each element becomes a line)
    @author Nelson Chen
    @written Feb 7, 2011
    ------------------------------------------------------- */
    public static void writeToFile(String filename, String[] text) {
        String url = "file:///" + filename;
        try {
            FileConnection conn = (FileConnection) Connector.open(url);
            OutputStreamWriter writer = new OutputStreamWriter(conn.openOutputStream());
            for (int k = 0; k < text.length; k++) {
                writer.write(text[k] + "\n");
                writer.flush();
            }
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
