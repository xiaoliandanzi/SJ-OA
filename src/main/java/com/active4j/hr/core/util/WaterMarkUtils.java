package com.active4j.hr.core.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class WaterMarkUtils {
    /**
     * @param srcImgPath 源图片路径
     * @param tarImgPath 保存的图片路径
     * @param waterMarkContent 水印内容
     */
    public static void addWaterMark(String srcImgPath, String tarImgPath, String waterMarkContent,String suffix) {

        try {
            ImgCompress.resizeByHeight(610,tarImgPath,srcImgPath,suffix);
            int length = waterMarkContent.length();
            if (length >= 30){
                waterMarkContent = waterMarkContent.substring(0,30) + "...";
            }
            // 读取原图片信息
            File srcImgFile = new File("/data/tomcat/liulitun/webapps"+srcImgPath);//得到文件
            System.out.println("------------------------------文件路径：" + srcImgFile);
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();

            //加水印图像
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);
            ImageIcon imgIcon = new ImageIcon("/data/tomcat/liulitun/webapps//upload/20210730174253.png");
            // 得到Image对象。
            Image img = imgIcon.getImage();
            float alpha = 0.9f; // 透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            int ImgWidth = img.getWidth(null);//获取背景图片的宽
            int ImgHeight = img.getHeight(null);//获取背景图片的高
            // 表示水印图片的位置
            g.drawImage(img, srcImgWidth/2 - ImgWidth/2, srcImgHeight - ImgHeight/2, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            FileOutputStream os = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, suffix, os);
            System.out.println("添加水印图片完成");
            os.flush();
            os.close();

            // 读取原图片信息
            File srcImgFile2 = new File("/data/tomcat/liulitun/webapps"+srcImgPath);//得到文件
            System.out.println("------------------------------文件路径：" + srcImgFile2);
            Image srcImg2 = ImageIO.read(srcImgFile2);//文件转化为图片
            int srcImgWidth2 = srcImg2.getWidth(null);//获取图片的宽
            int srcImgHeight2 = srcImg2.getHeight(null);//获取图片的高
            BufferedImage bufImg2 = new BufferedImage(srcImgWidth2, srcImgHeight2, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bufImg2.createGraphics();
            // 加水印文字
            g2.drawImage(srcImg2, 0, 0, srcImgWidth2, srcImgHeight2, null);
            g2.setColor(Color.WHITE);; //根据图片的背景设置水印颜色
            Font font = new Font("微软雅黑", Font.BOLD, 25);//水印字体
            g2.setFont(font);//设置字体

            //设置水印的坐标
            int x = srcImgWidth2/2 - getWatermarkLength(waterMarkContent, g2)/2;
            int y = srcImgHeight2 - 15;
            g2.drawString(waterMarkContent, x,y);  //画出水印
            g2.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream("/data/tomcat/liulitun/webapps"+tarImgPath);
            ImageIO.write(bufImg2, suffix, outImgStream);
            System.out.println("添加水印文字完成");
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }


    /*public static void main(String[] args) {
        String srcImgPath="C:\\Users\\13353\\Desktop\\ceshi123.jpg"; //源图片地址
        String tarImgPath="C:\\Users\\13353\\Desktop\\ceshi123.jpg"; //待存储的地址
        String waterMarkContent="测试测试测试测试测试，测试测试测试测试测试，测试测试测试测试...";  //水印内容
        new WaterMarkUtils().addWaterMark(srcImgPath, tarImgPath, waterMarkContent,"jpg");
    }*/
}

