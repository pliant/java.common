package code.pliant.common.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;


/**
 * Helper utility for working with images.
 * 
 * @author Daniel Rugg
 */
public class Images {

	/**
	 * Mime type of a PNG.  Value is {@code image/png};
	 */
	public static final String IMAGE_PNG = "image/png";
	
	/**
	 * Mime type of a JPEG.  Value is {@code image/jpeg};
	 */
	public static final String IMAGE_JPEG = "image/jpeg";
	
	/**
	 * BASE64 hint for the image data.  Value is {@code ;base64,};
	 */
	public static final String BASE64 = ";base64,";
	
	/**
	 * Data protocol for a URI.  Value is {@code data:};
	 */
	public static final String DATA = "data:";
	
	/**
	 * Full URI prefix for an BASE64 encoded PNG.  Value is {@link #DATA} + {@link #IMAGE_PNG} + {@link #BASE64}
	 */
	public static final String DATA_IMAGE_PNG_BASE64 = DATA + IMAGE_PNG + BASE64;
	
	/**
	 * Full URI prefix for an BASE64 encoded JPG.  Value is {@link #DATA} + {@link #IMAGE_JPEG} + {@link #BASE64}
	 */
	public static final String DATA_IMAGE_JPEG_BASE64 = DATA + IMAGE_JPEG + BASE64;
	
	/**
	 * Converts a base64 encoded image to a BufferedImage.  The encoded image can either be 
	 * just the base64 encoded string or the encoded string prefixed with the 'data:image/...;base64'
	 * prefix.
	 * 
	 * @param encodedImage The encoded image value.
	 * @return A {@link BufferedImage} generated from the encoded string.
	 * @throws IOException When the image conversion fails to be read.
	 */
	public static BufferedImage toBufferedImage(String encodedImage) throws IOException{
		if(!Strings.isValid(encodedImage)){
			throw new IllegalArgumentException("Original image can not be null");
		}
		if(encodedImage.startsWith(DATA)){
			String[] split = encodedImage.split(BASE64);
			if(split.length < 2){
				throw new IllegalArgumentException("Original image is not base64 encoded.");
			}
			encodedImage = split[1];
		}
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(encodedImage);
		return ImageIO.read(new ByteArrayInputStream(imageBytes));
	}
	
	/**
	 * Converts a PNG encoded string into a JPEG encoded string.
	 * @param encodedImage The encoded PNG value.
	 * @param transparentColor The color to use to replace transparency, as JPEG does not support it.
	 * @return A Base64 encoded JPEG.
	 * @throws IOException If the conversion fails to write.
	 */
	public static String convertPNGToJPEG(String encodedImage, Color transparentColor ) throws IOException{
		BufferedImage png = toBufferedImage(encodedImage);
		BufferedImage jpg = convertPNGToJPEG(png, transparentColor);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(jpg, "jpg", baos);
		String jpgBase64 = DatatypeConverter.printBase64Binary(baos.toByteArray());
		return DATA_IMAGE_JPEG_BASE64 + jpgBase64;
	}
	
	/**
	 * Converts a PNG BufferedImage to a JPEG BufferedImage.
	 * @param original A {@link BufferedImage} of type PNG.
	 * @param transparentColor The color to use to replace transparency, as JPEG does not support it.
	 * @return A {@link BufferedImage} of type JPEG.
	 */
	public static BufferedImage convertPNGToJPEG(BufferedImage original, Color transparentColor){
		if(original == null){
			throw new IllegalArgumentException("Original image can not be null");
		}
		BufferedImage jpg = null;
		int width = original.getWidth();
		int height = original.getHeight();
		if (GraphicsEnvironment.isHeadless()) {
			if (original.getType() == BufferedImage.BITMASK) {
				jpg =  new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			}
			else {
				jpg =  new BufferedImage(width, height, original.getType());
			}
		}
		else {
			jpg =  GraphicsEnvironment.getLocalGraphicsEnvironment().
					getDefaultScreenDevice().getDefaultConfiguration().
					createCompatibleImage(width, height, original.getTransparency());
		}

		Graphics2D g2 = null;
		try {
			g2 = jpg.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2.drawImage(original, 0, 0, width, height, null);
		}
		finally {
			if (g2 != null) {
				g2.dispose();
			}
		}
		jpg = fill(jpg, transparentColor == null ? Color.WHITE : transparentColor);
		return jpg;
	}

	/**
	 * Fill in an image with a specific color.
	 * @param original A {@link BufferedImage} to fill in.
	 * @param fillColor The color used for the fill.
	 * @return A {@link BufferedImage} with it's background filled in.
	 */
	public static BufferedImage fill(BufferedImage original, Color fillColor) {
		int w = original.getWidth();
		int h = original.getHeight();
		BufferedImage filled = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = filled.createGraphics();
		g.setColor(fillColor);
		g.fillRect(0, 0, w, h);
		g.drawRenderedImage(original, null);
		g.dispose();
		return filled;
	}
}
