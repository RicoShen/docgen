package com.youland.doc;

import com.deepoove.poi.XWPFTemplate;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlgraphics.util.MimeConstants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DocgenApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void replaceParagraphs() {

        try {
            ClassPathResource file = new ClassPathResource("demo.docx");
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        System.out.println("doc text: = " + text);
                        if (text != null && text.contains("[INSERT ADDRESS]")) {
                            text = text.replace("[INSERT ADDRESS]", "Rico Shen");
                            r.setText(text, 0);
                        }
                    }
                }
            }

            doc.write(new FileOutputStream("docs/demo_replace.docx"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void replaceTable() {
        try {
            ClassPathResource file = new ClassPathResource("Loan_Documents.docx");
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                System.out.println("【cell】：" + text);
                                if (text != null && text.contains("$Lender_Fee")) {
                                    text = text.replace("$Lender_Fee", "$22,500.00");
                                    r.setText(text, 0);
                                } else if (text != null && text.contains("$Lender_Processing_Fee")) {
                                    text = text.replace("$Lender_Processing_Fee", "$1,695.00");
                                    r.setText(text, 0);
                                }
                            }
                        }
                    }
                }
            }

            // 22,500.00
            // 1,695.00
            doc.write(new FileOutputStream("docs/Loan_Documents_replace.docx"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void replaceFooter() {
        try {
            ClassPathResource file = new ClassPathResource("Loan_Documents.docx");
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            for (XWPFFooter footer : doc.getFooterList()) {
                for (XWPFParagraph p : footer.getParagraphs()) {
                    List<XWPFRun> runs = p.getRuns();
                    if (runs != null) {
                        for (XWPFRun r : runs) {
                            String text = r.getText(0);
                            System.out.println("footer text: = " + text);
                            if (text != null && text.contains("#Loan_ID#")) {
                                text = text.replace("#Loan_ID#", "20220915KL");
                                r.setText(text, 0);
                            } else if (text != null && text.contains("#Property_Address#")) {
                                text =
                                        text.replace("#Property_Address#", "37 Country Club Drive, Hayward, CA 94542");
                                r.setText(text, 0);
                            }
                        }
                    }
                }
            }
            // 22,500.00
            // 1,695.00
            doc.write(new FileOutputStream("docs/Loan_Documents_replace_footer.docx"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void replaceByTemplate() {

        try {
            ClassPathResource file = new ClassPathResource("template.docx");
            XWPFTemplate template = XWPFTemplate.compile(file.getInputStream());
            // 段落内容替换，表格内容替换、页脚替换
            Map datas = new HashMap();
            datas.put("Loan_ID", "20220915KL"); // footer
            datas.put("Property_Address", "37 Country Club Drive, Hayward, CA 94542"); // footer
            datas.put("order_no", "56606-21-04011"); // 段落中的内容
            datas.put("email", "richard@youland.com"); // 段落中的内容
            datas.put("leander_fee", "$22,500.00"); // 表格内容
            datas.put("lender_processing_fee", "$1,695.00"); //表格内容
            template.render(datas);

            XWPFDocument doc = template.getXWPFDocument();
            OutputStream out = new FileOutputStream("docs/template_replace.docx");
            doc.write(out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void wordToPdf() {
        try {
            ClassPathResource file = new ClassPathResource("NoteCA.docx");
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            // convert word to pdf
            PdfOptions options = PdfOptions.create();
            OutputStream outPDF = new FileOutputStream("docs/loan_documents.pdf");
            PdfConverter.getInstance().convert(doc, outPDF, options);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void wordToPdf2() {
        try {
            ByteArrayOutputStream fopout = new ByteArrayOutputStream();
            // out
            OutputStream outfile = new FileOutputStream("docs/loan_documents.pdf");

            //input
            ClassPathResource file = new ClassPathResource("NoteCA.docx");

            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, fopout);

            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer(new StreamSource(file.getInputStream()));

            transformer.transform(new StreamSource(file.getInputStream()),
                    new SAXResult(fop.getDefaultHandler()));

            // reader
            PdfReader reader = new PdfReader(file.getInputStream().readAllBytes());
            int n = reader.getNumberOfPages();
            Document document = new Document(reader.getPageSizeWithRotation(1));

            PdfWriter writer = PdfWriter.getInstance(document, outfile);
            writer.setEncryption(PdfWriter.STRENGTH40BITS, "pdf", null,
                    PdfWriter.AllowCopy);
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page;
            int rotation;
            int i = 0;
            while (i < n) {
                i++;
                document.setPageSize(reader.getPageSizeWithRotation(i));
                document.newPage();
                page = writer.getImportedPage(reader, i);
                rotation = reader.getPageRotation(i);
                if (rotation == 90 || rotation == 270) {
                    cb.addTemplate(page, 0, -1f, 1f, 0, 0,
                            reader.getPageSizeWithRotation(i).getHeight());
                } else {
                    cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                }
                System.out.println("Processed page " + i);
            }
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void docxToPdf() {

        ClassPathResource src = new ClassPathResource("NoteCA.docx");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] resBytes = null;
        String result;
        try {

            OutputStream outfile = new FileOutputStream("docs/NoteCA.pdf");
            // pdf文件的尺寸
            Document pdfDocument = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument, baos);
            XWPFDocument doc = new XWPFDocument(src.getInputStream());
            pdfWriter.setInitialLeading(20);
            java.util.List<XWPFParagraph> plist = doc.getParagraphs();
            pdfWriter.open();
            pdfDocument.open();
            for (int i = 0; i < plist.size(); i++) {
                XWPFParagraph pa = plist.get(i);
                java.util.List<XWPFRun> runs = pa.getRuns();
                for (int j = 0; j < runs.size(); j++) {
                    XWPFRun run = runs.get(j);
                    java.util.List<XWPFPicture> piclist = run.getEmbeddedPictures();
                    Iterator<XWPFPicture> iterator = piclist.iterator();
                    while (iterator.hasNext()) {
                        XWPFPicture pic = iterator.next();
                        XWPFPictureData picdata = pic.getPictureData();
                        byte[] bytepic = picdata.getData();
                        Image imag = Image.getInstance(bytepic);
                        pdfDocument.add(imag);
                    }

                    String text = run.getText(-1);
                    byte[] bs;
                    if (text != null) {
                        bs = text.getBytes();
                        String str = new String(bs);
                        Chunk chObj1 = new Chunk(str);
                        pdfDocument.add(chObj1);
                    }
                }
                pdfDocument.add(new Chunk(Chunk.NEWLINE));
            }
            //需要关闭，不然无法获取到输出流
            pdfDocument.close();
            pdfWriter.close();
            resBytes = baos.toByteArray();

            outfile.write(resBytes);
            outfile.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
