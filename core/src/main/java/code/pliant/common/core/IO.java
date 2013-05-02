package code.pliant.common.core;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;


/**
 * Helper methods for working with IO classes.
 * 
 * @author Daniel Rugg
 */
public class IO {
	
	/**
	 * Performs a flush and close on the OutputStream, catching any exceptions thrown.
	 * @param out
	 */
	public static void close(OutputStream out){
		try {
			out.flush();
		}
		catch (Exception e) {}
		try {
			out.close();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Performs a close on the InputStream, catching any exceptions thrown.
	 * @param in
	 */
	public static void close(InputStream in){
		try {
			in.close();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Performs a flush and close on the Writer, catching any exceptions thrown.
	 * @param out
	 */
	public static void close(Writer out){
		try {
			out.flush();
		}
		catch (Exception e) {}
		try {
			out.close();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Performs a close on the InputStream, catching any exceptions thrown.
	 * @param in
	 */
	public static void close(Reader in){
		try {
			in.close();
		}
		catch (Exception e) {}
	}
	
	public static String toStringOrNull(InputStream stream) {
		StringBuilder answer = new StringBuilder();
		if (stream != null) {
			final char[] buffer = new char[0x10000];
			Reader in = new InputStreamReader(stream);
			try{
				int read;
				do {
					read = in.read(buffer, 0, buffer.length);
					if (read > 0) {
						answer.append(buffer, 0, read);
					}
				}
				while (read >= 0);
			}
			catch (Exception e) {
				// We are not letting exceptions escape.
				return null;
			}
			finally{
				close(in);
			}
		}
		return answer.toString();
	}
}
