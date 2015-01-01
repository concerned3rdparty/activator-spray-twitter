"use strict";

define(['require',
        'angular',
        'angular-webSocket',
        'lib/parser'], function(require) {
  var angular = require('angular');
  var Parser = require('lib/parser');
  
  var app = angular
  .module('TwitterTrackApp', [
    'angular-websocket'
  ])
  .config(function(WebSocketProvider){
    WebSocketProvider
      .prefix('')
      .uri('ws://localhost:9000/track/2015');
  }).controller('TwitterTrackController', [
      '$scope', 'WebSocket',
      function ($scope, WebSocket) {
        WebSocket.onmessage(function(event) {
          $scope.analysis = Parser.parserSentiment(event.data);
        });
        
      }
    ]
  )
  
});

