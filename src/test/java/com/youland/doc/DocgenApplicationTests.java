package com.youland.doc;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
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
import java.util.List;

@SpringBootTest
class DocgenApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void replaceParagraphs(){
        try {
            ClassPathResource file = new ClassPathResource("demo.docx");
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            for (XWPFParagraph p: doc.getParagraphs()) {
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

            doc.write(new FileOutputStream("demo_replace.docx"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void replaceTable(){
        try {
            ClassPathResource file = new ClassPathResource("Loan_Documents.docx");
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                System.out.println("【cell】："+text);
                                if (text != null && text.contains("$Lender_Fee")) {
                                    text = text.replace("$Lender_Fee", "$22,500.00");
                                    r.setText(text,0);
                                }else if(text != null && text.contains("$Lender_Processing_Fee")){
                                    text = text.replace("$Lender_Processing_Fee", "$1,695.00");
                                    r.setText(text,0);
                                }
                            }
                        }
                    }
                }
            }

            // 22,500.00
            // 1,695.00
            doc.write(new FileOutputStream("Loan_Documents_replace.docx"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
