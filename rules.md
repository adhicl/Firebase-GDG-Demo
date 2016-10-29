{
  "rules": {
    "menu": {
        "$user" : {
          ".read": "auth != null && auth.uid == $user",
          ".write": "auth != null && auth.uid == $user",
           
           "$item":{
            	".validate": "newData.hasChildren(['key', 'menu', 'description', 'rating', 'imageUrl'])",
            	"rating": { 
              		".validate": "newData.isNumber() && newData.val() >= 0 && newData.val() <= 5"
            	}
            }         
        }
    }
  }
}
