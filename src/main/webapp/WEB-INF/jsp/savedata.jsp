<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

		<h1>Upload XML Files</h1>
       <form:form method="post" action="savexml" enctype="multipart/form-data">  
		<input type="file" name="firstFile" value=firstFile required />
		<input type="file" name="secondFile" value=secondFile required />
		<input type="submit" value="Save & search" />
		
       </form:form>  
