"use strict";

define(['require',
        'angular',
        'angular-webSocket',
        'underscore',
        'lib/parser'], function(require) {
  var angular = require('angular');
  var Parser = require('lib/parser');
  return angular
  .module('analysisInfo', [
          'angular-websocket'
  ])
  .controller('analysisInfoController', [
      '$scope','$interval', 'WebSocket',
      function ($scope,$interval, WebSocket) {   
        WebSocket.onmessage(function(event) {
          var analysis = Parser.parserSentiment(event.data)
          $scope.tracks = [
            {
              trackWord: '2015',
              analysis: analysis
            }
          ]
        });
      }
    ]
  )
  .directive('analysisInfo', [function () {
    return {
      scope: {
        'tracks': '='
      },
      restrict: 'E', 
      templateUrl: 'assets/js/analysis-info/analysis-info.html'
    };
  }])

  .directive('sentimentList', [function(){
    return {
      scope: {
        'sentiment': '='
      },
      restrict: 'E', 
      templateUrl: 'assets/js/analysis-info/sentiment-list.html'
    };
  }])
  .directive('languagesList', [function(){
    return {
      scope: {
        'languages': '='
      },
      restrict: 'E', 
      templateUrl: 'assets/js/analysis-info/languages-list.html'
    };
  }])
  .directive('placesList', [function(){
    return {
      scope: {
        'places': '='
      },
      restrict: 'E', 
      templateUrl: 'assets/js/analysis-info/places-list.html'
    };
  }]);
});

