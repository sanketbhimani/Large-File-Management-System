<%-- 
    Document   : index
    Created on : 2 Jan, 2017, 3:49:27 AM
    Author     : Meet Dave
--%>

<%@page import="java.lang.String"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
        <script language="javascript" type="text/javascript" src="jit-yc.js"></script>

        <style>


            #infovis {
                position:relative;
                width:600px;
                height:600px;
                margin:auto;
                overflow:hidden;
            }


        </style>
    </head>
    <body onload="_init();" style="background-color: white;">
        <div id="infovis"></div>
        <div id="loader"></div>
        <script language="javascript" type="text/javascript">
            var labelType, useGradients, nativeTextSupport, animate;
            var json = [];

            function getObjects(obj, key, val) {
                var objects = [];
                for (var i in obj) {
                    if (!obj.hasOwnProperty(i))
                        continue;
                    if (typeof obj[i] == "object") {
                        objects = objects.concat(getObjects(obj[i], key, val));
                    } else if (i == key && obj[key] == val) {
                        objects.push(obj);
                    }
                }
                return objects;
            }


            function gup(name, url) {
                if (!url)
                    url = location.href;
                name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
                var regexS = "[\\?&]" + name + "=([^&#]*)";
                var regex = new RegExp(regexS);
                var results = regex.exec(url);
                return results == null ? null : results[1];
            }
            function _init() {

                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open("GET", "searchprocess?searchKey=" + gup('q'), true);
                xmlhttp.send();
                xmlhttp.onreadystatechange = function () {
                    json = JSON.parse(xmlhttp.responseText);
                    init();
                };

            }
            function init() {
                //init data




                var ht = new $jit.Hypertree({
                    injectInto: 'infovis',
                    width: 600,
                    height: 600,
                    Node: {
                        overridable: true,
                        dim: 9,
                        color: "#f00"
                    },
                    Edge: {
                        lineWidth: 2,
                        color: "#088"
                    },
                    Events: {
                        enable: true,
                        type: 'Native',
                        //Change cursor style when hovering a node
                        onRightClick: function (node, eventInfo, e) {
                            var aaa = node.id.toString();
                            
                          //alert(aaa+"\n"+node.id);
                            if (aaa.indexOf("SSS") >= 0) {
                                
                            }
                            else{
                                window.location.href = 'index.jsp?q=' + node.name;
                            }
                        },
                        onClick: function (node, eventInfo, e) {
                            ht.onClick(node.id, function () {
                                // window.location = "aaa.html?q="+node.name;
                            });
                        }
                    },
                    onCreateLabel: function (domElement, node) {
                        domElement.innerHTML = node.name;


                    },
                    onPlaceLabel: function (domElement, node) {
                        var style = domElement.style;
                        style.display = '';
                        style.cursor = 'pointer';
                        if (node._depth <= 1) {
                            style.fontSize = "0.8em";
                            style.color = "#ddd";

                        } else if (node._depth == 2) {
                            style.fontSize = "0.7em";
                            style.color = "#555";

                        } else {
                            style.display = 'none';
                        }

                        var left = parseInt(style.left);
                        var w = domElement.offsetWidth;
                        style.left = (left - w / 2) + 'px';
                    }
                });
                ht.loadJSON(json);
                ht.refresh();
            }


        </script>
    <center>
        <h1>Dictionary</h1>
        <hr>
        <form name="searchForm" action="searchprocess" method="get">
            <input type="text" name="searchKey" value=<% if (request.getParameter("searchKey") == null) {
                    out.println("");
                } else {
                    out.println(request.getParameter("searchKey"));
                }%>>
            <input type="submit" name="search" value="Search">
        </form>
    </center>
    <%

        HashMap<String, ArrayList<String>> hypernym = (HashMap<String, ArrayList<String>>) request.getAttribute("hypernym");
        HashMap<String, ArrayList<String>> hyponym = (HashMap<String, ArrayList<String>>) request.getAttribute("hyponym");
        ArrayList<String> result;
        if (hypernym == null || hyponym == null) {
            out.println("No result found");
        } else {
            for (String key : hypernym.keySet()) {
                out.println("<b>Meaning:</b>" + key + "<br>");
                for (int i = 0; i < 10; i++) {
                    out.println("&nbsp");
                }

                out.println("<b>Hypernyms:-</b>");
                result = hypernym.get(key);

                for (int i = 0; i < result.size(); i++) {
                    out.println("<a href='searchprocess?searchKey=" + result.get(i) + "'>" + result.get(i) + "</a>");
                }
                out.println("<br>");
                for (int i = 0; i < 10; i++) {
                    out.println("&nbsp");
                }

                out.println("<b>Hyponyms:-</b>");
                result = hyponym.get(key);

                for (int i = 0; i < result.size(); i++) {
                    out.println("<a href='searchprocess?searchKey=" + result.get(i) + "'>" + result.get(i) + "</a>");
                }

                out.println("<br><br>");
            }
        }

    %>
</body>
</html>
