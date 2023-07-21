<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.sicheng.common.persistence.Page"%>
<!-- 分页导航start   页面model中的变量名称为 page-->
<%
Page page_inner=(Page)request.getAttribute("page");
if(page_inner==null){
	page_inner=new Page();
	}
%>
<div class="body-bottom">
	<div class="page_nav">
		<ul class="pagination pagination-sm pull-left">
			<%if (page_inner.getPageNo() == page_inner.getFirst()) {// 如果是首页,"上一页"不可点击%>
				<li class="disabled"><a href="javaScript:;">&laquo;${fns:fy("上一页")}</a></li>
			<%} else {// 如果不是首页,"上一页"可点击   pageURL+prev%>
				<li><a href='<%=(page_inner.getPageURL()+page_inner.getPrev())%>'>&laquo;${fns:fy("上一页")}</a></li>
			<%}
			int begin = page_inner.getPageNo() - (page_inner.getLength() / 2);
			if (begin < page_inner.getFirst()) {
				begin = page_inner.getFirst();
			}
			int end = begin + page_inner.getLength() - 1;
			if (end >= page_inner.getLast()) {
				end = page_inner.getLast();
				begin = end - page_inner.getLength() + 1;
				if (begin < page_inner.getFirst()) {
					begin = page_inner.getFirst();
				}
			}
			//在循环中生成 左边的页码。slider为1时，作用就是“首页”
			if (begin > page_inner.getFirst()) {
				int i = 0;
				for (i = page_inner.getFirst(); i < page_inner.getFirst() + page_inner.getSlider() && i < begin; i++) {
				%>
				<li><a href="<%= (page_inner.getPageURL() + i) %>"><%= (i + 1 - page_inner.getFirst()) %></a></li>
				<%}
				if (i < begin) {%>
				<li class="disabled"><a href="javascript:;">...</a></li>
				<%}
			}
			//在循环中生成 中间的页码，如当前是第50页，46，47，48，49，50，51，52，53，54，并且50页不可点击
			for (int i = begin; i <= end; i++) {
				if (i == page_inner.getPageNo()) {
				%>
				<li class="active"><a href="javascript:;"><%=( i + 1 - page_inner.getFirst())%></a></li>
				<% } else {%>
				<li><a href="<%=(page_inner.getPageURL()+i)%>"><%=(i + 1 - page_inner.getFirst())%></a></li>
				<% }
			}
			if (page_inner.getLast() - end > page_inner.getSlider()) {
			%>
				<li class="disabled"><a href="javascript:;" class="disabled">...</a></li>
			<% 
				end = page_inner.getLast() - page_inner.getSlider();
			}
			//在循环中生成 右边的页码。slider为1时，作用就是“尾页”
			for (int i = end + 1; i <= page_inner.getLast(); i++) {
			%>
				<li><a href="<%=(page_inner.getPageURL()+i)%>"><%=(i + 1 - page_inner.getFirst())%></a></li>
			<%}if (page_inner.getPageNo() == page_inner.getLast()) {// 如果是尾页,"下一页"不可点击%>
				<li class="disabled"><a href='javascript:;'>${fns:fy("下一页")}&raquo;</a></li>
			<%} else {// 如果不是尾页,"下一页"可点击%>
				<li><a href='<%=(page_inner.getPageURL()+page_inner.getNext()) %>'>${fns:fy("下一页")}&raquo;</a></li>
			<%}%>
		</ul>
		<span class="pull-left ml15">
			${fns:fy("共")}&nbsp;&nbsp;${page.count}&nbsp;&nbsp;${fns:fy("条")}&nbsp;&nbsp;${page.totalPage}&nbsp;&nbsp;${fns:fy("页")}&nbsp;&nbsp;${fns:fy("每页")}&nbsp;&nbsp;
			<select id="pSize" class="form-control m0" style="width:50px;height:30px;" onchange="(function(obj){fdp.cookie('fdp_pageSize', obj.value);})(this);">
				<option value="20" ${page.pageSize==20?"selected":""}>20</option> 
				<option value="40" ${page.pageSize==40?"selected":""}>40</option> 
				<option value="60" ${page.pageSize==60?"selected":""}>60</option>
				<option value="100" ${page.pageSize==100?"selected":""}>100</option>
			</select> ${fns:fy("条")}&nbsp;&nbsp;
			${fns:fy("到")} <input type="text" id="iptPageTxt" class="form-control" style="width:32px;height:28px;" href="${page.pageURL}" value="${page.pageNo}"
			onkeypress="(function(obj){var e=window.event||this;var c=e.keyCode||e.which;if(c==13)window.location.href=obj.getAttribute('href')+(obj.value)})(this);">
			<span>&nbsp;&nbsp;${fns:fy("页")}</span>
			<button id="iptPageSubmit" class="btn btn-sm btn-info" 
			onclick="(function(obj){var input=document.getElementById('iptPageTxt');if(input.value==''){return;}window.location.href=input.getAttribute('href')+input.value;})(this);">${fns:fy("跳转")}</button>
		</span>
	</div>
</div>
<!-- 分页导航end -->