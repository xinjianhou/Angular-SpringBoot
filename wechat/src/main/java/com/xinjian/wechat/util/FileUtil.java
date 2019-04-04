package com.xinjian.wechat.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
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
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * File Util
 */
public class FileUtil {

    private final static String Source_DIR = "/Users/xinjianhou/Documents/";

    private final static  String INDEX_DIR = "/Users/xinjianhou/Documents/lucene";

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
//        File fileIndex = new File(INDEX_DIR);
//        if (deleteDir(fileIndex)) {
//            fileIndex.mkdir();
//        } else {
//            fileIndex.mkdir();
//        }
//
 //  creatIndex();
        searchIndex("java");

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

    public final static void creatIndex() {
        Date begin = new Date();
        // 1、创建Analyzer词法分析器，注意SimpleAnalyzer和StandardAnalyzer的区别
        Analyzer analyzer = null;
        // 2、创建directory,保存索引,可以保存在内存中也可以保存在硬盘上
        Directory directory = null;
        // 3、创建indexWriter创建索引
        IndexWriter indexWriter = null;
        try {
            //            analyzer = new StandardAnalyzer();
            //            analyzer = new SimpleAnalyzer();
            analyzer = new SimpleAnalyzer();
            //            directory = FSDirectory.open(new File(INDEX_DIR));
            directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_DIR));
            // 4、创建indexwriterConfig,并指定分词器版本
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            // 5、创建IndexWriter,需要使用IndexWriterConfig,
            indexWriter = new IndexWriter(directory, config);
            indexWriter.deleteAll();

            List<File> files = listAllFiles(Source_DIR, null);
            for (File file : files) {
                String content = "";
                //获取文件后缀
                String type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                if ("txt".equalsIgnoreCase(type)) {
                    content += readTxt(file);
                } else if ("doc".equalsIgnoreCase(type)) {
                    content += readWord(file, "doc");
                } else if ("docx".equalsIgnoreCase(type)) {
                    content += readWord(file, "docx");
                } else if ("xls".equalsIgnoreCase(type)) {
                    content += readExcel(file, "xls");
                } else if ("xlsx".equalsIgnoreCase(type)) {
                    content += readExcel(file, "xlsx");
                } else if ("pdf".equalsIgnoreCase(type)) {
                    content += readPDF(file);
                } else if ("html".equalsIgnoreCase(type)) {
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
    }
    public static void searchIndex(String keyWord) {
        Date begin = new Date();
        // 1、创建Analyzer词法分析器，注意SimpleAnalyzer和StandardAnalyzer的区别
        Analyzer analyzer  = null;
        // 2、创建索引在的文件夹
        Directory indexDirectory = null;
        // 3、创建DirectoryReader
        DirectoryReader directoryReader = null;
        try {
            //            analyzer = new StandardAnalyzer();
            //            analyzer = new SimpleAnalyzer();
            analyzer = new SimpleAnalyzer();
            //            indexDirectory = FSDirectory.open(new File(INDEX_DIR));
            indexDirectory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_DIR));
            directoryReader = DirectoryReader.open(indexDirectory);
            // 3:根据DirectoryReader创建indexSeacher
            IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
            // 4创建搜索用的query,指定搜索域
            //            QueryParser parser = new QueryParser(, "content", analyzer);
            //            Query query1 = parser.parse(keyWord);
            //            ScoreDoc[] hits = indexSearcher.search(query1, null, 1000).scoreDocs;
            //            for (int i = 0; i < hits.length; i++) {
            //                Document hitDoc = indexSearcher.doc(hits[i].doc);
            //                System.out.println("____________________________");
            //                System.out.println(hitDoc.get("content"));
            //                System.out.println(hitDoc.get("fileName"));
            //                System.out.println(hitDoc.get("filePath"));
            //                System.out.println(hitDoc.get("updateTime"));
            //                System.out.println("____________________________");
            //            }

            String[] fields = { "fileName", "content" }; // 要搜索的字段，一般搜索时都不会只搜索一个字段
            // 字段之间的与或非关系，MUST表示and，MUST_NOT表示not，SHOULD表示or，有几个fields就必须有几个clauses
            BooleanClause.Occur[] clauses = { BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD };
            Query query2 = MultiFieldQueryParser.parse(keyWord, fields, clauses, analyzer);
            // 5、根据searcher搜索并且返回TopDocs
            TopDocs topDocs = indexSearcher.search(query2, 100); // 搜索前100条结果
            System.out.println("共找到匹配处：" + topDocs.totalHits); // totalHits和scoreDocs.length的区别还没搞明白
            ///6、根据TopDocs获取ScoreDoc对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共找到匹配文档数：" + scoreDocs.length);
            QueryScorer scorer = new QueryScorer(query2, "content");
            // 7、自定义高亮代码
            SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span style=\"backgroud-color:black;color:red\">", "</span>");
            Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
            for (ScoreDoc scoreDoc : scoreDocs) {
                ///8、根据searcher和ScoreDoc对象获取具体的Document对象
                Document document = indexSearcher.doc(scoreDoc.doc);
                System.out.println("-----------------------------------------");
                System.out.println(document.get("fileName") + ":" + document.get("filePath"));
                System.out.println(highlighter.getBestFragment(analyzer, "content", document.get("content")));
                System.out.println("-----------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
        } finally {
            try {
                if (analyzer != null) analyzer.close();
                if (directoryReader != null) directoryReader.close();
                if (indexDirectory != null) indexDirectory.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Date end = new Date();
        System.out.println("查看关键字耗时：" + (end.getTime() - begin.getTime()) + "ms\n");
    }


}
