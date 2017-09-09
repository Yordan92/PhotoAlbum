app.factory('UserFactory', ['$resource', function ($resource) {
  return $resource('/user/categories/:categoryId', {}, {
      login: { 
        method: 'POST', url: "/user/login" 
      },
      signUp: { 
          method: 'POST', url: "/user/signup" 
      },
      current: { 
          method: 'GET', url: "/user/current" 
      },
      editUser: {
    	 method: 'PUT', url: "/user/edit"   
      },
      getSubCategories: {
    	  method: 'GET', params: {categoryId:-1}
      },
      addCategory: {
    	  method:'GET', url:"/user/addCategory/:parentId/:name", params:{}
      },
      addImage: {
    	  method: 'POST', url:"users/addImage"
      }
    });
}]);

app.factory('httpPostFactory', function ($http) {
    return function (file, data, callback) {
        $http({
            url: file,
            method: "POST",
            data: data,
            headers: {
            	 "Content-Type": undefined
            }
        }).then(function (response) {
            callback(response);
        });
    };
});




