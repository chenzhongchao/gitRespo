package com.fise.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.Constants;
import com.fise.utils.JsonUtil;


public class Page<T> implements Pagination,Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    
    @JsonProperty("page_no")
    @NotNull
    private int pageNo = 1; // 当前页, 默认为第1页
    
    @JsonProperty("page_size")
    @NotNull
    private int pageSize = Constants.DEFAULT_PAGE_SIZE; // 每页记录数
    
    @JsonProperty("total_count")
    private long totalCount = -1; // 总记录数, 默认为-1, 表示需要查询
    
    @JsonProperty("total_page_count")
    private int totalPageCount = -1; // 总页数, 默认为-1, 表示需要计算
    
    // 用于分页查询时的条件设置
    @JsonProperty("param")
    private T param;
    
    @JsonProperty("extra_param")
    private Map<String,Object> extraParam;
    
    @JsonProperty("result")
    private List<T> result; // 当前页记录List形式
    
    public Page() {
        super();
    }
    
    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
    
    public int getPageNo() {
        return pageNo;
    }
    
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        computeTotalPage();
    }
    
    public long getTotalCount() {
        return totalCount;
    }
    
    public int getTotalPageCount() {
        return totalPageCount;
    }
    
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        computeTotalPage();
    }
    
    protected void computeTotalPage() {
        if (getPageSize() > 0 && getTotalCount() > -1) {
            this.totalPageCount = (int) (getTotalCount() % getPageSize() == 0 ? getTotalCount() / getPageSize()
                    : getTotalCount() / getPageSize() + 1);
        }
    }
    
    public T getParam() {
        return param;
    }
    
    public void setParam(T param) {
        this.param = param;
    }
    
    public Map<String,Object> getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(Map<String,Object> extraParam) {
        this.extraParam = extraParam;
    }

    public List<T> getResult() {
        return result;
    }
    
    public void setResult(List<T> result) {
        this.result = result;
    }
    
    /**
     * 为Redis分页设计，其他场景慎重使用
     * 
     * @author 水墨
     * @return start
     */
    @JsonIgnore
    public int getStart() {
        return (pageNo - 1) * pageSize;
    }
    
    /**
     * 为Redis分页设计，其他场景慎重使用
     * 
     * @author 水墨
     * @return end
     */
    @JsonIgnore
    public int getEnd() {
        return pageNo * pageSize - 1;
    }

    @Override
    @JsonIgnore
    public int getCurrentPageNo() {
        return pageNo;
    }
    
    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
