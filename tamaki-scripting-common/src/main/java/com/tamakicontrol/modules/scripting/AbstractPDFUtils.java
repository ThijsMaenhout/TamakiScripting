package com.tamakicontrol.modules.scripting;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AbstractPDFUtils implements PDFUtilProvider {

    private final Logger logger = LoggerFactory.getLogger("Tamaki Scripts");

    @Override
    public String getPDFText(String filepath) {
        return getPDFTextImpl(filepath);
    }

    protected String getPDFTextImpl(String filepath){

        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        File file = new File(filepath);
        String parsedText = null;

        try {
            PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(5);
            parsedText = pdfStripper.getText(pdDoc);
        } catch (IOException e) {
            logger.error(e.getStackTrace().toString());
        }

        return parsedText;
    }
}
