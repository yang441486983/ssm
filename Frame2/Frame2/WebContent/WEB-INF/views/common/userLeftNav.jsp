<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
 $(document).ready(function(){
	$("#left_cate").affix({
		offset : {
			top : 125,
			bottom : function(){
				return(this.bottom = $('.bottom').outerHeight(true))
			        }
			}
	})
}); 
</script>
<div class="list-group" >
   	<ul class="nav nav-pills nav-stacked"  id="left_cate">
   	</ul>				
</div>


