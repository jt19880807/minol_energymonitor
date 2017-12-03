package com.minol.energymonitor.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.AbstractView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/2.
 */
public abstract class AbstractIText5PdfView extends AbstractView {
    public AbstractIText5PdfView(){
        setContentType("application/pdf;charset=UTF-8");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest httpServletRequest,
                                           HttpServletResponse httpServletResponse) throws Exception {
// IE workaround: write into byte array first.
        ByteArrayOutputStream baos = createTemporaryOutputStream();
        // Apply preferences and build metadata.
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        prepareWriter(model, writer, httpServletRequest);
        buildPdfMetadata(model, document, httpServletRequest);
        // Build PDF document.
        document.open();
        buildPdfDocument(model, document, writer, httpServletRequest, httpServletResponse);
        document.close();
        // Flush to HTTP response.
        writeToResponse(httpServletResponse, baos);
    }
    protected Document newDocument() {
        return new Document(PageSize.A4);
    }
    protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
        return PdfWriter.getInstance(document, os);
    }
    protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
            throws DocumentException {
        writer.setViewerPreferences(getViewerPreferences());
    }
    protected int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }
    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
    }
    protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception;
}
