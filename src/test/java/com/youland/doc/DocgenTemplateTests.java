package com.youland.doc;

import com.youland.doc.util.DocTimeUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

class DocgenTemplateTests {

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
    void docDate() {
        String docDate = DocTimeUtil.getDocDate(Instant.now());
        System.out.println("docDate : " + docDate);
    }
}
