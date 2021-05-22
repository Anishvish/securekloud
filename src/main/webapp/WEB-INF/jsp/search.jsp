
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<center>
	<h1>Person List</h1>
	<form method="get" action="search">
		<input type="text" name="q" /> <input type="submit" value="search" />
		<br />
		<br />
		<table border="1" width="80%" cellpadding="6"
			style="border-collapse: collapse">
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Phone Number</th>
				<th>Salary</th>
				<th>Pension</th>
				<th>Address</th>
			</tr>
			<c:forEach var="person" items="${list}">
				<tr>
					<td>${person.id}</td>
					<td>${person.name}</td>
					<td>${person.phonenumber}</td>
					<td>${person.salary}</td>
					<td>${person.pension}</td>
					<td>${person.address}</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</center>
