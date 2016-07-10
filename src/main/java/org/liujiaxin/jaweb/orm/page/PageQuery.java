package org.liujiaxin.jaweb.orm.page;

import org.codehaus.jackson.annotate.JsonIgnore;

public class PageQuery {

    public static final int MIN_PAGE = 1;

    public static final int MAX_PAGE = 1000;

    public static final int MIN_PAGE_SIZE = 1;

    public static final int MAX_PAGE_SIZE = 100;

    public static final int MAX_RECORDS_COUNT = 5000;

    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final String PARAM_PAGE = "page";

    public static final String PARAM_PAGE_SIZE = "pageSize";

    private int page = 1;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private boolean pageLimit = true;

    /**
     * 结果的数目
     */
    private int count = Integer.MIN_VALUE;

    /**
     * 是否需要下一页信息
     */
    private boolean simple = false;

    public PageQuery() {
    }

    public PageQuery(int page) {
        this.page = page;
    }

    public PageQuery(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageLimit(boolean pageLimit) {
        this.pageLimit = pageLimit;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSimple(boolean simple) {
        this.simple = simple;
    }

    @JsonIgnore
    public boolean isSimple() {
        return simple;
    }

    /**
     * 获取页面大小
     */
    public int getPageSize() {
        int _pageSize = pageSize;
        if (_pageSize < MIN_PAGE_SIZE) {
            _pageSize = MIN_PAGE_SIZE;
        } else if (_pageSize > MAX_PAGE_SIZE && pageLimit) {
            _pageSize = MAX_PAGE_SIZE;
        }
        return _pageSize;
    }

    /**
     * 获取页面数目：限制
     */
    public int getPageCount() {
        int _count = count;
        if (_count > MAX_RECORDS_COUNT && pageLimit) {
            _count = MAX_RECORDS_COUNT;
        }
        int _pageCount = (_count - 1) / getPageSize() + 1;
        if (_pageCount < MIN_PAGE) {
            _pageCount = MIN_PAGE;
        }
        if (_pageCount > MAX_PAGE && pageLimit) {
            _pageCount = MAX_PAGE;
        }
        return _pageCount;
    }

    /**
     * 获取页码
     */
    public int getPage() {
        int _page = page;
        if (_page < MIN_PAGE) {
            _page = MIN_PAGE;
        } else if (_page > MAX_PAGE && pageLimit) {
            _page = MAX_PAGE;
        }
        int pageCount = getPageCount();
        // 如果设置了count，且页码超过页面数目
        if (count != Integer.MIN_VALUE && _page > pageCount) {
            _page = pageCount;
        }
        return _page;
    }

    /**
     * 获取sql limit
     */
    @JsonIgnore
    public int getLimit() {
        return getPageSize();
    }

    /**
     * 获取sql offset
     */
    @JsonIgnore
    public int getOffset() {
        return (getPage() - 1) * getPageSize();
    }

}
