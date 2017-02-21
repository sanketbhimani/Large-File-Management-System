var labelType, useGradients, nativeTextSupport, animate;
var json = [];
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

function init() {
    flag = 1;
    $("#loader").css("visibility", "visible");
    $("#main").css("visibility", "hidden");
    document.getElementById('query').value = gup('q');
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", encodeURI("searchprocess?searchKey=" + gup('q')), true);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function () {
        json = JSON.parse(xmlhttp.responseText);
        showGraph(1);



        $("#sidepanel").html("<ul>")
        defs = $.grep(json, function (item) {

            a = item.id.toString();
            return a.indexOf("hypo") == -1 && a.indexOf("hypr") == -1;
        });
        //alert(defs.length);
        defs.forEach(function (entry, index) {
            //alert(entry.name);
            nodes = entry.adjacencies;
            $("#sidepanel").html($("#sidepanel").html() + "<li>" + entry.name + "</li>\n\
<li><b>Hypo</b></li><ul>");

            nodes.forEach(function (e, i) {
                id = e.nodeTo;
                //alert(id);
                names = $.grep(json, function (item) {
                    idd = item.id.toString();
                    //alert(idd);
                    //alert("###" + id);
                    //alert(idd === id && idd.indexOf("hypo") != -1);
                    return idd === id && idd.indexOf("hypo") != -1;
                });
                names.forEach(function (e, i) {
                    name = e.name;
                    if (name != undefined) {
                        $("#sidepanel").html($("#sidepanel").html() + "<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='temp.jsp?q=" + name + "'>"+name+"</a></li>");
                    }
                });


            });


            $("#sidepanel").html($("#sidepanel").html() + "</ul>");
            $("#sidepanel").html($("#sidepanel").html() + "<li><b>Hypr</b></li><ul>");
            nodes.forEach(function (e, i) {
               id = e.nodeTo;
                //alert(id);
                names = $.grep(json, function (item) {
                    idd = item.id.toString();
                    //alert(idd);
                    //alert("###" + id);
                    //alert(idd === id && idd.indexOf("hypo") != -1);
                    return idd === id && idd.indexOf("hypr") != -1;
                });
                names.forEach(function (e, i) {
                    name = e.name;
                    if (name != undefined) {
                        $("#sidepanel").html($("#sidepanel").html() + "<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='temp.jsp?q=" + name + "'>"+name+"</a></li>");
                    }
                });

            });
            $("#sidepanel").html($("#sidepanel").html() + "</ul>");

        });

        $("#sidepanel").html($("#sidepanel").html() + "</ul>");



        //   createTable();
        $("#loader").css("visibility", "hidden");
        $("#main").css("visibility", "visible");
    };


}

function showGraph(graph)
{





//$('#infovis').empty();
    document.getElementById('infovis').innerHTML = "";

    var Log = {
        elem: false,
        write: function (text) {
            if (!this.elem)
                this.elem = document.getElementById('log');
            this.elem.innerHTML = text;
            this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
        }
    };

    fd = new $jit.ForceDirected({
        //id of the visualization container
        injectInto: 'infovis',
        //Enable zooming and panning
        //by scrolling and DnD
        Navigation: {
            enable: true,
            //Enable panning events only if we're dragging the empty
            //canvas (and not a node).
            panning: 'avoid nodes',
            zooming: 10 //zoom speed. higher is more sensible
        },
        // Change node and edge styles such as
        // color and width.
        // These properties are also set per node
        // with dollar prefixed data-properties in the
        // JSON structure.
        Node: {
            overridable: true
        },
        Edge: {
            overridable: false,
            color: '#000000',
            lineWidth: 0.4
        },
        //Native canvas text styling
        Label: {
            type: labelType, //Native or HTML
            size: 13,
            style: 'bold',
            color: '#000000'
        },
        //Add Tips
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
            onMouseEnter: function () {
                fd.canvas.getElement().style.cursor = 'move';
            },
            onMouseLeave: function () {
                fd.canvas.getElement().style.cursor = '';
            },
            //Update node positions when dragged
            onDragMove: function (node, eventInfo, e) {
                var pos = eventInfo.getPos();
                node.pos.setc(pos.x, pos.y);
                fd.plot();
            },
            //Implement the same handler for touchscreens
            onTouchMove: function (node, eventInfo, e) {
                $jit.util.event.stop(e); //stop default touchmove event
                this.onDragMove(node, eventInfo, e);
            },
            //Add also a click handler to nodes
            onClick: function (node) {
                if (!node)
                    return;
                var nodeid = node.id.toString();
                if (nodeid.indexOf("hypr") >= 0) {
                    window.location.href = encodeURI('temp.jsp?q=' + node.name);
                } else if (nodeid.indexOf("hypo") >= 0) {
                    window.location.href = encodeURI('temp.jsp?q=' + node.name);
                } else {

                }
            } // end onClick
        },
        //Number of iterations for the FD algorithm
        iterations: 200,
        //Edge length
        levelDistance: 130,
        // Add text to the labels. This method is only triggered
        // on label creation and only for DOM labels (not native canvas ones).
        onCreateLabel: function (domElement, node) {
            domElement.innerHTML = node.name;
            var style = domElement.style;
            style.fontSize = '1.0em';
            var node = node.id.toString();
            if (nodeID.indexOf("hypr") > 0) {
                style.color = "#0000FF";
            } else if (nodeID.indexOf("hypo") > 0) {
                style.color = "#FF0000";

            } else {
                style.color = '#000';
            }

        },
        // Change node styles when DOM labels are placed
        // or moved.
        onPlaceLabel: function (domElement, node) {
            var style = domElement.style;
            var left = parseInt(style.left);
            var top = parseInt(style.top);
            var w = domElement.offsetWidth;
            style.left = (left - w / 2) + 'px';
            style.top = (top + 10) + 'px';
            style.display = '';
        }
    }); // end ForceDirected setup

    fd.loadJSON(json);

    if (graph == 1)
    {
        fd.computeIncremental({
            iter: 5,
            property: 'end',
            onStep: function (perc) {
                Log.write(perc + '% loaded...');
            },
            onComplete: function () {
                fd.animate({
                    modes: ['linear'],
                    transition: $jit.Trans.Elastic.easeOut,
                    duration: 0
                });
                Log.write('Graph complete');
            }
        });
    }
}