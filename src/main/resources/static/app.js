'use strict';

// Declare app level module which depends on views, and components
var app = angular.module('myApp', [
  'ngRoute',
  'ngResource',
  'ui.bootstrap'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');
  
}]).
config(['$routeProvider', function($routeProvider) {
	  $routeProvider.when('/album', {
	    templateUrl: 'album/album.html',
	    controller: 'AlbumCtrl'
	  }),
	  $routeProvider.when('/login', {
		    templateUrl: 'login/login.html',
		    controller: 'UserCtrl'
	  }),
	  $routeProvider.when('/signup', {
		    templateUrl: 'login/signup.html',
		    controller: 'UserCtrl'
	  }),
	  $routeProvider.when('/edit', {
		    templateUrl: 'login/edit.html',
		    controller: 'UserCtrl'
	  })
}]);




