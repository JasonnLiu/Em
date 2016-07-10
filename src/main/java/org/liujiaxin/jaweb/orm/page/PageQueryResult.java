package org.liujiaxin.jaweb.orm.page;


public class PageQueryResult extends PageQuery {

    private int recordCount;

    private int maxRecordCount = MAX_RECORDS_COUNT;

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public void setMaxRecordCount(int maxRecordCount) {
        this.maxRecordCount = maxRecordCount;
    }

    public int getPageCount() {
        int visibleRecordCount = Math.min(this.recordCount, Math.min(MAX_RECORDS_COUNT, maxRecordCount));
        int pageCount = (visibleRecordCount - 1) / getPageSize() + 1;

        if (pageCount > MAX_PAGE) {
            pageCount = MAX_PAGE;
        }

        if (pageCount < MIN_PAGE) {
            pageCount = MIN_PAGE;
        }

        return pageCount;
    }

    public boolean isMore() {
        return hasMore();
    }

    public boolean hasMore() {
        return !isSimple() && getPage() < getPageCount();
    }
    /*
    public static PageQueryResult copyFrom(PageQuery pageQuery, int recordCount) {
        PageQueryResult vo = new PageQueryResult();
        BeanUtil.copyProperties(vo, pageQuery);
        vo.setRecordCount(recordCount);
        return vo;
    }
    */
}
