package com.xinjian.wechat.service;

import com.xinjian.wechat.util.FileUtil;
import com.xinjian.wechat.vo.FileVo;
import com.xinjian.wechat.vo.SearchVo;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SearchService {

    @Value("${file.source}")
    private String Source_DIR;
    @Value("${file.index}")
    private String INDEX_DIR;

    public List<SearchVo> doSearch(String keyWord) {

        List<SearchVo> searchVos = new ArrayList<>();
        // 1、创建Analyzer词法分析器，注意SimpleAnalyzer和StandardAnalyzer的区别
        Analyzer analyzer = null;
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

            String[] fields = {"fileName", "content"}; // 要搜索的字段，一般搜索时都不会只搜索一个字段
            // 字段之间的与或非关系，MUST表示and，MUST_NOT表示not，SHOULD表示or，有几个fields就必须有几个clauses
            BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
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
                SearchVo searchVo = new SearchVo();
                FileVo fileVo = new FileVo();
                fileVo.setFileName(document.get("fileName"));
                fileVo.setLocation(document.get("filePath"));
                searchVo.setContent(highlighter.getBestFragment(analyzer, "content", document.get("content")));
                searchVo.setFile(fileVo);
                searchVos.add(searchVo);

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
        return searchVos;

    }

    public void createIndex() {
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

            List<File> files = FileUtil.listAllFiles(Source_DIR, null);
            for (File file : files) {
                String content = "";
                //获取文件后缀
                String type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                if ("txt".equalsIgnoreCase(type)) {
                    content += FileUtil.readTxt(file);
                } else if ("doc".equalsIgnoreCase(type)) {
                    content += FileUtil.readWord(file, "doc");
                } else if ("docx".equalsIgnoreCase(type)) {
                    content += FileUtil.readWord(file, "docx");
                } else if ("xls".equalsIgnoreCase(type)) {
                    content += FileUtil.readExcel(file, "xls");
                } else if ("xlsx".equalsIgnoreCase(type)) {
                    content += FileUtil.readExcel(file, "xlsx");
                } else if ("pdf".equalsIgnoreCase(type)) {
                    content += FileUtil.readPDF(file);
                } else if ("html".equalsIgnoreCase(type)) {
                    content += FileUtil.readHtml(file);
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
    }

}
