import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

var stompClient = null;
const clients = []

export function connect() {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {
        console.log('Connected: ', frame);
        stompClient.subscribe('/topic/greetings', function (message) {
            console.log('greetings: ', message);
            clients.forEach(handler => handler(JSON.parse((message.body))))
        });
    });
}

export function addClient(client) {
    clients.push(client);
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

export function sendName(message) {
    stompClient.send("/app/hello", {}, JSON.stringify(message));
}
