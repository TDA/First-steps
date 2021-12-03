package fb_recent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

class Reader4 {
    String abc = new File("").getAbsolutePath() + "/src/fb_recent/abc.txt";
    BufferedReader bufferedReader = Files.newBufferedReader(Path.of(abc));

    Reader4() throws IOException {
    }

    public int read4(char[] buf) throws IOException {
        int read;
        read = bufferedReader.read(buf);
        System.out.println(read);
        return read;
    }
}

public class ReadFromRead4 extends Reader4 {
    ReadFromRead4() throws IOException {
    }

    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    public int read(char[] buf, int n) throws IOException {
        System.out.printf("Attmpting to read %d chars into buf\n", n);
        int read = 0;
        boolean eof = false;
        while (!eof && read < n) {
            char[] tempBuf = new char[4];
            System.out.println("Temp buf size to : " + tempBuf.length);
            System.out.println("Buf size to : " + buf.length);
            try {
                int read4 = read4(tempBuf);
                eof = read4 < 4;
                read4 = Math.min(read4, n - read);
                System.arraycopy(tempBuf, 0, buf, read, read4);
                read = read4 + read;

                System.out.printf("Chars read %s and total size %s, will continue? %s \n", read, n, !eof && read < n);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
                eof = true;
            }
        }
        return read;
    }

    char[] tempBuf = new char[4];
    int tempBufLastReadItem = 0;
    int tempBufLastReadSize = 0;
    public int readHard(char[] buf, int n) throws IOException {
        int read = 0;
        while (read < n) {
            if (tempBufLastReadItem < tempBufLastReadSize) {
                // else, just copy from existing and update pointer
                buf[read++] = tempBuf[tempBufLastReadItem++];
            } else {
                // everything from temp buff was read, we gotta fetch again and store in tempBuf
                int read4 = read4(tempBuf);
                tempBufLastReadItem = 0;
                tempBufLastReadSize = read4;
                if (read4 == 0) break;
            }
        }
        return read;
    }

    public static void main(String[] args) throws IOException {

        int[] queries = {1,2,1};
        int[] Output = {1,2,0};

        for (int i = 0; i < queries.length; i++) {
            ReadFromRead4 readFromRead4 = new ReadFromRead4();
            char[] buf = new char[12];
            int query = queries[i];
            int read = readFromRead4.readHard(buf, query);// After calling your read method, buf should contain "a". We read a total of 1 character from the file, so return 1.
            System.out.println("contents: " + Arrays.toString(buf));
            System.out.println("Actual: " + read);
            System.out.println("Expected: " + Output[i]);
        }

    }
}
