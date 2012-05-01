<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Contact</title>
<s:head javascriptTooltip="true" />
<link rel="stylesheet" type="text/css"
	href="<s:url value="/styles/bootstrap.min.css" />" />
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="span12">
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>NO</th>
							<th>NAME</th>
							<th>PHONE</th>
							<th>ADDRESS</th>
						</tr>
					</thead>
					<tbody>
						<%
							int num = 1;
						%>
						<s:iterator value="contacts">
							<tr>
								<td><a href="<s:url value="/contact/" /><%= num-1 %>"><%= num++ %></a></td>
								<td>${name}</td>
								<td>${phone}</td>
								<td>${address}</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<a href='<s:url value="/contact/new"></s:url>'>Add new contact</a>
		</div>
	</div>
</body>
</html>