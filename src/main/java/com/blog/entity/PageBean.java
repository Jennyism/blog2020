/*  1:   */ package com.blog.entity;
/*  2:   */
/*  3:   */ public class PageBean
/*  4:   */ {
    /*  5:   */   private int page;
    /*  6:   */   private int pageSize;

    public PageBean(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    /* 39:   */ }



/* Location:           D:\classes\

 * Qualified Name:     com.blog.entity.PageBean

 * JD-Core Version:    0.7.0.1

 */