"use strict";

define(['require',
        'angular',
        'bootstrap',
        'analysis-info/analysis-info',
        'track-bar/track-bar'], function(require) {
  var angular = require('angular');

  var app = angular
  .module('twitterTrackApp', [
    'angular-websocket',
    'analysisInfo',
    'trackBar'
  ])
  .config(function(WebSocketProvider){
    WebSocketProvider
      .prefix('')
      .uri('ws://localhost:9000/track/');
  });

});
