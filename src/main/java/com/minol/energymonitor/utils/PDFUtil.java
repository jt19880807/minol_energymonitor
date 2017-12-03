package com.minol.energymonitor.utils;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/2.
 */
public class PDFUtil {
    // 基本字体和样式
    public static BaseFont bfChinese;
    public static Font fontChinese;
    public static Font UNDER_LINE = null;
    static {
        try {
            // SIMKAI.TTF 默认系统语言，这里没使用第三方语言包
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            fontChinese = new Font(bfChinese, 14, Font.NORMAL);
            UNDER_LINE = new Font(bfChinese, 14, Font.UNDERLINE);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 默认样式
    public static Paragraph getParagraph(String context) {
        return getParagraph(context, fontChinese);
    }
    public static Paragraph getParagraphWithUNDER_LINE(String context) {
        return getParagraph(context, UNDER_LINE);
    }
    public static Paragraph getParagraph(Chunk chunk) {
        return new Paragraph(chunk);
    }
    // 指定字体样式
    public static Paragraph getParagraph(String context, Font font) {
        return new Paragraph(context, font);
    }
}
