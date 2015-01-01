requirejs.config({
  baseUrl: '/assets/js',
  deps: ['main'],
  paths: {
    angular: "../lib/angularjs/angular",
    bootstrap: "../lib/bootstrap/js/bootstrap",
    jquery: "../lib/jquery/jquery",
    'angular-webSocket': "../vendor/angular-websocket/angular-websocket",
    underscore: "../lib/underscorejs/underscore-min"
  },
  shim: {
    jquery: {
      exports: "jquery"
    },
    angular: {
      exports: "angular"
    },
    bootstrap: {
      deps: ["jquery"],
      exports: "bootstrap"
    },
    'angular-webSocket': {
      deps: ["angular"]
    }
  }
});

