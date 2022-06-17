package kr.ne.abc.template.common.util.pagination;

public class PagingVO {
	
	private int page; //현재 페이지 번호
    private int perPageNum; //한 페이지당 보여줄 게시글의 갯수
    
    /**
     * 특정 페이지의 게시글 시작 번호, 게시글 시작 행 번호
     * @return
     */
    public int getPageStart() {
        return (this.page-1)*perPageNum; //현재 페이지의 게시글 시작 번호 = (현재 페이지 번호 - 1) * 페이지당 보여줄 게시글 갯수
    }
    
    /**
     * 기본 생성자
     */
    public PagingVO() {
        this.page = 1;
        this.perPageNum = 10;        
    }
    
    public int getPage() {
        return page;        
    }
    
    public void setPage(int page) {
        if(page <= 0) {
            this.page = 1;
        } else {
            this.page = page;
        }        
    }
    
    public int getPerPageNum() {
        return perPageNum;        
    }
    
    public void setPerPageNum(int pageCount) {
//        int defaultPageCount = this.perPageNum;
//        if(pageCount != defaultPageCount) {
//            this.perPageNum = defaultPageCount;
//        } else {
//            this.perPageNum = pageCount;
//        }
        
        this.perPageNum = pageCount;
    }

}
