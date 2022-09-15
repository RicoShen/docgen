/*
  Copyright (C) 2018-2021 YouYu information technology (Shanghai) Co., Ltd.
  <p>
  All right reserved.
  <p>
  This software is the confidential and proprietary
  information of YouYu Company of China.
  ("Confidential Information"). You shall not disclose
  such Confidential Information and shall use it only
  in accordance with the terms of the contract agreement
  you entered into with YouYu inc.
 */
package com.youland.doc;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author: rico
 * @date: 2022/9/14
 **/
public class Demo {

  public static void main(String[] args) {

      try {
          ClassPathResource file = new ClassPathResource("demo.docx");
          XWPFDocument doc = new XWPFDocument(file.getInputStream());
          doc.getTables();
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
}
