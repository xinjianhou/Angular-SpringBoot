package com.xinjian.wechat.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinjian.wechat.config.UploadConfig;
import com.xinjian.wechat.util.FileUtil;
import com.xinjian.wechat.vo.FileVo;
import com.xinjian.wechat.vo.SearchVo;

@Service
public class SearchServiceImpl {
	
	//defining the path for file to search
	//private final static String INDEX_DIR="C:/Users/David.Yang01/Documents/Downloads";
	//private final static String Source_DIR = "C:/Users/David.Yang01/Documents/Downloads";
	
	//max records
	private final static int MAX_COUNT = 100;
	
	private static int totalCount = 0;
	
	//upload file class
	@Autowired
	UploadConfig uploadConfig;
	
	/**
	 * Search by keyword
	 * @param keyWord
	 * @return
	 */

	public List<SearchVo> search(String keyWord){
	//public static void search(String keyWord){
		//building the index for documents
		creatIndex();
		
		Date begin = new Date();
        //build Analyzer
        Analyzer analyzer  = null;
        Directory indexDirectory = null;
        DirectoryReader directoryReader = null;
        
        List<SearchVo> searchVos = new ArrayList<SearchVo>();
        try {
            analyzer = new SimpleAnalyzer();
            indexDirectory = FSDirectory.open(FileSystems.getDefault().getPath(uploadConfig.getUploadPath()));
            directoryReader = DirectoryReader.open(indexDirectory);
            IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
            
            //Defining two fields for search
            String[] fields = { "fileName", "content" };
            //MUST:and，MUST_NOT:not, SHOULD:or, two files, two clauses
            BooleanClause.Occur[] clauses = { BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD };
            Query query = MultiFieldQueryParser.parse(keyWord, fields, clauses, analyzer);
            //how many records to search every time
            TopDocs topDocs = indexSearcher.search(query, MAX_COUNT);
            System.out.println("match records:" + topDocs.totalHits);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("total document records: " + scoreDocs.length);
            QueryScorer scorer = new QueryScorer(query, "content");
            
            SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span style=\"backgroud-color:black;color:red\">", "</span>");
            Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
            SearchVo searchVo = null;
            FileVo fileVo = null;
            for (ScoreDoc scoreDoc : scoreDocs) {
            	searchVo = new SearchVo();
            	fileVo = new FileVo();
                Document document = indexSearcher.doc(scoreDoc.doc);

                System.out.println(document.get("fileName") + ":" + document.get("filePath"));
				System.out.println(highlighter.getBestFragment(analyzer, "content", document.get("content")));
				fileVo.setId(Long.parseLong(document.get("id")));
				fileVo.setFileName(document.get("fileName"));
                fileVo.setLocation(document.get("filePath"));
                fileVo.setFileSize(Long.parseLong(document.get("fileSize")));
				fileVo.setUploadDate(new Date(Long.parseLong(document.get("updateTime"))));
                
                System.out.println("content====:" + document.get("content"));
                
                searchVo.setFile(fileVo);
            	searchVo.setContent(highlighter.getBestFragment(analyzer, "content", document.get("content")));
                
                searchVos.add(searchVo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}finally {
            try {
                if (analyzer != null) analyzer.close();
                if (directoryReader != null) directoryReader.close();
                if (indexDirectory != null) indexDirectory.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Date end = new Date();
        System.out.println("wasting time :" + (end.getTime() - begin.getTime()) + "ms\n");
		
		return searchVos;
	}
	
	/*public static void main(String []args){
		search("java");
	}
	
	/**
	 * building the index for documents
	 */
	public void creatIndex(){
        Date begin = new Date();
        Analyzer analyzer = null;
        Directory directory = null;
        IndexWriter indexWriter = null;
        try {
            analyzer = new SimpleAnalyzer();
            directory = FSDirectory.open(FileSystems.getDefault().getPath(uploadConfig.getUploadPath()));
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(directory, config);
            indexWriter.deleteAll();

            List<File> files = FileUtil.listAllFiles(uploadConfig.getUploadPath(), null);
            DecimalFormat decimalFormat = new DecimalFormat("00000000");
            for (File file : files) {
                String content = "";
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
                Document document = new Document();
                ++totalCount;
                document.add(new Field("id", decimalFormat.format(totalCount), TextField.TYPE_STORED));
                document.add(new Field("content", content, TextField.TYPE_STORED));
                document.add(new Field("fileName", file.getName(), TextField.TYPE_STORED));
                document.add(new Field("filePath", file.getAbsolutePath(), TextField.TYPE_STORED));
                document.add(new Field("fileSize", file.length() +"", TextField.TYPE_STORED));
                document.add(new Field("updateTime", file.lastModified() +"", TextField.TYPE_STORED));
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
        System.out.println("wasting time for creating index：" + (end.getTime() - begin.getTime()) + "ms\n");
    }

}
