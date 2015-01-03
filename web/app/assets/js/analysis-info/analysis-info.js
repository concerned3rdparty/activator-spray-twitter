"use strict";

define(['require',
        'angular',
        'angularjs-nvd3-directives',
        'angular-webSocket',
        'underscore',
        'lib/parser'], function(require) {
  var angular = require('angular');
  var Parser = require('lib/parser');

  return angular
  .module('analysisInfo', [
          'angular-websocket',
          'nvd3ChartDirectives'
  ])
  .controller('analysisInfoController', [
      '$scope', '$timeout', 'WebSocket',
      function ($scope, $timeout, WebSocket) {
        var analysisInfo = this;
        analysisInfo.renderCharts = function () {
          $scope.xFunction = function(){
            return function(d) {
              return d.key;
            };
          }
          $scope.yFunction = function(){
            return function(d) {
              return d.y;
            };
          }
          var colorArray = ['#ffbb78', '#ff7f0e', '#98df8a', '#2ca02c'];
          $scope.colorFunction = function() {
            return function(d, i) {
                return colorArray[i];
              };
          }
          
          $scope.sentiment = [
            { key: "Negative", y: $scope.analysis.sentiment.negative },
            { key: "Negative Gurus", y: $scope.analysis.sentiment.negative_gurus },
            { key: "Positive", y: $scope.analysis.sentiment.positive },
            { key: "Positive Gurus", y: $scope.analysis.sentiment.positive_gurus }

          ];
        };
        
        WebSocket.onmessage(function(event) {
          $scope.analysis = Parser.parserSentiment(event.data);
          analysisInfo.renderCharts();
        });
      }
    ]
  )
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

