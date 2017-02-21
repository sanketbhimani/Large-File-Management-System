
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script type="text/javascript" src="jit.js"></script>
        <script src="jquery.min.js"></script>
    </head>
    <style>
        #sidepanel{
            width: 25%;
            display: block;
            float:right;
        }
        #loader {
            position: absolute;
            top:50%;
            left:50%;
            -webkit-animation: spin 2s linear infinite;
            animation: spin 2s linear infinite;
            visibility: hidden;
        }

        @-webkit-keyframes spin {
            0% { -webkit-transform: rotate(0deg); }
            100% { -webkit-transform: rotate(360deg); }
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        #log {
            width: 1000px;
        }

        #infovis {
            width: 75%;
            float:left;
            display: block;
            height: 78%;
            overflow: hidden;
            background-color: white;
        }

        .jitFgHelp {
            display: none;
        }

        /*TOOLTIPS*/
        .tip {
            color: #111;
            width: 300px;
            background-color: white;
            border:1px solid #ccc;
            -moz-box-shadow:#555 2px 2px 8px;
            -webkit-box-shadow:#555 2px 2px 8px;
            -o-box-shadow:#555 2px 2px 8px;
            box-shadow:#555 2px 2px 8px;
            opacity:0.9;
            filter:alpha(opacity=90);
            font-size:10px;
            font-family:Verdana, Geneva, Arial, Helvetica, sans-serif;
            padding:7px;
        }

    </style>
    <body onload="init()">
        <script src="example1.js"></script>
    <center>
        <h1>Dictionary</h1>
        <input type="text" id="query" name="query" placeholder="Word"/>
        <input type="button" name="searchButton" value="Search" onClick="search();"/>
        <hr>
    </center>
    <div id="main">
    <div id="log"></div>

    <span id="infovis"></span>
    <span id="sidepanel"></span>
</div>
</body>
</html>