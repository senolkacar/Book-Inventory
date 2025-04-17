'use strict';

angular.module('bookInventoryApp')
    .controller('BookFormController', ['$scope', '$routeParams', '$location', 'BookService', 
    function($scope, $routeParams, $location, BookService) {
        $scope.book = {
            title: '',
            author: '',
            isbn: '',
            nbPages: null
        };
        $scope.loading = false;
        $scope.error = null;
        $scope.isEditing = !!$routeParams.id;
        $scope.formTitle = $scope.isEditing ? 'Edit Book' : 'Add New Book';
        
        // If editing, load the book data
        if ($scope.isEditing) {
            $scope.loading = true;
            BookService.getBookById($routeParams.id)
                .then(function(response) {
                    $scope.book = response.data;
                    $scope.loading = false;
                })
                .catch(function(error) {
                    $scope.error = 'Error loading book: ' + (error.data?.message || error.statusText || 'Unknown error');
                    $scope.loading = false;
                    console.error('Error loading book:', error);
                });
        }
        
        // Save the book (create or update)
        $scope.saveBook = function() {
            $scope.loading = true;
            
            // Validate form
            if (!$scope.book.title || !$scope.book.author || !$scope.book.isbn) {
                $scope.error = 'Please fill in all required fields.';
                $scope.loading = false;
                return;
            }
            
            // Convert nbPages to number
            if ($scope.book.nbPages) {
                $scope.book.nbPages = parseInt($scope.book.nbPages, 10);
            }
            
            // Create or update based on whether we're editing
            const savePromise = $scope.isEditing 
                ? BookService.updateBook($routeParams.id, $scope.book)
                : BookService.createBook($scope.book);
                
            savePromise
                .then(function() {
                    // Redirect to book list on success
                    $location.path('/');
                })
                .catch(function(error) {
                    $scope.error = 'Error saving book: ' + (error.data?.message || error.statusText || 'Unknown error');
                    $scope.loading = false;
                    console.error('Error saving book:', error);
                });
        };
        
        // Cancel and go back to book list
        $scope.cancel = function() {
            $location.path('/');
        };
    }]);