'use strict';

// Define the Angular module for our application
angular.module('bookInventoryApp', ['ngRoute'])
    // Configure the routes
    .config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'frontend/components/book-list/book-list.template.html',
                controller: 'BookListController'
            })
            .when('/books/new', {
                templateUrl: 'frontend/components/book-form/book-form.template.html',
                controller: 'BookFormController'
            })
            .when('/books/edit/:id', {
                templateUrl: 'frontend/components/book-form/book-form.template.html',
                controller: 'BookFormController'
            })
            .otherwise({
                redirectTo: '/'
            });
            
        // Use the HTML5 History API
        $locationProvider.hashPrefix('!');
    }]);