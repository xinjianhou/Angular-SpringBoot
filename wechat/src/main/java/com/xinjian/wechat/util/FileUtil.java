package com.xinjian.wechat.util;

import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * File Util
 */
public class FileUtil {


    public static List<String> list(File file) {

        List<String> folders  =  new ArrayList();
        if (file != null) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].isDirectory()) {
                            folders.add(files[i].getPath());
                        }
                        list(files[i]);
                    }
                }
            }
        }
        return folders;
    }

    /**
     * list all files under the folder
     *
     * @param filePath
     * @param fileNameFilter
     * @return files
     */
    public static List<File> listAllFiles(String filePath, FilenameFilter fileNameFilter) {
        List<File> files = new ArrayList();
        try {
            File root = new File(filePath);
            if (!root.exists())
                return files;
            if (root.isFile())
                files.add(root);
            else {
                for (File file : root.listFiles(fileNameFilter)) {
                    if (file.isFile())
                        files.add(file);
                    else if (file.isDirectory()) {
                        files.addAll(listAllFiles(file.getAbsolutePath(), fileNameFilter));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    public static void main(String[] args) {
//        File fileIndex = new File(INDEX_DIR);
//        if (deleteDir(fileIndex)) {
//            fileIndex.mkdir();
//        } else {
//            fileIndex.mkdir();
//        }
//
 //  creatIndex();
        //searchIndex("java");
       // System.out.println(list(new File("/Users/xinjianhou/Documents")));

    }

    /**
     * inner class for filename filter
     */
    private static class DirFilter implements FilenameFilter {
        private Pattern pattern;

        public DirFilter(String regex) {
            pattern = Pattern.compile(regex);
        }

        @Override
        public boolean accept(File dir, String name) {
            return pattern.matcher(name).matches();
        }

    }

    /**
     * delete provide file or files under the folder was provided
     *
     * @param file
     * @return status
     */
    public static boolean deleteDir(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        file.delete();
        return true;
    }

    /**
     * read a txt file
     *
     * @param file
     * @return the content of txt file
     */
    public final static String readTxt(final File file) {
        StringBuffer result = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while ((s = br.readLine()) != null) {
                result = result.append("\n").append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public final static String readWord(final File file, final String type) {
        String result = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            if ("doc".equals(type)) {
                HWPFDocument doc = new HWPFDocument(fis);
                Range rang = doc.getRange();
                result += rang.text();
            }
            if ("docx".equals(type)) {
                XWPFDocument doc = new XWPFDocument(fis);
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                result = extractor.getText();
            }

            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * read pdf file
     *
     * @param file
     * @return content of the file
     */
    public final static String readPDF(final File file) {
        String result = null;
        RandomAccessRead is = null;
        PDDocument document = null;
        try {
            is = new RandomAccessBufferedFileInputStream(file);
            PDFParser parser = new PDFParser(is);
            parser.parse();
            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            result = stripper.getText(document);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public final static String readExcel(File file, String type) {
        String result = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            StringBuilder sb = new StringBuilder();
            if ("xlsx".equals(type)) {
                XSSFWorkbook xwb = new XSSFWorkbook(fis);
                for (int i = 0; i < xwb.getNumberOfSheets(); i++) {
                    XSSFSheet sheet = xwb.getSheetAt(i);
                    for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                        XSSFRow row = sheet.getRow(j);
                        for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {
                            XSSFCell cell = row.getCell(k);
                            sb.append(cell.getRichStringCellValue());
                        }
                    }
                }
            }
            if ("xls".equals(type)) {
                // 得到Excel工作簿对象
                HSSFWorkbook hwb = new HSSFWorkbook(fis);
                for (int i = 0; i < hwb.getNumberOfSheets(); i++) {
                    HSSFSheet sheet = hwb.getSheetAt(i);
                    for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                        HSSFRow row = sheet.getRow(j);
                        for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {
                            HSSFCell cell = row.getCell(k);
                            sb.append(cell.getRichStringCellValue());
                        }
                    }
                }
            }

            fis.close();
            result += sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * read html file
     *
     * @param file
     * @return content of the file
     */
    public final static String readHtml(final File file) {
        StringBuffer content = new StringBuffer("");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            // 读取页面
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));//这里的字符编码要注意，要对上html头文件的一致，否则会出乱码
            String line = null;
            while ((line = reader.readLine()) != null) {
                content.append(line + "\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String contentString = content.toString();
        return contentString;
    }



}
