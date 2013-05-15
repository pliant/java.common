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
	 * @param out The OutputStream to close.
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
	 * @param in The InputStream to close.
	 */
	public static void close(InputStream in){
		try {
			in.close();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Performs a flush and close on the Writer, catching any exceptions thrown.
	 * @param out The Writer to close
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
	 * @param in The Reader to close.
	 */
	public static void close(Reader in){
		try {
			in.close();
		}
		catch (Exception e) {}
	}
	
	/**
	 * Attempts to convert an InputStream to a String.  If any exception is thrown during this process, {@code null}
	 * is return.  This will close the stream that is passed once it is done reading from it..
	 * 
	 * @param stream The InputStream to convert.
	 * @return A String or {@code null}
	 */
	public static String toStringOrNull(InputStream stream) {
		StringBuilder answer = new StringBuilder();
		if (stream != null) {
			InputStreamReader reader = new InputStreamReader(stream);
			int len = 1024;
			char[] buffer = new char[len];
			try{
				while((len = reader.read(buffer)) >= 0){
					answer.append(buffer, 0, len);
				}
			}
			catch (Exception e) {
				return null;
			}
			finally{
				close(reader);
			}
		}
		return answer.toString();
	}
}
