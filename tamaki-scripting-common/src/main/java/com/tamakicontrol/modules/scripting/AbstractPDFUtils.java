package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.script.hints.ScriptArg;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;
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

    static {
        BundleUtil.get().addBundle(
                AbstractPDFUtils.class.getSimpleName(),
                AbstractPDFUtils.class.getClassLoader(),
                AbstractPDFUtils.class.getName().replace('.', '/')
        );
    }

    private final Logger logger = LoggerFactory.getLogger("Tamaki Scripts");

    @Override
    @ScriptFunction(docBundlePrefix = "PDFUtils")
    public String getPDFText(@ScriptArg("filepath") String filePath) {
        return getPDFTextImpl(filePath);
    }

    protected String getPDFTextImpl(String filePath){

        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        File file = new File(filePath);
        String parsedText = null;
        RandomAccessBufferedFileInputStream fileInputStream = null;
        try {
            fileInputStream = new RandomAccessBufferedFileInputStream(file);
            PDFParser parser = new PDFParser(fileInputStream);
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
        } catch (IOException e) {
            logger.error(e.getStackTrace().toString());
        }finally {
            try {
                if(pdDoc != null)
                    pdDoc.close();
                if(cosDoc != null)
                    cosDoc.close();
                if(fileInputStream != null)
                    fileInputStream.close();
            }catch (IOException e){
                logger.error(e.getStackTrace().toString());
            }
        }

        return parsedText;
    }
}
