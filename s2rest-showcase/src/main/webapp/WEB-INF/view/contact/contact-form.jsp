<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Contact</title>
<s:head />
<link rel="stylesheet" type="text/css" href="<s:url value="/styles/bootstrap.min.css" />" />
</head>
<body>
	<div class="container">
	<s:form action="/contact/new" method="post">
		<input type="hidden" name="id" value="${id}" />
		<table class="table">
			<s:textfield name="contact.name" label="Name" />
			<s:textfield name="contact.phone" label="Phone" />
			<s:textarea name="contact.address" label="Description"></s:textarea>

			<tr>
				<td colspan="2">
					<input type="submit" value="Save" class="btn btn-primary" />
					<input type="reset" value="Reset" class="btn" />
				</td>
			</tr>
		</table>
	</s:form>
	</div>
</body>
</html>