/*   1:    */
package com.blog.entity;
/*   2:    */
/*   3:    */

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/*   4:    */
/*   5:    */
/*   6:    */

/*   7:    */
@Table(name = "t_blog")
/*   8:    */ public class Blog
        /*   9:    */ implements Serializable
        /*  10:    */ {
    /*  11:    */   private static final long serialVersionUID = 1L;
    @Id
    /*  12:    */ private Integer id;
    /*  13:    */   private String title;
    /*  14:    */   private String summary;
    /*  15:    */ private Date releaseDate;
    /*  16:    */   private Integer clickHit;
    /*  17:    */   private Integer replyHit;
    /*  18:    */   private String content;
    @Column(name = "type_id")
    private Integer typeId;

    @Transient
    /*  19:    */ private String contentNoTag;
    @Transient
    /*  20:    */ private BlogType blogType;
    @Transient
    /*  21:    */ private Integer blogCount;
    @Transient
    /*  22:    */ private String releaseDateStr;
    /*  23:    */   private String keyWord;
    @Transient
    /*  24: 34 */ private List<String> imagesList = new LinkedList();
    /*  25:    */

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", releaseDate=" + releaseDate +
                ", clickHit=" + clickHit +
                ", replyHit=" + replyHit +
                ", content='" + content + '\'' +
                ", typeId=" + typeId +
                ", contentNoTag='" + contentNoTag + '\'' +
                ", blogType=" + blogType +
                ", blogCount=" + blogCount +
                ", releaseDateStr='" + releaseDateStr + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", imagesList=" + imagesList +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getClickHit() {
        return clickHit;
    }

    public void setClickHit(Integer clickHit) {
        this.clickHit = clickHit;
    }

    public Integer getReplyHit() {
        return replyHit;
    }

    public void setReplyHit(Integer replyHit) {
        this.replyHit = replyHit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getContentNoTag() {
        return contentNoTag;
    }

    public void setContentNoTag(String contentNoTag) {
        this.contentNoTag = contentNoTag;
    }

    public BlogType getBlogType() {
        return blogType;
    }

    public void setBlogType(BlogType blogType) {
        this.blogType = blogType;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public String getReleaseDateStr() {
        return releaseDateStr;
    }

    public void setReleaseDateStr(String releaseDateStr) {
        this.releaseDateStr = releaseDateStr;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List<String> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<String> imagesList) {
        this.imagesList = imagesList;
    }
}



/* Location:           D:\classes\

 * Qualified Name:     com.blog.entity.Blog

 * JD-Core Version:    0.7.0.1

 */