export default class Uno {

    constructor() {
        this.host = "http://localhost:1202";
    }

    fbLogin(user, callback) {
        console.log(user, "Uno fbLogin");
        this.api(`facebook/users/login`, 'POST', user, callback);
    }

    fbSubscribeToPage(page, callback) {
        console.log(page, "Uno fbSubscribeToPage");
        this.api(`facebook/pages/`, 'POST', page, callback);
    }

    api(endpoint, method, data, callback) {
        $.ajax({
            url: `${this.host}/${endpoint}`,
            type: method,
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: callback,
            error: callback
        });
    }
}
