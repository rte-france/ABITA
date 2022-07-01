
/**
 * Load an "amChart"
 * @param chartType the chart type : 'amcolumn', 'amline', 'ampie', 'amradar', 'amxy'
 * @param label chart label
 * @param width the chart width
 * @param height the chart height
 * @param contextPath application context path
 * @param backgroundColor chart background color (hexadecimal format)
 */
function AmChartLoader(chartType, label, width, height, backgroundColor, contextPath) {
	"use-strict";

	if (!(chartType === 'amcolumn' || chartType === 'amline'
			|| chartType === 'ampie' || chartType === 'amradar' || chartType === 'amxy')) {
		throw "Wrong parameter 'chartType' used in AMChartLoader function ";
	}
	if (!(typeof label === "string")) {
		throw "Wrong parameter 'label' used in AMChartLoader function ";
	}
	if (!(typeof width === "number")) {
		throw "Wrong parameter 'width' used in AMChartLoader function ";
	}
	if (!(typeof height === "number")) {
		throw "Wrong parameter 'height' used in AMChartLoader function ";
	}
	var colorRegex = /#[0-9a-f]{6}/i;
	if (!(typeof backgroundColor === "string") || !(colorRegex.test(backgroundColor)) ) {
		throw "Wrong parameter 'backgroundColor' used in AMChartLoader function ";
	}
	if (!(typeof contextPath === "string")) {
		throw "Wrong parameter 'contextPath' used in AMChartLoader function ";
	}

	var setting_file = contextPath + "/GenerateAmchartsXmlServlet?" + (new Date()).getTime() + "&type=settings&collection=" + label;
    var so = new SWFObject(contextPath + "/resources/javascript/" + chartType + ".swf", chartType, width, height, "8", backgroundColor);
    so.addVariable("path", contextPath + "/pages/amcharts/");
    so.addVariable("settings_file", encodeURIComponent(contextPath + "/resources/amcharts/" + label + "-settings.xml," + setting_file));
    so.addVariable("data_file", escape(contextPath + "/GenerateAmchartsXmlServlet?collection=" + label + "&" + (new Date()).getTime() + "&type=data"));
    so.addVariable("preloader_color", "#999999");
    so.addParam("wmode", "opaque");
    so.write(label);
	return so;
}
