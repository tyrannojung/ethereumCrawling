package kr.ne.abc.template.common.util.pagination;

public class AdminPageMaker {
   
	private int page; //현재 페이지 번호
    private int perPageNum; 
	
	private int totalCount;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private int displayPageNum = 5;

    public AdminPageMaker() {
		this.page = 1;
        this.perPageNum = 10; //한 페이지당 보여줄 게시글의 갯수
	}
    
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcData();
    }
    
    public int getStartPage() {
        return startPage;
    }
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }
    public int getEndPage() {
        return endPage;
    }
    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
    public boolean isPrev() {
        return prev;
    }
    public void setPrev(boolean prev) {
        this.prev = prev;
    }
    public boolean isNext() {
        return next;
    }
    public void setNext(boolean next) {
        this.next = next;
    }
    public int getDisplayPageNum() {
        return displayPageNum;
    }
    public void setDisplayPageNum(int displayPageNum) {
        this.displayPageNum = displayPageNum;
    }
    
    /**
     * 특정 페이지의 게시글 시작 번호, 게시글 시작 행 번호
     * @return
     */
    public int getPageStart() {
        return (this.page-1)*perPageNum; //현재 페이지의 게시글 시작 번호 = (현재 페이지 번호 - 1) * 페이지당 보여줄 게시글 갯수
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
    
    private void calcData() {
        
        endPage = (int) (Math.ceil(page / (double) displayPageNum) * displayPageNum);
 
        startPage = (endPage - displayPageNum) + 1;
        if(startPage <= 0) startPage = 1;
        
        int tempEndPage = (int) (Math.ceil(totalCount / (double) perPageNum));
        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }
 
        prev = startPage == 1 ? false : true;
        next = endPage * perPageNum < totalCount ? true : false;
        
    }
    
}
