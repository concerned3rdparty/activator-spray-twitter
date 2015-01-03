"use strict";

define(['require',
        'angular',
        'analysis-info/analysis-info'], function(require) {
  var angular = require('angular');
  
  var app = angular
  .module('twitterTrackApp', [
    'angular-websocket',
    'analysisInfo'
  ])
  .config(function(WebSocketProvider){
    WebSocketProvider
      .prefix('')
      .uri('ws://localhost:9000/track/2015');
  });
  
});

