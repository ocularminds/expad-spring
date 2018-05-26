/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.codec.binary.Base64
 */
package com.ocularminds.expad.common;

import static com.ocularminds.expad.FileProducer.LOG;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import javax.imageio.ImageIO;

public class Images {

    public static void contact(String[] images, String finalImage) throws IOException, Exception {
        File[] files = new File[images.length];
        BufferedImage[] bis = new BufferedImage[images.length];
        for (int x = 0; x < files.length; ++x) {
            files[x] = new File(images[x]);
            bis[x] = ImageIO.read(files[x]);
        }
        int height = 0;
        int width = 0;
        for (int i = 0; i < bis.length; ++i) {
            height += bis[i].getHeight();
            width += bis[i].getWidth();
        }
        BufferedImage img = new BufferedImage(width, height, 1);
        boolean isDrawn = true;
        int h = 0;
        int w = 0;
        for (int j = 0; j < bis.length; ++j) {
            w = (j + 1) % 2 == 0 ? bis[0].getWidth() : 0;
            h = j + 1 > 3 ? bis[0].getHeight() : 0;
            isDrawn = img.createGraphics().drawImage(bis[j], w, h, null);
        }
        File final_image = new File(finalImage);
        boolean final_Image_drawing = ImageIO.write((RenderedImage) img, "jpeg", final_image);
        if (!final_Image_drawing) {
            LOG.info("Problems drawing final image");
        }
        LOG.info("Successfull");
    }

    public static byte[] decodeImageText(String encodedImage) {
        byte[] data = null;
        try {
            data = Base64.getDecoder().decode((byte[]) encodedImage.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static BufferedImage readEncodedImageText(String encodedImage) {
        BufferedImage img;
        try {
            byte[] data = Base64.getDecoder().decode((byte[]) encodedImage.getBytes());
            img = ImageIO.read(new ByteArrayInputStream(data));
        } catch (IOException e) {
            img = null;
            LOG.error("ERROR, <<error reading encoded image.", e);
        }
        return img;
    }

    public static String encodeImageByte(byte[] data) {
        String encodedImage = null;
        try {
            encodedImage = Base64.getEncoder().encodeToString((byte[]) data);
        } catch (Exception e) {
            LOG.error("ERROR, <<error reading encoded image.", e);
        }
        return encodedImage;
    }
    
    public static String encodeImageFromFile(String url) {
        String encodedImage;
        encodedImage = null;
        ByteArrayOutputStream baos = null;
        try {
            BufferedImage img = ImageIO.read(new File(url));
            baos = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) img, "png", baos);
            baos.flush();
            byte[] data = baos.toByteArray();
            encodedImage = Base64.getEncoder().encodeToString((byte[]) data);
        } catch (IOException e) {
            LOG.error("error processing image >> ", e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException i) {
                LOG.error("error processing image >> ", i);
            }
        }
        return encodedImage;
    }

    public static byte[] fromFile(String pathname) {
        BufferedInputStream bis;
        ArrayList<Byte> bytes = new ArrayList<>();
        byte[] data = null;
        File file = new File(pathname);
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException fe) {
            LOG.info("INFORMATION, <<Images:fromFile>> Image file does not Exist.");
            return null;
        }
        try {
            int bit;
            while ((bit = bis.read()) != -1) {
                bytes.add((byte) bit);
            }
            data = new byte[bytes.size()];
            for (int i = 0; i < data.length; ++i) {
                data[i] = (bytes.get(i));
            }
        } catch (IOException ioe) {
            LOG.error("INFORMATION, <<Images:fromFile>> Could not retrieve image.", ioe);
        }
        return data;
    }
}
