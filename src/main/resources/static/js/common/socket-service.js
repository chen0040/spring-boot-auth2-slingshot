(function(){

        var stompClient = null;
        var socket = null;
        var connected = false;
        var thisUserId = -1;
        var thisCallback = null;
        var thisIntervalHandler = null;

        function stompConnect() {
            console.log('STOMP: Attempting connection');
            stompClient = null;
            connect(thisUserId, thisCallback);
        }

        var connectToHost = function() {
            var url = "/app_name";

            socket = new SockJS(url);
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                connected = true;

                if(thisCallback) thisCallback('connect', connected);

                stompClient.subscribe('/topics/events-' + thisUserId, function(message){
                    console.log('event message result!');
                    if(thisCallback) thisCallback('events', message.body);
                });

                stompClient.subscribe('/topics/ping', function(message) {
                    console.log('ping!');
                    if(thisCallback) thisCallback('ping', message.body);
                });


            }, function (error) {
               console.log('STOMP: ' + error);
               setTimeout(stompConnect, 5000);
               console.log('STOMP: Reconecting in 5 seconds');
            });
        };

        var connect = function(userId, callback) {
            thisUserId = userId;
            thisCallback = callback;
            var host = $location.host();
            console.log('detect host: ' + host);

            if(stompClient == null) {
                connectToHost();
            }
        };

        var disconnect = function() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
            if (thisIntervalHandler != null) {
                thisIntervalHandler.cancel();
                thisIntervalHandler = null;
            }
            connected = false;
            console.log("Disconnected");
        };

        var send = function(topic, obj) {
            if(stompClient != null){
                stompClient.send(topic, {}, JSON.stringify(obj));
            }
        };

        return {
            connect : connect,
            disconnect : disconnect,
            send : send
        };
})();
