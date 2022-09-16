package com.youland.doc;

import com.deepoove.poi.XWPFTemplate;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DocgenApplicationTests {

  @Test
  void contextLoads() {}

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
      datas.put("email","richard@youland.com"); // 段落中的内容
      datas.put("leander_fee","$22,500.00"); // 表格内容
      datas.put("lender_processing_fee","$1,695.00"); //表格内容
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
      ClassPathResource file = new ClassPathResource("Loan_Documents.docx");
      XWPFDocument doc = new XWPFDocument(file.getInputStream());
      // convert word to pdf
      PdfOptions options = PdfOptions.create();
      OutputStream outPDF = new FileOutputStream("docs/loan_documents.pdf");
      PdfConverter.getInstance().convert(doc, outPDF, options);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
