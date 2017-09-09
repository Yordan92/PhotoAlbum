'use strict';

app.controller('AlbumCtrl', ['UserFactory','$scope', '$modal','httpPostFactory', function(UserFactory, $scope, $modal,httpPostFactory) {
	$scope.categories = new UserFactory();
	$scope.categories.$getSubCategories();

	$scope.goToCategory = function(category) {
		$scope.categories.$getSubCategories({categoryId: category.id})
	}
	
	$scope.categoryModal = function(cat) {
		var newCategory = new UserFactory();
		newCategory.$addCategory({parentId: $scope.categories.id, name: cat.name});
		
	}
	
	$scope.addImage = function() {
		 var f = document.getElementById('file').files[0],
	        r = new FileReader();
		 var formData = new FormData();
		 var filename = new Date().getTime() + ''+ Math.floor(Math.random()*1000);
		 formData.append('file', f);
		 httpPostFactory('addBinary', formData, function (callback) {
             // recieve image name to use in a ng-src
			 var image = new UserFactory();
			 image.description = $scope.image.description;
			 image.name = $scope.image.name;
			 image.path = callback.data.name;
			 image.date = new Date();
			 image.category = $scope.categories
             image.$addImage();
          });  
	    }
	}]);