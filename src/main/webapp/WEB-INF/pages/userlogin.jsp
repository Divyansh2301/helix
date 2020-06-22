<html>
<body>
    USER is trying to login at
    <div id="time">
    </div>

    <br>
    <br>
    <div>
        <marquee>Made by Divyansh Jain</marquee>
    </div>

    <script type="text/javascript">
    //it will update text of div in every 1000 seconds
        function updateTime(){
            document.getElementById("time").innerText=new Date().toString();
        }
        setInterval(updateTime,1000);
    </script>
</body>
</html>