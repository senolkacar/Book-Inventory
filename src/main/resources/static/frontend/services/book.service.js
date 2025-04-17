'use strict';

angular.module('bookInventoryApp')
    .factory('BookService', ['$http', function($http) {
        const API_URL = '/api/v1/books';
        
        return {
            // Get all books
            getAllBooks: function() {
                return $http.get(API_URL);
            },
            
            // Get a book by ID
            getBookById: function(id) {
                return $http.get(`${API_URL}/${id}`);
            },
            
            // Create a new book
            createBook: function(book) {
                return $http.post(API_URL, book);
            },
            
            // Update an existing book
            updateBook: function(id, book) {
                return $http.put(`${API_URL}/${id}`, book);
            },
            
            // Delete a book
            deleteBook: function(id) {
                return $http.delete(`${API_URL}/${id}`);
            }
        };
    }]);