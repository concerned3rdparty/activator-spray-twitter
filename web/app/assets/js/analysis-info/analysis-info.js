"use strict";

define(['require',
        'angular',
        'underscore',
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
      '$scope', 'WebSocket',
      function ($scope, WebSocket) {  
          $scope.tracks = [];
          $scope.keywords = [];
          WebSocket.onmessage(function(event) {
            var track = Parser.parserSentiment(event.data);
            var keyword = track.keyword;
            if (_.contains($scope.keywords, keyword)){
              $scope.tracks.forEach(function(trackData, index){
                if (trackData.keyword === keyword) {
                  trackData.analysis = track.analysis;
                }
              })

            }
          });
      }
    ]
  )
  .directive('analysisInfo', [function () {
    return {
      scope: {
        'tracks': '=',
        'keywords': '='
      },
      controller: function($scope, WebSocket){
        $scope.untrack = function (keyword) {
          $scope.tracks = _.without(
            $scope.tracks, 
            _.findWhere(
              $scope.tracks, 
              {'keyword': keyword}
            )
          );
          $scope.keywords = _.without($scope.keywords, keyword)
          WebSocket.send(JSON.stringify({untrack: keyword}));
          document.getElementById('heading-'+ keyword).parentNode.remove();
        };

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

