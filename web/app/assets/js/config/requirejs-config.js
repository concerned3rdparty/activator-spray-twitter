requirejs.config({
  baseUrl: '/assets/js',
  deps: ['main'],
  paths: {
    angular: '../lib/angularjs/angular',
    bootstrap: '../lib/bootstrap/js/bootstrap',
    jquery: '../lib/jquery/jquery',
    'angular-webSocket': '../vendor/angular-websocket/angular-websocket',
    underscore: '../lib/underscorejs/underscore-min',
    'angular-nvd3': '../lib/angular-nvd3/angular-nvd3.min',
    d3: '../lib/d3js/d3.min',
    nvd3: '../lib/nvd3/nv.d3.min'
  },
  shim: {
    jquery: {
      exports: 'jquery'
    },
    angular: {
      exports: 'angular'
    },
    bootstrap: {
      deps: ['jquery'],
      exports: 'bootstrap'
    },
    'angular-webSocket': {
      deps: ['angular']
    },
    d3: {
      exports: 'd3'
    },
    nvd3: {
      deps: ['d3'],
      exports: 'nvd3'
    },
    'angular-nvd3': {
      deps: ['angular', 'd3', 'nvd3']
    }
  }
});

