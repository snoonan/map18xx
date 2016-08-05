// Connect to PeerJS, have server assign an ID instead of providing one
// Showing off some of the configs available with PeerJS :).
var peer;
var connectedPeers = {};

function login(requestedId) {
  peer = new Peer(requestedId,{
  // Set API key for cloud server (you don't need this if you're running your
  // own.
  key: 'lwjd5qra8257b9',
  //key: 'peerjs', host: 'localhost', port:9000,
  // Use a TURN server for more network support
  config: {'iceServers': [
    { url: 'stun:stun.l.google.com:19302' }
  ], 'debug':3} /* Sample servers, please use appropriate ones */
});

// Show this peer's ID.
peer.on('open', function(id){
  var other = window.location.hash;

  $('#pid').text(id);
  window.location.hash = '#'+id;
  connectedPeers[id] = 1;     // Connected to myself.

  if (other != '') {
     connectToPeer(other.slice(1));
  }
});

// Await connections from others
peer.on('connection', connect);
}

// Handle a connection object.
function connect(c) {
  // Handle a chat connection.
  console.log("connect call for "+c.peer+" "+c.label);
  if (connectedPeers[c.peer] > 2) return;
  connectedPeers[c.peer] ++;
  if (c.label === 'chat') {
    var chatbox = $('<div></div>').addClass('connection').addClass('active').attr('id', c.peer);
    var header = $('<h1></h1>').html('Chat with <strong>' + c.peer + '</strong>');
    var messages = $('<div><em>Peer connected.</em></div>').addClass('messages');
    chatbox.append(header);
    chatbox.append(messages);

    // Select connection handler.
    chatbox.on('click', function() {
      if ($(this).attr('class').indexOf('active') === -1) {
        $(this).addClass('active');
      } else {
        $(this).removeClass('active');
      }
    });
    $('.filler').hide();
    $('#connections').append(chatbox);

    c.on('data', function(data) {
      messages.append('<div><span class="peer">' + c.peer + '</span>: ' + data +
        '</div>');
        });
        c.on('close', function() {
          //alert(c.peer + ' has left the chat.');
          chatbox.remove();
          if ($('.connection').length === 0) {
            $('.filler').show();
          }
          delete connectedPeers[c.peer];
        });
  } else if (c.label === 'game') {
    c.on('data', function(data) {
         console.log(this.peer+"@rx: "+data);
         var cmd = data.split(':');
         if (cmd[0] == "peers") {
            for (p in cmd) {
               if (cmd[p] == "peers") {
                  continue;
               }
               connectToPeer(cmd[p]);
            }
         }
         game_cmd(c,data);
    });
    c.on('open', function () {
       var peers = [];
       console.log("peer opened: "+this.peer);
       connectedPeers[this.peer] = 3;
       for (p in connectedPeers) {
           if (connectedPeers.hasOwnProperty(p)) {
               peers.push(p);
           }
       }
       game_cmd_send("peers:"+peers.join(':'));
    });
  }
}

function connectToPeer(requestedPeer) {
    console.log("connect "+requestedPeer);
    if (!connectedPeers[requestedPeer]) {
    console.log("connecting "+requestedPeer);
       connectedPeers[requestedPeer] = 1;
      // Create 2 connections, one labeled chat and another labeled game.
      var c = peer.connect(requestedPeer, {
        label: 'chat',
        serialization: 'none',
        reliable: false,
        metadata: {message: 'Would you like to play a game?'}
      });
      c.on('open', function() {
  console.log("chat -> connect call for "+this.peer);
        connect(c);
      });
      c.on('error', function(err) { alert(err); });
      var f = peer.connect(requestedPeer, { label: 'game' });
      f.on('open', function() {
  console.log("game -> connect call for "+this.peer);
        connect(f);
      });
      f.on('error', function(err) { alert(err); });
    }
    console.log("opened "+requestedPeer);
}

$(document).ready(function() {

   if (util.supports.data) {
      $('#webrtc').show();
   }
  // claim name
  $('#login').click(function() {
    requestedId = $('#pid').val();
    login(requestedId);
  });
  // Connect to a peer
  $('#connect').click(function() {
    requestedPeer = $('#rid').val();
    connectToPeer(requestedPeer);
  });

  // Close a connection.
  $('#close').click(function() {
    eachActiveConnection(function(c) {
      c.close();
    });
  });

  // Send a chat message to all active connections.
  $('#send').submit(function(e) {
    e.preventDefault();
    // For each active connection, send the message.
    var msg = $('#text').val();
    eachActiveConnection(function(c, $c) {
      if (c.label === 'chat') {
        c.send(msg);
        $c.find('.messages').append('<div><span class="you">You: </span>' + msg
          + '</div>');
      }
    });
    $('#text').val('');
    $('#text').focus();
  });

});

function game_cmd_send(data)
{
   console.log('cmd: '+data);
    eachValidConnection(function(c, _) {
        console.log("con:" + c.peer);
      if (c.label === 'game') {
        console.log("send "+data+" to " + c.peer);
        c.send(data);
      }
    });
}

  // Goes through each valid peer and calls FN on its connections.
  function eachValidConnection(fn) {
    var actives = $('.connection');
    var checkedIds = {};
    actives.each(function() {
      var peerId = $(this).attr('id');
console.log(peerId+": "+connectedPeers[peerId]);
      if (connectedPeers[peerId] < 3) return;
      if (!checkedIds[peerId]) {
          console.log('checking '+peerId);
        var conns = peer.connections[peerId];
        for (var i = 0, ii = conns.length; i < ii; i += 1) {
          var conn = conns[i];
          fn(conn, $(this));
        }
      }

          console.log('done '+peerId);
      checkedIds[peerId] = 1;
    });
  }

  // Goes through each active peer and calls FN on its connections.
  function eachActiveConnection(fn) {
    var actives = $('.active');
    var checkedIds = {};
    actives.each(function() {
      var peerId = $(this).attr('id');

      if (!checkedIds[peerId]) {
          console.log('checking '+peerId);
        var conns = peer.connections[peerId];
        for (var i = 0, ii = conns.length; i < ii; i += 1) {
          var conn = conns[i];
          fn(conn, $(this));
        }
      }

          console.log('done '+peerId);
      checkedIds[peerId] = 1;
    });
  }

// Make sure things clean up properly.

window.onunload = window.onbeforeunload = function(e) {
  if (!!peer && !peer.destroyed) {
    peer.destroy();
  }
};
