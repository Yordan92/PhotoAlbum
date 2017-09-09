app.controller('UserCtrl', ['UserFactory','$scope', '$location', 'CurrentUser', function(UserFactory, $scope, $location, CurrentUser) {

	$scope.user = new UserFactory();
	$scope.currentUser = CurrentUser;
	
	$scope.login = function() {
		$scope.user.$login().then(function(data) {
			angular.copy(data, CurrentUser);
			$location.path('/index.html');
		}, function(error) {
			
		});
	}
	
	$scope.signUp = function() {
		if ($scope.user.password !== $scope.user.confirmPassword) {
			$scope.signUpForm.$error.passwordsDoNotMach=true;
			$scope.signUpForm.$invalid = true;
		} else {
			$scope.signUpForm.$error.passwordsDoNotMach=false;
			$scope.signUpForm.$invalid = false;
			$scope.user.$signUp().then(function(data) {
				$location.path('/index.html');
			},function(error) {
				console.log(error);
			});
		}
	}
	

	$scope.editUser = function() {
		angular.merge($scope.user, $scope.user, CurrentUser);
		if ($scope.user.password !== $scope.user.confirmPassword) {
			$scope.editForm.$error.passwordsDoNotMach=true;
			$scope.editForm.$invalid = true;
		} else {
			$scope.editForm.$error.passwordsDoNotMach=false;
			$scope.editForm.$invalid = false;
			$scope.user.$editUser().then(function(data) {
				$location.path('/index.html');
			},function(error) {
				console.log(error);
			});
		}
	}

}]);


app.value('CurrentUser', {});