package kr.ne.abc.template.common.util.pagination;

public class PageMaker {
	
	private PagingVO pagingVO;
    private int totalCount;
    private int firstPage;
    private int startPage;
    private int endPage;
    private int lastPage;
    private boolean prev;
    private boolean next;
    private int displayPageNum = 5; //화면 하단에 보여지는 페이지 버튼의 수
    
    public PagingVO getPagingVO() {
        return pagingVO;
    }
    public void setPagingVO(PagingVO pagingVO) {
        this.pagingVO = pagingVO;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcData();
    }
    
    /*
     * 페이징 관련 메소드
     * pagingVO.getPage() : 현재 페이지 번호
     * pagingVO.getPerPageNum() : 한 페이지당 보여줄 게시글의 갯수
     * int displayPageNum : 화면 하단에 보여지는 페이지 버튼의 수
     * int totalCount : 총 게시글 수
     * int firstPage : 첫페이지 번호
     * int startPage : 화면에 보여질 첫번째 페이지 번호, 시작 페이지 번호
     * int endPage : 화면에 보여질 마지막 페이지 번호, 끝 페이지 번호
     * int lastPage : 끝페이지 번호
     * boolean prev : 이전 버튼 생성 여부
     * boolean next : 다음 버튼 생성 여부
     * 페이징의 버튼들을 생성하는 계산식을 만들었다. 끝 페이지 번호, 시작 페이지 번호, 이전, 다음 버튼들을 구한다.
     */
    private void calcData() {
        
    	//끝 페이지 번호 = (현재 페이지 번호 / 화면에 보여질 페이지 번호의 갯수) * 화면에 보여질 페이지 번호의 갯수
        endPage = (int) (Math.ceil(pagingVO.getPage() / (double) displayPageNum) * displayPageNum);
        
        //시작 페이지 번호 = (끝 페이지 번호 - 화면에 보여질 페이지 번호의 갯수) + 1
        startPage = (endPage - displayPageNum) + 1;
        if(startPage <= 0) startPage = 1;
        
        //마지막 페이지 번호 = 총 게시글 수 / 한 페이지당 보여줄 게시글의 갯수
        int tempEndPage = (int) (Math.ceil(totalCount / (double) pagingVO.getPerPageNum()));
        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }
        
        //이전 버튼 생성 여부 = 시작 페이지 번호 == 1 ? false : true
        prev = startPage == 1 ? false : true;
        
        //다음 버튼 생성 여부 = 끝 페이지 번호 * 한 페이지당 보여줄 게시글의 갯수 < 총 게시글의 수 ? true: false
        next = endPage * pagingVO.getPerPageNum() < totalCount ? true : false;
        
    }
    
    public int getFirstPage() {
    	firstPage = 1;
    	return firstPage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
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
    public int getLastPage() {
    	lastPage = (int) (Math.ceil(totalCount / (double) pagingVO.getPerPageNum()));
    	return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
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
    
}
