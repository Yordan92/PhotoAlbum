var tempResult = {};

function _getParameter(identifier , URL) {
    var result = null, tmp = [];

    var usefulParam = URL.split("?")[1] || "";
    var items = usefulParam.split("&");

    for (var index = 0; index < items.length; index++) {
        tmp = items[index].split("=");

        if (tmp[0] === identifier){
            result = decodeURIComponent(tmp[1]);
        }
    }

    return result;
}

function createCategoryList() {
}

function loadPictures() {
  
}

function details(id) {
    tempResult.children.forEach(function(element){
        if(element.id === id) {
            window.open('details.html?name='+element.name+'&description='+element.description+'&date='+element.date+'&path='+element.path); 
        }
    });
}

function openCategory(categoryID) {
   //    var result = {};
    /*$.ajax({
       url: some_url,
       type: 'POST',
       data: {id: categoryID},
        success: function (html) {
            result = html;
            createCategoryList(result);
            loadPictures(result);
        }
    });*/
     var result = {
            name: "root",
            isDir: true,
            parent: null,
            id: 'root',
            children: [
                {
                    name: "Yorda",
                    isDir: true,
                    parent: 'root',
                    id: 'yorda',
                    children: [], 
                },
                {
                    name:"picture1",
                    id: 'picture1',
                    isDir: false,
                    description: "Some description",
                    date: "08.05.2017",
                    path:"images/350.jpg",
                    category:{}
                }
            ]
    };
    tempResult = result;
    createCategoryList();
    loadPictures();
}