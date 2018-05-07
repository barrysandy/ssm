<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.wisesoft.com/eimm/ui" prefix="ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ImgUrl" value="http://111.231.209.74:8888/"/>
<%
    /**-====================================================================
     *                               基本常量的设定
     *=====================================================================**/
    //设定context path
    String path = request.getContextPath();
    if("/".equals(path.trim())) path = "";
    pageContext.setAttribute("path",path);
%>
<link rel="stylesheet" type="text/css" href="${path}/resources/core/andyui/icon/iconfont.css">
<link rel="stylesheet" type="text/css" href="${path}/resources/core/andyui/css/animate.css">
<link rel="stylesheet" type="text/css" href="${path}/resources/core/andyui/css/andyui.css">
<script src="${path}/resources/core/andyui/js/jquery.min.js"></script>
<script src="${path}/resources/core/andyui/js/andyui-debug.js"></script>
<script src="${path}/resources/core/andyui/js/laydate/laydate-debug.js"></script>
<script src="${path}/resources/core/eimm/js/base.js"></script>