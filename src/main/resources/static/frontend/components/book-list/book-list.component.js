'use strict';

angular.module('bookInventoryApp')
    .controller('BookListController', ['$scope', 'BookService', function($scope, BookService) {
        $scope.books = [];
        $scope.loading = true;
        $scope.error = null;
        
        // Load all books
        function loadBooks() {
            $scope.loading = true;
            BookService.getAllBooks()
                .then(function(response) {
                    $scope.books = response.data;
                    $scope.loading = false;
                })
                .catch(function(error) {
                    $scope.error = 'Error loading books: ' + (error.data?.message || error.statusText || 'Unknown error');
                    $scope.loading = false;
                    console.error('Error loading books:', error);
                });
        }
        
        // Delete a book
        $scope.deleteBook = function(id) {
            if (confirm('Are you sure you want to delete this book?')) {
                BookService.deleteBook(id)
                    .then(function() {
                        // Reload the books list after successful deletion
                        loadBooks();
                    })
                    .catch(function(error) {
                        $scope.error = 'Error deleting book: ' + (error.data?.message || error.statusText || 'Unknown error');
                        console.error('Error deleting book:', error);
                    });
            }
        };
        
        // Initialize controller
        loadBooks();
    }]);