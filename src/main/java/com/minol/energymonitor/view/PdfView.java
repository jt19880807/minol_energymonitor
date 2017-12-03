package com.minol.energymonitor.view;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.minol.energymonitor.utils.PDFUtil;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * Created by Administrator on 2017/12/2.
 */
public class PdfView extends AbstractIText5PdfView {
    // 敬语
    public static final String honorific_content = "亲爱的朋友们,下面是你们要的数据：";
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document,
                                    PdfWriter writer,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        response.setHeader("Content-disposition", "attachment; fileName=" + model.get("fileName") + ".pdf");
        List<Map<String, Object>> content = (List<Map<String, Object>>) model.get("content");
        List<String> headerList = (List<String>) model.get("header");
        document.open();
        // 默认A4 大小
        document.setPageSize(PageSize.A4);
        Paragraph title = PDFUtil.getParagraph(new Chunk(String.valueOf(model.get("fileName")), new Font(PDFUtil.bfChinese, 14, Font.BOLD)));
        title.setAlignment(Paragraph.ALIGN_CENTER);
        //中文 黑体
        document.add(title);
        Paragraph head = PDFUtil.getParagraph(honorific_content);
        head.setSpacingBefore(20);
        head.setSpacingAfter(10);
        document.add(head);
        // 表格标题
        PdfPTable table = new PdfPTable(headerList.size());
        table.setSpacingBefore(20);
        table.setSpacingAfter(30);
        for (String cellTitle : headerList) {
            table.addCell(PDFUtil.getParagraph(cellTitle));
        }
        //表格数据
        for (Iterator<Map<String, Object>> iterator = content.iterator(); iterator.hasNext(); ) {
            Map<String, Object> next = iterator.next();
            for (Map.Entry<String, Object> entry : next.entrySet()) {
                table.addCell(String.valueOf(entry.getValue()));
            }
        }
        document.add(table);
        document.close();
    }
}
