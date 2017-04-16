import $ from 'jquery';

export default class Uno {

    static fbLogin(user, callback) {
        console.log(user, "Uno fbLogin");
        Uno.api(`facebook/users/login`, 'POST', user, callback);
    }

    static fbSubscribeToPage(page, callback) {
        console.log(page, "Uno fbSubscribeToPage");
        Uno.api(`facebook/pages/`, 'POST', page, callback);
    }

    static api(endpoint, method, data, callback) {

        if (arguments.length == 2) {
            callback = method;
            method = 'get';
        } else if (arguments.length == 3) {
            callback = data;
            data = null;
        }

        console.log(endpoint, "Uno API request");
        $.ajax({
            url: endpoint,
            type: method,
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: callback,
            error: callback
        });
    }
}
