package com.blog.lucene;

import com.blog.entity.Blog;
import com.blog.service.BlogService;
import com.blog.util.DateUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jennyism
 * @date 20/5/2020 上午10:05
 */
@Component("blogIndex")
public class BlogIndex {
    private Directory dir = null;

    private BlogService blogService;

    public String getLuncenePath() {
        String luncenePath = this.getClass().getClassLoader().getResource("lucene").getPath();
        System.out.println("==============[info path]"+luncenePath);
        File lunceneFile = new File(luncenePath);
        if (!lunceneFile.exists()) {
            lunceneFile.mkdir();
        }
        return lunceneFile.getAbsolutePath();
    }

    private IndexWriter getWriter()
            throws Exception {
        this.dir = FSDirectory.open(Paths.get(getLuncenePath(), new String[0]));
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(this.dir, iwc);
        return writer;
    }

    public void addIndex(Blog blog)
            throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }

    public void updateIndex(Blog blog)
            throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(blog.getId())), doc);
        writer.close();
    }

    public void deleteIndex(String blogId)
            throws Exception {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term[]{new Term("id", blogId)});
        writer.forceMergeDeletes();
        writer.commit();
        writer.close();
    }

    //    public List<Integer> findAllBlogId() throws IOException {
//        List<Integer> ids = new ArrayList<>();
//        this.dir = FSDirectory.open(Paths.get(getLuncenePath(), new String[0]));
//        String[] strings = this.dir.listAll();
//        if (strings.length <= 1){
//            //如果为0，说明没有文件，全部创建
//            return ids;
//        }
//        IndexReader reader = DirectoryReader.open(this.dir);
//        IndexSearcher is = new IndexSearcher(reader);
//        int count = reader.maxDoc();
//        for (int i = 0; i < count; i++) {
//            Document doc = is.doc(i);
//            Integer blogId = doc.getField("blogId").numericValue().intValue();
//            ids.add(blogId);
//        }
//        reader.close();
//        dir.close();
//        return ids;
//    }
    public List<Blog> searchBlog(String q)
            throws Exception {
        this.dir = FSDirectory.open(Paths.get(getLuncenePath(), new String[0]));
        if (dir.listAll()==null || dir.listAll().length == 0){
            return new ArrayList<Blog>();
        }
        IndexReader reader = null;
        try {
            reader = DirectoryReader.open(this.dir);
        } catch (IndexNotFoundException e) {
            return new ArrayList<Blog>();
        }
        IndexSearcher is = new IndexSearcher(reader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        QueryParser parser = new QueryParser("title", analyzer);
        Query query = parser.parse(q);
        QueryParser parser2 = new QueryParser("content", analyzer);
        Query query2 = parser2.parse(q);
        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
        TopDocs hits = is.search(booleanQuery.build(), 100);
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);
        List<Blog> blogList = new LinkedList();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            Blog blog = new Blog();
            blog.setId(Integer.valueOf(Integer.parseInt(doc.get("id"))));
            blog.setReleaseDateStr(doc.get("releaseDate"));
            String title = doc.get("title");
            String content = StringEscapeUtils.escapeHtml(doc.get("content"));
            if (title != null) {
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                if (StringUtils.isEmpty(hTitle)) {
                    blog.setTitle(title);
                } else {
                    blog.setTitle(hTitle);
                }
            }
            if (content != null) {
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                if (StringUtils.isEmpty(hContent)) {
                    if (content.length() <= 200) {
                        blog.setContent(content);
                    } else {
                        blog.setContent(content.substring(0, 200));
                    }
                } else {
                    blog.setContent(hContent);
                }
            }
            blogList.add(blog);
        }
        return blogList;
    }
}
