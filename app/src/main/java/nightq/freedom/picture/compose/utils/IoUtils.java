package nightq.freedom.picture.compose.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

/**
 * Created by Nightq on 15/12/5.
 */
public class IoUtils {

    private static final int BUFFER_SIZE = 4096;

    public static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copyAllBytes(in, out);
        return out.toByteArray();
    }

    public static byte[] readAllBytesAndClose(InputStream in) throws IOException {
        try {
            return readAllBytes(in);
        } finally {
            safeClose(in);
        }
    }

    public static String readAllChars(Reader reader) throws IOException {
        char[] buffer = new char[2048];
        StringBuilder builder = new StringBuilder();
        while (true) {
            int read = reader.read(buffer);
            if (read == -1) {
                break;
            }
            builder.append(buffer, 0, read);
        }
        return builder.toString();
    }

    public static String readAllCharsAndClose(Reader reader) throws IOException {
        try {
            return readAllChars(reader);
        } finally {
            safeClose(reader);
        }
    }

    /**
     * Copies all available data from in to out without closing any stream.
     *
     * @return number of bytes copied
     */
    public static int copyAllBytes(InputStream in, OutputStream out) throws IOException {
        int byteCount = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        while (true) {
            int read = in.read(buffer);
            if (read == -1) {
                break;
            }
            out.write(buffer, 0, read);
            byteCount += read;
        }
        return byteCount;
    }


    /** Closes the given stream inside a try/catch. Does nothing if stream is null. */
    public static void safeClose(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                // Silent
            }
        }
    }

    /** Closes the given stream inside a try/catch. Does nothing if stream is null. */
    public static void safeClose(Reader in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                // Silent
            }
        }
    }

}
