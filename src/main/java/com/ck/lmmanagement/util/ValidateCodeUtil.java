package com.ck.lmmanagement.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author 01378803
 * @date 2019/1/24 10:32
 * Description  : 验证码数字生成工具
 */
public class ValidateCodeUtil {
    /**
     * 图片的宽度。
     */
    private static int width = 99;
    /**
     * 图片的高度。
     */
    private static int height = 33;
    /**
     * 验证码字符个数
     */
    private static int codeCount = 4;
    /**
     * 验证码干扰线数
     */
    private static int lineCount = 5;
    /**
     * 验证码
     */
    private static String code = null;
    /**
     * 验证码图片Buffer
     */
    private static BufferedImage buffImge = null;
    /**
     * 随机数生成器
     */
    private static Random random = new Random();
    /**
     * 字体大小
     */
    private static final int FONT_SIZE = 30;
    /**
     * 验证码范围,去掉0(数字)和O(拼音)容易混淆的(小写的1和L也可以去掉,大写不用了)
     */
    private static char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    // 输出验证码流图片
    public static void createCode(HttpServletResponse response, HttpSession session) throws IOException {
        StringBuffer sb = new StringBuffer();
        // 1.创建空白图片
        buffImge = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 2.获取图片画笔
        Graphics2D graphic = buffImge.createGraphics();
        // 3.设置画笔颜色 4.设置背景颜色
        graphic.setColor(Color.WHITE);
        // 5.绘制矩形背景
        graphic.fillRect(0, 0, width, height);
        for (int i = 0; i < codeCount; i++) {
            // 取随机字符索引
            int n = random.nextInt(codeSequence.length);
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 设置字体大小
            graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
            // 画字符
            graphic.drawString(codeSequence[n] + "", i * width / (codeCount), (height - 4));
            // 记录字符
            sb.append(codeSequence[n]);
        }
        // 6.画干扰线
        for (int i = 0; i < lineCount; i++) {
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 随机画线
            graphic.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));
        }
        code = sb.toString();
        session.setAttribute("code", code);
        write(response.getOutputStream());
    }

    /**
     * 随机取色
     */
    public static Color getRandomColor() {
        Random ran = new Random();
//		Color color = new Color(ran.nextInt(256),ran.nextInt(256), ran.nextInt(256));
        Color color = Color.LIGHT_GRAY;
        return color;
    }

    private static void write(OutputStream os) throws IOException {
        ImageIO.write(buffImge, "png", os);
        os.close();
    }
}
