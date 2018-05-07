package com.xiaoshu.tag;

import com.xiaoshu.util.Pager;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.List;

public class PageTag extends BodyTagSupport {
    private static final long serialVersionUID = 2169300686841812094L;
    private Pager pager;
    /**
     * 分页类型
     */
    private String type;

    /**
     * 在不提供 用户选择数据条数的时候，
     * 根据不同的场景 开发人员根据页面调整当前页面可装载的条数
     */
    private int pageSizeDef = 10;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspException {
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        doView1();
        return EVAL_PAGE;
    }

    private void doView() {
        StringBuffer htmlBuffer = new StringBuffer();
        if (pager != null) {
            int pageCount = pager.getPageCount();
            int pageNumber = pager.getPageNumber();
            if (pageNumber < 1) {
                pageNumber = 1;
                pager.setPageNumber(1);
            }
            if (pageNumber > pageCount) {
                pageNumber = pageCount;
                pager.setPageNumber(pageCount);
            }
            List<Object> dataList = pager.getList();
            if (dataList == null || dataList.isEmpty()) {
                // writeContent("<div class=\"m-page m-page-lt\">暂时无数据!</div>");
                return;
            }
            htmlBuffer.append("<div class=\"layout-foot\">");
            htmlBuffer.append("<div class=\"m-pagebar\" id = \"pagination1\">");
            htmlBuffer.append("<div class=\"f-left\"> 每页显示");
            htmlBuffer.append("<select class=\"u-select xs auto\" name=\"pageSize\" onchange=\"P.pageSizeChange()\" page-select>");
            htmlBuffer.append("<option>10</option><option>20</option><option>50</option><option>100</option></select>");
            htmlBuffer.append("<button class=\"u-btn xs texture\" onclick=\"javascript:P.gotoPage(1)\" page-fist><i class=\"iconfont\">&#xe60e;</i></button>");
            htmlBuffer.append("<button class=\"u-btn xs texture\" onclick=\"javascript:P.previousPage()\" page-prev><i class=\"iconfont\">&#xe626;</i></button>");
            htmlBuffer.append("第<input type=\"text\" id=\"pageNumber\" name=\"pageNumber\" style=\"width:50px\" value=\"" + pageNumber + "\" class=\"u-input xs auto\" page-index>页");
            htmlBuffer.append("<button class=\"u-btn xs texture\" onclick=\"javascript:P.nextPage()\" page-next><i class=\"iconfont\">&#xe60f;</i></button> 共<a page-counts>" + pageCount + "</a>页");
            htmlBuffer.append("<button class=\"u-btn xs texture\" onclick=\"javascript:P.gotoPage(").append(pageCount).append(")\" page-last><i class=\"iconfont\">&#xe60d;</i></button></div><div class=\"f-right\" page-record>共" + pager.getFullListSize() + "记录</div></div></div></div>");
        }
        writeContent(htmlBuffer.toString());
    }

    private void doView1() {
        StringBuffer htmlBuffer = new StringBuffer();
        if (pager != null) {
            int pageCount = pager.getPageCount();
            int pageNumber = pager.getPageNumber();
            int totalCount = pager.getFullListSize();
            int pageSize = pager.getPageSize();
            if (pageNumber < 1) {
                pageNumber = 1;
                pager.setPageNumber(1);
            }
            if (pageNumber > pageCount) {
                pageNumber = pageCount;
                pager.setPageNumber(pageCount);
            }
            List<Object> dataList = pager.getList();
            if (dataList == null || dataList.isEmpty()) {
                // writeContent("<div class=\"m-page m-page-lt\">暂时无数据!</div>");
                return;
            }
            htmlBuffer.append("<input type=\"hidden\" id=\"pageNumber\" name=\"pageNumber\" value=\"").append(pageNumber).append("\"/>");
            htmlBuffer.append("<div class=\"layout-foot f-b-n-t\"><div class=\"m-pagebar\"> <div class=\"f-left\">");
            htmlBuffer.append("每页显示");
            htmlBuffer.append(" <select class=\"u-select xs auto\" name=\"pageSize\"  onchange=\"P.pageSizeChange()\">" + "<option value=\"10\" ");
            if (pageSize == 10) {
                htmlBuffer.append("selected=\"selected\"");
            }
            htmlBuffer.append(">10</option> <option value=\"20\" ");
            if (pageSize == 20) {
                htmlBuffer.append("selected=\"selected\"");
            }
            htmlBuffer.append(">20</option> <option value=\"50\" ");
            if (pageSize == 50) {
                htmlBuffer.append("selected=\"selected\"");
            }
            htmlBuffer.append(">50</option> <option value=\"100\" ");
            if (pageSize == 100) {
                htmlBuffer.append("selected=\"selected\"");
            }
            htmlBuffer.append(">100</option></select>");
            // 首页
            htmlBuffer.append("<button class=\"u-btn xs texture\" onclick=\"javascript:P.gotoPage(1)\"><i class=\"iconfont\">&#xe60e;</i></button>");
            if (pageNumber <= 1) {
                htmlBuffer.append("<button class=\"u-btn xs texture\" onclick=\"javascript:void(0)\"><i class=\"iconfont\">&#xe626;</i></button>");// 上一页
            } else {
                htmlBuffer.append("<button class=\"u-btn xs texture\" onclick=\"javascript:P.previousPage()\"><i class=\"iconfont\">&#xe626;</i></button>");// 上一页
            }
            htmlBuffer.append("   第 <input type=\"text\" id=\"inPageNumber\" style=\"width:50px\" onchange=\"javascript:P.gotoPage2()\" value=\"").append(pageNumber).append("\" class=\"u-input xs auto\">");

            htmlBuffer.append("共").append(pageCount).append("页");

            if (pageNumber + 1 > pageCount) {
                htmlBuffer.append("<button class=\"u-btn xs texture\" onclick=\"javascript:void(0)\"><i class=\"iconfont\">&#xe60f;</i></button>");
            } else {
                htmlBuffer.append("<button class=\"u-btn xs texture\" onclick=\"javascript:P.nextPage()\"><i class=\"iconfont\">&#xe60f;</i></button>");// 下一页
            }
            // 尾页
            htmlBuffer.append("<button class=\"u-btn xs texture\" onclick=\"javascript:P.gotoPage(").append(pageCount).append(")\"><i class=\"iconfont\">&#xe60d;</i></button>");
            // 尾
            htmlBuffer.append("  </div><div class=\"f-right\">共").append(totalCount).append("条记录</div> </div>");
            htmlBuffer.append("</div>");
        }
        writeContent(htmlBuffer.toString());
    }

    private void doView2() {
        StringBuffer htmlBuffer = new StringBuffer();
        if (pager != null) {
            int pageCount = pager.getPageCount();
            int pageNumber = pager.getPageNumber();
            if (pageNumber < 1) {
                pageNumber = 1;
                pager.setPageNumber(1);
            }
            if (pageNumber > pageCount) {
                pageNumber = pageCount;
                pager.setPageNumber(pageCount);
            }
            List<Object> dataList = pager.getList();
            if (dataList == null || dataList.isEmpty()) {
                // writeContent("<div class=\"m-page m-page-lt\">暂时无数据!</div>");
                return;
            }
            htmlBuffer.append("<ul class=\"page\">");
            htmlBuffer.append("<li><a href=\"javascript:P.gotoPage(").append(1).append(")\" > <i class=\"iconfont\">&#xe6cf;</i> </a></li>  <li class=\"next\"><a href=\"javascript:P.previousPage()\"><i class=\"iconfont\"></i></a></li>");
            if (pageCount <= 5) {
                for (int i = 0; i < pageCount; i++) {
                    if (pager.getPageNumber() == i + 1) {
                        htmlBuffer.append("<li><a class=\"current\">" + (i + 1) + "</a></li>");
                    } else {
                        htmlBuffer.append("<li><a href=\"javascript:P.gotoPage(").append(i + 1).append(")\" >" + (i + 1) + "</a></li>");
                    }
                }
            } else {
                int startpage = pageNumber - 2;
                int endpage = pageNumber + 2;
                if (startpage < 1) {
                    startpage = 1;
                    endpage = 5;
                }
                if(endpage>pageCount){
                    endpage=pageCount;
                    startpage=pageCount-4;
                }
                for (int i = startpage; i <= endpage; i++) {
                    if (pager.getPageNumber() == i ) {
                        htmlBuffer.append("<li><a class=\"current\">" + (i) + "</a></li>");
                    } else {
                        htmlBuffer.append("<li><a href=\"javascript:P.gotoPage(").append(i).append(")\" >" + (i) + "</a></li>");
                    }
                }
            }
            htmlBuffer.append(" <li class=\"next\"><a href=\"javascript:P.nextPage()\"><i class=\"iconfont\"></i></a></li>  <li class=\"jiantou\"><a href=\"javascript:P.gotoPage(").append(pageCount).append(")\" ><i class=\"iconfont\">&#xe6cc;</i></a></li>  </ul> ");
        }
        writeContent(htmlBuffer.toString());
    }

    private void writeContent(String html) {
        try {
            IOUtils.write(html, pageContext.getOut());
        } catch (IOException er) {

        }
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
