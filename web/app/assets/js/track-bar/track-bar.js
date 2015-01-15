"use strict";

define(['require',
        'angular'], function(require) {
  var angular = require('angular');

  return angular
  .module('trackBar', [
          'angular-websocket'
  ])
  .directive('trackBar', [function () {
    return {
      replace: true,
      scope: {
        'keywords': '=',
        'tracks': '='
      },
      controller: function($scope, WebSocket){
        $scope.track = function (event) {
          var keyword  = document.getElementById('track-keyword-input').value;
          if (keyword.length > 0 && (! _.contains($scope.keywords, keyword)) ) {
            $scope.keywords.push(keyword)
            $scope.tracks.push(
              {
                'keyword': keyword,
                analysis: {}
              })
            WebSocket.send(JSON.stringify({track: keyword}));
          }
        }
      },
      restrict: 'E', 
      templateUrl: 'assets/js/track-bar/track-bar.html'
    };
  }
  ]);

});
