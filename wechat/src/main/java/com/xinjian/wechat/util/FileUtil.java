package com.xinjian.wechat.util;

import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
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

    /**
     * list all files under the folder
     *
     * @param filePath
     * @param fileNameFilter
     * @return files
     */
    public static List<File> listAllFiles(String filePath, FilenameFilter fileNameFilter) {
        List<File> files = new ArrayList<File>();
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
        System.out.println(readPDF(new File("/Users/xinjianhou/Documents/python/docs/Deep+Learning+with+Python+2017.pdf")));
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
    public static String readTxt(File file) {
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

    public final static String readWord(File file, String type) {
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
    public final static String readPDF(File file) {
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

    /**
     * read html file
     * @param file
     * @return content of the file
     */
    public static String readHtml(File file) {
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

   /* public static void creatIndex() {
                Date begin = new Date();
                 // 1、创建Analyzer词法分析器，注意SimpleAnalyzer和StandardAnalyzer的区别
               Analyzer analyzer  = null;
               // 2、创建directory,保存索引,可以保存在内存中也可以保存在硬盘上
                 Directory directory = null;
                 // 3、创建indexWriter创建索引
                 IndexWriter indexWriter = null;
                try {
             //            analyzer = new StandardAnalyzer();
             //            analyzer = new SimpleAnalyzer();
                         analyzer = new IKAnalyzer(true);
             //            directory = FSDirectory.open(new File(INDEX_DIR));
                         directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_DIR));
                         // 4、创建indexwriterConfig,并指定分词器版本
                        IndexWriterConfig config = new IndexWriterConfig(analyzer);
                         // 5、创建IndexWriter,需要使用IndexWriterConfig,
                         indexWriter = new IndexWriter(directory, config);
                         indexWriter.deleteAll();

                         File docDirectory = new File(Source_DIR);
                        for (File file : docDirectory.listFiles()) {
                                String content = "";
                                 //获取文件后缀
                                String type = file.getName().substring(file.getName().lastIndexOf(".")+1);
                                if("txt".equalsIgnoreCase(type)){
                                        content += readTxt(file);
                                    }else if("doc".equalsIgnoreCase(type)){
                                        content += readWord(file,"doc");
                                    }else if("docx".equalsIgnoreCase(type)){
                                        content += readWord(file,"docx");
                                    }else if("xls".equalsIgnoreCase(type)){
                                        content += readExcel(file,"xls");
                                    }else if("xlsx".equalsIgnoreCase(type)){
                                        content += readExcel(file,"xlsx");
                                    }else if("pdf".equalsIgnoreCase(type)){
                                        content += readPDF(file);
                                    }else if("html".equalsIgnoreCase(type)){
                                        content += readHtml(file);
                                    }
                                 // 6、创建document
                                Document document = new Document();
                                document.add(new Field("content", content, TextField.TYPE_STORED));
                                document.add(new Field("fileName", file.getName(), TextField.TYPE_STORED));
                                document.add(new Field("filePath", file.getAbsolutePath(), TextField.TYPE_STORED));
                                document.add(new Field("updateTime", file.lastModified() + "", TextField.TYPE_STORED));
                                 indexWriter.addDocument(document);
                        }
                        indexWriter.commit();
                     } catch (Exception e) {
                         e.printStackTrace();
                    } finally {
                        try {
                                 if (analyzer != null) analyzer.close();
                                if (indexWriter != null) indexWriter.close();
                                if (directory != null) directory.close();
                           } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
                Date end = new Date();
                System.out.println("创建索引-----耗时：" + (end.getTime() - begin.getTime()) + "ms\n");
            }*/
}
