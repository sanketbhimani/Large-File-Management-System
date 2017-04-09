
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script type="text/javascript" src="jit.js"></script>
        <script src="jquery.min.js"></script>
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="materialize.min.css"  media="screen,projection"/>

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <script type="text/javascript" src="materialize.min.js"></script>
    </head>
    <style>
        body{
            //color: white !important;
            // background-color: black !important;
        }
        #sidepanel{
            // width: 15%;
            //display: block;
            //float:right;
            //visibility: hidden;
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
            width: 85%;
            float:right;
            display: block;
            height: 100%;
            overflow: hidden;
            background-color: whitesmoke;
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
        nav .nav-wrapper form, nav .nav-wrapper form .input-field{
            height: 100%;
        }

    </style>
    <body onload="init()" >
        <script src="example1.js"></script>
        <nav style="position:fixed; z-index: 1000; top:0;">
            <div class="nav-wrapper">
                <!--<a href="#" class="brand-logo left" style="margin-left:30px;">Dictionary</a>-->
                <div class="row">
                    <div class="input-field offset-s4 col s3"> 
                        <input id="query" name="query" type="search" required>
                        <label for="query"><i class="material-icons">search</i></label>
                    </div>

                    <div class="input-field col s3">
                        <button style="margin-top:12px;" class="waves-effect waves-light btn" name="searchButton" onClick="search();"/> Search</button>
                    </div>
                    <!-- <input id="query" name="query" class="validate">
                     <label for="query">Word</label>
                  
                       <div class="input-field col s3">
                           <button  class="waves-effect waves-light btn" name="searchButton" onClick="search();"/> Search</button>
                       </div>-->
                </div>
            </div>
        </nav>






        <div id="main">
            <div id="log"></div>

            <span id="infovis"></span>

            <ul id="nav-mobile" style="padding-top:90px;" class="side-nav fixed" style="transform: translateX(0px);">
                <span id="defpanel"></span>
                <li><a class="waves-effect waves-light btn" id="view_all" href="#">view all words</a></li>
            </ul>

        </div>
        
       
  <!-- Modal Structure -->
  <div id="logs" class="modal modal-fixed-footer">
    <div class="modal-content">
      <h4>All Words</h4>
      <p> <div class="center" id="sidepanel"></div></p>
    </div>
    <div class="modal-footer">
      <a href="#" id="close_modal" class="modal-action modal-close waves-effect waves-green btn-flat ">close</a>
    </div>
  </div>
  <script>
         $(document).ready(function(){
    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    $('#logs').modal();
  });
  $("#view_all").click(function (){
     $('#logs').modal('open'); 
  });
  $("#close_modal").click(function (){
     $('#logs').modal('close'); 
  });
  </script>
    <!--    <div style="position:fixed; height:80%; width:80%; z-index: 1200;">     
            <ul style="transform: translateX(0px);">
                <div style="margin-left: 325px; " class="center" id="sidepanel">

                </div>
            </ul>
        </div>
-->
<div id="loader">
     <div class="preloader-wrapper big active">
    <div class="spinner-layer spinner-blue-only">
      <div class="circle-clipper left">
        <div class="circle"></div>
      </div><div class="gap-patch">
        <div class="circle"></div>
      </div><div class="circle-clipper right">
        <div class="circle"></div>
      </div>
    </div>
  </div>

</div>

    </body>
</html>