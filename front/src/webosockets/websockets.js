import Echo from "laravel-echo";

const setupWebSocket = (orderNo, callback) => {
  try {
    window.Pusher = require("pusher-js");
    window.Echo = new Echo({
      broadcaster: process.env.REACT_APP_WS_BROADCASTER,
      key: process.env.REACT_APP_WS_KEY,
      wsHost: process.env.REACT_APP_WS_HOST,
      wsPort: process.env.REACT_APP_WS_PORT,
      transports: ["websocket"],
      enabledTransports: ["ws"],
      forceTLS: false,
      disableStats: false,
      cluster: "mt1",
    });

    let channel = window.Echo.channel(`public.WebUIOrder.${orderNo}`);
    channel
      .subscribed(() => {
        console.log("Subscribed to event");
      })
      .listen(`.WebUIOrderReady.${orderNo}`, (event) => {
        callback(event); // Callback function to handle event
        channel.unsubscribe();
      });
  } catch (exception) {
    console.log(exception);
  }
};

export default setupWebSocket;
