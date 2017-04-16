var labelType, useGradients, nativeTextSupport, animate;
var json = [];
var sanket = new Array();
(function () {
    var ua = navigator.userAgent,
            iStuff = ua.match(/iPhone/i) || ua.match(/iPad/i),
            typeOfCanvas = typeof HTMLCanvasElement,
            nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'),
            textSupport = nativeCanvasSupport
            && (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
    //I'm setting this based on the fact that ExCanvas provides text support for IE
    //and that as of today iPhone/iPad current text support is lame
    labelType = (!nativeCanvasSupport || (textSupport && !iStuff)) ? 'Native' : 'HTML';
    nativeTextSupport = labelType == 'Native';
    useGradients = nativeCanvasSupport;
    animate = !(iStuff || !nativeCanvasSupport);
})();

function gup(name, url) {
    if (!url)
        url = location.href;
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(url);
    return results == null ? null : results[1];
}

function search() {
    var searchkey = document.getElementById('query').value;

    window.location.href = encodeURI('temp.jsp?q=' + searchkey);

}
function hello(a) {
    //alert(JSON.stringify(sanket[a]));
    json = sanket[a];
    showGraph(1);
}
function init() {
    flag = 1;
    $("#loader").css("visibility", "visible");
    $("#main").css("visibility", "hidden");
    document.getElementById('query').value = gup('q');
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", encodeURI("searchprocess?searchKey=" + gup('q')), true);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function () {
        json = JSON.parse(xmlhttp.responseText)[0];
        showGraph(1);


        $("#defpanel").html("<ul>");

        json.children.forEach(function (entry, index) {
            //alert(JSON.stringify(entry));
            sanket[index] = entry;
            $("#defpanel").html($("#defpanel").html() + "<li class='bold'> <a href='#' class='waves-effect waves-teal' onclick='hello(" + index + ");'>" + entry.name + "</a></li>");

        });

        json.children.forEach(function (entry, index) {
            $("#sidepanel").html($("#sidepanel").html() + ' <span class="bold"><h5>\n\
 ' + entry.name + '</h5></span><br>\n\
\n\
');

            entry.children.forEach(function (entry, index) {
                if (entry.id.indexOf("hypr") >= 0) {
                    $("#sidepanel").html($("#sidepanel").html() + "<span><a href='temp.jsp?q=" + entry.name + "'>" + entry.name + "(Hypr)</a></span><br>");
                }
            });
            entry.children.forEach(function (entry, index) {
                if (entry.id.indexOf("hypo") >= 0) {
                    $("#sidepanel").html($("#sidepanel").html() + "<span><a href='temp.jsp?q=" + entry.name + "'>" + entry.name + "(Hypo)</a></span><br>");
                }
            });
            $("#sidepanel").html($("#sidepanel").html() + "<br>");
        });

        $("#loader").css("visibility", "hidden");
        $("#main").css("visibility", "visible");
    };


}

function showGraph(graph)
{

    document.getElementById('infovis').innerHTML = "";


    var rgraph = new $jit.RGraph({
        //Where to append the visualization  
        injectInto: 'infovis',
        //Optional: create a background canvas that plots  
        //concentric circles.  
        background: {
            CanvasStyles: {
                strokeStyle: '#555'
            }
        },
        Tips: {
            enable: true,
            onShow: function (tip, node) {
                var nodeid = node.id.toString();
                if (nodeid.indexOf("hypr") >= 0) {
                    tip.innerHTML = "Word:- " + node.name;
                    tip.innerHTML += "<br>Relation:- Hypernym";
                } else if (nodeid.indexOf("hypo") >= 0) {
                    tip.innerHTML = "Word:- " + node.name;
                    tip.innerHTML += "<br>Relation:- Hyponym";
                } else {
                    tip.innerHTML = "Definition:- " + node.name;

                }
            }
        },
        // Add node events
        Events: {
            enable: true,
            type: 'Native',
            //Change cursor style when hovering a node


            //Implement the same handler for touchscreens
            onTouchMove: function (node, eventInfo, e) {
                $jit.util.event.stop(e); //stop default touchmove event
                this.onDragMove(node, eventInfo, e);
            },
            //Add also a click handler to nodes
            // end onClick
        },
        //Add navigation capabilities:  
        //zooming by scrolling and panning.  
        Navigation: {
            enable: true,
            panning: true,
            zooming: 10
        },
        //Set Node and Edge styles.  
        Node: {
            color: '#666'
        },
        Edge: {
            color: '#C17878',
            lineWidth: 1.5
        },
        onBeforeCompute: function (node) {

            //Add the relation list in the right column.  
            //This list is taken from the data property of each JSON node.  

        },
        //Add the name of the node in the correponding label  
        //and a click handler to move the graph.  
        //This method is called once, on label creation.  
        onCreateLabel: function (domElement, node) {
            domElement.innerHTML = node.name;

            domElement.onmousedown = function (e) {
                if (e.which == 3) {
                    if (node.id.indexOf("true") != -1) {
                        window.location.href = encodeURI('temp.jsp?q=' + node.name);
                    }
                }
            };


            domElement.onclick = function (e) {

                if (e.which == 3) {
                    if (node.id.indexOf("true") != -1) {
                        window.location.href = encodeURI('temp.jsp?q=' + node.name);
                    }
                }
            };
        },
        //Change some label dom properties.  
        //This method is called each time a label is plotted.  
        onPlaceLabel: function (domElement, node) {
            var style = domElement.style;
            style.display = '';
            style.cursor = 'pointer';

            if (node.id.indexOf("hypr") != -1) {
                style.fontSize = "0.8em";
                style.color = "#FF0000";
            }
            else if (node.id.indexOf("hypo") != -1) {
                style.fontSize = "0.8em";
                style.color = "#0000FF";
            }
            else {
                style.fontSize = "0.8em";
                style.color = "#000000";
            }


            var left = parseInt(style.left);
            var w = domElement.offsetWidth;
            style.left = (left - w / 2) + 'px';
        }
    });
//load JSON data  
    rgraph.loadJSON(json);
//trigger small animation  
    rgraph.graph.eachNode(function (n) {
        var pos = n.getPos();
        pos.setc(-200, -200);
    });
    rgraph.compute('end');
    rgraph.fx.animate({
        modes: ['polar'],
        duration: 2000
    });
    document.getElementById('infovis').focus();
}