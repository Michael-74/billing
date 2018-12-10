import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

var stompClient = null;
const clients = [];
const delClientId = [];

export function connect() {
    const socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = () => {};
    stompClient.connect({}, frame => {
        //console.log('Connected: ', frame);
        stompClient.subscribe('/client/changeClient', function (message) {
            //console.log('subscribe-parse: ', JSON.parse(message.body));
            clients.forEach(handler => handler(JSON.parse(message.body)));
        });
        stompClient.subscribe('/client/deleteClient', function (message) {
            //console.log('delete-parse: ', JSON.parse(message.body));
            delClientId.forEach(handler => handler(JSON.parse(message.body)));
        });
    });
}

export function addClient(client) {
    clients.push(client);
}

export function deleteClient(clientId) {
    delClientId.push(clientId);
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    //console.log("Disconnected");
}

export function sendClient(client) {
    stompClient.send("/app/changeClient", {}, JSON.stringify(client));
}

export function deleteSendClient(id) {
    stompClient.send("/app/deleteClient", {}, JSON.stringify(parseInt(id)));
}
