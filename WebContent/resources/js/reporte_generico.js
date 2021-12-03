var tableToExcel = (function() {
  var uri = 'data:application/vnd.ms-excel;base64,'
    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta charset="utf-8"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
  return function(table, name) {
    if (!table.nodeType) table = document.getElementById(table)
    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
    window.location.href = uri + base64(format(template, ctx))    
  }
})()


function PopupCenter(pageURL, title,w,h) {
	var link = '';
	//var link1 = 'http://serverbi/ReportServer_DBIABACO/Pages/ReportViewer.aspx?%2F4_ABACODW_RS_FI%2FIndicadores%20Instrucciones%201&rs%3AClearSession=true&rc%3AView=ce0d17e5-9592-4d4c-8683-a28408753d85';	
	//var link2 = 'http://serverbi/ReportServer_DBIABACO/Pages/ReportViewer.aspx?%2F4_ABACODW_RS_FI%2FIndicadores%20Instrucciones%202&rs%3AClearSession=true&rc%3AView=360171d3-478d-4b33-a33c-af4e2b920fa3';
	var link1 = 'http://svbides/ReportServer/Pages/ReportViewer.aspx?%2F4_ABACODW_RS_FI%2FIndicadores%20Legal%201&rs%3AClearSession=true&rc%3AView=b56d69c6-8cab-4a88-82d5-2b8d2cf82c45';	
	var link2 = 'http://svbides/ReportServer/Pages/ReportViewer.aspx?%2F4_ABACODW_RS_FI%2FIndicadores%20Instrucciones%202&rs%3AClearSession=true&rc%3AView=fb4a7665-ce28-445d-9424-2a2c4daeadda';
	if(pageURL == 1){
		link = link1; 
	} else {
		link = link2;
	}
	console.log(pageURL);
	console.log(link);
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);
	window.open(link,'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width='+w+',height='+h+',top='+top+',left='+left);	    
} 