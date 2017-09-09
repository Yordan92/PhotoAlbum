'use strict';

app.controller('ModalCtrl', ['UserFactory','$scope', '$modalInstance', function(UserFactory,$scope, $modalInstance) {
	 $scope.cancel = function () {
		 $modalInstance.close();
		 $modalInstance.dismiss();
	  };
	 $scope.newCategory = new UserFactory();
	  
	 $scope.ok = function() {
		 $modalInstance.close($scope.newCategory.name);
	 }
}]);