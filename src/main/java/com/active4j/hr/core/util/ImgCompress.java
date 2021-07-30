package com.active4j.hr.core.util;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class ImgCompress {
    private static Image img;
    private static int width;
    private static int height;

    public void img(String srcImgPath) throws IOException {
        File file = new File(srcImgPath);// 读入文件
        img = ImageIO.read(file);      // 构造Image对象
        width = img.getWidth(null);    // 得到源图宽
        height = img.getHeight(null);  // 得到源图长
    }

    /**
     * 按照宽度还是高度进行压缩
     * @param w int 最大宽度
     * @param h int 最大高度
     */
    public static void resizeFix(int w, int h, String tarImgPath, String srcImgPath,String suffix) throws IOException {
        ImgCompress imgCompress = new ImgCompress();
        imgCompress.img(srcImgPath);
        w = (width > w) ? w : width;
        h = (height > h) ? h : height;
        if (width / height > w / h) {
            resizeByWidth(w, tarImgPath, srcImgPath,suffix);
        } else {
            resizeByHeight(h, tarImgPath, srcImgPath,suffix);
        }
    }

    /**
     * 以宽度为基准，等比例放缩图片
     * @param w int 新宽度
     */
    public static void resizeByWidth(int w, String tarImgPath, String srcImgPath,String suffix) throws IOException {
        ImgCompress imgCompress = new ImgCompress();
        imgCompress.img(srcImgPath);
        int h = height * w / width;
        resize(w, h, tarImgPath, suffix);
    }

    /**
     * 以高度为基准，等比例缩放图片
     * @param h int 新高度
     */
    public static void resizeByHeight(int h, String tarImgPath, String srcImgPath,String suffix) throws IOException {
        ImgCompress imgCompress = new ImgCompress();
        imgCompress.img(srcImgPath);
        int w = width * h / height;
        resize(w, h, tarImgPath, suffix);
    }

    /**
     * 强制压缩/放大图片到固定的大小
     * @param w int 新宽度
     * @param h int 新高度
     */
    public static void resize(int w, int h, String tarImgPath, String suffix) throws IOException {
        BufferedImage image = new BufferedImage(w, h, Image.SCALE_SMOOTH); //SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        //File destFile = new File(path);
        FileOutputStream out = new FileOutputStream(tarImgPath); // 输出到文件流
        ImageIO.write(image, suffix, out);
        out.flush();
        out.close();
    }
}
